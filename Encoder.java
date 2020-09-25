package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static correcter.BinaryUtil.*;

public class Encoder extends FileWorker {
    private final Deque deque;

    public Encoder() {
        deque = new Deque();
    }

    @Override
    void transform(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        int input = fileInputStream.read();
        while (input != -1) {
            this.deque.add(getBits(input));

            ArrayList<char[]> chunks = this.deque.getChunks(4);
            for (char[] bits : chunks){
                fileOutputStream.write(encode(bits));
            }

            input = fileInputStream.read();
        }
    }

    private int encode(char[] bits) {
        var hByte = new HammingByte();
        var encodedByte = String.valueOf(hByte.encode(bits));

        return toDecimal(encodedByte);
    }
}
