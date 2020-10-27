package ca.mcgill.ecse223.kingdomino.view;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This class is the image of each domino. The number in first [] is the domino ID.
 * And in the second [], 0 indicates the left tile, 1 indicates the right tile.
 * 
 * @author chenkua
 *
 */

class DominoUI {
	private static ImageIcon d[][] = new ImageIcon[49][2];
	
	
	public static ImageIcon[][] getD() {
		
		d[0][0]=null;
		d[0][1]=null;
		
		d[1][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[1][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));;
		
		d[2][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[2][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		
		d[3][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[3][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		
		d[4][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[4][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		
		d[5][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[5][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		
		d[6][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		d[6][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		
		d[7][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		d[7][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		
		d[8][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		d[8][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));

		d[9][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		d[9][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		
		d[10][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		d[10][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		
		d[11][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		d[11][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		
		d[12][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp.png")));
		d[12][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp.png")));
		
		d[13][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[13][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		
		d[14][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[14][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		
		d[15][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[15][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		
		d[16][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[16][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp.png")));
		
		d[17][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[17][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		
		d[18][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[18][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		
		d[19][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[19][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat_1.png")));
		
		d[20][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		d[20][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat_1.png")));
		
		d[21][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		d[21][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat_1.png")));
		
		d[22][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp.png")));
		d[22][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat_1.png")));
		
		d[23][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Mountain.png")));
		d[23][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat_1.png")));
		
		d[24][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[24][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest_1.png")));
		
		d[25][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[25][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest_1.png")));
		
		d[26][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[26][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest_1.png")));
		
		d[27][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[27][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest_1.png")));
		
		d[28][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		d[28][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest_1.png")));
		
		d[29][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		d[29][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest_1.png")));
		
		d[30][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[30][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake_1.png")));
		
		d[31][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[31][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake_1.png")));
		
		d[32][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[32][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake_1.png")));
		
		d[33][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[33][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake_1.png")));
		
		d[34][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[34][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake_1.png")));
		
		d[35][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Forest.png")));
		d[35][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake_1.png")));
		
		d[36][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[36][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass_1.png")));
		
		d[37][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		d[37][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass_1.png")));
		
		d[38][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[38][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp_1.png")));
		
		d[39][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		d[39][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp_1.png")));
		
		d[40][0] = new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[40][1] = new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Mountain_1.png")));
				
		d[41][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[41][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass_2.png")));
		
		d[42][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Lake.png")));
		d[42][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass_2.png")));
		
		d[43][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[43][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp_2.png")));
		
		d[44][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Grass.png")));
		d[44][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp_2.png")));
		
		d[45][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[45][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Mountain_2.png")));
		
		d[46][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp.png")));
		d[46][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Mountain_2.png")));
		
		d[47][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Swamp.png")));
		d[47][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Mountain_2.png")));
		
		d[48][0]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Wheat.png")));
		d[48][1]=new ImageIcon(DominoUI.class.getResource(("/ca/mcgill/ecse223/kingdomino/resource/Mountain_3.png")));			
		
		return d;
	}

	
			
	
}


