package it.polimi.ingsw.controller.stub;

import it.polimi.ingsw.controller.DefeatManager;
import it.polimi.ingsw.core.Build;
import it.polimi.ingsw.core.Game;
import it.polimi.ingsw.core.Move;

import java.util.List;

public class DefeatStub extends DefeatManager {
	private boolean moveDefeatCalled;
	private boolean buildDefeatCalled;

	public DefeatStub(Game g) {
		super(g);
		moveDefeatCalled = false;
		buildDefeatCalled = false;
	}

	// stub methods
	public void moveDefeat(List<Move> worker1_possible_moves, List<Move> worker2_possible_moves) {
		moveDefeatCalled = true;
	}
	public void buildDefeat(List<Build> worker_possible_builds) {
		buildDefeatCalled = true;
	}

	// check if called
	public boolean isMoveDefeatCalled() {
		return moveDefeatCalled;
	}
	public boolean isBuildDefeatCalled() {
		return buildDefeatCalled;
	}
}
