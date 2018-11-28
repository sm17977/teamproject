package pesonalFinanceApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//Class to write CSV File
public class Write {

    private String FileName = "outputData.csv";



    public Write(){
    }


     void writeToFile (ArrayList list){
         try {
             FileWriter fileWriter = new FileWriter(FileName, true);
             File f = new File(FileName);
             PrintWriter printWriter = new PrintWriter(fileWriter);

             //This will retrieve values from today, to view historical data use a loop
             printWriter.println(list.get(list.size() -1));
             printWriter.close();
             System.out.println("Writing to: " + f.getAbsolutePath());

         }
         catch (IOException e){
             System.out.println("File creation failed.");
         }




     }



}
