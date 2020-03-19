import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Abstract class for all Elements
 *
 * @author Chadwick Cheney
 */
abstract class Element {

	public static final boolean DEBUG = true;
	public int marginLeft, marginTop, paddingTop, paddingLeft;
	public int width, height;
	public String name;
	public boolean toggle, isCurrentlyToggled, isDisplayKey = false;
	protected int renderX, renderY, elemii, elemij, elemji, elemjj;
	protected ArrayList<BufferedImage> frames;
	protected BufferedImage currentImage;
	protected Element parent;
	protected Element child;
	protected Container container;
	protected Containers containerOption;

	/**
	 * Element Constructor.
	 * 
	 * @param key
	 * @param rowIndex
	 * @param columnIndex
	 * @param frames2
	 * @param parentArg
	 * @param containerArg
	 */
	public Element(Keys key, int rowIndex, int columnIndex,
			ArrayList<BufferedImage> frames2, Element parentArg,
			Containers containerArg) {
	}

	/**
	 * Element constructor.
	 *
	 * @param x      is the x axis coordinate to render the currentImage
	 *               variable to.
	 * @param y      is the y axis coordinate to render the currentImage
	 *               variable to.
	 * @param frames is the list of frames available to the object.
	 * @see #setFrmaes(ArrayList<BuferedImage>)
	 * @see #setImage(int)
	 */
	public Element(int y, int x, ArrayList<BufferedImage> frames,
			Element parentElement) {
		setFrames(frames);
		setImage(0);
	}

	/**
	 * Element Constructor.
	 * 
	 * @param argMarginLeft
	 * @param argMarginTop
	 */
	public Element(int argMarginLeft, int argMarginTop) {

	}

	/**
	 * Sets the surface area for click radius to capture KeyEvent Click.
	 *
	 * @param no params
	 * @return no return value
	 */
	protected void setEdges() {
		elemii = renderX;
		elemij = elemii + currentImage.getWidth();
		elemji = renderY;
		elemjj = elemji + currentImage.getHeight();
	}

	/**
	 * Draws the currentImage to the window.
	 *
	 * @param g is the graphics object from Class Main method render.
	 */
	public void draw(Graphics g) {
		g.drawImage(currentImage, renderX, renderY, null);
	}

	/**
	 * Sets name of current element for debugging purposes
	 * 
	 * @param str
	 * @return no return value
	 */
	public void setName(String str) {
		this.name = str;
	}

	/**
	 * Sets the currentImage to the image at the index of the frames list of the
	 * class.
	 *
	 * @param index is the int value to retrieve image from in array list.
	 * @return no return value
	 */
	protected void setImage(int index) {
		BufferedImage img = frames.get(index);
		currentImage = img;
	}

	/**
	 * Sets the frames list object of the class to frames.
	 *
	 * @param frames
	 * @return no return value
	 */
	protected void setFrames(ArrayList<BufferedImage> frames) {
		this.frames = frames;
	}

	/**
	 * Gets the child element of the current element
	 * 
	 * @param parentArg
	 * @return Element child associated with the current element
	 */
	public Element getChild(Element parentArg) {
		return parentArg.child;
	}

	// PARENT
	// SETTER AND GETTER
	/**
	 * Gets the parent element of the current element
	 * 
	 * @return Element parent associated with the current element
	 */
	public Element getParent() {
		return this.parent;
	}

	/**
	 * Sets parent element for the current element
	 * 
	 * @param parentArg
	 */
	public void setParent(Element parentArg) {

		if (this != null) {
			parentArg.child = this;
		}

		if (parentArg != null) {
			this.parent = parentArg;
		}

	}

	// CONTAINER
	// SETTER AND GETTER
	/**
	 * Gets the container element of the current element
	 * 
	 * @return Element container associated with the current element
	 */
	public Container getContainer() {
		return this.container;
	}

	/**
	 * Calculate which container to host the element in based on restricted
	 * options from Containers Enum
	 * 
	 * @param containerArg
	 * @return Element container corresponding to Containers Enum
	 */
	protected Container calculateContainer(Containers containerArg) {
		Element parentArg = this.getParent();
		if (this != null && parentArg != null) {
			int ml = 0;
			int mt = 0;

			int parentHeight = parentArg.getHeight();
			int parentWidth = parentArg.getWidth();

			if (parentHeight > parentWidth) {
				if (containerArg == Containers.A) {
					mt = parentArg.getHeight() - parentArg.getWidth();
				} else if (containerArg == Containers.B) {

				}
			}

			else {
				if (containerArg == Containers.A) {

				} else if (containerArg == Containers.B) {
					ml = (int) (parentArg.getWidth() * .681);
				}
			}

			Container ctemp = new Container(ml, mt);
			ctemp.setElement(this);
			System.out.printf("\n\tSetContainer\n\t\t%s\n", this.container);
			return ctemp;
		}
		return null;
	}

