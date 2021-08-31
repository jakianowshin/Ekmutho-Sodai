package com.itbl.ekmuthosodai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerViewCart;
    public static TextView tv;
    static TextView cartPrice,delivery;
    Button checkout;
    static double TotalwithDel = 0.0;
    double sptotal =0.0;
    String itemId,userId,qty,shopName,shopId,shopTotal,largeTotal,largeTotalspId= "";
    static  String Total="";

    SharedPreferences sharedPreferences;

    EkmuthoSodaiDbAdapter dbAdapter=null;

    Spinner delDetail;


    public  ArrayList<CartList> items=new ArrayList<>();
    public  ArrayList<ShopDetail> lists=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cart);
        dbAdapter=new EkmuthoSodaiDbAdapter(this);









        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Your Cart");


        mToolbar.setTitleTextColor(0xFFFFFFFF);



//        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent i = new Intent(CartActivity.this,CategoryListView.class);
//               startActivity(i);
//            }
//        });


        if (items == null) {

            items = new ArrayList<>();
        }

        cartPrice = findViewById(R.id.cart_price);
        delivery = findViewById(R.id.delCharge);
        checkout = findViewById(R.id.checkOut);
        recyclerViewCart = findViewById(R.id.cart_recycleView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(linearLayoutManager);
        recyclerViewCart.setNestedScrollingEnabled(false);
        recyclerViewCart.setAdapter(new CartAdapter(getApplicationContext(),items));

        SharedPreferences Preferences = getSharedPreferences("pref", MODE_PRIVATE);
        userId = Preferences.getString("USER_ID","");



        dbAdapter.open();
        String[][] messageData=dbAdapter.getDataToRead();
        dbAdapter.close();

        for(int i=0;i<messageData[0].length;i++){
            CartList cart=new CartList(messageData[0][i],messageData[1][i],messageData[2][i],messageData[5][i],messageData[6][i],messageData[9][i],userId,messageData[11][i]);
            items.add(cart);
            shopName = messageData[5][i];
            shopId=messageData[6][i];
            itemId =messageData[0][i];
            qty=messageData[8][i];
            shopTotal=messageData[11][i];

        }

        CartAdapter cartAdapter=new CartAdapter( CartActivity.this,items);
        recyclerViewCart.setAdapter(cartAdapter);

        for(int j=0;j<items.size();j++){
            if(shopId.equals(items.get(j).getShopId())){
                sptotal=sptotal+Double.parseDouble(items.get(j).getPrice());
                shopTotal=String.valueOf(sptotal);
                ShopTotalUpdate(shopId,shopTotal);

            }}
//

//
        dbAdapter.open();
        String[][] messageData1=dbAdapter.getDataToRead();
        dbAdapter.close();
        for(int i=0;i<messageData[0].length;i++) {
            ShopDetail spDetail = new ShopDetail(messageData1[6][i], messageData1[11][i]);
            lists.add(spDetail);}
            largeTotal = largeTotalShop(lists);

        for(int i =0;i<lists.size();i++){
            if(lists.get(i).getShopTotal().equals(largeTotal)){
                largeTotalspId=lists.get(i).getShopId();
            }
        }

        SharedPreferences sharedPreferences22 = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor22 = sharedPreferences22.edit();
        editor22.putString("largeTotalspId", largeTotalspId);
        editor22.apply();

        SharedPreferences sharedPreferences20 = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor20 = sharedPreferences22.edit();
        editor20.putString("largeTotal", largeTotal);
        editor20.apply();








        SharedPreferences sharedPreferences1 = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            Gson gson = new Gson();
            String json = gson.toJson(items);
            editor1.putString("items", json);
            editor1.apply();


            TotalwithDel = grandTotal(items);
            if(TotalwithDel>999.0){

                delivery.setText("        0Tk.");
                cartPrice.setText(Double.toString(TotalwithDel) + " Tk.");
                Total = String.valueOf(TotalwithDel);
}
            else{

                delivery.setText("      50Tk.");
                TotalwithDel=TotalwithDel+50.0;

                cartPrice.setText(Double.toString(TotalwithDel) + " Tk.");
                Total = String.valueOf(TotalwithDel);

            }


            SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cartPrice", Total);
            editor.apply();



//
//



        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userId != ""  && items.size()!=0){
                    Intent i = new Intent(CartActivity.this,OrderActivity.class);
                    startActivity(i);

                }
                else {

                    if (items.size()==0){
                        Toast.makeText(CartActivity.this,"Please item book to checkout",Toast.LENGTH_SHORT).show();
                    }

                    else if (userId==""){
                        Toast.makeText(CartActivity.this,"Please Login to checkout",Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(CartActivity.this,"Sorry! try again properly!",Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });



    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        if (userId != "") {
            getMenuInflater().inflate(R.menu.menu_after_login, menu);
        }


        else{
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }


        MenuItem item = menu.findItem(R.id.action_addcart);
        MenuItemCompat.setActionView(item, R.layout.cart_count_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        View view = notifCount.findViewById(R.id.hotlist_bell);

        tv = notifCount.findViewById(R.id.hotlist_hot);
        cartUpdate();
        if (tv != null && items != null)
            tv.setText(Integer.toString(items.size()));

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.Login_button){
            Intent intent = new Intent(CartActivity.this, Login.class);
            startActivity(intent);
        }

        if (id == R.id.category_button){
            Intent intent = new Intent(CartActivity.this, CategoryView.class);
            startActivity(intent);
        }

        if (id == R.id.logout){
            SharedPreferences preferences = getSharedPreferences("pref", 0);
            preferences.edit().remove("USER_ID").commit();
            Intent intent = new Intent(CartActivity.this, Login.class);
            startActivity(intent);
            EkmuthoSodaiDbAdapter dbAdapter=new EkmuthoSodaiDbAdapter(this);
            dbAdapter.open();
            dbAdapter.removeAll();
            dbAdapter.close();

        }

        if (id == R.id.cat_butt){
            Intent intent = new Intent(CartActivity.this, CategoryView.class);
            startActivity(intent);
        }




        return super.onOptionsItemSelected(item);
    }



    public  void cartUpdate() {
        if (tv != null && items != null)
            tv.setText(Integer.toString(items.size()));
    }

    public  void ShopTotalUpdate(String shopId,String Shoptotal) {
       dbAdapter.open();
       dbAdapter.updateShop(shopId,Shoptotal);
       dbAdapter.close();
    }

    public  void priceAdjust(){
        cartPrice.setText(Double.toString(grandTotal(items)));
    }

    public static double grandTotal(ArrayList<CartList> books){

        double totalPrice = 0.0;
        for(int i = 0 ; i < books.size(); i++) {
            totalPrice =totalPrice+Double.parseDouble(books.get(i).getPrice());
        }
        return totalPrice;
    }

    public static String largeTotalShop(ArrayList<ShopDetail>lists){
        double a,b,temp;

        for(int j = 0;j<lists.size();j++){
                for(int k =1;k<lists.size();k++){
                    a=Double.parseDouble(lists.get(j).getShopTotal());
                    b=Double.parseDouble(lists.get(k).getShopTotal());

                    if(a>b){
                        temp=a;
                        a=b;
                        b=temp;


                    }
                }

            }
        return lists.get(lists.size()-1).getShopTotal();

        }


    }





