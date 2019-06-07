package ver02.symbolTable;

import ver02.symbolTable.subSybmolTable.SubClassSymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable.VarEntity.VarType;

import java.util.HashMap;

public class SymbolTable {
    private SymbolTable parent = null;
    private HashMap<String, SymbolTable> childs = new HashMap<>();

    private SubClassSymbolTable classTable = null;
    private SubVarSymbolTable varTable = null;

    public SymbolTable createChild(String key) {
        if (childs.containsKey(key))
            return childs.get(key);

        SymbolTable child = new SymbolTable();
        child.parent = this;
        this.childs.put(key, child);
        return child;
    }

    public SymbolTable getParent() {
        return parent;
    }


    //************************ class part ************************
    private void checkClassTable() {
        if (classTable == null)
            classTable = new SubClassSymbolTable(this);
    }

    public void addClassEntity(String className, int lineDefinition) {
        checkClassTable();

        classTable.addClass(className, lineDefinition);
    }

    public void addClassEntity(String className, int lineDefinition, String supperCalssName) {
        checkClassTable();

        // TODO Masoud 6/7/2019: (Logic) complete
    }

    public SubClassSymbolTable.ClassEntity findClass(String className) {
        SubClassSymbolTable.ClassEntity result;
        if (classTable == null)
            result = null;
        else
            result = classTable.getEntity(className);

        if (parent != null && result == null)
            return parent.findClass(className);

        return result;
    }

    //************************ var part ************************
    private void checkVarTable(){
        if (varTable != null)
            return;

        varTable = new SubVarSymbolTable(this);
    }

    public void addVarEntity(String varName, VarType varType, int lineDefinition){
        checkVarTable();
        varTable.addVariable(varName, varType, lineDefinition);
    }

    public void addVarEntity(String varName, String className, int lineDefinition){
        checkVarTable();
        varTable.addVariable(varName, className, lineDefinition);
    }

    public void addAttributeEntity(String varName, VarType varType, int lineDefinition){
        checkVarTable();
        varTable.addAttribute(varName, varType, lineDefinition);
    }

    public void addAttributeEntity(String varName, String className, int lineDefinition){
        checkVarTable();
        varTable.addAttribute(varName, className, lineDefinition);
    }

    public SubVarSymbolTable.VarEntity findVar(String varName){
        SubVarSymbolTable.VarEntity result;
        if (varTable == null)
            result = null;
        else
            result = varTable.getEntity(varName);

        if (parent != null && result == null)
            return parent.findVar(varName);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Child Size : ");
        builder.append(childs.size());
        builder.append("\n");

        builder.append("Class Table Size : ");
        if (classTable == null)
            builder.append("0");
        else
            builder.append(classTable.getSize());
        builder.append("\n");

        return builder.toString();
    }


}
