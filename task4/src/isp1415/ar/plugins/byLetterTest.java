package isp1415.ar.plugins;

import isp1415.ar.guiPack.Bibo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class byLetterTest {
	
	public byLetterTest(){
		super();
	}

	public JPanel setLetterLayout(){
		JPanel abcPanel = new JPanel();
		abcPanel.setLayout(new GridLayout(4,0));
		abcPanel.setPreferredSize(new Dimension(0,70));
		abcPanel.setBorder(new EmptyBorder(5,3,0,3));
		
		JButton sonder = new JButton("+.0-9");
		sonder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					Bibo.sortierung("^[A-Z]");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("A");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("B");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("C");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("D");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("E");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("F");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("G");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("H");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("I");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("J");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("K");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("L");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("M");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("N");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("O");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("P");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("Q");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("R");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("S");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("T");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("U");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("V");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("W");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("X");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("Y");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
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
					Bibo.sortierung("Z");
					Bibo.klick();
					Bibo.gesamtList.getViewport().setView(Bibo.list);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}			
		});
		abcPanel.add(z);
		
		JButton az = new JButton("A-Z");
		az.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Bibo.sammlung();
				Bibo.klick();
				Bibo.gesamtList.getViewport().setView(Bibo.list);				
			}			
		});
		abcPanel.add(az);
		
		return abcPanel;
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
