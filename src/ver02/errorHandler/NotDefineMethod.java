package ver02.errorHandler;

public class NotDefineMethod extends Error {
    private final String methodName;

    public NotDefineMethod(String methodName, int line) {
        super(line, 105);
        this.methodName = methodName;
    }

    @Override
    public String getText() {
        return "Error" + code +
                ": in line " + line +
                ", Can not find method " + methodName;
    }
}
