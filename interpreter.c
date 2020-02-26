#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include "hashTableLibrary.h"
#define MEMORY_SIZE 1024 * 1024 
#define MAX_STACK_SIZE 1 * 3
#define MAX_ADDR MEMORY_SIZE / sizeof(int32_t) 
#define MAX_LINES 200
#define STACK_OVERFLOW 888
#define STACK_UNDERFLOW 777
#define STACK_IS_EMPTY 111
#define MEMORY_ALLOCATION_FAILED 222
#define INCORRECT_ADDRESS 333
#define LABELS_NUMBER 100
#define MAX_STR_LEN 100
#define FAILED_TO_FIND_NODE 1337

struct Stack {
	int* data;
	size_t size;
};

struct Stack* createStack() {
	struct Stack* stack = NULL;
	stack = malloc(sizeof(struct Stack));
	if (stack == NULL) {
		exit(STACK_OVERFLOW);
	}
	stack->size = 0;
	stack->data = malloc(MAX_STACK_SIZE * sizeof(int));
	if (stack->data == NULL) {
		free(stack);
		exit(STACK_OVERFLOW);
	}
	return stack;
}

void push(struct Stack* stack, int value) {
	if (stack->size >= MAX_STACK_SIZE) {
		exit(STACK_UNDERFLOW);
	}
	stack->data[stack->size] = value;
	stack->size++;
}

int pop(struct Stack* stack) {
	if (stack->size == 0) {
		exit(STACK_UNDERFLOW);
	}
	stack->size--;
	return stack->data[stack->size];
}

int get(struct Stack* stack) {
	if (stack->size == 0) {
		exit(STACK_IS_EMPTY);
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
	struct Stack* stack;
	int32_t* memory;
	size_t ip;
};

int* allocateMemory() {
	int* memory = NULL;
	memory = (int32_t*)malloc(MEMORY_SIZE);
	if (memory == NULL) {
		exit(MEMORY_ALLOCATION_FAILED);
	}
	return memory;
}

struct CMD {  
	int opCode;  
	int arg;
};

struct Program {
	struct CMD operations[MAX_LINES];
	struct HashTable* lableToLine;
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
	if (strstr(str, "ld") != NULL && strstr(str, "ldc") == NULL) { 
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

void addLabel(struct Interpreter* myInterpreter, char* str, size_t strNum) {
	size_t labelLength = strcspn(str, ":");
	char label[30];
	strncpy(label, str, labelLength);
	label[labelLength] = '\0';
	set(myInterpreter->p.lableToLine, label, strNum);
}

int main() {
	struct Interpreter myInterpreter;   
	myInterpreter.s.memory = allocateMemory(); 
	myInterpreter.s.stack = createStack();
	myInterpreter.s.ip = 0;   
	myInterpreter.p.lableToLine = createHashTable(LABELS_NUMBER, polynomialHash);   
	FILE* labelsInfo;
	char name[] = "C:\\Users\\Nick Fast\\Desktop\\test.txt";
	labelsInfo = fopen(name, "r");
	char currentString[MAX_STR_LEN];
	size_t i = 0;  //gives info about the number of current instruction that we will compare with label
	while (fgets(currentString, MAX_STR_LEN, labelsInfo) != NULL) {
		if (strchr(currentString, ':') != NULL) {
			addLabel(&myInterpreter, currentString, i);
		}
		i++;
	}
	fclose(labelsInfo);
	//printHashTable(myInterpreter.p.lableToLine); //collection info about labels is ended
	FILE* byteCodeGeneration;
	byteCodeGeneration = fopen(name, "r");
	i = 0;  
	while (fgets(currentString, MAX_STR_LEN, labelsInfo) != NULL) {
		char symbols[] = "ldstcaubmpjre";  //gives the opportunity to have empty strings in program and 
		if (strpbrk(currentString, symbols) != NULL) {  //add some markup to the program code
			if (strchr(currentString, ':') != NULL) {  // convert the string to a convenient form
				size_t len = strcspn(currentString, ":");
				memset(currentString, ' ', len + 1); //remove "<label>:"
			}
			char comand[MAX_STR_LEN]; 
			sscanf(currentString, "%s", comand);
			myInterpreter.p.operations[i].opCode = generateByteCode(comand);
			int opCode = myInterpreter.p.operations[i].opCode;
			if (opCode == jmp || opCode == br) {
				char label[MAX_STR_LEN];
				memset(label, '\0', MAX_STR_LEN);
				sscanf(currentString, "%*s %s", label);
				if (findElement(myInterpreter.p.lableToLine, label) == NULL) {
					exit(FAILED_TO_FIND_NODE);
				}
				myInterpreter.p.operations[i].arg = getValue(myInterpreter.p.lableToLine, label);
			}
			if (opCode == ld || opCode == st || opCode == ldc) {
				sscanf(currentString, "%*s %i", &myInterpreter.p.operations[i].arg);
				if (opCode == ld || opCode == st) {   // check that ld and st use correct addresses
					if (myInterpreter.p.operations[i].arg < 0 || myInterpreter.p.operations[i].arg >= MAX_ADDR) {
						exit(INCORRECT_ADDRESS);
					}
				}
			}
		}
		i++;
	}
	fclose(byteCodeGeneration);
	i = 0;
	//start interpreter byte code
	while (i < MAX_LINES && myInterpreter.p.operations[i].opCode != ret) { //until read the comand "ret"
		struct Stack* stack = myInterpreter.s.stack;
		int* memory = myInterpreter.s.memory;
		int opCode = myInterpreter.p.operations[i].opCode;
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
		myInterpreter.s.ip = i;
	}
	printf("value on top of stack is %i\n", get(myInterpreter.s.stack));
	freeHashTable(myInterpreter.p.lableToLine);
	free(myInterpreter.s.memory);
	free(myInterpreter.s.stack);
}