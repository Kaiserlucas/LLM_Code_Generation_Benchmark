package GeminiSols;

import tasks.TextProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TextProcessorGemini implements TextProcessor {

    @Override
    public String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String returnLongestWord(String path) throws IOException {
        String content = readFile(path);
        String[] words = content.split("\\s+");  // Split by whitespace
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
        String content = readFile(path).toLowerCase();  // Convert to lowercase
        String[] words = content.split("\\s+");

        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            int count = wordCount.getOrDefault(word, 0);
            wordCount.put(word, count + 1);
        }

        int maxCount = 0;
        String mostFrequentWord = "";
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentWord = entry.getKey();
            }
        }
        return mostFrequentWord;
    }

    @Override
    public List<String> compareTextsForMatchingWords(String path1, String path2) throws IOException {
        Set<String> text1Words = new HashSet<>(toLowerCaseSet(readFile(path1)));
        Set<String> text2Words = new HashSet<>(toLowerCaseSet(readFile(path2)));

        text1Words.retainAll(text2Words);  // Keep only words in both sets
        return new ArrayList<>(text1Words);  // Convert to list and remove duplicates
    }

    private Set<String> toLowerCaseSet(String content) {
        String[] words = content.split("\\s+");
        Set<String> lowerCaseWords = new HashSet<>();
        for (String word : words) {
            lowerCaseWords.add(word.toLowerCase());
        }
        return lowerCaseWords;
    }
}
