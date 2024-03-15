package FileSystem;

public class File
{
    private final Mode mode;
    private Inode inode;

    public File(final Mode mode, final Inode inode)
    {
        this.mode = mode;
        this.inode = inode;
    }

    //read
    public byte[] fread()
    {
        if (inode == null)
        {
            throw new IllegalStateException("File is closed");
        }
        return inode.fread();
    }

    //write
    public boolean fwrite(byte[] data)
    {
        if (inode == null)
        {
            throw new IllegalStateException("File is closed");
        }

        if (mode.equals(Mode.READ))
        {
            throw new IllegalStateException("File is in read only mode");
        }

        return inode.fwrite(data);
    }

    public void fclose()
    {
        inode = null;
    }

    @Override
    public String toString()
    {
        return "File{" +
                "mode=" + mode +
                ", inode=" + inode +
                '}';
    }
}
