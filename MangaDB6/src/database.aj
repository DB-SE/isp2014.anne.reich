import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public aspect database {

  private static Connection connect = null;
  private static Statement statement = null;
  private static PreparedStatement preparedStatement = null;
  private static ResultSet resultSet = null;

  private static double[] selectSum() {
    /*
     * select sum(preis) as 'Gesamtpreis' from mangadb.Baender b where b.hab_ich = 'Y'; select
     * count(*) as 'Alle Mangareihen' from mangadb.mangareihen; select count(*) as 'Alle Mangas'
     * from mangadb.Baender b where b.hab_ich= 'Y';
     */

    double[] sum = new double[3];
    try {
      statement = connect.createStatement();
      // Gesamtpreis
      resultSet = statement
          .executeQuery("select sum(preis) as 'Gesamtpreis' from mangadb.Baender b where b.hab_ich = 1");
      if (resultSet.next()) {
        sum[0] = resultSet.getDouble("Gesamtpreis");
      }

      // Anzahl aller Mangareihen
      resultSet = statement
          .executeQuery("select count(*) as 'Alle Mangareihen' from mangadb.mangareihen m");
      if (resultSet.next()) {
        sum[1] = resultSet.getDouble("Alle Mangareihen");
      }

      // Anzahl aller Mangabaender
      resultSet = statement
          .executeQuery("select count(*) as 'Alle Mangas' from mangadb.Baender b where b.hab_ich = 1");
      if (resultSet.next()) {
        sum[2] = resultSet.getDouble("Alle Mangas");
      }

      return sum;
    } catch (Exception e) {
      System.out.println("Exception: " + e);
      sum[0] = 0;
      sum[1] = 0;
      sum[2] = 0;
      return sum;
    }
  }

  private void createMangareihe() {
    try {
      statement = connect.createStatement();
      String table = "create table mangadb.mangareihen(" + "ID int NOT NULL AUTO_INCREMENT, "
          + "Titel varchar(60) not null, " + "Autor varchar(60), " + "Verlag varchar(60), "
          + "Baender int, " + "Status ENUM('abgeschlossen', 'fortgesetzt'), "
          + "primary key (id), " + "UNIQUE INDEX `Titel_UNIQUE` (`Titel` ASC))";

      statement.executeUpdate(table);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void createBaender() {
    try {
      statement = connect.createStatement();
      String table = "create table mangadb.Baender(" + "BandNr int, " + "Untertitel varchar(60), "
          + "Preis numeric(5,2), " + "Hab_ich int, " + "Erscheinungsdatum varchar(45), "
          + "MangaID int, " + "foreign key (MangaID) references mangadb.mangareihen(ID)"
          + "on delete cascade on update cascade)";

      statement.executeUpdate(table);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  pointcut connection(): 
   call(public void DatabaseAccess.getConnection());

  pointcut deleteAndCreate():
   call(public void DatabaseAccess.deleteAndCreateDB());

  pointcut insertManga(String sTitel, String sAutor, String sVerlag, int nAnzBaender,
      String sStatus, int[] arrHabIch, double[] arrPreis, String[] arrUntertitel,
      String[] arrErscheinungsdatum):
    call(public void DatabaseAccess.insertManga(String, String, String, 
        int, String, int[],
        double[], String[], String[])) 
        && args(sTitel, sAutor, sVerlag, 
            nAnzBaender, sStatus, arrHabIch,
            arrPreis, arrUntertitel, arrErscheinungsdatum);

  pointcut insertBand(String sTitel, int nBandnr, String sUntertitel, double nPreis, int sHabIch,
      String sErscheinungsdatum, int nAnzBaender, String sVerlag, String sStatus):
       call(public void DatabaseAccess.insertBand(String, int, String,
               double, int, String, 
               int, String, String)) 
              && args(sTitel, nBandnr, sUntertitel, 
                      nPreis, sHabIch, sErscheinungsdatum, 
                      nAnzBaender, sVerlag, sStatus);

  pointcut editManga(String sTitel, String sAutor, String sVerlag, int nAnzBaender, String sStatus,
      int nBandnr, int nHabIch, double nPreis, String sUntertitel, String sErscheinungsdatum):
      call(public void DatabaseAccess.editManga(String, String, String,
                 int, String, int, int,
                 double, String, String)) 
              && args(sTitel, sAutor, sVerlag, 
                  nAnzBaender, sStatus, nBandnr, nHabIch, 
                      nPreis, sUntertitel, sErscheinungsdatum);

  pointcut deleteManga(String sTitel):
   call(public void DatabaseAccess.deleteManga(String)) && args(sTitel);

  pointcut deleteBand(int nBandNr, int nAnzBaender, String sTitel):
   call(public void DatabaseAccess.deleteBand(int, int, String)) && args(nBandNr, nAnzBaender, sTitel);

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

  after(): connection() {
    // This will load the MySQL driver, each DB has its own driver
    try {
      Class.forName("com.mysql.jdbc.Driver");

      // Setup the connection with the DB
      connect = DriverManager.getConnection("jdbc:mysql://localhost/mangadb?"
          + "user=root&password=a34920");

      // connect =
      // DriverManager.getConnection("jdbc:mysql://uereich.de:3306/usr_web812_2?user=web812&password=9OwUHTLr");

      // erzeugen eines Statement-Objektes
      statement = connect.createStatement();

      resultSet = statement.executeQuery("select mangareihen.Titel from mangadb.mangareihen");

      // speichert diese Informationen einmal als Titel +Bandnr in [i][0] und das Datum in [i][1]
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1));
      }

      System.out.println("Connection erfolgreich");
      //
      //
      // // ausgeben der existierenden Datenbanken
      // DatabaseMetaData meta = connect.getMetaData();
      // ResultSet res = meta.getTables(null, null, null,
      // new String[] { "TABLE" });
      // int[] exist = new int[2];
      // int i = 0;
      //
      // // wenn welche existieren, so werden sie mit 1 gekennzeichnet
      // while (res.next()) {
      // if (res.getString("TABLE_NAME").equals("baender")
      // || res.getString("TABLE_NAME").equals("mangareihen"))
      // exist[i] = 1;
      // else
      // exist[i] = 0;
      //
      // i++;
      // }
      //
      // // wenn keine Datenbank existieren sollte, werden diese neu erstellt
      // if (exist[0] == 0)
      // createMangareihe();
      // if (exist[1] == 0)
      // createBaender();
    } catch (ClassNotFoundException e) {
      System.out.println("ClassNotFoundException: " + e);
    } catch (SQLException e) {
      System.out.println("SQLException: " + e);
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }

  void around(): deleteAndCreate() {
    proceed();

    try {
      // erzeugen eines Statement-Objektes
      statement = connect.createStatement();

      // löschen bzw. leeren der Datenbanken
      statement.executeUpdate("drop table mangadb.Baender");
      statement.executeUpdate("drop table mangadb.mangareihen");

      createMangareihe();
      createBaender();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    proceed();
  }

  after(String sTitel, String sAutor, String sVerlag, int nAnzBaender, String sStatus,
      int[] arrHabIch, double[] arrPreis, String[] arrUntertitel, String[] arrErscheinungsdatum): 
           insertManga(sTitel, sAutor, sVerlag,
               nAnzBaender, sStatus, arrHabIch,
               arrPreis, arrUntertitel, arrErscheinungsdatum) {

    // erzeugen eines PreparedStatement
    // Füge Eintrag in mangadb.mangareihen ein
    try {
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
      resultSet = statement.executeQuery("select id from mangadb.mangareihen where titel = '"
          + sTitel + "'");
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
        preparedStatement.setInt(4, arrHabIch[i]);
        preparedStatement.setString(5, arrErscheinungsdatum[i]);
        preparedStatement.setInt(6, bandID);
        preparedStatement.executeUpdate();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  after(String sTitel, int nBandnr, String sUntertitel, double nPreis, int sHabIch,
      String sErscheinungsdatum, int nAnzBaender, String sVerlag, String sStatus):
           insertBand(sTitel, nBandnr, sUntertitel,
               nPreis, sHabIch, sErscheinungsdatum, 
               nAnzBaender, sVerlag,sStatus) {

    /*
     * insert into baender values (21, "-", 6.50, "N", "-", (select id from mangareihen where titel
     * = "Angel Sanctuary"));
     */

    // gibt die ID von mangadb.mangareihen, damit diese dann als fk für
    // mangadb.Baender genutzt werden kann
    try {
      statement = connect.createStatement();
      resultSet = statement.executeQuery("select id from mangadb.mangareihen where titel = '"
          + sTitel + "'");
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
      preparedStatement.setInt(4, sHabIch);
      preparedStatement.setString(5, sErscheinungsdatum);
      preparedStatement.setInt(6, bandID);
      preparedStatement.executeUpdate();

      // Wenn ein neuer Band hinzukommt, kann eventuell sich der Status, Verlag oder die Anzahl
      // aller
      // Baendern aendern
      // daher muss dies zusaützlich aktualisiert werden
      preparedStatement = connect.prepareStatement("update mangadb.mangareihen m"
          + " set m.verlag = '" + sVerlag + "'," + " m.baender = " + nAnzBaender + ","
          + " m.status = '" + sStatus + "'" + " where m.id = " + bandID);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  after(String sTitel, String sAutor, String sVerlag, int nAnzBaender, String sStatus, int nBandnr,
      int nHabIch, double nPreis, String sUntertitel, String sErscheinungsdatum):
           editManga(sTitel, sAutor, sVerlag,
                   nAnzBaender, sStatus, nBandnr, nHabIch,
                   nPreis, sUntertitel, sErscheinungsdatum) {

    /*
     * update mangareihen m, baender b set m.titel = "Angel Sanctuary", m.autor = "Kaori Yuki",
     * m.verlag = "Carlsen Comics", m.baender = 20, m.status = 'abgeschlossen', b.bandnr = 10,
     * b.untertitel = null, b.preis = 6.00, b.hab_ich = "y", b.neuerscheinung = null where m.id =
     * b.mangaid and m.titel = "Angel Sanctuary" and b.bandnr = 20;
     */

    try {
      preparedStatement = connect
          .prepareStatement("update mangadb.mangareihen m, mangadb.Baender b" + " set m.titel = '"
              + sTitel + "'," + " m.autor = '" + sAutor + "'," + " m.verlag = '" + sVerlag + "',"
              + " m.baender = " + nAnzBaender + "," + " m.status = '" + sStatus + "',"
              + " b.bandnr = " + nBandnr + "," + " b.untertitel = '" + sUntertitel + "',"
              + " b.preis = " + nPreis + "," + " b.hab_ich = " + nHabIch + ","
              + " b.erscheinungsdatum = '" + sErscheinungsdatum + "'"
              + "where m.id = b.mangaid and m.titel = '" + sTitel + "' and b.bandnr = " + nBandnr);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  after(String sTitel): deleteManga(sTitel) {
    try {
      preparedStatement = connect
          .prepareStatement("delete from mangadb.mangareihen where titel = '" + sTitel + "'");
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  after(int nBandNr, int nAnzBaender, String sTitel): deleteBand(nBandNr, nAnzBaender, sTitel) {
    /*
     * delete from mangadb.Baender where bandnr = 3 and (select id from mangadb.mangareihen where
     * titel like "%hack%")
     */
    // Wählt die Bandnr aus und die ID, wo die MangaID mit der ID zusammenpasst, mit dem richtigen
    // Titel
    try {
      preparedStatement = connect.prepareStatement("delete from mangadb.Baender where bandnr = "
          + nBandNr + " and " + "(select id from mangadb.mangareihen where titel = '" + sTitel
          + "') = mangaid;");
      preparedStatement.executeUpdate();

      /*
       * SELECT * from mangadb.mangareihen m natural join mangadb.Baender b where m.id = b.mangaid
       */

      preparedStatement = connect.prepareStatement("update mangadb.mangareihen m"
          + " set m.baender = " + nAnzBaender + " where m.titel = '" + sTitel + "';");
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  String[][] around(): getStart() {
    // holt die Sum-Werte aus der Datenbank
    double[] sum = selectSum();

    // sReturn wird zurückgegeben
    String[][] sReturn = new String[(int) sum[1] + 3][3];
    // Gibt den Preis ordentlich in Dezimalzahl aus
    DecimalFormat df = new DecimalFormat("#,##0.00");
    // speichert die Sum werte in sReturn
    sReturn[0][0] = "" + df.format(sum[0]) + " Euro";
    sReturn[1][0] = "" + (int) sum[1];
    sReturn[2][0] = "" + (int) sum[2];

    // select m.Titel, b.Erscheinungsdatum, b.BandNr
    // from mangadb.mangareihen m natural join mangadb.Baender b
    // where b.hab_ich = 'N' and m.ID = b.MangaID group by m.Titel
    try {
      statement = connect.createStatement();
      // besorgt Titel, Bandnr, Datum gruppiert nach Titel und wo hab_ich=N und m.id = b.mangaid ist
      resultSet = statement.executeQuery("select m.Titel, b.Erscheinungsdatum, b.BandNr, b.Preis"
          + " from mangadb.mangareihen m natural join mangadb.Baender b"
          + " where b.hab_ich = 'N' and m.ID = b.MangaID" + " group by m.Titel");
      int i = 0;
      // speichert diese Informationen einmal als Titel +Bandnr in [i][0] und das Datum in [i][1]
      while (resultSet.next()) {
        String sTitel = resultSet.getString("titel");
        String sErscheinungsdatum = resultSet.getString("erscheinungsdatum");
        int nBandnr = resultSet.getInt("Bandnr");
        double nPreis = resultSet.getDouble("preis");

        sReturn[3 + i][0] = sTitel + " " + nBandnr;
        sReturn[3 + i][1] = sErscheinungsdatum;
        sReturn[3 + i][2] = "" + df.format(nPreis);
        i++;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return sReturn;
  }

  String[] around(String titelManga): getBiboDetails(titelManga) {
    // sReturn wird zurückgegeben
    String[] sReturn = new String[8];

    try {
      statement = connect.createStatement();

      // select * from mangadb.mangareihen m where m.titel = 'Aishiteruze Baby';
      // besorgt alle Informationen aus Mangareihen zu einer bestimmten Manga
      resultSet = statement.executeQuery("select * from mangadb.mangareihen m where m.Titel = '"
          + titelManga + "';");

      // speichert die allg. Information zur Manga
      // Titel[0], Autor[1], Verlag[2], Anzahl der Baender[4], Status[5], int ID = id;
      int ID = 0;
      while (resultSet.next()) {
        String sTitel = resultSet.getString("titel");
        String sAutor = resultSet.getString("autor");
        String sVerlag = resultSet.getString("verlag");
        int nAnzBaender = resultSet.getInt("Baender");
        String sStatus = resultSet.getString("status");
        ID = resultSet.getInt("id");

        sReturn[0] = sTitel;
        sReturn[1] = sAutor;
        sReturn[2] = sVerlag;
        sReturn[4] = "" + nAnzBaender;
        sReturn[5] = sStatus;
      }

      // select count(*) as 'hab_ich_summe' from mangadb.Baender b where b.mangaid = 69 and
      // b.hab_ich
      // = 'Y';
      // zählt alle Baender aus dieser Reihe, die ich habe
      resultSet = statement.executeQuery("select count(*) as 'hab_ich_sum' "
          + "from mangadb.Baender b " + "where b.mangaid = " + ID + " and b.hab_ich = 1;");

      // speichert die Summe
      // hab_ich[3]
      while (resultSet.next()) {
        int nHabIch_Sum = resultSet.getInt("hab_ich_sum");

        sReturn[3] = "" + nHabIch_Sum;
      }

      // select sum(preis) as 'GesamtpreisManga' from mangadb.Baender b where b.mangaid = 69 and
      // b.hab_ich = 'Y';
      // zählt Gesamtpreis der Reihe, die ich habe
      resultSet = statement.executeQuery("select sum(preis) as 'GesamtpreisManga' "
          + "from mangadb.Baender b " + "where b.mangaid = " + ID + " and b.hab_ich = 1;");

      // speichert die Summe
      // Gesamtpreis[6]
      while (resultSet.next()) {
        double nGesamtpreis = resultSet.getDouble("GesamtpreisManga");

        // Gibt den Preis ordentlich in Dezimalzahl aus
        DecimalFormat df = new DecimalFormat("#,##0.00");
        sReturn[6] = "" + df.format(nGesamtpreis) + " Euro";
      }

      // select b.Erscheinungsdatum, b.BandNr from mangadb.Baender b where b.hab_ich = 'N' and
      // b.mangaID = 69;
      // gibt Erscheinungsdatum vom ersten Band aus, den ich noch nicht habe.
      resultSet = statement.executeQuery("select b.Erscheinungsdatum, b.BandNr "
          + "from mangadb.Baender b " + "where b.mangaID = " + ID + " and b.hab_ich = 0;");

      // speichert die Werte
      // Erscheinungsdatum meiner fehlender Manga[7]
      if (resultSet.next()) {
        String sErscheinungsdatum = resultSet.getString("Erscheinungsdatum");
        int nBandNr = resultSet.getInt("Bandnr");

        // Gibt das Erscheinunsdatum meiner fehlender Mangas aus
        sReturn[7] = "Band " + nBandNr + " - " + sErscheinungsdatum;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return sReturn;
  }

  String[][] around(String titelManga): getEditDetails(titelManga) {
    String[][] sReturn = null;
    try {
      statement = connect.createStatement();

      // select Baender from mangadb.mangareihen m where m.titel = 'Aishiteruze Baby';
      // gibt die Anzahl aller Mangas zu der Reihe aus
      resultSet = statement
          .executeQuery("select Baender from mangadb.mangareihen m where m.Titel = '" + titelManga
              + "';");
      int nAnzBaender = 0;
      if (resultSet.next()) {
        nAnzBaender = resultSet.getInt("Baender");
      }

      // sReturn wird zurückgegeben
      sReturn = new String[nAnzBaender][8];

      // select * from mangadb.mangareihen m natural join mangadb.Baender b
      // where m.titel = "Angel Sanctuary" and m.id = b.mangaid;
      // besorgt alle Informationen von einem Manga
      resultSet = statement
          .executeQuery("SELECT * FROM mangadb.mangareihen m natural join mangadb.Baender b "
              + "where m.titel = '" + titelManga + "' and m.id = b.mangaid;");

      // Autor[i][0], Verlag[i][1], Status[i][2], Untertitel[i][3], BandNr[i][4], Hab_ich[i][5],
      // Preis[i][6], Erscheinung[i][7], i = Anzahl Baender
      int i = 0;
      while (resultSet.next()) {

        String sAutor = resultSet.getString("autor");
        String sVerlag = resultSet.getString("verlag");
        String sStatus = resultSet.getString("status");
        String sUntertitel = resultSet.getString("untertitel");
        int nBandNr = resultSet.getInt("bandnr");
        int nHab_ich = resultSet.getInt("hab_ich");
        double nPreis = resultSet.getDouble("Preis");
        String sErscheinung = resultSet.getString("erscheinungsdatum");

        sReturn[i][0] = sAutor;
        sReturn[i][1] = sVerlag;
        sReturn[i][2] = sStatus;
        sReturn[i][3] = sUntertitel;
        sReturn[i][4] = "" + nBandNr;
        sReturn[i][5] = "" + nHab_ich;
        // Gibt den Preis ordentlich in Dezimalzahl aus
        DecimalFormat df = new DecimalFormat("#,##0.00");
        sReturn[i][6] = "" + df.format(nPreis) + " Euro";
        sReturn[i][7] = sErscheinung;

        i++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return sReturn;
  }

  String[][] around(): getMangareiheTitel() {
    String[][] sReturn = null;
    try {
      statement = connect.createStatement();

      // select count(*) from mangadb.mangareihen;
      // gibt die Anzahl aller Mangareihen aus
      resultSet = statement.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
      int nAnzManga = 0;
      if (resultSet.next()) {
        nAnzManga = resultSet.getInt("AnzManga");
      }

      // sReturn wird zurückgegeben
      sReturn = new String[nAnzManga][2];

      // select m.Titel from mangadb.Mangareihen m order by m.Titel asc;
      // gibt alle Titel wieder
      resultSet = statement
          .executeQuery("select m.Titel, m.Status from mangadb.mangareihen m order by m.titel asc;");
      int i = 0;
      while (resultSet.next()) {
        sReturn[i][0] = resultSet.getString("titel");
        String sStatus = resultSet.getString("Status");

        if (sStatus.equals("abgeschlossen"))
          sReturn[i][1] = "rot";
        else
          sReturn[i][1] = "gruen";

        i++;
      }

      // select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from
      // mangadb.mangareihen
      // m natural join mangadb.Baender b
      // where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
      // zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den
      // Status der Reihe und den Titel wieder
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

        while (true) {
          if (sReturn[i][0].equals(sTitel)) {
            if (nHab_ich == nBaender && sStatus.equals("abgeschlossen")) {
              sReturn[i][1] = "gelb";
              i++;
              break;
            } else if (nHab_ich < nBaender && sStatus.equals("abgeschlossen")) {
              sReturn[i][1] = "rot";
              i++;
              break;
            } else {
              sReturn[i][1] = "gruen";
              i++;
              break;
            }
          }
          i++;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return sReturn;
  }

  String[] around(): getVerlag() {
    String[] sReturn = null;

    try {
      statement = connect.createStatement();

      // select count(*) from mangadb.mangareihen;
      // gibt die Anzahl aller Mangareihen aus
      resultSet = statement.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
      int nAnzManga = 0;
      if (resultSet.next()) {
        nAnzManga = resultSet.getInt("AnzManga");
      }

      // sReturn wird zurückgegeben
      sReturn = new String[nAnzManga];

      // select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from
      // mangadb.mangareihen
      // m natural join mangadb.Baender b
      // where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
      // zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den
      // Status der Reihe und den Titel wieder
      resultSet = statement.executeQuery("select m.verlag from mangadb.mangareihen m");

      int i = 0;
      while (resultSet.next()) {
        String sVerlag = resultSet.getString("verlag");

        sReturn[i] = sVerlag;
        i++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return sReturn;
  }

  String[] around(): getAutor() {

    String[] sReturn = null;
    try {
      statement = connect.createStatement();

      // select count(*) from mangadb.mangareihen;
      // gibt die Anzahl aller Mangareihen aus
      resultSet = statement.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
      int nAnzManga = 0;
      if (resultSet.next()) {
        nAnzManga = resultSet.getInt("AnzManga");
      }

      // sReturn wird zurückgegeben
      sReturn = new String[nAnzManga];

      // select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from
      // mangadb.mangareihen
      // m natural join mangadb.Baender b
      // where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
      // zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den
      // Status der Reihe und den Titel wieder
      resultSet = statement.executeQuery("select m.autor from mangadb.mangareihen m");

      int i = 0;
      while (resultSet.next()) {
        String sAutor = resultSet.getString("autor");

        sReturn[i] = sAutor;

        i++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return sReturn;
  }

  String[] around(String autor): getTitelByAutor(autor) {
    String[] sReturn = null;
    try {
      statement = connect.createStatement();

      // select count(*) from mangadb.mangareihen;
      // gibt die Anzahl aller Mangareihen aus
      resultSet = statement.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
      int nAnzManga = 0;
      if (resultSet.next()) {
        nAnzManga = resultSet.getInt("AnzManga");
      }

      // sReturn wird zurückgegeben
      sReturn = new String[nAnzManga];

      // select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from
      // mangadb.mangareihen
      // m natural join mangadb.Baender b
      // where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
      // zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den
      // Status der Reihe und den Titel wieder
      resultSet = statement
          .executeQuery("select m.titel from mangadb.mangareihen m where m.autor = '" + autor
              + "';");

      int i = 0;
      while (resultSet.next()) {
        String sTitel = resultSet.getString("titel");
        sReturn[i] = sTitel;

        i++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return sReturn;
  }

  String[] around(String verlag): getTitelByVerlag(verlag) {
    String[] sReturn = null;
    try {
      statement = connect.createStatement();

      // select count(*) from mangadb.mangareihen;
      // gibt die Anzahl aller Mangareihen aus
      resultSet = statement.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
      int nAnzManga = 0;
      if (resultSet.next()) {
        nAnzManga = resultSet.getInt("AnzManga");
      }

      // sReturn wird zurückgegeben
      sReturn = new String[nAnzManga];

      // select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from
      // mangadb.mangareihen
      // m natural join mangadb.Baender b
      // where m.id = b.mangaid and b.hab_ich = 'Y' group by b.mangaid order by m.titel asc;
      // zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt, gibt den
      // Status der Reihe und den Titel wieder
      resultSet = statement
          .executeQuery("select m.titel from mangadb.mangareihen m where m.verlag = '" + verlag
              + "';");

      int i = 0;
      while (resultSet.next()) {
        String sTitel = resultSet.getString("titel");
        sReturn[i] = sTitel;

        i++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return sReturn;
  }

  String[][] around(String letter): getReiheStartsWith(letter) {
    String[][] sReturn = null;

    try {
      statement = connect.createStatement();

      // select count(*) from mangadb.mangareihen;
      // gibt die Anzahl aller Mangareihen aus
      resultSet = statement.executeQuery("select count(*) as 'AnzManga' from mangadb.mangareihen;");
      int nAnzManga = 0;
      if (resultSet.next()) {
        nAnzManga = resultSet.getInt("AnzManga");
      }

      // sReturn wird zurückgegeben
      sReturn = new String[nAnzManga][2];

      // select count(b.hab_ich) as 'Habe_ich', m.Baender, m.Status, m.Titel from
      // mangadb.mangareihen
      // m natural join mangadb.Baender b
      // where m.id = b.mangaid and b.hab_ich = 'Y' and m.Titel like 'A%' group by b.mangaidorder by
      // m.titel asc;
      // zählt alle "hab_ich" zusammen, alle baender die es insgesamt von der reihe gibt,
      // gibt den Status der Reihe und den Titel die mit A anfangen wieder, sortiert nach Name
      if (letter.equals("^[A-Z]")) {
        resultSet = statement
            .executeQuery("select m.Titel, m.Status from mangadb.mangareihen m where m.Titel not rlike '^[A-Z]' order by m.titel asc;");

        int i = 0;
        while (resultSet.next()) {
          sReturn[i][0] = resultSet.getString("titel");
          String sStatus = resultSet.getString("Status");

          if (sStatus.equals("abgeschlossen"))
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

      } else {
        resultSet = statement
            .executeQuery("select m.Titel, m.Status from mangadb.mangareihen m where m.Titel like '"
                + letter + "%' order by m.titel asc;");

        int i = 0;
        while (resultSet.next()) {
          sReturn[i][0] = resultSet.getString("titel");
          String sStatus = resultSet.getString("Status");

          if (sStatus.equals("abgeschlossen"))
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

        while (true) {
          if (sReturn[i][0].equals(sTitel)) {
            if (nHab_ich == nBaender && sStatus.equals("abgeschlossen")) {
              sReturn[i][1] = "gelb";
              i++;
              break;
            } else if (nHab_ich < nBaender && sStatus.equals("abgeschlossen")) {
              sReturn[i][1] = "rot";
              i++;
              break;
            } else {
              sReturn[i][1] = "gruen";
              i++;
              break;
            }
          }
          i++;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return sReturn;
  }

  String[][] around(): exportAll() {
    String[][] sReturn = null;
    try {
      statement = connect.createStatement();

      // select count(*) from mangadb.mangareihen;
      // gibt die Anzahl aller Mangareihen aus
      resultSet = statement
          .executeQuery("select count(*) as 'AnzMangas' from mangadb.mangareihen m natural join mangadb.Baender b where m.id = b.mangaid;");
      int nAnzManga = 0;
      if (resultSet.next()) {
        nAnzManga = resultSet.getInt("AnzMangas");
      }

      // sReturn wird zurückgegeben
      sReturn = new String[nAnzManga][10];

      resultSet = statement
          .executeQuery("select * from mangadb.mangareihen m natural join mangadb.Baender b where m.id = b.mangaid order by m.titel asc");

      int i = 0;
      while (resultSet.next()) {
        String sTitel = resultSet.getString("titel");
        String sAutor = resultSet.getString("autor");
        String sVerlag = resultSet.getString("verlag");
        String sStatus = resultSet.getString("status");
        String sUntertitel = resultSet.getString("untertitel");
        int nBandAnz = resultSet.getInt("baender");
        int nBandNr = resultSet.getInt("bandnr");
        int nHab_ich = resultSet.getInt("hab_ich");
        double nPreis = resultSet.getDouble("Preis");
        String sErscheinung = resultSet.getString("erscheinungsdatum");

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
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return sReturn;
  }

  String around(): getDAType() {

    return "db";
  }
}
