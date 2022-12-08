package day3;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> lineList = new ArrayList<String>();
        Path path = Paths.get("src",  "day3/input.txt");

        try {
            lineList = (ArrayList<String>) Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int prioritySum = 0;
        for (String line : lineList) {
            prioritySum += compareListsExtremlyOverExgineeredPartOne(line.substring(0, (line.length() / 2)), line.substring(line.length() / 2, line.length()));
        }

        System.out.println("Priority sum: " + prioritySum);

        int prioritySumPartTwo = 0;

        String[] lineArray = new String[3];

        for(int i = 0; i < lineList.size(); i++) {
            lineArray[i % 3] = lineList.get(i);
            if(i % 3 == 2) {
                prioritySumPartTwo += compareListsExtremlyOverExgineeredPartTwo(lineArray[0], lineArray[1], lineArray[2]);
            }
        }
        System.out.println("Priority sum part two: " + prioritySumPartTwo);

    }

    private static int compareListsExtremlyOverExgineeredPartOne(String firstPart, String secondPart) {
        System.out.println("First part: " + firstPart);
        System.out.println("Second part: " + secondPart);
        int[] firstPartBitArray = new int[63];
        int[] secondPartBitArray = new int[63];

        System.out.println("a " + (int) 'a');
        System.out.println("A " + (int) 'A');

        for (int i = 0; i < firstPart.length(); i++) {
            int ascii = (int) firstPart.toCharArray()[i];
            firstPartBitArray[ascii - 65] = 1;
        }

        for (int i = 0; i < secondPart.length(); i++) {
            int ascii = (int) secondPart.toCharArray()[i];
            secondPartBitArray[ascii - 65] = 1;
        }


        String firstPartBitString = Arrays.toString(firstPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", "");
        String secondPartBitString = Arrays.toString(secondPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", "");
        System.out.println(Arrays.toString(firstPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", ""));
        System.out.println(Arrays.toString(secondPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", ""));
        BigInteger firstPartBits = new BigInteger(firstPartBitString, 2);
        BigInteger secondPartBits = new BigInteger(secondPartBitString, 2);
        String binaryAnd = String.format("%63s\n", firstPartBits.and(secondPartBits).toString(2)).replace(" ", "0");
        System.out.println(binaryAnd);

        int doubleLetter = (char)(binaryAnd.toString().indexOf("1") + 65);
        System.out.println(doubleLetter + " : " + (binaryAnd.toString().indexOf("1") + 65));

        int priorityOfDoubleLetter = 0;
        if (Character.isUpperCase(doubleLetter)) {
            priorityOfDoubleLetter = ((int) doubleLetter) - 64 + 26;
        }
        else {
            priorityOfDoubleLetter = ((int) doubleLetter) - 96;
        }

        System.out.println("priority of A ist " + (((int) 'A') - 64 + 26));
        System.out.println("priority of a ist " + (((int) 'a') - 96));

        return priorityOfDoubleLetter;
    }

    private static int compareListsExtremlyOverExgineeredPartTwo(String firstPart, String secondPart, String thirdPart) {
        System.out.println("First part: " + firstPart);
        System.out.println("Second part: " + secondPart);
        System.out.println("Third part: " + thirdPart);
        int[] firstPartBitArray = new int[63];
        int[] secondPartBitArray = new int[63];
        int[] thirdPartBitArray = new int[63];

        for (int i = 0; i < firstPart.length(); i++) {
            int ascii = (int) firstPart.toCharArray()[i];
            firstPartBitArray[ascii - 65] = 1;
        }

        for (int i = 0; i < secondPart.length(); i++) {
            int ascii = (int) secondPart.toCharArray()[i];
            secondPartBitArray[ascii - 65] = 1;
        }

        for (int i = 0; i < thirdPart.length(); i++) {
            int ascii = (int) thirdPart.toCharArray()[i];
            thirdPartBitArray[ascii - 65] = 1;
        }


        String firstPartBitString = Arrays.toString(firstPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", "");
        String secondPartBitString = Arrays.toString(secondPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", "");
        String thirdPartBitString = Arrays.toString(thirdPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", "");
        System.out.println(Arrays.toString(firstPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", ""));
        System.out.println(Arrays.toString(secondPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", ""));
        System.out.println(Arrays.toString(thirdPartBitArray).replaceAll(", ", "").replace("[", "").replace("]", ""));
        BigInteger firstPartBits = new BigInteger(firstPartBitString, 2);
        BigInteger secondPartBits = new BigInteger(secondPartBitString, 2);
        BigInteger thirdPartBits = new BigInteger(thirdPartBitString, 2);
        String binaryAnd = String.format("%63s\n", firstPartBits.and(secondPartBits).and(thirdPartBits).toString(2)).replace(" ", "0");
        System.out.println(binaryAnd);

        char trippleLetter = (char)(binaryAnd.toString().indexOf("1") + 65);
        System.out.println(trippleLetter + " : " + (binaryAnd.toString().indexOf("1") + 65));

        int priorityOfTrippleLetter = 0;
        if (Character.isUpperCase(trippleLetter)) {
            priorityOfTrippleLetter = ((int) trippleLetter) - 64 + 26;
        }
        else {
            priorityOfTrippleLetter = ((int) trippleLetter) - 96;
        }

        return priorityOfTrippleLetter;
    }


}
