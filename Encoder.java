package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static correcter.BinaryUtil.*;

public class Encoder extends FileWorker {
    private EncodingDeque deque;

    public Encoder() {
        deque = new EncodingDeque();
    }

    @Override
    void transform(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        int input = fileInputStream.read();
        while (input != -1) {
            this.deque.add(getBits(input));

            var chunks = this.deque.getChunks(3);
            for (char[] bits : chunks){
                var encodedBits = encode(bits);

                fileOutputStream.write(encodedBits);
            }

            input = fileInputStream.read();
        }

        if (!this.deque.isEmpty()) {
            var chunks = this.deque.getChunks(3, true);
            var encodedBits = encode(chunks.get(0));

            fileOutputStream.write(encodedBits);
        }
    }

    private int encode(char[] bits) {
        int capacity = bits.length * 2 + 2;
        var encodedBits = new StringBuilder(capacity);

        byte parityBit = 0b00000000;
        for (char bit : bits) {
            encodedBits.append(bit);
            encodedBits.append(bit);

            parityBit ^= toByte(bit);
        }

        encodedBits.append(parityBit);
        encodedBits.append(parityBit);

        return toDecimal(encodedBits);
    }
}
