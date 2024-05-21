package Claude3Sols;

import exceptions.InvalidEncodingException;
import tasks.CipherDecoder;

public class CipherDecoderClaude3 implements CipherDecoder {
    private static final String BINARY_DELIMITER = " ";
    private static final String MORSE_LETTER_DELIMITER = "/";
    private static final String MORSE_WORD_DELIMITER = "//";
    private static final String TAP_CODE_DELIMITER = " ";
    private static final String NATO_ALPHABET_DELIMITER = " ";
    private static final String[] NATO_ALPHABET = {"ALFA", "BRAVO", "CHARLIE", "DELTA", "ECHO", "FOXTROT", "GOLF", "HOTEL", "INDIA", "JULIET", "KILO", "LIMA", "MIKE", "NOVEMBER", "OSCAR", "PAPA", "QUEBEC", "ROMEO", "SIERRA", "TANGO", "UNIFORM", "VICTOR", "WHISKEY", "XRAY", "YANKEE", "ZULU"};

    @Override
    public String binaryToText(String input) throws InvalidEncodingException {
        String[] binaryBlocks = input.split(BINARY_DELIMITER);
        StringBuilder result = new StringBuilder();

        for (String binaryBlock : binaryBlocks) {
            if (binaryBlock.length() != 8) {
                throw new InvalidEncodingException("Invalid binary input: " + binaryBlock);
            }
            int charCode = Integer.parseInt(binaryBlock, 2);
            result.append((char) charCode);
        }

        return result.toString();
    }

    @Override
    public String morseToText(String input) throws InvalidEncodingException {
        String[] morseLetters = input.split(MORSE_LETTER_DELIMITER);
        StringBuilder result = new StringBuilder();

        for (String morseLetterOrWord : morseLetters) {
            if (morseLetterOrWord.isEmpty()) {
                continue;
            }
            String[] morseLetter = morseLetterOrWord.split(MORSE_WORD_DELIMITER);
            for (String letter : morseLetter) {
                result.append(getMorseLetterEquivalent(letter));
            }
            result.append(" ");
        }

        return result.toString().trim().toUpperCase();
    }

    private char getMorseLetterEquivalent(String morseLetter) throws InvalidEncodingException {
        switch (morseLetter) {
            case ".-":
                return 'A';
            case "-...":
                return 'B';
            case "-.-.":
                return 'C';
            case "-..":
                return 'D';
            case ".":
                return 'E';
            case "..-..":
                return 'F';
            case "--.":
                return 'G';
            case "....":
                return 'H';
            case "..":
                return 'I';
            case ".---":
                return 'J';
            case "-.-":
                return 'K';
            case ".-..":
                return 'L';
            case "--":
                return 'M';
            case "-.":
                return 'N';
            case "---":
                return 'O';
            case ".--."
                    :
                return 'P';
            case "--.-":
                return 'Q';
            case ".-.":
                return 'R';
            case "...":
                return 'S';
            case "-":
                return 'T';
            case "..-":
                return 'U';
            case "...-":
                return 'V';
            case ".--":
                return 'W';
            case "-..-":
                return 'X';
            case "-.--":
                return 'Y';
            case "--.."
                    :
                return 'Z';
            default:
                throw new InvalidEncodingException("Invalid Morse code: " + morseLetter);
        }
    }

    @Override
    public String rot13ToText(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    result.append((char) ('A' + (c - 'A' + 13) % 26));
                } else {
                    result.append((char) ('a' + (c - 'a' + 13) % 26));
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    @Override
    public String tapCodeToText(String input) throws InvalidEncodingException {
        String[] tapCodePairs = input.split(TAP_CODE_DELIMITER);
        StringBuilder result = new StringBuilder();

        for (String tapCodePair : tapCodePairs) {
            if (tapCodePair.length() != 4) {
                throw new InvalidEncodingException("Invalid tap code input: " + tapCodePair);
            }
            int row = Integer.parseInt(tapCodePair.substring(0, 2));
            int col = Integer.parseInt(tapCodePair.substring(2, 4));
            if (row < 1 || row > 5 || col < 1 || col > 5) {
                throw new InvalidEncodingException("Invalid tap code input: " + tapCodePair);
            }
            char letter = getTapCodeLetter(row, col);
            result.append(letter);
        }

        return result.toString().toUpperCase();
    }

    private char getTapCodeLetter(int row, int col) {
        int index = (row - 1) * 5 + (col - 1);
        char[] tapCodeLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        return tapCodeLetters[index];
    }

    @Override
    public String natoAlphabetToText(String input) throws InvalidEncodingException {
        String[] natoWords = input.split(NATO_ALPHABET_DELIMITER);
        StringBuilder result = new StringBuilder();

        for (String natoWord : natoWords) {
            int index = getNatoAlphabetIndex(natoWord);
            if (index == -1) {
                throw new InvalidEncodingException("Invalid NATO alphabet word: " + natoWord);
            }
            result.append((char) ('A' + index));
        }

        return result.toString().toUpperCase();
    }

    private int getNatoAlphabetIndex(String natoWord) {
        for (int i = 0; i < NATO_ALPHABET.length; i++) {
            if (NATO_ALPHABET[i].equals(natoWord)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String identifyCipherAndDecode(String input) throws InvalidEncodingException {
        try {
            return binaryToText(input);
        } catch (InvalidEncodingException e) {
            // Ignore the exception and try the next cipher
        }

        try {
            return morseToText(input);
        } catch (InvalidEncodingException e) {
            // Ignore the exception and try the next cipher
        }

        try {
            return rot13ToText(input);
        } catch (Exception e) {
            // Ignore the exception and try the next cipher
        }

        try {
            return tapCodeToText(input);
        } catch (InvalidEncodingException e) {
            // Ignore the exception and try the next cipher
        }

        try {
            return natoAlphabetToText(input);
        } catch (InvalidEncodingException e) {
            // Ignore the exception and throw a new one
            throw new InvalidEncodingException("Input could not be decoded using any of the supported ciphers.");
        }
    }
}