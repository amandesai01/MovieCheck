package com.example.demo.moviecheck;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                    t1.execute("http://www.omdbapi.com/?apikey=1c38dfd2&s=" + query);
                }
            }
        });
        class MyTask extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... strings) {

            }
        }
    }
}
