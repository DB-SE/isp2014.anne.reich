package isp1415.ar.guiPack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class PluginLoader {
	
	boolean[] bPlugins;
	JFrame fenster;
	
	public PluginLoader(){
		readXML();
		fensterErzeugen();
	}
	
	private void readXML(){		  
		  try {
		 	  File file = new File(System.getProperty("user.dir") + "/Plugins.xml");
		      SAXBuilder builder = new SAXBuilder();		 
		      Element root = builder.build(file).getRootElement();
		      
		      bPlugins = new boolean[root.getChildren().size()];
		      
		      int i = 0; 
		      for(Element plugin : root.getChildren()){
		    	  int isActive = Integer.parseInt(plugin.getChildText("Active"));
		    	  if(isActive == 0)
		    		  bPlugins[i] = false;
		    	  else
		    		  bPlugins[i] = true;
		    	  
		    	  i++;
		      }
		      
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void fensterErzeugen(){
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO START 
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
		fenster = new JFrame("Anne's Manga Bibliothek");
		fenster.setSize(200, 200);
		fenster.setLocationRelativeTo(null);
		fenster.setResizable(false);		
		
		JPanel flaeche = new JPanel();
		flaeche.setLayout(new BorderLayout());
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO DATEN-FEST (oben)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel pluginPanel = new JPanel();
		pluginPanel.setBorder(new EmptyBorder(5,5,10,5));
		pluginPanel.setLayout(new GridLayout(8,1,2,0));

		
		final JCheckBox cbDetailView = new JCheckBox("DetailViewManga-Plugin", bPlugins[0]);
		pluginPanel.add(cbDetailView);
		final JCheckBox cbByLetter = new JCheckBox("ByLetter-Plugin", bPlugins[1]);
		pluginPanel.add(cbByLetter);
		final JCheckBox cbByTextInput = new JCheckBox("ByTextInput-Plugin", bPlugins[2]);
		pluginPanel.add(cbByTextInput);
		final JCheckBox cbDataTransfer = new JCheckBox("DataTransfer-Plugin", bPlugins[3]);
		pluginPanel.add(cbDataTransfer);
		final JCheckBox cbReste = new JCheckBox("Reset-Plugin", bPlugins[4]);
		pluginPanel.add(cbReste);		
		final JCheckBox cbInsertImage = new JCheckBox("InsertImage-Plugin", bPlugins[5]);
		pluginPanel.add(cbInsertImage);
		
		JLabel leer = new JLabel();
		pluginPanel.add(leer);
		
		JButton buSave = new JButton("Sichern");
		buSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				bPlugins[0] = cbDetailView.isSelected();
				bPlugins[1] = cbByLetter.isSelected();
				bPlugins[2] = cbByTextInput.isSelected();
				bPlugins[3] = cbDataTransfer.isSelected();
				bPlugins[4] = cbReste.isSelected();
				bPlugins[5] = cbInsertImage.isSelected();
				saveXML();
				JOptionPane.showMessageDialog(null, "Plugins wurden geladen. Einmal die Ansicht aendern, um die neuen Plugins zu nutzen.",
						"Plugin-Report", JOptionPane.OK_OPTION, UIManager.getIcon("OptionPane.informationIcon"));

			}			
		});
		pluginPanel.add(buSave);		
		
		flaeche.add(pluginPanel, BorderLayout.NORTH);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO ENDE
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
		fenster.add(flaeche);
		
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Fenster sichtbar machen
		fenster.setVisible(true);
		fenster.validate();
	}
	
	private void saveXML(){
		
		try {
			File file = new File(System.getProperty("user.dir") + "/Plugins.xml");
		    SAXBuilder builder = new SAXBuilder();	
		    XMLOutputter output = new XMLOutputter();
		    Document doc = builder.build(file);
		    Element root = doc.getRootElement();
			
		    int i = 0; 
		      for(Element plugin : root.getChildren()){
		    	  if(bPlugins[i])
		    		  plugin.getChild("Active").setText("1");
		    	  else
		    		  plugin.getChild("Active").setText("0");
		    	  
		    	  i++;
		      }
			
		      output.setFormat(Format.getPrettyFormat());
			
			  try {
				FileOutputStream outputStream = new FileOutputStream(file);
				output.output(doc, outputStream);
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  System.out.println("Aenderung gespeichert");
		  
		} catch (JDOMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		fenster.setVisible(false);
	}

}
