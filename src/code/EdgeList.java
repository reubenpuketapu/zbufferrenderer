package code;

public class EdgeList {

	private float[] coordinates = new float[4];

	public EdgeList() {
		coordinates[0] = Float.POSITIVE_INFINITY;
		coordinates[1] = Float.POSITIVE_INFINITY;
		coordinates[2] = Float.NEGATIVE_INFINITY;
		coordinates[3] = Float.POSITIVE_INFINITY;
	}

	public void addLeft(float xL, float zL) {
		coordinates[0] = xL;
		coordinates[1] = zL;
	}

	public void addRight(float xR, float zR) {
		coordinates[2] = xR;
		coordinates[3] = zR;
	}

	public float getLeftX() {
		return coordinates[0];
	}

	public float getLeftZ() {
		return coordinates[1];
	}

	public float getRightX() {
		return coordinates[2];
	}

	public float getRightZ() {
		return coordinates[3];
	}
}