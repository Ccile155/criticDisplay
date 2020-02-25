import org.w3c.dom.ls.LSOutput;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadJSONClass {

    private static String myHTML;

    public static String ReadJSON(String fileURL) throws IOException {

        File JSONToBeRead = new File(fileURL);
        if (!JSONToBeRead.exists()) {
            throw new FileNotFoundException();
        }

/*        if (JSONToBeRead.isDirectory()){
            throw new FileNameExtensionFilter();
        }

        myHTML = "<p>";

        FileReader JSONToBeReadInputStream = new FileReader(JSONToBeRead);
        Scanner JSONScanner = new Scanner(JSONToBeReadInputStream);

        while(JSONScanner.hasNextLine()) {
            String line = JSONScanner.nextLine();
                try {
                    myHTML+= line;
                } catch (Exception var6) {
                    System.out.println(var6.getMessage());
                }
            }
        JSONToBeReadInputStream.close();

        File myHTMLFile = new File("../../../critic/test/samples/criticHTML.html");
        myHTMLFile.createNewFile();
        FileOutputStream fileWriter = new FileOutputStream(myHTMLFile);
        fileWriter.write((myHTML + "</p>").getBytes());*/
        return myHTML + "</p>";
    }
}
