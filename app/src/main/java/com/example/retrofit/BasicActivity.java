package com.example.retrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicActivity extends AppCompatActivity {
    private ImageView image;
    private Button foto;
    private Button gallary;
    private EditText title;
    private EditText desc;
    private Button save;
    private Button cancel;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int GALLERY_REQUEST=2;
    private String mCurrentPhotoPath;
    private Uri photoURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        image=findViewById(R.id.image);
        foto=findViewById(R.id.foto);
        gallary=findViewById(R.id.gallery);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        save=findViewById(R.id.save);
        cancel=findViewById(R.id.cancel);
        if(getIntent().hasExtra(MainActivity.MODEL_MESSAGE))
        {
            Intent intent=getIntent();
            DataModel dataModel=(DataModel) intent.getSerializableExtra(MainActivity.MODEL_MESSAGE);
            image.setImageURI(Uri.parse(dataModel.getUri()));
            desc.setText(dataModel.getDesc());
            photoURI=Uri.parse(dataModel.getUri());
            save.setEnabled(false);
        }


    }



}
