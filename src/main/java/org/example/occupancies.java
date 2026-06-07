package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Hoteldata")
@Data
@NoArgsConstructor
public class occupancies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk; // surrogate primary key for DB

    @Column(name = "id")
    int  id;

    @Column(name = "rooms")
    int  room;

    @Column(name = "usedrooms")
    int usedrooms;

    @Column(name = "beds")
    int beds;

    @Column(name = "usedbeds")
    int usedbeds;

    @Column(name = "year")
    int year;

    @Column(name = "month")
    int month;

    // keep a constructor compatible with existing CSV loader usage
    public occupancies(int id, int room, int usedrooms, int beds, int usedbeds, int year, int month) {
        this.id = id;
        this.room = room;
        this.usedrooms = usedrooms;
        this.beds = beds;
        this.usedbeds = usedbeds;
        this.year = year;
        this.month = month;
    }

}
