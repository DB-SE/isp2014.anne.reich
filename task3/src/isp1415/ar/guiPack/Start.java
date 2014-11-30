package isp1415.ar.guiPack;

import isp1415.ar.dbPack.*;
import isp1415.ar.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;





public class Start {
	private JFrame fenster;
//	private JPanel pNORTH, pCENTER, pEAST, pSOUTH;
//	private Color c, defaultColor;
	//Label mit variable Werten
	private DB db;
	private XML xml;
	private String[][] startInfo;
	private DefaultTableModel tabModel;
	private JTable table;
	
	/**
	 * Konstruktor
	 * @param dao
	 */
	//von Main mit Datenbankanbindung
	public Start(DB dao) throws Exception{		
		db = dao;
		getStartInfo();
		
		fensterErzeugen();
		
		//checkStatus();
	}
	
	//von Main ohne Datenbankanbindung
	public Start(XML xml) throws Exception{
		
		this.xml = xml;
		getStartInfo();
		
		fensterErzeugen();
		
		//checkStatus();
	}
	
	//von Bibo kommend
	public Start(DB dao, int second) throws Exception{
		db = dao;
		getStartInfo();
		
		fensterErzeugen();
	}
	
	
	@SuppressWarnings("unused")
	private void checkStatus() throws SQLException {
		final String datum = getDate();
		
		
		//LOg-File generieren (existiert die Datei? wenn ja, gehe zur letzte Zeile und schaue nach ob aktueller Monat drin steht?
		//wenn nein und nein, dann frage ab
		try{
			File f = new File("LogFile.txt");
		    String merke = "";
		    //existiert die Datei bereits?
			if(f.exists()){
				//Lese Datei aus bis zur letzten Zeile und merke diese
				FileReader fr = new FileReader("LogFile.txt");
			    BufferedReader br = new BufferedReader(fr);
		
			    String zeile = "";
			    
			    while ((zeile = br.readLine()) != null) {
	                merke = zeile;
	            }
			    br.close();
			}
			//Vergleiche welches Datum da steht (wenn aktueller Monat -> Abfrage schon stattgefunden
			//wenn nicht -> Abfrage noch stellen
		    if(!merke.startsWith(datum) || merke.equals("")){
		
				final String[] titelRel = new String[startInfo.length];
				int k = 0;
				
				for(int i = 3; i < startInfo.length; i++){
					if(startInfo[i][0] != null){
						//Datum von der aktuellen Manga
						String date2 = startInfo[i][1];
						//schaut nach, ob die Manga im aktuellen Monat rauskommen soll
						if(date2.startsWith(datum)){
							//Splitten Titel von Bandnr
							String[] letter = startInfo[i][0].split("");
							String titel = "";
							String sBandnr = "";
							for(int j = letter.length-1; j >= 0; j--){
								if(letter[j].matches("[0-9]")){
									sBandnr = letter[j] + "" + sBandnr;
								}
								else{
									titel = letter[j] + "" + titel;
								}
							}
												
							int nBandnr = Integer.valueOf(sBandnr);
							//Ist Bandnr der letzte Teil der Manga?
							String[][] mangaDetail = db.getEditDetails(titel);
							if(mangaDetail.length == nBandnr && !mangaDetail[0][2].equals("abgeschlossen")){
								titelRel[k] = startInfo[i][0];
								k++;
							}
						}
					}
				}
				
				//Wenn es für den aktuellen Monat evtl. abgeschlossene übereinstimmungen gibt, werden diese nun bearbeitet
				//in einer Tabelle angezeigt und abgefragt: sind sie wirklich abgeschlossen oder gehen sie evtl weiter?
				if(titelRel[0] != null){
					final JFrame smallFenster = new JFrame();
					smallFenster.setPreferredSize(new Dimension(324, 200));
					smallFenster.setLocationRelativeTo(null);
					smallFenster.setResizable(false);
					
					JPanel pSmall = new JPanel();
					pSmall.setLayout(new BorderLayout());
					
					String[] spaltenName = {"Titel", "Abgeschlossen?"};		
					final DefaultTableModel tmSmall = new DefaultTableModel(spaltenName, 0);
					
					@SuppressWarnings("serial")
					JTable tSmall = new JTable(tmSmall){
						public boolean isCellEditable(int row, int column){
							if(column == 0)
								return false;
							else
								return true;
						}
					};
					
					tSmall.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					
					TableColumn cTitel = tSmall.getColumn("Titel");
					cTitel.setPreferredWidth(180);
					
					TableColumn cHab = tSmall.getColumn("Abgeschlossen?");
					cHab.setCellRenderer(tSmall.getDefaultRenderer(Boolean.class));
					cHab.setCellEditor(tSmall.getDefaultEditor(Boolean.class));
					cHab.setPreferredWidth(110);
					
					for(int i = 0; i < titelRel.length; i++){
						if(titelRel[i] != null){
							Object[] tmp = {titelRel[i], false};
							tmSmall.addRow(tmp);
						}
					}
					
					JScrollPane scroll = new JScrollPane(tSmall);
		
					scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					tSmall.setFillsViewportHeight(true);
					
					pSmall.add(scroll, BorderLayout.CENTER);
					
					
					JButton b = new JButton("Speichern");
					b.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent arg0) {
							try {
								//Liest die Werte aus
								int nAnz = tmSmall.getRowCount();
								for(int i = 0; i < nAnz; i++){
									//wenn eine Manga als abgeschlossen vermerkt wurde, muss sie editiert werden,
									//damit sie rot wird
									if((Boolean) tmSmall.getValueAt(i, 1)){
										String[] letter = ((String) tmSmall.getValueAt(i, 0)).split("");
										String titel = "";
										for(int j = letter.length-1; j >= 0; j--){
											if(!letter[j].matches("[0-9]")){
												titel = letter[j] + "" + titel;
											}
										}
										editManga(titel);
									}
								}
								
					            //schreiben Aktuelle Monat in logfile, damit wir wissen, dass wir im diesen Monat nicht mehr nachfragen müssen
								PrintWriter pWriter = new PrintWriter(new FileWriter("LogFile.txt", true), true);
					            pWriter.println(datum);
								pWriter.close();
					            
								smallFenster.setVisible(false);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
		
						//Manga wird auf abgeschlossen gesetzt
						private void editManga(String titel) {
							try {
								String[][] infos = db.getEditDetails(titel);
								int i = infos.length-1;
								String[] sPreis = infos[i][6].split(" ");
								String sPreis2 = sPreis[0].replace(",", ".");
								double dPreis = Double.valueOf(sPreis2);
								db.editManga(titel, infos[i][0], infos[i][1], infos.length, "Abgeschlossen", Integer.valueOf(infos[i][4]), Integer.valueOf(infos[i][5]), dPreis, infos[i][3], infos[i][7]);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							
						}
					});
					pSmall.add(b, BorderLayout.SOUTH);
					
					smallFenster.add(pSmall);
					smallFenster.pack();
					smallFenster.setVisible(true);
				
				}
		    }
		
		}catch(IOException ioe){
            ioe.printStackTrace();
        }
	}

