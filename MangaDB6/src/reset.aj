import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;


public aspect reset {
	
  private DatabaseAccess dba;
  private JFrame fenster;
  
  pointcut createMenuItem(JMenu menuDatei):
    call(private JMenu Start.resetMenuItem(JMenu)) && args(menuDatei);
  
  JMenu around(JMenu menuDatei): createMenuItem(menuDatei) {
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
//              resetDB();
              dba.deleteAndCreateDB();
              new Start(dba);
              fenster.setVisible(false);
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        } 
      }
    });
    menuDatei.add(itemReset);   
    JSeparator sep = new JSeparator();
    menuDatei.add(sep);
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
  
}
