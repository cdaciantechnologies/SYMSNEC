package com.vpmobitech.tungwahtsymsnect;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Camera extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView,imageView2,imageView3;
    FrameLayout share;
    ImageView btSave, btnClose, backBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.imageView = (ImageView)this.findViewById(R.id.imageView);
        this.imageView2 = (ImageView)this.findViewById(R.id.imageView2);
        this.imageView3 = (ImageView)this.findViewById(R.id.imageView3);
        this.share = (FrameLayout) this.findViewById(R.id.share);
        Button photoButton = (Button) this.findViewById(R.id.button1);
       // Button btnSave = (Button) this.findViewById(R.id.btnSave);
        btSave = (ImageView) this.findViewById(R.id.save_tick);
        btnClose = (ImageView) this.findViewById(R.id.close);
        backBtn = (ImageView) this.findViewById(R.id.back);


        btSave.setVisibility(View.VISIBLE);
        btnClose.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.VISIBLE);
        imageView3.setVisibility(View.VISIBLE);

        startCmaera();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCmaera();
                btSave.setVisibility(View.VISIBLE);
                btnClose.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.VISIBLE);
                imageView3.setVisibility(View.VISIBLE);
            }
        });


        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 startCmaera();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                share.setForeground(getDrawable(R.mipmap.frame_01));

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                share.setForeground(getDrawable(R.mipmap.frame_02));
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCmaera();
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                Bitmap bitmap = null;

//                viewToBitmap(v);
                BitmapDrawable draw_for = (BitmapDrawable) share.getForeground();
                BitmapDrawable draw_back = (BitmapDrawable) imageView.getDrawable();

                if(draw_for!=null && draw_back!=null )
                {
                    btSave.setVisibility(View.GONE);
                    btnClose.setVisibility(View.GONE);
                    imageView2.setVisibility(View.GONE);
                    imageView3.setVisibility(View.GONE);

                Bitmap bitmapFore = draw_for.getBitmap();
                Bitmap bitmapBack = draw_back.getBitmap();

//                BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
//                Bitmap bitmap = draw.getBitmap();
//
//                Bitmap bitmapFore = ((BitmapDrawable) draw_for).getBitmap();
//                Bitmap bitmapBack = ((BitmapDrawable) draw_back).getBitmap();

                Bitmap scaledBitmapFore = Bitmap.createScaledBitmap(bitmapFore, share.getWidth(), share.getHeight(), true);
                Bitmap scaledBitmapBack = Bitmap.createScaledBitmap(bitmapBack, imageView.getWidth(), imageView.getHeight(), true);

                Bitmap combineImages = overlay(scaledBitmapBack, scaledBitmapFore);

                ImageView image=(ImageView)findViewById(R.id.image);
                image.setImageBitmap(combineImages);




                //to get the image from the ImageView (say iv)
                BitmapDrawable draw = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = draw.getBitmap();

                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/TUNG");
                dir.mkdirs();
                String fileName = String.format("%d.png", System.currentTimeMillis());
                File outFile = new File(dir, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                try {
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(Camera.this, "成功儲存至相簿", Toast.LENGTH_SHORT).show();
                //finish();


                }else {

                    Toast.makeText(Camera.this, "Please Select Frame", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void startCmaera() {

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(photo);
//            imageView.setImageBitmap(photo);

        }else {
            Intent i =new Intent(Camera.this, MainActivity.class);
            startActivity(i);
            finish();

        }
    }



    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2)
    {
        try
        {
            Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(),  bmp1.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, 0,0, null);
            canvas.drawBitmap(bmp2, 0, 0, null);
            return bmOverlay;
        } catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i =new Intent(Camera.this, MainActivity.class);
        startActivity(i);
        finish();

    }

}
