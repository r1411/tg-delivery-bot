package me.r1411.tgbot.rest;

import me.r1411.tgbot.entities.Client;
import me.r1411.tgbot.entities.ClientOrder;
import me.r1411.tgbot.entities.Product;
import me.r1411.tgbot.services.ClientOrderService;
import me.r1411.tgbot.services.ClientService;
import me.r1411.tgbot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/clients")
public class ClientController {
    private final ClientService clientService;

    private final ProductService productService;

    private final ClientOrderService clientOrderService;

    @Autowired
    public ClientController(
            ClientService clientService,
            ProductService productService,
            ClientOrderService clientOrderService) {
        this.clientService = clientService;
        this.productService = productService;
        this.clientOrderService = clientOrderService;
    }

    @GetMapping("/{id}/orders")
    public List<ClientOrder> getClientOrders(@PathVariable Long id) {
        return clientOrderService.getClientOrders(id);
    }

    @GetMapping("/{id}/products")
    public List<Product> getClientProducts(@PathVariable Long id) {
        return productService.getClientProducts(id);
    }

    @GetMapping("/search")
    public List<Client> searchByName(@RequestParam String name) {
        return clientService.searchClientsByName(name);
    }
}
