package com.example.retrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, GALLERY_REQUEST);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(photoURI,desc.getText().toString());
            }
        });
    }

    private void uploadFile(Uri fileUri,String description)
    {
        File file=new File(getRealPathFromUri(fileUri));
     //   File file=new File(fileUri.toString());
        RequestBody requestFile=RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)),file);
        RequestBody descBody=RequestBody.create(MediaType.parse("text/plain"),description);
        Gson gson=new GsonBuilder().setLenient().create();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        Api api=retrofit.create(Api.class);
        Call<MyResponse> call=api.uploadImage(requestFile,descBody);
        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if(!response.body().isError()){
                    Toast.makeText(getApplicationContext(),"File Uploaded Successfully...",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Some error occurred...",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Фигово",Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getRealPathFromUri(Uri contentUri)
    {
        String[] proj={MediaStore.Images.Media.DATA};
        CursorLoader loader=new CursorLoader(this,contentUri,proj,null,null,null);
        Cursor cursor=loader.loadInBackground();
        int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result=cursor.getString(column_index);
        cursor.close();;
        return result;
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            image.setImageURI(photoURI);
        }
        switch(requestCode)
        {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    photoURI = data.getData();
                    image.setImageURI(photoURI);

                }
        }
    }

}
