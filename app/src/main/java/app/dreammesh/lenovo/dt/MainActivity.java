package app.dreammesh.lenovo.dt;

import android.app.SearchManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LocationListener {

    private static Location loc = null;
    private LocationManager locManager;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_BTW_UPDATES = 1000;

    nowview W;

    TextView city1;

    TextView temp;

    TextView des;
    ImageButton vi;

    Geocoder geocoder;// for GEOCODE
    String city;//for GEOCODE
    private List<Address> wcity;  //for GEOCODE

    boolean isGPS = true;
    private double lat, longt;

    private static String TAG = MainActivity.class.getName();

    ImageView v;

    private String urlIst = "http://api.openweathermap.org/data/2.5/weather?lon=";  ///for CURRENT WEATHER
    private String urlsnd = "&lat=";
    private String apiKey = "&appid=a179ef07bbbb79bddcbc6f3b41a38fc7&units=metric";

    private String urlIst1 = "http://api.openweathermap.org/data/2.5/forecast?lon=";



    private List<nowview> WI = new ArrayList<>(); //  list
    private RecyclerView nwui;


    private RecyclerView rec;


    public static final int MY_PERMISSIONS_REQUEST = 10;




    private List<downview> DW = new ArrayList<>();

Typeface faceBold, faceBook;





    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        faceBold = Typeface.createFromAsset(getAssets(), "fonts/Gotham_Bold.ttf");
        faceBook = Typeface.createFromAsset(getAssets(), "fonts/Gotham_Book.otf");
        v= (ImageView)findViewById(R.id.imageView1);

        city1= (TextView)findViewById(R.id.city);
        city1.setTypeface(faceBook);
        des = (TextView)findViewById(R.id.desc);
        des.setTypeface(faceBold);
        temp = (TextView)findViewById(R.id.temp);
        temp.setTypeface(faceBold);

        vi =(ImageButton)findViewById(R.id.searchBut);


        Window window = getWindow(); // clear FLAG_TRANSLUCENT_STATUS flag:
         window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }


       checkPermission();




        String h = Double.toString(getLat());

        String i = Double.toString(getLongt());

        Log.e("G", "SHow results starting " + h + " " + i);

