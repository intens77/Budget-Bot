package Patterns;

import WorkingClasses.User;

public interface ICommand {
    String execute(User user, String message);
}