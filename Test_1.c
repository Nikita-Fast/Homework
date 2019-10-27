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

//situation "-   123" not considered a mistake, program will give result equal -123
int stringToNumber(char string[]) {
    size_t length = strlen(string);
    size_t symbolError = 0;
    size_t spaceError = 0;
    size_t minusError = 0;
    size_t amountOfMinusesError = 0;
    size_t spaceFlag = 0;
    size_t numberFlag = 0;
    size_t firstDigitFlag = 0;
    size_t overflowFlag = 0;
    size_t negativeFlag = 0;
    size_t minusCounter = 0;
    int minusNumber = 1;
    int overflowConst = 214748364;
    int result = 0;
    for (size_t i = 0; i < length; i++) {
        if (string[i] >= '0' && string[i] <= '9') {
            if (result > overflowConst || result < -1 * overflowConst || \
                result == 214748364 && (string[i] == '8' || string[i] == '9') || \
                result == -1 * overflowConst && string[i] == '9') {
                overflowFlag = 1;
            }
            if (firstDigitFlag) {
                result *= 10;
            }
            result += (string[i] - '0') * minusNumber;
            firstDigitFlag = 1;
            numberFlag = 1;
        }
        if (numberFlag == 1 && string[i] == ' ') {
            spaceFlag = 1;
            numberFlag = 0;
        }
        if (numberFlag == 1 && spaceFlag == 1) {
            spaceError = 1;
        }
        if (string[i] != ' ' && string[i] != '-' && (string[i] > '9' || string[i] < '0')) {
            symbolError = 1;
        }
        if (firstDigitFlag == 1 && string[i] == '-') {
            minusError = 1;
        }
        if (minusCounter > 1) {
            amountOfMinusesError = 1;
        }
        if (string[i] == '-') {
            minusCounter++;
            negativeFlag = 1;
            minusNumber = -1;
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
    if (minusError) {
        printf("Error: minus can't stay after digit\n");
    }
    if (amountOfMinusesError) {
        printf("Error: string contain more than 1 minus\n");
    }
    if (overflowFlag) {
        printf("Error: integer overflow detected\n");
    }
    if (length && !spaceError && !symbolError && !overflowFlag && !minusError && !amountOfMinusesError) {
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
    char m[50] = "-   123 ";
    printf("%i", stringToNumber(m));
}
*/

