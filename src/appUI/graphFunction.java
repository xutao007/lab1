package appUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class graphFunction {
  public static void main(String[] args) {
    graphFunction f = new graphFunction();
    digraph G = f.createDirectedGraph("C:\\Users\\xutao\\Documents\\test.txt");
    String word1 = "to";
    String word2 = "civilizations";
    String s = f.calcShortestPath(G, word1, word2);
    System.out.println(s);
    System.out.println(f.getGraphDot(G, s));
    String t = f.randomWalk(G);
    System.out.println(t);
  }

  // 生成有向图
  public digraph createDirectedGraph(String filename) {
    String paragraph = myFileReader(filename);
    digraph res = new digraph(paragraph);
    return res;
  }

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

  // 从文件中读取字符串并整理
  public String myFileReader(String fileName) {

    // Scanner sc = new Scanner(System.in);
    // System.out.print("请输入文件位置：");
    // String fileName = sc.nextLine();
    // //C:\Users\xutao\workspace\helloworld\src\helloworld\test
    // sc.close();
    // String fileName = new
    // String("C:\\Users\\xutao\\workspace\\helloworld\\src\\helloworld\\digragh1");
    // fileName = new
    // String("C:\\Users\\xutao\\workspace\\helloworld\\src\\helloworld\\digragh1");
    String res = new String();
    try {
      File file = new File(fileName);
      if (file.isFile() && file.exists()) { // 判断文件是否存在
        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = new String();
        while ((lineTxt = bufferedReader.readLine()) != null) {
          // System.out.println(lineTxt);
          res += lineTxt;
          res += ' ';
        }
        read.close();
        // System.out.println(res);
      } else {
        System.out.println("找不到指定的文件");
      }
    } catch (Exception e) {
      System.out.println("读取文件内容出错");
      e.printStackTrace();
    }
    return cleanText(res);
  }

  // 查询桥接词
  public String queryBridgeWords(digraph G, String word1, String word2) {

    Map wordMap = G.getWordMap();
    Map numMap = G.getNumMap();

    // 做判断
    if (wordMap.get(word1) == null || wordMap.get(word2) == null) {
      if (wordMap.get(word1) == null && wordMap.get(word2) == null) {
        return "No " + word1 + " or " + word2 + " in the graph!";
      } else {
        if (wordMap.get(word1) == null) {
          return "No " + word1 + " in the graph!";
        } else {
          return "No " + word2 + " in the graph!";
        }
      }
    }

    // bridges存储bridge字符串
    ArrayList<String> bridges = getBridges(G, word1, word2);

    if (bridges.size() == 0) {
      String res = "No bridge words from " + word1 + " to " + word2 + "!";
      return res;
    } else {
      String res = "The bridge words from " + word1 + " to " + word2 + " is: ";
      int len = bridges.size();
      if (len == 1) {
        res += bridges.get(0);
      } else {
        for (int i = 0; i < len - 1; i++) {
          res += (bridges.get(i) + ", ");
        }
        res += ("and " + bridges.get(len - 1));
      }
      return res;
    }
    // 随机生成一个桥
    // Random random = new Random();
    // String res = (String)bridges.get(random.nextInt(bridges.size()));
    // System.out.println(res);
    // return "The bridge";
  }

  // 返回所有桥接词组合，如果没有则空
  public ArrayList<String> getBridges(digraph G, String word1, String word2) {

    ArrayList<String> bridges = new ArrayList<>();

    // 先获取字符串和数对应表
    Map wordMap = G.getWordMap();
    Map numMap = G.getNumMap();

    if (wordMap.get(word1) == null || wordMap.get(word2) == null) {
      return bridges;
    } else {
      int word1Code = (int) wordMap.get(word1);
      int word2Code = (int) wordMap.get(word2);
      ArrayList adj = G.getAdj();

      // 通过word1得到一个map,其键为可达到的点，生成一个集合
      // 通过检测该集合中所有的点是否和word2相连来检测该点是否为桥
      // 如果是桥，转换为String后加入bridges中
      for (Object bridge : ((Map) adj.get(word1Code)).keySet()) {
        if (G.isExist((int) bridge, word2Code)) {
          bridges.add((String) numMap.get(bridge));
        }
      }
    }
    return bridges;
  }

  // 得到一个随机的桥接词
  public String getRandomBridge(digraph G, String word1, String word2) {
    ArrayList<String> bridges = getBridges(G, word1, word2);
    String res = "";
    if (bridges.size() != 0) {
      Random random = new Random();
      res = (String) bridges.get(random.nextInt(bridges.size()));
    }
    return res;
  }

  // 根据桥接词生成新文本
  public String generateNewText(digraph G, String inputText) {
    // 整理输入
    inputText = cleanText(inputText);

    // 分割单词
    String[] words = inputText.split(" ");

    String res = new String();
    // 遍历并生成bridge单词
    for (int i = 0; i < words.length - 1; i++) {
      String word1 = words[i];
      String word2 = words[i + 1];
      String bridge = getRandomBridge(G, word1, word2);
      // res生成，如果bridge不为空，和最后一个单词的加入
      res += word1;
      res += " ";
      if (bridge != "") {
        res += bridge;
        res += " ";
      }
      if (i == words.length - 2) {
        res += word2;
      }
    }
    return res;
  }

  // 字符串的清理，去除非英文字母和多余的空格回车等
  public String cleanText(String s) {

    String res = new String();

    int flag = 1;
    for (int i = 0; i < s.length(); i++) {
      // 如果大写就变成小写
      if (s.charAt(i) <= 'Z' && s.charAt(i) >= 'A') {
        res += ((char) (s.charAt(i) + 32));
        flag = 1;
      }
      // 如果小写不变
      else if (s.charAt(i) <= 'z' && s.charAt(i) >= 'a') {
        res += (s.charAt(i));
        flag = 1;
      }

      // 如果flag为1即上一次不是空格
      else if (flag == 1) {
        res += ' ';
        flag = 0;
      } else {
      }
    }
    // 去除头尾的多余空格
    res = res.trim();
    return res;
  }

  // 计算两个单词间的最短路径并打印
  public String calcShortestPath(digraph G, String word1, String word2) {
    Map<String, Integer> wordMap = new HashMap<>();
    Map<Integer, String> numMap = new HashMap<>();
    wordMap = G.getWordMap();
    numMap = G.getNumMap();
    String path = "";
    // 判断 输入 是否合法
    if (wordMap.get(word1) == null || wordMap.get(word2) == null) {
      System.out.println(word1 + " or " + word2 + " is not in the graph.");
      return null;
    }

    int from = (int) wordMap.get(word1);
    int to = (int) wordMap.get(word2);
    int V = G.getV();
    int maxn = 999999;
    int[] used = new int[V];
    int[] dist = new int[V];
    int[] pre = new int[V];
    for (int i = 0; i < V; i++) {
      used[i] = 0;
      if (G.isExist(from, i)) {
        dist[i] = G.edgeWeight(from, i);
        pre[i] = from;
      } else {
        dist[i] = maxn;
        pre[i] = -1;
      }
    }
    dist[from] = 0;
    used[from] = 1;
    for (int i = 0; i < V; i++) {
      if (i == from)
        continue;
      int mindist = maxn;
      int u = from;
      for (int j = 0; j < V; j++) {
        if (used[j] == 0 && dist[j] < mindist) {
          u = j;
          mindist = dist[j];
        }
      }
      used[u] = 1;
      for (int j = 0; j < V; j++) {
        if (used[j] == 0 && G.isExist(u, j) == true)
          if (dist[u] + G.edgeWeight(u, j) < dist[j]) {
            dist[j] = dist[u] + G.edgeWeight(u, j);
            pre[j] = u;
          }
      }
    }
    if (dist[to] < maxn) {
      // System.out.println("Length of the shortest path "+word1+" to "+word2+" is
      // "+dist[to]+".");
      int t = pre[to];
      int[] pave = new int[V];
      int x = 0;
      while (t != from) {
        pave[x++] = t;
        t = pre[t];
      }
      // System.out.print(word1);
      path += word1; // 路径起点
      for (int i = x - 1; i >= 0; i--) {
        // System.out.print(" -> " + numMap.get(pave[i]));
        path += (" -> " + numMap.get(pave[i]));
      }
      // System.out.println(" -> " + word2);
      path += (" -> " + word2);
      path = cleanText(path);
      return path;
    } else {
      System.out.println("There is no path from " + word1 + " to " + word2 + ".");
      return null;
    }
  }

  // 可选功能4
  public void calcShortestPath(digraph G, String word1) {
    Map<String, Integer> wordMap = new HashMap<>();
    Map<Integer, String> numMap = new HashMap<>();
    wordMap = G.getWordMap();
    numMap = G.getNumMap();
    for (int i = 0; i < G.getV(); i++) {
      if ((int) wordMap.get(word1) == i)
        continue;
      calcShortestPath(G, word1, numMap.get(i));
    }
  }

  // 随机游走
  public String randomWalk(digraph G) {
    Map<String, Integer> wordMap = new HashMap<>();
    Map<Integer, String> numMap = new HashMap<>();
    // digraph Gcopy = new digraph(G);
    int V = G.getV();
    // 临时邻接表用于存储使用过的边
    ArrayList adjTmp = new ArrayList<Map<Integer, Integer>>();
    ;
    for (int i = 0; i < V; i++) {
      adjTmp.add(new HashMap<>());
    }
    // System.out.print(Gcopy == G);
    // System.out.println(Gcopy.getAdj() == G.getAdj());
    wordMap = G.getWordMap();
    numMap = G.getNumMap();

    String path = "";
    java.util.Random r = new java.util.Random();
    // 随机选取起点
    int start = r.nextInt(V);
    // System.out.print(numMap.get(start));
    path += numMap.get(start);
    while (true) {
      int[] branchs = new int[V];
      int index = 0;
      // 把下一步可能选择的分支全都保存到数组里
      for (int i = 0; i < V; i++) {
        if (i == start)
          continue;
        if (G.isExist(start, i))
          branchs[index++] = i;
      }
      // 没有出边，结束
      if (index == 0) {
        System.out.println(path);
        // G.setAdj(AdjTmp);
        // G= new digraph(Gcopy);
        return path;
      }
      // 随机选择一个出边
      int next = branchs[r.nextInt(index)];
      // System.out.print(" "+numMap.get(next));
      path += (" " + numMap.get(next));
      // 如果没有重复，则做标记，更新起点，清空分支数组
      // if(G.edgeWeight(start, next) >= 0){
      // G.deleteEdge(start, next);
      // start = next;
      // branchs = null;
      // }
      if (!((Map) adjTmp.get(start)).containsKey(next)) {
        ((Map) adjTmp.get(start)).put(next, 1);
        start = next;
        branchs = null;
      } else {
        System.out.println(path);
        // G= new digraph(Gcopy);
        // G.setAdj(AdjTmp);
        return path;
      }
    }
  }
}
