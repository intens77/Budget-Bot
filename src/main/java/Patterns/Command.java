package Patterns;

import WorkingClasses.User;

import java.util.ArrayList;

public abstract class Command  {
    private ArrayList<String> parameters;
    private Integer limitParameter;
    public Command(Integer limitParameter){
        this.limitParameter = limitParameter;

    }
    public String execute(User user, String message) {
        return null;
    }
}
