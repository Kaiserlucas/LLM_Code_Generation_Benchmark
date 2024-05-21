package tasks;

import exceptions.InvalidEncodingException;

public interface CipherDecoder {

    //InvalidEncodingException should be thrown whenever the input has a format that cannot be decoded using the function

    //A string of binary numbers which should be decoded using ASCII
    //There may be a space between blocks of 8 digits, or there may not be. The function should be able to handle both cases
    public String binaryToText(String input) throws InvalidEncodingException;

    //A string of morse code characters should be decoded Allowed symbols: ".-/" no spaces in the input
    //A single slash is a letter boundary. A double slash is a word boundary
    //Return result in all uppercase
    public String morseToText(String input) throws InvalidEncodingException;

    //A string that is encoded with rot13 should be decoded. Non-letter characters should be left as is
    //Preserve upper- and lowercase
    public String rot13ToText(String input);

    //A string of tap code should be decoded to text. It will always be given as pairs of numbers, separated by a space
    //Example: "44 15 43 44" decodes to "Test"
    //Use the version of the tap code cipher that combines the letters C and K
    //Return result in all uppercase
    public String tapCodeToText(String input) throws InvalidEncodingException;

    //A string consisting of NATO alphabet words, to be decoded to text
    //Each word will be in full uppercase and separated by spaces. Example:
    //"TANGO ECHO SIERRA TANGO" decodes to "TEST"
    //Return result in all uppercase
    //Omit the dash in XRAY, the first letter should be spelt ALFA
    public String natoAlphabetToText(String input) throws InvalidEncodingException;

    //Given a string that is encrypted using one of the methods shown in this class, identify what encryption is being used
    //Then and return the result
    public String identifyCipherAndDecode(String input) throws InvalidEncodingException;
}
