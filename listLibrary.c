#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

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

struct Node* createNode(int value, char* key) {
	struct Node* node = (struct Node*)malloc(sizeof(struct Node));
	if (node == NULL) {
		return NULL;
	}
	node->val = value;
	strcpy(node->key, key);
	node->next = NULL;
	return node;
}

struct List* createList() {
	struct List* list = (struct List*)malloc(sizeof(struct List));
	list->head = NULL;
	list->end = NULL;
	list->length = 0;
	return list;
}

struct Node* getNode(size_t number, struct List* listPtr) {
	if (listPtr->length <= number) { // нумерация с нуля
		return NULL;
	}
	size_t counter = 0;
	struct Node* currentNode = listPtr->head;
	while (counter != number) {
		currentNode = currentNode->next;
		counter++;
	}
	return currentNode;
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

void insertAfterElement(struct List* listPtr, struct Node* newNodePtr, size_t position) {
	if (position == 0) {
		newNodePtr->next = listPtr->head;
		listPtr->head = newNodePtr;
		listPtr->length++;
	}
	else {
		if (listPtr->length > position) {
			struct Node* prevNode = getNode(position - 1, listPtr);
			newNodePtr->next = prevNode->next;
			prevNode->next = newNodePtr;
			listPtr->length++;
		}
		else {
			insertToEnd(listPtr, newNodePtr);
			listPtr->length++;
		}
	}
}

void deleteNode(struct List* listPtr, size_t number) {
	if (listPtr->length > number) {
		struct Node* node = listPtr->head;
		struct Node* delNode = NULL;
		if (number == 0) {
			listPtr->head = listPtr->head->next;
			free(node);
			listPtr->length--;
		}
		else {
			size_t counter = 0;
			while (counter != number - 1) {
				node = node->next;
				counter++;
			}
			delNode = node->next;
			node->next = node->next->next;
			free(delNode);
			listPtr->length--;
		}
	}
}

void removeNode(struct List* list, struct Node* delNode) {
	if (list->length != 0) {
		struct Node* prevNode = list->head;
		while (prevNode->next != delNode && prevNode->next != NULL) {
			prevNode = prevNode->next;
		}
		if (prevNode->next == delNode) {
			prevNode->next = delNode->next;
			free(delNode);
			list->length--;
		}
	}
}

void printList(struct List* listPtr) {
	struct Node* currNodePtr = listPtr->head;
	while (currNodePtr) {
		printf("[%i %s] ", currNodePtr->val, currNodePtr->key);
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
	listPtr->length = 0;
}

bool isCycled(struct List* listPtr) {
	if (listPtr->length == 0) {
		return 0;
	}
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