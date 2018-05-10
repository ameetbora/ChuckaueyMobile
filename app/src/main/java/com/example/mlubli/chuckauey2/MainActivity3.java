package com.example.mlubli.chuckauey2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity {

    Button button;
    Button button1;
    Button button2;
    Button button3;
    String colorSelected;
    String shapeSelected;
    ArrayList<SignItem> sList;
    ArrayList<SignItem> fList;
    private RecyclerView mRecyclerView;
    private SignsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadSigns();



        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,3);

        mAdapter = new SignsAdapter(this,sList);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        shapeSelected = null;


        setupSpinner();
        setupSpinner2();


     //   mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        button1 = (Button)findViewById(R.id.button10);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this,
                        MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button2 = (Button)findViewById(R.id.button11);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this,
                        MainActivity3.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button3 = (Button)findViewById(R.id.button12);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this,
                        MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByCol(colorSelected);
            }
        });


    //    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //    fab.setOnClickListener(new View.OnClickListener() {
    //        @Override
     //       public void onClick(View view) {
     //           Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
     //                   .setAction("Action", null).show();
    //        }
    //    });

    }



    private void setupSpinner() {


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.select_color, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_list);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            String color = "";

               if (position == 1) {
                   color = "white";
                   findViewById(R.id.spinner2).setVisibility(View.VISIBLE);

               }
               else if (position ==2){
                   color="yellow";
                   findViewById(R.id.spinner2).setVisibility(View.VISIBLE);

               }
               else if (position == 3){
                   color="red";
                   findViewById(R.id.spinner2).setVisibility(View.VISIBLE);

               }
               else if (position ==4){
                   color="black";
                   findViewById(R.id.spinner2).setVisibility(View.VISIBLE);

               }
               else if (position ==5){
                   color="green";
                   findViewById(R.id.spinner2).setVisibility(View.VISIBLE);

               }
               else if (position ==6){
                   color="orange";
                   findViewById(R.id.spinner2).setVisibility(View.VISIBLE);

               }
               else if (position ==7){
                   color="blue";
                   findViewById(R.id.spinner2).setVisibility(View.VISIBLE);

               }
               else if (position ==0) {
                   color = "all";
                   findViewById(R.id.spinner2).setVisibility(View.GONE);
                   findViewById(R.id.button).setVisibility(View.GONE);
               }

               colorSelected = color;
               filterByCol(color);
                }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
             //   filterByCol("all");

            }
        });

    }

    private void setupSpinner2() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.select_shape, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_list);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            String shape = "";
                if (position == 1) {
                    shape = "circle";
                    findViewById(R.id.button).setVisibility(View.VISIBLE);
                }
                else if (position ==2){
                    shape="rectangle";
                    findViewById(R.id.button).setVisibility(View.VISIBLE);
                }
                else if (position ==3){
                    shape="diamond";
                    findViewById(R.id.button).setVisibility(View.VISIBLE);
                }
                else if (position ==4){
                    shape="triangle";
                    findViewById(R.id.button).setVisibility(View.VISIBLE);
                }
                else if (position ==5){
                    shape="octagon";
                    findViewById(R.id.button).setVisibility(View.VISIBLE);
                }
                else if (position==0) {
                    shape = "all";
                    findViewById(R.id.button).setVisibility(View.GONE);
                }

                filterByShape(shape);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
              //  filterByShape("all");

            }
        });

    }

    private void filterByCol(String color) {
    ArrayList<SignItem> colList = new ArrayList<>();
    boolean found;
    Spinner spinner = (Spinner) findViewById(R.id.spinner2);
    spinner.setSelection(0);

    if (color.equals("all")){
        colList = new ArrayList<>(sList);
    }

else{
    for (SignItem sign : sList){



         if (sign.getsColor1().equals(color) || sign.getsColor2().equals(color))
            colList.add(sign);
         found = true;

    }
    }
        fList = colList;
        mAdapter.filterList(colList);
    }


    private void filterByShape(String shape) {
        ArrayList<SignItem> shapeList = new ArrayList<>();
        boolean found = false;

        if (shape.equals("all")){
            filterByCol(colorSelected);
            shapeList = fList;

        }



else {
            for (SignItem sign : sList) {


                if (sign.getsShape().equals(shape) && (sign.getsColor1().equals(colorSelected) || sign.getsColor2().equals(colorSelected))) {
                    shapeList.add(sign);
                    found = true;
                    mAdapter.filterList(shapeList);
                }



            }
            if (!found) {
                Toast.makeText(this, "No Match Found", Toast.LENGTH_LONG).show();
                mAdapter.filterList(shapeList);
            }
        }



    }

    private void loadSigns() {
        sList = new ArrayList<>();

        try {
            String json = loadJSONFromAsset();
            JSONArray jsonArr = new JSONArray(json);
            String imageId;
            String name;
            String color1;
            String color2;
            String shape;
            String description;

            for (int i = 0; i< jsonArr.length(); i++)
            {
                JSONObject obj = jsonArr.getJSONObject(i);
                imageId = obj.getString("signNO");
                name = obj.getString("name");
                color1 = obj.getString("color1");
                color2 = obj.getString("color2");
                shape = obj.getString("shape1");
                description = obj.getString("descrption");


                sList.add(new SignItem(R.drawable.s001,imageId, name, color1, color2, shape, description));



            }


        }  catch (JSONException e) {
        Log.e("ChuckAuey", "unexpected JSON exception", e);

    }

        sList.get(0).setImageResource(R.drawable.s001);
        sList.get(1).setImageResource(R.drawable.s002);
        sList.get(2).setImageResource(R.drawable.s003);
        sList.get(3).setImageResource(R.drawable.s004);
        sList.get(4).setImageResource(R.drawable.s005);
        sList.get(5).setImageResource(R.drawable.s006);
        sList.get(6).setImageResource(R.drawable.s007);
        sList.get(7).setImageResource(R.drawable.s008);
        sList.get(8).setImageResource(R.drawable.s009);
        sList.get(9).setImageResource(R.drawable.s010);
        sList.get(10).setImageResource(R.drawable.s011);
        sList.get(11).setImageResource(R.drawable.s012);
        sList.get(12).setImageResource(R.drawable.s013);
        sList.get(13).setImageResource(R.drawable.s014);
        sList.get(14).setImageResource(R.drawable.s015);
        sList.get(15).setImageResource(R.drawable.s016);
        sList.get(16).setImageResource(R.drawable.s017);
        sList.get(17).setImageResource(R.drawable.s018);
        sList.get(18).setImageResource(R.drawable.s019);
        sList.get(19).setImageResource(R.drawable.s020);
        sList.get(20).setImageResource(R.drawable.s021);
        sList.get(21).setImageResource(R.drawable.s022);
        sList.get(22).setImageResource(R.drawable.s023);
        sList.get(23).setImageResource(R.drawable.s024);
        sList.get(24).setImageResource(R.drawable.s025);
        sList.get(25).setImageResource(R.drawable.s026);
        sList.get(26).setImageResource(R.drawable.s027);
        sList.get(27).setImageResource(R.drawable.s028);
        sList.get(28).setImageResource(R.drawable.s029);
        sList.get(29).setImageResource(R.drawable.s030);
        sList.get(30).setImageResource(R.drawable.s031);
        sList.get(31).setImageResource(R.drawable.s032);
        sList.get(32).setImageResource(R.drawable.s033);
        sList.get(33).setImageResource(R.drawable.s034);
        sList.get(34).setImageResource(R.drawable.s035);
        sList.get(35).setImageResource(R.drawable.s036);
        sList.get(36).setImageResource(R.drawable.s037);
        sList.get(37).setImageResource(R.drawable.s038);
        sList.get(38).setImageResource(R.drawable.s039);
        sList.get(39).setImageResource(R.drawable.s040);
        sList.get(40).setImageResource(R.drawable.s041);
        sList.get(41).setImageResource(R.drawable.s042);
        sList.get(42).setImageResource(R.drawable.s043);
        sList.get(43).setImageResource(R.drawable.s044);
        sList.get(44).setImageResource(R.drawable.s045);
        sList.get(45).setImageResource(R.drawable.s046);
        sList.get(46).setImageResource(R.drawable.s047);
        sList.get(47).setImageResource(R.drawable.s048);
        sList.get(48).setImageResource(R.drawable.s049);
        sList.get(49).setImageResource(R.drawable.s050);
        sList.get(50).setImageResource(R.drawable.s051);
        sList.get(51).setImageResource(R.drawable.s052);
        sList.get(52).setImageResource(R.drawable.s053);
        sList.get(53).setImageResource(R.drawable.s054);
        sList.get(54).setImageResource(R.drawable.s055);
        sList.get(55).setImageResource(R.drawable.s056);
        sList.get(56).setImageResource(R.drawable.s057);
        sList.get(57).setImageResource(R.drawable.s058);
        sList.get(58).setImageResource(R.drawable.s059);
        sList.get(59).setImageResource(R.drawable.s060);
        sList.get(60).setImageResource(R.drawable.s061);
        sList.get(61).setImageResource(R.drawable.s062);
        sList.get(62).setImageResource(R.drawable.s063);
        sList.get(63).setImageResource(R.drawable.s064);
        sList.get(64).setImageResource(R.drawable.s065);
        sList.get(65).setImageResource(R.drawable.s066);
        sList.get(66).setImageResource(R.drawable.s067);
        sList.get(67).setImageResource(R.drawable.s068);
        sList.get(68).setImageResource(R.drawable.s069);
        sList.get(69).setImageResource(R.drawable.s070);
        sList.get(70).setImageResource(R.drawable.s071);
        sList.get(71).setImageResource(R.drawable.s072);
        sList.get(72).setImageResource(R.drawable.s073);
        sList.get(73).setImageResource(R.drawable.s074);
        sList.get(74).setImageResource(R.drawable.s075);
        sList.get(75).setImageResource(R.drawable.s076);
        sList.get(76).setImageResource(R.drawable.s077);
        sList.get(77).setImageResource(R.drawable.s078);
        sList.get(78).setImageResource(R.drawable.s079);
        sList.get(79).setImageResource(R.drawable.s080);
        sList.get(80).setImageResource(R.drawable.s081);
        sList.get(81).setImageResource(R.drawable.s082);

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("signs.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



}

