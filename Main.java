package correcter;

import java.io.File;
import java.util.Scanner;

public class Main {

    private static final File inputFile = new File("send.txt");
    private static final File encodedFile = new File(".\\..\\..\\encoded.txt");
    private static final File receivedFile = new File(".\\..\\..\\received.txt");
    private static final File decodedFile = new File(".\\..\\..\\decoded.txt");

    public static void main(String[] args) {
        System.out.print("Write a mode: ");

        var scanner = new Scanner(System.in);
        String mode = scanner.next().toUpperCase();

        switch (mode) {
            case "ENCODE":
                encode();
                break;
            case "SEND":
                send();
                break;
            case "DECODE":
                decode();
                break;
            case "ALL":
                encode();
                send();
                decode();
                break;
            default:
                return;
        }
    }

    private static void encode() {
        FileWorker fileWorker = new Encoder();
        fileWorker.process(inputFile, encodedFile);
    }

    private static void send() {
        FileWorker fileWorker = new Interceptor();
        fileWorker.process(encodedFile, receivedFile);
    }

    private static void decode() {
        FileWorker fileWorker = new Decoder();
        fileWorker.process(receivedFile, decodedFile);
    }
}
