package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {


        ArrayList<String> lineList = new ArrayList<String>();
        Path path = Paths.get("src",  "day1/input.txt");

        try {
            lineList = (ArrayList<String>) Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        ArrayList<Integer> sumCaloriesPerElv = new ArrayList<Integer>();
        int currentElveCounter = 0;
        for (String line : lineList) {
            try {
                sumCaloriesPerElv.get(currentElveCounter);
            } catch (IndexOutOfBoundsException e) {
                sumCaloriesPerElv.add(currentElveCounter, 0);
            }
            if(!line.isEmpty()) {
                sumCaloriesPerElv.set(currentElveCounter, sumCaloriesPerElv.get(currentElveCounter) + Integer.parseInt(line));
            } else {
                currentElveCounter++;
            }
        }

        int elvWithMaxCalories = sumCaloriesPerElv.stream().max(Integer::compare).get();
        System.out.println("Elv with max calories: " + elvWithMaxCalories);

        Collections.sort(sumCaloriesPerElv, Collections.reverseOrder());
        int sumTopThreeElvesWithCalories = 0;
        for (int i = 0; i < 3; i++) {
            sumTopThreeElvesWithCalories += sumCaloriesPerElv.get(i);
        }

        System.out.println("Top three elves with calories: " + sumTopThreeElvesWithCalories);

    }

}