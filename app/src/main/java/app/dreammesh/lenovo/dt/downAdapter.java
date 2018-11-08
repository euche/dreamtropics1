package app.dreammesh.lenovo.dt;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by u.anyanwu on 3/28/2018.
 */

public class downAdapter extends RecyclerView.Adapter<downAdapter.wklyviewHolder> {


    private List<downview> dwv;
    private Context c;


    public downAdapter(List<downview> dwv, Context c) {
        this.dwv = dwv;
        this.c = c;
    }

public static class wklyviewHolder extends RecyclerView.ViewHolder{

    private ImageView wicon;
    private TextView date, dt;
    private TextView time, t;

    Typeface faceBold,faceBook;

    public wklyviewHolder(View v) {    //INNER CLASS CONSTRUCTOR

        super(v);

        faceBold = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Gotham_Bold.ttf");
        faceBook = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Gotham_Book.otf");
        wicon =(ImageView)v.findViewById(R.id.fwicon);
        date = (TextView)v.findViewById(R.id.datetxt);
        date.setTypeface(faceBook);
        time = (TextView)v.findViewById(R.id.timetxt);
        time.setTypeface(faceBook);

        dt = (TextView)v.findViewById(R.id.dt);
        dt.setTypeface(faceBook);
        t=(TextView)v.findViewById(R.id.t);
        t.setTypeface(faceBook);


    }
}

    @Override
    public wklyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View dwv = LayoutInflater.from(parent.getContext()).inflate(R.layout.downnview, parent ,false);
        return new wklyviewHolder(dwv);

    }


    @Override
    public void onBindViewHolder(wklyviewHolder holder, int position) {


        downview dv = dwv.get(position);

        String dateTime = dv.getDatetxt();

        String date = dateTime.substring(0, 10);
         String time = dateTime.substring(11, 19);

        holder.date.setText(date);
        holder.time.setText(time);


        Picasso.with(c)
                .load("http://openweathermap.org/img/w/"+dv.getWicon()+".png")
                .into(holder.wicon);


    }


    @Override
    public int getItemCount() {
        return dwv.size();
    }
}



