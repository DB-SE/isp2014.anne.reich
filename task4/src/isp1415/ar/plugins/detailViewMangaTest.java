package isp1415.ar.plugins;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class detailViewMangaTest {

	public detailViewMangaTest(JPanel detailseditPanel, JLabel lName,
			JLabel lZeichner, JLabel lVerlag, JLabel lStatus, JLabel lHab,
			JLabel lNext, JLabel lKosten, JLabel lAnz) {
		super();
		this.detailseditPanel = detailseditPanel;
		this.lName = lName;
		this.lZeichner = lZeichner;
		this.lVerlag = lVerlag;
		this.lStatus = lStatus;
		this.lHab = lHab;
		this.lNext = lNext;
		this.lKosten = lKosten;
		this.lAnz = lAnz;
		
	}

	JPanel detailseditPanel;
	JLabel lName, lZeichner, lVerlag, lStatus, lHab, lNext, lKosten, lAnz;
	
	
	public void createDetailView(){
		//#if detailViewManga		
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
	//#endif
	}


	public JPanel getDetailseditPanel() {
		return detailseditPanel;
	}


	public JLabel getlName() {
		return lName;
	}


	public JLabel getlZeichner() {
		return lZeichner;
	}


	public JLabel getlVerlag() {
		return lVerlag;
	}


	public JLabel getlStatus() {
		return lStatus;
	}


	public JLabel getlHab() {
		return lHab;
	}


	public JLabel getlNext() {
		return lNext;
	}


	public JLabel getlKosten() {
		return lKosten;
	}


	public JLabel getlAnz() {
		return lAnz;
	}
	

	
	

}
