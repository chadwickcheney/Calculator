import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class holds main thread for program and initializes graphics
 *
 * @author Chadwick Cheney
 */

public class Main implements Runnable {

	private static final boolean DEBUG = false;
	private Thread thread;
	private Window window;
	private Load load;
	private ArrayList<Button> buttons;
	private Display display;
	private Background background;
	private HashMap<Keys, Button> buttonsDragoman;
	public Controller controller;

	/**
	 * Main constructor.
	 * 
	 * @throws IOException
	 *
	 * @see #Load(int, int)
	 * @see #load.getBackground()
	 * @see #load.getDisplay()
	 * @see #load.loadButtons()
	 * @see #load.getButtonsDragoman()
	 * @see #Controller(ArrayList<Button>, Element, HashMap<keys, Button>
	 * @see #Window(controller)
	 */
	public Main() throws IOException {
		load = new Load(360, 640);
		background = load.getBackground();
		display = load.getDisplay();
		buttons = load.getButtons();
		buttonsDragoman = load.getButtonsDragoman();
		controller = new Controller(buttons, display, buttonsDragoman);
		window = new Window(controller);
	}

	/**
	 * main method for start of the program.
	 * 
	 * @params args unused
	 * @return no return value
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Main main = new Main();
		main.start();
	}

	/**
	 * start method is called from main to start the Thread.
	 *
	 * @see #run() called from thread.start()
	 * @return no return value
	 */
	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Runnable method for main thread
	 * 
	 * @see #render()
	 * @see controller.getRender()
	 * @see controller.setRender(boolean)
	 * @return no return value
	 */
	public synchronized void run() {
		render();
		render();
		while (true) {
			if (controller.getRender()) {
				render();
				controller.setRender(false);
			}
		}
	}

	/**
	 * render initializes a BufferStrategy for the frame when needed and draws
	 * BufferedImages to screen.
	 *
	 * @see draw(Graphics g)
	 * @return no return value
	 */
	public void render() {
		BufferStrategy bs = window.programFrame.getBufferStrategy();
		if (bs == null) {
			window.programFrame.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		draw(g);
		sleep(1);
		g.dispose();
		bs.show();
	}

	/**
	 * draw calls instanceof Class Background method draw(g) and instanceof
	 * Class Display method draw(g) and instanceof Class Button draw(g) to
	 * render all elements to the screen.
	 *
	 * @param g is the graphics parameter given from the bufferstrategy in
	 *          render()
	 * @see backgound.draw(g)
	 * @see display.draw(g)
	 * @see button.draw(g)
	 * @return no return value
	 */
	public void draw(Graphics g) {
		background.draw(g);
		display.draw(g);
		for (int i = 0; i < buttons.size(); ++i) {
			buttons.get(i).draw(g);
		}
	}

	/**
	 * sleep is a method for the main thread to pause for a certain amount of
	 * time.
	 *
	 * @param x is the time to wait in milliseconds
	 * @return no return value
	 */
	private void sleep(int x) {
		try {
			Thread.sleep(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
