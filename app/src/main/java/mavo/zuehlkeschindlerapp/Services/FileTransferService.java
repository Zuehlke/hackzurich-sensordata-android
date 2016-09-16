package mavo.zuehlkeschindlerapp.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import mavo.zuehlkeschindlerapp.R;
import mavo.zuehlkeschindlerapp.Services.Data.SensorDataFile;

/**
 *  FileTransferService.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 * Service that handles data transfer between local disk and backend server. The service uses
 * the JobScheduler paradigm to schedule transfer jobs in background threads at regular intervals.
 *
 */
 public class FileTransferService extends JobService {

    private static final String DEVICE_ID = "deviceID";
    private static final String DEVICE_TYPE = "deviceType";

    private FileTransferAsyncTask fileTransferTask;
    private String url;
    private String username;
    private String password;
    private String deviceId;
    private String deviceType;

    @Override
    public void onCreate(){
        super.onCreate();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.url = prefs.getString(getString(R.string.pref_shmack_instance_url_key),
                getString(R.string.pref_shmack_instance_url_default));
        this.username =    prefs.getString(getString(R.string.pref_shmack_instance_username_key),
                getString(R.string.pref_shmack_instance_username_default));
        this.password =    prefs.getString(getString(R.string.pref_shmack_instance_password_key),
                getString(R.string.pref_shmack_instance_password_default));

        this.deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        this.deviceType = Build.MODEL;

        fileTransferTask = new FileTransferAsyncTask(this);
    }

    /**
     * Contains the callback logic for this job.
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        fileTransferTask.execute(params);
        return true;

    }

    /**
     * This function is called by the system if the execution of the job has to be stopped before
     * jobFinished could have been called, probably because network connectivity will have stopped.
     * (return false) indicates that current job doesn't need to be rescheduled.
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * Background Task that takes care of the actual data transfer to the backend server.
     */
    private class FileTransferAsyncTask extends AsyncTask<JobParameters, Void, JobParameters[]> {

        private Context mContext;
        public FileTransferAsyncTask (Context context) {
            this.mContext = context;
        }

        /**
         * Log any transfer issues to sharedPreferences. Those are displayed in the settings UI for debugging purposes.
         */
        @Override
        protected JobParameters[] doInBackground(JobParameters... params) {
            try {
                transfer();
            } catch (JSONException | IOException e) {
                String lastFailure = e.toString();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.pref_shmack_instance_last_transfer_failure_key), lastFailure);
                editor.apply();
            }
            return params;
        }

        @Override
        protected void onPostExecute(JobParameters[] result) {
            for (JobParameters params : result) {
                jobFinished(params, false);

            }
        }

        /**
         * Generate a Base64 Login String out of the username and password strings.
         */
        private String generateBase64LoginString() {
            String formatted = String.format("%s:%s", username, password);
            byte[] bytesEncoded = Base64.encode(formatted.getBytes(), Base64.DEFAULT);
            return new String(bytesEncoded);
        }

        /**
         * Read oldest data file for the current sensor from disk and send it to transfer. If the transfer was
         * completed successfully, erase corresponding file from disk.
         */
        private void transfer() throws JSONException, IOException {
            List<SensorDataFile> sensorDataFileList = new FileReadService(mContext).getFilesFromDisk();
            if (sensorDataFileList == null || sensorDataFileList.isEmpty()) {
                return;
            }

            for (SensorDataFile sensorDataFile : sensorDataFileList) {
                boolean successfullyTransferData = transferData(sensorDataFile.readFileContent());
                if (successfullyTransferData) {
                    sensorDataFile.delete();
                }
            }
        }

        private HttpURLConnection buildHttpRequest() throws IOException {

            Uri builtUri = Uri.parse(url).buildUpon()
                    .appendQueryParameter(DEVICE_ID, deviceId)
                    .appendQueryParameter(DEVICE_TYPE, deviceType)
                    .build();

            URL url = new URL(builtUri.toString());

            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("POST");
            request.setRequestProperty("Authorization", "Basic " + generateBase64LoginString());
            request.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            request.setConnectTimeout(5000);

            return request;
        }

        private boolean transferData(String jsonData) throws JSONException, IOException {
            HttpURLConnection request;

            request = buildHttpRequest();
            request.connect();

            try(OutputStream os = request.getOutputStream();
                OutputStreamWriter ow = new OutputStreamWriter(os)){
                ow.write(jsonData);
                ow.flush();
                request.disconnect();
                return true;
            }
        }
    }
}
