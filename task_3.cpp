#include <iostream>
using namespace std;

int main()
{
    int a{7};
    int b{5};
    a=a^b;
    b=b^a;
    a=a^b;
    cout << "a=" <<a<<" "<< "b=" <<b;
}
