package mavo.zuehlkeschindlerapp.SensorModels;

import android.hardware.SensorEvent;

import mavo.zuehlkeschindlerapp.SensorData.LightSensorData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  LightSensorModel.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 */
public class LightSensorModel extends AbstractSensorModel {

    private static LightSensorModel instance = null;

    private LightSensorModel() {
    }

    public static synchronized LightSensorModel getInstance() {
        if (instance == null){
            instance = new LightSensorModel();
        }
        return instance;
    }

    @Override
    protected LightSensorData getLastDataEntry(SensorEvent event, String dateTime) {
        String ambientLevel = Float.toString(event.values[0]);
        String timestamp = dateTime;

        return new LightSensorData(ambientLevel, timestamp);
    }
}
