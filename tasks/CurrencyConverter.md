# Currency Converter #

[Back to readme](/README.md)

### General description ###

You should write a simple financial converter that works in the console.

### Functional:  

- 2 currencies - dollar ($12) and euro (52.5euro)
- Operations **+** and **-**
- The dollar sign is indicated before the number, the euro - after.
- The number can be integer (including negative) or fractional, with a maximum of two decimal places after the dot.
- Support for currency conversion (convertToDollar(), convertToEuro())
- Rates must be set before conversion. Courses may vary!
- Type control: cannot convert a currency to itself
- It's not possible to sum or subtract different currencies

### Example:  

- 1$ - 0.8euro

```
input:  convertToDollar(convertToEuro($10.00)+5euro)
output: $16.25
```