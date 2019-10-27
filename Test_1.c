#include <stdio.h>
#include <stdlib.h>
#include <string.h >

int fibbonachiNumber(int n) {
    int result = 1;
    int x = 1;
    int y = 1;
    for (int i = 0; i < n-1; i++) {
        result = x + y;
        y = x;
        x = result;
    }
    return result;
}

int stringToNumber(char string[]) {
    size_t length = strlen(string);
    size_t symbolError = 0;
    size_t spaceError = 0;
    size_t spaceFlag = 0;
    size_t numberFlag = 0;
    size_t firstDigitFlag = 0;
    size_t overflowFlag = 0;
    int result = 0;
    for (size_t i = 0; i < length; i++) {
        if (string[i] >= '0' && string[i] <= '9') {
            if (result > 214748364 || \
                result == 214748364 && (string[i] == '8' || string[i] == '9')) {
                overflowFlag = 1;
            }
            if (firstDigitFlag) {
                result *= 10;
            }
            result += string[i] - '0';
            firstDigitFlag = 1;
            numberFlag = 1;
        }
        if ((numberFlag == 1) && (string[i] == 32)) {
            spaceFlag = 1;
            numberFlag = 0;
        }
        if (numberFlag == 1 && spaceFlag == 1) {
            spaceError = 1;
        }
        if (string[i] != 32 && (string[i] > '9' || string[i] < '0')) {
            symbolError = 1;
        }
    }
    if (!length) {
        printf("Error: string must contain at least 1 symbol\n");
    }
    if (spaceError) {
        printf("Error: string has spaces between numbers\n");
    }
    if (symbolError) {
        printf("Error: string contain banned symbols\n");
    }
    if (overflowFlag) {
        printf("Error: integer overflow detected\n");
    }
    if (length && !spaceError && !symbolError && !overflowFlag) {
        return result;
    } else {
        return -1;
      }
}

void numbers_order(void) {
    printf("enter the number\n");
    int n = 0;
    scanf("%i", &n);
    printf("enter the numbers in range 0 to 255\n");
    int *p = malloc(n * sizeof(int));

    for (size_t i = 0; i < n; i++) {
        p[i] = -1;
    }
    int a = 0;
    size_t j = 0;
    int check = 0;
    for (size_t i=0; i < n; i++) {
        scanf("%i", &a);
        for(size_t k=0; k < j; k++) {
            if (p[k] == a) {
                check = 1;
            }
        }
        if (!check) {
            p[j] = a;
            j++;
        }
        check = 0;
    }
    while (*p != -1) {
        printf("%i ", *p);
        p++;
    }
}

/*
int main() {
    char m[50] = "564833";
    printf("%i", stringToNumber(m));
}
*/

