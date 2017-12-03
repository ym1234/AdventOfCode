import java.nio.file.*;
import java.util.*;

public class PerfectlySphericalHousesInaVacuum {
	public static void main(String[] args) {
		try {
			char[] input = Files.readAllLines(Paths.get(args[0])).get(0).toCharArray();
			HashSet < HashMap.Entry<Integer, Integer>> set = new HashSet<>();

			int xSanta = 0, ySanta = 0;
			int xRobo = 0, yRobo = 0;
			boolean santa = true;
			for (char ch : input) {

				set.add(new AbstractMap.SimpleEntry<Integer, Integer>(santa ? xSanta : xRobo, santa ? ySanta : yRobo));

				switch (ch) {
				case '^': if (santa) { ySanta++; } else { yRobo++; }; break;
				case 'v': if (santa) { ySanta--; } else { yRobo--; }; break;
				case '>': if (santa) { xSanta++; } else { xRobo++; }; break;
				case '<': if (santa) { xSanta--; } else { xRobo--; }; break;
				}

				santa = !santa;
			}
			System.out.println(set.size());
		} catch (Exception e) {}
	}
}