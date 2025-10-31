package com.selflearn.library;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibraryGUI extends JFrame {

    private final Library library = new Library();

    // Components for book tab
    private JTextField bookIdField, titleField, authorField;
    private JTextArea bookOutputArea;

    // Components for student tab
    private JTextField studentIdField, studentNameField;
    private JTextArea studentOutputArea;

    // Components for issue/return tab
    private JTextField issueBookIdField, issueStudentIdField;
    private JTextField returnBookIdField, returnStudentIdField;
    private JTextArea transactionArea;

    public LibraryGUI() {
        library.loadFromFile();

        setTitle("📚 Library Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Books", createBookPanel());
        tabbedPane.add("Students", createStudentPanel());
        tabbedPane.add("Issue/Return", createTransactionPanel());

        add(tabbedPane);
        setVisible(true);
    }

    // ---------------- BOOK PANEL ----------------
    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        inputPanel.add(bookIdField);

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Book");
        JButton viewBtn = new JButton("View Books");
        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);

        bookOutputArea = new JTextArea();
        bookOutputArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bookOutputArea), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addBookAction());
        viewBtn.addActionListener(e -> viewBooksAction());

        return panel;
    }

    private void addBookAction() {
        try {
            int id = Integer.parseInt(bookIdField.getText());
            String title = titleField.getText();
            String author = authorField.getText();

            if (title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            Book book = new Book(id, title, author);
            library.addBook(book);
            bookOutputArea.append("Added: " + book + "\n");

            bookIdField.setText("");
            titleField.setText("");
            authorField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Book ID must be a number!");
        }
    }

    private void viewBooksAction() {
        bookOutputArea.setText("");
        if (library.getBooks().isEmpty()) {
            bookOutputArea.setText("No books available.\n");
            return;
        }
        for (Book b : library.getBooks()) {
            bookOutputArea.append(b + "\n");
        }
    }

    // ---------------- STUDENT PANEL ----------------
    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        inputPanel.add(studentIdField);

        inputPanel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        inputPanel.add(studentNameField);

        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Student");
        JButton viewBtn = new JButton("View Students");
        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);

        studentOutputArea = new JTextArea();
        studentOutputArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(studentOutputArea), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addStudentAction());
        viewBtn.addActionListener(e -> viewStudentsAction());

        return panel;
    }

    private void addStudentAction() {
        try {
            int id = Integer.parseInt(studentIdField.getText());
            String name = studentNameField.getText();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!");
                return;
            }

            Student s = new Student(id, name);
            library.addStudent(s);
            studentOutputArea.append("Added: " + s + "\n");

            studentIdField.setText("");
            studentNameField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Student ID must be a number!");
        }
    }

    private void viewStudentsAction() {
        studentOutputArea.setText("");
        if (library.getStudents().isEmpty()) {
            studentOutputArea.setText("No students registered.\n");
            return;
        }
        for (Student s : library.getStudents()) {
            studentOutputArea.append(s + "\n");
        }
    }

    // ---------------- ISSUE/RETURN PANEL ----------------
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel issuePanel = new JPanel(new GridLayout(2, 2));
        issuePanel.setBorder(BorderFactory.createTitledBorder("Issue Book"));

        issuePanel.add(new JLabel("Book ID:"));
        issueBookIdField = new JTextField();
        issuePanel.add(issueBookIdField);

        issuePanel.add(new JLabel("Student ID:"));
        issueStudentIdField = new JTextField();
        issuePanel.add(issueStudentIdField);

        JPanel returnPanel = new JPanel(new GridLayout(2, 2));
        returnPanel.setBorder(BorderFactory.createTitledBorder("Return Book"));

        returnPanel.add(new JLabel("Book ID:"));
        returnBookIdField = new JTextField();
        returnPanel.add(returnBookIdField);

        returnPanel.add(new JLabel("Student ID:"));
        returnStudentIdField = new JTextField();
        returnPanel.add(returnStudentIdField);

        JPanel buttonPanel = new JPanel();
        JButton issueBtn = new JButton("Issue");
        JButton returnBtn = new JButton("Return");
        buttonPanel.add(issueBtn);
        buttonPanel.add(returnBtn);

        transactionArea = new JTextArea();
        transactionArea.setEditable(false);

        panel.add(issuePanel, BorderLayout.NORTH);
        panel.add(returnPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(new JScrollPane(transactionArea), BorderLayout.EAST);

        issueBtn.addActionListener(e -> issueBookAction());
        returnBtn.addActionListener(e -> returnBookAction());

        return panel;
    }

    private void issueBookAction() {
        try {
            int bookId = Integer.parseInt(issueBookIdField.getText());
            int studentId = Integer.parseInt(issueStudentIdField.getText());
            library.issueBook(bookId, studentId);
            transactionArea.append("Issued Book ID " + bookId + " to Student ID " + studentId + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid IDs!");
        }
    }

    private void returnBookAction() {
        try {
            int bookId = Integer.parseInt(returnBookIdField.getText());
            int studentId = Integer.parseInt(returnStudentIdField.getText());
            library.returnBook(bookId, studentId);
            transactionArea.append("Returned Book ID " + bookId + " from Student ID " + studentId + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid IDs!");
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryGUI::new);
    }
}


//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//public class LibraryGUI extends JFrame {
//
//    private Library library = new Library();
//
//    private JTextField bookIdField, titleField, authorField ;
//    private JTextArea displayArea;
//
//    public LibraryGUI() {
//        setTitle("📚 Library Management System");
//        setSize(600, 500);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        // Main Panel
//        JPanel panel = new JPanel();
//        panel.setLayout(new BorderLayout());
//
//        // Input Section
//        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
//        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Book Details"));
//
//        inputPanel.add(new JLabel("Book ID:"));
//        bookIdField = new JTextField();
//        inputPanel.add(bookIdField);
//
//        inputPanel.add(new JLabel("Title:"));
//        titleField = new JTextField();
//        inputPanel.add(titleField);
//
//        inputPanel.add(new JLabel("Author:"));
//        authorField = new JTextField();
//        inputPanel.add(authorField);
//
//
//
//        panel.add(inputPanel, BorderLayout.NORTH);
//
//        // Buttons Section
//        JPanel buttonPanel = new JPanel();
//        JButton addBtn = new JButton("➕ Add Book");
//        JButton viewBtn = new JButton("📖 View Books");
//        JButton issueBtn = new JButton("🧾 Issue Book");
//        JButton returnBtn = new JButton("🔁 Return Book");
//
//        buttonPanel.add(addBtn);
//        buttonPanel.add(viewBtn);
//        buttonPanel.add(issueBtn);
//        buttonPanel.add(returnBtn);
//
//        panel.add(buttonPanel, BorderLayout.CENTER);
//
//        // Output Section
//        displayArea = new JTextArea();
//        displayArea.setEditable(false);
//        displayArea.setBorder(BorderFactory.createTitledBorder("Library Output"));
//        panel.add(new JScrollPane(displayArea), BorderLayout.SOUTH);
//
//        add(panel);
//
//        // Action Listeners
//        addBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    int id = Integer.parseInt(bookIdField.getText());
//                    String title = titleField.getText();
//                    String author = authorField.getText();
//
//
//                    Book book = new Book(id, title, author);
//                    library.addBook(book);
//                    displayArea.setText("✅ Book added successfully!\n\n" + library.viewBooks());
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID! Please enter a number.");
//                }
//            }
//        });
//
//        viewBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                displayArea.setText("📚 Current Books in Library:\n\n" + library.viewBooks());
//            }
//        });
//
//        issueBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String input = JOptionPane.showInputDialog("Enter Book ID to Issue:");
//                try {
//                    int id = Integer.parseInt(input);
//                    library.issueBook(id);
//                    displayArea.setText("🧾 Book Issued!\n\n" + library.viewBooks());
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID or Book not found.");
//                }
//            }
//        });
//
//        returnBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String input = JOptionPane.showInputDialog("Enter Book ID to Return:");
//                try {
//                    int id = Integer.parseInt(input);
//                    library.returnBook(id);
//                    displayArea.setText("🔁 Book Returned!\n\n" + library.viewBooks());
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(null, "Invalid ID or Book not found.");
//                }
//            }
//        });
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new LibraryGUI().setVisible(true));
//    }
//}

