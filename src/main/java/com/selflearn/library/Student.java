package com.selflearn.library;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private int studentId;
    private String name;
    private List<Book> borrowedBooks = new ArrayList<>();

    public Student(int studentId, String name){
        this.studentId = studentId;
        this.name=name;
    }

    public int getStudentId(){
        return studentId;
    }
    public String getName() {
        return name;
    }
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    //method for borrowedBook
    public void borrowBook(Book book){
        if(book != null || !book.isIssued()){
            borrowedBooks.add(book);
            book.setIssued(true);
            System.out.println(name + " borrowed: " + book.getTitle());
        }else{
            System.out.println("Book is not available for borrowing.");
        }
    }

    //method for returnBook
    public void returnBook(Book book){
        if(book !=null || borrowedBooks.contains(book)){
            borrowedBooks.remove(book);
            book.setIssued(false);
            System.out.println(name + " returned: " + book.getTitle());
        }else{
            System.out.println("This book was not borrowed by " + name + ".");
        }
    }

    // Method to view all borrowed books
    public void viewBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println(name + " has not borrowed any books.");
        } else {
            System.out.println(name + " has borrowed the following books:");
            for (Book book : borrowedBooks) {
                System.out.println(" - " + book.getTitle());
            }
        }
    }
    @Override
    public String toString(){
        return "Student { " + " id = " + studentId + ", name = " + name + " | " +"borrowedBooks=" + borrowedBooks.size() + '}';
    }
}
