package code;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class Renderer extends GUI {

	private static Color[][] screen;
	private static float[][] zBuffer;

	private float shift = 0.1f;

	private BoundingBox bounding;

	private ArrayList<Polygon> triangles = new ArrayList<Polygon>();

	public Renderer() {
		Renderer.screen = new Color[CANVAS_HEIGHT][CANVAS_WIDTH];
		Renderer.zBuffer = new float[CANVAS_HEIGHT][CANVAS_WIDTH];
	}

	@Override
	protected void onLoad(File file) {
		triangles.clear();
		triangles.addAll(Parser.parsePolygons(file));
		initialisezBuffer();
		bounding = null;
		System.out.println("Loaded");

	}

	@Override
	protected void onKeyPress(KeyEvent ev) {
		if (ev.getKeyCode() == KeyEvent.VK_LEFT) {

			Movements.rotateRight(-shift, triangles);

		} else if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {

			Movements.rotateRight(shift, triangles);

		} else if (ev.getKeyCode() == KeyEvent.VK_DOWN) {

			Movements.rotateDown(shift, triangles);

		} else if (ev.getKeyCode() == KeyEvent.VK_UP) {

			Movements.rotateDown(-shift, triangles);

		}

	}

	private void moveIntoView() {

		if (bounding == null) {

			bounding = createBoundingBox();
			float scaleX = 500f / bounding.getWidth();
			float scaleY = 500f / bounding.getHeight();
			float scale = Math.max(scaleX, scaleY) / 2;

			Movements.scale(scale, triangles);
		}

		bounding = createBoundingBox();

		System.out.println("Bounds: " + bounding.getX() + ", "
				+ bounding.getY() + ", " + bounding.getWidth() + ", "
				+ bounding.getHeight());

		float shiftX = bounding.getX();
		float shiftY = bounding.getY();

		Movements.translation(-shiftX + 50, -shiftY + 50, triangles);

	}

	@Override
	protected BufferedImage render() {

		initialisezBuffer();
		if (triangles.isEmpty()) {
			return null;
		}

		moveIntoView();

		for (Polygon p : triangles) {
			p.shading(getAmbientLight());
			EdgeList[] edgelist = p.computeEdgeList();
			Color c = p.getReflectivity();
			for (int y = 0; y < edgelist.length - 1; y++) {

				int x = Math.round(edgelist[y].getLeftX());
				float z = edgelist[y].getLeftZ();

				float mz = (edgelist[y].getRightZ() - edgelist[y].getLeftZ())
						/ (edgelist[y].getRightX() - edgelist[y].getLeftX());

				while (x <= Math.round(edgelist[y].getRightX())) {

					if (z < zBuffer[x][y]) {
						zBuffer[x][y] = z;
						screen[x][y] = c;
					}
					x++;
					z += mz;
				}

			}

		}

		// render the bitmap to the image so it can be displayed (and saved)
		return convertBitmapToImage(screen);
	}

	private BufferedImage convertBitmapToImage(Color[][] bitmap) {
		BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				image.setRGB(x, y, bitmap[x][y].getRGB());
			}
		}
		return image;
	}

	private BoundingBox createBoundingBox() {

		int maxX = Integer.MIN_VALUE;
		int minX = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;

		for (Polygon p : triangles) {
			for (Vector3D v : p.getCoordinates()) {
				if (v.x > maxX) {
					maxX = Math.round(v.x);
				}
				if (v.x < minX) {
					minX = Math.round(v.x);
				}
				if (v.y > maxY) {
					maxY = Math.round(v.y);
				}
				if (v.y < minY) {
					minY = Math.round(v.y);
				}
			}
		}
		return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
	}

	private void initialisezBuffer() {
		for (int i = 0; i < CANVAS_HEIGHT; i++) {
			for (int j = 0; j < CANVAS_WIDTH; j++) {
				screen[i][j] = Color.gray;
				zBuffer[i][j] = Integer.MAX_VALUE;
			}
		}
	}

	public static void main(String[] args) {
		Renderer r = new Renderer();
		r.initialisezBuffer();
	}

}
