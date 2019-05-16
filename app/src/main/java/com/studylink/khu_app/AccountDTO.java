package com.studylink.khu_app;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDTO implements Serializable {

    private String uid;
    public String username;
    public String userbirth;
    public String usersex;
    public String userregion;
    public List<String> disposition;
    private List<String> roomId;
    private String profileImg;
    private List<RoomUploadDTO> myAlarm;
//    public String registcheck = "false";

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserbirth() {
        return userbirth;
    }

    public void setUserbirth(String userbirth) {
        this.userbirth = userbirth;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getUserregion() {
        return userregion;
    }

    public void setUserregion(String userregion) {
        this.userregion = userregion;
    }

    public List<String> getDisposition() {
        return disposition;
    }

    public void setDisposition(List<String> disposition) {
        this.disposition = disposition;
    }

    public List<String> getRoomId() {
        return roomId;
    }

    public void setRoomId(List<String> roomId) {
        this.roomId = roomId;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public List<RoomUploadDTO> getMyAlarm() {
        return myAlarm;
    }

    public void setMyAlarm(List<RoomUploadDTO> myAlarm) {
        this.myAlarm = myAlarm;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("userbirth", userbirth);
        result.put("usersex", usersex);
        result.put("disposition", disposition);
        return result;
    }
}
