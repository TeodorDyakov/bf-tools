
import java.util.Scanner;
import java.util.Stack;

public class BfCompiler {

    static String program = ".section .data\n" +
            "arr:\n" +
            ".zero 65536\n" +
            ".section .text\n" +
            ".globl _start\n" +
            "_start:\n" +
            "mov $arr, %r8\n";
            
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int label_num = 0;
        Stack<Integer> jmp_labels = new Stack<Integer>();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '>'){
                program += "inc %r8\n";
            }else if (c == '<'){
                program += "dec %r8\n";
            }else if (c == '+'){
                program += "incb (%r8)\n";
            }else if (c == '-'){
                program += "decb (%r8)\n";
            }else if (c == '.' || c == ','){
                int syscall_num = 0;
                if(c == '.'){
                    syscall_num = 1;
                }else{
                    syscall_num = 0;
                }
                program += "mov $" + syscall_num + 
                     ", %rax\n" +
                        "mov %r8, %rsi\n" +
                        "mov $1, %rdi\n" +
                        "mov $1, %rdx\n" + 
                        "syscall\n"; 
            } else if (c == '['){
                program += "cmpb $0, (%r8)\n";
                program += "je " + "R" + label_num + "\n";
                String label = "L" + label_num + ":\n";
                program += label;
                jmp_labels.add(label_num);
                label_num++;
            }else if (c == ']'){
                program += "cmpb $0, (%r8)\n";
                int label_number = jmp_labels.pop();
                String where_to_jmp = "L" + label_number;
                program += "jne " + where_to_jmp + "\n";
                String label = "R" + label_number + ":\n";
                program += label;
            }
        }
        program += "mov $1, %rax\n" +
                "int $0x80";

        System.out.println(program);
    }
}
