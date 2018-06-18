package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpClientConnection;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {




    // TODO Create a Base Country
    private String Base_Country = "USD";

    // TODO CREATE BASE URL
    private  String BASE_URL = "https://apiv2.bitcoinaverage.com/convert/global?from=BTC&to=";

    //https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCUSD
    private  final String TAG = "Bitcoin";




    // Member Variables:
    TextView mPriceTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Array of Countries

        final String [] COUNTRIES = getResources().getStringArray(R.array.currency_array);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);

        letsDoSomeNetworking(BASE_URL+Base_Country);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            // this is when the item is selected in the spinner
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    letsDoSomeNetworking(BASE_URL+COUNTRIES[position] );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String Url) {

         AsyncHttpClient client = new AsyncHttpClient();
       client.get(Url,  new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d(TAG, "JSON: " + response.toString());
                Currency bitcoin = new Currency(response);
                mPriceTextView.setText(bitcoin.getPrice());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d(TAG, "Request fail! Status code: " + statusCode);
                Log.d(TAG, "Fail response: " + response);
                Log.e("ERROR", e.toString());

            }
        });


    }



}
