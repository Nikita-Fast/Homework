#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include "hashTableLibrary.h"
#include "listLibrary.h"
#define MEMORY_SIZE 1024 * 1024 
#define MAX_STACK_SIZE 1 * 3
#define MAX_ADDR MEMORY_SIZE / sizeof(int32_t) 
#define MAX_LINES 200
#define STACK_OVERFLOW 888
#define STACK_UNDERFLOW 777
#define STACK_IS_EMPTY 111
#define MEMORY_ALLOCATION_FAILED 222
#define INCORRECT_ADDRESS 333
#define MAX_LABELS_NUMBER 100
#define MAX_STR_LEN 100
#define FAILED_TO_FIND_NODE 1337
#define FAILED_TO_OPEN_FILE_TO_COLLECT_LABELS_INFO 1999
#define FAILED_TO_OPEN_FILE_TO_GENERATE_BYTE_CODE 2000
#define TRUE 1
#define FALSE 0
#define NO_RET_COMMAND_IN_PROGRAM 3000
#define LAST_COMMAND_IN_PROGRAM_IS_NOT_RET 3005
#define MAX_BR_NUMBER 20
#define INVALID_RET_LOCATION 4000
#define FAILED_CHECK 0
#define FAILED_TO_ALLOCATE_MEMORY 4001
#define INCORRECT_COMMAND 5000

struct Stack {
	int32_t* data;
	size_t size;
};

struct Stack* createStack() {
	struct Stack* stack = NULL;
	stack = (struct Stack*)malloc(sizeof(struct Stack));
	if (stack == NULL) {
		exit(STACK_OVERFLOW);
	}
	stack->size = 0;
	stack->data = (int32_t*)malloc(MAX_STACK_SIZE * sizeof(int32_t));
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
	ret,
	incorrectCommand
};

struct Boundaries {
	int start;
	int end;
};

struct Boundaries* createBoundaries(struct Interpreter* myInterpreter) {
	struct Boundaries* myBoundaries = (struct Boundaries*)malloc(sizeof(struct Boundaries) * MAX_BR_NUMBER);
	if (myBoundaries == NULL) {
		exit(FAILED_TO_ALLOCATE_MEMORY);
	}
	size_t processingBrNumber = 0;
	for (size_t op = 0; op < MAX_LINES; op++) {     
		int opCode = myInterpreter->p.operations[op].opCode;
		if (opCode == br || opCode == jmp) {
			int argument = myInterpreter->p.operations[op].arg;
			if ((size_t)argument > op) {  //means that br or jmp jumps forward
				int end = argument; 
				myBoundaries[processingBrNumber].start = op;
				myBoundaries[processingBrNumber].end = end;
				processingBrNumber++;
			}
		}
	}
	return myBoundaries;
}

int checkRetCommand(struct Interpreter* myInterpreter, int whereRet, struct Boundaries* myBoundaries) {
	int strNumWhereRet = whereRet;
	for (size_t k = 0; k < 10; k++) {
		if (strNumWhereRet > myBoundaries[k].start && strNumWhereRet < myBoundaries[k].end) {
			return TRUE;
		}
	}
	return FALSE;
}

void retChecker(struct Interpreter* myInterpreter) {
	size_t finalRetNum = 0; //we don't check last ret since it can't be inside if-else block or just if block
	for (size_t i = 0; i < MAX_LINES; i++) {
		if (myInterpreter->p.operations[i].opCode == ret) {
			finalRetNum = i;
			printf("%i\n", finalRetNum);
		}
	}
	struct Boundaries* myBoundaries = createBoundaries(myInterpreter);
	for (size_t i = 0; i < MAX_LINES; i++) {
		if (myInterpreter->p.operations[i].opCode == ret && i < finalRetNum) {
			if (checkRetCommand(myInterpreter, i, myBoundaries) == FAILED_CHECK) {
				printf("you placed ret in invalid place\n");
				printf("number of string where this ret: %i\n", i);
				exit(INVALID_RET_LOCATION);
			}
			printf("ret placed normally\n");
		}
	}
	free(myBoundaries);
}

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
	return incorrectCommand;
}

void addLabel(struct Interpreter* myInterpreter, char* str, size_t strNum) {
	size_t labelLength = strcspn(str, ":");
	char label[MAX_STR_LEN];
	strncpy(label, str, labelLength);
	label[labelLength] = '\0';
	set(myInterpreter->p.lableToLine, label, strNum);
}

int stringHasLabel(char* string) {
	if (strchr(string, ':') != NULL) {
		return TRUE;
	}
	return FALSE;
}

void processString(char* string, struct Interpreter* myInterpreter, size_t strNumber) {
	if (stringHasLabel(string) == TRUE) {
		addLabel(myInterpreter, string, strNumber);
	}
}

