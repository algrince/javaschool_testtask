# Spreadsheet #
[Back to readme](/README.md)

### General description ###

Implement a simple spreadsheet as a command-line program. 
It should be able to process table cells.

### Each cell may contain: 

- Non-negative integer
- Text strings that start with the character **'**
- String expressions that start with **=**. Only 1 arithmetic operation (+ - / *)

- Expression Example:
    - **=C1**  <--- link to the value in the C1 cell
    - **=A1+C1**  <--- sum of the A1 and C1 cells values
    - **=A1-C1**  <--- difference of the A1 and C1 cells values
    - **=A1*C1**  <--- multiplication of the A1 and C1 cells values
    - **=A1/C1**  <--- division of the A1 and C1 cells values
    - **=4+3**  <--- sum of the 4 and 3 numbers
    - **=4-3**  <--- difference of the 4 and 3 numbers
    - **=4*3**  <--- multiplication of the 4 and 3 numbers
    - **=4/3**  <--- division of the 4 and 3 numbers

### Input and expected output ###

**Input:**
```
10          =C2       7       'Java1
=A1+B1      =A2*B1    =B3-C3  'Java2
'Java3      =4-3      5       'Java4
```

**Output:**
```
10          -4        7       Java1
6           -24       -4      Java2
Java3       1         5       Java4
```