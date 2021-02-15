#include "head.h"

int Heap::findMax(int a, int b){
	if (a > b)
		return a;
	else
		return b;

}

void Heap::printHeap(vector<int> heap) {
	string str_value;
	for (int i = 0; i < heap.size(); i++)
		str_value += to_string(heap[i]) + " ";
	cout << "Heap:   " << str_value << endl;
}

Heap::Heap(vector<int> inVec){
	int mainCountdown = inVec.size() - 1;
	int circle = 1;
	
	while(mainCountdown != 0){
		int countdown = mainCountdown;
		int locMax;
		
		while (countdown != 0){
		//Условие проверяет, полный лист или нет
			if (countdown % 2 == 1){
				locMax = countdown;
				countdown--;
				cout << "Check1:   " << inVec[locMax] << endl;
			} else{
				if (inVec[countdown] > inVec[countdown - 1])
					locMax = countdown;
				else
					locMax = countdown - 1;
				countdown -= 2;
				cout << "Check1:   " << inVec[locMax] << endl;
			}

			if (inVec[locMax] > inVec[countdown/2]){
				int tmp = inVec[countdown/2];
				inVec[countdown/2] = inVec[locMax];
				inVec[locMax] = tmp;
			} 
		}

		int tmp = inVec[0];
		inVec[0] = inVec[mainCountdown];
		inVec[mainCountdown] = tmp;

		printHeap(inVec);

		mainCountdown -= 1;
	}

}



