package com.himi.button;//包路径
//import导入类库

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/* 使用点击监听器接口进行监听
public class MainActivity extends Activity implements OnClickListener {//使用点击监听器
	private Button btn_ok, btn_cancel;
	private TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		tv = (TextView) findViewById(R.id.tv);
		btn_ok.setOnClickListener(this);//将btn_ok按钮绑定在点击监听器上
		btn_cancel.setOnClickListener(this);//将btn_cancel按钮绑定在点击监听器上
	}

	@Override
	public void onClick(View v) {//使用监听器就要重写其抽象函数 
		if (v == btn_ok) {
			tv.setText("确定按钮触发事件！");
		} else if (v == btn_cancel) {
			tv.setText("取消按钮触发事件！");
		}
	}
}
*/
//内部类实现按键监听
public class MainActivity extends Activity {//使用点击监听器
	private Button btn_ok, btn_cancel;
	private TextView tv;
	//public List<String> Content = new ArrayList<String>();
	public String content;
	public final static String EXTRA_MESSAGE = "";
	Intent service=null;

	String logfile="log.txt";
	String dbpath="So.txt";
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if ("com.himi.button.ClipCatching".equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn_ok = (Button) findViewById(R.id.btn_ok);
       if (isMyServiceRunning()==false)
       {
		service = new Intent(MainActivity.this, ClipCatching.class);
		startService(service);
       }
		tv = (TextView) findViewById(R.id.tv);
		btn_ok.setOnClickListener(new OnClickListener() {//将btn_ok按钮绑定在点击监听器上
					@Override
					public void onClick(View v) {
						//String str = "";
						//int number = 0;
						try {
							Scanner Fr =new Scanner (new File(dbpath));
						  while (Fr.hasNext())	
							content+=Fr.nextLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							Log.v("ff","IoException");
							e.printStackTrace();
						}
						Intent intent = new Intent();
				        intent.putExtra(EXTRA_MESSAGE,content);    
				        /* 指定intent要启动的类 */
				        intent.setClass(MainActivity.this, ShowContent.class);
				        /* 启动一个新的Activity */
				        startActivity(intent);
				//	
				       
				        /* 关闭当前的Activity */
				        //MainActivity.this.finish();
						//tv.setText(Content.get(number));
					}
				});
	
	}

   
	public String readFile(String fileName) throws IOException{ 
		  String res=""; 
		  int length = 0;
		  try{ 
		         FileInputStream fin = openFileInput(fileName); 
		         length = fin.available(); 
		         byte [] buffer = new byte[length]; 
		         fin.read(buffer);     
		         res = EncodingUtils.getString(buffer, "UTF-8"); 
		         fin.close();     
		     } 
		     catch(Exception e){ 
		         e.printStackTrace(); 
		     } 
		  /*
	         for (int i = 0; i < length - 1; i++) {
	        	 if (res.charAt(i) == '#') {
	        		 String temp = "";
	        		 i++;
	        		 while (res.charAt(i) != '#') {
	        			 temp += res.charAt(i);
	        			 i++;
	        		 }
	        		 i--;
	        		 Content.add(temp);
	        	 }
	         }
		     return res; 
	         return (int)(Math.random()*(Content.size()-1));*/
		  return res;
	} 
	
	//写数据
	

}
