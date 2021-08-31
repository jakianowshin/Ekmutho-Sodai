package com.itbl.ekmuthosodai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OrderDetailView extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    OrderDetailAdapter orderDetailAdapter;
    ListView listView;
    EkmuthoSodaiDbAdapter dbAdapter=null;

    ArrayList<OrderDetails> orderDetailModels=new ArrayList<>();
    ArrayList<OrderDetails>orderDetails =new ArrayList<>();
    String shopName,total,orderId,productId,productName,proQty,price;

    TextView total1,deliveryCharge1;
    Button goToCategory;
    String userId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_order_detail);
        listView =  findViewById(R.id.table_order_detaillist);

        total1 = (TextView)findViewById(R.id.grand_total) ;

        deliveryCharge1 = (TextView)findViewById(R.id.delivery_Charge) ;
        goToCategory = (Button) findViewById(R.id.gotoCategory) ;
        dbAdapter=new EkmuthoSodaiDbAdapter(this);



        SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("orderDetails", null);
        Type type = new TypeToken<ArrayList<OrderDetails>>() {}.getType();
        orderDetails = gson.fromJson(json, type);
        if (orderDetails == null) {
            orderDetails = new ArrayList<>();
        }



        SharedPreferences sharedPreferences1 = getSharedPreferences("pref", Context.MODE_PRIVATE);
        total = sharedPreferences1.getString("cartPrice", "");



        SharedPreferences Preferences = getSharedPreferences("pref", MODE_PRIVATE);
        userId = Preferences.getString("USER_ID","");


      for(int i =0;i<orderDetails.size();i++){
          productId=orderDetails.get(i).getItemId();
          price=orderDetails.get(i).getItemPrice();
          productName=orderDetails.get(i).getItemName();
          proQty=orderDetails.get(i).getQty();
          orderId=orderDetails.get(i).getOrderId();
          shopName=orderDetails.get(i).getShopName();

          OrderDetails od = new OrderDetails(shopName,price,proQty,productName);
          orderDetailModels.add(od);



      }

        orderDetailAdapter =new OrderDetailAdapter(OrderDetailView.this,orderDetailModels);
        listView.setAdapter(orderDetailAdapter);
        total1.setText(total);

        Order task = new Order(OrderDetailView.this);
        task.execute();

        goToCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderDetailView.this,CategoryView.class);
                startActivity(i);
                dbAdapter.open();
                dbAdapter.removeAll();
                dbAdapter.close();
            }
        });
    }


    private class Order extends AsyncTask<Void,Void,String> {


        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public Order(Activity context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(OrderDetailView.this, "Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "0" ;
            BufferedReader reader = null;
            StringBuilder stringBuilder ;
            int count = 0;
            try {
                for(int i=0;i<orderDetails.size();i++){
                    URL url = new URL("");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);


                    String z =orderDetails.get(i).getOrderId();
                    String a= orderDetails.get(i).getItemId();
                    String b = orderDetails.get(i).getQty();
                    String c = orderDetails.get(i).getItemPrice();
                    String d= orderDetails.get(i).getShopId();



                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("ORDER_ID", z );
                    jsonParam.put("ITEM_ID", a);
                    //jsonParam.put("PRODUCT_ID", arproductId.toString());
                    jsonParam.put("QNTY", b);
                    //jsonParam.put("PRODUCT_QTY", arproQty.toString());
                    jsonParam.put("RATE",c);
                    jsonParam.put("DB_POINT_CODE",d);
                    //jsonParam.put("PRODUCT_PRICE",arproPrice.toString());




                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    stringBuilder = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        stringBuilder.append(line + "\n");
                    }
                    result=stringBuilder.toString();
                    result=result.replaceAll("\n","");
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result;
        }


        @Override
        protected void onPostExecute(String result) {

            pd.dismiss();


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

    }
}
