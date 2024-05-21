package Claude3Sols;

import tasks.TextProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessorClaude3 implements TextProcessor {

    @Override
    public String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString().trim();
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
        String[] words1 = text1.split("\\s+");
        String[] words2 = text2.split("\\s+");
        List<String> matchingWords = new ArrayList<>();
        Map<String, Boolean> wordSet = new HashMap<>();
        for (String word : words1) {
            String lowercaseWord = word.toLowerCase();
            wordSet.put(lowercaseWord, true);
        }
        for (String word : words2) {
            String lowercaseWord = word.toLowerCase();
            if (wordSet.containsKey(lowercaseWord) && !matchingWords.contains(lowercaseWord)) {
                matchingWords.add(lowercaseWord);
            }
        }
        return matchingWords;
    }
}