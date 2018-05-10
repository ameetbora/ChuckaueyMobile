package com.example.mlubli.chuckauey2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final int REQUEST_LOCATION = 1;
    Button button;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    String server_url;
    Button button1;
    Button button2;
    Button button3;
    Button buttonL;
    Button buttonL2;

    String parkingTime;
    String responseYN;


    //notificationManger
    private final int NOTIFICATION_ID=1;

    //Location Parameters
    LocationManager locationManager;
    String lattitude,longitude;
    LatLng myLatLng;

    //Date and time Parameters
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat2;
    String Day;
    String Time;

    //map
    MapView mapView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initiate notification service

        buttonL = (Button)findViewById(R.id.button_location2) ;
        buttonL2 = (Button)findViewById(R.id.button_location3) ;
       // buttonL.setOnClickListener(new buttonLClickListener());


        //     FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //    fab.setOnClickListener(new View.OnClickListener() {
    //        @Override
     //       public void onClick(View view) {
      //          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
      //                  .setAction("Action", null).show();
      //      }
      //  });


        button1 = (Button)findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start();
                Intent intent = new Intent(MainActivity.this,
                        MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button2 = (Button)findViewById(R.id.button5);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start();
                Intent intent = new Intent(MainActivity.this,
                        MainActivity3.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button3 = (Button)findViewById(R.id.button6);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start();
                Intent intent = new Intent(MainActivity.this,
                        MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        buttonL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //save current parking location
                saveLocation();

                //update the map
                map.clear();
                updateMarker();

                //send notifications

                if (responseYN.equals("Yes")) {


                    notification(v,parkingTime);
                }

                //hide the parking result
               // textView.setVisibility(View.INVISIBLE);
               // textView2.setVisibility(View.INVISIBLE);
               // textView3.setVisibility(View.INVISIBLE);

                //hide the button
                buttonL.setVisibility(View.GONE);

                //show clear parking location button
                buttonL2.setVisibility(View.VISIBLE);
            }
        });

        buttonL2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start();

                clearLocation();

                //hide the button
                buttonL2.setVisibility(View.GONE);


                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(NOTIFICATION_ID);

                Intent intent = new Intent(MainActivity.this,
                        MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        textView = (TextView)findViewById(R.id.text_location1);
        textView2 = (TextView)findViewById(R.id.text_location2);
        textView3 = (TextView)findViewById(R.id.text_location3);
        textView3 = (TextView)findViewById(R.id.text_location4);

        button = (Button)findViewById(R.id.button_location);


        //Date declarations
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat2 = new SimpleDateFormat("EEE");
        Time = simpleDateFormat.format(calendar.getTime());
        Day = simpleDateFormat2.format(calendar.getTime());
        Day = Day.replace(".", "");
        String carLat = null;
        String carLon = null;


    if (!fileExists(this))
        start();
    else if (readFromFile(this).equals("no"))
        start();
    else
        start2(readFromFile(this));

   //     else
    //        start2(carLat, carLon);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
                Intent intent = new Intent(MainActivity.this,
                        MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(MainActivity.this,
                    About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

 /*   @Override
    public void onClick(View view) {
        Time = simpleDateFormat.format(calendar.getTime());
        Day = simpleDateFormat2.format(calendar.getTime());

        textView = (TextView)findViewById(R.id.text_location1);
        textView2 = (TextView)findViewById(R.id.text_location2);
        textView3 = (TextView)findViewById(R.id.text_location3);
        textView4 = (TextView)findViewById(R.id.text_location4);

        textView.setText("");
        textView2.setText("");
        textView3.setText("");
        textView4.setText("");

        findViewById(R.id.loadingRing).setVisibility(View.VISIBLE);
        findViewById(R.id.text_location1).setVisibility(View.GONE);
        findViewById(R.id.text_location2).setVisibility(View.GONE);
        findViewById(R.id.text_location3).setVisibility(View.GONE);
        findViewById(R.id.text_location4).setVisibility(View.GONE);
        findViewById(R.id.textView).setVisibility(View.GONE);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            getLocation();
        }
    }*/

    public void start() { 															//NEW METHOD
        Time = simpleDateFormat.format(calendar.getTime());
        Day = simpleDateFormat2.format(calendar.getTime());

        textView = (TextView)findViewById(R.id.text_location1);
        textView2 = (TextView)findViewById(R.id.text_location2);
        textView3 = (TextView)findViewById(R.id.text_location3);
        textView4 = (TextView)findViewById(R.id.text_location4);
        textView.setText("");
        textView2.setText("");
        textView3.setText("");
        textView4.setText("");
        findViewById(R.id.loadingRing).setVisibility(View.VISIBLE);
        findViewById(R.id.text_location1).setVisibility(View.GONE);
        findViewById(R.id.text_location2).setVisibility(View.GONE);
        findViewById(R.id.text_location3).setVisibility(View.GONE);
        findViewById(R.id.text_location4).setVisibility(View.GONE);
        findViewById(R.id.textView).setVisibility(View.GONE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            getLocation();
        }


    }

    public void start2(String latandlon) { 															//NEW METHOD
        Time = simpleDateFormat.format(calendar.getTime());
        Day = simpleDateFormat2.format(calendar.getTime());

        textView = (TextView)findViewById(R.id.text_location1);
        textView2 = (TextView)findViewById(R.id.text_location2);
        textView3 = (TextView)findViewById(R.id.text_location3);
        textView4 = (TextView)findViewById(R.id.text_location4);
        textView.setText("");
        textView2.setText("");
        textView3.setText("");
        textView4.setText("");
        findViewById(R.id.loadingRing).setVisibility(View.VISIBLE);
        findViewById(R.id.text_location1).setVisibility(View.GONE);
        findViewById(R.id.text_location2).setVisibility(View.GONE);
        findViewById(R.id.text_location3).setVisibility(View.GONE);
        findViewById(R.id.text_location4).setVisibility(View.GONE);
        findViewById(R.id.textView).setVisibility(View.GONE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            getLocation();
        }


        String[] arrayString = latandlon.split(",");
        String mLat = arrayString[0];
        String mLon = arrayString[1];


        buttonL2.setVisibility(View.VISIBLE);
        buttonL.setVisibility(View.GONE);

        //update the map
    //  map.clear();
    //  LatLng nl = new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLon));
    //  myLatLng = nl;
     //updateMarker();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {


            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);


            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

            //    textView.setText("Sorry, parking information for the current location is not availble"+"\n"+"\n"+"Your current location is"+ "\n" + "Lattitude = " + lattitude
              //          + "\n" + "Longitude = " + longitude + "\n" + "Day and time = " + Date);

            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);


           //     textView.setText("Sorry, parking information for the current location is not availble"+"\n"+"\n"+"Your current location is"+ "\n" + "Lattitude = " + lattitude
             //           + "\n" + "Longitude = " + longitude + "\n" + "Day and time = " + Date);


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

            //    textView.setText("Sorry, parking information for the current location is not availble"+"\n"+"\n"+"Your current location is"+ "\n" + "Lattitude = " + lattitude
              //          + "\n" + "Longitude = " + longitude + "\n" + "Day and time = " + Date);

            }else{

                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();

            }
           server_url = "http://www.chuckauey.tk/chuckauey/canpark/?format=json&lat="+lattitude+"&lon="+longitude+"&day="+Day+"&time="+Time;
          //  server_url = "http://www.chuckauey.tk/chuckauey/canpark/?format=json&lat=-37.811187187398097&lon=144.96735757463318&day=Mon&time=07:00:00";

                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {

                                    findViewById(R.id.loadingRing).setVisibility(View.GONE);
                                    JSONObject reader = new JSONObject(response);

                                    responseYN = reader.getString("Response");


                                    if (responseYN.equals("Yes")) {
                                     //   responseYN = "<font  color='#8bc34a'>" + responseYN + "</font>";
                                        parkingTime = reader.getString("ParkingTime");
                                        String parkingType = reader.getString("ParkingType");
                                        textView.setText(responseYN);
                                        findViewById(R.id.text_location1).setVisibility(View.VISIBLE);
                                        textView2.setText(parkingTime + " Hours");
                                        findViewById(R.id.text_location2).setVisibility(View.VISIBLE);
                                        textView3.setText(parkingType);
                                        findViewById(R.id.text_location3).setVisibility(View.VISIBLE);
                                        buttonL.setVisibility(View.VISIBLE);

                                    } else if (responseYN.equals("No restriction")) {
                                        textView.setText(Html.fromHtml("<font  color='#8bc34a'>Yes</font>"));
                                        findViewById(R.id.text_location1).setVisibility(View.VISIBLE);
                                        textView2.setText("No Restrictions");
                                        findViewById(R.id.text_location2).setVisibility(View.VISIBLE);
                                        textView3.setText("No Restrictions");
                                        findViewById(R.id.text_location3).setVisibility(View.VISIBLE);
                                        buttonL.setVisibility(View.VISIBLE);

                                    } else if (responseYN.equals("No parking information found")){
                                        textView4.setText("Parking information is not available for this location");
                                        findViewById(R.id.text_location4).setVisibility(View.VISIBLE);

                                    } else if (responseYN.equals("Can't park here")){
                                        String parkingType = reader.getString("ParkingType");
                                        textView4.setText(Html.fromHtml("<font  color='#ff0000'>No</font>, you can't park in this spot"));
                                        findViewById(R.id.text_location4).setVisibility(View.VISIBLE);

                                    }else {
                                        textView4.setText("Error in retriving data");
                                        findViewById(R.id.text_location4).setVisibility(View.VISIBLE);
                                    }
                                    if (responseYN.equals("Can't park here") || responseYN.equals("No parking information found"))
                                        findViewById(R.id.textView).setVisibility(View.VISIBLE);





                                    requestQueue.stop();

                                } catch (JSONException e) {
                                    Log.e("ChuckAuey", "unexpected JSON exception", e);
                                    // Do something to recover ... or kill the app.
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        textView.setText("Error: Unable to connect to the server!");
                        textView.setVisibility(View.VISIBLE);
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                });
                requestQueue.add(stringRequest);


        }
    }


    class buttonLClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            
            //save current parking location
            saveLocation();

            //update the map
            updateMarker();

            if (responseYN.equals("Yes")) {
                //send

                notification(v,parkingTime);
            }

            //hide the parking result
            textView.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
            textView3.setVisibility(View.INVISIBLE);

            //hide the button
            buttonL.setVisibility(View.INVISIBLE);
        }
    }

    private void updateMarker() {
        MarkerOptions a = new MarkerOptions()
                .position(myLatLng)
                .title("Parking Location");

        Marker m = map.addMarker(a);
        m.setPosition(myLatLng);
        m.showInfoWindow();
    }

    private void saveLocation() {

    //    SharedPreferences settings = getApplicationContext().getSharedPreferences("SavedLocation", 0);
    //    SharedPreferences.Editor editor = settings.edit();
    //    editor.putString("Lat", lattitude);
    //    editor.putString("Long",longitude);

        // Apply the edits!
    //    editor.apply();

        String carLatLon = lattitude+","+longitude;

        writeToFile(carLatLon, this);

        Toast.makeText(this,"Current Parking Location is Saved", Toast.LENGTH_LONG).show();

    }


    private void clearLocation() {

    writeToFile("no", this);

        Toast.makeText(this,"Last Known Parking is Cleared", Toast.LENGTH_LONG).show();

    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public boolean fileExists(Context context) {
        File file = context.getFileStreamPath("config.txt");
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void notification(View view,String parkAvailableTime) {
        //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.lufei);
        //Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        //set small icon
        mBuilder.setSmallIcon(R.drawable.parking);
        //set big icon
        //mBuilder.setLargeIcon(bitmap);
        //set title
        mBuilder.setContentTitle("Park reminder");
        //set content
        mBuilder.setContentText("Click to expand");
        //set abstract
        //mBuilder.setSubText("abstract");
        //set automatically clean after clicking
        mBuilder.setAutoCancel(false);
        //set specific text
        //mBuilder.setContentInfo("Info");




        //Total of live notifications
        // mBuilder.setNumber(2);
        //set ticker
        mBuilder.setTicker("ChuckAUey Reminder");
        //priority
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        //set notification time，unit is ms，当前设置为比系统时间少一小时
        mBuilder.setWhen(System.currentTimeMillis());
        //onGoing means user cannot clean it by swiping notification
        mBuilder.setOngoing(true);
        //set reminding style, vibrate：DEFAULT_VIBRATE     sound：NotificationCompat.DEFAULT_SOUND
        //light reminder:NotificationCompat.DEFAULT_LIGHTS     three all together：DEFAULT_ALL
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText("Your parking available time is: "+ parkAvailableTime+" Hour(s)");
        style.setBigContentTitle("Parking info");
        style.setSummaryText("ChuckAUey");
        mBuilder.setStyle(style);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification =  mBuilder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng nl;
        //map.getUiSettings().setScrollGesturesEnabled(false);


        if (!fileExists(this)) {
            nl = new LatLng(Double.parseDouble(lattitude), Double.parseDouble(longitude));
            myLatLng = nl;

            map.addMarker(new MarkerOptions()
                    .position(nl)
                    .title("Current Location")).showInfoWindow();
        }
        else if (readFromFile(this).equals("no"))
        {
            nl = new LatLng(Double.parseDouble(lattitude), Double.parseDouble(longitude));
            myLatLng = nl;

            map.addMarker(new MarkerOptions()
                    .position(nl)
                    .title("Current Location")).showInfoWindow();
        }
        else {
            String[] arrayString = readFromFile(this).split(",");
            String mLat = arrayString[0];
            String mLon = arrayString[1];
            nl = new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLon));
            myLatLng = nl;

            map.addMarker(new MarkerOptions()
                    .position(nl)
                    .title("Current Parking Location")).showInfoWindow();
        }



        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(nl, 18);
        map.animateCamera(cameraUpdate);



       // map.moveCamera(CameraUpdateFactory.newLatLng(ny));

    }

    public boolean isFileNo (){

        boolean ret;

        if (readFromFile(this).equals("no"))
            ret = true;

        else
            ret = false;

        return ret;
    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



}