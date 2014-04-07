package com.ihm.project;

import java.io.IOException;
import java.io.InputStream;
import com.ihm.project.core.Building;
import com.ihm.project.core.Level;
import com.ihm.project.core.Position;
import com.ihm.project.utils.XMLMapParser;
import com.ihm.project.view.DrawRotateView;
import com.ihm.project.view.DrawView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Menu;
import android.view.WindowManager;

public class ViewActivity extends Activity implements SensorEventListener{
	private XMLMapParser parser = null;
	private Building building = null;
	private Level level = null;
	private String urlString = "";
	private double buildingW = 0.0;
	private double buildingH = 0.0;
	private double scaleW = 0.0;
	private double scaleH = 0.0;
	private int screenW, screenH;
	DrawView dView;
	String className = "";
	
	private SensorManager mSensorManager; 
	private Sensor mAccelerometer; 
	private final float NOISE = (float) 1.3;
	
	private float initX=(float)0.0, initY=(float)0.0, initZ=(float)0.0;
	private float mLastX, mLastY, mLastZ;
	private boolean mInitialized = false; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//lastOrientation = 
		InputStream inps = null;
		try {
			inps = getAssets().open("new-building.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(inps!=null){
			parser = new XMLMapParser(inps);
			parser.loadXml();
			if(parser.isLoad){
				this.building = parser.getBuilding();
				this.buildingW = parser.maxWidth;
				this.buildingH = parser.maxHeight;
			}
		}
		super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        this.setView();
        //setContentView(this.dView);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		//mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	}
	
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
	}
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
	@Override
	protected void onStop() {
		super.onStop();
		Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.unregisterListener(this,sensor);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		switch(accuracy){
		case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
		case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM: 
		case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
		case SensorManager.SENSOR_STATUS_UNRELIABLE:
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch(event.sensor.getType()){
			case Sensor.TYPE_ACCELEROMETER:
				onAccelerometerChanged(event);
			break;
			case Sensor.TYPE_LINEAR_ACCELERATION:
				onOrientationChanged(event);
			break;
			case Sensor.TYPE_PROXIMITY:
				onProximityChanged(event);
				break;
		}
	}
	
	public void onOrientationChanged(SensorEvent event){
		float azimuth,pitch,roll;
		azimuth = event.values[0];
		pitch = event.values[1];
		roll = event.values[2];
		//((TextView)findViewById(R.id.orientation)).setText("ORIENTATION- Azimuth:"+azimuth+" Pitch:"+pitch+"° Roll:"+roll+"°");
	}
	
	public void onProximityChanged(SensorEvent event){
		
	}
	
	public void onAccelerometerChanged(SensorEvent event){
		//ImageView iv = (ImageView)findViewById(R.id.image);
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			mInitialized = true;
		} 
		else {
			float deltaX = Math.abs(initX - x);
			float deltaY = Math.abs(initY - y);
			float deltaZ = Math.abs(initZ - z);
			if (deltaX < NOISE) deltaX = (float)0.0;
			if (deltaY < NOISE) deltaY = (float)0.0;
			if (deltaZ < NOISE) deltaZ = (float)0.0;
			mLastX += x;
			mLastY += y;
			mLastZ += z;
			if (deltaX > deltaY) {
				//iv.setImageResource(R.drawable.horizontal);
			} 
			else if (deltaY > deltaX) {
				//iv.setImageResource(R.drawable.vertical);
			} 
			else {
				//iv.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ){
	    	this.setView();
	    }
		else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			this.setView();
		}
	}*/

	private void setView(){
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		/* Now we can retrieve all display-related infos */
		screenW = display.getWidth();
		screenH = display.getHeight();
		
		this.scaleW = screenW/this.buildingW;
		this.scaleH = screenH/this.buildingH;
		
		dView = new DrawView(this, building, 0, scaleW, scaleH);
        setContentView(this.dView);
	}
	
	private void setView2(){
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		/* Now we can retrieve all display-related infos */
		screenW = display.getWidth();
		screenH = display.getHeight();
		
		this.scaleW = screenW/this.buildingW;
		this.scaleH = screenH/this.buildingH;
		
		DrawRotateView dRotView = new DrawRotateView(this, building, 0, scaleW, scaleH);
		//this.setView();
        setContentView(dRotView);
	}
}
