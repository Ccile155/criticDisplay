import org.w3c.dom.ls.LSOutput;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadJSONClass {

    public static String ReadJSON(String fileURL) throws IOException, ParseException, EmptyDirectoryAnalysisException, NotAFileException {

        File JSONToBeRead = new File(fileURL);

        if (!JSONToBeRead.exists()) {
            throw new FileNotFoundException();
        }

        if (JSONToBeRead.isDirectory()){
            throw new NotAFileException("This is a folder !");
        }

        String htmlContent = readJSONWriteHtmlContent(JSONToBeRead);

        writeHtmlFile(htmlContent);

        return htmlContent;
    }

    private static String readJSONWriteHtmlContent(File JSONToBeRead) throws ParseException, IOException, EmptyDirectoryAnalysisException {
        FileReader JSONToBeReadInputStream = new FileReader(JSONToBeRead);
        Scanner JSONScanner = new Scanner(JSONToBeReadInputStream);

        if (!JSONScanner.hasNextLine()){
            throw new ParseException("Empty file", 0);
        }

        String myHTML = "<p>";
        while(JSONScanner.hasNextLine()) {
            String line = JSONScanner.nextLine();
                try {
                    myHTML+= line + "\n";
                } catch (Exception var6) {
                    System.out.println(var6.getMessage());
                }
            }
        JSONToBeReadInputStream.close();

        checkEmptyDirAnalysisContent(myHTML);

        return myHTML + "</p>\n";
    }

    private static void checkEmptyDirAnalysisContent(String myHTML) throws EmptyDirectoryAnalysisException {
        String emptyRepositoryAnalysisHtmlContent = "<p>{\n" +
                "\t\"path\" : \"test/samples/EmptyRepository\",\n" +
                "\t\"type\" : \"directory\",\n" +
                "\t\"score\" : \"0\",\n" +
                "\t\"content\" : [\n" +
                "\t]\n" +
                "}\n";

        if (myHTML.equals(emptyRepositoryAnalysisHtmlContent)) {
            throw new EmptyDirectoryAnalysisException("The analyzed directory was empty.");
        }
    }

    private static void writeHtmlFile(String htmlContent) throws IOException {
        File myHTMLFile = new File("./test/criticHTML.html");
        FileOutputStream fileWriter = new FileOutputStream(myHTMLFile);
        fileWriter.write(htmlContent.getBytes());
        fileWriter.close();
    }

    public static class EmptyDirectoryAnalysisException extends Exception {
        public EmptyDirectoryAnalysisException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class NotAFileException extends Exception {
        public NotAFileException(String errorMessage) {
            super(errorMessage);
        }
    }

}
