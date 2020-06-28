package it.polimi.ingsw.core.gods;
import it.polimi.ingsw.core.*;
import it.polimi.ingsw.core.state.GamePhase;
import it.polimi.ingsw.core.state.Turn;
import it.polimi.ingsw.util.exceptions.NoBuildException;
import it.polimi.ingsw.util.exceptions.NoMoveException;

import java.util.List;
import java.util.ArrayList;

/**
 * This is Minotaur GodCard, it has the specific implementation of the methods to calculate the moves and builds that each worker can do
 */
public class Minotaur extends GodCard {

	//MINOTAUR CODE
	private Player owner;
	public final TypeGod typeGod = TypeGod.SIMPLE_GOD;
	public final List<Integer> numPlayer = List.of(2,3,4);
	public final String name = "Minotaur";
	public final String description = "Your Move: Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";

	/**
	 * Constructor of the class
	 * @param player the owner of the card
	 */
	public Minotaur(Player player){
		this.owner = player;
	}

	/**
	 * Empty constructor of the class
	 */
	public Minotaur(){
		this.owner = null;
	}

	//GETTERS
	/**
	 * Getter of the number of players that can play if this card is used
	 * @return the number of players
	 */
	public List<Integer> getNumPlayer(){
		return numPlayer;
	}

	/**
	 * Getter of the owner of the card
	 * @return the owner of the card
	 */
	public Player getOwner(){
		return owner;
	}

	/**
	 * Getter of the type of god:
	 * @return the typeGod
	 */
	public TypeGod getTypeGod(){
		return typeGod;
	}

	/**
	 * Getter of the name of the card
	 * @return the name of the GodCard
	 */
	public String getName(){
		return name;
	}

	/**
	 * Getter of the description
	 * @return the GodCard description
	 */
	public String getDescription(){
		return description;
	}



	//CARD-SPECIFIC IMPLEMENTATION OF CHECKBUILD AND CHECKMOVE
	/**
	 * This is the specific implementation of the movement option for this GodCard
	 * @param m represents the map
	 * @param w represents the worker moved by the player during this turn
	 * @param turn the phase of the game
	 * @return the cells where the Player's Worker may move according to general game rules and his GodCard power
	 * @throws NoMoveException if the phase is wrong
	 */
	@Override
	public List<Move> checkMove(Map m, Worker w, Turn turn) throws NoMoveException {   //worker->activeworker
		// if the phase isn't the move phase it throws a move exception
		if (turn.getGamePhase() != GamePhase.MOVE) {
			throw new NoMoveException();
		}

		int y = m.getY(w.getPos());
		int x = m.getX(w.getPos());
		List<Move> moves = new ArrayList<>();
		for(int i = -1; i <= 1; i++){   //i->x   j->y     x1, y1 all the cells where I MAY move
			int x1 = x + i;
			for(int j = -1; j <= 1; j++){
				int y1 = y + j;

				if(x != x1 || y != y1){ //I shall not move where I am already
					if(0 <= x1 && x1 <= 4 && 0 <= y1 && y1 <= 4){	//Check that I am inside the map
						if(-1 <= (x1-x) && (x1-x) <= 1 && -1 <= (y1-y) && (y1-y) <=1){  //Check that distance from original is cell <= 1: useless?
							if(m.getCell(x1, y1).getBuilding().getLevel() - m.getCell(x, y).getBuilding().getLevel() <= 1){ //Check height difference
								if(!m.getCell(x1, y1).getBuilding().getDome()){   //Check there is NO dome
									if((m.getX(owner.getWorker1().getPos()) != x1 || m.getY(owner.getWorker1().getPos()) != y1) && (m.getX(owner.getWorker2().getPos()) != x1 || m.getY(owner.getWorker2().getPos()) != y1)){   //Check there is no OWNER worker on cell
										//Checks for opponent's workers because of Minotaur's power
										if(m.getCell(x1, y1).getWorker() != null){
											addCell(m, w, x1, y1, x, y, moves);
										}
										else{
											moves.add(new Move(TypeMove.SIMPLE_MOVE, m.getCell(x, y), m.getCell(x1, y1), w));
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return moves;
	}

	/**
	 * This method adds the other cells of the Minotaur power to the list moves, if possible
	 * @param m represents the map
	 * @param w represents the worker moved by the player during this turn
	 * @param x1,y1 represent the coordinates of the cell where the player may move
	 * @param x,y represent the coordinates of the cell where the worker is
	 * @param moves the list of the moves found before the addCell is called
	 */
	private void addCell(Map m, Worker w, int x1, int y1, int x, int y, List<Move> moves) {
		//Finds the next point through a vector representation of line
		int t = 2;
		int x2 = x + t * (x1 - x);
		int y2 = y + t * (y1 - y);

		if(0 <= x2 && x2 <= 4 && 0 <= y2 && y2 <= 4){	//Check that I am inside the map
			if(!m.getCell(x2, y2).getBuilding().getDome()){	//Check there is NO dome
				if(m.getCell(x2, y2).getWorker() == null){	//Check there is no worker [of ANY player] on cell
					//Adds move
					Move newMove = new Move(TypeMove.CONDITIONED_MOVE, m.getCell(x, y), m.getCell(x1, y1), w);
					newMove.setCondition(new Move(TypeMove.SIMPLE_MOVE, m.getCell(x1, y1), m.getCell(x2, y2), m.getCell(x1, y1).getWorker()));
					moves.add(newMove);
				}
			}
		}
	}
}
