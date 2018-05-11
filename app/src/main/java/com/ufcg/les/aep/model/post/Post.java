package com.ufcg.les.aep.model.post;

public class Post {

    private static int postId;

    private int id;
    private String name;


    public Post(String name) {
        this.id = Post.postId ++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
