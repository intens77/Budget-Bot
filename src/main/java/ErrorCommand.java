public class ErrorCommand implements ICommand {
    private Receiver receiver;

    public ErrorCommand(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public String execute() {
        return receiver.generateError();
    }
}