package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static correcter.BinaryUtil.*;
import static correcter.Constants.*;

public class Decoder extends FileWorker {
    @Override
    void transform(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        var decodedMessage = new Deque();

        int input = fileInputStream.read();
        while (input != -1) {

            char[] bits = getBits(input);
            char[] correctBits = ByteCorrector.correctError(bits);

            decodedMessage.add(getMessageBits(correctBits));

            input = fileInputStream.read();
        }

        ArrayList<char[]> chunks = decodedMessage.getChunks(DECODING_CHUNK);
        for (char[] bits : chunks) {
            fileOutputStream.write(toByte(bits));
        }

        if (!decodedMessage.isEmpty()) {
            // ignore
        }
    }

    private char[] getMessageBits(char[] bits) {
        int length = (bits.length - PARITY_LENGTH) / STEP;
        var messageBits = new char[length];
        for (int i = 0, j = 0; i < length; i++, j += STEP) {
            messageBits[i] = bits[j];
        }

        return messageBits;
    }
}
