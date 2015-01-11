import java.awt.BorderLayout;

/**
 * TODO description
 */
public class Bibo {
	
	public JPanel abcPanel;
	
	public void byLetter(){
		
		abcPanel = new JPanel();
		abcPanel.setLayout(new GridLayout(4,0));
		abcPanel.setPreferredSize(new Dimension(0,70));
		abcPanel.setBorder(new EmptyBorder(5,3,0,3));
		
		JButton sonder = new JButton("+.0-9");
		sonder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("^[A-Z]");
					klick();
					gesamtList.getViewport().setView(list);
					
								
			}			
		});
		abcPanel.add(sonder);
		
		JButton a = new JButton("A");
		a.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("A");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(a);
		
		JButton b = new JButton("B");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("B");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(b);
		
		JButton c = new JButton("C");
		c.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("C");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(c);
		
		JButton d = new JButton("D");
		d.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("D");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(d);
		
		JButton e = new JButton("E");
		e.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("E");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(e);
		
		JButton f = new JButton("F");
		f.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("F");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(f);
		
		JButton g = new JButton("G");
		g.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("G");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(g);
		
		JButton h = new JButton("H");
		h.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("H");
					klick();
					gesamtList.getViewport().setView(list);
								
			}			
		});
		abcPanel.add(h);
		
		JButton i = new JButton("I");
		i.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("I");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(i);
		
		JButton j = new JButton("J");
		j.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("J");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(j);
		
		JButton k = new JButton("K");
		k.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("K");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(k);
		
		JButton l = new JButton("L");
		l.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("L");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(l);
		
		JButton m = new JButton("M");
		m.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("M");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(m);
		
		JButton n = new JButton("N");
		n.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("N");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(n);
		
		JButton o = new JButton("O");
		o.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("O");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(o);
		
		JButton p = new JButton("P");
		p.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("P");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(p);
		
		JButton q = new JButton("Q");
		q.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("Q");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(q);
		
		JButton r = new JButton("R");
		r.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("R");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(r);
		
		JButton s = new JButton("S");
		s.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
					sortierung("S");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(s);
		
		JButton t = new JButton("T");
		t.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("T");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(t);
		
		JButton u = new JButton("U");
		u.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("U");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(u);
		
		JButton v = new JButton("V");
		v.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("V");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(v);
		
		JButton w = new JButton("W");
		w.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("W");
					klick();
					gesamtList.getViewport().setView(list);
					
			}			
		});
		abcPanel.add(w);
		
		JButton x = new JButton("X");
		x.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("X");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(x);
		
		JButton y = new JButton("Y");
		y.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
		
					sortierung("Y");
					klick();
					gesamtList.getViewport().setView(list);
							
			}			
		});
		abcPanel.add(y);
		
		JButton z = new JButton("Z");
		z.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			
					sortierung("Z");
					klick();
					gesamtList.getViewport().setView(list);
						
			}			
		});
		abcPanel.add(z);
		
		JButton az = new JButton("A-Z");
		az.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sammlung();
				klick();
				gesamtList.getViewport().setView(list);				
			}			
		});
		abcPanel.add(az);
		
	}
}