/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import gen.jythonLexer;
import gen.jythonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import gen.jythonLexer;
import gen.jythonParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import symbolTable.SymbolTable;


public class Antlr1 {


    public static void main(String[] args) throws IOException {
        SymbolTable symbolTable = new SymbolTable();
        File rootPath = new File("input\\");
        File[] fileList = rootPath.listFiles((dir, name) -> name.endsWith(".txt"));
        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            // create a CharStream that reads from standard input
            ANTLRInputStream input = new ANTLRInputStream(fileInputStream);
            // create a lexer that feeds off of input CharStream
            jythonLexer lexer = new jythonLexer(input);
            // create a buffer of tokens pulled from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer
            jythonParser parser = new jythonParser(tokens);
            ParseTree tree = parser.program(); // begin parsing at init rule
            // Create a generic parse tree walker that can trigger callbacks
            ParseTreeWalker walker = new ParseTreeWalker();
            // Walk the tree created during the parse, trigger callbacks
            walker.walk(new myListener(symbolTable, file.getName()), tree);
            System.out.println(); // print a \n after translation
            fileInputStream.close();
        }

    }

}
