package symbolTable;

public class SymbolTableArrayEntity extends SymbolTableEntity {
    private final VariableType arrayType;

    public SymbolTableArrayEntity(VariableType arrayType,int lineDefinition) {
        super(SymbolTableEntityType.ARRAY,lineDefinition);
        this.arrayType = arrayType;

    }

    public SymbolTableArrayEntity(VariableType arrayType) {
        super(SymbolTableEntityType.ARRAY);
        this.arrayType = arrayType;
    }

}
