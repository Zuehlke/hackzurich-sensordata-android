package mavo.zuehlkeschindlerapp.SensorModels;


import android.hardware.SensorEvent;

import java.util.LinkedList;
import java.util.Queue;

import mavo.zuehlkeschindlerapp.SensorData.AbstractSensorData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  AbstractSensorModel.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 * Handles incoming sensor data by converting unstructured sensor event data to structured data objects.
 * Also stores and keeps track of data in local cache before writing it to disk.
 */
public abstract class AbstractSensorModel {

    protected static final int CACHE_SIZE = 100;

    private boolean isActive;
    private Queue<AbstractSensorData> dataQueue = new LinkedList<>();

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Abstract function that converts sensor event to structured sensor data object.
     * */
    protected abstract AbstractSensorData getLastDataEntry(SensorEvent event, String timestamp);

    /**
     * Add new sensor data entry to local queue. When queue reaches cache size limit, send a copy
     * of it to the controller for further processing and clear the queue before new data can be added.
     * While queue size is smaller than cache size, return null. Queue manipulation is thread-safe.
     * */
    public Queue<? extends AbstractSensorData> updateAndGetCurrentDataQueue(SensorEvent event, String timestamp) {
        AbstractSensorData lastDataEntry = getLastDataEntry(event, timestamp);
        synchronized (this) {
            dataQueue.add(lastDataEntry);

            if(dataQueue.size() > CACHE_SIZE) {
                Queue<AbstractSensorData> queueCopy = new LinkedList<>(dataQueue);
                dataQueue.clear();
                return queueCopy;
            } else {
                return null;
            }
        }
    }

    public static AbstractSensorModel getSensorModelByType(SensorType type) {
        switch (type) {
            case BAROMETER:             return BarometerModel.getInstance();
            case ACCELEROMETER:         return AccelerometerModel.getInstance();
            case MAGNETOMETER:          return MagnetometerModel.getInstance();
            case GYROSCOPE:             return GyroscopeModel.getInstance();
            case LIGHT:                 return LightSensorModel.getInstance();
            case LINEAR_ACCELERATION:   return LinearAccelerometerModel.getInstance();
            case TEMPERATURE:           return TemperatureModel.getInstance();
            case STEP_COUNTER:          return StepCounterModel.getInstance();
            default:                    return null;
        }
    }
}
