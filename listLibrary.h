#include <stdbool.h>
#ifndef LIST_LIBRARY_INCLUDED
#define LIST_LIBRARY_INCLUDED

struct Node {
	int value;
	char key[50];
	struct Node* next;
};

struct List {
	struct Node* head;
	size_t length;
};

struct Node* createNode(int value, char* word);
struct List* createList();
void insertToBegin(struct List* list, int value, char* key);
struct Node* findNode(struct List* list, char* key);
struct Node* getNode(size_t number, struct List* list);
void insertToEnd(struct List* list, int value, char* key);
int getNodeNumber(struct List* list, char* key);
struct Node* getNodeByNumber(size_t number, struct List* list);
void deleteNode(struct List* list, char* key);
void freeList(struct List* list);
void printList(struct List* list);
bool isCycled(struct List* listPtr);

#endif //LIST_LIBRARY_INCLUDED
