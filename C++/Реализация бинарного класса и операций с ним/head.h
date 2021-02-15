#pragma once

#include <iostream>
#include <string>
#include <algorithm>
#include <cmath>
#include <typeinfo>
#include <vector>

using namespace std;

class MyBinary {
public:
	MyBinary(int number = 0);
	~MyBinary();
	
	vector<int> getValue();

	static int compare (vector<int> num1, vector<int> num2);

	static MyBinary addition(MyBinary add1, MyBinary add2);
	static MyBinary substraction(MyBinary subtr1, MyBinary subtr2);
	static MyBinary multiplication(MyBinary mult1, MyBinary mult2);
	static MyBinary diversity(MyBinary div1, MyBinary div2);

	static vector<int> reverse(vector<int> initial);
	static string toStringValue(vector<int> binNumber);
	static string toStringReversedValue(vector<int> binNumber);
	static vector<int> inverseCode(vector<int> code);
	static vector<int> vecAddition(vector<int> binNum1, vector<int> binNum2);
    static vector<int> createBinVec(int num = 0);
	static vector<int> toAdditCode(vector<int> neg_vec);
	static vector<int> toNormalCode(vector<int> neg_vec);

protected:
	vector<int> binNumber;
    int is_inversed = 0;
	static const int digitCapacity = 8;
};
