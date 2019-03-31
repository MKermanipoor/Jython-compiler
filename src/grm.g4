grammar grm;

WS: [ \n\t\r]+ -> skip;

CAPITAL_LETTER: ('A'..'Z');
SMALL_LETTER: ('a'..'z');
NUMBER: ('0'..'9');
INTEGER: NUMBER+;
ALPHABET: CAPITAL_LETTER | SMALL_LETTER;
SYMBOL: '_';
ANY_THING: ALPHABET | NUMBER | SYMBOL;

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
