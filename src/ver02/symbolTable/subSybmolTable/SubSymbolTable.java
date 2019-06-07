package ver02.symbolTable.subSybmolTable;

import ver02.symbolTable.SymbolTable;

import java.util.HashMap;

public abstract class SubSymbolTable<T> {
    protected final SymbolTable master;
    protected HashMap<String, T> entities = new HashMap<>();


    public SubSymbolTable(SymbolTable master) {
        this.master = master;
    }

    public T getEntity(String name) {
        return entities.get(name);
    }

    void add(String name, T entity) {
        entities.put(name, entity);
    }

    public int getSize(){
        return entities.size();
    }
}
