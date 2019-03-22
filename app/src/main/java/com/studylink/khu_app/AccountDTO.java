package com.studylink.khu_app;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDTO {

    public String username;
    public String userbirth;
    public String usersex;
    public List<String> disposition;
    public String registcheck = "false";

   /* public AccountDTO(){

    }

    public AccountDTO(String username, String userbirth, String usersex, List<String> disposition){
        this.username = username;
        this.userbirth= userbirth;
        this.usersex = usersex;
        this.disposition = disposition;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("userbirth", userbirth);
        result.put("usersex", usersex);
        result.put("disposition", disposition);
        return result;
    }*/
}
