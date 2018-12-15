package GUI;

import javax.swing.JFrame;


public class Main 
{
	public static void main(String[] args)
	{
		MyFrame window = new MyFrame("Ariel1.png");
		window.setVisible(true);
		window.setResizable(true);
		window.setSize(window.myImage.getWidth(),window.myImage.getHeight());
		window.getBaselineResizeBehavior();
	//	window.getContentPane().add(new DrawPath());
		window.setFocusable(true);
		window.setFocusableWindowState(true);
		window.setAutoRequestFocus(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
