package Patterns;

import WorkingClasses.User;

public abstract class Command implements ICommand {
    @Override
    public String execute(User user, String message) {
        return null;
    }
}
