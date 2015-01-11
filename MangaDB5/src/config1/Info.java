import java.awt.BorderLayout; 
import java.awt.GridLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 

import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JPanel; 
import javax.swing.JScrollPane; 
import javax.swing.ScrollPaneConstants; 
import javax.swing.SwingConstants; 
import javax.swing.border.EmptyBorder; 

/**
 * TODO description
 */
public  class  Info {
	
private JFrame fenster;

	
	
	public Info(){
		fensterErzeugen();		
	}

	

	private void fensterErzeugen(){
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO START 
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		fenster = new JFrame("Anne's Manga Bibliothek");
		fenster.setSize(300,300);
		fenster.setLocationRelativeTo(null);
		fenster.setResizable(false);
		
		JPanel flaeche = new JPanel();
		flaeche.setLayout(new BorderLayout());

		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO TITEL (oben)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel titelFla = new JPanel();
		
		JLabel lTitel = new JLabel("Anne's Manga Bibliothek");
		lTitel.setHorizontalAlignment(SwingConstants.CENTER);
		titelFla.add(lTitel);	
		
		flaeche.add(titelFla, BorderLayout.NORTH);

		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO INFO (mitte)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel infoFla = new JPanel();
		infoFla.setBorder(new EmptyBorder(10,10,10,10));
		infoFla.setLayout(new GridLayout(2,0));
		
		JPanel fest = new JPanel();
		fest.setBorder(new EmptyBorder(0,0,10,0));
		fest.setLayout(new GridLayout(2,2));
		JLabel autor = new JLabel("Autor: ");
		autor.setHorizontalAlignment(SwingConstants.LEFT);
		fest.add(autor);
		
		JLabel lAutor = new JLabel("Anne Reich");
		lAutor.setHorizontalAlignment(SwingConstants.LEFT);
		fest.add(lAutor);
		
		JLabel version = new JLabel("Version: ");
		version.setHorizontalAlignment(SwingConstants.LEFT);
		fest.add(version);
		
		JLabel lVersion = new JLabel("V 2.0 - September 2013");
		lVersion.setHorizontalAlignment(SwingConstants.LEFT);
		fest.add(lVersion);
		
		infoFla.add(fest);
		
		JPanel vari = new JPanel();
		vari.setLayout(new GridLayout(0,2));		
		JLabel news = new JLabel("Patch-News: ");
		news.setHorizontalAlignment(SwingConstants.LEFT);
		news.setVerticalAlignment(SwingConstants.TOP);
		vari.add(news);
		
		JLabel lNews = new JLabel("<html><body>- Import-Ordner<br>&nbsp;&nbsp;angepasst</body></html>");
		lNews.setHorizontalAlignment(SwingConstants.LEFT);
		lNews.setVerticalAlignment(SwingConstants.TOP);
		JScrollPane pane = new JScrollPane(lNews);
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setBorder( null );
		pane.getVerticalScrollBar().setValue(0);
		vari.add(pane);
		
		infoFla.add(vari);
		
		flaeche.add(infoFla, BorderLayout.CENTER);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO BUTTONS (unten)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel button = new JPanel();
		
		JButton okButton = new JButton("OK");

		
		okButton.addActionListener(new ActionListener()
        { 
			public void actionPerformed(ActionEvent evt) 
            {  
				//fenster.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				fenster.setVisible(false);
				fenster.dispose();
            }
         } );

		button.add(okButton);
		
		flaeche.add(button, BorderLayout.SOUTH);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO ENDE
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		fenster.add(flaeche);
		//Fenster sichtbar machen
		fenster.setVisible(true);

	}


}
