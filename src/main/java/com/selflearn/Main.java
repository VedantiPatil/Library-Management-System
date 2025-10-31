package com.selflearn;


import com.selflearn.library.Book;
import com.selflearn.library.Library;
import com.selflearn.library.Student;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        // Load previous saved data
        library.loadFromFile();
        library.loadStudentsFromFile();

        int choice;

        do {
            System.out.println("\n===== 📚 LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Add Student");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Save & Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = sc.nextLine();
                    library.addBook(new Book(bookId, title, author));
                    break;

                case 2:
                    library.viewBooks();
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Student Name: ");
                    String studentName = sc.nextLine();
                    library.addStudent(new Student(studentId, studentName));
                    break;

                case 4:
                    System.out.print("Enter Book ID to issue: ");
                    int issueId = sc.nextInt();
                    System.out.print("Enter Student ID: ");
                    int issueStudentId = sc.nextInt();
                    library.issueBook(issueId, issueStudentId);
                    break;

                case 5:
                    System.out.print("Enter Book ID to return: ");
                    int returnBookId = sc.nextInt();
                    System.out.print("Enter Student ID: ");
                    int returnStudentId = sc.nextInt();
                    library.returnBook(returnBookId, returnStudentId);
                    break;

                case 6:
                    library.saveToFile();
                    library.saveStudentsToFile();
                    System.out.println("💾 Data saved. Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("⚠️ Invalid choice. Please try again!");
            }

        } while (choice != 6);

        sc.close();
        System.out.println("Goodbye 👋");
    }
}
