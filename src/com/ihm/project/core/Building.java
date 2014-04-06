package com.ihm.project.core;

import java.util.ArrayList;
import java.util.List;


public class Building extends Component {
	private Level floor;
	private List<Level> levels = new ArrayList<Level>();
	
	public Building(String name) {
		super(name);
	}
	
	public Building() {
		super();
	}
	
	public void print(){
		super.print();
		for(Level lvl : this.levels){
			lvl.print();
			for(Corridor cor: lvl.getCorridors()){
				cor.print();
				for(Classroom cls: cor.getClassrooms()){
					cls.print();
				}
			}
		}
	}

	List <Component> findClaasroom(String name){
		return null;
	}
    void findPath(String clsName, String lvlUsr, Position userPos){
    	
    }
    List<Component> getLevelAndCorrdidorByPosition(String levelName, Position position){
    	return null;
    }
    double calculateDistanceInCorridor(Corridor corridor, Position userPos, Position porte){
    	return 0.0;
    }
    void loadFromXml(){
    }

	public Level getFloor() {
		return floor;
	}

	public void setFloor(Level floor) {
		this.floor = floor;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void addLevel(Level level) {
		this.levels.add(level);
	}
	
	 void removeLevel(Level level){
		 this.levels.remove(level);
	 }
}
