
package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
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
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MoveRotatePlaceDomino {

	Game g;
	Player p;
	DominoInKingdom dIKToMoveRotatePlace;

	/**
	 * initiate game
	 */
	@Given("the game is initialized for move current domino")
	public void the_game_is_initialized_for_move_current_domino() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		addDefaultUsersAndPlayers(game);
		Draft currentDraft = new Draft(DraftStatus.FaceUp, game);
		Draft nextDraft = new Draft(DraftStatus.FaceUp, game);
		game.setCurrentDraft(currentDraft);
		game.setNextDraft(nextDraft);
		createAllDominoes(game);
		game.getPlayer(0).setColor(PlayerColor.Blue);
		game.getPlayer(1).setColor(PlayerColor.Green);
		game.getPlayer(2).setColor(PlayerColor.Yellow);
		game.getPlayer(3).setColor(PlayerColor.Pink);

		KingdominoApplication.setKingdomino(kingdomino);
	}

	/**
	 * initiate game
	 */
	@Given("the game is initialized for rotate current domino")
	public void the_game_is_initialized_for_rotate_current_domino() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);

		addDefaultUsersAndPlayers(game);

		game.setCurrentDraft(new Draft(DraftStatus.FaceUp, game));
		game.setNextDraft(new Draft(DraftStatus.FaceUp, game));

		createAllDominoes(game);

		game.getPlayer(0).setColor(PlayerColor.Blue);
		game.getPlayer(1).setColor(PlayerColor.Green);
		game.getPlayer(2).setColor(PlayerColor.Yellow);
		game.getPlayer(3).setColor(PlayerColor.Pink);

		KingdominoApplication.setKingdomino(kingdomino);
	}

	/**
	 * find the player who has to play
	 * 
	 * @param string
	 */
	@Given("it is {string}'s turn")
	public void it_is_s_turn(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = getPlayerFromColor(string);
		game.setNextPlayer(player);
	}

	/**
	 * find the player who has to play
	 * 
	 * @param string
	 */
	@Given("It is {string}'s turn")
	public void It_is_s_turn(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = getPlayerFromColor(string);
		game.setNextPlayer(player);
	}

	/**
	 * select domino
	 * 
	 * @param string
	 * @param int1
	 */
	@Given("{string} has selected domino {int}")
	public void has_selected_domino(String string, Integer int1) {
		Player player = getPlayerFromColor(string);

		Domino domino = getdominoByID(int1);

		player.setDominoSelection(new DominoSelection(player, domino,
				KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft()));
	}

	/**
	 * 
	 * @param string
	 * @param int1
	 */
	@When("{string} removes his king from the domino {int}")
	public void removes_his_king_from_the_domino(String string, Integer int1) {
		Player temp = getPlayerFromColor(string);
		Domino d = getdominoByID(int1);
		assertTrue(temp.getDominoSelection().getDomino().getId() == int1);
		temp.setDominoSelection(null);

	}

	/**
	 * 
	 * @param int1
	 * @param int2
	 * @param int3
	 * @param string
	 */
	@Then("domino {int} should be tentative placed at position {int}:{int} of {string}'s kingdom with ErroneouslyPreplaced status")
	public void domino_should_be_tentative_placed_at_position_of_s_kingdom_with_ErroneouslyPreplaced_status(
			Integer int1, Integer int2, Integer int3, String string) {

		Player temp = getPlayerFromColor(string);
		Kingdom k = temp.getKingdom();
		Domino d = getdominoByID(int1);
		DominoInKingdom dk = new DominoInKingdom(int2, int3, k, d);
		KingDominoController.verifyDominoPreplacement(dk);
		assertTrue(dk.getDomino().getStatus() == DominoStatus.ErroneouslyPreplaced);
	}

	/**
	 * 
	 * @param string
	 * @param dataTable
	 */
	@Given("{string}'s kingdom has following dominoes:")
	public void s_kingdom_has_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = getPlayerFromColor(string);
		Kingdom kingdom = player.getKingdom();

		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("id"));
			DirectionKind dir;

			dir = getDirection(map.get("dir"));

			int posx = Integer.parseInt(map.get("posx"));
			int posy = Integer.parseInt(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace;

			dominoToPlace = getdominoByID(id);

			DominoInKingdom dominoInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
			dominoInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}

		p = player;
		g = game;

	}

	/**
	 * 
	 * @param string
	 * @param dataTable
	 */
	@Given("the {string}'s kingdom has the following dominoes:")
	public void the_s_kingdom_has_the_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player temp = getPlayerFromColor(string);
		Kingdom k = temp.getKingdom();

		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			Integer id = Integer.decode(map.get("domino"));
			DirectionKind dir;

			dir = getDirection(map.get("dominodir"));

			int posx = Integer.parseInt(map.get("posx"));
			int posy = Integer.parseInt(map.get("posy"));

			// Add the domino to a player's kingdom
			Domino dominoToPlace;

			dominoToPlace = getdominoByID(id);

			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, k, dominoToPlace);
			domInKingdom.setDirection(dir);
			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	/**
	 * 
	 * @param int1
	 * @param int2
	 * @param int3
	 * @param string
	 */
	@Given("domino {int} is tentatively placed at position {int}:{int} with direction {string}")
	public void domino_is_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3,
			String string) {

		Kingdom kingdom = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getKingdom();
		Domino domino = getdominoByID(int1);
		DominoInKingdom dominoInKingdom = new DominoInKingdom(int2, int3, kingdom, domino);
		dominoInKingdom.setDirection(getDirection(string));
		dIKToMoveRotatePlace = dominoInKingdom;
	}

	/**
	 * 
	 * @param int1
	 * @param string
	 */
	@Given("domino {int} is in {string} status")
	public void domino_is_in_status(Integer int1, String string) {
		Domino domino = getdominoByID(int1);
		domino.setStatus(util.dominoStatusFromString(string));
	}

	/**
	 * 
	 * @param int1
	 * @param string
	 */
	@Given("domino {int} has status {string}")
	public void domino_has_status(Integer int1, String string) {
		Domino d = getdominoByID(int1);
		if (string.equals("ErroneouslyPreplaced")) {
			dIKToMoveRotatePlace.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		} else if (string.equals("CorrectlyPreplaced")) {
			dIKToMoveRotatePlace.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
		}

	}

	/**
	 * 
	 * @param string
	 * @param string2
	 */
	@When("{string} requests to move the domino {string}")
	public void requests_to_move_the_domino(String string, String string2) {
		try {
			dIKToMoveRotatePlace = KingDominoController.moveDominoInKingdom(dIKToMoveRotatePlace,
					getDirection(string2));
		} catch (InvalidInputException e) {
			// normal, it should fail
		}
	}

	/**
	 * 
	 * @param string
	 * @param string2
	 */
	@When("{string} requests to rotate the domino with {string}")
	public void requests_to_rotate_the_domino_with(String string, String string2) {
		try {
			KingDominoController.rotateDominoInKingdom(dIKToMoveRotatePlace, string2);
		} catch (InvalidInputException e) {
			// normal, it should fail
		}
	}

	/**
	 * 
	 * @param string
	 * @param int1
	 */
	@When("{string} requests to place the selected domino {int}")
	public void requests_to_place_the_selected_domino(String string, Integer int1) {
		try {
			KingDominoController.placeDominoInKingdom(dIKToMoveRotatePlace);
		} catch (InvalidInputException e) {
			// normal, it should fail
		}
	}

	/**
	 * 
	 * @param int1
	 * @param int2
	 * @param int3
	 * @param string
	 */
	@Then("the domino {int} should be tentatively placed at position {int}:{int} with direction {string}")
	public void the_domino_should_be_tentatively_placed_at_position_D_with_direction(Integer int1, Integer int2,
			Integer int3, String string) {
		assertEquals(dIKToMoveRotatePlace.getX(), int2);
		assertEquals(dIKToMoveRotatePlace.getY(), int3);
		assertEquals(dIKToMoveRotatePlace.getDomino().getId(), int1);
		assertTrue(dIKToMoveRotatePlace.getDirection() == getDirection(string));
	}

	/**
	 * 
	 * @param string
	 */
	@Then("the new status of the domino is {string}")
	public void the_new_status_of_the_domino_is(String string) {
		assertTrue(dIKToMoveRotatePlace.getDomino().getStatus().toString().equals(string));

	}

	/**
	 * 
	 * @param int1
	 * @param string
	 */
	@Then("the domino {int} should have status {string}")
	public void the_domino_should_have_status(Integer int1, String string) {
		assertEquals(util.dominoStatusFromString(string), util.getDominoByID(int1).getStatus());
	}

	/**
	 * 
	 * @param int1
	 * @param int2
	 * @param int3
	 */
	@Then("the domino {int} is still tentatively placed at position {int}:{int}")
	public void the_domino_is_still_tentatively_placed_at_position(Integer int1, Integer int2, Integer int3) {
		assertEquals(dIKToMoveRotatePlace.getX(), int2);
		assertEquals(dIKToMoveRotatePlace.getY(), int3);
		assertEquals(dIKToMoveRotatePlace.getDomino().getId(), int1);
	}

	/**
	 * 
	 * @param int1
	 * @param int2
	 * @param int3
	 * @param string
	 */
	@Then("the domino {int} is still tentatively placed at {int}:{int} but with new direction {string}")
	public void the_domino_is_still_tentatively_placed_at_but_with_new_direction(Integer int1, Integer int2,
			Integer int3, String string) {
		for (KingdomTerritory kingdomTerritory : KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer()
				.getKingdom().getTerritories()) {
			if (kingdomTerritory instanceof DominoInKingdom) {
				DominoInKingdom dominoInKingdom = (DominoInKingdom) kingdomTerritory;
				if (dominoInKingdom.getDomino().getId() == int1) {
					assertEquals(dominoInKingdom.getX(), int2);
					assertEquals(dominoInKingdom.getY(), int3);
					assertEquals(util.directionKindFromString(string), dominoInKingdom.getDirection());
					break;
				}
			}
		}
	}

	/**
	 * 
	 * @param int1
	 * @param int2
	 * @param int3
	 * @param string
	 */
	@Then("domino {int} is tentatively placed at the same position {int}:{int} with the same direction {string}")
	public void domino_is_tentatively_placed_at_the_same_position_with_the_same_direction(Integer int1, Integer int2,
			Integer int3, String string) {
		for (KingdomTerritory kingdomTerritory : KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer()
				.getKingdom().getTerritories()) {
			if (kingdomTerritory instanceof DominoInKingdom) {
				DominoInKingdom dominoInKingdom = (DominoInKingdom) kingdomTerritory;
				Domino domino = dominoInKingdom.getDomino();
				if (domino.getId() == int1) {
					assertEquals(dominoInKingdom.getX(), int2);
					assertEquals(dominoInKingdom.getY(), int3);
					assertEquals(util.directionKindFromString(string), dominoInKingdom.getDirection());
					break;
				}
			}
		}
	}

	/**
	 * 
	 * @param string
	 * @param int1
	 * @param int2
	 * @param int3
	 * @param string2
	 */
	@Then("{string}'s kingdom should now have domino {int} at position {int}:{int} with direction {string}")
	public void s_kingdom_should_now_have_domino_at_position_with_direction(String string, Integer int1, Integer int2,
			Integer int3, String string2) {
		for (KingdomTerritory kingdomTerritory : getPlayerFromColor(string).getKingdom().getTerritories()) {
			if (kingdomTerritory instanceof DominoInKingdom) {
				DominoInKingdom dominoInKingdom = (DominoInKingdom) kingdomTerritory;
				if (dominoInKingdom.getDomino().getId() == int1) {
					assertEquals(int2, dominoInKingdom.getX());
					assertEquals(int3, dominoInKingdom.getY());
					assertEquals(util.directionKindFromString(string2), dominoInKingdom.getDirection());
				}
			}
		}
	}

	/**
	 * 
	 * @param int1
	 * @param string
	 */
	@Then("domino {int} should still have status {string}")
	public void domino_should_still_have_status(Integer int1, String string) {
		assertEquals(util.getDominoByID(int1).getStatus(), util.dominoStatusFromString(string));
	}

	/**
	 * 
	 * @param string
	 */
	@Then("the domino should still have status {string}")
	public void the_domino_should_still_have_status(String string) {
		assertTrue(dIKToMoveRotatePlace.getDomino().getStatus().toString().equals(string));
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

	private void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			// User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			// player.setUser(user);
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

	private DirectionKind getDirection(String dir) {
		switch (dir) {
		case "up":
			return DirectionKind.Up;
		case "down":
			return DirectionKind.Down;
		case "left":
			return DirectionKind.Left;
		case "right":
			return DirectionKind.Right;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}

	private Player getPlayerFromColor(String string) {
		Player temp = null;
		if (string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getColor().toString()
				.toLowerCase())) {

			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0);

		} else if (string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getColor()
				.toString().toLowerCase())) {

			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1);

		} else if (string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getColor()
				.toString().toLowerCase())) {

			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2);

		} else if (string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getColor()
				.toString().toLowerCase())) {

			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3);

		}
		return temp;
	}

}

