package GeminiSols;

import exceptions.InvalidEncodingException;
import tasks.CipherDecoder;

public class CipherDecoderGemini implements CipherDecoder {

    private static final String MORSE_CODE_MAP = ".- / ";
    private static final String[] TAP_CODE_MAP = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final String[] NATO_ALPHABET = {"ALFA", "BRAVO", "CHARLIE", "DELTA", "ECHO", "FOXTROT", "GOLF", "HOTEL", "INDIA", "JULIETT", "KILO", "LIMA", "MIKE", "NOVEMBER", "OSCAR", "PAPA", "QUEBEC", "ROMEO", "SIERRA", "TANGO", "UNIFORM", "VICTOR", "WHISKEY", "XRAY", "YANKEE", "ZULU"};

    @Override
    public String binaryToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        String[] binaryBlocks = input.split(" ");
        for (String block : binaryBlocks) {
            if (block.length() % 8 != 0) {
                throw new InvalidEncodingException("Invalid binary block length");
            }
            for (int i = 0; i < block.length(); i += 8) {
                String byteString = block.substring(i, i + 8);
                int charValue = Integer.parseInt(byteString, 2);
                result.append((char) charValue);
            }
        }
        return result.toString();
    }

    @Override
    public String morseToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        String[] codeParts = input.split("//");
        for (String part : codeParts) {
            for (int i = 0; i < part.length(); i++) {
                char symbol = part.charAt(i);
                int index = MORSE_CODE_MAP.indexOf(symbol);
                if (index == -1) {
                    throw new InvalidEncodingException("Invalid morse code symbol");
                }
                result.append(index == 0 ? MORSE_CODE_MAP.charAt(index + 1) : " ");
            }
            result.append(" ");
        }
        return result.toString().trim().toUpperCase();
    }

    @Override
    public String rot13ToText(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                int offset = Character.isUpperCase(c) ? 65 : 97;
                int newChar = (c - offset + 13) % 26;
                result.append((char) (newChar + offset));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    @Override
    public String tapCodeToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        String[] pairs = input.split(" ");
        for (String pair : pairs) {
            String[] digits = pair.split("");
            if (digits.length != 2) {
                throw new InvalidEncodingException("Invalid tap code pair");
            }
            int firstDigit = Integer.parseInt(digits[0]);
            int secondDigit = Integer.parseInt(digits[1]);
            if (firstDigit < 1 || firstDigit > 5 || secondDigit < 1 || secondDigit > 5) {
                throw new InvalidEncodingException("Invalid tap code digit");
            }
            int index = (firstDigit - 1) * 5 + (secondDigit - 1);
            result.append(TAP_CODE_MAP[index]);
        }
        return result.toString().toUpperCase();
    }

    @Override
    public String natoAlphabetToText(String input) throws InvalidEncodingException {
        StringBuilder result = new StringBuilder();
        String[] words = input.split(" ");
        for (String word : words) {
            int index = -1;
            for (int i = 0; i < NATO_ALPHABET.length; i++) {
                if (NATO_ALPHABET[i].equals(word)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new InvalidEncodingException("Invalid NATO alphabet word");
            }
            //Original incorrect version of this function
            // result.append(index == 0 ? "A" : (index == 24 ? "X" : NATO_ALPHABET[index].charAt(0)));

            //Fixed version that actually compiles
            result.append(index == 0 ? "A" : (index == 24 ? "X" : String.valueOf(NATO_ALPHABET[index].charAt(0))));
        }
        return result.toString().toUpperCase();
    }

    @Override
    public String identifyCipherAndDecode(String input) throws InvalidEncodingException {
        if (input.matches("[01 ]+")) {
            return binaryToText(input);
        } else if (input.matches("[.-/]+")) {
            return morseToText(input);
        } else if (input.matches("[A-Za-z0-9 ]+")) {
            return rot13ToText(input);
        } else if (input.matches("[1-5 ]{2,}")) {
            return tapCodeToText(input);
        } else if (input.matches("[A-Z ]+")) {
            return natoAlphabetToText(input);
        } else {
            throw new InvalidEncodingException("Unknown cipher type");
        }
    }
}
