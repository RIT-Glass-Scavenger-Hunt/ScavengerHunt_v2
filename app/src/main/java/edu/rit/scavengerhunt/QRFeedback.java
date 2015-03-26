package edu.rit.scavengerhunt;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class QRFeedback extends ActionBarActivity {

    final static int NEXT_TARGET_RESULT = 1;
    public int answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrfeedback);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            answer = extras.getInt("answer");

            if (answer == 1) {
                TextView feedback = (TextView)findViewById(R.id.feedback);
                Button next_target_button = (Button)findViewById(R.id.next_target_button);

                feedback.setText("Correct!");
                next_target_button.setText("Next Target");
            }
            /*String feedback_text = extras.getString("feedback_text");
            String button_text = extras.getString("button_text");

            TextView feedback = (TextView)findViewById(R.id.feedback);
            Button next_target_button = (Button)findViewById(R.id.next_target_button);

            feedback.setText(feedback_text);
            next_target_button.setText(button_text);*/
        }

    }


    public void showNextClue(View v) {
        //basically just close this activity now
        Intent intent = new Intent();
        //---set the data to pass back---
        intent.putExtra("answer", answer);
        setResult(Activity.RESULT_OK, intent);
        //---close the activity---
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qrfeedback, menu);
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
