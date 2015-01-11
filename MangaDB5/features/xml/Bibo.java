import java.util.ArrayList;
import java.util.Collections;

/**
 * TODO description
 */
public class Bibo {
	
	private static XML xml;
	
	public static XML getXml() {
		return xml;
	}
	
	public Bibo(XML xml){
		Bibo.xml = xml;
		getTitelColorList();
		
		fensterErzeugen();
	}
	
	public void getStart(){
		try {
			new Start(xml);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static void getNeuEditDetail(String action, String title){
		new NeuEditDetail(xml, action, title);
	}
	
	private void getTitelColorList(){
		mangaList = xml.getMangareiheTitel();
		
	}
	
	
	public static void getTitelColorListSort(String letter){
		
			mangaListSort = xml.getReiheStartsWith(letter);
	}
	
	private static void getMangaInfo(String mangaTitel){
		
			mangaDetail = xml.getBiboDetails(mangaTitel);
		
	}
	
	private static String[] getAutorList(){
		
			return xml.getAutor();
	}
	
	public static ArrayList<String> getTitelByAutor(String autor){
		String[] titel;
		
			titel = xml.getTitelByAutor(autor);
		ArrayList<String> arrTitel = new ArrayList<String>();
		Collections.sort(arrTitel);
		
		for(int i = 0; i < titel.length; i++){
			arrTitel.add(titel[i]);
		}		
		
		return arrTitel;
	}
	
	private static String[] getVerlagList(){
		
			return xml.getVerlag();
	}
	
	public static ArrayList<String> getTitelByVerlag(String verlag){
		String[] titel;
		
			titel = xml.getTitelByVerlag(verlag);
		
		ArrayList<String> arrTitel = new ArrayList<String>();
		Collections.sort(arrTitel);
		
		for(int i = 0; i < titel.length; i++){
			arrTitel.add(titel[i]);
		}		
		
		return arrTitel;
	}
	
	private static String[] getOnlyTitelList(){
		String[][] titelListe;
		
			titelListe = xml.getMangareiheTitel();
		
		String[] titel = new String[titelListe.length];
		
		for(int i = 0; i < titelListe.length; i++){
			titel[i] = titelListe[i][0];
		}
		
		return titel;		
	}

}