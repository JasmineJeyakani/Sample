package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.devsmart.android.ui.HorizontalListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class WeatherlistActivity extends Activity {
    NonScrollListView listweather;
    TextView country;
    TextView degree;
    TextView morvalue, noonvalue, evevalue, nghtvalue;
    HorizontalListView horidate;
    ImageView closeBtn, imgLogo;
    List<String> val = new ArrayList<String>();
    List<Integer> index = new ArrayList<Integer>();
    int pos, pos1;
    ArrayList<HashMap<String, String>> oslist;
    static final ArrayList<HashMap<String, String>> listweatheritem =
            new ArrayList<HashMap<String, String>>();
    ViewPager viewPager;
    LinearLayout progressLay;
    RelativeLayout relativeLayout;
    String[] description;
    TextView desc;
    String lat;
    String lng;
    String cityloc;
    String tz;
    String countryloc;
    DecimalFormat df = new DecimalFormat("0.##");
    String temperatureMax[];
    String daylis[];
    String datelis[];
    String frostlist[];
    String snowfallList[];
    String surfacelist[];
    String humiditylist[];
    String apparntlist[];
    String dewpointlist[];
    String surfacetemplist[];
    String liquidsoilmoisList[];
    String potenevaplist[];
    String soiltemplist[];
    String growingdayslist[];
    String morval[];
    String noonval[];
    String eveval[];
    String nghtval[];
    String[] pictodelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weatherlist);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            pos = b.getInt("position");
            Log.d("pos", String.valueOf(pos));
            lat = b.getString("lat");
            lng = b.getString("lng");
            tz = b.getString("tz");
            cityloc = b.getString("cityloc");
            countryloc = b.getString("counloc");
            temperatureMax = b.getStringArray("tempArray");
            daylis = b.getStringArray("day");
            datelis = b.getStringArray("date");
            frostlist = b.getStringArray("frost");
            snowfallList = b.getStringArray("snowfall");
            surfacelist = b.getStringArray("sealevel");
            humiditylist = b.getStringArray("relative");
            apparntlist = b.getStringArray("feltTemp");
            dewpointlist = b.getStringArray("dew");
            surfacetemplist = b.getStringArray("surfacetemp");
            liquidsoilmoisList = b.getStringArray("liquidSoilMoisture");
            potenevaplist = b.getStringArray("potentialEva");
            soiltemplist = b.getStringArray("soiltemp");
            growingdayslist = b.getStringArray("growingDays");
            morval = b.getStringArray("morvalue");
            noonval = b.getStringArray("noonvalue");
            eveval = b.getStringArray("evevalue");
            nghtval = b.getStringArray("nightvalue");
            pictodelist = b.getStringArray("picvalue");

        }


        progressLay = (LinearLayout) findViewById(R.id.prograssLayout);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        listweather = (NonScrollListView) findViewById(R.id.weatherList2);
        horidate = (HorizontalListView) findViewById(R.id.horilistview);
        country = (TextView) findViewById(R.id.countrytxtbox);
        degree = (TextView) findViewById(R.id.celciustxt);
        morvalue = (TextView) findViewById(R.id.morval);
        noonvalue = (TextView) findViewById(R.id.noonval);
        evevalue = (TextView) findViewById(R.id.eveval);
        nghtvalue = (TextView) findViewById(R.id.nghtval);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        viewPager = (ViewPager) findViewById(R.id.weatherlayout);
        desc = (TextView) findViewById(R.id.texMsg);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WeatherlistActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);

            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int newPos = 1;

            @Override

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
            }


            @Override
            public void onPageSelected(int position) {
                calculationFunction(position);
              if(position==(temperatureMax.length-4))
              {

                  horidate.scrollTo(70);
              }
               else if(position==(temperatureMax.length-3))
                {
                    horidate.scrollTo(70*2);
                }
              else if(position==(temperatureMax.length-2))
              {
                  horidate.scrollTo(70*3);
              }
                else if(position== (temperatureMax.length-1))
              {
                  horidate.scrollTo(70*4);
              }


            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        closeBtn = (ImageView) findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        oslist = new ArrayList<HashMap<String, String>>();
        new LoadAllArticles().execute();
    }


    class LoadAllArticles extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            description = new String[]{"Sunny, cloudless sky", "Sunny and few clouds", "Partly cloudy", "Overcast", "Fog", "Overcast with rain"
                    , "Mixed with showers", "Showers,thunderstorms likely", "Overcast with snow", "Mixed with snow showers",
                    "Mostly cloudy with a mixture of snow and rain", "Overcast with light rain", "Overcast with light snow", "Mostly cloudy with rain",
                    "Mostly cloudy with snow", "Mostly cloudy with lightrain", "Mostly cloudy with lightsnow"};


            for (int i = 0; i < datelis.length; i++) {

                HashMap<String, String> contact = new HashMap<>();
                contact.put("date", datelis[i]);
                contact.put("day", daylis[i]);
                oslist.add(contact);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            relativeLayout.setVisibility(View.VISIBLE);
            country.setText(cityloc + ", " + countryloc);
            weatherPager wp = new weatherPager(WeatherlistActivity.this, temperatureMax);
            viewPager.setAdapter(wp);
            viewPager.setCurrentItem(pos);
            calculationFunction(pos);
            if(pos==(temperatureMax.length-4))
            {
                horidate.scrollTo(70);
            }
            else if(pos==(temperatureMax.length-3))
            {
                horidate.scrollTo(70*2);
            }
            else if(pos==(temperatureMax.length-2))
            {
                horidate.scrollTo(70*3);
            }
            else if(pos== (temperatureMax.length-1))
            {
                horidate.scrollTo(70*4);
            }
            horidate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    pos1 = position;
                    int pic1 = Integer.parseInt(pictodelist[position]);
                    for (int i = 1; i <= 17; i++) {
                        if (pic1 == i)

                        {
                            desc.setText(description[i - 1]);
                        }
                    }
                    setItemNormal();
                    View rowView = view;
                    setItemSelected(rowView);

                }

                public void setItemSelected(View view) {
                    View rowView = view;
                    TextView tv = (TextView) rowView.findViewById(R.id.day2);
                    TextView tv1 = (TextView) rowView.findViewById(R.id.date2);
                    tv.setTypeface(null, Typeface.BOLD);
                    tv1.setTypeface(null, Typeface.BOLD);
                    // degree.setText(""+temperatureMax[pos1]);
                    viewPager.setCurrentItem(pos1);


                }

                public void setItemNormal() {

                    for (int i = 0; i < horidate.getChildCount(); i++) {
                        View v = horidate.getChildAt(i);
                        TextView txtview = ((TextView) v.findViewById(R.id.day2));
                        TextView txtview1 = ((TextView) v.findViewById(R.id.date2));
                        txtview.setTypeface(null, Typeface.NORMAL);
                        txtview1.setTypeface(null, Typeface.NORMAL);
                    }
                }


            });

        }
    }


    class weatherPager extends PagerAdapter {
        Context context;
        LayoutInflater inflater;
        String[] tempmeanArrSamp;

        public weatherPager(Context context, String[] tempmeanArrSamp) {
            this.context = context;
            this.tempmeanArrSamp = tempmeanArrSamp;
        }

        @Override
        public int getCount() {
            return tempmeanArrSamp.length;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            final TextView textView;
            final ImageView imageView;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflaterView = inflater.inflate(R.layout.temp_lay, container, false);
            textView = (TextView) inflaterView.findViewById(R.id.textView3);
            Typeface birdman = Typeface.createFromAsset(getAssets(), "fonts/helvetica_thin.ttf");
            textView.setTypeface(birdman);
            textView.setText("" + String.valueOf(temperatureMax[position]));
            ((ViewPager) container).addView(inflaterView);
            return inflaterView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflaterView = inflater.inflate(R.layout.temp_lay, container, false);
            ((ViewPager) container).removeView(inflaterView);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void calculationFunction(final int posi) {

        listweatheritem.clear();

        final SimpleAdapter adapter1 = new SimpleAdapter(WeatherlistActivity.this, oslist, R.layout.daydate, new String[]{"day", "date"}, new int[]{R.id.day2, R.id.date2}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);


                Log.d("posi", String.valueOf(posi));
                // pos=posi;
                if (posi == 6) {

                    viewPager.setCurrentItem(6);
                } else {
                    viewPager.setCurrentItem(posi);
                }
                int picture = Integer.parseInt(pictodelist[posi]);
                for (int i = 1; i <= 17; i++) {
                    if (picture == i)

                    {
                        desc.setText(description[i - 1]);
                    }
                }

                TextView tv = (TextView) view.findViewById(R.id.day2);
                TextView tv1 = (TextView) view.findViewById(R.id.date2);
                if (position == posi) {
                    tv.setTypeface(null, Typeface.BOLD);
                    tv1.setTypeface(null, Typeface.BOLD);

                } else {

                    tv.setTypeface(null, Typeface.NORMAL);
                    tv1.setTypeface(null, Typeface.NORMAL);
                }

                return view;
            }
        };


        horidate.setAdapter(adapter1);


        HashMap map = new HashMap();
        map.put("description", "Frost Index Tonight");
        map.put("value", frostlist[posi]);

        listweatheritem.add(map);
        map = new HashMap();
        map.put("description", "Snowfall");
        map.put("value", snowfallList[posi]);
        listweatheritem.add(map);
        map = new HashMap();
        map.put("description", "Surface Pressure");
        map.put("value", df.format(Double.parseDouble(surfacelist[posi])) + " kPa");
        listweatheritem.add(map);
        map = new HashMap();
        map.put("description", "Relative Humidity");
        map.put("value", humiditylist[posi] + "%");
        listweatheritem.add(map);
        map = new HashMap();
        map.put("description", "Apparent Temperature");
        map.put("value", apparntlist[posi] + "\u00B0");
        listweatheritem.add(map);
        map = new HashMap();
        map.put("description", "Surface Dew Point Temperature");
        map.put("value", dewpointlist[posi] + "\u00B0");
        listweatheritem.add(map);
        map = new HashMap();
        map.put("description", "Surface Temperature");
        map.put("value", surfacetemplist[posi]);
        listweatheritem.add(map);
        map = new HashMap();
        map.put("description", "Liquid Soil Moisture(0-10 cm)");
        map.put("value", liquidsoilmoisList[posi]);
        listweatheritem.add(map);
        map = new HashMap();
        map.put("description", "Potential Evapotranspiration");
        map.put("value", potenevaplist[posi] + " mm/hr");
        listweatheritem.add(map);
        map = new HashMap();
        map.put("description", "Soil Temperature(0-10cm)");
        map.put("value", soiltemplist[posi] + "\u00B0");
        listweatheritem.add(map);

        map = new HashMap();
        map.put("description", "Growing Green Days");
        map.put("value", growingdayslist[posi]);
        listweatheritem.add(map);
        morvalue.setText(morval[posi]);
        noonvalue.setText(noonval[posi]);
        evevalue.setText(eveval[posi]);
        nghtvalue.setText(nghtval[posi]);
        final SimpleAdapter adapter = new SimpleAdapter(WeatherlistActivity.this, listweatheritem, R.layout.weather_list_item, new String[]{"description", "value"}, new int[]{R.id.daylist, R.id.templist});
        listweather.setAdapter(adapter);


    }


}
