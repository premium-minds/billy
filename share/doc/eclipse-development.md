# Eclipse Development Install and Config Instructions

## Eclipse Install

Install Eclipse for Java EE (Previously tested with: `Neon.3 Release (4.6.3)`)  
https://www.eclipse.org/downloads/eclipse-packages/  
Configure the worspace, prespectives, and views to your liking  
  
Edit `eclipse.ini` and increase the values of the java args -Xms and -Xmx  
 - Check available memory on your system to decide the values  
 - Xms = Initial memory pool, recommended: 512m or more  
 - Xmx = Maximum memory pool, recommended: 2048m or more  
  
## Global Preferences

##### Recommended
  
`Window` > `Preferences`: Search for "text editors"  
 - Enable `Show print margin`  
  
`Window` > `Preferences`: Search for "spelling"  
 - Disable `spell checking`  
(No annoying spell-checking warnings, and better performance)  

##### Optional
  
`Window` > `Preferences`: Search for "git"  
 - `Projects` > Disable `Automatically share projects located in a Git repository`  
(We recommend that you use another graphical/command line tool to deal with git)  
(The eclipse git plugin is not very reliable. Also: much better performance.)  
(You may choose to uninstall the git plugin from Eclipse entirely)  

`Window` > `Preferences`: Search for "search"  
 - Disable `Reuse editors to show matches`  
(Prevents losing search results when making multiple searches)  
  
`Window` > `Preferences`: In `Java` > `Editor` > `Typing`, enable, under `Automatically insert at correct position:`  
 - `Semicolons`  
 - `Braces`  
(Helps writing statements & blocks faster)  

## Project Preferences
  
Right-Click on your project > `Properties` > Search for "formatter"  
 - Enable `Enable project specific settings`  
 - Click `Import...` > `billy/configs/EclipseBillyCodeStyle.xml`  
  
Right-Click on your project > `Properties` > Search for "clean up"  
 - Enable `Enable project specific settings`  
 - Click `Import...` > `billy/configs/EclipseBillyCleanUp.xml`  
  
Right-Click on your project > `Properties` > Search for "save actions", enable:  
 - `Enable project specific settings`  
 - `Perform the selected actions on save`  
 - `Format source code` (`Format all lines`)  
 - `Organize Imports`  
 - `Additional actions`  
  
Click on `Configure...`  
  
In the tab `Code Organizing`, enable:  
 - `Remove trailing whitespace` (`All lines`)  
 - `Correct indentation`  
  
In the tab `Code Style`, enable:  
 - `Use blocks in if/while/for/do statements` (`Always`)  
  
In the tab `Member Accesses`, enable:  
 - `Use 'this' qualifier for field accesses` (`Always`)  
 - `Use 'this' qualifier for method accesses` (`Always`)  
 - `Use declaring class as qualifier` (+ ALL inner checkboxes)  
  
In the tab `Missing Code`, enable:  
 - `Add missing Annotations`  
 - `@Override`  
 - `Implementations of interface methods (1.6 or higher)`  
 - `@Deprecated`  
  
In the tab `Unnecessary Code`, enable:  
 - `Remove unused imports`  
 - `Remove unnecessary casts`  
 - `Remove unnecessary $NON-NLS& tags`  
 - `Remove redundant type arguments (1.7 or higher)`  

## Want To Make Changes?

You may choose to change any of these settings, but be sensible; before doing so, speak with your team and try to reach a consensus.  
If you intend to make changes, keep in mind that ALL the developers' Eclipse configurations should always be the same. Otherwise, frustrating and time-consuming merge conflicts might occur.  

