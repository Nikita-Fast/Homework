#include <stdlib.h>
#include <stdio.h>

int globalVar1 = 5;
float globalVar2 = 3.1415;

void printMessage() {
	printf("We love C\n");
}

int addFunc(int x, int y) {
	return(x + y);
}

int main() {
	int localVar1 = -12;
	char localVar2 = 'x';
	size_t length = 250;
	printf("addresses of local variables: %p %p\n", &localVar1, &localVar2);
	printf("addresses of global variables: %p %p\n", &globalVar1, &globalVar2);
	printf("addresses of user functions: %p %p\n", &printMessage, &addFunc);
	printf("addresses of system functions: %p %p\n", &fprintf, &scanf);
	int* p = (int*)malloc(length * sizeof(int));
	if (p != NULL) {
		printf("address of dynamically allocated variable: %p\n", p);
		free(p);
	}
	else {
		printf("error of memory allocation\n");
	}
}
