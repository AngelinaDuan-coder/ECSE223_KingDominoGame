
package ca.mcgill.ecse223.kingdomino.features;



import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.hamcrest.core.Is;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.HashMap;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {
	private boolean check;
	private boolean check1;
	boolean loadGame;
	private DominoInKingdom domInKingdom; //from preplaced domino method @Amani
	private String result; //for castle adjacency test, grid size test, neighbor adjacency test, and overlapping test @Amani
	Game currentGame;
	boolean confirmation =true;
	Kingdomino kingdomino = KingdominoApplication.getKingdomino();
	private HashMap<String, Player> colorOfPlayers;
	private boolean readyToSartGame;
	// for ProvideUserProfile
	private String status;
	List<User> allUsers;
	
	// for RotateDomino
	DominoInKingdom dominoInKingdomToRotate;
	/** initialize a new game
	 * @author Angelina
	*/
	@Given("the game is initialized for choose next domino")
	public void the_game_is_initialized_for_choose_next_domino() {
		Kingdomino kingdomino = new Kingdomino(); 
		Game game = new Game(48,kingdomino); 
		game.setNumberOfPlayers(4); 
		addDefaultUsersAndPlayers(game);
		colorOfPlayers = new HashMap<String, Player>();
		colorOfPlayers.put("blue", game.getPlayer(0));
		colorOfPlayers.put("green", game.getPlayer(1));
		colorOfPlayers.put("yellow", game.getPlayer(2));
		colorOfPlayers.put("pink", game.getPlayer(3));
		kingdomino.addAllGame(game);
		kingdomino.setCurrentGame(game); 
		createAllDominoes(game);
		KingdominoApplication.setKingdomino(kingdomino);
	}
	/** sort the order of next draft
	 * @author Angelina
	*/
	@Given("the next draft is sorted with dominoes {string}")
	public void the_next_draft_is_sorted_with_dominoes(String string) {
		String order[];
		order=string.split(",");
		int first = Integer.parseInt(order[0]);
		int second = Integer.parseInt(order[1]);
		int third = Integer.parseInt(order[2]);
		int forth = Integer.parseInt(order[3]);
		Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft = new Draft(DraftStatus.Sorted, currentGame);
		
		nextDraft.addIdSortedDomino(currentGame.getAllDomino(first-1));
		currentGame.getAllDomino(first-1).setStatus(DominoStatus.InNextDraft);
		nextDraft.addIdSortedDomino(currentGame.getAllDomino(second-1));
		currentGame.getAllDomino(second-1).setStatus(DominoStatus.InNextDraft);
		nextDraft.addIdSortedDomino(currentGame.getAllDomino(third-1));
		currentGame.getAllDomino(third-1).setStatus(DominoStatus.InNextDraft);
		nextDraft.addIdSortedDomino(currentGame.getAllDomino(forth-1));
		currentGame.getAllDomino(forth-1).setStatus(DominoStatus.InNextDraft);
		
		currentGame.setNextDraft(nextDraft);
	}
	/** player make a selection of dominos
	 * @author Angelina
	*/
	@Given("player's domino selection {string}") // missing space in feature
	public void players_domino_selection(String string) {
	   String selectionArr[] = string.split(",");
	   for(int i=0; i<selectionArr.length;i++) {
		   if(!selectionArr[i].equals("none")) {
			   new DominoSelection(colorOfPlayers.get(selectionArr[i]), KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(i), KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
		   }
		}
	}
	/** current player choose to place king
	 * @author Angelina
	*/
	@When("current player chooses to place king on {string}")
	public void current_player_chooses_to_place_king_on(String string) throws InvalidInputException {
		try {
			KingDominoController.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer(), Integer.parseInt(string));
		} catch (InvalidInputException e) {
			System.out.println(e); // controller method throws error as expected, do nothing
		}
	}
	/** get the position of the king
	 * @author Angelina
	*/
	@Then("current player king now is on {string}")
	public void current_player_king_now_is_on(String string) {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getDominoSelection().getDomino().getId(), Integer.parseInt(string));
	}
	/** get new selection
	 * @author Angelina
	*/
	@Then("the selection for next draft is now equal to {string}")
	public void the_selection_for_next_draft_is_now_equal_to(String string) {//Here pink is not adding...
		String selectionCSV = "";
		for (Domino domino : KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDominos()) {
			if(domino.hasDominoSelection()) {
				selectionCSV += domino.getDominoSelection().getPlayer().getColor().toString().toLowerCase();
			} else {
				selectionCSV+="none";
			}
			selectionCSV += ",";
		}
		selectionCSV = selectionCSV.substring(0, selectionCSV.length() - 1);
		assertEquals(string, selectionCSV);
	}	
	/** set the current player
	 * @author Angelina
	*/
	@Given("the current player is {string}") // watch out for extra space between current and player in feature
	public void the_current_player_is(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(util.playerFromString(game, string));
	}
	/** get the selection
	 * @author Angelina
	*/
	@Then("the selection for the next draft selection is still {string}")
	public void the_selection_for_the_next_draft_selection_is_still(String string) {
		String selectionCSV = "";
		for (Domino domino : KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDominos()) {
			if(domino.hasDominoSelection()) {
				selectionCSV += domino.getDominoSelection().getPlayer().getColor().toString().toLowerCase();
			} else {
				selectionCSV+="none";
			}
			selectionCSV += ",";
		}
		selectionCSV = selectionCSV.substring(0, selectionCSV.length() - 1);
		assertEquals(string, selectionCSV);
	 }
	/** get the position of the king
	 * @author Angelina
	*/
	@When("current player chooses to place king on {int}")
	public void current_player_chooses_to_place_king_on(Integer int1) {
		try {
			KingDominoController.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer(), int1);
		} catch (InvalidInputException e) {
			System.out.println(e); // controller method throws error as expected, do nothing
		}
	}
	

	

	//game options!!!
	/** initialize a new game
	 * @author Angelina
	*/
	@Given("the game is initialized for set game options")
	public void the_game_is_initialized_for_set_game_options() {
		Kingdomino kingdomino = new Kingdomino(); 
		Game game = new Game(48,kingdomino); 
		game.setNumberOfPlayers(4); 
		kingdomino.setCurrentGame(game); 
		addDefaultUsersAndPlayers(game); 
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	/** initialize set game options
	 * @author Angelina
	*/
	@When("set game options is initiated")
	public void set_game_options_is_initiated() {
		KingdominoApplication.getKingdomino().getCurrentGame().hasSelectedBonusOptions();
	}
	/**set number of players
	 * @author Angelina
	*/
	@When("the number of players is set to {int}")
	public void the_number_of_players_is_set_to(Integer nplayers) {
		KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(nplayers);
	}
	/** set or unset Harmony
	 * @author Angelina
	*/
	@When("Harmony {string} selected as bonus option")
	public void harmony_selected_as_bonus_option(String string) throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if(string.equals("is")) {
			System.out.println("setting..........");
			System.out.println(string);
			KingDominoController.setHarmonyBonusOption(game);
		} else {
			KingDominoController.unsetHarmonyBonusOption(game);
		}
	}

	/** set or unset Middle Kingdom
	 * @author Angelina
	*/
	@When("Middle Kingdom {string} selected as bonus option")
	public void middle_Kingdom_selected_as_bonus_option(String string) throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if(string.equals("is")) {
			System.out.println("setting..........");
			KingDominoController.setMiddleKingdomBonusOption(game);
		} else {
			KingDominoController.unsetMiddleKingdomBonusOption(game);
		}
	}
	/** compare the number of players with int
	 * @author Angelina
	*/
	@Then("the number of players shall be {int}")
	public void the_number_of_players_shall_be(Integer int1) {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(), int1.intValue());
	}
	/** check that if Harmony is correctly set or unset
	 * @author Angelina
	*/
	@Then("Harmony {string} an active bonus")
	public void harmony_an_active_bonus(String string) {
		List<String> bonusOptionNames = new ArrayList<String>();
		
		for (BonusOption bonusOption : KingdominoApplication.getKingdomino().getCurrentGame().getSelectedBonusOptions()) {
			bonusOptionNames.add(bonusOption.getOptionName());
			System.out.println(bonusOption.getOptionName());
		}
		
		if (string.equals("is")) {
			assertTrue(bonusOptionNames.contains("Harmony"));
		} else if (string.equals("is not")) {
			assertTrue(! bonusOptionNames.contains("Harmony"));
		}
	}
	/** check that if Middle Kingdom is correctly set or unset
	 * @author Angelina
	*/
	@Then("Middle Kingdom {string} an active bonus")
	public void middle_Kingdom_an_active_bonus(String string) {
		List<String> bonusOptionNames = new ArrayList<String>();
		
		for (BonusOption bonusOption : KingdominoApplication.getKingdomino().getCurrentGame().getSelectedBonusOptions()) {
			bonusOptionNames.add(bonusOption.getOptionName());
			System.out.println(bonusOption.getOptionName());
		}
		
		if (string.equals("is")) {
			assertTrue(bonusOptionNames.contains("Middle Kingdom"));
		} else if (string.equals("is not")) {
			assertTrue(! bonusOptionNames.contains("Middle Kingdom"));
		}
	}

	//shuffle
	/** initialize a new game
	 * @author Angelina
	*/
	@Given("the game is initialized for shuffle dominoes") 
	public void the_game_is_initialized_for_shuffle_dominoes() { // Intialize empty game
		Kingdomino kingdomino = new Kingdomino(); 
		Game game = new Game(48,kingdomino); 
		game.setNumberOfPlayers(4); 
		kingdomino.setCurrentGame(game); //
		addDefaultUsersAndPlayers(game); 
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino); }
	/** set number of players
	 * @author Angelina
	*/
	@Given("there are {int} players playing") 
	public void there_are_players_playing(Integer nplayers) { 
		KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(nplayers);
	}
	/** initialize shuffle dominos
	 * @author Angelina
	*/
	@When("the shuffling of dominoes is initiated") 
	public void the_shuffling_of_dominoes_is_initiated() {
		KingDominoController.shuffleDominos(KingdominoApplication.getKingdomino().getCurrentGame()); 
	}
	/** the first draft should not be null
	 * @author Angelina
	*/
	@Then("the first draft shall exist") 
	public void the_first_draft_shall_exist() { 
		assertNotNull( KingdominoApplication.getKingdomino().getCurrentGame() .hasNextDraft()); 
	}
	/** check the number of dominos on board
	 * @author Angelina
	*/
	@Then("the first draft should have {int} dominoes on the board face down" ) 
	public void the_first_draft_should_have_dominoes_on_the_board_face_down(Integer dominoesonboard) { 
		assertEquals( KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() ,dominoesonboard.intValue()); }//question!!!
	/** check the number of dominos left
	 * @author Angelina
	*/
	@Then("there should be {int} dominoes left in the draw pile") 
	public void there_should_be_dominoes_left_in_the_draw_pile(Integer dominoesLeft) {
		assertEquals(48-KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(),dominoesLeft.intValue()); }
	/** initialize the fix arrangement
	 * @author Angelina
	*/
	@When("I initiate to arrange the domino in the fixed order {string}") public
	void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {
		
		String arr[]=string.split("\\D ?");
		int[] ints = new int[arr.length];
		for (int i = 0; i < ints.length; i++) {
			ints[i] = Integer.parseInt(arr[i]);
		}
		
		//KingDominoController.fixedArrangement(ints); 
	}
	/** the dominos left are in fixed order
	 * @author Angelina
	*/
	@Then("the draw pile should consist of everything in {string} except the first {int} dominoes with their order preserved") 
	public void the_draw_pile_should_consist_of_everything_in_except_the_first_dominoes_with_their_order_preserved
	(String string, Integer dominoesOnBoard) {

		String arr[]=string.split("\\D ?"); 
		Integer[] num = new Integer[arr.length];
		for(int i = 0;i < arr.length;i++) { 
			num[i] = Integer.parseInt(arr[i]); 
		}

		LinkedList<Integer> allDominoes = new LinkedList<Integer>(); 
		Domino domino = util.getDominoByID(1);
		allDominoes.add(domino.getId());
		while (domino.hasPrevDomino()) {
			domino = domino.getPrevDomino();
			allDominoes.addFirst(domino.getId());
		}
		domino = util.getDominoByID(1);
		while (domino.hasNextDomino()) {
			domino = domino.getNextDomino();
			allDominoes.addLast(domino.getId());
		}


		LinkedList<Integer> expected= new LinkedList<Integer>(Arrays.asList(num));
		while(!allDominoes.isEmpty()&&!expected.isEmpty()) {
			if(allDominoes.getFirst()==expected.getFirst()) { 
				allDominoes.removeFirst();
				expected.removeFirst(); 
			} 
			else { expected.removeFirst(); 
			} 
		}

		assertTrue(allDominoes.isEmpty());

	}

	@Given("the player's kingdom has the following domines:")
	public void the_player_s_kingdom_has_the_following_domines(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.parseInt(map.get("id"));
			DirectionKind dir = util.directionKindFromString(map.get("dominodir"));
			Integer posx = Integer.parseInt(map.get("posx"));
			Integer posy = Integer.parseInt(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = game.getPlayer(0).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}
	
	/** the game is initialized for calculate bonus scores
	 * @author Chen
	*/
	@Given("the game is initialized for calculate bonus scores")
	public void the_game_is_initialized_for_calculate_bonus_scores() {
		Kingdomino kingdomino = new Kingdomino();
		//kingdomino.clearUser();
		Game game = new Game(48, kingdomino);	
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	/** add the Harmony to the bonus option
	 * @author Chen
	*/
	@Given("Harmony is selected as bonus option")
	public void harmony_is_selected_as_bonus_option() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		BonusOption aSelectedBonusOption = new BonusOption("Harmony", KingdominoApplication.getKingdomino());
		game.addSelectedBonusOption(aSelectedBonusOption);

	}
	
	/** add the Middle Kingdom to the bonus option
	 * @author Chen
	*/
	@Given("Middle Kingdom is selected as bonus option")
	public void middle_Kingdom_is_selected_as_bonus_option() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		BonusOption aSelectedBonusOption = new BonusOption("Middle Kingdom", KingdominoApplication.getKingdomino());
		game.addSelectedBonusOption(aSelectedBonusOption);

	}

	/**
	 * @author Chen
	*/
	@When("calculate bonus score is initiated")
	public void calculate_bonus_score_is_initiated() {	
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer= game.getPlayer(0);
		KingDominoController.calculateBonusScore(aPlayer);	
	}

	/** compare the calculated bonus score and expected score
	 * @author Chen
	*/
	@Then("the bonus score should be {int}")
	public void the_bonus_score_should_be(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer= game.getPlayer(0);
		assertEquals(int1, aPlayer.getBonusScore());

	}

	/** compare the calculated bonus score and expected score
	 * @author Chen
	*/
	@Then("The bonus score should be {int}")
	public void the_bonus_score_should_be2(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer= game.getPlayer(0);
		assertEquals(int1, aPlayer.getBonusScore());
	}

	/** add additional dominos to the kingdom
	 * @author Chen
	*/
	@Given("the player's kingdom also includes the following dominoes:")
	public void the_player_s_kingdom_also_includes_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = util.directionKindFromString(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = game.getPlayer(0).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	/** the game is initialized for calculate player score
	 * @author Chen
	*/
	@Given("the game is initialized for calculate player score")
	public void the_game_is_initialized_for_calculate_player_score() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	/** add bonus options to the game
	 * @author Chen
	*/
	@Given("the game has {string} bonus option")
	public void the_game_has_bonus_option(String string) throws InvalidInputException {
		KingDominoController.setBonusOptionForGame(KingdominoApplication.getKingdomino().getCurrentGame(), string);
		
	}

	/** calculate player score is initiated
	 * @author Chen
	*/
	@When("calculate player score is initiated")
	public void calculate_player_score_is_initiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer= game.getPlayer(0);
		KingDominoController.calculatePlayerScore1(aPlayer);
		
	}

	/** compare the calculated socre and expected score
	 * @author Chen
	*/
	@Then("the total score should be {int}")
	public void the_total_score_should_be(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer= game.getPlayer(0);
		assertEquals(int1, aPlayer.getTotalScore());

	}
	
	@Given("the game is initialized for load game")
	public void the_game_is_initialized_for_load_game() {
		Game game = KingDominoController.createGametoload();
		
	}
	String errormsg;
	@When("I initiate loading a saved game from {string}")
	public void i_initiate_loading_a_saved_game_from(String string) throws FileNotFoundException {
	
		 try {
				loadGame =KingDominoController.loadGame(string);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				errormsg =e.getMessage();
			}
		
	}

	@When("each tile placement is valid")
	public void each_tile_placement_is_valid() throws FileNotFoundException, InvalidInputException {
		for(int i = 0 ; i < 4 ; i++) {
			Kingdom k = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(i).getKingdom();
			for (KingdomTerritory dk : k.getTerritories()) {
				DominoInKingdom s = (DominoInKingdom) dk;
				KingDominoController.verifyDominoPreplacement(s);
			}
		}
	}

	@When("the game result is not yet final")
	public void the_game_result_is_not_yet_final() {
		Game game  = KingdominoApplication.getKingdomino().getCurrentGame();
		assertTrue(game.getAllDomino(0) != null); //make sure that domino pile is not empty 
		readyToSartGame = true;
	}

	@Then("it shall be player {int}'s turn")
	public void it_shall_be_player_s_turn(Integer int1) throws FileNotFoundException {
	Game game  = KingdominoApplication.getKingdomino().getCurrentGame();
	Player p = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
	int i = 1;
	for(Player pl : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
		if (pl.equals(p)) {
			break;
		}
		i++;
	}
	assertEquals(i, int1);
	}

	@Then("each of the players should have the corresponding tiles on their grid:")
	public void each_of_the_players_should_have_the_corresponding_tiles_on_their_grid(io.cucumber.datatable.DataTable dataTable) {
		for(int i = 0; i<4; i++) {
			List<Map<String, String>> claimed = dataTable.asMaps();
			String s = claimed.get(i).toString();
			String [] C  = s.split(", ");
			String playerTiles = C[1];
			playerTiles =playerTiles.substring(12,playerTiles.length()-1);
			String [] tile = playerTiles.split(",");
			int terrSize =KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(i).getKingdom().getTerritories().size();
			assertEquals(tile.length, terrSize);
		}				
	}

	@Then("each of the players should have claimed the corresponding tiles:")
	public void each_of_the_players_should_have_claimed_the_corresponding_tiles(io.cucumber.datatable.DataTable dataTable) throws IOException {
		for(int i = 0; i<4; i++) {
			List<Map<String, String>> claimed = dataTable.asMaps();
			//this is not ideal but I was having hard time to deal with keys anw it's fixed in other stepdefinitions
			String s = claimed.get(i).toString();
			String [] C  = s.split(", ");
			String playerTiles = C[1];
			playerTiles =playerTiles.substring(12,playerTiles.length()-1);
			int num = Integer.parseInt(playerTiles);
			int plclaim =KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(i).getDominoSelection().getDomino().getId();			
			assertEquals(plclaim, num);
	
		}				
	}
	
	@Then("tiles {string} shall be unclaimed on the board")
	public void tiles_shall_be_unclaimed_on_the_board(String string) {
		string = KingDominoController.deleteSpaces(string); //spaces are evil
		String[] s = string.split(",");
		int size = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDominos().size();
		List<Domino> u = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDominos();
		for(int i= 0; i < size ; i++) {
			assertEquals(u.get(i).getId(), Integer.parseInt(s[i]));
		}
	}

	@Then("the game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
	assertTrue(readyToSartGame);
	}

	@Given("{string}'s kingdom has the following domines:")
	public void the_player_s_kingdom_has_the_following_domines(String string, io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.parseInt(map.get("id"));
			DirectionKind dir = util.directionKindFromString(map.get("dominodir"));
			Integer posx = Integer.parseInt(map.get("posx"));
			Integer posy = Integer.parseInt(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = util.playerFromString(game, string).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}
	
	
	//I initiate loading a saved game from "<filename>"
	@Then("the game shall notify the user that the loaded game is invalid")
	public void the_game_shall_notify_the_user_that_the_loaded_game_is_invalid() throws IOException {
		assertEquals(errormsg, "Invalid input");
		
		 }
	
	
	/**
	 * @author louca
	 */
	@Given("the program is started and ready for providing user profile")
	public void the_program_is_started_and_ready_for_providing_user_profile() {
		KingdominoApplication.setKingdomino(new Kingdomino());
	}
	
	/**
	 * @author louca
	 * ensure that there are no users
	 */
	@Given("there are no users exist")
	public void there_are_no_users_exist() {
		assertTrue(KingdominoApplication.getKingdomino().getUsers().isEmpty());
	}

	/**
	 * @author louca
	 * @param string the username of the new user to create
	 */
	@When("I provide my username {string} and initiate creating a new user")
	public void i_provide_my_username_and_initiate_creating_a_new_user(String string) {
		try {
			KingDominoController.createUser(KingdominoApplication.getKingdomino(), string);
			status = "succeed";
		} catch (InvalidInputException e) {
			status = "fail";
		}
	}
	
	/**
	 * @author louca
	 * @param string the username to verify will be in the list of all users in system
	 */
	@Then("the user {string} shall be in the list of users")
	public void the_user_shall_be_in_the_list_of_users(String string) {
		assertTrue(User.getWithName(string) != null);
	}
	
	/**
	 * @author louca
	 * @param list of users a list of users to create by name
	 */
	@Given("the following users exist:")
	public void the_following_users_exist(List<String> list) {		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<String> allUserNames = new ArrayList<String>();

		for (String string : list) {
			if (string.equals("name")) continue; // skip  field name in table
			try {
				KingDominoController.createUser(kingdomino, string);
			} catch (InvalidInputException e) {
				e.printStackTrace();
			}
			
		}
		
		for (User user : kingdomino.getUsers()) {
			allUserNames.add(user.getName());
		}
		
		assertEquals(allUserNames.size(), list.size() - 1);
		for (String string : list) {
			if (string.equals("name")) continue; // skip  field name in table
			assertTrue(allUserNames.contains(string));
		}
	}
	
	/**
	 * @author louca
	 * @param string representing the desired status
	 */
	@Then("the user creation shall {string}")
	public void the_user_creation_shall(String string) {
		assertTrue(status.equals(string));
	}
	
	/**
	 * @author louca
	 * sets the private field allUsers to the controller returned list
	 */
	@When("I initiate the browsing of all users")
	public void i_initiate_the_browsing_of_all_users() {
		allUsers = KingDominoController.listAllUsers(KingdominoApplication.getKingdomino());
	}
	
	/**
	 * @author louca
	 * @param dataTable representing the list of users and there place in list
	 */
	@Then("the users in the list shall be in the following alphabetical order:")
	public void the_users_in_the_list_shall_be_in_the_following_alphabetical_order(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> valueMaps = dataTable.asMaps();

		for (Map<String, String> map : valueMaps) {
			assertEquals(map.get("name"), allUsers.get(Integer.parseInt(map.get("placeinlist")) - 1).getName()); // -1 since arrays start at 0
		}
	}
	
	@Given("the following users exist with their game statistics:")
	public void the_following_users_exist_with_their_game_statistics(io.cucumber.datatable.DataTable dataTable) {
		Kingdomino kingdomino = new Kingdomino();
		KingdominoApplication.setKingdomino(kingdomino);
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			String name = map.get("name");

			User user = new User(name, kingdomino);
			kingdomino.addUser(user);
			
			user.setPlayedGames(Integer.parseInt(map.get("playedGames")));
			user.setWonGames(Integer.parseInt(map.get("wonGames")));
		}
	}
	
	int playedGames;
	int wonGames;
	
	@When("I initiate querying the game statistics for a user {string}")
	public void i_initiate_querying_the_game_statistics_for_a_user(String string) {
		playedGames = KingDominoController.getPlayedGamesForUserByName(KingdominoApplication.getKingdomino(), string);
		wonGames = KingDominoController.getWonGamesForUserByName(KingdominoApplication.getKingdomino(), string);

	}
	
	@Then("the number of games played by {string} shall be {int}")
	public void the_number_of_games_played_by_shall_be(String string, Integer int1) {
	    for (User user : KingdominoApplication.getKingdomino().getUsers()) {
	    	if (user.getName().equals(string)) {
	    		assertEquals(int1, playedGames);
	    	}
	    }
	}

	@Then("the number of games won by {string} shall be {int}")
	public void the_number_of_games_won_by_shall_be(String string, Integer int1) {
		 for (User user : KingdominoApplication.getKingdomino().getUsers()) {
		    	if (user.getName().equals(string)) {
		    		assertEquals(int1, wonGames);
		    	}
		    }
	}
	
	@Given("the game is initialized for save game")
	public void the_game_is_initialized_for_save_game() {
		Game game = KingDominoController.createGame();
		currentGame = game;
	}

	@Given("the game is still in progress")
	public void the_game_is_still_in_progress() {
		KingdominoApplication.setCurrentGame(currentGame);
	}

	@Given("no file named {string} exists in the filesystem")
	public void no_file_named_exists_in_the_filesystem(String string) {
		String relativePath ="./src/test/resources/save_game_test.mov";
		File file = new File(relativePath);
		if(file.exists() && file.isFile()) {
			file.delete();
		}
		 assertEquals(false, file.exists());
	}

	@When("the user initiates saving the game to a file named {string}")
	public void the_user_initiates_saving_the_game_to_a_file_named(String string) throws InvalidInputException, IOException {
		KingDominoController.saveGame(string,confirmation);
	}

	@Then("a file named {string} shall be created in the filesystem")
	public void a_file_named_shall_be_created_in_the_filesystem(String string) throws InvalidInputException, IOException {
		assertTrue(KingDominoController.containsFile(string));		
	}

	@Given("the file named {string} exists in the filesystem")
	public void the_file_named_exists_in_the_filesystem(String string) throws IOException, InvalidInputException {
	   if(!KingDominoController.isExistedFile(string)) {
		   KingDominoController.createNewFile(string);
	   }
	}

	@When("the user agrees to overwrite the existing file named {string}")
	public void the_user_agrees_to_overwrite_the_existing_file_named(String string) {
		 assertEquals(confirmation, true);
	}

	@Then("the file named {string} shall be updated in the filesystem")
	public void the_file_named_shall_be_updated_in_the_filesystem(String string) throws IOException, InvalidInputException {	
		String fileData = KingDominoController.readFileAsString(string);
		String updatedData = KingDominoController.saveDataAsString();
		assertEquals(fileData, updatedData);
	}

	/**
	 * @author louca
	 */
	@Given("the program is started and ready for starting a new game")
	public void the_program_is_started_and_ready_for_starting_a_new_game() {
		KingdominoApplication.setKingdomino(new Kingdomino());
	}
	
	/**
	 * @author louca
	 */
	@Given("there are four selected players")
	public void there_are_four_selected_players() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(4*12, kingdomino);
		kingdomino.setCurrentGame(game);
		
		for (int i=0; i<4; i++) {
			Player player = new Player(game);
			game.addPlayer(player);
			player.setColor(PlayerColor.values()[i]);
			
		}
	}
	
	/**
	 * @author louca
	 */
	@Given("bonus options Harmony and MiddleKingdom are selected")
	public void bonus_options_Harmony_and_MiddleKingdom_are_selected() {

		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		game.addSelectedBonusOption(new BonusOption("Harmony", kingdomino));
		game.addSelectedBonusOption(new BonusOption("MiddleKingdom", kingdomino));
	}
	
	@When("reveal first draft is initiated")
	public void reveal_first_draft_is_initiated() {
	   Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		try {
			KingDominoController.createAndSortFirstDraft(KingdominoApplication.getKingdomino().getCurrentGame());
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @author louca
	 */
	@When("starting a new game is initiated")
	public void starting_a_new_game_is_initiated() {
		try {
			KingDominoController.startANewGame(KingdominoApplication.getKingdomino().getCurrentGame());
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @author louca
	 */
	@Then("all kingdoms shall be initialized with a single castle")
	public void all_kingdoms_shall_be_initialized_with_a_single_castle() {
		for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			List<KingdomTerritory> kingdomTerritories = player.getKingdom().getTerritories();
			assertEquals(1, kingdomTerritories.size());
			assertTrue(kingdomTerritories.get(0) instanceof Castle);
		}
	}
	
	/**
	 * @author louca
	 * @param int1 the x
	 * @param int2 the y
	 */
	@Then("all castle are placed at {int}:{int} in their respective kingdoms")
	public void all_castle_are_placed_at_in_their_respective_kingdoms(Integer int1, Integer int2) {
		for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			KingdomTerritory kingdomTerritory = player.getKingdom().getTerritories().get(0); // actually a Castle
			assertEquals(kingdomTerritory.getX(), int1);
			assertEquals(kingdomTerritory.getX(), int2);
		}
	}
	
	/**
	 * @author louca
	 */
	
	@Then("the first draft of dominoes is revealed")
	public void the_first_draft_of_dominoes_is_revealed() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		Draft currentDraft = game.getCurrentDraft();
		
		//check that there is a current draft
		assertTrue(currentDraft != null);
		
	
		
		// check that it is sorted
		int i = 0;
		for (Domino domino : currentDraft.getIdSortedDominos()) {
			if (i == 0) continue;
			assertTrue(domino.getId() > currentDraft.getIdSortedDomino(i - 1).getId()); // the domino's id is greater than the previous id, for all dominos in current draft except the first
		}
		
		// check that there is no next draft
		assertTrue(game.getNextDraft() == null);
	}
	
	/**
	 * @author louca
	 * @throws InvalidInputException 
	 */
	@Then("all the dominoes form the first draft are facing up")
	public void all_the_dominoes_form_the_first_draft_are_facing_up() throws InvalidInputException {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		KingDominoController.revealFirstDraft(game);		
		Draft currentDraft = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft();
		
		
		// check that the first draft is face up
		
		
		assertEquals(currentDraft.getDraftStatus(), DraftStatus.FaceUp);

		// check that all dominos in the current (first draft) are indeed in the current draft
		for (Domino domino : currentDraft.getIdSortedDominos()) {
			assertEquals(domino.getStatus(), DominoStatus.InCurrentDraft);
		}
	}
	
	/**
	 * @author louca
	 */
	@Then("all the players have no properties")
	public void all_the_players_have_no_properties() {
		for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			assertEquals(player.getKingdom().getProperties().size(), 0);
		}
	}

	/**
	 * @author louca
	 */
	@Then("all player scores are initialized to zero")
	public void all_player_scores_are_initialized_to_zero() {
		for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			assertEquals(0, player.getBonusScore() + player.getTotalScore() + player.getPropertyScore());
		}
	}

	/**
	 * @author Amani
	 */
	@Given("the game is initialized for castle adjacency")
	public void the_game_is_initialized_for_castle_adjacency() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}


	/**
	 * @author Amani
	 */
	@When("check castle adjacency is initiated")
	public void check_castle_adjacency_is_initiated() {
		boolean check = KingDominoController.verifyCastleAdjacency(domInKingdom);
		if(check) result = "valid";
		else result = "invalid";
	}

	/**
	 * @author Amani
	 * @param validation
	 */
	@Then("the castle\\/domino adjacency is {string}")
	public void the_castle_domino_adjacency_is(String validation) {
		assertEquals(result, validation);
	}

	/**
	 * @author Amani
	 */
	@Given("the game is initialized for verify grid size")
	public void the_game_is_initialized_for_verify_gridsize() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	/**
	 * @author Amani
	 */
	@When("validation of the grid size is initiated")
	public void validation_of_the_grid_size_is_initiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getPlayer(0).getKingdom();
		boolean check = KingDominoController.verifyKingdomSize(kingdom);
		if(check) result = "valid";
		else result = "invalid";
	}

	/**
	 * @author Amani
	 * @param validation
	 */
	@Then("the grid size of the player's kingdom shall be {string}")
	public void the_grid_size_of_the_player_s_kingdom_shall_be(String validation) {
		assertEquals(validation, result);
	}

	/**
	 * @author Amani
	 * @param id
	 * @param x
	 * @param y
	 * @param givenDir
	 */
	@Given("the  player preplaces domino {int} to their kingdom at position {int}:{int} with direction {string}")
	public void the_player_preplaces_domino_to_their_kingdom_at_position_with_direction(Integer id, Integer x, Integer y, String givenDir) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino dominoToPlace = getdominoByID(id);
		Kingdom kingdom = game.getPlayer(0).getKingdom();
		domInKingdom = new DominoInKingdom(x, y, kingdom, dominoToPlace);
		domInKingdom.setDirection(util.directionKindFromString(givenDir));
		dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
	}

	/**
	 * @author Amani
	 */
	@Given("the game is initialized for neighbor adjacency")
	public void the_game_is_initialized_for_neighbor_adjacency() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	@Given("the current player preplaced the domino with ID {int} at position {int}:{int} and direction {string}")
	public void the_current_player_preplaced_the_domino_with_ID_at_position_and_direction(Integer id, Integer x, Integer y, String givenDir) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if(id == null) id = 1;
		Domino dominoToPlace = getdominoByID(id);
		Kingdom kingdom = game.getPlayer(0).getKingdom();
		domInKingdom = new DominoInKingdom(x, y, kingdom, dominoToPlace);
		domInKingdom.setDirection(util.directionKindFromString(givenDir));
		dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
	}

	/**
	 * @author Amani
	 */
	@When("check current preplaced domino adjacency is initiated")
	public void check_current_preplaced_domino_adjacency_is_initiated() {
		List<KingdomTerritory> adjacentTerritories = KingDominoController.verifyNeighborAdjacency(domInKingdom);
		boolean check = (adjacentTerritories.size() != 0);
		if(check) result = "valid";
		else result = "invalid";
	}

	/**
	 * @author Amani
	 * @param validation
	 */
	@Then("the current-domino\\/existing-domino adjacency is {string}")
	public void the_current_domino_existing_domino_adjacency_is(String validation) {
		assertEquals(validation, result);
	}

	/**
	 * @author Amani
	 */
	@Given("the game is initialized to check domino overlapping")
	public void the_game_is_initialized_to_check_domino_overlapping() {
		// Intialize empty game
				Kingdomino kingdomino = new Kingdomino();
				Game game = new Game(48, kingdomino);
				game.setNumberOfPlayers(4);
				kingdomino.setCurrentGame(game);
				// Populate game
				addDefaultUsersAndPlayers(game);
				createAllDominoes(game);
				game.setNextPlayer(game.getPlayer(0));
				KingdominoApplication.setKingdomino(kingdomino);
	}

	/**
	 * @author Amani
	 */
	@When("check current preplaced domino overlapping is initiated")
	public void check_current_preplaced_domino_overlapping_is_initiated() {
		boolean check = KingDominoController.verifyNoOverlapping(domInKingdom);
		if(!check) result = "valid";
		else result = "invalid";
	}

	/**
	 * @author Amani
	 * @param validation
	 */
	@Then("the current-domino\\/existing-domino overlapping is {string}")
	public void the_current_domino_existing_domino_overlapping_is(String validation) {
		assertEquals(result, validation);
	}
	
	@Given("the following dominoes are present in a player's kingdom:")
	public void the_following_dominoes_are_present_in_a_player_s_kingdom(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = util.directionKindFromString(map.get("dominodir"));
			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace = getdominoByID(id);
			Kingdom kingdom = game.getPlayer(0).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	@Given("the player's kingdom also includes the domino {int} at position {int}:{int} with the direction {string}")
	public void the_player_s_kingdom_also_includes_the_domino_at_position_with_the_direction(Integer int1, Integer int2, Integer int3, String string) throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();

		// Add the domino to a player's kingdom
		Domino dominoToPlace = getdominoByID(int1);
		Kingdom kingdom = game.getPlayer(0).getKingdom();
		DominoInKingdom domInKingdom = new DominoInKingdom(int2, int3, kingdom, dominoToPlace);
		DirectionKind dir = util.directionKindFromString(string);
		domInKingdom.setDirection(dir);

		dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		
		//////placedomino
	}
	
	//This is for CalculatePropertyAttributes. Do not change it please
	@Given("player's kingdom has the following dominoes:")
	public void players_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir;

			dir = util.directionKindFromString(map.get("dominodir"));

			Integer posx = Integer.decode(map.get("posx"));
			Integer posy = Integer.decode(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace;
			dominoToPlace = getdominoByID(id);
			Kingdom kingdom = game.getPlayer(0).getKingdom();
			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);

		}
	}
	

	//This is for Calculate Bonus
		@Given("the player's kingdom has the following dominoes Bonus:")
		public void players_has_the_following_dominoes_BonusPoints(io.cucumber.datatable.DataTable dataTable) throws InvalidInputException {
			Game game = KingdominoApplication.getKingdomino().getCurrentGame();
			List<Map<String, String>> valueMaps = dataTable.asMaps();
			for (Map<String, String> map : valueMaps) {
				// Get values from cucumber table
				Integer id = Integer.decode(map.get("id"));
				DirectionKind dir;

				dir = util.directionKindFromString(map.get("dominodir"));

				Integer posx = Integer.decode(map.get("posx"));
				Integer posy = Integer.decode(map.get("posy"));

				// Add the domino to a player's kingdom
				Domino dominoToPlace;
				dominoToPlace = getdominoByID(id);
				Kingdom kingdom = game.getPlayer(0).getKingdom();
				DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
				domInKingdom.setDirection(dir);
				dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);

			}
		}
		
	

	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////

	private void addDefaultUsersAndPlayers(Game game) {
		
		String[] userNames = { "User1", "User2", "User3", "User4" };
		
		
		for (int i = 0; i < userNames.length; i++) {
			
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
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

	private Domino getdominoByID(Integer id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
}


