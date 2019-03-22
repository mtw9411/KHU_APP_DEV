package com.studylink.khu_app;

import java.util.List;

public class RoomDTO {

    private String roomName;
    private String id;
    private Long fine;
    private String imageName;
    private String spinner;
    private String content;
    private List<String> roomdisposition;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getFine() {
        return fine;
    }

    public void setFine(Long fine) {
        this.fine = fine;
    }

    public String getimageName() {
        return imageName;
    }

    public void setimageName(String imageName) {
        this.imageName = imageName;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public List<String> getRoomdisposition() {
        return roomdisposition;
    }

    public void setRoomdisposition(List<String> roomdisposition) {
        this.roomdisposition = roomdisposition;
    }

}
