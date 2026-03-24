package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hotel {
    //id,rooms,usedrooms,beds,usedbeds,year,month
    int  id;
    int  room;
    int usedrooms;
    int beds;
    int usedbeds;
    int year;
    int month;


}
