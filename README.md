# Plugin-Based Text Editor  

This repository contains a **Plugin-Based Text Editor** project developed for the Design Patterns course. The project demonstrates the use of key design patterns to implement a modular and extensible text editor.  

## Overview  
The Plugin-Based Text Editor is designed to support various functionalities through plugins, making it flexible and easy to extend. The project highlights the use of **Strategy**, **Command**, **Factory**, and **Singleton** patterns to ensure clean code structure, scalability, and maintainability.  

## Key Features  
- **Plugin-Based Architecture**: Easily add or remove functionalities via plugins.  
- **Extensibility**: New features can be incorporated without modifying the core application.  
- **User-Friendly Interface**: Intuitive design for an enhanced user experience.  

## Design Patterns Used  

### 1. Strategy Pattern  
Used to define a family of text processing algorithms (e.g., text formatting, spell checking) and make them interchangeable at runtime.  

### 2. Command Pattern  
Implemented to encapsulate user actions (e.g., undo/redo, text modifications) into command objects, allowing easy execution and tracking of operations.  

### 3. Factory Pattern  
Utilized for creating plugin instances dynamically, ensuring a decoupled and organized creation process.  

### 4. Singleton Pattern  
Applied to manage shared resources, such as the editor's configuration and plugin registry, ensuring only one instance of these components exists.  

## Tech Stack  
- **Language**: Java
- **Frameworks/Libraries**:  
  - **Swing**: For building the graphical user interface.   

## Class Overview  

### 1. Text Editor Core  
- **PluginTextEditor**:  
  The main editor class, responsible for managing:  
  - Plugins  
  - Themes  
  - Text formatting  
  - Export functionality  
  - Command functionality  

### 2. Plugins  
- **MarkdownPlugin**:  
  Enables Markdown-specific features, including:  
  - Syntax highlighting  
  - Exporting to HTML  

- **LaTeXPlugin**:  
  Provides LaTeX-specific functionalities, such as:  
  - Exporting to PDF  

### 3. Commands  
Encapsulates text editor operations and supports undo functionality. Examples include:  
- **CopyCommand**  
- **CutCommand**  
- **DeleteCommand**  
- **PasteCommand**  

### 4. Strategies  
- **ThemeStrategy**:  
  Supports themes such as:  
  - Solarized  
  - Light  
  - Dark  

- **TextFormattingStrategy**:  
  Provides various text formatting options, including:  
  - Bold  
  - Italic  
  - Underline  

- **ExportStrategy**:  
  Manages exporting to different formats, such as:  
  - Markdown  
  - HTML


 ## UML  

![Uml](https://github.com/user-attachments/assets/a943a38c-82d1-4aea-971c-db15129c51a2)


