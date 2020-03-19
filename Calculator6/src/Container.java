/**
 * Container is a host elements that holds interactable elements to ensure
 * proper rendering for margins and paddings
 *
 * @author Chadwick Cheney
 *
 */
public class Container extends Element {

	protected Element element;

	/**
	 * Container Constructor.
	 * 
	 * @param argMarginLeft
	 * @param argMarginTop
	 */
	public Container(int argMarginLeft, int argMarginTop) {
		super(argMarginLeft, argMarginTop);
		setMarginLeft(argMarginLeft);
		setMarginTop(argMarginTop);
	}

	/**
	 * Sets the elements object to determine what element lives in the current
	 * container
	 * 
	 * @param childArg
	 * @return no return value
	 */
	public void setElement(Element childArg) {
		this.element = childArg;
	}

}
