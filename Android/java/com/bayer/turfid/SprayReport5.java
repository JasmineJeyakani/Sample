package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jasmine Jeyakani on 08-Dec-16.
 */

public class SprayReport5 extends Activity {
    int pos;
    ArrayList<HashMap<String, String>> oslist;
    TextView pesticideTxt, waterTxt, totalVolumeTxt, areaTreatedTxt, dateTxt, areaTxt;
    TextView partTankTxt, partTankPesticideTxt, partTankWaterTxt, partTankAreaTreatedTxt;
    TextView quantitiesPesticideTxt, quantitiesWaterTxt, quantitiesTotalVolumeTxt, quantitiesAreaTreatedTxt, finalTxtVal;
    ImageView email, imgLogo;
    ImageView closebtn;
    TextView tit;
    Activity parent;
    DecimalFormat df = new DecimalFormat("####0.00");
    String date;
    String area;
    String pes;
    String wat;
    String tot;
    String areatr;
    String partfil;
    String partpesti;
    String partwat;
    String partarea;
    String quanpes;
    String quanwat;
    String quantot;
    String quanarea;
    String finalTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity__result5);
        imgLogo = (ImageView) findViewById(R.id.logo_img);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SprayReport5.this, BayerTurfManagementActivity.class);
                startActivity(intent);
                SprayReport5.this.finish();
            }
        });
        tit = (TextView) findViewById(R.id.title);
        tit.setText("Spray Report");
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
//        quantitiesPesticideTxt=(TextView)findViewById(R.id.quantitiesPestisideValue);
//        quantitiesWaterTxt=(TextView)findViewById(R.id.quantitiesWaterValue);
//        quantitiesTotalVolumeTxt=(TextView)findViewById(R.id.quantitiesTotalVolumeValue);
//        quantitiesAreaTreatedTxt=(TextView)findViewById(R.id.quantitiesAreaTreatedValue);
        finalTxtVal = (TextView) findViewById(R.id.noTanksValue);


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // String k = "<html><body> This is my Project </body></html>";
                    String k = "<html><body><table border=\"1\" align=\"center\"><caption><b>Spray Report</b></caption><tr><th>Date</th><th>" +
                            date
                            + "</th></tr><tr><th>Area Name</th><th>" +
                            area
                            + "</th></tr><tr >" +
                            "<th colspan=\"2\" >Total Quantities</th>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Pesticide(L)</td>" +
                            "<td>" +
                            pes
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Water(L)</td>" +
                            "<td>" +
                            wat
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Total Volume(L)</td>" +
                            "<td>" +
                            tot
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Area Treated(Sq.m)</td>" +
                            "<td>" +
                            areatr
                            + "</td>" +
                            "</tr> <tr >" +
                            "<th colspan=\"2\" >Part Tank Quantities</th>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>PartTank Fill</td>" +
                            "<td>" +
                            partfil
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Pesticide(L)</td>" +
                            "<td>" +
                            partpesti
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Water(L)</td>" +
                            "<td>" +
                            partwat
                            + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Area Treated(Sq.m)</td>" +
                            "<td>" +
                            partarea
                            + "</td>" +
                            "</tr> " +
//                            "<th colspan=\"2\" >Quantities per Tank</th>" +
//                            "</tr>" +
//                            "<tr>" +
//                            "<td>Pesticide(L)</td>" +
//                            "<td>"+
//                            quantitiesPesticideTxt.getText().toString()
//                            +"</td>" +
//                            "</tr>" +
//                            "<tr>" +
//                            "<td>Water(L)</td>" +
//                            "<td>"+
//                            quantitiesWaterTxt.getText().toString()
//                            +"</td>" +
//                            "</tr>" +
//                            "<tr>" +
//                            "<td>Total Volume(L)</td>" +
//                            "<td>"+
//                            quantitiesTotalVolumeTxt.getText().toString()
//                            +"</td>" +
//                            "</tr>" +
//                            "<tr>" +
//                            "<td>Area Treated(Sq.m)</td>" +
//                            "<td>"+
//                            quantitiesAreaTreatedTxt.getText().toString()
//                            +"</td>" +
//                            "</tr>"+
                            "<tr>" +
                            "<td><b>No.of tanks required</b></td>" +
                            "<td>" +
                            finalTxt
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
            }
        });
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SprayReport5.this, BayerReportsTurf.class);
                startActivity(intent);
                finish();
            }
        });
        Bundle b = getIntent().getExtras();
        if (b != null) {
            pos = b.getInt("pos");
        }
        final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        oslist = db.samp();
        date = oslist.get(pos).get("datefinal");
        area = oslist.get(pos).get("area");
        pes = oslist.get(pos).get("tqp");
        wat = oslist.get(pos).get("tqw");
        tot = oslist.get(pos).get("tqt");
        areatr = oslist.get(pos).get("tqa");
        partfil = oslist.get(pos).get("ptf");
        partpesti = oslist.get(pos).get("ptp");
        partwat = oslist.get(pos).get("ptw");
        partarea = oslist.get(pos).get("pta");
        quanpes = oslist.get(pos).get("qpp");
        quanwat = oslist.get(pos).get("qpw");
        quantot = oslist.get(pos).get("qpt");
        quanarea = oslist.get(pos).get("qpa");
        finalTxt = oslist.get(pos).get("nots");


        dateTxt.setText(area + " - ");
        areaTxt.setText(date);
        pesticideTxt.setText(df.format(Double.parseDouble(pes)));
        waterTxt.setText(df.format(Double.parseDouble(wat)));
        totalVolumeTxt.setText(df.format(Double.parseDouble(tot)));
        areaTreatedTxt.setText(df.format(Double.parseDouble(areatr)));
        partTankTxt.setText(df.format(Double.parseDouble(partfil)));
        partTankPesticideTxt.setText(df.format(Double.parseDouble(partpesti)));
        partTankWaterTxt.setText(df.format(Double.parseDouble(partwat)));
        partTankAreaTreatedTxt.setText(df.format(Double.parseDouble(partarea)));
//
        finalTxtVal.setText(df.format(Double.parseDouble(finalTxt)));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SprayReport5.this, BayerReportsTurf.class);
//        i.putExtra("tabintent",true);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }
}
