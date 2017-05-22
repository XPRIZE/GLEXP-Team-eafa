# Update as of May 22nd 2017

1. Fixed issue with missing background in a phonics game (sd03)
2. Fixed issue with moving ahead in setting for lower/upper case letters (lt0*) that all
    previous activities not matched as complete causing uppercase to be stuck
3. Added for exiting application icons (green to stay, red to leave) to replace former dialog
    with words
4. A spelling activity has been placed in the books section that is only available until 
    words are reached
5. Added a calculator activity in math operations replace a duplicate addition activity. 
6. Some more comments were added
7. Added Fabric plugin to allow tracking of crashes
    


Currently working on settings page to allow childs name and data to be sent in CSV form to ET4D 
and any people wanting it. This is also fixing up the Launch_Platform page in order to 
accommodate fragments to allow cleaner code.

Also am doing more work on finding any memory leaks

# Version as of April 26th 2017

1. Fixed issue with Counting Syllable activities (CS01,CS02)
2. Fixed issue with math operations to make it move to next level more smooth.
3. Cleaned up books to only show book title in text and make sure book is not available until
    user finishes related levels.
4. Fixed other  random bugs

April 20th actions still need to be addressed have begun commenting code to make more sense.

# Version as of April 20th 2017

1. Removed majority of warnings in code. The warning left I am still researching the best
    way to handle them.
2. Cleaned code to align and not let line be longer then they should for easier writting
3. ReDid comments in Motoli_Application
4. Still needs more commenting
5. A few classes are still a little too large and most be reduced.
6. Launch_Platform.Java into fragments still to be done
7. Garbage Collection is going to be look at sometime next week to make sure there are no issues


# Version as of April 17th 2017

1. Nested Layouts have now been removed.
2. Editing for  Shape (SH) activities done due to getting rid of nested layout
3. Taken care of getting books to not appear until after related level has been finished
4. Still needs more commenting
5. A few classes are still a little too large and most be reduced.
6. Launch_Platform.Java into fragments still to be done
7. Garbage Collection is going to be look at sometime next week to make sure there are no issues




# Version as of April 4th 2017

1. Database issue in AppProvider.java was corrected
2. AppProvider.java was shrunk somewhat into a couple of different classes/files.
    However more still needs to be done.
3. NestedLayouts are currently being addressed and taken care of.
    All layouts are changing to ConstraintLayout. About 50% are done in this update.
4. More comments needs to still be address5. 
5. A few classes are still a little too large and most be reduced.
6. Launch_Platform.Java into fragments still to be done
7. In the Math section extra activity_op2.java has been hidden and not in use with activity_op8
    being the first activity now. It is nothing more then a simple calculator.
8. Garbage Collection is going to be look at sometime next week to make sure there are no issues




# Version as of January 17th 2017. 

NOTICE: this version is without images and audio meant for languages and the ENGLISH database.
The audio, images for both languages and Swahili Database put in auplimental/content/*****
folder at https://et4d.app.box.com commit folder divided into separate folders per language

#Issues still to be addressed in next few weeks are:
1.	AppProvider.java in addressing a database issue that while the code works the way it is
    calling that database and not properly closing it causes rare and random application crashes
    due to database locked. I began to look at it in a version of the code
    that is not being submitted now.
2.	AppProvider.java. This class is far too large and needs to be divided up into a few
    different child classes.
3.	cleaning up the code and adding more comments to make it a little clearer for someone else.
4.	Launch_Platform.java. This class needs to be divided into various fragments for
    the different parts (sections, videos, awards, settings).
    In particular, features that will be added to the final version of the settings page to get
    the users process. Such as along input of user’s name and allowance for different users.

#Issue to get after first update:
1.	Get Application to have both languages and others in the future in one application folder
    and not copying and pasting when changing languages.
    The goal is to allow more than one version of the application based upon
    the different languages to be on any device.  aka have English and Swahili on same device
    but as different applications.
2.	Cleaning all classes to get rid of unnecessary warnings. 
3.	Find any layouts that needed to be cleaned up and thus associated classes that may or may
not need use of fragments.



_______________________________________________________________________________________________________________________________________




# GLEXP-Team-eafa

Still to be Added:
awards page
numbers, math and write sections
settings page
database update with version number change if needed but keeping users info
ablity to get csv/database of users success

2015-11-12 commit:
a good bit of additions were added since first two commits.
 Almost every activates up to the words has been added.
WD06 still needs to be added in words.
There are some warnings dealing with @NotNull in regards to the Uri in the content provider.
Also warning  Unchecked cast: 'java.lang.Object' to 'java.util.ArrayList<java.lang.String>'
dealing with Tag/Objects that were ArrayList at first then object
and now going back as ArrayList that will be looked at
Format of looping through cursor needs to be redone to test If(cursor.moveToFirst())
before looping and most loops need to be be do{ /*Code*/ }while(cursor.moveToNext());
some logs and comments and error checking needs to still be added in entirety.


