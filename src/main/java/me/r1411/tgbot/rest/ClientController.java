package me.r1411.tgbot.rest;

import me.r1411.tgbot.entities.Client;
import me.r1411.tgbot.entities.ClientOrder;
import me.r1411.tgbot.entities.Product;
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

    @Autowired
    public ClientController(ClientService clientService, ProductService productService) {
        this.clientService = clientService;
        this.productService = productService;
    }

    @GetMapping("/{id}/orders")
    public List<ClientOrder> getClientOrders(@PathVariable Long id) {
        return clientService.getClientOrders(id);
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
