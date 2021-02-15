import java.io.*;
import java.util.*;

public class Main {

    static String dbName = null;
    static String[] columns = null;
    static String[] types = null;
    static int serviceRowsNumber = 2;


    public static void printSuccessfulMessage(String message) {
        System.out.println("\n--------------------");
        System.out.println("Successful!");
        System.out.println(message);
        System.out.println("--------------------\n");
    }

    public static void printUnsuccessfulMessage(String message) {
        System.out.println("\n!--------------------!");
        System.out.println("Unsuccessful!");
        System.out.println(message);
        System.out.println("!--------------------!\n");
    }


    public static void printWarningMessage(String message) {
        System.err.println("\n" + message + "\n");
    }


    public static int createDB() {
        String dbName = "database.txt";
        String[] defColumns = new String[]{"Id", "Name", "Average_Mark", "isRegistered"};
        String[] defTypes = new String[]{"int", "string", "float", "boolean"};
        return createDB(dbName, defColumns, defTypes);
    }


    public static int createDB(String dbName, String[] columns, String[] types) {
        File file = new File(dbName);
        if (!file.exists()) {
            System.out.println("Let's create DB");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                if (columns.length != types.length) {
                    throw new DatabaseException("\"Columns\" length have to be equal to \"Types\" length");
                }

                file.createNewFile();

                bw.write(String.join(",", columns) + "\n");
                bw.write(String.join(",", types));

                Main.dbName = dbName;
                Main.types = types;
                Main.columns = columns;

                return 1; ///////////////////////Database have been created successfully!

            } catch (IOException ioe) {
                System.err.println(ioe);
                System.exit(-1);
            } catch (DatabaseException de) {
                printWarningMessage(de.getMessage());
            }

            return 0; ///////////////////////DB wasn't created
        } else {
            return -1; ///////////////////////DB is already created
        }
    }


    public static int openDB() {
        String dbName = "database.txt";
        return openDB(dbName);
    }


    public static int openDB(String dbName) {
        File file = new File(dbName);

        if (!file.exists())
            return -1; ////////////There is no such DataBase!

        if (dbName == Main.dbName) {
            return -2; ///////////////////////// DB is already opened
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            if (file.length() == 0) {
                throw new DatabaseException("Incorrect File!");
            }

            String columns = br.readLine();
            String line;

            if ((line = br.readLine()) == null) {
                throw new DatabaseException("Incorrect File!");
            }

            Main.columns = columns.split(",");
            Main.types = line.split(",");
            Main.dbName = dbName;

            return 1; //////////Opened!
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(-1);
        } catch (DatabaseException de) {
            printWarningMessage(de.getMessage());
        }

        return 0; ////////Errors in opening!
    }


    public static int isDBOpen() {
        if (Main.dbName == null)
            return 0;
        return 1;
    }


    public static int isDBOpen(String dbName) {
        if ((Main.dbName == null) || (Main.dbName.equals(dbName) == false))
            return 0;
        return 1;
    }


    static public int deleteDB() {
        String dbName = Main.dbName;
        return deleteDB(dbName);
    }


    static public int deleteDB(String dbName) {
        if (isDBOpen(dbName) == 0) {
            return -1; //////////////////Database wasn't opened
        }

        File file = new File(dbName);

        if (file.delete()) {
            Main.dbName = null;
            Main.columns = null;
            Main.types = null;

            return 1; /////////////Deleted
        } else {
            printWarningMessage("Problem with deleting \"" + dbName + "\" in \"deleteDB\" method");
            System.exit(-1);
        }
        return 0; ////////////Problems
    }


    public static int addNewRecord() {
        if (isDBOpen() == 0)
            return -1; ///////////////Database wasn't opened!

        BufferedReader br = null;
        BufferedWriter bw = null;

        Scanner scanner = new Scanner(System.in);
        String tempFileName = "temp_" + Main.dbName;
        File tempFile = null;

        try {
            System.out.print("Enter new Record -> ");
            String newRecord = scanner.nextLine();
//            String newRecord = "7,Semyon,4.4,true";

            String[] newRecordArray = newRecord.split(",");

            int newKeyIndex = 0;
            validateRecord(newRecordArray);

            File dbFile = new File(Main.dbName);
            br = new BufferedReader(new FileReader(dbFile));

            String line;
            for (int i = 0; i < Main.serviceRowsNumber; i++)
                br.readLine();
            line = br.readLine();

            if (line == null) {
                newKeyIndex = -1;
            } else {
                tempFile = new File(tempFileName);

                if (!tempFile.exists())
                    tempFile.createNewFile();
                else {
                    bw = new BufferedWriter(new FileWriter(tempFile, false));
                    bw.write("");
                    bw.close();
                }

                bw = new BufferedWriter(new FileWriter(tempFile));
                bw.write(line);
                while ((line = br.readLine()) != null) {
                    bw.write("\n");
                    bw.write(line);
                }
                bw.close();

                newKeyIndex = fileBinarySearch(tempFileName, Main.columns[0], newRecordArray[0], false, false);

                if (newKeyIndex == -1)
                    return -2; ///////Such key already exists. You can't add this record!

//                pKeysArr.add(newKeyIndex, newRecordArray[0]); //Изменяем массив ключей, ставим новый ключ в нужном порядке
            }

            br.close();

            reWriteDB(newRecord, newKeyIndex);

            return 1;
        } catch (DatabaseException de) {
            printWarningMessage(de.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            boolean doExit = false;

            try {
                if (bw != null)
                    bw.close();
            } catch (IOException ioe) {
                printWarningMessage("Something wrong with closing of BufferedWriter in \"findRecord\" method");
                doExit = true;
            }

            try {
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                printWarningMessage("Something wrong with closing of BufferedReader in \"findRecord\" method");
                doExit = true;
            }

            if (tempFile != null && tempFile.exists())
                if(!tempFile.delete()) {
                    printWarningMessage("Something wrong with deleting of \"" + tempFileName + "\" in \"findRecord\" method");
                    doExit = true;
                }


            if (doExit)
                System.exit(-1);
        }

        return 0;
    }

    public static void reWriteDB(String newRecord, int newKeyIndex)
            throws IOException {

        File dbFile = new File(Main.dbName);
        File tempDbFile = new File("temp_" + Main.dbName);
        BufferedReader br = new BufferedReader(new FileReader(dbFile));
        PrintWriter pw = new PrintWriter(new FileWriter(tempDbFile));

        try {
            String line;

            pw.println(String.join(",", Main.columns));
            pw.print(String.join(",", Main.types));
            if (newKeyIndex == -1) {
                pw.print("\n" + newRecord);
            } else {
                for (int i = 0; i < 2; i++)  //Пропускаем строки с названиями столбцов, их типами из основного файла
                    br.readLine();

                int lineNumber = 0;

                while ((line = br.readLine()) != null) {
                    pw.print("\n");
                    if (lineNumber == newKeyIndex) {
                        pw.print(newRecord);
                        pw.print("\n");
                    }
                    pw.print(line);
                    lineNumber++;
                }

                if (newKeyIndex == lineNumber) {
                    pw.print("\n");
                    pw.print(newRecord);
                }
            }

            if (pw != null)
                pw.close();
            if (br != null)
                br.close();

            if (!dbFile.delete())
                throw new IOException("reWriteDB: Problem with deleting temp file!");

            tempDbFile.renameTo(dbFile);

        } finally {
            if (pw != null)
                pw.close();

            try {
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                printWarningMessage("Something wrong with closing of BufferedReader in \"findRecord\" method");
                System.exit(-1);
            }
        }
    }

    public static void validateRecord(String[] record) throws DatabaseException {
        if (record.length != Main.types.length) {
            throw new DatabaseException("\"Record's length\" is incorrect!");
        }
        for (int i = 0; i < record.length; i++)
            checkLegalType(Main.types[i], record[i]);
    }

    public static void checkLegalType(String type, String value) throws DatabaseException {
        try {
            switch (type) {
                case "byte":
                    Byte.parseByte(value);
                    break;
                case "short":
                    Short.parseShort(value);
                    break;
                case "int":
                    Integer.parseInt(value);
                    break;
                case "float":
                    Float.parseFloat(value);
                    break;
                case "double":
                    Double.parseDouble(value);
                    break;
                case "boolean":
                    Boolean.parseBoolean(value);
                    break;
                case "char":
                    if (value.length() == 1)
                        value.charAt(0);
                    else
                        throw new NumberFormatException();
                    break;
                case "string":
                    break;
                default:
                    throw new DatabaseException("There is no such type: \"" + type + "\"");
            }

        } catch (NumberFormatException nfe) {
            throw new DatabaseException("Wrong format of " + "\"" + value + "\"" + ", it needed to be \"" +
                    type + "\"" + "! Every column variable have to have required type!");
        }
    }


    public static int editRecord() {

        if (isDBOpen() == 0)
            return -1;

        BufferedReader br = null;
        BufferedWriter bw = null;
        File mainFile = new File(Main.dbName);
        File tempFile = new File("temp_" + Main.dbName);


        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter old Record --> ");
            String oldRecord = scanner.nextLine();

            System.out.print("Enter new Record --> ");
            String newRecord = scanner.nextLine();

            String[] newRecordArr = newRecord.split(",");
            for (int i = 0; i < newRecordArr.length; i++)
                checkLegalType(Main.types[i], newRecordArr[i]);

            int pos = fileBinarySearch(Main.dbName, Main.columns[0], oldRecord.split(",")[0], true, true);
            if (pos == -1) {
                return -2; /////////////////There is no such row to edit!
            }

            br = new BufferedReader(new FileReader(mainFile));
            for (int i = 0; i < Main.serviceRowsNumber; i++)
                br.readLine();
            for (int i = 0; i < pos; i++)
                br.readLine();
            if (oldRecord.equals(br.readLine()) == false)
                return -2;
            br.close();

            String oldId = oldRecord.split(",")[0];
            String newId = newRecord.split(",")[0];
            if (newId.equals(oldId) == false) {
                int newPos = fileBinarySearch(Main.dbName, Main.columns[0], newId, true, true);
                if (newPos != -1) {
                    return -3; /////////////////Row with such ID already exists!
                }
            }

            br = new BufferedReader(new FileReader(mainFile));
            bw = new BufferedWriter(new FileWriter(tempFile));

            bw.write(br.readLine());
            String line = br.readLine();

            while (line.equals(oldRecord) == false) {
                bw.write("\n");
                bw.write(line);
                line = br.readLine();
            }
            if (newId.equals(oldId)) {
                bw.write("\n");
                bw.write(newRecord);
            }
            while ((line = br.readLine()) != null) {
                bw.write("\n");
                bw.write(line);
            }

            br.close();
            bw.close();

            if (!mainFile.delete()) {
                throw new IOException("\nSomething wrong with deleting \"" + mainFile.getName() + "\" file in \"editRecord\" method");
            }

            tempFile.renameTo(mainFile);


            if (newId.equals(oldId) == false)
                addNewRecord();

            return 1; /////////////////Edited!
        } catch (DatabaseException de) {
            printWarningMessage(de.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            boolean doExit = false;

            try {
                if (bw != null)
                    bw.close();
            } catch (IOException ioe) {
                printWarningMessage("Something wrong with closing of BufferedWriter in \"editRecord\" method");
                doExit = true;
            }

            try {
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                printWarningMessage("Something wrong with closing of BufferedReader in \"editRecord\" method");
                doExit = true;
            }

            if ((tempFile.exists()) && (tempFile.getName().equals(mainFile.getName()) == false))
                if (!tempFile.delete()) {
                    printWarningMessage("Something wrong with deleting of \"" + tempFile.getName() + "\" in \"editRecord\" method");
                    doExit = true;
                }
            if (doExit)
                System.exit(-1);
        }

        return 0;
    }


    public static int deleteRecord() {
        if (isDBOpen() == 0)
            return -1;

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nColumns: " + String.join(",", Main.columns));
        System.out.println("Types: " + String.join(",", Main.types));
        System.out.print("Enter a comma-separated list of \"Column\" and \"Value\" that are needed to delete -> ");
        String[] recordToDelete = scanner.nextLine().split(",");

                String sortedFileName = "sorted_" + Main.dbName;
        File sortedFile = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            if (recordToDelete.length != 2) {
                throw new DatabaseException("Incorrect input!");
            }

            sortedFile = new File(sortedFileName);
            if (!sortedFile.exists())
                sortedFile.createNewFile();
            else {
                FileWriter fw = new FileWriter(sortedFile);
                fw.write("");
                fw.close();
            }

            String ind = findRecord(recordToDelete[0], recordToDelete[1]);

            if (ind.equals(""))
                return -1;

            String[] stringIndexes = ind.split(",");
            int[] indexes = new int[stringIndexes.length];
            for (int i = 0; i < indexes.length; i++)
                indexes[i] = Integer.parseInt(stringIndexes[i]);

            File mainDB = new File(Main.dbName);
            bw = new BufferedWriter(new FileWriter(mainDB, false));
            bw.write("");
            bw.close();

            br = new BufferedReader(new FileReader(sortedFileName));
            bw = new BufferedWriter(new FileWriter(mainDB));

            bw.write(String.join(",", Main.columns) + "\n");
            bw.write(String.join(",", Main.types));


            String line = br.readLine();
            int lineNum = 0;
            while (line != null) {
                if (!(lineNum >= indexes[0] && lineNum <= indexes[1])) {
                    bw.write("\n");
                    bw.write(line);
                }
                line = br.readLine();
                lineNum++;
            }
            bw.close();
            br.close();

            fileSort(Main.columns[0], true);
            return 1; ///////////Deleted!
        } catch (DatabaseException de) {
            printWarningMessage(de.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            boolean doExit = false;

            try {
                if (bw != null)
                    bw.close();
            } catch (IOException ioe) {
                printWarningMessage("Something wrong with closing of BufferedWriter in \"deleteRecord\" method");
                doExit = true;
            }

            try {
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                printWarningMessage("Something wrong with closing of BufferedReader in \"deleteRecord\" method");
                doExit = true;
            }

            if (sortedFile != null && sortedFile.exists())
                if (!sortedFile.delete()) {
                    printWarningMessage("Something wrong with deleting \"" + sortedFile.getName() + "\" file in \"deleteRecord\" method");
                    doExit = true;
                }

            if (doExit)
                System.exit(-1);
        }

        return 0;
    }


    public static int findRecord() {
        if (isDBOpen() == 0)
            return -1;

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nColumns: " + String.join(",", Main.columns));
        System.out.println("Types: " + String.join(",", Main.types));
        System.out.print("Enter a comma-separated list of \"Column\" and \"Value\" that are needed to find -> ");
        String[] valueToFind = scanner.nextLine().split(",");

        String sortedFileName = "sorted_" + Main.dbName;
        File sortedFile = null;
        BufferedReader br = null;

        try {
            sortedFile = new File(sortedFileName);
            if (!sortedFile.exists())
                sortedFile.createNewFile();
            else {
                FileWriter fw = new FileWriter(sortedFile);
                fw.write("");
                fw.close();
            }

            String ind = findRecord(valueToFind[0], valueToFind[1]);

            if (ind.equals(""))
                return -2;

            String[] stringIndexes = ind.split(",");
            int[] indexes = new int[stringIndexes.length];
            for (int i = 0; i < indexes.length; i++)
                indexes[i] = Integer.parseInt(stringIndexes[i]);
//            indexes.split(",");

            br = new BufferedReader(new FileReader(sortedFileName));
            for (int i = 0; i < indexes[0]; i++) {
                br.readLine();
            }

            System.out.println("\n---------------------------");
            System.out.println("Founded " + (indexes[1] - indexes[0] + 1) + " rows");
            for (int i = indexes[0]; i <= indexes[1]; i++) {
                System.out.println(br.readLine());
            }
            System.out.println("---------------------------\n");

            return 1;

//            System.out.println(ind);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DatabaseException de) {
            printWarningMessage(de.getMessage());
        } finally {
            boolean doExit = false;

            try {
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                printWarningMessage("Something wrong with closing of BufferedReader in \"findRecord\" method");
                doExit = true;
            }

            if (sortedFile != null && sortedFile.exists())
                if (!sortedFile.delete()) {
                    printWarningMessage("Something wrong with deleting \"" + sortedFile.getName() + "\" file in \"findRecord\" method");
                    doExit = true;
                }

            if (doExit)
                System.exit(-1);
        }

        return 0;
    }

    public static String findRecord(String column, String value) throws DatabaseException, IOException {   //Private
        boolean doExit = false;
        String sortedFileName = "sorted_" + Main.dbName;
        File sortedFile = null;
        BufferedReader br = null;


        try {

            int column_ind = -1;
            for (int i = 0; i < Main.columns.length; i++) {
                if (column.equals(Main.columns[i])) {
                    column_ind = i;
                    break;
                }
            }
            if (column_ind == -1) {
                throw new DatabaseException("There is no such column \"" + column + "\"");
            }

            checkLegalType(Main.types[column_ind], value);

            sortedFile = new File(sortedFileName);

            fileSort(column, false);
            int mainElementIndex = fileBinarySearch(sortedFileName, column, value, false, true);


            if (mainElementIndex < 0)
                return "";

            int leftBorder = 0;
            int rightBorder = mainElementIndex;
            while (leftBorder < rightBorder) {
                br = new BufferedReader(new FileReader(sortedFile));
                for (int i = 0; i < (leftBorder + rightBorder) / 2; i++) {
                    br.readLine();
                }
                if (br.readLine().split(",")[column_ind].equals(value)) {
                    rightBorder = (leftBorder + rightBorder) / 2;
                } else {
                    leftBorder = ((leftBorder + rightBorder) / 2) + 1;
                }
                br.close();
            }

            int leftIndex = leftBorder;

            leftBorder = mainElementIndex + 1;
            br = new BufferedReader(new FileReader(sortedFile));
            rightBorder = -1;
            while (br.readLine() != null)
                rightBorder++;
            br.close();

            while (leftBorder <= rightBorder) {
                br = new BufferedReader(new FileReader(sortedFile));
                for (int i = 0; i < (leftBorder + rightBorder) / 2; i++) {
                    br.readLine();
                }
                if (br.readLine().split(",")[column_ind].equals(value)) {
                    leftBorder = ((leftBorder + rightBorder) / 2) + 1;
                } else {
                    rightBorder = ((leftBorder + rightBorder) / 2) - 1;
                }
                br.close();
            }

            int rightIndex = rightBorder;

            String indexes = "";
            indexes += leftIndex + "," + rightIndex;
            return indexes;

        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                System.err.println(ioe);
                System.exit(-1);
            }
        }
    }

    public static void fileSort(String column, boolean replaceMainDB) throws DatabaseException, IOException {

        BufferedWriter bw = null;
        BufferedReader br = null;
        File mainFile = null;
        File sortedFile = null;
        File tempFile = null;
        try {
            if (isDBEmpty()) {
                throw new DatabaseException("Database is empty!");
            }

            int column_ind = -1;
            for (int i = 0; i < Main.columns.length; i++) {
                if (column.equals(Main.columns[i])) {
                    column_ind = i;
                    break;
                }
            }

            int totalQuantity = 0;
            mainFile = new File(Main.dbName);
            sortedFile = new File("sorted_" + Main.dbName);
            tempFile = new File("temp_" + sortedFile.getName());
            br = new BufferedReader(new FileReader(mainFile));
            //Создаём временный файл, который будет заменять текущий файл после части сортировки


            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }

            bw = new BufferedWriter(new FileWriter(sortedFile));
            String line = "";

            for (int i = 0; i < Main.serviceRowsNumber; i++) //Пропускаем 2 служебные строки
                br.readLine();
            line = br.readLine();

            while (line != null) {
                totalQuantity++;
                if (totalQuantity > 1)
                    bw.write("\n");
                bw.write(line);
                line = br.readLine();
            }
            br.close();
            bw.close();

            br = new BufferedReader(new FileReader(sortedFile));
            if (totalQuantity == 1) {
                line = br.readLine();
            } else
                sorting(0, totalQuantity - 1, totalQuantity, column_ind);
            br.close();

            if (replaceMainDB == true) {
                bw = new BufferedWriter(new FileWriter(mainFile, false));
                bw.write("");
                bw.close();

                bw = new BufferedWriter(new FileWriter(mainFile));
                br = new BufferedReader(new FileReader(sortedFile));

                bw.write(String.join(",", Main.columns) + "\n");
                bw.write(String.join(",", Main.types));

                line = br.readLine();
                while (line != null) {
                    bw.write("\n");
                    bw.write(line);
                    line = br.readLine();
                }
                bw.close();
                br.close();
            }

        } finally {
            boolean doExit = false;
            try {
                if (bw != null)
                    bw.close();
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                printWarningMessage("Something wrong with closing files in \"FileSort\" method");
                doExit = true;
            }

            if (tempFile != null && tempFile.exists())
                if (!tempFile.delete()) {
                    printWarningMessage("Something wrong with deleting \"" + tempFile.getName() + "\" file");
                    doExit = true;
                }

            if (doExit) {
                if (sortedFile != null && sortedFile.exists())
                    if (!sortedFile.delete()) {
                        printWarningMessage("Something wrong with deleting \"" + sortedFile.getName() + "\" file");
                    }
                System.exit(-1);
            }
        }
    }

    public static void sorting(int leftBorder, int rightBorder, int totalQuantity, int column_ind)
            throws IOException {
        if (rightBorder - leftBorder <= 0)
            return;

        int middle = (leftBorder + rightBorder) / 2;
        sorting(leftBorder, middle, totalQuantity, column_ind);
        sorting(middle + 1, rightBorder, totalQuantity, column_ind);

        int marker1 = leftBorder;
        int marker2 = middle + 1;

        BufferedWriter bw = new BufferedWriter(new FileWriter("temp_sorted_" + Main.dbName, true));

        BufferedReader br1 = new BufferedReader(new FileReader("sorted_" + Main.dbName));
        BufferedReader br2 = new BufferedReader(new FileReader("sorted_" + Main.dbName));
        String line1;
        String line2;

        for (int i = 0; i < marker1; i++)
            br1.readLine();
        for (int i = 0; i < marker2; i++)
            br2.readLine();

        line1 = br1.readLine();
        line2 = br2.readLine();

        for (int i = leftBorder; i <= rightBorder; i++) {
            if (marker1 > middle) {
                bw.write(line2);
                line2 = br2.readLine();
            } else if (marker2 > rightBorder) {
                bw.write(line1);
                line1 = br1.readLine();
            } else if (line1.split(",")[column_ind].compareTo(line2.split(",")[column_ind]) < 0) {
                bw.write(line1);
                marker1++;
                line1 = br1.readLine();
            } else {
                bw.write(line2);
                marker2++;
                line2 = br2.readLine();
            }
            if (i != rightBorder)
                bw.write("\n");
        }
        try {
            bw.close();
            br1.close();
            br2.close();
        } catch (IOException ioe) {
            throw new IOException("Channel wasn't closed in \"sorting\" method!");
        }

        br1 = new BufferedReader(new FileReader("sorted_" + Main.dbName));
        br2 = new BufferedReader(new FileReader("temp_sorted_" + Main.dbName));
        bw = new BufferedWriter(new FileWriter("temp_sorted_" + Main.dbName, true));

        marker1 = 0; //Отвечает за позицию в TempFile
        line1 = br1.readLine();
        line2 = br2.readLine(); //Отвечает за строку в TempFile

        for (int i = 0; i <= totalQuantity - 1; i++) {
            if ((i >= leftBorder) && (i <= rightBorder)) {
                bw.write("\n" + line2);
                line1 = br1.readLine();
                line2 = br2.readLine();
                marker1++;
            } else {
                bw.write("\n" + line1);
                line1 = br1.readLine();
            }
        }

        PrintWriter writer = new PrintWriter("sorted_" + Main.dbName);
        writer.print("");

        try {
            writer.close();
            bw.close();
            br2.close();

        } catch (IOException ioe) {
            throw new IOException("Channel wasn't closed in \"sorting\" method!");
        }

        br2 = new BufferedReader(new FileReader("temp_sorted_" + Main.dbName));
        for (int i = 0; i < (rightBorder - leftBorder + 1); i++) {
            br2.readLine();
        }
        line2 = br2.readLine();

        bw = new BufferedWriter(new FileWriter("sorted_" + Main.dbName, true));
        while (line2 != null) {
            bw.write(line2);
            line2 = br2.readLine();
            if (line2 != null)
                bw.write("\n");
        }


        writer = new PrintWriter("temp_sorted_" + Main.dbName);
        writer.print("");

        try {
            bw.close();
            br1.close();
            br2.close();
            writer.close();
        } catch (IOException ioe) {
            throw new IOException("Channel wasn't closed in \"sorting\" method!");
        }
    }


    public static boolean isDBEmpty() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(Main.dbName))) {
            for (int i = 0; i < Main.serviceRowsNumber + 1; i++) {
                if (br.readLine() == null)
                    return true;
            }
            return false;
        }
    }


    public static int fileBinarySearch(String file_name, String column, String value, boolean excludeServiceRows, boolean returnExistingPosition)
            throws DatabaseException, IOException {

        File file = new File(file_name);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            int column_ind = -1;
            for (int i = 0; i < Main.columns.length; i++) {
                if (column.equals(Main.columns[i])) {
                    column_ind = i;
                    break;
                }
            }
            if (column_ind == -1) {
                throw new DatabaseException("fileBinarySearch: There is no such column \"" + column + "\"");
            }

            if (excludeServiceRows == true) {
                for (int i = 0; i < Main.serviceRowsNumber; i++)
                    br.readLine();
            }

            int lineQuantity = -1;
            while (br.readLine() != null) {
                lineQuantity++;
            }

            return fileBinarySearch(file_name, column_ind, value, excludeServiceRows, returnExistingPosition, 0, lineQuantity);
        }
    }

    public static int fileBinarySearch(String file_name, int column_ind, String value, boolean excludeServiceRows, boolean returnExistingPosition, int leftBorder, int rightBorder)
            throws DatabaseException, IOException {

        //Попробовать использовать RandomAccessFile, чтобы не читать один и тот же файл по нескольку раз.
        //Также посмотреть на mark и reset в BufferedReader'е

        if (isDBEmpty()) {
            throw new DatabaseException("fileBinarySearch: The database is empty");
        }

        BufferedReader br = null;

        try {
            File file = new File(file_name);
            String line;
            br = new BufferedReader(new FileReader(file));

            if (excludeServiceRows == true) {
                for (int i = 0; i < Main.serviceRowsNumber; i++)
                    br.readLine();
            }

            int lineQuantity = -1;
            while ((br.readLine()) != null) {
                lineQuantity++;
            }
            br.close();

            if ((leftBorder < 0) || (rightBorder > lineQuantity) || (leftBorder > rightBorder)) {
                throw new DatabaseException("fileBinarySearch: Incorrect borders in \"fileBinarySearch\"!");
            }


            int low = leftBorder;
            int high = rightBorder;
            int newKeyIndex = low;
            int foundedIndex = -1;

            while (low <= high) {
                int middle = (low + high) / 2;

                br = new BufferedReader(new FileReader(file));
                if (excludeServiceRows == true) {
                    for (int i = 0; i < Main.serviceRowsNumber; i++)
                        br.readLine();
                }

                for (int i = 0; i < middle; i++)
                    br.readLine();
                line = br.readLine();
                br.close();

                String compared = line.split(",")[column_ind];
                if (compared.compareTo(value) == 0) {
                    foundedIndex = middle;
                    break;
                } else if (compared.compareTo(value) > 0) {
                    high = middle - 1;
                } else {
                    low = middle + 1;
                    newKeyIndex = low;
                }
            }

            if (returnExistingPosition == true)
                return foundedIndex;

            if (returnExistingPosition == false && foundedIndex == -1)
                return newKeyIndex;
            else
                return -1;
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                throw new IOException("Something wrong with closing of BufferedReader in \"fileBinarySearch\" method");
            }
        }
    }


    public static void createBackup(){
        BufferedWriter bw = null;
        BufferedReader br = null;

        String backupFileName = "backup_" + Main.dbName;
        File backupFile = new File(backupFileName);
        try {
            if (!backupFile.exists()) {
                backupFile.createNewFile();
            } else {
                bw = new BufferedWriter(new FileWriter(backupFile, false));
                bw.write("");
                bw.close();
            }

            br = new BufferedReader(new FileReader(Main.dbName));
            bw = new BufferedWriter(new FileWriter(backupFileName));


            bw.write(br.readLine());
            String line = br.readLine();
            while (line != null) {
                bw.write("\n");
                bw.write(line);
                line = br.readLine();
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.exit(-1);
            }
        }
    }


    public static int retrieveBackup(){
        BufferedWriter bw = null;
        BufferedReader br = null;

        String backupFileName = "backup_" + Main.dbName;
        File backupFile = new File(backupFileName);
        try {
            if (!backupFile.exists()) {
                return -1;
            } else {
                bw = new BufferedWriter(new FileWriter(Main.dbName, false));
                bw.write("");
                bw.close();
            }

            br = new BufferedReader(new FileReader(backupFileName));
            bw = new BufferedWriter(new FileWriter(Main.dbName));


            bw.write(br.readLine());
            String line = br.readLine();
            while (line != null) {
                bw.write("\n");
                bw.write(line);
                line = br.readLine();
            }

            return 1;
        } catch (IOException ioe){
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (br != null)
                    br.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.exit(-1);
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int answer = -1;
        while (true) {
            System.out.println("Выберите действие:\n" +
                    "1) Create DataBase\n" +
                    "2) Delete DataBase\n" +
                    "3) Open DataBase\n" +
                    "4) Open DataBase by Name\n" +
                    "5) Add Record\n" +
                    "6) Find Record\n" +
                    "7) Delete Record\n" +
                    "8) Edit Record\n" +
                    "9) Make BackUp\n" +
                    "10) Take from BackUp\n" +
                    "0) Выход");
            System.out.print("Your choice -> ");

            if (!scanner.hasNextInt()) {
                printWarningMessage("Have to be a number!");
                scanner.nextLine();
            } else {
                answer = scanner.nextInt();
                scanner.nextLine();

                switch (answer) {
                    case 0:
                        System.out.println("\nSee you soon :)");
                        System.exit(0);

//Creating
                    case 1:
                        switch (createDB()) {
                            case 0:
                                printUnsuccessfulMessage("DB wasn't created!");
                                break;
                            case 1:
                                printSuccessfulMessage("Database have been created successfully!");
                                break;
                            case -1:
                                printUnsuccessfulMessage("DB is already created!");
                                break;
                        }
                        break;

//Deleting
                    case 2:
                        switch (deleteDB()) {
                            case 0:
                                printUnsuccessfulMessage("Seems something went wrong...");
                                break;
                            case 1:
                                printSuccessfulMessage("Database was Deleted!");
                                break;
                            case -1:
                                printUnsuccessfulMessage("Database wasn't opened!");
                                break;
                        }
                        break;


//Opening
                    case 3:
                        switch (openDB()) {
                            case 1:
                                printSuccessfulMessage("Opened!");
                                break;
                            case -1:
                                printUnsuccessfulMessage("There is no such DataBase!");
                                break;
                            case -2:
                                printUnsuccessfulMessage("DB is already opened!");
                                break;
                        }
                        break;


//Opening by Name
                    case 4:
                        System.out.print("Enter database name for deleting (with.txt) --> ");
                        String dbNameOpen = scanner.nextLine();
                        switch (openDB(dbNameOpen)) {
                            case 1:
                                printSuccessfulMessage("Opened!");
                                break;
                            case -1:
                                printUnsuccessfulMessage("There is no such DataBase!");
                                break;
                            case -2:
                                printUnsuccessfulMessage("DB is already opened!");
                                break;
                        }
                        break;

//Adding of a new Record
                    case 5:
                        long start = System.currentTimeMillis();

                        switch (addNewRecord()) {
                            case 1:
                                printSuccessfulMessage("Added!");
                                break;
                            case -1:
                                printUnsuccessfulMessage("Database wasn't opened!");
                                break;
                            case -2:
                                printUnsuccessfulMessage("Such key already exists. You can't add this record!");
                                break;
                        }

                        long finish = System.currentTimeMillis();

                        printSuccessfulMessage("Time of running: " + (finish - start));
                        break;

//Finding record
                    case 6:
                        start = System.currentTimeMillis();

                        switch (findRecord()) {
                            case -1:
                                printUnsuccessfulMessage("Database wasn't opened!");
                                break;
                            case -2:
                                printUnsuccessfulMessage("There is no such record!");
                                break;
                        }

                        finish = System.currentTimeMillis();

                        printSuccessfulMessage("Time of running: " + (finish - start));

                        break;

//Deleting record
                    case 7:
                        start = System.currentTimeMillis();

                        switch (deleteRecord()) {
                            case 1:
                                printSuccessfulMessage("Deleted!");
                                break;
                            case -1:
                                printUnsuccessfulMessage("Database wasn't opened!");
                                break;
                        }

                        finish = System.currentTimeMillis();

                        printSuccessfulMessage("Time of running: " + (finish - start));
                        break;

//Editing record
                    case 8:
                        switch (editRecord()) {
                            case 1:
                                printSuccessfulMessage("Edited!");
                                break;
                            case -1:
                                printUnsuccessfulMessage("Database wasn't opened!");
                                break;
                            case -2:
                                printUnsuccessfulMessage("There is no such row to edit!");
                                break;
                            case -3:
                                printUnsuccessfulMessage("Row with such ID already exists!");
                                break;
                        }
                        break;

//Make BackUp
                    case 9:
                        createBackup();
                        printSuccessfulMessage("Backup have been created!");
                        break;

//Retrieve BackUp
                    case 10:
                        switch (retrieveBackup()){
                            case 1:
                                printSuccessfulMessage("Backup have been retrieved!");
                                break;
                            case -1:
                                printUnsuccessfulMessage("There is no backup for the database!");
                                break;
                        }
                        break;
                    default:
                        System.out.println("There is no such number!");
                        break;
                }
            }
        }
    }
}
