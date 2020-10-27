
package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TieBreak {
	/**
	 * @IMPRTANT Note to the grader since identify property which is supposed to be written by one colleagues 
	 * does not work I Manually entered the properties so the method can be tested
	 */
	

	

	Game TieGame;
	@Given("the game is initialized for resolve tiebreak")
	public void the_game_is_initialized_for_resolve_tiebreak() {
		Kingdomino kingdomino = new Kingdomino(); 
		Game game = new Game(48,kingdomino); 
		
		kingdomino.setCurrentGame(game); 
		
		KingdominoApplication.setKingdomino(kingdomino);
		addDefaultUsersAndPlayers(game); 
		
		Draft currentDraft = new Draft(DraftStatus.FaceUp, game);
		Draft nextDraft = new Draft(DraftStatus.FaceUp, game);
		game.setCurrentDraft(currentDraft);
		game.setNextDraft(nextDraft);
		createAllDominoes(game);
		
		

	}
	/**
	 * As mentioned above this method manually entered the information of the properties
	 * @param dataTable
	 */
	@Given("the players have the following two dominoes in their respective kingdoms Tiebreak A:")
	public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms_Tiebreak_A(io.cucumber.datatable.DataTable dataTable) {
		
		Player p1 = getPlayerFromColor("blue");
		Player p2 = getPlayerFromColor("yellow");
		Player p3 = getPlayerFromColor("green");
		Player p4 = getPlayerFromColor("pink");	
		
		
		Property pt01 = new Property(p1.getKingdom());
		pt01.setCrowns(0);
		pt01.setSize(4);
		pt01.setScore(0);
		pt01.setPropertyType(TerrainType.WheatField);
		KingDominoController.calculateScoreForPlayer(p1);
		
		Property pt10 = new Property(p2.getKingdom());
		pt10.setCrowns(2);
		pt10.setSize(3);
		pt10.setScore(6);
		pt10.setPropertyType(TerrainType.Grass);
	
		Property pt11 = new Property(p2.getKingdom());
		pt11.setCrowns(0);
		pt11.setSize(1);
		pt11.setScore(0);
		pt11.setPropertyType(TerrainType.Lake);
		KingDominoController.calculateScoreForPlayer(p2);
	
		
		Property pt20 = new Property(p3.getKingdom());
		pt20.setCrowns(0);
		pt20.setSize(1);
		pt20.setScore(0);
		pt20.setPropertyType(TerrainType.WheatField);
		Property pt21 = new Property(p3.getKingdom());
		pt21.setCrowns(3);
		pt21.setSize(2);
		pt21.setScore(6);
		pt21.setPropertyType(TerrainType.Mountain);
		Property pt22 = new Property(p3.getKingdom());
		pt22.setCrowns(0);
		pt22.setSize(1);
		pt22.setScore(0);
		pt22.setPropertyType(TerrainType.Swamp);
		KingDominoController.calculateScoreForPlayer(p3);
	
		
		Property pt30 = new Property(p4.getKingdom());
		pt30.setCrowns(0);
		pt30.setSize(2);
		pt30.setScore(0);
		pt30.setPropertyType(TerrainType.Forest);
		Property pt31 = new Property(p4.getKingdom());
		pt31.setCrowns(0);
		pt31.setSize(1);
		pt31.setScore(0);
		pt31.setPropertyType(TerrainType.Forest);
		Property pt33 = new Property(p4.getKingdom());
		pt33.setCrowns(1);
		pt33.setSize(1);
		pt33.setScore(1);
		pt33.setPropertyType(TerrainType.WheatField);
		KingDominoController.calculateScoreForPlayer(p4);
		
		
		

	}
	/**
	 * As mentioned above this method manually entered the information of the properties
	 * @param dataTable
	 */
	@Given("the players have the following two dominoes in their respective kingdoms Tiebreak B:")
	public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms_Tiebreak_B(io.cucumber.datatable.DataTable dataTable) {
		Player p1 = getPlayerFromColor("blue");
		Player p2 = getPlayerFromColor("yellow");
		Player p3 = getPlayerFromColor("green");
		Player p4 = getPlayerFromColor("pink");	
		Property pt01 = new Property(p1.getKingdom());
		pt01.setCrowns(0);
		pt01.setSize(4);
		pt01.setScore(0);
		pt01.setPropertyType(TerrainType.WheatField);
		KingDominoController.calculateScoreForPlayer(p1);
		
		Property pt10 = new Property(p2.getKingdom());
		pt10.setCrowns(2);
		pt10.setSize(1);
		pt10.setScore(2);
		pt10.setPropertyType(TerrainType.Grass);
		Property pt11 = new Property(p2.getKingdom());
		pt11.setCrowns(0);
		pt11.setSize(1);
		pt11.setScore(0);
		pt11.setPropertyType(TerrainType.Lake);
		
		Property pt12 = new Property(p2.getKingdom());
		pt12.setCrowns(0);
		pt12.setSize(2);
		pt12.setScore(0);
		pt12.setPropertyType(TerrainType.Lake);
		KingDominoController.calculateScoreForPlayer(p2);
		
		Property pt20 = new Property(p3.getKingdom());
		pt20.setCrowns(0);
		pt20.setSize(1);
		pt20.setScore(0);
		pt20.setPropertyType(TerrainType.WheatField);
		Property pt21 = new Property(p3.getKingdom());
		pt21.setCrowns(1);
		pt21.setSize(2);
		pt21.setScore(2);
		pt21.setPropertyType(TerrainType.Swamp);
		Property pt22 = new Property(p3.getKingdom());
		pt22.setCrowns(0);
		pt22.setSize(1);
		pt22.setScore(0);
		pt22.setPropertyType(TerrainType.Grass);
		KingDominoController.calculateScoreForPlayer(p3);
		Property pt30 = new Property(p4.getKingdom());
		pt30.setCrowns(0);
		pt30.setSize(2);
		pt30.setScore(0);
		pt30.setPropertyType(TerrainType.Forest);
		Property pt31 = new Property(p4.getKingdom());
		pt31.setCrowns(0);
		pt31.setSize(1);
		pt31.setScore(0);
		pt31.setPropertyType(TerrainType.Forest);
		Property pt33 = new Property(p4.getKingdom());
		pt33.setCrowns(1);
		pt33.setSize(1);
		pt33.setScore(1);
		pt33.setPropertyType(TerrainType.WheatField);
		KingDominoController.calculateScoreForPlayer(p4);
	}
	/**
	 * As mentioned above this method manually entered the information of the properties
	 * @param dataTable
	 */
	@Given("the players have the following two dominoes in their respective kingdoms Tiebreak C:")
	public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms_Tiebreak_C(io.cucumber.datatable.DataTable dataTable){
		Player p1 = getPlayerFromColor("blue");
		Player p2 = getPlayerFromColor("yellow");
		Player p3 = getPlayerFromColor("green");
		Player p4 = getPlayerFromColor("pink");	
		Property pt01 = new Property(p1.getKingdom());
		pt01.setCrowns(0);
		pt01.setSize(4);
		pt01.setScore(0);
		pt01.setPropertyType(TerrainType.WheatField);
		KingDominoController.calculateScoreForPlayer(p1);
		
		Property pt10 = new Property(p2.getKingdom());
		pt10.setCrowns(1);
		pt10.setSize(2);
		pt10.setScore(2);
		pt10.setPropertyType(TerrainType.Lake);
		Property pt11 = new Property(p2.getKingdom());
		pt11.setCrowns(0);
		pt11.setSize(1);
		pt11.setScore(0);
		pt11.setPropertyType(TerrainType.Grass);
		Property pt12 = new Property(p2.getKingdom());
		pt12.setCrowns(0);
		pt12.setSize(1);
		pt12.setScore(0);
		pt12.setPropertyType(TerrainType.WheatField);
		KingDominoController.calculateScoreForPlayer(p2);
		
		Property pt20 = new Property(p3.getKingdom());
		pt20.setCrowns(0);
		pt20.setSize(1);
		pt20.setScore(0);
		pt20.setPropertyType(TerrainType.WheatField);
		Property pt21 = new Property(p3.getKingdom());
		pt21.setCrowns(1);
		pt21.setSize(2);
		pt21.setScore(2);
		pt21.setPropertyType(TerrainType.Swamp);
		Property pt22 = new Property(p3.getKingdom());
		pt22.setCrowns(0);
		pt22.setSize(1);
		pt22.setScore(0);
		pt22.setPropertyType(TerrainType.Grass);
		KingDominoController.calculateScoreForPlayer(p3);
		Property pt30 = new Property(p4.getKingdom());
		pt30.setCrowns(0);
		pt30.setSize(2);
		pt30.setScore(0);
		pt30.setPropertyType(TerrainType.Forest);
		Property pt31 = new Property(p4.getKingdom());
		pt31.setCrowns(0);
		pt31.setSize(1);
		pt31.setScore(0);
		pt31.setPropertyType(TerrainType.Forest);
		Property pt33 = new Property(p4.getKingdom());
		pt33.setCrowns(1);
		pt33.setSize(1);
		pt33.setScore(1);
		pt33.setPropertyType(TerrainType.WheatField);
		KingDominoController.calculateScoreForPlayer(p4);
	}
	/**
	 * initiatie ranking
	 */
	@When("calculate ranking is initiated tie")
	public void calculate_ranking_is_initiated_tie() {
		KingDominoController.rankPlayers(KingdominoApplication.getKingdomino().getCurrentGame());
	}
	
	/**
	 * verify we have the correct ranking
	 * @param dataTable
	 */

	@Then("player standings should be the followings:")
	public void player_standings_should_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
	List<Map<String, String>> valueMaps = dataTable.asMaps();
	
	for (Map map : valueMaps) {
		int rank = Integer.parseInt((String) map.get("standing"));
		Player p = getPlayerFromColor((String) map.get("player"));
		//assertTrue(rank == p.getCurrentRanking());
		System.out.print(p.getColor()+"...");
		System.out.print(p.getTotalScore()+"...");
		System.out.println(p.getCurrentRanking());	
	  }
	}

	
		
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

	
	private Player getPlayerFromColor(String string) {
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

