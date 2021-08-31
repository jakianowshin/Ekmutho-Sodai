package com.itbl.ekmuthosodai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ItemListView extends AppCompatActivity {

    ItemAdapter itemAdapter;
    ListView listView;


    ArrayList<Items> itemLists=new ArrayList<>();
    ArrayList<LastItem> lastitemLists=new ArrayList<>();

    EditText inputSearch;
    String itemId,itemName,status="";
    String subcatId,parentId,itemCode,siteId="";
    Bundle bundle=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        listView = findViewById(R.id.table_itemlist);
        inputSearch = findViewById(R.id.search_tname);
        itemAdapter=new ItemAdapter(this,itemLists);



        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                itemAdapter.getFilter().filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override

            public void afterTextChanged(Editable arg0) {

            }
        });

        SharedPreferences Preferences = getSharedPreferences("pref", MODE_PRIVATE);
        parentId = Preferences.getString("parentId","");



           Display2 task = new Display2(ItemListView.this);
            task.execute();





    }


    public class Display2 extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public Display2(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(ItemListView.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params){
            String result = "";

            try{

                int count = 0;



                try {
                    String response = CustomHttpClientGet.execute(");
                    result = response.toString();


                }
                catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }
                itemLists.clear();

                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()){

                    JSONObject JO = jsonArray.getJSONObject(count);


                    itemId = JO.getString("item_ID");
                    itemName = JO.getString("item_DESCR");
                    status = JO.getString("status");



                    Items itemList = new Items(itemId,itemName,status);
                    itemLists.add(itemList);
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

            itemAdapter =new ItemAdapter(ItemListView.this,itemLists);
            listView.setAdapter(itemAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            status=itemLists.get(position).getStatus();

            if(status.equals("I")){
                parentId=itemLists.get(position).getItemid();

                Display3 task = new Display3(ItemListView.this);
                task.execute();


            }
            else{
                parentId=itemLists.get(position).getItemid();
                Display2 task = new Display2(ItemListView.this);
                task.execute();

            }
        }

            });

        }


        }

    public class Display3 extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public Display3(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(ItemListView.this, " Processing",
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


                }
                catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }


                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()){

                    JSONObject JO = jsonArray.getJSONObject(count);


                    itemCode = JO.getString("item_CODE");
                    siteId = JO.getString("site_ID");




                    LastItem item = new LastItem(itemCode,siteId);
                    lastitemLists.add(item);
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


            SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("itemCode",itemCode);
            editor.apply();
            Intent i = new Intent(ItemListView.this,ShopListView.class);
            startActivity(i);







    }}
    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onRestart() {
        super.onRestart();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }}


