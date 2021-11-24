package Patterns;

import WorkingClasses.User;

import java.util.ArrayList;

public abstract class Command  {
    private ArrayList<String> parameters;
    public final Integer limitParameter;
    public Command(Integer limitParameter){
        parameters = new ArrayList<>();
        this.limitParameter = limitParameter;

    }
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
