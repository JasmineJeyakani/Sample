package com.bayer.turfid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jasmine Jeyakani on 12-Oct-16.
 */

public class SampIdentify extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    ImageView camImg, imgLogo;
    static boolean fCamera = false;
    private GestureDetector gestureDetector;
    Button yes, no;
    String path;
    private Uri imagePath;
    ArrayList<Uri> images;
    ArrayList<String> patharray;
    Context context;
    TextView count;
    private ContentValues values;
    private Uri imageUri;
    private String imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identify);
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        count = (TextView) findViewById(R.id.count);


        imgLogo = (ImageView) findViewById(R.id.img_logo);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SampIdentify.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });


        patharray = new ArrayList<>();
        camImg = (ImageView) findViewById(R.id.cameraImg);
        takeImageFromCamera();
        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            public boolean onDoubleTap(MotionEvent e) {
                takeImageFromCamera();
                return true;
            }
        });
        if (patharray.size() == 2) {
            count.setText("<2 of 3>");
        } else if (patharray.size() == 3) {
            count.setText("<3 of 3>");
        }
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (patharray.size() >= 3) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(SampIdentify.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    dialog.setTitle("Only three images can be sent");
                    dialog.setMessage("Press OK to send images");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try {
                                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amenity@bayer.com"});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Images for Identification");
                                intent.putExtra(Intent.EXTRA_TEXT, "HI");
                                intent.setType("message/rfc822");

                                PackageManager pm = getApplicationContext().getPackageManager();
                                List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                                ResolveInfo best = null;
                                for (final ResolveInfo info : matches) {
                                    if (info.activityInfo.packageName.contains(".gm.") || info.activityInfo.name.toLowerCase().contains("gmail"))
                                        best = info;
                                }
                                ArrayList<Uri> uri = new ArrayList<Uri>();
                                for (int i = 0; i < patharray.size(); i++) {
                                    uri.add(Uri.parse(patharray.get(i)));
                                }


                                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uri);


                                if (best != null)
                                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                                startActivity(Intent.createChooser(intent, "Choose an email application..."));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                Toast.makeText(getApplicationContext(), "" + ex, Toast.LENGTH_SHORT).show();
                                throw ex;
                            }


                        }


                    });
                    dialog.show();

                    //  Toast.makeText(SampIdentify.this, "NotAllowed", Toast.LENGTH_SHORT).show();
                } else if (patharray.size() == 2) {
                    takeImageFromCamera();
                    count.setText("<3 of 3>");

                } else if (patharray.size() == 1) {
                    takeImageFromCamera();
                    count.setText("<2 of 3>");
                }

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (patharray.size() == 1) {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("text/plain");
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"amenity@bayer.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "Images for Identification");
                    email.setType("image/png");
                    email.putExtra(Intent.EXTRA_STREAM, imagePath);
                    startActivity(Intent.createChooser(email, "Send email using"));
                } else if (patharray.size() == 2) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amenity@bayer.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Images for Identification");
                        intent.putExtra(Intent.EXTRA_TEXT, "HI");
                        intent.setType("message/rfc822");

                        PackageManager pm = getApplicationContext().getPackageManager();
                        List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                        ResolveInfo best = null;
                        for (final ResolveInfo info : matches) {
                            if (info.activityInfo.packageName.contains(".gm.") || info.activityInfo.name.toLowerCase().contains("gmail"))
                                best = info;
                        }
                        //String[] filePaths=new String[]{"sdcard/Rohi/R.jpg","sdcard/Rohi/Rohii.jpg"};
                        ArrayList<Uri> uri = new ArrayList<Uri>();
                        for (int i = 0; i < patharray.size(); i++) {
                            //File file = new File(patharray.get(i));
                            uri.add(Uri.parse(patharray.get(i)));
                        }


                        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uri);


                        if (best != null)
                            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                        startActivity(Intent.createChooser(intent, "Choose an email application..."));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getApplicationContext(), "" + ex, Toast.LENGTH_SHORT).show();
                        throw ex;
                    }

                } else if (patharray.size() == 3)
                    try {
                        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amenity@bayer.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Images for Identification");
                        intent.putExtra(Intent.EXTRA_TEXT, "HI");
                        intent.setType("message/rfc822");

                        PackageManager pm = getApplicationContext().getPackageManager();
                        List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                        ResolveInfo best = null;
                        for (final ResolveInfo info : matches) {
                            if (info.activityInfo.packageName.contains(".gm.") || info.activityInfo.name.toLowerCase().contains("gmail"))
                                best = info;
                        }
                        ArrayList<Uri> uri = new ArrayList<Uri>();
                        for (int i = 0; i < patharray.size(); i++) {
                            uri.add(Uri.parse(patharray.get(i)));
                        }


                        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uri);


                        if (best != null)
                            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                        startActivity(Intent.createChooser(intent, "Choose an email application..."));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getApplicationContext(), "" + ex, Toast.LENGTH_SHORT).show();
                        throw ex;
                    }

                else {
                    Toast.makeText(SampIdentify.this, "Mail cant be sent", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void takeImageFromCamera() {
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            Bitmap thumbnail = null;
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                camImg.setImageBitmap(thumbnail);
                imageurl = getRealPathFromURI(imageUri);
                path = MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, "new Picture", null);
                if (path != null) {
                    imagePath = Uri.parse(path);
                    patharray.add(path);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_CANCELED) {
            Intent i = new Intent(SampIdentify.this, BayerIdentify2.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finishFromChild(SampIdentify.this);


        }


    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SampIdentify.this, BayerIdentify2.class);
        startActivity(i);
    }
}


