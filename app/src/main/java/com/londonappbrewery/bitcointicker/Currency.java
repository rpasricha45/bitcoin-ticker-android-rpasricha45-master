package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

public class Currency {
    // Member Varibles
   private JSONObject data;
   private String price;



    public Currency (JSONObject response){
        data = response;
        try {
            price =  data.getString("price");


        }

        catch ( JSONException e){
            e.printStackTrace();


        }



    }




    public String getPrice() {
        return price;
    }


}
