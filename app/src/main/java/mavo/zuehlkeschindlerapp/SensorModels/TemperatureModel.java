package mavo.zuehlkeschindlerapp.SensorModels;

import android.hardware.SensorEvent;

import mavo.zuehlkeschindlerapp.SensorData.TemperatureData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  TempratureModel.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 */
public class TemperatureModel extends AbstractSensorModel {

    private static TemperatureModel instance = null;

    private TemperatureModel() {
    }

    public static synchronized TemperatureModel getInstance() {
        if (instance == null){
            instance = new TemperatureModel();
        }
        return instance;
    }

    @Override
    protected TemperatureData getLastDataEntry(SensorEvent event, String dateTime) {
        String temperatureInCelsius = Float.toString(event.values[0]);
        String timestamp = dateTime;

        return new TemperatureData(temperatureInCelsius, timestamp);
    }

}