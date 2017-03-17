package com.bayer.turfid;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WeatherMainActivity extends Activity implements LocationListener {
    private String TAG = WeatherMainActivity.class.getSimpleName();
   NonScrollListView list;
    private ProgressDialog progress;
    LinearLayout lin1;
    String url,url1;
    TextView datetxt;
    TextView country;
    TextView celcius, humiditytxt, windtxt, feelsliketxt;
    ProgressBar progressBar;
    String   city,  direction;
    ArrayList<HashMap<String, String>> oslist;
    ArrayList<HashMap<String, String>> oslist12;
    ArrayList<String> oslistall;
//    ArrayList<String> relativeHumiditity;
    ArrayList<String> feelsLikeList;
    List<String> pictode = new ArrayList<>();
    List<Double> rehum=new ArrayList<Double>();
    List<Double> windDirec=new ArrayList<>();
    List<Integer> feelsLikefinal=new ArrayList<>();
    List<Integer> feelsLike=new ArrayList<>();
    List<Integer> wndSped=new ArrayList<Integer>();
    String tomorrowAsString1;
    int tempmeanall,tempmeanAvgall;
    Double soilmean,wndspedmean;
    Double direction1,windSpeedcalc;
    String daylist,daylistnew,daylist21,daylist21y,daylistnewMain,daylistMain;
    DecimalFormat df2=new DecimalFormat("####0.0");
    DecimalFormat df3=new DecimalFormat("####0.00");
    ImageView imgLogo;
    ViewPager viewPager;
    RelativeLayout lil;
    Double[] rehummeanArr;
    String[] pictodelist;


    List<String> val2 = new ArrayList<String>();
    List<String> frostIndex = new ArrayList<String>();
    List<String> temperatureNew = new ArrayList<String>();
    String[] temperatureList;
    List<Integer> index = new ArrayList<Integer>();
    int positions1;
    List<String> snowfall = new ArrayList<String>();
    List<Double> percipMor = new ArrayList<>();
    List<Double> percipNoon = new ArrayList<>();
    List<Double> perciEve = new ArrayList<>();
    List<Double> perciNight = new ArrayList<>();

    String[] dateArr;
    ArrayList<String> datelist;
    ArrayList<String> dtxlist;
    Integer[] feelsLikeArr;
    int postitionval;
    ArrayList<String> windlist;
    String[] windArr;
    int newPosValue;
    LinearLayout progressLay;
    RelativeLayout relativeLayout;
    ArrayList<Integer> oslistall1;
    Integer[] tempmeanArrSamp;


    ArrayList<String> dayListArray;
    ArrayList<String> dayListArrayMain;
    String[] daylistArrayval;
    ArrayList<Integer> oslistallMax;
    Integer[] tempmeanArrMax;
    ArrayList<Integer> oslistallMin;
    Integer[] tempmeanArrMin;

    ArrayList<String> conCatList;
    String[] conCatArr;

    Geocoder geocoder;
    String bestProvider;
    List<Address> user = null;
    double lat;
    double lng;
    String cityloc;
    String tz;
    String countryloc;


    ArrayList<String> datelistNew=new ArrayList<>();
    ArrayList<String> datelistNewy=new ArrayList<>();
    String dateArrNew[];


    List<Integer> temper=new ArrayList<Integer>();
    List<Integer> temper1=new ArrayList<Integer>();

    List<String> val=new ArrayList<String>();
    List<String> val1=new ArrayList<String>();
    List<String> valNew = new ArrayList<String>();

    List<Double> valPer = new ArrayList<>();
    List<Double> valPerDate = new ArrayList<>();
    List<String> DatePerci = new ArrayList<>();
    List<Double> soiltemp = new ArrayList<Double>();
    List<Double> soiltemp1 = new ArrayList<Double>();
    List<Double> skintemp = new ArrayList<Double>();
    List<Double> skintemp1 = new ArrayList<Double>();
    List<Long> dewPointtemp = new ArrayList<>();
    List<Long> dewPointtemp1 = new ArrayList<>();
    List<Double> evapo = new ArrayList<Double>();
    List<Double> evapo1 = new ArrayList<Double>();
    List<Integer> soilMois = new ArrayList<Integer>();
    List<Integer> soilMois1 = new ArrayList<Integer>();
    List<String> relativeHumidity = new ArrayList<String>();
    List<String> sealevel = new ArrayList<>();
    List<String> feltTemp = new ArrayList<String>();
    List<String> dewPoint = new ArrayList<String>();
    List<String> surfacepressure = new ArrayList<String>();
    List<String> soilMoisture = new ArrayList<String>();
    List<String> potentialEvap = new ArrayList<String>();
    List<String> potentialEvapDay = new ArrayList<String>();

    List<String> soilTemperature = new ArrayList<String>();
    List<String> morvalue =new ArrayList<>();
    List<String> noonvalue=new ArrayList<>();
    List<String> evevalue=new ArrayList<>();
    List<String> nghtvalue=new ArrayList<>();
    List<String> growingDays = new ArrayList<String>();
    List<String> tempMax = new ArrayList<String>();

    List<String> humd=new ArrayList<>();
    String date;
    Double soiltempmean, dewPointtempmean, evapomean,skintempmean;
    String soiltempAvg;
    int skintempAvg;
    int rehumAvg;
    double dewPointtempAvg;int soilMoisAvg;
    String surfacetempVal;
    int rehummean, soilMoismean;
    double evapoAvg;

    int pos;
    double mor=0,morAvg;
    double noon=0,noonAvg;
    double eve=0,eveAvg;
    double night=0,nightAvg;
    String frostList[];
    String snowfallList[];
    String relativeHumidityList[];
    String surfacePressureList[];
    String felttempList[];
    String dewPointList[];
    String sealevelPressureList[];
    String soilMoitureList[];
    String potentialEvapList[];
    String soiltempList[];
    String growingDaysList[];
    String morvalueList[];
    String noonvalueList[];
    String evevalueList[];
    String nghtvalueList[];
    String tempMaxList[];
    String humdArr[];
    String  datelistArrayval[];

    List<Integer> feelsLikeblue=new ArrayList<>();
    List<Integer> feelsLikeblue1=new ArrayList<>();
    int feelsLikeBluemeaan;
    int feelsLikeBlueAvg;
    Integer[] feelsLikebluefinal;
    List<String> humlist=new ArrayList<>();
    String humArray[];
    List<String> tempMain=new ArrayList<>();
    String[] tempMainArr;
    List<Double> apparant_temp;
    List<Double> apparant_temp1;
    List<String> apparant_tempFinal;
    Double apparat_mean;
    Double apparant_Avg;


    List<Double> Main_temp;
    List<Double> Main_temp1;
    List<String> Main_tempFinal;
    Double Main_mean;
    long Main_Avg;
    String[] tempMainArrMean;


    List<Integer> Main_windDirec;
    List<Integer> Main_windDirec1;
    List<Double> Main_windDirecFinal;
    Double Main_windDirecmean;
    Double Main_windDirecAvg;
    String[] windDirecMainArrMean;

    List<Double> Main_windSpeed;
    List<Double> Main_windSpeed1;
    List<Integer> Main_windSpeedFinal;
    Double Main_windSpeedmean;
    int Main_windSpeedAvg;
    String[] windSpeedArrMean;
    String hour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);





        String location_context = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, 1000, 0,
                    new LocationListener() {

                        public void onLocationChanged(Location location) {
                        }

                        public void onProviderDisabled(String provider) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onStatusChanged(String provider, int status,
                                                    Bundle extras) {
                        }
                    });
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                geocoder = new Geocoder(this);
                try {
                    user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    for(Address each : user){
                        if(each.getLocality()!=null){
                            cityloc = each.getLocality();
                        }
                        if(each.getLocality()!=null){
                            countryloc = each.getCountryName();
                        }
                    }




                }catch (Exception e) {
                    e.printStackTrace();
                }
                lat = location.getLatitude();
                lng = location.getLongitude();
