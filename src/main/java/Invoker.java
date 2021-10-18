public class Invoker {
    ICommand start;
    ICommand strategy;
    ICommand error;
    public Invoker(ICommand start, ICommand strategy, ICommand error){
        this.start = start;
        this.strategy = strategy;
        this.error = error;
    }
    void startMessage(){
        start.execute();
    }
    void strategyMessage(){
        strategy.execute();
    }
    void errorMessage(){
        error.execute();
    }
}
