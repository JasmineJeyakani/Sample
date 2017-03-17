package com.bayer.turfid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ItemDescriptionActivity extends Activity {
	private TextView titleTxt;
	private String type;
	private String name;
	private String family;
	private String image;
	private String img;
	private String description;
	private String title;
	private String cultural;
	private TIDatabase TIdb;
	private Cursor c = null;
	private String query = null;
	private Bitmap singleImg = null;
	private int imgCnt = 0;
	private ImageView imgLogo;
	private Bitmap bmpFirst = null;
	private Bitmap bmpSecond = null;
	Integer id = 0;
	Integer i = 0;

	private String singleImage;
	public int selectionPos;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_desc);
		Display display = getWindowManager().getDefaultDisplay();
		final int width = display.getWidth();  // deprecated
		int height = display.getWidth();  // deprecated

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			title = extras.getString(this.getPackageName() + ".title");
			type = extras.getString(this.getPackageName() + ".type");
			name = extras.getString(this.getPackageName() + ".name");
			family = extras.getString(this.getPackageName() + ".family");
			description = extras.getString(this.getPackageName() + ".desc");
			image = extras.getString(this.getPackageName() + ".image");
			img = extras.getString(this.getPackageName() + ".img");
			cultural = extras.getString(this.getPackageName() + ".cultural");
			selectionPos = extras.getInt(this.getPackageName() + ".selectionPos");
			Log.d("TAG", "selectionPos" + selectionPos);
		}
		imgLogo = (ImageView) findViewById(R.id.img_logo);
		imgLogo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemDescriptionActivity.this, BayerTurfManagementActivity.class);
				startActivity(intent);
			}
		});
		titleTxt = (TextView) findViewById(R.id.title1);
		if (type.equals("Products")) {
			StringTokenizer tokens = new StringTokenizer(title, "(");
			String prodTitle = tokens.nextToken();
			titleTxt.setText(prodTitle);
		} else {
			titleTxt.setText(title);
		}

		final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

		final ImageView flowerImg = (ImageView) findViewById(R.id.image);
		final ImageView youngImg = (ImageView) findViewById(R.id.img);
		imgCnt = 0;


		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(image, "drawable", getPackageName()), options);
			options.inJustDecodeBounds = false;
			bmpFirst = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(image, "drawable", getPackageName()), options);
			bmpFirst.prepareToDraw();
		} catch (Exception e) {

		}


		if (bmpFirst == null) {

		} else {

			imgCnt++;
			singleImg = bmpFirst;
			singleImage = image;
		}


		try {
			if (type.equals("Weeds")) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(img, "drawable", getPackageName()), options);
				options.inJustDecodeBounds = false;
				bmpSecond = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(img, "drawable", getPackageName()), options);
				bmpSecond.prepareToDraw();
			}
		} catch (Exception e) {

		}
		if (bmpSecond == null) {
		} else {
			imgCnt++;
			singleImg = bmpSecond;
			singleImage = img;
		}

		LinearLayout.LayoutParams layoutParams = new
				LinearLayout.LayoutParams(width, height / 2);
		RelativeLayout.LayoutParams layoutParams1 = new
				RelativeLayout.LayoutParams(width, height / 2);
		flowerImg.setLayoutParams(layoutParams1);
		youngImg.setLayoutParams(layoutParams1);

		if (imgCnt == 2) {

			RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			RelativeLayout.LayoutParams rigthParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			flowerImg.setScaleType(ScaleType.FIT_CENTER);
			flowerImg.setImageBitmap(bmpFirst);

			ImageView firstArrowView = (ImageView) findViewById(R.id.firstArrow);
			Bitmap firstBMap = BitmapFactory.decodeResource(getResources(), R.drawable.id_right);
			Bitmap firstBMapScaled = Bitmap.createScaledBitmap(firstBMap, firstBMap.getWidth() / 2, firstBMap.getHeight() / 2, true);
			firstArrowView.setImageBitmap(firstBMapScaled);

			float firstw2h = (float) bmpFirst.getWidth() / (float) bmpFirst.getHeight();
			int firstRatioWidth = (int) (firstw2h * layoutParams1.height);

			int XfromOrgin = (width - firstRatioWidth) / 2;
			int paddingSpaceX = (XfromOrgin + firstRatioWidth) - firstBMap.getWidth();
			float floatpaddingSpaceY = (float) (layoutParams1.height - firstBMap.getHeight() / 1.5);
			int paddingSpaceY = (int) floatpaddingSpaceY;
			rigthParams.setMargins(paddingSpaceX, paddingSpaceY, 0, 0);
			firstArrowView.setLayoutParams(rigthParams);

			youngImg.setScaleType(ScaleType.FIT_CENTER);
			youngImg.setImageBitmap(bmpSecond);

			ImageView secondArrowView = (ImageView) findViewById(R.id.secondArrow);
			Bitmap secondBMap = BitmapFactory.decodeResource(getResources(), R.drawable.id_left);
			Bitmap secondBMapScaled = Bitmap.createScaledBitmap(secondBMap, secondBMap.getWidth() / 2, secondBMap.getHeight() / 2, true);
			secondArrowView.setImageBitmap(secondBMapScaled);

			float secondw2h = (float) bmpSecond.getWidth() / (float) bmpSecond.getHeight();
			int secondRatioWidth = (int) (secondw2h * layoutParams1.height);

			int XXfromOrgin = (width - secondRatioWidth) / 2;
			int paddingSpaceXX = XXfromOrgin + (secondBMap.getWidth() / 2);
			leftParams.setMargins(paddingSpaceXX, paddingSpaceY, 0, 0);
			secondArrowView.setLayoutParams(leftParams);


		} else if (imgCnt == 1) {
			scrollView.setEnabled(false);
			scrollView.setFocusable(false);
			scrollView.setHorizontalScrollBarEnabled(false);
			scrollView.setVerticalScrollBarEnabled(false);
			scrollView.setEnabled(false);
			youngImg.setVisibility(View.GONE);
//    		flowerImg.setImageDrawable(singleImg);
			flowerImg.setImageBitmap(singleImg);
			flowerImg.setScaleType(ScaleType.FIT_CENTER);
		} else {
			flowerImg.setImageResource(R.drawable.bayer_default_big);
//    		flowerImg.setScaleType(ScaleType.FIT_CENTER);
		}

		if (type.equals("Products")) {
			flowerImg.setScaleType(ScaleType.FIT_CENTER);
		}


		flowerImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent slideViewActivity = new Intent(getParent(), ImageSlideViewActivity.class);
				slideViewActivity.putExtra(getPackageName() + ".title", title);
				if (type.equals("Weeds")) {
					slideViewActivity.putExtra(getPackageName() + ".type", "flower");
				} else {
					slideViewActivity.putExtra(getPackageName() + ".type", type);
				}

				if (imgCnt == 1 && type.equals("Weeds"))
					slideViewActivity.putExtra(getPackageName() + ".image", singleImage);
				else
					slideViewActivity.putExtra(getPackageName() + ".image", image);

				TabGroupActivity parentActivity = (TabGroupActivity) getParent();
				parentActivity.startChildActivity("ImageSlideViewActivity", slideViewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});


		youngImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent slideViewActivity = new Intent(getParent(), ImageSlideViewActivity.class);
				slideViewActivity.putExtra(getPackageName() + ".title", title);
				slideViewActivity.putExtra(getPackageName() + ".type", "young");
				slideViewActivity.putExtra(getPackageName() + ".img", img);
				TabGroupActivity parentActivity = (TabGroupActivity) getParent();
				parentActivity.startChildActivity("ImageSlideViewActivity", slideViewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});

		final TextView nameTxt = (TextView) findViewById(R.id.name);
		nameTxt.setText(name);

		TextView familyTxt = (TextView) findViewById(R.id.family);
		familyTxt.setText(family);

		TextView descTxt = (TextView) findViewById(R.id.description);
		descTxt.setMovementMethod(LinkMovementMethod.getInstance());
		descTxt.setText(Html.fromHtml(description));

		ImageButton btntreatment = (ImageButton) findViewById(R.id.treatment);
		ImageButton btncultural = (ImageButton) findViewById(R.id.cultural);
		TextView txttreatment = (TextView) findViewById(R.id.treatmentText);
		TextView txtcultural = (TextView) findViewById(R.id.culturalText);
		btntreatment.getBackground().setAlpha(0);
		btncultural.getBackground().setAlpha(0);
		Log.d("tag", "type.equals" + type);
		if (type.equals("Diseases") || type.equals("Insects") || type.equals("Weeds")) {
			btntreatment.setImageResource(R.drawable.nonturfuse);
			btncultural.setImageResource(R.drawable.turfuse);

			if (type.equals("Weeds")) {
				Log.d("tag", "Weedstxttreatment");
				txttreatment.setText("Non Turf-Use");
				txtcultural.setText("Turf-Use");
			} else {
				Log.d("tag", "txttreatment");
				txttreatment.setText("Treatment");
				txtcultural.setText("Cultural");
			}

		} else {
			btntreatment.setVisibility(View.GONE);
			btncultural.setVisibility(View.GONE);
		}


		TIdb = new TIDatabase(getBaseContext());

		if ((type.equals("Diseases")) || (type.equals("Insects"))) {

			TIdb.open();
			if (type.equals("Diseases")) {
				query = "Select DiseaseId from tblDisease where DiseaseName = '" + nameTxt.getText() + "'";
				c = TIdb.FetchAllRecordsForRawQuery(query);
				c.moveToFirst();
				id = c.getInt(0);
				c.close();
				c = null;
				query = "";
				query = "Select distinct A.* from tblProduct A, tblDiseaseProduct B on A.ProductId = B.ProductId where B.DiseaseId = " + id;
			} else if (type.equals("Insects")) {
				query = "Select PestId from tblPest where PestName = '" + nameTxt.getText() + "'";
				c = TIdb.FetchAllRecordsForRawQuery(query);
				c.moveToFirst();
				id = c.getInt(0);
				c.close();
				c = null;
				query = "";
				query = "Select distinct A.* from tblProduct A, tblPestProduct B on A.ProductId = B.ProductId where B.PestId = " + id;
			}

			TIdb.close();
		}

		btntreatment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

