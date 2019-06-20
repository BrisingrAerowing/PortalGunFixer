# PortalGunFixer
A client-side only patch mod for Portal Gun by iChun.  
It fixes item rendering by forcing default non-emmissive item rendering on portal guns.
## Setup for users
![](http://cf.way2muchnoise.eu/324827.svg)]  
Simply download the mod from [here](https://minecraft.curseforge.com/projects/portal-gun-fixer) and drag and drop it into your mods directory.   
## Setup for developers
Use your favourite Git client or the terminal to clone this repository.   
Navigate to the root of this project and execute the commands listed below matching your OS.   
### Windows
```BATCH
gradlew sDecW <idea|eclipse>
```
### Linux
First we wanna make sure the gradle script has sufficient execution permissions.   
```SH
chmod +x ./gradlew
```
No we wanna set up the workspace.   
```SH
./gradlew sDecW <idea|eclipse>
```
