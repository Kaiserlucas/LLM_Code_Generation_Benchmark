package tasks;

import java.io.IOException;
import java.util.List;

public interface TextProcessor {

    //Reads a text file and returns the entire text
    public String readFile(String path) throws IOException;

    //Reads a text file and then returns the longest word in it
    public String returnLongestWord(String path) throws IOException;

    //Reads a text file and then returns the most frequent word in it
    //Ignore different capitalization and treat them as the same
    public String returnMostFrequentWord(String path) throws IOException;

    //Reads two text files and then returns a list of all words that appear in both texts
    //Every word should only be appear once in the return list
    //Capitalization should be ignored
    public List<String> compareTextsForMatchingWords(String path1, String path2) throws IOException;
}
