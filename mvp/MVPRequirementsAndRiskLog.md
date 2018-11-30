# Requirements and Risk Log


# Requirements



### User Stories:

**[Story 1](https://cseejira.essex.ac.uk/browse/CE291T21-76)**
* **Summary:** As Jennifer, I want to be able to view the current total value of my investments, so that I can see whether my investments are doing well.  
* **Description:** For this story to be completed, the application needs a text field for the user to input their bank balance and a button to set it to a variable. It also needs 2 other text fields for the user to enter the companies they have shares in, and the amount of shares they have with those companies. The text fields must then have a button to set the input company and the input share amount to a Map variable, with the input company as the key and the input share amount as the value. A way of accessing the current share prices for the companies in the map is needed, so that the share amount can be multiplied by the prices. The bank balance and total value of all of the shares will then need to be added and shown on a center panel.  

**[Story 2](https://cseejira.essex.ac.uk/browse/CE291T21-74)**
* **Summary:** As Janice, I want to be able to input a date in the last 3 years and view the total value of my current investments at that time, so that I can see how the value of my total investments have fluctuated over time.  
* **Description:** For this story to be completed, the application will need a text field for the user to input a date. It will also need a button with an action listener, which when pressed will show what the user's investments are worth at that time in a center panel. For this to work, a way of accessing share prices from different companies on different dates is needed.  

**[Story 3](https://cseejira.essex.ac.uk/browse/CE291T21-77)**
* **Summary:** As Mark, I want to be able to view my investments in a tabular format, so that I can see a visual representation of my shares and see how their values differ.  
* **Description:** For this story to be completed, the application needs to have a JTable in a center panel. This will hold details of the users bank balance, each company the user has shares with, the amount of shares the user has with those companies, the values of the shares and the total value of all of the users investments.  

**[Story 4](https://cseejira.essex.ac.uk/browse/CE291T21-75)**
* **Summary:** As Paul, I want the application to be user-friendly, so that it is quick and easy to view the value of my current investments on any date I input.  
* **Description:** For this story to be completed, the application needs to have a simple layout which is easy to navigate. It should have well labelled components (e.g. buttons, text fields and tabs) so the user knows how to use everything.  


### Functional Requirements:

* The application GUI will be developed using Java Swing.
* The application will include the iexj4 Library to access data from IEX Trading.
* The application should be able to show the total value of the users investments (bank balance + shares).
* The application should allow the user to input a date within the last 3 years and view the value of their total investments at that time.
* The application should be able to take information about company shares from CSV files to be used to display the users total investment value.
* The application should be able to create a table based on the total value of the users investments at a given time.
* The application should allow the user to input their their bank balance, the companies they have shares with and the amount of shares they have with each company.
* The application should allow the user to enter in up to 5 companies and no more.
* The application should include validation and exception handling for all user input fields to prevent incorrect data formats used.
* The application shall calculate the total value of the client's investments using the formula (x*y) where x is the number of shares and y is the value of a single share.


### Non-Functional Requirements:

* The applications code should be commented, so it is easy to maintain and fix bugs.
* The application should be user friendly (easy to learn and understand).
* The application should have a fast response time (below 1 second).
* The application should follow a clear structure to allow other developers to easily understand what the code does.
* The application must be written in Java.


### Risk Log:


**[Risk 1: Missing Team Member](https://cseejira.essex.ac.uk/browse/CE291T21-61)**  
* **Summary:** Our 6th team member 'Domantas' has failed to attend all scrum meetings bar the first. He has not communicated with the team since that meeting nor has he completed any of his assigned tasks. His status is unknown.
His absence may have an impact on the overall project completion speed.  
* **Description:** A risk was created to document the disappearance of our 6th team member 'Domantas'. Prior to the creation of this risk, he had only attended the first meeting with failure to communicate on his status ever since. The risk was not assigned to anyone but the supervisor was informed who then made several attempts to reach out to Domantas. Domantas was eventually replaced with a new 6th team member: Timotheos.

**[Risk 2: Change management overload](https://management.simplicable.com/management/new/130-project-risks)**  
Link leads to helpful website
* **Summary:** There was miscommunication between team members which lead to various versions of code being overwritten.
* **Description:** This increased the complexity of aligning our coding ideas together and sacrificing time to re-implement a working version, but not to the point where there would be a stakeholder conflict.

**[Risk 3: Learning curves lead to delays and cost overrun]**  
* **Summary:** All team members were still in the progress of learning skills to complete the project which in turn introduced low productivity.
* **Description:** All team members were in the process of learning Java coding skills (for example IEX documentation), understanding interfaces with Jira and connecting IntelliJ with GitLab.

**[Risk 4: Decisions are low quality]**
* **Summary:** Decisions were vague.
* **Description:** Scrum masters had difficulties assigning tasks which covered every aspect of what needed to be done, and uneven workloads to team members.
