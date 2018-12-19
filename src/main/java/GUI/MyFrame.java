package GUI;

import Algorithm.Map;
import Algorithm.Path;
import Algorithm.ShortestPathAlgo;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MyFrame extends JFrame implements MouseListener , Runnable
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
	private DrawPath drawPath;
	private boolean play = false;
	private ShortestPathAlgo spa;
	private boolean DoDrawPacman = true , NotInThread = true;
	List<Point3D> lastpackmanDraw = new LinkedList<>();

	
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
		MenuItem playGame = new MenuItem("Play Game");
		MenuItem saveGame = new MenuItem("Save To Csv");
		menuBar.add(menuIn);
		menuBar.add(menuLoad);
		menuIn.add(addPackman);
		menuIn.add(addFruit);
		menuLoad.add(loadGame);
		menuLoad.add(saveGame);
		menuLoad.add(playGame);
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
				if (e.getSource()==loadGame){
						loadGameWindow.loadGame();      //opens jfile chooser window
						gameLoading = true;
						gameDataInitFromGui = loadGameWindow.getGame(); // initialize game from csv external file
						repaint();
					}
				}
		});
		playGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==playGame){
					if (!gameDataInitFromGui.getFruitsCopy().isEmpty() && !gameDataInitFromGui.getPackMansCopy().isEmpty()) {
						spa = new ShortestPathAlgo(gameDataInitFromGui);
							play = true;
							repaint();
					}}	}
		});
		saveGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==saveGame){
					if (!gameDataInitFromGui.getFruitsCopy().isEmpty() && !gameDataInitFromGui.getPackMansCopy().isEmpty()){
						try {
							gameDataInitFromGui.toCsv();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		}

	int x = -1;
	int y = -1;

	public void paint(Graphics g)
	{
		//there will be a repaint() each time we want to make any basic action regarding drawn elements displayed on to frame.
		//otherwise , many bugs are expected.
		g.drawImage(myImage, 0, 0,this.getWidth(),this.getHeight(), this);
		BufferedImage update = properize(this);


			if (!gameDataInitFromGui.getFruitsCopy().isEmpty()) {
				DrawFruit(g, gameDataInitFromGui, update);
			}
		if(NotInThread) {
			if (DoDrawPacman) {
				if (!gameDataInitFromGui.getPackMansCopy().isEmpty()) {
					DrawPacman(g, gameDataInitFromGui, update);
				}
			}
			if (x != -1 && y != -1) {
				if (DoDrawPacman) {
					if (pacman) {
						packmanIcon.paintIcon(this, g, x, y);
						Point3D packManDrawInPixels = new Point3D(x, y);
						Point3D packManDrownCoords = frameConverter.pixelToPoint3D(packManDrawInPixels, myImage);
						Packman packman = new Packman(packmanCounter++, packManDrownCoords, 1, 1);
						gameDataInitFromGui.addPackman(packman);
						pacman = false;
					}
				}
				if (fruit) {
					fruitIcon.paintIcon(this, g, x, y);
					Point3D fruitDrawInPixels = new Point3D(x, y);
					Point3D fruitDrawInCoords = frameConverter.pixelToPoint3D(fruitDrawInPixels, myImage);
					Fruit fruit1 = new Fruit(fruitCounter++, fruitDrawInCoords, 1);
					gameDataInitFromGui.addFruit(fruit1);
					fruit = false;
				}
				x = -1;
				y = -1;
			/*if (gameLoading){ //here i want to paint packmans and fruits
				//take the csv file , make it a game.
				//then , update current game
				//TODO - this is still not working. it seems that i need to change construction for the Game objects of both classes.
				//loadGameWindow.drawLoadedGameElements(g,loadGameWindow.getOnGuiScreenGame(),packmanIcon,fruitIcon,myImage,this);
                //drawPath.DrawAllPath(g,myImage,packmanIcon);
				gameLoading = false;
			}
			if (play){
				drawPath = new DrawPath(gameDataInitFromGui,g);
			    Thread play1 = new Thread(this);
			    play1.start();
			    DoDrawPacman = false;

				//play = false;
			}*/
			}
			if (gameLoading) { //here i want to paint packmans and fruits
				//take the csv file , make it a game.
				//then , update current game
				//TODO - this is still not working. it seems that i need to change construction for the Game objects of both classes.
				//loadGameWindow.drawLoadedGameElements(g,loadGameWindow.getOnGuiScreenGame(),packmanIcon,fruitIcon,myImage,this);
				//drawPath.DrawAllPath(g,myImage,packmanIcon);
				gameLoading = false;
			}
			if (play) {
				drawPath = new DrawPath(gameDataInitFromGui, g);
				Thread play1 = new Thread(this);
				DoDrawPacman = false;
				play1.start();
				//DoDrawPacman = false;
				//play = false;
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
		return graphicsConfiguration.createCompatibleImage(myFrame.getWidth(),myFrame.getHeight());
	}
	private void DrawPacman(Graphics g,Game game,BufferedImage update){
		for (Packman packman : game.getPackMansCopy()){
			Point3D drawAtpixel = frameConverter.point3DToPixel(packman.getCoordinates(),update);
			packmanIcon.paintIcon(this,g,drawAtpixel.ix(),drawAtpixel.iy());
		}
	}
	private void DrawFruit(Graphics g,Game game,BufferedImage update){
		for (Fruit fruit1 : game.getFruitsCopy()){
			Point3D drawAtpixel = frameConverter.point3DToPixel(fruit1.getCoordinates(),update);
			fruitIcon.paintIcon(this,g,drawAtpixel.ix(),drawAtpixel.iy());
		}
	}

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

		/*drawPath.DrawAllPath(myImage,packmanIcon);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/


		//List<Path> calcpath = spa.calcPathes();

		List<Path> calcpath = drawPath.getcalcPathes();
		int numOfPackmen = spa.getNumOfPackmen();

		int numOfPointsInPath = calcpath.get(0).size();
		for (int i = 0; i < numOfPointsInPath; i++) {
			for (int packmanIndex = 0; packmanIndex < numOfPackmen; packmanIndex++) {
				Point3D to = frameConverter.point3DToPixel(calcpath.get(packmanIndex).getPointsCopy().get(i), myImage);
				packmanIcon.paintIcon(this, this.getGraphics(), to.ix(), to.iy());
			}
			try {
				Thread.sleep(10);
				//Thread.currentThread().wait(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			NotInThread = false;
			repaint();
		}
		for (int packmanIndex = 0; packmanIndex < numOfPackmen; packmanIndex++) {
			Point3D to = frameConverter.point3DToPixel(calcpath.get(packmanIndex).getPointsCopy().get(numOfPointsInPath - 1), myImage);
			packmanIcon.paintIcon(this, this.getGraphics(), to.ix(), to.iy());
		}
	}
}