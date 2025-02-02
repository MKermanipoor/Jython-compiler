package symbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private SymbolTable parent = null;
    private HashMap<String , SymbolTableEntity> map = new HashMap<>();
    private ArrayList<SymbolTable> childs = new ArrayList<>();

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

    public boolean contains(String name){
        if(map.containsKey(name))
            return true;
        if (parent == null)
            return false;
        return parent.contains(name);
    }

    public boolean containsVarOrArray(String name){
        SymbolTableEntity temp = getSymbolTableEntity(name);
        return temp instanceof SymbolTableVarEntity || temp instanceof SymbolTableArrayEntity;
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
        this.childs.add(child);
        return child;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                ", map=" + map +
                '}';
    }

    public ArrayList<SymbolTable> getChilds() {
        return childs;
    }

    public HashMap<String, SymbolTableEntity> getMap() {
        return map;
    }
}
