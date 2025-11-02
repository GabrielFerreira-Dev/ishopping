package com.github.ishopping.clients.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "cpf", nullable = false, precision = 11)
    private String cpf;

    @Column(name = "address", precision = 100)
    private String address;

    @Column(name = "number_address", precision = 10)
    private String numberAddress;

    @Column(name = "district", precision = 100)
    private String district;

    @Column(name = "email", precision = 50)
    private String email;

    @Column(name = "phone_number", precision = 20)
    private String phoneNumber;


}
