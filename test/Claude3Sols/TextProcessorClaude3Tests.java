package Claude3Sols;


import org.junit.Test;
import tasks.TextProcessor;

import java.io.IOException;
import java.util.List;


public class TextProcessorClaude3Tests {

    public TextProcessor getTextProcessor() {
        return new TextProcessorClaude3();
    }

    @Test
    public void readFileTest() throws IOException {
        TextProcessor processor = getTextProcessor();
        String text = processor.readFile("resources/ShortFile.txt");
        text = text.replace("\n", "");

        assert(text.equals("Hallo"));
    }

    @Test
    public void findLongestWordTest() throws IOException {
        TextProcessor processor = getTextProcessor();
        String text = processor.returnLongestWord("resources/SampleText.txt");

        assert(text.equals("Rindfleischetikettierungsüberwachungsaufgabenübertragungsgesetz"));
    }

    @Test
    public void findMostFrequentWordTest() throws IOException {
        TextProcessor processor = getTextProcessor();
        String text = processor.returnMostFrequentWord("resources/SampleText.txt");

        assert(text.toLowerCase().equals("wort"));
    }

    //Check if the correct words were found
    @Test
    public void findMatchingWordsContainsTest() throws IOException {
        TextProcessor processor = getTextProcessor();
        List<String> text = processor.compareTextsForMatchingWords("resources/SampleText.txt", "resources/SampleText2.txt");

        assert(text.contains("in"));
        assert(text.contains("language"));
    }

    //Check if the duplicates were correctly eliminated
    @Test
    public void findMatchingWordsSizeTest() throws IOException {
        TextProcessor processor = getTextProcessor();
        List<String> text = processor.compareTextsForMatchingWords("resources/SampleText.txt", "resources/SampleText2.txt");

        assert(text.size() == 2);
    }
}
