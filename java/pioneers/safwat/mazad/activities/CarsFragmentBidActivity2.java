package pioneers.safwat.mazad.activities;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pioneers.safwat.mazad.R;
import pioneers.safwat.mazad.fragment.HomeFragment;

/**
 * Created by safwa on 12/11/2017.
 */

public class CarsFragmentBidActivity2 extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Dialog dialog,dialog2;
    ProgressDialog pDialog;
    List<DataAdapter> ListOfdataAdapter;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    ProgressDialog progressDialog;
    FloatingActionButton updatefast;
    // RecyclerView recyclerView;

    String HTTP_JSON_URL = "http://192.168.100.8/mazad/AuctionListprop.php";
    // String HTTP_JSON_URL = "http://192.168.43.215/mazad/AuctionListprop.php";
    //  String HTTP_JSON_URL = "http://192.168.43.215/mazad/AuctionList.php";
    // String HTTP_JSON_URL = "http://192.168.168.43/mazad/ItemJsonData.php";
    //  String HTTP_JSON_URL = "http://192.168.43.215/android_login_upload_images/ImageJsonData2.php";
    String biddateold,bidhouenew,biddatenew;
    String User_Downpay_JSON = "downpayment";
    String User_Downpay_JSON2 = "downpaymentcar";
    String Image_Email_JSON = "email";
    String Image_Auction_JSON = "auction";
    String Bid_Date_JSON = "biddate";
    String Bid_Hour_JSON = "bidhour";
    String Bid_Type_JSON = "type";
    String Bid_Winner_JSON = "bidwin";
    String Bid_Winner_JSON1 = "bidwin1";
    String Bid_Winner_JSON2 = "bidwin2";
    String Bid_Winner_JSON3 = "bidwin3";
    String Bid_Winner_JSON4 = "bidwin4";
    String Bid_Status_JSON = "status";
    String Image_Price_JSON = "price";
    String Image_Price_JSON1 = "price1";
    String Image_Price_JSON2 = "price2";
    String Image_Price_JSON3 = "price3";
    String Image_Price_JSON4 = "price4";
    String Image_NoBid_JSON = "nobid";
    String Image_Name_JSON = "email";
    String Image_ID_JSON = "id";
    String Image_Descr_JSON = "descr";
    List<String> IdList = new ArrayList<>();
    String Image_URL_JSON = "image_path1";
    String ParseResult ;
    String TempItem;
    private SQLiteHandlerUsers db1;
    private SessionManager session;
    long elapsed;
    String newprice;
    String auctione,stause,email,userpayment,userpayment2,newuserpayment,userid,newpayment,oldprice,usermobile,bidhourold;
    SimpleDateFormat formatter3 = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formatter1 = new SimpleDateFormat("mm/dd/yyyy HH:mm");
    SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");
    double userpaymentnumber, pricenumber,pricenumber2,newpricenumber,oldpricenumber;

    String IdHolder,NameHolder,pathholder,Priceholder,AuctionHolder,BidDateHolder,BidWinHolder1,BidWinHolder2,BidWinHolder3,BidHourHolder,ItemypeHolder,

    BidWinHolder4,BidWinHolder,Priceholder1,Priceholder2,Priceholder3,Priceholder4,BidNumberHolder,BidStatusHolder;
    JsonArrayRequest RequestOfJSonArray ;
    HttpParse httpParse = new HttpParse();
    String FinalJSonObject ;
    HashMap<String,String> ResultHash = new HashMap<>();
    RequestQueue requestQueue ;

    View view ;

    int RecyclerViewItemPosition ;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> ImageTitleNameArrayListForClick,PriceArray,Price1Array,Price2Array,Price3Array,Price4Array,WinArray,
            Win1Array,Win2Array,Win3Array,Win4Array,biddateArray,bidtimeArray,bidoneArray;

    private HomeFragment.OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        ImageTitleNameArrayListForClick = new ArrayList<>();
        PriceArray=new ArrayList<>();
        Price1Array = new ArrayList<>();
        Price2Array = new ArrayList<>();
        Price3Array = new ArrayList<>();
        Price4Array = new ArrayList<>();
        WinArray = new ArrayList<>();
        Win1Array = new ArrayList<>();
        Win2Array = new ArrayList<>();
        Win3Array = new ArrayList<>();
        Win4Array = new ArrayList<>();
        biddateArray = new ArrayList<>();
        bidtimeArray = new ArrayList<>();
        bidoneArray = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        JSON_HTTP_CALL();

                                    }
                                }
        );
        db1 = new SQLiteHandlerUsers(getApplicationContext());
        HashMap<String, String> usermazad = db1.getUserDetails();

        // userpayment = usermazad.get("downpayment");
        email = usermazad.get("email");
        usermobile=usermazad.get("mobile");
        if (email==null)
        {  postAlert();}

        JSON_HTTP_CALL_User();

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        // JSON_HTTP_CALL();
        // Implementing Click Listener on RecyclerView.
      /*  ViewGroup viewGroup = ((ViewGroup)recyclerView.get
        String newprice = ((EditText)viewGroup.getChildAt(0)).getText().toString();*/
      /*  EditText newpriceEditText = (EditText) view.findViewById(R.id.newpriceeditable);
        newprice = newpriceEditText.getText().toString();*/


    }



    public void onClickBotonBorrar(View view) {
        //Getting RecyclerView Clicked Item value.


        RecyclerViewItemPosition = (int) view.getTag();
       // View vview = recyclerView.getChildAt( RecyclerViewItemPosition);
        RecyclerView.ViewHolder vview = recyclerView.findViewHolderForAdapterPosition(RecyclerViewItemPosition);

        EditText nameEditText = (EditText) recyclerView.findViewById(R.id.newpriceeditable);
        String newprice = nameEditText.getText().toString();
     //   String newprice = ((EditText)viewGroup.getChildAt(RecyclerViewItemPosition)).getText().toString();
        Toast.makeText(getApplicationContext(),  newprice, Toast.LENGTH_LONG).show();


    /*    builder.setView(mview);
        AlertDialog dialog=builder.create();
        dialog.show();*/


    }

    private void postAlert() {

        NotificationManager mNotificationManager =
                (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
// The id of the channel.
        String id = "my_channel_01";
// The user-visible name of the channel.
        CharSequence name = getString(R.string.channel_name);
// The user-visible description of the channel.
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, name, importance);
        }
