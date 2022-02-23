import java.util.ArrayList;
import java.util.Collections;

public class Scheduler {
    private Assignment[] assignmentArray;
    private Integer[] C;
    private Double[] max;
    private ArrayList<Assignment> solutionDynamic;
    private ArrayList<Assignment> solutionGreedy;

    /**
     * @throws IllegalArgumentException when the given array is empty
     */

    public Scheduler(Assignment[] assignmentArray) {
        this.assignmentArray = assignmentArray;

        if (assignmentArray.length == 0) {
            throw new IllegalArgumentException();
        }

        // Initializing array lists
        C = new Integer[assignmentArray.length];
        max = new Double[assignmentArray.length];

        solutionDynamic = new ArrayList<>();
        solutionGreedy = new ArrayList<>();

        calculateC();
        calculateMax(max.length - 1);
    }

    /**
     * @param index of the {@link Assignment}
     * @return Returns the index of the last compatible {@link Assignment},
     * returns -1 if there are no compatible assignments
     */

    //
    public int binarySearch(int index) {
        int left = 0;   // first element, lower limit
        int right = index - 1;  // last element, upper limit
        int result = binarySearchRecursive(left, right, index);
        return result;
    }

    private int binarySearchRecursive(int left, int right, int index) {
        int middle = left + (right - left) / 2;

        // No need to go further if there is nothing
        if (assignmentArray.length == 0) {
            return -1;
        }

        if (right < left) {
            return -1;
        }

        // Checking if the middle element and the element at the given index is compatible by comparing necessary hour values of the assignment elements.
        if (assignmentArray[middle].getFinishTime().compareTo(assignmentArray[index].getStartTime()) <= 0) {
            int chosenIndex = binarySearchRecursive(middle + 1, right, index);
            if (chosenIndex == -1) {
                return middle;
            } else {
                return chosenIndex;
            }
        } else {
            return binarySearchRecursive(left, middle - 1, index);
        }
    }


    /**
     * {@link #C} must be filled after calling this method
     */
    private void calculateC() {
        int arrayLength = assignmentArray.length;
        int index = 0;
        for (int i = 0; i < arrayLength; i++) {
            C[index] = binarySearch(index);
            index++;
        }
    }


    /**
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleDynamic() {
        findSolutionDynamic(max.length - 1);
        Collections.reverse(solutionDynamic);
        return solutionDynamic;
    }

    /**
     * {@link #solutionDynamic} must be filled after calling this method
     */
    private void findSolutionDynamic(int index) {
        double currentWeight;

        // a required check to stop the function since the index goes down recursively and finally gets -1 as the value
        // which will cause an error.
        if (index < 0) {
            return;
        } else {
            System.out.println(String.format("findSolutionDynamic(%s)", index));

            if (C[index] < 0) {
                currentWeight = 0;
            } else {
                int cIndex = C[index];
                currentWeight = max[cIndex];
            }

            if (index == 0) {
                System.out.println(String.format("Adding %s to the dynamic schedule", assignmentArray[index].toString()));
                solutionDynamic.add(assignmentArray[index]);

                findSolutionDynamic(C[index]);
            } else if (assignmentArray[index].getWeight() + currentWeight >= max[index - 1]) {

                System.out.println(String.format("Adding %s to the dynamic schedule", assignmentArray[index].toString()));
                solutionDynamic.add(assignmentArray[index]);

                findSolutionDynamic(C[index]);
            } else {
                findSolutionDynamic(index - 1);
            }
        }

    }

    /**
     * {@link #max} must be filled after calling this method
     */
    private Double calculateMax(int i) {
        if (i < 0) {
            return (double) 0;
        } else {
            if (i == 0) {
                System.out.println(String.format("calculateMax(%s): Zero", i));
            } else if (max[i] == null) {
                System.out.println(String.format("calculateMax(%s): Prepare", i));
            } else {
                System.out.println(String.format("calculateMax(%s): Present", i));
            }

            double totalWeight = assignmentArray[i].getWeight() + calculateMax(C[i]);
            if (max[i] == null) {
                max[i] = Math.max(totalWeight, calculateMax(i - 1));
            }
            return max[i];
        }
    }


    /**
     * {@link #solutionGreedy} must be filled after calling this method
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleGreedy() {
        System.out.println(String.format("Adding %s to the greedy schedule", assignmentArray[0].toString()));
        solutionGreedy.add(assignmentArray[0]);

        int prevIndex = 0;
        for (int index = 1; index < assignmentArray.length; index++)
        {
            if (assignmentArray[prevIndex].getFinishTime().compareTo(assignmentArray[index].getStartTime()) <= 0) {
                System.out.println(String.format("Adding %s to the greedy schedule", assignmentArray[index].toString()));
                solutionGreedy.add(assignmentArray[index]);
                prevIndex = index;
            }
        }
        return solutionGreedy;
    }
}
