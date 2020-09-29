package main.java.views;

import java.awt.Font;
import java.awt.Insets;
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

  private Font font;

  private AdminController adminController;

  AddQuestionPage() {
    adminController = new AdminController();

    frame = new JFrame("Add Question");
    frame.setSize(661, 792);
    frame.setLayout(null);
    frame.setResizable(false);

    font = new Font("Sans Serif", Font.PLAIN, 24);

    // Labels
    qtnLabel = new JLabel("Question");
    qtnLabel.setFont(font);
    qtnLabel.setBounds(39, 41, 147, 53);
    optALabel = new JLabel("Option A");
    optALabel.setFont(font);
    optALabel.setBounds(39, 197, 147, 53);
    optBLabel = new JLabel("Option B");
    optBLabel.setFont(font);
    optBLabel.setBounds(39, 275, 147, 53);
    optCLabel = new JLabel("Option C");
    optCLabel.setBounds(39, 353, 147, 53);
    optCLabel.setFont(font);
    optDLabel = new JLabel("Option D");
    optDLabel.setBounds(39, 431, 147, 53);
    answerLabel = new JLabel("Correct Answer");
    optDLabel.setFont(font);
    answerLabel.setBounds(39, 509, 147, 53);
    answerLabel.setFont(font);

    // TextFields
    qtnField = new JTextArea();
    qtnField.setMargin(new Insets(5, 5, 5, 5));
    qtnField.setBounds(239, 41, 373, 131);
    qtnField.setFont(font);
    optAField = new JTextField();
    optAField.setMargin(new Insets(5, 5, 5, 5));
    optAField.setBounds(239, 201, 373, 49);
    optAField.setFont(font);
    optBField = new JTextField();
    optBField.setMargin(new Insets(5, 5, 5, 5));
    optBField.setBounds(239, 279, 373, 49);
    optBField.setFont(font);
    optCField = new JTextField();
    optCField.setMargin(new Insets(5, 5, 5, 5));
    optCField.setBounds(239, 357, 373, 49);
    optCField.setFont(font);
    optDField = new JTextField();
    optDField.setMargin(new Insets(5, 5, 5, 5));
    optDField.setBounds(239, 431, 373, 49);
    optDField.setFont(font);
    answerField = new JComboBox<>(new String[] { "A", "B", "C", "D" });
    answerField.setBounds(239, 509, 373, 49);
    answerField.setFont(font);

    // Buttons
    confirm = new JButton("Confirm");
    confirm.setBounds(153, 597, 147, 53);
    confirm.setFont(font);
    confirm.addActionListener(this);
    cancel = new JButton("Cancel");
    cancel.setBounds(330, 597, 147, 53);
    cancel.setFont(font);
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
