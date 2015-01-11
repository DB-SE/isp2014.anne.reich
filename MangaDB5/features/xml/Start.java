import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

public class Start {

	private static XML xml;	
	
	//von Main ohne Datenbankanbindung
	public Start(XML xml) throws Exception{
		
		this.xml = xml;
		getStartInfo();
		
		fensterErzeugen();

	}
	
	public static XML getXml() {
		return xml;
	}
	
	/**
	 * erstellt die GUI
	 */
	public void fensterErzeugen(){		
				
		//TODO
		new Bibo(xml);
		fenster.setVisible(false);		
		
	}	

	/**
	 * liest alle Informationen aus für die Startseite
	 */
	private void getStartInfo(){
		startInfo = xml.getStart();
	}
	
	public void resetDB(){
		Start.getXml().deleteAndCreateDB();
	}
	
	public String[][] exportAll(){
		String[][] allMangas = Start.getXml().exportAll();
		
		return allMangas;
	}
	
	public void deleteAndCreateDB(){
		Start.getXml().deleteAndCreateDB();
	}
	
	public void insertManga(String sTitel, String sAutor, String sVerlag, int nAnzBaender, String sStatus, int[] arrHab, String[] arrsPreis, String[] arrUntertitel, String[] arrErscheinung){
		Start.getXml().insertManga(sTitel, sAutor, sVerlag, nAnzBaender, sStatus, arrHab, arrsPreis, arrUntertitel, arrErscheinung);
	}
	
	public void startStart(){
		try{
			new Start(Start.getXml());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
}
