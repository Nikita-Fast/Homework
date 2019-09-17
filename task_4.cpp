#include<iostream>
using namespace std;

int main()
{
    int a,b;
    cin >> a >> b;
    int k{0};
    while(b<=a)
    {
        a = a-b;
        k++;
    }
    cout << k;
}
