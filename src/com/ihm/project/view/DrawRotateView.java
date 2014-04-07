package com.ihm.project.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.ihm.project.core.Building;
import com.ihm.project.core.Classroom;
import com.ihm.project.core.Component;
import com.ihm.project.core.Corridor;
import com.ihm.project.core.Level;
import com.ihm.project.core.Restroom;

public class DrawRotateView extends View {
	Paint paint = new Paint();
	private Building building;
	private int level;
	private double scaleW;
	private double scaleH;

	public DrawRotateView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//this.ro
	}
	
	public DrawRotateView(Context context, Building building, int level, double scaleW, double scaleH) {
		super(context);
		this.building = building;
		this.level = level;
		this.scaleW = scaleW;
		this.scaleH = scaleH;
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
					 drawText(canvas, cls);//TEXT
				}
				for(Restroom rtr: cor.getRestrooms()){
					// draw the restroom
					canvas.drawRect(getCompRectangle(rtr), paint);
					 drawText(canvas, rtr);//TEXT
				}
			}
			canvas.save();
			canvas.rotate(90);
			canvas.restore();
		}
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
}
