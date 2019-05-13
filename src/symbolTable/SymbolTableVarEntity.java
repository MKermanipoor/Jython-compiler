package symbolTable;

public class SymbolTableVarEntity extends SymbolTableEntity{

    public final VariableType variableType;

    public SymbolTableVarEntity(VariableType variableType) {
        this.variableType = variableType;
        this.type = SymbolTableEntityType.VARIABLE;
    }
}
