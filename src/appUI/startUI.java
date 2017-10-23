package appUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class startUI {

  private JFrame frame;
  private JTextField textField;
  private String filename;
  private JLabel warningMsg;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          startUI window = new startUI();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public startUI() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setTitle("\u6709\u5411\u56FE\u5C0F\u73A9\u610F");
    frame.setBounds(100, 100, 543, 348);
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    JLabel label = new JLabel("\u6587\u4EF6\uFF1A");
    label.setBounds(65, 156, 46, 15);
    frame.getContentPane().add(label);

    textField = new JTextField();
    textField.setColumns(10);
    textField.setBounds(121, 153, 227, 21);
    frame.getContentPane().add(textField);

    JButton fileChoose = new JButton("\u9009\u62E9\u6587\u4EF6");
    fileChoose.setActionCommand("filechoose");
    fileChoose.setBounds(373, 152, 93, 23);
    frame.getContentPane().add(fileChoose);

    JButton start = new JButton("\u786E\u5B9A");
    start.setActionCommand("start");
    start.setBounds(204, 246, 93, 23);
    frame.getContentPane().add(start);

    fileChoose.setActionCommand("filechoose");
    fileChoose.addActionListener(new FileChooser());

    start.setActionCommand("start");

    warningMsg = new JLabel("");
    warningMsg.setBounds(109, 211, 295, 15);
    frame.getContentPane().add(warningMsg);
    start.addActionListener(new ButtonClickListener());

  }

  private class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();

      if (command.equals("start")) {
        String filenameTemp = textField.getText();
        File file = new File(filenameTemp);
        // System.out.println("0" + filenameTemp);
        if (file.isFile() && filenameTemp.endsWith("txt") || filenameTemp.endsWith("test")) {

          filename = textField.getText();
          // System.out.println("1" + filename);
          String filenameCopy = filename;
          // System.out.println("2" + filenameCopy);
          menuUI menuUIWindow = new menuUI();
          menuUIWindow.setFileName(filenameCopy);
          menuUIWindow.setDigraph();
          menuUIWindow.setVisible(true);
          menuUIWindow.loadPic();

          frame.setVisible(false);
          // graphFunction f = new graphFunction();
          // menuUIWindow.setDigraph(f.createDirectedGraph(filenameTemp));

        } else {
          // 请选择一个txt文件
          warningMsg.setText("请选择一个txt文件");
          // System.out.println(filenameTemp.substring(filenameTemp.lastIndexOf(".")+1));
        }
      }
    }
  }

  public class FileChooser extends JFrame implements ActionListener {
    // JButton open = null;
    // public FileChooser(){
    // open=new JButton("open");
    // this.add(open);
    // this.setBounds(400, 200, 100, 100);
    // this.setVisible(true);
    // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // open.addActionListener(this);
    // }
    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      JFileChooser jfc = new JFileChooser();
      jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
      jfc.showDialog(new JLabel(), "选择");
      File file = jfc.getSelectedFile();
      if (file.isDirectory()) {
        System.out.println("文件夹:" + file.getAbsolutePath());
      } else if (file.isFile()) {
        System.out.println("文件:" + file.getAbsolutePath());
      }
      // System.out.println(jfc.getSelectedFile().getName());
      String filename = file.getAbsolutePath();
      textField.setText(filename);
    }

  }

}
