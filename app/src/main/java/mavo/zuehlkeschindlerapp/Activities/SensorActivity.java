package mavo.zuehlkeschindlerapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

import mavo.zuehlkeschindlerapp.R;
import mavo.zuehlkeschindlerapp.SensorAdapter;
import mavo.zuehlkeschindlerapp.SensorController;

/**
 *  SensorActivity.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 *  Builds the main view controller of the app . Manages sensor events
 *  and triggers the SensorController on sensor events and specific
 *  activity lifecycle events.
 */

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorController mSensorController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Context context = getApplicationContext();

        mSensorController = new SensorController(sensorManager, context);
        mSensorController.setup();

        SensorAdapter sensorAdapter = new SensorAdapter(context, sensorManager);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(sensorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is called whenever a SensorEvent is triggered by the OS. According to Android docs,
     * this happens when measured sensor values have changed.
     */
    @Override
    public final void onSensorChanged(SensorEvent event) {
        mSensorController.onSensorEvent(event);
    }

    /**
     * You may also consider triggering logic when the accuracy of a given sensor
     * changes.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorController.registerListener(this);
        mSensorController.scheduleTransferService();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mSensorController.unregisterListener(this);
        mSensorController.cancelAllTransferServices();
    }
}
