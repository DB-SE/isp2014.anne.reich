

public class DatabaseAccess {
	
  /**
   * 
   */
	public void getConnection() {	  	  
		//über pointcut
	}
	
	/**
	 * 
	 */
	public void deleteAndCreateDB() {
	  //über pointcut
	}
	

	
	/**
	 * Methode wird aufgerufen, wenn eine neue Manga hinzugefügt wird.
	 * 
	 * @param sTitel (string) = Titel der Manga
	 * @param sAutor (string) = Autor der Manga
	 * @param sVerlag (string) = Verlag der Manga
	 * @param nAnzBaender (int) = die Anzahl der Baender der Reihe
	 * @param sStatus (string) = abgeschlossene oder fortzgesetzte Reihe in Deutschland
	 * @param arrHabIch (int[]) = 1 - Band habe ich, 0 - Band habe ich noch nicht,
	 *                  Länge = nAnzBaender
	 * @param arrPreis (double[]) = Preis von jedem Band, Länge = nAnzBaender
	 * @param arrUntertitel (string[]) = Untertitel von jedem Band (soweit einer existiert)
	 * @param arrErscheinungsdatum (string[]) = Erscheinungsdatum als String 
	 *                             (z.B. "Februar 2013")
	 */
	public void insertMangaXML(String sTitel, String sAutor, String sVerlag,
      int nAnzBaender, String sStatus, int[] arrHabIch,
      String[] arrPreis, String[] arrUntertitel,
      String[] arrErscheinungsdatum) {
    //über pointcut
	}
	
	/**
	 *  Methode wird aufgerufen, wenn eine neue Manga hinzugefügt wird.
	 * @param sTitel (string) = Titel der Manga
	 * @param sAutor (string) = Autor der Manga
	 * @param sVerlag (string) = Verlag der Manga
	 * @param nAnzBaender (int) = die Anzahl der Baender der Reihe
	 * @param sStatus (string) = abgeschlossene oder fortzgesetzte Reihe in Deutschland
	 * @param arrHabIch (int[]) = 1 - Band habe ich, 0 - Band habe ich noch nicht,
	 *                  Länge = nAnzBaender
	 * @param arrPreis (double[]) = Preis von jedem Band, Länge = nAnzBaender
	 * @param arrUntertitel (string[]) = Untertitel von jedem Band (soweit einer existiert)
	 * @param arrErscheinungsdatum (string[]) = Erscheinungsdatum als String 
	 *                             (z.B. "Februar 2013")
	 */
	public void insertManga(String sTitel, String sAutor, String sVerlag,
      int nAnzBaender, String sStatus, int[] arrHabIch,
      double[] arrPreis, String[] arrUntertitel,
      String[] arrErscheinungsdatum) {
    //über pointcut
	}
	
	/**
	 * Methode wird aufgerufen, wenn ein neuer Band in einer 
	 * existierende Mangareihe hinzugefügt wird.
	 * @param sTitel (string) = Titel der Manga
	 * @param nBandnr (int) = Band Nr. x von der Manga
	 * @param sUntertitel (string) = Untertitel vom Band (soweit einer existiert)
   * @param nPreis (double) = Preis vom Band
	 * @param nHabIch (int) = 1 - Band habe ich, 0 - Band habe ich noch nicht
	 * @param sErscheinungsdatum (string) = Erscheinungsdatum als String (z.B. "Februar 2013")
	 * @param sStatus (string) = abgeschlossene oder fortzgesetzte Reihe in Deutschland
	 */
	public void insertBandXML(String sTitel, int nBandnr, String sUntertitel,
      double nPreis, int nHabIch, String sErscheinungsdatum, String sStatus) {
    //über pointcut
	}
	
	/**
	 * Methode wird aufgerufen, wenn ein neuer Band in einer 
	 * existierende Mangareihe hinzugefügt wird.
	 * @param sTitel (string) = Titel der Manga
	 * @param nBandnr (int) = Band Nr. x von der Manga
	 * @param sUntertitel (string) = Untertitel vom Band (soweit einer existiert)
	 * @param nPreis (double) = Preis vom Band
	 * @param sHabIch (int) = 1 - Band habe ich, 0 - Band habe ich noch nicht
	 * @param sErscheinungsdatum (string) = Erscheinungsdatum als String (z.B. "Februar 2013")
	 * @param nAnzBaender (int) = die Anzahl der Baender der Reihe
	 * @param sVerlag (string) = Verlag der Manga
	 * @param sStatus (string) = abgeschlossene oder fortzgesetzte Reihe in Deutschland
	 */
	public void insertBand(String sTitel, int nBandnr, String sUntertitel,
      double nPreis, int sHabIch, String sErscheinungsdatum,
      int nAnzBaender, String sVerlag, String sStatus) {
    //über pointcut	  
	}
	
