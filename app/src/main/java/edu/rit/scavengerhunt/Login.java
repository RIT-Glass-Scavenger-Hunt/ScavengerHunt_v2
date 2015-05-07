package edu.rit.scavengerhunt;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends ActionBarActivity {

    EditText TEAM_NAME;
    String team_name;

    Button sub;

    Context context = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        context = this;

        TEAM_NAME = (EditText) findViewById(R.id.team_name);
        sub = (Button) findViewById(R.id.signin_button);
        
        /*sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team_name = TEAM_NAME.getText().toString();
              //  new NewTeam().execute(team_name);
                //Call next activity here.
            }
        });*/
    }

    public void signIn(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        //EditText editText = (EditText) findViewById(R.id.team_name);
        //team_name = editText.getText().toString();

        //NewTeam myTeam = new NewTeam();
        //team_name = myTeam.createTeam();

        //intent.putExtra("team", team_name);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
