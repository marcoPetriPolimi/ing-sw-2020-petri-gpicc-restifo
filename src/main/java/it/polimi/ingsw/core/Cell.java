package it.polimi.ingsw.core;

public class Cell {
	// FIXME: the map isn't part of the cell state
	public final Map map;
	public final Building building;
	// worker isn't part of the state of a cell, it only
	private Worker worker;

	public Cell(Map m) {
		this.building = new Building();
		this.map = m;
	}

	// SETTERS
	void setWorker(Worker w){
		this.worker=w;
	}

	// CLASSES GETTERS
	public Building getBuilding() {
		return building;
	}
	public Worker getWorker(){ return worker; }

	// OVERRIDDEN METHODS //TODO: really necessary to override?!?!
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cell) {
			Cell other = (Cell)obj;
			if (map.getX(this) == other.map.getX(other) && map.getY(this) == other.map.getY(other)) {
				if (this.getBuilding().equals(other.getBuilding())) {
					if (this.worker == null && other.worker == null) {
						return true;
					} else if (this.worker != null && other.getWorker() != null) {
						return true;
					}
				}
			}
		}
		return false;
	}
}