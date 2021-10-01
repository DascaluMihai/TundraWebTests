# tundra-web-test-exercise

I created 5 automated Web tests which I have written in Java.
I also used Selenium, Maven and TestNG.

The tests were created by using a Page Object Model pattern, which allows to easily create more tests on different other pages in the application.

In order to run the tests :

1. Please download and install IntelliJ from here : https://www.jetbrains.com/idea/download/
2. Please download chromedriver for your version of browser and your operating system from here : https://chromedriver.chromium.org/downloads
3. Git clone this repository : git@github.com:DascaluMihai/TundraWebTests.git

Start IntelliJ and run the **testSuite.xml** file

Also, you can go in command line in the project directory and run : **mvn clean test -DsuiteXmlFile=testSuite.xml**

