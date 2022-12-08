package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> lineList = new ArrayList<String>();
        Path path = Paths.get("src",  "day2/input.txt");

        try {
            lineList = (ArrayList<String>) Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < lineList.size(); i++) {
            lineList.set(i, lineList.get(i).replace('X', 'A'));
            lineList.set(i, lineList.get(i).replace('Y', 'B'));
            lineList.set(i, lineList.get(i).replace('Z', 'C'));
        }

        int sumPoints = 0;
        for (String line : lineList) {
            Strategie s = new Strategie(line);
            s.playPartOne();
            sumPoints += s.points;
        }

        System.out.println("Sum points: " + sumPoints);

        sumPoints = 0;
        for (String line : lineList) {
            Strategie s = new Strategie(line);
            s.playPartTwo();
            sumPoints += s.points;
        }

        System.out.println("Sum points with playstyle 2: " + sumPoints);



    }

    private static class Strategie {

        LinkedList<Character> options = new LinkedList<Character>(Arrays.asList('A', 'B', 'C'));

        private char opponent;
        private char player;
        public int points;



        public Strategie(String strategieString) {
            this.opponent = strategieString.split(" ")[0].charAt(0);
            this.player = strategieString.split(" ")[1].charAt(0);;
        }



        public void playPartOne() {
            // B > A; C > B; A > C
            // Index is beaten by index - 1 and when -1 then by max index

            int indexOpponent = options.indexOf(opponent);
            int indexPlayer = options.indexOf(player);
            int indexPreviousToOpponent = options.indexOf(opponent)-1;
            if(indexPreviousToOpponent < 0) {
                indexPreviousToOpponent = 2;
            }
            int indexNextToOpponent = options.indexOf(opponent)+1;
            if(indexNextToOpponent > 2) {
                indexNextToOpponent = 0;
            }

            int winningPoints = 0;

            if (indexOpponent == indexPlayer) {
                winningPoints+=3;
                System.out.println("Opponent: " + opponent + " Player: " + player + " Points: " + winningPoints);
                this.points += winningPoints;
            } else if(indexPreviousToOpponent == indexPlayer) {
                winningPoints+=0;
                System.out.println("Opponent: " + opponent + " Player: " + player + " Points: " + winningPoints);
                this.points += winningPoints;
            } else  {
                winningPoints+=6;
                System.out.println("Opponent: " + opponent + " Player: " + player + " Points: " + winningPoints);
                this.points += winningPoints;
            }

            this.points += indexPlayer+1;
        }

        public void playPartTwo() {
            int indexOpponent = options.indexOf(opponent);
            int indexPlayer = options.indexOf(player);

            if(player == 'A') {
                this.player = whatLoosesToOpponent();
            }
            else if(player == 'B') {
                this.player = opponent;
            }
            else if (player == 'C') {
                this.player = whatBeatsOpponent();
            }
            else {
                System.out.println("This should not happen");
            }

            playPartOne();
        }

        public char whatBeatsOpponent() {
            int indexNextToOpponent = options.indexOf(opponent)+1;
            if(indexNextToOpponent > 2) {
                indexNextToOpponent = 0;
            }
            return options.get(indexNextToOpponent);
        }
        public char whatLoosesToOpponent() {
            int indexPreviousToOpponent = options.indexOf(opponent)-1;
            if(indexPreviousToOpponent < 0) {
                indexPreviousToOpponent = 2;
            }
            return options.get(indexPreviousToOpponent);
        }




    }

}

