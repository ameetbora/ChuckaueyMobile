package com.example.mlubli.chuckauey2;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    String server_url;
    Button button1;
    Button button2;
    Button button3;
    Switch switch1;
    Boolean isOn;

    //Location Parameters
    LocationManager locationManager;
    String lattitude,longitude;

    //Date and time Parameters
     Calendar calendar;
     SimpleDateFormat simpleDateFormat;
     SimpleDateFormat simpleDateFormat2;
     String Day;
     String Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     //   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
     //   fab.setOnClickListener(new View.OnClickListener() {
     //       @Override
     //       public void onClick(View view) {
     //           Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
     //                   .setAction("Action", null).show();
     //       }
     //   });

        button1 = (Button)findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,
                        MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        button2 = (Button)findViewById(R.id.button8);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,
                        MainActivity3.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button3 = (Button)findViewById(R.id.button9);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,
                        MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        textView5 = (TextView)findViewById(R.id.text_location5);
        textView6 = (TextView)findViewById(R.id.text_location6);
        textView7 = (TextView)findViewById(R.id.text_location7);
        textView8 = (TextView)findViewById(R.id.text_location8);
        textView9 = (TextView)findViewById(R.id.text_location9);

        switch1 = findViewById(R.id.switch1);

        //Date declarations
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat2 = new SimpleDateFormat("EEE");
        Time = simpleDateFormat.format(calendar.getTime());
        Day = simpleDateFormat2.format(calendar.getTime());
        Day = Day.replace(".", "");


        onClick(textView5);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                findViewById(R.id.loadingRing3).setVisibility(View.VISIBLE);
                findViewById(R.id.scrollView2).setVisibility(View.GONE);
                onClick(textView5);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // the main start of the actitivty
    public void onClick(View view) {
        String latLon;
        isOn = switch1.isChecked();



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            latLon = getLocation();
            if (isOn)
                server_url = "http://www.chuckauey.tk/chuckauey/findpark/?format=json&"+latLon+"&day="+Day+"&time="+Time+"&free=yes";
            else
                server_url = "http://www.chuckauey.tk/chuckauey/findpark/?format=json&"+latLon+"&day="+Day+"&time="+Time+"&free=no";

     //      server_url = "http://www.chuckauey.tk/chuckauey/findpark/?format=json&lat=-37.831318&lon=144.966338&day=Mon&time=07:50:00&free=no";

        }

        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        findViewById(R.id.loadingRing3).setVisibility(View.GONE);
                       // textView5.setVisibility(View.VISIBLE);
                        if (!response.equals("{\"Response\":\"No Nearby Free parking available\"}")) {
                            try {
                                JSONArray jsonArr = new JSONArray(response);
                                String responseYN;
                                String parkingType;
                                String distance;
                                String street;
                                int jArraySize = jsonArr.length();


                                // first text view
                                JSONObject jsonobj = jsonArr.getJSONObject(0);
                                responseYN = jsonobj.getString("Response");


                                if (responseYN.equals("No restriction"))
                                    parkingType = "Free";
                                else
                                    parkingType = jsonobj.getString("ParkingType");

                                distance = jsonobj.getString("Distance");
                                distance = checkDist(distance);
                                street = jsonobj.getString("Street");
                                street = checkStreet(street);
                                final String lon = jsonobj.getString("Lon");
                                final String lat = jsonobj.getString("Lat");

                                textView5.setText(street + "\n" + distance + "\n" + parkingType);
                                textView5.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + lat + ", " + lon;
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                                        startActivity(browserIntent);
                                    }
                                });

                                if (jArraySize > 1) {
                                    // Second text view
                                    jsonobj = jsonArr.getJSONObject(1);
                                    responseYN = jsonobj.getString("Response");

                                    textView6.setVisibility(View.VISIBLE);

                                    if (responseYN.equals("No restriction"))
                                        parkingType = "Free";
                                    else
                                        parkingType = jsonobj.getString("ParkingType");

                                    distance = jsonobj.getString("Distance");
                                    distance = checkDist(distance);
                                    street = jsonobj.getString("Street");
                                    street = checkStreet(street);
                                    final String lon2 = jsonobj.getString("Lon");
                                    final String lat2 = jsonobj.getString("Lat");

                                    textView6.setText(street + "\n" + distance + "\n" + parkingType);
                                    textView6.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + lat2 + ", " + lon2;
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                                            startActivity(browserIntent);
                                        }
                                    });
                                }
                                else
                                    textView6.setVisibility(View.INVISIBLE);

                                if (jArraySize >2) {

                                    //Third text view
                                    jsonobj = jsonArr.getJSONObject(2);
                                    responseYN = jsonobj.getString("Response");

                                    textView7.setVisibility(View.VISIBLE);


                                    if (responseYN.equals("No restriction"))
                                        parkingType = "Free";
                                    else
                                        parkingType = jsonobj.getString("ParkingType");

                                    distance = jsonobj.getString("Distance");
                                    distance = checkDist(distance);
                                    street = jsonobj.getString("Street");
                                    street = checkStreet(street);
                                    final String lon3 = jsonobj.getString("Lon");
                                    final String lat3 = jsonobj.getString("Lat");

                                    textView7.setText(street + "\n" + distance + "\n" + parkingType);
                                    textView7.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + lat3 + ", " + lon3;
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                                            startActivity(browserIntent);
                                        }
                                    });
                                }
                                else
                                    textView7.setVisibility(View.INVISIBLE);

                                if (jArraySize > 3) {
                                    //Forth text view
                                    jsonobj = jsonArr.getJSONObject(3);
                                    responseYN = jsonobj.getString("Response");

                                    textView8.setVisibility(View.VISIBLE);

                                    if (responseYN.equals("No restriction"))
                                        parkingType = "Free";
                                    else
                                        parkingType = jsonobj.getString("ParkingType");

                                    distance = jsonobj.getString("Distance");
                                    distance = checkDist(distance);
                                    street = jsonobj.getString("Street");
                                    street = checkStreet(street);
                                    final String lon4 = jsonobj.getString("Lon");
                                    final String lat4 = jsonobj.getString("Lat");

                                    textView8.setText(street + "\n" + distance + "\n" + parkingType);
                                    textView8.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + lat4 + ", " + lon4;
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                                            startActivity(browserIntent);
                                        }
                                    });
                                }
                                else
                                    textView8.setVisibility(View.INVISIBLE);

                                if (jArraySize > 4) {
                                    //Fifth text view
                                    jsonobj = jsonArr.getJSONObject(4);
                                    responseYN = jsonobj.getString("Response");
                                    textView9.setVisibility(View.VISIBLE);

                                    if (responseYN.equals("No restriction"))
                                        parkingType = "Free";
                                    else
                                        parkingType = jsonobj.getString("ParkingType");

                                    distance = jsonobj.getString("Distance");
                                    distance = checkDist(distance);
                                    street = jsonobj.getString("Street");
                                    street = checkStreet(street);
                                    final String lon5 = jsonobj.getString("Lon");
                                    final String lat5 = jsonobj.getString("Lat");

                                    textView9.setText(street + "\n" + distance + "\n" + parkingType);
                                    textView9.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + lat5 + ", " + lon5;
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                                            startActivity(browserIntent);
                                        }

                                    });
                                }
                                else
                                    textView9.setVisibility(View.INVISIBLE);

                                findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);

                                requestQueue.stop();

                            } catch (JSONException e) {
                                Log.e("ChuckAuey", "unexpected JSON exception", e);
                                // Do something to recover ... or kill the app.
                            }
                        }
                        else {
                            textView5.setText("No nearby free parkings available at the moment");
                            textView6.setVisibility(View.GONE);
                            textView7.setVisibility(View.GONE);
                            textView8.setVisibility(View.GONE);
                            textView9.setVisibility(View.GONE);
                            findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    textView5.setText("Timeout Error: Unable to connect to the server!");
                    error.printStackTrace();
                    findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
                } else if (error instanceof AuthFailureError) {
                    textView5.setText("Auth Error: Unable to connect to the server!");
                    error.printStackTrace();
                    findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
                } else if (error instanceof ServerError) {
                    textView5.setText("Server Error: Unable to connect to the server!");
                    error.printStackTrace();
                    findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
                } else if (error instanceof NetworkError) {
                    textView5.setText("Network Error: Unable to connect to the server!");
                    error.printStackTrace();
                    findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
                } else if (error instanceof ParseError) {
                    textView5.setText("Parseing Error: Unable to connect to the server!");
                    error.printStackTrace();
                    findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
                }


                requestQueue.stop();
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity2.this,
                    About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //converting the distence to propper value
    public String checkDist (String dist) {

        if (dist.charAt(0)!='0') {
            dist = dist.length() < 4 ? dist : dist.substring(0, 4);
            dist = dist + " km";
        }

            else if(dist.charAt(0)=='0' && dist.charAt(2)== '0'){
            dist = dist.length() < 5 ? dist : dist.substring(3, 5);
            dist = dist + " m";
        }

        else if (dist.charAt(0)=='0' && dist.charAt(2)!='0'){
            dist = dist.length() < 5 ? dist : dist.substring(2, 5);
            dist = dist + " m";
        }

        else {
            dist = dist.length() < 5 ? dist : dist.substring(0, 5);
            dist = dist + " km";
        }

        return dist;
    }

    //fixing some street names that have issues
    public String checkStreet (String street) {

        String[] result = street.split("\\s");
        if (result[0].equals("St"))
            street = result[0] +" "+ result[1]+" "+result[2] ;
        else
        street = result[0] +" "+ result [1];

        return street;
    }

//getting the location information
    private String getLocation() {
        String latLon = "";
        if (ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity2.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

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

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                //     textView.setText("Sorry, parking information for the current location is not availble"+"\n"+"\n"+"Your current location is"+ "\n" + "Lattitude = " + lattitude
                //           + "\n" + "Longitude = " + longitude + "\n" + "Day and time = " + Date);


            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                //    textView.setText("Sorry, parking information for the current location is not availble"+"\n"+"\n"+"Your current location is"+ "\n" + "Lattitude = " + lattitude
                //          + "\n" + "Longitude = " + longitude + "\n" + "Day and time = " + Date);

            } else {

                Toast.makeText(this, "Unable to Trace your location", Toast.LENGTH_SHORT).show();

            }
            latLon = "lat="+lattitude+"&lon="+longitude;
        }
        return latLon;
    }

//Alert message if the GPS is turned off
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



}
