package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
