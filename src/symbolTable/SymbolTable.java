package symbolTable;

import java.util.HashMap;

public class SymbolTable {
    private SymbolTable parent = null;
    private HashMap<String , SymbolTableEntity> map = new HashMap<>();

    public void setParent(SymbolTable parent) {
        this.parent = parent;
    }

    public SymbolTable getParent() {
        return parent;
    }

    public boolean add(String name ,SymbolTableEntity symbolTableEntity){
        if (contains(name)){
            SymbolTableEntity entity = getSymbolTableEntity(name);
            if (entity.isValid())
                return false;
            entity.setValid(true);
            return true;
        }

        map.put(name, symbolTableEntity);
        return true;
    }

    public boolean addNotValid(String name ,SymbolTableEntity symbolTableEntity){
        symbolTableEntity.setValid(false);
        if (contains(name)){
            return false;
        }

        map.put(name, symbolTableEntity);
        return true;
    }

    public boolean contains(String name){
        if(map.containsKey(name))
            return true;
        if (parent == null)
            return false;
        return parent.contains(name);
    }

    public SymbolTableEntity getSymbolTableEntity(String name){
        SymbolTableEntity temp ;
        temp = map.get(name);
        if (temp == null && parent != null)
            return parent.getSymbolTableEntity(name);
        return temp;
    }

    public SymbolTable createChild(){
        SymbolTable child = new SymbolTable();
        child.setParent(this);
        return child;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                ", map=" + map +
                '}';
    }
}
