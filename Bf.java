
import java.util.Scanner;
import java.util.Stack;

public class Bf {

    static String program = ".section .data\n" +
            "data_items: #These are the data items\n" +
            "        .zero 65536\n" +
            ".section .text\n" +
            ".globl _start\n" +
            "_start:\n" +
            "movl $0, %edi\n";

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
                program += "incb data_items(,%rdi,1)\n";
            }else if (c == '-'){
                program += "decb data_items(,%rdi,1)\n";
            }else if (c == '.'){
                program += "mov %rdi, %rdx\n";
                program += "add $data_items, %rdx\n";
                
                program += "mov     $1, %rax\n" +
                        "mov     %rdi, %rcx\n" +
                        "mov     $1, %rdi\n" +
                        "mov     %rdx, %rsi\n" +
                        "mov     $1, %rdx\n" +
                        "mov     %rcx, %rdi\n" + 
                        "syscall\n";
            }else if (c == ','){
                program += "dec %rdi\n";
            }else if (c == '['){
                program += "mov data_items(,%rdi,1), %rbx\n";
                program += "cmp $0, %rbx\n";
                program += "je " + "R" + label_num + "\n";
                String label = "L" + label_num + ":\n";
                program += label;
                jmp_labels.add(label_num);
                label_num++;
            }else if (c == ']'){
                program += "mov data_items(,%rdi,1), %rbx\n";
                program += "cmp $0, %rbx\n";

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
