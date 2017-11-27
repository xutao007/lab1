package appUI;

import org.junit.Before;

public class calcControlTest {
  private calcControl calcCont = new calcControl();
  private startControl startCont = new startControl();
  private digraph G; 
  
  @Before
  public void setUp() throws Exception {
    G = startCont.createDirectedGraph("E:\\test.txt");
  }

}
