
import java.util.ArrayList;

/**
 * This class works as the pool (queue) of tasks to be solve, threads retreive one task at the time.
 * And a charger can add new tasks if the queue is empty.
 * 
 * @implNote Mareksanicki: Prepare a shared variable or object that will allow you to submit and retrieve subsequent tasks.
 * 
 * @implNote Mareksanicki: The threads are to wait for the request to retrieve the data.
 * @implNote Mareksanicki: To synchronize threads use the wait-notify mechanism.
 */
public class Tasks {
    
    //true if charger must wait.
    //false if worker must wait
    private boolean takingTask = true;
    
    private ArrayList<Integer> toDo = new ArrayList<Integer>();

    /**
     * Constructs a tasks pool. Manages the submit and retrieve of tasks by
     * allowing the threads to work with wait() and notify() when using this object.
     * 
     * @param newTasks The new tasks to be added into the pool.
     */
    public Tasks(ArrayList<Integer> newTasks){
        this.toDo = newTasks;
    }

    /**
     * Adds a new task into the pool.
     * <p>
     * If a thread is taking a task, the thread triying to add a new task must wait().
     * <p>
     * If no thread is taking a task, the new task is added and notifyAll() threads wating.
     * 
     * @param newTask New task to be added.
     */
    public synchronized void addTask(Integer newTask){
        while(takingTask){
            try{
                wait();
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        this.toDo.add(newTask);
        takingTask = true;
        notifyAll();
    }

    /**
     * Gets a task from the pool.
     * <p>
     * If a thread is adding a task, the thread triying to get a task must wait().
     * <p>
     * If no thread is adding a task, the thread triying to get a task obtains and
     * removes it from the pool, then notifyAll() waiting threads if empty.
     * 
     * @return
     */
    public synchronized Integer getTask(){
        while(!takingTask){
            try{
                wait();
            }
            catch(InterruptedException e){
                return -1;
            }
        }
        
        if(this.toDo.size() > 0){
            Integer myTask = this.toDo.get(this.toDo.size()-1);
            this.toDo.remove(this.toDo.size() -1);
            notifyAll();
            return myTask;
        }
        else{
            takingTask = false;
            notifyAll();
            return 0;
        }
    }
}
