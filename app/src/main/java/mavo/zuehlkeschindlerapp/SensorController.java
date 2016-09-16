package mavo.zuehlkeschindlerapp;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import mavo.zuehlkeschindlerapp.Services.FileTransferService;
import mavo.zuehlkeschindlerapp.Services.FileWriteService;
import mavo.zuehlkeschindlerapp.SensorData.AbstractSensorData;
import mavo.zuehlkeschindlerapp.SensorModels.AbstractSensorModel;


/**
 *  SensorController.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 * Responsible for (un)registering active sensors and their transfer services
 * as well as managing and further processing sensor data input.
 */
public class SensorController {

    private static final int TRANSFER_SERVICE_PERIOD = 5000;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


    private Map<SensorType, AbstractSensorModel> availableSensors = new HashMap<>();

    private SensorManager mSensorManager;
    protected Context mContext;

    public SensorController(SensorManager mSensorManager, Context mContext) {
        this.mSensorManager = mSensorManager;
        this.mContext = mContext;
    }

    /**
     * Initialize the system by checking the SensorManager for the presence on the
     * device of all sensors described in the SensorType enum. If present, add available sensors
     * to the member map availableSensors. Then schedule the corresponding transfer services.
     * */
    public void setup() {
        for (SensorType type : SensorType.values()) {
            if(mSensorManager.getDefaultSensor(type.getSensorIdentifier()) != null) {
                availableSensors.put(type, AbstractSensorModel.getSensorModelByType(type));
            }
        }
        scheduleTransferService();
    }

    /**
     * When a new sensor event is triggered, check if corresponding sensor is registered in availableSensors. Then, check if sensor
     * is active. If yes, update the sensor's model by adding new sensor data to data queue. If the queue is returned,
     * write queue data to disk.
     * */
    public void onSensorEvent(SensorEvent event) {
        int eventSensorIdentifier = event.sensor.getType();
        SensorType sensorTypeById = SensorType.getSensorTypeById(eventSensorIdentifier);
        if(availableSensors.containsKey(sensorTypeById)) {
            AbstractSensorModel sensorModel = availableSensors.get(sensorTypeById);
            if (sensorModel.isActive()) {

                long time = System.currentTimeMillis();
                String currentDateTime = mFormat.format(new Date(time));

                Queue<? extends AbstractSensorData> dataQueue = sensorModel.updateAndGetCurrentDataQueue(event, currentDateTime);

                if(dataQueue != null) {
                    new FileWriteService(mContext).execute(dataQueue);
                }
            }
        }
    }

    /**
     * For all available sensors, register the corresponding sensor listener.
    * */
    public void registerListener(SensorEventListener listener) {
        for (SensorType sensorType : availableSensors.keySet()) {
            Sensor sensor = mSensorManager.getDefaultSensor(sensorType.getSensorIdentifier());
            mSensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterListener(SensorEventListener listener) {
        mSensorManager.unregisterListener(listener);
    }

    /**
     * Build the job info, including requiring network info, and schedule transfer service.
     * */
    public void scheduleTransferService() {
        ComponentName serviceName = new ComponentName(mContext, FileTransferService.class);

        JobInfo jobInfo = new JobInfo.Builder(1, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(TRANSFER_SERVICE_PERIOD)
                .build();

        JobScheduler scheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result != JobScheduler.RESULT_SUCCESS) {
            throw new RuntimeException("File Transfer Service couldn't be setup");
        }
        Log.i("SensorCont", "FileTransfer was setup");
    }

    public void cancelAllTransferServices() {
        Log.i("SensorController", "cancel all jobs called");
        JobScheduler scheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancelAll();
    }
}
