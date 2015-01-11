import java.io.File; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.sql.SQLException; 
import java.text.DecimalFormat; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.regex.Pattern; 

import org.jdom2.Attribute; 
import org.jdom2.Document; 
import org.jdom2.Element; 
import org.jdom2.filter.ElementFilter; 
import org.jdom2.input.SAXBuilder; 
import org.jdom2.output.Format; 
import org.jdom2.output.XMLOutputter; 

/**
 * @author Yuri-Li
 * 
 *         Grundlagen SQL: 
 *         Auf den Server greift man mit Hilfe vom Connecter zu;
 *         Mit Statement-Objekte kann man grundlegende SQL-Anfragen ausführen;
 *         Statement-Ergebnisse lassen sich über ResultSet abrufen;
 *         Statement-Objekt erzeugt man mit connection.createStatement(); 
 *         in executeQuery(String) lassen sich Select-Anfragen ausführen; 
 *         mit executeUpdate(String) aktualisiert man die Datenbank; 
 *         execute(String) ist für beides, bei true ist es eine Select Anfrage, bei false ist es ein Update, insert oder delete anfrage; 
 *         preparedStatemnt dient als vorübergehender Platzhalter, bevor es zur DB hinzugefügt wird;
 *         
 *         Wann nutzt man welche Methode?
 *         getConnection() - um eine Verbindung herzustellen
 *         createMangareihe(), createBaender() - um die benötigten Tabellen zu erstellen, wenn diese nicht existieren
 *         insertManga() - fügt man eine komplett neue Manga-Reihe hinzu
 *         editManga() - hat man ein neuen Band gekauft, die Reihe abgeschlossen o.ä. editiert man die Reihe nur
 *         insertBand() - kommt zu der "abgeschlossene" Reihe ein neuer Band hinzu, fügt man diesen ein (wird nAnzBaender+1)
 *         deleteManga() - wurde eine Mangareihe verkauft oder falsch eingetragen, kann man diese löschen
 *         deleteBand() - hat man ein falschen Band eingetragen, kann man diesen löschen
 *         selectSum() - Gibt die Preis-Gesamtsumme, Anzahl aller Reihen und Anzahl aller Mangas wieder (für Start-Seite)
 *         wirteResultSet() - dient als Testzweck, um Ergebnisse in Konsole zu schreiben
 *         getStart() - gibt alle Werte wieder, die auf der Startseite zu sehen sind
 *         getBiboDetails() - gibt alle Werte wieder, die auf der Bibo-Details-Seite zu sehen sind (zu einer bestimmten Manga)
 *         getBandDetails() - gibt alle Werte wieder, die auf der Band-Details-Seite zu sehen sind (zu einer bestimmten Manga)
 *         getMangareiheTitel() - gibt alle Mangareihen aus + Status (für farbliche unterlegung) 
 *         getReiheStartsWith() - gibt alle Mangareihen die mit einen bestimmten Buchstaben anfangen + Status-Farbe
 *         getReiheStartsWithVerlag() - gibt alle Mangareihen aus, von einem bestimmten Verlag + Status-Farbe
 *         getReiheStartsWithAutor() - gibt alle Mangareihen aus, von einem bestimmten Autor + Status-Farbe
 */

public  class  XML {
	
	private static Element root = null;

	
	private static Document doc = null;

	
	private File xml = null;

	
	SAXBuilder builder = null;

	
	XMLOutputter output = null;

	

	/**
	 * - Verbinden mit dem lokalen SQL-Server 
	 * - testen, ob eine Datenbank existiert
	 */
	public void getConnection(){
		
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

	
	
	public void deleteAndCreateDB() {
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
	}

	


	/**
	 * Methode wird aufgerufen, wenn eine neue Manga hinzugefügt wird
	 * 
	 * @param sTitel
	 *            (string) = Titel der Manga
	 * @param sAutor
	 *            (string) = Autor der Manga
	 * @param sVerlag
	 *            (string) = Verlag der Manga
	 * @param nAnzBaender
	 *            (int) = die Anzahl der Baender der Reihe
	 * @param sStatus
	 *            (string) = abgeschlossene oder fortzgesetzte Reihe in Deutschland
	 * @param arrHab_ich
	 *            (int[]) = 1 - Band habe ich, 0 - Band habe ich noch nicht, Länge = nAnzBaender
	 * @param arrPreis
	 *            (double[]) = Preis von jedem Band, Länge = nAnzBaender
	 * @param arrUntertitel
	 *            (string[]) = Untertitel von jedem Band (soweit einer existiert)
	 * @param arrErscheinungsdatum
	 *            (string[]) = Erscheinungsdatum als String (z.B. "Februar 2013")
	 */
	public void insertManga(String sTitel, String sAutor, String sVerlag,
			int nAnzBaender, String sStatus, int[] arrHab_ich,
			String[] arrPreis, String[] arrUntertitel,
			String[] arrErscheinungsdatum){
		
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
			band.addContent(new Element("Hab_ich").setText("" + arrHab_ich[indBand]));
			band.addContent(new Element("Datum").setText(arrErscheinungsdatum[indBand]));
				  
			titel.addContent(band);
			bandNr++;
			
		}
		
		root.addContent(titel);		
		
		saveChange();
	}

	

