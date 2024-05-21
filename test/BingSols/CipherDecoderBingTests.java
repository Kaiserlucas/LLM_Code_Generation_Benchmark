package BingSols;

import exceptions.InvalidEncodingException;
import tasks.CipherDecoder;
import org.junit.Assert;
import org.junit.Test;

public class CipherDecoderBingTests {

    public CipherDecoder getCipherDecoder() {
        return new CipherDecoderBing();
    }

    @Test
    public void decodeBinaryTest1() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.binaryToText("01000101 01111000 01100001 01101101 01110000 01101100 01100101");
        assert(text.equals("Example"));
    }

    @Test
    public void decodeBinaryTest2() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.binaryToText("01000101011110000110000101101101011100000110110001100101");
        assert(text.equals("Example"));
    }

    @Test
    public void decodeBinaryErrorTest() {
        CipherDecoder decoder = getCipherDecoder();

        try {
            String text = decoder.binaryToText("010001010111100X0110000101101101011100000110110001100101");
            Assert.fail();
        } catch (InvalidEncodingException e) {
            //Binary contains an X and is therefore invalid. Should thrown an exception
        }
    }

    @Test
    public void decodeMorseTest() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.morseToText("./-..-/.-/--/.--./.-../.");
        assert(text.equals("EXAMPLE"));
    }

    @Test
    public void decodeMorseMultipleWordsTest() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.morseToText("-/..../../...//../...//.-//-/./.../-");
        assert(text.equals("THIS IS A TEST"));
    }

    @Test
    public void decodeMorseErrorTest() {
        CipherDecoder decoder = getCipherDecoder();

        try {
            String text = decoder.binaryToText("./-..-/.-/--/.-0-./.-../.");
            Assert.fail();
        } catch (InvalidEncodingException e) {
            //Morse contains a 0 and is therefore invalid. Should thrown an exception
        }
    }

    @Test
    public void rot13Test1() {
        CipherDecoder decoder = getCipherDecoder();

        //All uppercase test
        String text = decoder.rot13ToText("RKNZCYR");
        assert(text.equals("EXAMPLE"));
    }

    @Test
    public void rot13Test2() {
        CipherDecoder decoder = getCipherDecoder();

        //Capitalization should be preserved
        String text = decoder.rot13ToText("Rknzcyr");
        assert(text.equals("Example"));
    }

    @Test
    public void rot13Test3() {
        CipherDecoder decoder = getCipherDecoder();

        //Non-letter characters should be preserved
        String text = decoder.rot13ToText("Guvf vf n grfg.");
        assert(text.equals("This is a test."));
    }

    @Test
    public void tapCodeTest() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.tapCodeToText("15 53 11 32 35 31 15");
        assert(text.equals("EXAMPLE"));
    }

    @Test
    public void tapCodeErrorTest() {
        CipherDecoder decoder = getCipherDecoder();

        try {
            String text = decoder.tapCodeToText("15 533 11 32 35 31 15");
            Assert.fail();
        } catch (InvalidEncodingException e) {
            //Tap code is invalid, so an exception is expected
        }
    }

    @Test
    public void natoAlphabetTest() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.natoAlphabetToText("ECHO XRAY ALFA MIKE PAPA LIMA ECHO");
        assert(text.equals("EXAMPLE"));
    }

    @Test
    public void natoAlphabetErrorTest() {
        CipherDecoder decoder = getCipherDecoder();

        try {
            String text = decoder.natoAlphabetToText("ECHO GUSTAV ALFA MIKE PAPA LIMA ECHO");
            Assert.fail();
        } catch (InvalidEncodingException e) {
            //Gustav is not in the Nato alphabet, so an error is expected
        }
    }

    @Test
    public void detectBinaryTest() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.identifyCipherAndDecode("01000101 01111000 01100001 01101101 01110000 01101100 01100101");
        assert(text.equals("Example"));
    }

    @Test
    public void detectMorseTest() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.identifyCipherAndDecode("./-..-/.-/--/.--./.-../.");
        assert(text.equals("Example"));
    }

    @Test
    public void detectRot13Test() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.identifyCipherAndDecode("Guvf vf n grfg.");
        assert(text.equals("This is a test."));
    }

    @Test
    public void detectTapCodeTest() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.identifyCipherAndDecode("15 53 11 32 35 31 15");
        assert(text.equals("EXAMPLE"));
    }

    @Test
    public void detectNatoAlphabetTest() throws InvalidEncodingException {
        CipherDecoder decoder = getCipherDecoder();

        String text = decoder.identifyCipherAndDecode("ECHO XRAY ALFA MIKE PAPA LIMA ECHO");
        assert(text.equals("EXAMPLE"));
    }

    @Test
    public void unknownEncodingTest() {
        CipherDecoder decoder = getCipherDecoder();

        try {
            String text = decoder.identifyCipherAndDecode("???");
            Assert.fail();
        } catch (InvalidEncodingException e) {
            //Error is expected
        }
    }
}
