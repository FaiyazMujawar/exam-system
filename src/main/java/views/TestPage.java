package main.java.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import main.java.controllers.TestController;
import main.java.models.Question;

public class TestPage implements ActionListener {
  private JFrame frame;

  private JTextArea qtn;
  private JRadioButton optA, optB, optC, optD;
  private JButton previous, next, save, submit;
  private ButtonGroup options;
  private int questionCount;

  private Font font;

  private TestController testController;

  /* 
    Todo: Beautify UI.
  */
  public TestPage(int questionCount) {
    frame = new JFrame("Test");
    frame.setSize(1000, 644);
    frame.setLayout(null);

    font = new Font("Sans Serif", Font.PLAIN, 24);

    this.questionCount = questionCount;

    testController = new TestController(questionCount);

    qtn = new JTextArea();
    qtn.setWrapStyleWord(true);
    qtn.setLineWrap(true);
    qtn.setOpaque(false);
    qtn.setEditable(false);
    qtn.setFocusable(false);
    qtn.setBackground(UIManager.getColor("Label.background"));
    qtn.setFont(UIManager.getFont("Label.font"));
    qtn.setBorder(UIManager.getBorder("Label.border"));
    qtn.setFont(font);
    qtn.setBounds(200, 93, 700, 130);

    optA = new JRadioButton("", false);
    optA.setFont(font);
    optA.setBounds(220, 257, 300, 45);
    optA.addActionListener(this);

    optB = new JRadioButton("", false);
    optB.setFont(font);
    optB.setBounds(570, 257, 300, 45);
    optB.addActionListener(this);

    optC = new JRadioButton("", false);
    optC.setFont(font);
    optC.setBounds(220, 356, 300, 45);
    optC.addActionListener(this);

    optD = new JRadioButton("", false);
    optD.setFont(font);
    optD.setBounds(570, 356, 300, 45);
    optD.addActionListener(this);

    options = new ButtonGroup();
    options.add(optA);
    options.add(optB);
    options.add(optC);
    options.add(optD);

    previous = new JButton("Previous");
    previous.setFont(font);
    previous.addActionListener(this);
    previous.setBounds(191, 535, 150, 40);

    save = new JButton("Save");
    save.addActionListener(this);
    save.setBounds(351, 535, 150, 40);
    save.setFont(font);

    next = new JButton("Next");
    next.addActionListener(this);
    next.setBounds(511, 535, 150, 40);
    next.setFont(font);

    submit = new JButton("Submit");
    submit.addActionListener(this);
    submit.setBounds(671, 535, 150, 40);
    submit.setFont(font);

    setQuestion(
      testController.getNextQuestion(),
      testController.getSelectedAnswer()
    );

    frame.add(qtn);
    frame.add(optA);
    frame.add(optB);
    frame.add(optC);
    frame.add(optD);
    frame.add(previous);
    frame.add(save);
    frame.add(next);
    frame.add(submit);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (command == "Previous") {
      testController.setAnswer(getSelectedOption());
      setQuestion(
        testController.getPreviousQuestion(),
        testController.getSelectedAnswer()
      );
    } else if (command == "Save") {
      testController.setAnswer(getSelectedOption());
    } else if (command == "Next") {
      testController.setAnswer(getSelectedOption());
      setQuestion(
        testController.getNextQuestion(),
        testController.getSelectedAnswer()
      );
    } else if (command == "Submit") {
      int opt = JOptionPane.showConfirmDialog(
        frame,
        "Sure to submit?",
        "Confirm Submit",
        JOptionPane.YES_NO_OPTION
      );
      if (opt == 0) {
        int score = testController.getResult();
        JOptionPane.showMessageDialog(
          frame,
          "Your score is: " + score + "/" + questionCount,
          "Result",
          JOptionPane.INFORMATION_MESSAGE
        );
        frame.dispose();
      }
    }
  }

  private void setQuestion(Question question, String answer) {
    qtn.setText(question.getQuestion());
    optA.setText(question.getOptionA());
    optB.setText(question.getOptionB());
    optC.setText(question.getOptionC());
    optD.setText(question.getOptionD());
    if (answer == "A") {
      optA.setSelected(true);
    } else if (answer == "B") {
      optB.setSelected(true);
    } else if (answer == "C") {
      optC.setSelected(true);
    } else if (answer == "D") {
      optD.setSelected(true);
    }
  }

  private String getSelectedOption() {
    String answer = "";
    if (optA.isSelected()) {
      answer = "A";
    } else if (optB.isSelected()) {
      answer = "B";
    } else if (optC.isSelected()) {
      answer = "C";
    } else if (optD.isSelected()) {
      answer = "D";
    }
    options.clearSelection();
    return answer;
  }

  public static void main(String[] args) {
    new TestPage(7);
  }
}
