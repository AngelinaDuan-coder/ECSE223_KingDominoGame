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
import gherkin.StringUtils;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderAndRevealNextDraftStepDefinitions {
	Game game;
	
	/** Initialize the game
	 * @author Ellina
	 */
	//Scenario Outline: Ordering the next draft before revealing
	@Given("the game is initialized for order next draft of dominoes")
	public void the_game_is_initialized_for_order_next_draft_of_dominoes() {
		//Initialize game
		Kingdomino kingdomino = new Kingdomino();
		game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	
	/** Creates the next draft from input string
	 * @author Ellina
	 */
	
    @Given("the next draft is {string}")
    public void the_next_draft_is(String string) throws InvalidInputException {
		String[] IDs = string.split(",");
		for(int f = 0; f < IDs.length; f++) {
			createADomino(Integer.parseInt(IDs[f]));
		}
		
    	game.setTopDominoInPile(game.getAllDomino(0));
		game.setCurrentDraft(new Draft(null, game));
    	KingDominoController.createNextDraft(game);
    }
    
    /** Sets the status of the drafts as face down
	 * @author Ellina
	 */
    @Given("the dominoes in next draft are facing down")
    public void the_dominoes_in_next_draft_are_facing_down() {
    	game.getNextDraft().setDraftStatus(DraftStatus.FaceDown);
    }
    
    /** Sets the status of the drafts as face down
	 * @author Ellina
	 */
    @When("the ordering of the dominoes in the next draft is initiated")
    public void the_ordering_of_the_dominoes_in_the_next_draft_is_initiated() throws InvalidInputException {
    	KingDominoController.sortNextDraft(game);
    }
    
    /** Assert that the draft status is sorted
   	 * @author Ellina
   	 */
    @Then("the status of the next draft is sorted")
    public void the_status_of_the_next_draft_is_sorted() {
    	assertEquals(DraftStatus.Sorted, game.getNextDraft().getDraftStatus());
    }
    
    /** Verify if ordered draft matches expected
   	 * @author Ellina
   	 */
    @Then("the order of dominoes in the draft will be {string}")
    public void the_order_of_dominoes_in_the_draft_will_be(String string) {
    	List<Domino> orderedDraft = getDominosByIDs(string);
    	orderedDraft = new ArrayList<Domino>(orderedDraft);
    	
    	assertThat(game.getNextDraft().getIdSortedDominos(), is(orderedDraft));
    }
    
    /** Initialize game
   	 * @author Ellina
   	 */
    //Scenario Outline: Revealing the next draft before the players start claiming dominoes
    @Given("the game is initialized for reveal next draft of dominoes")
    public void the_game_is_initialized_for_reveal_next_draft_of_dominoes() {
    	//Initialize game
    	Kingdomino kingdomino = new Kingdomino();
    	game = new Game(48, kingdomino);
    	game.setNumberOfPlayers(4);
    	kingdomino.setCurrentGame(game);
    	// Populate game
    	addDefaultUsersAndPlayers(game);
    	game.setNextPlayer(game.getPlayer(0));
    	KingdominoApplication.setKingdomino(kingdomino);
    }
    
    /** Creates the next draft from input string
   	 * @author Ellina
   	 */
    @Given("the next draft is  {string}")
    public void the_next_draft_is1(String string) throws InvalidInputException {
    	String[] IDs = string.split(",");
		for(int f = 0; f < IDs.length; f++) {
			createADomino(Integer.parseInt(IDs[f]));
		}
		game.setTopDominoInPile(game.getAllDomino(0));
		game.setCurrentDraft(new Draft(null, game));
		game.setNextDraft(null);
		KingDominoController.createNextDraft(game);
    }
    
    /** Sort next draft
   	 * @author Ellina
   	 */
    @Given("the dominoes in next draft are sorted")
    public void the_dominoes_in_next_draft_are_sorted() throws InvalidInputException {
    	KingDominoController.sortNextDraft(game);
    }
    
    /** Reveal the next draft
   	 * @author Ellina
   	 */
    @When("the revealing of the dominoes in the next draft is initiated")
    public void the_revealing_of_dominoes_in_the_next_draft_is_initiated() throws InvalidInputException {
    	KingDominoController.revealNextDraft(game);
    }
    
    /** Creates the next draft from input string
   	 * @author Ellina
   	 */
    @Then("the status of the next draft is face up")
    public void the_status_of_the_next_draft_is_face_up() {
    	assertEquals(DraftStatus.FaceUp, game.getNextDraft().getDraftStatus());
    }
    
    

	///////////////////////////////////////
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
	
	private void createADomino(Integer id) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				//loop through till find id looking for
				if(id == dominoId) {
					TerrainType leftTerrain = util.terrainTypeFromChar(dominoString[1].charAt(0));
					TerrainType rightTerrain = util.terrainTypeFromChar(dominoString[2].charAt(0));
					int numCrown = 0;
					if (dominoString.length > 3) {
						numCrown = Integer.decode(dominoString[3]);
					}
					new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
					break;
				}
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
