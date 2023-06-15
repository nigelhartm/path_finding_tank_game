import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Spieler extends Feld implements Serializable {
	// Variablen deklarieren
	//
	private int dir; /* "Blickrichtung"
			  		  * 1 = oben
			  		  * 2 = rechts
			  		  * 3 = unten 
			  		  * 4 = links  
			  		  */
	private Spielfeld spielfeld; // Referenz zum Feld in dem sich der Spieler befindet
	private int startXPos, startYPos, startDir;
	private int zielXPos, zielYPos;
	
	// Konstruktor
	//
	// Aufgabe: Eigenschaften setzen
	//
	public Spieler(int xPos, int yPos, int dir, Spielfeld spielfeld, int zielXPos, int zielYPos) throws IOException {
		super(xPos, yPos);
		this.startXPos = xPos;
		this.startYPos = yPos;
		this.startDir = dir;
		
		this.zielXPos = zielXPos;
		this.zielYPos = zielYPos;
		this.spielfeld = spielfeld;
		
		setDir(dir);
	}
	
	// isFrei()
	//
	// Aufgabe: Sensor (ist Feld vor Spieler frei?) : true -> Frei ; false -> Nicht Frei
	//
	public boolean isFrei() {
		switch(dir) {
			// Oben
			//
			case 1:
				if(this.getYPos() > 0) {
					if(!(this.spielfeld.feld[this.getXPos()][this.getYPos()-1] instanceof Hindernis)) {
						return true;
					}
				}
				break;
			// Rechts
			//
			case 2:
				if(this.getXPos() < spielfeld.feld.length-1) {
					if(!(this.spielfeld.feld[this.getXPos()+1][this.getYPos()] instanceof Hindernis)) {
						return true;
					}
				}
				break;
			// Unten
			//
			case 3:
				if(this.getYPos() < spielfeld.feld[0].length-1) {
					if(!(this.spielfeld.feld[this.getXPos()][this.getYPos()+1] instanceof Hindernis)) {
						return true;
					}
				}
				break;
			// Links
			//
			case 4:
				if(getXPos() > 0) {
					if(!(this.spielfeld.feld[this.getXPos()-1][this.getYPos()] instanceof Hindernis)) {
						return true;
					}
				}
				break;
			default:
				System.err.println("Fehler beim Pruefen mit Sensor.");
				break;
		}
		
		return false;
	}
	
	// move()
	// 
	// Aufgabe: Einen Schritt in blickrichtung machen
	//
	public void move() throws IOException {
		if(isFrei()) {
			switch(dir) {
				// Oben
				//
				case 1:
					super.setPos(this.getXPos(), this.getYPos()-1); // Neue Position des Spielers setzen
					spielfeld.feld[this.getXPos()][this.getYPos()+1] = new FreiesFeld(this.getXPos(), this.getYPos()+1); // Altes Feld mit Leerfeld füllen
					spielfeld.feld[this.getXPos()][this.getYPos()] = this; // Spieler auf neues Feld stellen
					break;
					// Rechts
					//
				case 2:
					super.setPos(this.getXPos()+1, this.getYPos()); // Neue Position des Spielers setzen
					spielfeld.feld[this.getXPos()-1][this.getYPos()] = new FreiesFeld(this.getXPos()-1, this.getYPos()); // Altes Feld mit Leerfeld füllen
					spielfeld.feld[this.getXPos()][this.getYPos()] = this; // Spieler auf neues Feld stellen
					break;
					// Unten
					//
				case 3:
					super.setPos(this.getXPos(), this.getYPos()+1); // Neue Position des Spielers setzen
					spielfeld.feld[this.getXPos()][this.getYPos()-1] = new FreiesFeld(this.getXPos(), this.getYPos()-1); // Altes Feld mit Leerfeld füllen
					spielfeld.feld[this.getXPos()][this.getYPos()] = this; // Spieler auf neues Feld stellen
					break;
					// Links
					//
				case 4:
					super.setPos(this.getXPos()-1, this.getYPos()); // Neue Position des Spielers setzen
					spielfeld.feld[this.getXPos()+1][this.getYPos()] = new FreiesFeld(this.getXPos()+1, this.getYPos()); // Altes Feld mit Leerfeld füllen
					spielfeld.feld[this.getXPos()][this.getYPos()] = this; // Spieler auf neues Feld stellen
					break;
				default:
					System.err.println("Bewegung konnte nicht ausgefuehrt werden.");
			}
		}
		spielfeld.repaint(); // Spielfeld neu zeichnen
	}
	
	// setDir()
	//
	// Aufgabe: Blick richtung setzen siehe (int dir)
	//
	public void setDir(int dir) throws IOException {
		this.dir = dir;	// Richtung setzen

		// Bild setzen
		//
		switch(this.dir) {
			case 1:
				super.setImage(ImageIO.read(new File("img/Spieler_oben.png")));
				break;
			case 2:
				super.setImage(ImageIO.read(new File("img/Spieler_rechts.png")));
				break;
			case 3:
				super.setImage(ImageIO.read(new File("img/Spieler_unten.png")));
				break;
			case 4:
				super.setImage(ImageIO.read(new File("img/Spieler_links.png")));
				break;
			default:
				System.err.println("Bild konnte nicht gesetzt werden.");
		}
		
		spielfeld.repaint();
	}
	
	// getDir()
	//
	// Aufgabe: Blick richtung zurueckgeben siehe (int dir)
	//
	int getDir() {
		return dir;
	}
	
	// reset()
	//
	// Aufgabe: spieler auf startPosition zuruecksetzen
	//
	public void reset() {
		// Altes Feld überschreiben
		try {
			spielfeld.setFeld(this.getXPos(), this.getYPos(), new FreiesFeld(this.getXPos(), this.getYPos()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Ziel neu platzieren
		try {
			spielfeld.feld[zielXPos][zielYPos] = new Ziel(zielXPos, zielYPos);
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		// Auf neue Position setzen
		setPos(startXPos, startYPos);
		spielfeld.feld[getXPos()][getYPos()] = this;
		try {
			setDir(startDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		spielfeld.repaint();
	}
	
	// isFinish()
	//
	// Aufgabe: Befindet sich Spieler auf Ziel?
	//
	public boolean isFinish() {
		if((this.getXPos()==this.zielXPos) && (this.getYPos()==this.zielYPos))
			return true;
		else
			return false;
	}
	
	// reloadImage()
	//
	// Aufgabe: Image neu laden (nach speichern neu laden)
	//
	@Override
	public void reloadImage() {
		try {
			super.reloadImage();
			setDir(getDir());
		} catch(Exception exc) {
			System.err.println("Problem beim reloaden des Images(x:" + getXPos() + " y:" + getYPos() + ")");
			exc.printStackTrace();
		}
	}
	
	// setZiel()
	//
	// Aufgabe: Nachträglich ein Ziel setzen
	//
	public void setZiel(int xPos, int yPos) {
		this.zielXPos = xPos;
		this.zielYPos = yPos;
	}
}
