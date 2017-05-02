package com.ctis8.atoi.touchlock;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.farbod.labelledspinner.LabelledSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Atoi on 30.03.2017.
 */
public class SearchAdvertisement extends Fragment {

    private EditText etCheckIn, etCheckOut;
    final Calendar myCalendar = Calendar.getInstance();
    ArrayList<String> cityNames = new ArrayList<>();
    String tag_string_req = "cityList";
    private LabelledSpinner citySpinner;
    private Button searchButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SearchAdvertisement newInstance() {
        SearchAdvertisement fragment = new SearchAdvertisement();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_advertisement, container, false);
        citySpinner = (LabelledSpinner) view.findViewById(R.id.city_spinner);
        LabelledSpinner outSpinner = (LabelledSpinner) view.findViewById(R.id.your_labelled_spinner2);
        etCheckIn = (EditText) view.findViewById(R.id.etCheckIn);
        etCheckOut = (EditText) view.findViewById(R.id.etCheckOut);
        //getCityList();
        citySpinner.setItemsArray(R.array.city_array);
        outSpinner.setItemsArray(R.array.numGuest_array);

        final DatePickerDialog.OnDateSetListener dateIn = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCheckIn();
            }

        };

        final DatePickerDialog.OnDateSetListener dateOut = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCheckOut();
            }

        };

        etCheckIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), dateIn, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dateOut, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;



    }

    private void getCityList() {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        JSONArray cities = jObj.getJSONArray("cities");

                        // Inserting row in users table
                    for (int i = 0; i < cities.length(); i++) {
                        JSONObject jsonObject = cities.getJSONObject(i);
                        String city = jsonObject.optString("city").toString();
                        cityNames.add(city);
                    }

                    //citySpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, cityNames));

                    } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void updateCheckIn() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etCheckIn.setText("Check-In Date:    " + sdf.format(myCalendar.getTime()));
    }


    private void updateCheckOut() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etCheckOut.setText("Check-Out Date:    " + sdf.format(myCalendar.getTime()));
    }
}
