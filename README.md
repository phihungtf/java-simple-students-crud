## Getting Started

To get started, you need to have the following installed:

- [Java Development Kit (JDK)](https://www.oracle.com/technetwork/java/javase/downloads/index.html) (version 8 or above)

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Compiling and Running

To compile your code, open the terminal in the root of your workspace and run the following command:

```bash
javac -d bin -cp src src/App.java
```

The above command compiles the code in `src` folder and generates the output in `bin` folder.

To run your code, open the terminal and run the following command:

```bash
java -cp bin App
```

The above command runs the code in `bin` folder.

You can also execute the `MyApp.jar` file to run the application.

## Features

The GUI of the application is built using Java Swing.

![image](/images/gui.png)

The application has the following features:

- Add a new student
  - To add a new student, click on the `Add` button.
  - A row will be added to the table.
  - Enter the student's information into the row.
- Remove a student
  - To delete a student, select the student you want to delete (by clicking on one cell of the row).
  - Click on the `Remove` button.
- Update a student
  - To update a student, select the student you want to update (by clicking on one cell of the row).
  - Enter the new information into the row.
  - Press `Enter`.
- View all students: all students will be displayed in the table.
- Sort students by `Ma HS`, `Diem`.
  - To sort students by `Ma HS`, click on the `Ma HS` column header.
  - To sort students by `Diem`, click on the `Diem` column header.
  - Other columns also support sorting.
- Load students from a binary file (`.bin`), or a CSV file (`.csv`).
  - To load students from a file, click on the `Open` button. You might need to save your current work first.
  - A file chooser will be displayed.
  - Select the file you want to load from.
    - To load students from a binary file, select type `Binary File (*.bin)`.
  - To load students from a CSV file, select type `CSV File (*.csv)`.
- Save students to a binary file (`.bin`), or a CSV file (`.csv`).
  - To save students to a file, click on the `Save` button.
  - If you have not loaded students from a file, a file chooser will be displayed.
  - Select the file you want to save to.
  - To save students to a binary file, select type `Binary File (*.bin)`.
  - To save students to a CSV file, select type `CSV File (*.csv)`.
  - The students will be saved to the file you loaded from.
- You can also load a binary file (`.bin`) and save to a CSV file (`.csv`), or vice versa.
  - Load a binary file (`.bin`) by clicking on the `Open` button.
  - When the file is loaded, click on the `Save As` button.
  - Select type `CSV File (*.csv)`.
  - The students will be saved to a CSV file (`.csv`).

## Acknowledgements

- [Java Swing Tutorial](https://www.javatpoint.com/java-swing)

- [Java Swing JTable Tutorial](https://www.javatpoint.com/java-jtable)

- [Java Swing JFileChooser Tutorial](https://www.javatpoint.com/java-jfilechooser)

## Author

- [@phihungtf](https://www.github.com/phihungtf)
