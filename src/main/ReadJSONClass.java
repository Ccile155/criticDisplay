import org.w3c.dom.ls.LSOutput;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadJSONClass {

    public static String ReadJSON(String fileURL) throws IOException, ParseException {

        File JSONToBeRead = new File(fileURL);
        if (!JSONToBeRead.exists()) {
            throw new FileNotFoundException();
        }

        if (JSONToBeRead.isDirectory()){
            throw new FileNotFoundException();
        }


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



        String htmlContent = myHTML + "</p>";


        File myHTMLFile = new File("./test/criticHTML.html");

        //myHTMLFile.createNewFile();
        FileOutputStream fileWriter = new FileOutputStream(myHTMLFile);
        fileWriter.write(htmlContent.getBytes());
        fileWriter.close();

        return htmlContent;
    }
}
