# Notes 
This is an app for creating, storing and managing your notes on your Android phone.


## Idea
I created this app to store my own random thoughts I came along in everydays life.
While programming the app I imagined it also to give you an overview of your tasks.
The features-part of this readme shows current state of the application and what I planned to do in the future.


## Features
1 storing your notes
-[x] database
-[ ] sync database/data with cloud
-[ ] encryption of data - currently not my focus, may be coming later when everything else is working
-[x] user should be able to delete datasets
-[ ] give user an overview of all taken notes of any kind
2 note types you may be able to take
  1 text
-[x] writing notes
-[x] modify taken notes
  2 images
-[x] taking an image with the phones camera
-[ ] selecting an already taken image
-[ ] modify the image (draw on it, mark special things as important, etc)
  3 scetches
-[ ] draw little scetches on ideas that are important to you and easier to express in scetches
3 properties of notes
-[ ] mark as task
-[ ] set an expiration date -> task has to be done till then
-[ ] notification if task is not done, but due in the next period of time [set by user]
-[x] set a category for the notes so you are able to see important things on a glance
-[x] priority so you are able to see important things on a glance
-[x] mark tasks/notes as done
-[ ] archive done tasks/notes
  1 categories
-[x] set/create your own categories, they are also stored in a database
  2 notification
-[x] create notification
-[ ] notify user when task is due
-[ ] tell user the title and short preview of note 
-4 communication between different applications
-[ ] share your notes
-[ ] recieve share from different applications to e.g. save a link to a website, etc.


## known, unsolved bugs

## TODOs/issues
-[ ] save changed finished-status on user interaction with notification
-[ ] analyze database update behavior, some updates may be unnecessary
-[ ] implement user-interaction into overview of notes - especially touch-motion gestures (deleting, archiving, etc)
-[ ] taking pictures - look for orientation of different devices. default orientation is +90 degrees as it happens to fit with my phone
-[ ] designing the notification (espeically creating new, sharper icons)
-[ ] comment the code
-[ ] optimize database-queries, to get the note use one query to join notes.db and category.db and not two seperate queries
-[ ] translate into different languages

## History/changes
1.  Jun 2016 - Initial commit
...
31. Jul 2016 - writing this readme.md






