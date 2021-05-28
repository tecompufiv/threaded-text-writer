import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;  
import java.io.IOException;  

/** 
 * The Writer class is a container to control threads requests to write into a file.
 * Works as a wrapper instances to be used by a thread to write into the output file.
 * 
 * @implNote Exercise: We put the results in one .txt file in the form:
 * "number given1: divisors1, divisor2, divisor3 ...
 * number given2: divisors1, divisor2, divisor3 ..."
*/
public class Writer {

    public String path = "";
    public BufferedWriter bw = null;
    public boolean closed = false;

    /**
     * Prepares an instance to be used by a thread to write into the output file.
     * 
     * @param newPath The path to write on.
     */
    public Writer(String newPath){
      this.path = newPath;
      try{
        FileWriter myWriter = new FileWriter(appPath(path));
        bw = new BufferedWriter(myWriter);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    
    /**
     * Writes the parameters (as a String) into the output file.
     * 
     * @param id Worker's unique identification.
     * @param task The task solved
     * @param taskSolutions The solutions for the given task
     */
    public synchronized void writeTo(int id,Integer task, ArrayList<Integer> taskSolutions){
        
        try {
            bw.write(toWrite(id, task, taskSolutions));
          } catch (IOException e) {
            e.printStackTrace();
      }
    }
    
    /**
     * Prepares the input parameters as a string to be written in the output file
     * 
     * @param id Worker's unique identification.
     * @param task The task solved
     * @param taskSolutions The solutions for the given task
     * @return The line as a String to be written.
     */
    private String toWrite(int id, Integer task, ArrayList<Integer> taskSolutions){
      String res = "Solved by: Thread "+String.valueOf(id)+" ";
      res = res + String.valueOf(task) + ": ";
      for(Integer solution: taskSolutions){
        res = res + String.valueOf(solution) + ", ";
      }
      return res + "\n";
    }

    /**
     *  Returns the app output file path.
     *  <p>
     * If the given path is empty returns the deafult path *...result.txt
     * <p>
     * If a custom path is given, is returned.
     * @param path The path to be use by the application.
     * @return The application path.
     */
    private String appPath(String path){
      if(path.equals("")){
        return "./../files/result.txt";
      }
      else{
        return path;
      }
      
    }

}
