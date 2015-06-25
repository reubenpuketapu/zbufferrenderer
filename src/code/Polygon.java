package code;

import java.awt.Color;
import java.util.*;

public class Polygon {

	private Vector3D light;
	private Vector3D normal;
	private Color reflectivity;
	private Color color;
	private ArrayList<Vector3D> vectors;
	private ArrayList<Edge> edges;
	private boolean visible = false;

	public Polygon(ArrayList<Vector3D> vectors, Vector3D light, Color color) {
		this.light = light;
		this.reflectivity = color;
		this.vectors = vectors;
		this.edges = getEdges();
		calcNormal();
	}

	public ArrayList<Edge> getEdges() {

		ArrayList<Edge> edges = new ArrayList<Edge>();
		edges.add(new Edge(vectors.get(0), vectors.get(1)));
		edges.add(new Edge(vectors.get(1), vectors.get(2)));
		edges.add(new Edge(vectors.get(2), vectors.get(0)));

		return edges;
	}

	public EdgeList[] computeEdgeList() {

		int maxY = Math.round(getMaxY());

		EdgeList[] edgeList = new EdgeList[maxY + 1];
		for (int i = 0; i < edgeList.length; i++) {
			edgeList[i] = new EdgeList();
		}

		for (Edge edge : edges) {
			Vector3D va = getSmallest(edge.getV1(), edge.getV2());
			Vector3D vb = getLargest(edge.getV1(), edge.getV2());

			float mx = (vb.x - va.x) / (vb.y - va.y);
			float mz = (vb.z - va.z) / (vb.y - va.y);

			float x = va.x;
			float z = va.z;

			int i = Math.round(va.y);
			int maxI = Math.round(vb.y);

			while (i < maxI) {
				if (x < edgeList[i].getLeftX()) {
					edgeList[i].addLeft(x, z);
				}
				if (x > edgeList[i].getRightX()) {
					edgeList[i].addRight(x, z);
				}
				i++;
				x += mx;
				z += mz;
			}

		}

		return edgeList;
	}

	private void calcNormal() {
		Vector3D first = vectors.get(0);
		Vector3D second = vectors.get(1);
		Vector3D third = vectors.get(2);

		Vector3D secondV = second.minus(first);
		Vector3D thirdV = third.minus(second);

		normal = secondV.crossProduct(thirdV).unitVector();

		if (normal.z > 0) {
			visible = false;
		} else {
			visible = true;
		}

	}

	public Vector3D getSmallest(Vector3D v1, Vector3D v2) {
		if (v1.y < v2.y) {
			return v1;
		} else {
			return v2;
		}
	}

	public Vector3D getLargest(Vector3D v1, Vector3D v2) {
		if (v1.y > v2.y) {
			return v1;
		} else {
			return v2;
		}
	}

	public void shading(int[] ambient) {
		float r = checkBounds((ambient[0] / 200f + 0.5f * normal
				.dotProduct(light)) * reflectivity.getRed() / 255f);
		float g = checkBounds((ambient[1] / 200f + 0.5f * normal
				.dotProduct(light)) * reflectivity.getGreen() / 255f);
		float b = checkBounds((ambient[2] / 200f + 0.5f * normal
				.dotProduct(light)) * reflectivity.getBlue() / 255f);

		this.color = new Color(r, g, b);

	}

	public float checkBounds(float num) {
		if (num < 0) {
			return 0f;
		} else if (num > 1f) {
			return 1f;
		} else {
			return num;
		}
	}

	private float getMaxY() {
		float max = Float.MIN_VALUE;
		for (Vector3D v : vectors) {
			if (v.y > max) {
				max = v.y;
			}
		}
		return max;
	}

	public boolean isVisible() {
		return visible;
	}

	public Vector3D getLight() {
		return light;
	}

	public Color getReflectivity() {
		return color;
	}

	public Color getColor() {
		return reflectivity;
	}

	public ArrayList<Vector3D> getCoordinates() {
		return vectors;
	}
}
