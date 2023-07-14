package me.r1411.tgbot.services.impl;

import me.r1411.tgbot.entities.Client;
import me.r1411.tgbot.repositories.ClientRepository;
import me.r1411.tgbot.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> searchClientsByName(String name) {
        return clientRepository.findAllByFullNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<Client> findClientByExternalId(Long id) {
        return clientRepository.findByExternalId(id);
    }

    @Transactional
    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
}
