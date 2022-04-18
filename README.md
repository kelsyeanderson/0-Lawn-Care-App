# Lawn Wizard
This app creates a crowdsourcing environment for local yard care work. In the app the user will be able to choose to either be a worker or a home owner. The home owners can create jobs and request work for their lawns which gets sent to the workers in the area. The workers can either accept or reject the job offers and get paid through the app. 

## Workspace layout
The Lawn Wizard android app will be stored in this repository.
The documentation and resources for this project will be kept in the "docs" folder. This includes use case diagrams, the project plan, database diagrams, and more as the project progresses.
The project will kept in the folder "Lawn Wizard".

## Version-control procedures
Collaborators should have a forked repository of the app in Kelsye’s account of the project "0-Lawn-Care-App", in their Github. Each collaborator should clone the forked repository.
Before each meeting, collaborators should submit a pull request so we can monitor progress and discuss issues.

## Tool stack description and setup procedure
Android Studio - a java editor that allows creation and posting of android apps.
Firebase - easy free database to use that connects to Android Stuido
Fireauth - easy free user authentication 

## Build instructions
To build and edit the app, simply run `bash $ git clone https://github.com/kelsyeanderson/0-Lawn-Care-App` to get a copy of the repository on your computer. You then just need to open the Android Studio app and click on the project to begin. Installation and set up instructions for Android Studio are located in the docs folder.

## Organization and Name Scheme
We will be using camel case for variables and upper camel case for class variables in our project. The individual pages will be fragments with their own XML files that have the necessary elements in them. 

## Unit testing instructions
The file unittests (located in docs/milestone3_deliverables) will walk you through different use cases and tell you what should happen.

## System testing instructions
Start by opening the app on your android device.
To test a worker profile use the following credentials:

Email:homeowner@example.com

Password: Password1!

To test a homeowner profile use the following credentials:

Email:worker@example.com

Password:Password1!

Email:admin@example.com

Password:Password1!

These credentials allow access to perform all actions as a customer and homeowner in a test environment.

## Milestone 3 Requirements
To make grading easier, we have added a milestone 3 deliverables folder within our docs file. In it we have all our sprint planning, sprint retrospective, and standup reports. Since Android Studio doesn't allow for code based unit tests the way a python program would, we have provided a unittests file that details tests you can run on the emulator to ensure that everything is working as it should be. We understand that we are the only group to impliment this app through an Android Application, so we've included instructions for installing and setting up Android Studio if you would like to run our code yourself. In the event that you run into trouble getting everything working, we've also provided a screen recording video demonstration of us walking through the different features in our app. We'd be happy to hop on a call with you or answer any questions you may have through discord if you run into any problems.


