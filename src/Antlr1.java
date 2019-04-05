/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import gen.grmLexer;
import gen.grmParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import gen.grmLexer;
import gen.grmParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Amin
 */
public class Antlr1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
  
        //File path = new File("");
       // FileInputStream fileInputStream = new FileInputStream(path);
        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream("import Ss\n" +
                "import Masoud\n" +
                "class SD{\n" +
                "def int saeid(){\n" +
                "for temp in range(22,30){\n" +
                "\n" +
                "}\n" +
                "}\n" +
                "}");
        // create a lexer that feeds off of input CharStream
        grmLexer lexer = new grmLexer(input);
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer
        grmParser parser = new grmParser(tokens);
        ParseTree tree = parser.program(); // begin parsing at init rule
        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();
        // Walk the tree created during the parse, trigger callbacks
        walker.walk(new myListener(), tree);
        System.out.println(); // print a \n after translation

    }

}
