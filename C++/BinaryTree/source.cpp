#include "head.h"

// Node::Node(int setData){
//     data = setData;
//     // parent = &parentNode;
//     leftBranch = NULL;
//     rightBranch = NULL;
// }

Node::Node(int setData, Node *parentNode){
    data = setData;
    parent = parentNode;
    leftBranch = NULL;
    rightBranch = NULL;
}

Node::~Node(){
}


Heap::Heap(){
    size = 0;
    bound = -1;

    root = NULL;
    last = NULL;
}

Heap::~Heap(){
}

void Heap::insert (int setData)
{
    // If the heap is empty, insert root node.
    if (root == NULL) {
        Node *node = new Node(setData, NULL);
        root = node;
        last = node;
        size++;
        return;
    }

    // We will be finding the node to insert below.

    // Start with the current last node and move up as long as the
    // parent exists and the current node is its right child.
    Node *cur = last;
    while (cur->parent != NULL && cur == cur->parent->rightBranch) {
        cur = cur->parent;
    }

    if (cur->parent != NULL) {
        if (cur->parent->rightBranch != NULL) {
            // The parent has a right child. Attach the new node to
            // the leftmost node of the parent's right subtree.
            cur = cur->parent->rightBranch;
            while (cur->leftBranch != NULL) {
                cur = cur->leftBranch;
            }
        } else {
            // The parent has no right child. This can only happen when
            // the last node is a right child. The new node can become
            // the right child.
            cur = cur->parent;
        }
    } else {
        // We have reached the root. The new node will be at a new level,
        // the left child of the current leftmost node.
        while (cur->leftBranch != NULL) {
            cur = cur->leftBranch;
        }
    }

    // Insert the new node, which becomes the new last node.
    
    Node *node = new Node(setData, *&cur);
    last = node;

    if (cur->leftBranch == NULL)
        cur->leftBranch = node;
    else
        cur->rightBranch = node;
    
    size++;
    
}


vector<int> Heap::addAlgorithm(int number){
	vector<int> alg;
	// cout << "Last number = " << size << endl;

    if (number == 0){
        return alg;
    }

	
	while(true){
		if (number == 1) {
			alg.push_back(1);
			break;
		} else if (number == 2){
			alg.push_back(0);
			break;
		} else {
			alg.push_back(number % 2);
			number = (number - 1) / 2;
		}
	}	
	reverse(alg.begin(), alg.end());
	return alg;
}


vector<int> Heap::goThrough(){
    vector<int> vecAr;
    vector<int> alg;

    if (root == NULL){
        vecAr.push_back(1);
        return vecAr;
    } else
        vecAr.push_back(0);
    
    
    vecAr.push_back(root->data);

    int i = 1;
    while (i <= size-1){
        Node *ptr = root;
        alg = addAlgorithm(i);
        while (alg.size() != 0){
            if(alg[0] == 1)
                ptr = ptr->leftBranch;
            else
                ptr = ptr->rightBranch;
            
            alg.erase(alg.begin());
        }
        // cout << "Data: " << ptr->data << endl;
        vecAr.push_back(ptr->data);
        i++;
    }

    return vecAr;
}

void Heap::printTree(){
    if (size == 0){
        cout << "Tree is empty" << endl << endl;
        return;
    }
    sortTree();
    vector<int> vecType = goThrough();
    if (vecType[0] == 1){
        cout << "Tree is empty" << endl << endl;
        return;
    }

    // for (int i = 0; i < vecType.size(); i++)
    //     cout << vecType[i] << " ";
    // cout << endl;
    // vecType.push_back(-1);
    // cout << vecType.size() << endl;
    // cout << vecType[0] << endl;
    // cout << "" << vecType[1] << endl;

    vecType.erase(vecType.begin());
    // cout << vecType[0] << endl;


    // cout << vecType.size() << endl;

    // for (int i = 0; i < vecType.size(); i++)
    //     cout << vecType[i] << " ";
    // cout << endl;

    // for (int i = 0; i < vecType.size(); i++)
    //     cout << vecType[i] << " ";
    // cout << endl;


    int layer = 0;
	int counter = size - 1;
	while (counter != 0){
		counter = (counter - 1)/2;
		layer++;
	}
	// layer++;
	// cout << "Last layer ===== " << layer << endl;

//Определяем количетсво знаков в последнем ряду
	int symb_amount = (pow(2,layer - 1) + (pow(2,layer - 1) - 1)) * 3;
	// cout << symb_amount << endl;
	
//Начальная табуляция на разных уровнях
	vector<int> tabsBegin;
	// tabs.push_back(0);
	int tab = symb_amount;
	for (int i = 0; i <= layer; i++){
		tab = ceil((float)tab/2) - 1;
		tabsBegin.push_back(tab);
		// cout << "Tab Begin: " << tab << endl;
	}

//определяем отступы между элементами ряда
	vector<int> tabsBetween;
	tabsBetween.push_back(0);
	for (int i = 1; i <= layer; i++){
		// cout << symb_amount << " " << tabsBegin[i] << " " << pow(2,i) << endl;
		tab = (symb_amount - 2*tabsBegin[i] - pow(2, i)) / (pow(2, i) - 1);
		tabsBetween.push_back(tab);
		// cout << "Tab Between: " << tab << endl;
	}

//Выводим дерево
	cout << endl;
	counter = 0;
	for (int i = 0; i <= layer - 1; i++){
		for (int k = 0; k <= tabsBegin[i] - 1; k++)
			cout << " ";
		for (int n = 0; n <= pow(2,i) - 1; n++){
			cout << vecType[counter];
			counter++;
			for (int k = 0; k <= tabsBetween[i] - 1; k++)
				cout << " ";
		}
		cout << endl;
	}
//Печатаем последний слой
    // for (int i = 0; i < vecType.size(); i++)
    //     cout << vecType[i];
    // cout << endl;

	int last_tabs = 1;
	for (int i = counter; i <= size - 1; i++){
		cout << vecType[i];
		for (int k = 0; k <= last_tabs - 1; k++)
			cout << " ";
		if (last_tabs == 1)
			last_tabs = 3;
		else
			last_tabs = 1;
	}

	cout << endl << endl;
	// cout << "Last number = " << size << endl;
}




