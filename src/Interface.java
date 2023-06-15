import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class Interface extends JFrame {
	
	public Spielfeld spielfeld;
	public JScrollPane scrollPane;
	private JPanel panelMitEditor;
	private Editor editor;
	private JMenuItem menuBearbeitenEditor;
	public JMenuBar menu;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Interface() throws IOException {
		// Fenster Einstellungen
		//
		setTitle("Projektwoche");
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Interface parameter = this;
		setBounds(100, 100, 450, 300);
		panelMitEditor = null;
		editor = null;
		spielfeld = null;
		scrollPane = null;
		
		// Hintergrundbild zeichnen
		//
		/*JLabel label = new JLabel();
		label.setIcon(new ImageIcon(ImageIO.read(new File("img/Hintergrund.png"))));
		label.getIcon().s
		add(label);*/
		
		
		// ---------------------------------------------------------
		// Menue - START
		// ---------------------------------------------------------
		menu = new JMenuBar();
		setJMenuBar(menu);
		
			// ---------------------------------------------------------
			// Datei - START
			// ---------------------------------------------------------
			final JMenu menuDatei = new JMenu("Datei");
			menu.add(menuDatei);
			
				// ---------------------------------------------------------
				// Neu - START
				// ---------------------------------------------------------
				JMenuItem menuDateiNeu = new JMenuItem("Neu");
				menuDatei.add(menuDateiNeu);
				menuDateiNeu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						NeuSpielfeld.main(parameter);
					}
				});
				
				// ---------------------------------------------------------
				// Oeffnen (Spielfeld) - START
				// ---------------------------------------------------------
				JMenuItem menuDateiOpenField = new JMenuItem("Oeffnen Spielfeld");
				menuDatei.add(menuDateiOpenField);
				menuDateiOpenField.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							JFileChooser open = new JFileChooser();
							open.setFileFilter(new FileNameExtensionFilter("Objekt", "obj"));
							open.setFileHidingEnabled(true);
							int echo = open.showOpenDialog(parameter);
							
							// Wurde oefnnen gedrueckt?
							if(echo == JFileChooser.APPROVE_OPTION) {
								try {
									ObjectInputStream ois = new ObjectInputStream(new FileInputStream(open.getSelectedFile()));
									spielfeld = (Spielfeld) ois.readObject();
									spielfeld.reloadImages();
									scrollPane = new JScrollPane(spielfeld);
									setContentPane(scrollPane);
									panelMitEditor = null; // Wegen der Bedingung der Editierfunktion null setzen
									repaint();
									dispose();
									show();
							        ois.close();
								} catch (IOException exc) {
									exc.printStackTrace();
								}
							}
						} catch(Exception exc) {
							JOptionPane.showMessageDialog( null, "Spielfeld laden fehlgeschlagen.", "Error", JOptionPane.ERROR_MESSAGE);
							System.err.println("Spielfeld konnte nicht geladen werden.");
							exc.printStackTrace();
						}
					}
				});
				
				// ---------------------------------------------------------
				// Speichern - START
				// ---------------------------------------------------------
				JMenuItem menuDateiSave = new JMenuItem("Speichern");
				menuDatei.add(menuDateiSave);
				menuDateiSave.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Gibt es ein existierendes Spielfeld?
						if(spielfeld != null) {
							try {
								JFileChooser save = new JFileChooser();
								save.setFileFilter(new FileNameExtensionFilter("Objekt", "obj"));
								save.setFileHidingEnabled(true);
								int echo = save.showSaveDialog(parameter);
								
								// Wurde Speichern gedrueckt?
								if((echo == JFileChooser.APPROVE_OPTION)) {
									try {
										ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(save.getSelectedFile() + ".obj"));
										oos.writeObject(spielfeld);
										oos.flush();
										oos.close();
									} catch (IOException exc) {
										exc.printStackTrace();
									}
								}
							} catch(Exception exc) {
								System.err.println("Speichern konnte nicht durchgefuehrt werden.");
								exc.printStackTrace();
							}
						}
						else {
							JOptionPane.showMessageDialog( null, "Speichern konnte nicht durchgefuehrt werden. (Kein Spielfeld vorhanden)", "Error", JOptionPane.ERROR_MESSAGE);
							System.err.println("Speichern konnte nicht durchgefuehrt werden. (Kein Spielfeld vorhanden)");
						}
					}
				});
				
				// ---------------------------------------------------------
				// Beenden - START
				// ---------------------------------------------------------
				JMenuItem menuDateiExit = new JMenuItem("Beenden");
				menuDatei.add(menuDateiExit);
				menuDateiExit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);	// Programm schliessen
					}
				});
				
			// ---------------------------------------------------------
			// Bearbeiten - Start
			// ---------------------------------------------------------
			JMenu menuBearbeiten = new JMenu("Bearbeiten");
			menu.add(menuBearbeiten);
			
				// ---------------------------------------------------------
				// Start/Stop - START
				// ---------------------------------------------------------
				JMenuItem menuBearbeitenRun = new JMenuItem("Start/Stop");
				menuBearbeiten.add(menuBearbeitenRun);
				menuBearbeitenRun.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void actionPerformed(ActionEvent e) {
						// Nur wenn es ein spielfeld gibt ausführen
						if(spielfeld!=null) {
							// Nur wenn es einen Spieler gibt ausführen
							if(spielfeld.s != null) {
								// Nur wenn es ein Ziel gibt ausführen
								if(spielfeld.z != null) {
									// Datei Menue sperren/entsperren
									//
									if(menuDatei.isEnabled() && menuBearbeitenEditor.isEnabled()) {	// Start KI
										menuDatei.setEnabled(false);
										menuBearbeitenEditor.setEnabled(false);
										
										// Editor entfernen
										//
										spielfeld.setUneditable();
										panelMitEditor = null;
										setCursor(Cursor.getDefaultCursor());
										if(scrollPane != null)
											setContentPane(scrollPane);
										else
											setContentPane(new JPanel());
										repaint();
										menu.repaint();
										dispose();
										show();
										
										// Start
										//
										spielfeld.kicon.startThread();
									}
									else {	// Stop KI
										menuBearbeitenEditor.setEnabled(true);
										menuDatei.setEnabled(true);
										spielfeld.kicon.stopThread();
									}
								}
								// Es gibt kein Ziel
								else {
									JOptionPane.showMessageDialog(null, "Es gibt kein Ziel.", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
							// Es gibt keinen Spieler
							else {
								JOptionPane.showMessageDialog(null, "Es gibt keinen Spieler.", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						// Es gibt kein Spielfeld
						else {
							JOptionPane.showMessageDialog(null, "Es gibt kein Spielfeld.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				
				// ---------------------------------------------------------
				// Editor - START
				// ---------------------------------------------------------
				menuBearbeitenEditor = new JMenuItem("Editor");
				menuBearbeiten.add(menuBearbeitenEditor);
				menuBearbeitenEditor.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void actionPerformed(ActionEvent e) {
						// Nur ausführen wenn es ein Spielfeld gibt
						if(spielfeld!=null) {
							// editieren
							//
							if(panelMitEditor == null && scrollPane != null) {
								panelMitEditor = new JPanel();
								panelMitEditor.setLayout(new BorderLayout());
								panelMitEditor.add(scrollPane, BorderLayout.CENTER);
								editor = new Editor(spielfeld);
								panelMitEditor.add(editor, BorderLayout.EAST);
							
								spielfeld.setEditable(editor);
							
								setContentPane(panelMitEditor);
								panelMitEditor.repaint();
								scrollPane.repaint();
		
								dispose();
								show();
							}
							// nicht editieren
							//
							else {
								spielfeld.setUneditable();
							
								panelMitEditor = null;
								if(scrollPane != null)
									setContentPane(scrollPane);
								else
									setContentPane(new JPanel());
								repaint();
								dispose();
								setCursor(Cursor.getDefaultCursor());
								
								show();
							}
						}
						// Gibt es kein Spielfeld? -> Error
						else {
							JOptionPane.showMessageDialog(null, "Sie koennen den Editor erst starten, wenn es ein Spielfeld gibt.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				
			// ---------------------------------------------------------
			// ? - Start
			// ---------------------------------------------------------
			JMenu menuMore = new JMenu("?");
			menu.add(menuMore);
				
		// ---------------------------------------------------------
		// Menue - ENDE
		// ---------------------------------------------------------
	}
}
