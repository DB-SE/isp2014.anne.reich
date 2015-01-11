import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * TODO description
 */
public class Bibo {
	
	public JPanel suchePanel;
	
public void byTextInput(){
		
		suchePanel = new JPanel();
		suchePanel.setLayout(new BorderLayout());
		suchePanel.setBorder(new EmptyBorder(0,3,5,3));
		
		//Combo-Box
	
		
		String[] nachList = {"nach Titel:", "nach Autor:", "nach Verlag:"};
		final JComboBox<String> nach = new JComboBox<String>(nachList);
		nach.setPreferredSize(new Dimension(112, 25));
		setTrefferList(searchAuswahl);
		//erkennt, welche Suche-Auswahl aktiviert ist (nach Zahlen)
		nach.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					searchAuswahl = nach.getSelectedIndex();
					setTrefferList(searchAuswahl);
			}			
		});
		
		suchePanel.add(nach, BorderLayout.LINE_START);
		
		//Textfeld
		textField = new JTextField(50);		
		suchePanel.add(textField, BorderLayout.CENTER);	
		autoComplete();
		
		//Suchen-Button
		JButton suchButton = new JButton();
		suchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String match = textField.getText();
				
				ArrayList<String> matchTitelDmy = new ArrayList<String>();
				
				//alle Möglichkeiten abfangen
				if(searchAuswahl == 0){
					matchTitelDmy.add(match);
				}
				else{
						if(searchAuswahl == 1)
							matchTitelDmy = getTitelByAutor(match);
						else
							matchTitelDmy = getTitelByVerlag(match);
					
				}
				
				//diese in String[] packen
				String[] matchTitel = new String[matchTitelDmy.size()];
				
				for(int j = 0; j < matchTitelDmy.size(); j++){
					matchTitel[j] = matchTitelDmy.get(j);
				}
				
			    list = new JList<String>(matchTitel);
		        String[] farbe = getFarbe(matchTitel);
			    list.setCellRenderer(new TitelFarbe(farbe));
		        klick();
			    gesamtList.getViewport().setView(list);
			}
		});
		try {
			BufferedImage bi = ImageIO.read(ImageIO.class.getResource("/suchenIcon.gif"));
			suchButton.setIcon(new ImageIcon(bi));
		} catch (Exception e2) {
			suchButton.setText("Suchen");
		}
		suchButton.setPreferredSize(new Dimension(25, 25));
		
		suchePanel.add(suchButton, BorderLayout.LINE_END);		
	
	
	}

}