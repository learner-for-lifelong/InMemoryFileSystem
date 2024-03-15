package FileSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileManager
{
    private Map<String, Inode> files;

    public FileManager()
    {
        files = new HashMap<>();
    }

    public File fopen(String filename, Mode mode)
    {
        if (files.get(filename) == null)
        {
            if (mode.equals(Mode.READ))
            {
                throw new IllegalStateException("File does not exists");
            }

            files.put(filename, new Inode(filename, System.nanoTime()));
        }

        return new File(mode, files.get(filename));
    }

    public boolean rename(final String old, final String newName)
    {
        if (files.get(newName) != null)
        {
            System.out.println("File already exists. Choose different name.");
            return false;
        }

        if (files.get(old) == null)
        {
            System.out.println("File does not exists. Nothing to rename.");
            return false;
        }

        Inode file = files.get(old);
        files.remove(old);
        files.put(newName, file);

        System.out.println("File renamed to " + newName);

        return true;
    }

    public boolean remove(final String fileName)
    {
        if (files.get(fileName) == null)
        {
            System.out.println("File does not exists. Nothing to remove.");
            return false;
        }

        Inode file = files.remove(fileName);
        return true;
    }

    public Set<String> listFiles()
    {
        return files.keySet();
    }
}
