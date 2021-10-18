public class StartCommand implements ICommand {
    private Receiver receiver;

    public StartCommand(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public String execute() {
        return receiver.startProcess();
    }
}
