#include<stdio.h>
char* m[256] = {
    ['>'] = "++p;",
	['<'] = "--p;",
	['+'] = "++*p;",
	['-'] = "--*p;",
	['.'] = "putchar(*p);",
	[','] = "*p = getchar();",
	['['] = "while(*p){",
	[']'] = "}"};
int main(int c, char** a) {
    printf("#include<stdio.h>\nchar m[30000]={0};int main(){char* p = m;");
 	for (char *p = a[1]; *p;)
		printf("%s", m[*p++]);
	printf("}");
}
