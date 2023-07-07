package me.r1411.tgbot;

import me.r1411.tgbot.entities.Category;
import me.r1411.tgbot.entities.Product;
import me.r1411.tgbot.repositories.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FillingTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Order(1)
    void createCategories() {
        // Создание основных категорий
        Category pizzaCategory = new Category();
        pizzaCategory.setName("Пицца");
        categoryRepository.save(pizzaCategory);

        Category rollsCategory = new Category();
        rollsCategory.setName("Роллы");
        categoryRepository.save(rollsCategory);

        Category burgersCategory = new Category();
        burgersCategory.setName("Бургеры");
        categoryRepository.save(burgersCategory);

        Category drinksCategory = new Category();
        drinksCategory.setName("Напитки");
        categoryRepository.save(drinksCategory);

        // Подкатегории для категории Роллы
        Category subCategoryRollsClassic = new Category();
        subCategoryRollsClassic.setName("Классические роллы");
        subCategoryRollsClassic.setParent(rollsCategory);
        categoryRepository.save(subCategoryRollsClassic);

        Category subCategoryRollsBaked = new Category();
        subCategoryRollsBaked.setName("Запеченные роллы");
        subCategoryRollsBaked.setParent(rollsCategory);
        categoryRepository.save(subCategoryRollsBaked);

        Category subCategoryRollsSweet = new Category();
        subCategoryRollsSweet.setName("Сладкие роллы");
        subCategoryRollsSweet.setParent(rollsCategory);
        categoryRepository.save(subCategoryRollsSweet);

        Category subCategoryRollsSets = new Category();
        subCategoryRollsSets.setName("Наборы");
        subCategoryRollsSets.setParent(rollsCategory);
        categoryRepository.save(subCategoryRollsSets);

        // Подкатегории для категории Бургеры
        Category subCategoryBurgersClassic = new Category();
        subCategoryBurgersClassic.setName("Классические бургеры");
        subCategoryBurgersClassic.setParent(burgersCategory);
        categoryRepository.save(subCategoryBurgersClassic);

        Category subCategoryBurgersSpicy = new Category();
        subCategoryBurgersSpicy.setName("Острые бургеры");
        subCategoryBurgersSpicy.setParent(burgersCategory);
        categoryRepository.save(subCategoryBurgersSpicy);

        // Подкатегории для категории Напитки
        Category subCategoryDrinksCarbonated = new Category();
        subCategoryDrinksCarbonated.setName("Газированные напитки");
        subCategoryDrinksCarbonated.setParent(drinksCategory);
        categoryRepository.save(subCategoryDrinksCarbonated);

        Category subCategoryDrinksEnergy = new Category();
        subCategoryDrinksEnergy.setName("Энергетические напитки");
        subCategoryDrinksEnergy.setParent(drinksCategory);
        categoryRepository.save(subCategoryDrinksEnergy);

        Category subCategoryDrinksJuices = new Category();
        subCategoryDrinksJuices.setName("Соки");
        subCategoryDrinksJuices.setParent(drinksCategory);
        categoryRepository.save(subCategoryDrinksJuices);

        Category subCategoryDrinksOther = new Category();
        subCategoryDrinksOther.setName("Другие");
        subCategoryDrinksOther.setParent(drinksCategory);
        categoryRepository.save(subCategoryDrinksOther);
    }

    @Test
    @Order(2)
    void createProductsPizza() {
        // Товары для категории Пицца
        Category pizzaCategory = categoryRepository.findByName("Пицца").orElseThrow();

        Product pizzaPepperoni = new Product();
        pizzaPepperoni.setName("Пепперони");
        pizzaPepperoni.setDescription("Идеальное блюдо для небольшой компании");
        pizzaPepperoni.setPrice(BigDecimal.valueOf(499));
        pizzaPepperoni.setCategory(pizzaCategory);
        productRepository.save(pizzaPepperoni);

        Product pizzaMargarita = new Product();
        pizzaMargarita.setName("Маргарита");
        pizzaMargarita.setDescription("Идеальное блюдо для небольшой компании");
        pizzaMargarita.setPrice(BigDecimal.valueOf(389));
        pizzaMargarita.setCategory(pizzaCategory);
        productRepository.save(pizzaMargarita);

        Product pizzaHawaiian = new Product();
        pizzaHawaiian.setName("Гавайская пицца");
        pizzaHawaiian.setDescription("Блюдо для небольшой компании");
        pizzaHawaiian.setPrice(BigDecimal.valueOf(439));
        pizzaHawaiian.setCategory(pizzaCategory);
        productRepository.save(pizzaHawaiian);
    }

    @Test
    @Order(3)
    void createProductsRolls() {
        // Товары для категории Роллы
        Category rollsCategory = categoryRepository.findByName("Роллы").orElseThrow();

        Product rollsSavayaki = new Product();
        rollsSavayaki.setName("Ролл Саваяки");
        rollsSavayaki.setDescription("Создай пикантное настроение!");
        rollsSavayaki.setPrice(BigDecimal.valueOf(263));
        rollsSavayaki.setCategory(rollsCategory);
        productRepository.save(rollsSavayaki);

        Product rollsVega = new Product();
        rollsVega.setName("Ролл Вега");
        rollsVega.setDescription("Только свежесть!");
        rollsVega.setPrice(BigDecimal.valueOf(347));
        rollsVega.setCategory(rollsCategory);
        productRepository.save(rollsVega);

        Product rollsSinay = new Product();
        rollsSinay.setName("Ролл Синай");
        rollsSinay.setDescription("Дуэт лосося и тунца");
        rollsSinay.setPrice(BigDecimal.valueOf(495));
        rollsSinay.setCategory(rollsCategory);
        productRepository.save(rollsSinay);

        // Товары для подкатегории Классические роллы
        Category rollsClassicCategory = categoryRepository.findByName("Классические роллы").orElseThrow();

        Product rollsTuna = new Product();
        rollsTuna.setName("Ролл Тунец");
        rollsTuna.setDescription("Максимум белка в одной порции!");
        rollsTuna.setPrice(BigDecimal.valueOf(225));
        rollsTuna.setCategory(rollsClassicCategory);
        productRepository.save(rollsTuna);

        Product rollsShrimp = new Product();
        rollsShrimp.setName("Ролл Креветка");
        rollsShrimp.setDescription("По-королевски вкусно!");
        rollsShrimp.setPrice(BigDecimal.valueOf(215));
        rollsShrimp.setCategory(rollsClassicCategory);
        productRepository.save(rollsShrimp);

        Product rollsCucumber = new Product();
        rollsCucumber.setName("Ролл Огурец");
        rollsCucumber.setDescription("Ничего лишнего!");
        rollsCucumber.setPrice(BigDecimal.valueOf(115));
        rollsCucumber.setCategory(rollsClassicCategory);
        productRepository.save(rollsCucumber);

        // Товары для подкатегории Запеченные роллы
        Category rollsBakedCategory = categoryRepository.findByName("Запеченные роллы").orElseThrow();

        Product rollsYummy = new Product();
        rollsYummy.setName("Запеченный ролл Ями");
        rollsYummy.setDescription("Начни открывать новые вкусы сегодня!");
        rollsYummy.setPrice(BigDecimal.valueOf(337));
        rollsYummy.setCategory(rollsBakedCategory);
        productRepository.save(rollsYummy);

        Product rollsCrazyChicken = new Product();
        rollsCrazyChicken.setName("Запеченный ролл Крейзи Чикен");
        rollsCrazyChicken.setDescription("Улетная копченая курочка");
        rollsCrazyChicken.setPrice(BigDecimal.valueOf(327.1D));
        rollsCrazyChicken.setCategory(rollsBakedCategory);
        productRepository.save(rollsCrazyChicken);

        Product rollsLouisShrimp = new Product();
        rollsLouisShrimp.setName("Запеченный ролл Луи Креветтон");
        rollsLouisShrimp.setDescription("Попробуй вкус с брендовым стилем");
        rollsLouisShrimp.setPrice(BigDecimal.valueOf(445.2D));
        rollsLouisShrimp.setCategory(rollsBakedCategory);
        productRepository.save(rollsLouisShrimp);

        // Товары для подкатегории Сладкие роллы
        Category rollsSweetCategory = categoryRepository.findByName("Сладкие роллы").orElseThrow();

        Product rollsSumoSan = new Product();
        rollsSumoSan.setName("Сладкий ролл Сумо-Сан");
        rollsSumoSan.setDescription("Идеальный нежный лосось");
        rollsSumoSan.setPrice(BigDecimal.valueOf(457));
        rollsSumoSan.setCategory(rollsSweetCategory);
        productRepository.save(rollsSumoSan);

        Product rollsChickenMaki = new Product();
        rollsChickenMaki.setName("Сладкий ролл Чикен Маки");
        rollsChickenMaki.setDescription("Идеальный нежный лосось");
        rollsChickenMaki.setPrice(BigDecimal.valueOf(335));
        rollsChickenMaki.setCategory(rollsSweetCategory);
        productRepository.save(rollsChickenMaki);

        Product rollsOreo = new Product();
        rollsOreo.setName("Сладкий ролл Орео");
        rollsOreo.setDescription("Отличный выбор");
        rollsOreo.setPrice(BigDecimal.valueOf(345));
        rollsOreo.setCategory(rollsSweetCategory);
        productRepository.save(rollsOreo);

        // Товары для подкатегории Наборы
        Category rollsSetsCategory = categoryRepository.findByName("Наборы").orElseThrow();

        Product rollsHot = new Product();
        rollsHot.setName("Сет Hot");
        rollsHot.setDescription("Горячий сет с запеченными роллами");
        rollsHot.setPrice(BigDecimal.valueOf(917));
        rollsHot.setCategory(rollsSetsCategory);
        productRepository.save(rollsHot);

        Product rollsSorryGrandma = new Product();
        rollsSorryGrandma.setName("Сет Sorry бабушка");
        rollsSorryGrandma.setDescription("Большой сет для большой компании");
        rollsSorryGrandma.setPrice(BigDecimal.valueOf(1997));
        rollsSorryGrandma.setCategory(rollsSetsCategory);
        productRepository.save(rollsSorryGrandma);

        Product rollsAllInc = new Product();
        rollsAllInc.setName("Сет Все включено");
        rollsAllInc.setDescription("Сет из самых популярных роллов");
        rollsAllInc.setPrice(BigDecimal.valueOf(1597));
        rollsAllInc.setCategory(rollsSetsCategory);
        productRepository.save(rollsAllInc);
    }

    @Test
    @Order(4)
    void createProductsBurgers() {
        // Товары для категории Бургеры
        Category burgersCategory = categoryRepository.findByName("Бургеры").orElseThrow();

        Product burgerBarcelona = new Product();
        burgerBarcelona.setName("Барселона бургер");
        burgerBarcelona.setDescription("Сочный бифштекс из натуральной говядины");
        burgerBarcelona.setPrice(BigDecimal.valueOf(239));
        burgerBarcelona.setCategory(burgersCategory);
        productRepository.save(burgerBarcelona);

        Product burgerFish = new Product();
        burgerFish.setName("Фиш бургер");
        burgerFish.setDescription("Два кусочка филе рыбы под нежной булкой");
        burgerFish.setPrice(BigDecimal.valueOf(221));
        burgerFish.setCategory(burgersCategory);
        productRepository.save(burgerFish);

        Product burgerPremier = new Product();
        burgerPremier.setName("Бургер Премьер");
        burgerPremier.setDescription("Сочная котлета в хрустящей панировке");
        burgerPremier.setPrice(BigDecimal.valueOf(159));
        burgerPremier.setCategory(burgersCategory);
        productRepository.save(burgerPremier);

        // Товары для подкатегории Классические бургеры
        Category burgersClassicCategory = categoryRepository.findByName("Классические бургеры").orElseThrow();

        Product burgerBigTasty = new Product();
        burgerBigTasty.setName("Бургер Биг Тейсти");
        burgerBigTasty.setDescription("Большой и вкусный");
        burgerBigTasty.setPrice(BigDecimal.valueOf(512));
        burgerBigTasty.setCategory(burgersClassicCategory);
        productRepository.save(burgerBigTasty);

        Product burgerMidTasty = new Product();
        burgerMidTasty.setName("Бургер Мид Тейсти");
        burgerMidTasty.setDescription("Средний и вкусный");
        burgerMidTasty.setPrice(BigDecimal.valueOf(256));
        burgerMidTasty.setCategory(burgersClassicCategory);
        productRepository.save(burgerMidTasty);

        Product burgerSmallTasty = new Product();
        burgerSmallTasty.setName("Бургер Смол Тейсти");
        burgerSmallTasty.setDescription("Маленький и вкусный");
        burgerSmallTasty.setPrice(BigDecimal.valueOf(128));
        burgerSmallTasty.setCategory(burgersClassicCategory);
        productRepository.save(burgerSmallTasty);

        // Товары для подкатегории Острые бургеры
        Category burgersSpicyCategory = categoryRepository.findByName("Острые бургеры").orElseThrow();

        Product burgerChickenChef = new Product();
        burgerChickenChef.setName("Бургер Чикен Шеф");
        burgerChickenChef.setDescription("Острая и хустящая курица!");
        burgerChickenChef.setPrice(BigDecimal.valueOf(333));
        burgerChickenChef.setCategory(burgersSpicyCategory);
        productRepository.save(burgerChickenChef);

        Product burgerMaestro = new Product();
        burgerMaestro.setName("Бургер Маэстро");
        burgerMaestro.setDescription("Очень острая и хрустящая курица!");
        burgerMaestro.setPrice(BigDecimal.valueOf(444));
        burgerMaestro.setCategory(burgersSpicyCategory);
        productRepository.save(burgerMaestro);

        Product burgerMex = new Product();
        burgerMex.setName("Бургер Мекс");
        burgerMex.setDescription("Самая острая и хрустящая курица!");
        burgerMex.setPrice(BigDecimal.valueOf(555));
        burgerMex.setCategory(burgersSpicyCategory);
        productRepository.save(burgerMex);
    }

    @Test
    @Order(5)
    void createProductsDrinks() {
        // Товары для категории Напитки
        Category drinksCategory = categoryRepository.findByName("Напитки").orElseThrow();

        Product greenTeaDrink = new Product();
        greenTeaDrink.setName("Зеленый чай");
        greenTeaDrink.setDescription("Приятный горячий напиток");
        greenTeaDrink.setPrice(BigDecimal.valueOf(100));
        greenTeaDrink.setCategory(drinksCategory);
        productRepository.save(greenTeaDrink);

        Product blackTeaDrink = new Product();
        blackTeaDrink.setName("Черный чай");
        blackTeaDrink.setDescription("Приятный горячий напиток");
        blackTeaDrink.setPrice(BigDecimal.valueOf(100));
        blackTeaDrink.setCategory(drinksCategory);
        productRepository.save(blackTeaDrink);

        Product vanillaCocktailDrink = new Product();
        vanillaCocktailDrink.setName("Ванильный молочный коктейль");
        vanillaCocktailDrink.setDescription("Охладись в жаркую погоду!");
        vanillaCocktailDrink.setPrice(BigDecimal.valueOf(170));
        vanillaCocktailDrink.setCategory(drinksCategory);
        productRepository.save(vanillaCocktailDrink);

        // Товары для подкатегории Газированные напитки
        Category carbonatedDrinksCategory = categoryRepository.findByName("Газированные напитки").orElseThrow();

        Product pepsiDrink = new Product();
        pepsiDrink.setName("Pepsi-Cola");
        pepsiDrink.setDescription("Кола с пепсикольным вкусом");
        pepsiDrink.setPrice(BigDecimal.valueOf(99));
        pepsiDrink.setCategory(carbonatedDrinksCategory);
        productRepository.save(pepsiDrink);

        Product strawberryDrink = new Product();
        strawberryDrink.setName("Клубничный лимонад");
        strawberryDrink.setDescription("Приятный летний напиток");
        strawberryDrink.setPrice(BigDecimal.valueOf(125));
        strawberryDrink.setCategory(carbonatedDrinksCategory);
        productRepository.save(strawberryDrink);

        Product drPepperDrink = new Product();
        drPepperDrink.setName("Доктор перец");
        drPepperDrink.setDescription("Отлично уталит жажду");
        drPepperDrink.setPrice(BigDecimal.valueOf(123));
        drPepperDrink.setCategory(carbonatedDrinksCategory);
        productRepository.save(drPepperDrink);

        // Товары для подкатегории Энергетические напитки
        Category energyDrinksCategory = categoryRepository.findByName("Энергетические напитки").orElseThrow();

        Product redBullDrink = new Product();
        redBullDrink.setName("Red Bull");
        redBullDrink.setDescription("Энергетик №1");
        redBullDrink.setPrice(BigDecimal.valueOf(111));
        redBullDrink.setCategory(energyDrinksCategory);
        productRepository.save(redBullDrink);

        Product orangeBullDrink = new Product();
        orangeBullDrink.setName("Orange Bull");
        orangeBullDrink.setDescription("Энергетик №2");
        orangeBullDrink.setPrice(BigDecimal.valueOf(112));
        orangeBullDrink.setCategory(energyDrinksCategory);
        productRepository.save(orangeBullDrink);

        Product greenBullDrink = new Product();
        greenBullDrink.setName("Green Bull");
        greenBullDrink.setDescription("Энергетик №3");
        greenBullDrink.setPrice(BigDecimal.valueOf(113));
        greenBullDrink.setCategory(energyDrinksCategory);
        productRepository.save(greenBullDrink);

        // Товары для подкатегории Соки
        Category juicesDrinksCategory = categoryRepository.findByName("Соки").orElseThrow();

        Product appleJ7Drink = new Product();
        appleJ7Drink.setName("Сок J7 яблочный");
        appleJ7Drink.setDescription("Любимый вкус");
        appleJ7Drink.setPrice(BigDecimal.valueOf(80));
        appleJ7Drink.setCategory(juicesDrinksCategory);
        productRepository.save(appleJ7Drink);

        Product dobryOrangeDrink = new Product();
        dobryOrangeDrink.setName("Сок Добрый апельсин");
        dobryOrangeDrink.setDescription("Любимый вкус");
        dobryOrangeDrink.setPrice(BigDecimal.valueOf(85));
        dobryOrangeDrink.setCategory(juicesDrinksCategory);
        productRepository.save(dobryOrangeDrink);

        Product lubimyPeaDrink = new Product();
        lubimyPeaDrink.setName("Сок Любимый груша");
        lubimyPeaDrink.setDescription("Любимый вкус");
        lubimyPeaDrink.setPrice(BigDecimal.valueOf(90));
        lubimyPeaDrink.setCategory(juicesDrinksCategory);
        productRepository.save(lubimyPeaDrink);

        // Товары для подкатегории Другие
        Category otherDrinksCategory = categoryRepository.findByName("Другие").orElseThrow();

        Product kumysDrink = new Product();
        kumysDrink.setName("Кумыс");
        kumysDrink.setDescription("Очень хороший!");
        kumysDrink.setPrice(BigDecimal.valueOf(80));
        kumysDrink.setCategory(otherDrinksCategory);
        productRepository.save(kumysDrink);

        Product strawberryCompoteDrink = new Product();
        strawberryCompoteDrink.setName("Клубничный компот");
        strawberryCompoteDrink.setDescription("Уталяет жажду");
        strawberryCompoteDrink.setPrice(BigDecimal.valueOf(99));
        strawberryCompoteDrink.setCategory(otherDrinksCategory);
        productRepository.save(strawberryCompoteDrink);

        Product kvassDrink = new Product();
        kvassDrink.setName("Квас Попивайкин");
        kvassDrink.setDescription("Лучше нет!");
        kvassDrink.setPrice(BigDecimal.valueOf(88));
        kvassDrink.setCategory(otherDrinksCategory);
        productRepository.save(kvassDrink);
    }

}
