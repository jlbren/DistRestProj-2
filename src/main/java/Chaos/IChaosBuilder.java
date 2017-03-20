package Chaos;

public interface IChaosBuilder {

    IChaosBuilder ForMethod(String name);

    void run() throws Exception;

}