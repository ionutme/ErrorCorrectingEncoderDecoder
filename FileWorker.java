package correcter;

import java.io.*;

abstract class FileWorker {
    public void process(File inputFile, File outputFile) {
        try (var fileOutputStream = new FileOutputStream(outputFile)) {
            try (var fileInputStream = new FileInputStream(inputFile)) {

                transform(fileInputStream, fileOutputStream);

            } catch (FileNotFoundException exception) {
                System.out.println(getFileNotFoundErrorMessage(inputFile));
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } catch (IOException exception) {
            System.out.println(getIOErrorMessage(outputFile));
            exception.printStackTrace();
        }
    }

    abstract void transform(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException;

    private static String getFileNotFoundErrorMessage(File file) {
        return String.format("The file %s\\%s was not found!", System.getProperty("user.dir"), file.getPath());
    }

    private static String getIOErrorMessage(File file) {
        return String.format("There was a problem when writing into %s!", file.getPath());
    }
}
