package com.ctis8.atoi.touchlock;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.farbod.labelledspinner.LabelledSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Atoi on 30.03.2017.
 */
public class SearchAdvertisement extends Fragment {

    private EditText etCheckIn, etCheckOut;
    final Calendar myCalendar = Calendar.getInstance();

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
        LabelledSpinner yourSpinner = (LabelledSpinner) view.findViewById(R.id.your_labelled_spinner);
        LabelledSpinner outSpinner = (LabelledSpinner) view.findViewById(R.id.your_labelled_spinner2);
        etCheckIn = (EditText) view.findViewById(R.id.etCheckIn);
        etCheckOut = (EditText) view.findViewById(R.id.etCheckOut);
        yourSpinner.setItemsArray(R.array.city_array);
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
