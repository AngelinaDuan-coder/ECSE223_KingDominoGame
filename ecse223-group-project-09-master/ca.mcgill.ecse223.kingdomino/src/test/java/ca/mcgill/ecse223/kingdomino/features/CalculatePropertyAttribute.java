
package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.TileFromDomino;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


/** This class test the methods related to calculate property attribute
 * @author Chen
 * @version 3.0
 */
public class CalculatePropertyAttribute {
	
	/** The game is initialized for calculate property attributes
	 * @author Chen
	 */
	@Given("the game is initialized for calculate property attributes")
	public void the_game_is_initialized_for_calculate_property_attributes() {
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
	
	/** Calculate property attributes is initiated
	 * @author Chen
	 */
	@When("calculate property attributes is initiated")
	public void calculate_property_attributes_is_initiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer= game.getPlayer(0);
		TileFromDomino.convertTilePropertyToProerty(aPlayer.getKingdom());
	}

	/** Compare the number of properties with the expected number
	 * @author Chen
	 */
	@Then("the player shall have a total of {int} properties")
	public void the_player_shall_have_a_total_of_properties(Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player aPlayer= game.getPlayer(0);
		List<Property> p = aPlayer.getKingdom().getProperties();
		assertEquals(int1,p.size());
	}
	
	/** Add dominos into player's kingdom
	 * @author Chen
	 */
	@Then("the player shall have properties with the following attributes:")
	public void the_player_shall_have_properties_with_the_following_attributes(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		Player aPlayer= game.getPlayer(0);
		List<Property> p = aPlayer.getKingdom().getProperties();
		List<Property> realPro = new ArrayList<Property>();
		List<Property> calculatePro = p;
		for (Map<String, String> map : valueMaps) {
			//type size grown
			Integer crowns = Integer.decode(map.get("crowns"));
			Integer size = Integer.decode(map.get("size"));
			TerrainType type;
			type = util.terrainTypeFromString(map.get("type"));
			
			Property realProperty = new Property(aPlayer.getKingdom());
			realProperty.setCrowns(crowns);
			realProperty.setPropertyType(type);
			realProperty.setSize(size);
			realPro.add(realProperty);
		}
		
		//count the number of property with samea attributes
		int sizeOfSame=0;
		for(Property e: calculatePro) {
			for(Property pp: realPro) {
				boolean sameType= e.getPropertyType()==pp.getPropertyType();
				boolean sameSize= e.getSize()==pp.getSize();
				boolean sameCrown= e.getCrowns()==pp.getCrowns();
				if(sameType && sameSize && sameCrown) {
					sizeOfSame++;
				}
			}	
		}
		int sizeOfSame2=0;
		for(Property e: realPro) {
			for(Property pp: calculatePro) {
				boolean sameType= e.getPropertyType()==pp.getPropertyType();
				boolean sameSize= e.getSize()==pp.getSize();
				boolean sameCrown= e.getCrowns()==pp.getCrowns();
				if(sameType && sameSize && sameCrown) {
					sizeOfSame2++;
				}
			}	
		}
		assertEquals(sizeOfSame2++,sizeOfSame++);
	}
	
	
	
	
	
	
	
	
	/////////////help method//////////
	
	
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

