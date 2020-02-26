import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestReader {

    @Test
    void NonExistantFileCase() {
        String NonExistantURL= "NonExistantURL";
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            ReadJSONClass.ReadJSON(NonExistantURL);
        });
    }

    @Test
    void FolderNotFileCase() {
        String folderNotFileURL = "./test/RepositoryWithOneFile";
        Assertions.assertThrows(ReadJSONClass.NotAFileException.class, () -> {
            ReadJSONClass.ReadJSON(folderNotFileURL);
        });
    }

    @Test
    void EmptyFileCase() {
        String emptyFileURL = "./test/emptyFile.json";
        Assertions.assertThrows(ParseException.class, () -> {
            ReadJSONClass.ReadJSON(emptyFileURL);
        });
    }

    @Test
    void EmptyDirectoryAnalysisCase() {
        String EmptyDirectoryAnalysisURL = "./test/EmptyRepository/critic.json";
        Assertions.assertThrows(ReadJSONClass.EmptyDirectoryAnalysisException.class, () -> {
            ReadJSONClass.ReadJSON(EmptyDirectoryAnalysisURL);
        });
    }

    @Test
    void StringFromJSONFileCase() {
        String JSONFileURL = "./test/RepositoryWithOneFile/critic.json";
        String htmlContent = "<p>{\n" +
                "\t\"path\" : \"test/samples/RepositoryWithOneFile\",\n" +
                "\t\"type\" : \"directory\",\n" +
                "\t\"score\" : \"1\",\n" +
                "\t\"content\" : [\n" +
                "\t\t{\n" +
                "\t\t\t\"path\" : \"firstFile.txt\",\n" +
                "\t\t\t\"type\" : \"file\",\n" +
                "\t\t\t\"score\" : \"1\",\n" +
                "\t\t\t\"content\" : [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}\n" +
                "</p>";
        Assertions.assertDoesNotThrow(() -> {
            String contentToPrint = ReadJSONClass.ReadJSON(JSONFileURL);
            assertEquals(htmlContent, contentToPrint);
        });
    }
    @Disabled
    @Test
    void WriteHTMLFileCase() {
        //String JSONFileURL = "../critic/test/samples/JSONFile.json";
        String JSONFileURL = "./test/RepositoryWithOneFile/critic.json";
        String generatedHTMLFileURL = "./test/criticHTML.html";
        String expectedHTMLFileURL = "./test/expectedEmptyHTMLFile.html";

        RemoveFile(generatedHTMLFileURL);

        Assertions.assertDoesNotThrow(() -> {
            ReadJSONClass.ReadJSON(JSONFileURL);
        });
        assertTrue(FilesContentsAreEquals(expectedHTMLFileURL, generatedHTMLFileURL));
    }

    private boolean FilesContentsAreEquals(String path1, String path2) {
        File f1 = new File(path1);
        File f2 = new File(path2);
        try {
            byte[] content1 = Files.readAllBytes(f1.toPath());
            byte[] content2 = Files.readAllBytes(f2.toPath());
            return Arrays.equals(content1, content2);
        } catch (Exception e)
        {
            return false;
        }
    }

    private void RemoveFile(String fileName) {
        File f = new File(fileName);
        f.delete();
    }

}