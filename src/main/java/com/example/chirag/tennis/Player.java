package com.example.chirag.tennis;

public class Player {
    private int rank;
    private String FirstN;
    private String LastN;
    private String year;
    private String Email;
    private int id;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int rank) {
        this.id = id;
    }

    public String getFirstN() {
        return FirstN;
    }

    public void setFirstN(String firstN) {
        FirstN = firstN;
    }

    public String getLastN() {
        return LastN;
    }

    public void setLastN(String lastN) {
        LastN = lastN;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Player(int rank, String firstN, String lastN, String year, String email, int id) {

        this.rank = rank;
        FirstN = firstN;
        LastN = lastN;
        this.year = year;
        this.id = id;
        this.Email = email;
    }
}
