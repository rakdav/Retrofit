package com.example.retrofit;


import com.google.gson.annotations.SerializedName;

public class DataModel
{
    @SerializedName("id")
    private int id;

    @SerializedName("desc")
    private String desc;

    @SerializedName("url")
    private String uri;

    public DataModel(String uri, String desc) {
        this.uri = uri;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
