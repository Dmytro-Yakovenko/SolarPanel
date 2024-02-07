Assessment03: Solar Farm Instructions
Have questions about this Reading? Visit the community here!
Assessment: Solar Farm
Introduction
A solar farm is a large collection of solar panels designed to generate electricity that is fed into the power grid. A solar farm generates utility level solar power.

Design, test, and build an application to track solar panels on a solar farm.

A solar farm near St. Cloud, MN

High Level Requirements
The user is a solar farm administrator.

Add a solar panel to the farm.
Update a solar panel.
Remove a solar panel.
Display all solar panels in a section.
Background
Solar panels are arranged in sections, rows, and columns. A panel can be uniquely identified with those three things.

The image below shows a farm with three sections. The Main section has three rows and four columns. The Upper Hill and Lower Hill sections each have two rows and four columns. Your solar farm will be configured by the administrator. Don't make assumptions about sections.

A solar farm with three sections: Main, Upper Hill, and Lower Hill. Panels are arranged in rows and columns

Panels
The application is only required to track solar panels. The concept of sections is not a separate class. It is a field in the solar panel class.

Data
Section: name that identifies where the panel is installed.
Row: the row number in the section where the panel is installed.
Column: the column number in the section where the panel is installed.
Year Installed
Material: multicrystalline silicon, monocrystalline silicon, amorphous silicon, cadmium telluride, or copper indium gallium selenide.
Is Tracking: determines if the panel is installed with sun-tracking hardware.
You may find it useful to add a unique identifier to each panel, though it's not required. Panels can also be uniquely identified by section, row, and column.

Rules
Section is required and cannot be blank.
Row is a positive number less than or equal to 250.
Column is a positive number less than or equal to 250.
Year Installed must be in the past.
Material is required and can only be one of the five materials listed.
Is Tracking is required.
The combined values of Section, Row, and Column may not be duplicated.
Sample UI
Start Up / Main Menu
Welcome to Solar Farm
=====================

Main Menu
=========
0. Exit
1. Find Panels by Section
2. Add a Panel
3. Update a Panel
4. Remove a Panel
Select [0-4]:
Find Panels by Section
Find Panels by Section
======================

Section Name: The Ridge

Panels in The Ridge
Row Col Year Material Tracking
  1   1 2014     CIGS      yes
  1   2 2014     GIGS      yes
  1   3 2015     GIGS      yes
  2   1 2018   PolySi       no
  2   3 2018   PolySi       no
Add a Panel
Add a Panel
===========

Section: Flats
Row: 251
[Err]
Row must be between 1 and 250.
Row: 250
Column: 43
Material: CdTe
Installation Year: 2020
Tracked [y/n]: n

[Success] 
Panel Flats-250-43 added.
Update a Panel
If you intend to use Section-Row-Column to uniquely identify a panel, you may not be able to update those fields. If original values change, they can't be used to find the old panel and update it. There are clever ways around the problem. Consider how you might solve it.

You are not required to update Section, Row, or Column, but you must allow editing of other fields.

Update a Panel
==============

Section: Treeline
Row: 10
Column: 5

Editing Treeline-10-5
Press [Enter] to keep original value.

Section (Treeline):
Row (10): 11
Column (5):
Material (CdTe):
Installation Year (2020):
Tracked (no) [y/n]: y

[Success] 
Panel Treeline-11-5 updated.
Remove a Panel - Success
Remove a Panel
==============

Section: Flats
Row: 50
Column: 50

[Success] 
Panel Flats-50-50 removed.
Remove a Panel - Failure
Remove a Panel
==============

Section: Flats
Row: 20
Column: 21

[Err] 
There is no panel Flats-20-21.
Technical Requirements
Start with a three-layer architecture. If you have a compelling reason to vary from the architecture, share it with your instructor and they will make a decision.

Data must be stored in a delimited file. Stopping and starting the application should not change the underlying data. The application picks up where it left off.

Repositories should throw a custom exception, never file-specific exceptions.

Repository and service classes must be fully tested with both negative and positive cases. Do not use your "production" data file to test your repository.

Solar panel material should be a Java enum with five values. Since solar technology is changing quickly, an enum may be a risky choice. The requirement is included specifically to practice with enums.

Approach
Plan before you write code.

Start with the model. Determine field data types and names.

Make a plan for each high-level scenario. Define the steps in detail. Identify which component (repository, service, controller, view) is responsible for each step.

Determine how each scenario is triggered from the main menu.

Consider sequence diagrams for insights about how components communicate and interact.

A flowchart may help refine decision and looping logic.

Stretch Goals
In the Find Panels by Section scenario, provide a list of existing sections to choose from.
Add search scenarios: find by installation year ranges, specific rows in sections, or specific materials.
If a panel is a duplicate, don't make the user re-enter all of the data. Instead, offer to let them change a row, column, or section to prevent the duplicate. (Maybe they mis-keyed one value.)
Add a bulk update feature.