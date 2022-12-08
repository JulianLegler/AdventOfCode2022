package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> lineList = new ArrayList<String>();
        Path path = Paths.get("src", "day8/input.txt");

        try {
            lineList = (ArrayList<String>) Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //partOne(lineList);
        partTwo(lineList);

    }

    public static void partTwo(ArrayList<String> lineList) {

        int[][] map = new int[lineList.get(0).length()][lineList.size()];
        int[][] scenicScoreMap = new int[lineList.get(0).length()][lineList.size()];


        for (int j = 0; j < lineList.get(0).length(); j++) {
            for (int i = 0; i < lineList.size(); i++) {
                scenicScoreMap[i][j] = 0;
            }
        }


        for (int j = 0; j < lineList.get(0).length(); j++) {
            for (int i = 0; i < lineList.size(); i++) {
                map[i][j] = Character.getNumericValue((lineList.get(i).charAt(j)))+1;
            }
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.printf("%4s", map[i][j]);
            }
            System.out.println();
        }

        System.out.println("=========================================");


        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {


                int viewDistanceRight = 0;
                int viewDistanceLeft = 0;
                int viewDistanceUp = 0;
                int viewDistanceDown = 0;

                // check right side
                int maxHeight = map[i][j];
                for (int k = j + 1; k < map[i].length; k++) {
                    if (map[i][k] < maxHeight) {
                        viewDistanceRight++;
                    } else if (map[i][k] >= maxHeight) {
                        viewDistanceRight++;
                        break;
                    }
                }
                // check left side
                maxHeight = map[i][j];
                for (int k = j - 1; k >= 0; k--) {
                    if (map[i][k] < maxHeight) {
                        viewDistanceLeft++;
                    } else if (map[i][k] >= maxHeight) {
                        viewDistanceLeft++;
                        break;
                    }
                }

                // check up
                maxHeight = map[i][j];
                for (int k = i - 1; k >= 0; k--) {
                    if (map[k][j] < maxHeight) {
                        viewDistanceUp++;
                    } else if (map[k][j] >= maxHeight) {
                        viewDistanceUp++;
                        break;
                    }
                }

                // check down
                maxHeight = map[i][j];
                for (int k = i + 1; k < map.length; k++) {
                    if (map[k][j] < maxHeight) {
                        viewDistanceDown++;
                    } else if (map[k][j] >= maxHeight) {
                        viewDistanceDown++;
                        break;
                    }
                }

                scenicScoreMap[i][j] = viewDistanceRight * viewDistanceLeft * viewDistanceUp * viewDistanceDown;
            }
        }

        // get max number from all the scenic scores
        int maxScenicScore = 0;
        for (int i = 0; i < scenicScoreMap.length; i++) {
            for (int j = 0; j < scenicScoreMap[i].length; j++) {
                if (scenicScoreMap[i][j] > maxScenicScore) {
                    maxScenicScore = scenicScoreMap[i][j];
                }
            }
        }





        // print out 2d array map
        for (int i = 0; i < scenicScoreMap.length; i++) {
            for (int j = 0; j < scenicScoreMap[i].length; j++) {
                System.out.printf("%4s", scenicScoreMap[i][j]);
            }
            System.out.println();
        }

        System.out.println("maxScenicScore = " + maxScenicScore);


    }
    public static void partOne(ArrayList<String> lineList) {

        int[][] map = new int[lineList.get(0).length()][lineList.size()];
        int[][] visibilityMap = new int[lineList.get(0).length()][lineList.size()];


        for (int j = 0; j < lineList.get(0).length(); j++) {
            for (int i = 0; i < lineList.size(); i++) {
                visibilityMap[i][j] = 0;
            }
        }


        for (int j = 0; j < lineList.get(0).length(); j++) {
            for (int i = 0; i < lineList.size(); i++) {
                map[i][j] = Character.getNumericValue((lineList.get(i).charAt(j)))+1;
            }
        }


        System.out.println(Arrays.toString(map[0]));

        // check the rows
        for (int i = 0; i < map.length; i++) {
            // from left to right
            int maxHeight = -1;
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] > maxHeight) {
                    maxHeight = map[i][j];
                    visibilityMap[i][j] = map[i][j]; // todo: set 1
                    //visibilityMap[i][j] = 1;
                }
            }
            // from the right to the left
            maxHeight = -1;
            for (int j = map[i].length - 1; j >= 0; j--) {
                if (map[i][j] > maxHeight) {
                    maxHeight = map[i][j];
                    visibilityMap[i][j] = map[i][j]; // todo: set 1
                    //visibilityMap[i][j] = 1;
                }
            }
        }


        // check the columns
        for (int i = 0; i < map[0].length; i++) {
            // from top to bottom
            int maxHeight = -1;
            for (int j = 0; j < map.length; j++) {
                if (map[j][i] > maxHeight) {
                    maxHeight = map[j][i];
                    visibilityMap[j][i] = map[j][i]; // todo: set 1
                    //visibilityMap[j][i] = 1;
                }
            }
            // from bottom to top
            maxHeight = -1;
            for (int j = map.length - 1; j >= 0; j--) {
                if (map[j][i] > maxHeight) {
                    maxHeight = map[j][i];
                    visibilityMap[j][i] = map[j][i]; // todo: set 1
                    //visibilityMap[j][i] = 1;
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < visibilityMap.length; i++) {
            for (int j = 0; j < visibilityMap[i].length; j++) {
                sum += visibilityMap[i][j] > 0 ? 1 : 0;
            }
        }



        // print out 2d array map
        for (int i = 0; i < visibilityMap.length; i++) {
            for (int j = 0; j < visibilityMap[i].length; j++) {
                System.out.print(visibilityMap[i][j]);
            }
            System.out.println();
        }

        System.out.println("Sum: " + sum);


    }

}
