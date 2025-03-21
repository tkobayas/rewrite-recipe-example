// Generated from org/example/lst/MyLang.g4 by ANTLR 4.10.1
package org.example.lst;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MyLangParser}.
 */
public interface MyLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MyLangParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MyLangParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MyLangParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MyLangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MyLangParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MyLangParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MyLangParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyLangParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(MyLangParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyLangParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(MyLangParser.PrimaryContext ctx);
}