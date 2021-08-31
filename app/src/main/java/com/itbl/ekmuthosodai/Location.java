package com.itbl.ekmuthosodai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Location extends AppCompatActivity {
    Spinner City,Area,Loc,Road;
    Button Next;
    String str_city_name,str_city_id,str_area_name,str_area_id,str_location_name,str_location_id,
            str_road_name,str_road_id;
    private ArrayList<LocationModel> locations=new ArrayList<>();





    ArrayList<String> city_name=new ArrayList<>();
    ArrayList<String> city_id=new ArrayList<>();

    ArrayList<String> area_name=new ArrayList<>();
    ArrayList<String> area_id=new ArrayList<>();

    ArrayList<String> location_name=new ArrayList<>();
    ArrayList<String> location_id=new ArrayList<>();

    ArrayList<String> road_name=new ArrayList<>();
    ArrayList<String> road_id=new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        City = findViewById(R.id.city);
        Area = findViewById(R.id.area);
        Loc = findViewById(R.id.location);
        Road = findViewById(R.id.road);
        Next = findViewById(R.id.gotoNext);

        city_name.add("Select your City");
        city_id.add("0");

        EkmuthoSodaiDbAdapter dbAdapter=new EkmuthoSodaiDbAdapter(this);
        dbAdapter.open();
        dbAdapter.close();





        CityList task1 = new CityList(Location.this);
        task1.execute();

//        Next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Location.this,CategoryView.class);
//                startActivity(i);
//            }
//        });






    }




    public  class CityList extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public CityList(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(Location.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params){



            String result = "";



            try{

                int count = 0;




                try {
                    String response = CustomHttpClientGet.execute("");
                    result = response.toString();


                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }



                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()){

                    JSONObject JO = jsonArray.getJSONObject(count);


                    str_city_id = JO.getString("loc_ID");
                    str_city_name = JO.getString("loc_DESCR");


                    city_id.add(str_city_id);
                    city_name.add(str_city_name);
                    count++;
                }
            }

            catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return result;
        }




        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            ArrayAdapter aa = new ArrayAdapter(Location.this, R.layout.spinner_text_layout,city_name);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            City.setAdapter(aa);

            City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    str_city_id = city_id.get(position);

                    if ((str_city_id.equals("0"))) {
                        Toast.makeText(Location.this,"Please select your City",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        AreaList task2 = new AreaList(Location.this);

                        task2.execute();


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            }
            );
        }


    }





        public class AreaList extends AsyncTask<Void, Void, String> {
            @SuppressWarnings("unused")
            private Activity context;

            @SuppressWarnings("unused")
            ProgressDialog pd=null;

            public AreaList(Activity context){
                this.context = context;
            }

            @Override
            protected void onPreExecute(){
                pd = ProgressDialog.show(Location.this, " Processing",
                        "Please wait...");
            }

            @Override
            protected String doInBackground(Void... params) {


                String result = "";


                try {

                    int count = 0;


                    try {
                        String response = CustomHttpClientGet.execute("");
                        result = response.toString();


                    } catch (Exception e) {
                        Log.e("log_tag", "Error in http connection!!" + e.toString());
                    }
                    area_id.clear();
                    area_name.clear();
                    area_name.add("Select your Area");
                    area_id.add("0");



                    JSONArray jsonArray = new JSONArray(result.toString());

                    while (count < jsonArray.length()) {

                        JSONObject JO = jsonArray.getJSONObject(count);


                        str_area_id = JO.getString("loc_ID");
                        str_area_name = JO.getString("loc_DESCR");

                        area_id.add(str_area_id);
                        area_name.add(str_area_name);

                        count++;
                    }
                }
                catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }

                return result;

            }
            @Override
            protected void onPostExecute(String result) {
                pd.dismiss();



                ArrayAdapter aa1 = new ArrayAdapter(Location.this, R.layout.spinner_text_layout,area_name);
                aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Area.setAdapter(aa1);

                Area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_area_id= area_id.get(position);

                        if ((str_area_id.equals("0"))) {
                            Toast.makeText(Location.this,"Please select your Area",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            LocationList task3 = new LocationList(Location.this);

                            task3.execute();


                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }



            }



    public class LocationList extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public LocationList(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(Location.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            String result = "";


            try {

                int count = 0;


                try {
                    String response = CustomHttpClientGet.execute("");
                    result = response.toString();


                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }
                location_id.clear();
                location_name.clear();
                location_name.add("Select your Location");
                location_id.add("0");



                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()) {

                    JSONObject JO = jsonArray.getJSONObject(count);


                    str_location_id = JO.getString("loc_ID");
                    str_location_name = JO.getString("loc_DESCR");

                    location_id.add(str_location_id);
                    location_name.add(str_location_name);

                    count++;
                }
            }
            catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();



            ArrayAdapter aa1 = new ArrayAdapter(Location.this, R.layout.spinner_text_layout,location_name);
            aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Loc.setAdapter(aa1);

            Loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    str_location_id= location_id.get(position);
                    if ((str_location_id.equals("0"))) {
                        Toast.makeText(Location.this,"Please select your Location",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        RoadList task3 = new RoadList(Location.this);

                        task3.execute();


                    }



                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }



    }


    public class RoadList extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public RoadList(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(Location.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            String result = "";


            try {

                int count = 0;


                try {
                    String response = CustomHttpClientGet.execute("");
                    result = response.toString();


                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }
                road_id.clear();
                road_name.clear();
                road_name.add("Select your Address");
                road_id.add("0");



                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()) {

                    JSONObject JO = jsonArray.getJSONObject(count);


                    str_road_id = JO.getString("loc_ID");
                    str_road_name = JO.getString("loc_DESCR");

                    road_id.add(str_road_id);
                    road_name.add(str_road_name);

                    count++;
                }
            }
            catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();



            ArrayAdapter aa1 = new ArrayAdapter(Location.this, R.layout.spinner_text_layout,road_name);
            aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Road.setAdapter(aa1);

            Road.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    str_road_id = road_id.get(position);
                    if ((str_road_id.equals("0"))) {
                        Toast.makeText(Location.this, "Please select your address", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("road_id",str_road_id);
            editor.apply();





            Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Location.this,CategoryView.class);
                    startActivity(i);
                }
            });




        }




    }
}











