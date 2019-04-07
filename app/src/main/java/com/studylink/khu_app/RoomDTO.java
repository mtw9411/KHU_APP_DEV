package com.studylink.khu_app;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoomDTO implements Serializable {

    private String roomName;
    private String id;
    private Long fine;
    private String imageName;
    private String spinner1;
    private String spinner2;
    private String age;
    private String gender;
    private String region;
    private String content1;
    private String content2;
    private List<String> roomdisposition;
    private Long total_member;
    private int member;
    private Date time;

    public Long getTotal_member() {
        return total_member;
    }

    public void setTotal_member(Long total_member) {
        this.total_member = total_member;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
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


    public List<String> getRoomdisposition() {
        return roomdisposition;
    }

    public void setRoomdisposition(List<String> roomdisposition) {
        this.roomdisposition = roomdisposition;
    }
    public String getSpinner1() {
        return spinner1;
    }

    public void setSpinner1(String spinner1) {
        this.spinner1 = spinner1;
    }

    public String getSpinner2() {
        return spinner2;
    }

    public void setSpinner2(String spinner2) {
        this.spinner2 = spinner2;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }



}
