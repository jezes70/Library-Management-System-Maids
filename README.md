# 📚 Library Management System

The Library Management System is a Spring Boot / Java application with MySQL designed to facilitate the management of books, patrons, and borrowing records in a library.

## 📌 Entities

### 1. 📖 Book

The `Book` entity represents a book in the library.

- **Attributes:**
  - `id` (Long): Unique identifier for the book.
  - `title` (String): Title of the book.
  - `author` (String): Author of the book.
  - `publicationYear` (Integer): Year of publication.
  - `isbn` (String): International Standard Book Number (ISBN) of the book.
  - `borrowed` (Boolean): Indicates whether the book is currently borrowed.

### 2. 🧑 Patron

The `Patron` entity represents a library patron.

- **Attributes:**
  - `id` (Long): Unique identifier for the patron.
  - `name` (String): Name of the patron.
  - `contactInformation` (String): Contact information of the patron.

### 3. 📋 Borrowing Record

The `BorrowingRecord` entity tracks the association between books and patrons.

- **Attributes:**
  - `id` (Long): Unique identifier for the borrowing record.
  - `book` (ManyToOne relationship with `Book`): The book associated with the borrowing record.
  - `patron` (ManyToOne relationship with `Patron`): The patron associated with the borrowing record.
  - `borrowingDate` (LocalDate): Date when the book was borrowed.
  - `returnDate` (LocalDate): Date when the book is expected to be returned.

## 🗃️ Database Schema

The entities are mapped to corresponding tables in the MySQL database. The relationships between entities are maintained through foreign key constraints.

- `book` table: Stores information about books.
- `patron` table: Stores information about patrons.
- `borrowing_record` table: Stores information about borrowing records, including foreign key references to `book` and `patron` tables.

## 🌐 API Endpoints
[API Collection Documentation on Swagger](http://localhost:8082/swagger-ui/index.html#/) for testing API
<br>

## 📚 Book Management Endpoints

#### 1. Retrieve All Books
`GET /api/books`
Retrieve a list of all books in the library.

#### 2. Retrieve Book by ID
`GET /api/books/{id}`
Retrieve details of a specific book by its ID.

#### 3. Add New Book
`POST /api/books`
Add a new book to the library.

#### 4. Update Book Information
`PUT /api/books/{id}`
Update the information of an existing book.

#### 5. Remove Book from Library
`DELETE /api/books/{id}`
Remove a book from the library.

## 🧑 Patron Management Endpoints

#### 1. Retrieve All Patrons
`GET /api/patrons`
Retrieve a list of all patrons in the system.

#### 2. Retrieve Patron by ID
`GET /api/patrons/{id}`
Retrieve details of a specific patron by their ID.

#### 3. Add New Patron
`POST /api/patrons`
Add a new patron to the system.

#### 4. Update Patron Information
`PUT /api/patrons/{id}`
Update the information of an existing patron.

#### 5. Remove Patron from System
`DELETE /api/patrons/{id}`
Remove a patron from the system.

## 📋 Borrowing Endpoints

#### 1. Borrow Book

`POST /api/borrowing/borrow/{bookId}/patron/{patronId}`
Allow a patron to borrow a book.

#### 2. Return Borrowed Book

`PUT /api/borrowing/return/{bookId}/patron/{patronId}`
Record the return of a borrowed book by a patron.

## ⚙️ Additional Features

1. **🔍 Request Validation**

- The Library Management System includes a validation mechanism to ensure the integrity of API requests. This includes validating required fields and ensuring that the submitted data adheres to the specified criteria.
- For example, when creating or updating a book or patron record, the system verifies that all required fields are provided and meet the expected data types. This helps maintain data consistency and prevents erroneous input.

2. **🚀 Caching Mechanism**

- Implemented a caching mechanism to optimize performance.
- Frequently accessed data, such as the list of all books, is cached to reduce response times.

3. **🔍 Aspects and Logging**

- Utilized Aspect-Oriented Programming (AOP) to log method calls and exceptions.
- Logging provides insights into the application's behavior, aiding in debugging and monitoring.

## 🛠️ Build and Run Spring Boot Application

### 📋 Requirements
- Java 17
- Maven: Maven is used as the build tool for the project.
  If Maven is not installed, you can use the included `mvnw` script.

### 🐳 Running with Docker
To run the Spring Boot application with Docker, use the following command:

```docker-compose up```

This command reads the `docker-compose.yml` file and sets up the Docker container for the MySQL database.

Wait for the containers to start, then build and run the application.

### 🔧 Building and Running the Application
After the Docker containers are up, open a new terminal and navigate to the root directory of the project. Then, execute the following commands:

1. ```./mvnw clean verify```

2. ```./mvnw spring-boot:run```

The first command will clean the project, compile the source code, run tests, and package the application.

The second command will start the application locally. The application will be accessible at http://localhost:8082.

### ⏹️ Stopping the Application
To stop the running Docker container and the locally running Spring Boot application, use the following command:

```docker-compose down```

This will stop and remove the container used for the MySQL database.