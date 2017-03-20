package Chaos;

public class NullBuilder implements IChaosBuilder {

    @Override
    public IChaosBuilder ForMethod(String string) {
        return this;
    }

    @Override
    public void run() {
    }

}