	// RENDER X AND Y
	/**
	 * Calculates proper render x point
	 * 
	 * @param childArg
	 * @param index
	 * @param padding
	 * @param margin
	 * @return int value for x coordinate
	 */
	protected int calculateRenderX(Element childArg, int index, int padding,
			int margin) {
		System.out.printf("\n\tRenderX\n");
		int a = calculateRenderHelper(childArg, index, padding, margin,
				childArg.getWidth());
		System.out.printf("\t\ta: %s", a);
		return a;
	}

	/**
	 * Calculates proper render y point
	 * 
	 * @param childArg
	 * @param row
	 * @param padding
	 * @param margin
	 * @return int value for y coordinate
	 */
	protected int calculateRenderY(Element childArg, int index, int padding,
			int margin) {
		System.out.printf("\n\tRenderY\n");
		int a = calculateRenderHelper(childArg, index, padding, margin,
				childArg.getHeight());
		System.out.printf("\t\ta: %s", a);
		return a;
	}

	/**
	 * Helper function to reuse algorithms for calculating render coordinates
	 * 
	 * @param childArg
	 * @param index
	 * @param padding
	 * @param margin
	 * @param dimension
	 * @return
	 */
	protected int calculateRenderHelper(Element childArg, int index,
			int padding, int margin, int dimension) {
		System.out.printf(
				"\n\t\tRenderHelper\n\t\t\tindex: %s\n\t\t\tpadding: %s\n\t\t\tmargin: %s\n",
				index, padding, margin);
		if (childArg.getParent() != null) {
			if (childArg.container == null) {
				return margin + padding;
			} else {
				if (childArg.containerOption == Containers.B) {
					padding = 7;
					dimension = 30;
				}
				return (margin + padding + (index * (dimension + padding)));
			}
		} else {
			return childArg.getMarginLeft();
		}
	}

	// SETTERS
	protected void setWidth(int arg) {
		this.width = arg;
	}

	protected void setHeight(int arg) {
		this.height = arg;
	}

	protected void setPaddingTop(int arg) {
		this.paddingTop = arg;
	}

	protected void setPaddingLeft(int arg) {
		this.paddingLeft = arg;
	}

	protected void setMarginLeft(int arg) {
		this.marginLeft = arg;
	}

	protected void setMarginTop(int arg) {
		this.marginTop = arg;
	}

	protected void setRenderX(int arg) {
		this.renderX = arg;
	}

	protected void setRenderY(int arg) {
		this.renderY = arg;
	}

	protected void setContainerOption(Containers arg) {
		this.containerOption = arg;
	}

	protected void setContainer(Container arg) {
		this.container = arg;
	}

	// GETTERS
	protected int getMarginLeft() {
		return this.marginLeft;
	}

	protected int getMarginTop() {
		return this.marginTop;
	}

	protected int getPaddingLeft() {
		return this.paddingLeft;
	}

	protected int getPaddingTop() {
		return this.paddingTop;
	}

	public int getWidth() {
		return this.currentImage.getWidth();
	}

	public int getHeight() {
		return this.currentImage.getHeight();
	}

	protected int getRenderX() {
		return this.renderX;
	}

	protected int getRenderY() {
		return this.renderY;
	}

	// MARGINS
	/**
	 * Calculates margin left by examing all parents and containers magins the
	 * current element lives in if any
	 * 
	 * @param childArg
	 * @return int calculated value for margin
	 */
	public int calculateMarginLeft(Element childArg) {
		if (childArg.getParent() != null) {
			int left = 0;
			childArg.setMarginLeft(left);

			// append container margins
			Container containerArg = childArg.getContainer();
			if (containerArg != null) {
				left += containerArg.getMarginLeft();
			}

			Element parentArg = childArg.getParent();
			while (!(parentArg == null)) {
				left += parentArg.getMarginLeft();
				parentArg = parentArg.getParent();
			}

			return left;
		}
		return childArg.marginLeft;
	}

