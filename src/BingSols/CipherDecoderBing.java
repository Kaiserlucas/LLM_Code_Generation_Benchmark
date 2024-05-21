package BingSols;

import exceptions.InvalidEncodingException;
import tasks.CipherDecoder;

import java.util.HashMap;
import java.util.Map;

public class CipherDecoderBing implements CipherDecoder {

    // ASCII binary to text mapping
    private static final Map<String, Character> BINARY_TO_ASCII_MAP = new HashMap<>();
    static {
        BINARY_TO_ASCII_MAP.put("01000001", 'A');
        // Add mappings for other characters...
    }

    @Override
    public String binaryToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        String[] binaryBlocks = input.split("\\s+"); // Split by spaces
        for (String block : binaryBlocks) {
            if (block.length() != 8) {
                throw new InvalidEncodingException("Invalid binary format");
            }
            char asciiChar = BINARY_TO_ASCII_MAP.getOrDefault(block, '?'); // Replace with '?' for unknown characters
            result.append(asciiChar);
        }
        return result.toString();
    }

    @Override
    public String morseToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        String[] morseLetters = input.split("/");

        // Define a mapping from Morse code to uppercase characters
        Map<String, Character> morseToChar = new HashMap<>();
        morseToChar.put(".-", 'A');
        morseToChar.put("-...", 'B');
        morseToChar.put("-.-.", 'C');
        morseToChar.put("-..", 'D');
        morseToChar.put(".", 'E');
        morseToChar.put("..-.", 'F');
        morseToChar.put("--.", 'G');
        morseToChar.put("....", 'H');
        morseToChar.put("..", 'I');
        morseToChar.put(".---", 'J');
        morseToChar.put("-.-", 'K');
        morseToChar.put(".-..", 'L');
        morseToChar.put("--", 'M');
        morseToChar.put("-.", 'N');
        morseToChar.put("---", 'O');
        morseToChar.put(".---.", 'P');
        morseToChar.put("--.-", 'Q');
        morseToChar.put(".-.", 'R');
        morseToChar.put("...", 'S');
        morseToChar.put("-", 'T');
        morseToChar.put("..-", 'U');
        morseToChar.put("...-", 'V');
        morseToChar.put(".--", 'W');
        morseToChar.put("-..-", 'X');
        morseToChar.put("-.--", 'Y');
        morseToChar.put("--..", 'Z');
        morseToChar.put(".----", '1');
        morseToChar.put("..---", '2');
        morseToChar.put("...--", '3');
        morseToChar.put("....-", '4');
        morseToChar.put(".....", '5');
        morseToChar.put("-....", '6');
        morseToChar.put("--...", '7');
        morseToChar.put("---..", '8');
        morseToChar.put("----.", '9');
        morseToChar.put("-----", '0');
        morseToChar.put("--..--", ',');
        morseToChar.put(".-.-.-", '.');
        morseToChar.put("..--..", '?');

        for (String morse : morseLetters) {
            if (morse.isEmpty()) {
                // Handle word boundaries (double slash)
                result.append(' ');
            } else {
                // Convert Morse to uppercase character
                Character decodedChar = morseToChar.get(morse);
                if (decodedChar != null) {
                    result.append(decodedChar);
                } else {
                    throw new InvalidEncodingException("Invalid Morse code: " + morse);
                }
            }
        }
        return result.toString();
    }

    @Override
    public String rot13ToText(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char decodedChar = (char) (((c - base + 13) % 26) + base);
                result.append(decodedChar);
            } else {
                result.append(c); // Non-letter characters remain unchanged
            }
        }
        return result.toString();
    }

    @Override
    public String tapCodeToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        String[] pairs = input.split("\\s+");
        for (String pair : pairs) {
            int row = Integer.parseInt(pair.substring(0, 1));
            int col = Integer.parseInt(pair.substring(1));
            // Assuming the grid is 5x5 with letters A-Z (excluding J/K)
            char decodedChar = (char) ('A' + (row - 1) * 5 + col - 1);
            result.append(decodedChar);
        }
        return result.toString();
    }

    @Override
    public String natoAlphabetToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        String[] words = input.split("\\s+");
        for (String word : words) {
            switch (word) {
                case "ALFA": result.append('A'); break;
                case "BRAVO": result.append('B'); break;
                case "CHARLIE": result.append('C'); break;
                case "DELTA": result.append('D'); break;
                case "ECHO": result.append('E'); break;
                case "FOXTROT": result.append('F'); break;
                case "GOLF": result.append('G'); break;
                case "HOTEL": result.append('H'); break;
                case "INDIA": result.append('I'); break;
                case "JULIETT": result.append('J'); break;
                case "KILO": result.append('K'); break;
                case "LIMA": result.append('L'); break;
                case "MIKE": result.append('M'); break;
                case "NOVEMBER": result.append('N'); break;
                case "OSCAR": result.append('O'); break;
                case "PAPA": result.append('P'); break;
                case "QUEBEC": result.append('Q'); break;
                case "ROMEO": result.append('R'); break;
                case "SIERRA": result.append('S'); break;
                case "TANGO": result.append('T'); break;
                case "UNIFORM": result.append('U'); break;
                case "VICTOR": result.append('V'); break;
                case "WHISKEY": result.append('W'); break;
                case "XRAY": result.append('X'); break;
                case "YANKEE": result.append('Y'); break;
                case "ZULU": result.append('Z'); break;
                default: throw new InvalidEncodingException("Unknown NATO word: " + word);
            }
        }
        return result.toString();
    }

    @Override
    public String identifyCipherAndDecode(String input) throws InvalidEncodingException {
        // Remove any leading/trailing spaces
        input = input.trim();

        // Check if the input is binary (ASCII)
        if (input.matches("[01 ]+")) {
            return binaryToText(input);
        }

        // Check if the input is Morse code
        if (input.matches("[.-/ ]+")) {
            return morseToText(input);
        }

        // Check if the input is ROT13 encoded
        if (input.matches("[A-Za-z ]+")) {
            return rot13ToText(input);
        }

        // Check if the input is tap code
        if (input.matches("[0-9 ]+")) {
            return tapCodeToText(input);
        }

        // Check if the input is NATO alphabet
        if (input.matches("[A-Z ]+")) {
            return natoAlphabetToText(input);
        }

        throw new InvalidEncodingException("Unknown or unsupported encoding: " + input);
    }

}