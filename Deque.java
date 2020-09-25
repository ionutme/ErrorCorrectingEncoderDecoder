package correcter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Deque extends ArrayDeque<Character>{

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

    public char[] getRestChunkWithTrailingZeros(int chunk) {
        var restChunks = this.getChunksWithTrailingZeros(chunk);

        return restChunks.get(restChunks.size() - 1);
    }

    private ArrayList<char[]> getChunksWithTrailingZeros(int chunk) {
        var bitsChunks = this.getChunks(chunk);

        int rest = this.size() % chunk;
        if (rest > 0) {
            bitsChunks.add(getRestChunkWithTrailingZeros(chunk, rest));
        }

        return bitsChunks;
    }

    private char[] getRestChunkWithTrailingZeros(int chunk, int rest) {
        char[] lastBitsGroup = getChunkWithZeros(chunk);

        int endMsgIndex = chunk - rest + 1;
        setMessageBits(endMsgIndex, lastBitsGroup);

        return lastBitsGroup;
    }

    private char[] getChunkWithZeros(int chunk) {
        var lastBitsGroup = new char[chunk];
        Arrays.fill(lastBitsGroup, '0');

        return lastBitsGroup;
    }

    private void setMessageBits(int toIndex, char[] lsatBitsGroup) {
        for (int i = 0; i < toIndex; i++) {
            lsatBitsGroup[i] = this.pop();
        }
    }
}
