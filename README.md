# Cordova FFMPEG Plugin

A feature add-on to get progress of running ffmpeg command.

Install the plugin
    
    $ cordova plugin add cordova-plugin-ffmpeg-with-progress


```js
ffmpeg.exec("-i file:///emulated/0/Downloads/meinput.mp4 -vn -c:a copy file:///emulated/0/Downloads/out.mp3", (success) => alert(success), (failure) => alert(failure));

//then ...

setInterval(()=>{
      ffmpeg.progress((progressDetails) => {
        console.log(progressDetails); 
        // {
        //     "frames": <number>,
        //     "fps": <number>,
        //     "size": <number>,
        //     "bitrate": <number>,
        //     "speed": <number>,
        //     "duration": <number>
        // }
      },(error) => {
        console.log(error); // cannot get progress
      }); 
},2000);

```

Detailed instructions on main repo : [MaximBelov](https://github.com/MaximBelov/cordova-plugin-ffmpeg)
