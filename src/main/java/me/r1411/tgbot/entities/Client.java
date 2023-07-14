package me.r1411.tgbot.entities;

import javax.persistence.*;

/**
 * Сущность Клиент
 */
@Entity
public class Client {
    /**
     * Уникальный ID клиента
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Внешний уникальный ID (Telegram)
     */
    @Column(nullable = false, unique = true)
    private Long externalId;

    /**
     * ФИО или форма обращения
     */
    @Column(nullable = false, length = 255)
    private String fullName;

    /**
     * Номер телефона клиента
     */
    @Column(length = 15)
    private String phoneNumber;

    /**
     * Адрес клиента
     */
    @Column(length = 400)
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
