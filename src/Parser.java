package code;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Parser {

	public static List<Polygon> parsePolygons(File file) {
		List<Polygon> polys = new ArrayList<Polygon>();

		try {
			// make a reader
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			// read the light coordinates
			line = br.readLine();
			String[] split = line.split(" ");
			Vector3D light = new Vector3D(asFloat(split[0]), asFloat(split[1]),
					asFloat(split[2]));
			line = br.readLine();
			// read in each line of the file
			while (line != null) {

				if (line.isEmpty()) {
					break;
				}

				// tokenise the line by splitting it at the tabs.
				String[] tokens = line.split(" ");

				// parse the coordinates of that polygon
				ArrayList<Vector3D> vectors = new ArrayList<Vector3D>(3);

				for (int i = 0; i < tokens.length - 3; i += 3) {
					float x = asFloat(tokens[i]);
					float y = asFloat(tokens[i + 1]);
					float z = asFloat(tokens[i + 2]);
					vectors.add(new Vector3D(x, y, z));
				}

				// parse the color of that polygon
				int r = asInt(tokens[tokens.length - 3]);
				int g = asInt(tokens[tokens.length - 2]);
				int b = asInt(tokens[tokens.length - 1]);
				Color color = new Color(r, g, b);

				// make the polygon
				Polygon poly = new Polygon(vectors, light, color);
				polys.add(poly);
				line = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			throw new RuntimeException("file reading failed.");
		}

		return polys;
	}

	private static int asInt(String str) {
		return Integer.parseInt(str);
	}

	private static float asFloat(String str) {
		return Float.parseFloat(str);
	}

}