//				Log.d("Button but","Button but"+flowerImg.getWidth()+flowerImg.getHeight()+test.getWidth()+test.getHeight());
				if (type.equals("Weeds")) {

					final TextView alertTitle = new TextView(ItemDescriptionActivity.this);
					alertTitle.setText("Bayer");
					alertTitle.setGravity(Gravity.CENTER);
					alertTitle.setTextColor(Color.WHITE);
					alertTitle.setTextSize(20);

					TIdb.open();
					query = "Select WeedId from tblWeed where weedName = '" + nameTxt.getText() + "'";
					c = TIdb.FetchAllRecordsForRawQuery(query);
					c.moveToFirst();
					id = c.getInt(0);
					c.close();
					c = null;
					query = "";
					query = "Select distinct A.* from tblProduct A, tblWeedProduct B on A.ProductId = B.ProductId where B.WeedId = " + id + " and B.ISTurf = 0";
					c = TIdb.FetchAllRecordsForRawQuery(query);
					if ((c == null) || ((c.getCount()) == 0)) {
						TabGroupActivity parentActivity = (TabGroupActivity) getParent();
						final AlertDialog.Builder ad = new AlertDialog.Builder(parentActivity);
						ad.setCancelable(false); // This blocks the 'BACK' button
						//ad.setCustomTitle(alertTitle);
						ad.setMessage("Bayer do not currently have a product to control this weed but please tel: 00800 1214 9451 for further advice.");
						ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						ad.setNegativeButton("Call Now", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent callIntent = new Intent(Intent.ACTION_CALL);
								callIntent.setData(Uri.parse("tel:00800 1214 9451"));
								if (ActivityCompat.checkSelfPermission(ItemDescriptionActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
									// TODO: Consider calling
									//    ActivityCompat#requestPermissions
									// here to request the missing permissions, and then overriding
									//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
									//                                          int[] grantResults)
									// to handle the case where the user grants the permission. See the documentation
									// for ActivityCompat#requestPermissions for more details.
									return;
								}
								startActivity(callIntent);
							}
						});
						try {
							ad.show();
						} catch (Exception ex) {

						}
						c.close();
						TIdb.close();
					} else {
						Intent prodDescActivity = new Intent(getParent(), ProductDescriptionActivity.class);
						prodDescActivity.putExtra(getPackageName() + ".weed", nameTxt.getText());
						prodDescActivity.putExtra(getPackageName() + ".type", "NonTurf");
						TabGroupActivity parentActivity = (TabGroupActivity) getParent();
						if (android.os.Build.VERSION.SDK_INT <= 10)
							parentActivity.startChildActivity("ProductDescriptionActivity", prodDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						else
							parentActivity.startChildActivity("ProductDescriptionActivity", prodDescActivity);

					}
				}

				else if (type.equals("Diseases")) {
					final TextView alertTitle = new TextView(ItemDescriptionActivity.this);
					alertTitle.setText("Bayer");
					alertTitle.setGravity(Gravity.CENTER);
					alertTitle.setTextColor(Color.WHITE);
					alertTitle.setTextSize(20);

					TIdb.open();
					query = "Select DiseaseId from tblDisease where DiseaseName = '" + nameTxt.getText() + "'";
					c = TIdb.FetchAllRecordsForRawQuery(query);
					c.moveToFirst();
					id = c.getInt(0);
					c.close();
					c = null;
					query = "";
					query = "Select distinct A.* from tblProduct A, tblDiseaseProduct B on A.ProductId = B.ProductId where B.DiseaseId = " + id;
					c = TIdb.FetchAllRecordsForRawQuery(query);
					if ((c == null) || ((c.getCount()) == 0)) {
						TabGroupActivity parentActivity = (TabGroupActivity) getParent();
						AlertDialog.Builder ad = new AlertDialog.Builder(parentActivity);
						ad.setCancelable(false); // This blocks the 'BACK' button
						//ad.setCustomTitle(alertTitle);
						ad.setMessage("Bayer do not currently have a product to control this disease but please tel: 00800 1214 9451 for further advice.");
						ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						ad.setNegativeButton("Call Now", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent callIntent = new Intent(Intent.ACTION_CALL);
								callIntent.setData(Uri.parse("tel:00800 1214 9451"));
								if (ActivityCompat.checkSelfPermission(ItemDescriptionActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
									// TODO: Consider calling
									//    ActivityCompat#requestPermissions
									// here to request the missing permissions, and then overriding
									//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
									//                                          int[] grantResults)
									// to handle the case where the user grants the permission. See the documentation
									// for ActivityCompat#requestPermissions for more details.
									return;
								}
								startActivity(callIntent);
							}
						});
						try {
							ad.show();
						} catch (Exception ex) {

						}
						c.close();
						TIdb.close();
					} else {
						Intent prodDescActivity = new Intent(getParent(), ProductDescriptionActivity.class);
						prodDescActivity.putExtra(getPackageName() + ".disease", nameTxt.getText());
						prodDescActivity.putExtra(getPackageName() + ".type", "Diseases");
						TabGroupActivity parentActivity = (TabGroupActivity) getParent();
						if (android.os.Build.VERSION.SDK_INT <= 10)
							parentActivity.startChildActivity("ProductDescriptionActivity", prodDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						else
							parentActivity.startChildActivity("ProductDescriptionActivity", prodDescActivity);
					}
				} else if (type.equals("Insects")) {
					final TextView alertTitle = new TextView(ItemDescriptionActivity.this);
					alertTitle.setText("Bayer");
					alertTitle.setGravity(Gravity.CENTER);
					alertTitle.setTextColor(Color.WHITE);
					alertTitle.setTextSize(20);

					TIdb.open();
					query = "Select PestId from tblPest where PestName = '" + nameTxt.getText() + "'";
					c = TIdb.FetchAllRecordsForRawQuery(query);
					c.moveToFirst();
					id = c.getInt(0);
					c.close();
					c = null;
					query = "";
					query = "Select distinct A.* from tblProduct A, tblPestProduct B on A.ProductId = B.ProductId where B.PestId = " + id;
					c = TIdb.FetchAllRecordsForRawQuery(query);
					if ((c == null) || ((c.getCount()) == 0)) {
						TabGroupActivity parentActivity = (TabGroupActivity) getParent();
						AlertDialog.Builder ad = new AlertDialog.Builder(parentActivity);
						ad.setCancelable(false); // This blocks the 'BACK' button
						//ad.setCustomTitle(alertTitle);
						ad.setMessage("Bayer do not currently have a product to control this insect but please tel: 00800 1214 9451 for further advice.");
						ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						ad.setNegativeButton("Call Now", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent callIntent = new Intent(Intent.ACTION_CALL);
								callIntent.setData(Uri.parse("tel:00800 1214 9451"));
								if (ActivityCompat.checkSelfPermission(ItemDescriptionActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
									// TODO: Consider calling
									//    ActivityCompat#requestPermissions
									// here to request the missing permissions, and then overriding
									//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
									//                                          int[] grantResults)
									// to handle the case where the user grants the permission. See the documentation
									// for ActivityCompat#requestPermissions for more details.
									return;
								}
								startActivity(callIntent);
							}
						});
						try {
							ad.show();
						} catch (Exception ex) {

						}
						c.close();
						TIdb.close();
					} else {
						Log.d("TAG", "ProductDescriptionActivity");
						Intent prodDescActivity = new Intent(getParent(), ProductDescriptionActivity.class);
						prodDescActivity.putExtra(getPackageName() + ".insect", nameTxt.getText());
						prodDescActivity.putExtra(getPackageName() + ".type", "Insects");
						TabGroupActivity parentActivity = (TabGroupActivity) getParent();
						if (android.os.Build.VERSION.SDK_INT <= 10)
							parentActivity.startChildActivity("ProductDescriptionActivity", prodDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						else
							parentActivity.startChildActivity("ProductDescriptionActivity", prodDescActivity);
					}
				}
			}
		});
		btncultural.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				if (type.equals("Weeds")) {
					final TextView alertTitle = new TextView(ItemDescriptionActivity.this);
					alertTitle.setText("Bayer");
					alertTitle.setGravity(Gravity.CENTER);
					alertTitle.setTextColor(Color.WHITE);
					alertTitle.setTextSize(20);

					TIdb.open();
					query = "Select WeedId from tblWeed where weedName = '" + nameTxt.getText() + "'";
					c = TIdb.FetchAllRecordsForRawQuery(query);
					c.moveToFirst();
					id = c.getInt(0);
					c.close();
					c = null;
					query = "";
					query = "Select distinct A.* from tblProduct A, tblWeedProduct B on A.ProductId = B.ProductId where B.WeedId = " + id + " and B.ISTurf = 1";
					c = TIdb.FetchAllRecordsForRawQuery(query);
					if ((c == null) || ((c.getCount()) == 0)) {
						TabGroupActivity parentActivity = (TabGroupActivity) getParent();
						AlertDialog.Builder ad = new AlertDialog.Builder(parentActivity);
						ad.setCancelable(false); // This blocks the 'BACK' button
						//ad.setCustomTitle(alertTitle);
						ad.setMessage("Bayer do not currently have a product to control this weed but please tel: 00800 1214 9451 for further advice.");
						ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						ad.setNegativeButton("Call Now", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent callIntent = new Intent(Intent.ACTION_CALL);
								callIntent.setData(Uri.parse("tel:00800 1214 9451"));
								if (ActivityCompat.checkSelfPermission(ItemDescriptionActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
									// TODO: Consider calling
									//    ActivityCompat#requestPermissions
									// here to request the missing permissions, and then overriding
									//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
									//                                          int[] grantResults)
									// to handle the case where the user grants the permission. See the documentation
									// for ActivityCompat#requestPermissions for more details.
									return;
								}
								startActivity(callIntent);
							}
						});
						try {
							ad.show();
						} catch(Exception ex) {
							
						}
						c.close();
						TIdb.close();
			        } else {
						Intent prodDescActivity = new Intent(getParent(), ProductDescriptionActivity.class);
						prodDescActivity.putExtra(getPackageName()+".weed", nameTxt.getText());
						prodDescActivity.putExtra(getPackageName()+".type", "Turf");
						TabGroupActivity parentActivity = (TabGroupActivity)getParent();
						if(android.os.Build.VERSION.SDK_INT <= 10)
							parentActivity.startChildActivity("ProductDescriptionActivity", prodDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						else
							parentActivity.startChildActivity("ProductDescriptionActivity", prodDescActivity);
			        }
				} 
