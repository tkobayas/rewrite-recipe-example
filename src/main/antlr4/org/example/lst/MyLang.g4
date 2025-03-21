// MyLang.g4
grammar MyLang;

// A program is a series of statements
program: statement* EOF ;

// A statement is an assignment ending with a semicolon
statement: ID '=' expr ';' ;

// Expressions: simple arithmetic
expr
    : expr op=('*'|'/') expr
    | expr op=('+'|'-') expr
    | primary
    ;

// Primary expressions: integers, identifiers, or parenthesized expressions
primary: INT | ID | '(' expr ')' ;

// Lexer rules
ID  : [a-zA-Z]+ ;
INT : [0-9]+ ;

// Whitespace is sent to a hidden channel to avoid interfering with parsing.
// (For a fully lossless tree you might instead capture these tokens for later reassembly.)
WS  : [ \t\r\n]+ -> channel(HIDDEN) ;