	/**
	 * erstellt die GUI
	 */
	@SuppressWarnings({"serial" })
	public void fensterErzeugen() throws Exception{

		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO START 
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//JFrame fenster erstellen
		fenster = new JFrame("Anne’s Manga Bibliothek");
		//Größe bestimmen
		fenster.setSize(500, 320);
		fenster.setLocationRelativeTo(null);
		fenster.setResizable(false);

//		defaultColor = fenster.getBackground();
//		c = defaultColor;
		createMenu();
		
		//Gesamtfläche die das Fenster abdeckt
		JPanel flaeche = new JPanel();
		flaeche.setLayout(new BorderLayout());
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO TITEL (oben)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel pNORTH = new JPanel();
		JLabel labTitel = new JLabel("Anne’s Manga Bibliothek");
		labTitel.setFont(new Font("calibri", Font.ITALIC, 20));
		labTitel.setForeground(Color.DARK_GRAY);
		pNORTH.add(labTitel);
		flaeche.add(pNORTH, BorderLayout.NORTH);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO ERSCHEINUNGSDATUM MEINER MANGAS (links)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel pCENTER = new JPanel();
		pCENTER.setLayout(new BorderLayout());
		pCENTER.setBorder(new EmptyBorder(10, 10, 10, 0));

		JLabel newsTitel = new JLabel("Erscheinungsdatum meiner Mangas");
		
		pCENTER.add(newsTitel, BorderLayout.NORTH);

		String[] spaltenName = {"Titel", "Release", "Preis"};		
		tabModel = new DefaultTableModel(spaltenName, 0){
			public Class<?> getColumnClass(int col) {
				switch (col) {
		          case  0: return String.class;
		          case  1: return Date.class;
		          case  2: return String.class;
		          default: return Object.class;
				}
	        }
		};
		
		table = new JTable(tabModel){	
			//verhindert das alle Zellen veraenderbar sind
			public boolean isCellEditable(int row, int column){
					return false;
			}
			
			//gibt den Titel als Tooltip wieder
			 public String getToolTipText(MouseEvent e) {
				 String tip = null;
	             java.awt.Point p = e.getPoint();
	             int rowIndex = rowAtPoint(p);

	             if(getValueAt(rowIndex,0) != null){
	            	 tip = "" + getValueAt(rowIndex, 0);
	             }
	             
				return tip;
				 
			 }
			 
			 //markiert Mangas die im aktuellen Monat herauskommen
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
			    Component c = super.prepareRenderer(renderer, row, column);
			    
			    if (!isRowSelected(table.convertRowIndexToModel(row))){
			    	if(rowColor[table.convertRowIndexToModel(row)][0] != null)
			    		c.setBackground(Color.GREEN);
			    	else
			    		c.setBackground(Color.WHITE);
			    }

			    return c;
			}
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//setzt die Spalten-Inhalte rechtsbündig
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		
		TableColumn cTitel = table.getColumn("Titel");
		cTitel.setPreferredWidth(180);
		
		TableColumn cRelease = table.getColumn("Release");
		cRelease.setPreferredWidth(105);
		cRelease.setCellRenderer(rightRender);
		
		TableColumn cPreis = table.getColumn("Preis");
		cPreis.setPreferredWidth(70);
		cPreis.setCellRenderer(rightRender);

		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
	
		erscheinung();
		
		//Gittefarbe weiß setzten (wie der Hintergrund)
		table.setGridColor(Color.white);
		table.setOpaque(true);
		
		JScrollPane scroll = new JScrollPane(table);
//		scroll.setPreferredSize(new Dimension(100,150));

		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		table.setFillsViewportHeight(true);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabModel){
			public void toggleSortOrder(int column) {
			    if (column != 2) {
			      super.toggleSortOrder(column);
			    }
			  }
		};
		table.setRowSorter(sorter);
		sorter.setComparator(1, new DateStringComparator());
		//pNewsList.add(scroll, BorderLayout.CENTER);
		
