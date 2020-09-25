package correcter;

public class HammingByte {
    private final char[] hByte = new char[8];

    public HammingByte(char[] bits) {// length must be 4
        hByte[2] = bits[0];
        hByte[4] = bits[1];
        hByte[5] = bits[2];
        hByte[6] = bits[3];
        hByte[7] = '0';

        hByte[0] = getParity(new char[] {hByte[2], hByte[4], hByte[6]});
        hByte[1] = getParity(new char[] {hByte[2], hByte[5], hByte[6]});
        hByte[3] = getParity(new char[] {hByte[4], hByte[5], hByte[6]});
    }

    public HammingByte() {
    }

    public char[] decode(char[] bits) {
        int errorIndex = 0;
        if (bits[0] != getParity(new char[] {bits[2], bits[4], bits[6]})) {
            errorIndex += 1;
        }
        if (bits[1] != getParity(new char[] {bits[2], bits[5], bits[6]})) {
            errorIndex += 2;
        }
        if (bits[3] != getParity(new char[] {bits[4], bits[5], bits[6]})) {
            errorIndex += 4;
        }

        if (errorIndex > 0) {
            bits[errorIndex - 1] = switchBit(bits[errorIndex - 1]);
        }

        return new char[] {bits[2], bits[4], bits[5], bits[6]};
    }

    private char switchBit(char bit) {
        return bit == '1' ? '0' : '1';
    }

    public char[] getMessageBits() {
        return new char[] {hByte[2], hByte[4], hByte[5], hByte[6]};
    }

    private char getParity(char[] bits) {
        int countOnes = 0;
        for (char bit : bits) {
            if (bit == '1') {
                countOnes++;
            }
        }

        return  getParity(countOnes);
    }

    private static char getParity(int count) {
        boolean isEven = count % 2 == 0;

        return isEven ? '0' : '1';
    }

    @Override
    public String toString() {
        return String.valueOf(hByte);
    }
}
