
package ca.mcgill.ecse223.kingdomino.features;



import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusEvaluation;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusInitializing;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


/*
 * This class contains the missing step definitions that were added for iteration-3
 */
public class Iteration3NewStepDefinitions {
	Gameplay curplay;
	//CalculatingPlayerScore.feature:


	/**The game is initialized for calculating player score
	 * @author chenkua
	 */
	@Given("the game is initialized for calculating player score")
	public void the_game_is_initalized_for_calculating_player_score() {
		Kingdomino kingdomino = new Kingdomino(); 
		Game game = new Game(48,kingdomino); 
		game.setNumberOfPlayers(4); 
		kingdomino.setCurrentGame(game); 
		addDefaultUsersAndPlayers(game); 
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);

	}

	/**
	 * @author chenkua
	 */
	@Given("the current player has no dominoes in his\\/her kingdom yet")
	public void the_current_player_has_no_dominoes_in_his_her_kingdom_yet() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getPlayer(0);

	}

	/**
	 * @author chenkua
	 * @param int1
	 */
	@Given("the score of the current player is {int}")
	public void the_score_of_the_current_player_is(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getPlayer(0);
		KingDominoController.calculatePlayerScore1(aPlayer);
		assertEquals(int1, aPlayer.getTotalScore());
	}

	/**
	 * @author chenkua
	 * @param int1
	 * @param int2
	 * @param int3
	 * @param string
	 */
	@Given("the current player is preplacing his\\/her domino with ID {int} at location {int}:{int} with direction {string}")
	public void the_current_player_is_preplacing_his_her_domino_with_ID_at_location_with_direction(Integer int1, Integer int2, Integer int3, String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino dominoToPlace = getdominoByID(int1);
		Kingdom kingdom = game.getPlayer(0).getKingdom();
		DirectionKind dir = util.directionKindFromString(string);
		domInKingdom = new DominoInKingdom(int2, int3, kingdom, dominoToPlace);
		domInKingdom.setDirection(dir);
		dominoToPlace.setStatus(DominoStatus.CorrectlyPreplaced);
	}

	/**
	 * @author chenkua
	 * @param string
	 */
	@Given("the preplaced domino has the status {string}")
	public void the_preplaced_domino_has_the_status(String string) {

	}

	/**
	 * @author chenkua
	 * @throws InvalidInputException
	 */
	@When("the current player places his\\/her domino")
	public void the_current_player_places_his_her_domino() throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getPlayer(0).getKingdom();

		for(KingdomTerritory dk: kingdom.getTerritories()) {
			if(dk instanceof DominoInKingdom && ((DominoInKingdom) dk).getDomino().getStatus()==DominoStatus.CorrectlyPreplaced) {
				KingDominoController.placeDominoInKingdom((DominoInKingdom) dk);
			}
		}
	}

	/**
	 * @author chenkua
	 * @param int1
	 */
	@Then("the score of the current player shall be {int}")
	public void the_score_of_the_current_player_shall_be(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer = game.getPlayer(0);
		KingDominoController.calculatePlayerScore1(aPlayer);
		assertEquals(int1, aPlayer.getTotalScore());
	}

	/**
	 * @author chenkua
	 */
	@Given("the game has no bonus options selected")
	public void the_game_has_no_bonus_options_selected() {
		//do nothing, since we need to generate bonus options and add optins.
		//if no options, do not generate them
	}

	/**
	 * @author chenkua
	 * @param int1
	 */

	DominoInKingdom domInKingdom;

	@Given("the current player is placing his\\/her domino with ID {int}")
	public void the_current_player_is_placing_his_her_domino_with_ID(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino dominoToPlace = getdominoByID(int1);
		Kingdom kingdom = game.getPlayer(0).getKingdom();

		domInKingdom = new DominoInKingdom(-4,-4,kingdom, dominoToPlace);
	}

	@Given("it is impossible to place the current domino in his\\/her kingdom")
	public void it_is_impossible_to_place_the_current_domino_in_his_her_kingdom() throws InvalidInputException {
		KingDominoController.discardDomino(domInKingdom);
		
	}


	@When("the current player discards his\\/her domino")
	public void the_current_player_discards_his_her_domino() {
		assertEquals(domInKingdom.getDomino().getStatus(),DominoStatus.Discarded);
	}


	//DiscardingDomino.feature:
	/**
	 * @author Amani
	 */
	@Given("the game is initialized for discarding domino")
	public void the_game_is_initialized_for_discarding_domino() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	/**
	 * @author: Amani
	 */
	@Given("it is not the last turn of the game")
	public void it_is_not_the_last_turn_of_the_game() {
		curplay = new Gameplay();
		curplay.setGamestatus("ProceedingToNextTurn");
		curplay.setTotal_No_Draft(1);
		
	}

	/**
	 * @author: Amani
	 */
	@Given("the current player is not the last player in the turn")
	public void the_current_player_is_not_the_last_player_in_the_turn() {
		curplay.setPlacingOrder(1);
		curplay.setSelectingOrder(1);
	}

	/**
	 * @author: Amani
	 */
	@Then("this player now shall be making his\\/her domino selection")
	public void this_player_now_shall_be_making_his_her_domino_selection() {
		assertEquals(curplay.getSelectingOrder(), 1);
	}

	/**
	 * @auhor: Amani
	 */
	@Given("the current player is the last player in the turn")
	public void the_current_player_is_the_last_player_in_the_turn() {
		curplay.setPlacingOrder(4);
	}

	/**
	 * @author: Amani
	 * @throws InvalidInputException
	 */
	@Then("a new draft shall be available")
	public void a_new_draft_shall_be_available() throws InvalidInputException {
		curplay.createNextDraft();
		assertNotNull(curplay.getGame().getNextDraft());
	}

	/**
	 * @author: Amani
	 * @throws InvalidInputException
	 */
	@Then("the draft shall be revealed")
	public void the_draft_shall_be_revealed() throws InvalidInputException {
		curplay.orderNextDraft();
		curplay.revealNextDraft();
	}


	//DiscardingLastDomino.feature
	/**
	 * @author: Amani
	 */
	@Given("the game is initialized for discarding last domino")
	public void the_game_is_initialized_for_discarding_last_domino() {
		// Write code here that turns the phrase above into concrete actions
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	@Given("it is the last turn of the game")
	public void it_is_the_last_turn_of_the_game() {
		curplay = new Gameplay();
		curplay.setTotal_No_Draft(12);
	}

	@Then("the next player shall be placing his\\/her domino")
	public void the_next_player_shall_be_placing_his_her_domino() {
		curplay.setPlacingOrder(2);
	}

	@Then("the game shall be finished")
	public void the_game_shall_be_finished() {
		curplay.setGamestatus("Evaluation");
	}

	@Then("the final results after discard shall be computed")
	public void the_final_results_after_discard_shall_be_computed() {
		// Write code here that turns the phrase above into concrete actions
		curplay.calculateAllPlayersScores();
	}





	/**Following are: InitializingGame.feature
	 * @author chenkua
	 * @author Amani
	 */
	@Given("the game has not been started")
	public void the_game_has_not_been_started() {
		Kingdomino kingdomino = new Kingdomino(); 
		KingdominoApplication.setKingdomino(kingdomino);
		assertNull(KingdominoApplication.getKingdomino().getCurrentGame());
	}

	@When("start of the game is initiated")
	public void start_of_the_game_is_initiated() throws InvalidInputException {		
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48,kingdomino); 
		game.setNumberOfPlayers(4); 
		kingdomino.setCurrentGame(game); 
		addDefaultUsersAndPlayersWithoutKingdoms(game); 
		KingDominoController.startANewGame(KingdominoApplication.getKingdomino().getCurrentGame());
		assertNull(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft());	

	}

	@Then("the pile shall be shuffled")
	public void the_pile_shall_be_shuffled() {
		curplay = new Gameplay();
	}


	@Then("the first draft shall be on the table")
	public void the_first_draft_shall_be_on_the_table() throws InvalidInputException {
		//curplay.createAndSortFirstDraft();  
		assertNotNull(curplay.getGame().getCurrentDraft());
	}

	@Then("the first draft shall be revealed")
	public void the_first_draft_shall_be_revealed() throws InvalidInputException {
		curplay.revealFirstDraft();
	}

	@Then("the initial order of players shall be determined")
	public void the_initial_order_of_players_shall_be_determined() {
		curplay.generateInitialPlayerOrder();
		//Player firstPlayer = curplay.getGame().getNextPlayer();
		List<Player> playersOrder = curplay.getGame().getCurrentOrder();
		assertNotNull(playersOrder);
	}

	@Then("the first player shall be selecting his\\/her first domino of the game")
	public void the_first_player_shall_be_selecting_his_her_first_domino_of_the_game() {
		curplay.setSelectingOrder(0);
	}

	@Then("the second draft shall be on the table, face down")
	public void the_second_draft_shall_be_on_the_table_face_down() throws InvalidInputException {
		curplay.setGamestatus("ProceedingToNextTurn");
		curplay.createNextDraft();
		curplay.orderNextDraft();
		assertNotNull(curplay.getGame().getNextDraft());
	}



	//PlacingDomino.feature:
	/**
	 * @author chenkua
	 */
	@Given("the game has been initialized for placing domino")
	public void the_game_has_been_initialized_for_placing_domino() {
		Kingdomino kingdomino = new Kingdomino(); 
		Game game = new Game(48,kingdomino); 
		game.setNumberOfPlayers(4); 
		kingdomino.setCurrentGame(game); 
		addDefaultUsersAndPlayers(game); 
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		
	}



	//PlacingLastDomino.feature
	@Given("the game has been initialized for placing last domino")
	public void the_game_has_been_initialized_for_placing_last_domino() {
		Kingdomino kingdomino = new Kingdomino(); 
		Game game = new Game(48,kingdomino); 
		game.setNumberOfPlayers(4); 
		kingdomino.setCurrentGame(game); 
		addDefaultUsersAndPlayers(game); 
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	
	@Then("the final results after successful placement shall be computed")
	public void the_final_results_after_successful_placement_shall_be_computed() {
		
		assertEquals(curplay.getGamestatusEvaluation(),GamestatusEvaluation.CalculatingPlayersScore);
	}







	/////////////////////
	/////////Help Method/
	/////////////////////

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

	private void addDefaultUsersAndPlayersWithoutKingdoms(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
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

