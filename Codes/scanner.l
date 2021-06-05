%option noyywrap
%{
	#include<stdio.h>
	#include<string.h>
	struct SymbolTable
	{
	char symbol[10];
	char type[10];
	}SymbolTable[10];

int count=0;
char data[10];
char type[10];
void insert();
void display();
%}
ws[\t]
letter [a-zA-Z]
digit [0-9]
id({letter}({letter}|{digit})*)
notid [0-9]*[a-zA-Z0-9]*
Commentmulti [/]{1}[*]{1}[^*]*[*]{1}[/]
Commentsingle [/]{1}[/]{1}[^*]*
FRACTION [0-9]*{1}[.]{1}[0-9]*
datatype(void|int|float|char|string)
keywords(main|getch|clrscr)
keyword1(if|else|case|while|do|for|switch{ws}*\(.*\))
%%
#include{ws}*<{id}\.h>	{printf("\nPreprocessor directive is %s\t",yytext);}
{FRACTION} {printf("\nFraction: %s\n",yytext);}
{digit}+    		{ printf("\nConstant is %s",yytext);}
{datatype} 		{ printf("\nDatatype is %s",yytext);}
printf{ws}*\(\".*\"\) | 
scanf{ws}*\(\".*\"\)    { printf("\nBuilt in function is %s",yytext);}
{keyword1}/"("      	{ printf("\nConditional Operator is %s",yytext);}
{keywords}/"("  	{ printf("\nBuilt in function is %s",yytext);}
{id}/"("  		{printf("\nUser defined function is %s",yytext);insert(yytext,"function");}
{id}"["{digit}*"]"	{ printf("\nArray Declaration is %s",yytext);insert(yytext,"array declaration");}
{id}			{ printf("\nVariable name is %s",yytext);insert(yytext,"id");}
"<"|"<="|">"|">+"|"=="|"!=" {printf("\nRelational operator is %s\n",yytext); }
{notid} {printf("\nInvalid Identifier is %s\n",yytext);}
{Commentmulti} 		{printf("\nComment MultiLine: %s\n",yytext);}
{Commentsingle} 	{printf("\nComment Single Line: %s\n",yytext);}
(\;) 			{printf("\nDELIMETER is %s\n",yytext);}

%%

int main()
{
	yyin = fopen("input.txt","r");
	yylex();
	display();
	return 0;
}

void insert(char data[10],char type[10])
{
	strcpy(SymbolTable[count].symbol,data);
	strcpy(SymbolTable[count].type,type);
	++count;
}

void display()
{
	int i;
	for(i=0;i<count;i++)
	{
		printf("\n%s\t%s",SymbolTable[i].symbol,SymbolTable[i].type);
	}
}
