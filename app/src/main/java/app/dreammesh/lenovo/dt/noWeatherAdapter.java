package app.dreammesh.lenovo.dt;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by LENOVO on 1/24/2018.
 */

public class noWeatherAdapter extends RecyclerView.Adapter<noWeatherAdapter.CurrentweatherviewHolder> {


       private List<nowview> nw ;
       private Context nwcontext;


    public noWeatherAdapter(List<nowview> nw, Context nwcontext) {// Constructor
        this.nw = nw;
        this.nwcontext = nwcontext;
    }




    public static class CurrentweatherviewHolder extends RecyclerView.ViewHolder{
      //  private ImageView wIcon;
        private TextView wcity;
        private TextView wmain;
        private TextView wtemp;
      //   private TextView whumid;
      //  private TextView time;



        public CurrentweatherviewHolder(View v) {   //INNER CLASS constructor


            super(v);

           // wIcon =(ImageView) v.findViewById(R.id.w_icon);
           // time =(TextView) v.findViewById(R.id.currentTime);
            wcity =(TextView) v.findViewById(R.id.city);
            wmain =(TextView) v.findViewById(R.id.desc);
            wtemp =(TextView) v.findViewById(R.id.temp);
          //  whumid =(TextView) v.findViewById(R.id.wHumidty);


        }





    }

    @Override
    public CurrentweatherviewHolder onCreateViewHolder(ViewGroup p, int viewType) {

        View nwv = LayoutInflater.from(p.getContext()).inflate(R.layout.now_weather, p ,false);
        return new CurrentweatherviewHolder(nwv);

    }


    @Override
    public void onBindViewHolder(CurrentweatherviewHolder holder, int position) {



        nowview w = nw.get(position);

//        holder.time.setText(w.getNdatetimeFormat(nwcontext));

        holder.wmain.setText(w.getDesc());

        holder.wtemp.setText(w.getTemp()+"°C");

//        holder.whumid.setText("Humidity "+w.getWhum()+"%");

        holder.wcity.setText(w.getCity());

//        Picasso.with(nwcontext)
//                .load("http://openweathermap.org/img/w/"+w.getIconW()+".png")
//                .into(holder.wIcon);




    }

    @Override
    public int getItemCount() {
        return nw.size();
    }
}


