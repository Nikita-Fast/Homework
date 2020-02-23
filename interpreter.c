#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include "hashTableLibrary.h"
#define MEMORY_SIZE 1024 //need to be increased  think about malloc
#define MAX_STACK_SIZE 1024
//#define MAX_ADDR MEMORY_SIZE / sizeof(int32_t)
#define MAX_LINES 200

struct Stack {
	int data[MAX_STACK_SIZE];
	size_t size;
};

void push(struct Stack* stack, int value) {
	if (stack->size >= MAX_STACK_SIZE) {
		exit(777);
	}
	stack->data[stack->size] = value;
	stack->size++;
}

int pop(struct Stack* stack) {
	if (stack->size == 0) {
		exit(-777);
	}
	stack->size--;
	return stack->data[stack->size];
}

int get(struct Stack* stack) {
	if (stack->size == 0) {
		exit(111);
	}
	return stack->data[stack->size - 1];
}

void loadFromMemory(struct Stack* stack, int* memory, int addr) { //удалить ли значение из ячейки памяти?
	int value = memory[addr];
	push(stack, value);
}

void uploadFromStack(struct Stack* stack, int* memory, int addr) {
	memory[addr] = pop(stack);
}

void loadConst(struct Stack* stack, int constant) {
	push(stack, constant);
}

void addOnStack(struct Stack* stack) {
	int a = pop(stack);
	int b = pop(stack);
	push(stack, a + b);
}

void subOnStack(struct Stack* stack) {
	int a = pop(stack);
	int b = pop(stack);
	push(stack, a - b);
}

void cmpOnStack(struct Stack* stack) {
	int a = pop(stack);
	int b = pop(stack);
	if (a > b) {
		push(stack, 1);
	}
	if (a < b) {
		push(stack, -1);
	}
	if (a == b) {
		push(stack, 0);
	}
}

struct State {
	struct Stack stack;
	int memory[MEMORY_SIZE];
	size_t ip;
};

struct CMD {  
	int opCode;  
	int arg;
};

struct Program {
	struct CMD operations[MAX_LINES];
	struct HashTable lableToLine;
};

struct Interpreter {
	struct Program p;
	struct State s;
};





void printHashTable(struct HashTable* table) {
	size_t size = table->size;
	for (size_t i = 0; i < size; i++) {
		printList(table->strings[i]);
	}
}

enum Opcodes {
	ld,
	st,
	ldc,
	add,
	sub,
	cmp,
	jmp,
	br,
	ret
};

int generateByteCode(char* str) {
	if (strstr(str, "ld") != NULL && strstr(str, "ldc") == NULL) { //shit, ldc consist of ld!!!!!
		return ld;
	}
	if (strstr(str, "st") != NULL) {
		return st;
	}
	if (strstr(str, "ldc") != NULL) {
		return ldc;
	}
	if (strstr(str, "add") != NULL) {
		return add;
	}
	if (strstr(str, "sub") != NULL) {
		return sub;
	}
	if (strstr(str, "cmp") != NULL) {
		return cmp;
	}
	if (strstr(str, "jmp") != NULL) {
		return jmp;
	}
	if (strstr(str, "br") != NULL) {
		return br;
	}
	if (strstr(str, "ret") != NULL) {
		return ret;
	}
}

int main() {
	struct Interpreter myInterpreter;
	myInterpreter.s.stack.size = 0;
	myInterpreter.p.lableToLine = *createHashTable(100, polynomialHash);
	//struct Program newProgram;
	char currentString[50];
	FILE* labelsInfo;
	char name[] = "C:\\Users\\Nick Fast\\Desktop\\test.txt";
	labelsInfo = fopen(name, "r");
	size_t i = 0;  //gives info about the number of current instruction that we will compare with label
	while (fgets(currentString, 50, labelsInfo) != NULL) {
		//printf("%s\n", currentString);
		if (strchr(currentString, ':') != NULL) {
			size_t labelLength = strcspn(currentString, ":");
			char label[30];
			strncpy(label, currentString, labelLength);
			label[labelLength] = '\0';
			set(&myInterpreter.p.lableToLine, label, i);
		}
		i++;
	}
	fclose(labelsInfo);
	printHashTable(&myInterpreter.p.lableToLine);
	//collection info about labels is ended
	FILE* byteCodeGeneration;
	byteCodeGeneration = fopen(name, "r");
	i = 0;  
	while (fgets(currentString, 50, labelsInfo) != NULL) {
		if (strchr(currentString, ':') != NULL) {
			size_t labelLength = strcspn(currentString, ":");
			memset(currentString, '-', labelLength + 1); //clear string from "<labelName>:"

			size_t num = 0;
			num = strcspn(currentString, "lsacjbr"); //first letters in commands
			memset(currentString, '-', num);

		}
		myInterpreter.p.operations[i].opCode = generateByteCode(currentString); //determine operation opCode
		int opCode = myInterpreter.p.operations[i].opCode; //use shorter name
		//consider cases when argument is labels name and thus it's string
		if (opCode == 6) {
			/*memset(currentString + strcspn(currentString, "jmp"), '-', 3);
			char label[30];
			sscanf(currentString, "%s", label);  //terminating null at the end?
			myInterpreter.p.operations[i].arg = getValue(&myInterpreter.p.lableToLine, label);*/
			
			size_t distance = strcspn(currentString, "jmp");
			memset(currentString + distance, '-', 3);
			char label[30];
			sscanf(currentString, "%*s %s", label);  //terminating null at the end?
			myInterpreter.p.operations[i].arg = getValue(&myInterpreter.p.lableToLine, label);
		}	
		if (opCode == 7) {
			size_t distance = strcspn(currentString, "br");
			memset(currentString + distance, '-', 2);
			char label[30];
			sscanf(currentString, "%*s %s", label);  //terminating null at the end?
			myInterpreter.p.operations[i].arg = getValue(&myInterpreter.p.lableToLine, label);
		}
		
		if (opCode == 0 || opCode == 1 || opCode == 2) {
			sscanf(currentString, "%*s %i", &myInterpreter.p.operations[i].arg); 
		} 
		i++;
	}
	fclose(byteCodeGeneration);
	/*
	for (size_t j = 0; j < i; j++) {
		printf("opcode:%i arg:%i\n", myInterpreter.p.operations[j].opCode, myInterpreter.p.operations[j].arg);
	}*/
	i = 0;
	//start interpreter byte code
	while (i < MAX_LINES && myInterpreter.p.operations[i].opCode != 8) { //until read the comand "ret"
		int opCode = myInterpreter.p.operations[i].opCode;
		struct Stack* stack = &myInterpreter.s.stack;
		int* memory = myInterpreter.s.memory;
		int arg = myInterpreter.p.operations[i].arg;
		switch (opCode) {
			case ld: 
				loadFromMemory(stack, memory, arg);
				break;
			case st:
				uploadFromStack(stack, memory, arg);
				break;
			case ldc:
				loadConst(stack, arg);
				break;
			case add:
				addOnStack(stack);
				break;
			case sub:
				subOnStack(stack);
				break;
			case cmp:
				cmpOnStack(stack);
				break;
			case jmp:
				i = arg - 1;
				break;
			case br:
				if (get(stack) != 0) {
					i = arg - 1;
				}
				break;
		}
		i++;
	}
	printf("value on top of stack is %i\n", get(&myInterpreter.s.stack));
}