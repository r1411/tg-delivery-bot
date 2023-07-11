package me.r1411.tgbot.services.impl;

import me.r1411.tgbot.entities.Client;
import me.r1411.tgbot.entities.ClientOrder;
import me.r1411.tgbot.repositories.ClientOrderRepository;
import me.r1411.tgbot.repositories.ClientRepository;
import me.r1411.tgbot.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    private final ClientOrderRepository clientOrderRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientOrderRepository clientOrderRepository) {
        this.clientRepository = clientRepository;
        this.clientOrderRepository = clientOrderRepository;
    }

    @Override
    public List<Client> searchClientsByName(String name) {
        return clientRepository.findAllByFullNameContainingIgnoreCase(name);
    }

    @Override
    public List<ClientOrder> getClientOrders(Long id) {
        return clientOrderRepository.findAllByClientId(id);
    }
}
