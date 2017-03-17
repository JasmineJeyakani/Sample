package com.bayer.turfid;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import org.apache.http.ParseException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class SprayActivity_Result5 extends Activity {
    TextView pesticideTxt, waterTxt, totalVolumeTxt, areaTreatedTxt, dateTxt, areaTxt;
    TextView partTankTxt, partTankPesticideTxt, partTankWaterTxt, partTankAreaTreatedTxt;
    TextView finalTxtVal;
    Double totalWater, TQpesticide, TQvol, TQarea;
    Double QPTareaTreated, QPTpartTankFill;
    Double PTQpartTank, PTQpesticide, PTQwater, PTQareaTreated;
    Double areaPerTank;
    Double partTankWater;
    String nooftanks;
    Double waterPerTank, tankCapacity, pesticidePerTank, numberOfTanks1, numberOfTanks;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    Character c;
    Character c1;
    String sb, date, areaName;
    ImageView email;
    DecimalFormat df = new DecimalFormat("####0.00");
    Double nofval;
    ImageView closebtn, imgLogo;
    DecimalFormat df2 = new DecimalFormat("0.#");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity__result5);
        email = (ImageView) findViewById(R.id.emailButton);
        closebtn = (ImageView) findViewById(R.id.closeButton);
        dateTxt = (TextView) findViewById(R.id.dateTxt);
        areaTxt = (TextView) findViewById(R.id.areaVal);
        pesticideTxt = (TextView) findViewById(R.id.pesticideValue);
        waterTxt = (TextView) findViewById(R.id.waterValue);
        totalVolumeTxt = (TextView) findViewById(R.id.totalvolumeValue);
        areaTreatedTxt = (TextView) findViewById(R.id.areatreatedVolume);
        partTankTxt = (TextView) findViewById(R.id.parttankValue);
        partTankPesticideTxt = (TextView) findViewById(R.id.parttankpesticideValue);
        partTankWaterTxt = (TextView) findViewById(R.id.parttankwaterValue);
        partTankAreaTreatedTxt = (TextView) findViewById(R.id.parttankAreatreatedValue);
        finalTxtVal = (TextView) findViewById(R.id.noTanksValue);


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        ImageView img = (ImageView) dialog.findViewById(R.id.close);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

        imgLogo = (ImageView) findViewById(R.id.logo_img);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprayActivity_Result5.this, BayerTurfManagementActivity.class);
                startActivity(intent);
                SprayActivity_Result5.this.finish();
            }
        });
        df2.setMaximumFractionDigits(8);

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clo = new Intent(SprayActivity_Result5.this, BayerTurfManagementActivity.class);
                startActivity(clo);
                SprayActivity_Result5.this.finish();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // String k = "<html><body> This is my Project </body></html>";
                    String k = "<html><body><table border=\"1\" align=\"center\"><caption><b>Spray Report</b></caption><tr><th>Date</th><th>" +
                            dateTxt.getText().toString()
                            + "</th></tr><tr><th>Area Name</th><th>" +
                            areaName
                            + "</th></tr><tr >" +
                            "<th colspan=\"2\" >Total Quantities</th>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Pesticide(L)</td>" +
                            "<td>" +
                            String.valueOf(TQpesticide)
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Water(L)</td>" +
                            "<td>" +
                            String.valueOf(totalWater)
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Total Volume(L)</td>" +
                            "<td>" +
                            String.valueOf(TQvol)
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Area Treated(Sq.m)</td>" +
                            "<td>" +
                            String.valueOf(TQarea)
                            + "</td>" +
                            "</tr> <tr >" +
                            "<th colspan=\"2\" >Part Tank Quantities</th>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>PartTank Fill</td>" +
                            "<td>" +
                            partTankTxt.getText().toString()
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Pesticide(L)</td>" +
                            "<td>" +
                            String.valueOf(PTQpesticide)
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Water(L)</td>" +
                            "<td>" +
                            String.valueOf(partTankWater)
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Area Treated(Sq.m)</td>" +
                            "<td>" +
                            String.valueOf(PTQareaTreated)
                            + "</td>" +
                            "</tr> " +
                            "<tr>" +
                            "<td>No.of tanks required</td>" +
                            "<td>" +
                            String.valueOf(nooftanks)
                            + "</td>" +
                            "</tr>" +

                            "</table></body></html>";
//
                    Document doc = new Document();
                    InputStream in = new ByteArrayInputStream(k.getBytes());
                    PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(getApplication().getExternalFilesDir("MyFileStorage") + "/out.pdf"));
                    doc.open();
                    XMLWorkerHelper.getInstance().parseXHtml(pdf, doc, in);
                    doc.close();

                    in.close();

                    File pdfpath = new File(getApplication().getExternalFilesDir("MyFileStorage") + "/out.pdf");
                    Uri path = Uri.fromFile(pdfpath);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"amenity@bayer.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Spray Report");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "hi");
                    emailIntent.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                String val="Hai";
