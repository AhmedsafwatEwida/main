package pioneers.safwat.mazad.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pioneers.safwat.mazad.R;

/**
 * Created by safwa on 12/7/2017.
 */

public class Bidone2 extends AppCompatActivity {

    List<DataAdapter2> ListOfdataAdapter;

    RecyclerView recyclerView;
    ProgressDialog pDialog;
     String HTTP_JSON_URL = "http://192.168.100.8/mazad/item_retreive.php";
   //String HTTP_JSON_URL = "http://192.168.43.215/mazad/item_retreive.php";
    // String HTTP_JSON_URL = "http://192.168.43.215/android_login_upload_images/image_retreive2.php";
    HashMap<String,String> ResultHash = new HashMap<>();
    String Image_Auction_JSON = "auction";
    String Bid_Winner_JSON1 = "bidwin1";
    String Bid_Winner_JSON2 = "bidwin2";
    String Bid_Winner_JSON3 = "bidwin3";
    String Bid_Winner_JSON4 = "bidwin4";
    String Image_Price_JSON1 = "price1";
    String Image_Price_JSON2 = "price2";
    String Image_Price_JSON3 = "price3";
    String Image_Price_JSON4 = "price4";
    String Image_NoBid_JSON = "nobid";
    String Bid_Date_JSON = "biddate";
    String Bid_Winner_JSON = "bidwin";
    String Bid_Status_JSON = "status";
    String Image_Price_JSON = "price";
    String Image_Name_JSON = "email";
    String Image_ID_JSON = "id";
    String Image_Descr_JSON = "descr";
    String ParseResult ;
    String Image_URL_JSON = "image_path1";
    String Image_URL_JSON1 = "image_path2";
    String Image_URL_JSON2 = "image_path3";
    String TempItem;
    String NameHolder,pathholder,Priceholder,AuctionHolder,BidDateHolder,BidWinHolder1,BidWinHolder2,BidWinHolder3,
            BidWinHolder4,BidWinHolder,Priceholder1,Priceholder2,Priceholder3,Priceholder4,BidNumberHolder,BidStatusHolder;
    String FinalJSonObject ;
    JsonArrayRequest RequestOfJSonArray ;
    HttpParse httpParse = new HttpParse();
    RequestQueue requestQueue ;

    View view ;

