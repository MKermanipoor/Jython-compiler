package ver02.errorHandler;

public class DoubleMethodDefine extends Error {
    private final String methodName;

    public DoubleMethodDefine(String methodName, int line) {
        super(line, 102);
        this.methodName = methodName;
    }

    @Override
    public String getText() {
        return "Error" + code +
                ": in line " + line +
                ", " + methodName +
                " has been defined already";
    }
}
