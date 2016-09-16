package mavo.zuehlkeschindlerapp.Services.Data;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *  SensorDataFile.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 *  Abstraction from the file class to simplify file manipulation
 *  in the FileTransferService.
 */
public class SensorDataFile implements Comparable<SensorDataFile>{

    private final File mFile;
    private final long mCreationDate;
    private final Context context;

    public SensorDataFile(File file, Context context) {
        this.mFile = file;
        this.mCreationDate = getCreationDate(file);
        this.context = context;
    }

    public String readFileContent() {
        if (mFile == null) {
            return null;
        }
        try (InputStreamReader inputStreamReader = new InputStreamReader(
                context.openFileInput(mFile.getName()));
             BufferedReader inputReader = new BufferedReader(inputStreamReader)) {

            String inputString;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuilder.append(inputString).append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private long getCreationDate(File file) {
        String fileName = file.getName();
        Log.i("SensorDataFile", fileName);
        long creationDateInMillis = Long.parseLong(fileName.substring(0, fileName.indexOf('.')));
        return creationDateInMillis;
    }

    public boolean delete() {
        return mFile.delete();
    }

    @Override
    public int compareTo(SensorDataFile another) {
        long creationDateDelta = this.mCreationDate - another.mCreationDate;
        if (creationDateDelta > 0) {
            return 1;
        } else if (creationDateDelta == 0) {
            return 0;
        } else {
            return -1;
        }
    }
}
