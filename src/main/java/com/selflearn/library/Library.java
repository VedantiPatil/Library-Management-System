package com.selflearn.library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Library {

    private List<Book> books = new ArrayList<>();
    private List<Student> students = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public List<Student> getStudents() {
        return students;
    }

    //Files name for persistence
    private static final String BOOK_FILE = "books.txt";
    private static final String STUDENT_FILE = "students.txt";

    // ------------------- Core Book Operations -------------------

    public void addBook(Book book) {
        books.add(book);
        System.out.println("✅ Book added: " + book.getTitle());
        saveToFile();
    }

    public void viewBooks(){
        if(books.isEmpty()){
            System.out.println("⚠️ No books available in the library.");
            return;
        }
        System.out.println("\n📚 Books in Library:");
        for (Book book : books) {
            System.out.println(book.getbookId() + " | " + book.getTitle() + " | " +
                    book.getAuthor() + " | Issued: " + book.isIssued());
        }
    }

    // ------------------- Core Student Operations -------------------

    public  void addStudent(Student student){
        students.add(student);
        System.out.println("✅ Student Registered Successfully: " + student.getName());
        saveStudentsToFile();
    }

    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("\nRegistered Students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }


    // ------------------- Issue & Return Operations -------------------

    public void issueBook(int bookId , int studentId){
        Book bookToIssue  = findBookById(bookId);
        Student student = findStudentById(studentId);

        if(bookToIssue  == null){
            System.out.println("Book not found.");
            return;
        }
        if(student == null){
            System.out.println("Student not found.");
            return;
        }
        if (bookToIssue .isIssued()) {
            System.out.println("Book already issued.");
            return;
        }
        bookToIssue .setIssued(true);
        student.borrowBook(bookToIssue );
        System.out.println("Book '" + bookToIssue .getTitle() + "' issued to " + student.getName());

        saveToFile();
        saveStudentsToFile();
    }

    public void returnBook(int bookId, int studentId){
        Book bookToReturn  = findBookById(bookId);
        Student student = findStudentById(studentId);

        if(bookToReturn  ==null || student ==null) {
            System.out.println("Invalid book or student ID.");
            return;
        }
        if(!bookToReturn .isIssued()){
            System.out.println("This book wasn't issued!");
            return;
        }
        bookToReturn .setIssued(false);
            student.returnBook(bookToReturn );
            System.out.println("🔁 Book '" + bookToReturn .getTitle() + "' returned by " + student.getName());
        saveToFile();
        saveStudentsToFile();
    }




    // ------------------- Helper Find Methods -------------------
    private Student findStudentById(int studentId) {
        for(Student  s : students){
            if(s.getStudentId() == studentId) return s;
        }
        return null;
    }

    private Book findBookById(int bookId) {
        for(Book b : books){
            if(b.getbookId() == bookId) return  b;
        }
        return  null;
    }

    // ------------------- File Handling for Books -------------------

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (Book book : books) {
                writer.write(book.getbookId() + "," + book.getTitle() + "," +
                        book.getAuthor() + "," + book.isIssued());
                writer.newLine();
            }
            System.out.println("💾 Books saved successfully!");
        } catch (IOException e) {
            System.out.println("⚠️ Error saving books: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        books.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int bookId = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    boolean issued = Boolean.parseBoolean(parts[3]);

                    Book book = new Book(bookId, title, author);
                    if (issued) {
                        book.setIssued(true);
                    }
                    books.add(book);
                }
            }
            System.out.println("📚 Books loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No saved books found. Starting fresh...");
        } catch (IOException e) {
            System.out.println("⚠️ Error loading books: " + e.getMessage());
        }
    }

    // ------------------- File Handling for Students -------------------

    public void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_FILE))) {
            for (Student student : students) {
                String borrowedIds = student.getBorrowedBooks().stream()
                        .map(book -> String.valueOf(book.getbookId()))
                        .collect(Collectors.joining(";"));

                writer.write(student.getStudentId() + "," + student.getName() + "," + borrowedIds);
                writer.newLine();
            }
            System.out.println("💾 Students saved successfully!");
        } catch (IOException e) {
            System.out.println("⚠️ Error saving students: " + e.getMessage());
        }
    }

    public void loadStudentsFromFile() {
        students.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length >= 2) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    Student student = new Student(id, name);

                    if (parts.length == 3 && !parts[2].isEmpty()) {
                        String[] borrowedIds = parts[2].split(";");
                        for (String bookIdStr : borrowedIds) {
                            try {
                                int bookId = Integer.parseInt(bookIdStr);
                                Book borrowedBook = findBookById(bookId);
                                if (borrowedBook != null) {
                                    student.borrowBook(borrowedBook);
                                }
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                    students.add(student);
                }
            }
            System.out.println("👩‍🎓 Students loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No saved students found. Starting fresh...");
        } catch (IOException e) {
            System.out.println("⚠️ Error loading students: " + e.getMessage());
        }
    }
}
