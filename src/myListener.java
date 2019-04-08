

import gen.grmBaseListener;


import gen.grmParser;
import org.antlr.v4.runtime.misc.NotNull;

public class myListener extends grmBaseListener {

    private int method_defune_number = 0;

    @Override
    public void exitProgram(grmParser.ProgramContext ctx) {
        System.out.println("+ " + ctx.getChild(1).getText());
        System.out.println("method define number : " + method_defune_number);
    }

    @Override
    public void exitMethodDec(grmParser.MethodDecContext ctx) {
        System.out.print("method name : ");
        System.out.println(ctx.getChild(2).getText());
        method_defune_number ++;
    }
}
