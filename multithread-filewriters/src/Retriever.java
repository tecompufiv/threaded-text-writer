import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Class in charge to work as a mechanism to read fata from a file and format it.
 * 
 * @implNote Excercise: Prepare a mechanism for initializing the starting numbers retrieved from the .txt file (each number on a new line).
 */
public class Retriever {
    
    public Retriever(){}

    /**
     * Retrieves numbers from a file and formats them as an array of integer.
     * This with the intention of simulate tasks loaded from file.
     * 
     * @param path The path to the file with the data.
     * @return The readed numbers formated for application porpuses.
     */
    public ArrayList<Integer> retrieveFrom( String path){
        ArrayList<Integer> readedNumbers = new ArrayList<Integer>();
        try{
            FileReader freader = new FileReader(appPath(path));
            BufferedReader br = new BufferedReader(freader);
            String line = br.readLine();
            while(line != null){
                readedNumbers.add(Integer.parseInt(line));
                line = br.readLine();
            }
            br.close();
        }
        catch(Exception e){}
        return readedNumbers;
    }

    private String appPath(String path){
        if(path.equals("")){
          return "./../files/numbers.txt";
        }
        else{
          return path;
        }
        
      }
}
