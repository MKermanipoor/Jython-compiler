grammar grm;

WS: [ \n\t\r]+ -> skip;

CAPITAL_LETTER: ('A'..'Z');
SMALL_LETTER: ('a'..'z');
NUMBER: ('0'..'9');
INTEGER: NUMBER+;
FLOAT: NUMBER+ '.' NUMBER+;
ALPHABET: CAPITAL_LETTER | SMALL_LETTER;
SYMBOL: '_';
ANY_THING: ALPHABET | NUMBER | SYMBOL;
ANY: ANY_THING | '\t' | '\r' | ' ' | '=' | '+=' | '-=' | '*=' | '/=' |  '<' | '>' | '<=' | '>=' | '==' | '!=' | '+' | '-' | '/' | '%' | ',' | '.' | '"' | '\'';
STRING:'"'(ANY_THING)*'"';
BOOL:'true'|'false';
CLASSNAME: CAPITAL_LETTER (ANY_THING)*;
TYPE: 'int' | 'float' | 'bool' | 'string' | CLASSNAME;
ID: SMALL_LETTER ANY_THING*;

MULLTI_LINE_COMMENT: '#*' .* '*#' -> skip;
SINGLE_LINE_COMMENT: '#' (ANY)* '\n' -> skip;

program: importclass* (classDef)?;

importclass:'import' CLASSNAME;

classDef: 'class' CLASSNAME ('(' CLASSNAME ')')? '{'class_body*'}';
class_body: varDec | methodDec | constructor |arrayDec;

varDec: TYPE ID;
arrayDec: TYPE '['INTEGER']' ID ;
methodDec: 'def' (TYPE|'void') ID '(' (parameter)?')''{' ( statement)* '}';
constructor: 'def' CLASSNAME '('(parameter)?')''{' ( statement)* '}' ;
parameter: varDec (',' varDec)* ;
statement : varDec | assignment | print_statment | method_call | return_statment | if_statment | if_else_statment | while_statment  | for_statment;
print_statment : 'print' '(' (prefixexp | TYPE args | INTEGER |STRING | BOOL) ')' ;
args : '(' (explist)? ')' ;
return_statment : 'return' exp ;

//condition
condition : BOOL | prefixexp | (exp) relational_operators (exp);
condition_list : condition (('or'|'and') condition)* ;

// if
if_statment : 'if' '(' condition_list ')' '{' statement* '}';
if_else_statment :'if' '(' condition_list ')' '{' statement* '}' ('elif' '(' condition_list ')' '{' statement* '}')* 'else' '{' statement* '}' ;

//while
while_statment : 'while' '(' condition_list ')' '{' statement* '}' ;

//for
for_statment : 'for' ID 'in' prefixexp '{' statement* '}'|
'for' ID 'in' 'range''('INTEGER (',' INTEGER)? (',' INTEGER)? ')' '{' statement* '}' ;

method_call : prefixexp '.' ID args | ID args;

//assignment
assignment : prefixexp assignment_operators exp|
varDec '=' exp|
arrayDec '=' TYPE args ('['INTEGER']') ;

//exp
//exp :'none' | BOOL | INTEGER | STRING | FLOAT | prefixexp | CLASSNAME args | ID args | '(' exp ')' | exp arithmetic_operator exp;
exp: exp_order4;
exp_order4: exp_order4 arithmetic_operator_order4 exp_order3 | exp_order3;
exp_order3: exp_order3 arithmetic_operator_order3 exp_order2 | exp_order2;
exp_order2: prefixexp | CLASSNAME args | ID args | exp_order1;
exp_order1: '(' exp ')' | 'none' | BOOL | INTEGER | STRING | FLOAT;

arithmetic_operator_order4:'+' | '-';
arithmetic_operator_order3:'*' | '/' | '%' ;


//prefixexp
prefixexp: ID prefixexp2;
prefixexp2: '[' INTEGER ']' prefixexp2 | '.' ID  prefixexp2 | '.' ID args prefixexp2 | ;

explist : exp (',' exp)*;
relational_operators : '<' | '>' | '<=' | '>=' | '==' | '!=' ;
assignment_operators : '=' | '+=' | '-=' | '*=' | '/=' ;