package com.example.dominos.model.entities;

import lombok.Data;
import org.w3c.dom.ls.LSInput;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "statuses")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;

    @OneToMany(mappedBy = "status")
    private List<Order> orders;
}
