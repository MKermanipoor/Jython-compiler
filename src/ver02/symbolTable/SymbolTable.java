package ver02.symbolTable;

import ver02.symbolTable.subSybmolTable.SubClassSymbolTable;
import ver02.symbolTable.subSybmolTable.SubMethodSymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable.VarEntity.VarType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SymbolTable {
    private SymbolTable parent = null;
    private HashMap<String, SymbolTable> childs = new HashMap<>();

    private SubClassSymbolTable classTable = null;
    private SubVarSymbolTable varTable = null;
    private SubMethodSymbolTable methodTable = null;

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

    public SymbolTable getChild(String key) {
        return childs.get(key);
    }

    //************************ class part ************************
    private void checkClassTable() {
        if (classTable == null)
            classTable = new SubClassSymbolTable(this);
    }

    public void addClassEntity(String className, int lineDefinition, String classHash) {
        checkClassTable();

        classTable.addClass(className, lineDefinition, classHash);
    }

    public void addClassEntity(String className, int lineDefinition, String classHash, String supperCalssName) {
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
    private void checkVarTable() {
        if (varTable != null)
            return;

        varTable = new SubVarSymbolTable(this);
    }

    public void addVarEntity(String varName, VarType varType, int lineDefinition, boolean isArray) {
        checkVarTable();
        if (isArray)
            varTable.addArrayVatiable(varName, varType, lineDefinition);
        else
            varTable.addVariable(varName, varType, lineDefinition);
    }

    public void addVarEntity(String varName, String className, int lineDefinition, boolean isArray) {
        checkVarTable();
        if (isArray)
            varTable.addArrayVatiable(varName, className, lineDefinition);
        else
            varTable.addVariable(varName, className, lineDefinition);
    }

    public void addAttributeEntity(String varName, VarType varType, int lineDefinition, boolean isArray) {
        checkVarTable();
        if (isArray)
            varTable.addArrayAttribute(varName, varType, lineDefinition);
        else
            varTable.addAttribute(varName, varType, lineDefinition);
    }

    public void addAttributeEntity(String varName, String className, int lineDefinition, boolean isArray) {
        checkVarTable();
        if (isArray)
            varTable.addArrayAttribute(varName, className, lineDefinition);
        else
            varTable.addAttribute(varName, className, lineDefinition);
    }

    public SubVarSymbolTable.VarEntity findVar(String varName) {
        SubVarSymbolTable.VarEntity result;
        if (varTable == null)
            result = null;
        else
            result = varTable.getEntity(varName);

        if (parent != null && result == null)
            return parent.findVar(varName);

        return result;
    }


    //************************ method ************************
    private void checkMethodTable() {
        if (methodTable == null)
            methodTable = new SubMethodSymbolTable(this);
    }

    public void addMethod(String methodName, SubMethodSymbolTable.MethodEntity.ReturnType returnType, String className, boolean arrayReturn, ArrayList<SubMethodSymbolTable.MethodEntity.InputInfo> inputType, int lineDefinition) {
        checkMethodTable();
        methodTable.addMethodEntity(methodName, returnType, arrayReturn, className, inputType, lineDefinition);
    }

    public ArrayList<SubMethodSymbolTable.MethodEntity> findMethod(String methodName) {
        ArrayList<SubMethodSymbolTable.MethodEntity> result = new ArrayList<>();

        if (methodTable == null && parent != null)
            return parent.findMethod(methodName);

        if (methodTable != null) {
            HashSet<String> methodSet = methodTable.getMethodNameToEntity(methodName);

            if (methodSet.isEmpty() && parent != null)
                return parent.findMethod(methodName);

            if (!methodSet.isEmpty())
                methodSet.forEach((hashcode) -> result.add(methodTable.getEntity(hashcode)));
        }
        return result;
    }

    public SubMethodSymbolTable.MethodEntity findMethodByHash(String hash) {
        SubMethodSymbolTable.MethodEntity result = null;

        if (methodTable == null && parent != null)
            return parent.findMethodByHash(hash);

        if (methodTable != null){
            result = methodTable.getEntity(hash);
        }

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
