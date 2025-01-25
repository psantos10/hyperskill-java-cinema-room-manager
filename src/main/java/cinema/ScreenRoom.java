package cinema;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ScreenRoom {
    private static final double FRONT_SECTION_PRICE = 10;
    private static final double BACK_SECTION_PRICE = 8;
    private static final char SEAT_CHARACTER = 'S';
    private static final char CHOSEN_SEAT_CHARACTER = 'B';

    private final int rows;
    private final int seatsPerRow;
    private final int frontSectionRows;
    private final int backSectionRows;
    private final int totalSeats;

    private final char[][] seatsArrangement;

    public ScreenRoom(int rows, int seatsPerRow) {
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;

        this.frontSectionRows = rows / 2;
        this.backSectionRows = rows - frontSectionRows;

        this.totalSeats = rows * seatsPerRow;

        this.seatsArrangement = new char[rows][seatsPerRow];
        for (char[] row : seatsArrangement) {
            Arrays.fill(row, SEAT_CHARACTER);
        }
    }

    public double getTotalIncome() {
        if (totalSeats < 60) {
            return totalSeats * FRONT_SECTION_PRICE;
        }

        int frontSectionSeats = frontSectionRows * seatsPerRow;
        int backSectionSeats = backSectionRows * seatsPerRow;

        return (frontSectionSeats * FRONT_SECTION_PRICE) + (backSectionSeats * BACK_SECTION_PRICE);
    }

    public void printSeatsArrangement() {
        System.out.println();
        System.out.println("Cinema:");

        System.out.print(" ");
        for (int i = 1; i <= seatsPerRow; i++) {
            System.out.printf(" %s", i);
        }
        System.out.println();

        for (int i = 0; i < seatsArrangement.length; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < seatsArrangement[i].length; j++) {
                System.out.printf(" %c", seatsArrangement[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }

    public double getTicketPrice(int row) {
        if (totalSeats < 60 || row <= frontSectionRows) {
            return FRONT_SECTION_PRICE;
        }

        return BACK_SECTION_PRICE;
    }

    public boolean chooseSeat(int row, int seat) {
        if (isValidSeat(row, seat)) {
            seatsArrangement[row - 1][seat - 1] = CHOSEN_SEAT_CHARACTER;
            return true;
        }

        return false;
    }

    private boolean isValidSeat(int row, int seat) {
        if (row <= 0 || row > this.rows) {
            System.out.println("Wrong input!");
            return false;
        }

        if (seat <= 0 || seat > this.seatsPerRow) {
            System.out.println("Wrong input!");
            return false;
        }

        if (seatsArrangement[row - 1][seat - 1] == CHOSEN_SEAT_CHARACTER) {
            System.out.println("That ticket has already been purchased!");
            return false;
        }

        return true;
    }

    public void showStatistics() {
        Stream<Character> seats = Stream.of(seatsArrangement)
                .flatMap(row -> IntStream.range(0, row.length).mapToObj(seat -> row[seat]));

        long numberOfPurchasedTickets = seats
                .filter(s -> s == CHOSEN_SEAT_CHARACTER)
                .count();

        double percentagePurchasedTickets = ((double) numberOfPurchasedTickets / totalSeats) * 100;

        double currentIncome = 0;
        for (int i = 0; i < seatsPerRow; i++) {
            for (int j = 0; j < seatsArrangement[i].length; j++) {
                if (seatsArrangement[i][j] == CHOSEN_SEAT_CHARACTER) {
                    currentIncome += getTicketPrice(i + 1);
                }
            }
        }

        System.out.println("Number of purchased tickets: " + numberOfPurchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", percentagePurchasedTickets);
        System.out.printf("Current income: $%.0f\n", currentIncome);
        System.out.printf("Total income: $%.0f\n", getTotalIncome());
    }
}
