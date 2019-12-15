#ifndef LIST_FUNCTIONS_INCLUDED
#define LIST_FUNCTIONS_INCLUDED

#include <stdlib.h>
#include <stdio.h>

struct Node {
	int val;
	Node* next;
};
struct List {
	Node* head;
	Node* end;
	size_t length;
};

Node* createNode(int value);
List createList();
void insertToBegin(List* listPtr, Node* nodePtr);
void insertToEnd(List* listPtr, Node* nodePtr);
void insertAfterElement(List* listPtr, Node* newNodePtr, Node* baseNode);
void deleteNode(List* listPtr, Node* delNodePtr);
void printList(List* listPtr);
void clearList(List* listPtr);
bool isCycled(List* listPtr);

#endif

