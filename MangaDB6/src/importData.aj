import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public aspect importData {
  
  private JFrame fenster;
  private DatabaseAccess dba;
  
  
  pointcut createMenuItem(JMenu menuDatei):
    call(private JMenu Start.importMenuItem(JMenu)) && args(menuDatei);
  
  JMenu around(JMenu menuDatei): createMenuItem(menuDatei) {
    JMenuItem itemImport = new JMenuItem("Importieren");
    itemImport.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        try {
          int result = importDB();
          if(result == 1){
            JOptionPane.showMessageDialog(null, "Datenbank erfolgreich Importiert!\nProgramm startet neu.",
                "Importieren", JOptionPane.OK_OPTION, UIManager.getIcon("OptionPane.informationIcon"));
            
            new Start(dba);
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
    
    return menuDatei;
  }
  
  after(JMenu menuDatei): createMenuItem(menuDatei) {
    try {
      Object start = thisJoinPoint.getThis();
      Field fieldFenster = thisJoinPoint.getThis().getClass().getDeclaredField("fenster");
      Field fieldDBA = thisJoinPoint.getThis().getClass().getDeclaredField("_dba"); 
      fieldFenster.setAccessible(true);
      fieldDBA.setAccessible(true);
      fenster = (JFrame) fieldFenster.get(start);
      dba = (DatabaseAccess) fieldDBA.get(start);
      
    } catch (Exception e) {
      e.printStackTrace();
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
        dba.deleteAndCreateDB();
        
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
            
            
            if(dba.getDAType().equals("db")) {
              arrHab[j] = Integer.valueOf(setArrMangas[nAnz+j][3]);
              String[] split = setArrMangas[nAnz+j][2].split(" ");
              String dPreis = split[0];
              arrPreis[j] = Double.valueOf(dPreis);
            }
            else {
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
          
          
          if(dba.getDAType().equals("db")) 
            dba.insertManga(sTitel, sAutor, sVerlag, nAnzBaender, sStatus, arrHab, arrPreis, arrUntertitel, arrErscheinung);
          else
            dba.insertMangaXML(sTitel, sAutor, sVerlag, nAnzBaender, sStatus, arrHab, arrsPreis, arrUntertitel, arrErscheinung);
        }
        return 1;
      }
      else {
        return 0;
      }
    } catch (IOException e) {
      System.out.println(e);
      return 0;
      }
  }
	
}
