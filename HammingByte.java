package correcter;

import java.util.Arrays;

import static correcter.Constants.*;

public class HammingByte {
    private char[] hByte = new char[] {'.', '.', 'A', '.', 'B', 'C', 'D', '0'};

    public HammingByte() {
    }

    public HammingByte(char[] bits) {
        assert bits.length == DECODING_CHUNK;

        this.hByte = Arrays.copyOf(bits, DECODING_CHUNK);

    }

    public char[] encode(char[] bits) {
        assert bits.length == HAM_ENCODING_CHUNK;

        setMessageBits(bits);
        setParityBits();

        return this.hByte;
    }

    public char[] decode() {
        int errorIndex = getErrorIndex();
        if (errorIndex > 0) {
            this.switchBit(errorIndex);
        }

        return this.getMessageBits();
    }

    private char[] getMessageBits() {
        return new char[]{ hByte[2], hByte[4], hByte[5], hByte[6] };
    }

    private void setMessageBits(char[] bits) {
        hByte[2] = bits[0];
        hByte[4] = bits[1];
        hByte[5] = bits[2];
        hByte[6] = bits[3];
    }

    private char[] getBits(int b1, int b2, int b3) {
        return new char[] { hByte[b1], hByte[b2], hByte[b3] };
    }

    private void setParityBits() {
        hByte[0] = this.getParity1();
        hByte[1] = this.getParity2();
        hByte[3] = this.getParity4();
    }

    private static char getParityBit(int count) {
        boolean isEven = count % 2 == 0;

        return isEven ? '0' : '1';
    }

    private char getParity1() {
        return calculateParity(getBits(2, 4, 6));
    }

    private char getParity2() {
        return calculateParity(getBits(2, 5, 6));
    }

    private char getParity4() {
        return calculateParity(getBits(4, 5, 6));
    }

    private static char calculateParity(char[] bits) {
        int countOnes = 0;
        for (char bit : bits) {
            if (bit == '1') {
                countOnes++;
            }
        }

        return  getParityBit(countOnes);
    }

    private int getErrorPositionParity1() {
        if (hByte[0] != getParity1()) {
            return  1;
        }

        return 0;
    }

    private int getErrorPositionParity2() {
        if (hByte[1] != getParity2()) {
            return  2;
        }

        return 0;
    }

    private int getErrorPositionParity4() {
        if (hByte[3] != getParity4()) {
            return  4;
        }

        return 0;
    }

    private int getErrorIndex() {
        int errorIndex = -1;
        errorIndex += getErrorPositionParity1();
        errorIndex += getErrorPositionParity2();
        errorIndex += getErrorPositionParity4();

        return errorIndex;
    }

    private void switchBit(int index) {
        char value = hByte[index];

        hByte[index] = value == '1' ? '0' : '1';
    }
}
