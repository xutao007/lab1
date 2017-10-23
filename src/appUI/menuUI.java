package appUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class menuUI extends JFrame {
  private digraph gDigraph;
  private JPanel contentPane;
  private String fileName;
  private ImagePanel panel;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          menuUI frame = new menuUI();
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
  public menuUI() {
    setTitle("\u6709\u5411\u56FE\u5C0F\u73A9\u610F");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1000, 900);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    panel = new ImagePanel();
    panel.setBounds(60, 49, 700, 750);
    contentPane.add(panel);

    JButton exportButton = new JButton("\u5BFC\u51FA\u6709\u5411\u56FE");
    exportButton.setActionCommand("export");
    exportButton.setBounds(850, 60, 122, 23);
    contentPane.add(exportButton);

    JButton queryButton = new JButton("\u67E5\u8BE2\u6865\u63A5\u8BCD");
    queryButton.setActionCommand("query");
    queryButton.setBounds(850, 130, 122, 23);
    contentPane.add(queryButton);

    JButton newstrButton = new JButton("\u751F\u6210\u65B0\u6587\u672C");
    newstrButton.setActionCommand("newStr");
    newstrButton.setBounds(850, 197, 122, 23);
    contentPane.add(newstrButton);

    JButton calcButton = new JButton("\u8BA1\u7B97\u8DEF\u5F84");
    calcButton.setActionCommand("calc");
    calcButton.setBounds(850, 264, 122, 23);
    contentPane.add(calcButton);

    JButton walkButton = new JButton("\u968F\u673A\u6E38\u8D70");
    walkButton.setActionCommand("walk");
    walkButton.setBounds(850, 331, 122, 23);
    contentPane.add(walkButton);

    exportButton.setActionCommand("export");
    queryButton.setActionCommand("query");
    newstrButton.setActionCommand("newStr");
    calcButton.setActionCommand("calc");
    walkButton.setActionCommand("walk");
    exportButton.addActionListener(new ButtonClickListener());
    queryButton.addActionListener(new ButtonClickListener());
    newstrButton.addActionListener(new ButtonClickListener());
    calcButton.addActionListener(new ButtonClickListener());
    walkButton.addActionListener(new ButtonClickListener());

  }

  public void setFileName(String filename) {
    this.fileName = filename;
    System.out.println("in function" + this.fileName + filename);
  }

  public void setDigraph() {
    graphFunction f = new graphFunction();
    this.gDigraph = f.createDirectedGraph(fileName);
  }

  public void loadPic() {
    // 通过调用grapgFunction生成图和dot文本
    // System.out.println("in menuUI" + fileName);
    graphFunction f = new graphFunction();
    digraph G = f.createDirectedGraph(fileName);
    // String dotStr = f.getGraphDot(G);
    // String dotStrTest = new String("a1 -> b3;b2 -> a3;a3 -> a0;a3 -> end;");
    // System.out.println(dotStr);
    f.showDirectedGrapg(G);
    // 产生图
    // GraphViz gv = new GraphViz();
    // gv.addln(gv.start_graph());
    // gv.add(dotStr);
    // gv.add(gv.end_graph());
    // gv.increaseDpi();
    // String type = "jpg";
    // String repesentationType= "dot";
    // File out = new File("e:\\out." + type); // Windows
    // if(out.exists()){
    // gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType),
    // out);
    // }
    // else{
    // System.out.print("空");
    // }

    // 加载图
    // Image image = new ImageIcon("e:/out.jpg").getImage();
    renewPic();
  }

  public void renewPic() {
    File file = null;
    file = new File("E:/out.jpg");
    // 显示图片
    Image srcImg;
    try {
      srcImg = ImageIO.read(file);
      int height = 750;
      // double width = srcImg.getWidth(null) * 750.0 / srcImg.getHeight(null);

      Image imageTemp = srcImg.getScaledInstance(700, height, Image.SCALE_SMOOTH);
      panel.loadPhoto(imageTemp);
      // panel.repaint();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  class ImagePanel extends JPanel {
    private Image image;

    // 初始化时，加载的图片1.jpg
    public ImagePanel() {
      // try{
      // image=ImageIO.read(new File("E:/out.jpg"));
      // }catch(IOException e){e.getStackTrace();}
    }

    // 实现图片的更新
    public void loadPhoto(Image img) {
      image = img;
      repaint();
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (image == null)
        return;
      int imageWidth = image.getWidth(this);
      int imageHeight = image.getHeight(this);
      // 将图片画在左上角
      g.drawImage(image, 0, 0, null);
    }
  }

  private class ButtonClickListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      if (command.equals("file")) {
      } else if (command.equals("export")) {
        exportUI exportUIWindow = new exportUI();
        exportUIWindow.setVisible(true);
      } else if (command.equals("query")) {
        queryUI queryUIWindow = new queryUI();
        queryUIWindow.setDigraph(gDigraph);
        queryUIWindow.setVisible(true);
      } else if (command.equals("newStr")) {
        newstrUI newstrUIWindow = new newstrUI();
        newstrUIWindow.setDigraph(gDigraph);
        newstrUIWindow.setVisible(true);
      } else if (command.equals("calc")) {
        calcUI calcUIWindow = new calcUI();
        calcUIWindow.setDigraph(gDigraph);
        calcUIWindow.setVisible(true);
      } else {
        graphFunction f = new graphFunction();
        // digraph Gcopy = new digraph(gDigraph);
        String pathStr = f.randomWalk(new digraph(gDigraph));
        String dotStr = f.getGraphDot(gDigraph, pathStr);
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
          System.out.print("空");
        }
        randomUI randomWin = new randomUI();
        randomWin.setTitle("随机游走");
        randomWin.setPath(pathStr);
        // picWin.setPicPath("e:\\path2.jpg");
        // picWin.loadPic();
        randomWin.showPath();
        randomWin.setDigraph(gDigraph);
        randomWin.setVisible(true);
        randomWin.startRandom();
      }
    }
  }
}
