# Room Dimension Analysis Application

This application processes a list of room dimensions from a .txt file to provide the following insights:

- Total Wallpaper Calculation: computes the total square footage of wallpaper required for all the rooms listed in the input file.
- Cubic Rooms Identification: identifies all cubic-shaped rooms, sorted by their total wallpaper requirement in descending order.
- Duplicate Room Detection: lists all rooms that appear multiple times in the input file.

## Compiling and Running the Application

To compile and execute the application with the default input file (sample-input.txt) provided in the resources folder, use the following commands:

```bash
mvn clean compile
mvn exec:java
```

If you would like to process a custom input file, provide the file path as an argument during execution:

```bash
mvn exec:java -Dexec.args="YOUR/PATH/TO/THE/INPUT/your-input.txt"
```

## Input File Format

The input file should be a .txt file where each line represents a room's dimensions in the format:

```text
width x height x length
```

Example:

```text
4x7x27
5x12x28
8x26x6
...
```

Ensure the dimensions are positive integers and separated by the x character.

## Testing the Application

To execute the unit tests for this application, use the following command:

```bash
mvn test
```

The tests validate the correctness of key functionalities, including input validation, room dimension processing, and result calculation.

## Notes

Ensure that the input file adheres to the specified format to avoid errors during execution. For any issues or questions, refer to the test cases included in the application to understand the expected behavior of the program.