import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Wasser extends Hindernis implements Serializable {
	// Konstruktor
	//
	// Aufgabe: Eigenschaften setzen
	//	
	public Wasser(int xPos, int yPos) throws IOException {
		super(xPos, yPos);
		super.setImage(ImageIO.read(new File("img/Wasser.png")));
	}
	
	// reloadImage()
	//
	// Aufgabe: Image neu laden (nach speichern neu laden)
	//
	@Override
	public void reloadImage() {
		try {
			super.reloadImage();
			super.setImage(ImageIO.read(new File("img/Wasser.png")));
		} catch(Exception exc) {
			System.err.println("Problem beim reloaden des Images(x:" + getXPos() + " y:" + getYPos() + ")");
			exc.printStackTrace();
		}
	}
}
