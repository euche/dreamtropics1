package app.dreammesh.lenovo.dt;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class currentweather extends Fragment implements LocationListener {

    private static Location loc = null;
    private LocationManager locManager1;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_BTW_UPDATES = 1000;

    Geocoder geocoder;// for GEOCODE
    String city;//for GEOCODE
    private List<Address> wcity;  //for GEOCODE

    boolean isGPS = true;
    private double lat, longt;

    private static String TAG = MainActivity.class.getName();

    private String urlIst = "http://api.openweathermap.org/data/2.5/weather?lon=";  ///for CURRENT WEATHER
    private String urlsnd = "&lat=";
    private String apiKey = "&appid=a179ef07bbbb79bddcbc6f3b41a38fc7&units=metric";

    private List<nowview> WI = new ArrayList<>(); // for list
    private RecyclerView nwui;



    public currentweather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_currentweather, container, false);

        nwui = (RecyclerView)root.findViewById(R.id.w_r);
        LinearLayoutManager NWM = new LinearLayoutManager(getContext());
        nwui.setLayoutManager(NWM);
        nwui.setHasFixedSize(true);

        return root;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {



        getLocation();

        String h = Double.toString(getLat());

        String i = Double.toString(getLongt());

        Log.e("GPS31", "SHow results starting " + h + " " + i);


        super.onCreate(savedInstanceState);


        new getWeatherForecast().execute();
    }


    private class getWeatherForecast extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            String h = Double.toString(getLat());

            String i = Double.toString(getLongt());

            Log.e("GPS", "SHow results starting" + h + " " + i);

            getLocation();



            Toast.makeText(getContext(), "Loading your Weather Report", Toast.LENGTH_SHORT).show();


        }

        @Override
        protected Void doInBackground(Void... voids) {

            HttpHandler urlParser = new HttpHandler();


            double lat1 = 0.0, lat2 = 0.0;

            lat1 = getLat();
            lat2 = getLongt();


            String llat = Double.toString(lat1);
            String llongt = Double.toString(lat2);

            Log.e("LINK", " " + llat + " " + llongt);

            String test = urlIst + llongt + urlsnd + llat + apiKey;
            String WC = urlParser.ServiceConnector(test);

            //SPACE for JSON DATA
            if (WC != null) {

                try {

                    //reading JSON from API
                    JSONObject wreader = new JSONObject(WC);

                    JSONObject wMain = wreader.getJSONObject("main");

                    String wTemp = wMain.getString("temp");
                    String wHum = wMain.getString("humidity");
                    String wLName = wreader.getString("name");



                    JSONArray wList = wreader.getJSONArray("weather");

                    //pulling JSON array
                    for (int y = 0; y < wList.length(); y++) {

                        JSONObject wList1 = wList.getJSONObject(y);

                        String desc = wList1.getString("description");
                        String wIcon = wList1.getString("icon");


                        Log.e("Inner results", desc + " , " + wIcon);

                        nowview W = new nowview( desc, wTemp,wLName);

                        WI.add(W);

                    }


                    Log.e("Results", wTemp + " ," + wLName);


                } catch (JSONException e) {

                    System.out.println(e.getMessage());

                    Log.e("Error", "Some problem here");
                    Toast.makeText(getContext(),
                            "Application error",
                            Toast.LENGTH_SHORT).show();

                }


            } else {

                Log.e("Not reading", " Null ");



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
        protected void onPostExecute(Void aVoid) {

            noWeatherAdapter n  = new noWeatherAdapter(WI, getContext());
            nwui.setAdapter(n);


            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

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

    private Location getLocation() {

            try {

                if (isGPS) {

                    locManager1 = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    locManager1.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_BTW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,  this);
                    loc = locManager1.getLastKnownLocation(LocationManager.GPS_PROVIDER);


                } else {

                    locManager1 = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    locManager1.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_BTW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    loc = locManager1.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                }


            } catch (SecurityException e) {

                e.getMessage();


            }


            return loc;

        }


        public double getLat() {

            if (loc != null) {

                lat = loc.getLatitude();

            } else {

                loc.setLatitude(0);
            }

            return lat;
        }


        public double getLongt() {

            if (loc != null) {

                longt = loc.getLongitude();
            } else {

                loc.setLongitude(0);

            }


            return longt;
        }





//
//   @TargetApi(Build.VERSION_CODES.M)
//        public void checkPermission() {
//
//            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                Toast.makeText(getContext(),
//                        "Enable Your GPS for Use ",
//                        Toast.LENGTH_SHORT).show();
//
//
//                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
//                ad.setTitle("GPS");
//                ad.setMessage("Do you want to turn on GPS");
//                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface d, int w) {
//
//                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(i);
//
//                    }
//
//
//                });
//
//                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface d, int w) {
//
//                        d.cancel();
//                    }
//
//
//                });
//
//                ad.show();
//
//            }
//
//
//        }
//
//

}