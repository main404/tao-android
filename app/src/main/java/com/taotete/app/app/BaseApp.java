package com.taotete.app.app;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {
    static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
    }

    public static synchronized BaseApp context() {
        return (BaseApp) _context;
    }
}
