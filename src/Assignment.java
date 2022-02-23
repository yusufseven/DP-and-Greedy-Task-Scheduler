import java.util.Locale;

public class Assignment implements Comparable
{
    private String name;
    private String start;
    private int duration;
    private int importance;
    private boolean maellard;

    /*
        Getter methods
     */

    public String getName()
    {
        return name;
    }

    public String getStartTime()
    {
        return start;
    }

    public int getDuration()
    {
        return duration;
    }

    public int getImportance()
    {
        return importance;
    }

    public boolean isMaellard()
    {
        return maellard;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime()
    {
        int hour = Integer.parseInt(start.substring(0, 2));
        hour += duration;

        String output = "";
        if (hour < 10)
        {
            output += "0";
        }

        output += String.valueOf(hour) + ":" + start.substring(3);
        return output;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight()
    {
        if(duration > 0){
            return (double) importance * (maellard ? 1001 : 1) / duration;
        }
        return 0.0;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o)
    {
        Assignment assignment = (Assignment) o;
        return this.getFinishTime().compareTo(assignment.getFinishTime());
    }

    /**
     * @return Should return a string in the following form:
     * Assignment{name='Refill vending machines', start='12:00', duration=1, importance=45, maellard=false, finish='13:00', weight=45.0}
     */
    @Override
    public String toString()    // TODO change
    {
        return String.format(Locale.US, "Assignment{name='%s', start='%s', duration=%d, importance=%d, maellard=%s, finish='%s', weight=%.1f}",
                this.name, this.start, this.duration, this.importance, this.maellard, this.getFinishTime(), this.getWeight());
    }
}
