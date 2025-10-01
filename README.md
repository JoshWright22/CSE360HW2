# Student Q&A System - HW2

This project is a JavaFX-based **Student Q&A System** designed for students to create, view, update, and delete questions and answers. It simulates a Reddit-like platform where users can share knowledge and request help, with a backend database to store content.

## Features

- **Question Management**
  - Create questions with a title and description
  - View all questions posted by others
  - Update your own questions
  - Delete your questions
  - Input validation with error messages for empty or invalid fields

- **Answer Management**
  - Post answers to questions
  - View all answers to a specific question
  - Update your own answers
  - Delete your answers
  - Input validation with error messages for empty or invalid fields

- **User Account Management**
  - Set up accounts without requiring invitation codes
  - User registration and login handling

- **Database Integration**
  - Uses H2 embedded database to store users, questions, and answers
  - Database helper class handles all CRUD operations

## User Stories

- As a student, I can create a question with a title and description to request help.
- As a student, I can read a list of questions to find relevant ones before asking a new question.
- As a student, I can update or delete my previously submitted questions.
- As a student, I can create, read, update, or delete answers for questions.
- I can see error messages when creating or updating content with invalid fields.

## Tech Stack

- **Language:** Java  
- **UI:** JavaFX  
- **Database:** H2 (embedded)  
- **Testing:** JUnit 5  

## Project Structure

