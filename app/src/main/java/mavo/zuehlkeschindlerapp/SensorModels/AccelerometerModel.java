package mavo.zuehlkeschindlerapp.SensorModels;

import android.hardware.SensorEvent;

import mavo.zuehlkeschindlerapp.SensorData.AccelerometerData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  AccelerometerModel.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 */

public class AccelerometerModel extends AbstractSensorModel {

    private static AccelerometerModel instance = null;

    private AccelerometerModel() {
    }

    public static synchronized AccelerometerModel getInstance() {
        if (instance == null){
            instance = new AccelerometerModel();
        }
        return instance;
    }

    @Override
    protected AccelerometerData getLastDataEntry(SensorEvent event, String dateTime) {
        String xAcc = Float.toString(event.values[0]);
        String yAcc = Float.toString(event.values[1]);
        String zAcc = Float.toString(event.values[2]);
        String timestamp = dateTime;

        return new AccelerometerData(xAcc, yAcc, zAcc, timestamp);
    }

}
