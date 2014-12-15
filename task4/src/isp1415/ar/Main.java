package isp1415.ar;

import java.io.File;
import java.io.IOException;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import isp1415.ar.dbPack.*;
import isp1415.ar.guiPack.Start;



public class Main {
		
  public static void main(String[] args) throws Exception { 
	  
	  getPlugins();
	  
	  //Features: db/xml; Import/Export: Y/N; Suchen: Buttons/Zeile; Details-Ansicht: Y/N
	
	//#if database
//@		DB dao = new DB();
//@		dao.getConnection();
//@    
//@		new Start(dao);
	//#endif
	  
	//#if xml
		XML xml = new XML();
		xml.getConnection();
		
		new Start(xml);
	//#endif
  }
  
  
  private static void getPlugins(){
	  
	/*
	 * 1. Plugin.xml wird ausgelesen und notiert, welche Plugins aktiv sind
	 * 2. aktive Plugins (jar) werden in bin/.../plugin geladen  (wenn sie nicht bereits dort drin sind)
	 * 3. fehlende Plugins werden als inaktiv gesetzt
	 */

	  File xml = new File(System.getProperty("user.dir") + "/Plugins.xml");
	  if(!xml.exists()){
			System.out.println("Die nötige XML-Datei für Plugins fehlt");
		}	
		else{
			try {
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(xml);
				Element root = doc.getRootElement();				
				
				for(Element plugin : root.getChildren()){
					String pluginName = plugin.getAttributeValue("id");
					int isActive = Integer.parseInt(plugin.getChildText("Active"));
					
					File classFile = new File(System.getProperty("user.dir") + "/bin/isp1415/ar/plugins/" + pluginName + ".class");
					if(classFile.exists()){
						if(isActive == 0)
							classFile.delete();
						else	
							continue;
					}
					else{
						if(isActive == 1){
							//code from: http://www.devx.com/tips/Tip/22124
							String destDir = System.getProperty("user.dir") + "/bin/";
							File jarFile = new File(System.getProperty("user.dir") + "/plugins/" + pluginName + "-Plugin.jar");
							java.util.jar.JarFile jar = new java.util.jar.JarFile(jarFile);
							java.util.Enumeration enumEntries = jar.entries();
							while (enumEntries.hasMoreElements()) {
							    java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
							    if(!file.getName().contains("MANIFEST")){
							      java.io.File f = new java.io.File(destDir + java.io.File.separator + file.getName());
							      java.io.InputStream is = jar.getInputStream(file); // get the input stream
							      java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
							      while (is.available() > 0) {  // write contents of 'is' to 'fos'
							          fos.write(is.read());
							      }
							      fos.close();
							      is.close();
							    }
							}
						}
						else{
							continue;
						}
					}
				}
				
			} catch (Exception e) {
				System.out.println("Exception: " + e);
				e.printStackTrace();
			}
		}  
  }
  
  public static int getActive(String attrName){
	  int isActive = 0;
	  
	  try {
	 	  File file = new File(System.getProperty("user.dir") + "/Plugins.xml");
	      SAXBuilder builder = new SAXBuilder();		 
	      Element root = builder.build(file).getRootElement();
	      
	      isActive = Integer.parseInt(root.getChild(attrName).getChildText("Active"));
	      
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isActive;
  }
  
  /*
   * Plugins deaktivieren
   *  1. Plugins werden aus bin/.../plugin gelöscht
   */
  
  /*
   * Wo befinden sich die Interfaces:
   * 1. Menü-Leiste bei Start
   * 2. Menü-Leiste bei Bibo
   */
  
}
