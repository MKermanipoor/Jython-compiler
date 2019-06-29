package ver02.symbolTable.subSybmolTable;

import ver02.symbolTable.SymbolTable;

public class SubClassSymbolTable extends SubSymbolTable<SubClassSymbolTable.ClassEntity>{

    public SubClassSymbolTable(SymbolTable master) {
        super(master);
    }

    public void addClass (String className, int lineDefinition, String classHash) {
        add(className, new ClassEntity(lineDefinition, classHash));
    }

    public void addClass (String className, int lineDefinition, String classHash, String parent){
        ClassEntity entity = new ClassEntity(lineDefinition, classHash);
        entity.setSuperClassName(parent);
        add(className, entity);
    }

    public static class ClassEntity{
        private final int lineDefinition;
        private final String classHash;
        private String superClassName;

        // TODO Masoud 6/7/2019: (DS) add constructor set


        public ClassEntity(int lineDefinition, String classHash) {
            this.lineDefinition = lineDefinition;
            this.classHash = classHash;
        }

        public int getLineDefinition() {
            return lineDefinition;
        }

        public String getSuperClassName() {
            return superClassName;
        }

        public String getClassHash() {
            return classHash;
        }

        public void setSuperClassName(String superClassName) {
            this.superClassName = superClassName;
        }
    }
}
