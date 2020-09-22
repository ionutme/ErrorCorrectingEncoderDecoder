package correcter;

import java.io.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        var inputFile = new File("send.txt");
        var outputFile = new File("received.txt");

        try (var fileOutputStream = new FileOutputStream(outputFile)) {
            try (var fileInputStream = new FileInputStream(inputFile)) {

                interceptMessage(fileOutputStream, fileInputStream);

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

    private static void interceptMessage(FileOutputStream fileOutputStream, FileInputStream fileInputStream) throws IOException {
        int input = fileInputStream.read();
        while (input != -1) {
            int output = getOutputValue(input);

            fileOutputStream.write((char) output);

            input = fileInputStream.read();
        }
    }

    private static int getOutputValue(int input) {
        String binaryValue = toBinary(input);
        String binaryValueWithError = introduceRandomError(binaryValue);

        return Integer.parseInt(binaryValueWithError, 2);
    }

    private static String introduceRandomError(String byteValue) {
        int bitIndex = getRandomIndex(byteValue);

        char reverseBit = getReversedBit(byteValue, bitIndex);

        return changeBit(byteValue, bitIndex, reverseBit);
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

    private static String changeBit(String byteValue, int bitIndex, char bitValue) {
        var stringBuilder = new StringBuilder(byteValue);
        stringBuilder.setCharAt(bitIndex, bitValue);

        return stringBuilder.toString();
    }

    private static String toBinary(int input) {
        String binaryValue = Integer.toBinaryString(input);

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
