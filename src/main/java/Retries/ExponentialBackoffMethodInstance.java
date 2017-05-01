package Retries;


import Response.SimpleResponse;

public class ExponentialBackoffMethodInstance implements ISingleMethodPolicy {

    private SimpleResponse _response;
    private int _retriesRemaining = 3;
    private int _currentRetryTime = 500;

    @Override
    public void HadResponse(SimpleResponse response) {
        _response = response;
        if(!_response.Success) {
            --_retriesRemaining;
        }
    }

    @Override
    public void PerformWaitIfNeeded() throws Exception {
        if(!_response.Success) {
            Thread.sleep(_currentRetryTime);
            _currentRetryTime = _currentRetryTime * 4;
        }
    }

    @Override
    public void ThrowErrorFromResponseIfAppropriate() throws Exception {
        if(_retriesRemaining <= 0 && !_response.Success) {
            throw new RuntimeException(_response.Message + "\r\n" + _response.StackTrace);
        }
    }

    @Override
    public boolean ShouldRetry() {
        return !_response.Success && _retriesRemaining > 0 && ErrorIsSomethingWeShouldRetryFor();
    }

    private boolean ErrorIsSomethingWeShouldRetryFor() {
        if(_response.Message.contains("deadlock")){
            return true;
        }
        if(_response.Message.contains("connection")) {
            return true;
        }
        if(_response.Message.contains("Latency")) {
            return true;
        }
        return false;
    }

}