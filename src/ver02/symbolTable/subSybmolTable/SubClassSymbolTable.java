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
        private int lineDefination;
        private String suberClassName;
        // TODO Masoud 6/7/2019: (DS) add constructor set


        public ClassEntity(int lineDefinition) {
            this.lineDefination = lineDefinition;
        }

        public int getLineDefination() {
            return lineDefination;
        }

        public String getSuberClassName() {
            return suberClassName;
        }
    }
}
