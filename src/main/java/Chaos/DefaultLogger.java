package Chaos;

public class DefaultLogger implements IChaosLogger {

    @Override
    public void Log(String objectType, String methodName, String chaosName) {
        System.out.println(objectType + " in method " + methodName + " had problem " + chaosName);

    }

}