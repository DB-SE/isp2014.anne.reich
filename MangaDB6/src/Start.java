import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Start {
  
  private DatabaseAccess _dba;
  public String[][] _startInfo;
  private JFrame fenster;
  //private JTable table;
  
  public Start(DatabaseAccess dba) {
    this._dba = dba;
    
    getStartInfo();
    fensterErzeugen();   
  }
  
  public Start() {
    
  }
  
  public void fensterErzeugen() {

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // TODO START 
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //JFrame fenster erstellen
    fenster = new JFrame("Anne’s Manga Bibliothek");
    //Größe bestimmen
    fenster.setSize(500, 320);
    fenster.setLocationRelativeTo(null);
    fenster.setResizable(false);
    
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
        
    flaeche = nextMangaTable(flaeche);   
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // TODO GESAMTERGEBNISSE (rechts)
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
    JPanel pEAST = new JPanel();
    pEAST.setBorder(new EmptyBorder(10, 10, 10, 10));
    pEAST.setLayout(new GridLayout(0, 1));
    
    JLabel sAktuell = new JLabel("Aktuell: ");
    pEAST.add(sAktuell);
    
    sAktuell.setBorder(new EmptyBorder(0, 0, 10, 0));
    
    JLabel sAnzahlLab = new JLabel("# Mangas: ");
    pEAST.add(sAnzahlLab);
    
    JLabel sAnzahl = new JLabel(_startInfo[2][0]);
    pEAST.add(sAnzahl);
    
    JLabel sReiheLab = new JLabel("# Mangareihen: ");
    pEAST.add(sReiheLab);
    
    JLabel sReihe = new JLabel(_startInfo[1][0]);
    pEAST.add(sReihe);
    
    JLabel sPreisLab = new JLabel("Gesamtsumme: ");
    pEAST.add(sPreisLab);
    
    JLabel sPreis = new JLabel(_startInfo[0][0]);
    pEAST.add(sPreis);
    
    //gesamt.setSize(new Dimension(50, 100));
    
    if (isFeatureTableOn()) {
      flaeche.add(pEAST, BorderLayout.EAST);
    } else {
      flaeche.add(pEAST, BorderLayout.CENTER);
    }
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // TODO BUTTON (unten)
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    JPanel pSOUTH = new JPanel();
//    pSOUTH.setBackground(c);
    
    JButton bBibo = new JButton("Zur Datenbank");
    bBibo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        new Bibo(_dba);
        fenster.setVisible(false);   
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
    JMenu menuHilfe = new JMenu("Hilfe");
    
    //TODO Menü - Datei     
    menuDatei = importMenuItem(menuDatei);
    menuDatei = exportMenuItem(menuDatei);
    menuDatei = resetMenuItem(menuDatei);
    
    JMenuItem itemClose = new JMenuItem("Schliessen");
    itemClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        System.exit(0); 
      }     
    });
    menuDatei.add(itemClose);

    menuBar.add(menuDatei);
    
    //TODO Menü - Eigenschaften
    menuBar = settingMenu(menuBar);
    
    //TODO Menü - Hilfe 
    JMenuItem itemInhalt = new JMenuItem("Inhalt");
    menuHilfe.add(itemInhalt);
    JMenuItem itemInfo = new JMenuItem("Info...");    
    itemInfo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
//        new Info();
      }     
    });
    menuHilfe.add(itemInfo);
    menuBar.add(menuHilfe);
    
    fenster.setJMenuBar(menuBar);
  }
  
  
  
  
  private JPanel nextMangaTable(JPanel flaeche) {  
    
    return flaeche;
  }
  
  private boolean isFeatureTableOn() {
    
    return false;
  }
  
  private JMenu importMenuItem(JMenu menuDatei) {
    
    return menuDatei;
  }
  
  private JMenu exportMenuItem(JMenu menuDatei) {
    
    return menuDatei;
  }
  
 private JMenu resetMenuItem(JMenu menuDatei) {
    
    return menuDatei;
  }
 
 private JMenuBar settingMenu(JMenuBar menuBar) {
   
   return menuBar;
 }

  public String[][] getStartInfo() {
    this._startInfo = _dba.getStart();
    
    return _startInfo;
  }
}
