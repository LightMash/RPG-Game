package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, totem;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2,
	guardUp,guardDown,guardLeft,guardRight;
	public BufferedImage image, image2, image3;
	
	public boolean collision = false;
	public Rectangle solidArea = new Rectangle(0,0, 48, 48);
	public Rectangle attackArea = new Rectangle(0,0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public Entity attacker;
	public Entity linkedEntity;
	

	//Dialogue
	public String dialogues[][] = new String[20][20];
	public int dialogueIndex = 0;
	public int dialogueSet = 0;
	
	//State
	public int worldX,worldY;
	public String direction ="down";
	public int spriteNum = 1;
	public boolean collisionOn = false;
	public boolean invincible = false;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	public boolean onPath = false;
	public boolean knockBack = false;
	public String knockBackDirection;
	public boolean guarding = false;
	public boolean transparent = false;
	public boolean offBalance = false;
	public Entity loot;
	public boolean opened = false;



	
	//Counter
	public int spriteCounter = 0;
	public int actionLockCounter = 0;
	public int invincibleCounter = 0;
	public int shotAvailableCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	int knockBackCounter = 0;
	public int guardCounter = 0;
	int offBalanceCounter = 0;
	
	
	//Character Status
	public int maxLife;
	public int life;
	public int defaultSpeed;
	public String name;
	public int speed;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public int motion1_duration;
	public int motion2_duration;
	public int maxMana;
	public int mana;
	public int ammo;
	public Entity currentWeapon;
	public Entity currentShield;
	public Entity currentLight;
	public Projectile projectile;
	
	//Item Attributes
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;
	public int value;
	public int price;
	public int knockBackPower;
	public boolean stackable = false;
	public int amount = 1;
	public int lightRadius;
	//Types
	public int type;// 0 = Player, 1 = NPC, 2 = Monster
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_pickupOnly = 7;
	public final int type_obstacle = 8;
	public final int type_light = 9;
	public final int type_pickaxe = 10;
	
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public int getLeftX() {
		return worldX + solidArea.x;
	}
	public int getRightX() {
		return worldX + solidArea.x + solidArea.width;
	}
	public int getTopY() {
		return worldY + solidArea.y;
	}
	public int getBottomY() {
		return worldY + solidArea.y + solidArea.height;
	}
	public int getCol() {
		return (worldX + solidArea.x) / gp.tileSize;
	}
	public int getRow() {
		return (worldY + solidArea.y) / gp.tileSize;
	}
	public int getXDistance(Entity target) {
		int xDistance = Math.abs(worldX - target.worldX);
		return xDistance;
	}
	public int getYDistance(Entity target) {
		int yDistance = Math.abs(worldY - target.worldY);
		return yDistance;
	}
	public int getTileDistance(Entity target) {
		int tileDistance = (getXDistance(target) + getYDistance(target)) / gp.tileSize;
		return tileDistance;
	}
	public int getGoalCol(Entity target) {
		int goalCol = (target.worldX + target.solidArea.x) /gp.tileSize;
		return goalCol;
	}
	public int getGoalRow(Entity target) {
		int goalRow = (target.worldY + target.solidArea.y) /gp.tileSize;
		return goalRow;
	}
	public void resetCounter() {
		spriteCounter = 0;
		actionLockCounter = 0;
		invincibleCounter = 0;
		shotAvailableCounter = 0;
		dyingCounter = 0;
		hpBarCounter = 0;
		knockBackCounter = 0;
		guardCounter = 0;
		offBalanceCounter = 0;
	}
	public void setLoot(Entity loot) {}
	public void setAction() {}
	public void move(String direction) {}
	public void damageReaction() {}
	public void speak() {}
	public void facePlayer() {
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}
	public void startDialogue(Entity entity, int setNum) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.npc = entity;
		dialogueSet = setNum;
	}
	public void interact() {
		
	}
	public boolean use(Entity entity) {
		return false;
	}
	public void checkDrop() {
		
	}
	public void dropItem(Entity droppedItem) {
		
		for(int i = 0; i < gp.obj[1].length; i++) {
			if(gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX; //Dead Monster
				gp.obj[gp.currentMap][i].worldY = worldY;	//Dead Monster
				break;
			}
		}
	}
	public Color getParticleColor() {
		Color color  = null;
		return color;
	}
	public int getParticleSize() {
		int size = 0; 
		return size;
	}
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}
	public void generateParticle(Entity generator, Entity target) {
		
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
		}
	public void checkCollision() {
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		
		if(this.type == type_monster && contactPlayer == true) {
			damagePlayer(attack);
		}
	}
	public void update() {
		
		if(knockBack == true) {
			
			checkCollision();
			
			if(collisionOn == true) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
			else if(collisionOn == false) {
				switch(knockBackDirection) {
				case "up":worldY -= speed;break;
				case "down":worldY += speed;break;	
				case "left":worldX -= speed;break;
				case "right":worldX += speed;break;
					
				}
			}
			
			knockBackCounter++;
			if(knockBackCounter == 10) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
		}
		else if(attacking == true) {
			attacking();
		}
		else {
			setAction();
			checkCollision();
			
			if(collisionOn == false) {
				switch(direction) {
				case "up":worldY -= speed;break;
				case "down":worldY += speed;break;	
				case "left":worldX -= speed;break;
				case "right":worldX += speed;break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 24) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			
			}
		}
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
		if(offBalance == true) {
			offBalanceCounter++;
			if(offBalanceCounter > 60) {
				offBalance = false;
				offBalanceCounter = 0;
			}
		}
		
	}
	public void checkStartChasingOrNot(Entity target, int distance, int rate) {
		
		if(getTileDistance(target) < distance) {
			int i = new Random().nextInt(rate);
			if(i == 0) {
				onPath = true;
			}
		}
	}
	public void checkStopChasingOrNot(Entity target, int distance, int rate) {
		
		if(getTileDistance(target) > distance) {
			int i = new Random().nextInt(rate);
			if(i == 0) {
				onPath = false;
			}
		}
	}
	public void checkShootOrNot(int rate, int shotInterval) {
		
		int i = new Random().nextInt(rate);
		if(i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval) {
			
			projectile.set(worldX, worldY, direction, true, this);

			
			//Check Vacancy
			for(int ii = 0; ii < gp.projectile[1].length; ii++) {
				if(gp.projectile[gp.currentMap][ii] == null) {
					gp.projectile[gp.currentMap][ii] = projectile;
					break;
				}
			}
			
			shotAvailableCounter = 0;
		}
	}
	public void getRandomDirection(int interval) {
		
		actionLockCounter++;
		
		if(actionLockCounter == interval) {
			
			Random random = new Random();
			int i = random.nextInt(100) + 1; // Picup a number from 1 to 100
			
			if(i <= 25) {direction = "up";}
			if(i > 25 && i <= 50) {direction = "down";}
			if(i > 50 && i <= 75) {direction = "left";}
			if(i > 75 && i <= 100) {direction = "right";}
			actionLockCounter = 0;
		}
	}
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= motion1_duration ) {
			spriteNum = 1;
		}
		if(spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
			spriteNum = 2;
			
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//Adjust player's worldX/Y for the attack area
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += gp.tileSize; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += gp.tileSize; break;
			}
			
			//Attack Area becomes solid area
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			if(type == type_monster) {
				if(gp.cChecker.checkPlayer(this) == true) {
					damagePlayer(attack);
				}
			}
			else {
				
				//Check monster collision with the updated world X/Y and solidArea
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);
				
				int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
				gp.player.damageInteractiveTile(iTileIndex);
				
				int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
				gp.player.damageProjectile(projectileIndex);
			}
			
			
			
			//After checking collision, restore the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter > motion2_duration ) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	public void checkAttackOrNot(int rate, int straight, int horizontal) {
		
		boolean targetInRange = false;
		int xDis = getXDistance(gp.player);
		int yDis = getYDistance(gp.player);
		
		switch(direction) {
		case "up":
			if(gp.player.worldY < worldY && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "down":
			if(gp.player.worldY > worldY && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "left":
			if(gp.player.worldX < worldX && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "right":
			if(gp.player.worldX > worldX && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}
			break;
		}
		
		if(targetInRange == true) {
			//Check if it initates an attack
			int i = new Random().nextInt(rate);
			if(i == 0) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
				shotAvailableCounter = 0;
			}
		}
	}
	public String getOppositeDirection(String direction) {
		
		String oppositeDirection = "";
		switch(direction) {
		case"up": oppositeDirection = "down";break;
		case"down":oppositeDirection = "up";break;
		case"left":oppositeDirection = "right";break;
		case"right":oppositeDirection = "left";break;
		}
		return oppositeDirection;
	}
	public void damagePlayer(int attack) {
		if(gp.player.invincible == false) {
			
			int damage = attack - gp.player.defense;
			
			//GEt an opposite direction of this attacker
			String canGuardDirection = getOppositeDirection(direction);
			
			if(gp.player.guarding == true && gp.player.direction.equals(canGuardDirection)) {
				
				//Parry
				if(gp.player.guardCounter < 15) {
					damage = 0;
					gp.playSE(16);
					setKnockBack(this,gp.player,knockBackPower);
					offBalance = true;
					spriteCounter =- 60;
				}else {
					//Normal gaurd
					damage /= 3;
					gp.playSE(15);
				}
			}
			else {
				//We can give damage
				gp.playSE(6);
				if(damage < 1) {
					damage = 1;
				}
			}
			
			if(damage != 0) {
				gp.player.transparent = true;
			}
			setKnockBack(gp.player,this,knockBackPower);
			gp.player.life -= damage;
			gp.player.invincible = true;
			
		}
	}
	public void setKnockBack(Entity target,Entity attacker, int knockBackPower) {
		
		this.attacker = attacker;
		target.knockBackDirection = attacker.direction;
		target.speed += knockBackPower;
		target.knockBack = true;
		
	}
	public void draw(Graphics2D g2) {
		
			BufferedImage image = null;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			
			//So it doesn't render the whole world constantly
			if(worldX  + gp.tileSize> gp.player.worldX - gp.player.screenX && 
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				
				int tempScreenX = screenX;
				int tempScreenY = screenY;
				
				switch(direction) {
				case "up":
					if(attacking == false) {
						if(spriteNum == 1) {image = up1;}
						if(spriteNum == 2) {image = up2;}
				}	
					if(attacking == true) {
						tempScreenY = screenY - up1.getHeight();
						if(spriteNum == 1) {image = attackUp1;}
						if(spriteNum == 2) {image = attackUp2;}
				}
					break;
				case "down":
					if(attacking == false) {
						if(spriteNum == 1) {image = down1;}
						if(spriteNum == 2) {image = down2;}
				}
					if(attacking == true) {
						if(spriteNum == 1) {image = attackDown1;}
						if(spriteNum == 2) {image = attackDown2;}
				}
					break;
				case "left":
					if(attacking == false) {
						if(spriteNum == 1) {image = left1;}
						if(spriteNum == 2) {image = left2;}
				}
					if(attacking == true) {
						tempScreenX = screenX - left1.getWidth();
						if(spriteNum == 1) {image = attackLeft1;}
						if(spriteNum == 2) {image = attackLeft2;}
				}
					break;
				case "right":
					if(attacking == false) {
						if(spriteNum == 1) {image = right1;}
						if(spriteNum == 2) {image = right2;}
				}	
					if(attacking == true) {
						if(spriteNum == 1) {image = attackRight1;}
						if(spriteNum == 2) {image = attackRight2;}
				}
					break;
				}
				
				//Monster HP bar
				if(type == 2 && hpBarOn == true) {
					
					double oneScale = (double)gp.tileSize / maxLife;
					double hpBarValue = oneScale * life;
					
					
					g2.setColor(new Color(35, 35, 35));
					g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
					
					g2.setColor(new Color(255, 0 , 30));
					g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
					
					hpBarCounter++;
					
					if(hpBarCounter > 600) {
						hpBarCounter = 0;
						hpBarOn = false;
					}
					
				}	
				
				
				if(invincible == true) {
					hpBarOn = true;
					hpBarCounter = 0;
					changeAlpha(g2, 0.4F);
				}
				if(dying == true) {
					dyingAnimation(g2);
				}
			
				
				g2.drawImage(image, tempScreenX, tempScreenY,null);
				
				changeAlpha(g2, 1F);
			}
	}
	public void dyingAnimation(Graphics2D g2) {
		
		dyingCounter++;
		
		int i = 5;
		
		if(dyingCounter <= i) {changeAlpha(g2, 0f);}
		if(dyingCounter > i && dyingCounter >= i * 2) {changeAlpha(g2, 1f);}
		if(dyingCounter > i * 2 && dyingCounter >= i * 3) {changeAlpha(g2, 0f);}
		if(dyingCounter > i * 3 && dyingCounter >= i * 4) {changeAlpha(g2, 1f);}
		if(dyingCounter > i * 4 && dyingCounter >= i * 5) {changeAlpha(g2, 0f);}
		if(dyingCounter > i * 5 && dyingCounter >= i * 6) {changeAlpha(g2, 1f);}	
		if(dyingCounter > i * 6 && dyingCounter >= i * 7) {changeAlpha(g2, 0f);}
		if(dyingCounter > i * 7 && dyingCounter >= i * 8) {changeAlpha(g2, 1f);}
		if(dyingCounter > i * 8) {
			alive = false;
		}
	}
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	public BufferedImage setup (String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
	try {
		image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
		image = uTool.scaleImage(image, width, height);
		
		}catch(IOException e) {
			e.printStackTrace();
		}	
		return image;
	}
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = (worldX + solidArea.x) / gp.tileSize;
		int startRow = (worldY + solidArea.y) / gp.tileSize;
		
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		if(gp.pFinder.search() == true) {
			
			//Next worldX & worldY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
			//Entity's solidArea Position
			int enLeftX =  worldX + solidArea.x;
			int enRightX =  worldX + solidArea.x + solidArea.width;
			int enTopY =  worldY + solidArea.y;
			int enBottomY =  worldY + solidArea.y + solidArea.height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "up";
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			}
			else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				//Left or Right
				if(enLeftX > nextX) {
					direction = "left";
				}
				if(enLeftX < nextX) {
					direction = "right";
				}
			}
			else if(enTopY > nextY && enLeftX > nextX) {
				//Up or left
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(enTopY > nextY && enLeftX < nextX) {
				//Up or right
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}
			else if(enTopY < nextY && enLeftX > nextX) {
				//Down or Left
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(enTopY < nextY && enLeftX < nextX) {
				//Down or Left
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}
			
			//If reaches the goal, we stop the search
//			int nextCol = gp.pFinder.pathList.get(0).col;
//			int nextRow	= gp.pFinder.pathList.get(0).row;
//			if(nextCol == goalCol && nextRow == goalRow) {
//				onPath = false;
//			}
			
		}
	}
	public int getDetected(Entity user, Entity target[][], String targetName) {
		
		int index = 999;
		
		//Check the surrounding object
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();
		
		switch(user.direction) {
		case "up": nextWorldY = user.getTopY()- gp.player.speed; break;		
		case "down": nextWorldY = user.getBottomY()+ gp.player.speed; break;	
		case "left": nextWorldX = user.getLeftX()- gp.player.speed; break;
		case "right": nextWorldX = user.getRightX()+ gp.player.speed; break;	
		}
		int col = nextWorldX / gp.tileSize;
		int row = nextWorldY / gp.tileSize;
		
		for(int i = 0; i < target[1].length; i++) {
			if(target[gp.currentMap][i] != null) {
				if(target[gp.currentMap][i].getCol() == col && target[gp.currentMap][i].getRow() == row && target[gp.currentMap][i].name.equals(targetName) ) {
					
					index = i;
					break;
				}
			}
		}
		return index;
	}
}
