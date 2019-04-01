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
STRING:'"'(ANY_THING)*'"';
BOOL:'true'|'false';
CLASSNAME: CAPITAL_LETTER (ANY_THING)*;
TYPE: 'int' | 'float' | 'bool' | 'string' | CLASSNAME;
ID: SMALL_LETTER ANY_THING*;

program: importclass* (classDef)?;

importclass:'import' CLASSNAME;

classDef: 'class' CLASSNAME ('(' CLASSNAME ')')? '{'class_body*'}';
class_body: varDec | methodDec | constructor |arrayDec;

varDec: TYPE ID;
arrayDec: TYPE '['INTEGER']' ID ;
methodDec: 'def' (TYPE|'void') ID '(' (parameter)?')''{' ( statement)* '}';
constructor: 'def' CLASSNAME '('(parameter)?')''{' ( statement)* '}' ;
parameter: varDec (',' varDec)* ;
statement : varDec | assignment | print_statment | method_call | return_statment | if_statment | while_statment | if_else_statment | for_statment;
print_statment : 'print' '(' (prefixexp | TYPE args | INTEGER |STRING | BOOL) ')' ;
args : '(' (explist)? ')' ;
return_statment : 'return' exp ;

condition_list : condition (('or'|'and') condition)* ;
condition : BOOL | prefixexp | (exp) relational_operators (exp);
if_statment : 'if' '(' condition_list ')' '{' statement* '}';
while_statment : 'while' '(' condition_list ')' '{' statement* '}' ;
if_else_statment :'if' '(' condition_list ')' '{' statement* '}' ('elif' '(' condition_list ')' '{' statement* '}')* 'else' '{' statement* '}' ;

for_statment : 'for' ID 'in' prefixexp '{' statement* '}'|
'for' ID 'in' 'range''('INTEGER (',' INTEGER)? (',' INTEGER)? ')' '{' statement* '}' ;
method_call : prefixexp '.' ID args | ID args;
assignment : prefixexp assignment_operators exp|
varDec '=' exp|
arrayDec '=' TYPE args ('['INTEGER']') ;

exp :'none' | BOOL | INTEGER | STRING | FLOAT | prefixexp | exp arithmetic_operator exp|
CLASSNAME args | '('exp')' | ID args ;
prefixexp : ID | prefixexp '[' INTEGER ']' | prefixexp '.' ID | prefixexp '.' ID args ;

explist : exp (',' exp)*;
arithmetic_operator: '+' | '-' | '*' | '/' | '%' ;
relational_operators : '<' | '>' | '<=' | '>=' | '==' | '!=' ;
assignment_operators : '=' | '+=' | '-=' | '*=' | '/=' ;