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

Node* createNode(int value) {
	Node* node = (Node*)malloc(sizeof(Node));
	if (node == NULL) {
		return NULL;
	}
	node->val = value;
	node->next = NULL;
	return node;
}

List createList() {
	List list;
	list.head = NULL;
	list.end = NULL;
	list.length = 0;
	return list;
}

void insertToBegin(List* listPtr, Node* nodePtr) {
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

void insertToEnd(List* listPtr, Node* nodePtr) {
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

void insertAfterElement(List* listPtr, Node* newNodePtr, Node* baseNode) {
	newNodePtr->next = baseNode->next;
	baseNode->next = newNodePtr;
	listPtr->length++;
	if (listPtr->length == 2) {
		listPtr->end = newNodePtr;
		newNodePtr->next = NULL;
	}
}

void deleteNode(List* listPtr, Node* delNodePtr) {
	if (listPtr->head == delNodePtr) {
		listPtr->head = listPtr->head->next;
		free(delNodePtr);
		listPtr->length--;
		return;
	}
	Node* currNodePtr = listPtr->head;
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

void printList(List* listPtr) {
	Node* currNodePtr = listPtr->head;
	while (currNodePtr) {
		printf("%i ", currNodePtr->val);
		currNodePtr = currNodePtr->next;
	}
	printf("\n");
}

void clearList(List* listPtr) {
	Node* cur = listPtr->head;
	while (cur) {
		cur = listPtr->head->next;
		free(listPtr->head);
		listPtr->head = cur;
	}
}

bool isCycled(List* listPtr) {
	Node* fast = listPtr->head->next;
	Node* slow = listPtr->head;
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