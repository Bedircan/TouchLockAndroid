package com.ctis8.atoi.touchlock;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Atoi on 30.03.2017.
 */
public class AdvertisementList extends Fragment {

    private ListView list;
    private static final String TAG = AdvertisementList.class.getSimpleName();
    private ProgressDialog pDialog;
    private ImageView houseIcon;
    private ArrayList<Advertisement> advertisementList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static AdvertisementList newInstance() {
        AdvertisementList fragment = new AdvertisementList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advertisement_list, container, false);
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        getAdvertisementList();
        CustomListAdapter adapter=new CustomListAdapter(getActivity(), advertisementList);
        list = (ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });
        return view;
    }

    private void getAdvertisementList() {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADVERTISELIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);

                        JSONObject house = jObj.getJSONObject("house");
                        String title = house.getString("title");

                        String imageUrl = house.getString("image");


                        java.net.URL url = null;
                        try {
                            url = new java.net.URL(imageUrl);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        HttpURLConnection connection = (HttpURLConnection) url
                                .openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        Bitmap houseBitmap = BitmapFactory.decodeStream(input);

                        Bitmap resizedHouseBitmap = Bitmap.createScaledBitmap(
                                houseBitmap, 30, 30, false);

                        String address = house
                                .getString("address");

                        String description = house.getString("description");
                        Advertisement advertisement = new Advertisement(title, resizedHouseBitmap, address, description);
                        refreshListView(advertisement);
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = getContext().getSharedPreferences("MyApp", Context.MODE_PRIVATE);
                String id = prefs.getString("username", "UNKNOWN");

                params.put("id", id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "advertisementList");
    }

    public void messageDialog(Advertisement advertisement) {

        final Dialog myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.detail_advertisement);
        myDialog.setTitle("My Advertisement");
        myDialog.setCancelable(true);

        TextView tvCity = (TextView) myDialog.findViewById(R.id.tvCity);
        tvCity.setText(advertisement.getCity());

        //TextView tvPet = (TextView) myDialog.findViewById(R.id.tvPet);
        //tvPet.setText(advertisement.isPetAllowed()+"");

        TextView tvType = (TextView) myDialog.findViewById(R.id.tvType);
        tvType.setText(advertisement.getType());

        TextView tvGuest = (TextView) myDialog.findViewById(R.id.tvNumGuest);
        tvType.setText(advertisement.getNumGuest()+"");

        TextView tvDesc = (TextView) myDialog.findViewById(R.id.tvDesc);
        tvDesc.setText(advertisement.getDescription());

        TextView tvDate = (TextView) myDialog.findViewById(R.id.tvDate);
        tvDate.setText(advertisement.getCheckInDate()+" --- "+advertisement.getCheckOutDate());

        myDialog.show();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void refreshListView(Advertisement advertisement)
    {
        advertisementList.add(advertisement);
        CustomListAdapter adapter=new CustomListAdapter(getActivity(), advertisementList);
        list.setAdapter(adapter);
    }
}
