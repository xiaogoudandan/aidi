package com.example.william_zhang.aidl_demo.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.william_zhang.aidl_demo.IAidlInterface;
import com.example.william_zhang.aidl_demo.IRollBack;

/**
 * Created by william_zhang on 2017/10/19.
 */

public class NumberServer extends Service {
    private Thread thread;
    private IRollBack mIRollBack;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iAidlInterface.asBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thread =new Thread(new Runnable() {
            @Override
            public void run() {
                mHandlder.sendEmptyMessageDelayed(0,1000);
            }
        });
        thread.start();
    }

    private IAidlInterface.Stub iAidlInterface=new IAidlInterface.Stub() {
        @Override
        public int getNumber(int number) throws RemoteException {
            return number*2;
        }

        @Override
        public void setBack(IRollBack back) throws RemoteException {
            mIRollBack=back;
        }
    };




    private Handler mHandlder=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if(mIRollBack!=null) {
                    mIRollBack.back();
                }
                mHandlder.sendEmptyMessageDelayed(0, 1000);
                Log.e("handler",Thread.currentThread().toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };



}
