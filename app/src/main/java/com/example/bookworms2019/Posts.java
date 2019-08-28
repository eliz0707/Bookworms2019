package com.example.bookworms2019;

public class Posts {

    private String postId;
    private String moduleCode;
    private String price;
    private String type;
    private String grade;
    private String username;

    public Posts(){

    }

    public Posts(String postId, String moduleCode, String price, String type, String grade, String username) {
        this.postId = postId;
        this.moduleCode = moduleCode;
        this.price = price;
        this.type = type;
        this.grade = grade;
        this.username = username;
    }

    public String getPostId() {
        return postId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getGrade() {
        return grade;
    }

    public String getUsername() {
        return username;
    }

}
