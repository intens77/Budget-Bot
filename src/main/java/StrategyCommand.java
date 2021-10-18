public class StrategyCommand implements ICommand {
    private Receiver receiver;

    public StrategyCommand(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public String execute() {
        return receiver.getStrategies();
    }
}