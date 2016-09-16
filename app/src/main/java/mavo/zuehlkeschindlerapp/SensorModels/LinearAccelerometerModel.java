package mavo.zuehlkeschindlerapp.SensorModels;

import android.hardware.SensorEvent;

import mavo.zuehlkeschindlerapp.SensorData.LinearAccelerationData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  LinearAccelerometerModel.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 */
public class LinearAccelerometerModel extends AbstractSensorModel {

    private static LinearAccelerometerModel instance = null;

    private LinearAccelerometerModel() {
    }

    public static synchronized LinearAccelerometerModel getInstance() {
        if (instance == null){
            instance = new LinearAccelerometerModel();
        }
        return instance;
    }

    @Override
    protected LinearAccelerationData getLastDataEntry(SensorEvent event, String dateTime) {
        String xAngV = Float.toString(event.values[0]);
        String yAngV = Float.toString(event.values[1]);
        String zAngV = Float.toString(event.values[2]);
        String timestamp = dateTime;

        return new LinearAccelerationData(xAngV, yAngV, zAngV, timestamp);
    }

}
