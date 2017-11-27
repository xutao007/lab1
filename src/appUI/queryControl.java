package appUI;

public class queryControl {
  // ≤È—Ø«≈Ω”¥ 
  public String queryBridgeWords(digraph G, String word1, String word2) {
    if(G == null)
      return "No " + word1 + " or " + word2 + " in the graph!";
    String res = G.queryBridgeWords(G, word1, word2);
    System.out.println(res);
    return res;
  }
}
