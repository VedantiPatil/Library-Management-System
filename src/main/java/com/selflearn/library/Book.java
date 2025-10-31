package com.selflearn.library;

public class Book {

    private int bookId;
    private String title;
    private String author;
  //  private String isbn;
    private boolean isIssued;

    public Book(int bookId, String title, String author){
        this.bookId = bookId;
        this.title = title;
        this.author = author;

        this.isIssued = false;

    }

   public int getbookId() {
        return bookId;
   }


//    public String getIsbn() {
//        return isbn;
//    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }


    public boolean isIssued() {
        return isIssued;
    }


    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public String toString(){
        return "Book{" +
                ", bookId ='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
//                ", isbn='" + isbn + '\'' +
                ", issued=" + isIssued +
                '}';
    }

}
