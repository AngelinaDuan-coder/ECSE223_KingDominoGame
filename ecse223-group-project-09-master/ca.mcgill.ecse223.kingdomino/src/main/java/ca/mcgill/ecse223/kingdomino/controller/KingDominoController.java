package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.security.auth.login.FailedLoginException;

//import com.google.common.collect.Ordering;
//import com.google.common.eventbus.AllowConcurrentEvents;


public class KingDominoController {
	/**
	 * 
	 * @author Angelina
	 * 
	 * Method to shuffle domino pile
	 * 
	 * @param  game
	 * 
	 * @return Game
	 * 
	 * @version 3.0
	 * 
	 */
	public static Game shuffleDominos(Game game) {
		List<Domino>shuffledList= KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos();
		Random r=new Random();
		for (int i = 0; i < shuffledList.size()-1; i++) {
			KingdominoApplication.getKingdomino().getCurrentGame()
			.addOrMoveAllDominoAt(shuffledList.get(i),r.nextInt(shuffledList.size()));	
		}
		for (int i = 0; i < shuffledList.size(); i++) {
			Domino domino = shuffledList.get(i);
			if(i!=0) {
				domino.setPrevDomino(shuffledList.get(i-1));
			}else {
				domino.setPrevDomino(null);
			}
			if(i!=shuffledList.size()-1) {
				domino.setNextDomino(shuffledList.get(i+1));
			}else {
				domino.setNextDomino(null);
			}
			
		}
		return game;

	}
	
	/**
	 * @author louca
	 * @param game
	 * @return
	 */
	public static Game shuffleDominos2(Game game) {
		List<Domino> shuffled = new ArrayList<Domino>(KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos());
		Collections.shuffle(shuffled);
		
		for (int i=0; i<shuffled.size(); i++) {
			Domino domino = shuffled.get(i);
			if (i != 0) { // if not the first domino
				domino.setPrevDomino(shuffled.get(i -1)); // set the previous domino to the previous shuffled domino
			} else {
				domino.setPrevDomino(null); // there is no previous domino
				game.setTopDominoInPile(domino); // this is the top domino in pile
			}
			
			if (i != shuffled.size() - 1) { // if not the last domino
				domino.setNextDomino(shuffled.get(i + 1)); // set the next domino to the next shuffled domino
			} else {
				domino.setNextDomino(null); // there is no next domino
			}
			
			domino.setStatus(DominoStatus.InPile);
		}
		
		// shuffled local variable will be garbage collected, but shuffled order lives through the Liked List of next and previous references
		return game;
	}

	/**
	 * 
	 * @author Angelina
	 * 
	 * Method to fix the arrangement of the shuffled pile
	 * 
	 * @param  order
	 * 
	 * @return void
	 * 
	 * @version 1.0
	 * 
	 */
	public static void fixedArrangement(int[] order) {
		util.getDominoByID(order[0]).setPrevDomino(null);
		for (int i = 0; i < order.length-1; i++) {
			util.getDominoByID(order[i]).setNextDomino(util.getDominoByID(order[i+1]));
		}
	}


	/**
	 * 
	 * @author Angelina
	 * 
	 * Method to set and unset Bonus Options for the game
	 * 
	 * @param game
	 * 
	 * @param optionName(must be Harmony,Middle Kingdom or no)
	 * 
	 * @return Game
	 * 
	 * @version 3.0
	 * 
	 * @throws InvalidInputException
	 * 
	 */
	public static Game setBonusOptionForGame(Game game, String optionName) throws InvalidInputException{		
		if (optionName.equals("Harmony")) {
			return setHarmonyBonusOption(game);
		} else if (optionName.equals("Middle Kingdom")) {
			return setMiddleKingdomBonusOption(game);

		} else if (optionName.equals("no")){
			return game;
		}else {
			throw new InvalidInputException(optionName+"///option name must be Harmony or Middle Kingdom or no");

		}		
	}

	public static Game setHarmonyBonusOption(Game game) throws InvalidInputException {
		for (BonusOption bonusOption : game.getSelectedBonusOptions()) {
			if (bonusOption.getOptionName().equals("Harmony")) {
				//throw new InvalidInputException("Harmony bonus option was already set for game.");
				return game;
			} 
		}
		game.addSelectedBonusOption(new BonusOption("Harmony", game.getKingdomino()));
		return game;
	}

	public static Game unsetHarmonyBonusOption(Game game) throws InvalidInputException {
		for (BonusOption bonusOption : game.getSelectedBonusOptions()) {
			if (bonusOption.getOptionName().equals("Harmony")) {
				game.removeSelectedBonusOption(bonusOption);
				return game;
			}
		}
		return game;
		//throw new InvalidInputException("Harmony bonus option was not set for game.");
	}

	public static Game setMiddleKingdomBonusOption(Game game) throws InvalidInputException {
		for (BonusOption bonusOption : game.getSelectedBonusOptions()) {
			if (bonusOption.getOptionName().equals("Middle Kingdom")) {
				throw new InvalidInputException("Middle Kingdom bonus option was already set for game.");
			} 
		}
		game.addSelectedBonusOption(new BonusOption("Middle Kingdom", game.getKingdomino()));
		return game;
	}

	public static Game unsetMiddleKingdomBonusOption(Game game) throws InvalidInputException {
		for (BonusOption bonusOption : game.getSelectedBonusOptions()) {
			if (bonusOption.getOptionName().equals("Middle Kingdom")) {
				game.removeSelectedBonusOption(bonusOption);
				return game;
			}
		}
		return game;
		//throw new InvalidInputException("Harmony bonus option was not set for game.");
	}

	public static Game unsetBonusOptionForGame(Game game, String optionName) throws InvalidInputException{
		if (optionName.equals("Harmony")) {
			unsetHarmonyBonusOption(game);
		} else if (optionName.equals("Middle Kingdom")) {
			unsetMiddleKingdomBonusOption(game);
		}else if (optionName.equals("no")) {
			return game;
		} else {
			throw new InvalidInputException("option name must be Harmony or Middle Kingdom or no.");
		}
		return game;
	}
	/**
	 * 
	 * @author Angelina
	 * 
	 * Method to set number of players for the game
	 * 
	 * @param game
	 * 
	 * @param numOfPlayers
	 * 
	 * @return Game
	 * 
	 * @version 1.0
	 * 
	 * @throws InvalidInputException
	 * 
	 */
	public static Game setNumberOfPlayersForGame(Game game, int numberOfPlayers) throws InvalidInputException {
		if (numberOfPlayers >=2 && numberOfPlayers <=4) {
			game.setNumberOfPlayers(numberOfPlayers);
		}
		throw new InvalidInputException("number of players must be between 2 and 4 (inclusive).");
	}

	/**
	 * @author Angelina
	 * 
	 * Method to choose next domino
	 * 
	 * @param chosendominoID
	 *
	 * @param player
	 * 
	 * @return Draft
	 * 
	 * @throws InvalidInputException 
	 *
	 */
	public static Draft chooseNextDomino(Player player, int chosenDominoID) throws InvalidInputException{
		Draft nextDraft = player.getGame().getNextDraft();
		for (Domino domino : nextDraft.getIdSortedDominos()) {
			if (domino.getId() == chosenDominoID) {
				Domino chosenDomino = domino;

				for (DominoSelection dominoSelection : nextDraft.getSelections()) {
					if (dominoSelection.getDomino() == chosenDomino) {
						throw new InvalidInputException("chosen domino is already occupied.");
					}
				}

				nextDraft.addSelection(player, chosenDomino);
				return nextDraft;
			}
		}
		throw new InvalidInputException("chosen domino was not in next draft.");
	}


	/**
	 * @author Louca
	 * 
	 * Method to rotate the passed domino in the kingdom
	 * 
	 * @version 2.0
	 * 
	 * @param dominoInKingdom to be rotated
	 * @param rotation the string representing how the domino in kingdom should be rotated, whose value should be either "clockwise" or "counterclockwise" (regardless of case)
	 * 
	 * @return the given dominoInKingdom, with its direction changed according to the given rotation
	 * 
	 * @throws InvalidInputException if the rotation param is not either "clockwise" or "counterclockwise" (regardless of case), or if rotating the given dominoInKingdom would make it no longer be in the grid 
	 */
	public static DominoInKingdom rotateDominoInKingdom(DominoInKingdom dominoInKingdom, String rotation) throws InvalidInputException {
		List<DirectionKind> cardinalities = new ArrayList<DirectionKind>();
		cardinalities.add(DirectionKind.Up); cardinalities.add(DirectionKind.Right); cardinalities.add(DirectionKind.Down); cardinalities.add(DirectionKind.Left);

		int cardinalityIndex = cardinalities.indexOf(dominoInKingdom.getDirection());

		DirectionKind initialDirection = dominoInKingdom.getDirection();

		if (rotation.toLowerCase().equals("counterclockwise")) { // ccw
			if (cardinalityIndex == 0) { // modulo 3 down counter
				cardinalityIndex = 3;
			} else {
				cardinalityIndex -= 1;
			}
		} else if (rotation.toLowerCase().equals("clockwise")) { // cw
			if (cardinalityIndex == 3) { // modulo 3 up counter
				cardinalityIndex = 0;
			} else {
				cardinalityIndex += 1;
			}
		} else {
			throw new InvalidInputException("rotation string must be either clockwise or counterclockwise (regardless of case).");
		}

		dominoInKingdom.setDirection(cardinalities.get(cardinalityIndex));
		
		if (! verifyKingdomTerritoryInBoard(dominoInKingdom)) { // with the rotated direction
			dominoInKingdom.setDirection(initialDirection); // undo rotation
			throw new InvalidInputException("rotation would place one of the domino tiles outside the board.");
		}

		verifyDominoPreplacement(dominoInKingdom);
		return dominoInKingdom;
	}

