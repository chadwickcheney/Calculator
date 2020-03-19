import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Loads all images to lists, initializes and returns instantiated elements
 *
 * @author Chadwick Cheney
 *
 */
public class Load {

	String[] btnFilenames = { "/button5.png", "/button5pressed.png" };
	String[] btnTallFilenames = { "/button5long.png",
			"/button5longpressed.png" };
	String[] btnLongFilenames = { "/buttonhlong5.png",
			"/buttonhlong5pressed.png" };
	String[] backgroundFilenames = { "/background2.png" };
	String[] displayFilenames = { "/display.png", "/DisplayDark.png" };
	String[] displayContainerBFilenames = { "/cb-button.png",
			"/cb-button-pressed.png" };

	private Maps maps;
	public ArrayList<Button> buttons;
	public Display display;
	public Background background;
	private int width;
	private int height;
	private Frame frame;

	/**
	 * Load constructor initializes instanceof Class Maps object maps
	 *
	 * @throws IOException
	 *
	 * @see Class Maps
	 */
	public Load(int width, int height) throws IOException {
		this.width = width;
		this.height = height;
		maps = new Maps();
		frame = loadFrame();
		background = loadBackground(frame);
		display = loadDisplay(background);
		buttons = new ArrayList<Button>();
		loadButtons(background, display);
	}

	/**
	 * Creates an array list of BufferedImages, instantiates a new display
	 * object with the array list and returns the display object.
	 *
	 * @see #getSpriteSheet(String, String[])
	 * @return display object
	 * @throws IOException
	 */
	public Display loadDisplay(Element parentElement) throws IOException {
		Display display = new Display(0, 0,
				getSpriteSheet("", 25, displayFilenames), parentElement);
		display.toString();
		return display;
	}

	/**
	 * Creates the frame container Background lives in.
	 *
	 * @see #getSpriteSheet(String, String[])
	 * @return background object
	 * @throws IOException
	 */
	public Frame loadFrame() throws IOException {
		ArrayList<BufferedImage> framesList = new ArrayList<BufferedImage>();
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		framesList.add(bufferedImage);
		Frame frame = new Frame(0, 0, framesList, null);
		return frame;
	}

	/**
	 * Creates an array list of BufferedImages, instantiates a new background
	 * object with the array list and returns the display object.
	 *
	 * @see #getSpriteSheet(String, String[])
	 * @return background object
	 * @throws IOException
	 */
	public Background loadBackground(Element parentArg) throws IOException {
		Background background = new Background(0, 0,
				getSpriteSheet("", 25, backgroundFilenames), parentArg);
		return background;
	}

