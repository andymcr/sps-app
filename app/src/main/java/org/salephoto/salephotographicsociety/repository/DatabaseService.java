package org.salephoto.salephotographicsociety.repository;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class DatabaseService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
