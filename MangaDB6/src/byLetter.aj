import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;


public aspect byLetter {
  
  private Method klick;
  private Method refreshGesamtList;
  private Method sammlung;
  private Object bibo;
  private Field fieldList;
  private DatabaseAccess dba;
  private JList<String> list;
	
  pointcut searchOption(JPanel obenAll):
    call(private JPanel Bibo.byLetter(JPanel)) && args(obenAll);
  
  @SuppressWarnings("unchecked")
  before(JPanel obenAll): searchOption(obenAll) {
    try {
      
      bibo = thisJoinPoint.getThis();      
      klick = thisJoinPoint.getThis().getClass().getDeclaredMethod("klick");      
      klick.setAccessible(true);
      
      @SuppressWarnings("rawtypes")
      Class[] arg = new Class[1];
      arg[0] = JList.class;
      refreshGesamtList = thisJoinPoint.getThis().getClass().getDeclaredMethod("refreshGesamtList", arg);
      refreshGesamtList.setAccessible(true);
      
      sammlung = thisJoinPoint.getThis().getClass().getDeclaredMethod("sammlung");      
      sammlung.setAccessible(true);
      
      Field fieldDBA = thisJoinPoint.getThis().getClass().getDeclaredField("_dba"); 
      fieldDBA.setAccessible(true);
      dba = (DatabaseAccess) fieldDBA.get(bibo);
      
      fieldList = thisJoinPoint.getThis().getClass().getDeclaredField("list"); 
      fieldList.setAccessible(true);
      list = (JList<String>) fieldList.get(bibo);
      
    } catch (Exception e) {
      e.printStackTrace();
    }      
  }
  
  JPanel around(JPanel obenAll): searchOption(obenAll) {
    
    JPanel abcPanel = new JPanel();
    abcPanel.setLayout(new GridLayout(4,0));
    abcPanel.setPreferredSize(new Dimension(0,70));
    abcPanel.setBorder(new EmptyBorder(5,3,0,3));
    
    JButton sonder = new JButton("+.0-9");
    sonder.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        try {
          sortierung("^[A-Z]");
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);       
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list); 
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list); 
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list); 
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list); 
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list); 
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
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
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
          
        } catch (Exception e) {
          e.printStackTrace();
        }       
      }     
    });
    abcPanel.add(z);
    
    JButton az = new JButton("A-Z");
    az.addActionListener(new ActionListener(){
      @SuppressWarnings("unchecked")
      public void actionPerformed(ActionEvent arg0) {
        try {
          sammlung.invoke(bibo);
          fieldList = thisJoinPoint.getThis().getClass().getDeclaredField("list"); 
          fieldList.setAccessible(true);
          list = (JList<String>) fieldList.get(bibo);
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list); 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      }     
    });
    abcPanel.add(az);
    
    obenAll.add(abcPanel, BorderLayout.PAGE_START);
    
    return obenAll;
  }
  
  @SuppressWarnings("unchecked")
  private void sortierung(String letter){
    String[][] mangaListSort = dba.getReiheStartsWith(letter);
    String[] samml = new String[mangaListSort.length];
    String[] farbe = new String[mangaListSort.length];
    
    for(int i = 0; i < mangaListSort.length; i++){
      samml[i] = mangaListSort[i][0];
      farbe[i] = mangaListSort[i][1];
    }
    
    list = new JList<String>(samml);
    list.setCellRenderer(new TitelFarbe(farbe));
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
