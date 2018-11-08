package app.dreammesh.lenovo.dt;


import android.app.SearchManager; // for Searching
import android.content.Intent;    //
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class searchweather extends Fragment {


    String isturl = "http://api.openweathermap.org/data/2.5/forecast?q=";
    String apikey = "&appid=a179ef07bbbb79bddcbc6f3b41a38fc7";



    public searchweather() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searchweather, container, false);


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);


        }



    }







    private void doMySearch(String j){

    final String wc = isturl+j+apikey;

    Log.e("Search",wc);

    Thread thread = new Thread(new Runnable(){

        @Override
        public void run() {


            HttpHandler sw = new HttpHandler();
              String swa = sw.ServiceConnector(wc);

          //   Log.e("Search1",swa);


            if(swa!= null){

                try{

                    JSONObject sweather = new JSONObject(swa);

                    String sreponse = sweather.getString("cod");

                    Log.e("RESULTTTTTS",sreponse);


//                    if(sreponse!=Integer.toString(200)){
//                        Log.e("RESULTS1",sreponse);
//                    }else{
//                       Log.e("RESULTS!","Check your input");
//                    }






                }catch(JSONException e){

                    Log.e("Search error"," Check for errors");
                }








            }else{

                Log.e("SEARCH:Not reading"," Null ");
                //local method.

                getActivity().runOnUiThread(new Runnable() {    //local method.
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),
                                "Please check your input and Network connection" ,
                                Toast.LENGTH_LONG).show();


                    }
                });

            }

        }
    });

   thread.start();






}


















}