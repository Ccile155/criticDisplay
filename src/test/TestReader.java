import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestReader {

    @Test
    void NonExistantFileCase() {
        String NonExistantPath= "NonExistantPath";
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            ReadJSONClass.ReadJSON(NonExistantPath);
        });
    }

    @Test
    void FolderNotFileCase() {
        String folderNotFilePath = "./test/RepositoryWithOneFile";
        Assertions.assertThrows(ReadJSONClass.NotAFileException.class, () -> {
            ReadJSONClass.ReadJSON(folderNotFilePath);
        });
    }

    @Disabled
    @Test
    void EmptyJSONFileCase() {
        String emptyJSONFilePath = "./test/emptyFile.json";
        Assertions.assertThrows(ParseException.class, () -> {
            ReadJSONClass.ReadJSON(emptyJSONFilePath);
        });
    }

    @Test
    void EmptyDirectoryAnalysisCase() {
        String EmptyDirectoryAnalysisPath = "./test/EmptyRepository/critic.json";
        String generatedHTMLFilePath = "./test/EmptyRepository/criticHTML.html";
        String expectedHTMLFilePath = "./test/EmptyRepository/expectedEmptyHTMLFile.html";

        RemoveFile(generatedHTMLFilePath);

        Assertions.assertDoesNotThrow(() -> ReadJSONClass.ReadJSON(EmptyDirectoryAnalysisPath));

        File generatedHTMLFile = new File(generatedHTMLFilePath);
        assertTrue(generatedHTMLFile.exists());
        File expectedHTMLFile = new File(expectedHTMLFilePath);
        assertTrue(FilesContentsAreEquals(expectedHTMLFilePath, generatedHTMLFilePath));
    }

    @Disabled
    @Test
    void WriteHTMLFileCase() {
        //String JSONFileURL = "../critic/test/samples/JSONFile.json";
        String JSONFilePath = "./test/RepositoryWithOneFile/critic.json";
        String generatedHTMLFilePath = "./test/criticHTML.html";
        String expectedHTMLFilePath = "./test/expectedHTMLFile.html";

        RemoveFile(generatedHTMLFilePath);

        Assertions.assertDoesNotThrow(() -> ReadJSONClass.ReadJSON(JSONFilePath));

        File generatedHTMLFile = new File(generatedHTMLFilePath);
        assertTrue(generatedHTMLFile.exists());
        File expectedHTMLFile = new File(expectedHTMLFilePath);
        assertTrue(FilesContentsAreEquals(expectedHTMLFilePath, generatedHTMLFilePath));
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