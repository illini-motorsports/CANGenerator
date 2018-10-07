# CANGenerator

This repository contains a program to generate various parsing functions, DBC files, and documentation from a JSON CAN specification.

# Generating a Jar File

## Prerequisites

* [Java 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Apache Maven](https://maven.apache.org/download.cgi)
* All other dependencies should be automatically pulled in by Maven!

## Build steps

* Make sure the Repository is at its latest revision
* Navigate to the root of the project (where the pom.xml is)
* Run the command:
```
mvn clean install
```
  which will build the project, run the tests, and package a jar file (This is untested on windows, but I believe it should work fine)
* You can now use the jar file found in the target directory on any computer with java installed!
* For this program to actaully be useful, you will also need the
```
can_spec.json
```
file located at the root directory of the project somewhere on your computer, so you can navigate to it with the CanGenerator's file explorer

## License
```
The MIT License (MIT)

Copyright (c) 2017 Illini Motorsports

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
