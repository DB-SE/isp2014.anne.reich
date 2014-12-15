package isp1415.ar.plugins;

import javax.swing.JLabel;

public class insertImageTest {
	
	String title;
	
	public insertImageTest(String title){
		this.title = title;
	}
	
	public JLabel setImage(){

		JLabel image = new JLabel(title);
		
		return image;
	}

}
