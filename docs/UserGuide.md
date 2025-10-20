---
layout: page
title: User Guide
---

TutorFlow is a desktop app for tuition centre managers. It helps you keep track of students, parents, tutors, and classes using simple type-and-press-Enter commands.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Install Java `17` or newer on your computer.<br>
   **Mac users:** Follow the guide [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest app file (`.jar`) from [the Releases page](https://github.com/AY2526S1-CS2103T-T11-4/tp/releases).

1. Copy the file into a folder where you want your TutorFlow data to be saved.

1. Open a command window/terminal, go to that folder, and run: `java -jar tutorflow.jar`<br>
   The app window should appear with sample data so you can try things out.<br>
   ![Ui](images/Ui.png)

1. Type a command in the box and press Enter. For example, type **`help`** and press Enter to open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add c/student n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a student named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: How to read command formats**<br>

* Words in `UPPER_CASE` are things you need to type in. <br>
  For example, in `n/NAME`, you replace `NAME` with the actual name: `n/John Doe`.

* Square brackets mean optional parts. <br>
  Example: `n/NAME [t/TAG]` can be `n/John Doe t/friend` or just `n/John Doe`.

* `…` means you can repeat that part multiple times (including zero). <br>
  Example: `[t/TAG]…` can be left out, or used like `t/friend`, or `t/friend t/family`.

* Extra text for commands that don't take inputs (like `help`, `list`, `exit`, `clear`) will be ignored. <br>
  Example: `help 123` is treated as `help`.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

• [Back to Command Summary](#command-summary)



### Adding a person: `add`

Adds a person to the address book.

Format: `add c/CATEGORY n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add c/student n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add c/tutor n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/GP`

• [Back to Command Summary](#command-summary)

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

• [Back to Command Summary](#command-summary)

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [c/CATEGORY] [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

• [Back to Command Summary](#command-summary)

### Linking a student to a parent : `linkParent`

Links an existing student to an existing parent in the address book.

Format: `linkParent n/STUDENT_NAME n/PARENT_NAME`

* Links the student identified by STUDENT_NAME to the parent identified by PARENT_NAME.
* Both the student and the parent must already exist in the address book.
* The names must be an exact match to the names stored in TutorFlow.
* The person identified as the student must have the 'student' category, and the person identified as the parent must have the 'parent' category.

Example:
* `linkParent n/Alice Pauline n/Daniel Meier` Links the student 'Alice Pauline' to the parent 'Daniel Meier', assuming both exist in the address book with the correct categories.

• [Back to Command Summary](#command-summary)

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>

  ![result for 'find alex david'](images/findAlexDavidResult.png)

• [Back to Command Summary](#command-summary)

### Finding a student's parent: `getParent`

Displays the parent of a specified student.

Format: `getParent n/STUDENT_NAME`
* The student must already exist in the address book.
* The student name must be an exact match to the name stored in TutorFlow.
* The student identified must have the 'student' category.

Examples:
* `getParent n/John Doe` shows the parent of student John Doe.

• [Back to Command Summary](#command-summary)

### Finding all students of a tutor: `getStudents`

Displays all students of a specified tutor.

Format: `getStudents n/TUTOR_NAME`
* The tutor must already exist in the address book.
* The tutor name must be an exact match to the name stored in TutorFlow.
* The tutor identified must have the 'tutor' category.

Examples:
* `getStudents n/Roy Balakrishnan` shows all students of  tutor Roy Balakrishnan.

• [Back to Command Summary](#command-summary)

### Creating a class time: `createClass`

Creates a new class time in the system. Create this first before linking a tutor and students to it.

Format: `createClass DAY TIME`

* `DAY` must be one of `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, `SUNDAY`.
* `TIME` must be a supported timeslot (e.g. `0800`, `1000`, `1400`, `1600`).
* The class is created without linked persons. Link a tutor/students using `linkClass`.

Examples:
* `createClass MONDAY 1600` creates a class on Monday at 4:00 PM.
* `createClass SATURDAY 1000` creates a class on Saturday at 10:00 AM.

See also: [`linkClass`](#linking-a-person-to-a-class-time-linkclass), [`getClassDetails`](#viewing-class-details-getclassdetails) 

• [Back to Command Summary](#command-summary)

### Linking a person to a class time: `linkClass`

Links an existing student or tutor to an existing class time.

Format: `linkClass DAY TIME n/NAME`

* `NAME` must exactly match a person in TutorFlow.
* Students can join only one class time.
* Tutors can teach multiple class times.
* The class identified by `DAY` and `TIME` must already exist (created using `createClass`).

Examples:
* `linkClass MONDAY 1600 n/Roy Balakrishnan` links tutor Roy Balakrishnan to the Monday 4:00 PM class.
* `linkClass SATURDAY 1000 n/Alice Pauline` links student Alice Pauline to the Saturday 10:00 AM class.

See also: [`createClass`](#creating-a-class-time-createclass), [`unlinkClass`](#removing-a-person-from-a-class-time-unlinkclass), [`getClassDetails`](#viewing-class-details-getclassdetails) 

• [Back to Command Summary](#command-summary)

### Removing a person from a class time: `unlinkClass`

Removes the person from the class time.

Format: `unlinkClass DAY TIME n/NAME`

* `NAME` must exactly match a person currently linked to the class.
* The class identified by `DAY` and `TIME` must already exist.

Examples:
* `unlinkClass MONDAY 1600 n/Roy Balakrishnan` removes tutor Roy Balakrishnan from the Monday 4:00 PM class.
* `unlinkClass SATURDAY 1000 n/Alice Pauline` removes student Alice Pauline from the Saturday 10:00 AM class.

See also: [`linkClass`](#linking-a-person-to-a-class-time-linkclass), [`getClassDetails`](#viewing-class-details-getclassdetails) 

• [Back to Command Summary](#command-summary)

### Viewing class details: `getClassDetails`

Shows the tutor (if any) and students (if any) linked to the class time you specify.

Format: `getClassDetails DAY TIME`

* `DAY` must be one of `MONDAY` to `SUNDAY`.
* `TIME` must be a supported timeslot.

Examples:
* `getClassDetails MONDAY 1600`
* `getClassDetails SATURDAY 1000`

See also: [`linkClass`](#linking-a-person-to-a-class-time-linkclass), [`getClasses`](#listing-classes-getclasses) 

• [Back to Command Summary](#command-summary)

### Listing classes: `getClasses`

Shows classes. If you add a tutor name, it shows only that tutor’s classes.

Format: `getClasses [n/TUTOR_NAME]`

* Without `n/TUTOR_NAME`: shows all classes in the system.
* With `n/TUTOR_NAME`: shows only classes linked to the specified tutor.

Examples:
* `getClasses` shows all classes.
* `getClasses n/Roy Balakrishnan` shows classes linked to tutor Roy Balakrishnan.

See also: [`getClassDetails`](#viewing-class-details-getclassdetails), [`linkClass`](#linking-a-person-to-a-class-time-linkclass) 

• [Back to Command Summary](#command-summary)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

• [Back to Command Summary](#command-summary)

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

• [Back to Command Summary](#command-summary)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

• [Back to Command Summary](#command-summary)

### Saving the data

TutorFlow data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

TutorFlow data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, TutorFlow will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause TutorFlow to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TutorFlow home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action | Format, Examples
--------|------------------
[Add](#adding-a-person-add) | `add c/CATEGORY n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add c/parent n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
[Clear](#clearing-all-entries--clear) | `clear`
[Delete](#deleting-a-person--delete) | `delete INDEX`<br> e.g., `delete 3`
[Edit](#editing-a-person--edit) | `edit INDEX [c/CATEGORY] [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
[Link Parent](#linking-a-student-to-a-parent--linkparent) | `linkParent n/STUDENT_NAME n/PARENT_NAME`<br> e.g., `linkParent n/Alice Pauline n/Fiona Kunz`
[Find](#locating-persons-by-name-find) | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
[Get Parent](#finding-a-students-parent-getparent) | `getParent n/STUDENT_NAME`<br> e.g., `getParent n/John Doe`
[Get Students](#finding-all-students-of-a-tutor-getstudents) | `getStudents n/TUTOR_NAME`<br> e.g., `getStudents n/Roy Balakrishnan`
[Create Class](#creating-a-class-time-createclass) | `createClass DAY TIME`<br> e.g., `createClass MONDAY 1600`
[Link Class](#linking-a-person-to-a-class-time-linkclass) | `linkClass DAY TIME n/NAME`<br> e.g., `linkClass MONDAY 1600 n/Roy Balakrishnan`
[Unlink Class](#removing-a-person-from-a-class-time-unlinkclass) | `unlinkClass DAY TIME n/NAME`<br> e.g., `unlinkClass MONDAY 1600 n/Alice Pauline`
[Get Class Details](#viewing-class-details-getclassdetails) | `getClassDetails DAY TIME`<br> e.g., `getClassDetails MONDAY 1600`
[Get Classes](#listing-classes-getclasses) | `getClasses [n/TUTOR_NAME]`<br> e.g., `getClasses n/Roy Balakrishnan`
[List](#listing-all-persons--list) | `list`
[Help](#viewing-help--help) | `help`
