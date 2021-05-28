import java.io.IOException;
import java.util.Scanner;


/**
 * @implNote Mareksanicki: The number of threads is to be as a program call parameter.
 */
public class App{
    /**
     * Starts the application.
     * 
     * @param args execution option arguments
     * @throws IOException on file manage
     */
    public static void main (String[] args) throws IOException{
        String command = "";
        Scanner scanner = new Scanner(System.in);
        Boolean flag = true;
        Charger charger = new Charger(args, flag);
        charger.start();
        while(!command.equals("exit")){
            System.out.print("Enter command: ");
            command = scanner.nextLine();
            System.out.println("In command: "+ command);
            if(!command.equals("exit")) {
                try{
                    int newInput = Integer.parseInt(command);
                    charger.semaphore.acquire();
                    charger.inputTask = newInput;
                }catch(Exception e){
                    System.out.println("Enter a number or exit");
                }
                charger.semaphore.release();
                command = "";
            }
        }
        charger.haveToWork = false;
        scanner.close();
    }
}