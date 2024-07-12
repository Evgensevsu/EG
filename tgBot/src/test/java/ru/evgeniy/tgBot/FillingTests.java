package ru.evgeniy.tgBot;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import ru.evgeniy.tgBot.entity.*;
import ru.evgeniy.tgBot.repository.*;

import java.math.BigDecimal;
@SpringBootTest
public class FillingTests {
    @Autowired
    private ClientRep clientRep;
    @Autowired
    private CategoryRep categoryRep;
    @Autowired
    private ProductRep productRep;
    @Autowired
    private ClientOrderRep clientOrderRep;
    @Autowired
    private OrderProductRep orderProductRep;

    @Test
    void createThreeClients(){
        Client client1 = new Client();
        client1.setExternalId(1L);
        client1.setFullName("Гринченко Евгений Константинович");
        client1.setPhoneNumber("79783136320");
        client1.setAddress("Севастополь");
        clientRep.save(client1);

        Client client2 = new Client();
        client2.setExternalId(2L);
        client2.setFullName("Гринченко Арсений Константинович");
        client2.setPhoneNumber("79494946590");
        client2.setAddress("Донецк");
        clientRep.save(client2);
    }

    @Test
        // Основные категории
    void createCategoriesAndProducts() {
        // Категория еды пицца
        Category pizza = new Category();
        pizza.setName("Пицца");
        pizza.setParent(null);
        categoryRep.save(pizza);

        // Категория еды роллы
        Category rolls = new Category();
        rolls.setName("Роллы");
        rolls.setParent(null);
        categoryRep.save(rolls);
        // Подкатегории для роллов
        Category classicRolls = new Category();
        classicRolls.setName("Классические роллы");
        classicRolls.setParent(rolls);
        categoryRep.save(classicRolls);

        Category bakedRolls = new Category();
        bakedRolls.setName(" Запеченные роллы");
        bakedRolls.setParent(rolls);
        categoryRep.save(bakedRolls);

        Category sweetRolls = new Category();
        sweetRolls.setName("Сладкие роллы");
        sweetRolls.setParent(rolls);
        categoryRep.save(sweetRolls);

        Category sets = new Category();
        sets.setName("Наборы");
        sets.setParent(rolls);
        categoryRep.save(sets);

        // Категория еды бургеры
        Category burgers = new Category();
        burgers.setName("Бургеры");
        burgers.setParent(null);
        categoryRep.save(burgers);
        // Подкатегории для бургеров
        Category classicBurgers = new Category();
        classicBurgers.setName("Классические бургеры");
        classicBurgers.setParent(burgers);
        categoryRep.save(classicBurgers);

        Category spicyBurgers = new Category();
        spicyBurgers.setName("Острые бургеры");
        spicyBurgers.setParent(burgers);
        categoryRep.save(spicyBurgers);

        // Напитки
        Category drinks = new Category();
        drinks.setName("Напитки");
        drinks.setParent(null);
        categoryRep.save(drinks);
        // Подкатегории для напитков
        Category carbonatedDrinks = new Category();
        carbonatedDrinks.setName("Газированные напитки");
        carbonatedDrinks.setParent(drinks);
        categoryRep.save(carbonatedDrinks);

        Category energyDrinks = new Category();
        energyDrinks.setName("Энергетические напитки");
        energyDrinks.setParent(drinks);
        categoryRep.save(energyDrinks);

        Category juices = new Category();
        juices.setName("Соки");
        juices.setParent(drinks);
        categoryRep.save(juices);

        Category otherDrinks = new Category();
        otherDrinks.setName("Другие");
        otherDrinks.setParent(drinks);
        categoryRep.save(otherDrinks);


        // Для пиццы
        Product napoletana = new Product();
        napoletana.setCategory(pizza);
        napoletana.setName("Неаполитанская Пицца");
        napoletana.setDescription("Маринара: томатный соус, чеснок, орегано, оливковое масло. Маргарита: томатный соус, моцарелла, свежий базилик.");
        napoletana.setPrice(BigDecimal.valueOf(500.0));
        productRep.save(napoletana);

        Product marinara = new Product();
        marinara.setCategory(pizza);
        marinara.setName("Пицца Маринара");
        marinara.setDescription("томатный соус, чеснок, орегано, оливковое масло.");
        marinara.setPrice(BigDecimal.valueOf(500.0));
        productRep.save(marinara);

        Product margherita = new Product();
        margherita.setCategory(pizza);
        margherita.setName("Маргарита");
        margherita.setDescription("томатный соус, моцарелла, свежий базилик.");
        margherita.setPrice(BigDecimal.valueOf(500.0));
        productRep.save(margherita);

        // Для классических роллов
        Product makizushi = new Product();
        makizushi.setCategory(classicRolls);
        makizushi.setName("Макидзуси");
        makizushi.setDescription("Популярные разновидности начинок для Макидзуси это авокадо, различные виды рыб (лосось, тунец), омлет и рубленные морепродукты.");
        makizushi.setPrice(BigDecimal.valueOf(500.0));
        productRep.save(makizushi);

        Product canada = new Product();
        canada.setCategory(classicRolls);
        canada.setName("Канада");
        canada.setDescription("Лосось, авокадо, сливочный сыр и икра.");
        canada.setPrice(BigDecimal.valueOf(250.0));
        productRep.save(canada);

        Product bonito = new Product();
        bonito.setCategory(classicRolls);
        bonito.setName("Бонито");
        bonito.setDescription("Ролл с тунцом, чили соусом и свежим огурцом.");
        bonito.setPrice(BigDecimal.valueOf(250.0));
        productRep.save(bonito);

        //  Для запеченных роллов
        Product ebiSet = new Product();
        ebiSet.setCategory(bakedRolls);
        ebiSet.setName("Запечённые роллы ЭБИ");
        ebiSet.setDescription("Рис, маминори, тигровая креветка, майонез, огурец, красный сырный соус, унаги соус.");
        ebiSet.setPrice(BigDecimal.valueOf(250.0));
        productRep.save(ebiSet);

        Product taySet = new Product();
        taySet.setCategory(bakedRolls);
        taySet.setName("Запечённые роллы ТАЙ");
        taySet.setDescription("Рис, нори, жареный окунь, огурец, спайси соус, кунжут, унаги соус, сырная смесь.");
        taySet.setPrice(BigDecimal.valueOf(250.0));
        productRep.save(taySet);

        Product maguraSet = new Product();
        maguraSet.setCategory(bakedRolls);
        maguraSet.setName("Запечённые роллы МАГУРА");
        maguraSet.setDescription("Рис, нори, тунец, омлет тамаго, масаго, огурец, сырная смесь, унаги соус.");
        maguraSet.setPrice(BigDecimal.valueOf(250.0));
        productRep.save(maguraSet);

        // Для сладких роллов
        Product bananaRoll = new Product();
        bananaRoll.setCategory(sweetRolls);
        bananaRoll.setName("Шоколадные роллы с творожным сыром и бананами");
        bananaRoll.setDescription("4 шоколадных блинчика 6 столовых ложек творожного сыра 6 чайных ложек сгущённого молока 1 банан");
        bananaRoll.setPrice(BigDecimal.valueOf(250.0));
        productRep.save(bananaRoll);

        Product fruitRoll = new Product();
        fruitRoll.setCategory(sweetRolls);
        fruitRoll.setName("Роллы со сладким рисом и фруктами");
        fruitRoll.setDescription("Рисовая бумага Сладкая густая рисовая каша Сочные фрукты – персики, киви, ананас, апельсин");
        fruitRoll.setPrice(BigDecimal.valueOf(250.0));
        productRep.save(fruitRoll);

        Product creamyRoll = new Product();
        creamyRoll.setCategory(sweetRolls);
        creamyRoll.setName("Сливочные роллы с сиропом");
        creamyRoll.setDescription("Сыр типа маскарпоне либо же классическая «Филадельфия». Замороженные ягоды вишни, свежая клубника и груша. Сахар, вода, чайная ложка сахарной пудры, листочки мяты.");
        creamyRoll.setPrice(BigDecimal.valueOf(230.0));
        productRep.save(creamyRoll);

        // Продукты для наборов
        Product basicSet = new Product();
        basicSet.setCategory(sets);
        basicSet.setName("Базовый Сет");
        basicSet.setDescription("Сет из 5 классических роллов: 2 салмона, 2 тунца и 1 темпуры.");
        basicSet.setPrice(BigDecimal.valueOf(1000.0));
        productRep.save(basicSet);

        Product luxurySet = new Product();
        luxurySet.setCategory(sets);
        luxurySet.setName("Люкс Сет");
        luxurySet.setDescription("Элитный сет из 6 роллов: 2 унаги, 2 крабовых и 2 японских овощей.");
        luxurySet.setPrice(BigDecimal.valueOf(1200.0));
        productRep.save(luxurySet);

        Product rushSet = new Product();
        rushSet.setCategory(sets);
        rushSet.setName("Форсаж Сет");
        rushSet.setDescription("Активный сет из 8 роллов: 4 темпуры, 2 креветки и 2 осьминога.");
        rushSet.setPrice(BigDecimal.valueOf(1500.0));
        productRep.save(rushSet);

        // Для классических бургеров
        Product bogatyrskyBurger = new Product();
        bogatyrskyBurger.setCategory(classicBurgers);
        bogatyrskyBurger.setName("Богатырский чизбургер ");
        bogatyrskyBurger.setDescription("Большой сэндвич приготовленный в булочке с кунжутом с двумя большими котлетами из говядины, свежим луком, маринованными огурчиками, салатом айсберг, ароматным соусом гриль, двумя ломтиками помидора и кусочком сыра чеддер.");
        bogatyrskyBurger.setPrice(BigDecimal.valueOf(450.0));
        productRep.save(bogatyrskyBurger);

        Product tsarsky = new Product();
        tsarsky.setCategory(classicBurgers);
        tsarsky.setName("Царский Чикен ");
        tsarsky.setDescription("Большой сэндвич приготовленный в булочке с кунжутом с большой куриной котлетой, сырным и чесночным соусом, салатом айсберг, двумя ломтиками помидора и двумя кусочками сыра чеддер");
        tsarsky.setPrice(BigDecimal.valueOf(350.0));
        productRep.save(tsarsky);

        Product hotBurger = new Product();
        hotBurger.setCategory(classicBurgers);
        hotBurger.setName("Хот-бургер ");
        hotBurger.setDescription("Большой сэндвич, приготовленные в булочке с кунжутом, с большой котлетой из говядины, майонезом, острым соусом сальса, салатом айсберг, двумя ломтиками помидора, кусочком сыра чеддер и жгучим перцем халапеньо.");
        hotBurger.setPrice(BigDecimal.valueOf(450.0));
        productRep.save(hotBurger);

        // Для острых бургеров
        Product chefBurger = new Product();
        chefBurger.setCategory(spicyBurgers);
        chefBurger.setName("Шефбургер");
        chefBurger.setDescription("Шефбургер острый - это острая курочка в острой панировке Hot&Spicy, сочные листья салата, пикантные маринованные огурчики, лук, фирменный соус «Бургер» и булочка с черно-белым кунжутом. Состав: Огурцы маринованные, Салат Айсберг, Филе куриное острое, Лук репчатый, Булочка с кунжутом, Соус Бургер. Аллергены: Продукт переработки молока, Глютен, Продукты переработки яиц.");
        chefBurger.setPrice(BigDecimal.valueOf(250.0));
        productRep.save(chefBurger);

        Product maestroBurger = new Product();
        maestroBurger.setCategory(spicyBurgers);
        maestroBurger.setName("Маэстро Де Люкс острый");
        maestroBurger.setDescription("Бургер Маэстро Де Люкс острый - это нежная сливочная булочка бриошь, сочные овощи, сыр, бекон и легендарное куриное филе в острой панировке, приготовленное экспертами в курице - КФС. Состав: Томаты, Соус песто, Филе куриное острое, Салат айсберг, Сыр Чеддер, Лук репчатый, Булочка c травами.");
        maestroBurger.setPrice(BigDecimal.valueOf(350.0));
        productRep.save(maestroBurger);

        Product juniorBurger = new Product();
        juniorBurger.setCategory(spicyBurgers);
        juniorBurger.setName("Шефбургер Джуниор Острый");
        juniorBurger.setDescription("Шефбургер Джуниор Острый - это два сочных стрипса в острой панировке Hot&Spicy, сочные листья салата, пикантные маринованные огурчики, лук, фирменный соус «Бургер» и булочка с черно-белым кунжутом. Состав: Огурцы маринованные, Салат Айсберг, Лук репчатый, Булочка с кунжутом, Соус Бургер, Стрипсы из куриного филе острые. Аллергены: Продукт переработки молока, Глютен, Продукты переработки яиц.");
        juniorBurger.setPrice(BigDecimal.valueOf(200.0));
        productRep.save(juniorBurger);

        // Для газированных напитков
        Product cola = new Product();
        cola.setCategory(carbonatedDrinks);
        cola.setName("Pepsi");
        cola.setDescription("Классический напиток Pepsi в КФС.");
        cola.setPrice(BigDecimal.valueOf(109.0));
        productRep.save(cola);

        Product lemonade = new Product();
        lemonade.setCategory(carbonatedDrinks);
        lemonade.setName("Лимонад");
        lemonade.setDescription("Шеф Лимонад - это освежающий напиток с лимонным соком, который идеально подходит к курочке!");
        lemonade.setPrice(BigDecimal.valueOf(90.0));
        productRep.save(lemonade);

        Product crimson = new Product();
        crimson.setCategory(carbonatedDrinks);
        crimson.setName("Малиновый лимонад");
        crimson.setDescription("Малиновый лимонад - это бодрящий напиток со вкусом малины и добавлением льда. Хотите зарядиться энергией и освежиться в жаркий день - это то, что нужно!");
        crimson.setPrice(BigDecimal.valueOf(90.0));
        productRep.save(crimson);

        // Продукты для энергетических напитков
        Product adrenaline = new Product();
        adrenaline.setCategory(energyDrinks);
        adrenaline.setName("Adrenaline Rush game");
        adrenaline.setDescription("Adrenaline Rush - это напиток для всех, кто живет полной жизнью и верит в бесконечность человеческих возможностей, силу духа и энергию сердца!");
        adrenaline.setPrice(BigDecimal.valueOf(139.0));
        productRep.save(adrenaline);

        Product Red = new Product();
        Red.setCategory(energyDrinks);
        Red.setName("Red Bull Energy Drink");
        Red.setDescription("«Окрыляющий», по мнению производителей, энергетик Red Bull содержит в себе 400 мг таурина и 32 мг кофеина — в расчете на 100 мл.");
        Red.setPrice(BigDecimal.valueOf(100.0));
        productRep.save(Red);

        Product rockstar = new Product();
        rockstar.setCategory(energyDrinks);
        rockstar.setName("Burn");
        rockstar.setDescription("Энергетик изготовлен на основе кофеина и таурина, также содержит тонизирующий экстракт гуараны.");
        rockstar.setPrice(BigDecimal.valueOf(120.0));
        productRep.save(rockstar);

        // Для соков
        Product cherryJuice = new Product();
        cherryJuice.setCategory(juices);
        cherryJuice.setName("Сок J7 вишневый");
        cherryJuice.setDescription("Сок J7 вишневый объемом 200 мл.");
        cherryJuice.setPrice(BigDecimal.valueOf(69.0));
        productRep.save(cherryJuice);

        Product appleJuice = new Product();
        appleJuice.setCategory(juices);
        appleJuice.setName("Сок J7 яблочный");
        appleJuice.setDescription("Сок J7 яблочный объемом 200 мл.");
        appleJuice.setPrice(BigDecimal.valueOf(69.0));
        productRep.save(appleJuice);

        Product orangeJuice = new Product();
        orangeJuice.setCategory(juices);
        orangeJuice.setName("Сок J7 апельсиновый");
        orangeJuice.setDescription("Сок J7 апельсиновый объемом 200 мл.");
        orangeJuice.setPrice(BigDecimal.valueOf(69.0));
        productRep.save(orangeJuice);

        // Продукты для других напитков
        Product blackTea = new Product();
        blackTea.setCategory(otherDrinks);
        blackTea.setName("Чай Черный");
        blackTea.setDescription("Чай Черный от КФС подается в 2 вариантах: 300 мл - 69 рублей, 400 мл - 79 рублей.");
        blackTea.setPrice(BigDecimal.valueOf(69.0));
        productRep.save(blackTea);

        Product coffee = new Product();
        coffee.setCategory(otherDrinks);
        coffee.setName("Кофе Глясе");
        coffee.setDescription("Кофе Глясе 200 мл.");
        coffee.setPrice(BigDecimal.valueOf(69.0));
        productRep.save(coffee);

        Product raspberryPunch = new Product();
        raspberryPunch.setCategory(otherDrinks);
        raspberryPunch.setName("Малиновый пунш");
        raspberryPunch.setDescription("Малиновый пунш - это сладкий напиток с насыщенным ягодным вкусом. Объем 360 мл.");
        raspberryPunch.setPrice(BigDecimal.valueOf(99.0));
        productRep.save(raspberryPunch);
    }

    @Test
    void addOrdersForClients() {
        Client client1 = clientRep.findByExternalId(1L);
        Client client2 = clientRep.findByExternalId(2L);

        // Создаем заказы для каждого клиента
        ClientOrder order1 = new ClientOrder();
        order1.setClient(client1);
        order1.setStatus(1); // Пример статуса заказа
        order1.setTotal(new BigDecimal("700.00"));
        clientOrderRep.save(order1);

        ClientOrder order2 = new ClientOrder();
        order2.setClient(client2);
        order2.setStatus(1); // Пример статуса заказа
        order2.setTotal(new BigDecimal("1500.00"));
        clientOrderRep.save(order2);

        Product product1 = productRep.findByName("Бонито");
        Product product2 = productRep.findByName("Хот-Бургер");

        // Добавляем продукты в заказы
        OrderProduct orderProduct1 = new OrderProduct();
        orderProduct1.setClientOrder(order1);
        orderProduct1.setProduct(product1);
        orderProduct1.setCountProduct(1);
        orderProductRep.save(orderProduct1);
    }

}
