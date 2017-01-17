
# Version as of January 17th 2017. 

NOTICE: this version is without images and audio meant for languages and the ENGLISH database. The audio, images for both languages and Swahili Database put in auplimental/content/*****  folder at https://et4d.app.box.com commit folder divided into separate folders per language

#Issues still to be addressed in next few weeks are:
1.	AppProvider.java in addressing a database issue that while the code works the way it is calling that database and not properly closing it causes rare and random application crashes due to database locked. I began to look at it in a version of the code that is not being submitted now. 
2.	AppProvider.java. This class is far too large and needs to be divided up into a few different child classes.
3.	cleaning up the code and adding more comments to make it a little clearer for someone else.
4.	Launch_Platform.java. This class needs to be divided into various fragments for the different parts (sections, videos, awards, settings). In particular, features that will be added to the final version of the settings page to get the users process. Such as along input of user’s name and allowance for different users.

#Issue to get after first update:
1.	Get Application to have both languages and others in the future in one application folder and not copying and pasting when changing languages. The goal is to allow more than one version of the application based upon the different languages to be on any device.  aka have English and Swahili on same device but as different applications.
2.	Cleaning all classes to get rid of unnecessary warnings. 
3.	Find any layouts that needed to be cleaned up and thus associated classes that may or may not need use of fragments. 



_______________________________________________________________________________________________________________________________________




# GLEXP-Team-eafa

Still to be Added:
awards page
numbers, math and write sections
settings page
database update with version number change if needed but keeping users info
ablity to get csv/database of users success

2015-11-12 commit:
a good bit of additions were added since first two commits. Almost every activates up to the words has been added. 
WD06 still needs to be added in words.
There are some warnings dealing with @NotNull in regards to the Uri in the content provider.
Also warning  Unchecked cast: 'java.lang.Object' to 'java.util.ArrayList<java.lang.String>'dealing with Tag/Objects that were ArrayList at first then object and now going back as ArrayList that will be looked at
Format of looping through cursor needs to be redone to test If(cursor.moveToFirst()) before looping and most loops need to be be do{ /*Code*/ }while(cursor.moveToNext());
some logs and comments and error checking needs to still be added in entirety.


