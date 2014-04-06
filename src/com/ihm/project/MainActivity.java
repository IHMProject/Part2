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
import android.content.res.Configuration;
import android.view.Display;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {
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
	
	private int lastOrientation;
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ){
	    	this.setView();
	    }
		else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			this.setView();
		}
	}

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
