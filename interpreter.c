#include <stdio.h>

char mem[30000] = {0}, *ptr = mem, *p;

void jump(int step){
    for (int brackets = 1; brackets;) {
        switch (*(p+=step)) {
            case '[' : brackets += step; break;
            case ']' : brackets -= step; break;
        }   
    }       
}

int main(int c, char**a){
    for (p = a[1]; *p; p++)
        switch(*p){
            case '>': ++ptr; break;
            case '<': --ptr; break;
            case '+': ++*ptr; break;
            case '-': --*ptr; break;
            case '.': putchar(*ptr); break;
            case ',': *ptr = getchar(); break;
            case '[': if(!*ptr) jump(1); break;
            case ']': if(*ptr) jump(-1);
        }
}