package ver02.symbolTable;

import ver02.symbolTable.subSybmolTable.SubClassSymbolTable;

import java.util.HashMap;

public class SymbolTable {
    private SymbolTable parent = null;
    private HashMap<String, SymbolTable> childs = new HashMap<>();
    private SubClassSymbolTable classTable = null;

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
