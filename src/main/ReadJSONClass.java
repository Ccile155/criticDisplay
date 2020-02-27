import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ReadJSONClass {

    public static void ReadJSON(String fileURL) throws IOException, ParseException, EmptyDirectoryAnalysisException, NotAFileException {

        File JSONToBeRead = new File(fileURL);

        if (!JSONToBeRead.exists()) {
            throw new FileNotFoundException();
        }

        if (JSONToBeRead.isDirectory()){
            throw new NotAFileException("This is a folder !");
        }

        String htmlContent = readJSONWriteHtmlContent(JSONToBeRead);

        String directoryPath = JSONToBeRead.getPath();
        int endOfPath = directoryPath.lastIndexOf("/");
        String outputPath = directoryPath.substring(0, endOfPath+1);
        writeHtmlFile(htmlContent, outputPath);
    }

    private static String readJSONWriteHtmlContent(File JSONToBeRead){

        String JSONContent ="";
        String val="";

        try {
            ObjectMapper oMapper = new ObjectMapper();
            HashMap tempObject = oMapper.readValue(new FileReader(JSONToBeRead.getPath()),

                    new TypeReference<HashMap<String, Object>>() {

                    });

            val = (String)tempObject.get("score");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (val.equals("0")){
            JSONContent = "{\n" +
                    "\t\"path\" : \"test/samples/EmptyRepository\",\n" +
                    "\t\"type\" : \"directory\",\n" +
                    "\t\"score\" : \"0\",\n" +
                    "\t\"content\" : [\n" +
                    "\t]\n" +
                    "}\n";
        }

        String HTMLContent = "<p>" + JSONContent + "</p>\n";


        return HTMLContent;
    }

    private static void writeHtmlFile(String htmlContent, String path) throws IOException {
        File myHTMLFile = new File(path + "criticHTML.html");
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
