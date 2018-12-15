package GUI;

import Algorithm.Map;
import GIS.Fruit;
import GIS.Game;
import GIS.Packman;
import Geom.Point3D;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MyFrame extends JFrame implements MouseListener
{
	public BufferedImage myImage ;
	public ImageIcon packmanIcon , fruitIcon;
	private boolean pacman = false;
	private boolean fruit = false;
	private boolean gameLoading = false;
	private LoadGameWindow loadGameWindow = new LoadGameWindow();
	private Map frameConverter = new Map();
	private Game gameDataInitFromGui = new Game();
	private int packmanCounter = 0;
	private int fruitCounter = 0;
	
	public MyFrame(String mapPath)
	{
		initGUI();		
		this.addMouseListener(this);
		try {
			myImage = ImageIO.read(new File(mapPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void initGUI() 
	{
		MenuBar menuBar = new MenuBar();
		Menu menuLoad = new Menu("Game Options");
		Menu menuIn = new Menu("Input");
		MenuItem addPackman = new MenuItem("Add Packman");
		MenuItem addFruit = new MenuItem("Add Fruit");
		MenuItem loadGame = new MenuItem("Load From Csv");
		MenuItem saveGame = new MenuItem("Save To Csv");
		
		menuBar.add(menuIn);
		menuBar.add(menuLoad);
		menuIn.add(addPackman);
		menuIn.add(addFruit);
		menuLoad.add(loadGame);
		menuLoad.add(saveGame);
		this.setMenuBar(menuBar);
		 packmanIcon = new ImageIcon("pacman.png");
		 fruitIcon = new ImageIcon("diet1.png");
		addPackman.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pacman = true;
			}
		}
		);
		addFruit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fruit = true;
			}
		}
		);
		loadGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameLoading = true;
				if (e.getSource()==loadGame){
					try {
						loadGameWindow.loadGame();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
		}});
		}
	int x = -1;
	int y = -1;

	public void paint(Graphics g)
	{
		//there will be a repaint() each time we want to make any basic action regarding drawn elements displayed on to frame.
		//otherwise , many bugs are expected.
		g.drawImage(myImage, 0, 0,this.getWidth(),this.getHeight(), this);
		BufferedImage update = properize(this);
		if (!gameDataInitFromGui.getPackMansCopy().isEmpty()){
		for (Packman packman : gameDataInitFromGui.getPackMansCopy()){
			Point3D drawAtpixel = frameConverter.point3DToPixel(packman.getCoordinates(),update);
			packmanIcon.paintIcon(this,g,drawAtpixel.ix(),drawAtpixel.iy());
		}}
		if (!gameDataInitFromGui.getFruitsCopy().isEmpty()){
		for (Fruit fruit1 : gameDataInitFromGui.getFruitsCopy()){
			Point3D drawAtpixel = frameConverter.point3DToPixel(fruit1.getCoordinates(),update);
			fruitIcon.paintIcon(this,g,drawAtpixel.ix(),drawAtpixel.iy());
		}}
		if(x!=-1 && y!=-1)
		{
			if (pacman)
			{
				packmanIcon.paintIcon(this,g,x,y);
				Point3D packManDrawInPixels = new Point3D(x,y);
				Point3D packManDrownCoords = frameConverter.pixelToPoint3D(packManDrawInPixels,myImage);
				Packman packman = new Packman(packmanCounter++,packManDrownCoords,1,1);
				gameDataInitFromGui.addPackman(packman);
				pacman = false;
			}
			if (fruit)
			{
				fruitIcon.paintIcon(this,g,x,y);
				Point3D fruitDrawInPixels = new Point3D(x,y);
				Point3D fruitDrawInCoords = frameConverter.pixelToPoint3D(fruitDrawInPixels,myImage);
				Fruit fruit1 = new Fruit(fruitCounter++,fruitDrawInCoords,1);
				gameDataInitFromGui.addFruit(fruit1);
				fruit = false;
			}
			if (gameLoading){ //here i want to paint packmans and fruits
				//TODO - this is still not working. it seems that i need to change construction for the Game objects of both classes.
				loadGameWindow.drawLoadedGameElements(g,loadGameWindow.getOnGuiScreenGame(),packmanIcon,fruitIcon,myImage,this);
				gameLoading = false;
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg) {
		System.out.println("("+ arg.getX() + "," + arg.getY() +")");
		Point3D pixelToPoint3D = frameConverter.pixelToPoint3D(new Point3D(arg.getX(),arg.getY()),myImage);
		System.out.println("("+ pixelToPoint3D.x() + "," + pixelToPoint3D.y() +")");
		Point3D c2p = frameConverter.point3DToPixel(pixelToPoint3D,myImage);
		System.out.println("("+ c2p.x() + "," + c2p.y() +")");
		x = arg.getX();
		y = arg.getY();
		repaint(x,y,35,35);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//System.out.println("mouse entered");
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public BufferedImage properize(MyFrame myFrame){
		GraphicsConfiguration graphicsConfiguration = new JWindow().getGraphicsConfiguration();
		BufferedImage resized = graphicsConfiguration.createCompatibleImage(myFrame.getWidth(),myFrame.getHeight());
		return resized;
	}
}
