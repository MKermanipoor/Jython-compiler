package ver02.errorHandler;

class PrintError extends Error {
    public PrintError(int line) {
        super(line, 270);
    }

    @Override
    public String getText() {
        return "Error " + 270 +
                " : in line " + line + " , Type of parameter of print built-in function must be integer , string ,float or boolean.";
    }
}
