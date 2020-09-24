package correcter;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class EncodingDeque extends ArrayDeque<Character>{

    public void add(char[] chars) {
        for (char c : chars) {
            this.add(c);
        }
    }

    public ArrayList<char[]> getChunks(int chunk) {
        int count = this.size() / chunk;

        var bitsChunks = new ArrayList<char[]>(count);
        for (int i = 0; i < count; i++) {
            var bitsGroup = new char[chunk];
            for (int j = 0; j < chunk; j++) {
                bitsGroup[j] = this.pop();
            }

            bitsChunks.add(bitsGroup);
        }

        return bitsChunks;
    }

    public ArrayList<char[]> getChunks(int chunk, boolean addTrailingZeros) {
        int count = this.size() / chunk;

        var bitsChunks = new ArrayList<char[]>();
        for (int i = 0; i < count; i++) {
            var bitsGroup = new char[chunk];
            for (int j = 0; j < chunk; j++) {
                bitsGroup[j] = this.pop();
            }

            bitsChunks.add(bitsGroup);
        }


        int rest = this.size() % chunk;
        if (rest > 0) {

            var lsatBitsGroup = new char[chunk];

            for (int i = 0; i <= chunk - rest; i++) {
                lsatBitsGroup[i] = this.pop();
            }

            for (int i = chunk - rest + 1; i < chunk; i++) {
                lsatBitsGroup[i] = '0';
            }

            bitsChunks.add(lsatBitsGroup);
        }

        return bitsChunks;
    }
}
