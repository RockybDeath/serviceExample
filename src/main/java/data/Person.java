package data;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private long timeLife;
    public Person(String name, long timeLife){
        this.name = name;
        this.timeLife = System.currentTimeMillis()+timeLife;
    }
    public String getName() {
        return name;
    }

    public long getTimeLife() {
        return timeLife;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeLife(long timeLife) {
        this.timeLife = timeLife;
    }
    public boolean isLive(long currentTimeMillis){
        return currentTimeMillis < timeLife;
    }
    @Override
    public String toString(){
        return "[ "+this.name+" -  "+this.timeLife+"]";
    }
}
