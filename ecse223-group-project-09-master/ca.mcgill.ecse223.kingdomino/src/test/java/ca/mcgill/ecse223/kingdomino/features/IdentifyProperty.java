package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.*;
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
import ca.mcgill.ecse223.kingdomino.controller.TileFromDomino;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class IdentifyProperty {

	List<Property>properties = Collections.emptyList();

	
	/** Initialize the game
	 * @author Ellina
	 */
	@Given("the game is initialized for identify properties")
	public void the_game_is_initialized_for_identify_properties() {
		//Initialize game
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

	/** Build the kingdom
	 * @author Ellina
	 * @param dataTable
	 */
	@Given("the player's kingdom has the following dominos:")
	public void the_player_s_kingdom_has_the_following_dominos(io.cucumber.datatable.DataTable dataTable) throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir = util.directionKindFromChar(Character.toUpperCase((map.get("dominodir")).charAt(0)));
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
	
	
	/** This method identify the properties of the player
	 * @author Ellina
	 * @version 1
	 */
	@When("the properties of the player are identified")
	public void the_properties_of_the_player_are_identified() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		properties = TileFromDomino.convertTilePropertyToProerty(game.getPlayer(0).getKingdom());
	}

	
	
	/** Compare expected properties with the actual ones
	 * @author Ellina
	 * @version 2.0
	 * 
	 */
	@Then("the player shall have the following properties:")
	public void the_player_shall_have_the_following_properties(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			List<Domino> dominos = getDominosByIDs(map.get("dominoes"));
			TerrainType type = util.terrainTypeFromChar(Character.toUpperCase((map.get("type")).charAt(0)));
			for(int e = 0; e < properties.size(); e++) {
				if(properties.get(e).getPropertyType() == type && (properties.get(e).getIncludedDominos().contains(dominos.get(0)))) {
					assertTrue(dominos.containsAll(properties.get(e).getIncludedDominos()));
				}	
			}	
		}
	
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
