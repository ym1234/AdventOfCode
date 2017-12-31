import java.nio.file.*;
import java.util.*;
import java.io.*;
public class HighEntropyPassphrases {
	public static void main(String[] args) throws Exception {

		System.out.println(
		Files.readAllLines(Paths.get(args[0])).stream().map(i ->i.split("\\s+")).filter(i -> {
			for (int j = 0; j < i.length; j++) {
				for (int k = 0; k < i.length; k++) {
					if (j != k && isPliandrome(i[j], i[k])) {
						return false;
					}
				}
			}
			return true;
		}).count());

		/*
					for (String line : input) {
					String[] words = line.split("\\s+");
						outer: for (int i = 0; i < words.length; i++) {
							for (int j = 0; j < words.length; j++) {
								if (words[i].equals(words[j]) && i != j) {
									valid--;
									break outer;
								}
								if (words[i].length() == words[j].length() && i != j) {
									HashMap<Character, Integer> hashMap2 = new HashMap<>();
									for (int k = 0; k < wordChar.length; k++) {
										hashMap2.put(wordCharJ[k], hashMap2.get(wordCharJ[k]) == null ? 1 : hashMap2.get(wordCharJ[k]) + 1);
									}
									if (hashMap.equals(hashMap2)) {
										valid--;
										break outer;
									}
								}
							}
						}
						valid++;
					}
					System.out.println(valid);
					*/
	}

	public static boolean isPliandrome(String word, String otherWord) {
		if (word.length() != otherWord.length()) return false;
		if (word.equals(otherWord)) return true;
		char[] wordChar = word.toCharArray();
		char[] wordCharJ = otherWord.toCharArray();
		Arrays.sort(wordChar);
		Arrays.sort(wordCharJ);
		return wordChar.equals(wordCharJ);

		/* 
		HashMap<Character, Integer> hashMap = new HashMap<>();

		for (int i = 0; i < word.length(); i++) {
			hashMap.put(word.charAt(i), hashMap.get(word.charAt(i)) == null ? 1 : hashMap.get(word.charAt(i)) + 1);
		}

		for (int i = 0; i < otherWord.length(); i++) {
			hashMap.put(otherWord.charAt(i), hashMap.get(otherWord.charAt(i)) == null ? -1 : hashMap.get(otherWord.charAt(i)) - 1);
		}

		return hashMap.entrySet().stream().mapToInt(i -> i.getValue()).sum() == 0;
		*/
	}
}

