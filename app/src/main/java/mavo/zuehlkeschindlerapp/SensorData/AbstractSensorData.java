package mavo.zuehlkeschindlerapp.SensorData;

import org.json.JSONException;
import org.json.JSONObject;

import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  AbstractSensorData.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 * Creates a general abstract model for sensor data. This class
 * is extended by all other classes in this package to model
 * individual sensor data values.
 */

public abstract class AbstractSensorData{

    private SensorType sensorType;
    /**
     * The event timestamp stores the time in nanoseconds since the last device boot.
     * In case you need an absolute timestamp, consider calling System.currentTimeInMillis
     * when registering an event.
     */
    private String timestamp;

    public AbstractSensorData(SensorType sensorType, String timestamp) {
        this.sensorType = sensorType;
        this.timestamp = timestamp;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    /**
     * Generates a JSON Object out of a AbstractSensorData object. This method
     * is used by FileWriteService to write sensor data values into the file
     * system as JSON array.
     *
     * The format of the JSON Object can be shown using an accelerometer data point as an example:
     *
     * {
     *     "type": "Accelerometer",
     *     "date": "2016-09-013T12:08:56.235-0700",
     *     "x": "0.637745",
     *     "y": "-2.3455",
     *     "z": "0.6756464"
     * }
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("type", getSensorType());
        object.put("date", timestamp);
        return object;
    }

}
