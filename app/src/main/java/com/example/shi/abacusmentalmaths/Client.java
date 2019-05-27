package com.example.shi.abacusmentalmaths;

public class Client {
    public int id;
    public String name;
    public String phone_number;
    public String test_date;
    public long score;


    public Client(int id, String name, String phone_number,String test_date, long score) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.test_date=test_date;
        this.score=score;
    }
    public Client(){
    }
}