	/**
	 * @author Elias
	 * 
	 * @version 3.0
	 *    
	 * @param d
	 * @param k
	 * @param userinputs
	 * @return
	 */
	public static DominoInKingdom moveDominoInKingdom(DominoInKingdom dominoInKingdom, DirectionKind moveDirection) throws InvalidInputException {
		int x = dominoInKingdom.getX();
		int y = dominoInKingdom.getY();
		int initX = x;
		int initY =y;
		DominoStatus s = dominoInKingdom.getDomino().getStatus();

		if (moveDirection == DirectionKind.Up) {
			y += 1;
		} else if (moveDirection == DirectionKind.Down) {
			y -= 1;
		} else if (moveDirection == DirectionKind.Left) {
			x -= 1;
		} else if (moveDirection == DirectionKind.Right) {
			x += 1;
		}
		
//		if(y>4) {
//			return dominoInKingdom;
//		}
//		
		dominoInKingdom.setX(x);
		dominoInKingdom.setY(y);
		if(!verifyKingdomTerritoryInBoard(dominoInKingdom)) {
			dominoInKingdom.setX(initX);
			dominoInKingdom.setY(initY);
			dominoInKingdom.getDomino().setStatus(s);
			throw new InvalidInputException("movement would place one of the domino tiles outside the board.");
		}
		verifyDominoPreplacement(dominoInKingdom);
		return dominoInKingdom;
	}

	/**
	 * @author Louca
	 * @param dominoInKingdom whose preplacement is to be verified
	 * @return boolean whether or not the domino's status set to either correctly preplaced depending on whether it was within the gird size, was adjacent to the castle or to at least one neighbor, and was not overlapping
	 */
	public static boolean verifyDominoPreplacement(DominoInKingdom dominoInKingdom) {
		Domino domino = dominoInKingdom.getDomino();
		if (verifyGridSize(dominoInKingdom.getKingdom())
				&& (verifyCastleAdjacency(dominoInKingdom) || (verifyNeighborAdjacency(dominoInKingdom).size() != 0))
				&& !verifyNoOverlapping(dominoInKingdom)) {
			domino.setStatus(DominoStatus.CorrectlyPreplaced);
			return true;
		} else {
			domino.setStatus(DominoStatus.ErroneouslyPreplaced);
			return false;
		}
	}

	/**
	 * @author Louca
	 * @param dominoInKingdom to place in its kingdom
	 * @return the given domino in kingdom with its domino status set to placed in kingdom
	 * @throws InvalidInputException if the given domino in kingdom was erroneously preplaced
	 */
	public static DominoInKingdom placeDominoInKingdom(DominoInKingdom dominoInKingdom) throws InvalidInputException {
		verifyDominoPreplacement(dominoInKingdom);
		Domino domino = dominoInKingdom.getDomino();
		
		if (domino.getStatus() == DominoStatus.ErroneouslyPreplaced) {
			throw new InvalidInputException("domino was erroneously preplaced.");
		}
		
		if (!verifyNeighborAdjacency(dominoInKingdom).isEmpty()) {
			List <DominoInKingdom> adjacentDominoInKingdoms = new ArrayList<DominoInKingdom>();
			for (KingdomTerritory kingdomTerritory : verifyNeighborAdjacency(dominoInKingdom)) {
				if (kingdomTerritory instanceof DominoInKingdom) {
					adjacentDominoInKingdoms.add((DominoInKingdom) kingdomTerritory);
				}
			}
			buildProperty(dominoInKingdom, adjacentDominoInKingdoms);
		} else {
			buildProperty(dominoInKingdom, null);
		}
		domino.setStatus(DominoStatus.PlacedInKingdom);
		return dominoInKingdom;
	}

	/**
	 * @author Ellina
	 * @param domino
	 * @param list of dominos in property
	 * @return true if the domino is included in the property, false if it isn't
	 */

	private static boolean dominoIncludedInProperty(Domino d, List<Domino> listOfDominosInProperty) {
		if (listOfDominosInProperty.contains(d)) {
			return true;
		}

		return false;
	}

	/**
	 * @author Ellina
	 * @param dominoInKingdom
	 * @returns map with lists of dominos adjacent to each tile of input domino
	 */
	private static Map<String, List<DominoInKingdom>> getAdjacentNeighborDominosInKingdomForEachTile(DominoInKingdom givenDominoInKingdom) {
		int givenLeftTileX = givenDominoInKingdom.getX();
		int givenLeftTileY = givenDominoInKingdom.getY();
		int[] givenRightTileCoordinates = getRightTileCoordinates(givenDominoInKingdom);
		int givenRightTileX = givenRightTileCoordinates[0];
		int givenRightTileY = givenRightTileCoordinates[1];
		Domino givenDomino = givenDominoInKingdom.getDomino();
		TerrainType givenLeftTileTerrainType = givenDomino.getLeftTile();
		TerrainType givenRightTileTerrainType = givenDomino.getRightTile();

		List<DominoInKingdom> neighborDominosInKingdomLeft = new ArrayList<DominoInKingdom>();
		List<DominoInKingdom> neighborDominosInKingdomRight = new ArrayList<DominoInKingdom>();

		for (DominoInKingdom otherDominoInKingdom : getAllDominoInKingdom(givenDominoInKingdom.getKingdom())) {
			int otherLeftTileX = otherDominoInKingdom.getX();
			int otherLeftTileY = otherDominoInKingdom.getY();
			int[] otherRightTileCoordinates = getRightTileCoordinates(otherDominoInKingdom);
			int otherRightTileX = otherRightTileCoordinates[0];
			int otherRightTileY = otherRightTileCoordinates[1];
			Domino otherDomino = otherDominoInKingdom.getDomino();
			TerrainType otherLeftTileTerrainType = otherDomino.getLeftTile();
			TerrainType otherRightTileTerrainType = otherDomino.getRightTile();

			//compare left tile with dominos to right and left of it
			boolean valid = true;
			//compare left with left
			valid = checkSurrounding(givenLeftTileX, givenLeftTileY, otherLeftTileX, otherLeftTileY, givenLeftTileTerrainType, otherLeftTileTerrainType);
			//comapre left with right
			if(!valid){
				valid = checkSurrounding(givenLeftTileX, givenLeftTileY, otherRightTileX, otherRightTileY, givenLeftTileTerrainType, otherRightTileTerrainType);
			} 

			if(valid) {
				neighborDominosInKingdomLeft.add(otherDominoInKingdom);
			}
		}

		for (DominoInKingdom otherDominoInKingdom : getAllDominoInKingdom(givenDominoInKingdom.getKingdom())) {
			int otherLeftTileX = otherDominoInKingdom.getX();
			int otherLeftTileY = otherDominoInKingdom.getY();
			int[] otherRightTileCoordinates = getRightTileCoordinates(otherDominoInKingdom);
			int otherRightTileX = otherRightTileCoordinates[0];
			int otherRightTileY = otherRightTileCoordinates[1];
			Domino otherDomino = otherDominoInKingdom.getDomino();
			TerrainType otherLeftTileTerrainType = otherDomino.getLeftTile();
			TerrainType otherRightTileTerrainType = otherDomino.getRightTile();

			//compare left tile with dominos to right and left of it
			boolean valid = true;
			//comapre right with left
			valid = checkSurrounding(givenRightTileX, givenRightTileY, otherLeftTileX, otherLeftTileY, givenRightTileTerrainType, otherLeftTileTerrainType);
			//compare right with right
			if(!valid){
				valid = checkSurrounding(givenRightTileX, givenRightTileY, otherRightTileX, otherRightTileY, givenRightTileTerrainType, otherRightTileTerrainType);
			} 

			if(valid) {
				neighborDominosInKingdomRight.add(otherDominoInKingdom);
			}
		}

		Map<String,List<DominoInKingdom>> map = new HashMap();
		map.put("leftTileAdjacentDominos", neighborDominosInKingdomLeft);
		map.put("rightTileAdjacentDominos", neighborDominosInKingdomRight);
		return map;
	}
	
	
	/**
	 * @author louca
	 * 
	 * @param kingdom whose origin is to be determined
	 * @return int array holding the coordinates as [x, y] of the origin tile (i.e. the tile with its x and y coordinates closest to -4, -4) of the given kingdom 
	 */
	private static int[] getKingdomOriginCoordinates(Kingdom kingdom) {
		int minX = 4;
		int minY = 4;
		for (KingdomTerritory kingdomTerritory : kingdom.getTerritories()) {
			int leftTileX = kingdomTerritory.getX();
			int leftTileY = kingdomTerritory.getY();
			
			if (leftTileX < minX) {
				minX = leftTileX;
			}
			if (leftTileY < minY) {
				minY = leftTileY;
			}
			
			if (kingdomTerritory instanceof DominoInKingdom) {
				int [] rightTileCoordinates = getRightTileCoordinates((DominoInKingdom) kingdomTerritory);
				int rightTileX = rightTileCoordinates[0];
				int rightTileY = rightTileCoordinates[1];
				
				if (rightTileX < minX) {
					minX = rightTileX;
				}
				if (rightTileY < minY) {
					minY = rightTileY;
				}
			}
		}
		return new int[] {minX, minY};
	}
	
	/**
	 * @author louca
	 * @param kingdom whose end it to be determined
	 * @return int array holding the coordinates as [x, y] of the end tile (i.e. the tile with its x and y coordinates closest to 4) of the given kingdom 
	 */
	private static int[] getKingdomEndCoordinates(Kingdom kingdom) {
		int maxX = -4;
		int maxY = -4;
		
		for (KingdomTerritory kingdomTerritory : kingdom.getTerritories()) {
			int leftTileX = kingdomTerritory.getX();
			int leftTileY = kingdomTerritory.getY();
			
			if (leftTileX > maxX) {
				maxX = leftTileX;
			}
			if (leftTileY > maxY) {
				maxY = leftTileY;
			}
			
			if (kingdomTerritory instanceof DominoInKingdom) {
				int [] rightTileCoordinates = getRightTileCoordinates((DominoInKingdom) kingdomTerritory);
				int rightTileX = rightTileCoordinates[0];
				int rightTileY = rightTileCoordinates[1];
				
				if (rightTileX > maxX) {
					maxX = rightTileX;
				}
				if (rightTileY > maxY) {
					maxY = rightTileY;
				}
			}
		}
		return new int[] {maxX, maxY};
	}
	
