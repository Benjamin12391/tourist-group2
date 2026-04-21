package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hotel {
    int id;
    String category;
    String name;
    String owner;
    String contact;
    String address;
    String city;
    String citycode;
    String phone;
    int noRooms;
    int noBeds;



}
