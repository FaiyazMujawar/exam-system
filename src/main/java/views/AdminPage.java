package main.java.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;
import main.java.controllers.AdminController;
import main.java.exceptions.InvalidArgumentsException;
import main.java.models.Question;

public class AdminPage implements ActionListener {
  private static List<Question> questions;
  private Question question;
  private static JFrame frame;
  private static JTable table;
  private static Object source;
  private static JButton add, search;
  private static JTextField searchText;
  private static JScrollPane scrollPane;
  private static AdminController adminController;

  public AdminPage() {
    adminController = new AdminController();
    frame = new JFrame("Admin Page");
    frame.setLayout(null);
    frame.setSize(1300, 710);

    searchText = new JTextField();
    searchText.setBounds(312, 39, 676, 47);
    search = new JButton("Search");
    search.setBounds(1015, 39, 213, 47);
    search.addActionListener(this);
    add = new JButton("Add");
    add.setBounds(64, 39, 213, 47);
    add.addActionListener(this);

    frame.add(add);
    setTable();
    frame.add(searchText);
    frame.add(search);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  /* 
   TODO: [BUG] The setTable() method freezes when called from other classes. 
   */
  protected static void setTable() {
    questions = adminController.getAllQuestions();
    Vector<Vector<String>> rows = new Vector<>();
    Vector<String> row;
    for (Question question : questions) {
      row = new Vector<>();
      row.add(Integer.toString(question.getId()));
      row.add(question.getQuestion());
      row.add(question.getOptionA());
      row.add(question.getOptionB());
      row.add(question.getOptionC());
      row.add(question.getOptionD());
      row.add(question.getcorrectAnswer());
      rows.add(row);
    }

    Vector<String> columnNames = new Vector<>();
    columnNames.add("ID");
    columnNames.add("Question");
    columnNames.add("Option A");
    columnNames.add("Option B");
    columnNames.add("Option C");
    columnNames.add("Option D");
    columnNames.add("Correct Answer");

    table = new JTable(rows, columnNames);
    table.addMouseListener(
      new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent me) {
          if (me.getClickCount() == 2) {
            int row = table.rowAtPoint(me.getPoint());
            if (row != -1) {
              new ViewQuestionPage(questions.get(row));
            }
          }
        }
      }
    );
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setRowHeight(30);
    table.setDefaultEditor(Object.class, null);
    TableColumnModel columnModel = table.getColumnModel();
    columnModel.getColumn(0).setMinWidth(50);
    columnModel.getColumn(1).setMinWidth(500);
    columnModel.getColumn(2).setMinWidth(200);
    columnModel.getColumn(3).setMinWidth(200);
    columnModel.getColumn(4).setMinWidth(200);
    columnModel.getColumn(5).setMinWidth(200);
    columnModel.getColumn(6).setMinWidth(50);

    try {
      frame.remove(scrollPane);
    } catch (Exception e) {}
    scrollPane = new JScrollPane(table);
    scrollPane.setBounds(64, 125, 1164, 500);
    frame.add(scrollPane);
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    source = ae.getSource();
    if (source == add) {
      new AddQuestionPage();
    } else if (source == search) {
      try {
        int ID = Integer.parseInt(searchText.getText());
        question = adminController.getQuestionById(ID);
        if (question == null) {
          JOptionPane.showMessageDialog(
            frame,
            "No question with specified ID found",
            "FAILURE!",
            JOptionPane.ERROR_MESSAGE
          );
        } else {
          new ViewQuestionPage(question);
        }
      } catch (InvalidArgumentsException e) {
        JOptionPane.showMessageDialog(
          frame,
          "Please enter a valid question ID (Greater than 0)!",
          "ERROR!",
          JOptionPane.ERROR_MESSAGE
        );
      } catch (NumberFormatException ne) {
        JOptionPane.showMessageDialog(
          frame,
          "ID should be a number!",
          "ERROR!",
          JOptionPane.ERROR_MESSAGE
        );
      }
    }
  }

  public static void main(String[] args) {
    new AdminPage();
  }
}
