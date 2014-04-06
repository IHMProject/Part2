package com.ihm.project.view;

import com.ihm.project.core.Building;
import com.ihm.project.core.Classroom;
import com.ihm.project.core.Component;
import com.ihm.project.core.Corridor;
import com.ihm.project.core.Level;
import com.ihm.project.core.Position;
import com.ihm.project.core.Size;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class DrawMap {
	private static double scaleWidth = 1.0;
	private static double scaleHeight = 1.0;
	public static void draw(Canvas canvas, Building building, int lvl){
		Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        
        Level level = building.getLevels().get(lvl);
        canvas.drawRect(getCompRectangle(level), paint);
		for(Corridor cor: level.getCorridors()){
			//draw the corridor
			canvas.drawRect(getCompRectangle(cor), paint);
			for(Classroom cls: cor.getClassrooms()){
				// draw the classroom
				canvas.drawRect(getCompRectangle(cls), paint);
			}
			for(Classroom clr: cor.getClassrooms()){
				// draw the restroom
				canvas.drawRect(getCompRectangle(clr), paint);
			}
		}
	}
	
	public static Rect getCompRectangle(Component comp){
		//Position pos, Size size
		int left = (int) comp.getPosition().getPosX();
		int top = (int)comp.getPosition().getPosY();
		int right = (int)(comp.getPosition().getPosX() + scaleWidth*comp.getSize().getWidth());
		int bottom = (int)(comp.getPosition().getPosY() + scaleHeight*comp.getSize().getHeight());
		//canvas.drawRect(xPosition, yPosition, xPosition + rectangleWidth, yPosition + rectangleHeight
		return new Rect(left, top, right, bottom);
	}

	public static void draw(Canvas canvas, Building building, int lvl, double scaleW, double scaleH) {
		scaleWidth = scaleW;
		scaleHeight = scaleH;
		Log.i("INFO", "SCALE W:"+scaleWidth+" H:"+scaleHeight);
		draw(canvas, building, lvl);
	}
}
