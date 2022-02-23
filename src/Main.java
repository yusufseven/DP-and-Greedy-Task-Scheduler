import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    /**
     * Propogated {@link IOException} here
     * {@link #readFile} and {@link #writeOutput} methods should be called here
     * A {@link Scheduler} instance must be instantiated here
     */
    public static void main(String[] args) throws IOException {
        Assignment[] assignmentArray = readFile(args[0]);
        sortArray(assignmentArray);

        Scheduler scheduler = new Scheduler(assignmentArray);

        writeOutput("solution_dynamic.json", scheduler.scheduleDynamic());
        writeOutput("solution_greedy.json", scheduler.scheduleGreedy());
    }

    /**
     * @param filename json filename to read
     * @return Returns a list of {@link Assignment}s obtained by reading the given json file
     * @throws FileNotFoundException If the given file does not exist
     */
    private static Assignment[] readFile(String fileName) throws FileNotFoundException{
        try {
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            ArrayList<Assignment> assignmentArray = new Gson().fromJson(reader, new TypeToken<ArrayList<Assignment>>() {}.getType());

            int arraySize = assignmentArray.size();
            Assignment[] assignmentObject = assignmentArray.toArray(new Assignment[arraySize]);

            return assignmentObject;
        }  catch (IOException except)
        {
            throw new FileNotFoundException("File not found");
        }
    }

    /**
     * @param fileName  json filename to write
     * @param arrayList a list of {@link Assignment}s to write into the file
     * @throws IOException If something goes wrong with file creation
     */
    private static void writeOutput(String fileName, ArrayList<Assignment> arrayList) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        Gson gson = builder.setPrettyPrinting().create();

        FileWriter writer = new FileWriter(fileName);

        String output = gson.toJson(arrayList, new TypeToken<ArrayList<Assignment>>() {}.getType());
        writer.write(output);

        writer.close();
    }

    private static Assignment[] sortArray(Assignment[] array) {
        Arrays.sort(array);
        return array;
    }
}
