package mavo.zuehlkeschindlerapp.SensorModels;

import android.hardware.SensorEvent;

import mavo.zuehlkeschindlerapp.SensorData.StepCounterData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  StepCounterModel.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 */
public class StepCounterModel extends AbstractSensorModel {

    private static StepCounterModel instance = null;

    private StepCounterModel() {
    }

    public static synchronized StepCounterModel getInstance() {
        if (instance == null){
            instance = new StepCounterModel();
        }
        return instance;
    }

    @Override
    protected StepCounterData getLastDataEntry(SensorEvent event, String dateTime) {
        String stepsSinceLastReboot = Float.toString(event.values[0]);
        String timestamp = dateTime;

        return new StepCounterData(stepsSinceLastReboot, timestamp);
    }

}
