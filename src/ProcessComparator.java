import java.util.Comparator;

public class ProcessComparator implements Comparator<Process>  {

    @Override
    public int compare(Process p1, Process p2) {
        /**
         * this method allows us to compare processes based on priority
         * input: none
         * output: 1 if current object is greater, -1 if smaller and 0 if they are equal
         */
        if (p1.getPr() > p2.getPr()) {
            // if current object is greater,then return 1
            return 1;
        } else if (p1.getPr() < p2.getPr()) {
            // if current object is greater,then return -1
            return -1;
        } else {
            // if current object is equal to o,then return 0
            return 0;
        }
    }

}
