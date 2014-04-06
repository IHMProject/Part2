package com.ihm.project.core;

public class Classroom extends Component{
	private Corridor corridor;
	
	public Classroom(String name) {
		super(name);
	}

	public Classroom() {
		super();
	}

	public Corridor getCorridor() {
		return corridor;
	}

	public void setCorridor(Corridor corridor) {
		this.corridor = corridor;
	}
	
	public Position getNearPosition(Component corridor, Position userPos){
		return null;
	}
}
