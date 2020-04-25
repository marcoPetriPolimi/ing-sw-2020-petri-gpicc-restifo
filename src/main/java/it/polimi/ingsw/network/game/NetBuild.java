package it.polimi.ingsw.network.game;

// necessary imports from other packages of the project
import it.polimi.ingsw.core.Build;

import java.io.Serializable;

/**
 * This class is used by the player to communicate to the server if
 */
public class NetBuild implements Serializable {
	public final int workerID;
	public final int cellX;
	public final int cellY;
	public final int level;
	public final boolean dome;
	public final NetBuild other;

	public NetBuild(NetWorker worker, NetCell cell, NetMap map, int level, boolean dome) throws NullPointerException {
		if (worker == null || cell == null || map == null) {
			throw new NullPointerException();
		}
		workerID = worker.workerID;
		cellX = map.getX(cell);
		cellY = map.getY(cell);
		this.level = level;
		this.dome = dome;
		other = null;
	}
	public NetBuild(NetWorker worker, NetCell cell, NetMap map, int level, boolean dome, NetBuild other) throws NullPointerException {
		if (worker == null || cell == null || map == null || other == null) {
			throw new NullPointerException();
		}
		workerID = worker.workerID;
		cellX = map.getX(cell);
		cellY = map.getY(cell);
		this.level = level;
		this.dome = dome;
		this.other = other;
	}
	public NetBuild(Build build) throws NullPointerException {
		if (build == null) {
			throw new NullPointerException();
		}
		workerID = build.worker.workerID;
		cellX = build.cell.map.getX(build.cell);
		cellY = build.cell.map.getY(build.cell);
		if (build.dome) {
			dome = true;
			level = -1;
		} else {
			level = build.cell.building.getLevel() + 1;
			dome = false;
		}
		if (build.getOther() == null) {
			other = null;
		} else {
			other = new NetBuild(build.getOther());
		}
	}
}
