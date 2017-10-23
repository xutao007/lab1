package appUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Window.Type;

public class exportUI extends JFrame {

  private JPanel contentPane;
  private JTextField path;
  private JTextField picName;
  private JLabel warningMsg;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          exportUI frame = new exportUI();
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
  public exportUI() {
    setTitle("\u5BFC\u51FA\u6709\u5411\u56FE");
    // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 509, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    path = new JTextField();
    path.setBounds(143, 84, 196, 21);
    contentPane.add(path);
    path.setColumns(10);

    JLabel label = new JLabel("\u9009\u62E9\u76EE\u5F55\uFF1A");
    label.setBounds(48, 87, 86, 15);
    contentPane.add(label);

    JButton dicChoose = new JButton("\u9009\u62E9\u8DEF\u5F84");
    dicChoose.setBounds(368, 83, 86, 23);
    contentPane.add(dicChoose);
    dicChoose.addActionListener(new FileChooser());

    JLabel label_1 = new JLabel("\u5BFC\u51FA\u56FE\u7247\u540D\u79F0\uFF1A");
    label_1.setBounds(48, 118, 98, 15);
    contentPane.add(label_1);

    picName = new JTextField();
    picName.setBounds(143, 115, 196, 21);
    contentPane.add(picName);
    picName.setColumns(10);

    warningMsg = new JLabel("");
    warningMsg.setBounds(143, 157, 165, 26);
    contentPane.add(warningMsg);

    JButton finish = new JButton("\u786E\u5B9A");
    finish.setBounds(197, 209, 93, 23);
    contentPane.add(finish);
    finish.addActionListener(new ButtonClickListener());
    finish.setActionCommand("ok");
  }

  private class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();

      if (command.equals("ok")) {
        String filepathTemp = path.getText();
        String picnameTemp = picName.getText();
        if (!picnameTemp.isEmpty()) {
          // 出口。。。执行代码
          String exportFileName = filepathTemp + "\\" + picnameTemp + ".jpg";
          System.out.println(exportFileName);
          File exportFile = new File(exportFileName);
          File source = new File("E:\\out.jpg");
          try {
            Files.copy(source.toPath(), exportFile.toPath());
          } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
          warningMsg.setText("导出成功");
        } else {
          warningMsg.setText("请输入导出图片名称");
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
      jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      jfc.showDialog(new JLabel(), "选择");
      File file = jfc.getSelectedFile();
      if (file.isDirectory()) {
        System.out.println("文件夹:" + file.getAbsolutePath());
      } else if (file.isFile()) {
        System.out.println("文件:" + file.getAbsolutePath());
      }
      // System.out.println(jfc.getSelectedFile().getName());
      String filename = file.getAbsolutePath();
      path.setText(filename);
    }

  }
}
