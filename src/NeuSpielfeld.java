import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class NeuSpielfeld extends JFrame {

	private JPanel contentPane;
	private JTextField tfHoehe;
	private JTextField tfBreite;

	/**
	 * Launch the application.
	 */
	public static void main(final Interface interf) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NeuSpielfeld frame = new NeuSpielfeld(interf);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NeuSpielfeld(final Interface interf) {
		// Fenstereinstellungen
		//
		setTitle("Neues Spielfeld");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 278, 144);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Labels
		//
		JLabel lblHoehe = new JLabel("Hoehe:");
		lblHoehe.setBounds(10, 11, 46, 14);
		contentPane.add(lblHoehe);
		
		JLabel lblBreite = new JLabel("Breite:");
		lblBreite.setBounds(10, 39, 46, 14);
		contentPane.add(lblBreite);
		
		// Textfelder
		//
		tfHoehe = new JTextField();
		tfHoehe.setBounds(61, 8, 191, 20);
		contentPane.add(tfHoehe);
		tfHoehe.setColumns(10);
		
		tfBreite = new JTextField();
		tfBreite.setBounds(61, 36, 191, 20);
		contentPane.add(tfBreite);
		tfBreite.setColumns(10);
		
		// Leer
		//
		JButton btnLeer = new JButton("Leer");
		btnLeer.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((Integer.valueOf(tfBreite.getText()) * Integer.valueOf(tfHoehe.getText())) > 1) && (Integer.valueOf(tfBreite.getText()) <= 300)
						&& (Integer.valueOf(tfHoehe.getText()) <= 300)) {
					try {
						interf.spielfeld = new Spielfeld(Integer.valueOf(tfBreite.getText()), Integer.valueOf(tfHoehe.getText()), false);
						interf.scrollPane = new JScrollPane(interf.spielfeld);
						interf.setContentPane(interf.scrollPane);
						interf.dispose();
						interf.show();
						interf.repaint();
						setVisible(false);
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog( null, "Fehler: Summe von Hoehe und Breite muss groesser als 1 sein und Hoehe sowie Breite duerfen nicht groesser als 300 sein.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLeer.setBounds(10, 72, 121, 23);
		contentPane.add(btnLeer);
		
		// Zufall
		//
		JButton btnZufall = new JButton("Zufall");
		btnZufall.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((Integer.valueOf(tfBreite.getText()) * Integer.valueOf(tfHoehe.getText())) > 1) && (Integer.valueOf(tfBreite.getText()) <= 300)
						&& (Integer.valueOf(tfHoehe.getText()) <= 300)) {
					try {
						interf.spielfeld = new Spielfeld(Integer.valueOf(tfBreite.getText()), Integer.valueOf(tfHoehe.getText()), true);
						interf.scrollPane = new JScrollPane(interf.spielfeld);
						interf.setContentPane(interf.scrollPane);
						interf.repaint();
						interf.dispose();
						interf.show();
						interf.setExtendedState(Frame.MAXIMIZED_BOTH);
						setVisible(false);
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog( null, "Fehler: Summe von Hoehe und Breite muss groesser als 1 sein und Hoehe sowie Breite duerfen nicht groesser als 300 sein.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnZufall.setBounds(141, 72, 111, 23);
		contentPane.add(btnZufall);
	}
}
