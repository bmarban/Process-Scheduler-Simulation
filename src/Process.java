public class Process {
    /**
     * this class creates all the processes
     * input is priority, id, arrival time and duration of a process
     */

    private int pr;  // priority of the process
    private int id;  // process id
    private int arrivalTime;  // the time when the process arrives at the system
    private int duration;  // time it takes to execute the process


    public Process(int pr, int id, int arrivalTime, int duration) {
        this.pr = pr;
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
    }

    // ability to update priority if over max time
    public void setPr (int pr) {this.pr = pr;}

    // ability to get priority
    public int getPr () {return pr;}

    // ability to get id
    public int getId () {return id;}

    // ability to get arrival time
    public int getArrivalTime () {return arrivalTime;}

    // ability to get duration
    public int getDuration () {return duration;}

    // ability to set duration
    public void setDuration (int duration) {this.duration = duration;}


    public String toString() {
        /**
         * this method creates a description of the process
         * output is priority, id, arrival time and duration of a process
         */

        String c =
                "\tID = " + id +
                "\tPriority = " + pr +
                "\tDuration = " + duration +
                "\tArrival Time = " + arrivalTime;
        return c;
    }
}
