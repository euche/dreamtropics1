package app.dreammesh.lenovo.dt;

/**
 * Created by LENOVO on 2/6/2018.
 */

public class Dailyweather {


    String wdesc, wdate, wicon, wtemp;




    //CONSTRUCTOR
    public Dailyweather(String desc, String date, String icon, String temp ){

        wdesc = desc;
        wdate = date;
        wicon = icon;
        wtemp = temp;




    }


    public String getWdesc() {
        return wdesc;
    }

    public void setWdesc(String wdesc) {
        this.wdesc = wdesc;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }

    public String getWicon() {
        return wicon;
    }

    public void setWicon(String wicon) {
        this.wicon = wicon;
    }

    public String getWtemp() {
        return wtemp;
    }

    public void setWtemp(String wtemp) {
        this.wtemp = wtemp;
    }
}
