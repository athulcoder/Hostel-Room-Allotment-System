# AI-Powered Hostel Room Allotment System

This is a Java-based application for managing student hostel room allotment using a local SQLite database and a Swing GUI (planned). The project uses Object-Oriented Programming concepts and modular design with DAO patterns.

---

## 📁 Project Structure

---

## 📦 Packages & Classes

### 1. `models.Student`

Represents a student with all relevant details for hostel room allotment.

**Attributes:**

- `String studentId`
- `String name`
- `String gender`
- `int age`
- `String department`
- `String academicYear`
- `String contactNumber`
- `String email`
- `String guardianName`
- `String guardianPhone`
- `String preferredRoomType`
- `String assignedRoom`
- `String sleepType`

**Methods:**

- Getters & setters for all fields
- Constructor with all parameters
- Default constructor

---

### 2. `dao.StudentDAO`

Handles database CRUD operations related to `Student`.

**Methods:**

- `boolean saveStudent(Student student)`
- `boolean updateStudent(Student student)`
- `Student getStudentById(String studentId)`
- `List<Student> getAllStudents()`
- `boolean deleteStudent(String studentId)`

---

### 3. `utils.DatabaseInitializer`

Responsible for initializing and connecting to the SQLite database.

**Methods:**

- `void initializeDatabase()` — Creates the `students` table if not exists
- `Connection getConnection()` — Returns a DB connection object

---

### 4. `Main.java`

Starting point of the application.

**Responsibilities:**

- Initializes the database
- Creates sample student records
- Invokes DAO methods to save/update data

---

## 🗄️ Database Schema

The app uses a single `students` table.

**Table Name:** `students`

| Column Name       | Type    | Description                       |
| ----------------- | ------- | --------------------------------- |
| studentId         | TEXT    | Primary Key                       |
| name              | TEXT    | Full name of the student          |
| gender            | TEXT    | Male/Female/Other                 |
| age               | INTEGER | Age in years                      |
| department        | TEXT    | Department (e.g., CSE, IT)        |
| academicYear      | TEXT    | Academic year (e.g., 1st Year)    |
| contactNumber     | TEXT    | Student's phone number            |
| email             | TEXT    | Email address                     |
| guardianName      | TEXT    | Parent/guardian name              |
| guardianPhone     | TEXT    | Guardian's phone number           |
| preferredRoomType | TEXT    | Single/Double etc.                |
| assignedRoom      | TEXT    | Allocated room ID/number          |
| sleepType         | TEXT    | Night Owl / Early Bird / Balanced |

> Note: `dateOfAdmission` was removed for simplicity in the prototype phase.

---

## 🔧 Technologies Used

- Java 17+
- SQLite (via JDBC)
- IntelliJ IDEA / VS Code
- JDBC Driver: `sqlite-jdbc`
- (Optional future UI: Java Swing)

---

## 🚀 How to Run

1. Ensure you have Java installed (`java --version`)
2. Clone the repo and open in IntelliJ or VS Code
3. Add the SQLite JDBC JAR (or use Maven)
4. Run `Main.java` to test with sample students

---

## 📌 To Do

- [ ] Swing-based GUI
- [ ] Room allocation logic
- [ ] Login and role management (Admin/Student)
- [ ] Room availability and preference logic

---

## 👥 Contributors

- Team Project — AI-Powered Hostel Room Allotment
- Technologies & structure guided by best Java OOP + DAO practices
