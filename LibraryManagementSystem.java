import java.util.Scanner;
class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;
    private String issuedTo;
    
    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.issuedTo = null;
    }
    
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public String getIssuedTo() { return issuedTo; }
    
    public boolean issueBook(String userId) {
        if (isAvailable) {
            this.isAvailable = false;
            this.issuedTo = userId;
            return true;
        }
        return false;
    }
    
    public boolean returnBook() {
        if (!isAvailable) {
            this.isAvailable = true;
            this.issuedTo = null;
            return true;
        }
        return false;
    }
    
    public String toString() {
        return "ID: " + bookId + ", Title: " + title + ", Author: " + author + 
               ", Available: " + isAvailable;
    }
}

class User {
    private String userId;
    private String name;
    private int booksIssued;
    private int maxBooks;
    
    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.booksIssued = 0;
        this.maxBooks = 3;
    }
    
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public int getBooksIssued() { return booksIssued; }
    
    public boolean canIssueBook() {
        return booksIssued < maxBooks;
    }
    
    public void issueBook() {
        if (canIssueBook()) {
            booksIssued++;
        }
    }
    
    public void returnBook() {
        if (booksIssued > 0) {
            booksIssued--;
        }
    }
    
    public String toString() {
        return "ID: " + userId + ", Name: " + name + ", Books Issued: " + booksIssued + "/" + maxBooks;
    }
}

class Library {
    private Book[] books;
    private User[] users;
    private int bookCount;
    private int userCount;
    
    public Library() {
        books = new Book[50];
        users = new User[20];
        bookCount = 0;
        userCount = 0;
    }
    
    public void addBook(String bookId, String title, String author) {
        if (bookCount < books.length) {
            books[bookCount] = new Book(bookId, title, author);
            bookCount++;
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Library is full!");
        }
    }
    
    public void addUser(String userId, String name) {
        if (userCount < users.length) {
            users[userCount] = new User(userId, name);
            userCount++;
            System.out.println("User added successfully!");
        } else {
            System.out.println("User limit reached!");
        }
    }
    
    public void issueBook(String bookId, String userId) {
        Book book = findBook(bookId);
        User user = findUser(userId);
        
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        
        if (!book.isAvailable()) {
            System.out.println("Book is already issued!");
            return;
        }
        
        if (!user.canIssueBook()) {
            System.out.println("User has reached book limit!");
            return;
        }
        
        if (book.issueBook(userId)) {
            user.issueBook();
            System.out.println("Book issued successfully to " + user.getName());
        }
    }
    
    public void returnBook(String bookId, String userId) {
        Book book = findBook(bookId);
        User user = findUser(userId);
        
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        
        if (book.isAvailable()) {
            System.out.println("Book is not issued!");
            return;
        }
        
        if (!book.getIssuedTo().equals(userId)) {
            System.out.println("Book was not issued to this user!");
            return;
        }
        
        if (book.returnBook()) {
            user.returnBook();
            System.out.println("Book returned successfully!");
        }
    }
    
    public void displayBooks() {
        System.out.println("\n--- All Books ---");
        for (int i = 0; i < bookCount; i++) {
            System.out.println(books[i]);
        }
    }
    
    public void displayUsers() {
        System.out.println("\n--- All Users ---");
        for (int i = 0; i < userCount; i++) {
            System.out.println(users[i]);
        }
    }
    
    private Book findBook(String bookId) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookId().equals(bookId)) {
                return books[i];
            }
        }
        return null;
    }
    
    private User findUser(String userId) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUserId().equals(userId)) {
                return users[i];
            }
        }
        return null;
    }
}



public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        
        library.addBook("B001", "Java Programming", "James Gosling");
        library.addBook("B002", "Data Structures", "Robert Lafore");
        library.addUser("U001", "John Doe");
        library.addUser("U002", "Jane Smith");
        
        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display All Books");
            System.out.println("6. Display All Users");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    String bookId = scanner.nextLine();
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    library.addBook(bookId, title, author);
                    break;
                    
                case 2:
                    System.out.print("Enter User ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    library.addUser(userId, name);
                    break;
                    
                case 3:
                    System.out.print("Enter Book ID to issue: ");
                    String issueBookId = scanner.nextLine();
                    System.out.print("Enter User ID: ");
                    String issueUserId = scanner.nextLine();
                    library.issueBook(issueBookId, issueUserId);
                    break;
                    
                case 4:
                    System.out.print("Enter Book ID to return: ");
                    String returnBookId = scanner.nextLine();
                    System.out.print("Enter User ID: ");
                    String returnUserId = scanner.nextLine();
                    library.returnBook(returnBookId, returnUserId);
                    break;
                    
                case 5:
                    library.displayBooks();
                    break;
                    
                case 6:
                    library.displayUsers();
                    break;
                    
                case 7:
                    System.out.println("Thank you for using Library Management System!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
