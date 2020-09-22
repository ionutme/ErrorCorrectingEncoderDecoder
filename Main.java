package correcter;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        var inputFile = new File("send.txt");
        var outputFile = new File("received.txt");

        TextInterceptor.process(inputFile, outputFile);
    }
}
