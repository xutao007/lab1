package appUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class digraph {
  private int V; // ����ͼ�Ķ�����
  private int E; // ����ͼ�ı���

  // adj.get(from).get(to) = w;
  // ArrayList��Map(Integer, Integer)�����飬ArrayList�±�Ϊfrom, Map��Ϊto, ֵΪȨ��w
  private ArrayList adj;

  // ����Map�����ַ���������֮���ת��
  private Map wordMap;
  private Map numMap;

  public digraph(digraph S) {
    this.V = S.getV();
    this.E = S.getE();
    this.adj = (ArrayList) S.getAdj().clone();
    this.wordMap = S.getWordMap();
    this.numMap = S.getNumMap();
  }

  public digraph(String s) {

    // ��������ı��ָ�Ϊ���еĴ�
    String[] words = s.split(" ");
    int wordsNum = 0;
    Map<String, Integer> wordMap = new HashMap<>();
    Map<Integer, String> numMap = new HashMap<>();

    // �������еĴʶԷ��ظ��Ĵ���������ת����
    for (String word : words) {
      // System.out.println(word);
      if (!wordMap.containsKey(word)) {
        // System.out.println(wordsNum);
        wordMap.put(word, wordsNum);
        numMap.put(wordsNum, word);
        wordsNum++;
      }
    }

    this.V = wordsNum;
    this.E = 0;
    this.wordMap = wordMap;
    this.numMap = numMap;

    // ���ڽӱ���г�ʼ��
    this.adj = new ArrayList<Map<Integer, Integer>>();
    for (int i = 0; i < this.V; i++) {
      this.adj.add(new HashMap<>());
    }

    // ���ݷָ��Ĵ���������ӱ�
    for (int i = 0; i < words.length - 1; i++) {
      int from = (int) this.wordMap.get(words[i]);
      int to = (int) this.wordMap.get(words[i + 1]);
      addEdge(from, to);
    }
  }

  public int getV() {
    return this.V;
  }

  public int getE() {
    return this.E;
  }

  public void testadj() {
    System.out.println(((Map) this.adj.get(0)).keySet().getClass());
  }

  public void setAdj(ArrayList adj) {
    this.adj = adj;
  }

  public void addEdge(int from, int to) {

    // �ж��Ƿ���ڣ����������Ȩ��Ϊ1�����������Ȩ�ؼ�1
    int w = isExist(from, to) ? (int) ((Map) this.adj.get(from)).get(to) + 1 : 1;
    ((Map) this.adj.get(from)).put(to, w);
    String word1 = (String) this.numMap.get(from);
    String word2 = (String) this.numMap.get(to);
    // System.out.println("��"+word1+"��"+word2+"����ɹ�, Ȩ��" + w);
    this.E++;
  }

  public void deleteEdge(int from, int to) {
    if (isExist(from, to)) {
      ((Map) this.adj.get(from)).put(to, -1);
      // this.E -= (int)((Map)this.adj.get(from)).get(to);
      // ((Map)this.adj.get(from)).remove(to);
      // System.out.println("ɾ���ɹ�");
    } else {
      // System.out.println("��������������");
    }
  }

  public int edgeWeight(int from, int to) {
    return isExist(from, to) ? (int) ((Map) this.adj.get(from)).get(to) : 0;
  }

  public boolean isExist(int from, int to) {
    return ((Map) this.adj.get(from)).containsKey(to);
  }

  public Map getWordMap() {
    return this.wordMap;
  }

  public Map getNumMap() {
    return this.numMap;
  }

  public ArrayList getAdj() {
    return this.adj;
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

  // ��ѯ�ŽӴ�
  public String queryBridgeWords(digraph G, String word1, String word2) {
    if(G == null)
      return "No " + word1 + " or " + word2 + " in the graph!";
    Map wordMap = G.getWordMap();
    Map numMap = G.getNumMap();
    ArrayList<String> bridges = new ArrayList<>();

    if (wordMap.get(word1) == null && wordMap.get(word2) == null)
      return "No " + word1 + " or " + word2 + " in the graph!";
    if (wordMap.get(word1) == null)
      return "No " + word1 + " in the graph!";
    if (wordMap.get(word2) == null)
      return "No " + word2 + " in the graph!";
    
    int word1Code = (int) wordMap.get(word1);
    int word2Code = (int) wordMap.get(word2);
    ArrayList adj = G.getAdj();

    for (Object bridge : ((Map) adj.get(word1Code)).keySet()) {
       if (G.isExist((int) bridge, word2Code)) {
         bridges.add((String) numMap.get(bridge));
       }
    }

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
    inputText = cleanText(inputText);
    String[] words = inputText.split(" ");
    String res = new String();
    if (words.length == 1){
      res += words[0];
      return res;
    }
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
        if (used[j] == 0)
          if(G.isExist(u, j) == true)
            if (dist[u] + G.edgeWeight(u, j) < dist[j]) {
              dist[j] = dist[u] + G.edgeWeight(u, j);
              pre[j] = u;
            }
      }
    }
    if (dist[to] < maxn) {
      int t = pre[to];
      int[] pave = new int[V];
      int x = 0;
      while (t != from) {
        pave[x++] = t;
        t = pre[t];
      }
      path += word1; // ·�����
      for (int i = x - 1; i >= 0; i--) {
        path += (" -> " + numMap.get(pave[i]));
      }
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
      if (index == 0) {
        System.out.println(path);
        // G.setAdj(AdjTmp);
        // G= new digraph(Gcopy);
        return path;
      }
      int next = branchs[r.nextInt(index)];
      path += (" " + numMap.get(next));
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
  
  public String myFileReader(String fileName) {

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
}
