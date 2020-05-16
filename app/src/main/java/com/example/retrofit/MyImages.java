package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyImages
{
    @SerializedName("error")
    private boolean error;
    @SerializedName("images")
    private ArrayList<DataModel> images;

    public MyImages(boolean error, ArrayList<DataModel> images) {
        this.error = error;
        this.images = images;
    }

    public boolean isError() {
        return error;
    }

    public ArrayList<DataModel> getImages() {
        return images;
    }
}
