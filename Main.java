package correcter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;

class StringSplitter {
    public static List<String> split(String text, int size) {
        int capacity = (text.length() + size - 1) / size;
        List<String> sequence = new ArrayList<>(capacity);

        for (int startIndex = 0; startIndex < text.length(); startIndex += size) {
            int nextIndex = startIndex + size;
            int endIndex = Math.min(text.length(), nextIndex);

            String substring = text.substring(startIndex, endIndex);
            sequence.add(substring);
        }
        return sequence;
    }
}

class RandomChar {
    public static char next() {
        var random = new Random();
        int choice = random.nextInt(3);

        switch (choice) {
            case 0:
                return getRandomNumber();
            case 1:
                return getRandomLowercaseLetter();
            case 2:
                return getRandomUppercaseLetter();
        }

        return ' ';
    }

    private static char getRandomNumber() {
        int nr = new Random().nextInt(10);

        return Character.forDigit(nr, 10);
    }

    private static char getRandomUppercaseLetter() {
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

        return getRandomLetter(charset);
    }

    private static char getRandomLowercaseLetter() {
        String charset = "abcdefghijklmnopqrstuvwxyz ";

        return getRandomLetter(charset);
    }

    private static char getRandomLetter(String charset) {
        var random = new Random();
        int index = random.nextInt(charset.length());

        return charset.charAt(index);
    }
}

/**
 * Generates text having 1 error every 'SIZE' times.
 */
class TextWithErrorsGenerator {

    private final int size;

    TextWithErrorsGenerator(int size) {
        this.size = size;
    }

    public String get(String text) {
        var textWithErrors = new StringBuilder(text.length());

        var sequence = StringSplitter.split(text, this.size);
        for (String string : sequence) {
            textWithErrors.append(getStringWithError(string));
        }
        return textWithErrors.toString();
    }

    private static StringBuilder getStringWithError(String string) {
        int randomCharIndex = new Random().nextInt(string.length());
        char randomChar = getRandomChar(string, randomCharIndex);

        var stringWithOneError = new StringBuilder(string);
        stringWithOneError.setCharAt(randomCharIndex, randomChar);

        return stringWithOneError;
    }

    private static char getRandomChar(String string, int randomCharIndex) {
        char randomChar;

        do {
            randomChar = RandomChar.next();
        } while (string.charAt(randomCharIndex) == randomChar);

        return randomChar;
    }
}

class TextEncoder {
    private final int size;

    TextEncoder(int size) {
        this.size = size;
    }

    public String encode(String text) {
        int count = this.size;

        var encodedText = new StringBuilder(text.length() * count);
        for (char character : text.toCharArray()) {
            encodedText.append(getMultipliedChar(character, count));
        }

        return encodedText.toString();
    }

    private static char[] getMultipliedChar(char character, int count) {
        char[] sameCharacters = new char[count];
        Arrays.fill(sameCharacters, character);

        return sameCharacters;
    }
}

class TextDecoder {
    private final int size;

    TextDecoder(int size) {
        this.size = size;
    }

    public String decode(String textWithErrors) {
        var text = new StringBuilder();

        var sequence = StringSplitter.split(textWithErrors, this.size);
        for (String string : sequence) {
            text.append(getDuplicatedChar(string));
        }

        return text.toString();
    }

    private char getDuplicatedChar(String string) {
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    return chars[i];
                }
            }
        }

        return '#';
    }
}

class TextProcessor {
    public final int chunkSize;
    private final ArrayList<Consumer<String>> textConsumers;

    TextProcessor(int chunkSize) {
        this.chunkSize = chunkSize;

        this.textConsumers = new ArrayList<>();
    }

    public void addTextConsumer(Consumer<String> textConsumer) {
        textConsumers.add(textConsumer);
    }

    public void process(String text) {
        // consume initial text
        consume(text);

        // processing pipeline
        String encodedText = getTextEncoded(text);
        String textWithErrors = getTextWithErrors(encodedText);
        String textDecoded = getTextDecoded(textWithErrors);
    }

    private String getTextEncoded(String text) {
        var textEncoder = new TextEncoder(this.chunkSize);
        String encodedText = textEncoder.encode(text);
        consume(encodedText);

        return encodedText;
    }

    private String getTextWithErrors(String encodedText) {
        var textWithErrorsGenerator = new TextWithErrorsGenerator(this.chunkSize);
        String textWithErrors = textWithErrorsGenerator.get(encodedText);
        consume(textWithErrors);

        return textWithErrors;
    }

    private String getTextDecoded(String textWithErrors) {
        var textDecoder = new TextDecoder(this.chunkSize);
        String decodedText = textDecoder.decode(textWithErrors);

        consume(decodedText);

        return decodedText;
    }

    private void consume(String text) {
        for (Consumer<String> textConsumer : this.textConsumers){
            textConsumer.accept(text);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        String textMessage = scanner.nextLine();

        var textProcessor = new TextProcessor(3);
        textProcessor.addTextConsumer(System.out::println);
        textProcessor.process(textMessage);
    }
}
