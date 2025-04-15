package cinema;

import java.util.Scanner;

public class Cinema {

    protected static class BookingException extends Exception {
        public BookingException(String message) {
            super(message);
        }
    }

    protected static class CinemaHall {
        final int rows;
        final int seatsInRow;
        final int seats;

        final static int MAX_SMALL_HALL = 60;
        final static int PRICE_CAT_A = 10;
        final static int PRICE_CAT_B = 8;

        final int rowsCatA;
        final int totalIncome;

        char[][] hall;

        public CinemaHall(int rows, int seatsInRow) {
            this.rows = rows;
            this.seatsInRow = seatsInRow;

            seats = rows * seatsInRow;
            rowsCatA = seats <= MAX_SMALL_HALL ? rows : rows / 2;
            totalIncome = (rowsCatA * PRICE_CAT_A + (rows - rowsCatA) * PRICE_CAT_B) * seatsInRow;

            createCinemaHall();
        }

        private void createCinemaHall() {
            hall = new char[rows][seatsInRow];

            for (int i = 0; i < hall.length; i++)
                for (int j = 0; j < hall[i].length; j++)
                    hall[i][j] = 'S';
        }

        public void print() {
            System.out.println("\nCinema:");

            StringBuilder sb = new StringBuilder();

            sb.append(" ");
            for (int i = 0; i < hall[0].length; i++)
                sb.append(" ").append(i + 1);

            System.out.println(sb.toString());

            for (int i = 0; i < hall.length; i++) {
                sb.setLength(0);

                sb.append(i + 1);
                for (int j = 0; j < hall[i].length; j++)
                    sb.append(" ").append(hall[i][j]);

                System.out.println(sb.toString());
            }

            System.out.println("");
        }

        public int book(int row, int seat) throws BookingException{
            if (row <= 0 || row > rows || seat <= 0 || seat > seatsInRow) throw new BookingException("Wrong input!");
            if (hall[row - 1][seat - 1] == 'B') throw new BookingException("That ticket has already been purchased!");

            int price = row <= rowsCatA ? PRICE_CAT_A : PRICE_CAT_B;

            hall[row - 1][seat - 1] = 'B';

            return price;
        }

        public void printStatistics() {
            int purchased = 0;
            int income = 0;

            for (int i = 0; i < hall.length; i++)
                for (int j = 0; j < hall[i].length; j++)
                    if (hall[i][j] == 'B'){
                        purchased++;
                        income += i < rowsCatA ? PRICE_CAT_A : PRICE_CAT_B;
                    }
            double percentage = (double)purchased / seats * 100;

            System.out.printf("Number of purchased tickets: %d\n", purchased);
            System.out.printf("Percentage: %.2f%%\n", percentage);
            System.out.printf("Current income: $%d\n", income);
            System.out.printf("Total income: $%d\n", totalIncome);

        }
    }

    public static void main(String[] args) {
        // Write your code here

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int seatsInRow = scanner.nextInt();

        CinemaHall cinemaHall = new CinemaHall(rows, seatsInRow);

        do {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();

            if (choice == 0) break;
            else if (choice == 1) cinemaHall.print();
            else if (choice == 2)  {
                do {
                    System.out.println("\nEnter a row number:");
                    int row = scanner.nextInt();

                    System.out.println("Enter a seat number in that row:");
                    int seat = scanner.nextInt();

                    try {
                        int price = cinemaHall.book(row, seat);

                        System.out.printf("\nTicket price: $%d\n", price);

                        break;
                    } catch (BookingException e) {
                        System.out.println(e.getMessage());

                        continue;
                    }
                } while (true);

            } else if (choice == 3) cinemaHall.printStatistics();

        } while(true);

        scanner.close();
    }
}