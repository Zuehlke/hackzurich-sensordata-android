package mavo.zuehlkeschindlerapp.SensorModels;

import android.hardware.SensorEvent;

import mavo.zuehlkeschindlerapp.SensorData.MagnetometerData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  MagnetometerModel.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 */
public class MagnetometerModel extends AbstractSensorModel {

    private static MagnetometerModel instance = null;

    private MagnetometerModel() {
    }

    public static synchronized MagnetometerModel getInstance() {
        if (instance == null){
            instance = new MagnetometerModel();
        }
        return instance;
    }

    @Override
    protected MagnetometerData getLastDataEntry(SensorEvent event, String dateTime) {
        String xMagn = Float.toString(event.values[0]);
        String yMagn = Float.toString(event.values[1]);
        String zMagn = Float.toString(event.values[2]);
        String timestamp = dateTime;

        return new MagnetometerData(xMagn, yMagn, zMagn, timestamp);
    }

}
