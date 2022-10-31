package com.example.dominos.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity(name = "statuses")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
}
