
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

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

public class DB {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	/**
	 * - Verbinden mit dem lokalen SQL-Server 
	 * - testen, ob eine Datenbank existiert
	 */
	public void getConnection(){
		// This will load the MySQL driver, each DB has its own driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
	
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/mangadb?"
					+ "user=root&password=a34920");
	
			//connect = DriverManager.getConnection("jdbc:mysql://uereich.de:3306/usr_web812_2?user=web812&password=9OwUHTLr");
			
			// erzeugen eines Statement-Objektes
			statement = connect.createStatement();
	
			
			resultSet = statement
					.executeQuery("select mangareihen.Titel from mangadb.mangareihen");
			
			//speichert diese Informationen einmal als Titel +Bandnr in [i][0] und das Datum in [i][1]
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
			}
			
			System.out.println("Connection erfolgreich");
//			
//			
//			// ausgeben der existierenden Datenbanken
//			DatabaseMetaData meta = connect.getMetaData();
//			ResultSet res = meta.getTables(null, null, null,
//					new String[] { "TABLE" });
//			int[] exist = new int[2];
//			int i = 0;
//			
//			// wenn welche existieren, so werden sie mit 1 gekennzeichnet
//			while (res.next()) {
//				if (res.getString("TABLE_NAME").equals("baender")
//						|| res.getString("TABLE_NAME").equals("mangareihen"))
//					exist[i] = 1;
//				else
//					exist[i] = 0;
//	
//				i++;
//			}
//	
//			// wenn keine Datenbank existieren sollte, werden diese neu erstellt
//			if (exist[0] == 0)
//				createMangareihe();
//			if (exist[1] == 0)
//				createBaender();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e);
		} catch (SQLException e) {
			System.out.println("SQLException: " + e);
		} catch (Exception e){
			System.out.println("Exception: " + e);
		}

	}
	
	public void deleteAndCreateDB() throws SQLException{
		// erzeugen eines Statement-Objektes
		statement = connect.createStatement();

		// löschen bzw. leeren der Datenbanken
		statement.executeUpdate("drop table mangadb.Baender");
		statement.executeUpdate("drop table mangadb.mangareihen");
		
		createMangareihe();
		createBaender();
	}

	/**
	 * - erstellt eine Tabelle Mangareihen im Schema Mangadb 
	 * - ID(PK,NN,AI),Titel(NN,UQ), Autor, Verlag, Baender, Status
	 */
	private void createMangareihe() throws SQLException {
		statement = connect.createStatement();
		String table = "create table mangadb.mangareihen("
				+ "ID int NOT NULL AUTO_INCREMENT, "
				+ "Titel varchar(60) not null, " 
				+ "Autor varchar(60), "
				+ "Verlag varchar(60), " 
				+ "Baender int, "
				+ "Status ENUM('abgeschlossen', 'fortgesetzt'), "
				+ "primary key (id), "
				+ "UNIQUE INDEX `Titel_UNIQUE` (`Titel` ASC))";

		statement.executeUpdate(table);
	}

	/**
	 * - erstellt eine Tabelle Baender im Schema Mangadb 
	 * - BandNr, Untertitel, Preis, Hab_ich, Erscheinungsdatum, MangaID(FK->Mangareihen(ID))
	 */
	private void createBaender() throws SQLException {
		statement = connect.createStatement();
		String table = "create table mangadb.Baender(" 
				+ "BandNr int, "
				+ "Untertitel varchar(60), " 
				+ "Preis numeric(5,2), "
				+ "Hab_ich int, " 
				+ "Erscheinungsdatum varchar(45), "
				+ "MangaID int, "
				+ "foreign key (MangaID) references mangadb.mangareihen(ID)"
				+ "on delete cascade on update cascade)";

		statement.executeUpdate(table);
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
			String[] arrErscheinungsdatum) throws SQLException {

		// erzeugen eines PreparedStatement
		// Füge Eintrag in mangadb.mangareihen ein
		preparedStatement = connect
				.prepareStatement("insert into mangadb.mangareihen values (default, ?, ?, ?, ?, ?)");
		// "defaultID, Titel, Autor, Verlag, Baender, Status");
		// Parameters start with 1
		preparedStatement.setString(1, sTitel);
		preparedStatement.setString(2, sAutor);
		preparedStatement.setString(3, sVerlag);
		preparedStatement.setInt(4, nAnzBaender);
		preparedStatement.setString(5, sStatus);
		preparedStatement.executeUpdate();

		// gibt die ID von mangadb.mangareihen, damit diese dann als fk für
		// mangadb.Baender genutzt werden kann
		statement = connect.createStatement();
		resultSet = statement
				.executeQuery("select id from mangadb.mangareihen where titel = '" + sTitel + "'");
		int bandID = 0;
		if (resultSet.next())
			bandID = resultSet.getInt("ID");

		for (int i = 0; i < nAnzBaender; i++) {
			// Füge Einträge in mangadb.Baender ein (je nachdem wie viele Baender
			// es insgesamt gibt)
			preparedStatement = connect
					.prepareStatement("insert into mangadb.Baender values (?, ?, ?, ?, ?, ?)");
			// "BandNr, Untertitel, Preis, Hab_ich, Neuerscheinung, MangaID");
			preparedStatement.setInt(1, i + 1);
			preparedStatement.setString(2, arrUntertitel[i]);
			preparedStatement.setDouble(3, arrPreis[i]);
			preparedStatement.setInt(4, arrHab_ich[i]);
			preparedStatement.setString(5, arrErscheinungsdatum[i]);
			preparedStatement.setInt(6, bandID);
			preparedStatement.executeUpdate();
		}
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
			double nPreis, int sHab_ich, String sErscheinungsdatum,
			int nAnzBaender, String sVerlag, String sStatus) throws SQLException {

		/*
		 * insert into baender values (21, "-", 6.50, "N", "-", (select id from
		 * mangareihen where titel = "Angel Sanctuary"));
		 */
		
		// gibt die ID von mangadb.mangareihen, damit diese dann als fk für
	    // mangadb.Baender genutzt werden kann
		statement = connect.createStatement();
		resultSet = statement
				.executeQuery("select id from mangadb.mangareihen where titel = '" + sTitel + "'");
		int bandID = 0;
		if (resultSet.next())
			bandID = resultSet.getInt("ID");

		// Füge Eintrag in mangadb.Baender ein
		preparedStatement = connect
				.prepareStatement("insert into mangadb.Baender values (?, ?, ?, ?, ?, ?)");
		// "BandNr, Untertitel, Preis, Hab_ich, Neuerscheinung, MangaID");
		preparedStatement.setInt(1, nBandnr);
		preparedStatement.setString(2, sUntertitel);
		preparedStatement.setDouble(3, nPreis);
		preparedStatement.setInt(4, sHab_ich);
		preparedStatement.setString(5, sErscheinungsdatum);
		preparedStatement.setInt(6, bandID);
		preparedStatement.executeUpdate();

		//Wenn ein neuer Band hinzukommt, kann eventuell sich der Status, Verlag oder die Anzahl aller Baendern aendern
		//daher muss dies zusaützlich aktualisiert werden
		preparedStatement = connect
				.prepareStatement("update mangadb.mangareihen m"
						+ " set m.verlag = '" + sVerlag + "'," 
						+ " m.baender = " + nAnzBaender + "," 
						+ " m.status = '" + sStatus + "'" 
						+ " where m.id = " + bandID);
		preparedStatement.executeUpdate();

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
			int nAnzBaender, String sStatus, int nBandnr, int sHab_ich,
			double nPreis, String sUntertitel, String sErscheinungsdatum)
			throws SQLException {

		/*
		 * update mangareihen m, baender b set m.titel = "Angel Sanctuary",
		 * m.autor = "Kaori Yuki", m.verlag = "Carlsen Comics", m.baender = 20,
		 * m.status = 'abgeschlossen', b.bandnr = 10, b.untertitel = null,
		 * b.preis = 6.00, b.hab_ich = "y", b.neuerscheinung = null where m.id =
		 * b.mangaid and m.titel = "Angel Sanctuary" and b.bandnr = 20;
		 */

		preparedStatement = connect
				.prepareStatement("update mangadb.mangareihen m, mangadb.Baender b"
						+ " set m.titel = '" + sTitel + "',"
						+ " m.autor = '" + sAutor + "',"
						+ " m.verlag = '" + sVerlag + "',"
						+ " m.baender = " + nAnzBaender + ","
						+ " m.status = '" + sStatus + "',"
						+ " b.bandnr = " + nBandnr + ","
						+ " b.untertitel = '" + sUntertitel + "',"
						+ " b.preis = " + nPreis + ","
						+ " b.hab_ich = " + sHab_ich + ","
						+ " b.erscheinungsdatum = '" + sErscheinungsdatum + "'"
						+ "where m.id = b.mangaid and m.titel = '" + sTitel + "' and b.bandnr = " + nBandnr);
		preparedStatement.executeUpdate();

	}

	/**
	 * Löscht die komplette Mangareihe
	 * 
	 * @param sTitel
	 * 				(string) = Titel der Manga
	 */
	public void deleteManga(String sTitel) throws SQLException {

		preparedStatement = connect
				.prepareStatement("delete from mangadb.mangareihen where titel = '"	+ sTitel + "'");
		preparedStatement.executeUpdate();

	}

	/**
	 * Löscht den Band einer bestimmten Manga
	 * 
	 * @param sTitel
	 *            (string) = Titel der Manga
	 * @param nBandnr
	 *            (int) = Band Nr. x von der Manga
	 */
	public void deleteBand(int nBandNr, int nAnzBaender, String sTitel) throws SQLException {
		/*
		 * delete from mangadb.Baender where bandnr = 3 and (select id from
		 * mangadb.mangareihen where titel like "%hack%")
		 */
		//Wählt die Bandnr aus und die ID, wo die MangaID mit der ID zusammenpasst, mit dem richtigen Titel
		preparedStatement = connect
				.prepareStatement(
						"delete from mangadb.Baender where bandnr = " + nBandNr + " and " 
						+ "(select id from mangadb.mangareihen where titel = '"	+ sTitel + "') = mangaid;");
		preparedStatement.executeUpdate();
		
		/*
		 * SELECT * from mangadb.mangareihen m natural join mangadb.Baender b where m.id = b.mangaid
		 */
		
		preparedStatement = connect
				.prepareStatement("update mangadb.mangareihen m"
						+ " set m.baender = " + nAnzBaender  
						+ " where m.titel = '" + sTitel + "';");
		preparedStatement.executeUpdate();

	}

	/**
	 * Berechnet die Gesamtpreis aller Mangas (die ich besitze)
	 * Berechnet die Anzahl aller Mangareihen (die ich besitze)
	 * Berechnet die Anzahl aller Mangabaender (die ich besitze)
	 */
	private double[] selectSum(){
		/*
		 * select sum(preis) as 'Gesamtpreis' from mangadb.Baender b where b.hab_ich = 'Y';
		 * select count(*) as 'Alle Mangareihen' from mangadb.mangareihen;
		 * select count(*) as 'Alle Mangas' from mangadb.Baender b where b.hab_ich= 'Y';
		 */

		double[] sum = new double[3];
		try{
			statement = connect.createStatement();
			//Gesamtpreis
			resultSet = statement
					.executeQuery("select sum(preis) as 'Gesamtpreis' from mangadb.Baender b where b.hab_ich = 1");
			if (resultSet.next()) {
				sum[0] = resultSet.getDouble("Gesamtpreis");
			}
	
			//Anzahl aller Mangareihen
			resultSet = statement
					.executeQuery("select count(*) as 'Alle Mangareihen' from mangadb.mangareihen m");
			if (resultSet.next()) {
				sum[1] = resultSet.getDouble("Alle Mangareihen");
			}
	
			//Anzahl aller Mangabaender
			resultSet = statement
					.executeQuery("select count(*) as 'Alle Mangas' from mangadb.Baender b where b.hab_ich = 1");
			if (resultSet.next()) {
				sum[2] = resultSet.getDouble("Alle Mangas");
			}
			
			return sum;
		}
		catch(Exception e){
			System.out.println("Exception: " + e);
			sum[0] = 0;
			sum[1] = 0;
			sum[2] = 0; 
			return sum;
		}

	}

	/**
	 * Gibt alle Informationen wieder, die für die Startseite gebraucht wird 
	 * 
	 * @return String[][] mit Gesamtsumme[0][0], Anzahl meiner Mangareihen[1][0], Mangas[2][0], Titel[i+3][0],Erscheinungsdatum[i+3][1] und Preis[i+3][2] aller Mangas
	 * @throws SQLException 
	 */
    public String[][] getStart() throws SQLException{
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
		
		
		//select m.Titel, b.Erscheinungsdatum, b.BandNr 
		//from mangadb.mangareihen m natural join mangadb.Baender b 
		//where b.hab_ich = 'N' and m.ID = b.MangaID group by m.Titel
		statement = connect.createStatement();
		//besorgt Titel, Bandnr, Datum gruppiert nach Titel und wo hab_ich=N und m.id = b.mangaid ist
		resultSet = statement
				.executeQuery("select m.Titel, b.Erscheinungsdatum, b.BandNr, b.Preis" 
						+ " from mangadb.mangareihen m natural join mangadb.Baender b" 
						+ " where b.hab_ich = 'N' and m.ID = b.MangaID" 
						+ " group by m.Titel");
		int i = 0;
		//speichert diese Informationen einmal als Titel +Bandnr in [i][0] und das Datum in [i][1]
		while (resultSet.next()) {
			String sTitel = resultSet.getString("titel");
			String  sErscheinungsdatum = resultSet.getString("erscheinungsdatum");
			int nBandnr = resultSet.getInt("Bandnr");
			double nPreis = resultSet.getDouble("preis");
			
			sReturn[3+i][0] = sTitel + " " + nBandnr;
			sReturn[3+i][1] = sErscheinungsdatum;
			sReturn[3+i][2] = "" + df.format(nPreis);
			i++;
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
    public String[] getBiboDetails(String titelManga) throws SQLException{
    	//sReturn wird zurückgegeben
    	String[] sReturn = new String[8];
		
    	statement = connect.createStatement();
    	
    	//select * from mangadb.mangareihen m where m.titel = 'Aishiteruze Baby';
		//besorgt alle Informationen aus Mangareihen zu einer bestimmten Manga
		resultSet = statement
				.executeQuery("select * from mangadb.mangareihen m where m.Titel = '" + titelManga + "';");
		
		//speichert die allg. Information zur Manga 
		//Titel[0], Autor[1], Verlag[2], Anzahl der Baender[4], Status[5], int ID = id;
		int ID = 0;
		while (resultSet.next()) {
			String sTitel = resultSet.getString("titel");
			String  sAutor = resultSet.getString("autor");
			String  sVerlag = resultSet.getString("verlag");
			int nAnzBaender = resultSet.getInt("Baender");
			String  sStatus = resultSet.getString("status");
			ID = resultSet.getInt("id");
			
			sReturn[0] = sTitel;
			sReturn[1] = sAutor;
			sReturn[2] = sVerlag;
			sReturn[4] = "" + nAnzBaender;
			sReturn[5] = sStatus;
		}
		
		//select count(*) as 'hab_ich_summe' from mangadb.Baender b where b.mangaid = 69 and b.hab_ich = 'Y';
		//zählt alle Baender aus dieser Reihe, die ich habe
		resultSet = statement
				.executeQuery("select count(*) as 'hab_ich_sum' " 
						+ "from mangadb.Baender b " 
						+ "where b.mangaid = " + ID + " and b.hab_ich = 1;");
		
		//speichert die Summe
		//hab_ich[3]
		while (resultSet.next()) {
			int nHabIch_Sum = resultSet.getInt("hab_ich_sum");
			
			sReturn[3] = "" + nHabIch_Sum;
		}
		
    	//select sum(preis) as 'GesamtpreisManga' from mangadb.Baender b where b.mangaid = 69 and b.hab_ich = 'Y';
		//zählt Gesamtpreis der Reihe, die ich habe
		resultSet = statement
				.executeQuery("select sum(preis) as 'GesamtpreisManga' " 
						+ "from mangadb.Baender b " 
						+ "where b.mangaid = " + ID + " and b.hab_ich = 1;");
		
		//speichert die Summe
		//Gesamtpreis[6]
		while (resultSet.next()) {
			double nGesamtpreis = resultSet.getDouble("GesamtpreisManga");

	    	//Gibt den Preis ordentlich in Dezimalzahl aus
			DecimalFormat df = new DecimalFormat("#,##0.00");
			sReturn[6] = "" + df.format(nGesamtpreis) + " Euro";
		}
		
    	//select b.Erscheinungsdatum, b.BandNr  from mangadb.Baender b where b.hab_ich = 'N' and b.mangaID = 69;
		//gibt Erscheinungsdatum vom ersten Band aus, den ich noch nicht habe.
		resultSet = statement
				.executeQuery("select b.Erscheinungsdatum, b.BandNr " 
						+ "from mangadb.Baender b " 
						+ "where b.mangaID = " + ID + " and b.hab_ich = 0;");
		
		//speichert die Werte
		//Erscheinungsdatum meiner fehlender Manga[7]
		if (resultSet.next()) {
			String sErscheinungsdatum = resultSet.getString("Erscheinungsdatum");
			int nBandNr = resultSet.getInt("Bandnr");

			//Gibt das Erscheinunsdatum meiner fehlender Mangas aus
			sReturn[7] = "Band " + nBandNr + " - " + sErscheinungsdatum;
		}
		    	
    	return sReturn;
    }
    
    /**
     * 
     * @param titelManga = Titel der ausgewählten Manga
     * @return String[][] mit Autor[i][0], Verlag[i][1], Status[i][2], Untertitel[i][3], 
     * 			BandNr[i][4], Hab_ich[i][5], Preis[i][6], Erscheinung[i][7], i = Anzahl Baender
     * @throws SQLException
     */
    public String[][] getEditDetails(String titelManga) throws SQLException{
      	
    	statement = connect.createStatement();
    	
    	//select Baender from mangadb.mangareihen m where m.titel = 'Aishiteruze Baby';
		//gibt die Anzahl aller Mangas zu der Reihe aus
		resultSet = statement
				.executeQuery("select Baender from mangadb.mangareihen m where m.Titel = '" + titelManga + "';");
		int nAnzBaender = 0;
		if(resultSet.next()){
			nAnzBaender = resultSet.getInt("Baender");
		}
    	
    	//sReturn wird zurückgegeben    	
    	String[][] sReturn = new String[nAnzBaender][8];
    	
    	//select * from mangadb.mangareihen m natural join mangadb.Baender b 
    	//where m.titel = "Angel Sanctuary" and m.id = b.mangaid;
		//besorgt alle Informationen von einem Manga
		resultSet = statement
				.executeQuery("SELECT * FROM mangadb.mangareihen m natural join mangadb.Baender b " +
						"where m.titel = '" + titelManga + "' and m.id = b.mangaid;");
		
		//Autor[i][0], Verlag[i][1], Status[i][2], Untertitel[i][3], BandNr[i][4], Hab_ich[i][5], Preis[i][6], Erscheinung[i][7], i = Anzahl Baender
		int i = 0;
		while (resultSet.next()) {
			
			String sAutor = resultSet.getString("autor");
			String sVerlag = resultSet.getString("verlag");
			String sStatus = resultSet.getString("status");
			String sUntertitel = resultSet.getString("untertitel");
			int  nBandNr = resultSet.getInt("bandnr");
			int  nHab_ich = resultSet.getInt("hab_ich");
			double nPreis = resultSet.getDouble("Preis");
			String  sErscheinung = resultSet.getString("erscheinungsdatum");
			
			sReturn[i][0] = sAutor;
			sReturn[i][1] = sVerlag;
			sReturn[i][2] = sStatus;
			sReturn[i][3] = sUntertitel;
			sReturn[i][4] = "" + nBandNr;
			sReturn[i][5] = "" + nHab_ich;
			//Gibt den Preis ordentlich in Dezimalzahl aus
			DecimalFormat df = new DecimalFormat("#,##0.00");
			sReturn[i][6] = "" + df.format(nPreis) + " Euro";
			sReturn[i][7] = sErscheinung;
			
			i++;
		}
    	
    	
    	return sReturn;    	

    }
    
    /**
     * Gibt die komplette Mangatitel wieder und deren status für farbbestimmung
     * @return String[][] mit Titel[i][0], Farbe[i][1] und i = alle Mangas
     * @throws SQLException 
     */
    public String[][] getMangareiheTitel() throws SQLException{
    	statement = connect.createStatement();
    	
    	//select count(*) from mangadb.mangareihen;
		//gibt die Anzahl aller Mangareihen aus
		resultSet = statement
				.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
		int nAnzManga = 0;
		if(resultSet.next()){
			nAnzManga = resultSet.getInt("AnzManga");
		}
    	
    	//sReturn wird zurückgegeben    	
    	String[][] sReturn = new String[nAnzManga][2];
    	
    	//select m.Titel from mangadb.Mangareihen m order by m.Titel asc;
    	//gibt alle Titel wieder
		resultSet = statement
				.executeQuery("select m.Titel, m.Status from mangadb.mangareihen m order by m.titel asc;");
		int i = 0; 
		while(resultSet.next()){
			sReturn[i][0] = resultSet.getString("titel");
			String sStatus = resultSet.getString("Status");

			if(sStatus.equals("abgeschlossen"))
				sReturn[i][1] = "rot";
			else
				sReturn[i][1] = "gruen";	
			
			i++;
		}

		//select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from mangadb.mangareihen m natural join mangadb.Baender b
		//where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
		//zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den Status der Reihe und den Titel wieder
		resultSet = statement
				.executeQuery("select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel " 
						+ "from mangadb.mangareihen m natural join mangadb.Baender b " 
						+ "where m.id = b.mangaid and b.hab_ich = 1 " 
						+ "group by b.mangaid order by m.titel asc;");
		
		i = 0;
		while (resultSet.next()) {
			String sTitel = resultSet.getString("titel");
			int nHab_ich = resultSet.getInt("Habe_ich");
			int nBaender = resultSet.getInt("Baender");
			String sStatus = resultSet.getString("Status");
			
			while(true){				
				if(sReturn[i][0].equals(sTitel)){
					if(nHab_ich == nBaender && sStatus.equals("abgeschlossen")){
						sReturn[i][1] = "gelb";
						i++;
						break;
					}
					else if(nHab_ich < nBaender && sStatus.equals("abgeschlossen")){
						sReturn[i][1] = "rot";
						i++;
						break;
					}
					else{
						sReturn[i][1] = "gruen";
						i++;
						break;
					}
				}
				i++;
			}
		}
		
    	return sReturn;
		
    }
 
    /**
     * Gibt die komplette Verläge wieder
     * @return String[] 
     * @throws SQLException 
     */   
    public String[] getVerlag() throws SQLException{
    	statement = connect.createStatement();
    	
    	//select count(*) from mangadb.mangareihen;
		//gibt die Anzahl aller Mangareihen aus
		resultSet = statement
				.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
		int nAnzManga = 0;
		if(resultSet.next()){
			nAnzManga = resultSet.getInt("AnzManga");
		}
    	
    	//sReturn wird zurückgegeben    	
    	String[] sReturn = new String[nAnzManga];

		//select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from mangadb.mangareihen m natural join mangadb.Baender b
		//where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
		//zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den Status der Reihe und den Titel wieder
		resultSet = statement
				.executeQuery("select m.verlag from mangadb.mangareihen m");
		
		int i = 0;
		while (resultSet.next()) {
			String sVerlag = resultSet.getString("verlag");
			
			sReturn[i] = sVerlag;			
			i++;
		}
		
    	return sReturn;
    }

    /**
     * Gibt die komplette Autoren wieder
     * @return String[]
     * @throws SQLException 
     */   
    public String[] getAutor() throws SQLException{
    	statement = connect.createStatement();
    	
    	//select count(*) from mangadb.mangareihen;
		//gibt die Anzahl aller Mangareihen aus
		resultSet = statement
				.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
		int nAnzManga = 0;
		if(resultSet.next()){
			nAnzManga = resultSet.getInt("AnzManga");
		}
    	
    	//sReturn wird zurückgegeben    	
    	String[] sReturn = new String[nAnzManga];

		//select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from mangadb.mangareihen m natural join mangadb.Baender b
		//where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
		//zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den Status der Reihe und den Titel wieder
		resultSet = statement
				.executeQuery("select m.autor from mangadb.mangareihen m");
		
		int i = 0;
		while (resultSet.next()) {
			String sAutor = resultSet.getString("autor");
			
			sReturn[i] = sAutor;
			
			i++;
		}
		
    	return sReturn;
    }

    /**
     * Gibt alle Titel von einem  Autoren wieder
     * @return String[]
     * @throws SQLException 
     */   
    public String[] getTitelByAutor(String autor) throws SQLException{
    	statement = connect.createStatement();
    	
    	//select count(*) from mangadb.mangareihen;
		//gibt die Anzahl aller Mangareihen aus
		resultSet = statement
				.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
		int nAnzManga = 0;
		if(resultSet.next()){
			nAnzManga = resultSet.getInt("AnzManga");
		}
    	
    	//sReturn wird zurückgegeben    	
    	String[] sReturn = new String[nAnzManga];

		//select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from mangadb.mangareihen m natural join mangadb.Baender b
		//where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
		//zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den Status der Reihe und den Titel wieder
		resultSet = statement
				.executeQuery("select m.titel from mangadb.mangareihen m where m.autor = '" + autor + "';");
		
		int i = 0;
		while (resultSet.next()) {
			String sTitel = resultSet.getString("titel");
			sReturn[i] = sTitel;
			
			i++;
		}
		
    	return sReturn;
    }

    /**
     * Gibt alle Titel von einem  Autoren wieder
     * @return String[]
     * @throws SQLException 
     */   
    public String[] getTitelByVerlag(String verlag) throws SQLException{
    	statement = connect.createStatement();
    	
    	//select count(*) from mangadb.mangareihen;
		//gibt die Anzahl aller Mangareihen aus
		resultSet = statement
				.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
		int nAnzManga = 0;
		if(resultSet.next()){
			nAnzManga = resultSet.getInt("AnzManga");
		}
    	
    	//sReturn wird zurückgegeben    	
    	String[] sReturn = new String[nAnzManga];

		//select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from mangadb.mangareihen m natural join mangadb.Baender b
		//where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
		//zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den Status der Reihe und den Titel wieder
		resultSet = statement
				.executeQuery("select m.titel from mangadb.mangareihen m where m.verlag = '" + verlag + "';");
		
		int i = 0;
		while (resultSet.next()) {
			String sTitel = resultSet.getString("titel");
			sReturn[i] = sTitel;
			
			i++;
		}
		
    	return sReturn;
    }
    
    /**
     * Gibt die gefilterten Mangatitel wieder und deren status für farbbestimmung
     * @param letter = Buchstabe nach dem gefiltert wird
     * @return String[][] mit Titel[i][0], Farbe[i][1] und i = alle gefilterten Mangas
     * @throws SQLException
     */
    public String[][] getReiheStartsWith(String letter) throws SQLException{
    	statement = connect.createStatement();
    	
    	//select count(*) from mangadb.mangareihen;
		//gibt die Anzahl aller Mangareihen aus
		resultSet = statement
				.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
		int nAnzManga = 0;
		if(resultSet.next()){
			nAnzManga = resultSet.getInt("AnzManga");
		}
    	
    	//sReturn wird zurückgegeben    	
    	String[][] sReturn = new String[nAnzManga][2];

		//select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from mangadb.mangareihen m natural join mangadb.Baender b
    	//where m.id = b.mangaid and b.hab_ich = 'Y'  and m.Titel like 'A%' group by b.mangaidorder by m.titel asc;
		//zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, 
    	//gibt den Status der Reihe und den Titel die mit A anfangen wieder, sortiert nach Name
    	if(letter.equals("^[A-Z]")){
    		resultSet = statement
    				.executeQuery("select m.Titel, m.Status from mangadb.mangareihen m where m.Titel not rlike '^[A-Z]' order by m.titel asc;");
    		
    		int i = 0; 
    		while(resultSet.next()){
    			sReturn[i][0] = resultSet.getString("titel");
    			String sStatus = resultSet.getString("Status");

    			if(sStatus.equals("abgeschlossen"))
    				sReturn[i][1] = "rot";
    			else
    				sReturn[i][1] = "gruen";	
    			
    			i++;
    		}
    		
    		resultSet = statement
    				.executeQuery("select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel " 
    						+ "from mangadb.mangareihen m natural join mangadb.Baender b " 
    						+ "where m.id = b.mangaid and b.hab_ich = 1 and m.Titel not rlike '^[A-Z]' " 
    						+ "group by b.mangaid order by m.titel asc;");    		
    		
    	}
    	else{
    		resultSet = statement
    				.executeQuery("select m.Titel, m.Status from mangadb.mangareihen m where m.Titel like '" + letter + "%' order by m.titel asc;");
    		
    		int i = 0; 
    		while(resultSet.next()){
    			sReturn[i][0] = resultSet.getString("titel");
    			String sStatus = resultSet.getString("Status");

    			if(sStatus.equals("abgeschlossen"))
    				sReturn[i][1] = "rot";
    			else
    				sReturn[i][1] = "gruen";	
    			
    			i++;
    		}
    		
    		resultSet = statement
    				.executeQuery("select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel " 
    						+ "from mangadb.mangareihen m natural join mangadb.Baender b " 
    						+ "where m.id = b.mangaid and b.hab_ich = 1 and m.Titel like '" + letter + "%' " 
    						+ "group by b.mangaid order by m.titel asc;");
    	}
		
		int i = 0;
		while (resultSet.next()) {
			String sTitel = resultSet.getString("titel");
			int nHab_ich = resultSet.getInt("Habe_ich");
			int nBaender = resultSet.getInt("Baender");
			String sStatus = resultSet.getString("Status");
			
			while(true){				
				if(sReturn[i][0].equals(sTitel)){
					if(nHab_ich == nBaender && sStatus.equals("abgeschlossen")){
						sReturn[i][1] = "gelb";
						i++;
						break;
					}
					else if(nHab_ich < nBaender && sStatus.equals("abgeschlossen")){
						sReturn[i][1] = "rot";
						i++;
						break;
					}
					else{
						sReturn[i][1] = "gruen";
						i++;
						break;
					}
				}
				i++;
			}
		}
		
    	return sReturn;
    }

    /**
     * 
     * @return String[][] Titel[i][0], Autor[i][1], Verlag[i][2], BanzAnz[i][3], Status[i][4], BandNr[i][5], 
     * Untertitel[i][6], Preis[i][7], Habe_ich[i][8], Erscheinung[i][9]
     * @throws SQLException
     */
    public String[][] exportAll() throws SQLException{
    	statement = connect.createStatement();

    	//select count(*) from mangadb.mangareihen;
		//gibt die Anzahl aller Mangareihen aus
		resultSet = statement
				.executeQuery("select count(*) as 'AnzMangas' from mangadb.mangareihen m natural join mangadb.Baender b where m.id = b.mangaid;");
		int nAnzManga = 0;
		if(resultSet.next()){
			nAnzManga = resultSet.getInt("AnzMangas");
		}
    	
    	//sReturn wird zurückgegeben    	
    	String[][] sReturn = new String[nAnzManga][10];
    	
		resultSet = statement
				.executeQuery("select * from mangadb.mangareihen m natural join mangadb.Baender b where m.id = b.mangaid order by m.titel asc");
		
		int i = 0; 
		while(resultSet.next()){			
			String sTitel = resultSet.getString("titel");
			String sAutor = resultSet.getString("autor");
			String sVerlag = resultSet.getString("verlag");
			String sStatus = resultSet.getString("status");
			String sUntertitel = resultSet.getString("untertitel");
			int nBandAnz = resultSet.getInt("baender");
			int  nBandNr = resultSet.getInt("bandnr");
			int  nHab_ich = resultSet.getInt("hab_ich");
			double nPreis = resultSet.getDouble("Preis");
			String  sErscheinung = resultSet.getString("erscheinungsdatum");
			
			sReturn[i][0] = sTitel;
			sReturn[i][1] = sAutor;
			sReturn[i][2] = sVerlag;
			sReturn[i][3] = "" + nBandAnz;
			sReturn[i][4] = sStatus;
			sReturn[i][5] = "" + nBandNr;
			sReturn[i][6] = sUntertitel;
			sReturn[i][7] = "" + nPreis + " Euro";
			sReturn[i][8] = "" + nHab_ich;
			sReturn[i][9] = sErscheinung;
			
			i++;
		}
    	
		return sReturn;
    }
}
