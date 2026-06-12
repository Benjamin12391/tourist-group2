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
    // NOTE: removed @GeneratedValue because the target SQL Server table does not have
    // IDENTITY on the id column. We will assign ids in the DAO when needed to avoid
    // INSERT failures. If you later make the DB column IDENTITY, you can restore
    // @GeneratedValue(strategy = GenerationType.IDENTITY).
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
    // Persist options to DB. Make sure the `hotel` table has an `options` column
    // (e.g. NVARCHAR(255) NULL). If the DB doesn't have this column yet, run the
    // provided migration SQL before using the UI to save options.
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
