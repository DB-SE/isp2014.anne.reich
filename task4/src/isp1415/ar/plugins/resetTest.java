package isp1415.ar.plugins;

import isp1415.ar.guiPack.Start;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class resetTest {
	
	public resetTest(){}
	
	public void resetDB() throws SQLException{
		Start.getXml().deleteAndCreateDB();
	}
	
	public JMenuItem setLayout(){
		JMenuItem itemReset = new JMenuItem("Zuruecksetzten");
		itemReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich die Datenbank zuruecksetzten?", 
							"Datenbank zuruecksetzten", JOptionPane.YES_NO_OPTION);
					if(eingabe == 0){
						eingabe = JOptionPane.showConfirmDialog(null, 
								"Sind Sie wirklich sicher, dass Sie die Datenbank zuruecksetzten wollen?\nSie koennen Sie dann nicht mehr herstellen\nAusser Sie haben vorher ein Export gemacht.", 
								"Datenbank wirklich zuruecksetzten", JOptionPane.YES_NO_OPTION);
						if(eingabe == 0){
							resetDB();
							new Start(Start.getXml());
							Start.fenster.setVisible(false);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
		
		return itemReset;
	}

}
