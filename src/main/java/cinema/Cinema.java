package cinema;

import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        ScreenRoom screenRoom = createScreenRoom();

        while (true) {
            int selectedMenuOption = menuOptionSelector();

            if (selectedMenuOption == 0) {
                break;
            }

            switch (selectedMenuOption) {
                case 1 -> screenRoom.printSeatsArrangement();
                case 2 -> buyTicket(screenRoom);
                case 3 -> screenRoom.showStatistics();
                default -> throw new IllegalArgumentException("Invalid menu option: " + selectedMenuOption);
            }
        }
    }

    private static void buyTicket(ScreenRoom screenRoom) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a row number:");
            int chosenRowNumber = scanner.nextInt();

            System.out.println("Enter a seat number in that row:");
            int chosenSeatNumber = scanner.nextInt();

            double ticketPrice = screenRoom.getTicketPrice(chosenRowNumber);
            System.out.println();
            System.out.printf("Ticket price: $%.0f%n", ticketPrice);

            boolean result = screenRoom.chooseSeat(chosenRowNumber, chosenSeatNumber);
            if (result) {
                break;
            }
        }

        scanner.close();
    }

    private static ScreenRoom createScreenRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        scanner.close();

        return new ScreenRoom(rows, seatsPerRow);
    }

    private static int menuOptionSelector() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        int selectedOption = scanner.nextInt();
        scanner.close();

        return selectedOption;
    }
}
