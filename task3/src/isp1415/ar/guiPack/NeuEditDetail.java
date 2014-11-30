package isp1415.ar.guiPack;

import isp1415.ar.dbPack.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;



public class NeuEditDetail {
	
	private DB db;
	private XML xml;
	private JFrame fenster;
	private JTextField tTitel, tZeichner, tAnzahl;
	private JLabel ltTitel, ltZeichner, ltVerlag, ltStatus;
	private JComboBox<String> tStatus, tVerlag;
	private String sWahl, sTitel;
	private JTable table;
	private DefaultTableModel tabModel;
	private String[][] mangaInfo;
	private String[] arrPreis= {"1,95 Euro", "5,00 Euro", "5,95 Euro", "6,00 Euro", "6,50 Euro", "6,95 Euro","7,50 Euro", "8,00 Euro", "14,00 Euro"},
			arrVerlag = {"Animexx e.V.", "Carlsen Comics/Carlsen Mangas", "Egmont Manga & Anime","Fireangels Verlag", "Planet Manga", "Tokyopop", "Verlag Schwarzer Turm"};
	
	public NeuEditDetail(DB dao, String action, String titel){
		db = dao;
		sWahl = action;
		sTitel = titel;
		
		fensterErzeugen();
	}
	
	public NeuEditDetail(XML xml, String action, String titel){
		this.xml = xml;
		sWahl = action;
		sTitel = titel;
		
		fensterErzeugen();
	}

	@SuppressWarnings("serial")
	private void fensterErzeugen() {	
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO START 
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		fenster = new JFrame("Anne's Manga Bibliothek");
		fenster.setSize(500, 400);
		fenster.setLocationRelativeTo(null);
		fenster.setResizable(false);		
		
		JPanel flaeche = new JPanel();
		flaeche.setLayout(new BorderLayout());
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO DATEN-FEST (oben)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel datenfest = new JPanel();
		datenfest.setBorder(new EmptyBorder(5,5,10,5));
		datenfest.setLayout(new GridLayout(3,3,2,0));
		
		JLabel lTitel = new JLabel("Titel");
		datenfest.add(lTitel);
		
		JLabel lAutor = new JLabel("Zeichner");
		datenfest.add(lAutor);
		
		JLabel lVerlag = new JLabel("Verlag");
		datenfest.add(lVerlag);
		
		if(sWahl.equals("neu")){
			tTitel = new JTextField();
			tTitel.addFocusListener(new FocusAdapter(){
				public void focusGained(FocusEvent evt){
					SwingUtilities.invokeLater(new Runnable(){
						public void run() {
							tTitel.selectAll();							
						}						
					});
				}				
			});
			datenfest.add(tTitel);
		}
		else{
			ltTitel = new JLabel();
			datenfest.add(ltTitel);
		}
		
		if(sWahl.equals("neu")){
			tZeichner = new JTextField();	
			tZeichner.addFocusListener(new FocusAdapter(){
				public void focusGained(FocusEvent evt){
					SwingUtilities.invokeLater(new Runnable(){
						public void run() {
							tZeichner.selectAll();							
						}						
					});
				}				
			});
			datenfest.add(tZeichner);
		}
		else{
			ltZeichner = new JLabel();
			datenfest.add(ltZeichner);
		}
		
		if(!sWahl.equals("Detail")){
			tVerlag = new JComboBox<String>(arrVerlag);
			tVerlag.setEditable(true);
			tVerlag.setBackground(new Color(255,255,255));
			tVerlag.setOpaque(true);
			datenfest.add(tVerlag);
		}
		else{
			ltVerlag = new JLabel();
			datenfest.add(ltVerlag);
		}
		
		//----
		
		JPanel pAnzahl = new JPanel();
		pAnzahl.setLayout(new BorderLayout());
		
		JLabel lAnzahl = new JLabel("Anzahl an Baender:  ");
		pAnzahl.add(lAnzahl, BorderLayout.WEST);
		
		if(!sWahl.equals("Detail")){
			tAnzahl = new JTextField();		
			tAnzahl.addKeyListener(new KeyListener(){
	
				public void keyPressed(KeyEvent arg0) {
					if(arg0.getKeyCode() == KeyEvent.VK_ENTER && sWahl.equals("neu")) {
						String anzValue = tAnzahl.getText();
						int nAnz = Integer.valueOf(anzValue);
						int index = tabModel.getRowCount();
						
						if(index > nAnz){
							tabModel.setRowCount(0);
							index = 0;
						}
						
						for(int i = index; i < nAnz; i++){
							Object[] tmp = {(i+1),"",true, "Euro", getDate()};
							tabModel.addRow(tmp);
						}
					}
					else if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
						String anzValue = tAnzahl.getText();
						int nAnz = Integer.valueOf(anzValue);
						
						eManga(sWahl, nAnz);
					}
				}
	
				public void keyReleased(KeyEvent arg0) {}
	
				public void keyTyped(KeyEvent arg0) {}			
			});
		}
		else{
			tAnzahl = new JTextField();
			tAnzahl.setEditable(false);
			tAnzahl.setBorder(new EmptyBorder(0,0,0,0));
		}
		pAnzahl.add(tAnzahl, BorderLayout.CENTER);
		
