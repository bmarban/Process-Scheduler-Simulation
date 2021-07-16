import java.util.Comparator;

public class TimeComparator implements Comparator<Process>  {

    @Override
    public int compare(Process p1, Process p2) {
        /**
         * this method allows us to compare processes based on arrival time to later transfer into second priority queue
         * input: none
         * output: 1 if current object is greater, -1 if smaller and 0 if they are equal
         */
        if (p1.getArrivalTime() > p2.getArrivalTime()) {
            // if current object is greater,then return 1
            return 1;
        } else if (p1.getArrivalTime() < p2.getArrivalTime()) {
            // if current object is greater,then return -1
            return -1;
        } else {
            // if current object is equal to o,then return 0
            return 0;
        }
    }

}