	/**
	 * Methode wird aufgerufen, wenn ein neuer Band in einer existierende Mangareihe hinzugefügt wird
	 * 
	 * @param sTitel
	 *            (string) = Titel der Manga
	 * @param nBandnr
	 *            (int) = Band Nr. x von der Manga
	 * @param sUntertitel
	 *            (string) = Untertitel vom Band (soweit einer existiert)
	 * @param nPreis
	 *            (double) = Preis vom Band
	 * @param nHab_ich
	 *            (int) = 1 - Band habe ich, 0 - Band habe ich noch nicht
	 * @param sErscheinungsdatum
	 *            (string) = Erscheinungsdatum als String (z.B. "Februar 2013")
	 * @param nAnzaender
	 *            (int) = die Anzahl der Baender der Reihe
	 * @param sVerlag
	 *            (string) = Verlag der Manga
	 * @param sStatus
	 *            (string) = abgeschlossene oder fortzgesetzte Reihe in Deutschland
	 */
	public void insertBand(String sTitel, int nBandnr, String sUntertitel,
			double nPreis, int nHab_ich, String sErscheinungsdatum, String sStatus){

		
		String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
		Element titel = root.getChild(newTitel);
		
		Element band = new Element("Band_" + (nBandnr));
		band.setAttribute(new Attribute("id", ""+(nBandnr)));
		band.addContent(new Element("Untertitel").setText(sUntertitel));
		band.addContent(new Element("Preis").setText(""+(nPreis)));
		band.addContent(new Element("Hab_ich").setText("" + nHab_ich));
		band.addContent(new Element("Datum").setText(sErscheinungsdatum));
			  
		titel.addContent(band);

		//Wenn ein neuer Band hinzukommt, kann eventuell sich der Status, Verlag oder die Anzahl aller Baendern aendern
		//daher muss dies zusaützlich aktualisiert werden
	    Element info = titel.getChild("Info");
	    info.getChild("Status").setText(sStatus);		  
	}

	

	/**
	 * Updatet/Aktualisiert die existierende Manga
	 * 
	 * @param sTitel
	 *            (string) = Titel der Manga
	 * @param sAutor
	 *            (string) = Autor der Manga
	 * @param sVerlag
	 *            (string) = Verlag der Manga
	 * @param nAnzBaender
	 *            (int) = die Anzahl der Baender der Reihe
	 * @param sStatus
	 *            (string) = abgeschlossene oder fortzgesetzte Reihe in Deutschland
	 * @param nHab_ich
	 *            (int) = 1 - Band habe ich, 0 - Band habe ich noch nicht
	 * @param nPreis
	 *            (double) = Preis vom Band
	 * @param sUntertitel
	 *            (string) = Untertitel vom Band (soweit einer existiert)
	 * @param sErscheinungsdatum
	 *            (string) = Erscheinungsdatum als String (z.B. "Februar 2013")
	 */
	public void editManga(String sTitel, String sAutor, String sVerlag,
			int nAnzBaender, String sStatus, int nBandnr, int nHab_ich,
			double nPreis, String sUntertitel, String sErscheinungsdatum){
		
		String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
		Element titel = root.getChild(newTitel);
		
		Element info = titel.getChild("Info");
		info.getChild("Autor").setText(sAutor);
		info.getChild("Verlag").setText(sVerlag);
		info.getChild("Status").setText(sStatus);	 			 
		
		Element band = titel.getChild("Band_" + (nBandnr));
		band.getChild("Untertitel").setText(sUntertitel);
		band.getChild("Preis").setText(""+(nPreis));
		band.getChild("Hab_ich").setText("" + nHab_ich);
		band.getChild("Datum").setText(sErscheinungsdatum);	
	}

	

