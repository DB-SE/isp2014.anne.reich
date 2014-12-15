package isp1415.ar.plugins;

import isp1415.ar.guiPack.Bibo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class byTextInputTest {
	
	public byTextInputTest(){}
	
	
	public JPanel setInputLayout(){
		JPanel suchePanel = new JPanel();
		suchePanel.setLayout(new BorderLayout());
		suchePanel.setBorder(new EmptyBorder(0,3,5,3));
		
		//Combo-Box
		String[] nachList = {"nach Titel:", "nach Autor:", "nach Verlag:"};
		final JComboBox<String> nach = new JComboBox<String>(nachList);
		nach.setPreferredSize(new Dimension(112, 25));
		try {
			Bibo.setTrefferList(Bibo.searchAuswahl);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		//erkennt, welche Suche-Auswahl aktiviert ist (nach Zahlen)
		nach.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					Bibo.searchAuswahl = nach.getSelectedIndex();
					Bibo.setTrefferList(Bibo.searchAuswahl);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}			
		});
		
		suchePanel.add(nach, BorderLayout.LINE_START);
		
		//Textfeld
		Bibo.textField = new JTextField(50);		
		suchePanel.add(Bibo.textField, BorderLayout.CENTER);	
		Bibo.autoComplete();
		
		//Suchen-Button
		JButton suchButton = new JButton();
		suchButton.addActionListener(new ActionListener(){
			@SuppressWarnings({ "unchecked"})
			public void actionPerformed(ActionEvent arg0) {
				String match = Bibo.textField.getText();
				
				ArrayList<String> matchTitelDmy = new ArrayList<String>();
				
				//alle Möglichkeiten abfangen
				if(Bibo.searchAuswahl == 0){
					matchTitelDmy.add(match);
				}
				else{
					try {
						if(Bibo.searchAuswahl == 1)
							matchTitelDmy = Bibo.getTitelByAutor(match);
						else
							matchTitelDmy = Bibo.getTitelByVerlag(match);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				//diese in String[] packen
				String[] matchTitel = new String[matchTitelDmy.size()];
				
				for(int j = 0; j < matchTitelDmy.size(); j++){
					matchTitel[j] = matchTitelDmy.get(j);
				}
				
				Bibo.list = new JList<String>(matchTitel);
		        String[] farbe = Bibo.getFarbe(matchTitel);
		        Bibo.list.setCellRenderer(new TitelFarbe(farbe));
		        Bibo.klick();
		        Bibo.gesamtList.getViewport().setView(Bibo.list);
			}
		});
		try {
			BufferedImage bi = ImageIO.read(ImageIO.class.getResource("/suchenIcon.gif"));
			suchButton.setIcon(new ImageIcon(bi));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		suchButton.setPreferredSize(new Dimension(25, 25));
		
		suchePanel.add(suchButton, BorderLayout.LINE_END);	
		
		return suchePanel;
	}
	
	@SuppressWarnings({ "serial", "rawtypes" })
	static class TitelFarbe extends JLabel implements ListCellRenderer{

		private String[] color;
		
		public TitelFarbe(String[] color){
			setOpaque(true);
			this.color = color;
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			if(value != null){
				setText(value.toString());
				
				if(isSelected){
					setBorder(BorderFactory.createLineBorder(Color.black));
				}
				else{
					setBorder(new EmptyBorder(0,0,0,0));
				}
	
				if(color[index].equals("gruen")){
					setBackground(Color.GREEN);
				}
				else if(color[index].equals("rot")){
					setBackground(Color.RED);
				}
				else{
					setBackground(Color.YELLOW);
				}
				
				return this;
			}
			
			else{
				setBackground(Color.WHITE);
				setText("");
				return this;
			}
		}
		
	}

	

}
