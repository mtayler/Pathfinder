Pathfinder
==========

Pathfinding algorithms using processing to draw to screen

Usage:
------
###Running the .jar
Navigate to <project_root>/out/artifacts/Pathfinder_jar  Run the jar file

###Running the source
Download the Processing application, and add the core.jar library to the project.

To add an algorithm:
--------------------
Create a new class extending Algorithm in the algorithms package, add an enum, which will display as the name in the program, to the AlgorithmType Enum file, and add the enum type and class instantiation to Pathfinder.java's AlgorithmType enum switch statement.
