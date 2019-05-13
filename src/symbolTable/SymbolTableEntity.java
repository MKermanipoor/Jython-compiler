package symbolTable;

public class SymbolTableEntity {
    public enum SymbolTableEntityType{
        METHOD,VARIABLE,CLASS
    }

    protected SymbolTableEntityType type;
    protected boolean valid = true;
    protected int lineDefenitaion;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
