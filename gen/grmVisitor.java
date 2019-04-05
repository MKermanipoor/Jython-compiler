// Generated from F:/University/Compiler/Jython compiler/src\grm.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link grmParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface grmVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link grmParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(grmParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#importclass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportclass(grmParser.ImportclassContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(grmParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#class_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_body(grmParser.Class_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#varDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDec(grmParser.VarDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#arrayDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDec(grmParser.ArrayDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#methodDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDec(grmParser.MethodDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructor(grmParser.ConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(grmParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(grmParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#print_statment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_statment(grmParser.Print_statmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(grmParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#return_statment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_statment(grmParser.Return_statmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(grmParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#condition_order2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition_order2(grmParser.Condition_order2Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#condition_order1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition_order1(grmParser.Condition_order1Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#condition_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition_list(grmParser.Condition_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#if_statment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statment(grmParser.If_statmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#if_else_statment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_else_statment(grmParser.If_else_statmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#while_statment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_statment(grmParser.While_statmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#for_statment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_statment(grmParser.For_statmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#method_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod_call(grmParser.Method_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(grmParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(grmParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#exp_order4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_order4(grmParser.Exp_order4Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#exp_order3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_order3(grmParser.Exp_order3Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#exp_order2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_order2(grmParser.Exp_order2Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#exp_order1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_order1(grmParser.Exp_order1Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#arithmetic_operator_order4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic_operator_order4(grmParser.Arithmetic_operator_order4Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#arithmetic_operator_order3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic_operator_order3(grmParser.Arithmetic_operator_order3Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#prefixexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixexp(grmParser.PrefixexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#prefixexp2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixexp2(grmParser.Prefixexp2Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#explist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplist(grmParser.ExplistContext ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#relational_operators_order1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_operators_order1(grmParser.Relational_operators_order1Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#relational_operators_order2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_operators_order2(grmParser.Relational_operators_order2Context ctx);
	/**
	 * Visit a parse tree produced by {@link grmParser#assignment_operators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_operators(grmParser.Assignment_operatorsContext ctx);
}