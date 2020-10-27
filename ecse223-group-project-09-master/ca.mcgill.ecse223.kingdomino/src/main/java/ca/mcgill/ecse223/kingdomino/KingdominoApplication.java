  
package ca.mcgill.ecse223.kingdomino;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.view.MainPage;


public class KingdominoApplication implements Runnable {
   
	private static Game currentGame;
	private static Kingdomino kingdomino;
	
	private static MainPage m;
	
	
	
	public static MainPage getM() {
		return m;
	}
	public static void setM(MainPage m) {
		KingdominoApplication.m = m;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException  {
		
		KingdominoApplication k1 = new KingdominoApplication();
    	Thread thread1 = new Thread(k1, "1");
 
    	thread1.start();
		    
		
	}
    public static Kingdomino getKingdomino() {
    	if (kingdomino ==null) {
    		kingdomino = new Kingdomino();
    	}
    	
    	
    	return kingdomino;
    }
    
    public static void setCurrentGame(Game aGame){
    	
        currentGame = aGame;
    }
    /**
     * @deprecated 
     * has unexpected effects, behaves differently then KingdominoApplication.getKingdomino().getCurrentGame()
     * for example in util.getDominoById
     * @return
     */
    public static Game getCurrentGame(){
    	if (currentGame ==null) {
    		currentGame = new Game(9, kingdomino);
    	}
        return currentGame;
    }

   

	public static void setKingdomino(Kingdomino newKingdomino) {
		// TODO Auto-generated method stub
		kingdomino = newKingdomino;
		
	}
	

	private  static Domino getdominoByID(Integer id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
		
	private static void addDefaultUsersAndPlayers(Game game) {
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
	private static void createAllDominoes(Game game) {
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
	
	private static  DirectionKind getDirection(String dir) {
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
	
	@Override
	public void run() {
		if (m==null) m = new MainPage();
		long time = System.currentTimeMillis();
		while(true) {
			//System.out.println(Thread.currentThread().getName()+" is running......");
			time = System.currentTimeMillis();
		}
	}

}
