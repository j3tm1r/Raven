package it.polito.mec.video.raven;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;

import it.polito.mec.video.raven.network_delay.Sync;
import it.polito.mec.video.raven.network_delay.SyncThread;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Jetmir on 11/01/2017.
 */

public class RavenApplication extends Application implements Sync {

    private SyncThread mSyncThread;
    private Gson mGson;

    @Override
    public void onCreate() {
        GsonBuilder builder = new GsonBuilder();
        mGson = builder.create();
        mSyncThread = new SyncThread(mGson);
        mSyncThread.start();
    }

    public void setRemoteSyncUrl(String ip, String port) {
        mSyncThread.setRemoteUrl(ip, port);
    }

    public void startSyncing() {
        mSyncThread.start();
    }

    public void stopSyncing() {
        mSyncThread.interrupt();
        try {
            mSyncThread.join();
        } catch (InterruptedException ex) {
            
        }
    }

    public long getDrift() {
        return mSyncThread.getDrift();
    }

}
