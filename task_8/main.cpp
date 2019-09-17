#include <iostream>
#include <string>

using namespace std;

int main()
{
    string s,s1;
    getline(cin,s);
    getline(cin,s1);
    int len = s.size();
    int len1 = s1.size();
    int counter{0};
    bool flag;
    for(int i=0;i<=len-len1;i++)
    {
        flag = true;
        for(int j=0; j < len1; j++)
        {
            if(s[i+j] != s1[j])
            {
                flag = false;
            }
        }
        if(flag==true)
        {
            counter++;
        }
    }
    cout << counter;
}
