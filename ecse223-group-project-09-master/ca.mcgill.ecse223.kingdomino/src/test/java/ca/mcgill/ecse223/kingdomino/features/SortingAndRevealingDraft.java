package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.CoreMatchers.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.Is;

import java.util.Collections;
import java.util.Comparator;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import gherkin.StringUtils;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

/**
 * 
 * @author Ellina
 *
 */
public class SortingAndRevealingDraft {
	Kingdomino kingdomino;
	Game game;
	Draft nextDraft = null;
	
	@Given("there is a next draft, face down")
	public void there_is_a_next_draft_face_down() throws InvalidInputException {
		//Initialize game
		kingdomino = new Kingdomino();
		game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		createAllDominoes(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		//Create curent draft
		KingDominoController.createAndSortFirstDraft(game);
		game.setNextDraft(null);
		KingDominoController.createNextDraft(game);
		game.getNextDraft().setDraftStatus(DraftStatus.FaceDown);
	}

	@And("all dominoes in current draft are selected")
	public void all_dominoes_in_current_draft_are_selected() throws InvalidInputException{
		KingDominoController.revealFirstDraft(game);
	    new DominoSelection(game.getPlayer(0), game.getCurrentDraft().getIdSortedDomino(0), game.getCurrentDraft());
	    new DominoSelection(game.getPlayer(1), game.getCurrentDraft().getIdSortedDomino(1), game.getCurrentDraft());
	    new DominoSelection(game.getPlayer(2), game.getCurrentDraft().getIdSortedDomino(2), game.getCurrentDraft());
	    new DominoSelection(game.getPlayer(3), game.getCurrentDraft().getIdSortedDomino(3), game.getCurrentDraft());
	}

	@When("next draft is sorted")
	public void next_draft_is_sorted() throws InvalidInputException {
	    KingDominoController.sortNextDraft(game);
	}

	@When("next draft is revealed")
	public void next_draft_is_revealed() throws InvalidInputException {
		KingDominoController.revealNextDraft(game);
	}

	@Then("the next draft shall be sorted")
	public void the_next_draft_shall_be_sorted() {
		List<Domino> dominos = Collections.emptyList();
		dominos = game.getNextDraft().getIdSortedDominos();
		dominos = new ArrayList<>(dominos);
		
		Collections.sort(dominos, new Comparator<Domino>() {
			@Override
			public int compare(Domino domino1, Domino domino2) {
				return domino1.getId() - domino2.getId();
			}
		});
		
		assertEquals(game.getNextDraft().getIdSortedDominos(), dominos);
	}

	@Then("the next draft shall be facing up")
	public void the_next_draft_shall_be_facing_up() {
	    assertEquals(DraftStatus.FaceUp, game.getNextDraft().getDraftStatus());
	}

	@Then("it shall be the player's turn with the lowest domino ID selection")
	public void it_shall_be_the_player_s_turn_with_the_lowest_domino_ID_selection() {
	    assertEquals(game.getNextPlayer(), game.getPlayer(0));
	}
	
	//////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////

	private void addDefaultUsersAndPlayers(Game game) {
		String[] users = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < users.length; i++) {
			game.getKingdomino().addUser(users[i]);
			Player player = new Player(game);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	
	private void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = util.terrainTypeFromChar(dominoString[1].charAt(0));
				TerrainType rightTerrain = util.terrainTypeFromChar(dominoString[2].charAt(0));
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
			"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}
	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
	/**Get a series of dominos from a string of ids
	* @author Ellina
	* @param String of ids
	* @return The dominos corresponding to input IDs
	*/
	private List<Domino> getDominosByIDs(String ids){
		List<Domino> dominos = Collections.emptyList();
		dominos = new ArrayList<>(dominos);
		String[] IDs = ids.split(",");
		for(int f = 0; f < IDs.length; f++) {
			dominos.add(getdominoByID(Integer.parseInt(IDs[f])));
		}
		
		return dominos;
	}

}