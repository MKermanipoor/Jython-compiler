package symbolTable;

import static symbolTable.SymbolTableEntity.SymbolTableEntityType.*;

public class SympolTableCalssEntity extends SymbolTableEntity {
    private String parentName;

    public SympolTableCalssEntity(int line,String parentName) {
        super(CLASS,line);
        this.type = CLASS;
        this.parentName = parentName;
    }

    public SympolTableCalssEntity(String parentName) {
        super(CLASS);
        this.parentName = parentName;
    }
}
