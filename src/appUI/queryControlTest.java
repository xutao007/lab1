package appUI;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class queryControlTest {
  private queryControl queryCont = new queryControl();
  private startControl startCont = new startControl();
  private digraph G; 
  private digraph G1;
  
  @Before
  public void setUp() throws Exception {
    G = startCont.createDirectedGraph("E:\\test.txt");
  }

  @Test
  public void testQueryBridgeWords1() {
    String s = queryCont.queryBridgeWords(G, "to", "strange");
    assertEquals("The bridge words from to to strange is: explore",s);
  }
  
  @Test
  public void testQueryBridgeWords2() {
    String s = queryCont.queryBridgeWords(G, "new", "null");
    assertEquals("No null in the graph!",s);
  }
  
  @Test
  public void testQueryBridgeWords3() {
    String s = queryCont.queryBridgeWords(G, "null","new");
    assertEquals("No null in the graph!",s);
  }
  
  @Test
  public void testQueryBridgeWords4() {
    String s = queryCont.queryBridgeWords(G, "nulltest1", "nulltest2");
    assertEquals("No nulltest1 or nulltest2 in the graph!",s);
  }
  
  @Test
  public void testQueryBridgeWords5() {
    String s = queryCont.queryBridgeWords(null, "to", "out");
    assertEquals("No to or out in the graph!",s);
  }
  
}
