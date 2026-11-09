package exceptions;

public class NurseExhaustedException extends Exception{
    private final String nurseName;

    public NurseExhaustedException(String nurseName) {
        this.nurseName = nurseName;
    }

    @Override
    public String getMessage() {
        return "Нянечка " + nurseName + " сбилась с ног и больше не может исполнять капризы";
    }
}
