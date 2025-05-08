package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

	public static final String objName = "Woodcutter's Axe";
	
	public OBJ_Axe(GamePanel gp) {
		super(gp);
		
		type = type_axe;
		name = objName;
		down1 = setup("/objects/axe",gp.tileSize,gp.tileSize);
		attackValue = 2;
		attackArea.width = 25;
		attackArea.height = 25;
		description = "[" + name + "]\nA trusty axe.";
		price = 50;
		knockBackPower = 10;
		motion1_duration = 20;
		motion2_duration = 40;
	}

}