void Heap::sortTree(){
    int counter = size-1;
    vector<int> alg;
    Node *curLast;
    Node *cur;

    int tempData;
    while (counter != 0){
        int curNum = counter;

        curLast = root;
        alg = addAlgorithm(counter);
        while (alg.size() != 0){
            if (alg[0] == 1)
                curLast = curLast->leftBranch;
            else
                curLast = curLast->rightBranch;
            alg.erase(alg.begin());
        }

        while(curNum != 0){
            
            cur = root;
            alg = addAlgorithm(curNum);
            while (alg.size() != 0){
                if (alg[0] == 1)
                    cur = cur->leftBranch;
                else
                    cur = cur->rightBranch;
                alg.erase(alg.begin());
            }


            if (cur->parent != NULL)
                cur = cur->parent;

            if ((cur->leftBranch != 0) && ((curNum/2 - !(curNum%2))*2 + 1 <= counter) && (cur->leftBranch->data > cur->data)){
                tempData = cur->data;
                cur->data = cur->leftBranch->data;
                cur->leftBranch->data = tempData;
            }

            if ((cur->rightBranch != 0) && ((curNum/2 - !(curNum%2))*2 + 2 <= counter) && (cur->rightBranch->data > cur->data)){
                tempData = cur->data;
                cur->data = cur->rightBranch->data;
                cur->rightBranch->data = tempData;
            }

            curNum = curNum-1;

            // printTree();
        }

        tempData = root->data;
        root->data = curLast->data;
        curLast->data = tempData;

        counter--;

        // printTree();
    }


}


int Heap::remove(int remData){
    if (last == NULL){
        // cout << "Tree is empty" << endl;
        return -1;
    }

    int counter = 0;
    vector<int> alg;
    Node *cur;
    while (counter <= size-1){
        if (last->data == remData){
            if (last->parent == NULL){
                delete last;
                root = NULL;
                last = NULL;
                cout << endl << "Tree is empty now" << endl << endl;
                return 1;
            }

            if (last->parent->leftBranch == last){
                Node *tempNode = last;
                last->parent->leftBranch = NULL;
                delete tempNode;
            } else {
                Node *tempNode = last;
                last->parent->rightBranch = NULL;
                delete tempNode;
            }

            last = root;

            size--;
            vector<int> alg = addAlgorithm(size-1);
            while (alg.size() != 0){
                if (alg[0] == 1)
                    last = last->leftBranch;
                else
                    last = last->rightBranch;
                alg.erase(alg.begin());
            }
            continue;
        }

        if (root->data == remData){
            root->data = last->data;
            last->data = remData;
        } else if (counter != 0){
            cur = root;
            alg = addAlgorithm(counter);
            while (alg.size() != 0){
                if (alg[0] == 1)
                    cur = cur->leftBranch;
                else
                    cur = cur->rightBranch;
                alg.erase(alg.begin());
            }
            if (cur->data == remData){
                cur->data = last->data;
                last->data = remData;
            }
        }
        counter++;
    }
    return 1;
}


int Heap::setBound(int newBound){
	if (newBound == -1){
		bound = newBound;
		return 0;
	}

	newBound;
	if ((newBound < 1) || (newBound < size))
		return -1;
	bound = newBound;
	return 0;
}

int Heap::checkBound(){
	if ((bound == -1) || (size < bound))
		return 1;
	else
		return 0;
}

int Heap::getSize(){
    return size;
}

int Heap::getBound(){
    return bound;
}


int Heap::findIdValue(int id){
    if (size == 0)
        return -2;
	if ((id < 0) || (id >= size))
		return -1;

	vector<int> alg = addAlgorithm(id);

	// cout << "Algorithm:   ";
	// for (int k = 0; k < alg.size(); k++)
	// 	cout << alg[k] << " ";
	// cout << endl;

    Node *cur = root;

	if (id > 0) {
        while (alg.size() != 0){
            if (alg[0] == 1) {
                alg.erase(alg.begin());
                cur = cur->leftBranch;
            } else {
                alg.erase(alg.begin());
                cur = cur->rightBranch;
	        }
        }
    }
    
    
	cout << "Value == " << cur->data << endl << endl;
	return cur->data;
}


vector<int> Heap::findValueId(int value){
	vector<int> ids;
	vector<int> alg;


	if (size == 0){
		ids.push_back(-2);
        return ids;
    }
	else {
        int counter = 0;
        while (counter < size){
            Node *cur = root;
            alg = addAlgorithm(counter);
            while (alg.size() != 0){
                if (alg[0] == 1)
                    cur = cur->leftBranch;
                else
                    cur = cur->rightBranch;
                alg.erase(alg.begin());
            }

            if (cur->data == value)
                ids.push_back(counter);
            counter++;
        }
    }

    if (ids.size() == 0)
        ids.push_back(-1);
    else{
        cout << "Indexes: ";
        for (int i = 0; i <= ids.size()-1; i++)
            cout << ids[i] << " ";
        cout << endl << endl;
    }
	return ids;
}

