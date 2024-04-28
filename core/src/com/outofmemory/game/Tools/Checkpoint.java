package com.outofmemory.game.Tools;

public class Checkpoint {
    private String id;

    private String phrase;

    private String task;

    private String letter;

    // Getters
    public String getId() {
        return id;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getTask() {
        return task;
    }

    public String getLetter() {
        return letter;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
