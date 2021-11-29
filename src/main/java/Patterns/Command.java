package Patterns;

import WorkingClasses.User;

import java.util.ArrayList;

public abstract class Command  {
    protected ArrayList<String> parameters;
    protected int limitParameter;
    public String execute(User user, String message) {
        return null;
    }

    public ArrayList<String> getParameters(){
        return parameters;
    }

    public boolean isEnough(){
        return parameters.size() == limitParameter;
    }
}