		pCENTER.add(scroll, BorderLayout.CENTER);
		
		flaeche.add(pCENTER, BorderLayout.CENTER);

		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO GESAMTERGEBNISSE (rechts)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
		JPanel pEAST = new JPanel();
		pEAST.setBorder(new EmptyBorder(10,10,10,10));
		pEAST.setLayout(new GridLayout(0,1));
		
		JLabel sAktuell = new JLabel("Aktuell: ");
		pEAST.add(sAktuell);
		
		sAktuell.setBorder(new EmptyBorder(0,0,10,0));
		
		JLabel sAnzahlLab = new JLabel("# Mangas: ");
		pEAST.add(sAnzahlLab);
		
		JLabel sAnzahl = new JLabel(startInfo[2][0]);
		pEAST.add(sAnzahl);
		
		JLabel sReiheLab = new JLabel("# Mangareihen: ");
		pEAST.add(sReiheLab);
		
		JLabel sReihe = new JLabel(startInfo[1][0]);
		pEAST.add(sReihe);
		
		JLabel sPreisLab = new JLabel("Gesamtsumme: ");
		pEAST.add(sPreisLab);
		
		JLabel sPreis = new JLabel(startInfo[0][0]);
		pEAST.add(sPreis);
		
		//gesamt.setSize(new Dimension(50, 100));
		
