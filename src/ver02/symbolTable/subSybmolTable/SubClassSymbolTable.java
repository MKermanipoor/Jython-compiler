package ver02.symbolTable.subSybmolTable;

import ver02.symbolTable.SymbolTable;

public class SubClassSymbolTable extends SubSymbolTable<SubClassSymbolTable.ClassEntity>{

    public SubClassSymbolTable(SymbolTable master) {
        super(master);
    }

    public void addClass (String className, int lineDefinition) {
        add(className, new ClassEntity(lineDefinition));
    }

    public static class ClassEntity{
        private int lineDefinition;
        private String suberClassName;
        // TODO Masoud 6/7/2019: (DS) add constructor set


        public ClassEntity(int lineDefinition) {
            this.lineDefinition = lineDefinition;
        }

        public int getLineDefinition() {
            return lineDefinition;
        }

        public String getSuberClassName() {
            return suberClassName;
        }
    }
}
