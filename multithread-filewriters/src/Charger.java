import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * This class works as a controller between user's submits to the pool and the pool queue.
 * Also injects the pool with the data obtained from the input file.
 * @implNote Mareksanicki: Implement the ability to add numbers into the queue 
 * from the console (using System.in by means of the Scanner class)
 * 
 * @implNote Mareksanicki: The termination of the application is done by typing in the console: "exit". After the command has been executed 
"exit" command all threads are to be correctly terminated and the queue is to be emptied.
 * 
 * @implNote josucano: You can submit numbers from the console, BUT, this option works while
 * a worker is aviable to take the task, this means you can submit new tasks in specific momments.
 * 
 */
public class Charger extends Thread{

    public Integer inputTask = -1;
    
    private ArrayList<Worker> workers = new ArrayList<Worker>();
    private Tasks tasks = null;
    public Writer writer = null;

    private String outputPath = "";
    private Integer maxWorkers = 5;
    private String inputPath = "";
    private int simulationTime = 100;
    public volatile Boolean haveToWork = true;
    public Semaphore semaphore = new Semaphore(1);

    /**
     * Constructs the object in charge to wait for the user submits and injections to the pool queue.
     * 
     * @param args Incoming option arguments.
     * @param newFlag A flag indicating when to stop. True as default
     */
    public Charger(String[] args, Boolean newFlag){
        this.haveToWork = newFlag;
        prepareCharger(args);
    }

    /**
     * Takes the arguments and reads them if neccesary, then builts the charger's attributes acording
     * to the given by them.
     */
    private void prepareCharger(String[] args){
        if(args.length > 0){
            for(int i = 0; i < args.length; i++){
                if(args[i].equals("-t")){maxWorkers = Integer.parseInt(args[i+1]);}
                if(args[i].equals("-s")){simulationTime = Integer.parseInt(args[i+1]);}
                if(args[i].equals("-i")){inputPath = args[i+1];}
                if(args[i].equals("-o")){outputPath = args[i+1];}
            }
        }
        prepareWorkers();
    }

    @Override
    public void run(){
        runTasks();
    }

    /**
     * Builts the worker threads and saves it's references, but do not starts them.
     * All workers share 1 semaphore, 1 pool and 1 writer, this 3 objects are builted in this method.
     */
    private void prepareWorkers(){
        Semaphore newSemaphore = new Semaphore(1);
        System.out.println("Loading data from local 'numbers.txt'\n under 'files' archive");
        Tasks newTasks = new Tasks(retrieveData());
        Writer newWriter = new Writer(outputPath);
        this.writer = newWriter;
        this.tasks = newTasks;
        for(int i = maxWorkers; i > 0; i--){
            Worker worker = new Worker(i,simulationTime, newSemaphore, newTasks, haveToWork, newWriter);
            workers.add(worker);
        }
    }

    /**
     * Obtains the tasks from the input file path
     */
    private ArrayList<Integer> retrieveData(){
        Retriever retriever = new Retriever();
        return retriever.retrieveFrom(inputPath);
        
    }

    /**
     * Starts all workers.
     * <p>
     * Starts readen from console async (While haveToWork).
     * <p>
     * When not haveToWork, ends all workers.
     * <p>
     * Saves the buffer used by the workers into an output file.
     */
    private void runTasks(){
        for (Worker worker : workers) {
            worker.start();
        }
        while(haveToWork){
            try{this.semaphore.acquire();}
            catch(Exception e){}
            if(inputTask > 0){
                tasks.addTask(inputTask);
                inputTask = -1;
            }
            this.semaphore.release();
        }
        endWorkers();
        try{
            writer.bw.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
        System.out.println("All threads terminated, please check the result in the local 'result.txt' file\n under the 'files' archive");
        
    }

    /**
     * Tells all workers to end.
     * <p>
     * If a worker is on wait(), the worker is interrupted and ends.
     * <p>
     * If a worker is executing, the worker is called to join the owner.
     */
    private void endWorkers(){
        for (Worker worker : workers) {
            System.out.println("Terminating thread: " + String.valueOf(worker.id)+ "...") ;
            try{
                synchronized(worker){
                    if(worker.working){
                        worker.haveToWork = false;
                        System.out.println("Interrupting thread: " + String.valueOf(worker.id));
                        worker.interrupt();
                    }
                    else{
                        worker.haveToWork = false;
                        System.out.println("Joining thread: " + String.valueOf(worker.id));
                        worker.join();
                    }
                }
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }

}
