package symbolTable;

public class SymbolTableVarEntity extends SymbolTableEntity{

    public final VariableType variableType;
    private boolean lineOrder;

    public SymbolTableVarEntity(VariableType variableType,int lineDefinition,boolean lineOrder) {
        super(SymbolTableEntityType.VARIABLE,lineDefinition);
        this.variableType = variableType;
        this.lineOrder=lineOrder;
    }

    public SymbolTableVarEntity(VariableType variableType) {
        super(SymbolTableEntityType.VARIABLE);
        this.variableType = variableType;
    }

    public boolean isLineOrder() {
        return lineOrder;
    }
}
