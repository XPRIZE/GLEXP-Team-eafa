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


