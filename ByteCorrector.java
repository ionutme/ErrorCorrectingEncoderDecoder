package correcter;

import java.util.Arrays;

import static correcter.BinaryUtil.*;

public class ByteCorrector {

    public static final int STEP = 2;
    public static final int PARITY_LENGTH = 2;

    public static char[] correctError(char[] bits) {
        int errorIndex = getErrorIndex(bits);
        if (errorIndex >= getMessageLength(bits)) {
            return bits;
        }

        return getCorrectBits(bits, errorIndex);
    }

    private static char[] getCorrectBits(char[] bits, int errorIndex) {
        var correctBits = bits.clone();

        correctBits[errorIndex] = getCorrectBit(bits, errorIndex);

        return correctBits;
    }

    private static char getCorrectBit(char[] bits, int errorIndex) {
        char[] importantBits = Arrays.copyOf(bits, getMessageLength(bits));

        char prevBit = getPrevBit(importantBits, errorIndex);
        char nextBit = getNextBit(importantBits, errorIndex);
        char parityBit = getParityBit(bits);

        return solveXorEquation(prevBit, nextBit, parityBit);
    }

    private static int getMessageLength(char[] bits) {
        return bits.length - PARITY_LENGTH;
    }

    private static int getErrorIndex(char[] bits) {
        int errorIndex = -1;
        for (int i = 0; i < bits.length - 1; i += 2) {
            if (bits[i] != bits[i + 1]) {
                errorIndex = i;
            }
        }

        return errorIndex;
    }

    private static char getPrevBit(char[] bits, int errorIndex) {
        int lastIndex = bits.length - STEP;
        int prevIndex = errorIndex - STEP;

        if (errorIndex < STEP) {
            prevIndex = lastIndex;
        }

        return bits[prevIndex];
    }

    private static char getNextBit(char[] bits, int errorIndex) {
        int firstIndex = 0;
        int lastIndex = bits.length - STEP;

        int nextIndex = errorIndex + STEP;
        if (errorIndex >= lastIndex) {
            nextIndex = firstIndex;
        }

        return bits[nextIndex];
    }

    private static char getParityBit(char[] bits) {
        int lastIndex = bits.length - 1;

        return bits[lastIndex];
    }

    private static char solveXorEquation(char b1, char b2, char output) {
        var xor = toByte(b1) ^ bZERO ^ toByte(b2);
        if (xor == toByte(output)) {
            return cZERO;
        } else {
            return cONE;
        }
    }
}
