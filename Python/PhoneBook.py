import datetime
import re
import os
import pickle

pickleFileLink = r"C:\Users\newnotebook\Desktop\PhoneBook\PhoneBook.pickle"
txtFileLink = r"C:\Users\newnotebook\Desktop\PhoneBook\PhoneBook.txt"


def savePhoneBook(phoneBook):
    with open(pickleFileLink, 'wb') as pickleFile:
        pickle.dump(phoneBook, pickleFile)
        createTxt(phoneBook)


def createTxt(phoneBook):
    global txtFileLink
    with open(txtFileLink, 'w') as txtFile:
        for surnameKey in phoneBook:
            for nameKey in phoneBook[surnameKey]:
                data = (surnameKey, nameKey, phoneBook[surnameKey][nameKey][0], phoneBook[surnameKey][nameKey][1])
                print(*data, file=txtFile, sep='; ')
    # os.startfile(txtFileLink)


def getBookFromPickle():
    global pickleFileLink
    try:
        with open(pickleFileLink, 'rb') as pickleFile:
            try:
                phoneBook = pickle.load(pickleFile)
            except EOFError:
                phoneBook = {}
    except FileNotFoundError:
        phoneBook = {}
    return phoneBook


def showPhoneBookInConsole(phoneBook):
    print()
    for surnameKey in phoneBook:
        for nameKey in phoneBook[surnameKey]:
            print(surnameKey, ' ', nameKey, ' --> Phone number: ', phoneBook[surnameKey][nameKey][0], '; ',
                  'Date of birth: ', sep='', end='')
            if phoneBook[surnameKey][nameKey][1] == '-':
                print('There is no date of birth')
            else:
                print(phoneBook[surnameKey][nameKey][1], ' ', end='')
                dateList = phoneBook[surnameKey][nameKey][1].split('.')
                global now
                if (now.month - int(dateList[1]) >= 0) and (now.day - int(dateList[0]) >= 0):
                    print('(', now.year - int(dateList[2]), ' years)', sep='')
                else:
                    print('(', now.year - int(dateList[2]) - 1, ' years)', sep='')
    print()


def clearPhoneBook(phoneBook):
    global pickleFileLink, txtFileLink
    with open(pickleFileLink, 'wb'):
        pass
    with open(txtFileLink, 'w'):
        pass
    print()
    print('PhoneBook content was deleted!', end='\n\n')
    return {}


def addNewContact(phoneBook):
    surname = addSurname(phoneBook)
    if surname == 0:
        return phoneBook

    name = addName(phoneBook)
    if name == 0:
        return phoneBook

    number = addNumber(phoneBook)
    if number == 0:
        return phoneBook

    birthDate = addBirthDate()
    if birthDate == 0:
        return phoneBook

    if isUnic(phoneBook, surname, name):
        if surname not in phoneBook:
            phoneBook[surname] = {}
        phoneBook[surname][name] = (number, birthDate)
        print()
        print("Contact was Created!", end='\n\n')
    else:
        print()
        print("Contact with such Surname and Name already exist", end='\n\n')
    return phoneBook


def deleteContact(phoneBook):
    surname = addSurname(phoneBook)
    name = addName(phoneBook)
    if contactInBook(phoneBook, surname, name):
        if len(phoneBook[surname]) == 1:
            phoneBook.pop(surname)
            print()
            print('The contact was deleted!', end='\n\n')
        else:
            phoneBook[surname].pop(name)
            print()
            print('The contact was deleted!', end='\n\n')
    else:
        print()
        print("There is no such Contact", end='\n\n')

    return phoneBook


