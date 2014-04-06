package com.ihm.project.core;

public class Position {
	private double posX;
	private double posY;
	public Position(double posX, double posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Position [posX=" + posX + ", posY=" + posY + "]";
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	
}
