CSE360HW2

Overview
CSE360HW2 is a JavaFX application simulating a student Q&A platform similar to Reddit or StackOverflow.
Students can create, read, update, and delete questions and answers, and interact with other students' content. This project implements a local H2 database to store users, questions, and answers.

Features / User Stories

Questions
- As a student, I can create a question with a title and description so I can request help.
- As a student, I can read a list of questions others have asked so I can find relevant ones before asking a new question.
- As a student, I can update my previously submitted question so I can correct or improve it.
- As a student, I can delete my question so outdated or duplicate requests are removed.
- As a student, I can see error messages if I try to create or update a question with missing or invalid fields.

Answers
- As a student, I can create an answer to a question so I can share knowledge with others.
- As a student, I can read all answers to a given question so I can benefit from multiple perspectives.
- As a student, I can update my answer so I can correct or improve it.
- As a student, I can delete my answer so that incorrect or unhelpful content is removed.
- As a student, I can see error messages if I try to create or update an answer with missing or invalid fields.

Users
- Users can register with a username and password.
- Users can log in and access their content.

Technology Stack
- Java 20+
- JavaFX 24
- H2 Database
- JUnit 5 for testing

Installation
1. Clone the repository:
   git clone https://github.com/<your-username>/CSE360HW2.git

2. Open the project in Eclipse or IntelliJ.

3. Add JavaFX library to your module path. Example VM arguments for running:
   --module-path "C:\path\to\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml

4. Run the Main.java file to launch the application.

Project Structure
- application/ - JavaFX UI pages and controllers.
- databasePart1/ - Database helper and data model classes.
- test/ - JUnit tests for questions and answers CRUD functionality.

UML Diagrams
- Class Diagram: Shows relationships between User, DatabaseHelper, Question, and Answer.
- Sequence Diagrams: Show how creating questions and answers flows through the UI and database.

Testing
JUnit tests cover all CRUD operations and validation rules for questions and answers.

Example test cases:
- Create question with valid input → success
- Create question with empty title → error
- Update an answer → success
- Delete a question that does not exist → error

Notes
- No invitation code is required to create an account.
- The application stores all data in a local H2 database (~/FoundationDatabase).
- Error messages are displayed for invalid input.
