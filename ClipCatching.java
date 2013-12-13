/**
 * 
 */
package com.himi.button;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Semaphore;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

public class ClipCatching extends Service {
	// 电子证据存放基本路径
	
//判断sd卡
	String dbpath="So.txt";
	public static boolean hasSdcard() {
	    String status = Environment.getExternalStorageState();
	    if (status.equals(Environment.MEDIA_MOUNTED)) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	
	    @Override  
	    public void onDestroy() {  
	        // TODO Auto-generated method stub   
	        Log.i("Service", "Service------->onDestory");  
	        super.onDestroy();  
	    }  
	private TextView tv;
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	   @Override  
	   
	  public int onStartCommand(Intent intent, int flags, int startId) {  
	        // TODO Auto-generated method stub   
	        Log.i("Service", "Service------->onStartCommand");  
	        //这里的返回有三种类型，可以自己手动返回。return XXXXX；   
	       // final File db= new File(dbpath);

		     final ClipboardManager cm =(ClipboardManager) getSystemService(CLIPBOARD_SERVICE);;
			  final AlertDialog confirm= new AlertDialog.Builder(this)   
			.setTitle("Pure Love")  
			.setMessage("确定收藏吗？")  
			.setPositiveButton("是", new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub

					
					try {
					//	fwriter = new BufferedWriter(new FileWriter(db,true));
						FileOutputStream fwriter =openFileOutput(dbpath, MODE_PRIVATE|MODE_APPEND);   
				    SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");     
					 Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
					String   str   =   formatter.format(curDate);   
					fwriter.write("##\r\n".getBytes());
					fwriter.write((str+"\r\n").getBytes());
					fwriter.write((cm.getText().toString()+"\r\n").getBytes());
					fwriter.flush();
					fwriter.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}) 
			.setNegativeButton("否",  new OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
				}
			})  
	        .create(); 
		    
			confirm.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		   
	         cm.setPrimaryClip(ClipData.newPlainText("", ""));  
	         cm.addPrimaryClipChangedListener(new OnPrimaryClipChangedListener() {
	        
					@SuppressWarnings({ "deprecation", "null" })
					@Override
					public void onPrimaryClipChanged() {
						// TODO Auto-generated method stub
					Log.v("ClipBoard Changed",cm.getText().toString());	
					confirm.show();	
					} 	
	         });
			
	        return super.onStartCommand(intent, flags, startId);  
	    }  
	 
	public void onCreate() {  
	        // TODO Auto-generated method stub   
	        Log.i("Service", "Service------->onCreate");  
	        super.onCreate();  
	    }  

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
