#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

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

struct Node* createNode(int value, char* data, char* key) {
	struct Node* node = (struct Node*)malloc(sizeof(struct Node));
	if (node == NULL) {
		return NULL;
	}
	node->val = value;
	node->key = key;
	strcpy(node->data, data);
	node->next = NULL;
	return node;
}

struct List createList() {
	struct List list;
	list.head = NULL;
	list.end = NULL;
	list.length = 0;
	return list;
}

void insertToBegin(struct List* listPtr, struct Node* nodePtr) {
	if (listPtr->length == 0) {
		listPtr->head = nodePtr;
		listPtr->end = nodePtr;
		listPtr->length++;
	}
	else {
		nodePtr->next = listPtr->head;
		listPtr->head = nodePtr;
		listPtr->length++;
	}
}

void insertToEnd(struct List* listPtr, struct Node* nodePtr) {
	if (listPtr->length == 0) {
		listPtr->head = nodePtr;
		listPtr->end = nodePtr;
		listPtr->length++;
	}
	else {
		listPtr->end->next = nodePtr;
		listPtr->end = nodePtr;
		listPtr->length++;
	}
}

void insertAfterElement(struct List* listPtr, struct Node* newNodePtr, struct Node* baseNode) {
	newNodePtr->next = baseNode->next;
	baseNode->next = newNodePtr;
	listPtr->length++;
	if (listPtr->length == 2) {
		listPtr->end = newNodePtr;
		newNodePtr->next = NULL;
	}
}

void deleteNode(struct List* listPtr, struct Node* delNodePtr) {
	if (listPtr->head == delNodePtr) {
		listPtr->head = listPtr->head->next;
		free(delNodePtr);
		listPtr->length--;
		return;
	}
	struct Node* currNodePtr = listPtr->head;
	while (currNodePtr) {
		if (currNodePtr->next == delNodePtr) {
			currNodePtr->next = currNodePtr->next->next;
			free(delNodePtr);
			listPtr->length--;
			return;
		}
		currNodePtr = currNodePtr->next;
	}
}

void printList(struct List* listPtr) {
	struct Node* currNodePtr = listPtr->head;
	while (currNodePtr) {
		printf("%s %i ", currNodePtr->data, currNodePtr->val);
		currNodePtr = currNodePtr->next;
	}
	printf("\n");
}

void clearList(struct List* listPtr) {
	struct Node* cur = listPtr->head;
	while (cur != NULL) {
		cur = listPtr->head->next;
		free(listPtr->head);
		listPtr->head = cur;
	}
}

bool isCycled(struct List* listPtr) {
	struct Node* fast = listPtr->head->next;
	struct Node* slow = listPtr->head;
	bool moveSlow = false;
	while (true) {
		if (fast == NULL) {
			return false;
		}
		if (fast == slow) {
			return true;
		}
		if (moveSlow) {
			slow = slow->next;
		}
		fast = fast->next;
		moveSlow = !moveSlow;
	}
}