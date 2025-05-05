# CodeWeaver

CodeWeaver is a Java-based desktop application that allows users to visually generate HTML and CSS code using a graphical interface. It is designed to help developers, students, and designers create structured and styled web pages without manually writing HTML.

## Features

- Add HTML elements such as headings, paragraphs, lists, links, images, and spans
- Customize styling with text alignment, font weight, italics, underlining, and more
- Real-time HTML preview generation
- Undo and redo functionality for managing design changes
- Clean object-oriented design for extensibility

## Technologies Used

- Java (with Swing for GUI)
- Object-oriented programming

## Project Structure

Code/
├── HtmlGuiApp.java # Main application and GUI logic
├── HtmlElement.java # Base class for HTML components
├── HtmlRoot.java # Manages all elements and rendering
├── Heading.java # Represents HTML heading elements
├── Paragraph.java # Represents paragraph elements with alignment
├── ListElement.java # Ordered and unordered lists
├── Link.java # Anchor tags with href and target
├── HtmlImage.java # Image tags with dimensions and alt text
├── Span.java # Inline elements with font styling


## How to Use

1. Compile and run the `HtmlGuiApp.java` file.
2. Use the GUI to choose an HTML element type.
3. Enter the required content and styling options.
4. Add the element to the canvas.
5. View the generated HTML code in the preview panel.
6. Copy or save the generated code as needed.

## Compilation and Execution

### Requirements

- Java Development Kit (JDK) 8 or higher
- Java IDE or command-line tools

### Compile and Run

```bash
javac *.java
java HtmlGuiApp

Example Output:

<!DOCTYPE html>
<html>
<head>
  <title>Generated HTML</title>
  <meta charset="UTF-8">
  <style>body { margin: 20px; }</style>
</head>
<body>
  <h1 style="color:red;">Welcome</h1>
  <p style="text-align:center;">This is a paragraph.</p>
  <img src="image.jpg" alt="Example" width="300" height="200">
</body>
</html>

Future Improvements

File export functionality for HTML and CSS
Drag-and-drop UI layout builder
Support for JavaScript insertion
Enhanced styling interface with color pickers and font selectors

Contributing
Contributions are welcome. Fork the repository and open a pull request with your proposed changes.
