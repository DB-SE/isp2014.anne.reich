import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public aspect byTextInput {

  private static int searchAuswahl = 0;
  private static ArrayList<String> treffer;

  private static Object bibo;
  private static DatabaseAccess dba;
  private static Method klick;
  private static Method refreshGesamtList;  
  private static JList<String> list;
  private static JTextField textField;
  private static String[][] mangaList;

  pointcut searchOption(JPanel obenAll):
    call(private JPanel Bibo.byTextInput(JPanel)) && args(obenAll);

  @SuppressWarnings("unchecked")
  before(JPanel obenAll): searchOption(obenAll) {
    try {

      bibo = thisJoinPoint.getThis();

      klick = thisJoinPoint.getThis().getClass().getDeclaredMethod("klick");
      klick.setAccessible(true);
      
      Class[] arg = new Class[1];
      arg[0] = JList.class;
      refreshGesamtList = thisJoinPoint.getThis().getClass().getDeclaredMethod("refreshGesamtList", arg);
      refreshGesamtList.setAccessible(true);

      Field fieldDBA = thisJoinPoint.getThis().getClass().getDeclaredField("_dba");
      fieldDBA.setAccessible(true);
      dba = (DatabaseAccess) fieldDBA.get(bibo);

      Field fieldList = thisJoinPoint.getThis().getClass().getDeclaredField("list");
      fieldList.setAccessible(true);
      list = (JList<String>) fieldList.get(bibo);

      Field fieldMangaList = thisJoinPoint.getThis().getClass().getDeclaredField("mangaList");
      fieldMangaList.setAccessible(true);
      mangaList = (String[][]) fieldMangaList.get(bibo);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  JPanel around(JPanel obenAll): searchOption(obenAll) {
    JPanel suchePanel = new JPanel();
    suchePanel.setLayout(new BorderLayout());
    suchePanel.setBorder(new EmptyBorder(0, 3, 5, 3));

    // Combo-Box
    String[] nachList = { "nach Titel:", "nach Autor:", "nach Verlag:" };
    final JComboBox<String> nach = new JComboBox<String>(nachList);
    nach.setPreferredSize(new Dimension(112, 25));
    setTrefferList(searchAuswahl);
    // erkennt, welche Suche-Auswahl aktiviert ist (nach Zahlen)
    nach.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchAuswahl = nach.getSelectedIndex();
        setTrefferList(searchAuswahl);
      }
    });

    suchePanel.add(nach, BorderLayout.LINE_START);

    // Textfeld
    textField = new JTextField(50);
    suchePanel.add(textField, BorderLayout.CENTER);
    autoComplete();

    // Suchen-Button
    JButton suchButton = new JButton();
    suchButton.addActionListener(new ActionListener() {
      @SuppressWarnings("unchecked")
      public void actionPerformed(ActionEvent arg0) {
        String match = textField.getText();

        ArrayList<String> matchTitelDmy = new ArrayList<String>();

        // alle Möglichkeiten abfangen
        if (searchAuswahl == 0) {
          matchTitelDmy.add(match);
        } else {
          if (searchAuswahl == 1)
            matchTitelDmy = getTitelByAutor(match);
          else
            matchTitelDmy = getTitelByVerlag(match);
        }

        // diese in String[] packen
        String[] matchTitel = new String[matchTitelDmy.size()];

        for (int j = 0; j < matchTitelDmy.size(); j++) {
          matchTitel[j] = matchTitelDmy.get(j);
        }

        try {
          list = new JList<String>(matchTitel);
          String[] farbe = getFarbe(matchTitel);
          list.setCellRenderer(new TitelFarbe(farbe));
          klick.invoke(bibo);
          refreshGesamtList.invoke(bibo, list);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    try {
      BufferedImage bi = ImageIO.read(ImageIO.class.getResource("/suchenIcon.gif"));
      suchButton.setIcon(new ImageIcon(bi));
    } catch (IOException e2) {
      e2.printStackTrace();
    }
    suchButton.setPreferredSize(new Dimension(25, 25));

    suchePanel.add(suchButton, BorderLayout.LINE_END);

    obenAll.add(suchePanel, BorderLayout.CENTER);

    return obenAll;
  }

  private void setTrefferList(int auswahl) {
    treffer = new ArrayList<String>();
    if (auswahl == 0) {
      String[] titelListe = getOnlyTitelList();

      for (int i = 0; i < titelListe.length; i++) {
        treffer.add(titelListe[i]);
      }
      Collections.sort(treffer);
    } else if (auswahl == 1) {
      String[] autorListe = dba.getAutor();

      // fülle Treffer aus
      for (int i = 0; i < autorListe.length; i++) {
        treffer.add(autorListe[i]);
      }
      Collections.sort(treffer);
    } else {

      String[] verlagListe = dba.getVerlag();
      for (int i = 0; i < verlagListe.length; i++) {
        treffer.add(verlagListe[i]);
      }
      Collections.sort(treffer);
    }
  }

  private String[] getOnlyTitelList() {
    String[][] titelListe = dba.getMangareiheTitel();

    String[] titel = new String[titelListe.length];

    for (int i = 0; i < titelListe.length; i++) {
      titel[i] = titelListe[i][0];
    }

    return titel;
  }

  private static ArrayList<String> getTitelByAutor(String autor) {
    String[] titel = dba.getTitelByAutor(autor);
    ArrayList<String> arrTitel = new ArrayList<String>();
    Collections.sort(arrTitel);

    for (int i = 0; i < titel.length; i++) {
      arrTitel.add(titel[i]);
    }

    return arrTitel;
  }

  private static ArrayList<String> getTitelByVerlag(String verlag) {
    String[] titel = dba.getTitelByVerlag(verlag);

    ArrayList<String> arrTitel = new ArrayList<String>();
    Collections.sort(arrTitel);

    for (int i = 0; i < titel.length; i++) {
      arrTitel.add(titel[i]);
    }

    return arrTitel;
  }

  private static String[] getFarbe(String[] matchList) {
    String[] matchFarbe = new String[matchList.length];

    for (int i = 0; i < matchList.length; i++) {
      for (int j = 0; j < mangaList.length; j++) {
        if (matchList[i] != null && matchList[i].equals(mangaList[j][0])) {
          matchFarbe[i] = mangaList[j][1];
        }
      }
    }

    return matchFarbe;
  }

  private static void autoComplete() {
    textField.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent arg0) {
      }

      public void insertUpdate(DocumentEvent ev) {
        String completion;
        int pos = ev.getOffset();
        String content = null;
        try {
          content = textField.getText(0, pos + 1);
        } catch (BadLocationException ex) {
          ex.printStackTrace();
        }

        int w;
        for (w = pos; w >= 0; w--) {
        }
        if (pos - w < 2) {
          return;
        }

        final String prefix = content.substring(w + 1);
        int n = Collections.binarySearch(treffer, prefix);
        if (n < 0 && -n <= treffer.size()) {
          final String match = treffer.get(-n - 1);
          if (match.startsWith(prefix)) {
            completion = match.substring(pos - w);
            SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
            textField.addKeyListener(new KeyListener() {

              @SuppressWarnings("unchecked")
              public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                  if (textField.getText().length() != 0) {

                    ArrayList<String> matchTitelDmy = new ArrayList<String>();

                    // alle Möglichkeiten abfangen
                    if (searchAuswahl == 0) {
                      for (int i = 0; i < treffer.size(); i++) {
                        if (treffer.get(i).startsWith(prefix))
                          matchTitelDmy.add(treffer.get(i));
                      }
                    } else {
                      if (searchAuswahl == 1)
                        matchTitelDmy = getTitelByAutor(match);
                      else
                        matchTitelDmy = getTitelByVerlag(match);
                    }

                    // diese in String[] packen
                    String[] matchTitel = new String[matchTitelDmy.size()];

                    for (int j = 0; j < matchTitelDmy.size(); j++) {
                      matchTitel[j] = matchTitelDmy.get(j);
                    }
                    try {
                      list = new JList<String>(matchTitel);
                      String[] farbe = getFarbe(matchTitel);
                      list.setCellRenderer(new TitelFarbe(farbe));
                      klick.invoke(bibo);
                      refreshGesamtList.invoke(bibo, list);
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  }

                  else {
                    String[] liste = new String[mangaList.length];
                    for (int i = 0; i < mangaList.length; i++) {
                      liste[i] = mangaList[i][0];
                    }

                    try {
                      list = new JList<String>(liste);
                      String[] farbe = getFarbe(liste);
                      list.setCellRenderer(new TitelFarbe(farbe));
                      klick.invoke(bibo);
                      refreshGesamtList.invoke(bibo, list);
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                  }

                } else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
                  ArrayList<String> matchTitelDmy = new ArrayList<String>();

                  // alle Möglichkeiten abfangen
                  if (searchAuswahl == 0) {
                    matchTitelDmy.add(match);
                  } else {
                    if (searchAuswahl == 1)
                      matchTitelDmy = getTitelByAutor(match);
                    else
                      matchTitelDmy = getTitelByVerlag(match);
                  }

                  // diese in String[] packen
                  String[] matchTitel = new String[matchTitelDmy.size()];

                  for (int j = 0; j < matchTitelDmy.size(); j++) {
                    matchTitel[j] = matchTitelDmy.get(j);
                  }
                  try {
                    list = new JList<String>(matchTitel);
                    String[] farbe = getFarbe(matchTitel);
                    list.setCellRenderer(new TitelFarbe(farbe));
                    klick.invoke(bibo);
                    refreshGesamtList.invoke(bibo, list);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              }

              public void keyReleased(KeyEvent arg0) {
              }

              public void keyTyped(KeyEvent arg0) {
              }
            });
          }
        }
      }

      public void removeUpdate(DocumentEvent arg0) {
      }

    });

  }

  private static class CompletionTask implements Runnable {

    String completion;

    int position;

    CompletionTask(String completion, int position) {
      this.completion = completion;
      this.position = position;
    }

    public void run() {
      textField.setText(textField.getText() + completion);
      textField.setCaretPosition(position + completion.length());
      textField.moveCaretPosition(position);
    }
  }

  @SuppressWarnings({ "rawtypes", "serial" })
  static class TitelFarbe extends JLabel implements ListCellRenderer {

    private String[] color;

    public TitelFarbe(String[] color) {
      setOpaque(true);
      this.color = color;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {

      if (value != null) {
        setText(value.toString());

        if (isSelected) {
          setBorder(BorderFactory.createLineBorder(Color.black));
        } else {
          setBorder(new EmptyBorder(0, 0, 0, 0));
        }

        if (color[index].equals("gruen")) {
          setBackground(Color.GREEN);
        } else if (color[index].equals("rot")) {
          setBackground(Color.RED);
        } else {
          setBackground(Color.YELLOW);
        }

        return this;
      }

      else {
        setBackground(Color.WHITE);
        setText("");
        return this;
      }
    }

  }
}
