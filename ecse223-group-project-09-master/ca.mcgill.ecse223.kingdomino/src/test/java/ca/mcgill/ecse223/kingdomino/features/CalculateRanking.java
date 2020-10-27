
package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculateRanking {

	/** The game is initialized for calculate ranking
	 * @author Chen
	 */
	@Given("the game is initialized for calculate ranking")
	public void the_game_is_initialized_for_calculate_ranking() {
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


	/** Add additional dominos to the kingdom
	 * @author Chen
	 * 
	*/
	@Given("the players have the following two dominoes in their respective kingdoms:")
	public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms(io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
	
		int i=0;
		for (Map<String, String> map : valueMaps) {
			Player thisplayer =game.getPlayer(i);
			i++;
			Kingdom kingdom = thisplayer.getKingdom();
			
			PlayerColor color = util.playerColorFromString(map.get("player")); // was getColor
			thisplayer.setColor(color);
			
			Integer id1 = Integer.decode(map.get("domino1"));
			DirectionKind dir1 = util.directionKindFromString(map.get("dominodir1"));
			Integer posx1 = Integer.decode(map.get("posx1"));
			Integer posy1 = Integer.decode(map.get("posy1"));
			
			Integer id2 = Integer.decode(map.get("domino2"));
			DirectionKind dir2 = util.directionKindFromString(map.get("dominodir2"));
			Integer posx2 = Integer.decode(map.get("posx2"));
			Integer posy2 = Integer.decode(map.get("posy2"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace1 = getdominoByID(id1);
			DominoInKingdom domInKingdom = new DominoInKingdom(posx1, posy1, kingdom, dominoToPlace1);
			domInKingdom.setDirection(dir1);
			dominoToPlace1.setStatus(DominoStatus.PlacedInKingdom);
			
			Domino dominoToPlace2 = getdominoByID(id2);
			DominoInKingdom domInKingdom2 = new DominoInKingdom(posx2, posy2, kingdom, dominoToPlace2);
			domInKingdom.setDirection(dir2);
			dominoToPlace2.setStatus(DominoStatus.PlacedInKingdom);
			}
		
	}

	/** Determine if there is a tie situation. Also print the standing and score of each player
	 * @author Chen
	 * 
	*/
	@Given("the players have no tiebreak")
	public void the_players_have_no_tiebreak() throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		KingDominoController.calculateRanking(game);
	}

	/** Calculate ranking is initiated
	 * @author Chen
	*/
	@When("calculate ranking is initiated")
	public void calculate_ranking_is_initiated() throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		KingDominoController.calculateRanking(game);

	}
 
	/** Compare each playes's calcualted standing and expected rank
	 * @author Chen
	*/
	@Then("player standings shall be the followings:")
	public void player_standings_shall_be_the_followings(io.cucumber.datatable.DataTable dataTable) throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		
		for (Map<String, String> map : valueMaps) {
			Integer rank = Integer.decode(map.get("standing"));
			PlayerColor color =  util.playerColorFromString(map.get("player")); // was getColor
			for(int i=0;i<4;i++) {
				Player aPlayer =game.getPlayer(i);
				if (color.equals(aPlayer.getColor())) {
					assertEquals(rank,aPlayer.getCurrentRanking());
				}
			}
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

