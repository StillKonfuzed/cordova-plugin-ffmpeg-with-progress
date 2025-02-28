package com.stillkonfuzed.plugin;

import android.util.Log;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.arthenica.ffmpegkit.*;

public class FFMpeg extends CordovaPlugin {
    private static final String TAG = "FFMpegPlugin";
    private FFmpegSession currentSession;
    private Statistics latestStatistics;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("exec")) {
            currentSession = FFmpegKit.executeAsync(data.getString(0), session -> {
                Log.d(TAG, String.format("FFmpeg process exited with state %s and rc %s.%s",
                        session.getState(), session.getReturnCode(), notNull(session.getFailStackTrace())));
                if (ReturnCode.isSuccess(session.getReturnCode())) {
                    callbackContext.success();
                } else {
                    callbackContext.error("Error Code: " + session.getReturnCode());
                }
            }, null, statistics -> {
                latestStatistics = statistics; // Store latest statistics for retrieval
            });
            return true;
        } else if (action.equals("probe")) {
            MediaInformationSession mediaInformationSession = FFprobeKit.getMediaInformation(data.getString(0));
            ReturnCode returnCode = mediaInformationSession.getReturnCode();
            if (ReturnCode.isSuccess(returnCode)) {
                callbackContext.success(mediaInformationSession.getMediaInformation().getAllProperties());
            } else {
                callbackContext.error(notNull(mediaInformationSession.getFailStackTrace()));
            }
            return true;
        } 
        //added progress stats frames,fps,speed,duration -- stillkonfuzed
        else if (action.equals("progress")) { 
            
            if (currentSession == null) {
                callbackContext.error("No active FFmpeg session found!");
                return true;
            }
            if(latestStatistics == null){
              callbackContext.error("No progress available yet!");
              return true;
            }
            try {
                String firstParam = data.getString(0);
                if ("string".equalsIgnoreCase(firstParam)) {
                    String formattedOutput = String.format(
                        "Frames : %d, Time: %d sec, New Size: %d MB, Speed: %s",
                        latestStatistics.getVideoFrameNumber(),
                        latestStatistics.getTime() / 1000,
                        latestStatistics.getSize() / 1024 / 1024,
                        latestStatistics.getSpeed()
                    );
                    callbackContext.success(formattedOutput);
                }else{ //return json
                    JSONObject progressData = new JSONObject();
                    progressData.put("frames", latestStatistics.getVideoFrameNumber());
                    progressData.put("fps", latestStatistics.getVideoFps());
                    progressData.put("size", latestStatistics.getSize());
                    progressData.put("bitrate", latestStatistics.getBitrate());
                    progressData.put("speed", latestStatistics.getSpeed());
                    progressData.put("duration", latestStatistics.getTime());
                    callbackContext.success(progressData);
                }
                
            } catch (JSONException e) {
                callbackContext.error("Error in progress reporting");
            }
            return true;
        }
        return false;
    }

    static String notNull(final String string) {
        return (string == null) ? "" : String.format("%s%s", "\n", string);
    }
}
