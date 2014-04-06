package com.ihm.project.core;

import java.util.ArrayList;
import java.util.List;

public class Component {
	protected String name="";
	protected Position position = null;
	protected Size size = null;
	protected List<Door> doors = new ArrayList<Door>();
	protected int state = 0;
	
	public Component() {this.name = "new "+this.getClass().getSimpleName();}
	public Component(String name) {
		this.name = name;
	}
	
	public void print(){
		System.out.println(this.getClass().getSimpleName()+"[Name:"+this.name+" - Pos:"+this.position.toString()+" - Size:"+this.size.toString()+" - NBDoors:"+this.doors.size()+"]");
	}
	
	public double calculateDistance(Component corridor, Position usrPos, Position door){
		return 0.0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<Door> getDoors() {
		return doors;
	}
	public void addDoor(Door door) {
		this.doors.add(door);
	}
	
	public void removeDoor(Door door){
		this.doors.remove(door);
	}
	
	
}
