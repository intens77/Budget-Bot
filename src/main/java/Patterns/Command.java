package Patterns;


import Objects.User;

import java.util.ArrayList;

public abstract class Command {
    private ArrayList<String> parameters;
    private static int limitParameter;

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public boolean isEnough() {
        return limitParameter == parameters.size();
    }

    public abstract String execute(User user, String message);
}