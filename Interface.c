#include <stdlib.h>
#include <stdio.h>
#include <Windows.h>
#include "listLibrary.h"
#include <crtdbg.h>

#ifdef _CRTDBG_MAP_ALLOC
inline void* __cdecl operator new(unsigned int s)
{
	return ::operator new(s, _NORMAL_BLOCK, __FILE__, __LINE__);
}
#endif
#define new new( _NORMAL_BLOCK, __FILE__, __LINE__)

#define CREATE_LIST_OP 1
#define INSERT_ELEMENT_OP 2
#define DELETE_ELEMENT_OP 3
#define PRINT_LIST_OP 4
#define CLEAR_LIST_OP 5
#define CHECK_LOOP_OP 6
#define EXIT_OP 7
#define BLUE_COLOR 11
#define WHITE_COLOR 15

int chooseTheOperation() {
	printf("choose the operation and insert it's number\n");
	printf("1) Create list\n");
	printf("2) insert element to the list\n");
	printf("3) delete secelted node\n");
	printf("4) print whole list\n");
	printf("5) clear list\n");
	printf("6) check for a loop in the list\n");
	printf("7) exit from the interface\n");
	size_t operationNumber = 0;
	printf("I want to perform operation with the number ");
	scanf("%ui", &operationNumber);
	return operationNumber;
}

void colorOutput(HANDLE hConsole, int textColor) {
	SetConsoleTextAttribute(hConsole, (WORD)((0 << 4 | textColor)));
}

int main() {
	_CrtSetReportMode(_CRT_WARN, _CRTDBG_MODE_FILE);
	_CrtSetReportFile(_CRT_WARN, _CRTDBG_FILE_STDOUT);
	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	int blue = BLUE_COLOR;
	int white = WHITE_COLOR;
	colorOutput(hConsole, white);
	struct List* listOfAddresses = createList();
	int operation = 0;
	operation = chooseTheOperation();
	while (operation != EXIT_OP) {
		switch (operation) {
			int value;
			size_t position;
			struct Node* node;
			struct Node* baseNodePtr;
			struct Node* delNodePtr;
			struct List* list;
		case CREATE_LIST_OP:
			list = createList();
			node = createNode((int)list);
			insertToEnd(listOfAddresses, node);
			colorOutput(hConsole, blue);
			printf("List was successfully created, its address is %p\n", list);
			colorOutput(hConsole, white);
			break;
		case INSERT_ELEMENT_OP:
			list = NULL;
			node = NULL;
			printf("Enter the address of the list\n");
			scanf("%p", &list);
			printf("insert node parametrs\n");
			scanf("%i", &value);
			printf("insert the number of node position\n");
			scanf("%ui", &position);
			insertAfterElement(list, createNode(value), position);
			colorOutput(hConsole, blue);
			printf("Element was successfully inserted to the list\n");
			colorOutput(hConsole, white);
			break;
		case DELETE_ELEMENT_OP:
			list = NULL;
			delNodePtr = NULL;
			printf("Enter the address of the list and the number of element which will be delet\n");
			scanf("%p %ui", &list, &position);
			deleteNode(list, position);
			colorOutput(hConsole, blue);
			printf("Element was successfully deleted from the list\n");
			colorOutput(hConsole, white);
			break;
		case PRINT_LIST_OP:
			list = NULL;
			printf("Enter the address of the list\n");
			scanf("%p", &list);
			colorOutput(hConsole, blue);
			printList(list);
			printf("All elements were successfully printed\n");
			colorOutput(hConsole, white);
			break;
		case CLEAR_LIST_OP:
			list = NULL;
			printf("Enter the address of the list\n");
			scanf("%p", &list);
			clearList(list);
			colorOutput(hConsole, blue);
			printf("List was successfully cleared\n");
			colorOutput(hConsole, white);
			break;
		case CHECK_LOOP_OP:
			list = NULL;
			printf("Enter the address of the list\n");
			scanf("%p", &list);
			colorOutput(hConsole, blue);
			if (isCycled(list)) {
				printf("The cycle was detected\n");
			}
			else {
				printf("List doesn't contains cycle\n");
			}
			colorOutput(hConsole, white);
			break;
		}
		operation = chooseTheOperation();
	}
	size_t length = listOfAddresses->length;
	struct Node* currNode = listOfAddresses->head;
	while (currNode != NULL) { 
		struct Node* toFree = currNode;
		freeList((struct List*)toFree->val);
		currNode = currNode->next;
	}
	freeList(listOfAddresses);
	colorOutput(hConsole, blue);
	printf("Exit from interface\n");
	colorOutput(hConsole, white);
	_CrtDumpMemoryLeaks();
}
