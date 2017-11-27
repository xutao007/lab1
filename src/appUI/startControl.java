package appUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class startControl {
  //生成有向图
  public digraph createDirectedGraph(String filename) {
   String paragraph = myFileReader(filename);
   digraph res = new digraph(paragraph);
   return res;
  }
  
  private String myFileReader(String fileName) {

    String res = new String();
    try {
      File file = new File(fileName);
      if (file.isFile() && file.exists()) { // 判断文件是否存在
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
        System.out.println("找不到指定的文件");
      }
    } catch (Exception e) {
      System.out.println("读取文件内容出错");
      e.printStackTrace();
    }
    return cleanText(res);
  }
  
  // 字符串的清理，去除非英文字母和多余的空格回车等
  private String cleanText(String s) {

    String res = new String();

    int flag = 1;
    for (int i = 0; i < s.length(); i++) {
      // 如果大写就变成小写
      if (s.charAt(i) <= 'Z' && s.charAt(i) >= 'A') {
        res += ((char) (s.charAt(i) + 32));
        flag = 1;
      }
      // 如果小写不变
      else if (s.charAt(i) <= 'z' && s.charAt(i) >= 'a') {
        res += (s.charAt(i));
        flag = 1;
      }

      // 如果flag为1即上一次不是空格
      else if (flag == 1) {
        res += ' ';
        flag = 0;
      } else {
      }
    }
    // 去除头尾的多余空格
    res = res.trim();
    return res;
  }
  
}
