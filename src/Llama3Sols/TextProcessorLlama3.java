package Llama3Sols;

import tasks.TextProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessorLlama3 implements TextProcessor {

    @Override
    public String readFile(String path) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
        }
        return text.toString();
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
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : words) {
            word = word.toLowerCase();
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }
        String mostFrequentWord = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
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
            word = word.toLowerCase();
            wordSet.put(word, true);
        }
        for (String word : words2) {
            word = word.toLowerCase();
            if (wordSet.containsKey(word) && !matchingWords.contains(word)) {
                matchingWords.add(word);
            }
        }
        return matchingWords;
    }
}