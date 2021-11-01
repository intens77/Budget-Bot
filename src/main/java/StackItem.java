public class StackItem {
    public String userId;
    public ICommand command;

    public StackItem(String userId, ICommand command) {
        this.userId = userId;
        this.command = command;
    }
}
