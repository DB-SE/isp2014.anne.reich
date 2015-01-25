import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public aspect exportData {
  
  private DatabaseAccess dba;
	
  pointcut createMenuItem(JMenu menuDatei):
    call(private JMenu Start.exportMenuItem(JMenu)) && args(menuDatei);
  
  JMenu around(JMenu menuDatei): createMenuItem(menuDatei) {
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
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    menuDatei.add(itemExport);
    
    return menuDatei;
  }
  
  after(JMenu menuDatei): createMenuItem(menuDatei) {
    try {
      Object start = thisJoinPoint.getThis();
      Field fieldDBA = thisJoinPoint.getThis().getClass().getDeclaredField("_dba"); 
      fieldDBA.setAccessible(true);
      dba = (DatabaseAccess) fieldDBA.get(start);      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private int exportDB(){
    String[][] allMangas = dba.exportAll();
    
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
    } catch(Exception e) {
      return 1;
    }
    
  }
  
}
