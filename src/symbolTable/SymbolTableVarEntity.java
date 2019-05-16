package symbolTable;

public class SymbolTableVarEntity extends SymbolTableEntity{

    public final VariableType variableType;

    public SymbolTableVarEntity(VariableType variableType,int lineDefinition) {
        super(SymbolTableEntityType.VARIABLE,lineDefinition);
        this.variableType = variableType;
    }

    public SymbolTableVarEntity(VariableType variableType) {
        super(SymbolTableEntityType.VARIABLE);
        this.variableType = variableType;
    }

}