//                createandDisplayPdf(val);
            }
        });

        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        tankCapacity = Double.valueOf(Prefrence.getString("sprayTankValue1", String.valueOf(0)));
        nooftanks = Prefrence.getString("numberOfTanks", String.valueOf(0));
        pesticidePerTank = Double.valueOf(Prefrence.getString("pesticidePerTank", String.valueOf(0)));
        numberOfTanks = Double.valueOf(Prefrence.getString("numberOfTanks", String.valueOf(0)));
        numberOfTanks1 = Double.valueOf(Prefrence.getString("numberOfTanks", String.valueOf(0)));// Double.valueOf( nooftanks);
        TQarea = Double.valueOf(Prefrence.getString("areaVal", String.valueOf(0)));
        TQpesticide = Double.valueOf(Prefrence.getString("TQpesticide", String.valueOf(0)));
        date = Prefrence.getString("dateofcalibration", "date");
        areaName = Prefrence.getString("areaName", "area");


        areaPerTank = TQarea / numberOfTanks1;

        if (nooftanks.contains(".")) {


            int dot = nooftanks.indexOf(".");

            String[] sepArray = nooftanks.split("\\.");
            String tempArray = sepArray[1];
            if (dot == 1) {
                if (tempArray.length() == 1) {
                    sb = tempArray;
                } else if (tempArray.length() >= 2) {
                    c = tempArray.charAt(0);
                    c1 = tempArray.charAt(1);
                    sb = new StringBuilder().append(c).append(c1).toString();
                    if (sb.startsWith(".") || (sb.endsWith("."))) {
                        sb = "0";
                    }
                }
            } else {
                if (tempArray.length() == 1) {
                    sb = tempArray;
                } else if (tempArray.length() == 2) {
                    c = tempArray.charAt(0);
                    c1 = tempArray.charAt(1);
                    sb = new StringBuilder().append(c).append(c1).toString();
                } else if (tempArray.length() == 3) {
                    c = tempArray.charAt(1);
                    c1 = tempArray.charAt(2);
                    sb = new StringBuilder().append(c).append(c1).toString();
                } else if (tempArray.length() > 3) {

                    sb = nooftanks.substring(3, 5);
                    if (sb.startsWith(".") || (sb.endsWith("."))) {
                        sb = "0";
                    }
                }
            }
        } else {
            sb = String.valueOf(0);
        }
        waterPerTank = tankCapacity - pesticidePerTank;
        totalWater = waterPerTank * numberOfTanks1;
        String partTankval = "0." + sb;
        PTQpartTank = Double.valueOf(partTankval);
        PTQpesticide = pesticidePerTank * PTQpartTank;
        PTQwater = waterPerTank * numberOfTanks1;
        PTQareaTreated = areaPerTank * PTQpartTank;
        partTankWater = waterPerTank * PTQpartTank;


        partTankTxt.setText("0." + sb);

        partTankPesticideTxt.setText(String.valueOf(df.format(PTQpesticide)));
        partTankWaterTxt.setText(String.valueOf(df.format(partTankWater)));
        ;
        partTankAreaTreatedTxt.setText(String.valueOf(df.format(PTQareaTreated)));


        Log.d("pt", "0." + sb);
        Log.d("ptt", String.valueOf(PTQpesticide));
        Log.d("ptw", String.valueOf(partTankWater));
        Log.d("pta", String.valueOf(PTQareaTreated));


        //Toast.makeText(this, ""+TQwater, Toast.LENGTH_SHORT).show();
        TQvol = TQpesticide + totalWater;
        QPTareaTreated = TQarea / numberOfTanks1;
        QPTpartTankFill = Double.valueOf(Prefrence.getString("pesticidePerTank", String.valueOf(0)));
        // DateFormat inputFormat = new SimpleDateFormat("mmm dd, YYYY");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        String date1 = outputFormat.format(Date.parse(date));
        dateTxt.setText(date1 + " - ");
        areaTxt.setText(areaName);

        pesticideTxt.setText(String.valueOf(df.format(TQpesticide)));
        waterTxt.setText(df.format(totalWater));
        totalVolumeTxt.setText(String.valueOf(df.format(TQvol)));
        areaTreatedTxt.setText(String.valueOf(df.format(TQarea)));
        finalTxtVal.setText(String.valueOf(df.format(Double.valueOf(nooftanks))));
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        String dte = date1;
        String area = areaName;
        String club = Prefrence.getString("golfName", "clubnme");
        String tqp = String.valueOf(TQpesticide);
        String tqw = String.valueOf(totalWater);
        String tqt = String.valueOf(TQvol);
        String tqa = String.valueOf(TQarea);
        String ptf = String.valueOf("0." + sb);
        String ptp = String.valueOf(PTQpesticide);
        String ptw = String.valueOf(partTankWater);
        String pta = String.valueOf(PTQareaTreated);
        String notnk = String.valueOf(nooftanks);
        SimpleDateFormat outputFormat2 = new SimpleDateFormat("dd-MMM-yyyy");
        String datefinal = outputFormat2.format(Date.parse(date));
        db.insertSprayTablle(datefinal, dte, area, club, tqp, tqw, tqt, tqa, ptf, ptp, ptw, pta, notnk);

    }

    @Override
    public void onBackPressed() {
        Intent step8Sprayertank = new Intent(SprayActivity_Result5.this, BayerTurfManagementActivity.class);
        startActivity(step8Sprayertank);
        SprayActivity_Result5.this.finish();
    }
}
