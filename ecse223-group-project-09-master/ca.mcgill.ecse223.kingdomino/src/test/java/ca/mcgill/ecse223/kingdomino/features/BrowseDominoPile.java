package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

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
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import gherkin.StringUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;

public class BrowseDominoPile {
	private List<Domino> orderedDominos = Collections.emptyList();
	Domino d;
	private List<Integer> listOfIds = Collections.emptyList();
	
	/** Initialize the game
	 * @author Ellina
	 */
	//Scenario: Browse all the dominoes
	@Given("the program is started and ready for browsing dominoes")
	public void the_program_is_started_and_ready_for_browsing_dominoes() {
		Kingdomino kingdomino = new Kingdomino(); 
		KingdominoApplication.setKingdomino(kingdomino);
	}
	
	/** Browse dominoes
	 * @author Ellina
	 */
	@When("I initiate the browsing of all dominoes")
	public void i_initiate_the_browsing_of_all_dominoes() {
	    orderedDominos = KingDominoController.browseDominoPile();
	}

	/** Verify that all dominos are listed in in order
	 * @author Ellina
	 */
	@Then("all the dominoes are listed in increasing order of identifiers")
	public void all_the_dominoes_are_listed_in_increasing_order_of_identifiers() {
	    List<Integer> idList = Collections.emptyList();
	    idList = new ArrayList<>(idList);
	    List<Integer> numbers = Collections.emptyList();
	    numbers = new ArrayList<>(numbers);
	    
	    Integer id;
	    for(int e = 0; e < 48; e++) {
	    	id = orderedDominos.get(e).getId();
	    	idList.add(id);
	    	numbers.add(e + 1);
	    }
	    
	    assertEquals(numbers, idList);

	}
	
	/** Get domino with specified id
	 * @author Ellina
	 */
	//Scenario Outline: Select and observe an individual domino
	@When("I provide a domino ID {int}")
	public void i_provide_a_domino_ID(Integer int1) {
		d = util.getDominoByIDNoGame(int1);
	}

	/**Assert that the left tile terrain is of the expected type
	 * @author Ellina
	 */
	@Then("the listed domino has {string} left terrain")
	public void the_listed_domino_has_left_terrain(String string) {
		//get first char of terrain type and pass to terrainTypeFromChar()
		assertEquals(util.terrainTypeFromChar(Character.toUpperCase(string.charAt(0))), d.getLeftTile());
	}

	/**Assert that the right tile terrain is of the expected type
	 * @author Ellina
	 */
	@Then("the listed domino has {string} right terrain")
	public void the_listed_domino_has_right_terrain(String string) {
		assertEquals(util.terrainTypeFromChar(Character.toUpperCase(string.charAt(0))), d.getRightTile());
	}
	
	/**Assert that the domino has the expected number of crowns
	 * @author Ellina
	 */
	@Then("the listed domino has {int} crowns")
	public void the_listed_domino_has_crowns(Integer int1) {
		assertEquals(int1, Integer.valueOf(d.getRightCrown()));
	}
	
	/** Browse dominos
	 * @author Ellina
	 */
	//Scenario Outline: Filter domino by terrain type
	@When("I initiate the browsing of all dominoes of {string} terrain type")
	public void i_initiate_the_browsing_of_all_dominoes_of_terrain_type(String string) {
		TerrainType t = util.terrainTypeFromChar(Character.toUpperCase(string.charAt(0)));
		listOfIds = KingDominoController.browseDominoPileByTerrainType(t);
	}

	/**Assert expected domino is shown
	 * @author Ellina
	 */
	@Then("list of dominoes with IDs {string} should be shown")
	public void list_of_dominoes_with_IDs_should_be_shown(String string) {
		List<String> listOfIdsString = new ArrayList<String>(listOfIds.size());
		listOfIdsString = new ArrayList<>(listOfIdsString);
				for (Integer id : listOfIds) { 
				  listOfIdsString.add(String.valueOf(id)); 
				}
		assertEquals(string, StringUtils.join(",", listOfIdsString));
	}
}