		datenfest.add(pAnzahl);
		
		//----
		
		flaeche.add(datenfest, BorderLayout.NORTH);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// 
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		JPanel pVari = new JPanel();
		pVari.setLayout(new BorderLayout());
		pVari.setBorder(new EmptyBorder(0,5,0,5));
		
		//----		
		
		String[] spaltenName = {"Band", "Untertitel", "Habe ich", "Preis", "Release"};		
		tabModel = new DefaultTableModel(spaltenName, 0);
		
		//Model Tabel zugeordnet
		table = new JTable(tabModel){
			//verhindert das alle Zellen veraenderbar sind
			public boolean isCellEditable(int row, int column){
					if(column == 0 || sWahl.equals("Detail"))
						return false;
					else
						return true;
			}
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//setzt die Spalten-Inhalte rechtsbündig
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//setzt die Spalten-Inhalte mittig
		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(SwingConstants.CENTER);
		
		TableColumn cBand = table.getColumn("Band");
		cBand.setPreferredWidth(40);
		cBand.setCellRenderer(centerRender);
		
		table.getColumn("Untertitel").setPreferredWidth(161);
		
		//CheckBox erstellen
		TableColumn cHab = table.getColumn("Habe ich");
		cHab.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		cHab.setCellEditor(table.getDefaultEditor(Boolean.class));
		cHab.setPreferredWidth(65);
		
		//ComboBox erstellen
		final JComboBox<String> combo = new JComboBox<String>(arrPreis);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(row >= 0){
					int auswahl = combo.getSelectedIndex();
					if(auswahl != -1){
						int allRow = table.getRowCount();
						Object value = combo.getItemAt(auswahl);
						for(int i = row; i < allRow; i++){
							table.setValueAt(value, i, 3);
						}
					}
				}				
			}			
		});
		combo.setEditable(true);
		
		//DropDown-Menü einbinden
		TableColumn cPreis = table.getColumn("Preis");		
		cPreis.setCellEditor(new DefaultCellEditor(combo));
		cPreis.setCellRenderer(rightRender);
		cPreis.setPreferredWidth(90);
		
		//JSpinner einbinden
		TableColumn cRelease = table.getColumn("Release");
		cRelease.setPreferredWidth(110);
		cRelease.setCellEditor(new DateCellEditor());
		cRelease.setCellRenderer(rightRender);
		
		//Gittefarbe weiß setzten (wie der Hintergrund)
		table.setGridColor(Color.white);
		table.setOpaque(true);
		
		JScrollPane pane = new JScrollPane(table);
		//wenn tabelle nicht voll ist, behält sie aber standard-höhe ein
		table.setFillsViewportHeight(true);
		//verhindert eine Scrollbar in Richtung horizontal
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		pVari.add(pane, BorderLayout.CENTER);
		
		//----
		
		flaeche.add(pVari, BorderLayout.CENTER);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO DATENFEST2(status) + BUTTONS (unten)
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		JPanel end = new JPanel();
		end.setLayout(new BorderLayout());
		
		//----				
		JPanel datenfest2 = new JPanel();
		datenfest2.setBorder(new EmptyBorder(10,5,10,5));
		datenfest2.setLayout(new GridLayout(2,3,2,0));
		
		JLabel lStatus = new JLabel("Status");
		datenfest2.add(lStatus);
		
		JLabel leer = new JLabel();
		datenfest2.add(leer);
		
		JLabel leer2 = new JLabel();
		datenfest2.add(leer2);
		
		if(sWahl.equals("Detail")){
			ltStatus = new JLabel();
			datenfest2.add(ltStatus);
		}
		else{
			String[] sStatus = {"abgeschlossen", "fortgesetzt"};
			tStatus = new JComboBox<String>(sStatus);
			tStatus.setBackground(new Color(255,255,255));
			tStatus.setOpaque(true);
			datenfest2.add(tStatus);
		}
		
		JLabel leer3 = new JLabel();
		datenfest2.add(leer3);
		
		JLabel leer4 = new JLabel();
		datenfest2.add(leer4);
		
		end.add(datenfest2, BorderLayout.NORTH);
		
		//----
		
		JPanel button = new JPanel();
		button.setLayout(new FlowLayout());
		
		//Hinzufügen bzw. Editieren
		if(!sWahl.equals("Detail")){
			JButton bEditNew = new JButton();
			if(sWahl.equals("neu")){
				bEditNew = new JButton("Hinzufuegen");
				bEditNew.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(tTitel.getText().equals("Bitte Ausfuellen")){
							JOptionPane.showMessageDialog(null, "Bitte fuellen Sie den Titel aus", "Bitte ausfuellen!", JOptionPane.OK_OPTION);
						}
						else if(tZeichner.getText().equals("Bitte Ausfuellen")){
							JOptionPane.showMessageDialog(null, "Bitte fuellen Sie den Autor aus", "Bitte ausfuellen!", JOptionPane.OK_OPTION);
						}
						else if(tAnzahl.getText().equals("")){
							JOptionPane.showMessageDialog(null, "Bitte geben Sie die Anzahl der Baender an", "Bitte ausfuellen!", JOptionPane.OK_OPTION);
						}
						else{
							try {
								insertManga();
								if(db != null)
									new Bibo(db);
								else
									new Bibo(xml);
								fenster.setVisible(false);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						
					}
				});
			}
			else if(sWahl.equals("Edit")){
				bEditNew = new JButton("aendern");
				bEditNew.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						try {
							editManga();
							if(db != null)
								new Bibo(db);
							else
								new Bibo(xml);
							fenster.setVisible(false);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					}
				});
			}
			
			button.add(bEditNew);
		}
		
		//Manga löschen
		if(sWahl.equals("Edit")){
			JButton bDelete = new JButton("Loeschen");
			bDelete.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					int eingabe = JOptionPane.showConfirmDialog(null, "Wollen Sie die Manga '" + sTitel + "' wirklich loeschen?", 
							"Loeschen", JOptionPane.YES_NO_OPTION);
					
					if(eingabe == 0){
						try {
							if(db != null){
								db.deleteManga(sTitel);
								new Bibo(db);
							}
							else{
								xml.deleteManga(sTitel);
								new Bibo(xml);
							}
							fenster.setVisible(false);
						} catch (SQLException e) {e.printStackTrace();}
					}
				}				
			});
			button.add(bDelete);
		}
		
		JButton bClose = new JButton("Abbruch");
		bClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!sWahl.equals("Detail")){
					try {
						if(db != null)
							new Bibo(db);
						else
							new Bibo(xml);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				fenster.setVisible(false);
			}			
		});
		button.add(bClose);
		
		end.add(button, BorderLayout.CENTER);
		
		//----
		
		flaeche.add(end, BorderLayout.SOUTH);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO ENDE 
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		if(sWahl.equals("neu")){
			nManga();
		}
		else{
			try {
				getEditInfo();
				eManga(sTitel);
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		
		fenster.add(flaeche);
		//Fenster sichtbar machen
		fenster.setVisible(true);
		
	}

	private void eManga(String mangaTitel) {		
		ltTitel.setText(mangaTitel);
		ltTitel.setToolTipText(mangaTitel);
		ltZeichner.setText(mangaInfo[0][0]);
		ltZeichner.setToolTipText(mangaInfo[0][0]);
		String sVerlag = mangaInfo[0][1];
		if(sWahl.equals("Detail")){
			ltVerlag.setText(sVerlag);
		}
		else{
			for(int i = 0; i < arrVerlag.length; i++){
				if(tVerlag.getItemAt(i).equals(sVerlag))
					tVerlag.setSelectedIndex(i);
			}
		}
		tAnzahl.setText("" + mangaInfo.length);
		if(sWahl.equals("Detail")){
			ltStatus.setText(mangaInfo[0][2]);
		}
		else{
			if(mangaInfo[0][2].equals("abgeschlossen"))
				tStatus.setSelectedIndex(0);
			else
				tStatus.setSelectedIndex(1);
		}
		int nAnz = mangaInfo.length;
		for(int i = 0; i < nAnz; i++){
			String sUntertitel = mangaInfo[i][3];
			int nBand = Integer.valueOf(mangaInfo[i][4]);
			boolean bHab = false;
			int nHabIch = Integer.valueOf(mangaInfo[i][5]);
			if(nHabIch == 1)
				bHab = true;
			String sPreis = mangaInfo[i][6];
			String sErscheinung = mangaInfo[i][7];
			
			Object[] tmp = {nBand,sUntertitel,bHab, sPreis, sErscheinung};
			tabModel.addRow(tmp);
		}		
	}
	
	private void eManga(String mangaTitel, int nAnz) {
		if(nAnz <= mangaInfo.length){
			tabModel.setRowCount(0);
			
			ltTitel.setText(mangaTitel);
			ltZeichner.setText(mangaInfo[0][0]);
			String sVerlag = mangaInfo[0][1];
			for(int i = 0; i < arrVerlag.length; i++){
				if(tVerlag.getItemAt(i).equals(sVerlag))
					tVerlag.setSelectedIndex(i);
			}
			tAnzahl.setText("" + nAnz);
			if(mangaInfo[0][2].equals("abgeschlossen"))
				tStatus.setSelectedIndex(0);
			else
				tStatus.setSelectedIndex(1);
			
			for(int i = 0; i < nAnz; i++){
				String sUntertitel = mangaInfo[i][3];
				int nBand = Integer.valueOf(mangaInfo[i][4]);
				boolean bHab = false;
				int nHabIch = Integer.valueOf(mangaInfo[i][5]);
				if(nHabIch == 1)
					bHab = true;
				String sPreis = mangaInfo[i][6];
				String sErscheinung = mangaInfo[i][7];
				
				Object[] tmp = {nBand,sUntertitel,bHab, sPreis, sErscheinung};
				tabModel.addRow(tmp);
			}		
		}
		else{
			int index = tabModel.getRowCount();
			
			for(int i = index; i < nAnz; i++){
				Object[] tmp = {(i+1),"",false, "Euro", getDate()};
				tabModel.addRow(tmp);
			}
		}
	}

	private void nManga() {
		String ausfuellen = "Bitte Ausfuellen";
		tTitel.setText(ausfuellen);
		tTitel.selectAll();
		tZeichner.setText(ausfuellen);
	}
	
	private void insertManga() throws SQLException{
		String[][] read = readDetails();
		
		int[] arrHab = new int[read.length];
		double[] arrPreis = new double[read.length];
		String[] arrsPreis = new String[read.length];
		String[] arrUntertitel = new String[read.length];
		String[] arrErscheinung = new String[read.length];
		
		for(int i = 0; i < read.length; i++){
			arrHab[i] = Integer.valueOf(read[i][4]);
			
			if(!(read[i][5].equals("Euro"))){
				arrsPreis[i] = ((String) tabModel.getValueAt(1, 3));
				String[] split = ((String) tabModel.getValueAt(i, 3)).split(" ");
				String[] split2 = split[0].split(",");
				String dPreis = split2[0] + "." + split2[1];
				arrPreis[i] = Double.valueOf(dPreis);
			}
			
			arrUntertitel[i] = read[i][6];
			arrErscheinung[i] = read[i][7];
		}
		
		if(db != null)
			db.insertManga(read[0][0], read[0][1], read[0][2], read.length, read[0][3], arrHab, arrPreis, arrUntertitel, arrErscheinung);
		else
			xml.insertManga(read[0][0], read[0][1], read[0][2], read.length, read[0][3], arrHab, arrsPreis, arrUntertitel, arrErscheinung);
	}
	
	private void editManga() throws SQLException{
		String[][] read = readDetails();
		
		if(read.length < mangaInfo.length){
			for(int i = read.length+1; i <= mangaInfo.length; i++){
				if(db != null)
					db.deleteBand(i, read.length, sWahl);
				else
					xml.deleteBand(i, sWahl);
			}
		}
		else if(read.length > mangaInfo.length){
			double dPreis = 0.0;
			for(int i = mangaInfo.length; i < read.length; i++){
				
				if(!(read[i][5].equals("Euro"))){
					String[] split = ((String) tabModel.getValueAt(i, 3)).split(" ");
					String[] split2 = split[0].split(",");
					String sPreis = split2[0] + "." + split2[1];
					dPreis = Double.valueOf(sPreis);
				}
				
				if(db != null)
					db.insertBand(sTitel, (i+1), read[i][6], dPreis, Integer.valueOf(read[i][4]), read[i][7], read.length, read[0][2], read[0][3]);
				else
					xml.insertBand(sTitel, (i+1), read[i][6], dPreis, Integer.valueOf(read[i][4]), read[i][7], read[0][2]);
			}
		}
		else{
			double dPreis = 0.0;
			for(int i = 0; i < read.length; i++){
				if(!(read[i][5].equals("Euro"))){
					String[] split = ((String) tabModel.getValueAt(i, 3)).split(" ");
					String[] split2 = split[0].split(",");
					String sPreis = split2[0] + "." + split2[1];
					dPreis = Double.valueOf(sPreis);
				}
				
				if(db != null)
					db.editManga(sTitel, read[0][1], read[0][2], read.length, read[0][3], (i+1), Integer.valueOf(read[i][4]), dPreis, read[i][6], read[i][7]);
				else
					xml.editManga(sTitel, read[0][1], read[0][2], read.length, read[0][3], (i+1), Integer.valueOf(read[i][4]), dPreis, read[i][6], read[i][7]);
			}
		}
	}
	
	/**
	 * 
	 * @return String[][] mit Titel[0][0], Autor[0][1], Verlag[0][2], Status[0][3], HabeIch[i][4], Preis[i][5], Untertitel[i][6], Erscheinungsdatum[i][7], [i] = Anzahl an Baender und i = BandNr
	 */
	private String[][] readDetails(){
		String sTitel = "";
		String sAutor = "";
		
		if(sWahl.equals("neu")){
			sTitel = tTitel.getText();
			sAutor = tZeichner.getText();
		}
		else{
			sTitel = ltTitel.getText();
			sAutor = ltZeichner.getText();
		}
		
		String sVerlag = tVerlag.getSelectedItem().toString();
		int nAnz = Integer.valueOf(tAnzahl.getText());
		String sStatus = tStatus.getSelectedItem().toString();
		
		String[][] read = new String[nAnz][8];
		read[0][0] = sTitel;
		read[0][1] = sAutor;
		read[0][2] = sVerlag;
		read[0][3] = sStatus;		
		
		for(int i = 0; i < nAnz; i++){
			read[i][6] = (String) tabModel.getValueAt(i, 1);
			
			if((Boolean) tabModel.getValueAt(i, 2)){
				read[i][4] = "" + 1;
			}
			else{
				read[i][4] = "" + 0;
			}
		
			read[i][5] = (String) tabModel.getValueAt(i, 3);			
			read[i][7] = (String) tabModel.getValueAt(i, 4)+" ";
		}
		
		return read;
	}
	
	private String getDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		DateFormat df = new SimpleDateFormat("MMMM yyyy");
		String formattedDate = df.format(calendar.getTime());
		
		return formattedDate;
	}
	
	private void getEditInfo() throws SQLException {
		if(db != null)
			mangaInfo = db.getEditDetails(sTitel);
		else
			mangaInfo = xml.getEditDetails(sTitel);
	}
	
	@SuppressWarnings("serial")
	public class DateCellEditor extends AbstractCellEditor implements
	TableCellEditor {

		Date currentDate;
		JSpinner spinner;

		protected static final String EDIT = "edit";

		public DateCellEditor() {
			Calendar calendar = Calendar.getInstance();
			Date initDate = calendar.getTime();
			calendar.set(Calendar.YEAR, 1980);
			Date earliestDate = calendar.getTime();
			calendar.add(Calendar.YEAR, 500);
			Date latestDate = calendar.getTime();
			SpinnerModel dateModel = new SpinnerDateModel(initDate,
					earliestDate,
					latestDate,
					Calendar.SECOND);//ignored for user input
			spinner = new JSpinner(dateModel);
			
			//Passt alle anderen Datum-Angabe dem vorherigen an
			spinner.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent arg0) {
					int row = table.getSelectedRow();
					if(row >= 0){
						int allRow = table.getRowCount();
						Date date = (Date) spinner.getValue(); 
						DateFormat df = new SimpleDateFormat("MMMM yyyy");
						Object value = df.format(date);
						for(int i = row; i < allRow; i++){
							table.setValueAt(value, i, 4);
						}						
					}
					
				}
			});
			spinner.setEditor(new JSpinner.DateEditor(spinner, "MMMM yyyy"));
		}

		// Implement the one CellEditor method that AbstractCellEditor doesn't.
		public Object getCellEditorValue() {
			currentDate = ((SpinnerDateModel)spinner.getModel()).getDate();
			Date date = ((SpinnerDateModel)spinner.getModel()).getDate();
			DateFormat df = new SimpleDateFormat("MMMM yyyy");
			String formattedDate = df.format(date);
			
			return formattedDate;
		}

		// Implement the one method defined by TableCellEditor.
		public Component getTableCellEditorComponent(JTable table, Object value,
				boolean isSelected, int row, int column) {
			String[] splitDate = ((String) value).split(" ");
			int month;
			if(splitDate[0].equals("Januar"))
				month = 0;
			else if(splitDate[0].equals("Februar"))
				month = 1;
			else if(splitDate[0].equals("Maerz"))
				month = 2;
			else if(splitDate[0].equals("April"))
				month = 3;
			else if(splitDate[0].equals("Mai"))
				month = 4;
			else if(splitDate[0].equals("Juni"))
				month = 5;
			else if(splitDate[0].equals("Juli"))
				month = 6;
			else if(splitDate[0].equals("August"))
				month = 7;
			else if(splitDate[0].equals("September"))
				month = 8;
			else if(splitDate[0].equals("Oktober"))
				month = 9;
			else if(splitDate[0].equals("November"))
				month = 10;
			else if(splitDate[0].equals("Dezember"))
				month = 11;
			else
				month = 2; //weil er Maerz wegen den Umlauten nicht lesen kann
			
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.valueOf(splitDate[1]), month, 1);
			currentDate = cal.getTime();
			spinner.setValue(cal.getTime());
			return spinner;
		}
	}
}
