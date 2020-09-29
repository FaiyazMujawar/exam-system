package main.java.views;

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

    frame.setSize(661, 792);
    frame.setLayout(null);

    // Labels
    idLabel = new JLabel("ID");
    idLabel.setBounds(39, 41, 238, 53);
    qtnLabel = new JLabel("Question");
    qtnLabel.setBounds(39, 119, 238, 53);
    optALabel = new JLabel("Option A");
    optALabel.setBounds(39, 257, 238, 53);
    optBLabel = new JLabel("Option B");
    optBLabel.setBounds(39, 353, 238, 53);
    optCLabel = new JLabel("Option C");
    optCLabel.setBounds(39, 431, 238, 53);
    optDLabel = new JLabel("Option D");
    optDLabel.setBounds(39, 509, 238, 53);
    answerLabel = new JLabel("Correct Answer");
    answerLabel.setBounds(39, 587, 238, 53);

    // TextFields
    idField = new JTextField();
    idField.setBounds(239, 41, 373, 49);
    idField.setEditable(false);
    idField.setText(Integer.toString(ID));
    qtnField = new JTextArea();
    qtnField.setBounds(239, 119, 373, 131);
    qtnField.setEditable(false);
    qtnField.setText(qtn);
    optAField = new JTextField();
    optAField.setBounds(239, 279, 373, 49);
    optAField.setEditable(false);
    optAField.setText(optA);
    optBField = new JTextField();
    optBField.setBounds(239, 357, 373, 49);
    optBField.setEditable(false);
    optBField.setText(optB);
    optCField = new JTextField();
    optCField.setBounds(239, 435, 373, 49);
    optCField.setEditable(false);
    optCField.setText(optC);
    optDField = new JTextField();
    optDField.setBounds(239, 509, 373, 49);
    optDField.setEditable(false);
    optDField.setText(optD);
    answerField = new JComboBox<>(new String[] { "A", "B", "C", "D" });
    answerField.setSelectedItem(question.getcorrectAnswer());
    answerField.setEnabled(false);
    answerField.setBounds(239, 587, 373, 49);

    // Buttons
    update = new JButton("Update");
    update.setBounds(153, 673, 148, 48);
    update.addActionListener(this);
    delete = new JButton("Delete");
    delete.setBounds(321, 673, 148, 48);
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
        adminController.updateQuestion(
          Integer.parseInt(idField.getText()),
          qtnField.getText(),
          optAField.getText(),
          optBField.getText(),
          optCField.getText(),
          optDField.getText(),
          (String) answerField.getSelectedItem()
        );
        update.setText("Update");
        delete.setText("Delete");
        JOptionPane.showMessageDialog(
          this.frame,
          "Question updated succesfully!",
          "SUCCESS!",
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
    } else if (command == "Delete") {
      int opt = JOptionPane.showConfirmDialog(
        frame,
        "Confirm to delete the question?",
        "Delete Question",
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
