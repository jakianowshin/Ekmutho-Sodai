package com.itbl.ekmuthosodai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class OrderActivity  extends AppCompatActivity {

    EditText address1,address2,email,phone,total_price;
    TextView name;
    CheckBox info;
    Button order;
    Spinner paymentTypes;
    int dlv_id;
    String orderId;
    int orderNum;
    String shopId,qty,shopName,itemName,itemPrice,shopTotal,maxLot,itemId,largeTotal,largeTotalId,add11,add21,email1,phone1,price1,lotNo,userId,condId,dlv_assgn,payMentType,loc_code;
      //add1,add2,email,phone,payment,orderNum,orderAmount,lotNo,condId,dlv_assgn,userId,loc_code;

    public  ArrayList<CartList> items=new ArrayList<>();
    public  ArrayList<ShopDetail> lists=new ArrayList<>();
    public  ArrayList<OrderProduct> orders=new ArrayList<>();
    public ArrayList<OrderProduct> orderIdList=new ArrayList<>();
    public ArrayList<OrderDetails> orderDetails=new ArrayList<>();
    EkmuthoSodaiDbAdapter dbAdapter=null;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        email = findViewById(R.id.email);

        address1 = findViewById(R.id.add1);
        address2 = findViewById(R.id.add2);
        phone = findViewById(R.id.phone);
        total_price = findViewById(R.id.total);
        paymentTypes = findViewById(R.id.payment_type);
        order = findViewById(R.id.btn_orderConfirm);
        SharedPreferences Preferences1= getSharedPreferences("pref", MODE_PRIVATE);
        loc_code = Preferences1.getString("road_id","");

        dbAdapter=new EkmuthoSodaiDbAdapter(this);

        SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("USER_ID", "");
        largeTotal= sharedPreferences.getString("largeTotal", "");
        largeTotalId = sharedPreferences.getString("largeTotalspId", "");



        SharedPreferences Preferences = getSharedPreferences("pref", MODE_PRIVATE);
        price1 = Preferences.getString("cartPrice","");

        if (Double.parseDouble(largeTotal)>=1000.0){
            condId="2";
        }
        else{
            condId="1";

        }

        final ArrayList<String> strPay_type=new ArrayList<>();
        strPay_type.add("Select payment Type");
        strPay_type.add("Credit Card");
        strPay_type.add("Bkash");
        strPay_type.add("Cash on delivery");


        dbAdapter.open();
        String[][] messageData=dbAdapter.getDataToRead();
        dbAdapter.close();

        for(int i=0;i<messageData[0].length;i++){
            CartList cart=new CartList(messageData[0][i],messageData[1][i],messageData[2][i],messageData[5][i],messageData[6][i],messageData[9][i],userId,messageData[11][i]);
            items.add(cart);


        }







            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,strPay_type);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            paymentTypes.setAdapter(aa);

            paymentTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    payMentType= strPay_type.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        total_price.setText(price1);





        order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                add11=address1.getText().toString().trim();
                add21=address2.getText().toString().trim();
                email1=email.getText().toString().trim();
                phone1=phone.getText().toString().trim();

                if ( add11.isEmpty() ||  email1.isEmpty()||  phone1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill The Empty Field", Toast.LENGTH_LONG).show();

                }
                else{

                    GetLotNumber task = new GetLotNumber(OrderActivity.this);
                  task.execute();

                }}
        });
    }



    public class GetLotNumber extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd = null;

        public GetLotNumber(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(OrderActivity.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            try{


            try {
                String response = CustomHttpClientGet.execute("");
                result = response.toString();


            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            JSONObject JO = new JSONObject(result);


            maxLot = JO.getString("maxLot");


        }catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            lotNo=String.valueOf(Integer.parseInt(maxLot)+1);


            OrderInfo task = new OrderInfo(OrderActivity.this);
                   task.execute();
        }
    }



    private class OrderInfo extends AsyncTask<Void,Void,String> {


        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public OrderInfo(Activity context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(OrderActivity.this, "Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "0" ;
            BufferedReader reader = null;
            StringBuilder stringBuilder ;

            try {
                URL url = new URL("http://192.168.1.118:8003/orderInfo");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("USER_ID", userId);
                jsonParam.put("LOC_CODE", loc_code);
                jsonParam.put("ADDRESS1", add11);
                jsonParam.put("ADDRESS2", add21);
                jsonParam.put("EMAIL", email1);
                jsonParam.put("PHONE", phone1);
                jsonParam.put("PAYMENT_TYPE", payMentType);
//

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
            } catch (Exception e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            pd.dismiss();

            dlv_id=Integer.parseInt(result);
            if(dlv_id>0){


                for(int i =0;i<items.size();i++){
                int c=0;
                    for(int j=0;j<items.size();j++){
                        if(items.get(i).getShopId().equals(items.get(j).getShopId()) && i!=j){
                            c++;
                        }
                    }

                    if(c==0){
                        shopTotal=items.get(i).getShopTotal();
                        shopId=items.get(i).getShopId();
                        if(items.get(i).getShopId().equals(largeTotalId)){
                            dlv_assgn="L";


                        }
                        else{
                            dlv_assgn="P";

                        }
                        OrderProduct op = new OrderProduct(shopTotal,lotNo,condId,String.valueOf(dlv_id)
                                ,dlv_assgn,userId,shopId);
                        orders.add(op);


                    }
                }

                for(int i =0;i<items.size();i++){
                    int c=0;
                    for(int j=0;j<items.size();j++){
                        if(items.get(i).getShopId().equals(items.get(j).getShopId()) && i!=j){
                            c++;
                        }
                    }
                            if(c==1){
                                shopTotal=items.get(i).getShopTotal();
                                shopId=items.get(i).getShopId();
                                if(items.get(i).getShopId().equals(largeTotalId)){
                                    dlv_assgn="L";

                                }
                                else{
                                    dlv_assgn="P";

                                }
                                OrderProduct op = new OrderProduct(shopTotal,lotNo,condId,String.valueOf(dlv_id)
                                        ,dlv_assgn,userId,shopId);
                                orders.add(op);


                                break;

                            }



                }

                Order task = new Order(OrderActivity.this);
                task.execute();

                dialog("","Order successful!!!");
                startActivity(new Intent(OrderActivity.this,CategoryView.class));
            }
            else{

                dialog1("Failed! try again");

            }

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
            pd = ProgressDialog.show(OrderActivity.this, "Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "0" ;
            BufferedReader reader = null;
            StringBuilder stringBuilder ;
            int count = 0;
            try {
                for(int i=0;i<orders.size();i++){
                    URL url = new URL("");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);


                    String z =String.valueOf(dlv_id);
                    String a= orders.get(i).getOrderAmount();
                    String bbb = orders.get(i).getShopId();
                    String e = orders.get(i).getCondId();
                    String f = orders.get(i).getDlv_assgn();
                    String g = orders.get(i).getUserId();
                    orderNum=rand.nextInt((9999 - 100) + 1) + 10;
                    String c=String.valueOf(orderNum);


                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("ORDER_NUM", c);
                    jsonParam.put("ORDER_AMT", a);
                    jsonParam.put("LOT_NO", lotNo);
                    jsonParam.put("COND_ID", e);
                    jsonParam.put("DLV_USER_ID",z);
                    jsonParam.put("DLV_ASSIGN",f);
                    jsonParam.put("USER_ID",g);





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
                    OrderProduct order = new OrderProduct(result,bbb);
                    orderIdList.add(order);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {

            pd.dismiss();

            for(int j =0;j<items.size();j++){
                itemName = items.get(j).getItemName();
                itemPrice=items.get(j).getPrice();
                shopTotal=items.get(j).getShopTotal();
                qty=items.get(j).getQuantity();
                shopId=items.get(j).getShopId();
                shopName=items.get(j).getShop();
                itemId=items.get(j).getItemId();
                for(int i =0;i<orderIdList.size();i++){
                    if (items.get(j).getShopId().equals(orderIdList.get(i).getShopId())) {
                        orderId=orderIdList.get(i).getOrderId();



                        OrderDetails aa = new OrderDetails(orderId,itemName,itemPrice,shopId,qty,userId,shopTotal,shopName,itemId);
                        orderDetails.add(aa);


                }

                    }

            }

            SharedPreferences sharedPreferences1 = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            Gson gson = new Gson();
            String json = gson.toJson(orderDetails);
            editor1.putString("orderDetails", json);
            editor1.apply();
            Intent intent = new Intent(OrderActivity.this,OrderDetailView.class);
            startActivity(intent);

            }

        }
        }


    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }







    public void dialog(String message,String title){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle(title);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void dialog1(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