		flaeche.add(pEAST, BorderLayout.EAST);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO BUTTON (unten)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel pSOUTH = new JPanel();
//		pSOUTH.setBackground(c);
		
		JButton bBibo = new JButton("Zur Datenbank");
		bBibo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(db != null)
						new Bibo(db);
					else
						new Bibo(xml);
					fenster.setVisible(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}			
		});
		
		pSOUTH.add(bBibo);
		flaeche.add(pSOUTH, BorderLayout.SOUTH);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO ENDE
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		fenster.add(flaeche);
		
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Fenster sichtbar machen
		fenster.setVisible(true);
		fenster.validate();
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuDatei = new JMenu("Datei");
//		JMenu menuEin = new JMenu("Einstellungen");
		JMenu menuHilfe = new JMenu("Hilfe");
		
		//TODO Menü - Datei 
		//#if dataTransfer
		JMenuItem itemImport = new JMenuItem("Importieren");
		itemImport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					int result = importDB();
					if(result == 1){
						JOptionPane.showMessageDialog(null, "Datenbank erfolgreich Importiert!\nProgramm startet neu.",
								"Importieren", JOptionPane.OK_OPTION, UIManager.getIcon("OptionPane.informationIcon"));
						
						if(db != null)
							new Start(db);
						else
							new Start(xml);
						fenster.setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null, "Vorgang wurde abgebrochen.",
								"Abbruch", JOptionPane.OK_OPTION, UIManager.getIcon("OptionPane.informationIcon"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		menuDatei.add(itemImport);
		JMenuItem itemExport = new JMenuItem("Exportieren");
		itemExport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					int result = exportDB();
					if(result == 0){
						JOptionPane.showMessageDialog(null, "Datenbank erfolgreich Exportiert!\nSie finden Sie unter C:\\Users\\<User>\\MangaDB-Export",
								"Exportieren", JOptionPane.OK_OPTION, UIManager.getIcon("OptionPane.informationIcon"));
					}
					else{
						JOptionPane.showMessageDialog(null, "Probleme beim Exporieren!\nDatenbank wurde NICHT Exportiert!",
								"Fehler", JOptionPane.OK_OPTION, UIManager.getIcon("OptionPane.errorIcon"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		menuDatei.add(itemExport);
		//#endif
		//#if reset
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
							if(db != null)
								new Start(db);
							else
								new Start(xml);
							fenster.setVisible(false);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
		menuDatei.add(itemReset);		
		JSeparator sep = new JSeparator();
		menuDatei.add(sep);
		//#endif
		JMenuItem itemClose = new JMenuItem("Schliessen");
		itemClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);	
			}			
		});
		menuDatei.add(itemClose);

		menuBar.add(menuDatei);
		
		//TODO Menü - Eigenschaften
//		JMenuItem itemBGFarbe = new JMenuItem("Hintergrundfarbe");
//		itemBGFarbe.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				c = JColorChooser.showDialog(null, "Farbauswahl", null);
//            	pNORTH.setBackground(c);
//            	pCENTER.setBackground(c);
//            	pEAST.setBackground(c);
//            	pSOUTH.setBackground(c);				
//			}			
//		});
//		menuEin.add(itemBGFarbe);
//		
//		JMenuItem itemTextFarbe = new JMenuItem("Schriftfarbe");
//		itemTextFarbe.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				c = JColorChooser.showDialog(null, "Farbauswahl", null);
//            	pNORTH.setBackground(c);
//            	pCENTER.setBackground(c);
//            	pEAST.setBackground(c);
//            	pSOUTH.setBackground(c);				
//			}			
//		});
//		menuEin.add(itemTextFarbe);		
//		
//		JMenuItem itemTextArt = new JMenuItem("Schriftart");
//		itemTextArt.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				c = JColorChooser.showDialog(null, "Farbauswahl", null);
//            	pNORTH.setBackground(c);
//            	pCENTER.setBackground(c);
//            	pEAST.setBackground(c);
//            	pSOUTH.setBackground(c);				
//			}			
//		});
//		menuEin.add(itemTextArt);	
//		
//		JMenuItem itemButtonFarbe = new JMenuItem("Buttonfarbe");
//		itemButtonFarbe.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				c = JColorChooser.showDialog(null, "Farbauswahl", null);
//            	pNORTH.setBackground(c);
//            	pCENTER.setBackground(c);
//            	pEAST.setBackground(c);
//            	pSOUTH.setBackground(c);				
//			}			
//		});
//		menuEin.add(itemButtonFarbe);
//		
//		menuBar.add(menuEin);
		
		//TODO Menü - Hilfe 
		JMenuItem itemInhalt = new JMenuItem("Inhalt");
		menuHilfe.add(itemInhalt);
		JMenuItem itemInfo = new JMenuItem("Info...");		
		itemInfo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new Info();
			}			
		});
		menuHilfe.add(itemInfo);
		menuBar.add(menuHilfe);
		
		fenster.setJMenuBar(menuBar);
	}

	/**
	 * liest alle Informationen aus für die Startseite
	 */
	private void getStartInfo() throws Exception{
		if(db != null) startInfo = db.getStart();
		else startInfo = xml.getStart();
	}

	/**
	 * erstellt eine Liste, die im JLabel angezeigt wird
	 */
	String[][] rowColor;
	private void erscheinung() throws Exception {
		String datum = getDate();
		rowColor = new String[startInfo.length][1];
		
		for(int i = 3; i < startInfo.length; i++){
			if(startInfo[i][0] != null){
				Object[] tmp = {startInfo[i][0], startInfo[i][1]+ " ", startInfo[i][2]+ " Euro "};
				String date2 = startInfo[i][1];
				//schaut nach, ob die Manga im aktuellen Monat rauskommen soll
				if(date2.startsWith(datum))
					rowColor[i-3][0] = startInfo[i][0];			

				tabModel.addRow(tmp);
			}
		}
	}
	
	private String getDate() {
		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		
		switch(month){
			case 1: return "Januar " + year; 
			case 2: return "Februar " + year; 
			case 3: return "Maerz " + year; 
			case 4: return "April " + year; 
			case 5: return "Mai " + year; 
			case 6: return "Juni " + year; 
			case 7: return "Juli " + year; 
			case 8: return "August " + year; 
			case 9: return "September " + year; 
			case 10: return "Oktober " + year; 
			case 11: return "November " + year; 
			case 12: return "Dezember " + year; 
			default: return "kein Monat";
		}
		
	}

	private int exportDB() throws SQLException{
		String[][] allMangas;
		if(db != null)
			allMangas = db.exportAll();
		else
			allMangas = xml.exportAll();
		
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		String sDate = df.format(date);
		
		try{
			String path = System.getProperty("user.home");
	        String fileName = "MangaDB_"+ sDate +".txt";
	        String dirName = "MangaDB-Export";
	        File file = new File(path + "/" + dirName + "/" + fileName);
	        File dir = new File(path + "/" + dirName);
	        
	        //erstellt den Ordner
	        if (!dir.exists()) {
	        	dir.mkdir();
	        }
			
			PrintWriter pWriter = new PrintWriter(file);
			for(int i = 0; i < allMangas.length; i++){
				pWriter.println(allMangas[i][0] + "_" + allMangas[i][1] + "_" + allMangas[i][2] + "_" + allMangas[i][3] +
						"_" + allMangas[i][4] + "_" + allMangas[i][5] + "_" + allMangas[i][6] + "_" + allMangas[i][7] +
						"_" + allMangas[i][8] + "_" + allMangas[i][9] + "_");
			}
			pWriter.flush();
			pWriter.close();
			
			return 0;
		}catch(Exception e){
			return 1;
		}
	}
	
	private int importDB(){
		try {
			String home = System.getProperty("user.home");
			String loc = home + "/MangaDB-Export";
			
			FileFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
			//C:\Users\Yuri-Li\workspace\MangaDB
			JFileChooser chooser = new JFileChooser(loc);
			chooser.addChoosableFileFilter(filter);
			int result = chooser.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				String filePlace = chooser.getSelectedFile().getAbsolutePath();
				
				String file = filePlace;

				//löscht die existierende DB und erstellt eine neue
				if(db != null)
					db.deleteAndCreateDB();
				else
					xml.deleteAndCreateDB();
				
				//liest MangaDB.txt aus und speichert alle Zeilen in ein Array
				BufferedReader in = new BufferedReader(new FileReader(file));
				String zeile = null;
				ArrayList<String> list = new ArrayList<String>();
				while ((zeile = in.readLine()) != null) {
					list.add(zeile);
				}
				in.close();
				
				//Liste wird in ein String[] gespeichert
				String[] arr = new String[list.size()];
				for(int i = 0; i < list.size(); i++){
					arr[i] = list.get(i);
				}
				
				//dieser String wird nochmal gesplittet in seine Einzeltteile
				//Titel[i][0], Autor[i][1], Verlag[i][2], BanzAnz[i][3], Status[i][4], 
				//BandNr[i][5], Untertitel[i][6], Preis[i][7], Habe_ich[i][8], Erscheinung[i][9]
				String[][] allMangas = new String[arr.length][10];
				for(int i = 0; i < arr.length; i++){
					String split[] = arr[i].split("_"); 
					allMangas[i][0] = split[0];
					allMangas[i][1] = split[1];
					allMangas[i][2] = split[2];
					allMangas[i][3] = split[3];
					allMangas[i][4] = split[4];
					allMangas[i][5] = split[5];
					allMangas[i][6] = split[6];
					allMangas[i][7] = split[7];
					allMangas[i][8] = split[8];
					allMangas[i][9] = split[9];
				}
				
				//sammelt alle Titel aus der obigen Liste und speichert sie in eine Liste
				ArrayList<String> titelAnz = new ArrayList<String>();
				for(int i = 0; i < allMangas.length; i++){
					titelAnz.add(allMangas[i][0]);
				}
				//durch den HashSet werden doppelte Einträge gelöscht und alles nach Name soriert
				//übergeben wir dann wieder der Liste, nun wissen wir, wie viele Titel es insgesamt gibt (titelAnz.size())
				HashSet<String> hashSet = new HashSet<String>(titelAnz);
			    titelAnz.clear();
			    titelAnz.addAll(hashSet);
			    Collections.sort(titelAnz);
			    
			    //in SetMangas werden von jeder Manga der Titel, Autor, Verlag und Status gespeichert
			    //dann brauchen wir die Baenderanzahl, um an der richtigen Stelle (aus dem riesen String[][]) weiter auszugeben
			    String[][] setMangas = new String[titelAnz.size()][5];
			    int nAnz = 0;
			    for(int i = 0; i < titelAnz.size(); i++){
			    	setMangas[i][0] = allMangas[nAnz][0];
			    	setMangas[i][1] = allMangas[nAnz][1];
			    	setMangas[i][2] = allMangas[nAnz][2];
			    	setMangas[i][3] = allMangas[nAnz][3];
			    	setMangas[i][4] = allMangas[nAnz][4];
			    	int anz = Integer.valueOf(allMangas[nAnz][3]);
			    	
			    	nAnz += anz;
			    }
			    
			    //nun werden alle Bände in richtiger Reihenfolge gespeichert
				String[][] setArrMangas = new String[allMangas.length][5];
				
				for(int j = 0; j < allMangas.length; j++){
					setArrMangas[j][0] = allMangas[j][5];
					setArrMangas[j][1] = allMangas[j][6];
					setArrMangas[j][2] = allMangas[j][7];
					setArrMangas[j][3] = allMangas[j][8];
					setArrMangas[j][4] = allMangas[j][9];				
				}
				
				
				//als nächstes werden die Unterarray erstellt und alles in die Datenbank geschrieben
				nAnz = 0; 
				for(int i = 0; i < setMangas.length; i++){
					String sTitel = setMangas[i][0];
					String sAutor = setMangas[i][1];
					String sVerlag = setMangas[i][2];
					int nAnzBaender = Integer.valueOf(setMangas[i][3]);
					String sStatus = setMangas[i][4];
				
					int[] arrHab = new int[nAnzBaender];
					double[] arrPreis = new double[nAnzBaender];
					String[] arrsPreis = new String[nAnzBaender];
					String[] arrUntertitel = new String[nAnzBaender];
					String[] arrErscheinung = new String[nAnzBaender];
					
					for(int j = 0; j < nAnzBaender; j++){
						
						if(db != null){
							arrHab[j] = Integer.valueOf(setArrMangas[nAnz+j][3]);
							String[] split = setArrMangas[nAnz+j][2].split(" ");
							String dPreis = split[0];
							arrPreis[j] = Double.valueOf(dPreis);
						}
						else{
							if(setArrMangas[nAnz+j][3].equals("1"))
								arrHab[j] = 1;
							else
								arrHab[j] = 0;
							
							arrsPreis[j] = setArrMangas[nAnz+j][2];
						}
						
											
						
						arrUntertitel[j] = setArrMangas[nAnz+j][1];
						arrErscheinung[j] = setArrMangas[nAnz+j][4];
						
						
					}
					
					nAnz += nAnzBaender;
					
					//fügt der neu erstellen DB die ImportParamter hinzu
					if(db != null)
						db.insertManga(sTitel, sAutor, sVerlag, nAnzBaender, sStatus, arrHab, arrPreis, arrUntertitel, arrErscheinung);
					else
						xml.insertManga(sTitel, sAutor, sVerlag, nAnzBaender, sStatus, arrHab, arrsPreis, arrUntertitel, arrErscheinung);
				}
				return 1;
			}
			else {
				return 0;
			}
		} catch (IOException e) {
			System.out.println(e);
			return 0;}
		catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return 0;
		}
	}

	private void resetDB() throws SQLException{
		if(db!= null)
			db.deleteAndCreateDB();
		else
			xml.deleteAndCreateDB();
	}
	
	/**
	 *Vergleicht Datum um dieses dann zu sortieren
	 */
	class DateStringComparator implements Comparator<String>
	{
	    public int compare(String strDate1, String strDate2) 
	    {
	        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMMM yyyy");
	        SimpleDateFormat dateFormat2 = new SimpleDateFormat("MMMM yyyy");
	        
	        try 
	        {
	            if(strDate1 == null || strDate2 == null)
	                return 0;
	            
	            if(strDate1.length() <= 11)
	                dateFormat1 = new SimpleDateFormat("MMMM yyyy");
	            
	            if(strDate2.length() <= 11)
	                dateFormat2 = new SimpleDateFormat("MMMM yyyy");
	            
	            Date date1 = dateFormat1.parse(strDate1);
	            Date date2 = dateFormat2.parse(strDate2);
	            
	            return date1.compareTo(date2);
	        }
	        catch (ParseException e) 
	        {
	            e.printStackTrace();
	        }
	        
	        return 0;
	    }
	}
}
