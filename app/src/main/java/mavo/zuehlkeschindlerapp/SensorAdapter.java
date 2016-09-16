package mavo.zuehlkeschindlerapp;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import mavo.zuehlkeschindlerapp.SensorModels.AbstractSensorModel;

/**
 *  SensorAdapter.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 *  Adapter for list view items in the UI's sensor list.
 */
public class SensorAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private SensorManager mSensorManager;
    private Context mContext;
    private List<SensorType> mSensorTypes;
    private Toast currentToast;

    public SensorAdapter(Context mContext, SensorManager sensorManager) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mSensorManager = sensorManager;
        this.mSensorTypes = Arrays.asList(SensorType.values());
    }

    @Override
    public int getCount() {
        return SensorType.values().length;
    }

    @Override
    public Object getItem(int position) {
        return mSensorTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Add sensor name string to the list item and add activation functionality to the switch button.
     * */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sensor_list_item, null);
        }

        TextView sensorName = (TextView) convertView.findViewById(R.id.sensor_list_view_item_title);
        SwitchCompat aSwitch = (SwitchCompat) convertView.findViewById(R.id.sensor_list_view_item_switch);

        SensorType sensorType = (SensorType) getItem(position);
        final String sensorString = getSensorResourceString(sensorName, sensorType);

        prepareSwitch(aSwitch, sensorType);

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SensorType sensorType = (SensorType) getItem(position);
                AbstractSensorModel sensorModel = AbstractSensorModel.getSensorModelByType(sensorType);
                String toast;

                if (sensorModel.isActive()) {
                    sensorModel.setActive(false);
                    toast = sensorString + " is now inactive";
                } else {
                    sensorModel.setActive(true);
                    toast = sensorString + " is now active";
                }

                if(currentToast != null) {
                    currentToast.cancel();
                }
                currentToast = Toast.makeText(mContext, toast, Toast.LENGTH_LONG);
                currentToast.show();
            }
        });
        return convertView;
    }

    /**
     * If sensor is not available, disable corresponding switch in the UI. If sensor is active, set
     * corresponding switch to "ON".
     * */
    private void prepareSwitch(SwitchCompat aSwitch, SensorType sensorType) {
        if (mSensorManager.getDefaultSensor(sensorType.getSensorIdentifier()) == null) {
            aSwitch.setEnabled(false);
        }

        AbstractSensorModel sensorModel = AbstractSensorModel.getSensorModelByType(sensorType);
        if (sensorModel.isActive()) {
            aSwitch.toggle();
        }
    }
    /**
     * Get sensor resource string.
     * */
    @NonNull
    private String getSensorResourceString(TextView sensorName, SensorType sensorType) {
        int sensorStringId = mContext.getResources().getIdentifier(sensorType.name(), "string", mContext.getPackageName());
        final String sensorString = mContext.getResources().getString(sensorStringId);
        sensorName.setText(sensorString);
        return sensorString;
    }
}
