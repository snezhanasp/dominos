package com.example.dominos.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column //todo change column name in db
    private String password;
    @Column
    private String phone;

    //todo make this sets?
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

}
