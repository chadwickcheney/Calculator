import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Hosts the Background element for main window view
 *
 * @author Chadwick Cheney
 *
 */
public class Frame extends Element {

	/**
	 * Frame Constructor.
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @param frames
	 * @param parentElement
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
	public Frame(int rowIndex, int columnIndex, ArrayList<BufferedImage> frames,
			Element parentElement) {
		super(rowIndex, columnIndex, frames, parentElement);
		// TODO Auto-generated constructor stub
		
		setName("frame");
		setMarginLeft(0);
		setMarginTop(0);
		setWidth(currentImage.getWidth());
		setHeight(currentImage.getHeight());
		calculateRenderX(this, columnIndex, getPaddingLeft(), getMarginLeft());
		calculateRenderY(this, rowIndex, getPaddingTop(), getMarginTop());
		setEdges();
	}
}
