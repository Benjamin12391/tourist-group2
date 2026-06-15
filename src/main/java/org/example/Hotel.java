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
    @Column(name = "id")

    private Integer id;

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

    @Column(name = "options", nullable = true)
    String options;

    // Keep existing convenience constructor to avoid changing many call sites.
    public Hotel(Integer id,
                 String category,
                 String name,
                 String owner,
                 String contact,
                 String address,
                 String city,
                 String citycode,
                 String phone,
                 int noRooms,
                 int noBeds) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.owner = owner;
        this.contact = contact;
        this.address = address;
        this.city = city;
        this.citycode = citycode;
        this.phone = phone;
        this.noRooms = noRooms;
        this.noBeds = noBeds;
        this.options = ""; // default empty
    }
}
