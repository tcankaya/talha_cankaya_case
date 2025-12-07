**Tech Stack**
	
•	Java
•	Selenium 4
•	TestNG
•	WebDriverManager
•	Log4j2
•	AssertJ

**Configuration**

Test data and environment settings live in:
`src/main/resources/config.properties
`

**Running the Tests**

The suite is driven by:
`src/test/resources/testng.xml
`

**Logs & Screenshots**

Log output is written into:
`reports/test.log
`

On failures, screenshots are saved to:
`reports/screenshots/
`

**Page Object Model**

UI behavior and locators stay inside page classes
Tests remain readable and assertion-focused

**Wait Strategy**

The project uses custom wait utilities rather than raw sleeps.

**Filter Stability**

Department/Location selection includes a lightweight retry approach
because the Select2 list sometimes requires a close+open cycle to populate.

**How to Extend**

Add new tests by:
1.	Creating a Page method in:
`src/main/java/pages
`	

2. Calling it from:
`src/test/java/tests
`
