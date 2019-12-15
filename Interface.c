#include <stdlib.h>
#include <stdio.h>
#include <Windows.h>
#include "myListFunctions.h"

int chooseTheOperation() {
	printf("choose the operation and insert it's number\n");
	printf(" 1) Create node\n");
	printf(" 2) Create list\n");
	printf(" 3) insert element to the begin of the list\n");
	printf(" 4) insert element to the end of the list\n");
	printf(" 5) insert element to list after base element\n");
	printf(" 6) delete secelted node\n");
	printf(" 7) print whole list\n");
	printf(" 8) clear list\n");
	printf(" 9) check for a loop in the list\n");
	printf("10) exit from the interface\n");
	size_t operationNumber = 0;
	printf("I want to perform operation with the number ");
	scanf("%ui", &operationNumber);
	return operationNumber;
}

void colorOutput(HANDLE hConsole, int textColor) {
	SetConsoleTextAttribute(hConsole, (WORD)((0 << 4 | textColor)));
}

int main() {
	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	int blue = 11; 
	int white = 15;
	int numberOfOperation = 0;
	numberOfOperation = chooseTheOperation();
	while (numberOfOperation != 10) {
		switch (numberOfOperation) {
			int value;
			Node* myNode;
			Node* nodePtr;
			Node* baseNodePtr;
			Node* delNodePtr;
			List myList;
			List* listPtr;
			case 1:
				printf("what value should be stored in the node?\n");
				printf("I want to store ");
				scanf("%i", &value);
				myNode = createNode(value);
				if (myNode == NULL) {
					printf("An error was occured, failed to create node\n");
					return 1;
				}
				colorOutput(hConsole, blue);
				printf("Node was successfully created, his address is %p\n", myNode);
				colorOutput(hConsole, white);
				break;
			case 2:
				myList = createList();
				colorOutput(hConsole, blue);
				printf("List was successfully created, his address is %p\n", &myList);
				colorOutput(hConsole, white);
				break;
			case 3:
				listPtr = NULL;
				nodePtr = NULL;
				printf("Enter the address of element and the address of the list\n");
				scanf("%p %p", &nodePtr, &listPtr);
				insertToBegin(listPtr, nodePtr);
				colorOutput(hConsole, blue);
				printf("Element was successfully inserted into the list\n");
				colorOutput(hConsole, white);
				break;
			case 4:
				listPtr = NULL;
				nodePtr = NULL;
				printf("Enter the address of element and the address of the list\n");
				scanf("%p %p", &nodePtr, &listPtr);
				insertToEnd(listPtr, nodePtr);
				colorOutput(hConsole, blue);
				printf("Element was successfully inserted into the list\n");
				colorOutput(hConsole, white);
				break;
			case 5:
				listPtr = NULL;
				nodePtr = NULL;
				baseNodePtr = NULL;
				printf("Enter the address of new node, address of base node and the address of the list\n");
				scanf("%p %p %p", &nodePtr, &baseNodePtr, &listPtr);
				insertAfterElement(listPtr, nodePtr, baseNodePtr);
				colorOutput(hConsole, blue);
				printf("Element was successfully inserted into the list\n");
				colorOutput(hConsole, white);
				break;
			case 6:
				listPtr = NULL;
				delNodePtr = NULL;
				printf("Enter the address of element which will be delet and the address of the list\n");
				scanf("%p %p", &delNodePtr, &listPtr);
				deleteNode(listPtr, delNodePtr);
				colorOutput(hConsole, blue);
				printf("Element was successfully deleted from the list\n");
				colorOutput(hConsole, white);
				break;
			case 7:
				listPtr = NULL;
				printf("Enter the address of the list\n");
				scanf("%p", &listPtr);
				colorOutput(hConsole, blue);
				printList(listPtr);
				printf("All elements were successfully printed\n");
				colorOutput(hConsole, white);
				break;
			case 8:
				listPtr = NULL;
				printf("Enter the address of the list\n");
				scanf("%p", &listPtr);
				clearList(listPtr);
				colorOutput(hConsole, blue);
				printf("List was successfully cleared\n");
				colorOutput(hConsole, white);
				break;
			case 9:
				listPtr = NULL;
				printf("Enter the address of the list\n");
				scanf("%p", &listPtr);
				colorOutput(hConsole, blue);
				if (isCycled(listPtr)) {
					printf("The cycle was detected\n");
				}
				else {
					printf("List doesn't contains cycle");
				}
				colorOutput(hConsole, white);
				break;
		}
		numberOfOperation = chooseTheOperation();
	}
	colorOutput(hConsole, blue);
	printf("Exit from interface\n");
	colorOutput(hConsole, white);
}

