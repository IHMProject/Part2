package com.ihm.project.core;

import java.util.ArrayList;
import java.util.List;

public class Level extends Component{
	private List<Corridor> corridors = new ArrayList<Corridor>();
	private List<Stair> stairs = new ArrayList<Stair>();
	private List<Elevator> elevators = new ArrayList<Elevator>();
	
	public Level(String name) {
		super(name);
	}

	public Level() {
		super();
	}

	public List<Corridor> getCorridors() {
		return corridors;
	}

	public void addCorridors(Corridor corridor) {
		this.corridors.add(corridor);
	}
	public void removeCorridors(Corridor corridor) {
		this.corridors.remove(corridor);
	}

	public List<Stair> getStairs() {
		return stairs;
	}

	public void addStairs(Stair stair) {
		this.stairs.add(stair);
	}
	public void removeStairs(Stair stair) {
		this.stairs.remove(stair);
	}

	public List<Elevator> getElevators() {
		return elevators;
	}

	public void addElevators(Elevator elevator) {
		this.elevators.add(elevator);
	}
	
	public void removeElevators(Elevator elevator) {
		this.elevators.remove(elevator);
	}
}
