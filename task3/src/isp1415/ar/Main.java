package isp1415.ar;

import isp1415.ar.dbPack.*;
import isp1415.ar.guiPack.Start;



public class Main {
		
  public static void main(String[] args) throws Exception { 
	  
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
}
