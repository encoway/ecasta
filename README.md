# ecasta

Documentation
-------------
* [Motivation](#Motivation)
* [Requirments](#Requirments)
* [Build](#Build)
* [How-To](#How-To)

## Motivation
ecasta - encoway cucumber and selenium test assistant - is a tool that makes running cucumber tests easier. 
This tool was created as part of my professional training, which I completed in encoway in 2015.
The idea behind ecasta is to make it possible to run cucumber and selenium tests outside the development environment, so that developer and especially customer have the opputunity to test their web applications.

Every project has to pass the quality assurance and those projects who uses cucumber and selenium have usually an automated process to run the tests.
But when a customer want to test the application, so he has to create the same enviroment like developers do or he has to test it manually.

## Requirments
To run ecasta there is nothing to install just download the zip file and unzip it and run it by clicking on the run.bat file. For starting tests see [How-To](#How-To).

## Build

To build the application from the source code just run mvn package and it will create a tow jars (with and without dependencies) and a zip bundle see [Zip skeleton](#Zip skeleton).

### Zip skeleton
    
    ecasta
        |
        |__application
        |       |
        |       |__lib
        |       |   |
        |       |   |__custom_jar
        |       |   |
        |       |   |__testsystems
        |       |   
        |       |__log    
        |       |
        |       |__ecasta-1.0.jar
        |
        |__java
        |
        |__run.bat   
 
## How-To:
You have already implemented the pages and steps for running cucumber tests with selenium. You have to build a jar containing all pages and steps but first at all you have to make some changes to get the URL which has been passed in ecasta. 

To get the URL (protocol://host:port/context) there are four system properties which you have to read:

* **application.local.protocol** to get the protocol
* **application.local.host** to get the host
* **application.local.port** to get the port
* **application.local.context** to get the context

Secondly put the jar with the pages and steps into **_custom_jar_** folder and start ecasta. 

Finally add a new testsystem and load the feature files.

Now you are ready to run a test.