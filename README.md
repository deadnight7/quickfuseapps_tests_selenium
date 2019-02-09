# QuickFuseApps tests in Selenium WebDriver
Automated tests in Selenium WebDriver for performing Drag and Drop operations on http://quickfuseapps.com/


![](./src/main/resources/drag_test.gif)
Video - https://www.youtube.com/watch?v=etYHZNVcdks

Steps to run locally

	
~~~~
$ git clone
Cloning into 'quickfuseapps_tests_selenium'...
remote: Total 115 (delta 13), reused 110 (delta 9), pack-reused 0
Receiving objects: 100% (115/115), 9.24 MiB | 1.37 MiB/s, done.
Resolving deltas: 100% (13/13), done.

$ cd quickfuseapps_tests_selenium/
~~~~
Ensure Maven is in the classpath 

~~~~
$ mvn clean test
~~~~
 
OR if Maven is not in the classpath; import the project in eclipse as a Maven Project > Run As > Maven Test 
