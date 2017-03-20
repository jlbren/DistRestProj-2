package Retries;

public class ExponentialBackoffRetryPolicy implements IRetryPolicy {

    @Override
    public ISingleMethodPolicy NewMethod() {
        return new ExponentialBackoffMethodInstance();
    }

}