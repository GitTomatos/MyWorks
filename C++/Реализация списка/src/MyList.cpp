#include "MyList.h"

template<typename T>
MyList<T>::MyList(int maxSize) {
	Size = 0;
	this->maxSize = maxSize;
	head = nullptr;
}

template<typename T>
MyList<T>::~MyList() {
	removeAll();
}

template<typename T>
int MyList<T>::increaseSize(int incSize) {
	if (this->maxSize == 0) {
//		perror("This List is infinite. You can't increase its size");
		return -1;
	}
	if (incSize < 0) {
//		perror("Your number should be >= 0");
		return -2;
	}
	maxSize += incSize;
	return 0;
}

template<typename T>
int MyList<T>::addToTheEnd(T data) {
	if (maxSize != 0 and Size == maxSize) {
//		perror("List is full");
		return -1;
	}
	if (head == nullptr) {
		head = new Echelon;
		head->data = data;
	} else {
		Echelon *current = head;
		while (current->pNext != nullptr) {
			current = current->pNext;
		}
		current->pNext = new Echelon;
		current = current->pNext;
		current->data = data;
	}
	Size++;
	return 0;
}

template<typename T>
int MyList<T>::removeFirst() {
	if (head != nullptr) {
		Echelon *oldHead = head;
		head = head->pNext;
		delete oldHead;
		Size--;
		return 0;
	} else {
//		perror("The List is empty. You can't delete first element.");
		return -1;
	}
}

template<typename T>
int MyList<T>::removeByIndex(const int index) {
	if (index >= Size) {
//		perror("The List is empty. You can't delete elements.");
		return -1;
	}

	if (index == 0) {
		Echelon *current = head;
		head = head->pNext;
		delete current;
		Size--;
	} else {
		int counter = 0;
		Echelon *current = this->head;
		Echelon *last = this->head;
		while (counter != index) {
			last = current;
			current = current->pNext;
			counter++;
		}
		last->pNext = current->pNext;
		delete current;
		Size--;
	}
	return 0;
}

template<typename T>
void MyList<T>::removeAllByElement(T element) {
	while (true) {
		int ind = findIndexOfElement(element);
		if (ind == -1 or ind == -2)
			return;
		removeByIndex(ind);
	}
}

template<typename T>
void MyList<T>::removeAll() {
	while (Size) {
		removeFirst();
	}
}

template<typename T>
vector<int> MyList<T>::findAllIndexesOfElement(T element) {
	vector<int> indexes;
	if (Size != 0) {
		Echelon *current = head;
		int index = 0;
		while (true) {
			if (current->data == element)
				indexes.push_back(index);
			if (current->pNext == nullptr)
				break;
			index++;
			current = current->pNext;
		}
		return indexes;
	} else {
//		perror("The List is empty.");
		indexes.push_back(-1);
		return indexes;
	}
}

template<typename T>
int MyList<T>::findIndexOfElement(T element) {
	if (Size != 0) {
		Echelon *current = head;
		int index = 0;
		while (true) {
			if (current->data == element)
				return index;
			if (current->pNext == nullptr)
				break;
			index++;
			current = current->pNext;
		}
		return -2;
	} else {
//		perror("The List is empty.");
		return -1;
	}
}

template<typename T>
int MyList<T>::GetSize() {
	return Size;
}
template<typename T>
int MyList<T>::GetMaxSize() {
	return maxSize;
}

template<typename T>
int MyList<T>::print() {
	if (Size == 0) {
//		cout << "List is Empty" << endl;
		return -1;
	}
	Echelon *ech = head;
	while (ech != nullptr) {
		cout << ech->data << " ";
		ech = ech->pNext;
	}
	cout << endl;
	return 0;
}

template<typename T>
T& MyList<T>::operator[](const int index) {
	int counter = 0;
	Echelon *current = this->head;
	while (current != nullptr) {
		if (counter == index)
			return current->data;
		current = current->pNext;
		counter++;
	}
}
