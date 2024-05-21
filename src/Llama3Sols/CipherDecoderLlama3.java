package Llama3Sols;

import exceptions.InvalidEncodingException;
import tasks.CipherDecoder;

public class CipherDecoderLlama3 implements CipherDecoder {

    @Override
    public String binaryToText(String input) throws InvalidEncodingException {
        input = input.replace(" ", ""); // remove spaces
        if (input.length() % 8 != 0) {
            throw new InvalidEncodingException("Invalid binary input");
        }
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i += 8) {
            String byteStr = input.substring(i, i + 8);
            int byteInt = Integer.parseInt(byteStr, 2);
            char c = (char) byteInt;
            output.append(c);
        }
        return output.toString();
    }

    @Override
    public String morseToText(String input) throws InvalidEncodingException {
        String[] morseCode = {".-",   "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        input = input.replace("/", " / "); // add spaces around slashes
        String[] words = input.split(" / ");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            String[] letters = word.split(" ");
            for (String letter : letters) {
                int index = -1;
                for (int i = 0; i < morseCode.length; i++) {
                    if (morseCode[i].equals(letter)) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    throw new InvalidEncodingException("Invalid morse code input");
                }
                output.append(alphabet[index]);
            }
            output.append(" ");
        }
        return output.toString().trim().toUpperCase();
    }

    @Override
    public String rot13ToText(String input) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + 13) % 26 + base);
            }
            output.append(c);
        }
        return output.toString();
    }

    @Override
    public String tapCodeToText(String input) throws InvalidEncodingException {
        String[] tapCode = {"11", "12", "13", "14", "15", "16", "21", "22", "23", "24", "25", "26", "31", "32", "33", "34", "35", "36", "41", "42", "43", "44", "45", "46"};
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] pairs = input.split(" ");
        StringBuilder output = new StringBuilder();
        for (String pair : pairs) {
            int index = -1;
            for (int i = 0; i < tapCode.length; i++) {
                if (tapCode[i].equals(pair)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new InvalidEncodingException("Invalid tap code input");
            }
            output.append(alphabet[index]);
        }
        return output.toString().toUpperCase();
    }

    @Override
    public String natoAlphabetToText(String input) throws InvalidEncodingException {
        String[] natoAlphabet = {"ALFA", "BRAVO", "CHARLIE", "DELTA", "ECHO", "FOXTROT", "GOLF", "HOTEL", "INDIA", "JULIET", "KILO", "LIMA", "MIKE", "NOVEMBER", "OSCAR", "PAPA", "QUEBEC", "ROMEO", "SIERRA", "TANGO", "UNIFORM", "VICTOR", "WHISKEY", "XRAY", "YANKEE", "ZULU"};
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] words = input.split(" ");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            int index = -1;
            for (int i = 0; i < natoAlphabet.length; i++) {
                if (natoAlphabet[i].equals(word)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new InvalidEncodingException("Invalid NATO alphabet input");
            }
            output.append(alphabet[index]);
        }
        return output.toString().toUpperCase();
    }

    @Override
    public String identifyCipherAndDecode(String input) throws InvalidEncodingException {
        if (input.matches("[01 ]+")) {
            return binaryToText(input);
        } else if (input.matches("[.-/ ]+")) {
            return morseToText(input);
        } else if (input.matches("[A-Za-z0-9 ]+")) {
            if (input.contains(" ")) {
                if (input.toUpperCase().matches("[A-Z ]+")) {
                    return natoAlphabetToText(input);
                } else {
                    return tapCodeToText(input);
                }
            } else {
                return rot13ToText(input);
            }
        } else {
            throw new InvalidEncodingException("Unknown encryption method");
        }
    }
}