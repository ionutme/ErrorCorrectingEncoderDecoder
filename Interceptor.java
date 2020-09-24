package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static correcter.BinaryUtil.*;

public class Interceptor extends FileWorker {

    @Override
    void transform(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
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

        return toDecimal(binaryValueWithError);
    }

    private static String introduceRandomError(String byteValue) {
        int bitIndex = getRandomIndex(byteValue);

        return switchBit(byteValue, bitIndex);
    }

    private static int getRandomIndex(String byteValue) {
        var random = new Random();
        int bound = byteValue.length();                //8

        return random.nextInt(bound);
    }
}
