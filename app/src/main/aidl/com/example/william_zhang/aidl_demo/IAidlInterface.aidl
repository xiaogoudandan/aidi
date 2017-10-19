package com.example.william_zhang.aidl_demo;
import com.example.william_zhang.aidl_demo.IRollBack;
interface IAidlInterface{
     int getNumber(int number);
     void setBack(IRollBack back);
}