# Process Scheduler Simulation

# Why Was This Program Created?

This program was created as a final project for CS526. The main requirements 
were to show a good use of java and data structures.

# Description of the Scheduling System

Processes arrive at a computer system and the computer system executes the processes one at
a time based on a priority criterion. Each process has a process id, priority, arrival time, and
duration. The duration of a process is the amount of time it takes to completely execute the
process. The system keeps a priority queue to keep arriving processes and prioritize the
execution of processes. When a process arrives, it is inserted into the priority queue. Then,
each time the system is ready to execute a process, the system removes a process with the
smallest priority from the priority queue and executes it for the duration of the process. Once
the system starts executing a process, it finishes the execution of the process completely
without any interruption.

Suppose that a process p with a very large priority arrives at the system while the system is
executing another process. While p is waiting in the queue, another process q with a smaller
priority arrives. After the execution of the current process is finished, the system will remove
and execute q (because q has a smaller priority), and p must wait until q finishes. If another
process with a smaller priority arrives while q is being executed, p will have to wait again
after q is completed. If this is repeated, p will have to wait for a very long time. One way of
preventing this is as follows: If a process has waited longer than a predetermined maximum
wait time, we update the priority of the process by decreasing the priority by one. For
example, if the current priority of the process is 5, then it is updated to 4. Technically, we
have to perform this update at each (logical) time. However, to make the simulation program
simple, we will do this update only when the system finishes the execution of a process.

#Data Structures

To complete this project I decided to use 3 data structures all of which were priority queues. The first priority queue is used to hold the process information we get from the text file. I decided to use a priority queue because I wanted to have the processes sorted by arrival time. By using this data structure, it made it easier to later pop processes as the current time was more than or equal to the process arrival and input them into the second priority queue. An additional benefit about using a priority queue from java is that I did not have to worry about the size of the input data as I would if I used a simpler data structure such as an array.

The second and third priority queues are connected. The second priority queue is used to transfer the processes once the arrival time condition is met. In this priority queue the processes are stored by priority which simplifies the order and which we process each process.
The reason I used a third priority queue is to be able to update the priority of processes as they met the wait time condition. This third priority queue serves as a temporary data holding area because we can only look at the top of the pile of queues. During each loop I test whether any process has meet the wait time condition. If it has meet the wait time condition the priority gets updated and that process gets added to the third priority queue. If the process has not met the criteria it gets added into the third priority queue without any changes. Once the second priority queue is empty then we add everything back from the third priority queue.

#Observations

Although I used priority queues as the main data structures in my program I first tested my data with a simple array with a fixed size to test that all the data was imported correctly and completely. This showed me that there are many different data structures that could work better or worse for the assignment. However, I believe that my choice of using priority queues was good as it made it easy to have it always sorted in the order that I needed it.

Another observation that I have is that I have some repetition on my code. Once data structure 1 is empty then I had to copy my inner loop into another loop to empty data structure 2. If I had had more time I think there might be a way to make this code better and less repetitive.
