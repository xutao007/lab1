package appUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class digraph {
  private int V; // 有向图的顶点数
  private int E; // 有向图的边数

  // adj.get(from).get(to) = w;
  // ArrayList是Map(Integer, Integer)的数组，ArrayList下标为from, Map键为to, 值为权重w
  private ArrayList adj;

  // 两个Map用于字符串和整数之间的转换
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

    // 由输入的文本分割为所有的词
    String[] words = s.split(" ");
    int wordsNum = 0;
    Map<String, Integer> wordMap = new HashMap<>();
    Map<Integer, String> numMap = new HashMap<>();

    // 根据所有的词对非重复的词生成两个转换表
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

    // 对邻接表进行初始化
    this.adj = new ArrayList<Map<Integer, Integer>>();
    for (int i = 0; i < this.V; i++) {
      this.adj.add(new HashMap<>());
    }

    // 根据分割后的词来依次添加边
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

    // 判断是否存在，如果不存在权重为1，如果存在则权重加1
    int w = isExist(from, to) ? (int) ((Map) this.adj.get(from)).get(to) + 1 : 1;
    ((Map) this.adj.get(from)).put(to, w);
    String word1 = (String) this.numMap.get(from);
    String word2 = (String) this.numMap.get(to);
    // System.out.println("从"+word1+"到"+word2+"加入成功, 权重" + w);
    this.E++;
  }

  public void deleteEdge(int from, int to) {
    if (isExist(from, to)) {
      ((Map) this.adj.get(from)).put(to, -1);
      // this.E -= (int)((Map)this.adj.get(from)).get(to);
      // ((Map)this.adj.get(from)).remove(to);
      // System.out.println("删除成功");
    } else {
      // System.out.println("不存在这两条边");
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
}
