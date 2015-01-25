import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public aspect nextMangaTable {
  
  private String[][] rowColor;
  private JTable table;  
  private String[][] startInfo;
  private TableModel tabModel;
  
  pointcut setTable(JPanel flaeche):
    execution(private JPanel Start.nextMangaTable(JPanel)) 
    && args(flaeche);
  
  pointcut isTableOn():
    execution(private boolean Start.isFeatureTableOn());  
  
  pointcut getStartInfo():
    call(public String[][] getStartInfo());
  
  after(): getStartInfo() {    
    try {
      Object start = thisJoinPoint.getThis();
      Field field = thisJoinPoint.getThis().getClass().getField("_startInfo");
      startInfo = (String[][]) field.get(start);
    } catch (Exception e) {
      e.printStackTrace();
    }    
  }
  
  
  @SuppressWarnings("serial")
  JPanel around(JPanel flaeche): setTable(flaeche) {
    
    JPanel pCENTER = new JPanel();
    
    pCENTER.setLayout(new BorderLayout());
    pCENTER.setBorder(new EmptyBorder(10, 10, 10, 0));
    
    JLabel newsTitel = new JLabel("Erscheinungsdatum meiner Mangas");
    pCENTER.add(newsTitel, BorderLayout.NORTH);

    String[] spaltenName = {"Titel", "Release", "Preis"};
    
    tabModel = new DefaultTableModel(spaltenName, 0) {
      public Class<?> getColumnClass(int col) {
        switch (col) {
              case  0: return String.class;
              case  1: return Date.class;
              case  2: return String.class;
              default: return Object.class;
        }
          }
    };
    
    this.table = new JTable(tabModel) { 
      //verhindert das alle Zellen veraenderbar sind
      public boolean isCellEditable(int row, int column) {
          return false;
      }
      
      //gibt den Titel als Tooltip wieder
       public String getToolTipText(MouseEvent e) {
         String tip = null;
               java.awt.Point p = e.getPoint();
               int rowIndex = rowAtPoint(p);

               if (getValueAt(rowIndex, 0) != null) {
                 tip = "" + getValueAt(rowIndex, 0);
               }
               
        return tip;
         
       }
       
       //markiert Mangas die im aktuellen Monat herauskommen
      public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
          Component c = super.prepareRenderer(renderer, row, column);
          
          if (!isRowSelected(table.convertRowIndexToModel(row))) {
            if (rowColor[table.convertRowIndexToModel(row)][0] != null) {
              c.setBackground(Color.GREEN);
            } else {
              c.setBackground(Color.WHITE);
            }
          }

          return c;
      }
    };
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    //setzt die Spalten-Inhalte rechtsbündig
    DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
    rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
    
    TableColumn cTitel = table.getColumn("Titel");
    cTitel.setPreferredWidth(180);
    
    TableColumn cRelease = table.getColumn("Release");
    cRelease.setPreferredWidth(105);
    cRelease.setCellRenderer(rightRender);
    
    TableColumn cPreis = table.getColumn("Preis");
    cPreis.setPreferredWidth(70);
    cPreis.setCellRenderer(rightRender);

    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setResizingAllowed(false);
  
    erscheinung();
    
    //Gittefarbe weiß setzten (wie der Hintergrund)
    table.setGridColor(Color.white);
    table.setOpaque(true);
    
    JScrollPane scroll = new JScrollPane(table);
//    scroll.setPreferredSize(new Dimension(100,150));

    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    table.setFillsViewportHeight(true);

    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabModel) {
      public void toggleSortOrder(int column) {
          if (column != 2) {
            super.toggleSortOrder(column);
          }
        }
    };
    table.setRowSorter(sorter);
    sorter.setComparator(1, new DateStringComparator());
    
    pCENTER.add(scroll, BorderLayout.CENTER);
    
    flaeche.add(pCENTER, BorderLayout.CENTER);
    
    return flaeche;
    
  }
  
  boolean around(): isTableOn() {
    return true;
  }  
  
  private void erscheinung() {      
    String datum = getDate();
    rowColor = new String[startInfo.length][1];
    
    for (int i = 3; i < startInfo.length; i++) {
      if (startInfo[i][0] != null) {
        Object[] tmp = {startInfo[i][0], startInfo[i][1] + " ", startInfo[i][2] + " Euro "};
        String date2 = startInfo[i][1];
        //schaut nach, ob die Manga im aktuellen Monat rauskommen soll
        if (date2.startsWith(datum))
          rowColor[i - 3][0] = startInfo[i][0];     

        ((DefaultTableModel) tabModel).addRow(tmp);
      }
    }
  }
  
  private String getDate() {
    Calendar cal = new GregorianCalendar();
    int month = cal.get(Calendar.MONTH)+1;
    int year = cal.get(Calendar.YEAR);
    
    switch(month){
      case 1: return "Januar " + year; 
      case 2: return "Februar " + year; 
      case 3: return "Maerz " + year; 
      case 4: return "April " + year; 
      case 5: return "Mai " + year; 
      case 6: return "Juni " + year; 
      case 7: return "Juli " + year; 
      case 8: return "August " + year; 
      case 9: return "September " + year; 
      case 10: return "Oktober " + year; 
      case 11: return "November " + year; 
      case 12: return "Dezember " + year; 
      default: return "kein Monat";
    }
    
  }
  
  /**
   *Vergleicht Datum um dieses dann zu sortieren
   */
  class DateStringComparator implements Comparator<String> {
      public int compare(String strDate1, String strDate2) {
          SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMMM yyyy");
          SimpleDateFormat dateFormat2 = new SimpleDateFormat("MMMM yyyy");
          
          try {
              if (strDate1 == null || strDate2 == null) {
                  return 0;
              }
              
              if (strDate1.length() <= 11) {
                  dateFormat1 = new SimpleDateFormat("MMMM yyyy");
              }
              
              if (strDate2.length() <= 11) {
                  dateFormat2 = new SimpleDateFormat("MMMM yyyy");
              }
              
              Date date1 = dateFormat1.parse(strDate1);
              Date date2 = dateFormat2.parse(strDate2);
              
              return date1.compareTo(date2);
          } catch (ParseException e) {
              e.printStackTrace();
          }
          
          return 0;
      }
  }
	
}
