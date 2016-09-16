package mavo.zuehlkeschindlerapp.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import mavo.zuehlkeschindlerapp.SensorData.AbstractSensorData;
import mavo.zuehlkeschindlerapp.SensorType;

/**
 *  FileWriteService.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 *  Handles file input operations. This class only contains one background task which converts
 *  a queue of structured java objects into a JSON array and stores on a file in the local filesystem.
 *
 */

public class FileWriteService extends AsyncTask<Queue<? extends AbstractSensorData>, Void, Void> {

    private Context mContext;

    public FileWriteService(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Queue<? extends AbstractSensorData>... queues) {

        LinkedList<? extends AbstractSensorData> newQueue;

        if (queues[0] instanceof LinkedList && queues[0].size() > 0) {
            newQueue = (LinkedList) queues[0];
        } else {
            return null;
        }

        SensorType sensorType = newQueue.getFirst().getSensorType();

        JSONArray jsonArray = new JSONArray();

        for (AbstractSensorData data : newQueue) {
            JSONObject object;

            try {
                object = data.toJSON();
            } catch (JSONException e) {
                continue;
            }
            jsonArray.put(object);
        }

        String fileName = System.currentTimeMillis() + "." + sensorType.name();


        try (FileOutputStream fileOutputStream = mContext.openFileOutput(fileName, mContext.MODE_PRIVATE);
             PrintWriter printWriter = new PrintWriter(fileOutputStream)){
            printWriter.print(jsonArray);
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
