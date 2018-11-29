# MVP Demonstration

*This section should contain a brief description and demo of product you have built.*

* *Include screenshots (actual screenshots, not mock ups) of all of the facets of the product working.*
* *Link consecutive screenshots together with a brief narrative explaining how the product works, e.g. a sentence "Click on OK and it takes you to the next window", "On starting the app, the following window is shown".  This document should not take you a long time to create - it's just pasting photos and adding explanatory sentences between them, so that your MVP is adequately described.*
* *Make sure an image of each window of your software is included - so that a person who has not seen the actual demo of the product has a good idea of what your product currently does.*
* *If your product is a physical device (a hardware product) then you may replace all of the above screenshots by actual photos/vidoes where you feel it is appropriate.*
* *Make sure this section includes any functionality/features you are particularly proud of.*
* *Indicate clearly which parts of the functionality shown in the screeshots are currently incomplete, and what is likely to change in the final version.  For example if a graph displayed is currently based on static hard-coded data for the MVP, but in the future version the graph will dynamically change depending on fresh data, then point this out explicitly.*


Our product has 2 tabbed panes, one for collecting necessary data from the user and one for displaying information to the user.
The first tab that will be shown is the input tab



###Input tab:

<img src = "images/MVPDemonstration_Image1.png" width = 600>
<img src = "images/MVPDemonstration_Image2.png" width = 600>
<img src = "images/MVPDemonstration_Image3.png" width = 600>

The input tab has a text field where the user inputs their bank balance and a button which will set the users balance to an instance variable.
When the button is pressed a message will be shown in the center panel telling the user if their input balance has been set.




<img src = "images/MVPDemonstration_Image4.png" width = 600>
<img src = "images/MVPDemonstration_Image5.png" width = 600>
<img src = "images/MVPDemonstration_Image6.png" width = 600>
<img src = "images/MVPDemonstration_Image7.png" width = 600>

The input tab also has two text fields where the user inputs the symbol of the company they have shares in and the amount of shares they have in that company.
There is a button which when pressed adds the company symbol input and the share amount input into a map instance variable. 
The map takes the company symbol as the key and the share amount as the value.




<img src = "images/MVPDemonstration_Image8.png" width = 600>
<img src = "images/MVPDemonstration_Image9.png" width = 600>
<img src = "images/MVPDemonstration_Image10.png" width = 600>

If there are 5 companies in the map and the user tries to enter another, an appropriate message will be shown, and the map with the 5 companies will be shown.





<img src = "images/MVPDemonstration_Image11.png" width = 600>
<img src = "images/MVPDemonstration_Image12.png" width = 600>

If the users input is invalid then an appropriate message will be shown


###Data tab:

<img src = "images/MVPDemonstration_Image13.png" width = 600>
<img src = "images/MVPDemonstration_Image14.png" width = 600>

On the data tab there is a "Total current value of investments" button. When it is pressed, the bank balance obtained from the input tab is added to the value of the users shares, which gives the total value of the users investments. The value of the users shares is obtained using the map from the input tab and the IEX trading API. Using the company symbols input by the user and the amount of shares they have in that company, the API is used to multiply the input share amount by the companies share value. The users share values for each company are added together with the bank balance and are shown to the user in an appropriate message in the center panel.

<img src = "images/MVPDemonstration_Image15.png" width = 600>
<img src = "images/MVPDemonstration_Image16.png" width = 600>

On the data tab there is also a text field where the user can input a date in the format dd/mm/yyyy. Once the user enters a date, the "Search" button can be pressed, and it will show what the users total investments would be on a specific date in the last 3 years. This is done by finding the difference in days between the current date and the input date. This can then be used with the IEX trading API to find any companies share value on a previous date. 

<img src = "images/MVPDemonstration_Image17.png" width = 600>
<img src = "images/MVPDemonstration_Image18.png" width = 600>