package ru.evgeniy.tgBot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.evgeniy.tgBot.entity.*;
import ru.evgeniy.tgBot.repository.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TelegramBotConnection {
    private static final String SUBCATEGORY_PREFIX = "subcategory,";
    private static final String PRODUCT_PREFIX = "product:";
    private static final String DATA_PREFIX = "Инфо: ";

    private final Map<Long, STATES> userStates = new HashMap<>();
    private final Map<Long, List<Product>> selectedProducts = new HashMap<>();
    private final Map<Long, Client> activeClients = new HashMap<>();
    private TelegramBot bot;

    private final ClientRep clientRep;
    private final ClientOrderRep clientOrderRep;
    private final ProductRep productRep;
    private final CategoryRep categoryRep;
    private final OrderProductRep orderProductRep;
    private final EntitiesService entitiesService;

    public TelegramBotConnection(ClientRep clientRep, ClientOrderRep clientOrderRep, ProductRep productRep, CategoryRep categoryRep, OrderProductRep orderProductRep, EntitiesService entitiesService) {
        this.clientRep = clientRep;
        this.clientOrderRep = clientOrderRep;
        this.productRep = productRep;
        this.categoryRep = categoryRep;
        this.orderProductRep = orderProductRep;
        this.entitiesService = entitiesService;
    }

    private class TelegramUpdatesHandler implements UpdatesListener {
        @Override
        public int process(List<Update> updates) {
            try {
                updates.forEach(this::processSingleUpdate);
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            } catch (Exception e) {
                e.printStackTrace();
                return UpdatesListener.CONFIRMED_UPDATES_NONE;
            }
        }

        private void processSingleUpdate(Update update) {
            CallbackQuery callbackQuery = update.callbackQuery();
            if (callbackQuery != null) {
                String callbackData = callbackQuery.data();
                Long chatId = callbackQuery.message().chat().id();
                handleCallbackQuery(callbackData, chatId);
            } else {
                Message message = update.message();
                if (message != null) {
                    String userText = message.text();
                    Long chatId = message.chat().id();
                    STATES state = userStates.getOrDefault(chatId, STATES.INIT);
                    switch (state) {
                        case INIT -> handleClientData(userText, chatId);
                        case MENU -> handleMenuSelection(userText, chatId);
                    }
                }
            }
        }

        private void handleCallbackQuery(String callbackData, long chatId) {
            if (callbackData.startsWith(SUBCATEGORY_PREFIX)) {
                String subCategory = callbackData.split(",")[1];
                displayProductsForCategory(chatId, subCategory);
            } else if (callbackData.startsWith(PRODUCT_PREFIX)) {
                try {
                    String[] parts = callbackData.split(":");
                    if (parts.length != 3) {
                        throw new NumberFormatException("Invalid format");
                    }
                    int productId = Integer.parseInt(parts[1]);
                    Product selectedProduct = productRep.findById((long) productId).orElse(null);
                    if (selectedProduct != null) {
                        selectedProducts.computeIfAbsent(chatId, k -> new ArrayList<>()).add(selectedProduct);
                        sendTextToUser(chatId, String.format("Товар %s добавлен в заказ.", selectedProduct.getName()));
                    } else {
                        sendTextToUser(chatId, "Товар не найден.");
                    }
                } catch (NumberFormatException e) {
                    sendTextToUser(chatId, "Возникла ошибка. Попробуйте позже.");
                }
            }
        }

        private void handleMenuSelection(String text, Long chatId) {
            switch (text) {
                case "Пицца", "Классические роллы", "Запеченные роллы", "Сладкие роллы", "Наборы",
                        "Классические бургеры", "Острые бургеры",
                        "Газированные напитки", "Энергетические напитки", "Соки", "Другие" -> displayProductsForCategory(chatId, text);
                case "Роллы" -> displaySubCategoryOptions(chatId, 2L);
                case "Бургеры" -> displaySubCategoryOptions(chatId, 7L);
                case "Напитки" -> displaySubCategoryOptions(chatId, 10L);
                case "Оформить заказ" -> generateOrderSummary(chatId);
                default -> displayMenuOptions(chatId);
            }
        }

        private void sendWelcomeMessage(Long chatId) {
            sendTextToUser(chatId, """
                Привет! Я Телеграм-бот для автоматизации службы доставки.\n
                Введите данные пользователя:
                Формат ввода данных: 'Инфо: <Имя; Номер; Адрес;>'\n
                Пример ввода: Инфо: Олег; 79783910217; ул. Захарова, 4
                """);
        }

        private void handleClientData(String text, Long chatId) {
            if (text != null) {
                if (text.startsWith("/start")) {
                    sendWelcomeMessage(chatId);
                } else if (text.startsWith(DATA_PREFIX)) {
                    String[] userData = text.substring(DATA_PREFIX.length()).split(";");
                    if (userData.length >= 3) {
                        try {
                            Client client = clientRep.findFirstByExternalId(chatId);
                            if (client == null) {
                                client = new Client();
                                client.setExternalId(chatId);
                            }
                            client.setFullName(userData[0].trim());
                            client.setPhoneNumber(userData[1].trim());
                            client.setAddress(userData[2].trim());
                            clientRep.save(client);

                            activeClients.put(chatId, client);

                            sendTextToUser(chatId, "Данные успешно записаны");
                            displayMenuOptions(chatId);
                            userStates.put(chatId, STATES.MENU);
                        } catch (Exception e) {
                            sendTextToUser(chatId, "Ошибка при сохранении данных. Попробуйте снова.");
                        }
                    } else {
                        sendTextToUser(chatId, "Неверный формат ввода. Пожалуйста, введите данные в следующем формате: Имя; Номер; Адрес.");
                        sendTextToUser(chatId, "Введите данные пользователя заново:");
                    }
                } else {
                    sendTextToUser(chatId, "Введите команду /start для начала.");
                }
            }
        }

        private void displayMenuOptions(Long chatId) {
            ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup(
                    new KeyboardButton("Пицца"),
                    new KeyboardButton("Роллы"),
                    new KeyboardButton("Бургеры"),
                    new KeyboardButton("Напитки")
            ).addRow(
                    new KeyboardButton("Оформить заказ")
            ).resizeKeyboard(true);
            sendTextToUser(chatId, "Выберите пункт меню:", keyboard);
        }

        private void displayProductsForCategory(Long chatId, String categoryName) {
            List<Product> products = entitiesService.getCategoryProducts(categoryName);
            if (products == null || products.isEmpty()) {
                sendTextToUser(chatId, "Товары не найдены.");
                return;
            }
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            for (Product product : products) {
                String callbackData = String.format("product:%d:%s", product.getId(), categoryName);
                markup.addRow(new InlineKeyboardButton(String.format("Товар %s. Цена %.2f руб.", product.getName(), product.getPrice()))
                        .callbackData(callbackData));
            }
            sendTextToUser(chatId, "Товары", markup);
        }

        private void displaySubCategoryOptions(Long chatId, Long parentId) {
            List<Category> categories = categoryRep.findCategoriesByParentId(parentId);
            if (categories == null || categories.isEmpty()) {
                sendTextToUser(chatId, "Подкатегории не найдены.");
                return;
            }
            List<InlineKeyboardButton> buttons = categories.stream()
                    .map(category -> new InlineKeyboardButton(category.getName()).callbackData(SUBCATEGORY_PREFIX + category.getName()))
                    .collect(Collectors.toList());

            InlineKeyboardMarkup markup = new InlineKeyboardMarkup(buttons.toArray(new InlineKeyboardButton[0]));
            sendTextToUser(chatId, "Выберите подкатегорию:", markup);
        }

        private void generateOrderSummary(long chatId) {
            List<Product> products = selectedProducts.getOrDefault(chatId, new ArrayList<>());
            if (!products.isEmpty()) {
                StringBuilder summary = new StringBuilder("Ваш заказ:\n");
                BigDecimal totalCost = BigDecimal.ZERO;

                for (Product product : products) {
                    summary.append(product.getName()).append(". Цена: ").append(product.getPrice()).append(" руб.\n");
                    totalCost = totalCost.add(product.getPrice());
                }

                summary.append("\n");

                Client client = clientRep.findFirstByExternalId(chatId);
                ClientOrder order = new ClientOrder();
                order.setClient(client);
                order.setStatus(2);
                order.setTotal(totalCost);
                clientOrderRep.save(order);

                products.forEach(product -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setClientOrder(order);
                    orderProduct.setProduct(product);
                    orderProduct.setCountProduct(1);
                    orderProductRep.save(orderProduct);
                });

                selectedProducts.remove(chatId);

                Random rand = new Random();
                int waitTime = 30 + rand.nextInt(61);
                summary.append("Общая стоимость: ").append(totalCost).append(" руб.\n\n");
                summary.append("Курьер приедет по адресу: ").append(client.getAddress()).append(".\n\n");
                summary.append("Ожидайте доставку в течение ").append(waitTime).append(" минут.");
                sendTextToUser(chatId, summary.toString());
            } else {
                sendTextToUser(chatId, "Ошибка: Ваш заказ пуст.");
            }
        }

        private void sendTextToUser(Long chatId, String text) {
            bot.execute(new SendMessage(chatId, text));
        }

        private void sendTextToUser(Long chatId, String text, InlineKeyboardMarkup markup) {
            bot.execute(new SendMessage(chatId, text).replyMarkup(markup));
        }

        private void sendTextToUser(Long chatId, String text, ReplyKeyboardMarkup markup) {
            bot.execute(new SendMessage(chatId, text).replyMarkup(markup));
        }
    }

    @PostConstruct
    public void initializeBot() {
        bot = new TelegramBot("6751221933:AAFIsV_RoVPS2twH-3OIyvUtxDU-OYznFqI");
        bot.setUpdatesListener(new TelegramUpdatesHandler());
    }
}

enum STATES {
    INIT,
    MENU
}
