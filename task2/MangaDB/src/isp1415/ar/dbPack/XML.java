package isp1415.ar.dbPack;
import java.io.File;
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

public class XML {
	private static Element root = null;
	private static Document doc = null;
	private File xml = null;
	SAXBuilder builder = null;
	XMLOutputter fmt = null;

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
				fmt = new XMLOutputter();
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
			double[] arrPreis, String[] arrUntertitel,
			String[] arrErscheinungsdatum){
		
		//Vorbereitung
		String[] asHab_ich = new String[arrHab_ich.length];
		for(int i = 0; i < asHab_ich.length; i++){
			if(arrHab_ich[i] == 0) asHab_ich[i] = "N";
			else asHab_ich[i] = "Y";
		}
		String[] asPreis = new String[arrPreis.length];
		for(int i = 0; i < asPreis.length; i++){
			asPreis[i] = "" + arrPreis[i];
		}
		
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
			band.addContent(new Element("Preis").setText(asPreis[indBand]));
			band.addContent(new Element("Hab_ich").setText(asHab_ich[indBand]));
			band.addContent(new Element("Datum").setText(arrErscheinungsdatum[indBand]));
				  
			titel.addContent(band);
			bandNr++;
			
		}
		
		root.addContent(titel);		
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

		String sHab_ich;
		if(nHab_ich == 0) sHab_ich = "N";
		else sHab_ich = "Y";
		
		String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
		Element titel = root.getChild(newTitel);
		
		Element band = new Element("Band_" + (nBandnr));
		band.setAttribute(new Attribute("id", ""+(nBandnr)));
		band.addContent(new Element("Untertitel").setText(sUntertitel));
		band.addContent(new Element("Preis").setText(""+(nPreis)));
		band.addContent(new Element("Hab_ich").setText(sHab_ich));
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

		String sHab_ich;
		if(nHab_ich == 0) sHab_ich = "N";
		else sHab_ich = "Y";
		
		String newTitel = sTitel.replaceAll("[^A-Za-z0-9]", "");
		Element titel = root.getChild(newTitel);
		
		Element info = titel.getChild("Info");
		info.getChild("Autor").setText(sAutor);
		info.getChild("Verlag").setText(sVerlag);
		info.getChild("Status").setText(sStatus);	 			 
		
		Element band = titel.getChild("Band_" + (nBandnr));
		band.getChild("Untertitel").setText(sUntertitel);
		band.getChild("Preis").setText(""+(nPreis));
		band.getChild("Hab_ich").setText(sHab_ich);
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
				if(c.getText().equals("N")){
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
			if(manga.getChild("Band_" + i).getChildTextNormalize("Hab_ich").equals("Y")){
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
			
			String sUntertitel = manga.getChild("Band_" + i).getChildTextNormalize("Untertitel");
			String sPreis = manga.getChild("Band_" + i).getChildTextNormalize("Preis");
			String sHab = manga.getChild("Band_" + i).getChildTextNormalize("Hab_ich");
			String sDate = manga.getChild("Band_" + i).getChildTextNormalize("Datum");
							
			sReturn[i][0] = sAutor;
			sReturn[i][1] = sVerlag;
			sReturn[i][2] = sStatus;
			sReturn[i][3] = sUntertitel;
			sReturn[i][4] = "" + i;
			sReturn[i][5] = sHab;
			sReturn[i][6] = sPreis;
			sReturn[i][7] = sDate;
							
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
				if(manga.getChild("Band_" + (j+1)).getChildTextNormalize("Hab_ich").equals("Y"))
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
			System.out.println(sReturn[i][0]);
			
			String sStatus = manga.getChild("Info").getChildText("Status");
			
			int nAnzahlBand = manga.getChildren().size()-1;
			
			int nHab = 0;
			for(int j = 0; j < nAnzahlBand; j++){
				if(manga.getChild("Band_" + (j+1)).getChildTextNormalize("Hab_ich").equals("Y"))
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
			System.out.println(sReturn[i][1]);
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
    	int nAnzahl = root.getChildren().size();
		String[][] sReturn = new String[nAnzahl][10];
		
		for(int i = 0; i < nAnzahl; i++){
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
				
				sReturn[i][0] = sTitel;
				sReturn[i][1] = sAutor;
				sReturn[i][2] = sVerlag;
				sReturn[i][3] = "" + nAnzahlBand;
				sReturn[i][4] = sStatus;
				sReturn[i][5] = "" + j;
				sReturn[i][6] = sUntertitel;
				sReturn[i][7] = sPreis;
				sReturn[i][8] = sHab;
				sReturn[i][9] = sDate;
								
			}  	
		}
		
		
		return sReturn;
    }

//    /**
//     * Gibt die gefilterten Verlag mit Mangatitel wieder und deren status für farbbestimmung 
//     * @param letter = Buchstabe nach dem gefiltert wird
//     * @return String[][] mit Titel[i][0], Farbe[i][1] und i = alle gefilterten Mangas
//     * @throws SQLException
//     */
//    public String[][] getReiheStartsWithVerlag(String letter) throws SQLException{
//    	statement = connect.createStatement();
//    	
//    	//select count(*) from mangadb.mangareihen;
//		//gibt die Anzahl aller Mangareihen aus
//		resultSet = statement
//				.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;;");
//		int nAnzManga = 0;
//		if(resultSet.next()){
//			nAnzManga = resultSet.getInt("AnzManga");
//		}
//    	
//    	//sReturn wird zurückgegeben    	
//    	String[][] sReturn = new String[nAnzManga][2];
//
//		//select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from mangadb.mangareihen m natural join mangadb.Baender b
//    	//where m.id = b.mangaid and b.hab_ich = 'Y'  and m.Titel like 'A%' group by b.mangaidorder by m.titel asc;
//		//zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, 
//    	//gibt den Status der Reihe und den Titel die mit A anfangen wieder, sortiert nach Name
//    	
//		resultSet = statement
//				.executeQuery("select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel " 
//						+ "from mangadb.mangareihen m natural join mangadb.Baender b " 
//						+ "where m.id = b.mangaid and b.hab_ich = 'Y' and m.Verlag like '" + letter + "%' " 
//						+ "group by b.mangaid order by m.titel asc;");
//
//		
//		int i = 0;
//		while (resultSet.next()) {
//			String sTitel = resultSet.getString("titel");
//			int nHab_ich = resultSet.getInt("Habe_ich");
//			int nBaender = resultSet.getInt("Baender");
//			String sStatus = resultSet.getString("Status");
//			
//			sReturn[i][0] = sTitel;
//			
//			if(nHab_ich == nBaender && sStatus.equals("abgeschlossen"))
//				sReturn[i][1] = "gelb";
//			else if(nHab_ich < nBaender && sStatus.equals("abgeschlossen"))
//				sReturn[i][1] = "rot";
//			else
//				sReturn[i][1] = "gruen";
//			
//			i++;
//		}
//		
//    	return sReturn;
//    }
    
//    /**
//     * Gibt die gefilterten Autor mit Mangatitel wieder und deren status für farbbestimmung
//     * @param letter = Buchstabe nach dem gefiltert wird
//     * @return String[][] mit Titel[i][0], Farbe[i][1] und i = alle gefilterten Mangas
//     * @throws SQLException
//     */
//    public String[][] getReiheStartsWithAutor(String letter) throws SQLException{
//    	statement = connect.createStatement();
//    	
//    	//select count(*) from mangadb.mangareihen;
//		//gibt die Anzahl aller Mangareihen aus
//		resultSet = statement
//				.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;;");
//		int nAnzManga = 0;
//		if(resultSet.next()){
//			nAnzManga = resultSet.getInt("AnzManga");
//		}
//    	
//    	//sReturn wird zurückgegeben    	
//    	String[][] sReturn = new String[nAnzManga][2];
//
//		//select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from mangadb.mangareihen m natural join mangadb.Baender b
//    	//where m.id = b.mangaid and b.hab_ich = 'Y'  and m.Titel like 'A%' group by b.mangaidorder by m.titel asc;
//		//zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, 
//    	//gibt den Status der Reihe und den Titel die mit A anfangen wieder, sortiert nach Name
//
//		resultSet = statement
//				.executeQuery("select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel " 
//						+ "from mangadb.mangareihen m natural join mangadb.Baender b " 
//						+ "where m.id = b.mangaid and b.hab_ich = 'Y' and m.Autor like '" + letter + "%' " 
//						+ "group by b.mangaid order by m.titel asc;");
//    	
//		int i = 0;
//		while (resultSet.next()) {
//			String sTitel = resultSet.getString("titel");
//			int nHab_ich = resultSet.getInt("Habe_ich");
//			int nBaender = resultSet.getInt("Baender");
//			String sStatus = resultSet.getString("Status");
//			
//			sReturn[i][0] = sTitel;
//			
//			if(nHab_ich == nBaender && sStatus.equals("abgeschlossen"))
//				sReturn[i][1] = "gelb";
//			else if(nHab_ich < nBaender && sStatus.equals("abgeschlossen"))
//				sReturn[i][1] = "rot";
//			else
//				sReturn[i][1] = "gruen";
//			
//			i++;
//		}
//		
//    	return sReturn;
//    }

    
//	/**
//	 * Dient zur Testzwecke, um Ergebnisse in Konsole zuschreiben
//	 */
//	public void writeResultSet() throws SQLException {
//		statement = connect.createStatement();
//		//zeige alle Informationen aus Mangareihen und Baender, sortiert nach Name
//		resultSet = statement
//				.executeQuery("select * from mangadb.mangareihen m left join mangadb.Baender b on m.id = b.mangaid order by titel asc");
//		System.out.println("Alle Informationen aus Mangareihen + Baender: ");
//		System.out.println();
//		DecimalFormat df = new DecimalFormat("#,##0.00");
//
//		// ResultSet is initially before the first data set
//		while (resultSet.next()) {
//			// It is possible to get the columns via name
//			// also possible to get the columns via the column number
//			// which starts at 1
//			// e.g. resultSet.getSTring(2);
//			String titel = resultSet.getString("titel");
//			String autor = resultSet.getString("autor");
//			String verlag = resultSet.getString("verlag");
//			int anzBaender = resultSet.getInt("Baender");
//			String hab_ich = resultSet.getString("Hab_ich");
//			double preis = resultSet.getDouble("preis");
//			String status = resultSet.getString("Status");
//			String untertitel = resultSet.getString("Untertitel");
//			String erscheinungsdatum = resultSet.getString("Erscheinungsdatum");
//			int bandNr = resultSet.getInt("BandNr");
//			System.out.println("Titel: " + titel + " " + bandNr);
//			System.out.println("Autor: " + autor);
//			System.out.println("Verlag: " + verlag);
//			System.out.println("Baender: " + anzBaender);
//			System.out.println("Hab ich: " + hab_ich);
//			System.out.println("Preis: " + df.format(preis) + " Euro");
//			System.out.println("Status: " + status);
//			System.out.println("Untertitel: " + untertitel);
//			System.out.println("Erscheinungsdatum: " + erscheinungsdatum);
//			System.out.println("----");
//		}
//
//		System.out.println("######");
//	}
	
//	   /**
//     * Gibt alle Informationen aus, die für Band-Detail-Seite nötig ist
//     * 
//     * @param titelManga  = Titel der ausgewählten Manga
//     * @return String[][] mit Untertitel[i][0], BandNr[i][1], Hab_ich[i][2], Preis[i][3], Erscheinung[i][4], Länge i = Anzahl aller Bände
//     */
//    public String[][] getBandDetails(String titelManga) throws SQLException{
//    	
//    	statement = connect.createStatement();
//    	
//    	//select Baender from mangadb.mangareihen m where m.titel = 'Aishiteruze Baby';
//		//gibt die Anzahl aller Mangas zu der Reihe aus
//		resultSet = statement
//				.executeQuery("select Baender from mangadb.mangareihen m where m.Titel = '" + titelManga + "';");
//		int nAnzBaender = 0;
//		if(resultSet.next()){
//			nAnzBaender = resultSet.getInt("Baender");
//		}
//    	
//    	//sReturn wird zurückgegeben    	
//    	String[][] sReturn = new String[nAnzBaender][5];
//    	
//    	//select * from mangadb.Baender b where (select id from mangadb.mangareihen m where m.titel = 'Aishiteruze Baby') = b.mangaid;
//		//besorgt alle Informationen aus Baender zu einer bestimmten Manga
//		resultSet = statement
//				.executeQuery("select * from mangadb.Baender b " 
//						+ "where (select id from mangadb.mangareihen m where m.titel = '" + titelManga + "') = b.mangaid;");
//		
//		//speichert die alle Informationen zu den Baendern
//		//Untertitel[i][0], BandNr[i][1], Hab_ich[i][2], Preis[i][4], Erscheinung[i][5]
//		int i = 0;
//		while (resultSet.next()) {
//			String sUntertitel = resultSet.getString("untertitel");
//			int  nBandNr = resultSet.getInt("bandnr");
//			String  sHab_ich = resultSet.getString("hab_ich");
//			double nPreis = resultSet.getDouble("Preis");
//			String  sErscheinung = resultSet.getString("erscheinungsdatum");
//			
//			sReturn[i][0] = sUntertitel;
//			sReturn[i][1] = "" + nBandNr;
//			sReturn[i][2] = sHab_ich;
//			//Gibt den Preis ordentlich in Dezimalzahl aus
//			DecimalFormat df = new DecimalFormat("#,##0.00");
//			sReturn[i][3] = "" + df.format(nPreis) + " Euro";
//			sReturn[i][4] = sErscheinung;
//			
//			i++;
//		}
//    	
//    	
//    	return sReturn;    	
//    }

}
