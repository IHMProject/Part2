package com.ihm.project.view;

import java.util.List;

import com.ihm.project.R;
import com.ihm.project.core.Building;
import com.ihm.project.core.Classroom;
import com.ihm.project.core.Component;
import com.ihm.project.core.Corridor;
import com.ihm.project.core.Level;
import com.ihm.project.core.Position;
import com.ihm.project.core.Restroom;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DrawView extends View {
	Paint paint = new Paint();
	Canvas canvas;
	private Building building;
	private int level;
	private double scaleW;
	private double scaleH;
	Position posUser = null;
	String className = "";
	Classroom clasDest = null;
	
	private final static int imgW = 28;
	private final static int imgH = 48;
	public DrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public DrawView(Context context, Building building, int level, double scaleW, double scaleH) {
		super(context);
		this.building = building;
		this.level = level;
		this.scaleW = scaleW;
		this.scaleH = scaleH;
		this.className = "039";
		//Position posUser = new Position(119, 130);
	}
	
	public DrawView(Context context, Building building, int level, String clsDest, double scaleW, double scaleH) {
		super(context);
		this.building = building;
		this.level = level;
		this.scaleW = scaleW;
		this.scaleH = scaleH;
		this.className = clsDest;
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		int x = (int) event.getX();
        int y = (int) event.getY();
        
        if(!this.setUserPos(x, y)){
        	Toast aToast = Toast.makeText(this.getContext(), "The position ("+x+","+y+") is not in corridor!", 10000);
            aToast.show();
        }
        else{
        	//ImageView iv = (ImageView)findViewById(R.drawable.marker_green);
        	//this.se
        	this.traceRoute(this.canvas);
        }
        
        this.invalidate();
        
		return false;
	}

	@Override
	public void onDraw(Canvas canvas) {
		//DrawMap.draw(canvas, building, level, this.scaleW, this.scaleH);
		if(this.building != null){
	        paint.setStyle(Paint.Style.STROKE);
	        paint.setColor(Color.GREEN);
	        paint.setTextSize(18f);
	        
	        Level level = building.getLevels().get(this.level);
	        canvas.drawRect(getCompRectangle(level), paint);
	        //canvas.set
	        drawText(canvas, level);//TEXT
	        paint.setColor(Color.BLACK);

	        for(Corridor cor: level.getCorridors()){
				//draw the corridor
				paint.setColor(Color.BLUE);
				canvas.drawRect(getCompRectangle(cor), paint);
				paint.setColor(Color.BLACK);
				drawText(canvas, cor);//TEXT
				for(Classroom cls: cor.getClassrooms()){
					// draw the classroom
					canvas.drawRect(getCompRectangle(cls), paint);
					if(cls.getName().equalsIgnoreCase(this.className)){
						this.clasDest = cls;

						Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.marker_red);
						float left = (float)(this.scaleW*(cls.getPosCenter().getPosX() - imgW/2));
						float top = (float)(this.scaleH*(cls.getPosCenter().getPosY()-imgH/2));

						canvas.drawBitmap(b, left, top, paint);
					}
					drawText(canvas, cls);//TEXT
				}
				for(Restroom rtr: cor.getRestrooms()){
					// draw the restroom
					canvas.drawRect(getCompRectangle(rtr), paint);
					 drawText(canvas, rtr);//TEXT
				}
			}
		}
		this.canvas = canvas;
		if(posUser != null){
			//ImageView iv = (ImageView)findViewById(R.drawable.marker_green);
			Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.marker_green);
			float left = (float)(posUser.getPosX()+1);
			float top = (float)(posUser.getPosY()+1);
			canvas.drawBitmap(b, left, top, paint);
			this.traceRoute(this.canvas); //TESTS
		}
		
		this.canvas = canvas;
	}

	private void traceRoute(Canvas canvas) {
		int color = paint.getColor();
		float strW = paint.getStrokeWidth();
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10);
        //Drawing the traiangle with Path class which moves our pencil to a point
		Path path = new Path();
		path.moveTo((float)(posUser.getPosX()), (float)(posUser.getPosY()));
		path.lineTo((float)(this.scaleW*(clasDest.getPosition().getPosX()+clasDest.getSize().getWidth()/2)), (float)(posUser.getPosY()));
		path.lineTo((float)(this.scaleW*(clasDest.getPosition().getPosX()+clasDest.getSize().getWidth()/2)), (float)(this.scaleH*(clasDest.getPosition().getPosY()+clasDest.getSize().getHeight()/2)));
		//path.close();
		canvas.drawPath(path, paint);
		
		paint.setColor(color);
		paint.setStrokeWidth(strW);
	}

	private RectF getCompRectangle(Component comp){
		//Position pos, Size size
		double w = comp.getSize().getWidth();
		double h = comp.getSize().getHeight();
		double x = comp.getPosition().getPosX();
		double y = comp.getPosition().getPosY();
		
		float left = (float)(this.scaleW*x);
		float top = (float)(this.scaleH*y);
		float right = (float)(this.scaleW*(x+w));
		float bottom = (float)(this.scaleH*(y+h));
		//canvas.drawRect(xPosition, yPosition, xPosition + rectangleWidth, yPosition + rectangleHeight
		return new RectF(left, top, right, bottom);
	}
	
	private void drawText(Canvas canvas, Component comp){
		int left = (int) (this.scaleW*comp.getPosition().getPosX());
		int top = (int) (this.scaleH*comp.getPosition().getPosY());
		canvas.drawText(comp.getName(), left+10, top+20, this.paint);
	}
	
	private boolean setUserPos(int x, int y){
		//Check if user in a corridor
		Level level = building.getLevels().get(this.level);
        
		for(Corridor cor: level.getCorridors()){
			double x1 = this.scaleW*cor.getPosition().getPosX();
			double y1 = this.scaleH*cor.getPosition().getPosY();
			
			double x2 = this.scaleW*(cor.getPosition().getPosX() + cor.getSize().getWidth());
			double y2 = this.scaleH*(cor.getPosition().getPosY() + cor.getSize().getHeight());
			
			//Log.i("POS", "x1="+x1+" x2="+x2+" y1="+y1+" y2="+y2);
			
			if((x>x1 && y>y1) && (x<x2 && y<y2)){
				this.posUser = new Position(x, y);
				return true;
			}
		}
		return false;
	}
}
