package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.in;

public class WeatherForecast extends AppCompatActivity {

    ImageView imageView;
    TextView currentTempView;
    TextView minTempView;
    TextView maxTempView;
    ProgressBar progressBar;
    ForecastQuery forecastQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        imageView = findViewById(R.id.imageWeather);
        currentTempView = findViewById(R.id.currentTemp);
        minTempView = findViewById(R.id.minTemp);
        maxTempView = findViewById(R.id.maxTemp);

        progressBar = findViewById(R.id.progress_horizontal1);
        progressBar.setVisibility(View.VISIBLE);


        forecastQuery = new ForecastQuery();
        forecastQuery.execute();
    }



    class ForecastQuery extends AsyncTask<String, Integer, String>{

        private static final String ACTIVITY_NAME = "ForecastQuery";

        String minTemp,maxTemp,currentTemp;
        Bitmap weatherPicture;
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric";
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                InputStream in = conn.getInputStream();

                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);

                    int type;
                    while((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
                    {
                        if(parser.getEventType() == XmlPullParser.START_TAG)
                        {
                            if(parser.getName().equals("temperature") )
                            {
                                currentTemp = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                Log.d(ACTIVITY_NAME, currentTemp);
                                minTemp = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                Log.d(ACTIVITY_NAME, minTemp);
                                maxTemp = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                                Log.d(ACTIVITY_NAME, maxTemp);
                            }
                            else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";

                                Log.i(ACTIVITY_NAME,"File name: " + fileName);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);

                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d(ACTIVITY_NAME,"Already Exists");
                                    weatherPicture = BitmapFactory.decodeStream(fis);
                                }
                                else {
                                    String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                    weatherPicture = getImage(new URL(iconUrl));

                                    FileOutputStream outputStream = openFileOutput( fileName, Context.MODE_PRIVATE);
                                    weatherPicture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.d(ACTIVITY_NAME,"Downloaded from the Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }
                        }
                        parser.next();
                    }
                } finally {
                    in.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }


    @Override
    protected void onPostExecute(String a) {
        progressBar.setVisibility(View.INVISIBLE);
        imageView.setImageBitmap(weatherPicture);
        currentTempView.setText(currentTemp + "C\u00b0");
        minTempView.setText(minTemp + "C\u00b0");
        maxTempView.setText(maxTemp + "C\u00b0");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}

