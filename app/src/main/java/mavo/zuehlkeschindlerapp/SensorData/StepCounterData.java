package mavo.zuehlkeschindlerapp.SensorData;

import org.json.JSONException;
import org.json.JSONObject;

import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  StepCounterData.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 * Models data generated by the step counter sensor.
 */
public class StepCounterData extends AbstractSensorData {

    private String stepsSinceLastReboot;

    public StepCounterData(String stepsSinceLastReboot, String timestamp) {
        super(SensorType.STEP_COUNTER, timestamp);
        this.stepsSinceLastReboot = stepsSinceLastReboot;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject object = super.toJSON();
        object.put("stepsSinceLastReboot", this.stepsSinceLastReboot);
        return object;
    }
}
