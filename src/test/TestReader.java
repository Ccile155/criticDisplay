import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
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
        String folderNotFileURL = "../../../critic/test/samples/EmptyRepository/";
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            ReadJSONClass.ReadJSON(folderNotFileURL);
        });
    }

    @Test
    void emptyJSONFileCase() {
        String emptyJSONFileURL = "../../../critic/test/samples/emptyJSONFile.JSON";
        Assertions.assertThrows(FileNotFoundException.class, () -> {
        assertEquals("<p></p>", ReadJSONClass.ReadJSON(emptyJSONFileURL));
        });
    }

    @Test
    void simpleJSONFileCase() {
        String simpleJSONFileURL = "../../../critic/test/samples/RepositoryWithOneFile/critic.JSON";
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            assertEquals("<p>{\n" +
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
                    "}</p>", ReadJSONClass.ReadJSON(simpleJSONFileURL));
        });
    }

/*    @Test
    void emptyJSONFileCase() {
        String emptyJSONFileURL = "../../../critic/test/samples/emptyJSONFile.JSON";
        Assertions.assertThrows(FileNotFoundException.class, () -> {
        ReadJSONClass.ReadJSON(emptyJSONFileURL);
        });
        String emptyHTMLFileURL = "../../../critic/test/samples/criticHTML.html";
        String emptyExpectedHTMLFileURL = "../../../critic/test/samples/expectedEmptyHTMLFile.html";
        //File emptyHTMLFile = new File(emptyHTMLFileURL);
        //File emptyExpectedHTMLFile = new File(emptyExpectedHTMLFileURL);
        assertTrue(FilesContentsAreEquals(emptyExpectedHTMLFileURL, emptyHTMLFileURL));
    }*/

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

}