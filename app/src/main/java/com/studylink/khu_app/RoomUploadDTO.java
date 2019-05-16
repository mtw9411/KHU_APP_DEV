package com.studylink.khu_app;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomUploadDTO implements Serializable {

    private String roomId;
    private String writing_content;
    private String uploaderId;
    private String uploaderImg;
    private String uploadername;
    private List<String> filename;
    private ArrayList<String> filetitle;
    private Date time;
    private String category;
    private String textType;
    private List<VoteDTO> voteList;
    private Date voteDeadline;
    private boolean duplicate;


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getWriting_content() {
        return writing_content;
    }

    public void setWriting_content(String writing_content) {
        this.writing_content = writing_content;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getUploaderImg() {
        return uploaderImg;
    }

    public void setUploaderImg(String uploaderImg) {
        this.uploaderImg = uploaderImg;
    }

    public String getUploadername() {
        return uploadername;
    }

    public void setUploadername(String uploadername) {
        this.uploadername = uploadername;
    }

    public List<String> getFilename() {
        return filename;
    }

    public void setFilename(List<String> filename) {
        this.filename = filename;
    }

    public void setFiletitle(ArrayList<String> filetitle) {
        this.filetitle = filetitle;
    }

    public ArrayList<String> getFiletitle() {
        return filetitle;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public List<VoteDTO> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<VoteDTO> voteList) {
        this.voteList = voteList;
    }

    public Date getVoteDeadline() {
        return voteDeadline;
    }

    public void setVoteDeadline(Date voteDeadline) {
        this.voteDeadline = voteDeadline;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }
}
