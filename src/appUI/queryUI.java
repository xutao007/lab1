package appUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class queryUI extends JFrame {

  private JPanel contentPane;
  private JTextField word1;
  private JTextField word2;
  private JTextArea textArea;
  private digraph gDigraph;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          queryUI frame = new queryUI();
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
  public queryUI() {
    setTitle("\u67E5\u8BE2\u6865\u63A5\u8BCD");
    // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 437, 328);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel label = new JLabel("word1");
    label.setBounds(74, 28, 54, 15);
    contentPane.add(label);

    word1 = new JTextField();
    word1.setColumns(10);
    word1.setBounds(138, 25, 182, 21);
    contentPane.add(word1);

    word2 = new JTextField();
    word2.setColumns(10);
    word2.setBounds(138, 68, 182, 21);
    contentPane.add(word2);

    JLabel label_1 = new JLabel("word2");
    label_1.setBounds(74, 71, 54, 15);
    contentPane.add(label_1);

    JButton button = new JButton("\u786E\u5B9A");
    button.setActionCommand("ok");
    button.setBounds(147, 117, 120, 23);
    contentPane.add(button);

    textArea = new JTextArea();
    textArea.setBounds(73, 181, 247, 75);
    textArea.setLineWrap(true);
    contentPane.add(textArea);
    button.addActionListener(new ButtonClickListener());
  }

  public void setDigraph(digraph G) {
    this.gDigraph = G;
  }

  private class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      if (command.equals("ok")) {
        String w1 = word1.getText();
        String w2 = word2.getText();
        if (w1.isEmpty() && w2.isEmpty()) {
          textArea.setText("请确保word1和word2非空");
        } else {
          graphFunction f = new graphFunction();
          String res = f.queryBridgeWords(gDigraph, w1, w2);
          textArea.setText(res);
        }
      }
    }
  }
}
