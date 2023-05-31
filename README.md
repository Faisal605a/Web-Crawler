# Web-Crawler

You are given the source code for your company's legacy web crawler, which is single-threaded.
You notice it's a bit slow, and you quickly realize a way to improve its performance and impress your new manager.
You can upgrade the code to take advantage of multi-core architectures to increase crawler throughput.
Furthermore, you will measure the performance of your crawler to prove that, given the same amount of time, the multi-threaded implementation can visit more web pages than the legacy implementation.


Getting Started
Dependencies
Java JDK Version 17
Maven 3.6.3 or higher
IntelliJ IDEA
Installation
Download the JDK 17. I recommend JDK 17 since it is the latest long-term support Java version.
Accept the license agreements and run the downloaded installer.
Follow the official installation and run mvn -version in a terminal to make sure you have at least version 3.6.3 installed.
Download the Community Edition of IntelliJ IDEA. Run the downloaded installer.


Using the Terminal
In this project, you will be executing commands in a terminal to compile your code, run unit tests, and run the web crawler app. If you are using IntelliJ (recommended), you can use the built-in Terminal tab by clicking the "Terminal" button near the lower left corner of the main window, or by pressing the keyboard shortcut (Alt + F12 on Windows/Linux, or ⌥ + F12 on Mac).

If you already have another terminal application you like to use, such as the terminal that comes with the operating system you are running, that will also work with this project. If you go this route, make sure you run the terminal from the root directory of the project. In other words, the terminal's working directory should be the same folder where the pom.xml file is located.

Note: If you are using IntelliJ's Terminal tab, it automatically opens the terminal in the project's root directory.

Once you have a terminal open, make sure everything is working by typing (or copy-pasting) the following mvn command into the terminal and pressing the Enter key:
mvn test -Dtest=WebCrawlerTest -DcrawlerImplementations=com.udacity.webcrawler.SequentialWebCrawler
