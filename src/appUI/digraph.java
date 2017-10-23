package appUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
}
