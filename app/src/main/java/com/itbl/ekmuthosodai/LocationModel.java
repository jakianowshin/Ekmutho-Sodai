package com.itbl.ekmuthosodai;

public class LocationModel {
    private String loc_id,loc_name;

    public LocationModel(String loc_id, String loc_name) {
        this.loc_id = loc_id;
        this.loc_name = loc_name;
    }

    public String getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(String loc_id) {
        this.loc_id = loc_id;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }
}
