/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import gen.grmBaseListener;


import gen.grmParser;
import org.antlr.v4.runtime.misc.NotNull;

/**
 *
 * @author Amin
 */
public class myListener extends grmBaseListener {

//    @Override
//    public void exitMessage(@NotNull grmParser.MessageContext ctx) {
//        System.out.println("+ " + ctx.getChild(1).getText());
//    }


    @Override
    public void exitProgram(grmParser.ProgramContext ctx) {
        System.out.println(ctx.children.size());
        System.out.println("+ " + ctx.getChild(2).getText());
    }
}
