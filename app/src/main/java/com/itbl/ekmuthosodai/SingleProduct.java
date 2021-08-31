package com.itbl.ekmuthosodai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SingleProduct extends AppCompatActivity {

     String total1,AMT_RATE,AMT_QTY,ITEM_DESCR,QTY,dis_price1,qty1,DB_POINT_DESCR="";


    public Toolbar toolbar;

    String cartItemId="";
    String setQuantity,userId="";
    Bundle bundle = null;
    EkmuthoSodaiDbAdapter dbAdapter=null;

    TextView pName,shopName,price,disPrice,stock,quantity,perPrice;
    Button addCart;
    ImageButton addbutton,minusbutton;
    ImageView image;
    Bitmap myBitmap;
    Double shopTotal=0.0;
    String setnewPrice,itemId,shopId,sptotal= " ";
    public static TextView tv;
    int q=0;


    private static ArrayList<Items> itemLists;
    private static ArrayList<Items>items;
    private static ArrayList<CartList>cartitems;
    private static ArrayList<CartList>cartitem1;
    Items item = new Items();
//



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product);
        dbAdapter=new EkmuthoSodaiDbAdapter(this);

        SharedPreferences Preferences1 = getSharedPreferences("pref", MODE_PRIVATE);
        userId = Preferences1.getString("USER_ID","");


        SharedPreferences Preferences = getSharedPreferences("pref", MODE_PRIVATE);
        itemId = Preferences.getString("itemId","");


        SharedPreferences Preferences2 = getSharedPreferences("pref", MODE_PRIVATE);
        shopId = Preferences2.getString("shopId","");


        SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", null);
        Type type = new TypeToken<ArrayList<Items>>() {}.getType();
        items = gson.fromJson(json, type);
        if (items == null) {
            items = new ArrayList<>();
        }




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setTitle(" Product Details");

        if (itemLists == null) {

            itemLists = new ArrayList<>();
        }






        pName = (TextView) findViewById(R.id.pName);
        shopName = (TextView) findViewById(R.id.shop);
        price = (TextView)findViewById(R.id.totaltaka);
        disPrice = (TextView)findViewById(R.id.dis_price);
        stock = (TextView)findViewById(R.id.stock);
        addCart = (Button) findViewById(R.id.addCart);
        quantity = (TextView) findViewById(R.id.quantity);
        addbutton = (ImageButton) findViewById(R.id.plus);
        minusbutton = (ImageButton) findViewById(R.id.subquantity);
        perPrice = (TextView) findViewById(R.id.perPrice);


        SharedPreferences shared1 = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = shared1.getString("items", null);
        Type type2 = new TypeToken<ArrayList<CartList>>() {}.getType();
        cartitem1 = gson2.fromJson(json2, type2);
        if (cartitem1 == null) {
            cartitem1 = new ArrayList<>();
        }
        for(int i=0;i<cartitem1.size();i++){
            if(cartitem1.get(i).getItemId().equals(itemId)){
                q=Integer.parseInt(cartitem1.get(i).getQuantity());
                quantity.setText(String.valueOf(q));


            }}


        Display task = new Display(SingleProduct.this);
            task.execute();

            addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(Double.parseDouble(dis_price1)>0){
                        double baseprice1 = Double.parseDouble(dis_price1);

                        q++;
                        quantity.setText(String.valueOf(q));
                        setQuantity=String.valueOf(q);
                        double bookPrice = baseprice1 * q;
                        setnewPrice = String.valueOf(bookPrice);
                        disPrice.setText(" Tk. "+setnewPrice);
                    }



                    else{
                        int baseprice = Integer.parseInt(total1);
                        q++;
                        quantity.setText(String.valueOf(q));
                        setQuantity=String.valueOf(q);
                        int bookPrice = baseprice * q;
                        setnewPrice = String.valueOf(bookPrice);
                        price.setText(" Tk. "+ setnewPrice);}

                }
            });


            minusbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (q == 0) {
                        Toast.makeText(SingleProduct.this, "Cant decrease quantity < 0", Toast.LENGTH_SHORT).show();
                    } else {
                        if(Double.parseDouble(dis_price1)>0){
                            double baseprice1 = Double.parseDouble(dis_price1);
                            q--;
                            setQuantity=String.valueOf(q);
                            quantity.setText(String.valueOf(q));
                            double bookPrice = baseprice1 * q;
                            setnewPrice = String.valueOf(bookPrice);
                            disPrice.setText("Tk. "+setnewPrice);

                        }
                        else{
                            int basePrice = Integer.parseInt(total1);
                            q--;
                            setQuantity=String.valueOf(q);
                            quantity.setText(String.valueOf(q));
                            int bookPrice = basePrice * q;
                            setnewPrice = String.valueOf(bookPrice);
                            price.setText("Tk. "+setnewPrice);
                        }}
                }
            });

            SharedPreferences sharedPreferences1 = getSharedPreferences("total", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("total",setnewPrice);
            editor.apply();

//
            addCart.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    if(q>0 ){
                        SaveCart();
                        SharedPreferences shared = getSharedPreferences("pref", MODE_PRIVATE);
                        Gson gson1 = new Gson();
                        String json1 = shared.getString("items", null);
                        Type type1 = new TypeToken<ArrayList<CartList>>() {}.getType();
                        cartitems = gson1.fromJson(json1, type1);
                        if (cartitems == null) {
                            cartitems = new ArrayList<>();
                        }





                        for(int i=0;i<cartitems.size();i++){
                            if(cartitems.get(i).getItemId().equals(itemId)){
                                updateCart(cartitems.get(i).getItemId(),setQuantity,setnewPrice);

                            }
                        }
                        Intent intent = new Intent(SingleProduct.this,CartActivity.class);
                        startActivity(intent);

//
                   }
//
                    else{
                        Toast.makeText(SingleProduct.this, "Please enter the item quantity", Toast.LENGTH_SHORT).show();
                    }
//
               }
         });
        }


    protected void OnResume(){
        super.onResume();
    }


    private void SaveCart(){

        try {
            dbAdapter.open();
            dbAdapter.insertItem(itemId,ITEM_DESCR,setnewPrice,total1,dis_price1,DB_POINT_DESCR,shopId," ","",setQuantity,userId,"");
            dbAdapter.close();
            
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void updateCart(String productId,String setQuantity,String setnewPrice){

        try {
            dbAdapter.open();
            dbAdapter.updateAll(productId,setQuantity,setnewPrice);
            dbAdapter.close();


        }catch (Exception e){
            e.printStackTrace();
        }

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
            pd = ProgressDialog.show(SingleProduct.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {


            String result = "";

            try {


                try {
                    String response = CustomHttpClientGet.execute("http://192.168.1.118:8003/productDetail/" + itemId);
                    result = response.toString();
                    //result=result.replaceAll("[^a-zA-Z0-9]+","");

                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }

//                JSONArray jsonArray = new JSONArray(result.toString());
//
//                while (count < jsonArray.length()){

                    JSONObject JO = new JSONObject(result);



                    total1 = JO.getString("amt");
                    dis_price1 = JO.getString("disc_AMOUNT");
                    qty1 = JO.getString("stock_QTY");
                    ITEM_DESCR = JO.getString("item_DESCR");
                    DB_POINT_DESCR = JO.getString("db_POINT_DESCR");
                    AMT_RATE = JO.getString("amt_RATE");
                    AMT_QTY = JO.getString("amt_QTY");
                    QTY = JO.getString("qty");






                    pName.setText(ITEM_DESCR);
                    shopName.setText(DB_POINT_DESCR);
                    perPrice.setText(AMT_RATE+AMT_QTY+"/"+QTY);

                    price.setText("Tk. "+total1);
                    disPrice.setText("Tk. "+dis_price1 );
                    stock.setText(qty1);


//
//                book.setPrice(dis_price1);
//                book.setProductName(bName1);

            }catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(userId!=""){
            inflater.inflate(R.menu.menu_for_all, menu);}
        else{

            inflater.inflate(R.menu.menu_confirmation, menu);}

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cat_butt){
            Intent intent = new Intent(SingleProduct.this, CategoryView.class);
            startActivity(intent);
        }
        if (id == R.id.cart_btn){
            Intent intent = new Intent(SingleProduct.this, CartActivity.class);
            startActivity(intent);
        }
        if (id == R.id.login_btn1){
            Intent intent = new Intent(SingleProduct.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.logout_btttn){
            Intent intent = new Intent(SingleProduct.this, Login.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    public  void cartUpdate() {
        if (tv != null && items != null)
            tv.setText(Integer.toString(items.size()));
    }
    @Override
    protected void onStart() {
        super.onStart();

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
