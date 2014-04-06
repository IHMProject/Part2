package com.ihm.project.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import android.graphics.Canvas;
import android.util.Log;

import com.ihm.project.core.Building;
import com.ihm.project.core.Classroom;
import com.ihm.project.core.Component;
import com.ihm.project.core.Corridor;
import com.ihm.project.core.Door;
import com.ihm.project.core.Level;
import com.ihm.project.core.Position;
import com.ihm.project.core.Restroom;
import com.ihm.project.core.Size;


public class XMLMapParser {
	private List<Canvas> canvas;
	private List<Building> buildings;
	private Building building;
	private String urlXml;
	public boolean isLoad = false;
	private InputStream inputStrm;
	public double maxWidth = 0.0;
	public double maxHeight = 0.0;
		
	
	public XMLMapParser(String urlXml) {
		this.urlXml = urlXml;
		this.building = new Building();
	}
	
	
	public XMLMapParser(InputStream inputStrm) {
		this.inputStrm = inputStrm;
		this.building = new Building();
	}


	public void setDataFromElm(Element elm, Component comp){
		//System.out.println("elm"+elm.toString());
		comp.setName(elm.getAttributeValue("name"));
		double width = 0.0, height = 0.0, posX=0.0, posY=0.0;
		int state = 0;
		
		try{
			if(elm.getAttributeValue("width")!=null && elm.getAttributeValue("width").length()>0) 
				width = Double.parseDouble(elm.getAttributeValue("width"));
			
			if(elm.getAttributeValue("height")!=null && elm.getAttributeValue("height").length()>0) 
				height = Double.parseDouble(elm.getAttributeValue("height"));
			
			if(elm.getAttributeValue("posX")!=null && elm.getAttributeValue("posX").length()>0) 
				posX = Double.parseDouble(elm.getAttributeValue("posX"));
			
			if(elm.getAttributeValue("posY")!=null && elm.getAttributeValue("posY").length()>0)
				posY = Double.parseDouble(elm.getAttributeValue("posY"));
			
			if(elm.getAttributeValue("state")!=null && elm.getAttributeValue("state").length()>0) 
				state = (int)Double.parseDouble(elm.getAttributeValue("state"));			
		}
		catch(Exception ex){ ex.printStackTrace();}
		
		if(this.maxWidth < posX+width)
			this.maxWidth = posX+width;
		if(this.maxHeight < posY+height)
			this.maxHeight = posY+height;
		
		comp.setPosition(new Position(posX, posY));
		comp.setSize(new Size(width, height));
		comp.setState(state);
		
		//Add for canvas if different if door ??
		this.addNewCanvas(comp);
	}
	
	public void setCorridors(Element elm, Level level){
		//looping through classrooms
		if(elm.getChild("corridors")==null)
			return;
		List<Element> elmsCorrs = elm.getChild("corridors").getChildren("corridor");
		if(elmsCorrs==null || elmsCorrs.size()==0)
			return;
        Iterator<Element> itrCorr = elmsCorrs.iterator();
        while(itrCorr.hasNext()){
        	Element elmCor = (Element)itrCorr.next();
        	//Create new level
        	Corridor corridor = new Corridor();
        	level.addCorridors(corridor);
        	//Add corridor's infos
        	this.setDataFromElm(elmCor, corridor);
        	
        	//Add classrooms
        	this.setClassrooms(elmCor, corridor);
        	//Add restrooms
        	this.setRestrooms(elmCor, corridor);
        	//Add corridor's doors
        	this.setDoors(elmCor, corridor);
        }
	}
	
	public void setClassrooms(Element elm, Corridor corridor){
		//looping through classrooms
		if(elm.getChild("classrooms")==null)
			return;
		List<Element> elmsClrs = elm.getChild("classrooms").getChildren("classroom");
		if(elmsClrs==null || elmsClrs.size()==0)
			return;
		
        Iterator<Element> clr = elmsClrs.iterator();
        while(clr.hasNext()){
        	Element elmClr = (Element)clr.next();
        	//Create new level
        	Classroom classroom = new Classroom();
        	corridor.addClassroom(classroom);
        	//Add classroom's infos
        	this.setDataFromElm(elmClr, classroom);
        	this.setDoors(elmClr, classroom);
        }
	}
	
	public void setRestrooms(Element elm, Corridor corridor){
		//looping through classrooms
		if(elm.getChild("restrooms")==null)
			return;
		
		List<Element> elmsRtrs = elm.getChild("restrooms").getChildren("restroom");
		if(elmsRtrs==null || elmsRtrs.size()==0)
			return;
		
        Iterator<Element> iRtr = elmsRtrs.iterator();
        while(iRtr.hasNext()){
        	Element elmRtr = (Element)iRtr.next();
        	//Create new level
        	Restroom restroom = new Restroom();
        	corridor.addRestroom(restroom);
        	//Add classroom's infos
        	this.setDataFromElm(elmRtr, restroom);
        	//this.setDoors(elmClr, classroom);
        }
	}
	
	public void setDoors(Element elm, Component comp){
		//looping through classrooms
		if(elm.getChild("doors")==null)
			return;
		List<Element> elms = elm.getChild("doors").getChildren("door");
		if(elms==null || elms.size()==0)
			return;
        Iterator<Element> itrDoors = elms.iterator();
        while(itrDoors.hasNext()){
        	Element elmDoor = (Element)itrDoors.next();
        	//Create new level
        	Door door = new Door();
        	comp.addDoor(door);
        	//Add classroom's infos
        	this.setDataFromElm(elmDoor, door);
        }
	}
	
	public void loadXml(){
		Element elmBuilding = getFirstElement();
		//Add building infos
		this.setDataFromElm(elmBuilding, this.building);
		
		Element elmLevels = elmBuilding.getChild("levels");
		List<Element> levels = elmLevels.getChildren("level");

        Iterator<Element> l = levels.iterator();
        
        //looping through levels
        while(l.hasNext()){
        	Element elmLvl = (Element)l.next();
        	//Create new level
        	Level level = new Level();
        	this.building.addLevel(level);
        	//Add building infos
    		this.setDataFromElm(elmLvl, level);
    		//Add corridors' level
    		this.setCorridors(elmLvl, level);
    		//Add doors' level
    		this.setDoors(elmLvl, level);
        }
        
        this.isLoad = true;
	}
	
	
	public Building getBuilding() {
		return building;
	}

	private void addNewCanvas(Component comp) {
		
	}

	public List<Canvas> getCanvas() {
		return canvas;
	}
	public List<Building> getBuildings() {
		return buildings;
	}
	
	public Element getFirstElement(){
		Element allElements = null;
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(this.inputStrm));

	        String line = "";
	       
	        StringBuilder builder = new StringBuilder();
	        while((line = in.readLine()) != null){
	        	builder.append(line);
	        }
	        String content = builder.toString();
	        //Log.i("INFO", content);
	        in.close();
	       
	        if(content.length()>0){
		        ByteArrayInputStream stream = new ByteArrayInputStream(content.getBytes());
		        SAXBuilder sxb = new SAXBuilder();
		        Document xmlDoc = sxb.build(stream);
		        
		        allElements = xmlDoc.getRootElement();
		        //System.out.println("all found:"+allElements.getAttributeValue("name"));
		        //Log.i("INFO", allElements.getAttributeValue("name"));
	        }
			
		} catch (MalformedURLException e) {
			Log.e("INFO", e.getMessage());
		} catch (IOException e) {
			Log.e("INFO", e.getMessage());
		} catch (JDOMException e) {
			Log.e("INFO", e.getMessage());
		}
		
		return allElements;
	}
}
