package app.dreammesh.lenovo.dt;

/**
 * Created by u.anyanwu on 3/27/2018.
 */

public class nowview {

    private String city,desc,temp;


    public nowview(String city, String desc, String temp) {
        this.city = city;
        this.desc = desc;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