	/**
	 * Updatet/Aktualisiert die existierende Manga
	 * @param sTitel (string) = Titel der Manga
	 * @param sAutor (string) = Autor der Manga
	 * @param sVerlag (string) = Verlag der Manga
	 * @param nAnzBaender (int) = die Anzahl der Baender der Reihe
	 * @param sStatus (string) = abgeschlossene oder fortzgesetzte Reihe in Deutschland
	 * @param nBandnr (int) = Band Nr. x von der Manga
	 * @param nHabIch (int) = 1 - Band habe ich, 0 - Band habe ich noch nicht
	 * @param nPreis (double) = Preis vom Band
	 * @param sUntertitel (string) = Untertitel vom Band (soweit einer existiert)
	 * @param sErscheinungsdatum (string) = Erscheinungsdatum als String (z.B. "Februar 2013")
	 */
	public void editManga(String sTitel, String sAutor, String sVerlag,
      int nAnzBaender, String sStatus, int nBandnr, int nHabIch,
      double nPreis, String sUntertitel, String sErscheinungsdatum) {
    //über pointcut	  
	}
	
	/**
	 * Löscht die komplette Mangareihe.
	 * @param sTitel (string) = Titel der Manga
	 */
	public void deleteManga(String sTitel) {
    //über pointcut	  
	}
	
	/**
	 * Löscht den Band einer bestimmten Manga.
	 * @param nBandNr (int) = Band Nr. x von der Manga
	 * @param sTitel (string) = Titel der Manga
	 */
	public void deleteBandXML(int nBandNr, String sTitel) {
    //über pointcut	  
	}
	
	/**
	 * Löscht den Band einer bestimmten Manga.
	 * @param nBandNr (int) = Band Nr. x von der Manga
	 * @param nAnzBaender (int) = die Anzahl der Baender der Reihe
	 * @param sTitel (string) = Titel der Manga
	 */
	public void deleteBand(int nBandNr, int nAnzBaender, String sTitel) {
    //über pointcut	  
	}
	
	/**
	 * Gibt alle Informationen wieder, die für die Startseite gebraucht wird.
	 * @return String[][] mit Gesamtsumme[0][0], Anzahl meiner Mangareihen[1][0], 
	 * Mangas[2][0], Titel[i+3][0],Erscheinungsdatum[i+3][1] und Preis[i+3][2] aller Mangas
	 */
	public String[][] getStart() {
    //über pointcut
    return null;	  
	}
	
	/**
	 * Gibt alle Informationen aus, die für Bibo-Detail-Seite nötig sind.
	 * @param titelManga (string) = Titel der ausgewählten Manga
	 * @return String[] mit Titel[0], Autor[1], Verlag[2], hab_ich[3], Anzahl der Baender[4],
   *      Status[5], Gesamtkosten[6], Erscheinungsdatum meiner fehlender Manga[7]
	 */
	public String[] getBiboDetails(String titelManga) {
	  
	  return null;
	}
	
	/**
	 * 
	 * @param titelManga (string) = Titel der ausgewählten Manga
	 * @return String[][] mit Autor[i][0], Verlag[i][1], Status[i][2], Untertitel[i][3], 
   *      BandNr[i][4], Hab_ich[i][5], Preis[i][6], Erscheinung[i][7], i = Anzahl Baender
	 */
	public String[][] getEditDetails(String titelManga) {
    //über pointcut
	  
	  return null;
	}
	
	/**
	 * Gibt die komplette Mangatitel wieder und deren status für farbbestimmung.
	 * @return String[][] mit Titel[i][0], Farbe[i][1] und i = alle Mangas
	 */
	public String[][] getMangareiheTitel() {
    //über pointcut
	  return null;
	}
	
	/**
	 * Gibt die komplette Verläge wieder.
	 * @return String[] 
	 */
	public String[] getVerlag() {
    //über pointcut
	  return null;
	}
	
	/**
	 * Gibt die komplette Autoren wieder
	 * @return String[]
	 */
	public String[] getAutor() {
    //über pointcut
	  return null;
	}
	
	/**
	 * Gibt alle Titel von einem  Autoren wieder.
	 * @param autor (string) = Autor der Manga
	 * @return String[]
	 */
	public String[] getTitelByAutor(String autor) {
    //über pointcut
	  return null;
	}
	
	/**
	 * Gibt alle Titel von einem  Autoren wieder.
	 * @param verlag (string) = Verlag der Manga
	 * @return String[]
	 */
	public String[] getTitelByVerlag(String verlag) {
    //über pointcut
	  return null;
	}
	
	/**
	 * Gibt die gefilterten Mangatitel wieder und deren status für farbbestimmung.
	 * @param letter (string) = Buchstabe nach dem gefiltert wird
	 * @return String[][] mit Titel[i][0], Farbe[i][1] und i = alle gefilterten Mangas
	 */
	public String[][] getReiheStartsWith(String letter) {
    //über pointcut
	  return null;
	}
	
	/**
	 * 
	 * @return String[][] Titel[i][0], Autor[i][1], Verlag[i][2], BanzAnz[i][3], 
	 * Status[i][4], BandNr[i][5], Untertitel[i][6], Preis[i][7], Habe_ich[i][8], 
	 * Erscheinung[i][9]
	 */
	public String[][] exportAll() {
    //über pointcut
	   return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDAType() {
	  
	  return "db";
	}
	

}
