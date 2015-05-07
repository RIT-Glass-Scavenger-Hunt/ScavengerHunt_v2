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
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Location;
import android.location.LocationListener;


public class MainActivity extends ActionBarActivity  {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    static final int QR_SCAN_RESULT = 0;
    static final int NEXT_TARGET_RESULT = 1;
    static final int GAME_TIME = 2700000;

    private TextView textTimer;
    //private Button startButton;
    //private Button pauseButton;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;

    boolean hint;
    public String[][] location_clues;
    public String[] location_QR;
    public double[] location_lat;
    public double[] location_long;
    public int target_id;
    public int clue_id;
    static int score = 0;
    public int target_score = 0;
    String team_name;
    double userLog ;
    double userLat;
    boolean gps = false;
    int counter =0;
    GPSTemperature gpsTemp;
    protected LocationManager locationManager;
    private View view;
    
    @Override
    public void onBackPressed(){

    }
    
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
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        if (location!=null) {
            userLog = location.getLongitude();
            userLat = location.getLatitude();
        }
        //create location clues
        location_clues = new String[10][3];
        location_clues[0][0] = "Where can you get a free copy of the New York Times?";
        location_clues[0][1] = "You can also sit here next to a fireplace while looking at a giant clock.";
        location_clues[0][2] = "You can also order coffee here.";

        location_clues[1][0] = "What is known as Geek Heaven?";
        location_clues[1][1] = "It is also known as the Student Innovation Hall.";
        location_clues[1][2] = "It is surrounded by glass and is filled with projectors.";

        location_clues[2][0] = "You can get your tan here.";
        location_clues[2][1] = "You can look professional after getting service from this place.";
        location_clues[2][2] = "This place will remove your dead cells or doing add-ons to your dead cells.";

        location_clues[3][0] = "You can order Western Union money orders here.";
        location_clues[3][1] = "You can buy boxes here.";
        location_clues[3][2] = "This place always close on Sunday.";

        location_clues[4][0] = "Where can you buy hand-made gifts?";
        location_clues[4][1] = "Where can you buy hand-made desk clock?";
        location_clues[4][2] = "Where can you buy glass pen?";

        location_clues[5][0] = "Where can you find a wall covered in different languages?";
        location_clues[5][1] = "Where should you go if you want to enroll other campus other than Rochester, NY?";
        location_clues[5][2] = "Where should you go to gain your international experience?";

        location_clues[6][0] = "Where can you find out about research about future everyday technologies?";
        location_clues[6][1] = "You can see this building when looking out from the Student Innovation Center.";
        location_clues[6][2] = "The building name is also a color.";

        location_clues[7][0] = "Where can you seek out the dean for the College of Applied Science and Technology?";
        location_clues[7][1] = "The same building also includes the office for Women in Technology.";
        location_clues[7][2] = "You’ll find the building entrance near a fountain.";

        location_clues[8][0] = "You will see a white board of RIT Aero design team.";
        location_clues[8][1] = "Where can you find a model of rocket launch vehicle?";
        location_clues[8][2] = "Where can you find a model of formula one racecar?";

        location_clues[9][0] = "What building contains the departments for computer science, information technology, and software engineering?";
        location_clues[9][1] = "You can see the Dean’s office from here.";
        location_clues[9][2] = "You can get a “byte” at CTRL ALT DELi.";



        location_QR = new String[10];
        location_QR[0] = "1"; //Midnight Oil
        location_QR[1] = "2"; //Magic Lab
        location_QR[2] = "3"; //Shear Global Salon
        location_QR[3] = "4"; //Post Office
        location_QR[4] = "5"; //Shop One
        location_QR[5] = "6"; //Study Abroad Center
        location_QR[6] = "7"; //Orange Hall/FET Lab
        location_QR[7] = "8"; //Color Science
        location_QR[8] = "9"; //Aviation Lab
        location_QR[9] = "10"; //Entrance of Golisano Hall


        location_lat = new double[10];

        location_lat[0]=  43.082541; //Midnight Oil
        location_lat[1] = 43.083085; //Magic Lab
        location_lat[2] = 43.082657;//Shear Global Salon
        location_lat[3] = 43.082597; //Post Office
        location_lat[4] = 43.082762; //Shop One
        location_lat[5] = 43.083172;//Study Abroad Center
        location_lat[6] = 43.083729 ; //Orange Hall/FET Lab
        location_lat[7] = 43.082548; //Color Science
        location_lat[8] = 43.084624; //Aviation Lab
        location_lat[9] = 43.084658;  //Entrance of Golisano Hall




        location_long = new double[10];
        location_long[0] =-77.679751; //Midnight Oil
        location_long[1] = -77.679854;//Magic Lab
        location_long[2] = -77.680929;//Shear Global Salon
        location_long[3] = -77.680686; //Post Office
        location_long[4] =  -77.680389; //Shop One
        location_long[5] = -77.680978; //Study Abroad Center
        location_long[6] = -77.678980; //Orange Hall/FET Lab
        location_long[7] = -77.678461; //Color Science
        location_long[8] = -77.678362; //Aviation Lab
        location_long[9] = -77.679969;  //Entrance of Golisano Hall

        //need to start up the location counter
        target_id = 0;
        clue_id = 0;

        //set timer
        textTimer = (TextView) findViewById(R.id.textTimer);

        //start timer
        startTime = SystemClock.uptimeMillis();
        myHandler.postDelayed(updateTimerMethod, 0);