//                lat= Double.parseDouble("53.350140");
//                lng= Double.parseDouble("-6.266155");

                Log.d("lat", String.valueOf(lat));
                Log.d("lng", String.valueOf(lng));
                Log.d("city1", String.valueOf(cityloc));
                Log.d("coun1", String.valueOf(countryloc));


            }
            else
            {
               // Toast.makeText(this, "not", Toast.LENGTH_SHORT).show();
            }
        }

        Calendar cal = Calendar.getInstance();
        tz = cal.getTimeZone().getID();
       // tz="Dublin/Ireland";
         LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        bestProvider = lm.getBestProvider(criteria, false);
        Log.d("loc",tz+lat+lng+city+country);

        url="http://my.meteoblue.com/packages/agro-1h_basic-day?name="+cityloc+"&lat="+lat+"&lon="+lng+"&asl=14&tz="+tz+"&apikey=84hd73dkd72ad3&temperature=C&windspeed=ms-1&winddirection=degree&precipitationamount=mm&timeformat=iso8601&format=json";
        url1="http://my.meteoblue.com/packages/agro-1h_basic-1h?name="+cityloc+"&lat="+lat+"&lon="+lng+"&asl=14&tz="+tz+"&apikey=84hd73dkd72ad3&temperature=C&windspeed=ms-1&winddirection=degree&precipitationamount=mm&timeformat=iso8601&format=json";
//        url="http://my.meteoblue.com/packages/agro-1h_basic-day?name=Sydney&lat=-33.865143&lon=151.209900&asl=14&tz=Australia/Sydney&apikey=84hd73dkd72ad3&temperature=C&windspeed=ms-1&winddirection=degree&precipitationamount=mm&timeformat=iso8601&format=json";
//        url1="http://my.meteoblue.com/packages/agro-1h_basic-1h?name=Sydney&lat=-33.865143&lon=151.209900&asl=14&tz=Australia/Sydney&apikey=84hd73dkd72ad3&temperature=C&windspeed=ms-1&winddirection=degree&precipitationamount=mm&timeformat=iso8601&format=json";

//        url="http://my.meteoblue.com/packages/agro-1h_basic-day?name=Chennai&lat=12.981129&lon=80.2546528&asl=14&tz=Asia/Calcutta&apikey=84hd73dkd72ad3&temperature=C&windspeed=ms-1&winddirection=degree&precipitationamount=mm&timeformat=iso8601&format=json";
//        url1="http://my.meteoblue.com/packages/agro-1h_basic-1h?name=Chennai&lat=12.981129&lon=80.2546528&asl=14&tz=Asia/Calcutta&apikey=84hd73dkd72ad3&temperature=C&windspeed=ms-1&winddirection=degree&precipitationamount=mm&timeformat=iso8601&format=json";
      //

       // Log.d("URL",url);
        progressBar=(ProgressBar)findViewById(R.id.progressbar_loading);
        soilmean=0.0;
        windSpeedcalc=0.0;
        wndspedmean=0.0;
        tempmeanAvgall=0;
        tempmeanall=0;
        newPosValue=1;
        list = (NonScrollListView) findViewById(R.id.nonlistView);
        lil=(RelativeLayout)findViewById(R.id.ll);
        datetxt = (TextView) findViewById(R.id.datetxtview);
        country = (TextView) findViewById(R.id.countrytxt);
        celcius = (TextView) findViewById(R.id.celciustxt);
        humiditytxt = (TextView) findViewById(R.id.humiditytxt1);
        windtxt = (TextView) findViewById(R.id.windtxt1);
        feelsliketxt=(TextView)findViewById(R.id.feelsliketxt1);
        imgLogo=(ImageView)findViewById(R.id.img_logo);
        viewPager=(ViewPager)findViewById(R.id.weatherlayout);
        progressLay=(LinearLayout)findViewById(R.id.prograssLayout);
        relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        lin1=(LinearLayout)findViewById(R.id.lin);


        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WeatherMainActivity.this, BayerTurfManagementActivity.class);
                startActivity (intent);
            }
        });




        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // oslist.clear();

