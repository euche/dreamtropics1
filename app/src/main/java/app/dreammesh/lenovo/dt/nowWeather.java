package app.dreammesh.lenovo.dt;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by LENOVO on 1/22/2018.
 */

public class nowWeather {


    private String iconW, wmainDesc, wcity;
    private String whum,wtemp;
    private long  wtime;


    //constructor

     public nowWeather(String humidity,String icon, String desc, String temp, long time, String city ){


         iconW = icon;
         wmainDesc = desc;
         wtemp = temp;
         wcity = city;
         wtime = time;
         whum = humidity;


     }


    public String getIconW() {
        return iconW;
    }

    public void setIconW(String iconW) {
        this.iconW = iconW;
    }

    public String getWmainDesc() {
        return wmainDesc;
    }

    public void setWmainDesc(String wmainDesc) {
        this.wmainDesc = wmainDesc;
    }

    public String getWcity() {
        return wcity;
    }

    public void setWcity(String wcity) {
        this.wcity = wcity;
    }

    public String getWtemp() {
        return wtemp;
    }

    public void setWtemp(String wtemp) {
        this.wtemp = wtemp;
    }

    public long getWtime() {
        return wtime;
    }

    public void setWtime(long wtime) {
        this.wtime = wtime;
    }

    public String getWhum() {
        return whum;
    }

    public void setWhum(String whum) {
        this.whum = whum;
    }

    public String getNdatetimeFormat(Context c){

        //SimpleDateFormat class is imported, used to format date and time //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm", c.getResources().getConfiguration().locale);
        df.setTimeZone(TimeZone.getDefault());
        return df.format(new Date(wtime));



    }
}