def changeContact(phoneBook):
    print('Whom do u want to change?')
    surname = addSurname(phoneBook)
    name = addName(phoneBook)
    if contactInBook(phoneBook, surname, name) == 0:
        print('There is no such Contact.')
        return phoneBook
    while True:
        print()
        print('What do you want to change? (Without ' ')')
        print(''''surname' - To change Surname
'name' - To change Name
'phone' - To change Phone Number
'date' - To change Date of Birth
'cancel' - To cancel Changing
''')
        print('Your choice --> ', end='')
        answer = input().lower()
        if answer == 'surname':
            newSurname = addSurname(phoneBook)
            phoneBook[newSurname] = phoneBook.pop(surname)
            surname = newSurname
        elif answer == 'name':
            newName = addName(phoneBook)
            phoneBook[surname][newName] = phoneBook[surname].pop(name)
            name = newName
        elif answer == 'phone':
            newNumber = addNumber(phoneBook)
            phoneBook[surname][name][0] = newNumber
        elif answer == 'date':
            newDate = addBirthDate()
            phoneBook[surname][name][1] = newDate
        elif answer == 'cancel':
            return phoneBook
        print('\nThe Contact was changed\n')

        while True:
            print('Do you want to change something else (Yes or No)? --> ', end='')
            answer = input().lower()
            if answer == 'yes':
                print()
                break
            elif answer == 'no':
                return phoneBook
            else:
                print('There is no such command. Repeat: ')


def findContact(phoneBook):
    if phoneBook == {}:
        print('The PhoneBook is empty')
        return
    answer = ""
    while answer != 'quit':
        print('''To find by
1 - Surname
2 - Name
3 - Number
4 - Surname and Name
quit - To exit

Your choice --> ''', end='')
        answer = input()
        print()

        if answer == 'quit':
            return
        if answer == '1':
            surname = addSurname(phoneBook)
            if surname in phoneBook:
                counter = 1
                for name in phoneBook[surname]:
                    print(counter, end='. ')
                    printPerson(phoneBook, surname, name)
                    counter += 1
            else:
                print("There is no such Contact")

        elif answer == '2':
            flag = True
            name = addName(phoneBook)
            counter = 1
            for surname in phoneBook:
                if name in phoneBook[surname]:
                    print(counter, end='. ')
                    printPerson(phoneBook, surname, name)
                    counter += 1
            if flag:
                print("There is no such Contact")

        elif answer == '3':
            flag = True
            number = addNumber(phoneBook)
            counter = 1
            for surname in phoneBook:
                for name in phoneBook[surname]:
                    if phoneBook[surname][name][0] == number:
                        print(counter, end='. ')
                        printPerson(phoneBook, surname, name)
                        counter += 1
                        flag = False
            if flag:
                print("There is no such Contact")
        elif answer == '4':
            surname = addSurname(phoneBook)
            name = addName(phoneBook)
            if contactInBook(phoneBook, surname, name):
                printPerson(phoneBook, surname, name)
            else:
                print('There is no such Contact')
        print()


def contactInBook(phoneBook, surname, name):
    if (surname in phoneBook) and (name in phoneBook[surname]):
        return 1
    else:
        return 0


def printPerson(phoneBook, surname, name):
    print(surname, name)
    print('Phone Number:', phoneBook[surname][name][0])
    print('Date of Birth: ', phoneBook[surname][name][1], sep='', end='')
    if phoneBook[surname][name][1] != '-':
        dateList = phoneBook[surname][name][1].split('.')
        global now
        if (now.month - int(dateList[1]) >= 0) and (now.day - int(dateList[0]) >= 0):
            print(' (', now.year - int(dateList[2]), ' years)', sep='')
        else:
            print('(', now.year - int(dateList[2]) - 1, ' years)', sep='')
    print()


def isUnic(phoneBook, surname, name):
    if (surname not in phoneBook) or (name not in phoneBook[surname]):
        return 1
    else:
        return 0


def addSurname(phoneBook):
    while True:
        print("Enter a surname in Latin characters --> ", end='')
        surname = input().lower().title()
        if re.search(r'[^a-zA-Z 1-9]', surname):
            print()
            print("The surname have to contain only figures and Latin characters.")
            while True:
                print("Do u want to repeat? (Yes или No) -- > ",
                    end='')
                answer = input().lower()
                if answer == 'yes':
                    break
                elif answer == 'no':
                    return 0
                else:
                    print('There is no such command. Repeat: ')
        else:
            break
    return surname


