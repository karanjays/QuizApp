package com.qcenter.quizapp.model;

import lombok.Data;

@Data
public class Response {
    private int id;
    private String response;


    public Response(int id, String response) {
        this.id = id;
        this.response = response;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAns(String response) {
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public String getResponse() {
        return response;
    }
}
