


public class Main {

  /**
   * 
   * @param args empty
   */
	public static void main(String[] args) {
	  
	  DatabaseAccess dba = new DatabaseAccess();
	  dba.getConnection();
	  
	  new Start(dba);

	}

}