// Configure the notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel.setDescription(description);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel.enableLights(true);
        }
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel.setLightColor(Color.RED);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel.enableVibration(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);
        }

        int notifyID = 1;

// The id of the channel.
        String CHANNEL_ID = "my_channel_01";

// Create a notification and set the notification channel.
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Hurry Up")
                    .setContentText("Register your Account .......")
                    .setSmallIcon(R.drawable.iconmodel)
                    .setChannelId(CHANNEL_ID)
                    .build();
        }

// Issue the notification.
        mNotificationManager.notify(notifyID, notification);
        notifyID++;

    }


    public void GetDataFromEditText(){
        if (Integer.parseInt(BidNumberHolder)==1)
        {
            Priceholder1 = newprice;
            //  userpaymentnumber = Double.parseDouble( Priceholder1);
            BidWinHolder1=email;
            BidNumberHolder=String.valueOf(2);

        }
        else if (Integer.parseInt(BidNumberHolder)==2)
        {  Priceholder2 = newprice;
            //     userpaymentnumber = Double.parseDouble( Priceholder2);
            BidWinHolder2=email;
            BidNumberHolder=String.valueOf(3);
        }
        else if (Integer.parseInt(BidNumberHolder)==3)
        {  Priceholder3 = newprice;
            //    userpaymentnumber = Double.parseDouble( Priceholder3);
            BidWinHolder3=email;
            BidNumberHolder=String.valueOf(4);
        }

        else if (Integer.parseInt(BidNumberHolder)==4)
        {  Priceholder4 = newprice;
            //     userpaymentnumber = Double.parseDouble( Priceholder4);
            BidWinHolder4=email;
            BidNumberHolder=String.valueOf(5);
        }
        else if (Integer.parseInt(BidNumberHolder)==5)
        {  Priceholder = newprice;
            //    userpaymentnumber = Double.parseDouble( priceholder);
            BidWinHolder=email;
            BidNumberHolder=String.valueOf(1);
        }


    }

    public void JSON_HTTP_CALL(){
        swipeRefreshLayout.setRefreshing(true);
        RequestOfJSonArray = new JsonArrayRequest(AppConfig.HTTP_JSON_URL_listcar,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response == null )
                            return;
                        ListOfdataAdapter.clear();//to resset adapter each time
                        ParseJSonResponse(response);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(RequestOfJSonArray);

    }
    public void ParseJSonResponse(JSONArray array){
        //  swipeRefreshLayout.setRefreshing(false);
        for(int i = 0; i<array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);
                //   if (json.getString((Image_Auction_JSON)).equalsIgnoreCase("YES"))

                GetDataAdapter2.setImageTitle("Name:"+json.getString(Image_Name_JSON));
             /*  GetDataAdapter2.setImagePrice("Price:"+json.getString(Image_Price_JSON));
                GetDataAdapter2.setImagePrice1("Price1:"+json.getString(Image_Price_JSON1));
                GetDataAdapter2.setImagePrice2("Price2:"+json.getString(Image_Price_JSON2));
                GetDataAdapter2.setImagePrice3("Price3:"+json.getString(Image_Price_JSON3));
                GetDataAdapter2.setImagePrice4("Price4:"+json.getString(Image_Price_JSON4));*/
                GetDataAdapter2.setBidDate("Bid Date:"+json.getString(Bid_Date_JSON));
                GetDataAdapter2.setItemType("Item Type:"+json.getString(Bid_Type_JSON));
                GetDataAdapter2.setNoBid("Bid:"+json.getString(Image_NoBid_JSON));
                GetDataAdapter2.setItemHour("Bid Time:"+json.getString(Bid_Hour_JSON));
                GetDataAdapter2.setBidDate("Bid Date:"+json.getString(Bid_Date_JSON));
                GetDataAdapter2.setNoBid("NO.of Bids:"+json.getString(Image_NoBid_JSON));
                GetDataAdapter2.setItemStatus("Status:"+json.getString(Bid_Status_JSON));
              /* GetDataAdapter2.setBidWin("Win1:"+json.getString(Bid_Winner_JSON));
                GetDataAdapter2.setBidWin1("Win2:"+json.getString(Bid_Winner_JSON1));
                GetDataAdapter2.setBidWin2("Win3:"+json.getString(Bid_Winner_JSON2));
                GetDataAdapter2.setBidWin3("Win4:"+json.getString(Bid_Winner_JSON3));
                GetDataAdapter2.setBidWin4("Win5:"+json.getString(Bid_Winner_JSON4));*/
                GetDataAdapter2.setItemHour("Bid Time:"+json.getString(Bid_Hour_JSON));
                GetDataAdapter2.setItemAuction("Auction:"+json.getString(Image_Auction_JSON));
                GetDataAdapter2.setImageID("Item Code#:"+json.getString(Image_ID_JSON));
                GetDataAdapter2.setItemDescr("Description:"+json.getString(Image_Descr_JSON));
                GetDataAdapter2.setNoBid("NO.of Bids:"+json.getString(Image_NoBid_JSON));
                if (Integer.parseInt(json.getString(Image_NoBid_JSON))==1)
                {
                    GetDataAdapter2.setImagePrice("Price1:"+json.getString(Image_Price_JSON));
                    GetDataAdapter2.setBidWin("Win1:"+json.getString(Bid_Winner_JSON));

                    ImageTitleNameArrayListForClick.add(json.getString(Image_Price_JSON));
                    PriceArray.add(json.getString(Image_Price_JSON));
                    Price1Array.add(json.getString(Image_Price_JSON1));
                    Win1Array.add(json.getString(Bid_Winner_JSON1));
                    Price2Array.add(json.getString(Image_Price_JSON2));
                    Win2Array.add(json.getString(Bid_Winner_JSON2));
                    Price3Array.add(json.getString(Image_Price_JSON3));
                    Win3Array.add(json.getString(Bid_Winner_JSON3));
                    Price4Array.add(json.getString(Image_Price_JSON4));
                    Win4Array.add(json.getString(Bid_Winner_JSON4));
                    WinArray.add(json.getString(Bid_Winner_JSON));
                    bidoneArray.add(json.getString(Image_NoBid_JSON));
                    biddateArray.add(json.getString(Bid_Date_JSON));
                    bidtimeArray.add(json.getString(Bid_Hour_JSON));
                }
                else if (Integer.parseInt(json.getString(Image_NoBid_JSON))==2)
                {
                    GetDataAdapter2.setImagePrice("Price2:" + json.getString(Image_Price_JSON1));
                    GetDataAdapter2.setBidWin("Win2:"+json.getString(Bid_Winner_JSON1));
                    ImageTitleNameArrayListForClick.add(json.getString(Image_Price_JSON1));
                    PriceArray.add(json.getString(Image_Price_JSON));
                    Price1Array.add(json.getString(Image_Price_JSON1));
                    Win1Array.add(json.getString(Bid_Winner_JSON1));
                    Price2Array.add(json.getString(Image_Price_JSON2));
                    Win2Array.add(json.getString(Bid_Winner_JSON2));
                    Price3Array.add(json.getString(Image_Price_JSON3));
                    Win3Array.add(json.getString(Bid_Winner_JSON3));
                    Price4Array.add(json.getString(Image_Price_JSON4));
                    Win4Array.add(json.getString(Bid_Winner_JSON4));
                    WinArray.add(json.getString(Bid_Winner_JSON));
                    bidoneArray.add(json.getString(Image_NoBid_JSON));
                    biddateArray.add(json.getString(Bid_Date_JSON));
                    bidtimeArray.add(json.getString(Bid_Hour_JSON));
                }
                else if (Integer.parseInt(json.getString(Image_NoBid_JSON))==3)
                {
                    GetDataAdapter2.setImagePrice("Price3:"+json.getString(Image_Price_JSON2));
                    GetDataAdapter2.setBidWin("Win3:"+json.getString(Bid_Winner_JSON2));
                    ImageTitleNameArrayListForClick.add(json.getString(Image_Price_JSON2));
                    PriceArray.add(json.getString(Image_Price_JSON));
                    Price1Array.add(json.getString(Image_Price_JSON1));
                    Win1Array.add(json.getString(Bid_Winner_JSON1));
                    Price2Array.add(json.getString(Image_Price_JSON2));
                    Win2Array.add(json.getString(Bid_Winner_JSON2));
                    Price3Array.add(json.getString(Image_Price_JSON3));
                    Win3Array.add(json.getString(Bid_Winner_JSON3));
                    Price4Array.add(json.getString(Image_Price_JSON4));
                    Win4Array.add(json.getString(Bid_Winner_JSON4));
                    WinArray.add(json.getString(Bid_Winner_JSON));
                    bidoneArray.add(json.getString(Image_NoBid_JSON));
                    biddateArray.add(json.getString(Bid_Date_JSON));
                    bidtimeArray.add(json.getString(Bid_Hour_JSON));
                }
                else if (Integer.parseInt(json.getString(Image_NoBid_JSON))==4)
                {
                    GetDataAdapter2.setImagePrice("Price4:"+json.getString(Image_Price_JSON3));
                    GetDataAdapter2.setBidWin("Win4:"+json.getString(Bid_Winner_JSON3));
                    ImageTitleNameArrayListForClick.add(json.getString(Image_Price_JSON3));
                    PriceArray.add(json.getString(Image_Price_JSON));
                    Price1Array.add(json.getString(Image_Price_JSON1));
                    Win1Array.add(json.getString(Bid_Winner_JSON1));
                    Price2Array.add(json.getString(Image_Price_JSON2));
                    Win2Array.add(json.getString(Bid_Winner_JSON2));
                    Price3Array.add(json.getString(Image_Price_JSON3));
                    Win3Array.add(json.getString(Bid_Winner_JSON3));
                    Price4Array.add(json.getString(Image_Price_JSON4));
                    Win4Array.add(json.getString(Bid_Winner_JSON4));
                    WinArray.add(json.getString(Bid_Winner_JSON));
                    bidoneArray.add(json.getString(Image_NoBid_JSON));
                    biddateArray.add(json.getString(Bid_Date_JSON));
                    bidtimeArray.add(json.getString(Bid_Hour_JSON));
                }
                else if (Integer.parseInt(json.getString(Image_NoBid_JSON))==5)
                {
                    GetDataAdapter2.setImagePrice("Price:"+json.getString(Image_Price_JSON4));
                    GetDataAdapter2.setBidWin("Win5:"+json.getString(Bid_Winner_JSON4));
                    ImageTitleNameArrayListForClick.add(json.getString(Image_Price_JSON4));
                    PriceArray.add(json.getString(Image_Price_JSON));
                    Price1Array.add(json.getString(Image_Price_JSON1));
                    Win1Array.add(json.getString(Bid_Winner_JSON1));
                    Price2Array.add(json.getString(Image_Price_JSON2));
                    Win2Array.add(json.getString(Bid_Winner_JSON2));
                    Price3Array.add(json.getString(Image_Price_JSON3));
                    Win3Array.add(json.getString(Bid_Winner_JSON3));
                    Price4Array.add(json.getString(Image_Price_JSON4));
                    Win4Array.add(json.getString(Bid_Winner_JSON4));
                    WinArray.add(json.getString(Bid_Winner_JSON));
                    bidoneArray.add(json.getString(Image_NoBid_JSON));
                    biddateArray.add(json.getString(Bid_Date_JSON));
                    bidtimeArray.add(json.getString(Bid_Hour_JSON));
                }

                IdList.add(json.getString("id").toString());
                // Adding image title name in array to display on RecyclerView click event.
                //   ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
                //ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
                GetDataAdapter2.setImageUrl(json.getString(Image_URL_JSON));
                ListOfdataAdapter.add(GetDataAdapter2);

            } catch (JSONException e) {

                e.printStackTrace();
            }

            // ListOfdataAdapter.add(GetDataAdapter2);
            swipeRefreshLayout.setRefreshing(false);
        }

        recyclerViewadapter = new RecyclerViewAdapteritemsuser(ListOfdataAdapter, getApplicationContext());

        recyclerView.setAdapter(recyclerViewadapter);

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    @Override
    public void onRefresh() {
        JSON_HTTP_CALL();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void HttpWebCallFast(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(getApplicationContext(),"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new CarsFragmentBidActivity2.GetHttpResponse(getApplicationContext()).execute();

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
                        // DataAdapter2 GetDataAdapter2 = new DataAdapter2();
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

                          /*  GetDataAdapter2.setImageTitle("Name:"+jsonObject.getString(Image_Name_JSON));

                            GetDataAdapter2.setItemmark("Make:"+jsonObject.getString(Image_Make_JSON));
                            GetDataAdapter2.setItemmodel("Model:"+jsonObject.getString(Image_Model_JSON));
                            GetDataAdapter2.setItemmillage("Millage:"+jsonObject.getString(Image_Millage_JSON));
                            GetDataAdapter2.setItemyear("Year:"+jsonObject.getString(Image_Year_JSON));
                            GetDataAdapter2.setItemwarn("Waranty:"+jsonObject.getString(Image_Warn_JSON));
                            GetDataAdapter2.setItemtype("Type:"+jsonObject.getString(Image_Cartypr_JSON));
                            GetDataAdapter2.setItemengine("Engine:"+jsonObject.getString(Image_Engine_JSON));
                            GetDataAdapter2.setItembidtime("Bid Time:"+jsonObject.getString(Image_Bidtime_JSON));



                            //   GetDataAdapter2.setImagePrice("Price:"+jsonObject.getString(Image_Price_JSON));
                          /*  GetDataAdapter2.setImagePrice1("Price1:"+jsonObject.getString(Image_Price_JSON1));
                            GetDataAdapter2.setImagePrice2("Price2:"+jsonObject.getString(Image_Price_JSON2));
                            GetDataAdapter2.setImagePrice3("Price3:"+jsonObject.getString(Image_Price_JSON3));
                            GetDataAdapter2.setImagePrice4("Price4:"+jsonObject.getString(Image_Price_JSON4));*/
                         /*   GetDataAdapter2.setBidDate("Bid Date:"+jsonObject.getString(Bid_Date_JSON));
                            GetDataAdapter2.setItemStatus("Status:"+jsonObject.getString(Bid_Status_JSON));
                            GetDataAdapter2.setItemAuction("Auction:"+jsonObject.getString(Image_Auction_JSON));
                            GetDataAdapter2.setImageID("Item Code#:"+jsonObject.getString(Image_ID_JSON));
                            GetDataAdapter2.setItemDescr("Description:"+jsonObject.getString(Image_Descr_JSON));
                            GetDataAdapter2.setNoBid("NO.of Bids:"+jsonObject.getString(Image_NoBid_JSON));
                            if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==1)
                            {
                                GetDataAdapter2.setImagePrice("Price:"+jsonObject.getString(Image_Price_JSON));
                                GetDataAdapter2.setBidWin("Win:"+jsonObject.getString(Bid_Winner_JSON));}
                            else if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==2)
                            {   GetDataAdapter2.setImagePrice("Price1:"+jsonObject.getString(Image_Price_JSON1));
                                GetDataAdapter2.setBidWin1("Win1:"+jsonObject.getString(Bid_Winner_JSON1));}
                            else if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==3)
                            {    GetDataAdapter2.setImagePrice("Price2:"+jsonObject.getString(Image_Price_JSON2));
                                GetDataAdapter2.setBidWin2("Win2:"+jsonObject.getString(Bid_Winner_JSON2));}
                            else if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==4)
                            {  GetDataAdapter2.setImagePrice("Price3:"+jsonObject.getString(Image_Price_JSON3));
                                GetDataAdapter2.setBidWin3("Win3:"+jsonObject.getString(Bid_Winner_JSON3));}
                            else if (Integer.parseInt(jsonObject.getString(Image_NoBid_JSON))==5)
                            {     GetDataAdapter2.setImagePrice("Price4:"+jsonObject.getString(Image_Price_JSON4));
                                GetDataAdapter2.setBidWin4("Win4:"+jsonObject.getString(Bid_Winner_JSON4));}
                            GetDataAdapter2.setImageUrl(jsonObject.getString(Image_URL_JSON));
                            GetDataAdapter2.setImageUrl1(jsonObject.getString(Image_URL_JSON1));
                            GetDataAdapter2.setImageUrl2(jsonObject.getString(Image_URL_JSON2));*/

                            // Adding image title name in array to display on RecyclerView click event.
                            ImageTitleNameArrayListForClick.add(jsonObject.getString(Image_Name_JSON));
                            //ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));

                            // Storing Student Name, Phone Number, Class into Variables.
                            IdHolder= jsonObject.getString("id").toString() ;
                            NameHolder = jsonObject.getString("email").toString() ;
                            Priceholder = jsonObject.getString("price").toString() ;
                            Priceholder1 = jsonObject.getString("price1").toString() ;
                            Priceholder2 = jsonObject.getString("price2").toString() ;
                            Priceholder3 = jsonObject.getString("price3").toString() ;
                            Priceholder4 = jsonObject.getString("price4").toString() ;
                            BidDateHolder = jsonObject.getString("biddate").toString() ;
                            BidHourHolder = jsonObject.getString("bidhour").toString() ;
                            ItemypeHolder = jsonObject.getString("type").toString() ;
                            BidNumberHolder = jsonObject.getString("nobid").toString() ;
                            BidWinHolder = jsonObject.getString("bidwin").toString() ;
                            BidWinHolder1 = jsonObject.getString("bidwin1").toString() ;
                            BidWinHolder2 = jsonObject.getString("bidwin2").toString() ;
                            BidWinHolder3 = jsonObject.getString("bidwin3").toString() ;
                            BidWinHolder4 = jsonObject.getString("bidwin4").toString() ;
                            AuctionHolder = jsonObject.getString("auction").toString() ;
                            BidStatusHolder = jsonObject.getString("status").toString() ;
                            pathholder = jsonObject.getString("image_path1").toString() ;
                            //   ListOfdataAdapter.add(GetDataAdapter2);
                        }



                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                //    recyclerViewadapter = new RecyclerViewAdapter2itemusers(ListOfdataAdapter, context);
                //   recyclerView.setAdapter(recyclerViewadapter);

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
    public void JSON_HTTP_CALL_User(){

        RequestOfJSonArray = new JsonArrayRequest(AppConfig.HTTP_JSON_URL_Users,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response2) {

                        ParseJSonResponse2(response2);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse2(JSONArray array){

        for(int i = 0; i<array.length(); i++) {


            JSONObject json = null;
            try {

                json = array.getJSONObject(i);
                if (json.getString((Image_Email_JSON)).equals(email))
                {
                    userpayment=(json.getString(User_Downpay_JSON));
                    userpayment2=(json.getString(User_Downpay_JSON2));
                    userid=(json.getString(Image_ID_JSON));
                }
                //  IdList.add(json.getString("id").toString());
                // Adding image title name in array to display on RecyclerView click event.
                //    ImageTitleNameArrayListForClick.add(json.getString(User_Name_JSON));
                //ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
                // ListOfdataAdapter.add(GetDataAdapter2);
            } catch (JSONException e) {

                e.printStackTrace();
            }

            //  ListOfdataAdapter.add(GetDataAdapter2);
        }

    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void StudentRecordUpdate(final String ID, final String S_price,final String S_price1,final String S_price2,final String S_price3,final String S_price4
            ,final String S_mobile,final String S_mobile1,final String S_mobile2,final String S_mobile3,final String S_mobile4,
                                    final String S_nobid,final String S_bidhour, final String S_biddate){

        class StudentRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(getApplicationContext(),"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(),httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);
                hashMap.put("price",params[1]);
                hashMap.put("price1",params[2]);
                hashMap.put("price2",params[3]);
                hashMap.put("price3",params[4]);
                hashMap.put("price4",params[5]);
                hashMap.put("bidwin",params[6]);
                hashMap.put("bidwin1",params[7]);
                hashMap.put("bidwin2",params[8]);
                hashMap.put("bidwin3",params[9]);
                hashMap.put("bidwin4",params[10]);
                hashMap.put("nobid",params[11]);
                hashMap.put("bidhour",params[12]);
                hashMap.put("biddate",params[13]);
                //   hashMap.put("auction",params[2]);
                //  hashMap.put("status",params[3]);
                //    hashMap.put("biddate",params[4]);
                //   hashMap.put("bidwin",params[5]);
                //    hashMap.put("image_path",params[2]);

                finalResult = httpParse.postRequest(hashMap, AppConfig.HttpURLitem);

                return finalResult;
            }
        }

        StudentRecordUpdateClass studentRecordUpdateClass = new StudentRecordUpdateClass();

        studentRecordUpdateClass.execute(ID,S_price,S_price1,S_price2,S_price3,S_price4,S_mobile,S_mobile1,S_mobile2,S_mobile3,S_mobile4,S_nobid,S_bidhour,S_biddate);
    }

}
