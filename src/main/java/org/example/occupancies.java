package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class occupancies {
    int  id;
    int  room;
    int usedrooms;
    int beds;
    int usedbeds;
    int year;
    int month;


}
