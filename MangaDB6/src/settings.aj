import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public aspect settings {
	
  pointcut createMenu(JMenuBar menuBar):
    call(private JMenuBar Start.settingMenu(JMenuBar)) && args(menuBar);
  
  JMenuBar around(JMenuBar menuBar): createMenu(menuBar) {  

    JMenu menuEig = new JMenu("Eigenschaften");
    
    JMenuItem itemBGFarbe = new JMenuItem("Hintergrundfarbe");
    menuEig.add(itemBGFarbe);
    
    JMenuItem itemTextFarbe = new JMenuItem("Schriftfarbe");
    menuEig.add(itemTextFarbe);   
    
    JMenuItem itemTextArt = new JMenuItem("Schriftart");
    menuEig.add(itemTextArt); 
    
    JMenuItem itemButtonFarbe = new JMenuItem("Buttonfarbe");
    menuEig.add(itemButtonFarbe);
    
    menuBar.add(menuEig);
    return menuBar;
  }
}
