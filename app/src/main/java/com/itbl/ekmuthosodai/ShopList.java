package com.itbl.ekmuthosodai;

public class ShopList {
    private String ITEM_ID,ITEM_DESCR,DB_POINT_DESCR,DB_POINT_CODE;

    public ShopList(String ITEM_ID, String ITEM_DESCR, String DB_POINT_DESCR,String DB_POINT_CODE) {
        this.ITEM_ID = ITEM_ID;
        this.ITEM_DESCR = ITEM_DESCR;
        this.DB_POINT_DESCR = DB_POINT_DESCR;
        this.DB_POINT_CODE = DB_POINT_CODE;
    }

    public String getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(String ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getITEM_DESCR() {
        return ITEM_DESCR;
    }

    public void setITEM_DESCR(String ITEM_DESCR) {
        this.ITEM_DESCR = ITEM_DESCR;
    }

    public String getDB_POINT_DESCR() {
        return DB_POINT_DESCR;
    }

    public void setDB_POINT_DESCR(String DB_POINT_DESCR) {
        this.DB_POINT_DESCR = DB_POINT_DESCR;
    }

    public String getDB_POINT_CODE() {
        return DB_POINT_CODE;
    }

    public void setDB_POINT_CODE(String DB_POINT_CODE) {
        this.DB_POINT_CODE = DB_POINT_CODE;
    }
}
