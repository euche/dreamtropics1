package app.dreammesh.lenovo.dt;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class searchactivity extends AppCompatActivity {


   private String isturl = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String apikey = "&appid=a179ef07bbbb79bddcbc6f3b41a38fc7&units=metric";

    RecyclerView saUI;

    private List<nowview> SWI = new ArrayList<>();  //for list

    nowview SW;

    TextView city1;

    TextView temp;

    TextView des;

    ImageView v;

    Typeface face1,face2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchactivity);


//      Intent sa =  getIntent();
//       String s = sa.getStringExtra("city");

//        Log.e("Search",s);
//         domySearch(s);

        String w = dosearch();


        Log.e("Search",w);



        face1 = Typeface.createFromAsset(getAssets(), "fonts/Gotham_Bold.ttf");
        face2 = Typeface.createFromAsset(getAssets(), "fonts/Gotham_Book.otf");
        v= (ImageView)findViewById(R.id.wpic);

        city1= (TextView)findViewById(R.id.city1);
        city1.setTypeface(face2);
        des = (TextView)findViewById(R.id.desc1);
        des.setTypeface(face1);
        temp = (TextView)findViewById(R.id.temp1);
        temp.setTypeface(face1);




//        saUI = (RecyclerView) findViewById(R.id.sw_r);
//        LinearLayoutManager saLayout = new LinearLayoutManager(this);         //affix recycleview to supposed layout manager
//        saUI.setLayoutManager(saLayout);
//        saUI.setHasFixedSize(true);





       new currentsearch().execute();


    }




    private  String dosearch(){
       Intent test = getIntent();
        String s = test.getStringExtra("city");

        return s;

    }



    private class currentsearch extends AsyncTask <Void, Void, Void>{


        String cid;

        @Override
        protected void onPreExecute() {



            Toast.makeText(getApplicationContext(), "Loading your Weather Report", Toast.LENGTH_SHORT).show();

            super.onPreExecute();
        }



        @Override
        protected Void doInBackground(Void... voids) {

            String search1 = dosearch();


            final String h = isturl+search1+apikey;

            HttpHandler sw = new HttpHandler();

            String search2 = sw.ServiceConnector(h);


            if (search2!= null){

                try {


                    JSONObject searchW = new JSONObject(search2);  //Overrall
                        String sres = searchW.getString("cod");

                         Log.e("Search response", sres);




                     JSONObject swMain =  searchW.getJSONObject("main");     //JSONObject main


                        String swTemp = swMain.getString("temp");
                        String swHum = swMain.getString("humidity");
                        String swCname = searchW.getString("name");


                    JSONArray swWeather = searchW.getJSONArray("weather");

                      for(int r = 0; r < swWeather.length();r++){

                          JSONObject swWeather1 = swWeather.getJSONObject(r);


                          String id = swWeather1.getString("id");

                          String sWdesc =  swWeather1.getString("description");

                            String swWicon = swWeather1.getString("icon");

                             cid =id;

                           SW =  new nowview(swCname,sWdesc,swTemp);                   //CurrentWeather CLass

                          Log.e("Search response 2", id+" "+sWdesc+" "+swTemp+" "+swCname);

                            SWI.add(SW);




                      }






                }catch(JSONException e){

                    Log.e("Error", "Json parsing error:" +e.getMessage());


                }




            }else{

                Log.e("Not reading", " Null ");



                runOnUiThread(new Runnable() {    //local method.
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(),
                                "Check your Network connection or Check your Input" ,
                                Toast.LENGTH_LONG).show();

                    }
                });





            }







            return null;
        }




        @Override
        protected void onPostExecute(Void aVoid) {


//                noWeatherAdapter swadapter = new noWeatherAdapter(SWI, searchactivity.this);
//                saUI.setAdapter(swadapter);
            try {


            Log.e("POST EXECUTE 1",cid);

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






                city1.setText(SW.getCity().toUpperCase().trim());         //    SW.getCity()
                des.setText(SW.getDesc().toUpperCase().trim());            //SW.getDesc()
                temp.setText(SW.getTemp() + "°C");                          //SW.getTemp()
                // v.setImageResource(R.drawable.broken_cloud_);
            }catch (NullPointerException e){

                e.getMessage();

                runOnUiThread(new Runnable() {    //local method.
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(),
                                "Check your Network connection or Check your Input" ,
                                Toast.LENGTH_LONG).show();

                    }
                });





            }












            super.onPostExecute(aVoid);
        }
    }

















//    private void domySearch(String w){
//
//        final String wc =  isturl+w+apikey;
//
//        Log.e("Search1",wc);
//
//
//
//
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpHandler sw = new HttpHandler();
//                String swa = sw.ServiceConnector(wc);
//
////                Log.e("Search2",swa);
//
//
//
//                if(swa!= null){
//
//                    try{
//
//
//                        JSONObject searchW = new JSONObject(swa);  //Overrall
//                        String sres = searchW.getString("cod");
//
//                        Log.e("Search response", sres);
//
//
//                        JSONObject swMain =  searchW.getJSONObject("main");     //JSONObject main
//
//                        String swTemp = swMain.getString("temp");
//                        String swHum = swMain.getString("humidity");
//                        String swCname = searchW.getString("name");
//
//
//
//
//
//                        JSONArray swWeather = searchW.getJSONArray("weather");
//
//
//                        for(int r = 0; r < swWeather.length();r++){
//
//                            JSONObject swWeather1 = swWeather.getJSONObject(r);
//
//                            String sWdesc =  swWeather1.getString("description");
//
//                            String swWicon = swWeather1.getString("icon");
//
//
//
//                            nowWeather SW =  new nowWeather(swHum,swWicon,sWdesc,swTemp,System.currentTimeMillis(),swCname);                   //CurrentWeather CLass
//
//                            SWI.add(SW);
//
//
//
//
//                        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//                    }catch(JSONException e){
//
//                        Log.e("Search error"," Check for errors");
//
//                    }
//
//
//
//
//
//                }else{
//
//                    Log.e("Not reading", " Null ");
//
//                    runOnUiThread(new Runnable() {    //local method.
//                        @Override
//                        public void run() {
//
//                            Toast.makeText(getApplicationContext(),
//                                    "Check your Network connection" ,
//                                    Toast.LENGTH_LONG).show();
//
//                        }
//                    });
//
//
//
//                }
//
//
//
//
//
//
//
//            }
//        });
//
//
//             t.start();     //thread Starts here
//
//
//
//    }


}