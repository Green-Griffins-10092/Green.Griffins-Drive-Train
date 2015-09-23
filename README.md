# One Day Robot Code
This repository is a fork of the [ftc-app](https://github.com/ftctechnh/ftc_app) at commit 4e23900.
It contains an abstract opmode for the Green.Griffins; one day robot, and several opmodes for controlling the robot.
They rely on having the motors named "left_motor" and "right_motor" and an optical distance sensor called "distance_sensor" in the hardware map.
See our [twitter page](https://twitter.com/griffins10092) or our [facebook page](https://www.facebook.com/GreenGriffins10092-489387721245658/timeline/)
for the CAD model of the one day robot.
If you find any errors, please add an issue in the [git hub tracker](https://github.com/archerD/Green.Griffins-Drive-Train/issues/new).

```
To use this SDK, download/clone the entire project to your local computer.
Use Android Studio to import the folder  ("Import project (Eclipse ADT, Gradle, etc.)").

Documentation for the FTC SDK are included with this repository.  There is a subfolder called "doc" which contains several subfolders:

 * The folder "apk" contains the .apk files for the FTC Driver Station and FTC Robot Controller apps.
 * The folder "javadoc" contains the JavaDoc user documentation for the FTC SDK.
 * The folder "tutorial" contains PDF files that help teach the basics of using the FTC SDK.

For technical questions regarding the SDK, please visit the FTC Technology forum:

  http://ftcforum.usfirst.org/forumdisplay.php?156-FTC-Technology

In this latest version of the FTC SDK (20150803_001) the following changes should be noted:

 * New user interfaces for FTC Driver Station and FTC Robot Controller apps.
 * An init() method is added to the OpMode class.
   - For this release, init() is triggered right before the start() method.
   - Eventually, the init() method will be triggered when the user presses an "INIT" button on driver station.
   - The init() and loop() methods are now required (i.e., need to be overridden in the user's op mode).
   - The start() and stop() methods are optional.
 * A new LinearOpMode class is introduced.
   - Teams can use the LinearOpMode mode to create a linear (not event driven) program model.
   - Teams can use blocking statements like Thread.sleep() within a linear op mode.
 * The API for the Legacy Module and Core Device Interface Module have been updated.
   - Support for encoders with the Legacy Module is now working.
 * The hardware loop has been updated for better performance.


T. Eng
August 3, 2015
```
D. Flores
September 19, 2015
