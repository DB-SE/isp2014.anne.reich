import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

/**
 * TODO description
 */
class Start {
	

	
	JMenuBar menuBar = new JMenuBar();
	JMenu menuDatei = new JMenu("Datei");
	public void createMenu() {
		
		JMenuItem itemReset = new JMenuItem("Zuruecksetzten");
		itemReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich die Datenbank zuruecksetzten?", 
							"Datenbank zuruecksetzten", JOptionPane.YES_NO_OPTION);
					if(eingabe == 0){
						eingabe = JOptionPane.showConfirmDialog(null, 
								"Sind Sie wirklich sicher, dass Sie die Datenbank zuruecksetzten wollen?\nSie koennen Sie dann nicht mehr herstellen\nAusser Sie haben vorher ein Export gemacht.", 
								"Datenbank wirklich zuruecksetzten", JOptionPane.YES_NO_OPTION);
						if(eingabe == 0){
							resetDB();
							startStart();
							Start.fenster.setVisible(false);
						}
					}
				
			}
		});
		
		menuDatei.add(itemReset);
	}

}