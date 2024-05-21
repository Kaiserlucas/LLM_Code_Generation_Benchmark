package BingSols;

import tasks.TextProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TextProcessorBing implements TextProcessor {

    @Override
    public String readFile(String path) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    @Override
    public String returnLongestWord(String path) throws IOException {
        String text = readFile(path);
        String[] words = text.split("\\s+");
        String longestWord = "";
        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }
        return longestWord;
    }

    @Override
    public String returnMostFrequentWord(String path) throws IOException {
        String text = readFile(path);
        String[] words = text.split("\\s+");
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String word : words) {
            String lowercaseWord = word.toLowerCase();
            wordFrequency.put(lowercaseWord, wordFrequency.getOrDefault(lowercaseWord, 0) + 1);
        }
        int maxFrequency = 0;
        String mostFrequentWord = "";
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentWord = entry.getKey();
            }
        }
        return mostFrequentWord;
    }

    @Override
    public List<String> compareTextsForMatchingWords(String path1, String path2) throws IOException {
        String text1 = readFile(path1);
        String text2 = readFile(path2);
        Set<String> words1 = new HashSet<>(Arrays.asList(text1.split("\\s+")));
        Set<String> words2 = new HashSet<>(Arrays.asList(text2.split("\\s+")));
        words1.retainAll(words2); // Intersection of both sets
        return new ArrayList<>(words1);
    }
}
