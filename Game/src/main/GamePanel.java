package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;
public class GamePanel extends JPanel implements Runnable{

	
	//Screen Settings
	final int originalTileSize = 16; //16 x 16 tile
	final int scale = 3; // Scaling so that the character on the screen is appropriate for current resolution
	
	public final int tileSize = originalTileSize * scale; // 48 x 48 tile
	
	
	//Size of screen
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	
	
	
	//World Settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int maxMap = 10;
	public int currentMap = 0;
	
	//For Full Screen
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;

	//FPS 
	int FPS = 60;
	//System
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	//Creates inGame clock (keeps the program running until stopped)
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	EnvironmentManager eManager = new EnvironmentManager(this);
	Map map = new Map(this);
	SaveLoad saveLoad = new SaveLoad(this);
	public EntityGenerator eGenerator = new EntityGenerator(this);
	Thread gameThread;
	//Entity and Object
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[maxMap][30];
	public Entity npc[][] = new Entity[maxMap][10];
	public Entity monster[][] = new Entity[maxMap][20];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	public Entity projectile[][] = new Entity[maxMap][20];
	//public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	
	//Game States
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;
	public final int sleepState = 9;
	public final int mapState = 10;
	
	
	//Area State
	public int currentArea;
	public final int outside = 50;
	public final int indoor = 51;
	public final int dungeon = 52;
	public int nextArea ;
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}

	public void setupGame() {
		
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		eManager.setup();
//		playMusic(0);
		gameState = titleState;
		currentArea = outside;
		
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB_PRE);
		g2 = (Graphics2D)tempScreen.getGraphics();
		
		if(fullScreenOn == true) {
			setFullScreen();
		}
		
	}
	public void resetGame(boolean restart) {
		
		player.setDefaultPosition();
		player.restoreStatus();
		aSetter.setNPC();
		aSetter.setMonster();
		player.resetCounter();
		
		if(restart == true) {
			
			player.setDefaultValues();
			aSetter.setObject();
			aSetter.setInteractiveTile();
			eManager.lighting.resetDay();
		}

	}
	public void setFullScreen() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		screenWidth2 = (int) width;
		screenHeight2 = (int) height;
	
	}
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

/*	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		
		while(gameThread != null) {
			
			//UPDATE: Update information such as character positions
			update();
			//DRAW: Draw the screen with the update information
			repaint();
			
		
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}
*/
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta =0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
			//UPDATE: Update information such as character positions
			update();
			//DRAW: Draw the screen with the update information
			drawToTempScreen();
			drawToScreen();
			delta--;
			drawCount++;
			}
			
			if(timer >= 1000000000) {
				drawCount = 0;
				timer = 0;
			}
			
		
			
			
			}
		}
		
		
	
	public void update() {
		
		if(gameState == playState) {
			player.update();
			
			for(int i = 0; i < npc[1].length; i++) {
				if(npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			for(int i = 0; i < monster[1].length; i++) {
				if(monster[currentMap][i] != null) {
					if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
						monster[currentMap][i].update();
					}
					if(monster[currentMap][i].alive == false) {
						monster[currentMap][i].checkDrop();
						monster[currentMap][i] = null;
					}
				}
			}
			for(int i = 0; i < projectile[1].length; i++) {
				if(projectile[currentMap][i] != null) {
					if(projectile[currentMap][i].alive == true) {
						projectile[currentMap][i].update();
					}
					if(projectile[currentMap][i].alive == false) {
						projectile[currentMap][i] = null;
					}
				}
			}
			for(int i = 0; i < particleList.size(); i++) {
				if(particleList.get(i) != null) {
					if(particleList.get(i).alive == true) {
						particleList.get(i).update();
					}
					if(particleList.get(i).alive == false) {
						particleList.remove(i);
					}
				}
			}	
			for(int i = 0; i < iTile[1].length; i++) {
				if(iTile[currentMap][i] != null) {
					iTile[currentMap][i].update();
				}
			}
			eManager.update();
		}
		if(gameState == pauseState) {
			
		}
		//if(gameState == optionState) {
		//	if(fullScreenOn == true) {
		//		setFullScreen();
		//	}
		//	else
		//		drawToTempScreen();
		//}
		
		
		
	}
	public void drawToTempScreen() {
		
	
		g2.clearRect(0, 0, screenWidth, screenHeight);
		//Debug
				long drawStart = 0;
				if(keyH.checkDrawTime == true) {
				drawStart = System.nanoTime();
				}
				
				
				//title Screen
				if(gameState == titleState) {
					ui.draw(g2);
				}
				//Map Screen
		
				//Others
				else {
					//Tile
					tileM.draw(g2);
					
					//Interactive Tiles
					for(int i =0; i < iTile[1].length; i++) {
						if(iTile[currentMap][i] != null) {
							iTile[currentMap][i].draw(g2);
						}
					}
					
					//Add Entities to the list
					entityList.add(player);
					
					for(int i = 0; i < npc[1].length; i++) {
						if(npc[currentMap][i] != null) {
							entityList.add(npc[currentMap][i]);
						}
					}
					for(int i = 0; i < obj[1].length; i++) {
						if(obj[currentMap][i] != null) {
							entityList.add(obj[currentMap][i]);
						}
					}
					for(int i = 0; i < monster[1].length; i++) {
						if(monster[currentMap][i] != null) {
							entityList.add(monster[currentMap][i]);
						}
					}
					for(int i = 0; i < projectile[1].length; i++) {
						if(projectile[currentMap][i] != null) {
							entityList.add(projectile[currentMap][i]);
						}
					}
					for(int i = 0; i < particleList.size(); i++) {
						if(particleList.get(i) != null) {
							entityList.add(particleList.get(i));
						}
					}
					
					//Sort
					Collections.sort(entityList,new Comparator<Entity>()  {
						
						
						public int compare(Entity e1, Entity e2) {
							
							int result = Integer.compare(e1.worldY, e2.worldY);
							return result;
						}
					});
					
					//Draw entities
					for(int i = 0; i < entityList.size(); i++) {
						entityList.get(i).draw(g2);
					}
					//Empty Entity List
					entityList.clear();
					
					//Environment
					eManager.draw(g2);
					
					//MiniMap
					map.drawMiniMap(g2);
					
					//UI
					ui.draw(g2);
				}
				if(gameState == mapState) {
					map.drawFullMapScreen(g2);
					
				}
				
			
				//Debug
				if(keyH.checkDrawTime == true) {
				long drawEnd = System.nanoTime();
				long passed = drawEnd - drawStart;
				g2.setColor(Color.white);
				g2.drawString("Draw Time: " + passed, 10, 400);
				System.out.println("Draw Time: " + passed);
			}
		
	}
	public void drawToScreen() {
		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		
		music.stop();
	}
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
		
	}
	public void changeArea() {
		
		if(nextArea != currentArea) {
			
			stopMusic();
			
			if(nextArea == outside) {
				playMusic(0);
			}
			if(nextArea == indoor) {
				playMusic(18);
			}
			if(nextArea == dungeon) {
				playMusic(19);
			}
			
			aSetter.setNPC();
		}
		
		currentArea = nextArea;
		aSetter.setMonster();
		
		
	}
}
