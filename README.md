# Gps

Project is still on the developing stages, and will roll on until about January 2019 ,
As it is a part of Ariel University's OOP course exercise.

Main repository for exercise: https://github.com/benmoshe/OOP_EX2-EX4


Description as appears in Readme at above link, at least for current writing time of this text:
# OOP_EX2-Ex4
This Github project is an educational example for using git & github
as part of the Object-Oriented Programming course in Ariel University.

The main focus of this project is to allow an online suppoer of
Ex2, Ex3, Ex4 which are all related to a GIS like system.

# What is the azimuth?
By the US Army definition, the term azimuth describes the angle created by two lines:
one joining your current position and the North Pole, and the one joining your current position and the distant location.

Azimuth is always measured clockwise.

For example, a point lying east from you would have an azimuth of 90°, but a point lying west from you - of 270°.

![understanding azimuth and elevation photopills](https://user-images.githubusercontent.com/44743734/49344225-f0bb8980-f67c-11e8-9614-0828eff80506.png)

Calculate Azimuth and Distance for any two coordinates online here: https://www.omnicalculator.com/other/azimuth


Current repository is updated to Ex2, currently supports multicable coordinates calculations.

Also, within features,
you can use this code to convert an Excel files (in a .csv format)
into a GIS layer, and writing it into a Kml file type.

The received Kml represents a collection of GIS layers, each created from a .csv file

Implement the conversion yourself!!!

Select your folder that contains your csv files, as shown in pictures
![csvcontent](https://user-images.githubusercontent.com/44743734/49344216-eac5a880-f67c-11e8-84c9-207f61055e3e.PNG)
Enter the full path of your selected folder to the Gis_project creation method
Initialize to the KmlWrriter
Done! You can now view your own Kml file on Google Earth.

![kmlonge](https://user-images.githubusercontent.com/44743734/49344222-f022f300-f67c-11e8-8982-866525d26479.PNG)

To create Kml files I used JAK external library: https://labs.micromata.de/projects/jak/quickstart.html  

![hello kml quickstart micromata labs](https://user-images.githubusercontent.com/44743734/49344221-eef1c600-f67c-11e8-9881-f95a4237d005.png)


# Class diagram for Ex_2


![diagram](https://user-images.githubusercontent.com/44743734/49344217-ec8f6c00-f67c-11e8-8f2d-861d2f233bb3.png)
