package com.player.coachesapp.Model;

public class BasBoll {

    String Name;
    boolean selection;

    public BasBoll(String name, boolean selection) {
        Name = name;
        this.selection = selection;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    @Override
    public String toString() {
        return "BasBoll{" +
                "Name='" + Name + '\'' +
                ", selection=" + selection +
                '}';
    }
}
