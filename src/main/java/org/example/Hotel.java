package org.example;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "category")
    String category;

    @Column(name = "name")
    String name;

    @Column(name = "owner")
    String owner;

    @Column(name = "contact")
    String contact;

    @Column(name = "address")
    String address;

    @Column(name = "city")
    String city;

    @Column(name = "citycode")
    String citycode;

    @Column(name = "phone")
    String phone;

    @Column(name = "noRooms")
    int noRooms;

    @Column(name = "noBeds")
    int noBeds;


}
