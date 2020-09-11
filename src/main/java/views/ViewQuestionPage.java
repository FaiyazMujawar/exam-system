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
import main.java.models.Question;

public class ViewQuestionPage implements ActionListener {
  private int ID;
  private String qtn, optA, optB, optC, optD, answer;
  private AdminController adminController;
  private JFrame frame;
  private JTextArea qtnField;
  private JButton update, delete;
  private JTextField idField, optAField, optBField, optCField, optDField;
  private JLabel idLabel, qtnLabel, optALabel, optBLabel, optCLabel, optDLabel, answerLabel;
  private JComboBox<String> answerField;

  ViewQuestionPage(Question question) {
    adminController = new AdminController();

    ID = question.getId();
    qtn = question.getQuestion();
    optA = question.getOptionA();
    optB = question.getOptionB();
    optC = question.getOptionC();
    optD = question.getOptionD();
    answer = question.getcorrectAnswer();
    frame = new JFrame("Question " + question.getId());

    frame.setSize(300, 500);
    frame.setLayout(new FlowLayout());

    // Labels
    idLabel = new JLabel("ID");
    qtnLabel = new JLabel("Question");
    optALabel = new JLabel("Option A");
    optBLabel = new JLabel("Option B");
    optCLabel = new JLabel("Option C");
    optDLabel = new JLabel("Option D");
    answerLabel = new JLabel("Correct Answer");

    // TextFields
    idField = new JTextField(25);
    idField.setEditable(false);
    idField.setText(Integer.toString(ID));
    qtnField = new JTextArea(4, 25);
    qtnField.setEditable(false);
    qtnField.setText(qtn);
    optAField = new JTextField(25);
    optAField.setEditable(false);
    optAField.setText(optA);
    optBField = new JTextField(25);
    optBField.setEditable(false);
    optBField.setText(optB);
    optCField = new JTextField(25);
    optCField.setEditable(false);
    optCField.setText(optC);
    optDField = new JTextField(25);
    optDField.setEditable(false);
    optDField.setText(optD);
    answerField = new JComboBox<>(new String[] { "A", "B", "C", "D" });
    answerField.setSelectedItem(question.getcorrectAnswer());
    answerField.setEnabled(false);

    // Buttons
    update = new JButton("Update");
    update.addActionListener(this);
    delete = new JButton("Delete");
    delete.addActionListener(this);

    frame.add(idLabel);
    frame.add(idField);
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
    frame.add(update);
    frame.add(delete);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    String command = ae.getActionCommand();
    if (command == "Update") {
      qtnField.setEditable(true);
      optAField.setEditable(true);
      optBField.setEditable(true);
      optCField.setEditable(true);
      optDField.setEditable(true);
      answerField.setEnabled(true);
      update.setText("Confirm");
      delete.setText("Cancel");
    } else if (command == "Confirm") {
      try {
        qtnField.setEditable(false);
        optAField.setEditable(false);
        optBField.setEditable(false);
        optCField.setEditable(false);
        optDField.setEditable(false);
        answerField.setEnabled(false);
        adminController.addQuestion(
          qtnField.getText(),
          optAField.getText(),
          optBField.getText(),
          optCField.getText(),
          optDField.getText(),
          (String) answerField.getSelectedItem()
        );
        update.setText("Update");
        delete.setText("Delete");
      } catch (InvalidArgumentsException e) {
        JOptionPane.showMessageDialog(
          frame,
          e.getMessage(),
          "ERROR!",
          JOptionPane.ERROR_MESSAGE
        );
      }
    } else if (command == "Delete") {
      int opt = JOptionPane.showConfirmDialog(
        frame,
        "Confirm to add the question?",
        "Add Question",
        JOptionPane.YES_NO_OPTION
      );
      if (opt == 0) {
        boolean isDeleted = false;
        try {
          isDeleted = adminController.deleteQuestionByID(ID);
        } catch (InvalidArgumentsException e) {}
        if (isDeleted) {
          JOptionPane.showMessageDialog(
            this.frame,
            "Question deleted succesfully!",
            "SUCCESS!",
            JOptionPane.INFORMATION_MESSAGE
          );
          AdminPage.setTable();
          frame.dispose();
        } else {
          JOptionPane.showMessageDialog(
            this.frame,
            "Question deletion failed!",
            "ERROR!",
            JOptionPane.ERROR_MESSAGE
          );
        }
      }
    } else if (command == "Cancel") {
      update.setText("Update");
      delete.setText("Delete");
      qtnField.setText(qtn);
      optAField.setText(optA);
      optBField.setText(optB);
      optCField.setText(optC);
      optDField.setText(optD);
      answerField.setSelectedItem(answer);
      qtnField.setEditable(false);
      optAField.setEditable(false);
      optBField.setEditable(false);
      optCField.setEditable(false);
      optDField.setEditable(false);
      answerField.setEditable(false);
    }
  }
}
