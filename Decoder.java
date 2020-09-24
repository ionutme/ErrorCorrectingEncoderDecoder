package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static correcter.BinaryUtil.getBits;
import static correcter.BinaryUtil.toByte;

public class Decoder extends FileWorker {
    @Override
    void transform(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        var decodedText = new EncodingDeque();

        int input = fileInputStream.read();
        while (input != -1) {

            char[] bits = getBits(input);
            int errorIndex = -1;

            for (int i = 0; i < bits.length - 1; i += 2) {
                if (bits[i] != bits[i + 1]) {
                    errorIndex = i; // 0 | 2 | 4 | 6
                }
            }

            if (errorIndex < 6) {
                int prevIndex = errorIndex < 2 ? 4 : errorIndex - 2;
                int nextIndex = errorIndex >= 4 ? 0 : errorIndex + 2;

                var eq = toByte(bits[prevIndex]) ^ 0b00000000 ^ toByte(bits[nextIndex]);
                if (eq == toByte(bits[7])) {
                    bits[errorIndex] = '0';
                } else {
                    bits[errorIndex] = '1';
                }
            }

            decodedText.add(new char[] {bits[0], bits[2], bits[4]});

            input = fileInputStream.read();
        }

        var chunks = decodedText.getChunks(8);
        for (int i = 0, chunksSize = chunks.size(); i < chunksSize; i++) {
            char[] bits = chunks.get(i);
            byte b = toByte(bits);
            fileOutputStream.write(b);
        }

        if (!decodedText.isEmpty()) {

            /*char[] binary = new char[8];
            Arrays.fill(binary, '0');
            int index = 7;

            for (int i = 0; i < decodedText.size(); i++) {
                char bit = decodedText.pop();
                if (bit != '0') {
                    binary[index] = bit;
                    index--;
                }
            }

            fileOutputStream.write(toByte(binary));*/
        }
    }
}
