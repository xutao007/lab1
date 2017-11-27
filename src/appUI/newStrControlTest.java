package appUI;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class newStrControlTest {
  private newStrControl newStrCont = new newStrControl();
  private startControl startCont = new startControl();
  private digraph G; 
  
  @Before
  public void setUp() throws Exception {
    G = startCont.createDirectedGraph("E:\\test.txt");
  }
  
  @Test
  public void testGenerateNewText1() {
    String s = newStrCont.generateNewText(G, "to");
    assertEquals("to",s);
  }
  
  @Test
  public void testGenerateNewText2() {
    String s = newStrCont.generateNewText(G, "to strange");
    assertEquals("to explore strange",s);
  }
  
  @Test
  public void testGenerateNewText3() {
    String s = newStrCont.generateNewText(G, "nulltest nulltestt");
    assertEquals("nulltest nulltestt",s);
  }
  
  @Test
  public void testGenerateNewText4() {
    String s = newStrCont.generateNewText(G, "to strange worlds");
    assertEquals("to explore strange new worlds",s);
  }

}
