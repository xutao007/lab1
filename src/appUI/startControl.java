package appUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class startControl {
  //��������ͼ
  public digraph createDirectedGraph(String filename) {
   String paragraph = myFileReader(filename);
   digraph res = new digraph(paragraph);
   return res;
  }
  
  private String myFileReader(String fileName) {

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
  
  // �ַ���������ȥ����Ӣ����ĸ�Ͷ���Ŀո�س���
  private String cleanText(String s) {

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
  
}