	/**
	 * Löscht die komplette Mangareihe
	 * 
	 * @param sTitel
	 * 				(string) = Titel der Manga
	 */
	public void deleteManga(String sTitel)  {
		
		String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
		root.removeChild(newTitel);
		output.setFormat(Format.getPrettyFormat());
		
		saveChange();
		  
	}

	

	/**
	 * Löscht den Band einer bestimmten Manga
	 * 
	 * @param sTitel
	 *            (string) = Titel der Manga
	 * @param nBandnr
	 *            (int) = Band Nr. x von der Manga
	 */
	public void deleteBand(int nBandNr, String sTitel){
		
		String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
		Element titel = root.getChild(newTitel);
		
		titel.removeChild("Band_" + (nBandNr));
		}

	
	
	public void saveChange(){
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

	

	/**
	 * Berechnet die Gesamtpreis aller Mangas (die ich besitze)
	 * Berechnet die Anzahl aller Mangareihen (die ich besitze)
	 * Berechnet die Anzahl aller Mangabaender (die ich besitze)
	 */
	private static double[] selectSum(){
		/*
		 * select sum(preis) as 'Gesamtpreis' from mangadb.Baender b where b.hab_ich = 'Y';
		 * select count(*) as 'Alle Mangareihen' from mangadb.mangareihen;
		 * select count(*) as 'Alle Mangas' from mangadb.Baender b where b.hab_ich= 'Y';
		 */
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

	

	/**
	 * Gibt alle Informationen wieder, die für die Startseite gebraucht wird 
	 * 
	 * @return String[][] mit Gesamtsumme[0][0], Anzahl meiner Mangareihen[1][0], Mangas[2][0], Titel[i+3][0],Erscheinungsdatum[i+3][1] und Preis[i+3][2] aller Mangas
	 * @throws SQLException 
	 */
    public String[][] getStart() {
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

	

    /**
     * Gibt alle Informationen aus, die für Bibo-Detail-Seite nötig sind
     * 
     * @param titelManga = Titel der ausgewählten Manga
     * @return String[] mit Titel[0], Autor[1], Verlag[2], hab_ich[3], Anzahl der Baender[4],
     * 			Status[5], Gesamtkosten[6], Erscheinungsdatum meiner fehlender Manga[7]
     */
    public String[] getBiboDetails(String titelManga) {
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

	
    
    /**
     * 
     * @param titelManga = Titel der ausgewählten Manga
     * @return String[][] mit Autor[i][0], Verlag[i][1], Status[i][2], Untertitel[i][3], 
     * 			BandNr[i][4], Hab_ich[i][5], Preis[i][6], Erscheinung[i][7], i = Anzahl Baender
     * @
     */
    public String[][] getEditDetails(String titelManga) {
      	
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

	
    
    /**
     * Gibt die komplette Mangatitel wieder und deren status für farbbestimmung
     * @return String[][] mit Titel[i][0], Farbe[i][1] und i = alle Mangas
     * @throws SQLException 
     */
    public String[][] getMangareiheTitel() {
    	
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

	
 
    /**
     * Gibt die komplette Verläge wieder
     * @return String[] 
     * @throws SQLException 
     */   
    public String[] getVerlag() {
    	
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

	

    /**
     * Gibt die komplette Autoren wieder
     * @return String[]
     * @throws SQLException 
     */   
    public String[] getAutor() {
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

	

    /**
     * Gibt alle Titel von einem  Autoren wieder
     * @return String[]
     * @throws SQLException 
     */   
    public String[] getTitelByAutor(String autor){
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

	

    /**
     * Gibt alle Titel von einem  Autoren wieder
     * @return String[]
     * @throws SQLException 
     */   
    public String[] getTitelByVerlag(String verlag) {
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

	
    
    /**
     * Gibt die gefilterten Mangatitel wieder und deren status für farbbestimmung
     * @param letter = Buchstabe nach dem gefiltert wird
     * @return String[][] mit Titel[i][0], Farbe[i][1] und i = alle gefilterten Mangas
     * @throws SQLException
     */
    public String[][] getReiheStartsWith(String letter) {
		
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

	

    /**
     * 
     * @return String[][] Titel[i][0], Autor[i][1], Verlag[i][2], BanzAnz[i][3], Status[i][4], BandNr[i][5], 
     * Untertitel[i][6], Preis[i][7], Habe_ich[i][8], Erscheinung[i][9]
     * @throws SQLException
     */
    public String[][] exportAll() {
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


}
