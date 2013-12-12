package com.himi.button;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ShowContent extends Activity{
	public List<String> Content = new ArrayList<String>();
	private SensorManager sensorManager; 
	private static final String TAG = "TestSensorActivity";  
	private static final int SENSOR_SHAKE = 10; 
	private TextView tv = null;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(R.layout.activity_show_content);
        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        tv = (TextView)findViewById(R.id.tv);
        // Create the text view
        tv.setTextSize(40);
        //tv.setText("摇一摇改变信息");
        //TextView textView = new TextView(this);
   
      for (int i = 0; i < message.length(); i++) {
       	 if (message.charAt(i) == '#') {
       		 String temp = "";
       		 i+=2;
       		 while (i < message.length() && message.charAt(i) != '#' ) {
       			 temp += message.charAt(i);
       			 i++;
       		 }
       		 i--;
       		 Content.add(temp);
       	 }
        }
   
        tv.setText(Content.get(0));
        	//int number = (int)(Math.random()*(Content.size()-1));
        
        //textView.setText(Content.get(number));

        // Set the text view as the activity layout
        //setContentView(tv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public boolean OnKeyDown(int keyCode,KeyEvent event){   
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {   
            Intent intent=new Intent();   
            intent.setClass(ShowContent.this, MainActivity.class);   
            startActivity(intent);   
            ShowContent.this.finish();   
        }      
        return false;   
     } 
    
    
    @SuppressLint("NewApi")
	@Override  
    protected void onResume() {  
        super.onResume();  
        if (sensorManager != null) {// 注册监听器  
            sensorManager.registerListener(sensorEventListener, 

sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 

SensorManager.SENSOR_DELAY_NORMAL);  
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率  
        }  
    }  
  
    @SuppressLint("NewApi")
	@Override  
    protected void onPause() {  
        super.onPause();  
        if (sensorManager != null) {// 取消监听器  
            sensorManager.unregisterListener(sensorEventListener);  
        }  
    }  
    
    /** 
     * 重力感应监听 
     */  
    @SuppressLint("NewApi")
	private SensorEventListener sensorEventListener = new SensorEventListener() {  
  

		@SuppressLint("NewApi")
		@Override  
        public void onSensorChanged(SensorEvent event) {  
            // 传感器信息改变时执行该方法  
            float[] values = event.values;  
            float x = values[0]; // x轴方向的重力加速度，向右为正  
            float y = values[1]; // y轴方向的重力加速度，向前为正  
            float z = values[2]; // z轴方向的重力加速度，向上为正  
            Log.i(TAG, "x轴方向的重力加速度" + x +  "；y轴方向的重力加速度" + y +  "；z轴方向的重力加速度" + z);  
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。  
            int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了  
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > 

medumValue) {  
                //Message msg = new Message();  
                //msg.what = SENSOR_SHAKE;  
                //handler.sendMessage(msg); 
            	int number = (int)(Math.random()*(Content.size()-1));
            	tv.setText(Content.get(number));
            }  
        }  
  
        @Override  
        public void onAccuracyChanged(Sensor sensor, int accuracy) {  
  
        }  
    };  
    /*
    Handler handler = new Handler() {  
    	  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch (msg.what) {  
            case SENSOR_SHAKE:  
                break;  
            }  
        }  
    };*/
    
    
}
  
