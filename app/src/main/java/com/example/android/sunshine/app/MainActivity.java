package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForcastFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        if (id == R.id.action_location) {
            showLocationOnMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLocationOnMap() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String zipcodePref = sharedPreferences.getString("location", "94043");
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q", zipcodePref).build();
        Log.d("LOCATIONURI", geoLocation.toString());
        Intent intentLocation = new Intent(Intent.ACTION_VIEW);
        intentLocation.setData(geoLocation);
        if (intentLocation.resolveActivity(getPackageManager()) != null) {
            startActivity(intentLocation);
        } else {
            Log.e("LOCATION ERROR", "No Map App available!");
        }
    }

}
