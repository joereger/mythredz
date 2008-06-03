package com.mythredz.htmluibeans;

/**
 * User: Joe Reger Jr
 * Date: Nov 7, 2007
 * Time: 12:43:43 PM
 */
public class TestGrid {

    private String name;
    private String description;
    private int id;
    private TestGridSubobject testGridSubobject;
    private double money;

    public TestGrid(int id, String name, String description, double money, TestGridSubobject testGridSubobject){
        this.id = id;
        this.name = name;
        this.description = description;
        this.money = money;
        this.testGridSubobject = testGridSubobject;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public TestGridSubobject getTestGridSubobject() {
        return testGridSubobject;
    }

    public void setTestGridSubobject(TestGridSubobject testGridSubobject) {
        this.testGridSubobject=testGridSubobject;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money=money;
    }
}
