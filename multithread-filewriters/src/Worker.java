import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * This class is used as a thread to solve a task. 
 * 
 * @implNote Exercise: Use a critical section mechanism (e.g., semaphore) when writing to the file.
 */
public class Worker extends Thread{

    private int simulationTime = 500;
    private Semaphore semaphore = null;
    private Tasks tasks = null;
    public volatile Boolean haveToWork = null;
    private Writer writer = null;
    public int id = -1;
    public boolean working = true;

    /**
     * Constructs a worker to solve a task, when started the worker take tasks from the tasks pool. 
     * The writes the task solutions into a file.
     * 
     * @param id This workers identification.
     * @param newSimulationTime Time to be use to give sense of complexity.
     * @param newSemaphore Semaphore shared, to crontrol the writing into a file between al threads.
     * @param newTasks A reference to the tasks pool.
     * @param newFlag A flag indicating when to stop. True as default
     * @param newWriter A reference to the object able to write into a file.
     */
    public Worker(int id, int newSimulationTime, Semaphore newSemaphore, Tasks newTasks,  Boolean newFlag, Writer newWriter){
        this.simulationTime = newSimulationTime;
        this.semaphore = newSemaphore;
        this.tasks = newTasks;
        this.haveToWork = newFlag;
        this.writer = newWriter;
        this.id = id;
    }

    /**
     * Starts solving tasks by retreiving from the pool. Uses a single calculator to
     * find the task solutions.
     * <p>
     * Once the solutions are found the worker request the writing of this into a file.
     */
    private void solveTask() {
        Calculator calculator = new Calculator();
        while(haveToWork){
            working = true;
            ArrayList<Integer> taskSolutions = null;
            Integer task = tasks.getTask();
            working = false;
            if(task > 0){
                taskSolutions = calculator.calculate(task, simulationTime);
                saveSolution(task, taskSolutions);
            }
        }
    }


    /**
     * Uses the semaphore to control the usage of a Writer object, this causes the threads
     * to do not interfer during writing time. Sends the solution to a buffer to be written.
     * 
     * @param task The task solved.
     * @param taskSolutions The task solutions.
     */
    private void saveSolution(Integer task, ArrayList<Integer> taskSolutions){
        try{
            semaphore.acquire();
            writer.writeTo(id, task, taskSolutions);
        }catch(Exception e){}
        semaphore.release();
    }

    @Override
    public void run(){
        solveTask();
        
    }
}
