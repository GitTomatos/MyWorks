#pragma once
#include <iostream>
#include <string>
#include <vector>
using namespace std;

template<typename T>
class MyList {
public:
	MyList(int maxSize);
	~MyList();
	int addToTheEnd(T data);
	int increaseSize(int incSize);
	int findIndexOfElement(T element);
	vector<int> findAllIndexesOfElement(T element);
	int GetSize();
	int GetMaxSize();
	T& operator[](int index);
	int removeFirst();
	int removeByIndex(int index);
	void removeAllByElement(T element);
	void removeAll();
	int print();

protected:
//	template<typename T>
	struct Echelon {
		Echelon *pNext = nullptr;
		T data = T();
	};
	int Size;
	int maxSize;
	Echelon *head;
};


