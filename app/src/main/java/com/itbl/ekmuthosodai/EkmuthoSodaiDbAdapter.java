package com.itbl.ekmuthosodai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;

public class EkmuthoSodaiDbAdapter {

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mCtx;
    public static int count=0;

    public static String dir="";

    public EkmuthoSodaiDbAdapter(Context mCtx) {
        this.mCtx = mCtx;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            /*File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+File.separator+"/ekmutho" + "/" + "BOI");
            if(!filePath.exists())
                filePath.mkdirs();
            dir=filePath.getAbsolutePath();*/
            dir="SODAI";
        } else{
            dir= Environment.getExternalStorageDirectory().toString() + "/ekmutho" + "/" + "SODAI";
        }
    }


    private static class DatabaseHelper
            extends SQLiteOpenHelper
    {


        public DatabaseHelper(Context context)
        {
            super(context, dir, null, 1);
        }

        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("create table order_product ( Item_id text primary key,Item_name text, Item_price text ,Item_Singleprice text," +
                    "Item_dis_price text,Shop_name text,Shop_id text,Cond_id text, Lot_num text, Item_quantity text,user_id text,Shop_total text );");


            db.execSQL("create table order_information ( Order_id text primary key,Item_name text, Item_price text ,Shop_id text, Item_quantity text,user_id text,Shop_total text );");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS order_product");
            db.execSQL("DROP TABLE IF EXISTS order_information");

            onCreate(db);
        }
    }

    public EkmuthoSodaiDbAdapter open()
            throws SQLException
    {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        this.mDbHelper.close();
    }





    public String[][] getDataToRead(){
        try
        {
            String getdata = "SELECT * FROM order_product";
            Cursor read = this.mDb.rawQuery(getdata, null);

            long p = read.getCount();
            int a = (int)p;
            String[][] result = new String[12][a];
            int i = 0;

            int a1 = read.getColumnIndex("Item_id");
            int a2 = read.getColumnIndex("Item_name");
            int a3 = read.getColumnIndex("Item_price");
            int a4 = read.getColumnIndex("Item_Singleprice");
            int a5 = read.getColumnIndex("Item_dis_price");
            int a6 = read.getColumnIndex("Shop_name");
            int a7 = read.getColumnIndex("Shop_id");
            int a8 = read.getColumnIndex("Cond_id");
            int a9= read.getColumnIndex("Lot_num");
            int a10= read.getColumnIndex("Item_quantity");
            int a11 = read.getColumnIndex("user_id");
            int a12 = read.getColumnIndex("Shop_total");


            for (read.moveToFirst(); !read.isAfterLast(); read.moveToNext())
            {
                result[0][i] = read.getString(a1);
                result[1][i] = read.getString(a2);
                result[2][i] = read.getString(a3);
                result[3][i] = read.getString(a4);
                result[4][i] = read.getString(a5);
                result[5][i] = read.getString(a6);
                result[6][i] = read.getString(a7);
                result[7][i] = read.getString(a8);
                result[8][i] = read.getString(a9);
                result[9][i] = read.getString(a10);
                result[10][i] = read.getString(a11);
                result[11][i] = read.getString(a12);

                i++;
            }
            return result;
        }
        catch (Exception e) {}
        return null;
    }


    public String[][] getOrderInfoToRead(){
        try
        {
            String getdata = "SELECT * FROM order_information";
            Cursor read = this.mDb.rawQuery(getdata, null);

            long p = read.getCount();
            int a = (int)p;
            String[][] result = new String[7][a];
            int i = 0;

            int a1 = read.getColumnIndex("Order_id");
            int a2 = read.getColumnIndex("Item_name");
            int a3 = read.getColumnIndex("Item_price");
            int a4 = read.getColumnIndex("Shop_id");
            int a5= read.getColumnIndex("Item_quantity");
            int a6 = read.getColumnIndex("user_id");
            int a7 = read.getColumnIndex("Shop_total");


            for (read.moveToFirst(); !read.isAfterLast(); read.moveToNext())
            {
                result[0][i] = read.getString(a1);
                result[1][i] = read.getString(a2);
                result[2][i] = read.getString(a3);
                result[3][i] = read.getString(a4);
                result[4][i] = read.getString(a5);
                result[5][i] = read.getString(a6);
                result[6][i] = read.getString(a7);


                i++;
            }
            return result;
        }
        catch (Exception e) {}
        return null;
    }


    public void deleteFromDB(String itemId)
    {
        this.mDb.execSQL("delete from order_product where Item_id="+itemId+";");
    }

    public void updateAll(String itemId,String Quantity,String price)
    {
        this.mDb.execSQL("update order_product set Item_quantity="+Quantity+", Item_price="+price+" where Item_id="+itemId+";");
    }

    public void updateShop(String shopId,String shopTotal)
    {
        this.mDb.execSQL("update order_product set Shop_total="+shopTotal+" where Shop_id="+shopId+";");
    }

    public void removeAll()
    {
        this.mDb.execSQL("delete from order_product;");
    }



    public long insertOrderDetail( String Order_id,String itemName, String price,
            String shopId,String Quantity,String userId,String shopTotal )
    {
        ContentValues cv = new ContentValues();
        cv.put("Item_id", Order_id);
        cv.put("Item_name", itemName);
        cv.put("Item_price", price);
        cv.put("Shop_id", shopId);
        cv.put("Item_quantity", Quantity);
        cv.put("user_id", userId);
        cv.put("user_id", shopTotal);


        return this.mDb.insert("order_information", null, cv);


    }

    public long insertItem( String itemId,String itemName, String price,String SinglePrice,String DisPrice,
                            String shop ,String shopId,String condId,String lot,String Quantity,String userId,String shopTotal )
    {
        ContentValues cv = new ContentValues();
        cv.put("Item_id", itemId);
        cv.put("Item_name", itemName);
        cv.put("Item_price", price);
        cv.put("Item_Singleprice", SinglePrice);
        cv.put("Item_dis_price", DisPrice);
        cv.put("Shop_name", shop);
        cv.put("Shop_id", shopId);
        cv.put("Cond_id", condId);
        cv.put("Lot_num", lot);
        cv.put("Item_quantity", Quantity);
        cv.put("user_id", userId);
        cv.put("user_id", shopTotal);


        return this.mDb.insert("order_product", null, cv);


    }



    public long getTotalNoOfWriteTag(String bookNo)
    {
        long total = 0L;
        String sql = "select count(CardNo) as total from mbill_customer where blockNumber='" +
                bookNo + "' and CardNo!='null' limit 1";
        Cursor cr = this.mDb.rawQuery(sql, null);
        if ((cr.getCount() > 0) &&
                (cr.moveToFirst())) {
            if (cr.getLong(0) != 0L) {
                total = cr.getLong(0);
            } else {
                total = 0L;
            }
        }
        cr.close();
        return total;
    }

}
