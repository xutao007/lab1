package appUI;

public class graphvizDot {
    private String graphDot;
    
    graphvizDot(){
      this.setContent("");
    }
    
    public void addToContent(String text){
       this.setContent(this.getContent()+text);
    }
    
    public void clone(graphvizDot g){
      this.setContent(g.getContent());
    }
    
    public String getContent(){
      return graphDot;
    }
    
    public void setContent(String c){
      this.graphDot = c;
    }
}
