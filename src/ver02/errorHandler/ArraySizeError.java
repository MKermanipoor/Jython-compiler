package ver02.errorHandler;

class ArraySizeError extends Error {
    ArraySizeError(int line) {
        super(line, 210);
    }

    @Override
    public String getText() {
        return "Error " + code +
                ": in line " + line +
                ", Size of an array must be of type integer.";
    }
}
