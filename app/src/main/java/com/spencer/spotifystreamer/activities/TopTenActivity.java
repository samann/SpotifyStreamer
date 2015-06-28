package com.spencer.spotifystreamer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import com.spencer.spotifystreamer.R;
import com.spencer.spotifystreamer.fragments.TopTenActivityFragment;

public class TopTenActivity extends Activity {

    private String artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().hasExtra("artistName")) {
                artistName = getIntent().getStringExtra("artistName");
            } else {
                artistName = "";
            }
            getFragmentManager().beginTransaction()
                    .add(R.id.top_ten_container, new TopTenActivityFragment())
                    .commit();
        } else {
            artistName = savedInstanceState.getString("artistName");
        }
        assert getActionBar() != null;
        getActionBar().setTitle(R.string.app_name);
        getActionBar().setSubtitle(artistName);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_ten, menu);
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
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("artistName", artistName);
    }
}
