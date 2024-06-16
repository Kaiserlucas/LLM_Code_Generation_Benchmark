package ChatGPTSols;

import tasks.TextProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessorGPT implements TextProcessor {

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
            String normalizedWord = word.toLowerCase();
            wordFrequency.put(normalizedWord, wordFrequency.getOrDefault(normalizedWord, 0) + 1);
        }
        String mostFrequentWord = "";
        int maxFrequency = 0;
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostFrequentWord = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return mostFrequentWord;
    }

    @Override
    public List<String> compareTextsForMatchingWords(String path1, String path2) throws IOException {
        String text1 = readFile(path1);
        String text2 = readFile(path2);
        String[] words1 = text1.toLowerCase().split("\\s+");
        String[] words2 = text2.toLowerCase().split("\\s+");
        Map<String, Boolean> wordMap = new HashMap<>();
        for (String word : words1) {
            wordMap.put(word, true);
        }
        List<String> commonWords = new ArrayList<>();
        for (String word : words2) {
            if (wordMap.containsKey(word) && !commonWords.contains(word)) {
                commonWords.add(word);
            }
        }
        return commonWords;
    }
}
