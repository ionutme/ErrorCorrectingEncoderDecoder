package correcter;

public class BinaryUtil {

    public static final byte bONE = 0b00000001;
    public static final byte bZERO = 0b00000000;

    public static final char cONE = '1';
    public static final char cZERO = '0';

    public static byte toByte(char[] bits) {
        var binary = new StringBuilder();
        for (char bit : bits) {
            binary.append(bit);
        }

        return (byte) toDecimal(binary);
    }

    public static char[] getBits(int decimal) {
        return BinaryUtil.toBinary(decimal)
                         .toCharArray();
    }

    public static String switchBit(String byteValue, int bitIndex) {
        char newValue = getInvertedBit(byteValue, bitIndex);

        var stringBuilder = new StringBuilder(byteValue);
        stringBuilder.setCharAt(bitIndex, newValue);

        return stringBuilder.toString();
    }

    public static int toDecimal(StringBuilder binaryNrBuilder) {
        String binaryNr = binaryNrBuilder.toString();

        return toDecimal(binaryNr);
    }

    public static int toDecimal(String binaryNr) {
        return Integer.parseInt(binaryNr, 2);
    }

    public static char toChar(byte bit) {
        return bit == bONE ? cONE : cZERO;
    }

    public static byte toByte(char bit) {
        return bit == cONE ? bONE : bZERO;
    }

    public static String toBinary(int decimal) {
        String binaryValue = Integer.toBinaryString(decimal);

        return formatWithTrailingZeros(binaryValue);
    }

    private static char getInvertedBit(String byteValue, int index) {
        return byteValue.charAt(index) == cZERO ? cONE : cZERO;
    }

    public static byte getParityBit(char[] bits) {
        byte parityBit = bZERO;
        for (char bit : bits) {
            parityBit ^= toByte(bit);
        }

        return parityBit;
    }

    private static String formatWithTrailingZeros(String binaryValue) {
        return String.format("%8s", binaryValue)
                     .replace(' ', '0');
    }
}
