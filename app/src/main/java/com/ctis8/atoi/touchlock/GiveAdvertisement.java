package com.ctis8.atoi.touchlock;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
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
import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.ui.PageFragmentCallbacks;
import com.tech.freak.wizardpager.ui.ReviewFragment;
import com.tech.freak.wizardpager.ui.StepPagerStrip;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Atoi on 30.03.2017.
 */
public class GiveAdvertisement extends Fragment {

    private String city, guestNumber, type, petAllowed, smokeAllowed, basicNeed,
        televisionAvailable, wifiAvailable, heatingAvailable, acAvailable, fireExtuingisher,
        firstAid, fireDetector, NFCProtection;
    private EditText etCheckIn, etCheckOut, etDescription;
    final Calendar myCalendar = Calendar.getInstance();
    private ProgressDialog pDialog;
    private static final String TAG = GiveAdvertisement.class.getSimpleName();

    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;

    private boolean mEditingAfterReview;

    private AbstractWizardModel mWizardModel = new SandwichWizardModel(getContext());

    private boolean mConsumePageSelectedEvent;

    private Button submitButton;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;


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

        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        submitButton = (Button) view.findViewById(R.id.submit_button);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveAdvertisement(etCheckIn.getText().toString(), etCheckOut.getText().toString(), etDescription.getText().toString());
            }
        });

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

        RadioGroup rb = (RadioGroup) view.findViewById(R.id.radio_pet);
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

        RadioGroup rg = (RadioGroup) view.findViewById(R.id.radio_smoke);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioSmokeYes:
                        smokeAllowed = "YES";
                        break;
                    case R.id.radioSmokeNo:
                        smokeAllowed = "NO";
                }
            }
        });

        RadioGroup rg2 = (RadioGroup)view.findViewById(R.id.radio_Wifi);
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioWifiYes:
                        wifiAvailable = "YES";
                        break;
                    case R.id.radioWifiNo:
                        wifiAvailable = "NO";
                        break;
                }
            }
        });

        RadioGroup rg3 = (RadioGroup) view.findViewById(R.id.radio_Basic);
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBasicYes:
                        basicNeed = "YES";
                        break;
                    case R.id.radioBasicNo:
                        basicNeed = "NO";
                        break;
                }
            }
        });

        RadioGroup rg4 = (RadioGroup) view.findViewById(R.id.radio_tv);
        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioTvYes:
                        televisionAvailable = "YES";
                        break;
                    case R.id.radioTvNo:
                        televisionAvailable = "NO";
                        break;
                }
            }
        });

        RadioGroup rg5 = (RadioGroup) view.findViewById(R.id.radio_heating);
        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioHeatingYes:
                        heatingAvailable = "YES";
                        break;
                    case R.id.radioHeatingNo:
                        heatingAvailable = "NO";
                        break;
                }
            }
        });

        RadioGroup rg6 = (RadioGroup) view.findViewById(R.id.radio_ac);
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioACYes:
                        acAvailable = "YES";
                        break;
                    case R.id.radioACNo:
                        acAvailable = "NO";
                        break;
                }
            }
        });

        RadioGroup rg7 = (RadioGroup) view.findViewById(R.id.radio_fire);
        rg7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioFireYes:
                        fireDetector = "YES";
                        break;
                    case R.id.radioFireNo:
                        fireDetector = "NO";
                        break;
                }
            }
        });

        RadioGroup rg8 = (RadioGroup) view.findViewById(R.id.radio_firstAid);
        rg8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioAidYes:
                        firstAid = "YES";
                        break;
                    case R.id.radioAidNo:
                        firstAid = "NO";
                        break;
                }
            }
        });


        RadioGroup rg9 = (RadioGroup) view.findViewById(R.id.radio_fireExt);
        rg9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioExtYes:
                        fireExtuingisher = "YES";
                        break;
                    case R.id.radioExtNo:
                        fireExtuingisher = "NO";
                        break;
                }
            }
        });


        RadioGroup rg10 = (RadioGroup) view.findViewById(R.id.radio_NFC);
        rg10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioNFCYes:
                        NFCProtection = "YES";
                        break;
                    case R.id.radioExtNo:
                        NFCProtection = "NO";
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

      /*  giveAdvertisementButton.setOnClickListener(new View.OnClickListener() {
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
        });*/

        /*
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    DialogFragment dg = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage("Confirm")
                                    .setPositiveButton(
                                            "Submit",
                                            null)
                                    .setNegativeButton(android.R.string.cancel,
                                            null).create();
                        }
                    };
                    dg.show(getFragmentManager(), "place_order_dialog");
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });*/


        //onPageTreeChanged();
        //updateBottomBar();

        return view;
    }

    private void giveAdvertisement(final String checkInDate, final String checkoutDate, final String description) {
        String tag_string_req = "advertise";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("city", city);
                params.put("checkindate", checkInDate);
                params.put("checkoutdate", checkoutDate);
                params.put("numGuest", guestNumber);
                params.put("type", type);
                params.put("s_smoke", smokeAllowed);
                params.put("s_pet", petAllowed);
                params.put("s_wifi", wifiAvailable);
                params.put("s_basic", basicNeed);
                params.put("s_tv", televisionAvailable);
                params.put("s_heating", heatingAvailable);
                params.put("s_cooling", acAvailable);
                params.put("s_Firedetector", fireDetector);
                params.put("s_aidkit", firstAid);
                params.put("s_extinguisher", fireExtuingisher);
                params.put("s_nfc", NFCProtection);
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

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO: be smarter about this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position,
                                   Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            if (mCurrentPageSequence == null) {
                return 0;
            }
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }
}
