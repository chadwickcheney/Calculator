import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Controller will take in all input for the program and call on Event to
 * execute
 *
 * @author chadcheney
 *
 */
public class Controller implements KeyListener, MouseListener,
		FocusListener, MouseMotionListener {

	private Event event;
	private Display display;
	private ArrayList<Button> buttons;
	private HashMap<Keys, Button> buttonsDragoman;
	public static boolean render;
	private final AtomicBoolean canShoot = new AtomicBoolean(true);
	private static final HashMap<Integer, Keys> keyDragoman = keyEventsMap();

	private static final HashMap<Integer, Keys> keyEventsMap() {
		HashMap<Integer, Keys> map = new HashMap<>();
		map.put(KeyEvent.VK_0, Keys.KEY0);
		map.put(KeyEvent.VK_1, Keys.KEY1);
		map.put(KeyEvent.VK_2, Keys.KEY2);
		map.put(KeyEvent.VK_3, Keys.KEY3);
		map.put(KeyEvent.VK_4, Keys.KEY4);
		map.put(KeyEvent.VK_5, Keys.KEY5);
		map.put(KeyEvent.VK_6, Keys.KEY6);
		map.put(KeyEvent.VK_7, Keys.KEY7);
		map.put(KeyEvent.VK_8, Keys.KEY8);
		map.put(KeyEvent.VK_9, Keys.KEY9);
		map.put(KeyEvent.VK_0, Keys.KEY0);
		map.put(8, Keys.DELETE);
		map.put(10, Keys.ENTER);
		map.put(42, Keys.MULTIPLICATION);
		map.put(47, Keys.DIVISION);
		map.put(43, Keys.ADDITION);
		map.put(45, Keys.SUBTRACTION);
		map.put(40, Keys.LEFT_PARENTHESIS);
		map.put(41, Keys.RIGHT_PARENTHESIS);
		map.put(46, Keys.DECIMAL);
		map.put(94, Keys.POWER);
		map.put(92, Keys.ROOT);
		map.put(37, Keys.MODULO);
		map.put(KeyEvent.VK_P, Keys.PI);
		map.put(KeyEvent.VK_N, Keys.NEGATE);

		return map;
	}

	/**
	 * Display constructor.
	 *
	 * @param buttons         is button elements of the program initialized
	 *                        in Class Main and Instantiated in
	 *                        load.getButtons().
	 * @param display         is the display element of the program
	 *                        initialized in Class Main and Instantiated in
	 *                        load.getDisplay().
	 * @param buttonsDragoman is the HashMap to transcribe Keys to Button
	 *                        objects from Class Maps method getMaps().
	 */
	public Controller(ArrayList<Button> buttons, Display display,
			HashMap<Keys, Button> buttonsDragoman) {
		event = new Event(display, buttons, buttonsDragoman);
		this.display = display;
		this.buttonsDragoman = buttonsDragoman;
		this.buttons = buttons;
		setRender(true);
	}

	/*
	 *
	 */
	public synchronized boolean getRender() {
		return this.render;
	}

	/**
	 *
	 */
	public synchronized void setRender(boolean r) {
		render = r;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setRender(true);
	}

	@Override
	public void focusGained(FocusEvent e) {
		setRender(true);
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Method to retrieve event of mouse pressed and released.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		setRender(true);
	}

	/**
	 * Method to retrieve event of mouse pressed.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		Button buttonSelected = null;
		for (Button b : buttons) {
			if ((mouseX >= b.elemii && mouseX <= b.elemij)
					&& (mouseY >= b.elemji && mouseY <= b.elemjj)) {
				buttonSelected = b;
			}
		}
		if (buttonSelected != null) {
			//buttonSelected.pressed();
			event.interpreter(buttonSelected);
		}

		if (buttonSelected == null && (mouseX >= display.elemii && mouseX <= display.elemij)
				&& (mouseY >= display.elemji
						&& mouseY <= display.elemjj)) {
			display.clicked();
			event.deleteAllKeys();
		}

		setRender(true);
	}

	/**
	 * Method to retrieve event of mouse released.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		for (Button b : buttons) {
			if ((mouseX >= b.elemii && mouseX <= b.elemij)
					&& (mouseY >= b.elemji && mouseY <= b.elemjj)) {
				b.released();
			}
		}
		setRender(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Method to retrieve keyboard button pressed event.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyEventInt = e.getKeyChar();
		Keys key = keyDragoman.get(keyEventInt);
		
		if (key != null && canShoot.compareAndSet(true, false)) {
			Button b = buttonsDragoman.get(key);
			//b.pressed();
			event.interpreter(b);
		}
		setRender(true);
	}

	/**
	 * Method to retrieve keyboard button released event.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int keyEventInt = e.getKeyChar();
		Keys key = keyDragoman.get(keyEventInt);

		if (key != null)
			buttonsDragoman.get(key).released();

		canShoot.set(true);
		setRender(true);
	}
}
