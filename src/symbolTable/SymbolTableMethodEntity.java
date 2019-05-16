package symbolTable;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableMethodEntity extends SymbolTableEntity{
    VariableType returnType;
    List<VariableType> list=new ArrayList<>();

    public SymbolTableMethodEntity(VariableType returnType, int lineDefenitaion) {
        super(SymbolTableEntityType.METHOD, lineDefenitaion);
        this.returnType =returnType;
    }

    public SymbolTableMethodEntity(VariableType returnType) {
        super(SymbolTableEntityType.METHOD);
        this.returnType =returnType;
    }

    public void addParam(VariableType variableType){
        // TODO: skewfield 16 May 2019 (Logic) age class bede pareimmmmm :D
        list.add(variableType);
    }

}
