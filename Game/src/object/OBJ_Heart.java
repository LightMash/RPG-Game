package object;



import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

	GamePanel gp;
	public static final String objName = "Heart";

	public OBJ_Heart(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		value = 2;
		name = objName;
		down1 = setup("/objects/banana_full",gp.tileSize,gp.tileSize);
		image = setup("/objects/banana_full",gp.tileSize,gp.tileSize);
		image2 = setup("/objects/banana_half",gp.tileSize,gp.tileSize);
		image3 = setup("/objects/banana_empty",gp.tileSize,gp.tileSize);
		
	}
	public boolean use(Entity entity) {
	
		gp.playSE(2);
		gp.ui.addMessage("Life + " + value);
		entity.life += value;
		return true;
	}
}
