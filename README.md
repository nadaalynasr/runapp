# Term Project - Strupe

## Group ugrd-19

## Contributions

### Nada Aly-Nasr

Report: I spearheaded the report, writing up our problem statement, domain, coming up with the different interfaces we would need for the project.

UX/UI Design: I created the UI design for our application in Figma, laying out how each interface would look in the final product.

Project Setup: I did the Spring-Boot setup for the project, ensuring all dependencies aligned with those of Project 2.

Home Page: I implemented the RunService, UserService, Run model, User model, etc. to get the home page fully functional. It displays all users public runs.

Login Page/Authorization: Implemented the LoginController and recreated the WebConfig \& AuthInterceptor files from Project 2 for proper login, authentication, and authorization within our app.

Indexing: I added indexes to 2 tables within our app and tested the query duration/timing before and after adding to see if performance improved.

Generating Sample data: I implemented 3 python scripts to generate sample data to populate our app with.



<br>
### Andrea Ayon

Groups Page: I implemented the Groups Page, which shows two sections: the groups a user is already part of and the groups they can join. To build this page, I added the logic to load the user’s current groups, display all other available groups, and update the member count when someone joins or leaves. I also connected these actions to the buttons on each group card so the page responds immediately to user interaction. On the UI side, I designed the layout for this page and created a reusable group card component that we later used on the Trending Page, ensuring each group displays its name, member information, and next run details consistently.

Trending Page: I created the UI and layout for the Trending Page, setting up its structure and design. I reused the Groups Page Mustache components so the layout follows a similar format and keeps the app’s overall design consistent.

Login \& Registration Pages: I implemented the UI for the Login and Registration pages, giving them a clean layout and styling that matches the rest of the app. These pages provide a simple, user-friendly way for users to log in or create a new account.

### Emily Freeman

Log A Run Page: I implemented the Log A Run page that allows users to save their runs into their account. Each new run entry contains the duration of the run, the distance, and BPM. We assume the time of entry as the ending time, and calculate the start time ourselves. We also save the run's title in a similar way to Strava, where no title entered then defaults to a time-based default title. I implemented the UI for this page in a way that could be reused for the Create Group page.

### Angel Lopez

### Jamie Chen

Create Group Run page: In Create Group Run (CGR) page, users are able to schedule group runs by inputting the name, date, time, and max number of members for the group run. I implemented CGR page by building a controller that reuses existing UI fragments from Log a Run page to display the required CGR page design. I restricted user input for the date, time, and member max fields to ensure correct data format and more intuitive data inputs. I also ensured that the group creator is added as the first member.

Private feature: I generalized the share toggle from Log a Run page so that it could be reused as CGR page's private toggle. When a group run is private, only the creator can see it in the Groups page. If the creator leaves the group run, it is deleted from the database and disappears from Group page.

## Technologies Used

## Database Used

Database Name:
Database Username:
Database Password:

## Test Users

Username 1:
Password 1:

Username 2:
Password 2:

Username 3:
Password 3:

