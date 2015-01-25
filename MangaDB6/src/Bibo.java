import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


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
  
  private static DatabaseAccess _dba;
  private static String[][] mangaList;
  private String[][] mangaListSort;
  private static String[] mangaDetail;
  
  public Bibo(DatabaseAccess dba) {
    this._dba = dba;
    getTitelColorList();
    
    fensterErzeugen();
  }
  
  private void fensterErzeugen() {
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
    
    obenAll = byLetter(obenAll);
    obenAll = byTextInput(obenAll);
    
    // TODO zurück + neuerManga-Button
    JPanel backnewPanel = new JPanel();
    backnewPanel.setLayout(new BorderLayout());
    backnewPanel.setBorder(new EmptyBorder(0, 3, 5, 3));
    
    //back-Button führt zur Startseite zurück
    JButton back = new JButton();
    back.setPreferredSize(new Dimension(25, 25));
    try {
      BufferedImage bi = ImageIO.read(ImageIO.class.getResource("/backIcon.gif"));
      back.setIcon(new ImageIcon(bi));
    } catch (IOException e2) {
      e2.printStackTrace();
    }
    back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          new Start(_dba);
          fenster.setVisible(false);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        
      }     
    });
    backnewPanel.add(back, BorderLayout.LINE_START);
    
    //neu-Button öffnet die manga-erstell-seite
    JButton neu = new JButton("Neuer Manga"); 
    neu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        System.out.println("Nicht implementiert");
//        if(db != null)
//          new NeuEditDetail(db, "neu", "-");
//        else
//          new NeuEditDetail(xml, "neu", "-");
//        fenster.setVisible(false);
      }
    });
    neu.setPreferredSize(new Dimension(112, 20));    
    backnewPanel.add(neu, BorderLayout.LINE_END);
    
    obenAll.add(backnewPanel, BorderLayout.PAGE_END);
    
    flaeche.add(obenAll, BorderLayout.PAGE_START);
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // TODO SAMMLUNG (mitte)
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    JPanel samml = new JPanel();
    samml.setLayout(new BorderLayout());
    samml.setBorder(new EmptyBorder(0, 3, 0, 3));
    
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
    detailseditPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
    
    detailseditPanel = detailViewManga(detailseditPanel);
    
    //TODO Edit + Detail Button
    JPanel edButtonsPanel = new JPanel();
    edButtonsPanel.setLayout(new GridLayout(0, 2));
    
    JPanel buttonEdit = new JPanel();
    buttonEdit.setBorder(new EmptyBorder(0, 3, 0, 155));
    
    JButton edit = new JButton("Editieren");
    edit.addActionListener(new ActionListener()
        { public void actionPerformed(ActionEvent evt) {

          System.out.println("Nicht implementiert");
//            if(sName != null){
//              if(db != null)
//                new NeuEditDetail(db, "Edit" ,sName);
//              else
//                new NeuEditDetail(xml, "Edit" ,sName);
//              fenster.setVisible(false);
//            }
             }
        });
    
    buttonEdit.add(edit);
    
    edButtonsPanel.add(buttonEdit);
    
    JPanel buttonDetail = new JPanel();
    buttonDetail.setBorder(new EmptyBorder(0, 0, 0, 110));
    
    JButton details = new JButton("Baender Uebersicht");    
    details.addActionListener(new ActionListener()
          { public void actionPerformed(ActionEvent evt) { 

            System.out.println("Nicht implementiert");
//          if(sName != null)
//            if(db != null)
//              new NeuEditDetail(db, "Detail", sName);
//            else
//              new NeuEditDetail(xml, "Detail", sName);
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
  
  public void refreshGesamtList(JList<String> list) {
    gesamtList.getViewport().setView(list);
  }
  
  @SuppressWarnings("unchecked")
  private void sammlung() {
    String[] samml = new String[mangaList.length];
    String[] farbe = new String[mangaList.length];
    
    for (int i = 0; i < mangaList.length; i++) {
      samml[i] = mangaList[i][0];
      farbe[i] = mangaList[i][1];
    }
    
    list = new JList<String>(samml);
    list.setCellRenderer(new TitelFarbe(farbe));
  }
  
  private static void klick() {
    
    list.addListSelectionListener(new ListSelectionListener() {  

      public void valueChanged(ListSelectionEvent e) {
       
            if (list.getSelectedValue() != null) {
              String select = list.getSelectedValue().toString();
              String[] mangaDetail = _dba.getBiboDetails(select);
              sName = mangaDetail[0];
            
                refreshLabels(mangaDetail);
            }
        } 
    });
      
    list.addMouseListener(new MouseAdapter() {
       public void mouseClicked(MouseEvent e) {
         if (e.getClickCount() == 2) {
           System.out.println("Nicht implementiert");
//           if(db != null)
//             new NeuEditDetail(db, "Detail", list.getSelectedValue().toString());
//           else
//             new NeuEditDetail(xml, "Detail", list.getSelectedValue().toString());
         }
       }
    });
    
  }
  
  public static void refreshLabels(String[] mangaDetail) {
    
  }
  
  
  
  private void getTitelColorList() {
    mangaList = _dba.getMangareiheTitel();
  }
  
  private JPanel byLetter(JPanel obenAll) {
    
    return obenAll;
  }
  
  private JPanel byTextInput(JPanel obenAll) {
    
    return obenAll;
  }
  
  private JPanel detailViewManga(JPanel detailseditPanel) {
    
    return detailseditPanel;
  }
  
  @SuppressWarnings({ "serial", "rawtypes" })
  static class TitelFarbe extends JLabel implements ListCellRenderer {

    private String[] color;
    
    public TitelFarbe(String[] color) {
      setOpaque(true);
      this.color = color;
    }

    public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus) {
      
      if (value != null) {
        setText(value.toString());
        
        if (isSelected) {
          setBorder(BorderFactory.createLineBorder(Color.black));
        } else {
          setBorder(new EmptyBorder(0, 0, 0, 0));
        }
  
        if (color[index].equals("gruen")) {
          setBackground(Color.GREEN);
        } else if (color[index].equals("rot")) {
          setBackground(Color.RED);
        } else {
          setBackground(Color.YELLOW);
        }
        
        return this;
      } else {
        setBackground(Color.WHITE);
        setText("");
        return this;
      }
    }
    
  }

}