       /* startButton = (Button) findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod, 0);
            }
        }    );
        pauseButton = (Button) findViewById(R.id.btnPause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);

            }
        });*/

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
        clue_id = 0;
       // score = 0; //RESET THE SCORE if the user want to play the game again.
        target_score = 10;
        clue.setText("-"+location_clues[target_id][clue_id]);
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
          case 0: clue.setText("-"+ location_clues[target_id][0]);
              break;
         case 1:
        String message = "-"+location_clues[target_id][0]+ "\n-"+ location_clues[target_id][1];
              target_score = target_score - 2;
              clue.setText(message);
              //System.out.println(message);
              break;
          case 2: clue.setText("-"+location_clues[target_id][0]+ "\n-"+ location_clues[target_id][1]+ "\n-"+ location_clues[target_id][2]);
              target_score = target_score - 2;
             // View cluePlus = findViewById(R.id.clue_plus);
            //  cluePlus.setVisibility(View.GONE);
              break;
         default: clue.setText("-"+location_clues[target_id][0]+ "\n-"+ location_clues[target_id][1]+ "\n-"+ location_clues[target_id][2] +"\n-No more clues!");
             View cluePlusAgain = findViewById(R.id.clue_plus);
             cluePlusAgain.setVisibility(View.GONE);
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
                target_score = 0; //default score should be set... if skip lose points.
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
            System.out.println("GPS is turned on.");
        }
    }

    public void turnOnGPS(View v) {
        view = v;
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            alertNoGPS(MainActivity.this, "Warning Message", "Currently, your phone does not support GPS", "Ok", v).show();
        } else {
            if(!gps) { //take 5 points off once.
                target_score = target_score - 5;
            }
            doGpsView(userLat, userLog);
        }
        gps = true; // turn on the GPS feature.

    }

 /* warning Dialog box
    * */
        private  AlertDialog alertNoGPS(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, View view) {
            final View v = view;
            AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
            downloadDialog.setTitle(title);
            downloadDialog.setMessage(message);
            downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            return downloadDialog.show();
    }
    public void tempGPS(double lat,double log){
        userLat = lat;
        userLog = log;
        //double userLat = gpsTemp.getUserLat();//location_lat[0];//input location manually for testing. -> should get actual location -> //gpsTemp.getUserLat();
       // double userLog = gpsTemp.getUserLog();//location_long[0];//gpsTemp.getUserLog();
      //  int result  = gpsTemp.calculateDistance(userLat,userLog, location_lat[target_id],location_long[target_id]);
        //double result  = distFrom(userLat,userLog, location_lat[target_id],location_long[target_id]);
        System.out.println("location_lat[target_id]:"+location_lat[target_id]);// for testing purposes. need to set [target_id]
        System.out.println("location_long[target_id]:"+location_long[target_id]);
      //  System.out.println("result:"+result);
     //   System.out.println("convert:"+convertKMtoInches(result));
    //    double distance = convertKMtoInches(result);
        //update background color
        float[] results = new float[4];
        location.distanceBetween(userLat,userLog,location_lat[target_id],location_long[target_id],results );
        String color = hexColors((int)(results[0]*5));
        System.out.println("distance to... "+results[0]*5);
        View main = findViewById(R.id.Main_Layout);
        main.setBackgroundColor(Color.parseColor(color));

       TextView  text = (TextView) findViewById(R.id.distance);
        text.setText("Distance away: "+(results[0]*5));
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
       // double conv = 1000 / 0.3048;
        double feet = Math.round((km*3280.8));
        return (int) feet;
    }

    /*
    * The colors change within 510 inches, if above it will stay blue.
    * */
    public String hexColors(int distance){
        String color = "#";
        int blue = 0;
        String green = "00";
        int red = 0;
        if(distance >= 255){ //higher than 255 feet (blue hue)
            red = (510-distance); //how much red should blend in with blue.
            if(red<= 0){
                red = 0;
            }else if(red>=255){
                red = 255;
            }
            blue = 255;
            System.out.println("red:"+red);

        }else{ //below 255 feet (red hue)
            System.out.println(distance);
            blue = (distance); //how much blue should blend in with red.
            if(distance<= 10 || distance == 0){
                blue = 0;
            }else if(distance>=255){
               blue = 255;
            }else{
               // blue =+ 50;
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
        score = score + target_score;
        target_score = 10;
        gps = false; // turning off the GPS temp feature until user wants to use it.
        TextView target_label = (TextView) findViewById(R.id.current_target);
        TextView clue = (TextView) findViewById(R.id.clue_text);
        //reset background color
        View main = findViewById(R.id.Main_Layout);
        main.setBackgroundColor(Color.BLACK);

        //also remove distance value
        TextView  text = (TextView) findViewById(R.id.distance);
        text.setText("");

        if (target_id < 9) {
            target_id++;
           // clue.setText(location_clues[target_id][0]);
            showFirstClue(clue);
            String target_string = String.valueOf(target_id + 1);
            target_label.setText("Target " + target_string + "/10:");
        }
        else {
            endGame();
        }

        //Bundle bundle = getIntent().getExtras();
        //team_name = bundle.getString("team");

        //UpdateScore myScore = new UpdateScore();
        //myScore.execute(team_name, "" + score);

        //update score on screen
        TextView teamScore = (TextView) findViewById(R.id.team_score);
        teamScore.setText("" + score);

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

    private void endGame() {
        Intent intent = new Intent(this, End.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;
            finalTime=GAME_TIME-finalTime;

            if (finalTime <= 0) {
                endGame();
                return;
            }

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);
            textTimer.setText("" + minutes + ":"
                    + String.format("%02d", seconds));

            //+ ":" + String.format("%03d", milliseconds));
            myHandler.postDelayed(this, 0);
        }

    };





}