//                int pos=position+1;
//                postitionval=pos;
//                LoadAllArticles la=new LoadAllArticles();
//                la.getListItem(pos);

                oslist.clear();

                int pos=position+1;
                postitionval=pos;
                LoadAllArticles la=new LoadAllArticles();
                la.getListItem(pos);
               // Toast.makeText(WeatherMainActivity.this, "pos3"+pos, Toast.LENGTH_SHORT).show();
                if(pos==0)
                {
                    newPosValue=1;
                }
                else {
                    newPosValue=pos;
                }




            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        oslist = new ArrayList<HashMap<String, String>>();
        oslist12 = new ArrayList<HashMap<String, String>>();
        oslistall = new ArrayList<>();
        oslistall1 = new ArrayList<>();
        oslistallMax = new ArrayList<>();
        oslistallMin = new ArrayList<>();
        //relativeHumiditity=new ArrayList<>();
        feelsLikeList=new ArrayList<>();
        datelist=new ArrayList<>();
        dtxlist=new ArrayList<>();
        windlist=new ArrayList<>();
        conCatList=new ArrayList<>();
        dayListArray=new ArrayList<>();
        dayListArrayMain=new ArrayList<>();
        feelsLikeblue=new ArrayList<>();
        apparant_temp=new ArrayList<>();
        apparant_tempFinal=new ArrayList<>();
        Main_temp=new ArrayList<>();
        Main_tempFinal=new ArrayList<>();
        Main_windDirec=new ArrayList<>();
        Main_windDirecFinal=new ArrayList<>();
        Main_windSpeed=new ArrayList<>();
        Main_windSpeedFinal=new ArrayList<>();





        new LoadAllArticles().execute();
    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        Log.d("loc", "onLocationChanged" + location.getLatitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    class weatherPager extends PagerAdapter
    {
        Context context;
        LayoutInflater inflater;
        String[] tempmeanArrSamp;

        public weatherPager(Context context, String[] tempmeanArrSamp) {
            this.context=context;
            this.tempmeanArrSamp=tempmeanArrSamp;
        }

        @Override
        public int getCount() {
           return tempMainArrMean.length;

        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==((LinearLayout)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {


            TextView textView;
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflaterView=inflater.inflate(R.layout.temp_lay_main,container,false);
            textView= (TextView) inflaterView.findViewById(R.id.textView3);
            Typeface birdman = Typeface.createFromAsset(getAssets(),
                    "fonts/helvetica_thin.ttf");

            textView.setTypeface(birdman);
            textView.setText(String.valueOf(tempMainArrMean[position]));
          //  newPosValue=position;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent weatherListActivity = new Intent(getParent(), WeatherlistActivity.class).addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);//FLAG_ACTIVITY_CLEAR_TOP);

                    weatherListActivity.putExtra("position",position);
                    weatherListActivity.putExtra("lat",String.valueOf(lat));
                    weatherListActivity.putExtra("lng",String.valueOf(lng));
                    weatherListActivity.putExtra("tz",String.valueOf(tz));
                    weatherListActivity.putExtra("cityloc",String.valueOf(cityloc));
                    weatherListActivity.putExtra("counloc",String.valueOf(countryloc));
                    weatherListActivity.putExtra("tempArray",tempMainArr);
                    weatherListActivity.putExtra("frost",frostList);
                    weatherListActivity.putExtra("snowfall",snowfallList);
                    weatherListActivity.putExtra("relative",humArray);
                    weatherListActivity.putExtra("sealevel", sealevelPressureList);
                    weatherListActivity.putExtra("feltTemp", felttempList);
                    weatherListActivity.putExtra("dew",dewPointList);
                    weatherListActivity.putExtra("surfacetemp",surfacePressureList);
                    weatherListActivity.putExtra("liquidSoilMoisture",soilMoitureList);
                    weatherListActivity.putExtra("potentialEva",potentialEvapList);
                    weatherListActivity.putExtra("soiltemp",soiltempList);
                    weatherListActivity.putExtra("growingDays",growingDaysList);
                    weatherListActivity.putExtra("morvalue",morvalueList);
                    weatherListActivity.putExtra("noonvalue",noonvalueList);
                    weatherListActivity.putExtra("evevalue",evevalueList);
                    weatherListActivity.putExtra("nightvalue",nghtvalueList);
                    weatherListActivity.putExtra("date",daylistArrayval);
                    weatherListActivity.putExtra("day",datelistArrayval);
                    weatherListActivity.putExtra("picvalue",pictodelist);

                    TabGroupActivity parentActivity = (TabGroupActivity)getParent();
                    if(android.os.Build.VERSION.SDK_INT <= 10)
                    {
                        parentActivity.startChildActivity("WeatherlistActivity", weatherListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }else
                    {
                        parentActivity.startChildActivity("WeatherlistActivity", weatherListActivity);
                    }
                }
            });
            ((ViewPager)container).addView(inflaterView);
            return inflaterView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflaterView=inflater.inflate(R.layout.temp_lay,container,false);
            ((ViewPager)container).removeView(inflaterView);

        }
    }



    class LoadAllArticles extends AsyncTask<Void, Void, Void> {

        //   = new ProgressDialog(WeatherMainActivity.this);
        InputStream inputStream = null;
        String result = "";
        String day = "";
        String text = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //  String date="";


        protected void onPreExecute() {
//            if(progressLay.getVisibility()==View.INVISIBLE)
//            {
//                progressLay.setVisibility(View.VISIBLE);
//                relativeLayout.setVisibility(View.INVISIBLE);
//            }

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            JSONParser sh = new JSONParser();
            String jsonStr = sh.makeServiceCall(url);
            String jsonStr1 = sh.makeServiceCall(url1);

           // Log.e(TAG, "Response from url: " + jsonStr1);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject dataCurr = jsonObject.getJSONObject("metadata");
                    JSONObject dateday = jsonObject.getJSONObject("data_day");
                    JSONObject data1hObj = jsonObject.getJSONObject("data_1h");
                    JSONArray tempMax1 = dateday.getJSONArray("temperature_mean");
                    JSONArray tempMaxDetail = dateday.getJSONArray("temperature_max");
                    JSONArray felsLike = dateday.getJSONArray("felttemperature_min");
                    JSONArray windDirection = dateday.getJSONArray("winddirection");
                    JSONArray wndspd = dateday.getJSONArray("windspeed_mean");
                    JSONArray sealevelpressure_mean = dateday.getJSONArray("sealevelpressure_mean");
                    JSONArray relativeh = dateday.getJSONArray("relativehumidity_mean");
                    JSONArray humismall=dateday.getJSONArray("humiditygreater90_hours");
                    JSONArray timedte=dateday.getJSONArray("time");
                    JSONArray felt = dateday.getJSONArray("felttemperature_min");
                    JSONArray pic = dateday.getJSONArray("pictocode");
                    JSONArray snowFall = dateday.getJSONArray("snowfraction");
                    JSONArray evapojson = data1hObj.getJSONArray("potentialevapotranspiration");
                    JSONArray timeval=data1hObj.getJSONArray("time");




                    int timeLength=timedte.length();

                    for (int i = 0; i < evapojson.length(); i++) {
                        if(evapojson.getString(i).equals(null))
                        {
                            evapo.add(0.0);
                        }
                        else {
                            evapo.add(evapojson.getDouble(i));
                        }
                    }

                    Date newDate = null;
                    SimpleDateFormat df1 = new SimpleDateFormat("HH");
                    Calendar c = Calendar.getInstance();

                    hour= String.valueOf(df1.format(c.get(Calendar.HOUR_OF_DAY)));


                   // System.out.println("Current time => "+Hr24);

                    //hour= String.valueOf(c.getTime().getHours());

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String date1  = df.format(c.getTime());
                    String date2=timeval.getString(timeval.length()-1);
                    //Toast.makeText(WeatherMainActivity.this, "hi"+timedte.getString(timedte.length()-1), Toast.LENGTH_SHORT).show();
                    String[] v=date2.split("T");

                    //Toast.makeText(WeatherMainActivity.this, "Date"+v[0], Toast.LENGTH_SHORT).show();
                    Log.d("Date2",v[0]);


                    List<String> array=getDates(date1,date2);
                    for(int i=0;i<array.size();i++)
                    {
                        Log.d("date", String.valueOf(array.get(i)));
                    }




//                    Date dt = new Date();
//                    int hours = dt.getHours();
//                    Toast.makeText(WeatherMainActivity.this, "HR"+hours, Toast.LENGTH_SHORT).show();







                    for (int i = 0; i < tempMax1.length(); i++) {

                        if(snowFall.getString(i).equals(null))
                        {
                            snowfall.add(String.valueOf(0.0));
                        }
                        else {
                            snowfall.add(String.valueOf(df3.format(snowFall.getDouble(i))));
                        }
                        if(relativeh.getString(i).equals(null))
                        {
                            relativeHumidity.add(String.valueOf(0));
                        }
                        else
                        {
                            relativeHumidity.add(relativeh.getString(i));
                        }

                        if(sealevelpressure_mean.getString(i).equals(null))
                        {
                            sealevel.add(String.valueOf(0));
                        }
                        else
                        {
                            sealevel.add(String.valueOf(df3.format(sealevelpressure_mean.getDouble(i) * 0.1)));
                        }

                        if(felt.getString(i).equals(null))
                        {
                            feltTemp.add(String.valueOf(0));
                        }
                        else
                        {
                            feltTemp.add(String.valueOf(Math.round(felt.getDouble(i))));
                        }

                        if(humismall.getString(i).equals(null))
                        {
                            humd.add(String.valueOf(0));
                        }
                        else
                        {
                            humd.add(String.valueOf(Math.round(humismall.getDouble(i))));
                        }

                        if(pic.getString(i).equals(null))
                        {
                            pictode.add(String.valueOf(1));
                        }
                        else
                        {
                            pictode.add(pic.getString(i));
                        }


                        if(relativeh.getString(i).equals(null))
                        {
                            humlist.add(String.valueOf(1));
                        }
                        else
                        {
                            humlist.add(relativeh.getString(i));
                        }


//                        if(tempMaxDetail.getString(i).equals(null))
//                        {
//                            tempMain.add(String.valueOf(20));
//                        }
//                        else
//                        {
                            tempMain.add(String.valueOf((int) Math.round(tempMaxDetail.getDouble(i))));
                       // }




                        humArray=humlist.toArray(new String[humlist.size()]);
                        humdArr=humd.toArray(new String[humd.size()]);
                        pictodelist=pictode.toArray(new String[pictode.size()]);

                      //  Log.d("max",String.valueOf((int) Math.round(tempMaxDetail.getDouble(i))));
                        tempMainArr=tempMain.toArray(new String[tempMain.size()]);

                        String dte = date1;
                        try {
                            newDate = dateFormat.parse(dte);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tomorrowAsString1 = dateFormat.format(newDate);
                      //  Log.d("pic", String.valueOf(pictodelist[i]));

                        oslistall1.add(Integer.valueOf((int) Math.round(tempMax1.getDouble(i))));
                        //rehum.add(Double.valueOf(df2.format(rehu.getDouble(i))));
                        feelsLikeblue.add((int) Math.round(felsLike.getDouble(i)));
                        Log.d("feels", String.valueOf(feelsLikeblue.get(i)));
                        wndSped.add((int) Math.round(wndspd.getDouble(i)));
                        windDirec.add(windDirection.getDouble(i));

                        tempmeanArrSamp = oslistall1.toArray(new Integer[oslistall1.size()]);
                        //rehummeanArr = rehum.toArray(new Double[rehum.size()]);
                        feelsLikebluefinal = feelsLikeblue.toArray(new Integer[feelsLikeblue.size()]);
                      //  Log.d("feels", String.valueOf(feelsLikefinal.get(i)));

                        oslistallMax.add(Integer.valueOf((int) Math.round(tempMax1.getDouble(i))));
                        tempmeanArrMax = oslistallMax.toArray(new Integer[oslistallMax.size()]);
                        oslistallMin.add(Integer.valueOf((int) Math.round(tempMax1.getDouble(i))));
//
                        tempmeanArrMin = oslistallMin.toArray(new Integer[oslistallMin.size()]);


//                        direction1 = Double.valueOf(windDirec.get(i));
//                        if ((direction1 >= 326.25) && (direction1 <= 348.75)) {
//                            direction = "NNW";
//                        } else if ((direction1 >= 303.75) && (direction1 <= 326.25)) {
//                            direction = "NW";
//                        } else if ((direction1 >= 281.25) && (direction1 <= 303.74)) {
//                            direction = "WNW";
//                        } else if ((direction1 >= 258.75) && (direction1 <= 281.24)) {
//                            direction = "W";
//                        } else if ((direction1 >= 236.25) && (direction1 <= 258.74)) {
//                            direction = "WSW";
//                        } else if ((direction1 >= 213.75) && (direction1 <= 236.24)) {
//                            direction = "SW";
//                        } else if ((direction1 >= 191.25) && (direction1 <= 213.74)) {
//                            direction = "SSW";
//                        } else if ((direction1 >= 168.75) && (direction1 <= 191.24)) {
//                            direction = "S";
//                        } else if ((direction1 >= 146.25) && (direction1 <= 168.74)) {
//                            direction = "SSE";
//                        } else if ((direction1 >= 123.75) && (direction1 <= 146.24)) {
//                            direction = "SE";
//                        } else if ((direction1 >= 101.25) && (direction1 <= 123.74)) {
//                            direction = "ESE";
//                        } else if ((direction1 >= 78.75) && (direction1 <= 101.24)) {
//                            direction = "E";
//                        } else if ((direction1 >= 56.25) && (direction1 <= 78.74)) {
//                            direction = "ENE";
//                        } else if ((direction1 >= 33.75) && (direction1 <= 56.24)) {
//                            direction = "NE";
//                        } else if ((direction1 >= 11.25) && (direction1 <= 33.74)) {
//                            direction = "NNE";
//                        } else if ((direction1 >= 348.75) && (direction1 <= 11.24)) {
//                            direction = "E";
//                        }
//                        String windConcat = direction + " " + wndSped.get(i);
//                        windlist.add(windConcat);
//                        windArr = windlist.toArray(new String[windlist.size()]);

                        String maxVal = String.valueOf(tempmeanArrMax[i]);
                        String minVal = String.valueOf(tempmeanArrMin[i]);
                        String conCat = maxVal + "\u00B0" + "/" + minVal + "\u00B0";
                        conCatList.add(conCat);
                        conCatArr = conCatList.toArray(new String[conCatList.size()]);

                    }


                    SimpleDateFormat formatx = new SimpleDateFormat("yyyy-MM-dd");

                    for (int i = 0; i <= timeLength-1; i++) {




                        SimpleDateFormat formatx1 = new SimpleDateFormat("dd/MM");
                        Calendar calendar1 = Calendar.getInstance();
                        try {
                            calendar1.setTime(formatx.parse(tomorrowAsString1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date dtx = null;
                        calendar1.add(Calendar.DATE, i);
                        newDate = calendar1.getTime();
                        daylistnew = formatx1.format(newDate);
                        daylist = dateFormat.format(newDate);
                        try {
                            dtx = formatx.parse(daylist);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar calendarx = Calendar.getInstance();
                        calendarx.setTime(dtx);
                        String[] days1x = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

                        String[] days1y=new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

                       // Log.d("daylistNew", daylistnew);
                        dayListArray.add(daylistnew);
                        daylist21 = days1x[calendarx.get(Calendar.DAY_OF_WEEK) - 1];
                        daylist21y=days1y[calendarx.get(Calendar.DAY_OF_WEEK)-1];

                        datelistNew.add(daylist21);
                        datelistNewy.add(daylist21y);
                        dateArrNew = datelistNew.toArray(new String[datelistNew.size()]);
                        String concatStr = daylist21 + " " + daylistnew;
                        dtxlist.add(concatStr);
                        datelist.add(concatStr);
                        dateArr = dtxlist.toArray(new String[dtxlist.size()]);
                        daylistArrayval = dayListArray.toArray(new String[dayListArray.size()]);
                        datelistArrayval = datelistNewy.toArray(new String[datelistNewy.size()]);

                    }




                    for (int i = 0; i <= timeLength; i++) {




                        SimpleDateFormat formatx1 = new SimpleDateFormat("dd/MM");
                        Calendar calendar1 = Calendar.getInstance();
                        try {
                            calendar1.setTime(formatx.parse(tomorrowAsString1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date dtx = null;
                        calendar1.add(Calendar.DATE, i);
                        newDate = calendar1.getTime();
                        daylistnewMain = formatx1.format(newDate);
                        daylistMain = dateFormat.format(newDate);
                        try {
                            dtx = formatx.parse(daylistMain);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar calendarx = Calendar.getInstance();
                        calendarx.setTime(dtx);
                        String[] days1x = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

                        String[] days1y=new String[]{"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};

                      //  Log.d("daylistNew", daylistnewMain);
                        dayListArrayMain.add(daylistnewMain);
//                        daylist21 = days1x[calendarx.get(Calendar.DAY_OF_WEEK) - 1];
//                        daylist21y=days1y[calendarx.get(Calendar.DAY_OF_WEEK)-1];
//
//                        datelistNew.add(daylist21);
//                        datelistNewy.add(daylist21y);
//                        dateArrNew = datelistNew.toArray(new String[datelistNew.size()]);
//                        String concatStr = daylist21 + " " + daylistnew;
//                        dtxlist.add(concatStr);
//                        datelist.add(concatStr);
//                        dateArr = dtxlist.toArray(new String[dtxlist.size()]);
//                        daylistArrayval = dayListArray.toArray(new String[dayListArray.size()]);
//                        datelistArrayval = datelistNewy.toArray(new String[datelistNewy.size()]);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            if (jsonStr1 != null) {
                try {
                    JSONObject jsonObj1 = new JSONObject(jsonStr1);
                    JSONObject data1hObj = jsonObj1.getJSONObject("data_1h");
                    JSONArray time = data1hObj.getJSONArray("time");
                    JSONArray temperature = data1hObj.getJSONArray("temperature");
                    JSONArray soiltempjson = data1hObj.getJSONArray("soiltemperature_0to10cm");
                    JSONArray skintempjson = data1hObj.getJSONArray("skintemperature");
                    JSONArray dewPointjson = data1hObj.getJSONArray("dewpointtemperature");
                    JSONArray soilMoisjson = data1hObj.getJSONArray("soilmoisture_0to10cm");
                    JSONArray perciProb = data1hObj.getJSONArray("precipitation_probability");
                    JSONArray feltTemp=data1hObj.getJSONArray("felttemperature");
                    JSONArray windSpeed=data1hObj.getJSONArray("windspeed");
                    JSONArray windDir=data1hObj.getJSONArray("winddirection");
                    JSONObject dataCurr = jsonObj1.getJSONObject("metadata");







                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    String date1  = df.format(c.getTime());
//                    String date2=time.getString(time.length()-1);
//                    //Toast.makeText(WeatherMainActivity.this, "hi"+timedte.getString(timedte.length()-1), Toast.LENGTH_SHORT).show();
//                    String[] v=date2.split("T");
//
//                    //Toast.makeText(WeatherMainActivity.this, "Date"+v[0], Toast.LENGTH_SHORT).show();
//                    Log.d("Date2",v[0]);
//
//
//                    List<String> array=getDates(date1,date2);
//                    for(int i=0;i<array.size();i++)
//                    {
//                        Log.d("date", String.valueOf(array.get(i)));
//                    }
//


                    JSONObject meta = jsonObj1.getJSONObject("metadata");
                    city = meta.getString("name");
                    for (int i = 0; i < time.length(); i++) {
                        val.add(time.getString(i));
                        valNew.add((time.getString(i)));
                    }

                    for (int z = 0; z < val.size(); z++) {
                        String[] sep = val.get(z).split("T");
                    }

                    for (int z = 0; z < val.size(); z++) {
                        String[] sep = val.get(z).split("T");

                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd/MM");
                        String dat = sep[0];
                        Date datenew = null;
                        try {
                            datenew = inputFormat.parse(dat);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String outputDateStr = outputFormat.format(datenew);
                        val1.add(outputDateStr);

                    }
                    for (int i = 0; i < temperature.length(); i++) {
                        if(temperature.getString(i).equals(null))
                        {
                            temper.add((int) 0);
                            Main_temp.add(0.0);
                        }
                        else
                        {
                            temper.add((int) Math.round(temperature.getDouble(i)));
                            Main_temp.add(temperature.getDouble(i));
                        }
                       // Log.d("temper", String.valueOf(temper.get(i)));
                    }
                    for (int i = 0; i < perciProb.length(); i++) {
                        if(perciProb.getString(i).equals(null)) {
                            valPer.add(Double.valueOf(0));
                        }
                        else {
                            valPer.add(perciProb.getDouble(i));
                        }
                    }

                    for (int i = 0; i < soiltempjson.length(); i++) {

                        if(soiltempjson.getString(i).equals(null)) {
                            soiltemp.add(Double.valueOf(0));
                        }
                        else {
                            soiltemp.add(soiltempjson.getDouble(i));
                        }
                    }


                    for (int i = 0; i < skintempjson.length(); i++) {

                        if(skintempjson.getString(i).equals(null)) {
                            skintemp.add(Double.valueOf(0));
                        }
                        else {
                            skintemp.add(skintempjson.getDouble(i));
                        }


                    }



                    for (int i = 0; i < dewPointjson.length(); i++) {



                        if(dewPointjson.getString(i).equals(null)) {
                            dewPointtemp.add(Long.valueOf(0));
                        }
                        else {
                            dewPointtemp.add(Math.round(dewPointjson.getDouble(i)));
                        }




                    }

                    for (int i = 0; i < soilMoisjson.length(); i++) {
                        soilMois.add(soilMoisjson.getInt(i));
                        //Log.d("soilmois", String.valueOf(soilMois.get(i)));
                    }
                    for(int i=0;i<feltTemp.length();i++)
                    {
                        apparant_temp.add(feltTemp.getDouble(i));
                    }
                    for(int i=0;i<windSpeed.length();i++)
                    {
                        Main_windSpeed.add(windSpeed.getDouble(i));
                    }
                    for(int i=0;i<windDir.length();i++)
                    {
                        Main_windDirec.add((int) Math.round(windDir.getDouble(i)));
                    }


                    for (int i = 0; i < daylistArrayval.length; i++) {
                        calculationFunction(daylistArrayval[i]);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            } else {
              //  Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {


            if ((cityloc == null) || (countryloc == null)) {
                onBackPressed();
                Toast.makeText(WeatherMainActivity.this, "Please... Try again", Toast.LENGTH_SHORT).show();
            } else {
                relativeLayout.setVisibility(View.VISIBLE);
                progressLay.setBackgroundDrawable(Drawable.createFromPath("@drawable/home_bg"));
                progressBar.setVisibility(View.INVISIBLE);
                lin1.setVisibility(View.INVISIBLE);
                super.onPostExecute(result);


               // Log.d("URL", url);
                weatherPager wp = new weatherPager(WeatherMainActivity.this, tempMainArrMean);
                viewPager.setAdapter(wp);

                df2.setMaximumFractionDigits(2);
                humiditytxt.setText(humdArr[0] + "%");
                windtxt.setText(windArr[0] + " mps");
                feelsliketxt.setText(String.valueOf(felttempList[0] + " \u00B0"));
                country.setText(cityloc + ", " + countryloc);
                datetxt.setText(dateArr[0]);

                for (int i = 1; i < dateArrNew.length; i++) {
                    HashMap<String, String> contact = new HashMap<>();
                    contact.put("day", dateArrNew[i]);
                    contact.put("text", temperatureNew.get(i));
                    oslist12.add(contact);

                }


                ListAdapter adapter = new SimpleAdapter(
                        WeatherMainActivity.this, oslist12, R.layout.weather_activity_main, new String[]{"day", "text"}, new int[]{R.id.daylist, R.id.templist});
//

                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent weatherListActivity = new Intent(getParent(), WeatherlistActivity.class).addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);//FLAG_ACTIVITY_CLEAR_TOP);

                        //  Toast.makeText(WeatherMainActivity.this, "pos"+position, Toast.LENGTH_SHORT).show();
                        int val = (newPosValue + position);
                        weatherListActivity.putExtra("position", val);
                        weatherListActivity.putExtra("lat", String.valueOf(lat));
                        weatherListActivity.putExtra("lng", String.valueOf(lng));
                        weatherListActivity.putExtra("tz", String.valueOf(tz));
                        weatherListActivity.putExtra("cityloc", String.valueOf(cityloc));
                        weatherListActivity.putExtra("counloc", String.valueOf(countryloc));
                        weatherListActivity.putExtra("tempArray", tempMainArr);
                        weatherListActivity.putExtra("frost", frostList);
                        weatherListActivity.putExtra("snowfall", snowfallList);
                        weatherListActivity.putExtra("relative", humArray);
                        weatherListActivity.putExtra("sealevel", sealevelPressureList);
                        weatherListActivity.putExtra("feltTemp", felttempList);
                        weatherListActivity.putExtra("dew", dewPointList);
                        weatherListActivity.putExtra("surfacetemp", surfacePressureList);
                        weatherListActivity.putExtra("liquidSoilMoisture", soilMoitureList);
                        weatherListActivity.putExtra("potentialEva", potentialEvapList);
                        weatherListActivity.putExtra("soiltemp", soiltempList);
                        weatherListActivity.putExtra("growingDays", growingDaysList);
                        weatherListActivity.putExtra("morvalue", morvalueList);
                        weatherListActivity.putExtra("noonvalue", noonvalueList);
                        weatherListActivity.putExtra("evevalue", evevalueList);
                        weatherListActivity.putExtra("nightvalue", nghtvalueList);
                        weatherListActivity.putExtra("date", daylistArrayval);
                        weatherListActivity.putExtra("day", datelistArrayval);
                        weatherListActivity.putExtra("picvalue", pictodelist);
                        TabGroupActivity parentActivity = (TabGroupActivity) getParent();

                        if (android.os.Build.VERSION.SDK_INT <= 10) {
                            parentActivity.startChildActivity("WeatherlistActivity", weatherListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else {
                            parentActivity.startChildActivity("WeatherlistActivity", weatherListActivity);
                        }


                    }
                });

            }
        }


        public void getListItem(int n) {



            int n1 = n - 1;
            datetxt.setText(dateArr[n1]);
            humiditytxt.setText(humdArr[n1] + "%");
            windtxt.setText(windArr[n1] + " mps");
            feelsliketxt.setText(String.valueOf( felttempList[n1] + " \u00B0"));
            for (int i = n; i < dateArrNew.length; i++) {

                HashMap<String, String> contact = new HashMap<>();
                contact.put("day", dateArrNew[i]);
                contact.put("text", temperatureNew.get(i));
                oslist.add(contact);
            }

            ListAdapter adapter = new SimpleAdapter(
                    WeatherMainActivity.this, oslist, R.layout.weather_activity_main, new String[]{"day", "text"}, new int[]{R.id.daylist, R.id.templist});

            list.setAdapter(adapter);
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WeatherMainActivity.this, BayerTurfManagementActivity.class);
        startActivity (intent);

    }

    public void calculationFunction(String newDate) {
        dewPointtemp1=new ArrayList<>();
        val2.clear();
        index.clear();
        temper1.clear();
        soiltemp1.clear();
        evapo1.clear();
        dewPointtemp1.clear();
        skintemp1.clear();
        soiltempmean = 0.0;
        dewPointtempmean = 0.0;
        evapomean = 0.0;
        rehummean = 0;
        soilMoismean = 0;
        skintempmean=0.0;
        DatePerci=new ArrayList<>();
        percipMor=new ArrayList<>();
        percipNoon=new ArrayList<>();
        perciEve=new ArrayList<>();
        perciNight=new ArrayList<>();
        valPerDate=new ArrayList<>();
        apparant_temp1=new ArrayList<>();
        temper1=new ArrayList<>();
        Main_temp1=new ArrayList<>();
        Main_windDirec1=new ArrayList<>();
        Main_windSpeed1=new ArrayList<>();
        soiltemp1=new ArrayList<>();
        val2=new ArrayList<>();
        index=new ArrayList<>();
        dewPointtemp1=new ArrayList<>();

        apparat_mean=0.0;
        Main_mean=0.0;
        Main_windDirecmean=0.0;
        Main_windSpeedmean=0.0;




        mor=0.0;
        noon=0.0;
        eve=0.0;
        night=0.0;



        DateFormat inputFormat = new SimpleDateFormat("dd/MM");
        Date datex = null;
        try {
            datex = inputFormat.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String outputDateStr = inputFormat.format(datex);
        for (int i1 = 0; i1 < val1.size(); i1++) {
//            Log.d("val1",val1.get(i1));
//            Log.d("outputDate",outputDateStr);
            if (val1.get(i1).contains(outputDateStr)) {

                val2.add(String.valueOf(val1.indexOf(val1.get(i1))));
                positions1 = val1.indexOf(val1.get(i1));

            }

        }


        for (int b = 0; b < val2.size(); b++) {
            index.add(positions1 + b);

        }
        for (int t = 0; t < index.size(); t++) {
            temper1.add(temper.get(index.get(t)));

           // Log.d("temper1", String.valueOf(temper1.get(t)));
        }

        for (int t = 0; t < index.size(); t++) {
            dewPointtemp1.add(dewPointtemp.get(index.get(t)));
        }
        for (int tempm = 0; tempm <dewPointtemp1.size(); tempm++) {
            dewPointtempmean = dewPointtempmean + dewPointtemp1.get(tempm);
        }
        dewPointtempAvg = Double.parseDouble(df2.format(dewPointtempmean / dewPointtemp1.size()));
        dewPoint.add(String.valueOf(Math.round(dewPointtempAvg)));


        for (int t = 0; t < index.size(); t++) {
            apparant_temp1.add(apparant_temp.get(index.get(t)));
        }
        for (int tempm = 0; tempm <dewPointtemp1.size(); tempm++) {
            apparat_mean = apparat_mean + apparant_temp1.get(tempm);
        }
        apparant_Avg = Double.parseDouble(df2.format(apparat_mean / apparant_temp1.size()));
        apparant_tempFinal.add(String.valueOf(Math.round(apparant_Avg)));




        for (int t = 0; t < index.size(); t++) {
            Main_windSpeed1.add(Main_windSpeed.get(index.get(t)));
        }
//        for (int tempm = 0; tempm <Main_windSpeed1.size(); tempm++) {
//            Main_windSpeedmean= Main_windSpeedmean + Main_windSpeed1.get(tempm);
//
//        }
//        Main_windSpeedAvg = (int)(Math.round(Main_windSpeedmean / Main_windSpeed1.size()));
        for (int t = 0; t < index.size(); t++) {
            Main_windDirec1.add(Main_windDirec.get(index.get(t)));
        }
//        for (int tempm = 0; tempm <Main_windDirec1.size(); tempm++) {
//            Main_windDirecmean +=  Main_windDirec1.get(tempm);
//        }
//
//        Main_windDirecAvg = Main_windDirecmean / Main_windDirec1.size();
//
//        Main_windDirecFinal.add(Main_windDirecAvg);

//            direction1 = Main_windDirecAvg;
//            if ((direction1 >= 326.25) && (direction1 <= 348.75)) {
//                direction = "NNW";
//            } else if ((direction1 >= 303.75) && (direction1 <= 326.25)) {
//                direction = "NW";
//            } else if ((direction1 >= 281.25) && (direction1 <= 303.74)) {
//                direction = "WNW";
//            } else if ((direction1 >= 258.75) && (direction1 <= 281.24)) {
//                direction = "W";
//            } else if ((direction1 >= 236.25) && (direction1 <= 258.74)) {
//                direction = "WSW";
//            } else if ((direction1 >= 213.75) && (direction1 <= 236.24)) {
//                direction = "SW";
//            } else if ((direction1 >= 191.25) && (direction1 <= 213.74)) {
//                direction = "SSW";
//            } else if ((direction1 >= 168.75) && (direction1 <= 191.24)) {
//                direction = "S";
//            } else if ((direction1 >= 146.25) && (direction1 <= 168.74)) {
//                direction = "SSE";
//            } else if ((direction1 >= 123.75) && (direction1 <= 146.24)) {
//                direction = "SE";
//            } else if ((direction1 >= 101.25) && (direction1 <= 123.74)) {
//                direction = "ESE";
//            } else if ((direction1 >= 78.75) && (direction1 <= 101.24)) {
//                direction = "E";
//            } else if ((direction1 >= 56.25) && (direction1 <= 78.74)) {
//                direction = "ENE";
//            } else if ((direction1 >= 33.75) && (direction1 <= 56.24)) {
//                direction = "NE";
//            } else if ((direction1 >= 11.25) && (direction1 <= 33.74)) {
//                direction = "NNE";
//            } else if ((direction1 >= 348.75) && (direction1 <= 360.25)) {
//                direction = "N";
//            }
//            else if ((direction1 >= 0.00 ) && (direction1 <= 11.25)) {
//                direction = "N";
//            }
//
//            String windConcat = direction + " " + String.valueOf(Main_windSpeedAvg);
//            windlist.add(windConcat);






        for (int t = 0; t < index.size(); t++) {
            skintemp1.add(skintemp.get(index.get(t)));
        }

        for (int tempm = 0; tempm < skintemp1.size(); tempm++) {
            skintempmean =  skintempmean  + skintemp1.get(tempm);
        }

        skintempAvg = (int) Math.round(skintempmean / skintemp1.size());
        surfacetempVal=skintempAvg+ "\u00B0";
        surfacepressure.add(surfacetempVal);




        for (int t = 0; t < index.size(); t++) {
            soilMois1.add(soilMois.get(index.get(t)));
            //Log.d("soil1", String.valueOf(soilMois1.get(t)));
        }
        for (int tempm = 0; tempm < soilMois1.size(); tempm++) {
            soilMoismean = soilMoismean + soilMois1.get(tempm);
           // Log.d("soilmean", String.valueOf(soilMoismean));
        }
        soilMoisAvg = Math.round(soilMoismean / soilMois1.size());


        soilMoisture.add(String.valueOf(soilMoisAvg));






        for (int t = 0; t < index.size(); t++) {
            Main_temp1.add(Main_temp.get(index.get(t)));
        }
//        for (int tempm = 0; tempm < Main_temp1.size(); tempm++) {
//            Main_mean = Main_mean + Main_temp1.get(tempm);
//
//        }
//        Main_Avg = Math.round(Main_mean / Main_temp1.size());
//
//
//        Main_tempFinal.add(String.valueOf(Main_Avg));











        for (int t = 0; t < index.size(); t++) {
            evapo1.add(evapo.get(index.get(t)));
        }
        for (int tempm = 0; tempm < evapo1.size(); tempm++) {
            evapomean = evapomean + evapo1.get(tempm);
        }
        evapoAvg = Double.parseDouble(df3.format(evapomean / evapo1.size()));
        potentialEvap.add(String.valueOf(evapoAvg));


        for (int t = 0; t < index.size(); t++) {
            soiltemp1.add(soiltemp.get(index.get(t)));
        }
        for (int tempm = 0; tempm < soiltemp1.size(); tempm++) {
            soiltempmean = soiltempmean + soiltemp1.get(tempm);
        }
        soiltempAvg = String.valueOf(Math.round(soiltempmean / soiltemp1.size()));
        soilTemperature.add(String.valueOf(soiltempAvg));

        for(int i=0;i<index.size();i++)
        {
            valPerDate.add(valPer.get(index.get(i)));
        }







        for(int i=0;i<index.size();i++)
        {

            String valTemp=valNew.get(index.get(i));
            String sep1[] = valTemp.split("T");
            String strdate = sep1[1];
            DatePerci.add(strdate);
//            Log.d("datePerci",DatePerci.get(i));
//            Log.d("valperDate", String.valueOf(valPerDate.get(i)));
//            Log.d("hour",hour);



            if(DatePerci.get(i).startsWith(hour))
            {
                int posi=DatePerci.indexOf(DatePerci.get(i));
                Main_tempFinal.add(String.valueOf(Math.round(Main_temp1.get(posi))));
                Main_windSpeedAvg= (int) Math.round(Main_windSpeed1.get(posi));
                direction1 = Double.valueOf(Main_windDirec1.get(posi));
                if ((direction1 >= 326.25) && (direction1 <= 348.75)) {
                    direction = "NNW";
                } else if ((direction1 >= 303.75) && (direction1 <= 326.25)) {
                    direction = "NW";
                } else if ((direction1 >= 281.25) && (direction1 <= 303.74)) {
                    direction = "WNW";
                } else if ((direction1 >= 258.75) && (direction1 <= 281.24)) {
                    direction = "W";
                } else if ((direction1 >= 236.25) && (direction1 <= 258.74)) {
                    direction = "WSW";
                } else if ((direction1 >= 213.75) && (direction1 <= 236.24)) {
                    direction = "SW";
                } else if ((direction1 >= 191.25) && (direction1 <= 213.74)) {
                    direction = "SSW";
                } else if ((direction1 >= 168.75) && (direction1 <= 191.24)) {
                    direction = "S";
                } else if ((direction1 >= 146.25) && (direction1 <= 168.74)) {
                    direction = "SSE";
                } else if ((direction1 >= 123.75) && (direction1 <= 146.24)) {
                    direction = "SE";
                } else if ((direction1 >= 101.25) && (direction1 <= 123.74)) {
                    direction = "ESE";
                } else if ((direction1 >= 78.75) && (direction1 <= 101.24)) {
                    direction = "E";
                } else if ((direction1 >= 56.25) && (direction1 <= 78.74)) {
                    direction = "ENE";
                } else if ((direction1 >= 33.75) && (direction1 <= 56.24)) {
                    direction = "NE";
                } else if ((direction1 >= 11.25) && (direction1 <= 33.74)) {
                    direction = "NNE";
                } else if ((direction1 >= 348.75) && (direction1 <= 360.25)) {
                    direction = "N";
                }
                else if ((direction1 >= 0.00 ) && (direction1 <= 11.25)) {
                    direction = "N";
                }

                String windConcat = direction + " " + String.valueOf(Main_windSpeedAvg);
                windlist.add(windConcat);












               // Log.d( "WIND", String.valueOf(Main_windDirec1.get(posi)));//, Toast.LENGTH_SHORT).show();
            }


             if (DatePerci.get(i).startsWith("00")) {
            int posi = DatePerci.indexOf(DatePerci.get(i));
            percipMor.add(valPerDate.get(posi));
        }

             else  if (DatePerci.get(i).startsWith("01")) {
            int posi = DatePerci.indexOf(DatePerci.get(i));
            percipMor.add(valPerDate.get(posi));
        }
        else if (DatePerci.get(i).startsWith("02")) {
            int posi = DatePerci.indexOf(DatePerci.get(i));
            percipMor.add(valPerDate.get(posi));
        }
        else if (DatePerci.get(i).startsWith("03")) {
            int posi = DatePerci.indexOf(DatePerci.get(i));
            percipMor.add(valPerDate.get(posi));
        }
        else if (DatePerci.get(i).startsWith("04")) {
            int posi = DatePerci.indexOf(DatePerci.get(i));
            percipMor.add(valPerDate.get(posi));
        }


           else if (DatePerci.get(i).startsWith("05")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipMor.add(valPerDate.get(posi));
               // Log.d("posi",String.valueOf(percipMor.get(0)));


            }
            else  if (DatePerci.get(i).startsWith("06")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipMor.add(valPerDate.get(posi));
              //  Log.d("posi",String.valueOf(percipMor.get(0)));


            }
            else if (DatePerci.get(i).startsWith("07")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipMor.add(valPerDate.get(posi));
                //Log.d("posi",String.valueOf(percipMor.get(0)));


            }
            else if (DatePerci.get(i).startsWith("08")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipMor.add(valPerDate.get(posi));
               // Log.d("posi",String.valueOf(percipMor.get(0)));


            }
            else if (DatePerci.get(i).startsWith("09")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipMor.add(valPerDate.get(posi));
               // Log.d("posi",String.valueOf(percipMor.get(0)));


            }
            else if (DatePerci.get(i).startsWith("10")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipMor.add(valPerDate.get(posi));
               // Log.d("posi",String.valueOf(percipMor.get(0)));


            }
            else if (DatePerci.get(i).startsWith("11")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipNoon.add(valPerDate.get(posi));
               // Log.d("posi",String.valueOf(percipMor.get(0)));


            }
            else if (DatePerci.get(i).startsWith("12")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipNoon.add(valPerDate.get(posi));
              //  Log.d("posi",String.valueOf(percipMor.get(0)));


            }
            else if (DatePerci.get(i).startsWith("13")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipNoon.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("14")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipNoon.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("15")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                percipNoon.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("16")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                perciEve.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("17")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                perciEve.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("18")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                perciEve.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("19")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                perciEve.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("20")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                perciNight.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("21")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                perciNight.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("22")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                perciNight.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("23")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                perciNight.add(valPerDate.get(posi));
            }
            else if (DatePerci.get(i).startsWith("24")) {
                int posi = DatePerci.indexOf(DatePerci.get(i));
                perciNight.add(valPerDate.get(posi));
            }


//
        }
        Log.d("percMor", String.valueOf(percipMor.size()));
        Log.d("percNoon", String.valueOf(percipNoon.size()));
        Log.d("percEve", String.valueOf(perciEve.size()));
        Log.d("percNght", String.valueOf(perciNight.size()));






        if(percipMor.size()==0)
        {
            morAvg=0.0;
        }
        else
        {
            for(int i=0;i<percipMor.size();i++)
            {
                mor=mor+percipMor.get(i);
            }
            morAvg=mor/percipMor.size();
        }

        morvalue.add(String.valueOf(df2.format(morAvg))+"%");

        if(percipNoon.size()==0)
        {
            noonAvg=0.0;
        }
        else
        {
            for(int i=0;i<percipNoon.size();i++)
            {
                noon=noon+percipNoon.get(i);
            }
            noonAvg=noon/percipNoon.size();
        }


        noonvalue.add(String.valueOf(df2.format(noonAvg))+"%");

        if(perciEve.size()==0)
        {
            eveAvg=0.0;
        }
        else {
            for(int i=0;i<perciEve.size();i++)
            {
                eve=eve+perciEve.get(i);
            }
            eveAvg=eve/perciEve.size();
        }


        evevalue.add(String.valueOf(df2.format(eveAvg))+"%");

        if(perciNight.size()==0)
        {
            nightAvg=0.0;
        }
        else
        {
            for(int i=0;i<perciNight.size();i++)
            {
                night=night+perciNight.get(i);
            }
            nightAvg=night/perciNight.size();
        }


        nghtvalue.add(String.valueOf(df2.format(nightAvg))+"%");




        int min=temper1.get(0);
        int max=temper1.get(0);
        for(Integer i1:temper1)
        {
            if(i1<min)
            {
                min=i1;

            }
            if(i1>max)
            {
                max=i1;

            }
        }

        int grodays = (int) Math.round(((max + min) / 2) - 10);
        growingDays.add(String.valueOf(grodays));
//        Log.d("min", String.valueOf(min));
//        Log.d("max", String.valueOf(max));
        tempMax.add(String.valueOf(max));
        String concat=String.valueOf(max)+"\u00B0"+"/"+String.valueOf(min)+"\u00B0";
        temperatureNew.add(concat);
        String frostResult;
        if(min<=0)
        {
            frostResult="Likely";
        }
        else
        {
            frostResult="Not Likely";
        }
        frostIndex.add(frostResult);
        frostList=frostIndex.toArray(new String[frostIndex.size()]);
        snowfallList=snowfall.toArray(new String[snowfall.size()]);
        relativeHumidityList=relativeHumidity.toArray(new String[relativeHumidity.size()]);
        sealevelPressureList=sealevel.toArray(new String[sealevel.size()]);
        felttempList=apparant_tempFinal.toArray(new String[apparant_tempFinal.size()]);
        dewPointList=dewPoint.toArray(new String[dewPoint.size()]);
        surfacePressureList=surfacepressure.toArray(new String[surfacepressure.size()]);
        soilMoitureList=soilMoisture.toArray(new String[soilMoisture.size()]);
        tempMainArrMean=Main_tempFinal.toArray(new String[Main_tempFinal.size()]);
        potentialEvapList=potentialEvap.toArray(new String[potentialEvap.size()]);
        soiltempList=soilTemperature.toArray(new String[soilTemperature.size()]);
        growingDaysList=growingDays.toArray(new String[growingDays.size()]);
        morvalueList=morvalue.toArray(new String[morvalue.size()]);
        noonvalueList=noonvalue.toArray(new String[noonvalue.size()]);
        evevalueList=evevalue.toArray(new String[evevalue.size()]);
        nghtvalueList=nghtvalue.toArray(new String[nghtvalue.size()]);
        tempMaxList=tempMax.toArray(new String[tempMax.size()]);
        windArr = windlist.toArray(new String[windlist.size()]);


//        for(int i=0;i<windArr.length;i++) {
//            Log.d("TEMP",tempMainArrMean[i]);
//
//        }

    }
    private static ArrayList<String> getDates(String dateString1, String dateString2)
    {
        ArrayList<String> dates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(String.valueOf(df1.format(cal1.getTime())));
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }


}

