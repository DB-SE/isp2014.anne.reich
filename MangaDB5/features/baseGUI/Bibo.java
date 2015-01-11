import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;

public class Bibo {
	
	private JFrame fenster;
	private static JLabel lName;
	private static String sName;
	private static JLabel lZeichner;
	private static JLabel lVerlag;
	private static JLabel lHab;
	private static JLabel lAnz;
	private static JLabel lStatus;
	private static JLabel lKosten;
	private static JLabel lNext; 
	public static JScrollPane gesamtList;
	public static JTextField textField;
	public static JList<String> list;
	public static int searchAuswahl = 0;
	private static ArrayList<String> treffer;
	
	private static XML xml;
	

	private static String[][] mangaList;
	private static String[][] mangaListSort;
	public static String[][] getMangaListSort() {
		return mangaListSort;
	}

	private static String[] mangaDetail;
	
	public JPanel abcPanel = null;
	public JPanel suchePanel = null;
	public JPanel detailPanel = null;


	@SuppressWarnings("unchecked")
	private void fensterErzeugen(){
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO START 
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//JFrame fenster erstellen
		fenster = new JFrame("Anne's Manga Bibliothek");
		//Größe bestimmen
		fenster.setSize(500, 600);
		fenster.setLocationRelativeTo(null);
		fenster.setResizable(false);
		
		//Gesamtfläche die das Fenster abdeckt
		JPanel flaeche = new JPanel();
		flaeche.setLayout(new BorderLayout());
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO BUTTON-SORTIERUNG + SUCHE (oben)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel obenAll = new JPanel();
		obenAll.setLayout(new BorderLayout());
		
		// TODO Suche-Bereich
		byLetter();
		if(abcPanel != null)
			obenAll.add(abcPanel, BorderLayout.PAGE_START);		
		
		byTextInput();
		if(suchePanel != null)
			obenAll.add(suchePanel, BorderLayout.CENTER);
		
		// TODO zurück + neuerManga-Button
		JPanel backnewPanel = new JPanel();
		backnewPanel.setLayout(new BorderLayout());
		backnewPanel.setBorder(new EmptyBorder(0,3,5,3));
		
		//back-Button führt zur Startseite zurück
		JButton back = new JButton();
		back.setPreferredSize(new Dimension(25,25));
		try {
			BufferedImage bi = ImageIO.read(ImageIO.class.getResource("/backIcon.gif"));
			back.setIcon(new ImageIcon(bi));
		} catch (Exception e2) {
			back.setText("Zurueck");
		}
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getStart();
				fenster.setVisible(false);				
			}			
		});
		backnewPanel.add(back, BorderLayout.LINE_START);
		
		//neu-Button öffnet die manga-erstell-seite
		JButton neu = new JButton("Neuer Manga");	
		neu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				getNeuEditDetail("neu", "-");
				fenster.setVisible(false);
			}
		});
		neu.setPreferredSize(new Dimension(112,20));		
		backnewPanel.add(neu, BorderLayout.LINE_END);
		
		obenAll.add(backnewPanel, BorderLayout.PAGE_END);
		
		flaeche.add(obenAll, BorderLayout.PAGE_START);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO SAMMLUNG (mitte)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		JPanel samml = new JPanel();
		samml.setLayout(new BorderLayout());
		samml.setBorder(new EmptyBorder(0,3,0,3));
		
		sammlung();		
		klick();
		
		gesamtList = new JScrollPane(list);
		
		samml.add(gesamtList, BorderLayout.CENTER);
		
		flaeche.add(samml, BorderLayout.CENTER);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO DATEN + BUTTON (unten)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel detailseditPanel = new JPanel();
		detailseditPanel.setLayout(new BorderLayout());
		detailseditPanel.setBorder(new EmptyBorder(5,0,5,0));
		
		//#if detailViewManga	
		getDetailPanel();
		if(detailPanel != null)
			detailseditPanel.add(detailPanel, BorderLayout.CENTER);		
		
		//TODO: Edit + Detail Button
		JPanel edButtonsPanel = new JPanel();
		edButtonsPanel.setLayout(new GridLayout(0,2));
		
		JPanel buttonEdit = new JPanel();
		buttonEdit.setBorder(new EmptyBorder(0,3,0,155));
		
		JButton edit = new JButton("Editieren");
		edit.addActionListener(new ActionListener()
        { public void actionPerformed(ActionEvent evt) 
            {
        		if(sName != null){
        			getNeuEditDetail("Edit", sName);
        			fenster.setVisible(false);
        		}
             }
        } );
		
		buttonEdit.add(edit);
		
		edButtonsPanel.add(buttonEdit);
		
		JPanel buttonDetail = new JPanel();
		buttonDetail.setBorder(new EmptyBorder(0,0,0,110));
		
		JButton details = new JButton("Baender Uebersicht");		
		details.addActionListener(new ActionListener()
        	{ public void actionPerformed(ActionEvent evt) 
            	{ 
					if(sName != null);
						getNeuEditDetail("Detail", sName);
            	}
        	});		
		
		buttonDetail.add(details);
		
		edButtonsPanel.add(buttonDetail);
		
		detailseditPanel.add(edButtonsPanel, BorderLayout.PAGE_END);
		
		//------------
		
		flaeche.add(detailseditPanel, BorderLayout.PAGE_END);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO ENDE
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		fenster.add(flaeche);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Fenster sichtbar machen
		fenster.setVisible(true);		
	}
	
	public void byLetter(){ original(); }
		
	public void byTextInput(){ original(); }
	
	public void getDetailPanel(){ original(); }
	
	public static void isActive(){ original(); }
	
	public static void autoComplete() {
	        textField.getDocument().addDocumentListener(new DocumentListener() {
	 

	            public void changedUpdate(DocumentEvent arg0) {}
	 
	            public void insertUpdate(DocumentEvent ev) {
	                String completion;
	                int pos = ev.getOffset();
	                String content = null;
	                try {
	                    content = textField.getText(0, pos+1);
	                } catch (BadLocationException ex) {
	                    ex.printStackTrace();
	                }
	 
	                int w;
	                for (w = pos; w >= 0; w--) {
	                }
	                if (pos - w < 2) {
	                    return;
	                }
	 
	                final String prefix = content.substring(w + 1);
	                int n = Collections.binarySearch(treffer, prefix);
	                if (n < 0 && -n <= treffer.size()) {
	                    final String match = treffer.get(-n-1);
	                    if (match.startsWith(prefix)) {
	                        completion = match.substring(pos - w);
	                        SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
	                        textField.addKeyListener(new KeyListener() {

								@SuppressWarnings("unchecked")
								public void keyPressed(KeyEvent arg0) {
									if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
										if(textField.getText().length()!=0){
											
											ArrayList<String> matchTitelDmy = new ArrayList<String>();
											
											//alle Möglichkeiten abfangen
											if(searchAuswahl == 0){
												for(int i = 0; i < treffer.size(); i++){
													if(treffer.get(i).startsWith(prefix))
														matchTitelDmy.add(treffer.get(i));
												}												
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
										
										else{
											String[] liste = new String[mangaList.length];
									        for(int i = 0; i < mangaList.length; i++){
									        	liste[i] = mangaList[i][0];
									        }
									        
									        list = new JList<String>(liste);
									        String[] farbe = getFarbe(liste);
										    list.setCellRenderer(new TitelFarbe(farbe));
									        klick();
									        gesamtList.getViewport().setView(list);
										}
										
									   }
									else if(arg0.getKeyCode() == KeyEvent.VK_RIGHT){
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
								}

								public void keyReleased(KeyEvent arg0) {}	
								
								public void keyTyped(KeyEvent arg0) {}
	                        });
	                    }
	                }
	            }
	 
	            public void removeUpdate(DocumentEvent arg0) {}
	            
	        });
	 
	    }
	 
	private static class CompletionTask implements Runnable {
	 
	        String completion;
	 
	        int position;
	 
	        CompletionTask(String completion, int position) {
	            this.completion = completion;
	            this.position = position;
	        }
	 
	        public void run() {
	            textField.setText(textField.getText() + completion);
	            textField.setCaretPosition(position + completion.length());
	            textField.moveCaretPosition(position);
	        }
	    }

	@SuppressWarnings("unchecked")
	public static void sammlung(){
		String[] samml = new String[mangaList.length];
		String[] farbe = new String[mangaList.length];
		
		for(int i = 0; i < mangaList.length; i++){
			samml[i] = mangaList[i][0];
			farbe[i] = mangaList[i][1];
		}
		
		list = new JList<String>(samml);
		list.setCellRenderer(new TitelFarbe(farbe));
	}
	
	@SuppressWarnings("unchecked")
	public static void sortierung(String letter){
		getTitelColorListSort(letter);
		String[] samml = new String[mangaListSort.length];
		String[] farbe = new String[mangaListSort.length];
		
		for(int i = 0; i < mangaListSort.length; i++){
			samml[i] = mangaListSort[i][0];
			farbe[i] = mangaListSort[i][1];
		}
		
		list = new JList<String>(samml);
		list.setCellRenderer(new TitelFarbe(farbe));
	}
	
	public static int isActive = 0;
	
	public static void klick(){
		
		list.addListSelectionListener(new ListSelectionListener(){  

			public void valueChanged(ListSelectionEvent e) {
					
						if(list.getSelectedValue() != null){
							String select = list.getSelectedValue().toString();
							getMangaInfo(select);
							sName = mangaDetail[0];
							
							//#if detailViewManga	
							//int isActive = isActive();//Main.getActive("detailViewManga");
							isActive();
							if(isActive == 1){
								lName.setText(mangaDetail[0]);
								lName.setToolTipText(mangaDetail[0]);
								lZeichner.setText(mangaDetail[1]);
								lZeichner.setToolTipText(mangaDetail[1]);
								lVerlag.setText(mangaDetail[2]);
								lVerlag.setToolTipText(mangaDetail[2]);
								lHab.setText(mangaDetail[3]);
								lAnz.setText(mangaDetail[4]);
								lStatus.setText(mangaDetail[5]);
								lKosten.setText(mangaDetail[6]);
								lNext.setText(mangaDetail[7]);	
							}
							//#endif
						}
						
					
				}	
		});
			
		list.addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e) {
				 if (e.getClickCount() == 2){
					 getNeuEditDetail("Detail", list.getSelectedValue().toString());
				 }
			 }
		});
		
	}
	

	
	public static void setTrefferList(int auswahl){
		treffer = new ArrayList<String>();
		if(auswahl == 0){
			String[] titelListe = getOnlyTitelList();
			
			for(int i = 0; i < titelListe.length; i++){
				treffer.add(titelListe[i]);
			}
			Collections.sort(treffer);
		}
		else if(auswahl == 1){
			String[] autorListe = getAutorList();
			
			//fülle Treffer aus
			for(int i = 0; i < autorListe.length; i++){
				treffer.add(autorListe[i]);
			}
			Collections.sort(treffer);			
		}
		else{

			String[] verlagListe = getVerlagList();
			for(int i = 0; i < verlagListe.length; i++){
				treffer.add(verlagListe[i]);
			}
			Collections.sort(treffer);
		}
	}
	
	public static String[] getFarbe(String[] matchList){
		String[] matchFarbe = new String[matchList.length];
		
		for(int i = 0; i < matchList.length; i++){
			for(int j = 0; j < mangaList.length; j++){
				if(matchList[i] != null && matchList[i].equals(mangaList[j][0]) ){
					matchFarbe[i] = mangaList[j][1];
				}
			}
		}
		
		return matchFarbe;
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
