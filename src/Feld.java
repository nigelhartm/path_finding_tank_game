import java.awt.image.BufferedImage;
import java.io.Serializable;

@SuppressWarnings("serial")
public  abstract class Feld implements Serializable {
	// Variablen deklarieren
	//
	private int xPos, yPos;
	private transient BufferedImage img;
	
	// Konstruktor
	//
	// Aufgabe: Eigenschaften setzen
	//
	public Feld(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		setImage(null);
	}
	
	// getXPos
	//
	// Aufgabe: xPosition als integer zurueckgeben
	//
	public int getXPos() {
		return this.xPos;
	}
	
	// getYPos
	//
	// Aufgabe: yPosition als integer zurueckgeben
	//
	public int getYPos() {
		return this.yPos;
	}
	
	// setPos
	//
	// Aufgabe: Setzen der Position
	//
	protected void setPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;		
	}
	
	// getImage
	//
	// Aufgabe: Image des Objektes zurueckgeben
	//
	public BufferedImage getImage() {
		return this.img;
	}
	
	// setImage
	//
	// Aufgabe: Image des Objektes aendern
	//
	public void setImage(BufferedImage img) {
		this.img = img;
	}
	
	// reloadImage()
	//
	// Aufgabe: Image neu laden (nach speichern neu laden)
	//
	public void reloadImage() {
	}
}
