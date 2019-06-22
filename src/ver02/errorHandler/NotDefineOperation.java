package ver02.errorHandler;

public class NotDefineOperation extends Error {
    public NotDefineOperation(int line) {
        super(line, 280);
    }

    @Override
    public String getText() {
        return "Error " + code +
                ": in line " + line +
                ", operation not defined on this types.";
    }
}
