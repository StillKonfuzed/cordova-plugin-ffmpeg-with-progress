# Cordova FFMPEG Plugin

A feature add-on to get progress of running ffmpeg command.

### Progress is NOT supported for IOS.


### Install the plugin (ios/android) :
    
    $ cordova plugin add https://github.com/StillKonfuzed/cordova-plugin-ffmpeg-with-progress.git

### Install the plugin (android Only branch) - (plugin size under 50MB out of 201MB) :

    $ cordova plugin add https://github.com/StillKonfuzed/cordova-plugin-ffmpeg-with-progress.git#android-only



### Usage : 
```js
ffmpeg.exec("-i file:///emulated/0/Downloads/meinput.mp4 -vn -c:a copy file:///emulated/0/Downloads/out.mp3", (success) => alert(success), (failure) => alert(failure));

//then ...

setInterval(()=>{
      //pass "string" or "json" as response type
      ffmpeg.progress("json", (progressDetails) => {
        console.log(progressDetails); 
        //if "json" =>

        // {
        //     "frames": <number>,
        //     "fps": <number>,
        //     "size": <number>,
        //     "bitrate": <number>,
        //     "speed": <number>,
        //     "duration": <number>
        // }

        //if "string" => "Frames : 1234, Time: 100 sec, New Size: 700 MB, Speed: 1.5 x"
      },(error) => {
        console.log(error); // cannot get progress
      }); 
},2000);

```

Detailed instructions on main repo : [MaximBelov](https://github.com/MaximBelov/cordova-plugin-ffmpeg)
