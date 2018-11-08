package app.dreammesh.lenovo.dt;


import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.location.LocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment implements LocationListener {


    private static Location loc = null;
    private LocationManager locManager;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_BTW_UPDATES = 1000;

    boolean isGPS = true;

    private double lat, longt;


    private String urlIst = "http://api.openweathermap.org/data/2.5/forecast?lon=";
    private String urlsnd = "&lat=";
    private String apiKey = "&appid=a179ef07bbbb79bddcbc6f3b41a38fc7&units=metric";



    private List<Dailyweather> ft  = new ArrayList<>();
    private RecyclerView fwui;






    public ForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_forecast, container, false);

        View rv = inflater.inflate(R.layout.fragment_forecast, container, false);



        fwui = (RecyclerView) rv.findViewById(R.id.w_rv2);
        LinearLayoutManager LM = new LinearLayoutManager(getContext());    //specifying the layout manager to use
        fwui.setLayoutManager(LM);
        fwui.setHasFixedSize(true);


        return rv;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {



        getLocation();




        super.onCreate(savedInstanceState);

        String h = Double.toString(getLat());

        String i = Double.toString(getLongt());

        Log.e("GPS1", "SHow results starting " + h +" " + i);


        new getForecast().execute();

    }




private class getForecast extends AsyncTask<Void,Void,Void>{


    @Override
    protected void onPreExecute() {

        super.onPreExecute();



        String h = Double.toString(getLat());

        String i = Double.toString(getLongt());

        Log.e("GPS11", "SHow results starting " + h +" " + i);



    }



    @Override
    protected Void doInBackground(Void... voids) {

         HttpHandler urlparser = new HttpHandler();


        double lat1 = 0.0, longt1 = 0.0;

          lat1 = getLat();

          longt1 = getLongt();


          String wLat = Double.toString(lat1);

          String wLongt = Double.toString(longt1);


            Log.e("Link"," "+wLat+" "+wLongt);


           String wRoute = urlparser.ServiceConnector(urlIst + wLongt +urlsnd +wLat +apiKey);

//                Log.e("Answer ", wRoute);


        /// Pulling out JSON Data.


           if(wRoute != null){

               try{

                   // reading JSON from API
                   JSONObject  wreader = new JSONObject(wRoute);

                   JSONObject city = wreader.getJSONObject("city");
                   String Wcityname = city.getString("name");

                   String Wcountry = city.getString("country");


                    Log.e("RESULTS1"," "+Wcityname+" ,"+Wcountry);

                   //Try pulling JSON Array
                   JSONArray weatherList = wreader.getJSONArray("list");


                   //loop through all results in the List
                   for(int y = 0; y < weatherList.length(); y++){

                       JSONObject wList = weatherList.getJSONObject(y);

                       JSONObject wListmain =  wList.getJSONObject("main");
                       String defaultTemp =  wListmain.getString("temp");

                       String wDate = wList.getString("dt_txt");

                        Log.e("RESULTS", defaultTemp+" , "+ wDate);


                        //pull array within the LIST  array
                       JSONArray listW = wList.getJSONArray("weather");

                       for(int x = 0; x < listW.length();x++){

                        JSONObject listW1 = listW.getJSONObject(x);


                         String maindescx = listW1.getString("main");
                         String wicon = listW1.getString("icon");


                           Log.e("Inner results", maindescx+" , "+ wicon);


                           Dailyweather dwo = new Dailyweather(maindescx,wDate,wicon,defaultTemp);
                           ft.add(dwo);



                       }





                   }




               }catch(JSONException e){

                   Log.e("Null", "Catch Exception");



               }



           }else{

               Log.e("Null"," Results "+ wRoute);

               Log.e("Not reading"," Null ");
                  //local method.

               getActivity().runOnUiThread(new Runnable() {    //local method.
                   @Override
                   public void run() {
                       Toast.makeText(getContext(),
                               "Check your Network connection" ,
                               Toast.LENGTH_LONG).show();





                   }
               });
           }






        return null;
    }






    @Override
    protected void onPostExecute(Void v) {

        Dailyweatheradapter dw = new Dailyweatheradapter(ft,getContext());
        fwui.setAdapter(dw);

        super.onPostExecute(v);
    }
}




















    @Override
    public void onLocationChanged(Location location) {

//        getLocation();
   }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(i);

    }





private Location getLocation(){

    try{

        if(isGPS){

            locManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);  // If you are returning to a fragment while being inside a different fragment, and upon returning to the fragment you execute a method which is causing the Nullpointer(Content) Exception error
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_BTW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this ); // use getActivty to refer to the fragment first in contact with the main Activity.
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);





        }else{

            locManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            locManager.requestLocationUpdates(locManager.NETWORK_PROVIDER, MIN_BTW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            loc = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);




        }







    }catch(SecurityException e){

        e.getMessage();


    }






return loc;

}

    public double getLat(){

        if(loc!=null){

            lat = loc.getLatitude();

        }else{

            loc.setLatitude(0);
        }

        return lat;
    }



    public double getLongt(){

        if(loc!=null){

            longt = loc.getLongitude();
        }else{

            loc.setLongitude(0);

        }



        return longt;
    }











}
