#include <iostream>
using namespace std;

int main()
{
    int array[10];
    int counter{0};
    for(int i=0; i<10; i++)
    {
        cin >> array[i];
        if(array[i]==0)
        {
            counter++;
        }
    }
    cout << counter;
}
