import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadJSONClass {

    public static void ReadJSON(String fileURL) throws IOException, NotAFileException {

        File JSONToBeRead = new File(fileURL);

        if (!JSONToBeRead.exists()) {
            throw new FileNotFoundException();
        }

        if (JSONToBeRead.isDirectory()) {
            throw new NotAFileException("This is a folder !");
        }

        String htmlContent = readJSONWriteHtmlContent(JSONToBeRead);

        String directoryPath = JSONToBeRead.getPath();
        int endOfPath = directoryPath.lastIndexOf("/");
        String outputPath = directoryPath.substring(0, endOfPath + 1);

        writeHtmlFile(htmlContent, outputPath);
    }

    private static String readJSONWriteHtmlContent(File JSONToBeRead) {

        String htmlContent = "";
        String path = "";
        String type = "";
        String score = "";
        ArrayList content = new ArrayList();

        try {
            FileReader jsonFileReader = new FileReader(JSONToBeRead.getPath());
            ObjectMapper oMapper = new ObjectMapper();
            HashMap<String, Object> criticFileMap;

            if (jsonFileReader != null) {
                criticFileMap = oMapper.readValue(jsonFileReader, new TypeReference<>() {
                });
            } else {
                throw new ParseException("Empty file", 0);
            }
            path = (String) criticFileMap.get("path");
            type = (String) criticFileMap.get("type");
            score = (String) criticFileMap.get("score");
            content = (ArrayList) criticFileMap.get("content");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (score.equals("0")) {
            htmlContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\t<meta charset=\"utf-8\">\n" +
                    "\t<!--<link rel=\"stylesheet\" href=\"critic.css\">-->\n" +
                    "\t<title>Critic</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "\n" +
                    "\t<div>\n" +
                    "\t\t<table>\n" +
                    "\t\t\t<tbody>\n" +
                    "\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t<td><img src=\"../image/folderIcon.jpg\" alt=\"folder icon\" height=\"40px\"></td>\n" +
                    "\t\t\t\t\t<td width=\"400px\">test/samples/EmptyRepository</td>\n" +
                    "\t\t\t\t\t<td>0</td>\n" +
                    "\t\t\t\t</tr>\n" +
                    "\t\t\t</tbody>\n" +
                    "\t\t</table>\n" +
                    "\t</div>\n" +
                    "\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n";
        }
        return htmlContent;
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
