javac BfCompiler.java
cat hello.txt | java BfCompiler > out
as out -o out.o
ld out.o
./a.out