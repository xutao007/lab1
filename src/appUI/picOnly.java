package appUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

public class picOnly extends JFrame {

	private JPanel contentPane;
	private String picPath;
	private ImagePanel panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					picOnly frame = new picOnly();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public picOnly() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new ImagePanel();
		//panel.setBounds(25, 22, 384, 450);
		contentPane.add(panel);
	}
	
	public void setPicPath(String s){
		this.picPath = s;
	}
	
	public void loadPic(){
		File file = null;
		file = new File(picPath);
		//��ʾͼƬ
		Image srcImg;
		try {
			srcImg = ImageIO.read(file);
<<<<<<< HEAD
			int height = 1000;
			int width = srcImg.getWidth(null) * 1000 / height;
=======
			int height = 580;
			int width = srcImg.getWidth(null) * 580 / height;
>>>>>>> origin/1150310722
			Image imageTemp = srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			panel.setBounds(5, 5, width, height);
			panel.loadPhoto(imageTemp);
			setBounds(100, 100, width + 30, height + 50);
			//panel.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class ImagePanel extends JPanel {
		private Image image;
		        //��ʼ��ʱ�����ص�ͼƬ1.jpg
		public ImagePanel(){
//			try{
//				image=ImageIO.read(new File("E:/out.jpg"));
//		   }catch(IOException e){e.getStackTrace();}
		}
		        //ʵ��ͼƬ�ĸ���
		public void loadPhoto(Image img){
		    image=img;
		    repaint();
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(image==null) return;
			int imageWidth=image.getWidth(this);
			int imageHeight=image.getHeight(this);
			//��ͼƬ�������Ͻ�
			g.drawImage(image, 0, 0, null);
		}
	} 
	
	private class ButtonClickListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	        String command = e.getActionCommand();  
	        if( command.equals( "export" ))  {
	        	
	        }    
	    }     
	}
}
