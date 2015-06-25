package code;

public class Edge {

	private Vector3D v1;
	private Vector3D v2;

	public Edge(Vector3D v1, Vector3D v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	public Vector3D getV1() {
		return v1;
	}

	public Vector3D getV2() {
		return v2;
	}

}
