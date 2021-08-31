package com.itbl.ekmuthosodai;

public class Category {
    private String cat_Id, cat_name;

    public Category(String cat_Id, String cat_name) {
        this.cat_Id = cat_Id;
        this.cat_name = cat_name;
    }

    public String getCat_Id() {
        return cat_Id;
    }

    public void setCat_Id(String cat_Id) {
        this.cat_Id = cat_Id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
}
