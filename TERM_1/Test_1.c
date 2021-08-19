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

void numbersOrder(void) {
    printf("enter the number\n");
    int n = 0;
    size_t memoryError = 0;
    scanf("%i", &n);
    int *p = malloc(n * sizeof(int));
    if (p == NULL) {
        printf("failed to allocate memory\n");
        memoryError = 1;
    }
    if (!memoryError) {
        for (size_t i = 0; i < n; i++) {
            p[i] = -1;
        }
        int number = 0;
        size_t j = 0;
        int check = 0;
        printf("enter the numbers in range 0 to 255\n");
        for (size_t i = 0; i < n; i++) {
            scanf("%i", &number);
            for(size_t k = 0; k < j; k++) {
                if (p[k] == number) {
                    check = 1;
                }
            }
            if (!check) {
                p[j] = number;
                j++;
            }
            check = 0;
        }
        while (*p != -1) {
            printf("%i ", *p);
            p++;
        }
    }
    free(p);
}

void base64(char string[]) {
    int bufer = 0;
    size_t index = 0;
    size_t length = strlen(string);
    char *result = malloc(2 * length * sizeof(char));
    int workMod = -1;
    if (result == NULL) {
        printf("allocation memory error");
    } else {
        printf("Please write 0 if you want to encode and 1 if you want to decode\n");
        scanf("%i", &workMod);
        if (workMod == 0) {
            for (size_t i = 0; i < length; i += 3) {
                bufer =  ((int) string[i]);
                bufer = bufer << 8;
                bufer += ((int) string[i+1]);
                bufer = bufer << 8;
                bufer +=  (int) string[i+2];
                for (size_t j = 4; j > 0; j--) {
                    int buf = (bufer & ((1 << 6*j) - (1 << 6*(j-1)))) >> 6*(j-1);
                    if (-1 < buf && buf < 26) {
                    result[index] = (char) (buf + 65);
                    index++;
                    }
                    if (25 < buf && buf < 52) {
                        result[index] = (char) (buf + 71);
                        index++;
                    }
                    if (51 < buf && buf < 62) {
                        result[index] = (char) (buf - 4);
                        index++;
                    }
                    if (buf == 62) {
                        result[index] = (char) (43);
                        index++;
                    }
                    if (buf == 63) {
                        result[index] = (char) (47);
                        index++;
                    }
                }
            }
            if (length % 3 == 1) {
                result[index - 2] = '=';
                result[index - 1] = '=';
            }
            if (length % 3 == 2) {
                result[index - 1] = '=';
            }
        } else if (workMod == 1) {
                for (size_t j = 0; j < length; j += 4) {
                    bufer = 0;
                    for (size_t i = j; i < j+4; i++) {
                        if (string[i] >= 65 && string[i] <= 90) {
                            bufer += string[i] - 65;
                        }
                        if (string[i] >= 97 && string[i] <= 122) {
                            bufer += string[i] - 71;
                        }
                        if (string[i] >= 48 && string[i] <= 57) {
                            bufer += string[i] + 4;
                        }
                        if (string[i] == 43) {
                            bufer += string[i] + 19;
                        }
                        if (string[i] == 47) {
                            bufer += string[i] + 16;
                        }
                        if (i != j+3) {
                            bufer = bufer << 6;
                        }
                    }
                    for (size_t i = 0; i < 3; i++) {
                        int mask = (1 << (24 - 8 * i)) - (1 << (16 - 8 * i));
                        result[index] = (char) ((bufer & mask) >> (16 - 8 * i));
                        index++;
                    }
                }
         }
         size_t i = 0;
         while (i < index) {
            printf("%c", result[i]);
            i++;
         }
      }
      free(result);
}























