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

  // ��������ͼ
  public digraph createDirectedGraph(String filename) {
    String paragraph = myFileReader(filename);
    digraph res = new digraph(paragraph);
    return res;
  }

  // ����dot�ı����������ɿ��ӻ�����ͼ
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

  // ��ͼ���ַ��������ı������ڿ�·��
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

  // ִ������ͼ
  public void showDirectedGrapg(digraph G) {
    // ����ͼ
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
      System.out.print("��");
    }
  }

  // ���ļ��ж�ȡ�ַ���������
  public String myFileReader(String fileName) {

    // Scanner sc = new Scanner(System.in);
    // System.out.print("�������ļ�λ�ã�");
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
      if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
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
        System.out.println("�Ҳ���ָ�����ļ�");
      }
    } catch (Exception e) {
      System.out.println("��ȡ�ļ����ݳ���");
      e.printStackTrace();
    }
    return cleanText(res);
  }

  // ��ѯ�ŽӴ�
  public String queryBridgeWords(digraph G, String word1, String word2) {

    Map wordMap = G.getWordMap();
    Map numMap = G.getNumMap();

    // ���ж�
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

    // bridges�洢bridge�ַ���
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
    // �������һ����
    // Random random = new Random();
    // String res = (String)bridges.get(random.nextInt(bridges.size()));
    // System.out.println(res);
    // return "The bridge";
  }

  // ���������ŽӴ���ϣ����û�����
  public ArrayList<String> getBridges(digraph G, String word1, String word2) {

    ArrayList<String> bridges = new ArrayList<>();

    // �Ȼ�ȡ�ַ���������Ӧ��
    Map wordMap = G.getWordMap();
    Map numMap = G.getNumMap();

    if (wordMap.get(word1) == null || wordMap.get(word2) == null) {
      return bridges;
    } else {
      int word1Code = (int) wordMap.get(word1);
      int word2Code = (int) wordMap.get(word2);
      ArrayList adj = G.getAdj();

      // ͨ��word1�õ�һ��map,���Ϊ�ɴﵽ�ĵ㣬����һ������
      // ͨ�����ü��������еĵ��Ƿ��word2���������õ��Ƿ�Ϊ��
      // ������ţ�ת��ΪString�����bridges��
      for (Object bridge : ((Map) adj.get(word1Code)).keySet()) {
        if (G.isExist((int) bridge, word2Code)) {
          bridges.add((String) numMap.get(bridge));
        }
      }
    }
    return bridges;
  }

  // �õ�һ��������ŽӴ�
  public String getRandomBridge(digraph G, String word1, String word2) {
    ArrayList<String> bridges = getBridges(G, word1, word2);
    String res = "";
    if (bridges.size() != 0) {
      Random random = new Random();
      res = (String) bridges.get(random.nextInt(bridges.size()));
    }
    return res;
  }

  // �����ŽӴ��������ı�
  public String generateNewText(digraph G, String inputText) {
    // ��������
    inputText = cleanText(inputText);

    // �ָ��
    String[] words = inputText.split(" ");

    String res = new String();
    // ����������bridge����
    for (int i = 0; i < words.length - 1; i++) {
      String word1 = words[i];
      String word2 = words[i + 1];
      String bridge = getRandomBridge(G, word1, word2);
      // res���ɣ����bridge��Ϊ�գ������һ�����ʵļ���
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

  // �ַ���������ȥ����Ӣ����ĸ�Ͷ���Ŀո�س���
  public String cleanText(String s) {

    String res = new String();

    int flag = 1;
    for (int i = 0; i < s.length(); i++) {
      // �����д�ͱ��Сд
      if (s.charAt(i) <= 'Z' && s.charAt(i) >= 'A') {
        res += ((char) (s.charAt(i) + 32));
        flag = 1;
      }
      // ���Сд����
      else if (s.charAt(i) <= 'z' && s.charAt(i) >= 'a') {
        res += (s.charAt(i));
        flag = 1;
      }

      // ���flagΪ1����һ�β��ǿո�
      else if (flag == 1) {
        res += ' ';
        flag = 0;
      } else {
      }
    }
    // ȥ��ͷβ�Ķ���ո�
    res = res.trim();
    return res;
  }

  // �����������ʼ�����·������ӡ
  public String calcShortestPath(digraph G, String word1, String word2) {
    Map<String, Integer> wordMap = new HashMap<>();
    Map<Integer, String> numMap = new HashMap<>();
    wordMap = G.getWordMap();
    numMap = G.getNumMap();
    String path = "";
    // �ж� ���� �Ƿ�Ϸ�
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
      path += word1; // ·�����
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

  // ��ѡ����4
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

  // �������
  public String randomWalk(digraph G) {
    Map<String, Integer> wordMap = new HashMap<>();
    Map<Integer, String> numMap = new HashMap<>();
    // digraph Gcopy = new digraph(G);
    int V = G.getV();
    // ��ʱ�ڽӱ����ڴ洢ʹ�ù��ı�
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
    // ���ѡȡ���
    int start = r.nextInt(V);
    // System.out.print(numMap.get(start));
    path += numMap.get(start);
    while (true) {
      int[] branchs = new int[V];
      int index = 0;
      // ����һ������ѡ��ķ�֧ȫ�����浽������
      for (int i = 0; i < V; i++) {
        if (i == start)
          continue;
        if (G.isExist(start, i))
          branchs[index++] = i;
      }
      // û�г��ߣ�����
      if (index == 0) {
        System.out.println(path);
        // G.setAdj(AdjTmp);
        // G= new digraph(Gcopy);
        return path;
      }
      // ���ѡ��һ������
      int next = branchs[r.nextInt(index)];
      // System.out.print(" "+numMap.get(next));
      path += (" " + numMap.get(next));
      // ���û���ظ���������ǣ�������㣬��շ�֧����
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
