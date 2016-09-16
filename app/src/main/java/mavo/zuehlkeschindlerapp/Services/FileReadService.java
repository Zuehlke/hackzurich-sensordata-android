package mavo.zuehlkeschindlerapp.Services;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mavo.zuehlkeschindlerapp.Services.Data.SensorDataFile;

/**
 *  FileReadService.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 *  Handles file output operations.
 */
public class FileReadService {

    private Context mContext;

    public FileReadService(Context mContext) {
        this.mContext = mContext;
    }

    /**
     *  Retrieves a list of all SensorData files currently stored on disk sorted by timestamp,
     *  older files coming first, newer files coming last.
     */
    public List<SensorDataFile> getFilesFromDisk() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getActiveNetworkInfo() == null) {
            return null;
        }

        File[] files = mContext.getFilesDir().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.matches("\\d+\\.\\D+");
            }
        });

        List<SensorDataFile> sensorDataFileList = new ArrayList<>();
        for (File file : files) {
            sensorDataFileList.add(new SensorDataFile(file, mContext));
        }
        Collections.sort(sensorDataFileList);
        return sensorDataFileList;
    }
}
