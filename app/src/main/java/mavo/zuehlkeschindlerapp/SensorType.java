package mavo.zuehlkeschindlerapp;

import android.hardware.Sensor;

/**
 * SensorType.java
 * AndroidSensorApp
 * <p/>
 * © 2016 Zühlke Engineering AG. All rights reserved.
 * <p/>
 * Lists all sensors whose data we would like to read.
 */
public enum SensorType {

    BAROMETER(Sensor.TYPE_PRESSURE),
    ACCELEROMETER(Sensor.TYPE_ACCELEROMETER),
    MAGNETOMETER(Sensor.TYPE_MAGNETIC_FIELD),
    GYROSCOPE(Sensor.TYPE_GYROSCOPE),
    LIGHT(Sensor.TYPE_LIGHT),
    LINEAR_ACCELERATION(Sensor.TYPE_LINEAR_ACCELERATION),
    TEMPERATURE(Sensor.TYPE_AMBIENT_TEMPERATURE),
    STEP_COUNTER(Sensor.TYPE_STEP_COUNTER);

    private int sensorIdentifier;

    SensorType(int sensorIdentifier) {
        this.sensorIdentifier = sensorIdentifier;
    }

    public static SensorType getSensorTypeById(int sensorIdentifier) {
        for (SensorType sensorType : SensorType.values()) {
            if (sensorType.getSensorIdentifier() == sensorIdentifier) {
                return sensorType;
            }
        }
        throw new RuntimeException("SensorType not found for id " + sensorIdentifier);
    }

    public int getSensorIdentifier() {
        return sensorIdentifier;
    }

        public String toString() {
                return capitalize(this.name());
        }

        private String capitalize(final String line) {
                return Character.toUpperCase(line.charAt(0)) + line.substring(1).toLowerCase();
        }

}
