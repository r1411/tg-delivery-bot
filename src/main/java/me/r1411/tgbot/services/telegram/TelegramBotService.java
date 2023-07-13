package me.r1411.tgbot.services.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import me.r1411.tgbot.entities.*;
import me.r1411.tgbot.services.CategoryService;
import me.r1411.tgbot.services.ClientOrderService;
import me.r1411.tgbot.services.ClientService;
import me.r1411.tgbot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TelegramBotService {
    private static final String BUTTON_MAIN_MENU_TEXT = "В основное меню";

    private static final String BUTTON_MAKE_ORDER_TEXT = "Оформить заказ";

    private static final String BUTTON_CLEAR_ORDER_TEXT = "Очистить заказ";

    private final TelegramBot telegramBot;

    private final CategoryService categoryService;

    private final ProductService productService;

    private final ClientService clientService;

    private final ClientOrderService clientOrderService;

    @Autowired
    public TelegramBotService(
            CategoryService categoryService,
            ProductService productService,
            ClientService clientService, ClientOrderService clientOrderService,
            @Value("${telegram.bot.token}") String botToken) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.clientService = clientService;
        this.clientOrderService = clientOrderService;
        this.telegramBot = new TelegramBot(botToken);
    }

    /**
     * Запуск бота (long poll)
     */
    @PostConstruct
    public void startBot() {
        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    // Обработка ответов пользователя

    private void processUpdate(Update update) {
        if (update.message() != null) {
            processMessage(
                    getOrRegisterClient(update.message().chat()),
                    update.message()
            );
        }

        if (update.callbackQuery() != null) {
            processCallback(
                    getOrRegisterClient(update.callbackQuery().message().chat()),
                    update.callbackQuery()
            );
            telegramBot.execute(new AnswerCallbackQuery(update.callbackQuery().id()));
        }
    }

    private void processMessage(Client client, Message msg) {
        Long chatId = msg.chat().id();
        String msgText = msg.text();

        if (msgText.startsWith("/")) {
            processCommand(client, msg);
            return;
        }

        switch (msgText) {
            case BUTTON_MAIN_MENU_TEXT -> {
                Keyboard mainKb = createSubCategoriesKeyboard(null);
                sendKeyboardMessage(chatId, "Главное меню", mainKb);
            }
            case BUTTON_CLEAR_ORDER_TEXT -> {
                ClientOrder order = clientOrderService.getOrCreateOpenOrder(client);
                clientOrderService.clearOrder(order);
                sendTextMessage(chatId, "Заказ очищен");
            }
            case BUTTON_MAKE_ORDER_TEXT -> {
                ClientOrder order = clientOrderService.getOrCreateOpenOrder(client);
                List<OrderProduct> orderProducts = clientOrderService.getProductsInOrder(order);
                if (orderProducts.size() == 0) {
                    sendTextMessage(chatId, "Заказ пуст!");
                    return;
                }

                if (client.getPhoneNumber() == null || client.getAddress() == null) {
                    sendTextMessage(chatId, "Вы не заполнили все данные о себе!");
                    return;
                }

                String summary = getOrderSummary(order, orderProducts);
                order.setStatus(ClientOrderStatus.FINISHED);
                clientOrderService.saveClientOrder(order);
                sendTextMessage(chatId, summary);
                sendTextMessage(chatId, "Заказ №" + order.getId() +
                        " подтвержден. Курьер уже едет к Вам по адресу: " + client.getAddress());
            }
            default -> {
                // Обрабатываем сообщение как имя категории товаров

                Optional<Category> categoryOpt = categoryService.findCategoryByName(msgText);
                if (categoryOpt.isEmpty()) {
                    sendTextMessage(chatId, "Нет такой категории!");
                    return;
                }

                Category category = categoryOpt.get();
                if (categoryService.hasChildren(category)) {
                    Keyboard keyboard = createSubCategoriesKeyboard(category);
                    sendKeyboardMessage(chatId, String.format("Выбрана категория: %s", category.getName()), keyboard);
                }

                List<Product> products = productService.searchProducts(null, category.getId());
                Keyboard keyboard = createInlineProductsKeyboard(products);
                sendKeyboardMessage(chatId, "Доступные товары:", keyboard);
            }
        }
    }

    private void processCommand(Client client, Message msg) {
        Long chatId = msg.chat().id();
        String[] parts = msg.text().split(" ");
        switch (parts[0]) {
            case "/start" -> {
                Keyboard startKb = createSubCategoriesKeyboard(null);
                sendKeyboardMessage(chatId, "Наполняйте заказ и нажимайте Оформить!", startKb);

                if (client.getPhoneNumber() == null) {
                    sendTextMessage(chatId, "Установите телефон: /setphone +79xxxxxxxxx");
                }

                if (client.getPhoneNumber() == null) {
                    sendTextMessage(chatId, "Установите адрес: /setaddress <адрес>");
                }
            }
            case "/setaddress" -> {
                if (parts.length < 2) {
                    sendTextMessage(chatId, "Использование: /setaddress <адрес>");
                    return;
                }

                String address = Arrays.stream(parts).skip(1).collect(Collectors.joining(" "));
                client.setAddress(address);
                clientService.saveClient(client);
                sendTextMessage(chatId, "Ваш адрес сохранен");
            }
            case "/setphone" -> {
                if (parts.length < 2) {
                    sendTextMessage(chatId, "Использование: /setphone +79xxxxxxxxx");
                    return;
                }

                String phone = parts[1];
                if (!phone.matches("^\\+79\\d{9}$")) {
                    sendTextMessage(chatId, "Неверный формат телефона! Формат: +79xxxxxxxxx");
                    return;
                }

                client.setPhoneNumber(phone);
                clientService.saveClient(client);
                sendTextMessage(chatId, "Ваш телефон сохранен");
            }
        }
    }

    private void processCallback(Client client, CallbackQuery callback) {
        Long chatId = callback.message().chat().id();
        String[] parts = callback.data().split(" ");
        switch (parts[0]) {
            case "addProduct" -> {
                Long productId = Long.parseLong(parts[1]);
                Optional<Product> productOpt = productService.findById(productId);
                if (productOpt.isEmpty()) {
                    sendTextMessage(chatId, "Неизвестный продукт!");
                    return;
                }

                Product product = productOpt.get();
                ClientOrder clientOrder = clientOrderService.getOrCreateOpenOrder(client);
                clientOrderService.addProductToOrder(clientOrder, product, 1);
                sendTextMessage(chatId, "Товар добавлен в заказ");
            }
            default -> sendTextMessage(chatId, "Неизвестное действие!");
        }
    }

    // Отправка ответов пользователю

    private void sendTextMessage(Long chatId, String msg) {
        SendMessage sendMessage = new SendMessage(chatId, msg);
        telegramBot.execute(sendMessage);
    }

    private void sendKeyboardMessage(Long chatId, String msg, Keyboard keyboard) {
        SendMessage sendMessage = new SendMessage(chatId, msg).replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    // Клавиатуры

    private Keyboard createSubCategoriesKeyboard(@Nullable Category parent) {
        List<Category> subCategories = categoryService.getSubCategories(parent);
        String[] topRow = subCategories.stream().map(Category::getName).toArray(String[]::new);
        ReplyKeyboardMarkup result = new ReplyKeyboardMarkup(topRow);

        result.addRow(BUTTON_CLEAR_ORDER_TEXT, BUTTON_MAKE_ORDER_TEXT);

        if (parent != null) {
            result.addRow(BUTTON_MAIN_MENU_TEXT);
        }

        return result;
    }

    private Keyboard createInlineProductsKeyboard(List<Product> products) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        products.forEach(product -> {
            String buttonText = String.format("%s — %.2f ₽", product.getName(), product.getPrice());
            String callbackData = "addProduct " + product.getId();
            keyboard.addRow(new InlineKeyboardButton(buttonText).callbackData(callbackData));
        });
        return keyboard;
    }

    /**
     * Вернуть пользователя или зарегать, если его еще нет в базе
     * @param chat Чат с пользователем
     */
    private Client getOrRegisterClient(Chat chat) {
        Optional<Client> client = clientService.findClientByExternalId(chat.id());
        if (client.isEmpty()) {
            Client newClient = new Client();
            newClient.setExternalId(chat.id());
            String fullName = chat.firstName();
            if (chat.lastName() != null) {
                fullName += " " + chat.lastName();
            }
            newClient.setFullName(fullName);
            return clientService.saveClient(newClient);
        } else {
            return client.get();
        }
    }


    /**
     * Получить текст со всеми позициями и итоговой ценой
     * @param orderProducts Список позиций
     */
    private String getOrderSummary(ClientOrder order, List<OrderProduct> orderProducts) {
        StringBuilder sb = new StringBuilder();
        sb.append("Чек по заказу: \n\n");
        orderProducts.forEach(
                op -> sb.append(op.getProduct().getName())
                        .append(" ")
                        .append(op.getCountProduct())
                        .append("x")
                        .append(op.getProduct().getPrice())
                        .append(" = ")
                        .append(op.getProduct().getPrice().multiply(BigDecimal.valueOf(op.getCountProduct())))
                        .append(" ₽")
                        .append("\n")
        );
        sb.append("\n").append("Итого: ").append(order.getTotal()).append(" ₽");
        return sb.toString();
    }
}
