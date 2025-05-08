package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	
	public TileManager(GamePanel gp) {
		
		
		this.gp = gp;
		
		//Number of tile types
		tile = new Tile[60];
		mapTileNum = new int [gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		
		getTileImage();
		
		loadMap("/maps/worldmap.txt",0);
		loadMap("/maps/indoor01.txt",1);
		loadMap("/maps/dungeon01.txt",2);
		loadMap("/maps/dungeon02.txt",3);
		
		
	}
	
	public void getTileImage() {
		//PlaceHolders
//		setup(0, "000", false);
//		setup(1, "000", false);
//		setup(2, "000", false);
//		setup(3, "000", false);
//		setup(4, "000", true);
//		setup(5, "000", false);
//		setup(6, "000", false);
//		setup(7, "000", false);
//		setup(8, "000", false);
//		setup(9, "000", false);
//		setup(10, "000", false);
//		//Actual tiles
//		setup(11, "000", false);
//		setup(12, "001", false);
//		setup(13, "002", true);
//		setup(14, "003", true);
//		setup(15, "004", true);
//		setup(16, "005", true);
//		setup(17, "006", true);
//		setup(18, "007", true);
//		setup(19, "008", true);
//		setup(20, "009", true);
//		setup(21, "010", true);
//		setup(22, "011", true);
//		setup(23, "012", true);
//		setup(24, "013", true);
//		setup(25, "014", true);
//		setup(26, "015", true);
//		setup(27, "016", false);
//		setup(28, "017", false);
//		setup(29, "018", false);
//		setup(30, "019", false);
//		setup(31, "020", false);
//		setup(32, "021", false);
//		setup(33, "022", false);
//		setup(34, "023", false);
//		setup(35, "024", false);
//		setup(36, "025", false);
//		setup(37, "026", false);
//		setup(38, "027", false);
//		setup(39, "028", false);
//		setup(40, "029", false);
//		setup(41, "030", true);
//		setup(42, "031", true);
//		setup(43, "032", false);
//		setup(44, "033", false);
//		setup(45, "034", true);
//		setup(46, "035", true);
//		setup(47, "036", false);
//		setup(48, "037", false);
		
		setup(0, "000", false);
		setup(1, "001", false);
		setup(2, "002", false);
		setup(3, "003", false);
		setup(4, "004", false);
		setup(5, "005", false);
		setup(6, "006",	false);
		setup(7, "007", false);
		setup(8, "008", false);
		setup(9, "009", false);
		setup(10, "010", false);
		setup(11, "011", false);
		setup(12, "012", false);
		setup(13, "013", false);
		setup(14, "014", false);
		setup(15, "015", true);
		setup(16, "016", true);
		setup(17, "017", false);
		setup(18, "018", true);
		setup(19, "019", true);
		setup(20, "020", true);
		setup(21, "021", true);
		setup(22, "022", true);
		setup(23, "023", true);
		setup(24, "024", true);
		setup(25, "025", true);
		setup(26, "026", true);
		setup(27, "027", true);
		setup(28, "028", true);
		setup(29, "029", true);
		setup(30, "030", true);
		setup(31, "031", true);
		setup(32, "032", true);
		setup(33, "033", false);
		setup(34, "034", false);
		setup(35, "035", true);
		setup(36, "036", false);
		setup(37, "037", false);
		
	}
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
	try {
		tile[index] = new Tile();
		tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName +".png"));
		tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
		tile[index].collision = collision;
		
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	public void loadMap(String filePath, int map) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
				
			}
			br.close();
			
		}catch(Exception e) {
		
		}
	}
	public void draw(Graphics2D g2) {
		
		int worldCol =0;
		int worldRow =0;
		
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			
			//So it doesn't render the whole world constantly
			if(worldX  + gp.tileSize> gp.player.worldX - gp.player.screenX && 
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldCol++;
		
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			
			}
		}
	}
}