def addName(phoneBook):
    while True:
        print("Enter a name in Latin characters --> ", end='')
        name = input().lower().title()
        if re.search(r'[^a-zA-Z 1-9]', name):
            print()
            print("The name have to contain only figures and Latin characters. ")
            while True:
                print("Do u want to repeat?? (Yes или No) -- > ",
                    end='')
                answer = input().lower()
                if answer == 'yes':
                    break
                elif answer == 'no':
                    return 0
                else:
                    print('There is no such command. Repeat: ')
        else:
            break
    return name


def addNumber(phoneBook):
    while True:
        print("Enter a Phone Number --> ", end='')
        number = input()
        number = verifyNumber(number)
        if number == 0:
            print("Invalid phone number format (89******** or +79*********). ")
            while True:
                print("Do you want to repeat? (Yes or No) --> ",
                    end='')
                answer = input().lower()
                if answer == 'yes':
                    break
                elif answer == 'no':
                    return 0
                else:
                    print('There is no such command. Repeat: ')
        else:
            break
    return number


def addBirthDate():
    while True:
        print("Enter a date of birth in 'dd.mm.yyyy' format (Unnecessary, else '-') --> ", end='')
        birthDate = input()
        if birthDate == '-':
            return birthDate
        dateElements = birthDate.split('.')
        flag = True

        if len(dateElements) == 3:
            if len(dateElements[0]) != 2 or len(dateElements[1]) != 2 or len(dateElements[2]) != 4:
                print('Look at the date format: dd.mm.yyyy !')
                flag = False
            elif not 1900 <= int(dateElements[2]) <= 2019:
                print('Dude, we need a real year :)')
                flag = False
            elif not 1 <= int(dateElements[1]) <= 12:
                print('Such month is not exist')
                flag = False
            elif int(dateElements[1]) == 2:
                if (int(dateElements[2]) % 400 == 0 and int(dateElements[2]) % 100 == 0) or (
                        int(dateElements[2]) % 4 == 0 and int(dateElements[2]) % 100 != 0):
                    if not (1 <= int(dateElements[0]) <= 29):
                        print('Такого дня не существует')
                        flag = False
                elif not (1 <= int(dateElements[0]) <= 28):
                    print('Such Day is not exist')
                    flag = False
            elif int(dateElements[1]) in {1, 3, 5, 7, 8, 10, 12}:
                if not (1 <= int(dateElements[0]) <= 31):
                    print('Such Day is not exist')
                    flag = False
            elif int(dateElements[1]) in {4, 6, 9, 11}:
                if not (1 <= int(dateElements[0]) <= 30):
                    print('Such Day is not exist')
                    flag = False
        else:
            print("Invalid format. It have to look like 'dd.mm.yyyy'")
            flag = False
        if flag:
            return birthDate

        while True:
            print('Do you want to repeat? (Yes or No) --> ', end='')
            answer = input().lower()
            if answer == 'yes':
                break
            elif answer == 'no':
                return 0
            else:
                print('There is no such command. Repeat: ')


def verifyNumber(number):
    if len(number) == 11:
        if number[0] == '8':
            return number
        else:
            return '8' + number[1:]
    elif (number[0] == '+' and number[1] == '7' and len(number) == 12):
        return '8' + number[2:]
    else:
        return 0


if __name__ == '__main__':
    now = datetime.datetime.now()
    phoneBook = getBookFromPickle()
    answer = ''
    while answer != 'quit':
        print('''Menu:
1 - Create Contact
2 - Delete Contact
3 - Find Contact
4 - Change Contact
5 - Show PhoneBook in text editor 
6 - Show PhoneBook in Console
7 - Delete PhoneBook content
quit - To exit

Your Choice --> ''', end='')
        answer = input()
        if answer == '1':
            phoneBook = addNewContact(phoneBook)
        elif answer == '2':
            phoneBook = deleteContact(phoneBook)
        elif answer == '3':
            findContact(phoneBook)
        elif answer == '4':
            phoneBook = changeContact(phoneBook)
        elif answer == '5':
            createTxt(phoneBook)
            os.startfile(txtFileLink)
        elif answer == '6':
            showPhoneBookInConsole(phoneBook)
        elif answer == '7':
            phoneBook = clearPhoneBook(phoneBook)
        else:
            print('There is no such command\n')
    savePhoneBook(phoneBook)
