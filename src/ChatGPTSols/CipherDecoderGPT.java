package ChatGPTSols;

import exceptions.InvalidEncodingException;
import tasks.CipherDecoder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CipherDecoderGPT implements CipherDecoder {

    @Override
    public String binaryToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        String[] blocks = input.split("\\s+");
        for (String block : blocks) {
            if (!block.matches("[01]+")) {
                throw new InvalidEncodingException("Invalid binary format");
            }
            int asciiValue = Integer.parseInt(block, 2);
            result.append((char) asciiValue);
        }
        return result.toString();
    }

    @Override
    public String morseToText(String input) throws InvalidEncodingException {
        Map<String, String> morseToChar = new HashMap<>();
        morseToChar.put(".-", "A");
        morseToChar.put("-...", "B");
        morseToChar.put("-.-.", "C");
        morseToChar.put("-..", "D");
        morseToChar.put(".", "E");
        morseToChar.put("..-.", "F");
        morseToChar.put("--.", "G");
        morseToChar.put("....", "H");
        morseToChar.put("..", "I");
        morseToChar.put(".---", "J");
        morseToChar.put("-.-", "K");
        morseToChar.put(".-..", "L");
        morseToChar.put("--", "M");
        morseToChar.put("-.", "N");
        morseToChar.put("---", "O");
        morseToChar.put(".--.", "P");
        morseToChar.put("--.-", "Q");
        morseToChar.put(".-.", "R");
        morseToChar.put("...", "S");
        morseToChar.put("-", "T");
        morseToChar.put("..-", "U");
        morseToChar.put("...-", "V");
        morseToChar.put(".--", "W");
        morseToChar.put("-..-", "X");
        morseToChar.put("-.--", "Y");
        morseToChar.put("--..", "Z");
        morseToChar.put("-----", "0");
        morseToChar.put(".----", "1");
        morseToChar.put("..---", "2");
        morseToChar.put("...--", "3");
        morseToChar.put("....-", "4");
        morseToChar.put(".....", "5");
        morseToChar.put("-....", "6");
        morseToChar.put("--...", "7");
        morseToChar.put("---..", "8");
        morseToChar.put("----.", "9");

        StringBuilder result = new StringBuilder();
        String[] words = input.split("//");
        for (String word : words) {
            String[] letters = word.split("/");
            for (String letter : letters) {
                if (!morseToChar.containsKey(letter)) {
                    throw new InvalidEncodingException("Invalid Morse code format");
                }
                result.append(morseToChar.get(letter));
            }
            result.append(" ");
        }
        return result.toString().trim();
    }

    @Override
    public String rot13ToText(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) ((c - base + 13) % 26 + base));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    @Override
    public String tapCodeToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        Map<String, Character> tapCodeToChar = new HashMap<>();
        tapCodeToChar.put("11", 'A');
        tapCodeToChar.put("12", 'B');
        tapCodeToChar.put("13", 'C');
        tapCodeToChar.put("14", 'D');
        tapCodeToChar.put("15", 'E');
        tapCodeToChar.put("21", 'F');
        tapCodeToChar.put("22", 'G');
        tapCodeToChar.put("23", 'H');
        tapCodeToChar.put("24", 'I');
        tapCodeToChar.put("25", 'J');
        tapCodeToChar.put("31", 'L');
        tapCodeToChar.put("32", 'M');
        tapCodeToChar.put("33", 'N');
        tapCodeToChar.put("34", 'O');
        tapCodeToChar.put("35", 'P');
        tapCodeToChar.put("41", 'Q');
        tapCodeToChar.put("42", 'R');
        tapCodeToChar.put("43", 'S');
        tapCodeToChar.put("44", 'T');
        tapCodeToChar.put("45", 'U');
        tapCodeToChar.put("51", 'V');
        tapCodeToChar.put("52", 'W');
        tapCodeToChar.put("53", 'X');
        tapCodeToChar.put("54", 'Y');
        tapCodeToChar.put("55", 'Z');

        String[] pairs = input.split("\\s+");
        for (String pair : pairs) {
            if (!pair.matches("\\d{2}")) {
                throw new InvalidEncodingException("Invalid tap code format");
            }
            if (!tapCodeToChar.containsKey(pair)) {
                throw new InvalidEncodingException("Invalid tap code value");
            }
            result.append(tapCodeToChar.get(pair));
        }
        return result.toString();
    }

    @Override
    public String natoAlphabetToText(String input) throws InvalidEncodingException {
        Map<String, Character> natoAlphabetToChar = new HashMap<>();
        natoAlphabetToChar.put("ALFA", 'A');
        natoAlphabetToChar.put("BRAVO", 'B');
        natoAlphabetToChar.put("CHARLIE", 'C');
        natoAlphabetToChar.put("DELTA", 'D');
        natoAlphabetToChar.put("ECHO", 'E');
        natoAlphabetToChar.put("FOXTROT", 'F');
        natoAlphabetToChar.put("GOLF", 'G');
        natoAlphabetToChar.put("HOTEL", 'H');
        natoAlphabetToChar.put("INDIA", 'I');
        natoAlphabetToChar.put("JULIETT", 'J');
        natoAlphabetToChar.put("KILO", 'K');
        natoAlphabetToChar.put("LIMA", 'L');
        natoAlphabetToChar.put("MIKE", 'M');
        natoAlphabetToChar.put("NOVEMBER", 'N');
        natoAlphabetToChar.put("OSCAR", 'O');
        natoAlphabetToChar.put("PAPA", 'P');
        natoAlphabetToChar.put("QUEBEC", 'Q');
        natoAlphabetToChar.put("ROMEO", 'R');
        natoAlphabetToChar.put("SIERRA", 'S');
        natoAlphabetToChar.put("TANGO", 'T');
        natoAlphabetToChar.put("UNIFORM", 'U');
        natoAlphabetToChar.put("VICTOR", 'V');
        natoAlphabetToChar.put("WHISKEY", 'W');
        natoAlphabetToChar.put("XRAY", 'X');
        natoAlphabetToChar.put("YANKEE", 'Y');
        natoAlphabetToChar.put("ZULU", 'Z');

        StringBuilder result = new StringBuilder();
        String[] words = input.split("\\s+");
        for (String word : words) {
            if (!natoAlphabetToChar.containsKey(word)) {
                throw new InvalidEncodingException("Invalid NATO alphabet format");
            }
            result.append(natoAlphabetToChar.get(word));
        }
        return result.toString();
    }

    @Override
    public String identifyCipherAndDecode(String input) throws InvalidEncodingException {
        // Check if input is binary
        if (input.matches("[01\\s]+")) {
            return binaryToText(input);
        }

        // Check if input is Morse code
        if (input.matches("[.-]+")) {
            return morseToText(input);
        }

        // Check if input is Rot13
        Pattern rot13Pattern = Pattern.compile("[a-zA-Z]+");
        Matcher rot13Matcher = rot13Pattern.matcher(input);
        if (rot13Matcher.matches()) {
            return rot13ToText(input);
        }

        // Check if input is Tap code
        Pattern tapCodePattern = Pattern.compile("\\d{2}(\\s+\\d{2})*");
        Matcher tapCodeMatcher = tapCodePattern.matcher(input);
        if (tapCodeMatcher.matches()) {
            return tapCodeToText(input);
        }

        // Check if input is NATO alphabet
        Pattern natoAlphabetPattern = Pattern.compile("(ALPHA|BRAVO|CHARLIE|DELTA|ECHO|FOXTROT|GOLF|HOTEL|INDIA|JULIETT|KILO|LIMA|MIKE|NOVEMBER|OSCAR|PAPA|QUEBEC|ROMEO|SIERRA|TANGO|UNIFORM|VICTOR|WHISKEY|XRAY|YANKEE|ZULU)(\\s+(ALPHA|BRAVO|CHARLIE|DELTA|ECHO|FOXTROT|GOLF|HOTEL|INDIA|JULIETT|KILO|LIMA|MIKE|NOVEMBER|OSCAR|PAPA|QUEBEC|ROMEO|SIERRA|TANGO|UNIFORM|VICTOR|WHISKEY|XRAY|YANKEE|ZULU))*");
        Matcher natoAlphabetMatcher = natoAlphabetPattern.matcher(input);
        if (natoAlphabetMatcher.matches()) {
            return natoAlphabetToText(input);
        }

        throw new InvalidEncodingException("Unable to identify the cipher");
    }
}

