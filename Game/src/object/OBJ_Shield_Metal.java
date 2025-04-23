package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Metal extends Entity {

	public OBJ_Shield_Metal(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = "Metal Shield";
		down1 = setup("/objects/shield_metal",gp.tileSize,gp.tileSize);
		defenseValue = 2;
		description = "[" + name + "]\nA knight's trusty shield.";
		
		price = 50;
	}

}
