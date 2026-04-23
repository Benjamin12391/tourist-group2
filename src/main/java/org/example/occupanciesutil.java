package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class occupanciesutil {
    public static List<occupancies> master_data_occupancies() throws FileNotFoundException {
        String path="src/main/resources/Hotels.csv";
        Scanner sc = new Scanner(new File(path));
        List<occupancies> hoteldata=new ArrayList<>();
        while (sc.hasNextLine()) {
            String l=sc.nextLine();
            String[] p=l.split(",");

                int id =Integer.parseInt(p[0]);
                int room =Integer.parseInt(p[1]);
                int usedrooms =Integer.parseInt(p[2]);
                int beds =Integer.parseInt(p[3]);
                int usedbed =Integer.parseInt(p[4]);
                int year=Integer.parseInt(p[5]);
                int month=Integer.parseInt(p[6]);
                occupancies h= new occupancies(id,room,usedrooms,beds,usedbed,year,month );
                hoteldata.add(h);
        }
        return hoteldata;
    }
}
