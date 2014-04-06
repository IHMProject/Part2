package com.ihm.project.core;

import java.util.ArrayList;
import java.util.List;

public class Corridor extends Component{
	private List<Classroom> classrooms = new ArrayList<Classroom>();
    private List<Restroom> restrooms = new ArrayList<Restroom>();
    private List<Stair> stairs = new ArrayList<Stair>();
    private List<Elevator> elevators = new ArrayList<Elevator>();
    private List<Junction> junctions = new ArrayList<Junction>();
    
	public Corridor(String name) {
		super(name);
	}

	public Corridor() {
		super();
	}

	public List<Classroom> getClassrooms() {
		return classrooms;
	}

	public void addClassroom(Classroom classroom) {
		this.classrooms.add(classroom);
	}
	public void removeClassroom(Classroom classroom) {
		this.classrooms.remove(classroom);
	}

	public List<Restroom> getRestrooms() {
		return restrooms;
	}

	public void addRestroom(Restroom restroom) {
		this.restrooms.add(restroom);
	}
	public void removeRestroom(Restroom restroom) {
		this.restrooms.add(restroom);
	}

	public List<Stair> getStairs() {
		return stairs;
	}

	public void addStair(Stair stair) {
		this.stairs.add(stair);
	}
	public void addRemove(Stair stair) {
		this.stairs.remove(stair);
	}

	public List<Elevator> getElevators() {
		return elevators;
	}

	public void addElevator(Elevator elevator) {
		this.elevators.add(elevator);
	}

	public List<Junction> getJunctions() {
		return junctions;
	}

	public void addJunction(Junction junction) {
		this.junctions.add(junction);
	}

	
}
