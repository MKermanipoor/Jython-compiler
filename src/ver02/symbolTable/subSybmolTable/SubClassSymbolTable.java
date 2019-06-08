package ver02.symbolTable.subSybmolTable;

import ver02.symbolTable.SymbolTable;

public class SubClassSymbolTable extends SubSymbolTable<SubClassSymbolTable.ClassEntity>{

    public SubClassSymbolTable(SymbolTable master) {
        super(master);
    }

    public void addClass (String className, int lineDefinition, String classHash) {
        add(className, new ClassEntity(lineDefinition, classHash));
    }

    public static class ClassEntity{
        private final int lineDefinition;
        private final String classHash;
        private String suberClassName;

        // TODO Masoud 6/7/2019: (DS) add constructor set


        public ClassEntity(int lineDefinition, String classHash) {
            this.lineDefinition = lineDefinition;
            this.classHash = classHash;
        }

        public int getLineDefinition() {
            return lineDefinition;
        }

        public String getSuberClassName() {
            return suberClassName;
        }

        public String getClassHash() {
            return classHash;
        }
    }
}
