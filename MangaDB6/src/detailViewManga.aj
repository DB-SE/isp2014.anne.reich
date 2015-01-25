import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


public aspect detailViewManga {
  
  private Object bibo;
  private static JLabel lName;
  private static JLabel lZeichner;
  private static JLabel lVerlag;
  private static JLabel lHab;
  private static JLabel lAnz;
  private static JLabel lStatus;
  private static JLabel lKosten;
  private static JLabel lNext; 
  
	
  pointcut detailView(JPanel detailseditPanel):
    call(private JPanel Bibo.detailViewManga(JPanel)) && args(detailseditPanel);
  
  pointcut refresh(String[] mangaDetail):
    call(public void Bibo.refreshLabels(String[])) && args(mangaDetail);
  
  void around(String[] mangaDetail): refresh(mangaDetail) {
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
  
  before(JPanel detailseditPanel): detailView(detailseditPanel) {
 try {
      
      bibo = thisJoinPoint.getThis();  
      
      Field fName = thisJoinPoint.getThis().getClass().getDeclaredField("lName"); 
      fName.setAccessible(true);
      lName = (JLabel) fName.get(bibo);
      
      Field fZeichner = thisJoinPoint.getThis().getClass().getDeclaredField("lZeichner"); 
      fZeichner.setAccessible(true);
      lZeichner = (JLabel) fZeichner.get(bibo);
      
      Field fVerlag = thisJoinPoint.getThis().getClass().getDeclaredField("lVerlag"); 
      fVerlag.setAccessible(true);
      lVerlag = (JLabel) fVerlag.get(bibo);
      
      Field fHab = thisJoinPoint.getThis().getClass().getDeclaredField("lHab"); 
      fHab.setAccessible(true);
      lHab = (JLabel) fHab.get(bibo);
      
      Field fAnz = thisJoinPoint.getThis().getClass().getDeclaredField("lAnz"); 
      fAnz.setAccessible(true);
      lAnz = (JLabel) fAnz.get(bibo);
      
      Field fStatus = thisJoinPoint.getThis().getClass().getDeclaredField("lStatus"); 
      fStatus.setAccessible(true);
      lStatus = (JLabel) fStatus.get(bibo);
      
      Field fKosten = thisJoinPoint.getThis().getClass().getDeclaredField("lKosten"); 
      fKosten.setAccessible(true);
      lKosten = (JLabel) fKosten.get(bibo);
      
      Field fNext = thisJoinPoint.getThis().getClass().getDeclaredField("lNext"); 
      fNext.setAccessible(true);
      lNext = (JLabel) fNext.get(bibo);
      
    } catch (Exception e) {
      e.printStackTrace();
    }      
  }
  
  JPanel around(JPanel detailseditPanel): detailView(detailseditPanel) {
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
    return detailseditPanel;
  }
  
}