//				else {
//					if(i > 1) {
//						Intent itemDescActivity = new Intent(getParent(), ItemDescriptionActivity.class);
//						itemDescActivity.putExtra(getPackageName()+".title", productList.get(1).ProductName);
//						itemDescActivity.putExtra(getPackageName()+".type", "Products");
//						itemDescActivity.putExtra(getPackageName()+".name", productList.get(1).ProductName);
//						itemDescActivity.putExtra(getPackageName()+".family", productList.get(1).ProductType);
//						String desc;
//						desc = "<b>Overview</b>\n" + productList.get(1).ProductOverview + "\n" + "<b>Use Area</b>\n" + productList.get(1).UseArea + "<b>KeyFeatures</b>\n" + productList.get(1).KeyFeatures;
//						itemDescActivity.putExtra(getPackageName()+".desc", desc);
//						itemDescActivity.putExtra(getPackageName()+".image", productList.get(1).ProductPack);
//						TabGroupActivity parentActivity = (TabGroupActivity)getParent();
//						parentActivity.startChildActivity("ProductItemDescriptionActivity", itemDescActivity);				
//					}
//				}
				else
				{
Log.d("tag","product desc act");
					Intent culturalDescActivity = new Intent(getParent(), CulturalActivity.class);
					culturalDescActivity.putExtra(getPackageName()+".cultural", cultural);
					culturalDescActivity.putExtra(getPackageName()+".title", title);
					
					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
					if(android.os.Build.VERSION.SDK_INT <= 10)
						parentActivity.startChildActivity("CulturalActivity", culturalDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					else
						parentActivity.startChildActivity("CulturalActivity", culturalDescActivity);

				}
			}
		});
    }
    
    public void resetBitmap() {
    	if(bmpFirst != null )
    	{
    		bmpFirst.recycle();
    		bmpFirst = null;
    	}
    	if(bmpSecond != null)
    	{
    		bmpSecond.recycle();
    		bmpSecond = null;
    	}
    	System.gc();
       
       Log.d("tag" ,"BackresetBitmapresetBitmapresetBitmap");
    }
}

