package appUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class randomUI extends JFrame {

  private JPanel contentPane;
  private JTextArea textArea;
  private String allPath;
  private String nowPath;
  private digraph gDigraph;
  private Thread2 tmp;
  private boolean flag = true;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          randomUI frame = new randomUI();
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
  public randomUI() {
    // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 513, 486);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JButton stop = new JButton("\u505C\u6B62");
    stop.addActionListener(new ButtonClickListener());
    stop.setActionCommand("stop");
    stop.setBounds(190, 362, 130, 27);
    contentPane.add(stop);

    textArea = new JTextArea();
    textArea.setLineWrap(true);
    textArea.setBounds(88, 50, 328, 160);
    contentPane.add(textArea);
    textArea.setColumns(10);
  }

  public void setPath(String s) {
    this.allPath = s;
  }

  public void setDigraph(digraph G) {
    this.gDigraph = G;
  }

  public void showPath() {
    textArea.setText(allPath);
  }

  public void startRandom() {
    tmp = new Thread2("tmp");
    tmp.start();
  }

  private class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      if (command.equals("stop")) {
        // œﬂ≥Ã÷’÷π
        flag = false;
        String nowPathTmp = nowPath.trim();
        graphFunction f = new graphFunction();
        String dotStr = f.getGraphDot(gDigraph, nowPathTmp);
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotStr);
        gv.add(gv.end_graph());
        gv.increaseDpi();
        String type = "jpg";
        String repesentationType = "dot";
        File out = new File("e:\\path2." + type); // Windows
        if (out.exists()) {
          gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out);
        } else {
          System.out.print("ø’");
        }
        picOnly picWin = new picOnly();
        picWin.setPicPath("e:\\path2.jpg");
        picWin.loadPic();
        picWin.setVisible(true);

      }
    }
  }

  class Thread2 extends Thread {
    private String name;
    private boolean stopMe = true;

    public Thread2(String name) {
      this.name = name;
    }

    public void stopMe() {
      this.stopMe = false;
    }

    @Override
    public void run() {
      String[] words = allPath.split(" ");
      nowPath = new String();
      for (String word : words) {
        nowPath += word;
        nowPath += " ";
        textArea.setText(nowPath);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (!flag) {
          break;
        }
      }
      if (flag) {
        graphFunction f = new graphFunction();
        String dotStr = f.getGraphDot(gDigraph, allPath);
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotStr);
        gv.add(gv.end_graph());
        gv.increaseDpi();
        String type = "jpg";
        String repesentationType = "dot";
        File out = new File("e:\\path2." + type); // Windows
        if (out.exists()) {
          gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out);
        } else {
          System.out.print("ø’");
        }
        picOnly picWin = new picOnly();
        picWin.setPicPath("e:\\path2.jpg");
        picWin.loadPic();
        picWin.setVisible(true);
      }
    }
  }
}
