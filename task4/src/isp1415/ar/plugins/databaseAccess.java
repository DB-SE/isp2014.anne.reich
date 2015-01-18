package isp1415.ar.plugins;


public interface databaseAccess {
	
	public void getConnection();
	
	public void deleteAndCreateDB();
	
	public void insertManga(String sTitel, String sAutor, String sVerlag,
			int nAnzBaender, String sStatus, int[] arrHab_ich,
			String[] arrPreis, String[] arrUntertitel,
			String[] arrErscheinungsdatum);
	
	public void insertBand(String sTitel, int nBandnr, String sUntertitel,
			double nPreis, int nHab_ich, String sErscheinungsdatum,
			int nAnzBaender, String sVerlag, String sStatus);
	
	public void editManga(String sTitel, String sAutor, String sVerlag,
			int nAnzBaender, String sStatus, int nBandnr, int nHab_ich,
			double nPreis, String sUntertitel, String sErscheinungsdatum);	
	
	public void deleteManga(String sTitel);
	
	public void deleteBand(int nBandNr, int nAnzBaender, String sTitel);
	
	public void saveChange();
	
	public double[] selectSum();
	
	public String[][] getStart();
	
	public String[] getBiboDetails(String titelManga);
	
	public String[][] getEditDetails(String titelManga);
	
	public String[][] getMangareiheTitel();
	
	public String[] getVerlag();
	
	public String[] getAutor();
	
	public String[] getTitelByAutor(String autor);
	
	public String[] getTitelByVerlag(String verlag);
	
	public String[][] getReiheStartsWith(String letter);
	
	public String[][] exportAll();
	
	

}
