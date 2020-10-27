package ca.mcgill.ecse223.kingdomino.controller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;


public class util {
	/**
	 * @author Angelina 
	 * This method get domino by its ID
	 * @param ID
	 * @throws IllegalArgumentException
	 */
	public static Domino getDominoByID(int ID) {
		for (Domino domino : KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos()) {
			if (domino.getId() == ID) {
				return domino;
			}
		}
		throw new IllegalArgumentException("Domino with ID " + Integer.toString(ID) + " not found.");
	}
	/**
	 * @author Angelina 
	 * This method get player by color
	 * @param game, color
	 * @throws IllegalArgumentException
	 */
	public static Player playerFromString(Game game, String color) {
		for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			if (player.getColor().toString().toLowerCase().equals(color.toLowerCase())) {
				return player;
			}
		}
		throw new IllegalArgumentException("player with the given color does not exist.");
	}
	
	public static PlayerColor playerColorFromString(String color) {
		for (PlayerColor playerColor : PlayerColor.values()) {
			if (playerColor.toString().toLowerCase().equals(color.toLowerCase())) {
				return playerColor;
			}
		}
		
		throw new IllegalArgumentException("string \"" + color + "\" was not a valid player color constant.");
	}

	/**
	 * @author Elias 
	 * This method converts letters 'U', 'D' .. to DirectionKind
	 * @param c
	 * @throws InvalidInputException 
	 */
	public static DirectionKind directionKindFromChar(char direction) {
		for (DirectionKind directionKind : DirectionKind.values()) {
			if (directionKind.toString().toLowerCase().charAt(0) == Character.toLowerCase(direction)) {
				return directionKind;
			}
		}

		throw new IllegalArgumentException("expected the given char to be one of U, D, L, R, not '" + Character.toString(direction) + "'.");
	}
	
	/**
	 * @author Chen
	 *
	 * must have toUpperCase to accommodate the method
	 *
	 * @return convert the string direction into the enum.direction 
	 * @throws InvalidInputException 
	 */
	public static DirectionKind directionKindFromString(String direction) {
		return directionKindFromChar(Character.toUpperCase(direction.charAt(0)));
	}
	
	public static TerrainType terrainTypeFromString(String str) {
		return 	terrainTypeFromChar(Character.toUpperCase(str.charAt(0)));
	}
	
	
	/**
	 * @author Elias
	 * This method converts DirectionKind to symbol char like 'U', 'D'
	 * @param directionKind
	 * @return
	 */
	public static String stringFromDirectionKind(DirectionKind direction) {
		if(direction == DirectionKind.Up) return "U";
		else if (direction == DirectionKind.Left) return "L";
		else if (direction == DirectionKind.Right)return "R";
		else if (direction == DirectionKind.Down) return "D";
		throw new IllegalArgumentException("Invalid direction: " + direction); // ideally we should throw exception (I'm gonna do it this way later)
	}

	public static TerrainType terrainTypeFromChar(char terrain) {
		switch (terrain) {
		case 'W':
			return TerrainType.WheatField;
		case 'F':
			return TerrainType.Forest;
		case 'M':
			return TerrainType.Mountain;
		case 'G':
			return TerrainType.Grass;
		case 'S':
			return TerrainType.Swamp;
		case 'L':
			return TerrainType.Lake;
		default:
			throw new IllegalArgumentException("Invalid terrain type: " + Character.toString(terrain));
		}
	}

	public DirectionKind getDirection(String dir) {
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
			throw new IllegalArgumentException("Invalid direction: " + dir);
		}
	}

	public DominoStatus getDominoStatus(String status) {
		switch (status) {
		case "inPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "inCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "inNextDraft":
			return DominoStatus.InNextDraft;
		case "erroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "correctlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new IllegalArgumentException("Invalid domino status: " + status);
		}
	}
	
	public static char charFromDirectionKind(DirectionKind direction) {
		return stringFromDirectionKind(direction).charAt(0);
	}
	
	public static DominoStatus dominoStatusFromString(String status) {
		for (DominoStatus dominoStatus : DominoStatus.values()) {
			if (dominoStatus.toString().toLowerCase().equals(status.toLowerCase())) {
				return dominoStatus;
			}
		}

		throw new IllegalArgumentException("string was not a valid domino status constant.");
	}
	
	public static List<Domino> createAllDominoesNoGame() {
		List <Domino> allDominos = Collections.emptyList();
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			allDominos = new ArrayList<>(allDominos);
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = util.terrainTypeFromChar(dominoString[1].charAt(0));
				TerrainType rightTerrain = util.terrainTypeFromChar(dominoString[2].charAt(0));
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				Game game = new Game(48, new Kingdomino());
				allDominos.add(new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
		return allDominos;
	}
	
	public static Domino getDominoByIDNoGame(int ID) {
		for (Domino domino : createAllDominoesNoGame()) {
			if (domino.getId() == ID) {
				return domino;
			}
		}
		throw new IllegalArgumentException("Domino with ID " + Integer.toString(ID) + " not found.");
	}
}

