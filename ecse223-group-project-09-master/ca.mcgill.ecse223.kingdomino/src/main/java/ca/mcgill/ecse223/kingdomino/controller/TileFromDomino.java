
package ca.mcgill.ecse223.kingdomino.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223 .kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;


/** This class is created to solve property problem
 * @author Chen
 * @version 1
 * 
 */
public class TileFromDomino {

	private TerrainType type;
	private int id;
	private int crown;
	private int x;
	private int y;
	private boolean isVisited;

	public TileFromDomino(int x, int y, int crown ,int id, TerrainType type) {
		this.x=x;
		this.y=y;
		this.crown = crown;
		this.id = id;
		this.type = type;
		isVisited=false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCrwon() {
		return crown;
	}

	public int getID() {
		return id;
	}

	public TerrainType getTerrainType() {
		return type;
	}

	
	
	/** break down dominos into tiles
	 * @author Chen
	 * @param List of dominoInKingdom
	 * @return List of TileFromDomino
	 */
	public static List<TileFromDomino> transToTile(List<DominoInKingdom> dominoInKingdoms){
		List<TileFromDomino> totalTileOfKingdom = new ArrayList<TileFromDomino>();

		for(DominoInKingdom dIK: dominoInKingdoms) {
			int ID = dIK.getDomino().getId();
			int LeftX = dIK.getX();
			int LeftY = dIK.getY();
			int[] right = KingDominoController.getRightTileCoordinates(dIK);
			int RightX = right[0];
			int RightY = right[1];
			int numCrowns = dIK.getDomino().getRightCrown();
			TerrainType Left = dIK.getDomino().getLeftTile();
			TerrainType	Right = dIK.getDomino().getRightTile();

			TileFromDomino leftTile = new TileFromDomino(LeftX,LeftY,0,ID,Left);
			TileFromDomino rightTile = new TileFromDomino(RightX,RightY,numCrowns,ID,Right);

			totalTileOfKingdom.add(leftTile);
			totalTileOfKingdom.add(rightTile);
		}
		return totalTileOfKingdom;
	}

	/** input the position, and get the tile at that position
	 * @author Chen
	 * @param x,y, List of tile
	 * @return TileFromDomino
	 */
	public static TileFromDomino getTileFromPostion(int x,int y,List<TileFromDomino> totalTile) {

		for(TileFromDomino tFD: totalTile ) {
			if(tFD.getX()==x && tFD.getY()==y) return tFD;
		}
		return null;
	}


	/** This method connect kingdom and tile properties
	 * @author Chen
	 * @param kingdom
	 * @return List of property
	 */
	public static List<List<TileFromDomino>> IdentifyProperties(Kingdom kd) {

		// get all dominoInKingdom
		List<DominoInKingdom> dIK = KingDominoController.getAllDominoInKingdom(kd);
		List<TileFromDomino> tiles = TileFromDomino.transToTile(dIK);
		List<List<TileFromDomino>> total = new ArrayList<List<TileFromDomino>>();

		total = createProperty(tiles);

		return total;
	}

	
	/** graph traversal using stack
	 * @author Chen
	 * @param tile
	 * @param list of tile
	 */
	public static List<TileFromDomino> buildProerty(TileFromDomino tile, List<TileFromDomino> Tiles){

		List<TileFromDomino> tiles = new ArrayList<TileFromDomino>();
		TileFromDomino temp;
		TerrainType type = tile.getTerrainType(); 

		if(tile.isVisited==false) {
			tile.isVisited =true;
			Stack<TileFromDomino> stack = new Stack<TileFromDomino>(); 
			stack.push(tile);	
			
			while(!stack.isEmpty()) {
				temp = stack.pop();
				tiles.add(temp);
				//tile down			
				if(!(getTileFromPostion(temp.x,temp.y-1,Tiles)==null) &&((getTileFromPostion(temp.x,temp.y-1,Tiles).getTerrainType()==type)) 
						&&(!getTileFromPostion(temp.x,temp.y-1,Tiles).isVisited)) 
				{
					getTileFromPostion(temp.x,temp.y-1,Tiles).isVisited=true;
					stack.push(getTileFromPostion(temp.x,temp.y-1,Tiles));
				}
				//tile up
				if(!(getTileFromPostion(temp.x,temp.y+1,Tiles)==null) && (getTileFromPostion(temp.x,temp.y+1,Tiles).getTerrainType()==type)&&
						(!getTileFromPostion(temp.x,temp.y+1,Tiles).isVisited)) {
					getTileFromPostion(temp.x,temp.y+1,Tiles).isVisited=true;
					stack.push(getTileFromPostion(temp.x,temp.y+1,Tiles));
				}
				//tile left  
				if(!(getTileFromPostion(temp.x-1,temp.y,Tiles)==null) && (getTileFromPostion(temp.x-1,temp.y,Tiles).getTerrainType()==type)&&
						(!getTileFromPostion(temp.x-1,temp.y,Tiles).isVisited)) {
					getTileFromPostion(temp.x-1,temp.y,Tiles).isVisited=true;
					stack.push(getTileFromPostion(temp.x-1,temp.y,Tiles));
				}
				//tile right 
				if(!(getTileFromPostion(temp.x+1,temp.y,Tiles)==null) && (getTileFromPostion(temp.x+1,temp.y,Tiles).getTerrainType()==type)&&(!getTileFromPostion(temp.x+1,temp.y,Tiles).isVisited)) {
					getTileFromPostion(temp.x+1,temp.y,Tiles).isVisited=true;
					stack.push(getTileFromPostion(temp.x+1,temp.y,Tiles));
				}
			}
		return tiles;	
		}else {
			return null;
		}
	
	}

	
	/** this method divide a list of tiles into tile properties and add it to the list of list
	 * @author Chen
	 * @param List of Tiles
	 */
	private static List<List<TileFromDomino>> createProperty(List<TileFromDomino> Tiles) {
		if(Tiles == null) return null;

		List<List<TileFromDomino>> listOfLisfOfTile = new ArrayList<List<TileFromDomino>>();
		List<TileFromDomino> tmp = new ArrayList<TileFromDomino>();

		for(TileFromDomino tile :Tiles) {
			tmp = buildProerty(tile, Tiles);
			if(!(tmp==null)) listOfLisfOfTile.add(tmp);
		}

		return listOfLisfOfTile;
	}
	public void setVisited(boolean vis) {
		this.isVisited = vis;
	}

	
	
	/** return the list of properties and build the properties
	 * @author Chen
	 * @param kingdom
	 * @return List<Property>
	 */
	public static List<Property> convertTilePropertyToProerty(Kingdom kd){
		List<List<TileFromDomino>> tileProperty = IdentifyProperties(kd);
		List<Property> properties = new ArrayList<Property>();
		
		for(List<TileFromDomino> LT: tileProperty) {
			int aSize = 0;
			int aCrown =0;
			Property tmp = new Property(kd);
			// change the tile to domino and add to the property
			for(TileFromDomino TFD: LT) {
				 TerrainType type = TFD.getTerrainType();
				 aSize=LT.size();
				int ID= TFD.getID();
				aCrown +=TFD.getCrwon();
				Domino domino = getdominoByID2(ID);
				if(!tmp.getIncludedDominos().contains(domino)) {
					tmp.addIncludedDomino(domino);
				}
				tmp.setPropertyType(type);
			}
			tmp.setCrowns(aCrown);
			tmp.setSize(aSize);
			tmp.setScore(aCrown*aSize);
			properties.add(tmp);
		}
		kd.setProperties(properties);
		return properties; 
	}
	
	
	/////////help method//////////
	
	private static Domino getdominoByID2(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
	
}