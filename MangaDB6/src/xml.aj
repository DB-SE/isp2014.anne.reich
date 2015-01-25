
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public aspect xml {
	
	static Element root = null;
	static Document doc = null;
	File xml = null;
	SAXBuilder builder = null;
	XMLOutputter output = null;
	
	 private static double[] selectSum() {
		 double[] sum = new double[3];
	        
	        sum[1] = root.getChildren().size();
	        
	        for(int i = 0; i < root.getChildren().size(); i++){
	            Element manga = root.getChildren().get(i);
	            sum[2] += (manga.getChildren().size()-1);
	            
	            for(int j = 1; j < manga.getChildren().size(); j++){
	                String bandPreis = manga.getChildren().get(j).getChildText("Preis");
	                String[] split1 = bandPreis.split(" ");
	                String sPreis = split1[0].replace(",",".");
	                double dPreis = Double.parseDouble(sPreis);
	                sum[0] += dPreis;
	            }
	        }
	        
	        return sum;
	 }
	 
	 private void saveChange() {
		 output.setFormat(Format.getPrettyFormat());
	        
         try {
           FileOutputStream outputStream = new FileOutputStream(xml);
           output.output(doc, outputStream);
           outputStream.close();
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
         System.out.println("Aenderung gespeichert");
	 }
  
  pointcut connection(): 
	  call(public void DatabaseAccess.getConnection());
  
  pointcut deleteAndCreate():
	  call(public void DatabaseAccess.deleteAndCreateDB());
  
  pointcut insertManga(String sTitel, String sAutor, String sVerlag,
	      int nAnzBaender, String sStatus, int[] arrHabIch,
	      String[] arrPreis, String[] arrUntertitel,
	      String[] arrErscheinungsdatum):
	   call(public void DatabaseAccess.insertMangaXML(String, String, String, 
			   int, String, int[],
			   String[], String[], String[])) 
			   && args(sTitel, sAutor, sVerlag, 
					   nAnzBaender, sStatus, arrHabIch,
					   arrPreis, arrUntertitel, arrErscheinungsdatum);
  
  pointcut insertBand(String sTitel, int nBandnr, String sUntertitel,
          double nPreis, int nHabIch, String sErscheinungsdatum, String sStatus):
       call(public void DatabaseAccess.insertBandXML(String, int, String,
    	          double, int, String, String)) 
               && args(sTitel, nBandnr, sUntertitel, 
                       nPreis, nHabIch, sErscheinungsdatum, sStatus);
  
  pointcut editManga(String sTitel, String sAutor, String sVerlag,
          int nAnzBaender, String sStatus, int nBandnr, int nHabIch,
          double nPreis, String sUntertitel, String sErscheinungsdatum):
       call(public void DatabaseAccess.editManga(String, String, String,
                  int, String, int, int,
                  double, String, String)) 
               && args(sTitel, sAutor, sVerlag, 
            		   nAnzBaender, sStatus, nBandnr, nHabIch, 
                       nPreis, sUntertitel, sErscheinungsdatum);
  
  pointcut deleteManga(String sTitel):
	  call(public void DatabaseAccess.deleteManga(String)) && args(sTitel);
  
  pointcut deleteBand(int nBandNr, String sTitel):
	  call(public void DatabaseAccess.deleteBandXML(int, String)) && args(nBandNr, sTitel);
  
//  pointcut saveChanges():
//	  call(public void saveChangeXML());
  
  pointcut getStart():
	  call(public String[][] DatabaseAccess.getStart());
  
  pointcut getBiboDetails(String titelManga):
	  call(public String[] DatabaseAccess.getBiboDetails(String)) && args (titelManga);
  
  pointcut getEditDetails(String titelManga):
	  call(public String[][] DatabaseAccess.getEditDetails(String)) && args(titelManga);
  
  pointcut getMangareiheTitel():
	  call(public String[][] DatabaseAccess.getMangareiheTitel());
  
  pointcut getVerlag():
	  call(public String[] DatabaseAccess.getVerlag());
  
  pointcut getAutor():
	  call(public String[] DatabaseAccess.getAutor());
  
  pointcut getTitelByAutor(String autor):
	  call(public String[] DatabaseAccess.getTitelByAutor(String)) && args(autor);
  
  pointcut getTitelByVerlag(String verlag):
	  call(public String[] DatabaseAccess.getTitelByVerlag(String)) && args(verlag);
  
  pointcut getReiheStartsWith(String letter):
	  call(public String[][] DatabaseAccess.getReiheStartsWith(String)) && args(letter);
  
  pointcut exportAll():
	  call(public String[][] DatabaseAccess.exportAll());
  
  pointcut getDAType():
    call(public String DatabaseAccess.getDAType());
  
//  void around(): saveChanges() {
//	  System.out.println("around before Proceed");
//	  proceed();
//	  
//	  System.out.println("around after Proceed");
//	  
//	  proceed();
//  }
//  
//  before(): saveChanges() {
//	  System.out.println("before advice");
//  }
//  
//  after(): saveChanges() {
//      System.out.println("after advice");
//  }
  
  
  after(): connection() {
	  File file = new File("AllManga.xml");
      String path = file.getAbsolutePath();
      xml = new File(path);
      if(!xml.exists()){
          System.out.println("Die nötige XML-Datei fehlt");
          System.exit(0);
      }   
      else{
          try {
              builder = new SAXBuilder();
              doc = builder.build(xml);
              output = new XMLOutputter();
              root = doc.getRootElement();
              
              System.out.println("Connection erfolgreich!");
          } catch (Exception e) {
              System.out.println("Exception: " + e);
              e.printStackTrace();
          }
      }
  }
  
  void around(): deleteAndCreate() {
    proceed();
    
	  boolean fOk;
      
      try {
          fOk = xml.delete();
          if(!fOk) System.out.println("XML-File konnte nicht geloescht werden");
          
          xml.createNewFile();
          if(!fOk) System.out.println("XML-File konnte nicht erstellt werden");
          root = new Element("Data");
          doc = new Document(root);
      } catch (IOException e) {
          e.printStackTrace();
      }      
      
      proceed();
  }
  
  after(String sTitel, String sAutor, String sVerlag,
          int nAnzBaender, String sStatus, int[] arrHabIch,
          String[] arrPreis, String[] arrUntertitel,
          String[] arrErscheinungsdatum): 
        	  insertManga(sTitel, sAutor, sVerlag,
        			  nAnzBaender, sStatus, arrHabIch,
        			  arrPreis, arrUntertitel, arrErscheinungsdatum) {
	  
	  String newTitel = sTitel.replaceAll("[^a-zA-Z0-9]", "");
      //Wurzel der Manga
      Element titel = new Element(newTitel);
      titel.setAttribute(new Attribute("Titel", sTitel));
      
      //erster Knoten = allg.Info
      Element info = new Element("Info");
      info.addContent(new Element("Autor").setText(sAutor));
      info.addContent(new Element("Verlag").setText(sVerlag));
      info.addContent(new Element("Status").setText(sStatus));
            
      titel.addContent(info);
      
      int indBand;
      int bandNr = 1;
      for(indBand = 0; indBand < nAnzBaender; indBand++){
          
          Element band = new Element("Band_" + (bandNr));
          band.setAttribute(new Attribute("id", ""+(bandNr)));
          band.addContent(new Element("Untertitel").setText(arrUntertitel[indBand]));
          band.addContent(new Element("Preis").setText(arrPreis[indBand]));
          band.addContent(new Element("Hab_ich").setText("" + arrHabIch[indBand]));
          band.addContent(new Element("Datum").setText(arrErscheinungsdatum[indBand]));
                
          titel.addContent(band);
          bandNr++;
          
      }
      
      root.addContent(titel);     
      
      saveChange();
	  
  }
  
  after(String sTitel, int nBandnr, String sUntertitel,
          double nPreis, int nHabIch, String sErscheinungsdatum, String sStatus):
        	  insertBand(sTitel, nBandnr, sUntertitel,
        			  nPreis, nHabIch, sErscheinungsdatum, sStatus) {
	  
	  String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
      Element titel = root.getChild(newTitel);
      
      Element band = new Element("Band_" + (nBandnr));
      band.setAttribute(new Attribute("id", ""+(nBandnr)));
      band.addContent(new Element("Untertitel").setText(sUntertitel));
      band.addContent(new Element("Preis").setText(""+(nPreis)));
      band.addContent(new Element("Hab_ich").setText("" + nHabIch));
      band.addContent(new Element("Datum").setText(sErscheinungsdatum));
            
      titel.addContent(band);

      //Wenn ein neuer Band hinzukommt, kann eventuell sich der Status, Verlag oder die Anzahl aller Baendern aendern
      //daher muss dies zusaützlich aktualisiert werden
      Element info = titel.getChild("Info");
      info.getChild("Status").setText(sStatus);   
  }
  
  after(String sTitel, String sAutor, String sVerlag,
          int nAnzBaender, String sStatus, int nBandnr, int nHabIch,
          double nPreis, String sUntertitel, String sErscheinungsdatum):
        	  editManga(sTitel, sAutor, sVerlag,
        	          nAnzBaender, sStatus, nBandnr, nHabIch,
        	          nPreis, sUntertitel, sErscheinungsdatum) {
	  
	  String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
      Element titel = root.getChild(newTitel);
      
      Element info = titel.getChild("Info");
      info.getChild("Autor").setText(sAutor);
      info.getChild("Verlag").setText(sVerlag);
      info.getChild("Status").setText(sStatus);                
      
      Element band = titel.getChild("Band_" + (nBandnr));
      band.getChild("Untertitel").setText(sUntertitel);
      band.getChild("Preis").setText(""+(nPreis));
      band.getChild("Hab_ich").setText("" + nHabIch);
      band.getChild("Datum").setText(sErscheinungsdatum);	  
  }
  
  after(String sTitel): deleteManga(sTitel) {
	  String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
      root.removeChild(newTitel);
      output.setFormat(Format.getPrettyFormat());
      
      saveChange();
  }
  
  after(int nBandNr, String sTitel): deleteBand(nBandNr, sTitel) {
	  String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
      Element titel = root.getChild(newTitel);
      
      titel.removeChild("Band_" + (nBandNr));
  }
  
  String[][] around(): getStart() {
	//holt die Sum-Werte aus der Datenbank
      double[] sum = selectSum();
      
      //sReturn wird zurückgegeben
      String[][] sReturn = new String[(int)sum[1]+3][3];
      //Gibt den Preis ordentlich in Dezimalzahl aus
      DecimalFormat df = new DecimalFormat("#,##0.00");
      //speichert die Sum werte in sReturn
      sReturn[0][0] = "" + df.format(sum[0]) + " Euro";
      sReturn[1][0] = "" + (int)sum[1];
      sReturn[2][0] = "" + (int)sum[2];
      
      //jeweils die nächsten einer Mangareihe raussuchen, die ich noch nicht habe 
      String sTitel = "";
      int i = 0;
      ElementFilter filter = new org.jdom2.filter.ElementFilter("Hab_ich");
      
      //speichert diese Informationen einmal als Titel +Bandnr in [i][0] und das Datum in [i][1]
      for(Element c:root.getDescendants(filter))
      {
          if(!sTitel.equals(c.getParentElement().getParentElement().getAttributeValue("Titel"))){
              if(c.getText().equals("0")){
                  int nBandnr = Integer.parseInt(c.getParentElement().getAttributeValue("id"));
                  String sDate = c.getParentElement().getChildText("Datum");
                  String[] split = c.getParentElement().getChildText("Preis").split(" ");
                  double nPreis = Double.parseDouble(split[0].replace(",", "."));
                  sTitel = c.getParentElement().getParentElement().getAttributeValue("Titel");
                  
                  sReturn[3+i][0] = sTitel + " " + nBandnr;
                  sReturn[3+i][1] = sDate;
                  sReturn[3+i][2] = "" + df.format(nPreis);
                  
                  i++;
              }
          }
          
      }
      
      return sReturn;     
  }
  
  String[] around(String titelManga): getBiboDetails(titelManga) {
	//sReturn wird zurückgegeben
      String[] sReturn = new String[8];
      
      //besorgt alle Informationen aus Mangareihen zu einer bestimmten Manga      
      String newTitel = titelManga.replaceAll("[^a-zA-Z0-9]", "");
      Element manga = root.getChild(newTitel);
      String sAutor = manga.getChild("Info").getChildText("Autor");
      String sVerlag = manga.getChild("Info").getChildText("Verlag");
      String sStatus = manga.getChild("Info").getChildText("Status");
      String sAnzahl = "" + (manga.getChildren().size()-1);
      
      
      int nHab = 0;
      double nPreis = 0;
      boolean bHab = false;
      String sDate = "";
      for(int i = 1; i < manga.getChildren().size(); i++){
          
          //zählt alle Baender aus dieser Reihe, die ich habe     
          //zählt Gesamtpreis der Reihe, die ich habe   
          if(manga.getChild("Band_" + i).getChildTextNormalize("Hab_ich").equals("1")){
              nHab++;
              
              String[] split = manga.getChild("Band_" + i).getChildTextNormalize("Preis").split(" ");
              nPreis += Double.parseDouble(split[0].replace(",", "."));
          }
          //gibt Erscheinungsdatum vom ersten Band aus, den ich noch nicht habe.
          else if(!bHab){
              sDate = manga.getChild("Band_" + i).getChildTextNormalize("Datum");
              bHab = true;
          }               
      }
      
      //Gibt den Preis ordentlich in Dezimalzahl aus
      DecimalFormat df = new DecimalFormat("#,##0.00");
      String sPreis = "" + df.format(nPreis) + " Euro";
      
      //speichert alle Infos:
      //Titel[0], Autor[1], Verlag[2], hab_ich[3], Anzahl der Baender[4], Status[5], Gesamtpreis[6], Erscheinungsdatum meiner fehlender Manga[7]  
      sReturn[0] = titelManga;
      sReturn[1] = sAutor;
      sReturn[2] = sVerlag;
      sReturn[3] = "" + nHab;
      sReturn[4] = sAnzahl;
      sReturn[5] = sStatus;
      sReturn[6] = sPreis;
      sReturn[7] = sDate;
      
              
      return sReturn;	  
  }
  
  String[][] around(String titelManga): getEditDetails(titelManga) {
	  String newTitel = titelManga.replaceAll("[^a-zA-Z0-9]", "");
      Element manga = root.getChild(newTitel);
      
      String sAutor = manga.getChild("Info").getChildText("Autor");
      String sVerlag = manga.getChild("Info").getChildText("Verlag");
      String sStatus = manga.getChild("Info").getChildText("Status");
      int nAnzahl = manga.getChildren().size()-1;     

      String[][] sReturn = new String[nAnzahl][8];
      
      for(int i = 1; i < manga.getChildren().size(); i++){
          
          String sUntertitel = manga.getChild("Band_" + (i)).getChildTextNormalize("Untertitel");
          String sPreis = manga.getChild("Band_" + (i)).getChildTextNormalize("Preis");
          String sHab = manga.getChild("Band_" + (i)).getChildTextNormalize("Hab_ich");
          String sDate = manga.getChild("Band_" + (i)).getChildTextNormalize("Datum");
                          
          sReturn[i-1][0] = sAutor;
          sReturn[i-1][1] = sVerlag;
          sReturn[i-1][2] = sStatus;
          sReturn[i-1][3] = sUntertitel;
          sReturn[i-1][4] = "" + (i);
          sReturn[i-1][5] = sHab;
          sReturn[i-1][6] = sPreis;
          sReturn[i-1][7] = sDate;
                          
      }   
      
      
      return sReturn; 
  }
  
  String[][] around(): getMangareiheTitel() {    
	  List<Element> mangas = root.getChildren();
      int nAnzahlManga = mangas.size();
      
      String[][]sReturn = new String[nAnzahlManga][2];
      
      for(int i = 0; i < nAnzahlManga; i++){
          Element manga = mangas.get(i);
          
          sReturn[i][0] = manga.getAttributeValue("Titel");
          
          String sStatus = manga.getChild("Info").getChildText("Status");
          int nAnzahlBand = manga.getChildren().size()-1;
          
          int nHab = 0;
          for(int j = 0; j < nAnzahlBand; j++){
              String sHab = manga.getChild("Band_" + (j+1)).getChildTextNormalize("Hab_ich");
              if(sHab.equals("1"))
                  nHab++;
          }
          
          if(nHab == nAnzahlBand && sStatus.equals("abgeschlossen")){
              sReturn[i][1] = "gelb";
          }
          else if(nHab < nAnzahlBand && sStatus.equals("abgeschlossen")){
              sReturn[i][1] = "rot";
          }
          else{
              sReturn[i][1] = "gruen";
          }
      }
      
      return sReturn;
  }
  
  String[] around(): getVerlag() {
	  List<Element> mangas = root.getChildren();
      int nAnzahlManga = mangas.size();
      
      String[] sReturn = new String[nAnzahlManga];
      
      ElementFilter filter=new org.jdom2.filter.ElementFilter("Verlag");
      int i = 0;
      for(Element c:root.getDescendants(filter))
      {
          sReturn[i] = c.getText();
          i++;
      }
      
      
      return sReturn;
  }
  
  String[] around(): getAutor() {
	  List<Element> mangas = root.getChildren();
      int nAnzahlManga = mangas.size();
      
      String[] sReturn = new String[nAnzahlManga];
      
      ElementFilter filter=new org.jdom2.filter.ElementFilter("Autor");
      int i = 0;
      for(Element c:root.getDescendants(filter))
      {
          sReturn[i] = c.getText();
          i++;
      }
      
      
      return sReturn;
  }
  
  String[] around(String autor): getTitelByAutor(autor) {
	  List<Element> mangas = root.getChildren();
      int nAnzahlManga = mangas.size();
      
      String[] sReturn = new String[nAnzahlManga];
      
      int i = 0;
      ElementFilter filter=new org.jdom2.filter.ElementFilter("Autor");
      for(Element c:root.getDescendants(filter))
      {
          if(c.getTextNormalize().equals(autor)){
              sReturn[i] = c.getParentElement().getParentElement().getAttributeValue("Titel");
              i++;
          }
      }
      
      return sReturn;
  }
  
  String[] around(String verlag): getTitelByVerlag(verlag) {
	  List<Element> mangas = root.getChildren();
      int nAnzahlManga = mangas.size();
      
      String[] sReturn = new String[nAnzahlManga];
      
      int i = 0;
      ElementFilter filter=new org.jdom2.filter.ElementFilter("Verlag");
      for(Element c:root.getDescendants(filter))
      {
          if(c.getTextNormalize().equals(verlag)){
              sReturn[i] = c.getParentElement().getParentElement().getAttributeValue("Titel");
              i++;
          }
      }
      
      return sReturn;
  }
  
  String[][] around(String letter): getReiheStartsWith(letter) {
	  List<Element> mangas = root.getChildren();
      int nAnzahlManga = mangas.size();
      
      String[][] sReturn = new String[nAnzahlManga][2];
      List<String> listReturn = new ArrayList<String>();
      
      
      for(int i = 0; i < nAnzahlManga; i++){
          Element manga = mangas.get(i);
          String sTitel = manga.getAttributeValue("Titel");
          
          if(letter.equals("^[A-Z]")){
              if(Pattern.matches("^[^A-Z].*", sTitel))
                  listReturn.add(sTitel);                 
          }
          else{
              if(sTitel.startsWith(letter))
                  listReturn.add(sTitel);
          }
      }
      
      int i = 0;
      for(String sTitel:listReturn){
          String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
          Element manga = root.getChild(newTitel);
          
          sReturn[i][0] = manga.getAttributeValue("Titel");
          
          String sStatus = manga.getChild("Info").getChildText("Status");
          
          int nAnzahlBand = manga.getChildren().size()-1;
          
          int nHab = 0;
          for(int j = 0; j < nAnzahlBand; j++){
              if(manga.getChild("Band_" + (j+1)).getChildTextNormalize("Hab_ich").equals("1"))
                  nHab++;
          }
          
          if(nHab == nAnzahlBand && sStatus.equals("abgeschlossen")){
              sReturn[i][1] = "gelb";
          }
          else if(nHab < nAnzahlBand && sStatus.equals("abgeschlossen")){
              sReturn[i][1] = "rot";
          }
          else{
              sReturn[i][1] = "gruen";
          }
          
          i++;
      }
      
      return sReturn;
  }
	  
  String[][] around(): exportAll() {
	  int nAnzahl = 0;
      for(int i = 0; i < root.getChildren().size(); i++){
          nAnzahl += (root.getChildren().get(i).getChildren().size()-1);
      }
      String[][] sReturn = new String[nAnzahl][10];
      
      int returnIndex = 0;
      for(int i = 0; i < root.getChildren().size(); i++){
          Element manga = root.getChildren().get(i);
          
          String sTitel = manga.getAttributeValue("Titel");
          String sAutor = manga.getChild("Info").getChildText("Autor");
          String sVerlag = manga.getChild("Info").getChildText("Verlag");
          String sStatus = manga.getChild("Info").getChildText("Status");
          int nAnzahlBand = manga.getChildren().size()-1;     
          
          for(int j = 1; j < manga.getChildren().size(); j++){
              
              String sUntertitel = manga.getChild("Band_" + j).getChildTextNormalize("Untertitel");
              String sPreis = manga.getChild("Band_" + j).getChildTextNormalize("Preis");
              String sHab = manga.getChild("Band_" + j).getChildTextNormalize("Hab_ich");
              String sDate = manga.getChild("Band_" + j).getChildTextNormalize("Datum");              
              
              sReturn[returnIndex][0] = sTitel;
              sReturn[returnIndex][1] = sAutor;
              sReturn[returnIndex][2] = sVerlag;
              sReturn[returnIndex][3] = "" + nAnzahlBand;
              sReturn[returnIndex][4] = sStatus;
              sReturn[returnIndex][5] = "" + j;
              sReturn[returnIndex][6] = sUntertitel;
              sReturn[returnIndex][7] = sPreis;
              sReturn[returnIndex][8] = sHab;
              sReturn[returnIndex][9] = sDate;
              returnIndex++;              
          }   
      }
      
      
      return sReturn;
  }  
  
  String around(): getDAType() {
    
    return "xml";
  }
  
}
