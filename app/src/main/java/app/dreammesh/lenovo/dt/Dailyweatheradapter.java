package app.dreammesh.lenovo.dt;

/**
 * Created by LENOVO on 2/7/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;






public class Dailyweatheradapter extends RecyclerView.Adapter<Dailyweatheradapter.ForecastviewHolder>{


    private Context dwcontext;
    private List<Dailyweather> dws;



    //Constructor
    public Dailyweatheradapter( List<Dailyweather> dws,Context dw){

         dwcontext = dw;
          this.dws = dws;

    }


public static class ForecastviewHolder extends RecyclerView.ViewHolder{

     private ImageView icon;
     private TextView  desc;
     private TextView  date;
     private TextView  temp;


    public ForecastviewHolder(View v){

        super(v);


        icon = (ImageView)v.findViewById(R.id.wicon);
        date = (TextView)v.findViewById(R.id.wdate);
        desc = (TextView)v.findViewById(R.id.wdesc);
        temp = (TextView)v.findViewById(R.id.wtemp);



    }



}


    @Override
    public ForecastviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View fcv = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather,parent,false);

        return new ForecastviewHolder(fcv);

    }


    @Override
    public void onBindViewHolder(ForecastviewHolder holder, int position) {

        Dailyweather dw = dws.get(position);

        holder.date.setText(dw.getWdate());
        holder.desc.setText(dw.getWdesc());
        holder.temp.setText(dw.getWtemp()+"°C");

        Picasso.with(dwcontext)
                .load("http://openweathermap.org/img/w/"+dw.getWicon()+".png")
                .into(holder.icon);;


    }

    @Override
    public int getItemCount() {
        return dws.size();
    }
}
