/*global cordova, module*/

module.exports = {
  exec: function (cmd, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "FFMpeg", "exec", [cmd]);
  },
  probe: function (filePath, statusCallback, errorCallback) {
    cordova.exec(statusCallback, errorCallback, "FFMpeg", "probe", [filePath]);
  },
  progress: function (responseType,statusCallback, errorCallback) {
    cordova.exec(statusCallback, errorCallback, "FFMpeg", "progress", [responseType]);
  },
};