	/**
	 * @author Louca
	 * @param kingdom that is to be checked whether its size is within 5x5 on the grid
	 * @return whether or not the given kingdom's size is within 5x5, i.e. whether the distance between the x and y components of the given kingdom's origin and end coordinates are both less than 5
	 * @see https://mycourses2.mcgill.ca/d2l/le/433764/discussions/threads/736105/View?searchText=rotate for how this method differs from the verifyKingdomTerritoryInGrid method
	 */
	public static boolean verifyGridSize(Kingdom kingdom) {
		int [] originCoordinates = getKingdomOriginCoordinates(kingdom);
		int originX = originCoordinates[0];
		int originY = originCoordinates[1];
		
		int [] endCoordinates = getKingdomEndCoordinates(kingdom);
		int endX = endCoordinates[0];
		int endY = endCoordinates[1];
		
		if ((Math.abs(originX - endX) > 4) || (Math.abs(originY - endY) > 4) // if the distance between the origin and end coordinates > 4
				|| originX < -4 || originY < -4 || endX > 4 || endY > 4) { // or any of the origin or end coordinates is outside [-4, 4]
			return false;
		}
		return true;
	}
	
	/**
	 * @author Louca
	 * @param kingdomTerritory that is to be checked whether the grid location it occupies is within the 9x9 board size (can be either an instance of Castle or DominoInKingdom)
	 * @return whether or not the grid location (and the grid location occupied by the right tile given kingdom territory is an instance of DominoInKingdom) is within the 9x9 board size
	 */
	public static boolean verifyKingdomTerritoryInBoard(KingdomTerritory kingdomTerritory) {
		int leftTileX = kingdomTerritory.getX();
		int leftTileY = kingdomTerritory.getY();
		
		if (leftTileX < -4 || leftTileX > 4 || leftTileY < -4  || leftTileY > 4) {		
			return false;
		}
		
		if (kingdomTerritory instanceof DominoInKingdom) {
			int [] rightTileCoordinates = getRightTileCoordinates(kingdomTerritory);
			int rightTileX = rightTileCoordinates[0];
			int rightTileY = rightTileCoordinates[1];
			
			if (rightTileX < -4 || rightTileX > 4 || rightTileY < -4 ||rightTileY > 4) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * 
	 * @author Amani
	 * 
	 * @param DominoInKingdom
	 * @param Castle
	 * @return boolean
	 * 
	 * @version 3.0
	 * 
	 */
	public static boolean verifyCastleAdjacency(DominoInKingdom d){
		int leftX = d.getX();
		int leftY = d.getY();
		int[] rightCorrdinates = getRightTileCoordinates(d);
		int rightX = rightCorrdinates[0];
		int rightY = rightCorrdinates[1];

		if(((leftX == 0) && (leftY == 0)) || ((rightX == 0) && (rightY == 0))) return false;
		if((leftX == -1) && (leftY == 0)) return true;
		if((rightX == -1) && (rightY == 0)) return true;
		if((leftX == 0) && (leftY == 1)) return true;
		if((leftX == 0) && (leftY == -1)) return true;
		if((rightX == 0) && (rightY == 1)) return true;
		if((rightX == 0) && (rightY == -1)) return true;
		if((leftX == 1) && (leftY == 0)) return true;
		if((rightX == 1) && (rightY == 0)) return true;
		return false;
	}

	/**
	 * @author Elias
	 * This method saves current game at file "fileName" 
	 * @param fileName
	 * @return
	 * @throws InvalidInputException
	 * @throws IOException 
	 */
	public static boolean saveGame(String fileName, boolean confirm) throws InvalidInputException, IOException  {
		boolean savedGame = false;
		String relativePath =  fileName;
		// if the File exists
		if(!isExistedFile(relativePath)) {
			createNewFile(fileName);
		}
		if (confirm) {
			saveCurrentGameAsFile(fileName);
			savedGame = true;
		}
		return savedGame;
	}
	
	/**
	 * @author Elias
	 * This method creates a file named file name at this path "./src/main/resources/savedGames/"
	 * @param fileName
	 * @return
	 * @throws InvalidInputException 
	 * @throws IOException 
	 * 
	 * @TESTED 
	 */
	public static boolean createNewFile(String fileName) throws IOException, InvalidInputException {
		boolean saveFileCreated = false;
		String relativePath =  fileName;
		File saveFile = new File(relativePath);
		saveFile.createNewFile();
		return saveFileCreated;
	}
	
	/**
	 * @author Elias
	 * @param relativePath
	 * @return true if file is existed and false otherwise
	 * 
	 * @TESTED
	 */
	public static boolean isExistedFile(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}
	
	/**
	 * @author Elias
	 * This method writes game info as a string on a file f
	 * @param fileName
	 * @return
	 * @throws InvalidInputException
	 */
	private static boolean saveCurrentGameAsFile(String fileName) throws InvalidInputException, IOException {
		String relativePath = fileName;
		String saveGameData = saveDataAsString();
		PrintWriter printWriter = new PrintWriter(new FileWriter(relativePath));
		printWriter.printf("%s", saveGameData);
		printWriter.close();
		return true;
	}
	
	/**
	 * @author Elias 
	 * This method creates a virtual Game that we can use to test our methods 
	 * @IMPORTANT 
	 * this game that we create is not a valid game it's just a way to test our code without having Null pointer Exception
	 * @return
	 */
	public static Game createGame() {
		Kingdomino kingdomino = new Kingdomino();
		Game currentGame = new Game(9, kingdomino);
		createAllDominoes(currentGame);
		Player p1  = new Player(currentGame);
		Player p2  = new Player(currentGame);
		Player p3  = new Player(currentGame);
		Player p4  = new Player(currentGame);

		Kingdom k1 = new Kingdom(p1);
		Kingdom k2 = new Kingdom(p2);
		Kingdom k3 = new Kingdom(p3);
		Kingdom k4 = new Kingdom(p4);
		Draft currentDraft = new Draft(null, currentGame);
		Draft nextDraft = new Draft(null, currentGame);
		currentGame.setCurrentDraft(currentDraft);
		currentGame.setNextDraft(nextDraft);
		KingdominoApplication.getKingdomino().setCurrentGame(currentGame);
		DominoSelection s1= new DominoSelection(p1, getdominoByID(8), nextDraft);
		DominoSelection s2= new DominoSelection(p2, getdominoByID(7), nextDraft);
		DominoSelection s3= new DominoSelection(p3, getdominoByID(6), currentDraft);
		DominoSelection s4= new DominoSelection(p4, getdominoByID(5), currentDraft);
		
		currentDraft.addSelection(s3);
		currentDraft.addSelection(s4);
		currentDraft.addIdSortedDomino(getdominoByID(35));
		currentDraft.addIdSortedDomino(getdominoByID(24));
		
		nextDraft.addIdSortedDomino(getdominoByID(3));
		nextDraft.addIdSortedDomino(getdominoByID(12));
		nextDraft.addSelection(s1);
		nextDraft.addSelection(s2);
		
		
		DominoInKingdom d = new DominoInKingdom(1, 0, k1, getdominoByID(11));
		DominoInKingdom z = new DominoInKingdom(1, 0, k2, getdominoByID(13));
		
		

		return currentGame;
	}

	/**
	 * @author Amani
	 * 
	 * @param DominoInKingdom : domino you want to verify adacency to another 
	 * @return true if given DominoInKingdom has a tile that is adjacent to another
	 * tile with the same TerrainType
	 * 
	 * @version 1.1
	 * 
	 */
	public static List<KingdomTerritory> verifyNeighborAdjacency(DominoInKingdom d){
		Kingdom kingdom = d.getKingdom();
		List<KingdomTerritory> territories = kingdom.getTerritories();
		Domino givenDomino = d.getDomino();
		//DirectionKind givenDomDir = d.getDirection();
		TerrainType givenLeftType = givenDomino.getLeftTile();
		TerrainType givenRightType = givenDomino.getRightTile();
		int givenLeftX = d.getX();
		int givenLeftY = d.getY();
		List<KingdomTerritory> adjacentTerr = new ArrayList<KingdomTerritory>();
		//get coordinates of right tile of given domino
		int[] rightTileCoordinates = getRightTileCoordinates(d);
		int givenRightX = rightTileCoordinates[0];
		int givenRightY = rightTileCoordinates[1];

		//int i;
		DominoInKingdom terrDIK;
		Domino terrDom;
		TerrainType terrLeftType;
		TerrainType terrRightType;
		//DirectionKind terrDomDir;
		int terrLeftX;
		int terrLeftY;
		int terrRightX;
		int terrRightY;
		//iterate through all territories in kingdom
		for(KingdomTerritory thisTerr: territories){
			boolean check;
			//if territory is a DominoInKingdom, then we can compare it's placement and TerrainType
			//with those of the given domino
			if((thisTerr instanceof DominoInKingdom) && (!thisTerr.equals(d))){
				terrDIK = (DominoInKingdom) thisTerr;
				terrDom = terrDIK.getDomino();
				terrLeftType = terrDom.getLeftTile();
				terrRightType = terrDom.getRightTile();
				//terrDomDir = terrDIK.getDirection();
				terrLeftX = terrDIK.getX();
				terrLeftY = terrDIK.getY();

				//get coordinates of right tile of territory domino
				rightTileCoordinates = getRightTileCoordinates(terrDIK);
				terrRightX = rightTileCoordinates[0];
				terrRightY = rightTileCoordinates[1];

				//check if territory domino tile is on 4 surrounding possible places around given domino tile
				check = false;
				//compare left with left
				check = checkSurrounding(givenLeftX, givenLeftY, terrLeftX, terrLeftY, givenLeftType, terrLeftType);
				//comapre left with right
				if(!check){
					check = checkSurrounding(givenLeftX, givenLeftY, terrRightX, terrRightY, givenLeftType, terrRightType);
				} 
				//comapre right with left
				if(!check){
					check = checkSurrounding(givenRightX, givenRightY, terrLeftX, terrLeftY, givenRightType, terrLeftType);
				} 
				//compare right with right
				if(!check){
					check = checkSurrounding(givenRightX, givenRightY, terrRightX, terrRightY, givenRightType, terrRightType);
				} 
				if(check) {
					adjacentTerr.add(thisTerr);
				}
			}
		}
		return adjacentTerr;
	}

	/**
	 * @author Amani
	 * 
	 * Helper method
	 * 
	 * after being given 2 coordinates points and 2 terrain types for 2 different tiles
	 * this method checks if these tiles are next to each other and whether they have the 
	 * same TerrainType
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param t1
	 * @param t2
	 * @return
	 * 
	 * @version 1.0
	 */
	private static boolean checkSurrounding(int x1, int y1, int x2, int y2, TerrainType t1, TerrainType t2){
		if(x2 == x1){
			if ((y2 == y1 + 1) || (y2 == y1 - 1)){
				if(t2 == t1) return true;
			}
		}else if(y2 == y1){
			if((x2 == x1 + 1) || (x2 == x1 - 1)){
				if (t2 == t1) return true;
			}
		}
		return false;
	}
	/**
	 * @author Elias
	 * loads a game from a file 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static boolean loadGame(String fileName) throws FileNotFoundException {
		Kingdomino kk = new Kingdomino();
		Game loadedGame = KingdominoApplication.getKingdomino().getCurrentGame();
		Player p1 =loadedGame.getPlayer(0);
		Player p2 =loadedGame.getPlayer(1);
		Player p3 = loadedGame.getPlayer(2);
		Player p4 = loadedGame.getPlayer(3);
		Kingdom k1 = p1.getKingdom();
		Kingdom k2 = p2.getKingdom();
		Kingdom k3 = p3.getKingdom();
		Kingdom k4 = p4.getKingdom();
		Draft currentDraft = new Draft(null, loadedGame);
		Draft nextDraft = new Draft(null, loadedGame);
		loadedGame.setCurrentDraft(currentDraft);
		loadedGame.setNextDraft(nextDraft);
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		int turn = whosturn(fileName);
		loadedGame.setNextPlayer(loadedGame.getPlayer(turn-1));
		//processing first line (C:....)
		String line = sc.nextLine();
		line = deleteSpaces(line); //spaces are evil
		line = line.substring(2);
		String[] Claimed = line.split(",");
		int c = Claimed.length;
		for(int i = turn; i<=4 ; i++) {
			Player tempPlayer = null;
			if (i ==1) tempPlayer = p1;
			else if (i ==2) tempPlayer = p2;
			else if (i ==3) tempPlayer = p3;
			else if (i ==4) tempPlayer = p4;

			String s= Claimed[i-1];
			int id = Integer.parseInt(s.trim());	
			Domino d = new Domino (id, null, null, i, loadedGame);
			DominoSelection selection = new DominoSelection(tempPlayer, d, loadedGame.getCurrentDraft());
		}		
		for(int i = 1; i<turn ; i++) {
			Player tempPlayer = null;
			if (i ==1) tempPlayer = p1;
			else if (i ==2) tempPlayer = p2;
			else if (i ==3) tempPlayer = p3;
			else if (i ==4) tempPlayer = p4;

			String s= Claimed[i-1];
			int id = Integer.parseInt(s.trim());	
			Domino d = new Domino (id, null, null, 0, loadedGame);
			DominoSelection selection = new DominoSelection(tempPlayer, d, loadedGame.getNextDraft());
		}
		line = sc.nextLine();
		line = deleteSpaces(line); //spaces are evil
		line = line.substring(2);
		Claimed = line.split(",");
		int u = Claimed.length;

		

		for(int i=0; i<Claimed.length;i++) {
			String s = Claimed[i];
			int id = Integer.parseInt(s.trim());	
			Domino domino = new Domino(id, null, null, 0, loadedGame);
			loadedGame.getNextDraft().addIdSortedDomino(domino);
		}
		for(int i = 0; i<4; i++) {
			line = sc.nextLine();
			//line = deleteSpaces(line); //spaces are evil
			line = line.substring(4);
			Claimed = line.split(", ");
			for(String j : Claimed) {
				if(i ==0)loadDominoinKingdom(loadedGame,k1, j);
				else if(i ==1)loadDominoinKingdom(loadedGame,k2, j);
				else if(i ==2)loadDominoinKingdom(loadedGame,k3, j);
				else loadDominoinKingdom(loadedGame,k4, j);
			}
		}
		for(Player p : loadedGame.getPlayers()) {
			Kingdom k = p.getKingdom();
		}
		return true;
	}
	
	/**
	 * @author Elias
	 * @param fileName
	 * @return the number of the player who's turn to start
	 * @throws FileNotFoundException
	 */
	public static int whosturn(String fileName) throws FileNotFoundException {
		//	String relativePath = "./src/main/resources/savedGames/" + fileName;
		File file = new File(fileName);
		int player = 1;
		Scanner sc = new Scanner(file);
		String s = sc.nextLine();
		s = deleteSpaces(s); //spaces are evil
		s = s.substring(2);
		String[] Claimed = s.split(",");
		int len = Claimed.length;
	
		
		sc.nextLine();
		int maxlen = 50;
		int i = 1;
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			line = line.substring(4);
			String[] selections = line.split(",");
			if (selections.length<maxlen) {
				maxlen = selections.length;
				player = i;
			}
			i++;
		}
		
		
		if (maxlen ==12) {
			player =1;
			int number = Integer.MAX_VALUE;
			for(String string: Claimed) {
				number = Math.min(number, Integer.parseInt(string));
			}
			
			for(String string: Claimed) {
				int Pnumber = Integer.parseInt(string);
				if(Pnumber == number) {
					break;
				}
				player++;
			}
			return player;
		}
		return player;
	}
	
	/**
	 * @author Elias
	 * this method gets a string and return the same string after deleting all spaces
	 * this makes our life easier when we read from a file
	 * @param s
	 */
	public static String deleteSpaces(String s) {
		String withoutSpaces = "";
		for(int i = 0; i<s.length();i++) {
			if(s.charAt(i)!=' ') {
				withoutSpaces+=s.charAt(i);
			}
		}
		return withoutSpaces;
	}
	
	/**
	 * @author Elias
	 * 
	 * @param g
	 * @param k
	 * @param string
	 * @return
	 */
	public static DominoInKingdom loadDominoinKingdom(Game g,Kingdom k, String string) {
		String s1,s2,s3;
		char c;
		String[] info = string.split("@");
		s1 = info[0];
		String[] coords = info[1].split(",");
		s2 = coords[0].substring(1);
		s3 = coords[1];
		c  = coords[2].charAt(0);
		
		int id = Integer.parseInt(s1.trim()), x = Integer.parseInt(s2.trim()),y= Integer.parseInt(s3.trim());
		for(Player p : g.getPlayers()) {
			if(id == p.getDominoSelection().getDomino().getId()) {
				throw new java.lang.IllegalArgumentException("Invalid input");
			}
			System.out.println(id);
		}
		if(x>4 || x< -4 || y> 4 || y <-4 || id<0 || id>48) {
			throw new java.lang.IllegalArgumentException("Invalid input");
		}
	
		
		Domino d = getdominoByID(id);
		DominoInKingdom dk = new DominoInKingdom(x, y, k, d);
		dk.setDirection(util.directionKindFromChar(c));
		verifyDominoPreplacement(dk);
		if(dk.getDomino().getStatus() == DominoStatus.ErroneouslyPreplaced) {
			throw new java.lang.IllegalArgumentException("Invalid input");
		}
	
		return dk;
	}
	
	/**
	 * @author Elias 
	 * @param fileName (raltive path)
	 * @return true if a file exist and false other wise
	 */
	public static boolean containsFile(String fileName) {
		String relativePath =  fileName;
		File file = new File(relativePath);
		return file.exists();
	}
	
	/**
	 * @author Elias
	 * This method converts a game data into string
	 * @return
	 * @throws IOException
	 * @throws InvalidInputException
	 */
	public static String saveDataAsString () throws IOException, InvalidInputException {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game currentGame = KingdominoApplication.getCurrentGame();
		String saveGameData = "C:";
		Draft currentDraft = currentGame.getCurrentDraft();
		Draft nextDraft = currentGame.getNextDraft();
		for(Player p : currentGame.getPlayers()) {
			if(p.getDominoSelection()!= null) {
				saveGameData+= p.getDominoSelection().getDomino().getId()+",";
			}
		}
		saveGameData = saveGameData.substring(0, saveGameData.length() - 1); // :-) love this		
		saveGameData+= "\n";
		saveGameData+= "U:";
		if(currentDraft.getIdSortedDominos()!= null) {
			for (Domino i:currentDraft.getIdSortedDominos()) {
				saveGameData+=i.getId()+",";
			}
		}
		if(nextDraft.getIdSortedDominos()!= null) {
			for (Domino i:nextDraft.getIdSortedDominos()) {
				saveGameData+=i.getId()+",";
			}
			saveGameData = saveGameData.substring(0, saveGameData.length() - 1); // :-) love this
		}
		saveGameData+= "\nP1:";
		int i= 2;
		for (Player p : currentGame.getPlayers()) {
			Kingdom k = p.getKingdom();
			boolean entered = false;
			if(k.getTerritories()!= null) {
				for(KingdomTerritory op : k.getTerritories()) {
					DominoInKingdom s = (DominoInKingdom) op;
					saveGameData+=	s.getDomino().getId()+ "@(";
					saveGameData+= s.getX() + ",";
					saveGameData+=s.getY() +",";
					saveGameData+= util.stringFromDirectionKind(s.getDirection())+ "),";
					entered = true;
				}
				if (true)saveGameData = saveGameData.substring(0, saveGameData.length() - 1); // :-) love this
				entered = false;
				saveGameData+= "\nP"+i+":";
				i++;
			}

		}
		saveGameData = saveGameData.substring(0, saveGameData.length() - 4); // :-) love this (this deletes P5: )
		return saveGameData;
	}

	/**
	 * @author Elias
	 * this is a method that reads a file and converts it into a string
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String readFileAsString(String fileName) throws FileNotFoundException {
		String relativePath =  fileName;
		File file = new File(relativePath);
		Scanner s = new Scanner(file);
		String fileData= "";
		while(s.hasNext()) {
			fileData += s.next()+"\n";		
		}
		if(fileData!= null) {
			fileData = fileData.substring(0,fileData.length()-1);
		}
		return fileData;
	}

	/**
	 * @author Amani
	 * 
	 * @param d
	 * @return true if given DominoInKingdom is overlapping with another terrain and false if not 
	 * 
	 * @version 2.0
	 */
	public static boolean verifyNoOverlapping(DominoInKingdom d){
		Kingdom kingdom = d.getKingdom();
		List<KingdomTerritory> territories = kingdom.getTerritories();
		//DirectionKind givenDomDir = d.getDirection();
		int givenLeftX = d.getX();
		int givenLeftY = d.getY();
		//get coordinates of right tile of given domino
		int[] rightTileCoordinates = getRightTileCoordinates(d);
		int givenRightX = rightTileCoordinates[0];
		int givenRightY = rightTileCoordinates[1];

		int i;
		//Castle terrCastle;
		DominoInKingdom terrDIK;
		//DirectionKind terrDir;
		int terrLeftX = 0;
		int terrLeftY = 0;
		int terrRightX = 0;
		int terrRightY = 0;
		for(i = 0; i < territories.size(); i++){ // should probably be territories.size()
			KingdomTerritory thisTerr = territories.get(i);
			if(thisTerr instanceof Castle){
				terrLeftX = thisTerr.getX();
				terrLeftY = thisTerr.getY();
				terrRightX = terrLeftX;
				terrRightY = terrLeftY;
			}else if((thisTerr instanceof DominoInKingdom) && (!thisTerr.equals(d))){
				terrDIK = (DominoInKingdom) thisTerr;
				//terrDir = terrDIK.getDirection();
				terrLeftX = terrDIK.getX();
				terrLeftY = terrDIK.getY();
				terrRightX = 0;
				terrRightY = 0;

				//get coordinates of right tile of territory domino
				rightTileCoordinates = getRightTileCoordinates(terrDIK);
				terrRightX = rightTileCoordinates[0];
				terrRightY = rightTileCoordinates[1];
			}

			boolean check;
			//compare left and left
			check = ((givenLeftX == terrLeftX) && (givenLeftY == terrLeftY));
			if(check) return true;
			//compare left and right
			check = ((givenLeftX == terrRightX) && (givenLeftY == terrRightY));
			if(check) return true;
			//compare right and left
			check = ((givenRightX == terrLeftX) && (givenRightY == terrLeftY));
			if(check) return true;
			//compare right and right
			check = ((givenRightX == terrRightX) && (givenRightY == terrRightY));
			if(check) return true;
		}

		return false;
	}

	public static void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				game.addAllDomino(new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}

	private static Domino getdominoByID(Integer id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	private static TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}

	/**
	 * @author Louca
	 * @param dominoInKingdom for which the right tile coordinates are to be obtained
	 * @return int array holding the coordinates as [x, y] of the right tile of the given dominoInKingdom
	 */
	public static int[] getRightTileCoordinates(KingdomTerritory kingdomTerritory) {
		int leftTileX = kingdomTerritory.getX();
		int leftTileY = kingdomTerritory.getY();

		if (kingdomTerritory instanceof Castle) {
			return new int[] {leftTileX, leftTileX};
		}

		DominoInKingdom dominoInKingdom = (DominoInKingdom) kingdomTerritory;
		int rightTileX = 0;
		int rightTileY = 0;

		switch(dominoInKingdom.getDirection()){
		case Up:
			rightTileX = leftTileX;
			rightTileY = leftTileY + 1;
			break;
		case Down:
			rightTileX = leftTileX;
			rightTileY = leftTileY - 1;
			break;
		case Right:
			rightTileX = leftTileX + 1;
			rightTileY = leftTileY;
			break;
		case Left:
			rightTileX = leftTileX -1;
			rightTileY = leftTileY;
			break;
		}

		return new int[] {rightTileX, rightTileY};
	}

	/**
	 * @author Chen
	 * @param kingdom 
	 *
	 * @return a list of dominos in kingdom
	 */
	public static List<DominoInKingdom> getAllDominoInKingdom(Kingdom kingdom) {
		List<DominoInKingdom> dominosInKingdom = new ArrayList<>();

		for(KingdomTerritory kingdomTerritory: kingdom.getTerritories()) {
			if(kingdomTerritory instanceof DominoInKingdom) {
				dominosInKingdom.add((DominoInKingdom) kingdomTerritory);
			}
		}
		return dominosInKingdom;
	}

	/**
	 * @author Chen
	 * 
	 *
	 * @return true if MiddleKingdom bonus
	 */
	public static boolean verifyMiddleKingdom(Kingdom aKingdom){
		if(aKingdom.numberOfTerritories()<2) {
			return false;
		}

		int minY=0,minX=0,maxY=0, maxX=0;
		for (DominoInKingdom dominoInKingdom : getAllDominoInKingdom(aKingdom)) {
			int leftTileX = dominoInKingdom.getX();
			int leftTileY = dominoInKingdom.getY();
			int [] rightTileCoordinates = getRightTileCoordinates(dominoInKingdom);
			int rightTileX = rightTileCoordinates[0];
			int rightTileY = rightTileCoordinates[1];

			minX = Math.min(leftTileX, rightTileX) < minX ? Math.min(leftTileX, rightTileX) : minX;
			minY = Math.min(leftTileY, rightTileY) < minY ? Math.min(leftTileY, rightTileY) : minY;
			maxX = Math.max(leftTileX, rightTileX) > maxX ? Math.max(leftTileX, rightTileX) : maxX;
			maxY = Math.max(leftTileY, rightTileY) > maxY ? Math.max(leftTileY, rightTileY) : maxY;

		}

		if((maxY==2) && (maxX==2) && (minX==-2) && (minY==-2)) {
			return true;
		}

		return false;
	}

	/**This method calcualte bonus score for a player
	 * @author Chen
	 * @param player
	 * @return bonus score 
	 */
	public static int calculateBonusScore(Player aPlayer) {
		int bonusScore = 0;

		if(aPlayer.getGame().getKingdomino().hasBonusOptions()!=true) {
			aPlayer.setBonusScore(bonusScore);
			return aPlayer.getBonusScore();
		}

		boolean isMiddleKingdomSelected = false;
		boolean isHarmonySelected = false;
		for(BonusOption bonusOption: aPlayer.getGame().getSelectedBonusOptions()) {
			if(bonusOption.getOptionName().equals("Harmony")) isHarmonySelected = true;
			if(bonusOption.getOptionName().equals("Middle Kingdom")) isMiddleKingdomSelected = true;
		}

		if(isMiddleKingdomSelected && verifyMiddleKingdom(aPlayer.getKingdom())) {
			bonusScore += 10;
		}

		if (isHarmonySelected && aPlayer.getKingdom().numberOfTerritories() == 13) {
			bonusScore += 5;
		}
		
		aPlayer.setBonusScore(bonusScore);
		return aPlayer.getBonusScore();
	}

	/** Set the corresponding fields for properties 
	 * @author Chen
	 * @return a list of properties with correct fields
	 */
	public static List<Property> calculatePropertyAttributes(Player aPlayer) {
		TerrainType propertyType;
		List<Property> property = TileFromDomino.convertTilePropertyToProerty(aPlayer.getKingdom());
		for(Property pt: property) {
			List<Domino> dominosInProperty = pt.getIncludedDominos();
			int size=0;
			int score=0;
			int crowns=0;
			propertyType = pt.getPropertyType();

			for (Domino domino: dominosInProperty) {
				if(domino.getRightTile().equals(propertyType)) {
					size+=1;
					crowns+=domino.getRightCrown();
				}
				if(domino.getLeftTile().equals(propertyType)) {
					size+=1;
				}
				// crown only exist in the right tile.
			}
			score = size*crowns;
			pt.setCrowns(crowns);
			pt.setSize(size);
			pt.setScore(score);
		}
		
		return property;
	}



	/**
	 * @author Chen
	 * 
	 *@param player
	 * @return player score 
	 */
	public static int calculatePlayerScore1(Player aPlayer) {
		TileFromDomino.convertTilePropertyToProerty(aPlayer.getKingdom());
		int score=0;
		List<Property> property = aPlayer.getKingdom().getProperties();  //TileFromDomino.convertTilePropertyToProerty(aPlayer.getKingdom());
		for(Property pt: property) {
			score+=pt.getScore();
		}
		aPlayer.setPropertyScore(score);
		calculateBonusScore(aPlayer);
		return aPlayer.getTotalScore();
	}

	/**
	 * @author Louca
	 * @param the player whose property score is to be calculated
	 * @return the given player's property score after setting it on the given player
	 * 
	 * this method also updates the score of all of the given player's properties using the properties size and crowns attributes
	 */
	public static int calculatePropertyScore(Player player) {
		TileFromDomino.convertTilePropertyToProerty(player.getKingdom());
		int propertyScore = 0;
		for (Property property : player.getKingdom().getProperties()) {
			property.setScore(property.getCrowns() * property.getSize()); // update if necessary
			propertyScore += property.getScore();
		}

		player.setPropertyScore(propertyScore);
		return player.getPropertyScore();
	}

	/**
	 * @author Louca
	 * @param player whose player score is to be calculated
	 * @return the given player's total after calculating the given player's property score and bonus score 
	 */
	public static int calculatePlayerScore(Player player) {
		calculatePropertyScore(player);
		calculateBonusScore(player);
		return player.getTotalScore();
	}

	/**
	 * @author Amani
	 * 
	 * @param k
	 * @return true if Kingdom is within a 5x5 grid
	 * 
	 * @version 1.0
	 */
	public static boolean verifyKingdomSize(Kingdom k) {
		List<KingdomTerritory> territories = k.getTerritories();
		List<Integer> listOfX = new ArrayList<Integer>();
		List<Integer> listOfY = new ArrayList<Integer>();

		int i;
		int terrLeftX;
		int terrLeftY;
		DominoInKingdom terrDIK;
		DirectionKind terrDir;

		for(i = 0; i < territories.size(); i++){
			KingdomTerritory thisTerr = territories.get(i);
			if(thisTerr instanceof Castle){
				terrLeftX = thisTerr.getX();
				terrLeftY = thisTerr.getY();
				listOfX.add(terrLeftX);
				listOfY.add(terrLeftY);
			}else if(thisTerr instanceof DominoInKingdom){
				terrLeftX = thisTerr.getX();
				terrLeftY = thisTerr.getY();
				listOfX.add(terrLeftX);
				listOfY.add(terrLeftY);
				terrDIK = (DominoInKingdom) thisTerr;
				terrDir = terrDIK.getDirection();

				switch(terrDir){
				case Up:
					listOfY.add(terrLeftY + 1);
					break;
				case Down:
					listOfY.add(terrLeftY - 1);
					break;
				case Right:
					listOfX.add(terrLeftX + 1);
					break;
				case Left:
					listOfX.add(terrLeftX - 1);
					break;
				}
			}
		}
		Collections.sort(listOfX);
		Collections.sort(listOfY);
		int minX = listOfX.get(0);
		int maxX = listOfX.get(listOfX.size() - 1);
		int minY = listOfY.get(0);
		int maxY = listOfY.get(listOfY.size() - 1);

		return (((maxX - minX) < 5) && ((maxY - minY) < 5));
	}

	/**
	 * @author Ellina
	 * @param dominoInKingdom
	 * @param List<DominoInKingdom>
	 * @return void
	 */
	private static void buildProperty(DominoInKingdom d, List<DominoInKingdom> dominosInProperty) {
		List<KingdomTerritory> adjacentTerritories = verifyNeighborAdjacency(d);
		for(int i = 0; i < adjacentTerritories.size(); i++) {
			if(!dominosInProperty.contains(adjacentTerritories.get(i))){
				dominosInProperty.add((DominoInKingdom) adjacentTerritories.get(i));
				buildProperty((DominoInKingdom) adjacentTerritories.get(i), dominosInProperty);
			}
		}
	}

	/**
	 * @author Ellina
	 * @param player
	 * @return List of properties for a single player and their kingdom
	 */

	public static List<Property> identifyProperties(Player p) {
		Kingdom kingdom = p.getKingdom();
		List<Property> properties = kingdom.getProperties();
		return properties;
	}
	
	/**
	 * @author louca
	 * 
	 *         uses the same brute force algorithm as the discardDomino. Used to
	 *         provide a placement hint for the user upon request
	 * @param dominoInKingdom that we want to find a correct preplacement for.
	 * @return an int array where the first value is the x, the second the y, and
	 *         the third the index of the direction (where 0 is Up, 1 is right, and
	 *         so on clockwise) of a correct placement for the given domino
	 * @throws InvalidInputException if there is no correct placement for the given
	 *                               domino
	 * 
	 *                               restore the position and state of the given
	 *                               domino back to its initial state before method
	 *                               call
	 */
	public static int[] findACorrectPreplacement(DominoInKingdom dominoInKingdom) throws InvalidInputException {
		int initialX = dominoInKingdom.getX();
		int initialY = dominoInKingdom.getY();

		DirectionKind initialDirection = dominoInKingdom.getDirection();
		for (int x = -4; x <= 4; x++) {
			for (int y = -4; y <= 4; y++) {
				for (DirectionKind direction : DirectionKind.values()) {
					dominoInKingdom.setX(x);
					dominoInKingdom.setY(y);
					dominoInKingdom.setDirection(direction);

					verifyDominoPreplacement(dominoInKingdom);
					if (dominoInKingdom.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced) {
						dominoInKingdom.setX(initialX);
						dominoInKingdom.setY(initialY);
						dominoInKingdom.setDirection(initialDirection);
						verifyDominoPreplacement(dominoInKingdom); // restore domino status
						int directionNumber;
						if (direction == DirectionKind.Up) {
							directionNumber = 0;
						} else if (direction == DirectionKind.Right) {
							directionNumber = 1;
						} else if (direction == DirectionKind.Down) {
							directionNumber = 2;
						} else {
							directionNumber = 3;
						}
						return new int[] { x, y, directionNumber };
					}
				}
			}
		}

		throw new InvalidInputException("given domino does not have any correct preplacements in the kingdom.");
	}

	/**
	 * @author louca
	 * 
	 * @param kingdom of the player requesting the hint
	 * @param draft   that the player is to select their next domino from
	 * @return the domino that can be correctly preplaced in their
	 *         kingdom (if many can be correctly preplaced, returns the 
	 *         first domino encountered in sorted order)
	 * 
	 * @throws InvalidInputException if none of the unselected dominos of the given draft could
	 *                               be placed in the given kingdom (i.e. the player
	 *                               will have to select an arbitrary domino and
	 *                               discard it)
	 */
	public static Domino findACorrectSelection(Kingdom kingdom, Draft draft) throws InvalidInputException {
		List<DominoSelection> selectionsOfDraft = draft.getSelections();
		for (Domino domino : draft.getIdSortedDominos()) {
			boolean alreadySelected = false;
			
			for (DominoSelection dominoSelection : selectionsOfDraft) {
				if (domino.equals(dominoSelection.getDomino())) {
					alreadySelected = true;
					break;
				}
			}
			
			if (alreadySelected) {
				continue; // to next domino in given draft 
			}
			
			DominoInKingdom dominoInKingdom = new DominoInKingdom(0, 0, kingdom, domino);
			dominoInKingdom.setDirection(DirectionKind.Up);

			for (int x = -4; x <= 4; x++) {
				for (int y = -4; y <= 4; y++) {
					for (DirectionKind direction : DirectionKind.values()) {
						dominoInKingdom.setX(x);
						dominoInKingdom.setY(y);
						dominoInKingdom.setDirection(direction);

						verifyDominoPreplacement(dominoInKingdom);
						if (dominoInKingdom.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced) {
							dominoInKingdom.delete();
							return domino; // player should select this domino from the current draft
						}
					}
				}
			}
		}
		throw new InvalidInputException(
				"given draft does not have any unselected dominos with any correct preplacements in given kingdom.");
	}
	
	/**
	 * @author Louca
	 * 
	 * @param dominoInKingdom to be discarded 
	 * @return whether the dominoInKingdom's status was set to Discarded
	 * @throws InvalidInputException if the given dominoInKingdom has at least one correct preplacement in its kingdom
	 */
	public static boolean discardDomino(DominoInKingdom dominoInKingdom) throws InvalidInputException {
		int initialX = dominoInKingdom.getX();
		int initialY = dominoInKingdom.getY();
		DirectionKind initialDirection = dominoInKingdom.getDirection();
		for (int x=-4; x<=4; x++) {
			for (int y=-4; y<=4; y++) {
				for (DirectionKind direction : DirectionKind.values()) {
					dominoInKingdom.setX(x);
					dominoInKingdom.setY(y);
					dominoInKingdom.setDirection(direction);

					verifyDominoPreplacement(dominoInKingdom);
					if (dominoInKingdom.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced) {
						dominoInKingdom.setX(initialX);
						dominoInKingdom.setY(initialY);
						dominoInKingdom.setDirection(initialDirection);
						verifyDominoPreplacement(dominoInKingdom); // restore domino status
						return false;
						//throw new InvalidInputException("given domino has at least one correct preplacement in the kingdom.");
					}
				}
			}
		}

		dominoInKingdom.getKingdom().removeTerritory(dominoInKingdom);
		dominoInKingdom.getDomino().setStatus(DominoStatus.Discarded);
		return true;
	}

	/**
	 * @author Louca
	 * @param kingdomino the system that owns the created user
	 * @param name that will be given to the new user
	 * @return the newly created user
	 * @throws InvalidInputException if name is empty or null, not unique (ignoring case), or non-alphanumeric.
	 */
	public static User createUser(Kingdomino kingdomino, String name) throws InvalidInputException {
		if (name.isEmpty() || name == null) {
			throw new InvalidInputException("name was empty or null.");
		}

		for (User user : kingdomino.getUsers()) {
			if (user.getName().toLowerCase().equals(name.toLowerCase())) {
				throw new InvalidInputException("user with the given name already exists (ignoring case).");
			}
		}

		for(char c : name.toCharArray())
		{
			if (! Character.isLetterOrDigit(c))
				throw new InvalidInputException("name contained non-alphanumeric characters.");
		}

		return new User(name, kingdomino);
	}

	/**
	 * @author Louca
	 * @param kingdomino thee system for which the users are to be listed
	 * @return a list of all users sorted in increasing alphabetical order
	 */
	public static List<User> listAllUsers(Kingdomino kingdomino) {		
		List<User> allUsers = kingdomino.getUsers(); // unmodifiable
		List<User> sortedUsers = new ArrayList<User>(allUsers); // modifiable

		if (!sortedUsers.isEmpty()) {
			Collections.sort(sortedUsers, new Comparator<User>() {
				@Override
				public int compare(User user1, User user2) {
					return user1.getName().compareTo(user2.getName());
				}
			});
		}

		return sortedUsers;
	}
	
	/**
	 * @author louca
	 * @param domino used to create dominoInKingdom, domino status will be set to ...Preplaced
	 * @param kingdom where the domino is to be preplaced
	 * @return the dominoInKingdom created for the given domino and kingdom, preplaced at 0:0 with direction Up in the kingdom
	 */
	public static DominoInKingdom preplaceDomino(Domino domino, Kingdom kingdom) {
		DominoInKingdom dominoInKingdom = new DominoInKingdom(0, 0, kingdom, domino);
		verifyDominoPreplacement(dominoInKingdom); // should always be erroneously preplaced, since it will be overlapping with the castle placed at 0:0
		return dominoInKingdom;
	}

	/**
	 * @author Louca
	 * @param kingdomino
	 * @param name
	 * @return
	 * @throws InvalidInputException
	 */
	public static int getPlayedGamesForUserByName(Kingdomino kingdomino, String name) {
		return User.getWithName(name).getPlayedGames();
		// TODO intercept the exception thrown by get with name and convert to InvalidInputException
	}
	
	/**
	 * @author Louca
	 * @param kingdomino
	 * @param name
	 * @return
	 * @throws InvalidInputException
	 */
	public static int getWonGamesForUserByName(Kingdomino kingdomino, String name) {
		return User.getWithName(name).getWonGames();
		// TODO intercept the exception thrown by get with name and convert to InvalidInputException
	}
	
	/**
	 * @author louca
	 * @param game
	 * @return the number of kings in play for the given game, based on the number of players in the given game, which dictates the number of dominos in the drafts
	 * @throws InvalidInputException 
	 * @see https://mycourses2.mcgill.ca/d2l/le/lessons/433764/topics/4798537 "three for a 3 players game and four for a 2 or 4 players game"
	 */
	private static int getNumberOfKingsInPlay(Game game) throws InvalidInputException {
		int numberOfPlayers = game.getPlayers().size();
		if (numberOfPlayers == 3) {
			return 3;
		} else if (numberOfPlayers == 2 || numberOfPlayers == 4) {
			return 4;
		}
		throw new InvalidInputException("game did not have between 2, 3, or 4 players.");
	}

	/**
	 * @author Louca
	 * @author Ellina
	 * @param kingdomino
	 * @param game
	 * @return game
	 * @throws InvalidInputException
	 * @versio 2.0
	 */
	public static Game createNextDraft(Game game) throws InvalidInputException {
		//Set game.setNextDraft(null) before calling
		if (game.getNextDraft() != null) {
			throw new InvalidInputException("there is already a next draft.");
		}

		Draft nextDraft = new Draft(DraftStatus.FaceDown, game);
		game.setNextDraft(nextDraft);
		List <Domino> dominos = game.getAllDominos();
		Integer size = dominos.size();
		for (int i=0; i<getNumberOfKingsInPlay(game); i++) {
			if(game.getTopDominoInPile() == game.getAllDominos().get(size - 1)) { //check if pile is empty
				if (i == (game.getNumberOfPlayers() - 1)) {//means we have a full draft, might change when handle 2 players
					nextDraft.addIdSortedDomino(game.getTopDominoInPile());
					return game;
				}else { //if we don't have enough dominos for a full draft
					nextDraft = null;
					return game;
				}
				
			}			
			nextDraft.addIdSortedDomino(game.getTopDominoInPile()); // first check if there are 4 more left in pile (i.e. there is 1 more left at least)
			
			Integer index = getDominoIndexInList(game.getTopDominoInPile(),dominos);
			game.getTopDominoInPile().setNextDomino(game.getAllDomino(index + 1));
			
			game.setTopDominoInPile(game.getTopDominoInPile().getNextDomino()); //move top domino in pile to next in pile
		}

		return game;
	}

	
	/**
	 * @author Ellina
	 * @param Domino
	 * @param List of dominos
	 * @return Index of domino in the list of dominos
	 */
	private static Integer getDominoIndexInList(Domino d, List<Domino> dominos) {
		Integer index = null;
		for(int e = 0; e < dominos.size(); e++) {
			if(d == dominos.get(e)) {
				index = e;
				break;
			}
		}
		return index;

	}

	/**
	 * @author Louca
	 * @author Ellina
	 * @param kingdomino
	 * @param game
	 * @return game
	 * @throws InvalidInputException
	 * @versio 2.0
	 */
	public static Game revealNextDraft(Game game) throws InvalidInputException {
		if (game.getCurrentDraft() == null) {
			throw new InvalidInputException("there is no current draft.");
		}
		Draft nextDraft = game.getNextDraft();
		if (nextDraft == null) {
			throw new InvalidInputException("there is no next draft to reveal.");
		}
		if (nextDraft.getDraftStatus() != DraftStatus.Sorted) {
			throw new InvalidInputException("next draft is not sorted.");
		}

		game.setCurrentDraft(nextDraft);
		game.getCurrentDraft().setDraftStatus(DraftStatus.FaceUp);

		return game;
	}

	public static Game createAndSortFirstDraft(Game game) throws InvalidInputException {
		if (game.getCurrentDraft() != null) {
			throw new InvalidInputException("there is already a current draft.");
		}
		if (game.getNextDraft() != null) {
			throw new InvalidInputException("there is already a next draft.");
		}
		
		Draft currentDraft = new Draft(DraftStatus.FaceDown, game);
		game.setTopDominoInPile(game.getAllDominos().get(0));
		for (int i=0; i<getNumberOfKingsInPlay(game); i++) {
			Domino topDominoInPile = game.getTopDominoInPile();
			
			currentDraft.addIdSortedDomino(topDominoInPile);
			topDominoInPile.setStatus(DominoStatus.InCurrentDraft);
			
			game.setTopDominoInPile(game.getAllDominos().get(game.indexOfAllDomino(topDominoInPile) + 1));
		}

		game.setCurrentDraft(currentDraft);

		// sortFirstDraft(game);
		// first, sort the current (first) draft
		
		// next, make a copy of the list of all dominos in the current draft
		List<Domino> sortedDominos = new ArrayList<Domino>(currentDraft.getIdSortedDominos());
		// and sort that copy
		if (!sortedDominos.isEmpty()) {
			Collections.sort(sortedDominos, new Comparator<Domino>() {
				@Override
				public int compare(Domino domino1, Domino domino2) {
					return domino1.getId() - domino2.getId();
				}
			});
		}
		
		// lastly, reorder the dominos in the current draft according to their order in the sorted copy of all dominos in the current draft
		int sortedIndex = 0;
		for (Domino domino : sortedDominos) {
			currentDraft.addOrMoveIdSortedDominoAt(domino, sortedIndex); // will not add, since the dominos in the current draft and its copy are the same objects, but rather will move to the sorted index
			sortedIndex++;
		}
		
		currentDraft.setDraftStatus(DraftStatus.Sorted);

		return game;
	}
	
	public static Game revealFirstDraft(Game game) throws InvalidInputException {
		if (game.getCurrentDraft() == null) {
			throw new InvalidInputException("There is no current draft.");
		}
		
		if (game.getCurrentDraft().getDraftStatus() != DraftStatus.Sorted) {
			throw new InvalidInputException("next draft is not sorted.");
		}
		
		Draft currentDraft = game.getCurrentDraft();
		currentDraft.setDraftStatus(DraftStatus.FaceUp); // reveal

		return game;
	}

	/**
	 * @author Louca
	 * @author Ellina
	 * @param kingdomino
	 * @param game
	 * @return game
	 * @throws InvalidInputException
	 * @version 2.0
	 */
	public static Game sortNextDraft(Game game) throws InvalidInputException {
		Draft nextDraft = game.getNextDraft();

		if (game.getCurrentDraft() == null || nextDraft == null) {
			throw new InvalidInputException("there is no current or there is no next draft.");
		}

		if (nextDraft.getDraftStatus() != DraftStatus.FaceDown) {
			throw new InvalidInputException("the next draft is already sorted or is already face up.");
		}

		List<Domino> unsortedDominos = nextDraft.getIdSortedDominos();
		unsortedDominos = new ArrayList<Domino> (unsortedDominos);
		//remove out of order dominos
		// try to add back using addOrMoveIdSortedDominoAt
		for(int i = 0; i < unsortedDominos.size(); i++) {
			nextDraft.removeIdSortedDomino(unsortedDominos.get(i));
		}

		//sort dominos
		Collections.sort(unsortedDominos, new Comparator<Domino>() {
			@Override
			public int compare(Domino domino1, Domino domino2) {
				return domino1.getId() - domino2.getId();
			}
		});

		//add dominos back in order
		for(int i = 0; i < unsortedDominos.size(); i++) {
			nextDraft.addIdSortedDomino(unsortedDominos.get(i));
		}

		nextDraft.setDraftStatus(DraftStatus.Sorted);

		return game;
	}

	/**
	 * @author Louca
	 * @param game
	 * @return
	 * @throws InvalidInputException
	 */
	public static Game startANewGame(Game game) throws InvalidInputException {
		for (Player player : game.getPlayers()) {
			Kingdom kingdom = new Kingdom(player);
			player.setKingdom(kingdom);
			kingdom.addTerritory(new Castle(0, 0, kingdom, player));
			player.setBonusScore(0);
			player.setPropertyScore(0);
		}
		createAllDominoes(game);
		shuffleDominos2(game);
		
		for (int i=0; i< (48 - game.getMaxPileSize()); i++) { // this only ever iterates if there are fewer than 4 players
			Domino domino = game.getTopDominoInPile(); // is set by shuffleDominos
			domino.setStatus(DominoStatus.Excluded); // then set the domino status of the top (48 - max pile size) to excluded 
			game.setTopDominoInPile(domino.getNextDomino()); // traverse LL via next association
		}
		
		// top domino in pile is now set to the (48 - max pile size)th domino in pile from the top
		
		game.getKingdomino().setCurrentGame(game); // probably already done

		return game;
	}


	
	public static boolean HasTieBreak(Game game) {
		List<Player> allPlayers = game.getPlayers();
		for (Player player : allPlayers) {
			calculatePlayerScore1(player);
		}
		
		for (Player player : allPlayers) {
		int repeat =0;

			for (Player player2 : allPlayers) {
				if(player.getTotalScore()==player2.getTotalScore()) repeat++;
			}
			if(repeat>=2) {
				return true;
				}
		}
		return false;
	}
	
	/**
	 * @author Louca
	 * @param game
	 * @return a list of all players sorted by increasing total score (has side effect of sorting the list of players of game instance passed)
	 * @throws InvalidInputException if there was a tie between at least two players
	 */
	public static List<Player> calculateRanking(Game game) throws InvalidInputException {
		List<Player> allPlayers = game.getPlayers();

		List<Integer> allPlayerTotalScores = new ArrayList<Integer>();
		for (Player player : allPlayers) {
			calculatePlayerScore1(player);
			allPlayerTotalScores.add(player.getTotalScore());
		}

//		for (Player player : allPlayers) {
//			int repeat =0;
//
//			for (Player player2 : allPlayers) {
//				if(player.getTotalScore()==player2.getTotalScore()) repeat++;
//			}
//			if(repeat>=2)throw new InvalidInputException("at least two players were tied in total score.");
//		}


		List<Player> rankedPlayers = new ArrayList<Player>(allPlayers);

		if (!rankedPlayers.isEmpty()) {
			Collections.sort(rankedPlayers, new Comparator<Player>() {
				@Override
				public int compare(Player player1, Player player2) {
					return player1.getTotalScore() - player2.getTotalScore(); // result is > 0 if p1 score > p2 score, and vice-versa
				}
			});

			//set the rank for each player
			int numberOfPlayers = game.getNumberOfPlayers();
			int rank = numberOfPlayers;
			for (int i=0; i<numberOfPlayers; i++) {
				rankedPlayers.get(i).setCurrentRanking(rank);
				rank--;
			}		
		}


		return rankedPlayers;
	}

	/**
	 * @author Louca
	 * @param game
	 * @return
	 * @throws InvalidInputException
	 * 
	 * probably unecessary
	 */
	public static List<PlayerColor> calculatePlayerColorRanking(Game game) throws InvalidInputException {
		List<PlayerColor> rankedPlayerColors = new ArrayList<PlayerColor>();

		for (Player player : calculateRanking(game)) {
			rankedPlayerColors.add(player.getColor());
		}

		return rankedPlayerColors;
	}

	/**
	 * @author Elias Tamraz
	 * @return
	 */
	public static Game createGametoload () {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(9, kingdomino);

		Player p1 = new Player(game);
		Player p2 = new Player(game);
		Player p3 = new Player(game);
		Player p4 = new Player(game);

		Kingdom k1 = new Kingdom(p1);
		Kingdom k2 = new Kingdom(p2);
		Kingdom k3 = new Kingdom(p3);
		Kingdom k4 = new Kingdom(p4);

		kingdomino.setCurrentGame(game);
		createAllDominoes(game);
		return game;	
	}


	/**
	 * @author Ellina
	 * @param 
	 * @return list of dominos in order outside the context of a game
	 * 
	 */
	public static List<Domino> browseDominoPile(){
		List<Domino> orderedList = Collections.emptyList();
		orderedList = new ArrayList<>(orderedList);
		Domino d;

		for(int i = 1; i < 49; i++) {
			d = util.getDominoByIDNoGame(i);
			orderedList.add(d);
		}
		return orderedList;
	}

	/**
	 * @author Ellina
	 * @param terrain
	 * @return list of dominos by terrain type
	 * 
	 */
	public static List<Integer> browseDominoPileByTerrainType(TerrainType t){
		List<Domino> orderedList = browseDominoPile();
		List<Integer> listofids = Collections.emptyList();
		listofids = new ArrayList<>(listofids);

		for(int i = 0; i < orderedList.size(); i++) {
			if(orderedList.get(i).getLeftTile() == t) {
				listofids.add(orderedList.get(i).getId());
			}else if(orderedList.get(i).getRightTile() == t) {
				listofids.add(orderedList.get(i).getId());
			}
		}

		return listofids;
	}

	/**
	 * @author Ellina
	 * @param Integer id
	 * @return retrieve individual domino
	 * 
	 */
	public static Domino browseDominoPileById(Integer id) {
		return util.getDominoByIDNoGame(id);
	}
	
	/**
	 * @author elias
	 * @param game
	 */
	 static List<Player> l = new ArrayList<Player>();
	public static void rankPlayers(Game game) {
		List<Player> s = l;
		l.clear();
		Player p1 = getPlayerFromColor("pink");
		Player p2 = getPlayerFromColor("green");
		Player p3 =getPlayerFromColor("yellow");
		Player p4 =getPlayerFromColor("blue"); 
		
		 l.add(p1);
		 if(p2.getTotalScore()>p1.getTotalScore()) l.add(1, p2);
		 else l.add(0, p2);
		 int i=0;
		 for(Player p : l) {
			 
			 if(p.getTotalScore()>= p3.getTotalScore()) {
				 l.add(i, p3);
				 break;
			 }
			 i++;
			 
		 }if(i == l.size()) {
			 l.add(i, p3);
		 }
		 i=0;
		 for(Player p : l) {
			 
			 if(p.getTotalScore() >= p4.getTotalScore()) {
				 l.add(i, p4);
				 break;
			 }
			 i++;
			 
		 }if(i == l.size()) {
			 l.add(i, p4);
		 }
		 	l.get(3).setCurrentRanking(1);
			l.get(2).setCurrentRanking(2);
			l.get(1).setCurrentRanking(3);
			l.get(0).setCurrentRanking(4);
			int z=0;
			s = l;
			
				while(z<3) {
					 if(l.get(0).getTotalScore() ==  l.get(1).getTotalScore()){
						 ResolveTiebreakPropertySize(l,0,1); s = l;
					 }if(l.get(1).getTotalScore() ==  l.get(2).getTotalScore()){
						 ResolveTiebreakPropertySize(l,1,2);s = l;
					 }if(l.get(2).getTotalScore() ==  l.get(3).getTotalScore()){
						 ResolveTiebreakPropertySize(l,2,3);s = l;
					 }
					 z++;
				}	
			
			while(z>0) {
				 if(l.get(z).getCurrentRanking() <  l.get(z-1).getCurrentRanking()-1){
					for(int in = z-1; in>=0; in--) {
						l.get(in).setCurrentRanking(l.get(in).getCurrentRanking()-1);
					}
				 }
				 z--;
			}	
			
	}
	
	public static void ResolveTiebreakPropertySize (List<Player> list, int first, int second ) {
		List<Property> ps1 = list.get(first).getKingdom().getProperties();
		List<Property> ps2 = list.get(second).getKingdom().getProperties();
		int size1 = 0; int size2 =0;
		
		for (Property pt : ps1) {
			size1 = Math.max(size1, pt.getSize());
		}
		for (Property pt : ps2) {
			size2 = Math.max(size2, pt.getSize());
		}
		if (size1 < size2) {
			
			
		}else if( size1 == size2) {
			 
			ResolveTiebreakCrowns(list,first,second);
		}
		else {
			Player temp;
			temp = list.get(first);
			int rank = temp.getCurrentRanking();
			list.get(first).setCurrentRanking(list.get(second).getCurrentRanking());
			list.get(second).setCurrentRanking(rank);	
			Collections.swap(l, first, second);
		}
	}
	
	public static void ResolveTiebreakCrowns (List<Player> list, int first, int second ) {
		List<Property> ps1 = list.get(first).getKingdom().getProperties();
		List<Property> ps2 = list.get(second).getKingdom().getProperties();
		int crowns1 = 0; int crowns2 =0;
		
		for (Property pt : ps1) {
			crowns1 = Math.max(crowns1, pt.getCrowns());
		}
		for (Property pt : ps2) {
			crowns2 = Math.max(crowns2, pt.getCrowns());
		}
		if (crowns1 < crowns2) {
			
		}else if(crowns1 == crowns2) {
			Player temp;
			temp = list.get(second);
			int rank = temp.getCurrentRanking();
			list.get(first).setCurrentRanking(list.get(second).getCurrentRanking());
			
			int iterator = first-1;
			if(list.get(first).getCurrentRanking() != list.get(second).getCurrentRanking()) {
				while(iterator >= 0) {
					list.get(iterator).setCurrentRanking(list.get(iterator+1).getCurrentRanking()+1);
					iterator--;
				}
				
			}
		
		}
		else {
			Player temp;
			temp = list.get(first);
			int rank = temp.getCurrentRanking();
			list.get(first).setCurrentRanking(list.get(second).getCurrentRanking());
			list.get(second).setCurrentRanking(rank);	
			Collections.swap(l, first, second);
		}
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
	
	/**
	 * @author chenkua
	 */
	public static boolean hasMoreDraft(int draftNum) {
		if(draftNum == 12) {
			return false;			
		}
		return true;
		
	}
	
	public static void rankPlayers_3(Game game) {
		List<Player> s = l;
		l.clear();
		Player p1 = getPlayerFromColor("blue");
		Player p2 = getPlayerFromColor("green");
		Player p3 =getPlayerFromColor("yellow");
	//	Player p4 =getPlayerFromColor("pink"); 
		
		 l.add(p1);
		 if(p2.getTotalScore()>p1.getTotalScore()) l.add(1, p2);
		 else l.add(0, p2);
		 int i=0;
		 for(Player p : l) {
			 
			 if(p.getTotalScore()>= p3.getTotalScore()) {
				 l.add(i, p3);
				 break;
			 }
			 i++;
			 
		 }if(i == l.size()) {
			 l.add(i, p3);
		 }
		 i=0;
//		 for(Player p : l) {
//			 
//			 if(p.getTotalScore() >= p4.getTotalScore()) {
//				 l.add(i, p4);
//				 break;
//			 }
//			 i++;
//			 
//		 }if(i == l.size()) {
//			 l.add(i, p4);
//		 }
//		 	l.get(3).setCurrentRanking(1);
			l.get(2).setCurrentRanking(1);
			l.get(1).setCurrentRanking(2);
			l.get(0).setCurrentRanking(3);
			int z=0;
			s = l;
			
				while(z<2) {
					 if(l.get(0).getTotalScore() ==  l.get(1).getTotalScore()){
						 ResolveTiebreakPropertySize(l,0,1); s = l;
					 }if(l.get(1).getTotalScore() ==  l.get(2).getTotalScore()){
						 ResolveTiebreakPropertySize(l,1,2);s = l;
					 }
					 z++;
				}	
			
			while(z>0) {
				 if(l.get(z).getCurrentRanking() <  l.get(z-1).getCurrentRanking()-1){
					for(int in = z-1; in>=0; in--) {
						l.get(in).setCurrentRanking(l.get(in).getCurrentRanking()-1);
					}
				 }
				 z--;
			}	
			
	}
	
	public static int calculateScoreForPlayer(Player aPlayer) {
		int score=0;
		List<Property> property = aPlayer.getKingdom().getProperties();
		for(Property pt: property) {
			score+=pt.getScore();
		}
		aPlayer.setPropertyScore(score);
		return aPlayer.getTotalScore();
	}

	
}