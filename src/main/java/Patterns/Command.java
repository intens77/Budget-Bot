package Patterns;


import Objects.User;

import java.util.ArrayList;

public abstract class Command {
    protected ArrayList<String> parameters;
    protected int limitParameter;

    public abstract String execute(User user, String message);

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public boolean isEnough() {
        return parameters.size() == limitParameter;
    }

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }
}