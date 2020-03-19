import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Display is the element of the program that shows the users calculations
 *
 * @author chadcheney
 *
 */
public class Display extends Element {

	public String text;
	public int maxWidth;
	public int linePadding = 10;
	public static ArrayList<Keys> keys;
	public static final HashMap<Keys, String> keyDragoman = keyEventsMap();
	public static final HashMap<String, Keys> stringDragoman = reverseKeyDragoman();
	public static boolean isOn;

	/**
	 * Initializes and returns HashMap plotting Class Key objects to a string.
	 *
	 * @return the HashMap created.
	 */
	public static final HashMap<Keys, String> keyEventsMap() {
		HashMap<Keys, String> map = new HashMap<>();
		map.put(Keys.KEY0, "0");
		map.put(Keys.KEY1, "1");
		map.put(Keys.KEY2, "2");
		map.put(Keys.KEY3, "3");
		map.put(Keys.KEY4, "4");
		map.put(Keys.KEY5, "5");
		map.put(Keys.KEY6, "6");
		map.put(Keys.KEY7, "7");
		map.put(Keys.KEY8, "8");
		map.put(Keys.KEY9, "9");
		map.put(Keys.KEY0, "0");
		map.put(Keys.MULTIPLICATION, "*");
		map.put(Keys.DIVISION, "/");
		map.put(Keys.ADDITION, "+");
		map.put(Keys.SUBTRACTION, "-");
		map.put(Keys.LEFT_PARENTHESIS, "(");
		map.put(Keys.RIGHT_PARENTHESIS, ")");
		map.put(Keys.DECIMAL, ".");
		map.put(Keys.POWER, "^");
		map.put(Keys.ROOT, "_/");
		map.put(Keys.MODULO, "%");
		map.put(Keys.PI, "pi");

		return map;
	}

	/**
	 * Initializes a HashMap with the reversed the keys and values of the
	 * keyEventsMap hashmap.
	 *
	 * @return the HashMap created.
	 */
	public static final HashMap<String, Keys> reverseKeyDragoman() {
		HashMap<String, Keys> map = new HashMap<>();
		for (HashMap.Entry<Keys, String> entry : keyDragoman.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		return map;
	}

	/**
	 * Display constructor.
	 *
	 * @param columnIndex is the x axis coordinate to render the currentImage
	 *                    variable to.
	 * @param rowIndex    is the y axis coordinate to render the currentImage
	 *                    variable to.
	 * @param frames      is the list of frames available to the object.
	 * @see #setText(String)
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
	public Display(int columnIndex, int rowIndex,
			ArrayList<BufferedImage> frames, Element parentElement) {
		super(rowIndex, columnIndex, frames, parentElement);
		keys = new ArrayList<Keys>();
		isOn = true;
		setText("");
		setName("display");

		toString("1");

		setParent(parentElement);
		calculateContainer(Containers.B);
		setMarginLeft(calculateMarginLeft(this));
		setMarginTop(calculateMarginTop(this));
		setPaddingLeft(15);
		setPaddingTop(15);
		setWidth(currentImage.getWidth());
		setHeight(currentImage.getHeight());
		setRenderX(calculateRenderX(this, columnIndex, getPaddingLeft(),
				getMarginLeft()));
		setRenderY(calculateRenderY(this, rowIndex, getPaddingTop(),
				getMarginTop()));
		setEdges();
		maxWidth = (int) (currentImage.getWidth() * .681);
		toString("2");
	}

	/**
	 * Sets the text variables to the String parameter.
	 *
	 * @param text
	 * @return no return value
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the text variable of the class.
	 *
	 * @return String text variable.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Calls method to update the text variable from the hashmap String values
	 * by using key entries in keys as a lookup value.
	 *
	 * @param keys is an array list of Keys to use as lookup values in the
	 *             hashmap.
	 * @see #setText(String)
	 * @return no return value.
	 */
	public void updateTextWithKeys(ArrayList<Keys> keys) {
		String set = ""; // makes display work
		for (Keys k : keys)
			set += keyDragoman.get(k);
		setText(set);
	}

	/**
	 * Calls methods to transcribe ArrayList of Class Keys to a String
	 *
	 * @see #keysMapString(ArrayList)
	 * @see #setText(String)
	 * @return no return value.
	 */
	public void tick() {
		String set = keysMapString(keys);
		setText(set);
	}

	/**
	 * Draws the currentImage variable to the program window
	 *
	 * @param g is the graphics object from Class Main method render.
	 * @return no return value.
	 */
	public void draw(Graphics g) {
		g.drawImage(currentImage, renderX, renderY, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 30));
		int stringWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text,
				renderX + ((int) maxWidth - stringWidth - this.paddingLeft),
				renderY + ((currentImage.getHeight()) - (this.paddingTop)));
	}

	/**
	 * Updates class variable keys with Class Key objects of a String.
	 *
	 * @param answerText
	 * @return the class variable keys
	 */
	public ArrayList<Keys> updateKeysWithString(String answerText) {
		keys.removeAll(keys);
		keys = stringMapKeys(answerText);
		return keys;
	}

	/**
	 * Used to transcribe ArrayList of Class Keys to a String.
	 *
	 * @param userInputs
	 * @return a String object
	 */
	public ArrayList<Keys> stringMapKeys(String str) {
		ArrayList<Keys> tempKeys = new ArrayList<Keys>();
		for (int i = 0; i < str.length(); ++i) {
			Keys k = stringDragoman.get(Character.toString(str.charAt(i)));
			tempKeys.add(k);
		}
		return tempKeys;
	}

	/**
	 * Used to transcribe ArrayList of Class Keys to a String.
	 *
	 * @param userInputs is an ArrayList of Class Keys that the user entered.
	 * @return a String object representing the userInputs parameter
	 */
	public String keysMapString(ArrayList<Keys> userInputs) {
		String str = "";
		for (Keys k : userInputs)
			str += keyDragoman.get(k);
		return str;
	}

	/**
	 * Toggles the screen on and off and erases content if turning off
	 *
	 * @see #setImage(int)
	 * @see #setText(String)
	 * @return no return value
	 */
	public void clicked() {
		isOn = !isOn;
		if (!isOn) {
			setImage(1);
			keys.removeAll(keys);
			setText("OFF");
		} else
			setText("");
			setImage(0);
	}
}
