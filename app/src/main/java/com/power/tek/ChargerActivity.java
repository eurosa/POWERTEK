package com.power.tek;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.os.SystemClock;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.instantapps.InstantApps;
import com.google.android.material.navigation.NavigationView;

import com.kyleduo.switchbutton.SwitchButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import hearsilent.discreteslider.Dash;
import hearsilent.discreteslider.DiscreteSlider;
import hearsilent.discreteslider.Dot;
import hearsilent.discreteslider.libs.Utils;

public class ChargerActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String MY_PREFS_NAME = "MyTxtFile";
    private Handler handler = new Handler();
    TextView setTempDisplay;
    TextView setHumDisplay;
    ImageButton tempMinusButton;
    ImageButton tempPlusButton;
    ImageButton[] arrayOfControlButtons;
    final String sNewName = "Syntactics";
    final long lTimeToGiveUp_ms = System.currentTimeMillis() + 10000;

    /***************************************************************************************
     *                           Start Increment and Decrement
     ****************************************************************************************/

    /***************************************************************************************
     *  End Increment and Decrement
     ****************************************************************************************/

    /***************************************************************************************
     *  Play and pause in only one button - Android
     ****************************************************************************************/
    boolean isPlaying = true;
    /***************************************************************************************
     *  Play and pause in only one button - Android
     ****************************************************************************************/
    //  private CameraKitView cameraKitView;
    //==============================To Connect Bluetooth Device==============================
    private ProgressDialog progress;
    private boolean isBtConnected = false;
    BluetoothSocket btSocket = null;
    OutputStream mmOutputStream;
    InputStream mmInputStream;//00001101-0000-1000-8000-00805F9B34FB
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address = null;
    private Button sendBtn;

    boolean listViewFlag = true;


    //==============================To connect Bluetooth devices===============================
    //-----------------------------Camera------------------------------------------------------
    private static final String LOG_TAG = "JBCamera";
    private static final int REQUEST_CAMERA_PERMISSION = 21;
    private static final int REQUEST_STORAGE_PERMISSION = 22;
    private int cameraId = 1;
    private Camera camera = null;
    private boolean waitForPermission = false;
    private boolean waitForStoragePermission = false;
    //-----------------------------------Camera------------------------------------------------
    // ------------------------ Auto Repeat increment and decrement ---------------------------
    Integer currentDisplayValue = 0;
    float currentTempValue = 320;
    Integer maxValue = 380;
    Integer minValue = 320;
    final int DisplayValueMin = 0;
    final int DisplayValueMax1 = 99;
    final int DisplayValueMax2 = 999;
    final int DisplayValueMax3 = 9999;
    //+++++++++++++++++++++++++ Auto Repeat increment and decrement ++++++++++++++++++++++++++

    Button On, Off, Discnt, Abt;

    //widgets
    Button btnPaired, scanDevices;
    ListView devicelist;
    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";
    public static String EXTRA_INFO = "device_info";

    //screenshott
    private final static String[] requestWritePermission =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private ImageView imageView;
    private Bitmap bitmap;

    private String str_celcius;
    private String str_fahrenheit;
    private ImageView bmpView;
    //=================================For temperature limit count==================================


    private String double_str_fahrenheit;


    private ImageView iv_your_image;
    private String str_cel, str_fah;

    //=========================================End temperature limit count==========================
    String[] permissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private FrameLayout layout;

    private TextView one, two, three, four, five, six, seven, eight, nine, zero, div, multi, sub, plus, dot, equals, display, clear;
    private ImageButton backDelete;

    /***************************************************************************************
     * Navigation Drawer Layout
     *
     ***************************************************************************************/
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView mNavigationView;
    private ListView idList, sndList, digitList, typeList;
    private ArrayAdapter<String> typeAdapter;
    private ArrayAdapter<String> sndAdapter;
    private ArrayAdapter<String> idAdapter;
    private ArrayAdapter<String> digitAdapter;
    private Dialog idDialog;
    private Dialog digitDialog;
    private Dialog sndDialog;
    private Dialog typeDialog;

    private DataModel dataModel;
    private boolean success = false;
    private TextView connectionStatus;
    private String infoBLE = null;
    private String addressBLE = null;
    private String name = null;
    private String info_address;
    private TextView clockShow;
    private ImageButton bltStatus;

    /****************************************************************************************
     * End of Navigation Drawer
     *
     * */

    /***************************************************************************************
     * Start Stop Watch
     ****************************************************************************************/
    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private ImageButton playPause;
    private ImageButton stopButton;
    private ImageButton resetButton;
    private DiscreteSlider mSlider1, mSlider2, mSlider3, mSlider4;
    private SwitchButton switch1;
    private SwitchButton switch2;
    private ImageButton humBtnPlus;
    private ImageButton humBtnMinus;
    private Button changeUnit;
    private TextView airTempUnit;
    private TextView celUnit;
    private TextView setTempUnit;
    private boolean unitChange = false;
    private AlertDialog dialog;
    private TextView tempShow, airTempShow;
    private int tempValue;
    private String tempValueString = "00";
    private int Temp2, Temp1, Humid;
    private String skinTemp2String;
    private String heaterOutputString = "00";
    private String timerShowString = "00";
    private String setSkinTempString;
    private String heatModeString;
    private Button timerONBtn;
    private Button servoBtn, manualBtn;
    private String[] arrayHex;
    private String[] rawArrayData;
    private boolean checkSendReceive = true;
    private Button muteBtn;
    private boolean initSetTempCheck = true;
    private float floatCurrentSetTempValue = 32;
    private char[] TxData;
    private Timer repeatTimer;
    private TimerTask timerTask;
    private String humidityValueString = "00";

    private Button startBtn, stopBtn;
    private Map<String, String> assoc_day;
    private TextView temperature;
    private TextView humidity;
    private float voltage;
    private TextView voltageView, stVoltage, current, stCurrent, stTime, mode, stTimer, fileNameView, systemView, operationView;
    private float setVoltage, setCurrent, currentValue;
    private int ccMode;
    private int stTimeValue;
    private int stTimerValue;
    private int fileNameValue;
    private int systemValue;
    private int operationValue;
    Map<Integer, String> dataFile;
    private byte bFileName,bcvccMode,bSystem,bOperation;

    /***************************************************************************************
     *****************************End Stop Watch*****************************
     ****************************************************************************************/

    //screenshot
    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger);
        stVoltage = findViewById(R.id.stVoltage);
        voltageView = findViewById(R.id.voltage);
        stCurrent = findViewById(R.id.stCurrent);
        current = findViewById(R.id.current);
        stTime = findViewById(R.id.stTime);
        stTimer = findViewById(R.id.stTimer);
        mode = findViewById(R.id.Mode);
        fileNameView = findViewById(R.id.fileName);
        systemView = findViewById(R.id.systemView);
        operationView = findViewById(R.id.operationModeView);
        //String fileNameArray[] = new String[]{ "NS40 ","N50Z ","N70Z ","N100 ","N120 ","N110 ","N115 ","N150 ",
         //       "N160 ","N200 ","N210 ","IT400","IT500","LiOn ","NiCa ","F-16 "};
        dataFile = new HashMap<>();
        dataFile.put(0, "NS40");
        dataFile.put(1, "N50Z");
        dataFile.put(2, "N70Z");
        dataFile.put(3, "N100");
        dataFile.put(4, "N120");
        dataFile.put(5, "N110");
        dataFile.put(6, "N115");
        dataFile.put(7, "N150");
        dataFile.put(8, "N160");
        dataFile.put(9, "N200");
        dataFile.put(10, "N210");
        dataFile.put(11, "IT400");
        dataFile.put(12, "IT500");
        dataFile.put(13, "LiOn");
        dataFile.put(14, "NiCa");
        dataFile.put(15, "F-16");
        // Bluetooth Permission
        checkPermissions();
        //--------------------------------------------------- Grant Storage Permission --------------------------------------------------
        setAnimation();
        repeatTimer = new Timer();
        assoc_day = new HashMap<String, String>();
        assoc_day.put("01", "Monday");
        assoc_day.put("02", "Tuesday");
        assoc_day.put("03", "Wednesday");
        assoc_day.put("04", "Thursday");
        assoc_day.put("05", "Friday");
        assoc_day.put("06", "Saturday");
        assoc_day.put("07", "Sunday");

        startBtn = findViewById(R.id.startBtn);
        //  startBtn.setOnClickListener(this);

        startBtn.setOnClickListener(view -> {
            if (isPlaying) {
                play();
                isPlaying = false;

            } else {
                pause();
                isPlaying = true;
            }
          //  isPlaying = !isPlaying;
        });

        stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(this);
        dataModel = new DataModel();

        // dbHandler.getQmsUtilityById("1", dataModel);




        // connectionStatus = findViewById(R.id.connectionStatus);
        bltStatus = findViewById(R.id.bltStatus);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(mReceiver, filter);
        //------------------------------------------------------------------------------------------------
        //=========================Adding Toolbar in android layout=======================================
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        //=========================Toolbar End============================================================
        Drawable drawable = myToolbar.getOverflowIcon();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), getResources().getColor(R.color.whiteColor));
            myToolbar.setOverflowIcon(drawable);
        }
        // ******************** Changing the color of three dot or overflow button *****************************
        /***************************************************************************************
         * Navigation Drawer Layout
         *
         ***************************************************************************************/
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.draw_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
            }

            /**********************************************************************************************************************
             ********************************** Called when a drawer has settled in a completely open state. **********************
             **********************************************************************************************************************/

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //  Do whatever you want here
                //  initItemData();
            }

            public void onDrawerStateChanged(int i) {

            }

            public void onDrawerSlide(View view, float v) {

            }
        };

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // To change hamburger icon color
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.sun_color));


        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        // After giving this Navigation menu Item Icon becomes colorful
        mNavigationView.setItemIconTintList(null); // <-- HERE add this code for icon color
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /***************************************************************************************
         * Navigation Drawer Layout
         *
         ***************************************************************************************/

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "DSEG7Classic-Bold.ttf");
        //   display.setTypeface(tf);


        //============================Keyboard====================================================//
