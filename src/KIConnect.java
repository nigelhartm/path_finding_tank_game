import java.io.IOException;
import java.io.Serializable;

@SuppressWarnings("serial")
public class KIConnect implements Serializable {
	// Variablen deklarieren
	//
	private Spielfeld spielfeld;
	private KI ki;
	private int time;
	private transient Thread thread;
	
	// Konstruktor
	//
	// Aufgabe: Initialisieren
	//
	public KIConnect(Spielfeld spielfeld) {
		setDelay(0);
		this.spielfeld = spielfeld;
		ki = new KI(this);
		thread = null;
	}
	
	// startThread
	//
	// Aufgabe: Thread starten
	//
	public void startThread() {
		thread = new Thread(ki);
		thread.start();
	}
	
	// stopThread
	//
	// Aufgabe: Thread stoppen
	//
	@SuppressWarnings("deprecation")
	public void stopThread() {
		if(thread != null)
			thread.stop();
	}
	
	// reset()
	//
	// Aufgabe: Resetten des Spielers
	//
	public void reset() {
		spielfeld.s.reset();
	}
	
	// isFinish()
	//
	// Aufgabe: Auf Ziel?
	//
	public boolean isFinish() {
		return spielfeld.s.isFinish();
	}
	
	// spielerXPos()
	//
	// Aufgabe: Horizontale Spieler Position
	//
	public int spielerXPos() {
		return spielfeld.s.getXPos();
	}
	
	// spielerYPos()
	//
	// Aufgabe: Vertikale Spieler Position
	//
	public int spielerYPos() {
		return spielfeld.s.getYPos();
	}
	
	// isFrei() 
	//
	// Aufgabe: Position vor Spieler Frei
	//
	public boolean isFrei() {
		return spielfeld.s.isFrei();
	}
	
	// move()
	//
	// Aufgabe: Spieler vorwaerts bewegen
	//
	@SuppressWarnings("static-access")
	public void move() {
		try {
			spielfeld.s.move();
			try {
				thread.sleep(time);
			} catch(Exception exc) {
				exc.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// setDir()
	//
	// Aufgabe: Richtung setzen -> 1=oben ++ clockwise
	//
	@SuppressWarnings("static-access")
	public void setDir(int dir) { 
		try {
			spielfeld.s.setDir(dir);
			try {
				thread.sleep(time);
			} catch(Exception exc) {
				exc.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// getDir()
	//
	// Aufgabe: Richtung zurueckgeben -> 1=oben ++ clockwise
	//
	public int getDir() {
		return spielfeld.s.getDir();
	}
	
	// zielXPos()
	//
	// Aufgabe: Horizontale Ziel Position
	//
	public int zielXPos() {
		return spielfeld.z.getXPos();
	}
	
	// zielYPos()
	//
	// Aufgabe: Vertikale Ziel Position
	//
	public int zielYPos() {
		return spielfeld.z.getYPos();
	}
	
	// repaint()
	//
	// Aufgabe: Feld neu zeichnen
	//
	public void repaint() {
		spielfeld.repaint();
	}
	
	// setDelay()
	//
	// Aufgabe: Delay setzen
	//
	public void setDelay(int time) {
		this.time = time;
	}
	
	// KI+
	//
	// gibt feld zurück
		public int feldGetY() {
			return spielfeld.feld[0].length;
		}
		public int feldGetX() {
			return spielfeld.feld.length;
		}
		
		public Feld[][] getFeld()
		{
			return spielfeld.feld;
		}
}
