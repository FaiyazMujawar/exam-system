package main.java.views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import main.java.controllers.AdminController;
import main.java.exceptions.InvalidArgumentsException;

public class AddQuestionPage implements ActionListener {
  private JFrame frame;
  private Object source;
  private JTextArea qtnField;
  private JButton confirm, cancel;
  private JTextField optAField, optBField, optCField, optDField;
  private JComboBox<String> answerField;
  private JLabel qtnLabel, optALabel, optBLabel, optCLabel, optDLabel, answerLabel;

  private AdminController adminController;

  AddQuestionPage() {
    adminController = new AdminController();

    frame = new JFrame("Add Question");
    frame.setSize(300, 500);
    frame.setLayout(new FlowLayout());

    // Labels
    qtnLabel = new JLabel("Question");
    optALabel = new JLabel("Option A");
    optBLabel = new JLabel("Option B");
    optCLabel = new JLabel("Option C");
    optDLabel = new JLabel("Option D");
    answerLabel = new JLabel("Correct Answer");

    // TextFields
    qtnField = new JTextArea(4, 25);
    optAField = new JTextField(25);
    optBField = new JTextField(25);
    optCField = new JTextField(25);
    optDField = new JTextField(25);
    answerField = new JComboBox<>(new String[] { "A", "B", "C", "D" });

    // Buttons
    confirm = new JButton("Confirm");
    confirm.addActionListener(this);
    cancel = new JButton("Cancel");
    cancel.addActionListener(this);

    frame.add(qtnLabel);
    frame.add(qtnField);
    frame.add(optALabel);
    frame.add(optAField);
    frame.add(optBLabel);
    frame.add(optBField);
    frame.add(optCLabel);
    frame.add(optCField);
    frame.add(optDLabel);
    frame.add(optDField);
    frame.add(answerLabel);
    frame.add(answerField);
    frame.add(confirm);
    frame.add(cancel);

    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    source = ae.getSource();
    if (source == confirm) {
      int opt = JOptionPane.showConfirmDialog(
        frame,
        "Confirm to add the question?",
        "Add Question",
        JOptionPane.YES_NO_OPTION
      );
      if (opt == 0) {
        try {
          adminController.addQuestion(
            qtnField.getText(),
            optAField.getText(),
            optBField.getText(),
            optCField.getText(),
            optDField.getText(),
            (String) answerField.getSelectedItem()
          );
          qtnField.setText("");
          optAField.setText("");
          optBField.setText("");
          optCField.setText("");
          optDField.setText("");
          answerField.setSelectedItem("A");
          JOptionPane.showMessageDialog(
            frame,
            "Question added successfully!",
            "Add Question",
            JOptionPane.INFORMATION_MESSAGE
          );
          AdminPage.setTable();
        } catch (InvalidArgumentsException e) {
          JOptionPane.showMessageDialog(
            frame,
            e.getMessage(),
            "ERROR!",
            JOptionPane.ERROR_MESSAGE
          );
        }
      }
    } else if (source == cancel) frame.dispose();
  }
}