////        nwui = (RecyclerView)findViewById(R.id.w_r1);
//        LinearLayoutManager NWM = new LinearLayoutManager(this);
//        nwui.setLayoutManager(NWM);
//        nwui.setHasFixedSize(true);


        vi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onSearchRequested();
            }

        });






        rec =(RecyclerView)findViewById(R.id.w_rv21);
        LinearLayoutManager NWM1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
         rec.setLayoutManager(NWM1);
         rec.setHasFixedSize(true);



          new cForecast().execute();
          new WForecast().execute();


    }
















    








    private class cForecast extends AsyncTask<Void,Void,Void>{

       String cid = "";
        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            String h = Double.toString(getLat());

            String i = Double.toString(getLongt());

            Log.e("GPS", "SHow results starting" + h + " " + i);

            getLocation();



            Toast.makeText(MainActivity.this, "Loading your Weather Report", Toast.LENGTH_SHORT).show();

            super.onPreExecute();
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

                        String id = wList1.getString("id");

                        String desc = wList1.getString("description");
                        String wIcon = wList1.getString("icon");

                        cid = id;


                        Log.e("Inner results", desc + " , " + wIcon+" , "+id);


                        W = new nowview(wLName,desc,wTemp);

                        WI.add(W);

                    }


                    Log.e("Results", wTemp + " ," + wLName);


                } catch (JSONException e) {

                    System.out.println(e.getMessage());

                    Log.e("Error", "Some problem here");
                    Toast.makeText(MainActivity.this,
                            "Application error",
                            Toast.LENGTH_SHORT).show();

                }


            } else {

                Log.e("Not reading", " Null ");



                runOnUiThread(new Runnable() {    //local method.
                    @Override
                    public void run() {

                        Toast.makeText(MainActivity.this,
                                "Check your Network connection" ,
                                Toast.LENGTH_LONG).show();

                    }
                });



            }


            return null;
        }







        @Override
        protected void onPostExecute(Void aVoid) {
//            noWeatherAdapter n  = new noWeatherAdapter(WI, MainActivity.this);
//            nwui.setAdapter(n);

            Log.e("POST EXECUTE",cid);

          if(cid. equals("200")  || cid.equals("201") ||cid.equals("202")||cid.equals("210")||cid.equals("211")||cid.equals("212")||cid.equals("221")||cid.equals("230")||cid.equals("231")||cid.equals("232")||cid.equals("960")||cid.equals("961")){

              v.setImageResource(R.drawable.thunderstrike01);

          }else if(cid.equals("300")  || cid.equals("301")||cid.equals("302")||cid.equals("310")||cid.equals("311")||cid.equals("312")||cid.equals("313")||cid.equals("314")||cid.equals("321")){

              v.setImageResource(R.drawable.raindrops01);

          }else if(cid.equals("500") || cid.equals("501") ||cid.equals("502")||cid.equals("503")||cid.equals("504")||cid.equals("511")||cid.equals("520")||cid.equals("521")||cid.equals("522")||cid.equals("531")){

                v.setImageResource(R.drawable.heavyraindrops01);


          }else if(cid.equals("600") ||cid.equals("601")||cid.equals("602")||cid.equals("611")||cid.equals("612")||cid.equals("615")||cid.equals("616")||cid.equals("620")||cid.equals("621")|| cid.equals("622")){

              v.setImageResource(R.drawable.snowdrops01);

          }else if(cid.equals("701") ||cid.equals("711")||cid.equals("721")||cid.equals("731")||cid.equals("741")||cid.equals("751")||cid.equals("761")||cid.equals("762")||cid.equals("771")){

              v.setImageResource(R.drawable.fog01);

          } else if(cid.equals("781")  ||cid.equals("900") ||  cid.equals("901")||cid.equals("902") || cid.equals("962") ){

              v.setImageResource(R.drawable.tornado01);

          } else if(cid.equals("801") ||cid.equals("802")||cid.equals("803") || cid.equals("804")){

              v.setImageResource(R.drawable.plentyclouds01);

          }else if(cid.equals("800")  || cid.equals("903") ||cid.equals("904")||cid.equals("905") ||cid.equals("951") ||cid.equals("952")||cid.equals("953")||cid.equals("954") || cid.equals("955")||cid.equals("956")||cid.equals("957")||cid.equals("958")||cid.equals("959")){

              v.setImageResource(R.drawable.fewclouds01);

          }else{


              v.setImageResource(R.drawable.fewclouds01);
          }





            try {
                city1.setText(W.getCity().toUpperCase().trim());
                des.setText(W.getDesc().toUpperCase().trim());
                temp.setText(W.getTemp() + "°C");
                // v.setImageResource(R.drawable.broken_cloud_);
            }catch (NullPointerException e){

                e.getMessage();

            }

            super.onPostExecute(aVoid);
        }
    }





    //

    private  class WForecast extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {


            getLocation();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler urlParser1 = new HttpHandler();

            double lat11 = 0.0, lat22 = 0.0;

            lat11 = getLat();
            lat22 = getLongt();


            String llat = Double.toString(lat11);
            String llongt = Double.toString(lat22);


            String test = urlIst1 + llongt + urlsnd + llat + apiKey;
            String WC1 = urlParser1.ServiceConnector(test);



            if(WC1!=null){

               Log.e("TTTT",WC1);

                try{
                    JSONObject  wreader = new JSONObject(WC1);


                    //Try pulling JSON Array
                    JSONArray weatherList = wreader.getJSONArray("list");

                    //loop through all results in the List
                    for(int y = 0; y < weatherList.length(); y++){

                        JSONObject wList = weatherList.getJSONObject(y);

                        String wDate = wList.getString("dt_txt");


                        //pull array within the LIST  array
                        JSONArray listW = wList.getJSONArray("weather");


                        for(int x = 0; x < listW.length();x++){

                            JSONObject listW1 = listW.getJSONObject(x);

                            String wicon = listW1.getString("icon");

                            downview d1 =  new downview(wicon,wDate);

                            Log.e("SHOW ERROR",wicon+" "+wDate);


                            DW.add(d1);


                        }




                    }






                }catch(JSONException e){

                    System.out.println(e.getMessage());

                    Log.e("Error", "Some problem here");
                    Toast.makeText(MainActivity.this,
                            "Application error",
                            Toast.LENGTH_SHORT).show();



                }


            }else{


                Log.e("Not reading", " Null ");



                runOnUiThread(new Runnable() {    //local method.
                    @Override
                    public void run() {

                        Toast.makeText(MainActivity.this,
                                "Check your Network connection" ,
                                Toast.LENGTH_LONG).show();

                    }
                });


            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            downAdapter da = new downAdapter(DW,MainActivity.this);
            rec.setAdapter(da);



            super.onPostExecute(aVoid);
        }
    }










//        @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item_now) {
            // Home Page


//            doChangeFragment(new currentweather());



        } else if (id == R.id.item_weekly) {

//          doChangeFragment(new ForecastFragment());


        } else if (id == R.id.item_search) {

            onSearchRequested();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





//    private void doInitFragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment, fragment)
//                .commit();
//    }

//    private void doChangeFragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment, fragment)
//                .commit();
//    }



    @Override
    protected void onNewIntent(Intent intent) {

        setIntent(intent);
        handleIntent(intent);

    }


    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Intent i = new Intent(this, searchactivity.class);
            i.putExtra("city",query);

            startActivity(i);




        }

    }















        @TargetApi(Build.VERSION_CODES.M)
    public void checkPermission() {

        if ((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
        ((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))) {

//            Toast.makeText(getApplicationContext(),
//                    "Enable Your GPS for Use ",
//                    Toast.LENGTH_SHORT).show();

            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST);


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST);






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
//        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(i);

    }





    private Location getLocation() {

        try {

            if (isGPS) {

                locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_BTW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,  this);
                loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            } else {

                locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_BTW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                loc = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


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

            try{

                loc.setLatitude(0);

            }catch(NullPointerException e){

                e.getMessage();
            }





        }

        return lat;
    }


      public double getLongt() {

        if (loc != null) {

            longt = loc.getLongitude();
        } else {

            try{

                loc.setLongitude(0);

            }catch(NullPointerException e){

                e.getMessage();

            }

        }


        return longt;
    }
















}