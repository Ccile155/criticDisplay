import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
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

        String outputPath = getOutputFilePath(JSONToBeRead);

        writeHtmlFile(htmlContent, outputPath);
    }

    private static String readJSONWriteHtmlContent(File JSONToBeRead) throws FileNotFoundException {

        String htmlContent = "";
        String path = "";
        String type = "";
        String score = "";
        ArrayList content = new ArrayList();

        if (JSONToBeRead.length() == 0) {
            htmlContent = getEmptyFileHtmlContent();

        } else {
            FileReader jsonFileReader = new FileReader(JSONToBeRead.getPath());
            ObjectMapper oMapper = new ObjectMapper();
            HashMap<String, Object> criticFileMap = new HashMap<>();

            try {
                criticFileMap = oMapper.readValue(jsonFileReader, new TypeReference<>() {
                });

                path = (String) criticFileMap.get("path");
                type = (String) criticFileMap.get("type");
                score = (String) criticFileMap.get("score");
                content = (ArrayList) criticFileMap.get("content");

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (score.equals("0")) {
                htmlContent = getHtmlContent();
            }
        }
        return htmlContent;
    }

    private static String getHtmlContent() {
        String htmlContent = "<!DOCTYPE html>\n" +
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
                "</body>\n" +
                "</html>\n";
        return htmlContent;
    }

    private static String getEmptyFileHtmlContent() {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<!--<link rel=\"stylesheet\" href=\"critic.css\">-->\n" +
                "\t<title>Critic</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\t<div>\n" +
                "\t\t<table>\n" +
                "\t\t\t<tbody>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td><img src=\"../image/invalidFile.png\" alt=\"invalid file error\" height=\"50px\"></td>\n" +
                "\t\t\t\t\t<td>Invalid file error : the analyzed file was empty !</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</tbody>\n" +
                "\t\t</table>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>\n";
        return htmlContent;
    }

    private static String getOutputFilePath(File JSONToBeRead) {
        String directoryPath = JSONToBeRead.getPath();
        int endOfPath = directoryPath.lastIndexOf("/");
        return directoryPath.substring(0, endOfPath + 1);
    }
    
    private static void writeHtmlFile(String htmlContent, String path) throws IOException {
        File myHTMLFile = new File(path + "criticHTML.html");
        FileOutputStream fileWriter = new FileOutputStream(myHTMLFile);
        fileWriter.write(htmlContent.getBytes());
        fileWriter.close();
    }

    public static class NotAFileException extends Exception {
        public NotAFileException(String errorMessage) {
            super(errorMessage);
        }
    }
}
