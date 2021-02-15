#pragma once

#include <iostream>
#include <string>
#include <vector>
#include <vector>

using namespace std;

class Heap {
public:
	Heap(vector<int> inVec);
	static int findMax(int a, int b);
	void printHeap(vector<int> heap);
protected:
	vector<int> resultArray;
};
