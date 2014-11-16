package isp1415.ar;

import isp1415.ar.dbPack.*;
import isp1415.ar.guiPack.Start;



public class Main {
	
	public static boolean[] features;
	
  public static void main(String[] args) throws Exception { 
	  
	  //Features: db/xml; Import/Export: Y/N; Suchen: Buttons/Zeile; Details-Ansicht: Y/N
	
	if(args.length > 0){
		features = new boolean[args.length];
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("db")) 
				features[0] = true;
			else if(args[i].equals("xml"))
				features[0] = false;
			else if(args[i].equals("transferY"))
				features[1] = true;
			else if(args[i].equals("transferN"))
				features[1] = false;
			else if(args[i].equals("buttons"))
				features[2] = true;
			else if(args[i].equals("field"))
				features[2] = false;
			else if(args[i].equals("detailsY"))
				features[3] = true;
			else if(args[i].equals("detailsN"))
				features[3] = false;
			else{
				System.out.println("Keine gueltige Eingabe. Programm wird geschlossen.");
				System.out.println("Sie koennen folgendes eingeben:");
				System.out.println("Fuer die Datenquelle: xml oder db");
				System.out.println("Fuer Import/Export an: transferY oder transferN");
				System.out.println("Fuer die Suchumgebung: buttons oder field");
				System.out.println("Fuer die Detailanzeige: detailsY oder detailsN");
				System.exit(0);
			}
		}
		
	
	}
	
	
	if(features[0]){
		DB dao = new DB();
		dao.getConnection();
    
		new Start(dao);
	}
	else{
		XML xml = new XML();
		xml.getConnection();
		
		new Start(xml);
	}
    //convertDBtoXML(dao);
  }
  
  
//  public static void convertDBtoXML(DB db){
//	  Element root = new Element("Data");
//	  Document doc = new Document(root);
//	  
//	  String[][] all = null;
//		try {
//			all = db.exportAll();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		System.out.println("Beginne mit dem erstellen des Baumes...");
//	  for(int indTitel = 0; indTitel < all.length;){
//		  String strTitel = all[indTitel][0];
//		  String strNewTitel = strTitel.replaceAll("[^a-zA-Z0-9]", "");
//		  //Wurzel der Manga
//		  Element titel = new Element(strNewTitel);
//		  titel.setAttribute(new Attribute("Titel", strTitel));
//		  
//		  //erster Knoten = allg.Info
//		  Element info = new Element("Info");
//		  info.addContent(new Element("Autor").setText(all[indTitel][1]));
//		  info.addContent(new Element("Verlag").setText(all[indTitel][2]));
//		  info.addContent(new Element("Status").setText(all[indTitel][4]));
//		  
//		  titel.addContent(info);
//		  int indBand;
//		  int bandNr = 1;
//		  for(indBand = indTitel; indBand < all.length; indBand++){
//			  if(all[indBand][0].equals(all[indTitel][0])){
//				  Element band = new Element("Band_" + (bandNr));
//				  band.setAttribute(new Attribute("id", ""+(bandNr)));
//				  band.addContent(new Element("Untertitel").setText(all[indBand][6]));
//				  band.addContent(new Element("Preis").setText(all[indBand][7]));
//				  band.addContent(new Element("Hab_ich").setText(all[indBand][8]));
//				  band.addContent(new Element("Datum").setText(all[indBand][9]));
//				  
//				  titel.addContent(band);
//				  bandNr++;
//			  }
//			  else{
//				  break;
//			  }
//		  }
//
//		  indTitel = indBand;
//		  doc.getRootElement().addContent(titel);
//	  }
//	  
//	  String bsp = "Angel Sanctuary";
//	  bsp = bsp.replaceAll("[^A-Za-z0-9]", "");
//	  System.out.println(root.getChild(bsp).getName());
//	  System.out.println(root.getChild(bsp).getAttribute("Titel").getValue());
//	  System.out.println(root.getChild(bsp).getChild("Info").getChild("Autor").getValue());
//	  
//	  System.out.println("Beginne mit dem Schreiben von Baum zu XML");
//	  XMLOutputter output = new XMLOutputter();
//	  output.setFormat(Format.getPrettyFormat());
//	  
//	  File file = new File("AllManga.xml");
//	  try {
//		FileOutputStream outputStream = new FileOutputStream(file);
//		output.output(doc, outputStream);
//		outputStream.close();
//		
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	  System.out.println("Fertig");
//  }
}