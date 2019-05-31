package symbolTable;

import static symbolTable.SymbolTableEntity.SymbolTableEntityType.*;

public class SymbolTableCalssEntity extends SymbolTableEntity {
    private String parentName;

    public SymbolTableCalssEntity(int line, String parentName) {
        super(CLASS,line);
        this.type = CLASS;
        this.parentName = parentName;
    }

    public SymbolTableCalssEntity(String parentName) {
        super(CLASS);
        this.parentName = parentName;
    }
}
