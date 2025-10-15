package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("App Activated\n\nFinancial Transaction Tracker Plus");
        while (true) {
            run();
        }
    }

    public static void run() {
        Scanner keyboard = new Scanner(System.in);
        menu();
        menuSelector(keyboard);
    }

    public static void menu() {
        System.out.println("\n=== Menu ===");
        System.out.println("\tD) Add Deposit");
        System.out.println("\tP) Make Payment (Debit)");
        System.out.println("\tL) Ledger");
        System.out.println("\tX) Exit");
    }

    public static void menuSelector(Scanner keyboard) {
        System.out.print("Enter a letter: ");
        String selected = keyboard.nextLine().trim();
        switch (selected) {
            case "D":
                System.out.println();
                addDeposit(keyboard);
                break;
            case "P":
                makePayment(keyboard);
                break;
            case "L":
                ledgerInfo();
                ledger(keyboard);
                break;
            case "X":
                exit();
                break;
            default:
                System.out.println("Letter has to be uppercase and one of the letters above");
                System.out.print("Press Enter to continue...");
                keyboard.nextLine().trim();
                System.out.println();
                break;
        }
    }

    public static void addDeposit(Scanner keyboard) {
        System.out.println("=== Add Deposit ===");
        LocalDate date = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        String time = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.print("Description: ");
        String desc = keyboard.nextLine().trim();

        System.out.print("Vendor: ");
        String vendor = keyboard.nextLine().trim();

        System.out.print("Amount: ");
        double price = keyboard.nextDouble();
        price *= 1;

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/Transactions.csv", true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);

            bufWriter.newLine();
            bufWriter.write(date + "|" + time + "|" + String.format("%s|%s|%.2f",desc, vendor, price));
            bufWriter.close();

            System.out.println("Your Deposit has been added to your Transactions...");
            System.out.println();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void makePayment(Scanner keyboard) {
        System.out.println("=== Make Payment ===");
        LocalDate date = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        String time = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.print("Description: ");
        String desc = keyboard.nextLine().trim();
        System.out.print("Vendor: ");
        String vendor = keyboard.nextLine().trim();

        System.out.print("Amount: ");
        double price = keyboard.nextDouble();
        price *= -1;

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/Transactions.csv", true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);

            bufWriter.newLine();
            bufWriter.write(date + "|" + time + "|" + String.format("%s|%s|%.2f",desc, vendor, price));
            bufWriter.close();

            System.out.println("Your Payment has been added to your Transactions...");
            System.out.println();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void exit() {
        System.exit(0);
    }
    public static void ledgerInfo() {
        System.out.println("\n=== Ledger ===");
        System.out.println("\tA) All");
        System.out.println("\tD) Deposits");
        System.out.println("\tP) Payments");
        System.out.println("\tR) Reports");
        System.out.println("\tH) Home");
    }

    public static void ledger(Scanner keyboard) {
        System.out.print("Enter a letter: ");
        String selected = keyboard.nextLine().trim();
        HashMap<String, Info> infoList = loadInfo();
        switch (selected) {
            case "A":
                showAll(infoList, keyboard);
                break;
            case "D":
                showDeposits(infoList, keyboard);
                break;
            case "P":
                showPayments(infoList, keyboard);
                break;
            case "R":
                showReports();
                reports(infoList, keyboard);
                break;
            case "H":
                System.out.println("Returning");
                break;
            default:
                System.out.println("Letter has to be uppercase and one of the letters above");
                System.out.print("Press Enter to continue...");
                keyboard.nextLine().trim();
                System.out.println();
                ledgerInfo();
                ledger(keyboard);
                break;
        }
    }

    public static void showAll(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.println("\n=== All Transactions ===");
        for (Info info : infoList.values()) {
            System.out.printf("Date: %s\nTime: %s\nDescription: %s\nVendor: %s\nPrice: $%.2f\n", info.getDate(), info.getTime(), info.getDesc(), info.getVendor(), info.getPrice());
            System.out.println();
        }
        ledgerInfo();
        ledger(keyboard);
    }

    public static void showDeposits(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.println("\n=== Deposits ===");
        for (Info info : infoList.values()) {
            if (info.getPrice() > 0) {
                System.out.printf("Date: %s\nTime: %s\nDescription: %s\nVendor: %s\nPrice: $%.2f\n", info.getDate(), info.getTime(), info.getDesc(), info.getVendor(), info.getPrice());
                System.out.println();
            }
        }
        ledgerInfo();
        ledger(keyboard);
    }

    public static void showPayments(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.println("\n=== Payments ===");
        for (Info info : infoList.values()) {
            if (info.getPrice() < 0) {
                System.out.printf("Date: %s\nTime: %s\nDescription: %s\nVendor: %s\nPrice: $%.2f\n", info.getDate(), info.getTime(), info.getDesc(), info.getVendor(), info.getPrice());
                System.out.println();
            }
        }
        ledgerInfo();
        ledger(keyboard);
    }

    public static void showReports() {
        System.out.println("\n=== Reports ===");
        System.out.println("\t1) Month To Date");
        System.out.println("\t2) Previous Month");
        System.out.println("\t3) Year To Date");
        System.out.println("\t4) Previous Year");
        System.out.println("\t5) Search by Vendor");
        System.out.println("\t6) Custom Search");
        System.out.println("\t7) Back");
    }

    public static void reports(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.print("Enter a Number: ");
        int selected = keyboard.nextInt();
        keyboard.nextLine().trim();

        switch (selected) {
            case 1:
                monthToDate(infoList, keyboard);
                break;
            case 2:
                previousMonth(infoList, keyboard);
                break;
            case 3:
                monthToYear(infoList, keyboard);
                break;
            case 4:
                previousYear(infoList, keyboard);
                break;
            case 5:
                vendorSearch(infoList, keyboard);
                break;
            case 6:
                customSearch(infoList, keyboard);
                break;
            case 7:
                ledgerInfo();
                ledger(keyboard);
                break;
            default:
                System.out.println("Input has to be a number and one of the numbers above");
                System.out.print("Press Enter to continue...");
                keyboard.nextLine().trim();
                System.out.println();
                showReports();
                reports(infoList, keyboard);
                break;
        }
    }

    public static void monthToDate(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.println("\n=== Purchases Last Month ===");
        LocalDate todayDate = LocalDate.now();
        String[] dateList = todayDate.toString().split("[-]");
        int thisMonth = Integer.parseInt(dateList[1]);
        for (Info info : infoList.values()) {
            String[] date = info.getDate().split("[-]");
            int month = Integer.parseInt(date[1]);
            if (month >= (thisMonth)) {
                System.out.printf("Date: %s\nTime: %s\nDescription: %s\nVendor: %s\nPrice: $%.2f\n", info.getDate(), info.getTime(), info.getDesc(), info.getVendor(), info.getPrice());
                System.out.println();
            }
        }
        showReports();
        reports(infoList, keyboard);
    }

    public static void previousMonth(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.println("\n=== Purchases This Month ===");
        LocalDate todayDate = LocalDate.now();
        String[] dateList = todayDate.toString().split("[-]");
        int lastMonth = Integer.parseInt(dateList[1]) - 1;
        for (Info info : infoList.values()) {
            String[] date = info.getDate().split("[-]");
            int month = Integer.parseInt(date[1]);
            if (month == (lastMonth)) {
                System.out.printf("Date: %s\nTime: %s\nDescription: %s\nVendor: %s\nPrice: $%.2f\n", info.getDate(), info.getTime(), info.getDesc(), info.getVendor(), info.getPrice());
                System.out.println();
            }
        }
        showReports();
        reports(infoList, keyboard);
    }

    public static void monthToYear(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.println("\n=== Purchases This Year ===");
        LocalDate todayDate = LocalDate.now();
        String[] dateList = todayDate.toString().split("[-]");
        int thisYear = Integer.parseInt(dateList[0]) - 1;
        for (Info info : infoList.values()) {
            String[] date = info.getDate().split("[-]");
            int year = Integer.parseInt(date[0]);
            if (year >= (thisYear)) {
                System.out.printf("Date: %s\nTime: %s\nDescription: %s\nVendor: %s\nPrice: $%.2f\n", info.getDate(), info.getTime(), info.getDesc(), info.getVendor(), info.getPrice());
                System.out.println();
            }
        }
        showReports();
        reports(infoList, keyboard);
    }

    public static void previousYear(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.println("\n=== Purchases Last Year ===");
        LocalDate todayDate = LocalDate.now();
        String[] dateList = todayDate.toString().split("[-]");
        int lastYear = Integer.parseInt(dateList[0]) - 1;
        for (Info info : infoList.values()) {
            String[] date = info.getDate().split("[-]");
            int year = Integer.parseInt(date[0]);
            if (year == (lastYear)) {
                System.out.printf("Date: %s\nTime: %s\nDescription: %s\nVendor: %s\nPrice: $%.2f\n", info.getDate(), info.getTime(), info.getDesc(), info.getVendor(), info.getPrice());
                System.out.println();
            }
        }
        showReports();
        reports(infoList, keyboard);
    }

    public static void vendorSearch(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.println("\n=== Vendor Search ===");
        System.out.print("Search Vendor's Name: ");
        String vendorName = keyboard.nextLine().trim();
        for (Info info : infoList.values()) {
            if (vendorName.equalsIgnoreCase(info.getVendor())) {
                System.out.printf("Date: %s\nTime: %s\nDescription: %s\nVendor: %s\nPrice: $%.2f\n", info.getDate(), info.getTime(), info.getDesc(), info.getVendor(), info.getPrice());
                System.out.println();
            }
        }
        showReports();
        reports(infoList, keyboard);
    }

    public static void customSearch(HashMap<String, Info> infoList, Scanner keyboard) {
        System.out.println("\n=== Custom Search ===");
        System.out.println("\nPlease Keep Blank if you are not \nlooking for what is being asked\n");

        System.out.println("Please type Month or Year \n[Keep Blank if not looking for a Start/End Date]");
        String type = keyboard.nextLine().trim();
        LocalDate todayDate = LocalDate.now();
        String startDate = "";
        String endDate = "";

        if (!type.equalsIgnoreCase("Month") && !type.equalsIgnoreCase("Year")) {
            endDate = todayDate.toString();
            System.out.println("Skipping start and end date");
            System.out.println("Please click Enter to continue");
            keyboard.nextLine();
        }

        if (!type.equals("")) {
            System.out.println("Please select 1 of the following: \n\t[1] Just Start Date\n\t[2] Just End Date\n\t[3] Both\n\t[4] Back to Reports\nEnter In a Number: ");
            int selected = keyboard.nextInt();

            if (selected == 1) {
                System.out.print("Enter Starting Date (YYYY-MM-DD): ");
                startDate = keyboard.next().trim();
                keyboard.nextLine();
            } else if (selected == 2) {
                System.out.print("Enter Ending Date (YYYY-MM-DD): ");
                endDate = keyboard.next().trim();
                keyboard.nextLine();
            } else if (selected == 3) {
                System.out.print("Enter Starting Date (YYYY-MM-DD): ");
                startDate = keyboard.next().trim();
                keyboard.nextLine();

                System.out.print("Enter Ending Date (YYYY-MM-DD): ");
                endDate = keyboard.nextLine().trim();

                if (!startDate.contains("-") || !endDate.contains("-")) {
                    System.out.println("ERROR YOU HAVE TO TYPE THE DATES IN A (YYYY-MM-DD) Format");
                    System.out.println("Please click Enter to continue");
                    keyboard.nextLine();
                    showReports();
                    reports(infoList, keyboard);
                }
            } else if (selected == 4) {
                showReports();
                reports(infoList, keyboard);
            } else {
                System.out.println("Custom Search has found an error ");
                System.out.println("Invalid option selected, please Click Enter to continue... ");
                keyboard.nextLine();
            }
        }
        nextPart(infoList, keyboard, type, startDate, endDate);
    }

    public static void nextPart(HashMap<String, Info> infoList, Scanner keyboard,String type, String startDate, String endDate) {
        System.out.print("Enter Description: ");
        String desc = keyboard.nextLine().trim();
        System.out.print("Enter Vendor: ");
        String vendor = keyboard.nextLine();

        System.out.print("[Enter 0 if you are not looking for this]\nEnter Amount: ");
        int amount = keyboard.nextInt();
        keyboard.nextLine();
        customSearched(infoList, type, startDate, endDate, desc, amount, vendor);
    }

    public static void customSearched(HashMap<String, Info> infoList, String type, String startDate, String endDate, String desc, int amount, String vendor) {
        if (type.equalsIgnoreCase("Month")) {
            searchCheck(infoList, startDate, endDate, desc, vendor, amount, 1);
        } else if (type.equalsIgnoreCase("Year")) {
            searchCheck(infoList, startDate, endDate, desc, vendor, amount, 0);
        } else {
            searchCheck(infoList, startDate, endDate, desc, vendor, amount, 0);
        }
    }

    public static void searchCheck(HashMap<String, Info> infoList, String startDate, String endDate, String desc, String vendor, int amount, int index) {
        boolean isStartDate = (!startDate.equals(""));
        boolean isEndDate = (!endDate.equals(""));
        boolean isDesc = (!desc.equals(""));
        boolean isVendor = (!vendor.equals(""));
        boolean isAmount = (amount != 0);

        int chosenStartDate = 0;
        int chosenEndDate = 0;
        if (!startDate.equals("")) {
            String[] startDateList = startDate.split("[-]");
            chosenStartDate = Integer.parseInt(startDateList[index]);
        }

        if (!endDate.equals("")) {
            String[] endDateList = endDate.split("[-]");
            chosenEndDate = Integer.parseInt(endDateList[index]);
        }

        for (Info info : infoList.values()) {
            String[] dateList = info.getDate().split("[-]");
            int date = Integer.parseInt(dateList[index]);

            boolean startDatePass = ((chosenStartDate != 0) && (date >= chosenStartDate));
            boolean endDatePass = ((chosenEndDate != 0) && (date >= chosenEndDate));
            boolean descPass = (info.getDesc().equals(desc));
            boolean vendorPass = (info.getVendor().equals(vendor));
            boolean amountPass = (info.getPrice() == amount);

            if (isStartDate == startDatePass && isEndDate == endDatePass && isDesc == descPass && isVendor == vendorPass && amountPass == isAmount) {
                System.out.println();
                System.out.printf("Date: %s\nTime: %s\nDescription: %s\nVendor: %s\nPrice: $%.2f\n", info.getDate(), info.getTime(), info.getDesc(), info.getVendor(), info.getPrice());
                System.out.println();
            }
        }
    }

    public static HashMap<String, Info> loadInfo() {
        HashMap<String, Info> infoList = new HashMap<>();

        try {
            FileReader fileReader = new FileReader("src/main/resources/Transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);

            String input;
            bufReader.readLine();
            while ((input = bufReader.readLine()) != null) {
                String[] tokens = input.split("[|]");
                String date = tokens[0];
                String time = tokens[1];
                String desc = tokens[2];
                String vendor = tokens[3];
                double price = Double.parseDouble(tokens[4]);

                infoList.put(desc, new Info(date, time, desc, vendor, price));
            }
            bufReader.close();
        } catch (IOException e) {
            e.getMessage();
        }
        return infoList;
    }
}
