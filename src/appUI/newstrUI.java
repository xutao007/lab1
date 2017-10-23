package appUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;

public class newstrUI extends JFrame {

  private JPanel contentPane;
  private JTextArea inputStr;
  private JTextArea outputStr;
  private digraph gDigraph;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          newstrUI frame = new newstrUI();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public newstrUI() {
    setTitle("\u751F\u6210\u65B0\u6587\u672C");
    // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 624, 361);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBorder(new EmptyBorder(5, 5, 5, 5));
    panel.setBounds(0, 25, 602, 261);
    contentPane.add(panel);

    inputStr = new JTextArea();
    inputStr.setWrapStyleWord(true);
    inputStr.setBounds(146, 10, 368, 92);
    inputStr.setLineWrap(true);
    panel.add(inputStr);

    JButton ok = new JButton("\u786E\u5B9A");
    ok.setActionCommand("ok");
    ok.setBounds(284, 126, 93, 23);
    panel.add(ok);
    ok.addActionListener(new ButtonClickListener());

    outputStr = new JTextArea();
    outputStr.setBounds(146, 159, 368, 92);
    outputStr.setLineWrap(true);
    panel.add(outputStr);

    JLabel label = new JLabel("\u8F93\u5165");
    label.setBounds(44, 48, 54, 15);
    panel.add(label);

    JLabel label_1 = new JLabel("\u7ED3\u679C");
    label_1.setBounds(44, 199, 54, 15);
    panel.add(label_1);
  }

  public void setDigraph(digraph G) {
    this.gDigraph = G;
  }

  private class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      if (command.equals("ok")) {
        graphFunction f = new graphFunction();
        String res = f.generateNewText(gDigraph, inputStr.getText());
        outputStr.setText(res);
      }
    }
  }
}
