package me.r1411.tgbot.services.impl;

import me.r1411.tgbot.entities.*;
import me.r1411.tgbot.repositories.ClientOrderRepository;
import me.r1411.tgbot.repositories.OrderProductRepository;
import me.r1411.tgbot.services.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientOrderServiceImpl implements ClientOrderService {
    private final ClientOrderRepository clientOrderRepository;

    private final OrderProductRepository orderProductRepository;

    @Autowired
    public ClientOrderServiceImpl(
            ClientOrderRepository clientOrderRepository,
            OrderProductRepository orderProductRepository) {
        this.clientOrderRepository = clientOrderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public List<ClientOrder> getClientOrders(Long id) {
        return clientOrderRepository.findAllByClientId(id);
    }

    @Transactional
    @Override
    public ClientOrder saveClientOrder(ClientOrder clientOrder) {
        return clientOrderRepository.save(clientOrder);
    }

    @Transactional
    @Override
    public ClientOrder getOrCreateOpenOrder(Client client) {
        Optional<ClientOrder> openOrder = clientOrderRepository
                .findFirstByStatusAndClientId(ClientOrderStatus.CREATED, client.getId());

        return openOrder.orElseGet(() -> {
            ClientOrder order = new ClientOrder();
            order.setClient(client);
            order.setStatus(ClientOrderStatus.CREATED);
            order.setTotal(BigDecimal.ZERO);
            return clientOrderRepository.save(order);
        });
    }

    @Transactional
    @Override
    public void addProductToOrder(ClientOrder order, Product product, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be greater than 0");
        }

        Optional<OrderProduct> existingOrderProduct = orderProductRepository
                .findByClientOrderIdAndProductId(order.getId(), product.getId());

        OrderProduct op = existingOrderProduct.orElseGet(() -> {
            OrderProduct newOp = new OrderProduct();
            newOp.setClientOrder(order);
            newOp.setProduct(product);
            newOp.setCountProduct(0);
            return newOp;
        });

        op.setCountProduct(op.getCountProduct() + count);
        order.setTotal(order.getTotal().add(product.getPrice().multiply(BigDecimal.valueOf(count))));
        orderProductRepository.save(op);
        clientOrderRepository.save(order);
    }

    @Override
    public List<OrderProduct> getProductsInOrder(ClientOrder order) {
        return orderProductRepository.findAllByClientOrderId(order.getId());
    }

    @Transactional
    @Override
    public void clearOrder(ClientOrder order) {
        orderProductRepository.deleteAllByClientOrderId(order.getId());
        order.setTotal(BigDecimal.ZERO);
        clientOrderRepository.save(order);
    }
}
