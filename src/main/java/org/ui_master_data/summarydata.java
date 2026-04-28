package org.ui_master_data;

import org.example.Hotel;
import org.example.Hotelutil;

import java.io.FileNotFoundException;
import java.util.List;

public class summarydata {
   public static String[][] summarydata() throws FileNotFoundException {
        List<Hotel> hotels= Hotelutil.HotelData();
        int c[]=new int[6];
        double sumbeds[]=new double[6];
        double sumrooms[]=new double[6];
        double avgbeds[]=new double[6];
        double avgrooms[]=new double[6];

        for(Hotel rn:hotels){
            int cc=rn.getCategory().trim().length();
            if(cc>=1&&cc<=5){
                c[cc]++;
                sumbeds[cc]=sumbeds[cc]+rn.getNoBeds();
                sumrooms[cc]=sumrooms[cc]+rn.getNoRooms();
            }
        }
        for (int j=1;j<=5;j++){
            if(c[j] > 0){
                avgbeds[j]=sumbeds[j]/c[j];
                avgrooms[j]=sumrooms[j]/c[j];
            }

        }

       String[][] s =new String[5][];
       for (int j = 1; j <= 5; j++) {
           s[j-1]=new String[4];
           s[j-1][0]=""+j + " star hotels";
           s[j-1][1]=""+c[j];
           s[j-1][2]=""+ Math.round(avgbeds[j]*100)/100.0;
           s[j-1][3]=""+ Math.round(avgrooms[j]*100)/100.0;
       }

       return s;





    }
}
