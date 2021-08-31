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

public class ShopListView  extends AppCompatActivity {

    ShopAdapter shopAdapter;
    ListView listView;

    ArrayList<ShopList> shopLists=new ArrayList<>();

    EditText inputSearch;
    String itemName,itemCode,location= "";
    String shopName,shopId,itemId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_shop);
        listView = findViewById(R.id.table_shoplist);
        inputSearch = findViewById(R.id.search_tname);
        shopAdapter=new ShopAdapter(this,shopLists);

        SharedPreferences Preferences2 = getSharedPreferences("pref", MODE_PRIVATE);
        itemCode = Preferences2.getString("itemCode","");


        SharedPreferences Preferences1= getSharedPreferences("pref", MODE_PRIVATE);
        location = Preferences1.getString("road_id","");




        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                shopAdapter.getFilter().filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override

            public void afterTextChanged(Editable arg0) {

            }
        });

        Display task = new Display(ShopListView.this);
        task.execute();


    }

    public class Display extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public Display(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(ShopListView.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params){
            String result = "";

            try{

                int count = 0;



                try {
                    String response = CustomHttpClientGet.execute("http://192.168.1.118:8003/shops/"+itemCode+"/"+location);
                    result = response.toString();


                }
                catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }

                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()){

                    JSONObject JO = jsonArray.getJSONObject(count);


                    itemName = JO.getString("item_DESCR");
                    shopName = JO.getString("db_POINT_DESCR");
                    shopId = JO.getString("db_POINT_CODE");
                    itemId = JO.getString("item_ID");


                    ShopList shop = new ShopList(itemId,itemName,shopName,shopId);
                    shopLists.add(shop);
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

            shopAdapter =new ShopAdapter(ShopListView.this,shopLists);
            listView.setAdapter(shopAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    itemId = shopLists.get(position).getITEM_ID();
                    SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("itemId",itemId);
                    editor.apply();


                    shopId = shopLists.get(position).getDB_POINT_CODE();
                    SharedPreferences sharedPreferences1 = getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    editor1.putString("shopId",shopId);
                    editor1.apply();

                    Intent intent = new Intent(ShopListView.this, SingleProduct.class);
                    startActivity(intent);


                }
            });







        }

    }
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

    }


}
