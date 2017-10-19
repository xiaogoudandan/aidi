package com.example.william_zhang.aidl_demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.william_zhang.aidl_demo.server.NumberServer;

import javax.net.ssl.SSLEngineResult;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IAidlInterface mBinder;
    private EditText mEditText;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.input);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinder != null) {
                    try {
                        mEditText.setText(Integer.toString(mBinder.getNumber(2)));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "binder == null");

                }
            }
        });
        findViewById(R.id.start_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumberServer.class);
                bindService(intent, serviceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.d(TAG, "onServiceConnected");
                        mBinder = IAidlInterface.Stub.asInterface(service);
                        try {
                            mBinder.setBack(new IRollBack() {
                                @Override
                                public void back() throws RemoteException {
                                    mEditText.setText(Integer.toString((int) (Math.random() * 100)));
                                }

                                @Override
                                public IBinder asBinder() {
                                    return null;
                                }
                            });
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                    }
                }, Context.BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.stop_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
                mBinder = null;
            }
        });
    }
}
