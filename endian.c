#include <stdlib.h>
#include <stdio.h>

int main() {
	int y = 1;
	if (*((char*)(&y))) {
		printf("little-Endian\n");
	}
	else {
		printf("Big Endian\n");
	}
}

