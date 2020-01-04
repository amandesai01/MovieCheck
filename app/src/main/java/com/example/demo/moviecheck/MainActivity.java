package com.example.demo.moviecheck;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString();
                if(query.length() < 2){
                    editText.setError("Valid Name Please");
                    editText.requestFocus();
                    editText.setText("");
                    return;
                }
                else{
                    MyTask t1 = new MyTask();
                    t1.execute("https://www.omdbapi.com/?apikey=1c38dfd2&s=" + query);
                }
            }
        });
    }
    class MyTask extends AsyncTask<String, Void, String>{
        String searchResult = "";
        String jsonString = "";
        String line = "";
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();
                InputStream inputStreamReader = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamReader));
                while((line = reader.readLine()) != null){
                    jsonString += line + "\n";
                }
                if(jsonString != null){
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("Search");
                    for(int i = 0; i < jsonArray.length() ; i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        String title = movie.getString("Title");
                        String year = movie.getString("Year");
                        searchResult += title + " " + year + "\n";
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return searchResult;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(searchResult);
        }
    }
}
