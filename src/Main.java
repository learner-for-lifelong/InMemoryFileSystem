import FileSystem.File;
import FileSystem.FileManager;
import FileSystem.Mode;

public class Main
{
    /*

    1. In memory file system
    2. File operations need to be implemented.
    3. Handle concurrency if needed.
    4. Do we need to support directory? Skipping initially.
    5. Flat heirarichy? Path ?
    6. User: system

    What is inode?
    > File metadata
    > file data block pointers.

    Block device
    > Split file into blocks

    Allocate byte array of e.g. 100MB
    - need interface to get next free block.

    File consiste of e.g. 1KB blocks.
    Inode map keeps track of file blocks.
    Map<String, Inode> : map of filename to inode and then goes to data blocks.




     */
    public static void main(String[] args)
    {
        FileManager fileManager = new FileManager();

        //opening new file in write mode
        File test1 = fileManager.fopen("test1", Mode.WRITE);
        String data = "Ravinder";
        test1.fwrite(data.getBytes()); //TODO non-ascii character support ??
        test1.fclose();

        //opening existing file for read
        File test1Read = fileManager.fopen("test1", Mode.READ);
        System.out.println(new String(test1Read.fread()));

        //opening existing file in write mode
        File test1Append = fileManager.fopen("test1", Mode.WRITE);
        test1Append.fwrite(" Kumar".getBytes());

        //REading appended data
        System.out.println(new String(test1Append.fread()));

        //Renaming non existent file
        fileManager.rename("test2", "test3");

        //Renaming to existing file
        fileManager.rename("test2", "test1");

        //Renaming existing file to new name
        fileManager.rename("test1", "myName");

        System.out.println(fileManager.listFiles());

        fileManager.remove("myName");

        System.out.println(fileManager.listFiles());

        File newFile = fileManager.fopen("longFile", Mode.WRITE);

        newFile.fwrite("This is very long test files spanning across multiple blocks".getBytes());

        System.out.println(newFile);
        System.out.println(new String(newFile.fread()));
    }
}