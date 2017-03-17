package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.StringTokenizer;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageSlideViewActivity extends Activity {
    private TextView titleTxt;
    private String image;
    private String img;
    private String type;
    private String title;
    private Bitmap d = null;
    PhotoViewAttacher mAttacher;
    private ImageView imgLogo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageSlideViewActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString(this.getPackageName() + ".title");
            type = extras.getString(this.getPackageName() + ".type");
            image = extras.getString(this.getPackageName() + ".image");
            img = extras.getString(this.getPackageName() + ".img");
        }
        titleTxt = (TextView) findViewById(R.id.title1);
        if (type.equals("Products")) {
            StringTokenizer tokens = new StringTokenizer(title, "(");
            String prodTitle = tokens.nextToken();
            titleTxt.setText(prodTitle);
        } else {
            titleTxt.setText(title);
        }

        final ImageView imgView = (ImageView) findViewById(R.id.imageView);
        if (image != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(image, "drawable", getPackageName()), options);
            options.inJustDecodeBounds = false;
            d = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(image, "drawable", getPackageName()), options);
            d.prepareToDraw();
        }
        if (img != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(img, "drawable", getPackageName()), options);
            options.inJustDecodeBounds = false;
            d = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(img, "drawable", getPackageName()));
            d.prepareToDraw();
        }
        if (d != null) {
            imgView.setImageBitmap(d);
            mAttacher = new PhotoViewAttacher(imgView);
        } else {

            imgView.setImageResource(R.drawable.bayer_default_big);
            mAttacher = new PhotoViewAttacher(imgView);
        }

        imgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    public void resetBitmap() {
        if (d != null) {
            d.recycle();
            d = null;
        }
    }

}
