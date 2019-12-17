#include <stdbool.h>
#ifndef LIST_LIBRARY_INCLUDED
#define LIST_LIBRARY_INCLUDED

struct Node {
	int val;
	char data[30];
	char* key;
	struct Node* next;
};

struct List {
	struct Node* head;
	struct Node* end;
	size_t length;
};

struct Node* createNode(int value);
struct List createList();
void insertToBegin(struct List* listPtr, struct Node* nodePtr);
void insertToEnd(struct List* listPtr, struct Node* nodePtr);
void insertAfterElement(struct List* listPtr, struct Node* newNodePtr, struct Node* baseNode);
void deleteNode(struct List* listPtr, struct Node* delNodePtr);
void printList(struct List* listPtr);
void clearList(struct List* listPtr);
bool isCycled(struct List* listPtr);
 
#endif LIST_LIBRARY_INCLUDED

