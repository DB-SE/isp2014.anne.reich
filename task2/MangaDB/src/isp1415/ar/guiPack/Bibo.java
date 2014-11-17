package isp1415.ar.guiPack;

import isp1415.ar.Main;
import isp1415.ar.dbPack.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
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
	private static JScrollPane gesamtList;
	private static JTextField textField;
	private static JList<String> list;
	private static int searchAuswahl = 0;
	private static ArrayList<String> treffer;
	
	private static DB db;
	private static XML xml;
	private static boolean[] features = Main.features;
	private static String[][] mangaList;
	private String[][] mangaListSort;
	private static String[] mangaDetail;
	
	public Bibo(DB dao) throws SQLException{
		db = dao;
		getTitelColorList();
		
		fensterErzeugen();		
	}
	
	public Bibo(XML xml) throws SQLException{
		Bibo.xml = xml;
		getTitelColorList();
		
		fensterErzeugen();
	}

	private void fensterErzeugen() throws SQLException {
		
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
		
		// TODO Buchstaben Buttons
		if(features[2]){
			JPanel abcPanel = new JPanel();
			abcPanel.setLayout(new GridLayout(4,0));
			abcPanel.setPreferredSize(new Dimension(0,70));
			abcPanel.setBorder(new EmptyBorder(5,3,0,3));
			
			JButton sonder = new JButton("+.0-9");
			sonder.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("^[A-Z]");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(sonder);
			
			JButton a = new JButton("A");
			a.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("A");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(a);
			
			JButton b = new JButton("B");
			b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("B");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(b);
			
			JButton c = new JButton("C");
			c.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("C");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(c);
			
			JButton d = new JButton("D");
			d.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("D");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(d);
			
			JButton e = new JButton("E");
			e.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("E");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(e);
			
			JButton f = new JButton("F");
			f.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("F");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(f);
			
			JButton g = new JButton("G");
			g.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("G");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(g);
			
			JButton h = new JButton("H");
			h.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("H");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(h);
			
			JButton i = new JButton("I");
			i.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("I");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(i);
			
			JButton j = new JButton("J");
			j.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("J");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(j);
			
			JButton k = new JButton("K");
			k.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("K");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(k);
			
			JButton l = new JButton("L");
			l.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("L");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(l);
			
			JButton m = new JButton("M");
			m.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("M");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(m);
			
			JButton n = new JButton("N");
			n.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("N");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(n);
			
			JButton o = new JButton("O");
			o.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("O");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(o);
			
			JButton p = new JButton("P");
			p.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("P");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(p);
			
			JButton q = new JButton("Q");
			q.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("Q");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(q);
			
			JButton r = new JButton("R");
			r.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("R");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(r);
			
			JButton s = new JButton("S");
			s.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("S");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(s);
			
			JButton t = new JButton("T");
			t.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("T");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(t);
			
			JButton u = new JButton("U");
			u.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("U");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(u);
			
			JButton v = new JButton("V");
			v.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("V");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(v);
			
			JButton w = new JButton("W");
			w.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("W");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(w);
			
			JButton x = new JButton("X");
			x.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("X");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(x);
			
			JButton y = new JButton("Y");
			y.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("Y");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			});
			abcPanel.add(y);
			
			JButton z = new JButton("Z");
			z.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					try {
						sortierung("Z");
						klick();
						gesamtList.getViewport().setView(list);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}				
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
			
			obenAll.add(abcPanel, BorderLayout.PAGE_START);
		}
		
		// TODO Suche-Bereich
		if(!features[2]){
			JPanel suchePanel = new JPanel();
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
					try {
						searchAuswahl = nach.getSelectedIndex();
						setTrefferList(searchAuswahl);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
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
				@SuppressWarnings("unchecked")
				public void actionPerformed(ActionEvent arg0) {
					String match = textField.getText();
					
					ArrayList<String> matchTitelDmy = new ArrayList<String>();
					
					//alle Möglichkeiten abfangen
					if(searchAuswahl == 0){
						matchTitelDmy.add(match);
					}
					else{
						try {
							if(searchAuswahl == 1)
								matchTitelDmy = getTitelByAutor(match);
							else
								matchTitelDmy = getTitelByVerlag(match);
						} catch (SQLException e) {
							e.printStackTrace();
						}
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
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			suchButton.setPreferredSize(new Dimension(25, 25));
			
			suchePanel.add(suchButton, BorderLayout.LINE_END);		
			
			obenAll.add(suchePanel, BorderLayout.CENTER);
		}
		
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
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					if(db != null)
						new Start(db,2);
					else
						new Start(xml);
					fenster.setVisible(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}			
		});
		backnewPanel.add(back, BorderLayout.LINE_START);
		
		//neu-Button öffnet die manga-erstell-seite
		JButton neu = new JButton("Neuer Manga");	
		neu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(db != null)
					new NeuEditDetail(db, "neu", "-");
				else
					new NeuEditDetail(xml, "neu", "-");
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
		
		if(features[3]){		
			JPanel detailPanel = new JPanel();
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
			
			detailseditPanel.add(detailPanel, BorderLayout.CENTER);
		}
		
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
        			if(db != null)
        				new NeuEditDetail(db, "Edit" ,sName);
        			else
        				new NeuEditDetail(xml, "Edit" ,sName);
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
					if(sName != null)
						if(db != null)
							new NeuEditDetail(db, "Detail", sName);
						else
							new NeuEditDetail(xml, "Detail", sName);
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
	
	private static void autoComplete() {
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
												try {
													if(searchAuswahl == 1)
														matchTitelDmy = getTitelByAutor(match);
													else
														matchTitelDmy = getTitelByVerlag(match);
												} catch (SQLException e) {
													e.printStackTrace();
												}
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
											try {
												if(searchAuswahl == 1)
													matchTitelDmy = getTitelByAutor(match);
												else
													matchTitelDmy = getTitelByVerlag(match);
											} catch (SQLException e) {
												e.printStackTrace();
											}
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
	private void sammlung(){
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
	private void sortierung(String letter) throws SQLException{
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
	
	private static void klick(){
		
		list.addListSelectionListener(new ListSelectionListener(){  

			public void valueChanged(ListSelectionEvent e) {
					try {
						if(list.getSelectedValue() != null){
							String select = list.getSelectedValue().toString();
							getMangaInfo(select);
							sName = mangaDetail[0];
							
							if(features[3]){							
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
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}	
		});
			
		list.addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e) {
				 if (e.getClickCount() == 2){
					 if(db != null)
						 new NeuEditDetail(db, "Detail", list.getSelectedValue().toString());
					 else
						 new NeuEditDetail(xml, "Detail", list.getSelectedValue().toString());
				 }
			 }
		});
		
	}
	

	
	private void setTrefferList(int auswahl) throws SQLException{
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
	
	private static String[] getFarbe(String[] matchList){
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
	
	private void getTitelColorList() throws SQLException {
		if(db != null)
			mangaList = db.getMangareiheTitel();
		else
			mangaList = xml.getMangareiheTitel();
		
	}
	
	private void getTitelColorListSort(String letter) throws SQLException{
		if(db != null)
			mangaListSort = db.getReiheStartsWith(letter);
		else
			mangaListSort = xml.getReiheStartsWith(letter);
	}
	
	private static void getMangaInfo(String mangaTitel) throws SQLException{
		if(db != null)
			mangaDetail = db.getBiboDetails(mangaTitel);
		else
			mangaDetail = xml.getBiboDetails(mangaTitel);
		
	}
	
	private String[] getAutorList() throws SQLException{
		if(db != null)
			return db.getAutor();
		else
			return xml.getAutor();
	}
	
	private static ArrayList<String> getTitelByAutor(String autor) throws SQLException{
		String[] titel;
		if(db != null)
			titel = db.getTitelByAutor(autor);
		else
			titel = xml.getTitelByAutor(autor);
		ArrayList<String> arrTitel = new ArrayList<String>();
		Collections.sort(arrTitel);
		
		for(int i = 0; i < titel.length; i++){
			arrTitel.add(titel[i]);
		}		
		
		return arrTitel;
	}
	
	private String[] getVerlagList() throws SQLException{
		if(db != null)
			return db.getVerlag();
		else
			return xml.getVerlag();
	}
	
	private static ArrayList<String> getTitelByVerlag(String verlag) throws SQLException{
		String[] titel;
		if(db != null)
			titel = db.getTitelByVerlag(verlag);
		else
			titel = xml.getTitelByVerlag(verlag);
		
		ArrayList<String> arrTitel = new ArrayList<String>();
		Collections.sort(arrTitel);
		
		for(int i = 0; i < titel.length; i++){
			arrTitel.add(titel[i]);
		}		
		
		return arrTitel;
	}
	
	private String[] getOnlyTitelList() throws SQLException{
		String[][] titelListe;
		if(db != null)
			titelListe = db.getMangareiheTitel();
		else
			titelListe = xml.getMangareiheTitel();
		
		String[] titel = new String[titelListe.length];
		
		for(int i = 0; i < titelListe.length; i++){
			titel[i] = titelListe[i][0];
		}
		
		return titel;		
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

