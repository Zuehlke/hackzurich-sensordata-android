package mavo.zuehlkeschindlerapp.SensorModels;


import android.hardware.SensorEvent;

import mavo.zuehlkeschindlerapp.SensorData.GyroscopeData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  GyroscopeModel.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.Created by mavo on 25/08/16.
 */
public class GyroscopeModel extends AbstractSensorModel {

    private static GyroscopeModel instance = null;

    private GyroscopeModel() {
    }

    public static synchronized GyroscopeModel getInstance() {
        if (instance == null){
            instance = new GyroscopeModel();
        }
        return instance;
    }

    @Override
    protected GyroscopeData getLastDataEntry(SensorEvent event, String dateTime) {
        String xAngV = Float.toString(event.values[0]);
        String yAngV = Float.toString(event.values[1]);
        String zAngV = Float.toString(event.values[2]);
        String timestamp = dateTime;

        return new GyroscopeData(xAngV, yAngV, zAngV, timestamp);
    }
}
