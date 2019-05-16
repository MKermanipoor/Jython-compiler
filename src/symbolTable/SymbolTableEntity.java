package symbolTable;

public class SymbolTableEntity {
    public enum SymbolTableEntityType{
        ARRAY,METHOD,VARIABLE,CLASS
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

    public SymbolTableEntity(SymbolTableEntityType type, int lineDefenitaion) {
        this.type = type;
        this.lineDefenitaion = lineDefenitaion;
    }

    public SymbolTableEntity(SymbolTableEntityType type) {
        this.type = type;
        this.valid=false;
    }
}