//--------------------------Java--------------------------
        if (ContextCompat.checkSelfPermission(ChargerActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(ChargerActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }

        if (ContextCompat.checkSelfPermission(ChargerActivity.this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(ChargerActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 3);
                return;
            }
        }

        if (ContextCompat.checkSelfPermission(ChargerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(ChargerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 4);
                return;
            }
        }


        Intent newint = getIntent();
        address = newint.getStringExtra(ChargerActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device
        info_address = newint.getStringExtra(ChargerActivity.EXTRA_INFO);
        if (address != null) {
            new ConnectBT(address, info_address).execute(); //Call the class to
        }
        //-------------------------------------To Receive device address from background==================
        //====================================Camera======================================================

        boolean isInstantApp = InstantApps.getPackageManagerCompat(this).isInstantApp();
        Log.d(LOG_TAG, "are we instant?" + isInstantApp);


        //if the device has bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (myBluetooth == null) {
            //Show a Mensag. That the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        } else if (!myBluetooth.isEnabled()) {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);
        }


        //Camera screenshot

        imageView = findViewById(R.id.imageView);


//----------------------------------screen_shot xml view-----------------------------------------
        //Camera screenshot

        //=================================FileExposed============================
        /*
         *
         *
         * android.os.FileUriExposedException: file:///storage/emulated/0/test.txt exposed beyond app through Intent.getData()
         * solved using this
         * */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        //=======================================================================


    }

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };
    private static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };
    private void checkPermissions(){
        int permission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN);
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    1
            );
        } else if (permission2 != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_LOCATION,
                    1
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ChargerActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChargerActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private static String toASCII(int value) {
        int length = 1;
        StringBuilder builder = new StringBuilder(length);
        for (int i = length - 1; i >= 0; i--) {
            builder.append((char) ((value >> (8 * i)) & 0xFF));
        }
        Log.d("data_ascii", "" + builder.toString());
        return builder.toString();
    }

    public String decimalToHex(String value) {

        String hex = Integer.toHexString(Integer.parseInt(value));
        Integer parsedResult = Integer.parseInt(hex, 16);

        String hex1 = hexToAscii(Integer.toHexString(parsedResult));


        Log.d("string_to_hex", "hex:" + hex1 + "" + " secondPart: " + " " + " " + value + " " + Integer.toHexString(parsedResult) + " " + asciiToHex(hex1));
        return hex1;
    }

    // String conversion unicode
    public static String stringToUnicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i); // Take out each character
            unicode.append("\\u" + Integer.toHexString(c));// Convert to unicode
        }
        return unicode.toString();
    }

    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 1) {
            String str = hexStr.substring(i);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }


    private static String asciiToHex(String asciiValue) {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        Log.d("unicode_print", "" + hex.toString());
        return hex.toString();
    }

    public void hexunicode(int st) {

        //  int cp=Integer.parseInt(st,16);// it convert st into hex number.
        String s = String.valueOf(st);


// we now have str with the desired character as the first item
// confirm that we indeed have character with code point 128149

        Log.d("unicode_print", "" + s);
    }


    public String hexToUnicode(String hex) {

        int len = hex.length();
        byte[] cStr = new byte[len / 2];
        for (int i = 0; i < len; i += 1) {
            cStr[i / 2] = (byte) Integer.parseInt(hex.substring(i, i), 16);
        }
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        CharBuffer cb = null;
        try {
            cb = decoder.decode(ByteBuffer.wrap(cStr));
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        System.out.println(cb.toString());
        return cb.toString();
    }


    public void stopDataSendThread() {

        new Thread(() -> {
            // Run whatever background code you want here.
            String data = "$P0W3;";
            try {
                if (btSocket != null) {
                    Log.e("checkSendReceive run", "" + checkSendReceive);
                    //   if(checkSendReceive) {
                    btSocket.getOutputStream().write(data.getBytes());
                    // }
                    /* final Thread receive = new Thread(() -> {
                        try {
                            if(checkSendReceive) {
                                receiveData();
                            }
                        }catch (Exception e){
                            Log.d("timer_thread_stopped",""+e.getMessage());
                        }
                    });
                    receive.start();*/

                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }).start();
    }

    public void sendStartCommand() {
        String data = "$P0W1;";
        try {
            if (btSocket != null) {
                Log.e("checkSendReceive run", "" + checkSendReceive);
                //   if(checkSendReceive) {
                btSocket.getOutputStream().write(data.getBytes());
                // }


            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void sendPauseCommand() {
        String data = "$P0W2;";
        try {
            if (btSocket != null) {
                Log.e("checkSendReceive run", "" + checkSendReceive);
                //   if(checkSendReceive) {
                btSocket.getOutputStream().write(data.getBytes());
                // }


            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void runDataSendThread() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //what you want to do

                String data = "$P0R;";
                try {
                    if (btSocket != null) {
                        Log.e("checkSendReceive run", "" + checkSendReceive);
                        if (checkSendReceive) {
                            btSocket.getOutputStream().write(data.getBytes());
                        }
                        final Thread receive = new Thread(() -> {
                            try {
                                if (checkSendReceive) {
                                    receiveData();
                                }
                            } catch (Exception e) {
                                Log.d("timer_thread_stopped", "" + e.getMessage());
                            }
                        });
                        receive.start();

                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        };

        repeatTimer.schedule(timerTask, 100, 1000);//wait 0 ms before doing the action and do it evry 1000ms (1second)
/*
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                String data="$P0R;";
                try {
                    if(btSocket!=null) {
                        Log.d("checkSendReceive",""+checkSendReceive);
                        if(checkSendReceive) {
                            btSocket.getOutputStream().write(data.getBytes());
                            receiveData();
                        }
                    }
                } catch (IOException e) {
                    Log.d("timer_thread_stopped",""+e.getMessage());
                    e.printStackTrace();
                }
                // /* and here comes the "trick" /
                handler.postDelayed(this, 500);
            }
        };

        handler.postDelayed(runnable, 500);
*/
    }

    public void timerOnThread() {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String data = "$I0R;";
                try {
                    if (btSocket != null) {
                        btSocket.getOutputStream().write(data.getBytes());
                        //  receiveData();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 500, 500);
    }


    public void receiveData4() {
        try {
            int bytesAvailable = btSocket.getInputStream().available();

            byte[] packetBytes = new byte[bytesAvailable];
            if (bytesAvailable > 0) {
                // tb.setText(bytesAvailable+ "ok");
                btSocket.getInputStream().read(packetBytes);
                Log.i("logging", btSocket.getInputStream().read(packetBytes) + "");
                //   for(int i=0; i<bytesAvailable;i++)
                //   {
                //   if (packetBytes[i]==65)
                //    tb.setText("ON");
                //  else if (packetBytes[i] ==90)
                // tb.setText("off");
                // }
            }

        } catch (Exception e) {
            // ADD THIS TO SEE ANY ERROR
            //     e.printStackTrace();
        }

    }


    public static String replaceCharAt(String s, int pos, char c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }

    public int findPos(String s, char ch) {

        int pos = 0;
        char[] array = s.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ch) {
                System.out.println(i);
                pos = i;
            }
        }
        return pos;
    }


    public void receiveData() {

        if (btSocket != null) {
            try {
                InputStream socketInputStream = btSocket.getInputStream();

                byte[] buffer = new byte[23];
                int bytes;

                // Keep looping to listen for received messages

                try {

                    bytes = socketInputStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);

                    Log.i("data_asd readMessage", readMessage + " Bytes: " + bytes + " " + " Index of $: " + findPos(readMessage, '$'));

                    //   airTemp1 = int(hex(configVariables.hex_string[6]), 16)
                    //   airTemp2 = int(hex(configVariables.hex_string[7]), 16)
                    //  voltage = (vol << 8) | airTemp2

                    byte[] dataArray = new byte[23];
                    String[] dataArrayStr = new String[23];
                    StringBuilder sb = new StringBuilder(bytes * 2);
                    int i = 0;
                    for (byte b : buffer) {
                        sb.append(String.format("%02x ", b));

                        dataArray[i] = b;
                        dataArrayStr[i] = String.format("%02x", b);
                        Log.d("Received", String.valueOf(b));
                        i++;
                    }
                    String[] splited = sb.toString().split("\\s+");

                    int[] array = bytearray2intarray(dataArray);
                    //  voltage = (float) (dataArray[10]>>8|dataArray[11]);
                    //String.format("%02f", (byte) (dataArray[10]>>8|dataArray[11]));
                    // in the wrapper class Byte
                    //  Byte b = new Byte((byte) (dataArray[10]>>8|dataArray[11]));

                    // intValue of the Byte Object
                    // int output = b.intValue();
                    byte bSetVoltage = (byte) ((byte)dataArray[10] >> 8 | dataArray[11]);
                    byte bVoltage = (byte) (dataArray[4] >> 8 | dataArray[5]);
                    byte bStCurrent = (byte) (dataArray[12] >> 8 | dataArray[13]);
                    byte bStTime = (byte) (dataArray[14] >> 8 | dataArray[15]);
                    byte bCurrentValue = (byte) (dataArray[6] >> 8 | dataArray[7]);
                    byte bCycleTime = (byte) (dataArray[8] >> 8 | dataArray[9]);

                    byte bSemicolon = (byte) (dataArray[22]);
                    int semiVal = byteint(bSemicolon);
                    if(semiVal==59)
                    {
                        bFileName = (byte) (dataArray[18]);
                        bcvccMode = (byte) (dataArray[19]);
                        bSystem = (byte) (dataArray[21]);
                        bOperation = (byte) (dataArray[20]);
                    }else {
                        bFileName = (byte) (dataArray[16]);
                        bcvccMode = (byte) (dataArray[17]);
                        bSystem = (byte) (dataArray[19]);
                        bOperation = (byte) (dataArray[18]);
                    }
                    // Log.e("data_asd Received Data ", sb.toString()+" voltage: "+byteint(indf));
                    setVoltage = byteint(bSetVoltage);
                    voltage = byteint(bVoltage);
                    stTimeValue = byteint(bStTime);
                    stTimerValue = byteint(bCycleTime);
                    setCurrent = byteint(bStCurrent);
                    currentValue = byteint(bCurrentValue);
                    fileNameValue = byteint(bFileName);
                    ccMode = byteint(bcvccMode);
                    systemValue = byteint(bSystem);
                    operationValue = byteint(bOperation);

                    // if(bytes==7) {

                       //convertStringToHex(sb.toString(), readMessage);
                    //   }
                    // hexStringToByteArray(readMessage);
                    handler.post(() -> runOnUiThread(() -> {
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                    }));
                    Log.e("data_asd Received Data ", sb.toString()+"  ctime " + stTimerValue);
                    stVoltage.setText("" + setVoltage);
                    voltageView.setText("" + voltage);
                    stCurrent.setText("" + setCurrent / 10);
                    current.setText("" + currentValue / 10);
                    stTime.setText("" + stTimeValue);
                    stTimer.setText("" + stTimerValue);
                    Log.d("File_Name: ",""+fileNameValue+" System "+systemValue);
                    for (Map.Entry<Integer, String> entry : dataFile.entrySet()) {
                        if (entry.getKey().equals(fileNameValue)) {
                            System.out.println(entry.getKey());
                            fileNameView.setText(entry.getValue());
                        }
                    }


                    if(ccMode==1){
                        mode.setText("CVCC");
                    }else if(ccMode==2)
                    {
                        mode.setText("FCBC");
                    }else if(ccMode==3){
                        mode.setText("DCHG");
                    }else if(ccMode==4) {
                        mode.setText("AUTO");
                    }
                    if(systemValue==0){
                        systemView.setText("OFF");
                        isPlaying = true;
                        startBtn.setText("Start");
                    }else if(systemValue==1)
                    {
                        systemView.setText("ON");
                        isPlaying = false;
                        startBtn.setText("Pause");
                    }else if(systemValue==2){
                        systemView.setText("PAUSED");
                        isPlaying = true;
                        startBtn.setText("Start");
                    }
                    if(operationValue==0){
                        operationView.setText("D-CHG");
                    }
                    else if(operationValue==1)
                    {
                        operationView.setText("C-CHG");
                    }
                    else if(operationValue==2)
                    {
                        operationView.setText("PC-ON");
                    }
                    else if(operationValue==3)
                    {
                        operationView.setText("PC-OFF");
                    }
                    else{

                        operationView.setText(""+operationValue);
                    }
                   // systemView.setText("" + systemValue);


                } catch (Exception e) {
                    Log.d("Received Exception", e.getMessage());
                }

            } catch (IOException e) {
                msg("Error");
            }

        }

    }

    public int byteint(byte barray) {
        int iarray = barray & 0xff;
        return iarray;
    }


    public int[] bytearray2intarray(byte[] barray) {
        int[] iarray = new int[barray.length];
        int i = 0;
        for (byte b : barray)
            iarray[i++] = b & 0xff;
        return iarray;
    }


    static String stringToHex(String string) {
        StringBuilder buf = new StringBuilder(200);
        for (char ch : string.toCharArray()) {
            if (buf.length() > 0)
                buf.append(' ');
            buf.append(String.format("%04x", (int) ch));
        }
        return buf.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        Log.i("logging", data + "");
        return data;
    }


    private String convertStringToHex(String string, String rawMessage) {
        Log.d("logging_vol", "string length: " + string.length());
        StringBuilder newString = new StringBuilder();
        arrayHex = new String[23];
        rawArrayData = new String[23];
        arrayHex = string.split("\\s+");

        for (int i = 0; i <= rawMessage.length(); i++) {
            try {
                //  arrayHex[i] = String.format("%x", (byte) (string.charAt(i)));
                //  Log.d("data_asd arrayHex[5]",arrayHex[5]+" "+string.charAt(i));
                rawArrayData[i] = String.valueOf(rawMessage.charAt(i));
                newString.append(String.format("%x ", (byte) (rawMessage.charAt(i))));
                // Log.d("data_asd rawData[]",""+rawArrayData[i]+" "+newString);
            } catch (Exception e) {

            }
        }

        int i = Byte.parseByte(arrayHex[0]) >> 8;
        if (arrayHex[4] != null) {
            Temp1 = (Integer.parseInt(arrayHex[4], 16));
            // float tempFloat = (float) Temp1;
            //  tempValueString = String.valueOf(Temp1);
        }

        byte bSetVoltage = (byte) (Byte.parseByte(arrayHex[10]) >> 8 | Byte.parseByte(arrayHex[11]));
        byte bVoltage = (byte) (Byte.parseByte(arrayHex[4]) >> 8 | Byte.parseByte(arrayHex[5]));
        byte bStCurrent = (byte) (Byte.parseByte(arrayHex[12]) >> 8 | Byte.parseByte(arrayHex[13]));
        byte bStTime = (byte) (Byte.parseByte(arrayHex[14]) >> 8 | Byte.parseByte(arrayHex[15]));
        byte bCurrentValue = (byte) (Byte.parseByte(arrayHex[6]) >> 8 | Byte.parseByte(arrayHex[7]));
        byte bCycleTime = (byte) (Byte.parseByte(arrayHex[8]) >> 8 | Byte.parseByte(arrayHex[9]));
        byte bFileName = (byte) Byte.parseByte(arrayHex[18]);
        byte bCvcMode = (byte) Byte.parseByte(arrayHex[19]);
        byte bOperation = (byte) Byte.parseByte(arrayHex[20]);
        byte bSystem = (byte) Byte.parseByte(arrayHex[21]);
        // Log.e("data_asd Received Data ", sb.toString()+" voltage: "+byteint(indf));
        setVoltage = byteint(bSetVoltage);
        voltage = byteint(bVoltage);
        stTimeValue = byteint(bStTime);
        stTimerValue = byteint(bCycleTime);
        setCurrent = byteint(bStCurrent);
        currentValue = byteint(bCurrentValue);

        fileNameValue = byteint(bFileName);
        ccMode = byteint(bCvcMode);
        systemValue = byteint(bSystem);
        operationValue = byteint(bOperation);

        stVoltage.setText("" + setVoltage);
        voltageView.setText("" + voltage);
        stCurrent.setText("" + setCurrent / 10);
        current.setText("" + currentValue / 10);
       // stTime.setText("" + stTimeValue);
       // stTimer.setText("" + stTimerValue);
        Log.d("File_Name: ",""+fileNameValue+" Timer "+stTimerValue);
        for (Map.Entry<Integer, String> entry : dataFile.entrySet()) {
            if (entry.getKey().equals(fileNameValue)) {
                System.out.println(entry.getKey());
                fileNameView.setText(entry.getValue());
            }
        }


        if(ccMode==1){
            mode.setText("CVCC");
        }else if(ccMode==2)
        {
            mode.setText("FCBC");
        }else if(ccMode==3){
            mode.setText("DCHG");
        }else if(ccMode==4) {
            mode.setText("AUTO");
        }
        if(systemValue==0){
            systemView.setText("OFF");
        }
        if(systemValue==1)
        {
            systemView.setText("ON");
            isPlaying = false;
            startBtn.setText("Pause");
        }else if(systemValue==2){
            systemView.setText("PAUSED");
            isPlaying = true;
            startBtn.setText("Start");
        }
        // systemView.setText("" + systemValue);
        operationView.setText("" + operationValue);


        Log.i("logging", Arrays.toString(arrayHex) + "  " + Arrays.toString(rawArrayData));


        //hexStringToByteArray(newString.toString());
        return newString.toString();
    }

    public String HexToBinary(String Hex) {
        String bin = new BigInteger(Hex, 16).toString(2);
        int inb = Integer.parseInt(bin);
        byte b = Byte.parseByte(bin, 2);

        bin = String.format(Locale.getDefault(), "%08d", inb);
        //  Log.i("logging bit",""+getCharFromString(bin,6));
        return bin;
    }

    public int getBit(int position, byte ID) {
        return ((ID & (1 << position)) >> position);
        // return (byte) ((ID) & (0x01 << position));
    }

    public static char getCharFromString(String str, int index) {
        return str.charAt(index);
    }


    public static String hexToBinary(String hex) {
        return new BigInteger(hex, 16).toString(2);
    }


    // ON-CLICKS (referred to from XML)

    public void tempBtnMinusPressed() {
        currentDisplayValue--;

    }

    public void tempBtnPlusPressed() {
        currentDisplayValue++;

    }


    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int seconds = (int) (updatedTime / 1000);
            int minutes = seconds / 60;
            int hours = seconds / 3600;
            seconds = seconds % 60;
            //  int milliseconds = (int) (updatedTime % 1000);

            String string = "";
            string += "" + String.format("%02d", hours);
            string += ":" + String.format("%02d", minutes);
            string += ":" + String.format("%02d", seconds);
            //  string += ":" + String.format("%03d", milliseconds);

            timerValue.setText(string);
            customHandler.postDelayed(this, 0);
        }
    };



    /*private void sendData()
    {
        if (btSocket!=null)
        {
            try
            {
                final Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // myLabel.setText(sendEditText.getText().toString());
                        handler.postDelayed(this,100);
                    }
                },100);
                String digitNo =  dataModel.getDigitNo();
                dataModel.getTypeNo();
                dataModel.getDevId();
                dataModel.getSoundType();
                // String data="$134"+display.getText().toString()+";";
                String displayText = display.getText().toString();
                if(displayText.length()==2){

                    displayText = "0"+displayText;
                    digitNo = "3";
                }
                String displayData  = fixedLengthString(displayText, 4);

                String data="$"+dataModel.getDevId()+digitNo+dataModel.getSound_id()+displayData+";";
                btSocket.getOutputStream().write(data.getBytes());
                Log.d("Display_Digit",data);
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }*/

    private String fixedLengthString(String textData, int length) {
        String stringData = null;
        // String stringData = textData.rightPad(lenght, ' ').Substring(0, length);
        // String stringData = leftpad(textData,28);
        return stringData;
    }


    public void play() {
        checkSendReceive = false;
        sendStartCommand();
        startBtn.setText("Pause");
        checkSendReceive = true;
    }

    public void pause() {
        checkSendReceive = false;
        sendPauseCommand();
        startBtn.setText("Start");
        checkSendReceive = true;
    }


    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(400);
            slide.setInterpolator(new AccelerateDecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }


    private void setUpView(DiscreteSlider mSlider) {
        mSlider.setTrackWidth(Utils.convertDpToPixel(4, this));
        mSlider.setTrackColor(0xFFD81B60);
        mSlider.setInactiveTrackColor(0x3DD81B60);

        mSlider.setThumbRadius(Utils.convertDpToPixel(6, this));
        mSlider.setThumbColor(0xFFD81B60);
        mSlider.setThumbPressedColor(0x1FD81B60);

        mSlider.setTickMarkColor(0x3DFFFFFF);
        mSlider.setTickMarkInactiveColor(0x1FD81B60);
        mSlider.setTickMarkPatterns(
                Arrays.asList(new Dot(), new Dash(Utils.convertDpToPixel(1, this))));

        mSlider.setValueLabelTextColor(Color.WHITE);
        mSlider.setValueLabelTextSize(Utils.convertSpToPixel(16, this));
        mSlider.setValueLabelFormatter(new DiscreteSlider.ValueLabelFormatter() {

            @Override
            public String getLabel(int input) {
                return Integer.toString(input);
            }
        });

        mSlider.setCount(101);
        mSlider.setMode(DiscreteSlider.MODE_NORMAL);
        mSlider.setProgressOffset(0);
        mSlider.setMinProgress(0);
        mSlider.setTickMarkStep(10);


        mSlider.setOnValueChangedListener(new DiscreteSlider.OnValueChangedListener() {

            @Override
            public void onValueChanged(int progress, boolean fromUser) {
                super.onValueChanged(progress, fromUser);
                Log.i("DiscreteSlider", "Progress: " + progress + ", fromUser: " + fromUser);
            }

            @Override
            public void onValueChanged(int minProgress, int maxProgress, boolean fromUser) {
                super.onValueChanged(minProgress, maxProgress, fromUser);
                Log.i("DiscreteSlider",
                        "MinProgress: " + minProgress + ", MaxProgress: " + maxProgress +
                                ", fromUser: " + fromUser);
            }
        });

        mSlider.setClickable(true);
    }

    private void ScanDevicesList() {

        Intent intent = new Intent(this, ScanActivity.class);
        // startActivity(intent);
        if (Build.VERSION.SDK_INT > 20) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
        //overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
    }

    private void pairedDevicesList() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address

            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }


        //--------------------------------------------------------------------------------------------------------------

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a paired device for connecting");

        LinearLayout parent = new LinearLayout(ChargerActivity.this);
        parent.setBackgroundColor(getResources().getColor(R.color.sun_color));
        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.VERTICAL);

        ListView modeList = new ListView(this);


        //------------------To fixed height of listView------------------------------------
        setListViewHeightBasedOnItems(modeList);
        //RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(200, 0);
        //modeList.setLayoutParams(params);
        //modeList.requestLayout();
        /******************************************************************************************************************
         *
         ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
         params.height = 20;
         modeList.setLayoutParams(params);
         modeList.requestLayout();
         *
         * */
        // ViewGroup.LayoutParams listViewParams = (ViewGroup.LayoutParams)modeList.getLayoutParams();
        //listViewParams.height = 20;
        // modeList.smoothScrollToPosition(3);
/*
        ListAdapter listadp = modeList.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int i = 0; i < listadp.getCount(); i++) {
                View listItem = listadp.getView(i, null, modeList);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = modeList.getLayoutParams();
            params.height = totalHeight + (modeList.getDividerHeight() * (listadp.getCount() - 1));
            modeList.setLayoutParams(params);
            modeList.requestLayout();
        }
*/
        //------------------End of fixed height of listView---------------------------------

        final ArrayAdapter modeAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        modeList.setAdapter(modeAdapter);
        modeList.setOnItemClickListener(myListClickListener);
        builder.setView(modeList);
        //  builder.show();
        dialog = builder.create();
        try {
            dialog.show();

        } catch (Exception e) {
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, 600); //Controlling width and height.


        //-------------------------------------------------------------------------------------------------------------


    }


    private void pairedDevicesListOriginal() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {


            //Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            //Make an intent to start next activity.
            //Intent i = new Intent(DeviceList.this, DeviceList.class);

            //Change the activity.
            //i.putExtra(EXTRA_ADDRESS, address); //this will be received at DataControl (class) Activity
            //startActivity(i);
            new ConnectBT(address, info).execute(); //Call the class to connect
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_exit:
                exitApplication();
                break;
            case R.id.action_share:
                shareApp();
                break;
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_searchList:
                ScanDevicesList();
                break;
            //case R.id.action_pairedList:
            //   pairedDevicesList();
            //   break;

          /* case R.id.graphView:
               Intent i  = new Intent(this, RealtimeLineChartActivity.class);
               i.putExtra("MyModel",   dataModel);
               if (i != null) startActivity(i);
               overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
               break;
           case R.id.tableView:
               Intent intent1  = new Intent(this, TableViewMainActivity.class);
               if (intent1 != null) startActivity(intent1);
               break;*/
            case R.id.action_disconnect:
                Disconnect();
                break;

        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        /*******************************************************************************
         * Navigation Menu Item
         *******************************************************************************/
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            //Toast.makeText(getApplicationContext(), "nav_exit"+item.getItemId(), Toast.LENGTH_LONG).show();
          /*  int n_id= item.getItemId();
            switch (n_id) {
                case R.id.nav_id:
                    pairedDevicesList();
                    return true;
                case R.id.nav_exit:
                    Toast.makeText(getApplicationContext(), "nav_exit", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.nav_digits:
                    finish();
                    return true;
                case R.id.nav_sound:
                    Toast.makeText(getApplicationContext(), "nav_sound", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.nav_type:
                    Toast.makeText(getApplicationContext(), "nav_type", Toast.LENGTH_LONG).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }*/
            return true;
        }

        switch (id) {

            case R.id.action_searchList:
                ScanDevicesList();
                return true;
            //case R.id.action_pairedList:
            //  pairedDevicesList();
            //  return true;
            /*case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }


    }


    public void shareApp() {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            // e.toString();
        }

    }


    public void exitApplication() {
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        // adb.setView(Integer.parseInt("Delete Folder"));
        // adb.setTitle("Exit");
        adb.setMessage("Are you sure you want to exit application?");
        // adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(Build.VERSION.SDK_INT>=16 && Build.VERSION.SDK_INT<21){
                    finishAffinity();
                } else if(Build.VERSION.SDK_INT>=21){
                    finishAndRemoveTask();
                }
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ChargerActivity.this, "Cancel",
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                //finish();
            }
        });
        adb.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        // cameraKitView.onStop();
        super.onStop();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) && shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog confirmationDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    REQUEST_CAMERA_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                    .create();
            confirmationDialog.show();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog confirmationDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    REQUEST_STORAGE_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                    .create();
            confirmationDialog.show();
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
    }


    @Override
    public void onClick(View v) {
        // So we will make
        // int backgroundColor = ContextCompat.getColor(getApplicationContext(), R.color.red);
        switch (v.getId() /*to get clicked view id**/) {

           /* case R.id.startBtn:
                checkSendReceive = false;
                sendStartCommand();
                checkSendReceive = true;
                break;*/

            case R.id.stopBtn:
                checkSendReceive = false;
                stopDataSendThread();
                checkSendReceive = true;
                break;
            default:
                break;
        }
    }

    private void setClock() {

        String data = null;
        String dt = null;
        /*
        *
        * Pattern Examples

Here are a few Java SimpleDateFormat date pattern examples:
Pattern 	Example
dd-MM-yy 	31-01-12
dd-MM-yyyy 	31-01-2012
MM-dd-yyyy 	01-31-2012
yyyy-MM-dd 	2012-01-31
yyyy-MM-dd HH:mm:ss 	2012-01-31 23:59:59
yyyy-MM-dd HH:mm:ss.SSS 	2012-01-31 23:59:59.999
yyyy-MM-dd HH:mm:ss.SSSZ 	2012-01-31 23:59:59.999+0100
EEEEE MMMMM yyyy HH:mm:ss.SSSZ 	Saturday November 2012 10:45:42.720+0100
*
* Symbol  Meaning                Kind         Example
D       day in year             Number        189
E       day of week             Text          E/EE/EEE:Tue, EEEE:Tuesday, EEEEE:T
F       day of week in month    Number        2 (2nd Wed in July)
G       era designator          Text          AD
H       hour in day (0-23)      Number        0
K       hour in am/pm (0-11)    Number        0
L       stand-alone month       Text          L:1 LL:01 LLL:Jan LLLL:January LLLLL:J
M       month in year           Text          M:1 MM:01 MMM:Jan MMMM:January MMMMM:J
S       fractional seconds      Number        978
W       week in month           Number        2
Z       time zone (RFC 822)     Time Zone     Z/ZZ/ZZZ:-0800 ZZZZ:GMT-08:00 ZZZZZ:-08:00
a       am/pm marker            Text          PM
c       stand-alone day of week Text          c/cc/ccc:Tue, cccc:Tuesday, ccccc:T
d       day in month            Number        10
h       hour in am/pm (1-12)    Number        12
k       hour in day (1-24)      Number        24
m       minute in hour          Number        30
s       second in minute        Number        55
w       week in year            Number        27
G       era designator          Text          AD
y       year                    Number        yy:10 y/yyy/yyyy:2010
z       time zone               Time Zone     z/zz/zzz:PST zzzz:Pacific Standard
        *
        * */

      /*  String HH = decimalToHex(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date()));
        String MM = decimalToHex(new SimpleDateFormat("mm", Locale.getDefault()).format(new Date()));
        String SS = decimalToHex(new SimpleDateFormat("ss", Locale.getDefault()).format(new Date()));
        String DT = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
        String DY = decimalToHex(new SimpleDateFormat("dd", Locale.getDefault()).format(new Date()));
        String MH = decimalToHex(new SimpleDateFormat("MM", Locale.getDefault()).format(new Date())).trim();
        String YR = decimalToHex(new SimpleDateFormat("yy", Locale.getDefault()).format(new Date()));
        // String fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());*/

        String HH = toASCII(Integer.parseInt(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date())));
        String MM = toASCII(Integer.parseInt(new SimpleDateFormat("mm", Locale.getDefault()).format(new Date())));
        String SS = toASCII(Integer.parseInt(new SimpleDateFormat("ss", Locale.getDefault()).format(new Date())));
        String DT = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
        String DY = toASCII(Integer.parseInt(new SimpleDateFormat("dd", Locale.getDefault()).format(new Date())));
        String MH = toASCII(Integer.parseInt(new SimpleDateFormat("MM", Locale.getDefault()).format(new Date())));
        String YR = toASCII(Integer.parseInt(new SimpleDateFormat("yy", Locale.getDefault()).format(new Date())));

        for (Map.Entry<String, String> entry : assoc_day.entrySet()) {
            if (entry.getValue().contains(DT)) {
                System.out.println("Found Cat in " + entry.getKey());
                dt = toASCII(Integer.parseInt(entry.getKey()));
            }

        }


        data = "$C1W" + SS + MM + HH + DY + dt + MH + YR + ";";


        try {
            if (btSocket != null) {
                Log.d("checkSendReceive", "" + checkSendReceive);
                //    if(checkSendReceive) {
                Log.d("data_time", " " + data);//data.replaceAll("\\s+", "")
                btSocket.getOutputStream().write(data.getBytes());
                Thread.sleep(300);
                // btSocket.getOutputStream().write( data.replaceAll("\\s+", "").getBytes());
                    /*final Thread receive = new Thread(() -> {
                        try {
                        //    receiveData();
                        }catch (Exception e){}
                    });
                    receive.start();*/

                // }
            }
        } catch (IOException | InterruptedException e) {
            Log.d("timer_thread_stopped", "" + e.getMessage());
            e.printStackTrace();
        }
    }


    public String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }






    /*
    final Handler handler = new Handler();
    Timer timer2 = new Timer();
    TimerTask testing = new TimerTask() {
        public void run() {
            handler.post(new Runnable() {


                public void run() {
                    Toast.makeText(mainActivity.this, "test", Toast.LENGTH_SHORT).show();
                }

            });


        }
    };
    timer2.schedule(testing, 1000);*/


    public static byte[] decode(String hex) {

        String[] list = hex.split("");
        ByteBuffer buffer = ByteBuffer.allocate(list.length);
        System.out.println(list.length);
        for (String str : list)
            buffer.put(Byte.parseByte(str, 16));

        return buffer.array();

    }


    //=================================To Connect Bluetooth Device====================================
    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {

        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        public ConnectBT(String address, String info) {
            super();
            Log.d("infoBLE", "" + address + "  " + info);
            addressBLE = address;
            infoBLE = info;
            //Do stuff
        }


        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ChargerActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {

            if (btSocket != null) {
                try {
                    btSocket.close();
                    btSocket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            try {
                if (btSocket == null || !isBtConnected) {
                    Log.d("connection_error", "" + addressBLE);
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(addressBLE);//connects to the device's address and checks if it's available
                    if (ActivityCompat.checkSelfPermission(ChargerActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                    }
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                    mmOutputStream = btSocket.getOutputStream();
                    mmInputStream = btSocket.getInputStream();

                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
                Log.d("connection_error",""+e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);


            if (!ConnectSuccess)
            {
                Intent intent = getIntent();
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                // finish();
              //  startActivity(intent);
               // Disconnect();
                //getSupportActionBar().setTitle(R.string.app_name);
            }
            else
            {

                isBtConnected = true;
                address = infoBLE.substring(infoBLE.length() - 17);
                name = infoBLE.replace(address, "");
                msg("Device connected.");
                // getSupportActionBar().setTitle(name);
                // connectionStatus.setText(R.string.device_connected);
                // connectionStatus.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.limeGreen));
                bltStatus.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_bluetooth_green));

                runDataSendThread();


   /*
                try {
                    receiveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
            progress.dismiss();

            //beginListenForData();

            new Thread(new Runnable() {
                public void run(){
                    try {
                     //   receiveData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //beginListenForData();
                }
            }).start();

        }
    }



    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    private void Disconnect()
    {
     if (btSocket!=null) //If the btSocket is busy
         {
            try
            {
                btSocket.close(); //close connection
                Toast.makeText(ChargerActivity.this, "Bluetooth device has been disconnected", Toast.LENGTH_LONG).show();
                // getSupportActionBar().setTitle(R.string.app_name);
            }
            catch (IOException e)
            { msg("Error");}
         }else{
         Toast.makeText(ChargerActivity.this, "No device connected", Toast.LENGTH_LONG).show();
     }



    }



    @Override
    public void onBackPressed() {
// TODO Auto-generated method stub
        AlertDialog.Builder builder=new AlertDialog.Builder(ChargerActivity.this);
        // builder.setCancelable(false);
        builder.setTitle("Rate Us if u like this");
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(ChargerActivity.this, "Yes i wanna exit", Toast.LENGTH_LONG).show();

                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(ChargerActivity.this, "i wanna stay on this", Toast.LENGTH_LONG).show();
                dialog.cancel();

            }
        });
        builder.setNeutralButton("Rate",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            }
        });
        AlertDialog alert=builder.create();
        alert.show();
        //super.onBackPressed();
    }


    // Fetch the stored data in onResume()
    // Because this is what will be called
    // when the app opens again
    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //The BroadcastReceiver that listens for bluetooth broadcasts
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Toast.makeText(getApplicationContext(),""+"Device found",Toast.LENGTH_LONG).show();
            }
            else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

                //Toast.makeText(getApplicationContext(),""+"Device connected",Toast.LENGTH_LONG).show();
               // connectionStatus.setText(name.trim()+" device connected");
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //Done searching
                Toast.makeText(getApplicationContext(),""+"Device Searching",Toast.LENGTH_LONG).show();
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                //Device is about to disconnect
                Toast.makeText(getApplicationContext(),""+"Device disconnected",Toast.LENGTH_LONG).show();
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                //Device has disconnected
                // Toast.makeText(getApplicationContext(),name.trim()+" device disconnected",Toast.LENGTH_LONG).show();
                //connectionStatus.setText("No bluetooth device connected");
                //connectionStatus.setTextColor(ContextCompat.getColor(context, R.color.redColor));
                bltStatus.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_bluetooth));
            }
        }
    };

    public void changeName()
    {
/*
        if (myBluetooth != null)
        {
            String sOldName = myBluetooth.getName();
            if (sOldName.equalsIgnoreCase(sNewName) == false)
            {
                final Handler myTimerHandler = new Handler();
                myBluetooth.enable();
                myTimerHandler.postDelayed(
                        new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (myBluetooth.isEnabled())
                                {
                                    myBluetooth.setName(sNewName);
                                    if (sNewName.equalsIgnoreCase(myBluetooth.getName()))
                                    {
                                        Log.i("", "Updated BT Name to " + myBluetooth.getName());
                                        myBluetooth.disable();
                                    }
                                }
                                if ((sNewName.equalsIgnoreCase(myBluetooth.getName()) == false) && (System.currentTimeMillis() < lTimeToGiveUp_ms))
                                {
                                    myTimerHandler.postDelayed(this, 500);
                                    if (myBluetooth.isEnabled())
                                        Log.i("575", "Update BT Name: waiting on BT Enable");
                                    else
                                        Log.i("56", "Update BT Name: waiting for Name (" + sNewName + ") to set in");
                                }
                            }
                        } , 500);
            }
        }
*/
    }
}



