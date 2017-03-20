package Retries;

public interface IRetryPolicy {

    ISingleMethodPolicy NewMethod();

}