void collectLabelsInf(char* fileName, struct Interpreter* myInterpreter) {
	FILE* file;
	//char name[] = "test.txt";
	file = fopen(fileName, "r");
	if (file == NULL) {
		exit(FAILED_TO_OPEN_FILE_TO_COLLECT_LABELS_INFO);
	}
	char currentString[MAX_STR_LEN];
	size_t i = 0;
	while (fgets(currentString, MAX_STR_LEN, file) != NULL) {
		processString(currentString, myInterpreter, i);
		i++;
	}
	fclose(file);
}

void createByteCode(char* fileName, struct Interpreter* myInterpreter) {
	FILE* byteCodeGeneration;
	byteCodeGeneration = fopen(fileName, "r");
	if (byteCodeGeneration == NULL) {
		exit(FAILED_TO_OPEN_FILE_TO_GENERATE_BYTE_CODE);
	}
	char currentString[MAX_STR_LEN];
	size_t i = 0;
	size_t hasRetCommand = FALSE;
	size_t existInstrAfterLatestRet = FALSE;
	while (fgets(currentString, MAX_STR_LEN, byteCodeGeneration) != NULL) { //here was written  fgets(currentString, MAX_STR_LEN, labelsInfo) why labelsInfo ???
		char symbols[] = "ldstcaubmpjre";  //gives the opportunity to have empty strings in program and 
		if (strpbrk(currentString, symbols) != NULL) {  //add some markup to the program code
			if (strchr(currentString, ':') != NULL) {  // convert the string to a convenient form
				size_t len = strcspn(currentString, ":");
				memset(currentString, ' ', len + 1); //remove "<label>:"
			}
			char command[MAX_STR_LEN];
			sscanf(currentString, "%s", command);

			int opCode = generateByteCode(command);
			if (opCode == incorrectCommand) {
				exit(INCORRECT_COMMAND);
			}
			myInterpreter->p.operations[i].opCode = opCode;

			existInstrAfterLatestRet = TRUE;                                     //new, important to check!
			opCode = myInterpreter->p.operations[i].opCode;
			if (opCode == ret) {
				hasRetCommand = TRUE;
				existInstrAfterLatestRet = FALSE;
			}
			if (opCode == jmp || opCode == br) {
				char label[MAX_STR_LEN];
				memset(label, '\0', MAX_STR_LEN);
				sscanf(currentString, "%*s %s", label);
				if (findElement(myInterpreter->p.lableToLine, label) == NULL) {
					exit(FAILED_TO_FIND_NODE);
				}
				myInterpreter->p.operations[i].arg = getValue(myInterpreter->p.lableToLine, label);
			}
			if (opCode == ld || opCode == st || opCode == ldc) {
				sscanf(currentString, "%*s %i", &myInterpreter->p.operations[i].arg);
				if (opCode == ld || opCode == st) {   // check that ld and st use correct addresses
					if (myInterpreter->p.operations[i].arg < 0 || (size_t)myInterpreter->p.operations[i].arg >= MAX_ADDR) {
						exit(INCORRECT_ADDRESS);
					}
				}
			}
		}
		i++;
	}
	fclose(byteCodeGeneration);
	
	if (hasRetCommand == FALSE) {
		exit(NO_RET_COMMAND_IN_PROGRAM);
	}
	if (existInstrAfterLatestRet == TRUE) {
		exit(LAST_COMMAND_IN_PROGRAM_IS_NOT_RET);
	} 
	printf("%s", "before retChecker\n");
	retChecker(myInterpreter);  //check that ret commands in middle of program placed inside if-else or just if blocks 
	printf("%s", "after retChecker\n");
}

void interpreterByteCode(struct Interpreter* myInterpreter) {
	size_t i = 0;
	while (i < MAX_LINES) { 
		struct Stack* stack = myInterpreter->s.stack;
		int* memory = myInterpreter->s.memory;
		int opCode = myInterpreter->p.operations[i].opCode;
		int arg = myInterpreter->p.operations[i].arg;
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
		case ret:
			i = MAX_LINES;
			break;
		}
		i++;
		myInterpreter->s.ip = i;
	}
	printf("program finished successfully\n");
}

int main() {
	struct Interpreter myInterpreter;   
	myInterpreter.s.memory = allocateMemory(); 
	myInterpreter.s.stack = createStack();
	myInterpreter.s.ip = 0;   
	myInterpreter.p.lableToLine = createHashTable(MAX_LABELS_NUMBER, polynomialHash);   
	char fileName[] = "testSimple.txt";

	collectLabelsInf(fileName, &myInterpreter);
	printf("%s", "label\n");
	createByteCode(fileName, &myInterpreter);
	interpreterByteCode(&myInterpreter);
	printf("value on top of stack is %i\n", get(myInterpreter.s.stack));

	freeHashTable(myInterpreter.p.lableToLine);
	free(myInterpreter.s.memory);
	free(myInterpreter.s.stack);
}