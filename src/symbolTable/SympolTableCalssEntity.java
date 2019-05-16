package symbolTable;

import static symbolTable.SymbolTableEntity.SymbolTableEntityType.*;

public class SympolTableCalssEntity extends SymbolTableEntity {
    private String parentName;

    public SympolTableCalssEntity() {
        this.type = CLASS;
    }

    public SympolTableCalssEntity(String parentName) {
        super();
        this.parentName = parentName;
    }
}
