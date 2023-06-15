
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Spielfeld extends JPanel implements Serializable {
	// Variablen deklarieren
	//
	public Feld[][] feld;	// Spielfeld
	public Spieler s;		// Spieler
	public Ziel z;			// Ziel
	public Hindernis h;
	public KIConnect kicon;
	private transient MouseListener mouseListener;
	
	// Konstruktor
	//
	// Aufgabe: Eigenschaften setzen
	//
	public Spielfeld(int width, int height, boolean random) throws IOException {
		// Panel einstellungen
		//
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		// Definieren von s und z
		//
		s = null;
		z = null;
		
		// Soll Feld zufaellig befuellt werden?
		if(random) {
			// Spieler und Ziel definieren
			//
			z = new Ziel((int) (Math.random()*width), (int) (Math.random()*height));
			s = new Spieler((int) (Math.random()*width), (int) (Math.random()*height), (int) (Math.random()*4)+1, this, z.getXPos(), z.getYPos());
		
			while((z.getXPos()==s.getXPos()) && (s.getYPos()==z.getYPos())) {
				s = new Spieler((int) (Math.random()*width), (int) (Math.random()*height), (int) (Math.random()*4)+1, this, z.getXPos(), z.getYPos());
			}
		}
		
		
		// Spielfeld initialisieren
		//
		initFeld(width, height);
		
		// Soll Feld zufaellig befuellt werden?
		if(random) {
			fillRandom();
			// Spieler und Ziel Positionieren
			addObject(z);
			addObject(s);
		}
		
		// KiCon erstellen
		//
		kicon = new KIConnect(this);
	}
	
	// initFeld
	//
	// Aufgabe: Feld initialisieren (leer f�llen)
	//
	private void initFeld(int width, int height) throws IOException {
		feld = new Feld[width][height];	// Definieren
		
		// felder definieren
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
					feld[x][y] = new FreiesFeld(x, y);
			}
		}
	}
	
	// fillRandom()
	//
	// Aufgabe: Feld zufaellig mit Hindernissen befuellen
	//
	private void fillRandom() throws IOException {
		for(int x = 0; x < feld.length; x++) {
			for(int y = 0; y < feld[0].length; y++) {
				if(((int) (Math.random()*5)+1) == 1) {	// Wahrscheinlichkeit fuer Hindernisse
					feld[x][y] = new Hindernis(x, y);
				}
			}
		}
	}
	
	// addObject
	//
	// Aufgabe: Spieler und ziel an eine Position setzen
	//
	public void addObject(Feld o) {
		feld[o.getXPos()][o.getYPos()] = o;
	}
	
	// getPreferredSize
	//
	// Aufgabe: siehe panel klasse
	//
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(feld.length*40, feld[0].length*40);
	}
	
	// paint
	//
	// Aufgabe: Drucken des Feldes
	//
	@Override
	public void paint(Graphics g) {
		
		for(int x = 0; x < feld.length; x++) {
			for(int y = 0; y < feld[x].length; y++) {
				g.drawImage(feld[x][y].getImage(), x*40, y*40, null);
			}
		}
	}
	
	// reloadImages()
	//
	// Aufgabe: Images neu laden (beispiel nach speichern wieder laden)
	//
	public void reloadImages() {
		for(int x = 0; x < feld.length; x++) {
			for(int y = 0; y < feld[x].length; y++) {
				feld[x][y].reloadImage();
			}
		}
	}
	
	// setFeld()
	//
	// Aufgabe: Feld aus feld[]
	//
	public void setFeld(int x, int y, Feld neu) {
		if((x<feld.length) && (y<feld[0].length) && (x>=0) && (y>=0)) {
			feld[x][y] = neu;
		}
	}
	
	// setEditable()
	//
	// Aufgabe: Spielfeld Editierbar einstellen
	//
	public void setEditable(final Editor editor) {
		final Spielfeld spielfeldParam = this;
		mouseListener = new MouseListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void mouseClicked(MouseEvent e) {
				// Nur ausführen wenn Mouse Event im spielfeld liegt
				if((((int) (e.getX()/40)) < spielfeldParam.feld.length) &&
						(((int) (e.getY()/40)) < spielfeldParam.feld[0].length)) {
					try {
						// Spieler im Editor ausgewaehlt und es gibt schon nen spieler -> alten löschen
						if((editor.objekte.getSelection().getActionCommand() == "Spieler") && s!=null) {
							feld[s.getXPos()][s.getYPos()] = new FreiesFeld(s.getXPos(), s.getYPos());
							s = null;
						}
						// Ziel im Editor ausgewaehlt und es gibt schon ein Ziel -> altes löschen
						if((editor.objekte.getSelection().getActionCommand() == "Ziel" && z != null)) {
							feld[z.getXPos()][z.getYPos()] = new FreiesFeld(z.getXPos(), z.getYPos());
							z = null;
						}
					
						// Allgemeine Reflection 
						//
						// Aufgabe: Reflection für Klassen mit gleichem Konstruktor wie Feld
						//
						Feld neuesFeld = null;	// Wird später nochmal gebraucht
						if(editor.objekte.getSelection().getActionCommand() != "Spieler"){
							Class neuesFeldClass = Class.forName(editor.objekte.getSelection().getActionCommand());
							Constructor constructor = neuesFeldClass.getConstructor(new Class[]{int.class, int.class});
							Object[] parameter = new Object[] {(int) (e.getX()/40), (int) (e.getY()/40)};
							neuesFeld = (Feld) constructor.newInstance(parameter);

							// Altes Feld vor dem umstellen prüfen
							if(feld[(int) (e.getX()/40)][(int) (e.getY()/40)] instanceof Spieler) {
								s = null;
							}
							else if(feld[(int) (e.getX()/40)][(int) (e.getY()/40)] instanceof Ziel) {
								z = null;
							}
						
							// Feld setzen
							setFeld((int) (e.getX()/40), (int) (e.getY()/40), neuesFeld);
							repaint();
						}
					
						// Editor wahl Spieler ? -> s = andere Reflektion
						if((editor.objekte.getSelection().getActionCommand() == "Spieler") && s == null) {
							Class neuesFeldClass = Class.forName("Spieler");
							Constructor constructor = neuesFeldClass.getConstructor(new Class[]{int.class, int.class, int.class, Spielfeld.class, int.class, int.class});

							Object[] parameter;
							// Gibt es schon ein Ziel?
							if(z!=null) {
								parameter = new Object[] {(int) (e.getX()/40), (int) (e.getY()/40), 1, spielfeldParam, z.getXPos(), z.getYPos()};
							}
							// Nein?
							else {
								parameter = new Object[] {(int) (e.getX()/40), (int) (e.getY()/40), 1, spielfeldParam, -1, -1};							
							}
							s = (Spieler) constructor.newInstance(parameter);

							// Wenn altes Feld Ziel ist dann z null setzen
							if(feld[(int) (e.getX()/40)][(int) (e.getY()/40)] instanceof Ziel) {
								z = null;
							}
						
							setFeld((int) (e.getX()/40), (int) (e.getY()/40), s);
							repaint();
						}
						// Reflektion Ziel ? -> z = Reflektion
						if((editor.objekte.getSelection().getActionCommand() == "Ziel") && z == null) {
							z = (Ziel) neuesFeld;
						
							// Ziel des Spielers neu setzen wenn vorhanden
							if(s!=null) {
								s.setZiel(z.getXPos(), z.getYPos());
							}
						}
					
					} catch (Exception exc) {
						// Exception array index out of bounds wenn stelle angedrückt wo kein spielfeld ist
						// exc.printStackTrace();
					}
				}
				else {
					/*
					 * MouseEvent liegt nicht in Spielfeld
					 */
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		};
		addMouseListener(mouseListener);
	}
	
	// setUneditable()
	//
	// Aufgabe: Spielfeld als nicht Editierbar einstellen
	//
	public void setUneditable() {
		removeMouseListener(mouseListener);
	}
}
