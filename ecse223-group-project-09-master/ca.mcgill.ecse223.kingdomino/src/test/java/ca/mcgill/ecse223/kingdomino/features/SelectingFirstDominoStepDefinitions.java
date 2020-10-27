package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SelectingFirstDominoStepDefinitions {
	Gameplay curplay;
	Boolean success;
	/*
	 * 
	 * @author chenkua
	 */
	//SelectingFirstDomino.feature:
	@Given("the game has been initialized for selecting first domino")
	public void the_game_has_been_initialized_for_selecting_first_domino() {
		Kingdomino kingdomino = new Kingdomino(); 
		Game game = new Game(48,kingdomino); 
		game.setNumberOfPlayers(4); 
		kingdomino.setCurrentGame(game); 
		addDefaultUsersAndPlayers(game); 
		createAllDominoes(game);
		KingdominoApplication.setKingdomino(kingdomino);
		Gameplay play = new Gameplay();
		curplay= play;
	}

	
	/* Feature SelecingFirstDomino
	 * @author chenkua
	 */
	@Given("the initial order of players is {string}")
	public void the_initial_order_of_players_is(String string) {
		
		List<String> order = Arrays.asList(string.split(","));
		List<Player> neworder = new ArrayList<Player>();
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame(); 
		
		for(String s : order) {
			neworder.add(getPlayerFromColor(s));
		}
		
		game.setCurrentOrder(neworder);
		
		for(Player p: game.getCurrentOrder()) {
			System.out.println(p.getColor());
		}
		
	}
	
	int domino;
	@Given("the {string} player is selecting his\\/her domino with ID {int}")
	public void the_player_is_selecting_his_her_domino_with_ID(String string, Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame(); 
	
		Player p = getPlayerFromColor(string);
		int tmp = 0;
		for(int i=0; i<4;i++) {
			if(game.getCurrentOrder(i).equals(p)) {
			tmp=i;
			break;
			}
		}
		curplay.setSelectingOrder(tmp);
		System.out.println(p.getColor());
		success = curplay.DominoSelectable(int1);
		System.out.println(curplay.DominoSelectable(int1));
		domino = int1;
		
	}
	
	/*
	 * 
	 * @author chenkua
	 */
	@Given("Selecting player's domino selection {string}") 
	public void players_domino_selection(String string) {
	   Game game = KingdominoApplication.getKingdomino().getCurrentGame(); 
		
	   String selectionArr[] = string.split(",");
	   for(int i=0; i<selectionArr.length;i++) {
		   if(!selectionArr[i].equals("none")) {
			   
			   new DominoSelection(getPlayerFromColor(selectionArr[i]), game.getCurrentDraft().getIdSortedDomino(i), game.getCurrentDraft());
			   }
		}
	}
	
	/**SelecingFirstDomino
	 * @author chenkua
	 * @param string
	 */
	@Then("the {string} player shall be selecting his\\/her domino")
	public void the_player_shall_be_selecting_his_her_domino(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		System.out.println("//////////////////////");
		System.out.println(curplay.getSelectingOrder());
		String  color = game.getCurrentOrder(curplay.getSelectingOrder()).getColor().toString().toLowerCase();
		assertEquals(string, color);
	}

	
	////////////////Given the "yellow" player is selecting his/her first domino with ID 4 
	@Given("the {string} player is selecting his\\/her first domino with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_with_ID(String string, Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame(); 
		
		Player p = getPlayerFromColor(string);
		int tmp = 0;
		for(int i=0; i<4;i++) {
			if(game.getCurrentOrder(i).equals(p)) {
			tmp=i;
			break;
			}
		}
		curplay.setSelectingOrder(tmp);
		System.out.println(p.getColor());
		success = curplay.DominoSelectable(int1);
		System.out.println(curplay.DominoSelectable(int1));
		domino = int1;
	}

	
	/**
	 * @author chenkua
	 */
	@Given("the game has been initialized for selecting domino")
	public void the_game_has_been_initialized_for_selecting_domino() {
		Kingdomino kingdomino = new Kingdomino(); 
		Game game = new Game(48,kingdomino); 
		game.setNumberOfPlayers(4); 
		kingdomino.setCurrentGame(game); 
		addDefaultUsersAndPlayers(game); 
		createAllDominoes(game);
		KingdominoApplication.setKingdomino(kingdomino);
		Gameplay play = new Gameplay();
		curplay= play;
		
	}

	/** Feature SelecingDomino
	 * @author chenkua
	 * @param string
	 */
	@Given("the order of players is {string}")
	public void the_order_of_players_is(String string) {

		List<String> order = Arrays.asList(string.split(","));
		List<Player> neworder = new ArrayList<Player>();
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame(); 
		
		for(String s : order) {
			neworder.add(getPlayerFromColor(s));
		}
		
		game.setCurrentOrder(neworder);
		
		for(Player p: game.getCurrentOrder()) {
			System.out.println(p.getColor());
		}
	}

	@Given("the current draft has the dominoes with ID {string}")
	public void the_current_draft_has_the_dominoes_with_ID(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<String> order = Arrays.asList(string.split(","));
		Draft tmp = new Draft(DraftStatus.FaceUp,game);
		List<Domino> domoino_order = new ArrayList<>();
		for(String s: order) {
			
			Integer i = Integer.parseInt(s);
			domoino_order.add(getdominoByID(i));
			System.out.println(i);
			tmp.addIdSortedDomino(getdominoByID(i));
			
		}
	
		
		
		 game.setCurrentDraft(tmp); 
		
		System.out.println(game.getCurrentDraft()!=null);
		
	}

	// We use the annotation @And to signal precondition check instead of
		// initialization (which is done in @Given methods)
		@And("the validation of domino selection returns {string}")
		public void the_validation_of_domino_selection_returns(String expectedValidationResultString) {
			String actualValidationResult;
			
			
			System.out.println();
			
			
			if (success) {
				actualValidationResult ="success";
			}else {
				actualValidationResult = "error";
			}
				
				

			// TODO call here the guard function from the statemachine and store the result
			// actualValidationResult = gameplay.isSelectionValid();

			// Check the precondition prescribed by the scenario
			assertEquals(expectedValidationResultString, actualValidationResult);
		}
	
		
	/*Feature selecingDomino
	 * @author chenkua
	 */
	@Given("the {string} is selecting his\\/her domino with ID {int}")
	public void the_is_selecting_his_her_domino_with_ID(String string, Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame(); 
		
		Player p = getPlayerFromColor(string);
		int tmp = 0;
		for(int i=0; i<4;i++) {
			if(game.getCurrentOrder(i).equals(p)) {
			tmp=i;
			break;
			}
		}
		curplay.setSelectingOrder(tmp);
		System.out.println(p.getColor());
		success = curplay.DominoSelectable(int1);
		System.out.println(curplay.DominoSelectable(int1));
		domino = int1;
	}

	
	@When("the {string} player completes his\\/her domino selection")
	public void the_player_completes_his_her_domino_selection(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if(success) {
			
			curplay.setSelectingOrder(curplay.getSelectingOrder()+1);
			
		}
	}

	/** Feature SelecingDomino
	 * @author chenkua
	 * @param string
	 */
	@Then("the {string} player shall be selecing his\\/her domino")
	public void the_player_shall_be_placing_his_her_domino(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		System.out.println("//////////////////////");
		System.out.println(curplay.getSelectingOrder());
		String  color = game.getCurrentOrder(curplay.getSelectingOrder()).getColor().toString().toLowerCase();
		assertEquals(string, color);
		
	}

	@Given("the {string} player is selecting his\\/her first domino of the game with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_of_the_game_with_ID(String string, Integer int1) {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame(); 
		
		Player p = getPlayerFromColor(string);
		int tmp = 0;
		for(int i=0; i<4;i++) {
			if(game.getCurrentOrder(i).equals(p)) {
			tmp=i;
			break;
			}
		}
		curplay.setSelectingOrder(tmp);
		System.out.println(p.getColor());
		success = curplay.DominoSelectable(int1);
		System.out.println(curplay.DominoSelectable(int1));
		domino = int1;
	}

	

	@When("the {string} player completes his\\/her domino selection of the game")
	public void the_player_completes_his_her_domino_selection_of_the_game(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if(success) {
			
			curplay.setSelectingOrder(curplay.getSelectingOrder()+1);
			
		}
	}

	@Then("a new draft shall be available, face down")
	public void a_new_draft_shall_be_available_face_down() throws Exception {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNextDraft(null);
		KingDominoController.createNextDraft(game);
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
	
	private static  Player getPlayerFromColor(String string) {
		Player temp = null;
		
		if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0);	
			
		}else if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1);
			
		}else if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2);
			
		}else if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3);
			
		}
		return temp;
	}

}
