package appUI;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class graphFunctionTest {
  private graphFunction f = new graphFunction();
  private digraph G; 
  @Before
  public void setUp() throws Exception {
    G = f.createDirectedGraph("E:\\test.txt");
  }
  
  @Test
  public void testGenerateNewText1() {
    String s = f.generateNewText(G, "to");
    assertEquals("to",s);
  }
  @Test
  public void testGenerateNewText2() {
    String s = f.generateNewText(G, "to strange");
    assertEquals("to explore strange",s);
  }
  @Test
  public void testGenerateNewText3() {
    String s = f.generateNewText(G, "nulltest nulltestt");
    assertEquals("nulltest nulltestt",s);
  }
  @Test
  public void testGenerateNewText4() {
    String s = f.generateNewText(G, "to strange worlds");
    assertEquals("to explore strange new worlds",s);
  }
  
  @Test
  public void testQueryBridgeWords1() {
    String s = f.queryBridgeWords(G, "to", "strange");
    assertEquals("The bridge words from to to strange is: explore",s);
  }
  @Test
  public void testQueryBridgeWords2() {
    String s = f.queryBridgeWords(G, "new", "null");
    assertEquals("No null in the graph!",s);
  }
  @Test
  public void testQueryBridgeWords3() {
    String s = f.queryBridgeWords(G, "null","new");
    assertEquals("No null in the graph!",s);
  }
  @Test
  public void testQueryBridgeWords4() {
    String s = f.queryBridgeWords(G, "nulltest1", "nulltest2");
    assertEquals("No nulltest1 or nulltest2 in the graph!",s);
  }
  @Test
  public void testQueryBridgeWords5() {
    String s = f.queryBridgeWords(null, "to", "out");
    assertEquals("No to or out in the graph!",s);
  }
}

