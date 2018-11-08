package app.dreammesh.lenovo.dt;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by LENOVO on 1/10/2018.
 */

public class HttpHandler {


    private static final String TAG = HttpHandler.class.getSimpleName();




    public HttpHandler(){  //constructor


    }

    public String ServiceConnector(String requestURL){

        String response = null;

        try{
            URL url = new URL(requestURL);
            HttpURLConnection connect  = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(connect.getInputStream());
            response = convertStreamToString(in);






        } catch(MalformedURLException e) {
            Log.e(TAG, "MalformedURLException, " + e.getMessage());

        } catch(ProtocolException e){

            Log.e(TAG,"ProtocolException, " +e.getMessage());

        }catch(IOException e){

            Log.e(TAG,"IOException, " +e.getMessage());
        }



        return response;


    }

    private String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();


        String line;

        try {
            while ((line = reader.readLine()) != null) {

                sb.append(line).append('\n');

            }

        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.getMessage();
            }

        }

        return sb.toString();
    }




}
