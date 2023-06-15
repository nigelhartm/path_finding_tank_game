import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Editor extends JPanel {

	public ButtonGroup objekte;
	
	/**
	 * Create the panel.
	 */
	public Editor(final Spielfeld spielfeld) {
		// Panel Einstellungen
		//
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// ------------------------------------------------
		// ButtonGroup - START
		// ------------------------------------------------
		objekte = new ButtonGroup();
		try {
			
			// ------------------------------------------------
			// Hindernis - START
			// ------------------------------------------------
			JRadioButton hindernis = new JRadioButton("Hindernis", new ImageIcon(ImageIO.read(new File("img/Hindernis.png"))));
			hindernis.setActionCommand("Hindernis");
			hindernis.setPreferredSize(new Dimension(150, 40));
			objekte.add(hindernis);
			add(hindernis);
			// Ausgewählt?
			//
			hindernis.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    try {
                    	// Cursor Image setzen
                        SwingUtilities.windowForComponent(Editor.this).setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("img/Hindernis.png")), new Point(20, 20), "test"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
			
			// ------------------------------------------------
			// Hindernis2 - START
			// ------------------------------------------------
			JRadioButton hindernis2 = new JRadioButton("Hindernis2", new ImageIcon(ImageIO.read(new File("img/Hindernis2.png"))));
			hindernis2.setActionCommand("Hindernis2");
			hindernis2.setPreferredSize(new Dimension(150, 40));
			objekte.add(hindernis2);
			add(hindernis2);
			// Ausgewählt?
			//
			hindernis2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						// Cursor Image setzen
						SwingUtilities.windowForComponent(Editor.this).setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("img/Hindernis2.png")), new Point(20, 20), "test"));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				}
			});
			
			// ------------------------------------------------
			// Hindernis3 - START
			// ------------------------------------------------
			JRadioButton hindernis3 = new JRadioButton("Hindernis3", new ImageIcon(ImageIO.read(new File("img/Hindernis3.png"))));
			hindernis3.setActionCommand("Hindernis3");
			hindernis3.setPreferredSize(new Dimension(150, 40));
			objekte.add(hindernis3);
			add(hindernis3);
			// Ausgewählt?
			//
			hindernis3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						// Cursor Image setzen
						SwingUtilities.windowForComponent(Editor.this).setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("img/Hindernis3.png")), new Point(20, 20), "test"));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				}
			});
			
			// ------------------------------------------------
			// Wasser - START
			// ------------------------------------------------
			JRadioButton wasser = new JRadioButton("Wasser", new ImageIcon(ImageIO.read(new File("img/Wasser.png"))));
			wasser.setActionCommand("Wasser");
			wasser.setPreferredSize(new Dimension(150, 40));
			objekte.add(wasser);
			add(wasser);
			// Ausgewählt?
			//
			wasser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						// Cursor Image setzen
						SwingUtilities.windowForComponent(Editor.this).setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("img/Wasser.png")), new Point(20, 20), "test"));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				}
			});
			
			// ------------------------------------------------
			// FreiesFeld - START
			// ------------------------------------------------
			JRadioButton freiesFeld = new JRadioButton("Freies Feld", new ImageIcon(ImageIO.read(new File("img/FreiesFeld.png"))));
			freiesFeld.setActionCommand("FreiesFeld");
			freiesFeld.setPreferredSize(new Dimension(150, 40));
			objekte.add(freiesFeld);
			add(freiesFeld);
			// Ausgewählt?
			//
			freiesFeld.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						// Cursor Image setzen
						SwingUtilities.windowForComponent(Editor.this).setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("img/FreiesFeld.png")), new Point(20,20), "test"));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				}
			});
			
			// ------------------------------------------------
			// Ziel - START
			// ------------------------------------------------
			JRadioButton ziel = new JRadioButton("Ziel", new ImageIcon(ImageIO.read(new File("img/Ziel.png"))));
			ziel.setActionCommand("Ziel");
			ziel.setPreferredSize(new Dimension(150, 40));
			objekte.add(ziel);
			add(ziel);
			// Ausgewählt?
			//
			ziel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						// Cursor Image setzen
						SwingUtilities.windowForComponent(Editor.this).setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("img/Ziel.png")), new Point(20, 20), "test"));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				}
			});
			
			// ------------------------------------------------
			// Spieler - START
			// ------------------------------------------------
			JRadioButton spieler = new JRadioButton("Spieler", new ImageIcon(ImageIO.read(new File("img/Spieler_oben.png"))));
			spieler.setActionCommand("Spieler");
			spieler.setPreferredSize(new Dimension(100, 40));
			objekte.add(spieler);
			add(spieler);
			// Ausgewählt?
			//
			spieler.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						// Cursor Image setzen
						SwingUtilities.windowForComponent(Editor.this).setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("img/Spieler_oben.png")), new Point(20, 20), "test"));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				}
			});

			// ------------------------------------------------
			// Turn - START
			// ------------------------------------------------
			JButton turn = new JButton("", new ImageIcon(ImageIO.read(new File("img/Turn.png"))));
			turn.setActionCommand("Turn");
			turn.setPreferredSize(new Dimension(40, 40));
			objekte.add(turn);
			add(turn);
			turn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(spielfeld.s != null) {
						try {
							if(spielfeld.s.getDir() >= 4)
								spielfeld.s.setDir(1);
							else
								spielfeld.s.setDir(spielfeld.s.getDir()+1);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			
			
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		
		// ------------------------------------------------
		// ButtonGroup - Ende
		// ------------------------------------------------
		
		// Weitere Panel Einstellungen
		//
		setPreferredSize(new Dimension((int) objekte.getElements().nextElement().getPreferredSize().getWidth(), 800));
	}
}
