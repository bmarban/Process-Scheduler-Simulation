/**
 * @author Barbara Marban
 * @since 04/15/2021
 * Assignment: Final Project
 * Description: This is a simulation program to show the processing of processes based on the priorty and arrival time
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ProcessScheduling {

    private static PriorityQueue<Process> inputProcesses(String fileName) throws IOException {
        /**
         * this method populates the process class and adds the processes into a priority queue
         * input: a text file that has a list of processes
         * output: a priority queue
         */
        //read file with process throw IO exception if failure
        try (Scanner lineScan = new Scanner(new File(fileName))) {
            PriorityQueue<Process> q1 = new PriorityQueue<>(new TimeComparator());
            int k = 0; // index

            int pr;  // priority of the process
            int id;  // process id
            int arrivalTime;  // the time when the process arrives at the system
            int duration; // duration

            while(lineScan.hasNext()) // read full file until the end
            {
                String lineInput = lineScan.nextLine();// scan file line by line

                // get data for each process
                String[] splitLines = lineInput.split(" ");
                id = Integer.parseInt(splitLines[0]);
                pr = Integer.parseInt(splitLines[1]);
                duration = Integer.parseInt(splitLines[2]);
                arrivalTime = Integer.parseInt(splitLines[3]);

                Process p = new Process(pr, id, arrivalTime, duration); // populate process class
                q1.add(p); // populate q1 priority queue
                k++; // indexing step
            }
            return q1;
        }
    }

    public static void main(String[] args) throws IOException {
        /**
         * this method writes an output file with all the process and how they are processed based on priority and arrival time
         * input: a priorty queue of processes
         * output: a text file with all the processes in order of how they are processed
         */

        // create and write output file with try catch in case of error with file
        try {

            FileWriter processOutput = new FileWriter("process_scheduling_output.txt");

            //Constant used
            int MAXWAIT = 30;

            // priorty queue 1 to store all processes from input file sort by arrival time
            PriorityQueue<Process> q1;
            // call inputProcess method to populate process class and retrieve the array of processes
            q1 = inputProcesses("src/process_scheduling_input.txt");

            // if no processes exit
            if (q1.size() == 0) {
                processOutput.write("No processes provided");
                processOutput.close();
                System.exit(0);
            }
            // add all processes into output file
            processOutput.write("All processes:");
            for (int i = 0; i < q1.size(); i++) {
                processOutput.write("\n");
                processOutput.write((q1.toArray()[i]).toString());
            }

            // variables needed for loop controls
            int waitTime = 0;
            int currentTime = 10;
            int duration = 0;
            int currentId = 0;
            int totalWaitTime = 0;
            float totalProcesses = q1.size();

            // maximum wait time used for calculations
            processOutput.write("\n\nMaximum wait time = " + MAXWAIT);

            // flag for processor running
            Boolean running = false;

            // Second priority queue q2 sorted by priority
            PriorityQueue<Process> q2 = new PriorityQueue<>(new ProcessComparator());

            // will run until q1 is empty
            while (q1.size() > 0) {

                // Transfer from q1 to q2 when arrival time is <= to current time
                while (q1.peek().getArrivalTime() <= currentTime) {
                    q2.add(q1.poll());
                    if (q1.size() == 0) {
                        break;
                    }
                }

                // if process is currently running and is not finished
                if (running == true & duration > 1) {
                    duration--;
                }

                // if process is currently running and finished
                else if (running == true & duration == 1) {
                    running = false;
                    processOutput.write("\nProcess " + currentId + " finished at time " + currentTime + "\n");
                    // update priorties based on waiting time for everything in q2
                    processOutput.write("\nUpdate priority:\n");
                    // temporarity priorty queue to be able to update priorty if max wait time is reached
                    PriorityQueue<Process> tempq2 = new PriorityQueue<>(new ProcessComparator());
                    int tempSize = q2.size();
                    while (q2.size() > 0) {
                        Process p = q2.poll();
                        int wt = currentTime - p.getArrivalTime();
                        if (wt > MAXWAIT & p.getPr() != 0) {
                            processOutput.write("PID = " + p.getId() + ", wait time = " + wt + ", current priority = " + p.getPr() + "\n");
                            p.setPr(Math.max(1, p.getPr() - 1));
                            processOutput.write("PID = " + p.getId() + ", new priority = " + p.getPr() + "\n");
                            tempq2.add(p);
                        } else {
                            tempq2.add(p);
                        }
                    }
                    // add back from temporary queue to queue 2
                    while (tempq2.size() > 0) {
                        Process p = tempq2.poll();
                        q2.add(p);
                    }
                }

                // if no process running and q2 is not empty
                if (running == false & q2.size() > 0) {
                    Process p = q2.poll();
                    running = true;
                    waitTime = currentTime - p.getArrivalTime();
                    totalWaitTime += waitTime;
                    duration = p.getDuration();
                    currentId = p.getId();

                    // output
                    processOutput.write("\nProcess removed from queue is: id = " + currentId
                            + ", at time " + currentTime + ", wait time = " + waitTime
                            + " Total wait time = " + totalWaitTime);
                    processOutput.write("\nProcess id = " + p.getId()
                            + "\n\tPriority = " + p.getPr()
                            + "\n\tArrival = " + p.getArrivalTime()
                            + "\n\tDuration = " + p.getDuration());
                }

                currentTime++;
            }


            // Once q1 is empty finish the rest of the processes in q2
            while (q2.size() > 0 || running == true) {
                // if process is currently running and is not finished
                if (running == true & duration > 1) {
                    duration--;
                }

                // if process is currently running and finished
                else if (running == true & duration == 1) {
                    running = false;
                    processOutput.write("\nProcess " + currentId + " finished at time " + currentTime + "\n");
                    // update priorties based on waiting time for everything in q2
                    processOutput.write("\nUpdate priority:\n");
                    PriorityQueue<Process> tempq2 = new PriorityQueue<>(new ProcessComparator());
                    int tempSize = q2.size();
                    while (q2.size() > 0) {
                        Process p = q2.poll();
                        int wt = currentTime - p.getArrivalTime();
                        if (wt > MAXWAIT & p.getPr() != 0) {
                            processOutput.write("PID = " + p.getId() + ", wait time = " + wt + ", current priority = " + p.getPr() + "\n");
                            p.setPr(Math.max(1, p.getPr() - 1));
                            processOutput.write("PID = " + p.getId() + ", new priority = " + p.getPr() + "\n");
                            tempq2.add(p);
                        } else {
                            tempq2.add(p);
                        }
                    }
                    while (tempq2.size() > 0) {
                        Process p = tempq2.poll();
                        q2.add(p);
                    }
                }

                // if no process running and q2 is not empty
                if (running == false & q2.size() > 0) {
                    Process p = q2.poll();
                    running = true;
                    waitTime = currentTime - p.getArrivalTime();
                    totalWaitTime += waitTime;
                    duration = p.getDuration();
                    currentId = p.getId();

                    // output
                    processOutput.write("\nProcess removed from queue is: id = " + currentId
                            + ", at time " + currentTime + ", wait time = " + waitTime
                            + " Total wait time = " + totalWaitTime);
                    processOutput.write("\nProcess id = " + p.getId()
                            + "\n\tPriority = " + p.getPr()
                            + "\n\tArrival = " + p.getArrivalTime()
                            + "\n\tDuration = " + p.getDuration());
                }

                currentTime++;
            }
            // final summary of the process
            processOutput.write("\nTotal wait time = " + totalWaitTime);
            processOutput.write("\nAverage wait time = " + totalWaitTime / totalProcesses);
            // close output file
            processOutput.close();
        }
        // if any errors error message is printed
        catch (IOException e) {
            System.out.println("Sorry something went wrong. Program will terminate.");
        }
    }
}
