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
public class CreateNextDraftStepDefinitions {
	
	Kingdomino kingdomino;
	Game game;
	List<Domino> dominos = Collections.emptyList();
	Draft nextDraft = null;
	@Given("the game is initialized to create next draft")
	public void the_game_is_initialized_to_create_next_draft() {
		//Initialize game
		kingdomino = new Kingdomino();
		game = new Game(48, kingdomino);
	}
	
	//Scenario Outline: Creating next draft when there are dominoes still in the pile
	@Given("there has been {int} drafts created")
	public void there_has_been_drafts_created(Integer int1) {
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		
				
		// Populate game
		addDefaultUsersAndPlayers(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		
		for (int e = 0; e < int1; e++) {
			Draft draft = new Draft(null, game);
		}
	}
	
	@Given("there is a current draft")
	public void there_is_a_current_draft() {
		game.setCurrentDraft(new Draft(null, game));
	}
	
	@Given("there is a next draft")
	public void there_is_a_next_draft() {
		game.setNextDraft(null);
	}
	
	@Given("the top 5 dominoes in my pile have the IDs {string}")
	public void the_top_5_dominoes_in_my_pile_have_the_IDs(String string) {
		new Domino(9, TerrainType.Lake, TerrainType.Lake, 0, game);
		new Domino(10, TerrainType.Grass, TerrainType.Grass, 0, game);
		new Domino(11, TerrainType.Grass, TerrainType.Grass, 0, game);
		new Domino(12, TerrainType.Swamp, TerrainType.Swamp, 0, game);
		new Domino(13, TerrainType.WheatField, TerrainType.Forest, 0, game);
		dominos = getDominosByIDs(string);
		dominos = new ArrayList<>(dominos);
	}
	
		
	
	// causing error
	@When("create next draft is initiated")
	public void create_next_draft_is_initiated() throws InvalidInputException {
		
		if(KingDominoController.hasMoreDraft(game.getAllDrafts().size())) {
		List<Domino> gameDominos = game.getAllDominos();
		game.setTopDominoInPile(dominos.get(0));
		game.getTopDominoInPile().setNextDomino(dominos.get(1));
		for(Domino gameD : gameDominos) {System.out.println(gameD.getId());}
		}
		

	}
	
	@Then("a new draft is created from dominoes {string}")
	public void a_new_draft_is_created_from_dominoes(String string) throws InvalidInputException {
		game.setNextDraft(null);
		KingDominoController.createNextDraft(game);
		List<Domino> expectdDominos = getDominosByIDs(string);
    	expectdDominos = new ArrayList<>(expectdDominos);
    	Draft nextDraft = game.getNextDraft();
    	List<Domino> dominosInDraft = nextDraft.getIdSortedDominos();
    	assertEquals(expectdDominos, dominosInDraft);
	}
	
    @Then("the next draft now has the dominoes {string}")
    public void the_next_draft_now_has_the_dominoes(String string) {
    	List<Domino> expectdDominos = getDominosByIDs(string);
    	expectdDominos = new ArrayList<>(expectdDominos);
    	nextDraft = game.getNextDraft();
    	List<Domino> dominosInDraft = nextDraft.getIdSortedDominos();
    	assertEquals(expectdDominos, dominosInDraft);
    }
    
    @Then("the dominoes in the next draft are face down")
    public void the_dominoes_in_the_next_are_face_down() {
    	assertEquals(DraftStatus.FaceDown, game.getNextDraft().getDraftStatus());
    }
    
    @Then("the top domino of the pile is ID {int}")
    public void the_top_domino_of_the_pile_is_ID(Integer int4) {
    	Domino topDominoInPile = game.getTopDominoInPile();
    	assertEquals(int4, topDominoInPile.getId());
    }
    
    @Then("the former next draft is now the current draft")
    public void the_former_next_draft_is_now_the_current_draft() throws InvalidInputException {
    	
    	if(KingDominoController.hasMoreDraft(game.getAllDrafts().size())){
    		
    	KingDominoController.sortNextDraft(game);
    	KingDominoController.revealNextDraft(game);
    	assertEquals(nextDraft, game.getCurrentDraft());}
    }
    
    //Scenario Outline: Revealing the next draft when there are no more dominoes still in the pile
    @Given("this is a {int} player game")
    public void this_is_a_player_game(Integer int1) {
    	game.setNumberOfPlayers(int1);

    }
    
    @Given("there has been {int} drafts created1")
    public void there_has_been_drafts_created1(Integer int1) {
    	for (int e = 0; e < int1; e++) {
			Draft draft = new Draft(null, game);
		}
    }
    
    @When("create next draft is initiated1")
    public void create_next_draft_is_initiated1() throws InvalidInputException {
    	game.setCurrentDraft(new Draft(null, game));
    	Domino d = new Domino(9, TerrainType.Lake, TerrainType.Lake, 0, game);
		game.setTopDominoInPile(d);
		game.setNextDraft(null);
		KingDominoController.createNextDraft(game);
    }
    
    @Then("the pile is empty")
    public void the_pile_is_empty() {
    	boolean isNull = false;
    	try {
    	game.getTopDominoInPile().getNextDomino();
    	}catch(NullPointerException e) {
    		isNull = true;
    	}
    	assertEquals(true, isNull);
    	
    }
    
    @Then("there is no next draft")
    public void there_is_no_next_draft() {
    	boolean isNull = false;
    	try {
    	game.getTopDominoInPile().getNextDomino();
    	}catch(NullPointerException e) {
    		isNull = true;
    	}
    	assertEquals(true, isNull);
    	
    }
    
    @Then("the former next draft is now the current draft1")
    public void the_former_next_draft_is_now_the_current_draft1() throws InvalidInputException {
    	KingDominoController.sortNextDraft(game);
    	KingDominoController.revealNextDraft(game);
    	assertEquals(game.getNextDraft().getIdSortedDominos(), game.getCurrentDraft().getIdSortedDominos());
    }
    
    
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
	
	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

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
