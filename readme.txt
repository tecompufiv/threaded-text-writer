========================================================
usage: (when located command promt in 'class' archive)
	
	Flags:
		-t <Integer> indicates the number of threads to solve tasks.
		-s <Integer> indicates ths time to be use in time.sleep(time) to give sense of complexity.
 
	Run:
		$ java App -t <Integer> -s <Integer>
		
	e.g.	$ java App -t 5 -s 100
		
		Meaning the programm starts with 5 threads to solve tasks, and 100 miliseconds to wait every time 
		a divisor is found.

=========================================================
Verbose description if the program's work flow.

verbose pseudocode:
	0 - Have to work, is true.
	1 - Console starts. [Thread]
		1.0 - scannner reads
			1.0 - exit
				1.0.0 - have to work, is false
				1.0.1 - terminates charger
			1.1 - number
				1.1.0 - injects the number in charger
		1.1 - goto: 1.0
	2 - Charger is created.
		2.0 - Charger initiates the instance with the option arguments.
		2.1 - Charger retrieves data from input file.
		2.2 - Charger creates a Writer instance.
		2.3 - Charger creates a Semaphore instance.
		2.4 - Charger creates a Task instance and injected with the retrieved data.
		2.5 - Charger prepares the threads.
		2.6 - Charger shares the mentioned instances with every thread.
	3 - Charger starts. [Thread]
		3.0 - Charger starts every thread created before. [starts in 4]
		3.1 - Charger reads local atribute specting change.
		3.2 - If have to work
			3.1.0 - if new value 
				3.1.0.0 - Adds new task to Tasks
				3.1.0.1 - if can't add: wait()
				3.1.0.2 - add(task)
				3.1.0.3 - notifyAll(at 4.1.1)
				3.1.0.4 - value = -1
			3.1.1 - goto: 3.2
	4 - Worker starts. [Thread]
		4.0 - Creates a calculator instance.
		4.1 - If have to work
			4.1.0 - Gets a new task to solve
			4.1.1 - If can't get: wait()
			4.1.2 - get(task)
			4.1.3 - notifyAll(at 3.1.0.1)
			4.1.4 - solve task
			4.1.5 - save solutions
		4.2. - goto: 4.1
		