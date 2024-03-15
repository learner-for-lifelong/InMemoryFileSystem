package FileSystem;

import java.util.Iterator;
import java.util.LinkedList;

import lombok.Getter;
import lombok.Setter;

public class Inode
{
    private final String name;
    @Getter
    private int size;
    private final long creationTime;
    @Setter
    private long lastUpdateTime;
    private LinkedList<byte[]> dataBlocks;
    @Getter
    private int blockSize;


    public Inode(final String name, final long creationTime)
    {
        this.name = name;
        this.creationTime = creationTime;
        this.dataBlocks = new LinkedList<>();
        this.size = 0;
        this.blockSize = 8; //1KB
    }

    @Override
    public String toString()
    {
        return "Inode{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", creationTime=" + creationTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }

    private Iterator<byte[]> getBlocks()
    {
        return dataBlocks.listIterator();
    }

    public byte[] fread()
    {
        byte[] data = new byte[size];
        Iterator<byte[]> iterator = getBlocks();
        int index = 0;
        while (iterator.hasNext())
        {
            byte[] block = iterator.next();
            int i = 0;
            while(i < getBlockSize() && index < getSize())
            {
                data[index++] = block[i++];
            }
        }

        return data;
    }

    //write
    public synchronized boolean fwrite(byte[] data)
    {
        int index = size;
        byte[] currentBlock = null;

        if (getSize() > 0)
        {
            currentBlock = dataBlocks.getLast();
        }

        size += data.length; // Should done once data is written
        int i = 0;

        while (index < size)
        {
            if (index % blockSize == 0)
            {
                byte[] newBlock = new byte[blockSize];
                dataBlocks.add(newBlock);
                currentBlock = newBlock;
            }
            currentBlock[index % blockSize] = data[i++];
            index++;
        }

        lastUpdateTime = System.nanoTime();

        return true;
    }
}
