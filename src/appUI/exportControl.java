package appUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class exportControl {

  // 生成dot文本，用于生成可视化有向图
  public String getGraphDot(digraph G) {
    ArrayList adj = G.getAdj();
    String dotStr = new String();

    // dotStr += "digraph G {\n";
    int v = G.getV();
    Map<Integer, String> numMap = G.getNumMap();
    for (int i = 0; i < v; i++) {
      String from = (String) numMap.get(i);
      for (Object j : ((Map) adj.get(i)).keySet()) {
        String to = (String) numMap.get((int) j);

        dotStr += from;
        dotStr += " -> ";
        dotStr += to;
        dotStr += ";\n";
        int w = G.edgeWeight(i, (int) j);

        // dotStr += " [label=\"";
        // dotStr += w;
        // dotStr += "\"];\n";
      }
    }
    // dotStr += "}";
    // System.out.println(dotStr);
    return dotStr;
  }
  
  // 用图和字符串生成文本，用于看路径
  public String getGraphDot(digraph G, String s) {
    String dotSrc = getGraphDot(G);
    String[] pathWords = s.split(" ");
    int len = pathWords.length;
    Random random = new Random();
    String[] colors = { "red", "green", "blue", "pink", "yellow", "orange" };
    String color = colors[random.nextInt(6)];
    String appendStr = new String();
    for (int i = 0; i < len - 1; i++) {
      String word1 = pathWords[i];
      String word2 = pathWords[i + 1];
      String tmp = new String();
      tmp += word1;
      tmp += " -> ";
      tmp += word2;
      tmp += " [color=\"";
      tmp += color;
      //
      tmp += "\" label=\"";
      tmp += (i + 1);
      //
      tmp += "\"];\n";
      appendStr += tmp;
    }
    dotSrc += "\n";
    dotSrc += appendStr;
    return dotSrc;
  }
  
  // 执行生成图
  public void showDirectedGrapg(digraph G) {
    // 产生图
    String dotStr = getGraphDot(G);
    GraphViz gv = new GraphViz();
    gv.addln(gv.start_graph());
    gv.add(dotStr);
    gv.add(gv.end_graph());
    gv.increaseDpi();
    String type = "jpg";
    String repesentationType = "dot";
    File out = new File("e:\\out." + type); // Windows
    if (out.exists()) {
      gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out);
    } else {
      System.out.print("空");
    }
  }
}
