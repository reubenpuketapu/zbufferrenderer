package code;

import java.util.*;

public class Movements {

	public static void translation(float shiftX, float shiftY,
			ArrayList<Polygon> triangles) {

		Transform move = Transform.newTranslation(shiftX, shiftY, 1);

		for (int i = 0; i < triangles.size(); i++) {
			ArrayList<Vector3D> vectors = new ArrayList<Vector3D>();
			Polygon p = triangles.get(i);
			vectors.add(move.multiply(p.getCoordinates().get(0)));
			vectors.add(move.multiply(p.getCoordinates().get(1)));
			vectors.add(move.multiply(p.getCoordinates().get(2)));
			triangles.set(triangles.indexOf(p),
					new Polygon(vectors, p.getLight(), p.getColor()));
		}
	}

	public static void scale(float scale, ArrayList<Polygon> triangles) {

		Transform move = Transform.newScale(scale, scale, scale);

		for (int i = 0; i < triangles.size(); i++) {
			ArrayList<Vector3D> vectors = new ArrayList<Vector3D>();
			Polygon p = triangles.get(i);
			vectors.add(move.multiply(p.getCoordinates().get(0)));
			vectors.add(move.multiply(p.getCoordinates().get(1)));
			vectors.add(move.multiply(p.getCoordinates().get(2)));
			triangles.set(triangles.indexOf(p),
					new Polygon(vectors, p.getLight(), p.getColor()));
		}
	}

	public static void rotateDown(float shift, ArrayList<Polygon> triangles) {

		Transform move = Transform.newXRotation(shift);

		for (Polygon p : triangles) {
			ArrayList<Vector3D> vectors = new ArrayList<Vector3D>();
			vectors.add(move.multiply(p.getCoordinates().get(0)));
			vectors.add(move.multiply(p.getCoordinates().get(1)));
			vectors.add(move.multiply(p.getCoordinates().get(2)));
			Vector3D light = move.multiply(p.getLight());
			triangles.set(triangles.indexOf(p),
					new Polygon(vectors, light, p.getColor()));
		}
	}

	public static void rotateRight(float shift, ArrayList<Polygon> triangles) {

		Transform move = Transform.newYRotation(-shift);

		for (Polygon p : triangles) {
			ArrayList<Vector3D> vectors = new ArrayList<Vector3D>();
			vectors.add(move.multiply(p.getCoordinates().get(0)));
			vectors.add(move.multiply(p.getCoordinates().get(1)));
			vectors.add(move.multiply(p.getCoordinates().get(2)));
			Vector3D light = move.multiply(p.getLight());
			triangles.set(triangles.indexOf(p),
					new Polygon(vectors, light, p.getColor()));
		}
	}
}
