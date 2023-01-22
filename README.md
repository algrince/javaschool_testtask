# README #

This is a repo with T-Systems Java School preliminary examination tasks.
Code points where you solution is to be located are marked with TODOs.

The solution is to be written using Java version greater than 1.8, external libraries are forbidden. 
You can add dependencies with scope "test" if it's needed to write new unit-tests.

The exam includes 3 tasks to be done. Please, choose 3 of 5: [Calculator](/tasks/Calculator.md), [Pyramid](/tasks/Pyramid.md), [CurrencyConverter](/tasks/CurrencyConverter.md), [Spreadsheet](/tasks/Spreadsheet.md) and 
[Subsequence](/tasks/Subsequence.md)

### Result ###

* Author name : Ekaterina Mitiashkina
* Pipeline status: 
[![Project CI](https://github.com/algrince/javaschool_testtask/actions/workflows/maven.yml/badge.svg)](https://github.com/algrince/javaschool_testtask/actions/workflows/maven.yml)
* Completed tasks: Pyramid, CurrencyConverter, Subsequence


### How to start?  ###
* Install [GIT](https://git-scm.com/) and [Maven](https://maven.apache.org)
* [Fork](https://confluence.atlassian.com/bitbucket/forking-a-repository-221449527.html) the repository 
* You're ready to go!

### How can I submit the result?  ###

* You need to do 3 tasks out of 5, so you need to mark the 2 test classes with the annotation @Ignore to exclude them. This is the only change you can make to the test classes. Do not modify class methods. Example for excluding Calculator task: 
```java
import org.junit.Ignore;

@Ignore
public class CalculatorTest {
   ...
}
```
* Make sure your code can be built and all tests are green (example command: "mvn clean install")
* Commit and push all changes to your repository
* Configure the [Bitbucket Pipelines](https://support.atlassian.com/bitbucket-cloud/docs/get-started-with-bitbucket-pipelines/) to deploy your maven project(use maven template for creating your pipelines) 
* Add your name to the README.md under Result section
* Add a badge with a status of your pipeline under Result section using the next template: https://img.shields.io/bitbucket/pipelines/:userName/:repositoryName/:branchName
* Check that the latest pipeline shows green build. We will not accept your solution if it is red. Also check that all 33 tests are passed in the pipeline.
* Send us an email with the link to your repository. Be aware that the build must be green all the time after you send us the link

### Test fails but I'm sure my solution is correct. What to do?  ###
* First of all - **don't** modify the tests. It will be considered as fraud and treated accordingly
+ Send us an email with full details:
    * Link to your repository
    * Name of failing test
    * Explain what is expected behaviour from your point of view
    * Explain why do you think your version is correct
* Wait for the response

### Who do I talk to? ###

* In case of any questions contact the person who sent you the task (first) or HR department of T-Systems (second).

### Useful links ###

* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)
