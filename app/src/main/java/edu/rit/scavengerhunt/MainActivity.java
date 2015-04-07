package edu.rit.scavengerhunt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.location.Location;
import android.location.LocationListener;



public class MainActivity extends ActionBarActivity  {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    static final int QR_SCAN_RESULT = 0;
    static final int NEXT_TARGET_RESULT = 1;
    boolean hint;
    public String[][] location_clues;
    public String[] location_QR;
    public double[] location_lat;
    public double[] location_long;
    public int target_id;
    public int clue_id;
    double userLog;
    double userLat;
    boolean gps = false;
    int counter =0;
    GPSTemperature gpsTemp;
    protected LocationManager locationManager;
    private View view;
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            userLog = location.getLongitude();
            userLat = location.getLatitude();
            doGpsView(userLat,userLog);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    LocationManager lm;
    Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        userLog = location.getLongitude();
        userLat = location.getLatitude();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListener);
        //create location clues
        location_clues = new String[3][3];
        location_clues[0][0] = "Where is Location #1? Clue 1";
        location_clues[0][1] = "Where is Location #1? Clue 2";
        location_clues[0][2] = "Where is Location #1? Clue 3";

        location_clues[1][0] = "Where is Location #2? Clue 1";
        location_clues[1][1] = "Where is Location #2? Clue 2";
        location_clues[1][2] = "Where is Location #2? Clue 3";

        location_clues[2][0] = "Where is Location #3? Clue 1";
        location_clues[2][1] = "Where is Location #3? Clue 2";
        location_clues[2][2] = "Where is Location #3? Clue 3";

        location_QR = new String[3];
        location_QR[0] = "Magic Lab";
        location_QR[1] = "ATM at Crossroads"; //Approx
        location_QR[2] = "Clock at Midnight Oil";

        location_lat = new double[3];
        location_lat[0] = 43.083113;
        location_lat[1] = 43.082629;
        location_lat[2] = 43.0826;

        location_long = new double[3];
        location_long[0] = -77.679786;
        location_long[1] = -77.679788;
        location_long[2] = -77.679678;

        //need to start up the location counter
        target_id = 0;
        clue_id = 0;

        //then add number to the target header
        TextView target_label = (TextView)findViewById(R.id.current_target);
        target_label.setText("Target 1/10:");

        //then show clue
        TextView clue = (TextView)findViewById(R.id.clue_text);
        showFirstClue(clue);
    }


    /*
    *  Shows the first clue.
    * */
    public void showFirstClue(View v) {
        TextView clue = (TextView)v;
        clue.setText(location_clues[target_id][0]);
        View cluePlus = findViewById(R.id.clue_plus);
        cluePlus.setVisibility(View.VISIBLE);
    }

    /*
    *  Shows the next clue for current location.
    * */
    public void showNextClue(View v) {
        TextView target_label = (TextView) findViewById(R.id.current_target);
        TextView clue = (TextView) findViewById(R.id.clue_text);
            clue_id++;
            String target_string = String.valueOf(target_id + 1);
           target_label.setText("Target " + target_string + "/10:");
     switch(clue_id){
          case 0: clue.setText(location_clues[target_id][0]);
              break;
         case 1:
        String message = location_clues[target_id][0]+ "\n"+ location_clues[target_id][1];
              clue.setText(message);
              System.out.println(message);
              break;
          case 2: clue.setText(location_clues[target_id][0]+ "\n"+ location_clues[target_id][1]+ "\n"+ location_clues[target_id][2]);
              break;
         default: clue.setText(location_clues[target_id][0]+ "\n"+ location_clues[target_id][1]+ "\n"+ location_clues[target_id][2] +"\n No more clues!");
             View cluePlus = findViewById(R.id.clue_plus);
             cluePlus.setVisibility(View.GONE);
      }


    }

    /*
    *  Skip - can be only used once in the game.
    *  need to add alert dialog to confirm skipping.
    * */
    public void skip(View v){
        showWarning(MainActivity.this, "Confirmation Message", "You are sure that you want to skip? It can be used once.", "Yes", "No", v).show();
    }
    /* warning Dialog box
    * */
    private  AlertDialog showWarning(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo, View view) {
        final View v = view;
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                View cluePlus = findViewById(R.id.skip);
                cluePlus.setVisibility(View.GONE);
                showNextLocation();
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void doGpsView(double lat, double log){
        if(gps) {
            tempGPS(lat, log);
            counter++;
        }
    }

    public void turnOnGPS(View v){
        gps = true; // turn on the GPS feature.
        view = v;
    }
    public void tempGPS(double lat,double log){
        userLat = lat;
        userLog = log;
       // double userLat = gpsTemp.getUserLat();//location_lat[0];//input location manually for testing. -> should get actual location -> //gpsTemp.getUserLat();
       // double userLog = gpsTemp.getUserLog();//location_long[0];//gpsTemp.getUserLog();
      //  int result  = gpsTemp.calculateDistance(userLat,userLog, location_lat[target_id],location_long[target_id]);
        double result  = distFrom(userLat,userLog, location_lat[target_id],location_long[target_id]);
        System.out.println("location_lat[target_id]:"+location_lat[target_id]);// for testing purposes. need to set [target_id]
        System.out.println("location_long[target_id]:"+location_long[target_id]);
        System.out.println("result:"+result);
        System.out.println("convert:"+convertKMtoInches(result));
      //  double distance = convertKMtoInches(result);
        //update background color
        float[] results = new float[4];
        location.distanceBetween(userLat,userLog,43.07996217,-77.61915561,results );
        String color = hexColors((results[0]*3.28084));
        View main = findViewById(R.id.Main_Layout);
        main.setBackgroundColor(Color.parseColor(color));

       TextView  text = (TextView) findViewById(R.id.other_score);

            text.setText("lat:"+userLat+" log:"+userLog+" c"+counter+ "d"+((int)(results[0]*3.28084)));
    }


    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371.0;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }

    /*
  *  Converts KM to feet
  * */
    public int convertKMtoInches (double km){
        System.out.println("Conv km:"+km);
        double conv = 1000 / 0.3048;
        double feet = Math.round((km*conv));
        return (int) feet;
    }

    /*
    * The colors change within 510 inches, if above it will stay blue.
    * */
    public String hexColors(double distance){
        String color = "#";
        int blue = 0;
        String green = "00";
        int red = 0;
        if(distance >= 255){ //higher than 255 feet (blue hue)
            red = (int)(510-distance); //how much red should blend in with blue.
            if(red<= 0){
                red = 0;
            }else if(red>=255){
                red = 255;
            }
            blue = 255;
            System.out.println("red:"+red);

        }else{ //below 255 feet (red hue)
            blue = (int)(distance); //how much blue should blend in with red.
            if(blue<= 5 || distance == 0){
                blue = 0;
            }else if(blue>=255){
                blue = 255;
            }else{
                blue =+ 50;
            }
            red = 255;
            System.out.println("blue:"+blue);
        }

        if(Integer.toHexString(red).length()==2){
            color += Integer.toHexString(red);
        }else{
            color += "0"+Integer.toHexString(red);
        }
        color += green;

        if(Integer.toHexString(blue).length()==2){
            color += Integer.toHexString(blue);
        }else{
            color += "0"+Integer.toHexString(blue);
        }
        System.out.println("color:"+color);
        return color;
    }

    /*
    *  Scans QR code.
    *  It will automatically ask to download scanner code if there is no scanner found.
    * */
    public void scanQR(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, QR_SCAN_RESULT);
        } catch (ActivityNotFoundException anfe) {
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    /*
    *  Show the next location.
    * */
    private void showNextLocation() {
        gps = false; // turning off the GPS temp feature until user wants to use it.
        TextView target_label = (TextView) findViewById(R.id.current_target);
        TextView clue = (TextView) findViewById(R.id.clue_text);

        if (target_id < 2) {
            target_id++;
            clue.setText(location_clues[target_id][0]);

            String target_string = String.valueOf(target_id + 1);
            target_label.setText("Target " + target_string + "/10:");
        }
        else {
            target_label.setText("Done!");
            clue.setText("No more clues!");
        }

    }

    /*
    *  Alert Dialog for to download the scanner.
    * */
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }


    /*
    *  The QR scan activity result
    * */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == QR_SCAN_RESULT) {
            if (resultCode == RESULT_OK) {

                String contents = intent.getStringExtra("SCAN_RESULT");
                int answer = 0;

                /*String feedback_text = "Sorry, incorrect! Try a different location!";
                String button_text = "Back to Clue";*/

                if (contents.equals(location_QR[target_id])) {
                    answer = 1;
                }

                Intent intent2 = new Intent(getApplicationContext(), QRFeedback.class);
                intent2.putExtra("answer", answer);
                startActivityForResult(intent2, NEXT_TARGET_RESULT);
            }
        }
        else if (requestCode == NEXT_TARGET_RESULT) {
            if (resultCode == RESULT_OK) {
                int answer = intent.getIntExtra("answer", 0);

                if (answer == 1) {
                    showNextLocation();
                }
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
