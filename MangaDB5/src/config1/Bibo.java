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
import java.awt.GridLayout; 

import javax.swing.JLabel; 
import java.util.Collections; import java.awt.*; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener; 
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent; 
import java.lang.reflect.Constructor; 
import java.lang.reflect.InvocationTargetException; 
import javax.swing.*; 
import javax.swing.event.DocumentEvent; 
import javax.swing.event.DocumentListener; 
import javax.swing.event.ListSelectionEvent; 
import javax.swing.event.ListSelectionListener; 
import javax.swing.text.BadLocationException; 

public   class  Bibo {
	
	
	public JPanel abcPanel  = null;

	
	
	 private void  byLetter__wrappee__byLetter  (){
		
		abcPanel = new JPanel();
		abcPanel.setLayout(new GridLayout(4,0));
		abcPanel.setPreferredSize(new Dimension(0,70));
		abcPanel.setBorder(new EmptyBorder(5,3,0,3));
		
		JButton sonder = new JButton("+.0-9");
		sonder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("^[A-Z]");
					klick();
					gesamtList.getViewport().setView(list);
					
								
			}			
		});
		abcPanel.add(sonder);
		
		JButton a = new JButton("A");
		a.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("A");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(a);
		
		JButton b = new JButton("B");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("B");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(b);
		
		JButton c = new JButton("C");
		c.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("C");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(c);
		
		JButton d = new JButton("D");
		d.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("D");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(d);
		
		JButton e = new JButton("E");
		e.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("E");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(e);
		
		JButton f = new JButton("F");
		f.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("F");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(f);
		
		JButton g = new JButton("G");
		g.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("G");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(g);
		
		JButton h = new JButton("H");
		h.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("H");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(h);
		
		JButton i = new JButton("I");
		i.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("I");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(i);
		
		JButton j = new JButton("J");
		j.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("J");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(j);
		
		JButton k = new JButton("K");
		k.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("K");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(k);
		
		JButton l = new JButton("L");
		l.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("L");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(l);
		
		JButton m = new JButton("M");
		m.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("M");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(m);
		
		JButton n = new JButton("N");
		n.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("N");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(n);
		
		JButton o = new JButton("O");
		o.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("O");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(o);
		
		JButton p = new JButton("P");
		p.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("P");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(p);
		
		JButton q = new JButton("Q");
		q.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("Q");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(q);
		
		JButton r = new JButton("R");
		r.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("R");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(r);
		
		JButton s = new JButton("S");
		s.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("S");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(s);
		
		JButton t = new JButton("T");
		t.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("T");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(t);
		
		JButton u = new JButton("U");
		u.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("U");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(u);
		
		JButton v = new JButton("V");
		v.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("V");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(v);
		
		JButton w = new JButton("W");
		w.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("W");
					klick();
					gesamtList.getViewport().setView(list);
					
			}			
		});
		abcPanel.add(w);
		
		JButton x = new JButton("X");
		x.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("X");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(x);
		
		JButton y = new JButton("Y");
		y.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("Y");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(y);
		
		JButton z = new JButton("Z");
		z.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("Z");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(z);
		
		JButton az = new JButton("A-Z");
		az.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sammlung();
				klick();
				gesamtList.getViewport().setView(list);				
			}			
		});
		abcPanel.add(az);
		
	}

	
	
	public void byLetter(){ byLetter__wrappee__byLetter(); }

	
	public JPanel suchePanel  = null;

	
	
 private void  byTextInput__wrappee__byTextInput  (){
		
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

	
		
	public void byTextInput(){ byTextInput__wrappee__byTextInput(); }

	
	public JPanel detailPanel  = null;

	
	
	 private void  getDetailPanel__wrappee__detailViewManga  (){	
		detailPanel = new JPanel();
		detailPanel.setLayout(new GridLayout(0,2));
		detailPanel.setBorder(new EmptyBorder(0,3,0,3));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		
		JPanel leftText = new JPanel();
		leftText.setPreferredSize(new Dimension(60, 65));
		leftText.setLayout(new GridLayout(4,0));
			
			JLabel name = new JLabel("Titel:");
			leftText.add(name);

			JLabel autor = new JLabel("Zeichner:");
			leftText.add(autor);
			
			JLabel verlag = new JLabel("Verlag:");
			leftText.add(verlag);
			
			JLabel status = new JLabel("Status:");
			leftText.add(status);
			
		leftPanel.add(leftText, BorderLayout.LINE_START);
		
		JPanel leftValue = new JPanel();
		leftValue.setLayout(new GridLayout(4,0));
		
			lName = new JLabel();
			leftValue.add(lName);

			lZeichner = new JLabel();
			leftValue.add(lZeichner);
		
			lVerlag = new JLabel();
			leftValue.add(lVerlag);
		
			lStatus = new JLabel();
			leftValue.add(lStatus);

		leftPanel.add(leftValue, BorderLayout.CENTER);
		
		detailPanel.add(leftPanel);
			
			//-------
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		JPanel rightText = new JPanel();
		rightText.setPreferredSize(new Dimension(95, 65));
		rightText.setLayout(new GridLayout(4,0));

			JLabel hab = new JLabel("Habe ich:");
			rightText.add(hab);
		
			JLabel gibt = new JLabel("Gibt es:");
			rightText.add(gibt);
		
			JLabel kosten = new JLabel("Gesamtkosten:");
			rightText.add(kosten);
		
			JLabel next = new JLabel("Naechster Band:");
			rightText.add(next);
			
		rightPanel.add(rightText, BorderLayout.LINE_START);
		
		JPanel rightValue = new JPanel();
		rightValue.setLayout(new GridLayout(4,0));
		
			lHab = new JLabel();
			rightValue.add(lHab);

			lAnz = new JLabel();
			rightValue.add(lAnz);
		
			lKosten = new JLabel();
			rightValue.add(lKosten);
		
			lNext = new JLabel();
			rightValue.add(lNext);

		rightPanel.add(rightValue, BorderLayout.CENTER);
			
		detailPanel.add(rightPanel);
	}

	
	
	public void getDetailPanel(){ getDetailPanel__wrappee__detailViewManga(); }

	
	
	 private static void  isActive__wrappee__detailViewManga  (){
		isActive = 1;
	}

	
	
	public static void isActive(){ isActive__wrappee__detailViewManga(); }

	
	
	private static XML xml  ;

	
	
	public static XML getXml() {
		return xml;
	}

	
	
	public Bibo(XML xml){
		Bibo.xml = xml;
		getTitelColorList();
		
		fensterErzeugen();
	}

	
	
	public void getStart(){
		try {
			new Start(xml);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	
	
	public static void getNeuEditDetail(String action, String title){
		new NeuEditDetail(xml, action, title);
	}

	
	
	private void getTitelColorList(){
		mangaList = xml.getMangareiheTitel();
		
	}

	
	
	
	public static void getTitelColorListSort(String letter){
		
			mangaListSort = xml.getReiheStartsWith(letter);
	}

	
	
	private static void getMangaInfo(String mangaTitel){
		
			mangaDetail = xml.getBiboDetails(mangaTitel);
		
	}

	
	
	private static String[] getAutorList(){
		
			return xml.getAutor();
	}

	
	
	public static ArrayList<String> getTitelByAutor(String autor){
		String[] titel;
		
			titel = xml.getTitelByAutor(autor);
		ArrayList<String> arrTitel = new ArrayList<String>();
		Collections.sort(arrTitel);
		
		for(int i = 0; i < titel.length; i++){
			arrTitel.add(titel[i]);
		}		
		
		return arrTitel;
	}

	
	
	private static String[] getVerlagList(){
		
			return xml.getVerlag();
	}

	
	
	public static ArrayList<String> getTitelByVerlag(String verlag){
		String[] titel;
		
			titel = xml.getTitelByVerlag(verlag);
		
		ArrayList<String> arrTitel = new ArrayList<String>();
		Collections.sort(arrTitel);
		
		for(int i = 0; i < titel.length; i++){
			arrTitel.add(titel[i]);
		}		
		
		return arrTitel;
	}

	
	
	private static String[] getOnlyTitelList(){
		String[][] titelListe;
		
			titelListe = xml.getMangareiheTitel();
		
		String[] titel = new String[titelListe.length];
		
		for(int i = 0; i < titelListe.length; i++){
			titel[i] = titelListe[i][0];
		}
		
		return titel;		
	}

	
	
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

	
	

	private static String[][] mangaList;

	
	private static String[][] mangaListSort;

	
	public static String[][] getMangaListSort() {
		return mangaListSort;
	}

	

	private static String[] mangaDetail;

	


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

	
	 
	private static  class  CompletionTask  implements Runnable {
		
	 
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
	static  class  TitelFarbe  extends JLabel  implements ListCellRenderer {
		

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
