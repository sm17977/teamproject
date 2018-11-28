# Requirements and Risk Log

*In this markdown document, list all of the requirements that you have identified for your product so far.*

# Requirements

*List several user stories (no more than 8) that describe the product requriements. For each user story that you include:*

* *Include a URL link to the Jira user-story issue.*
* *Paste in the Story Description and Story Summary for Jira.  Attempt to keep story summaries in the form taught in the lectures, i.e. the form "As a [persona], I [want to], [so that]".  Feel free to update your Jira issues into this form if they are not already.*
* *Give details of whether this story has been assigned to someone yet, and whether it is completed yet.*
* *Include a screenshots of any relevant attachments.*

*In addition to user stories (which is the main thing we are trying to teach for agile), try to include one or two other requirement modelling techniques, e.g. as listed in the lecture.*

*Try to group the requirements under two sub-headings: functional requirements, and non-functional requirements.*

*When marking this section we will be looking to see your team has understood the correct way to represent User-Stories and one or more other requirement modelling methods, and the requirements listed are give as much relevant information for the development team as possible.*


### User Stories:

[Story 1](https://cseejira.essex.ac.uk/browse/CE291T21-76)  
**Summary:** As Jennifer, I want to be able to view the current total value of my investments, so that I can see whether my investments are doing well.  
**Description:** For this story to be completed, the application needs a text field for the user to input their bank balance and a button to set it to a variable. It also needs 2 other text fields for the user to enter the companies they have shares in, and the amount of shares they have with those companies. The text fields must then have a button to set the input company and the input share amount to a Map variable, with the input company as the key and the input share amount as the value. A way of accessing the current share prices for the companies in the map is needed, so that the share amount can be multiplied by the prices. The bank balance and total value of all of the shares will then need to be added and shown on a center panel.  

[Story 2](https://cseejira.essex.ac.uk/browse/CE291T21-74)  
**Summary:** As Janice, I want to be able to input a date in the last 3 years and view the total value of my current investments at that time, so that I can see how the value of my total investments have fluctuated over time.  
**Description:** For this story to be completed, the application will need a text field for the user to input a date. It will also need a button with an action listener, which when pressed will show what the user's investments are worth at that time in a center panel. For this to work, a way of accessing share prices from different companies on different dates is needed.  

[Story 3](https://cseejira.essex.ac.uk/browse/CE291T21-77)  
**Summary:** As Mark, I want to be able to view my investments in a tabular format, so that I can see a visual representation of my shares and see how their values differ.  
**Description:** For this story to be completed, the application needs to have a JTable in a center panel. This will hold details of the users bank balance, each company the user has shares with, the amount of shares the user has with those companies, the values of the shares and the total value of all of the users investments.  

[Story 4](https://cseejira.essex.ac.uk/browse/CE291T21-75)  
**Summary:** As Paul, I want the application to be user-friendly, so that it is quick and easy to view the value of my current investments on any date I input.  
**Description:** For this story to be completed, the application needs to have a simple layout which is easy to navigate. It should have well labelled components (e.g. buttons, text fields and tabs) so the user knows how to use everything.  


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


### Risk Log

*In this section list the risks you have identified for your project.  For each Risk identified:*

* *Include a URL link to the Jira risk issue.* 
* *Paste in the Risk's Description and Summary from Jira.  Also state the Impact and Likilihood.*
* *Give details of whether this story has been assigned to someone yet, and whether it is completed yet.*
* *Include, from the Jira description / comments, details of what mitigating actions are being taking and by whom.*

*When marking this section we will be looking to see several realistic risks have been noted, and are actively being tracked and mitigated against.*

**[Risk 1] Missing Team Member (https://cseejira.essex.ac.uk/browse/CE291T21-61)**  
**Description:** Our 6th team member 'Domantas' has failed to attend all scrum meetings bar the first. He has not communicated with the team since that meeting nor has he completed any of his assigned tasks. His status is unknown.

His absence may have an impact on the overall project completion speed. 
**Summary:** A risk was created to document the disappearance of our 6th team member 'Domantas' who had only attended the first meeting and not communicated since. The risk was not assigned to anyone but the supervisor was informed who then made several attempts to reach out to Domantas. Domantas was eventually replaced with a new 6th team member: Timotheos.
