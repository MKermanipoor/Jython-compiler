// Generated from F:/University/Compiler/Jython compiler/src\grm.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link grmParser}.
 */
public interface grmListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link grmParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(grmParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(grmParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#importclass}.
	 * @param ctx the parse tree
	 */
	void enterImportclass(grmParser.ImportclassContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#importclass}.
	 * @param ctx the parse tree
	 */
	void exitImportclass(grmParser.ImportclassContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(grmParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(grmParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#class_body}.
	 * @param ctx the parse tree
	 */
	void enterClass_body(grmParser.Class_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#class_body}.
	 * @param ctx the parse tree
	 */
	void exitClass_body(grmParser.Class_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#varDec}.
	 * @param ctx the parse tree
	 */
	void enterVarDec(grmParser.VarDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#varDec}.
	 * @param ctx the parse tree
	 */
	void exitVarDec(grmParser.VarDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#arrayDec}.
	 * @param ctx the parse tree
	 */
	void enterArrayDec(grmParser.ArrayDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#arrayDec}.
	 * @param ctx the parse tree
	 */
	void exitArrayDec(grmParser.ArrayDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#methodDec}.
	 * @param ctx the parse tree
	 */
	void enterMethodDec(grmParser.MethodDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#methodDec}.
	 * @param ctx the parse tree
	 */
	void exitMethodDec(grmParser.MethodDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#constructor}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(grmParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#constructor}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(grmParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(grmParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(grmParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(grmParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(grmParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#print_statment}.
	 * @param ctx the parse tree
	 */
	void enterPrint_statment(grmParser.Print_statmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#print_statment}.
	 * @param ctx the parse tree
	 */
	void exitPrint_statment(grmParser.Print_statmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(grmParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(grmParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#return_statment}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statment(grmParser.Return_statmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#return_statment}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statment(grmParser.Return_statmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(grmParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(grmParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#condition_order2}.
	 * @param ctx the parse tree
	 */
	void enterCondition_order2(grmParser.Condition_order2Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#condition_order2}.
	 * @param ctx the parse tree
	 */
	void exitCondition_order2(grmParser.Condition_order2Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#condition_order1}.
	 * @param ctx the parse tree
	 */
	void enterCondition_order1(grmParser.Condition_order1Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#condition_order1}.
	 * @param ctx the parse tree
	 */
	void exitCondition_order1(grmParser.Condition_order1Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#condition_list}.
	 * @param ctx the parse tree
	 */
	void enterCondition_list(grmParser.Condition_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#condition_list}.
	 * @param ctx the parse tree
	 */
	void exitCondition_list(grmParser.Condition_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#if_statment}.
	 * @param ctx the parse tree
	 */
	void enterIf_statment(grmParser.If_statmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#if_statment}.
	 * @param ctx the parse tree
	 */
	void exitIf_statment(grmParser.If_statmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#if_else_statment}.
	 * @param ctx the parse tree
	 */
	void enterIf_else_statment(grmParser.If_else_statmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#if_else_statment}.
	 * @param ctx the parse tree
	 */
	void exitIf_else_statment(grmParser.If_else_statmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#while_statment}.
	 * @param ctx the parse tree
	 */
	void enterWhile_statment(grmParser.While_statmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#while_statment}.
	 * @param ctx the parse tree
	 */
	void exitWhile_statment(grmParser.While_statmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#for_statment}.
	 * @param ctx the parse tree
	 */
	void enterFor_statment(grmParser.For_statmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#for_statment}.
	 * @param ctx the parse tree
	 */
	void exitFor_statment(grmParser.For_statmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#method_call}.
	 * @param ctx the parse tree
	 */
	void enterMethod_call(grmParser.Method_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#method_call}.
	 * @param ctx the parse tree
	 */
	void exitMethod_call(grmParser.Method_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(grmParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(grmParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(grmParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(grmParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#exp_order4}.
	 * @param ctx the parse tree
	 */
	void enterExp_order4(grmParser.Exp_order4Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#exp_order4}.
	 * @param ctx the parse tree
	 */
	void exitExp_order4(grmParser.Exp_order4Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#exp_order3}.
	 * @param ctx the parse tree
	 */
	void enterExp_order3(grmParser.Exp_order3Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#exp_order3}.
	 * @param ctx the parse tree
	 */
	void exitExp_order3(grmParser.Exp_order3Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#exp_order2}.
	 * @param ctx the parse tree
	 */
	void enterExp_order2(grmParser.Exp_order2Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#exp_order2}.
	 * @param ctx the parse tree
	 */
	void exitExp_order2(grmParser.Exp_order2Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#exp_order1}.
	 * @param ctx the parse tree
	 */
	void enterExp_order1(grmParser.Exp_order1Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#exp_order1}.
	 * @param ctx the parse tree
	 */
	void exitExp_order1(grmParser.Exp_order1Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#arithmetic_operator_order4}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_operator_order4(grmParser.Arithmetic_operator_order4Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#arithmetic_operator_order4}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_operator_order4(grmParser.Arithmetic_operator_order4Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#arithmetic_operator_order3}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_operator_order3(grmParser.Arithmetic_operator_order3Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#arithmetic_operator_order3}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_operator_order3(grmParser.Arithmetic_operator_order3Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#prefixexp}.
	 * @param ctx the parse tree
	 */
	void enterPrefixexp(grmParser.PrefixexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#prefixexp}.
	 * @param ctx the parse tree
	 */
	void exitPrefixexp(grmParser.PrefixexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#prefixexp2}.
	 * @param ctx the parse tree
	 */
	void enterPrefixexp2(grmParser.Prefixexp2Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#prefixexp2}.
	 * @param ctx the parse tree
	 */
	void exitPrefixexp2(grmParser.Prefixexp2Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#explist}.
	 * @param ctx the parse tree
	 */
	void enterExplist(grmParser.ExplistContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#explist}.
	 * @param ctx the parse tree
	 */
	void exitExplist(grmParser.ExplistContext ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#relational_operators_order1}.
	 * @param ctx the parse tree
	 */
	void enterRelational_operators_order1(grmParser.Relational_operators_order1Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#relational_operators_order1}.
	 * @param ctx the parse tree
	 */
	void exitRelational_operators_order1(grmParser.Relational_operators_order1Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#relational_operators_order2}.
	 * @param ctx the parse tree
	 */
	void enterRelational_operators_order2(grmParser.Relational_operators_order2Context ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#relational_operators_order2}.
	 * @param ctx the parse tree
	 */
	void exitRelational_operators_order2(grmParser.Relational_operators_order2Context ctx);
	/**
	 * Enter a parse tree produced by {@link grmParser#assignment_operators}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_operators(grmParser.Assignment_operatorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link grmParser#assignment_operators}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_operators(grmParser.Assignment_operatorsContext ctx);
}