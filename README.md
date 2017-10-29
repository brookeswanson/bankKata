# KataBankOCR 
This is one solution to the coding [challenge](http://codingdojo.org/kata/BankOCR/) created by Emmanuel Gaillot and Christopher Thibaut.

#### User Story One:

Converting the characters printed from a machine in the below format:
```$xslt                          
  |  |  |  |  |  |  |  |  |
  |  |  |  |  |  |  |  |  |
```
to a matching account number (111111111)

#### User Story Two:

Verifying that the converted number is a valid account number using the formula
```$xslt
account number:  3  4  5  8  8  2  8  6  5
position names:  d9 d8 d7 d6 d5 d4 d3 d2 d1

checksum calculation:
(d1+2*d2+3*d3 +..+9*d9) mod 11 = 0
```

#### User Story Three:

Creating a print out of the converted account numbers including either ERR or ILL if the 
account number was invalid or one or more character was illegible.

# System Requirements
To build this project you need Java 8.0 (Java SDK 1.8) or better and [Maven 3.0](https://maven.apache.org/install.html)or better. 

Clone the repository 
```
 $ git clone git@github.com:bswanson3/bankKata.git
```

Navigate to the root directory of the project and in order to compile and run all tests run
the command 
```
 $ mvn install
```

The app will take one or 2 command line arguments:
1: the location of a file to read, *optional*: 2: The location to write to
```
mvn exec:java -Dexec.mainClass="net.bswanson.App" -Dexec.args="FILE_TO_READ FILE_TO_WRITE"
```

# Running the tests
To run all the tests navigate to the root directory and type
```
 $ mvn clean test
```

User Story 1 Tests
```$xslt
 $ mvn clean test -Dtest=UserStoryOneTest
```

User Story 2 Tests
```$xslt
 $ mvn clean test -Dtest=UserStoryTwoTest
```

User Story 3 Tests
```$xslt
 $ mvn clean test -Dtest=UserStoryThreeTest
```

# Built With
- [Maven](https://maven.apache.org/) dependency management

# Contact Me
- [Brooke Swanson](mailto:brookeswanson09@gmail.com)