import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Buttons are the clickable elements for user input
 *
 * @author chadcheney
 *
 */
public class Button extends Element {

	protected Keys key;

	/**
	 * Button constructor without toggle setting.
	 *
	 * @param columnIndex is the x axis coordinate to render the currentImage
	 *                    variable to.
	 * @param rowIndex    is the y axis coordinate to render the currentImage
	 *                    variable to.
	 * @param frames      is the list of frames available to the object.
	 * @param input       is the string that appears in the display area of the
	 *                    program.
	 * @param key         is the Key object assigned to each button on
	 *                    instantiation.
	 * @see #init(Keys, int, int, ArrayList, Element, Containers)
	 */
	public Button(Keys key, int columnIndex, int rowIndex, boolean isDisplayKey,
			boolean toggle, ArrayList<BufferedImage> framesArg,
			Element parentArg, Containers containerArg) {
		super(key, rowIndex, columnIndex, framesArg, parentArg, containerArg);
		this.name = "toggle button";
		this.toggle = toggle;
		this.isDisplayKey = isDisplayKey;
		init(key, columnIndex, rowIndex, framesArg, parentArg, containerArg);
	}

	/**
	 * Button constructor without toggle setting.
	 *
	 * @param columnIndex is the x axis coordinate to render the currentImage
	 *                    variable to.
	 * @param rowIndex    is the y axis coordinate to render the currentImage
	 *                    variable to.
	 * @param frames      is the list of frames available to the object.
	 * @param input       is the string that appears in the display area of the
	 *                    program.
	 * @param key         is the Key object assigned to each button on
	 *                    instantiation.
	 */
	public Button(Keys key, int columnIndex, int rowIndex,
			ArrayList<BufferedImage> framesArg, Element parentArg,
			Containers containerArg) {
		super(key, rowIndex, columnIndex, framesArg, parentArg, containerArg);
		init(key, columnIndex, rowIndex, framesArg, parentArg, containerArg);
	}

	/**
	 * 
	 * @param key
	 * @param columnIndex
	 * @param rowIndex
	 * @param framesArg
	 * @param parentArg
	 * @param containerArg
	 * 
	 * @see #setFrames(ArrayList)
	 * @see #setImage(int)
	 * @see #setName(String)
	 * @see #setParent(Element)
	 * @see #setContainer( @see
	 *      {@link #calculateContainer(Element, int, int, int)}
	 * @see #setContainerOption(Containers)
	 * @see #setMarginLeft( @see {@link #calculateMarginLeft(Element)})
	 * @see #setMarginTop( @see {@link #calculateMarginTop(Element)})
	 * @see #setPaddingLeft(int)
	 * @see #setPaddingTop(int)
	 * @see #setWidth(int)
	 * @see #setHeight(int)
	 * @see #setRenderX( @see {@link #calculateRenderX(Element, int, int, int)}
	 * @see #setRenderY( @see {@link #calculateRenderY(Element, int, int, int)}
	 * @see #setEdges()
	 */
	private void init(Keys key, int columnIndex, int rowIndex,
			ArrayList<BufferedImage> framesArg, Element parentArg,
			Containers containerArg) {
		setKeys(key);
		setFrames(framesArg);
		setImage(0);
		setName("button");
		setParent(parentArg);
		setContainer(calculateContainer(containerArg));
		setContainerOption(containerArg);
		setMarginLeft(calculateMarginLeft(this));
		setMarginTop(calculateMarginTop(this));
		setPaddingLeft(12);
		setPaddingTop(-4);
		setWidth(currentImage.getWidth());
		setHeight(currentImage.getHeight());
		setRenderX(calculateRenderX(this, columnIndex, getPaddingLeft(),
				getMarginLeft()));
		setRenderY(calculateRenderY(this, rowIndex, getPaddingTop(),
				getMarginTop()));
		setEdges();
	}

	/**
	 * Method called from Class Controller methods mousePressed and keyPressed.
	 * 
	 * @return no return value
	 */
	public void pressed() {
		if (this.toggle) {
			this.isCurrentlyToggled = !this.isCurrentlyToggled;
			setImage(1);
		}
	}

	/**
	 * Method called from Class Controller methods mouseReleased and
	 * keyReleased.
	 * 
	 * @return no return value
	 */
	public void released() {
		if (this.toggle && !this.isCurrentlyToggled) {
			setImage(0);
		}
	}

	/**
	 * Changes the toggle status to the opposite of what the current toggle
	 * status is
	 * 
	 * @return no return value
	 */
	public void changeToggleStatus() {
		this.isCurrentlyToggled = !this.isCurrentlyToggled;
		this.released();
	}

	/**
	 * Method to get the toggle status of buttons with the toggle setting.
	 * Called from Class Event method interpreter.
	 *
	 * @return the current toggle status of the button or false if the button
	 *         doesn't have a toggle setting.
	 */
	public boolean getToggleStatus() {
		if (this.toggle)
			return this.isCurrentlyToggled;
		else
			return false;
	}

	/**
	 * Returns reported width of current button
	 * 
	 * @return int value of width
	 */
	@Override
	public int getWidth() {
		return 75;
	}

	/**
	 * Return reported height of current button
	 * 
	 * @return int value of height
	 */
	@Override
	public int getHeight() {
		return 75;
	}

	/**
	 * Sets the Enum Keys object for the current button for consistent user
	 * inputs types
	 * 
	 * @param key
	 * @return no return value
	 */
	protected void setKeys(Keys key) {
		this.key = key;
	}
}
