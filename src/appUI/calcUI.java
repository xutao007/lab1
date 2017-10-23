package appUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class calcUI extends JFrame {

  private JPanel contentPane;
  private JTextField from;
  private JTextField to;
  private JTextField st;
  private JLabel warningMsg;
  private digraph gDigraph;
  private String pathStr;
  private int flag = 0;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          calcUI frame = new calcUI();
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
  public calcUI() {
    setTitle("\u8BA1\u7B97\u8DEF\u5F84");
    // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 407, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    from = new JTextField();
    from.setBounds(72, 81, 66, 21);
    contentPane.add(from);
    from.setColumns(10);

    to = new JTextField();
    to.setBounds(182, 81, 66, 21);
    contentPane.add(to);
    to.setColumns(10);

    st = new JTextField();
    st.setBounds(72, 187, 66, 21);
    contentPane.add(st);
    st.setColumns(10);

    JLabel label = new JLabel("\u4ECE");
    label.setBounds(45, 84, 30, 15);
    contentPane.add(label);

    JLabel label_1 = new JLabel("\u5230");
    label_1.setBounds(155, 84, 54, 15);
    contentPane.add(label_1);

    JButton button1 = new JButton("\u6700\u77ED\u8DEF\u5F84");
    button1.setBounds(258, 80, 93, 23);
    contentPane.add(button1);
    button1.setActionCommand("button1");
    button1.addActionListener(new ButtonClickListener());

    JButton button2 = new JButton("\u6700\u77ED\u8DEF\u5F84");
    button2.setBounds(258, 186, 93, 23);
    contentPane.add(button2);
    button2.setActionCommand("button2");
    button2.addActionListener(new ButtonClickListener());

    JLabel label_2 = new JLabel("\u8D77\u70B9");
    label_2.setBounds(45, 190, 37, 15);
    contentPane.add(label_2);

    JLabel lblNewLabel = new JLabel("\u4E24\u70B9\u6700\u77ED\u8DEF\u5F84");
    lblNewLabel.setBounds(28, 36, 150, 15);
    contentPane.add(lblNewLabel);

    JLabel lblNewLabel_1 = new JLabel("\u5355\u6E90\u70B9\u6700\u77ED\u8DEF\u5F84");
    lblNewLabel_1.setBounds(28, 143, 110, 15);
    contentPane.add(lblNewLabel_1);

    warningMsg = new JLabel("");
    warningMsg.setBounds(72, 236, 176, 15);
    contentPane.add(warningMsg);
  }

  public void setDigraph(digraph G) {
    this.gDigraph = G;
  }

  public String getDotStr() {
    return this.pathStr;
  }

  public int getFlag() {
    return flag;
  }

  private class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();

      if (command.equals("button1")) {
        String word1 = from.getText();
        String word2 = to.getText();
        if (word1.isEmpty() || word2.isEmpty()) {
          warningMsg.setText("请规范输入，不要为空");
        } else {
          graphFunction f = new graphFunction();
          pathStr = f.calcShortestPath(gDigraph, word1, word2);
          if (pathStr == null) {
            warningMsg.setText("两者不可达");
          }
          String dotStr = f.getGraphDot(gDigraph, pathStr);
          GraphViz gv = new GraphViz();
          gv.addln(gv.start_graph());
          gv.add(dotStr);
          gv.add(gv.end_graph());
          gv.increaseDpi();
          String type = "jpg";
          String repesentationType = "dot";
          File out = new File("e:\\path1." + type); // Windows
          if (out.exists()) {
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out);
          } else {
            System.out.print("空");
          }
          picOnly picWin = new picOnly();
          picWin.setPicPath("e:\\path1.jpg");
          picWin.setTitle("最短路径");
          picWin.loadPic();
          picWin.setVisible(true);
        }
      } else {
        String source = st.getText();
        if (source.isEmpty()) {
          warningMsg.setText("请规范输入,不要为空");
        } else {
        }

      }
    }
  }
}
