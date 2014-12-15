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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
	public static JScrollPane gesamtList;
	public static JTextField textField;
	public static JList<String> list;
	public static int searchAuswahl = 0;
	private static ArrayList<String> treffer;
	
	private static DB db;
	private static XML xml;
	public static XML getXml() {
		return xml;
	}

	private static String[][] mangaList;
	private static String[][] mangaListSort;
	public static String[][] getMangaListSort() {
		return mangaListSort;
	}

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

	@SuppressWarnings("unchecked")
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
		//#if byLetter
//			JPanel abcPanel = new JPanel();
//			abcPanel.setLayout(new GridLayout(4,0));
//			abcPanel.setPreferredSize(new Dimension(0,70));
//			abcPanel.setBorder(new EmptyBorder(5,3,0,3));
//			
//			JButton sonder = new JButton("+.0-9");
//			sonder.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("^[A-Z]");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(sonder);
//			
//			JButton a = new JButton("A");
//			a.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("A");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(a);
//			
//			JButton b = new JButton("B");
//			b.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("B");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(b);
//			
//			JButton c = new JButton("C");
//			c.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("C");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(c);
//			
//			JButton d = new JButton("D");
//			d.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("D");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(d);
//			
//			JButton e = new JButton("E");
//			e.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("E");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(e);
//			
//			JButton f = new JButton("F");
//			f.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("F");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(f);
//			
//			JButton g = new JButton("G");
//			g.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("G");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(g);
//			
//			JButton h = new JButton("H");
//			h.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("H");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(h);
//			
//			JButton i = new JButton("I");
//			i.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("I");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(i);
//			
//			JButton j = new JButton("J");
//			j.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("J");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(j);
//			
//			JButton k = new JButton("K");
//			k.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("K");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(k);
//			
//			JButton l = new JButton("L");
//			l.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("L");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(l);
//			
//			JButton m = new JButton("M");
//			m.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("M");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(m);
//			
//			JButton n = new JButton("N");
//			n.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("N");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(n);
//			
//			JButton o = new JButton("O");
//			o.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("O");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(o);
//			
//			JButton p = new JButton("P");
//			p.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("P");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(p);
//			
//			JButton q = new JButton("Q");
//			q.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("Q");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(q);
//			
//			JButton r = new JButton("R");
//			r.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("R");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(r);
//			
//			JButton s = new JButton("S");
//			s.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("S");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(s);
//			
//			JButton t = new JButton("T");
//			t.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("T");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(t);
//			
//			JButton u = new JButton("U");
//			u.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("U");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(u);
//			
//			JButton v = new JButton("V");
//			v.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("V");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(v);
//			
//			JButton w = new JButton("W");
//			w.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("W");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(w);
//			
//			JButton x = new JButton("X");
//			x.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("X");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(x);
//			
//			JButton y = new JButton("Y");
//			y.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("Y");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(y);
//			
//			JButton z = new JButton("Z");
//			z.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					try {
//						sortierung("Z");
//						klick();
//						gesamtList.getViewport().setView(list);
//						
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}				
//				}			
//			});
//			abcPanel.add(z);
//			
//			JButton az = new JButton("A-Z");
//			az.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					sammlung();
//					klick();
//					gesamtList.getViewport().setView(list);				
//				}			
//			});
//			abcPanel.add(az);
		
		JPanel abcPanel = new JPanel();
		
		int isActivebyLetter = Main.getActive("byLetter");
		
		if(isActivebyLetter == 1){
			ClassLoader classLoader = Bibo.class.getClassLoader();
			
			try {
				Class<?> plugin = classLoader.loadClass("isp1415.ar.plugins.byLetter");
				Constructor<?>[] cons = plugin.getDeclaredConstructors();
				Object instance = cons[0].newInstance();
							
				abcPanel = (JPanel) plugin.getMethod("setLetterLayout").invoke(instance);
								
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} 
		}	
		
		
			obenAll.add(abcPanel, BorderLayout.PAGE_START);
		//#endif
		
		// TODO Suche-Bereich
		//#if byTextInput
