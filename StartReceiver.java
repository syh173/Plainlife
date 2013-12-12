package com.himi.button;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartReceiver extends BroadcastReceiver  
{  
    /*要接收的intent源*/  
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";  
          
    public void onReceive(Context context, Intent intent)   
    {  
        if (intent.getAction().equals(ACTION))   
        {  
        	Log.v("startRe","receive");
                  context.startService(new Intent(context, ClipCatching.class));//启动倒计时服务  
            //这边可以添加开机自动启动的应用程序代码  
        }  
    }  
}  
