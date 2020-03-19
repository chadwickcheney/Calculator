import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
/**
 * Window Class is the window for the graphics
 *
 * @author Chadwick Cheney
 *
 */
public class Window {

	private String title = "Calculator";
	private int width = 360;
	private int height = 640;
	private Thread thread;
	public JFrame programFrame;

	/**
	 * Window constructor set the Jframe component
	 *
	 * @param 	controller is instanceof Class Controller that listens for user
	 *			input and kicks off events.
	 * @see #setFrame()
	 */
	public Window(Controller controller) {
		programFrame = setFrame();
		programFrame.addKeyListener(controller);
		programFrame.addMouseListener(controller);
	}

	/**
	 * Creates the Jframe object for the program 
	 * 
	 * @return The object to store in programFrame variable.
	 */
	public JFrame setFrame() {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.pack();
		
		return frame;
	}
	
	/**
	 * Hides mouse cursor when in the window frame. Used to hide the the default
	 * cursor and to add a custom cursor (not yet implemented)
	 * 
	 * @param frame is used to call setCursor(blankCursor)
	 */

	private void hideCursor(JFrame frame) {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.getContentPane().setCursor(blankCursor);
	}
}