//			JPanel suchePanel = new JPanel();
//			suchePanel.setLayout(new BorderLayout());
//			suchePanel.setBorder(new EmptyBorder(0,3,5,3));
//			
//			//Combo-Box
//			String[] nachList = {"nach Titel:", "nach Autor:", "nach Verlag:"};
//			final JComboBox<String> nach = new JComboBox<String>(nachList);
//			nach.setPreferredSize(new Dimension(112, 25));
//			setTrefferList(searchAuswahl);
//			//erkennt, welche Suche-Auswahl aktiviert ist (nach Zahlen)
//			nach.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e) {
//					try {
//						searchAuswahl = nach.getSelectedIndex();
//						setTrefferList(searchAuswahl);
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//				}			
//			});
//			
//			suchePanel.add(nach, BorderLayout.LINE_START);
//			
//			//Textfeld
//			textField = new JTextField(50);		
//			suchePanel.add(textField, BorderLayout.CENTER);	
//			autoComplete();
//			
//			//Suchen-Button
//			JButton suchButton = new JButton();
//			suchButton.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent arg0) {
//					String match = textField.getText();
//					
//					ArrayList<String> matchTitelDmy = new ArrayList<String>();
//					
//					//alle Möglichkeiten abfangen
//					if(searchAuswahl == 0){
//						matchTitelDmy.add(match);
//					}
//					else{
//						try {
//							if(searchAuswahl == 1)
//								matchTitelDmy = getTitelByAutor(match);
//							else
//								matchTitelDmy = getTitelByVerlag(match);
//						} catch (SQLException e) {
//							e.printStackTrace();
//						}
//					}
//					
//					//diese in String[] packen
//					String[] matchTitel = new String[matchTitelDmy.size()];
//					
//					for(int j = 0; j < matchTitelDmy.size(); j++){
//						matchTitel[j] = matchTitelDmy.get(j);
//					}
//					
//				    list = new JList<String>(matchTitel);
//			        String[] farbe = getFarbe(matchTitel);
//				    list.setCellRenderer(new TitelFarbe(farbe));
//			        klick();
//				    gesamtList.getViewport().setView(list);
//				}
//			});
//			try {
//				BufferedImage bi = ImageIO.read(ImageIO.class.getResource("/suchenIcon.gif"));
//				suchButton.setIcon(new ImageIcon(bi));
//			} catch (IOException e2) {
//				e2.printStackTrace();
//			}
//			suchButton.setPreferredSize(new Dimension(25, 25));
//			
//			suchePanel.add(suchButton, BorderLayout.LINE_END);		
//			
			JPanel suchePanel = new JPanel();
			
			int isActiveByText = Main.getActive("byTextInput");
			
			if(isActiveByText == 1){
				ClassLoader classLoader = Bibo.class.getClassLoader();
				
				try {
					Class<?> plugin = classLoader.loadClass("isp1415.ar.plugins.byTextInput");
					Constructor<?>[] cons = plugin.getDeclaredConstructors();
					Object instance = cons[0].newInstance();
								
					suchePanel = (JPanel) plugin.getMethod("setInputLayout").invoke(instance);
									
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				} 
			}	
			
			obenAll.add(suchePanel, BorderLayout.CENTER);
		//#endif
		
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
		
		//#if detailViewManga		
		int isActiveDetail = Main.getActive("detailViewManga");
		
		if(isActiveDetail == 1){
			ClassLoader classLoader = Bibo.class.getClassLoader();
			
			try {
				Class<?> plugin = classLoader.loadClass("isp1415.ar.plugins.detailViewManga");
				Constructor<?>[] cons = plugin.getDeclaredConstructors();
				Object instance = cons[0].newInstance(detailseditPanel, lName, lZeichner, lVerlag, lStatus, lHab, lNext, lKosten, lAnz);
							
				plugin.getMethod("createDetailView").invoke(instance);
				detailseditPanel = (JPanel) plugin.getMethod("getDetailseditPanel").invoke(instance);
				lName = (JLabel) plugin.getMethod("getlName").invoke(instance);
				lZeichner = (JLabel) plugin.getMethod("getlZeichner").invoke(instance);
				lVerlag = (JLabel) plugin.getMethod("getlVerlag").invoke(instance);
				lStatus = (JLabel) plugin.getMethod("getlStatus").invoke(instance);
				lHab = (JLabel) plugin.getMethod("getlHab").invoke(instance);
				lNext = (JLabel) plugin.getMethod("getlNext").invoke(instance);
				lKosten = (JLabel) plugin.getMethod("getlKosten").invoke(instance);
				lAnz = (JLabel) plugin.getMethod("getlAnz").invoke(instance);
				
				
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} 
		}
		//#endif
		
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
	public static void sortierung(String letter) throws SQLException{
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
	
	public static void klick(){
		
		list.addListSelectionListener(new ListSelectionListener(){  

			public void valueChanged(ListSelectionEvent e) {
					try {
						if(list.getSelectedValue() != null){
							String select = list.getSelectedValue().toString();
							getMangaInfo(select);
							sName = mangaDetail[0];
							
							//#if detailViewManga	
							int isActive = Main.getActive("detailViewManga");							
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
	

	
	public static void setTrefferList(int auswahl) throws SQLException{
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
	
	private void getTitelColorList() throws SQLException {
		if(db != null)
			mangaList = db.getMangareiheTitel();
		else
			mangaList = xml.getMangareiheTitel();
		
	}
	
	public static void getTitelColorListSort(String letter) throws SQLException{
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
	
	private static String[] getAutorList() throws SQLException{
		if(db != null)
			return db.getAutor();
		else
			return xml.getAutor();
	}
	
	public static ArrayList<String> getTitelByAutor(String autor) throws SQLException{
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
	
	private static String[] getVerlagList() throws SQLException{
		if(db != null)
			return db.getVerlag();
		else
			return xml.getVerlag();
	}
	
	public static ArrayList<String> getTitelByVerlag(String verlag) throws SQLException{
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
	
	private static String[] getOnlyTitelList() throws SQLException{
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
