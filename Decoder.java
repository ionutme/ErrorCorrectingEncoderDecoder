package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static correcter.BinaryUtil.*;

public class Decoder extends FileWorker {
    @Override
    void transform(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        var decodedText = new EncodingDeque();

        int input = fileInputStream.read();
        while (input != -1) {

            char[] bits = getBits(input);
            char[] correctBits = ByteCorrector.correctError(bits);

            decodedText.add(getMessage(correctBits));

            input = fileInputStream.read();
        }

        ArrayList<char[]> chunks = decodedText.toChunks(8);
        for (char[] bits : chunks) {
            fileOutputStream.write(toByte(bits));
        }

        if (!decodedText.isEmpty()) {
            // ignore
        }
    }

    private char[] getMessage(char[] bits) {
        return new char[]{bits[0], bits[2], bits[4]};
    }
}
