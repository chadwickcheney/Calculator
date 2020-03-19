import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Background is the background color of the application window and is not
 * interactable
 *
 * @author Chadwick Cheney
 *
 */
public class Background extends Element {

	/**
	 * Background constructor.
	 *
	 * @param columnIndex is the x axis coordinate to render the currentImage
	 *                    variable to.
	 * @param rowIndex    is the y axis coordinate to render the currentImage
	 *                    variable to.
	 * @param frames      is the list of frames available to the object.
	 * @see #setName(String)
	 * @see #setParent(Element)
	 * @see #setMarginLeft(int)
	 * @see #setMarginTop(int)
	 * @see #setWidth(int)
	 * @see #setHeight(int)
	 * @see #setRenderX( @see {@link #calculateRenderX(Element, int, int, int)}
	 * @see #setRenderY( @see {@link #calculateRenderY(Element, int, int, int)}
	 * @see #setEdges()
	 */
	public Background(int columnIndex, int rowIndex,
			ArrayList<BufferedImage> frames, Element parentArg) {
		super(columnIndex, rowIndex, frames, parentArg);
		setName("background");
		setParent(parentArg);
		setMarginLeft(0);
		setMarginTop(25);
		setWidth(currentImage.getWidth());
		setHeight(currentImage.getHeight());
		setRenderX(calculateRenderX(this, columnIndex, getPaddingLeft(),
				getMarginLeft()));
		setRenderY(calculateRenderY(this, rowIndex, getPaddingTop(),
				getMarginTop()));
		setEdges();
	}
}
