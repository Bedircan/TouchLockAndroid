package com.ctis8.atoi.touchlock;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.farbod.labelledspinner.LabelledSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Atoi on 30.03.2017.
 */
public class GiveAdvertisement extends Fragment {

    private String city, guestNumber, type, petAllowed;
    private EditText etCheckIn, etCheckOut, etDescription;
    final Calendar myCalendar = Calendar.getInstance();
    private ProgressDialog pDialog;
    private static final String TAG = GiveAdvertisement.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static GiveAdvertisement newInstance() {
        GiveAdvertisement fragment = new GiveAdvertisement();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.give_advertisement, container, false);
        final LabelledSpinner citySpinner = (LabelledSpinner) view.findViewById(R.id.city_spinner);
        LabelledSpinner guestSpinner = (LabelledSpinner) view.findViewById(R.id.guest_spinner);
        LabelledSpinner propertySpinner = (LabelledSpinner) view.findViewById(R.id.property_spinner);
        citySpinner.setItemsArray(R.array.city_array);
        guestSpinner.setItemsArray(R.array.numGuest_array);
        propertySpinner.setItemsArray(R.array.property_array);
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        etCheckIn = (EditText) view.findViewById(R.id.etCheckIn);
        etCheckOut = (EditText) view.findViewById(R.id.etCheckOut);
        etDescription = (EditText)view.findViewById(R.id.etDescription);
        Button giveAdvertisementButton = (Button)view.findViewById(R.id.btnAdvertise);


        RadioGroup rb = (RadioGroup) view.findViewById(R.id.radio_group);
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioNo:
                        petAllowed = "NO";
                        break;
                    case R.id.radioYes:
                        petAllowed = "YES";
                        break;
                }
            }

        });

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

        citySpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
               city  = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });

        propertySpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                type  = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });

        guestSpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                guestNumber = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });

        giveAdvertisementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkInDateString = etCheckIn.getText().toString();
                String[] tempArray = checkInDateString.split(":");
                String checkInDate = tempArray[1].trim();
                String checkOutDateString = etCheckOut.getText().toString();
                tempArray = null;
                tempArray = checkOutDateString.split(":");
                String checkoutDate = tempArray[1].trim();
                String description = etDescription.getText().toString();

                if(!checkInDate.isEmpty() && !checkoutDate.isEmpty() && !description.isEmpty()) {
                    giveAdvertisement(checkInDate, checkoutDate, description);
                }
                else
                {
                    Toast.makeText(getContext(),
                            "Please fill out every field!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
        return view;
    }

    private void giveAdvertisement(final String checkInDate, final String checkoutDate, final String description) {
        String tag_string_req = "advertise";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADVERTISE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Advertise Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getContext(), "Property successfully registered!", Toast.LENGTH_LONG).show();

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Advertisement Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("city", city);
                params.put("checkindate", checkInDate);
                params.put("checkoutdate", checkoutDate);
                params.put("numGuest", guestNumber);
                params.put("type", type);
                params.put("isPetAllowed", petAllowed);
                params.put("description", description);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        resetLayout();
    }

    private void resetLayout() {

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