	/**
	 * Creates an array lists of BufferedImages, instantiates a new button
	 * objects with the array lists and returns the buttons list instance.
	 *
	 * @see #getSpriteSheet(String, String[])
	 * @return ArrayList of Button objects
	 * @throws IOException
	 */
	public void loadButtons(Element backgroundElement, Element displayElement)
			throws IOException {

		Containers a = Containers.A;
		Containers b = Containers.B;

		int x = -1;
		int y = -1;
		int fontSize = 35;
		addButton(Keys.DELETE,
				new Button(Keys.DELETE, ++x, ++y,
						getSpriteSheet("c", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.DELETE,
				new Button(Keys.DELETEALL, ++x, y,
						getSpriteSheet("ce", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.DIVISION,
				new Button(Keys.DIVISION, ++x, y,
						getSpriteSheet("/", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.MULTIPLICATION,
				new Button(Keys.MULTIPLICATION, ++x, y,
						getSpriteSheet("*", fontSize, btnFilenames),
						backgroundElement, a));

		x = -1;
		addButton(Keys.KEY7,
				new Button(Keys.KEY7, ++x, ++y,
						getSpriteSheet("7", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.KEY8,
				new Button(Keys.KEY8, ++x, y,
						getSpriteSheet("8", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.KEY9,
				new Button(Keys.KEY9, ++x, y,
						getSpriteSheet("9", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.SUBTRACTION,
				new Button(Keys.SUBTRACTION, ++x, y,
						getSpriteSheet("-", fontSize, btnFilenames),
						backgroundElement, a));

		x = -1;
		addButton(Keys.KEY4,
				new Button(Keys.KEY4, ++x, ++y,
						getSpriteSheet("4", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.KEY5,
				new Button(Keys.KEY5, ++x, y,
						getSpriteSheet("5", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.KEY6,
				new Button(Keys.KEY6, ++x, y,
						getSpriteSheet("6", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.ADDITION,
				new Button(Keys.ADDITION, ++x, y,
						getSpriteSheet("+", fontSize, btnTallFilenames),
						backgroundElement, a));

		x = -1;
		addButton(Keys.KEY1,
				new Button(Keys.KEY1, ++x, ++y,
						getSpriteSheet("1", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.KEY2,
				new Button(Keys.KEY2, ++x, y,
						getSpriteSheet("2", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.KEY3,
				new Button(Keys.KEY3, ++x, y,
						getSpriteSheet("3", fontSize, btnFilenames),
						backgroundElement, a));

		x = -1;
		addButton(Keys.DECIMAL,
				new Button(Keys.DECIMAL, ++x, ++y,
						getSpriteSheet(".", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.KEY0,
				new Button(Keys.KEY0, ++x, y,
						getSpriteSheet("0", fontSize, btnFilenames),
						backgroundElement, a));
		addButton(Keys.ENTER,
				new Button(Keys.ENTER, ++x, y,
						getSpriteSheet("ent", fontSize, btnLongFilenames),
						backgroundElement, a));

		x = -1;
		y = -1;
		fontSize = 15;
		addButton(Keys.ROOT,
				new Button(Keys.ROOT, ++x, ++y, true, true,
						getSpriteSheet("_/", fontSize,
								displayContainerBFilenames),
						displayElement, b));
		addButton(Keys.POWER,
				new Button(Keys.POWER, ++x, y, true, true,
						getSpriteSheet("^", fontSize,
								displayContainerBFilenames),
						displayElement, b));
		addButton(Keys.MODULO,
				new Button(Keys.MODULO, ++x, y,
						getSpriteSheet("%", fontSize,
								displayContainerBFilenames),
						displayElement, b));
		x = -1;
		addButton(Keys.LEFT_PARENTHESIS,
				new Button(Keys.LEFT_PARENTHESIS, ++x, ++y, true, true,
						getSpriteSheet("(", fontSize,
								displayContainerBFilenames),
						displayElement, b));
		addButton(Keys.RIGHT_PARENTHESIS,
				new Button(Keys.RIGHT_PARENTHESIS, ++x, y, true, true,
						getSpriteSheet(")", fontSize,
								displayContainerBFilenames),
						displayElement, b));
		addButton(Keys.NEGATE,
				new Button(Keys.NEGATE, ++x, y,
						getSpriteSheet("+/-", fontSize,
								displayContainerBFilenames),
						displayElement, b));
		x = -1;
		addButton(Keys.PI,
				new Button(Keys.PI, ++x, ++y,
						getSpriteSheet("pi", fontSize,
								displayContainerBFilenames),
						displayElement, b));
	}

	/**
	 * Adds a new button to declares new button, adds to button list, and add
	 * key,value entry to buttonsDragoman<Keys,Button> hashmap
	 *
	 * @param key
	 * @param btn
	 * @return no return value
	 */
	public void addButton(Keys key, Button btn) {
		buttons.add(btn);
		maps.buttonsDragoman.put(key, btn);
	}

	/**
	 * Creates an instanceof Class ArrayList of BufferedImages and iterates
	 * through the string array resource of files names, loads the each image
	 * from filename, draws the desired string to the loaded image and adds all
	 * images to the array list
	 *
	 * @param str       is the desiredString to be drawn to an image.
	 * @param resources is the list string filenames.
	 * @see #loadImage(String)
	 * @see #etcher(String)
	 * @return array list of bufferedimags.
	 * @throws IOException
	 */
	public ArrayList<BufferedImage> getSpriteSheet(String str, int fontSize,
			String... resources) throws IOException {

		if (resources != null) {

			ArrayList<BufferedImage> arrayListFrames = new ArrayList<BufferedImage>();

			for (int i = 0; i < resources.length; ++i) {
				BufferedImage bufferedImage = loadImage(resources[i]);
				bufferedImage = etcher(bufferedImage, str, fontSize);
				arrayListFrames.add(bufferedImage);
			}
			;

			return arrayListFrames;
		} else {
			return null;
		}
	}

	/**
	 * Draws an image of a string on top of the other image
	 *
	 * @param img is the image to be drawn on.
	 * @param str is the string to draw on to the image.
	 * @return the image.
	 */
	public BufferedImage etcher(BufferedImage img, String str, int fontSize) {
		if (str.length() > 0) {
			System.out.printf("\nEtcher\n\t%s\n", str);
			Graphics graphics = img.getGraphics();
			graphics.setColor(Color.BLACK);
			graphics.setFont(new Font("Courier New", Font.PLAIN, fontSize));
			int stringWidth = graphics.getFontMetrics().stringWidth(str);
			int stringHeight = graphics.getFontMetrics().getHeight();
			graphics.drawString(str, (img.getWidth() / 2) - (stringWidth / 2),
					(img.getHeight() / 2) + (stringHeight / 4));
		}
		return img;
	}

	/**
	 * Loads the images to the running thread of the program
	 *
	 * @param imageFilename is the filename to load the image from.
	 * @return the image when found. Null if not found.
	 * @throws IOException
	 */
	public BufferedImage loadImage(String imageFilename) throws IOException {
		try {
			BufferedImage image = ImageIO
					.read(getClass().getResourceAsStream(imageFilename));
			return image;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns buttonsDragoman from maps instance of the class
	 *
	 * @return Class Maps variable HashMap<Keys, Button>
	 */
	public HashMap<Keys, Button> getButtonsDragoman() {
		return maps.buttonsDragoman;
	}

	/**
	 * Gets Elements Background
	 * 
	 * @return Element Background
	 */
	public Background getBackground() {
		return this.background;
	}

	/**
	 * Gets Elements Display
	 * 
	 * @return Element Display
	 */
	public Display getDisplay() {
		return this.display;
	}

	/**
	 * Gets ArrayList of all button elements
	 * 
	 * @return ArrayList<Button> of all button elements
	 */
	public ArrayList<Button> getButtons() {
		return this.buttons;
	}
}
