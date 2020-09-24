package correcter;

public class BinaryUtil {

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

    public static byte toByte(char bit) {
        byte zero = 0b00000000;
        byte one =  0b00000001;

        return bit == '1' ? one : zero;
    }

    public static String toBinary(int decimal) {
        String binaryValue = Integer.toBinaryString(decimal);

        return formatWithTrailingZeros(binaryValue);
    }

    private static char getInvertedBit(String byteValue, int index) {
        return byteValue.charAt(index) == '0'
                ? '1'
                : '0';
    }

    private static String formatWithTrailingZeros(String binaryValue) {
        return String.format("%8s", binaryValue)
                     .replace(' ', '0');
    }
}