    int RecyclerViewItemPosition ;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> ImageTitleNameArrayListForClick;
    Button UpdateButtonn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.retreive_one2);
        TempItem = getIntent().getStringExtra("ListViewValue");
        ImageTitleNameArrayListForClick = new ArrayList<>();
        UpdateButtonn=(Button)findViewById(R.id.update);
        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        HttpWebCall(TempItem);

        UpdateButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Bidone2.this,BidActivity.class);

                // Sending Student Id, Name, Number and Class to next UpdateActivity.
                intent.putExtra("Id", TempItem);
                intent.putExtra("email", NameHolder);
                intent.putExtra("price", Priceholder);
                intent.putExtra("price1", Priceholder1);
                intent.putExtra("price2", Priceholder2);
                intent.putExtra("price3", Priceholder3);
                intent.putExtra("price4", Priceholder4);
                intent.putExtra("biddate", BidDateHolder);
                intent.putExtra("bidwin", BidWinHolder);
                intent.putExtra("bidwin1", BidWinHolder1);
                intent.putExtra("bidwin2", BidWinHolder2);
                intent.putExtra("bidwin3", BidWinHolder3);
                intent.putExtra("bidwin4", BidWinHolder4);
                intent.putExtra("auction", AuctionHolder);
                intent.putExtra("nobid", BidNumberHolder);
                intent.putExtra("status", BidStatusHolder);
                intent.putExtra("image_path1", pathholder);

                startActivity(intent);

                // Finishing current activity after opening next activity.
                finish();

            }
        });
        //  JSON_HTTP_CALL();
        // Implementing Click Listener on RecyclerView.

    }

    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(Bidone2.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new GetHttpResponse(Bidone2.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("ImageID",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, AppConfig.HTTP_JSON_URL_item);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }


    // Parsing Complete JSON Object.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);
                        JSONObject jsonObject;
                        DataAdapter2 GetDataAdapter2 = new DataAdapter2();
                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);
                            //     json = array.getJSONObject(i);
                            // if (json.getString((Image_Name_JSON)).equalsIgnoreCase("ahmed"))
                           /* Double maxi1,maxi2,maxi3,maxi4;
                            maxi1=Math.max(Double.parseDouble(jsonObject.getString(Image_Price_JSON)), Double.parseDouble(jsonObject.getString(Image_Price_JSON1)));
                            maxi2=Math.max(Double.parseDouble(jsonObject.getString(Image_Price_JSON2)), Double.parseDouble(jsonObject.getString(Image_Price_JSON1)));
                            maxi3=Math.max(maxi1,maxi2);
                            maxi4=Math.max(Double.parseDouble(jsonObject.getString(Image_Price_JSON2)),maxi3);*/

                            GetDataAdapter2.setImageTitle("Name:"+jsonObject.getString(Image_Name_JSON));
                         //   GetDataAdapter2.setImagePrice("Price:"+jsonObject.getString(Image_Price_JSON));
                          /*  GetDataAdapter2.setImagePrice1("Price1:"+jsonObject.getString(Image_Price_JSON1));
                            GetDataAdapter2.setImagePrice2("Price2:"+jsonObject.getString(Image_Price_JSON2));
                            GetDataAdapter2.setImagePrice3("Price3:"+jsonObject.getString(Image_Price_JSON3));
                            GetDataAdapter2.setImagePrice4("Price4:"+jsonObject.getString(Image_Price_JSON4));*/
                            GetDataAdapter2.setBidDate("Bid:"+jsonObject.getString(Bid_Date_JSON));
                            GetDataAdapter2.setItemStatus("Status:"+jsonObject.getString(Bid_Status_JSON));
                            GetDataAdapter2.setBidWin("Win1:"+jsonObject.getString(Bid_Winner_JSON));
                            GetDataAdapter2.setBidWin1("Win2:"+jsonObject.getString(Bid_Winner_JSON1));
                            GetDataAdapter2.setBidWin2("Win3:"+jsonObject.getString(Bid_Winner_JSON2));
                            GetDataAdapter2.setBidWin3("Win4:"+jsonObject.getString(Bid_Winner_JSON3));
                            GetDataAdapter2.setBidWin4("Win5:"+jsonObject.getString(Bid_Winner_JSON4));
                            GetDataAdapter2.setItemAuction("Auction:"+jsonObject.getString(Image_Auction_JSON));
                            GetDataAdapter2.setImageID("Item Code#:"+jsonObject.getString(Image_ID_JSON));
                            GetDataAdapter2.setItemDescr("Description:"+jsonObject.getString(Image_Descr_JSON));
                            GetDataAdapter2.setNoBid("NO.of Bids:"+jsonObject.getString(Image_NoBid_JSON));
                            if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==1)
                                GetDataAdapter2.setImagePrice("Price1:"+jsonObject.getString(Image_Price_JSON));
                            else if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==2)
                                GetDataAdapter2.setImagePrice("Price2:"+jsonObject.getString(Image_Price_JSON1));
                            else if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==3)
                                GetDataAdapter2.setImagePrice("Price3:"+jsonObject.getString(Image_Price_JSON2));
                            else if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==4)
                                GetDataAdapter2.setImagePrice("Price4:"+jsonObject.getString(Image_Price_JSON3));
                            else if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==5)
                                GetDataAdapter2.setImagePrice("Price:"+jsonObject.getString(Image_Price_JSON4));
                            // Adding image title name in array to display on RecyclerView click event.
                            ImageTitleNameArrayListForClick.add(jsonObject.getString(Image_Name_JSON));
                            //ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
                            GetDataAdapter2.setImageUrl(jsonObject.getString(Image_URL_JSON));
                            GetDataAdapter2.setImageUrl1(jsonObject.getString(Image_URL_JSON1));
                            GetDataAdapter2.setImageUrl2(jsonObject.getString(Image_URL_JSON2));
                            // Storing Student Name, Phone Number, Class into Variables.
                            NameHolder = jsonObject.getString("email").toString() ;
                            Priceholder = jsonObject.getString("price").toString() ;
                            Priceholder1 = jsonObject.getString("price1").toString() ;
                            Priceholder2 = jsonObject.getString("price2").toString() ;
                            Priceholder3 = jsonObject.getString("price3").toString() ;
                            Priceholder4 = jsonObject.getString("price4").toString() ;
                            BidDateHolder = jsonObject.getString("biddate").toString() ;
                            BidNumberHolder = jsonObject.getString("nobid").toString() ;
                            BidWinHolder = jsonObject.getString("bidwin").toString() ;
                            BidWinHolder1 = jsonObject.getString("bidwin1").toString() ;
                            BidWinHolder2 = jsonObject.getString("bidwin2").toString() ;
                            BidWinHolder3 = jsonObject.getString("bidwin3").toString() ;
                            BidWinHolder4 = jsonObject.getString("bidwin4").toString() ;
                            AuctionHolder = jsonObject.getString("auction").toString() ;
                            BidStatusHolder = jsonObject.getString("status").toString() ;
                            pathholder = jsonObject.getString("image_path1").toString() ;
                            ListOfdataAdapter.add(GetDataAdapter2);
                        }



                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                recyclerViewadapter = new RecyclerViewAdapter2itemusers(ListOfdataAdapter, context);
                recyclerView.setAdapter(recyclerViewadapter);

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {

            // Setting Student Name, Phone Number, Class into TextView after done all process .
            //  NAME.setText(NameHolder);

            recyclerView.setAdapter(recyclerViewadapter);
        }
    }


}
