package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class KeyHandler implements KeyListener {
	
	GamePanel gp;
	public boolean upPressed,downPressed,leftPressed,rightPressed, enterPressed, shotKeyPressed;
	//Debug
	
	 boolean checkDrawTime = false;
	 
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	 
	public void keyTyped(KeyEvent e) {		
	}

	
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		//Title State
		if(gp.gameState == gp.titleState) {
			titleState(code);	
		}		
		//PlayState
		else if (gp.gameState == gp.playState) {
			playState(code);	
		}
		//PauseState
		else if(gp.gameState == gp.pauseState) {
			pauseState(code);	
		}
	
		else if(gp.gameState == gp.dialogueState) {
			dialogueState(code);	
		}
		//Character State
		else if(gp.gameState == gp.characterState) {
			characterState(code);	
		}
		else if(gp.gameState == gp.optionState) {
			optionState(code);	
		}
		else if(gp.gameState == gp.gameOverState) {
			gameOverState(code);	
		}
		else if(gp.gameState == gp.tradeState) {
			tradeState(code);	
		}
		else if(gp.gameState == gp.mapState) {
			mapState(code);	
		}
		
	}
	public void titleState(int code) {
		
		if(gp.ui.titleScreenState == 0) {
			
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
				
				}
				if(gp.ui.commandNum == 1) {
					
				}
				if(gp.ui.commandNum == 2) {
					System.exit(0);
					}
			}
		}
		else if(gp.ui.titleScreenState == 1) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.player.level = 1;
					gp.player.maxLife = 6;
					gp.player.life= gp.player.maxLife;
					gp.player.strength = 2; // Damage
					gp.player.dexterity = 1; // Defense
					gp.player.attack = gp.player.getAttack();
					gp.player.defense = gp.player.getDefense();
					gp.playMusic (0);
					gp.gameState = gp.playState;
				}
				if(gp.ui.commandNum == 1) {
					gp.player.level = 1;
					gp.player.maxLife = 8;
					gp.player.life= gp.player.maxLife;
					gp.player.strength = 1; // Damage
					gp.player.dexterity = 2; // Defense
					gp.player.attack = gp.player.getAttack();
					gp.player.defense = gp.player.getDefense();
					gp.playMusic (0);
					gp.gameState = gp.playState;
				}
				if(gp.ui.commandNum == 2) {
					gp.player.level = 1;
					gp.player.maxLife = 6;
					gp.player.life= gp.player.maxLife;
					gp.player.strength = 1; // Damage
					gp.player.dexterity = 1; // Defense
					gp.player.maxMana = 6;
					gp.player.mana = gp.player.maxMana;
					gp.player.attack = gp.player.getAttack();
					gp.player.defense = gp.player.getDefense();
					gp.playMusic (0);
					gp.gameState = gp.playState;
				}
				if(gp.ui.commandNum == 3) {
					gp.ui.titleScreenState = 0;
					}
			}
			
			
		}
	}
	public void playState(int code) {
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = true;
		}
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionState;
		}
		if(code == KeyEvent.VK_M) {
			gp.gameState = gp.mapState;
		}
		if(code == KeyEvent.VK_X) {
			if(gp.map.miniMapOn == false) {
				gp.map.miniMapOn = true;
			}
			else {
				gp.map.miniMapOn = false;
			}
		}
	}
	public void pauseState(int code) {
		
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
	}
	public void dialogueState(int code) {
		
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	public void characterState(int code) {
		
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}

		if(code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}
		playerInventory(code);
	}
	public void optionState(int code) {
		
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		
		int maxCommandNum = 0;
		switch(gp.ui.subState) {
		case 0: maxCommandNum = 5;break;
		case 3: maxCommandNum = 1;break;
		}
		
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSE(9);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSE(9);
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					gp.se.checkVolume();
					gp.playSE(9);
				}
				
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.playSE(9);
				}
				if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					gp.playSE(9);
				}
			}
		}
	}	
	public void gameOverState(int code) {
		
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;

			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			gp.playSE(9);
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(9);
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry();
				gp.playMusic(0);
			}
			else if(gp.ui.commandNum == 1) {
				gp.ui.titleScreenState = 0;
				gp.gameState = gp.titleState;
				gp.restart();
			}
		}
	}
	public void tradeState(int code) {
		
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		
		if(gp.ui.subState == 0) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
				gp.playSE(9);
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
				gp.playSE(9);
				
			}
		}
		if(gp.ui.subState == 1) {
			npcInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		if(gp.ui.subState == 2) {
			playerInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		
	}
	public void mapState(int code) {
		
		if(code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
		}
	}
	public void playerInventory(int code) {
		
		if(code == KeyEvent.VK_W) {
			if(gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
				gp.playSE(9);
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.playerSlotCol != 0) {
				gp.ui.playerSlotCol--;
				gp.playSE(9);
				}
		}	
		if(code == KeyEvent.VK_S) {
			if(gp.ui.playerSlotRow != 3) {
				gp.ui.playerSlotRow++;
				gp.playSE(9);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.playerSlotCol != 4) {
				gp.ui.playerSlotCol++;
				gp.playSE(9);
			}
		}
	}
	public void npcInventory(int code) {
		
		if(code == KeyEvent.VK_W) {
			if(gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
				gp.playSE(9);
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.npcSlotCol != 0) {
				gp.ui.npcSlotCol--;
				gp.playSE(9);
				}
		}	
		if(code == KeyEvent.VK_S) {
			if(gp.ui.npcSlotRow != 3) {
				gp.ui.npcSlotRow++;
				gp.playSE(9);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.npcSlotCol != 4) {
				gp.ui.npcSlotCol++;
				gp.playSE(9);
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		}
		
	}

}
