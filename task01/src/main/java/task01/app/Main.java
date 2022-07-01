package task01.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String csvFile = "";
        String template = "";
        List<String> individuals = new LinkedList<String>();
        String originalMessage = "";

        //Checks for path in argument
        if(args.length <=0) {
            System.out.println("Invalid arguments given.");
            return;
        } else {
            try {
                csvFile = args[0];
                template = args[1];
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Out of bounds");
            }
        }

        Path pathCSV = Paths.get(csvFile);
        Path pathTemplate = Paths.get(template);
        //checks if files exists at path given
        if (Files.exists(pathCSV) && Files.exists(pathTemplate)) {
            File csv = pathCSV.toFile();
            File messageTemplate = pathTemplate.toFile();
            if(!csv.getName().contains(".csv") || !messageTemplate.getName().contains(".csv")) {
                System.out.println("Files given are not appropriate format.");
            } else {
                try{
                    //read the files and save to variables
                    InputStream is = new FileInputStream(csv);
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    while((line = br.readLine()) != null) {
                        individuals.add(line);
                    }
                    br.close();
                    isr.close();
                    is.close();
    
                    is = new FileInputStream(messageTemplate);
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);
                    originalMessage = br.lines().collect(Collectors.joining("\n"));
    
                    br.close();
                    isr.close();
                    is.close();
    
                    //parses first line to know what are the variables to replace
                    String[] sfl = individuals.get(0).split(",");
                    //reads the subsequent lines and replaces the variables with the actual terms
                    for(int i=1; i<individuals.size();i++) {
                        String message = originalMessage;
                        String[] splitTerms = individuals.get(i).split(",");
    
                        message = message.replace("__"+sfl[0]+"__", splitTerms[0]);
                        message = message.replace("__"+sfl[1]+"__", splitTerms[1]);
                        message = message.replace("__"+sfl[2]+"__", splitTerms[2]);
                        message = message.replace("__"+sfl[3]+"__", splitTerms[3]);
                        message = message.replace("\\n", "\n");
    
                        System.out.println(message);
                        System.out.println();
                    }
            
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Files not found at given path");
        }
    }
}