	/**
	 * Calculates margin left by examing all parents and containers magins the
	 * current element lives in if any
	 * 
	 * @param childArg
	 * @return int calculated value for margin
	 */
	public int calculateMarginTop(Element childArg) {
		if (childArg.getParent() != null) {
			int top = 0;
			childArg.setMarginTop(0);

			// append container margins
			Container containerArg = childArg.getContainer();
			if (containerArg != null) {
				top += containerArg.getMarginTop();
			}

			Element parentArg = (childArg.getParent());
			while (!(parentArg == null)) {
				top += parentArg.marginTop;
				parentArg = parentArg.getParent();
			}

			return top;
		}
		return childArg.marginTop;
	}

	/**
	 * Prints all attributes for debugging
	 * 
	 * @param x
	 * @return no return value
	 */
	public String toString(String x) {
		System.out.printf("\n\n%s\n%s\n	  marginLeft, marginTop: (%s,%s)", x,
				this.name, this.marginLeft, this.marginTop);
		System.out.printf("\n	paddingLeft, paddingTop: (%s,%s)",
				this.paddingLeft, this.paddingTop);
		System.out.printf("\n	      render X, renderY: (%s,%s)", this.renderX,
				this.renderY);
		System.out.printf("\n      		  width, heigth: (%s,%s)\n", this.width,
				this.height);
		if (container != null)
			System.out.printf(
					"\n\tcontainer:\n\t\t(%s,%s)\n\t\tContainerOption: %s\n",
					this.container.marginLeft, this.container.marginTop,
					this.containerOption);
		if (parent != null)
			System.out.printf("\n\tparent:\n\t\t(%s,%s)",
					this.parent.marginLeft, this.parent.marginTop);
		if (x == "2") {
			System.out.println("\n___________________________________________");
			System.out.println("___________________________________________\n");
		}
		return null;
	}
}

/*
 * / Set the x coordinate to the grid of the elements on the screen using the x
 * parameter as a plot number.
 *
 * @param x is the plot number.
 *
 * protected void setRenderX(Element childArg, int columnIndex) { if
 * (getParent(childArg) != null) { int tmpPaddingLeft =
 * getPaddingLeft(childArg); int tmpMarginLeft = getMarginLeft(childArg);
 *
 * int innerPadding = tmpPaddingLeft; int outerPadding = tmpPaddingLeft;
 *
 * System.out.printf( "\n setRenderX:\n " + "int renderX = %s + (%s * ((%s +
 * %s)))", tmpMarginLeft, columnIndex, innerPadding, getWidth(childArg));
 *
 * //if (columnIndex == 0) { //this.renderX = tmpMarginLeft + tmpPaddingLeft //}
 * if (true) { this.renderX = tmpMarginLeft + outerPadding + (columnIndex *
 * (innerPadding + getWidth(childArg))); } } else { this.renderX =
 * getMarginLeft(childArg); } }
 *
 **
 * 
 * Set the y coordinate to the grid of the elements on the screen using the y
 * parameter as a plot number.
 *
 * @param y is the plot number.
 *
 * protected void setRenderY(Element childArg, int rowIndex) { if
 * (getParent(childArg) != null) { int tmpPaddingTop = getPaddingTop(childArg);
 * int tmpMarginTop = getMarginTop(childArg); int childArgHeight = (int)
 * childArg.getB();
 *
 * int innerPadding = ((int) (tmpPaddingTop / childArg.getN())); int
 * outerPadding = ((int) (tmpPaddingTop / childArg.getN()));
 *
 * System.out.printf( "\n setRenderY:\n " + "int renderY = %s + (%s * ((%s +
 * %s)))", tmpMarginTop, rowIndex, innerPadding, childArgHeight);
 *
 * this.renderY = tmpMarginTop + outerPadding + (rowIndex * (innerPadding +
 * childArgHeight)); } else { this.renderY = getMarginTop(childArg); } }
 * 
 * ** Sets Padding variables depending on standard button width.
 *
 * @param no params
 * 
 * @return no return value
 *
 *
 * public void adjustMarginsFromParentElementsMarings() { Element e = this; int
 * left = 0 + getMarginLeft(); int top = 0 + getMarginTop();
 *
 * while (e.hasParentElement()) { e = e.getParentElement(); left +=
 * e.getMarginLeft(); top += e.getMarginTop(); }
 *
 * System.out.printf("\nleft %s, top %s\n", left, top); setMarginLeft(left);
 * setMarginTop(top); }
 *
 * /
 */
