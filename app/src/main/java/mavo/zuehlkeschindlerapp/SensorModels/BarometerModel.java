package mavo.zuehlkeschindlerapp.SensorModels;

import android.hardware.SensorEvent;

import mavo.zuehlkeschindlerapp.SensorData.BarometerData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  BarometerModel.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 */
public class BarometerModel extends AbstractSensorModel {

    private static BarometerModel instance = null;

    private BarometerModel() {
    }

    public static synchronized BarometerModel getInstance() {
        if (instance == null){
            instance = new BarometerModel();
        }
        return instance;
    }

    @Override
    protected BarometerData getLastDataEntry(SensorEvent event, String dateTime) {
        String pressure = Float.toString(event.values[0]);
        String timestamp = dateTime;

        return new BarometerData(pressure, timestamp);
    }
}
