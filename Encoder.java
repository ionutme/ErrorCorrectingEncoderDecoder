package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static correcter.BinaryUtil.*;
import static correcter.EncodingDecodingUtil.*;

public class Encoder extends FileWorker {
    private final EncodingDeque deque;

    public Encoder() {
        deque = new EncodingDeque();
    }

    @Override
    void transform(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        int input = fileInputStream.read();
        while (input != -1) {
            this.deque.add(getBits(input));

            ArrayList<char[]> chunks = this.deque.toChunks(ENCODING_CHUNK);
            for (char[] bits : chunks){
                fileOutputStream.write(encode(bits));
            }

            input = fileInputStream.read();
        }

        if (!this.deque.isEmpty()) {
            char[] restBits = this.deque.toChunksWithTrailingZeros(ENCODING_CHUNK).get(0);

            fileOutputStream.write(encode(restBits));
        }
    }

    private int encode(char[] bits) {
        String encodedBits = duplicateBits(bits).concat(getParityBits(bits));

        return toDecimal(encodedBits);
    }

    private String duplicateBits(char[] bits) {
        var duplicatedBits = new StringBuilder(bits.length * 2);

        for (char bit : bits) {
            duplicatedBits.append(bit);
            duplicatedBits.append(bit);
        }

        return duplicatedBits.toString();
    }

    private String getParityBits(char[] bits) {
        byte parityBit = getParityBit(bits);

        return new StringBuilder(2)
                        .append(parityBit)
                        .append(parityBit)
                   .toString();
    }
}
