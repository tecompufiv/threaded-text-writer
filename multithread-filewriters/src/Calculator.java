import java.util.ArrayList;

/**
 * @implNote Problem: You should implement an application in text mode that allows you to find and write divisors of 
 * large numbers (minimum uint 32) using a simple method (checking each smaller is a divisor). In 
 * order to simulate the complexity of the calculations you can use the Thread.sleep method with 
 * less time consuming calculations.
 */
public class Calculator {
    public Calculator(){}

    /**
     * Finds return divisors of a number.
     * 
     * @param number Number to find divisord of.
     * @param simulationTime Time to sleep every time a divisor is found.
     * @return An array with the divisor in it.
     */
    public ArrayList<Integer> calculate(Integer number, int simulationTime){
        ArrayList<Integer> divisors = new ArrayList<Integer>();
        for(int i = 1 ; i <=number ; i++){
            if(number%i==0){
                try{
                    Thread.sleep(simulationTime);
                }catch(Exception e){}
                divisors.add(i);
            }
        }
        return divisors;
    }
}
