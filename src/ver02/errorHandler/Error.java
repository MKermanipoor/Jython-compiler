package ver02.errorHandler;

public abstract class Error {
    final int line;
    final int code;

    public Error(int line, int code) {
        this.line = line;
        this.code = code;
    }

    public abstract String getText();

    @Override
    public String toString() {
        return getText();
    }
}
