# Java: Write into a file using multiple threads
This is a simulation on how multiple threads works syncronized to write into the same file. This small excercise shows how to use the program and how it works.

## Java usage
Using threads from java and the notify()-wait() functions and the semaphore class to control the resources consumption. 

## Usage
Usage here

## Problem
The idea is to use the threads to simulate a complex number calculation, said number comes from an input file "number.txt", then this numbers are stored in a queue waiting tobe recolected by a thread, when calculated the thread attemps to write the result in output file "output.txt". Let me describe this problem as a sequence of steps.
1. The programs reads the file.
2. All numbers are saved in a queue.
3. Threads can recolect a number each.
4. Every thread simulates a complex calculation.
5. Threads attemp to write the result in a file.
6. While all above happens the user can insert new numbers into the queue.
7. The program finishes when que queue is empty.

## Rules
 * The program must run with the user being able to insert a number into the queue with no problem. 
 * Amount of Threads used are set by the user.
 * No errors when the result is written.
 * Total control over wich thread is using the output file.
 * Threads must syncronize when recollecting its number.
 * The results must say which thread solved it.
 * The user interface must not freeze.

## Solution
[image]

## Conclusion

## Additional information
