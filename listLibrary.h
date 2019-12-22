#include <stdbool.h>
#ifndef LIST_LIBRARY_INCLUDED
#define LIST_LIBRARY_INCLUDED

struct Node {
	int val;
	char key[50];
	struct Node* next;
};

struct List {
	struct Node* head;
	struct Node* end;
	size_t length;
};

struct Node* createNode(int value, char* key);
struct List* createList();
struct Node* getNode(size_t number, struct List* listPtr);
void insertToBegin(struct List* listPtr, struct Node* nodePtr);
void insertToEnd(struct List* listPtr, struct Node* nodePtr);
void insertAfterElement(struct List* listPtr, struct Node* newNodePtr, size_t position);
void deleteNode(struct List* listPtr, size_t number);
void removeNode(struct List* list, struct Node* delNode);
void printList(struct List* listPtr);
void clearList(struct List* listPtr);
bool isCycled(struct List* listPtr);
 
#endif LIST_LIBRARY_INCLUDED

