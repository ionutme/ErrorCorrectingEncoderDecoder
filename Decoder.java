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
            var hByte = new HammingByte();

            decodedMessage.add(hByte.decode(bits));

            input = fileInputStream.read();
        }

        ArrayList<char[]> chunks = decodedMessage.getChunks(DECODING_CHUNK);
        for (char[] bits : chunks) {
            fileOutputStream.write(toByte(bits));
        }
    }
}
