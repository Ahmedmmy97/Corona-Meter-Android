package com.a33y.jo.coronameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("Active")
    @Expose
    private String active;
    @SerializedName("Death")
    @Expose
    private String death;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("New")
    @Expose
    private String _new;
    @SerializedName("NewDeath")
    @Expose
    private String newDeath;
    @SerializedName("Recovered")
    @Expose
    private String recovered;
    @SerializedName("Serious")
    @Expose
    private String serious;
    @SerializedName("Total")
    @Expose
    private String total;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNew() {
        return _new;
    }

    public void setNew(String _new) {
        this._new = _new;
    }

    public String getNewDeath() {
        return newDeath;
    }

    public void setNewDeath(String newDeath) {
        this.newDeath = newDeath;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getSerious() {
        return serious;
    }

    public void setSerious(String serious) {
        this.serious = serious;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
