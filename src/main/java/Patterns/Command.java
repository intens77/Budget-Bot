package Patterns;


import Objects.User;

public abstract class Command {
    public abstract String execute(User user, String message);
}
