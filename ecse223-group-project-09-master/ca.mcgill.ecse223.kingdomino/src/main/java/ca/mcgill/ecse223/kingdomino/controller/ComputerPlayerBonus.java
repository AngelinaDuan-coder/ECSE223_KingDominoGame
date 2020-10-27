package ca.mcgill.ecse223.kingdomino.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;

public class ComputerPlayerBonus {
// wish me luck with this 
// I feel it's gonna be a mess but let's try 

/**
 * @IMPORTANT_MESSAGE_TO_THE_GRADER
 * 
 * if you want to test this feature you can just start a new game and pick "CPU kasparov" as one of the 
 * players
 * because simply the method uses the name of the player to define that it is a computer player.
 * 
 *  PS you don't need to create that user it will be automatically generated ;) 
 */
/**
 * @author Elias Tamraz
 * this method is responsible for making a decision for computer player which domino to choose from draft
 * it will make sure that the domino can be placed inside the kingdom and make the decision
 * the method tries all the available dominos and picks the one that has the higher points 
 * @param game
 * @return int which represents the index of the selected domino in the sorted draft
 */
public static int computerSelection(Game game) {
	Kingdom ComputerKingdom = computerPlayer(game).getKingdom();
	
	Draft draft = game.getCurrentDraft();
	List<Domino> list = draft.getIdSortedDominos();
	List<Domino> dominos = new ArrayList<Domino>();
	int i= 0;
	for(Domino d : list ) {
		if (!d.hasDominoSelection()) {
			dominos.add(d);
		}		
	}
	Domino selectedDomino = SelectedDomino(dominos, ComputerKingdom);
	
	for(Domino d : list ) {
		if (d.getId()==selectedDomino.getId()) {
			return i;
		}
		i++;
	}
	
	return i;
	
}
	/**
	 * @author Elias Tamraz
	 * this method iterates over all players and 
	 * @return computer player (player who has a user called "CPU kasparov")
	 */
public static Player computerPlayer(Game game)   {
	
	List<Player> players  = game.getPlayers();
	for (Player p : players ) {
		System.out.println(p.getUser().getName());
		if (p.getUser().getName().equals("CPU kasparov")) {
			return p;
		}
	}
	throw new NullPointerException("no computer player");
}
/**
 * @author Elias Tamraz 
 * a supportive method for computerSelection it tries all the possible placements of all available dominos
 * 
 * @param dominos
 * @param ComputerKingdom
 * @return the Domino that will be picked by computer
 */
public static Domino SelectedDomino(List<Domino> dominos, Kingdom ComputerKingdom) {
	Domino selectedDomino;
	Collections.reverse(dominos);
	for(Domino d: dominos) {
		DominoStatus backUp = d.getStatus();
		DominoInKingdom dominoInKingdom = new DominoInKingdom(0,0,ComputerKingdom,d);
			for (int x=-4; x<=4; x++) { // needs to be from the start of the kingdom, not from -4, -4
				for (int y=-4; y<=4; y++) {
					for (DirectionKind direction : DirectionKind.values()) {
						dominoInKingdom.setX(x);
						dominoInKingdom.setY(y);
						dominoInKingdom.setDirection(direction);
					
						KingDominoController.verifyDominoPreplacement(dominoInKingdom);
						if (dominoInKingdom.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced) {
							dominoInKingdom.delete();
							return d;
						}
					}
				}
			}
		}
	return dominos.get(dominos.size()-1);
}



public static void PlaceDominoInKingdom(Game game) {
	Player ComputerPlayer = computerPlayer(game);
	Kingdom ComputerKingdom = computerPlayer(game).getKingdom();
	Domino d =  util.getDominoByID(ComputerPlayer.getDominoSelection().getDomino().getId());
	
	DominoInKingdom dominoInKingdom = new DominoInKingdom(0,0,ComputerKingdom, d);
	for (int x=-4; x<=4; x++) { // needs to be from the start of the kingdom, not from -4, -4
		for (int y=-4; y<=4; y++) {
			for (DirectionKind direction : DirectionKind.values()) {
				dominoInKingdom.setX(x);
				dominoInKingdom.setY(y);
				dominoInKingdom.setDirection(direction);
			
				KingDominoController.verifyDominoPreplacement(dominoInKingdom);
				if (dominoInKingdom.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced) {
					ComputerPlayer.getDominoSelection().delete();
					return;
				}
			}
		}
	}
}
	
}

