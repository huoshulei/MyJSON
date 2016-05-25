package edu.hsl.myjson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class JsonActivity extends AppCompatActivity {
    TextView       name1;
    TextView       name2;
    TextView       age1;
    TextView       age2;
    BufferedReader reader;
    StringBuffer sb = new StringBuffer();
    private static final String TAG = "JsonActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        name1 = (TextView) findViewById(R.id.tv_name_1);
        name2 = (TextView) findViewById(R.id.tv_name_2);
        age1 = (TextView) findViewById(R.id.tv_age_1);
        age2 = (TextView) findViewById(R.id.tv_age_2);
        new Thread() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://192.168.1.147:8080/index2.jsp");

                    HttpURLConnection urlConnection =
                            (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.connect();
                    InputStream inputStream = urlConnection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String strData;
                    while ((strData = reader.readLine()) != null) {
                        sb.append(strData);
                    }
                    String  data    = sb.toString();
                    Message message = new Message();
                    message.what = 0;
                    message.obj = data;
                    handler.sendMessage(message);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (ProtocolException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String data = (String) msg.obj;
                    Log.d(TAG, "handleMessage: "+data);
                    //            JSONObject json = new JSONObject(data);
                    Gson gson = new Gson();
                    Students students = gson.fromJson(data, Students.class);
                    List<StudentsData> studentsDatas = students.getStudents();
                    name1.setText(studentsDatas.get(0).getName());
                    name2.setText(studentsDatas.get(1).getName());
                    age1.setText(studentsDatas.get(0).getAge());
                    age2.setText(studentsDatas.get(1).getAge());
                    break;
            }
        }
    };
}
