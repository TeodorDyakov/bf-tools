
import java.util.Scanner;
import java.util.Stack;

public class BfCompiler {

    static String program = ".section .data\n" +
            "arr:\n" +
            ".zero 65536\n" +
            ".section .text\n" +
            ".globl _start\n" +
            "_start:\n" +
            "mov $0, %rdi\n";

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String s = in.next();
        int label_num = 0;
        Stack<Integer> jmp_labels = new Stack<Integer>();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '>'){
                program += "inc %rdi\n";
            }else if (c == '<'){
                program += "dec %rdi\n";
            }else if (c == '+'){
                program += "incb arr(,%rdi,1)\n";
            }else if (c == '-'){
                program += "decb arr(,%rdi,1)\n";
            }else if (c == '.' || c == ','){
                program += "mov %rdi, %rdx\n";
                program += "add $arr, %rdx\n";
                int syscall_num = 0;
                if(c == '.'){
                    syscall_num = 1;
                }else{
                    syscall_num = 0;
                }
                program += "mov $" + syscall_num + 
                     ", %rax\n" +
                        "mov %rdi, %r9\n" +
                        "mov $1, %rdi\n" +
                        "mov %rdx, %rsi\n" +
                        "mov $1, %rdx\n" + 
                        "syscall\n" + 
                        "mov     %r9, %rdi\n";
            } else if (c == '['){
                program += "cmpb $0, arr(,%rdi,1)\n";
                program += "je " + "R" + label_num + "\n";
                String label = "L" + label_num + ":\n";
                program += label;
                jmp_labels.add(label_num);
                label_num++;
            }else if (c == ']'){
                program += "cmpb $0, arr(,%rdi,1)\n";
                int label_number = jmp_labels.pop();
                String where_to_jmp = "L" + label_number;
                program += "jne " + where_to_jmp + "\n";
                String label = "R" + label_number + ":\n";
                program += label;
            }
        }
        program += "mov $1, %rax\n" +
                "syscall";

        System.out.println(program);
    }
}
