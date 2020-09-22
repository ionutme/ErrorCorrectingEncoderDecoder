package correcter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class TextInterceptor {
    public static void process(File inputFile, File outputFile) {
        try (var fileOutputStream = new FileOutputStream(outputFile)) {
            try (var fileInputStream = new FileInputStream(inputFile)) {

                process(fileInputStream, fileOutputStream);

            } catch (FileNotFoundException exception) {
                System.out.println(getFileNotFoundErrorMessage(inputFile));
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } catch (IOException exception) {
            System.out.println(getIOErrorMessage(outputFile));
            exception.printStackTrace();
        }
    }

    private static void process(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        int input = fileInputStream.read();
        while (input != -1) {
            int output = getDecimalWithBitError(input);

            fileOutputStream.write((char) output);

            input = fileInputStream.read();
        }
    }

    private static int getDecimalWithBitError(int decimal) {
        String binaryValue = toBinary(decimal);
        String binaryValueWithError = introduceRandomError(binaryValue);

        int decimalWithError = Integer.parseInt(binaryValueWithError, 2);

        return decimalWithError;
    }

    private static String introduceRandomError(String byteValue) {
        int bitIndex = getRandomIndex(byteValue);

        char reverseBit = getReversedBit(byteValue, bitIndex);

        return switchBit(byteValue, bitIndex, reverseBit);
    }

    private static int getRandomIndex(String byteValue) {
        var random = new Random();

        int length = byteValue.length();                //8

        return random.nextInt(length);
    }

    private static char getReversedBit(String byteValue, int index) {
        return byteValue.charAt(index) == '0'
                ? '1'
                : '0';
    }

    private static String switchBit(String byteValue, int bitIndex, char bitValue) {
        var stringBuilder = new StringBuilder(byteValue);
        stringBuilder.setCharAt(bitIndex, bitValue);

        return stringBuilder.toString();
    }

    private static String toBinary(int decimal) {
        String binaryValue = Integer.toBinaryString(decimal);

        return formatWithTrailingZeros(binaryValue);
    }

    private static String formatWithTrailingZeros(String binaryValue) {
        return String.format("%8s", binaryValue)
                .replace(' ', '0');
    }

    private static String getFileNotFoundErrorMessage(File file) {
        return String.format("The file %s\\%s was not found!", System.getProperty("user.dir"), file.getPath());
    }

    private static String getIOErrorMessage(File file) {
        return String.format("There was a problem when writing into %s!", file.getPath());
    }
}
