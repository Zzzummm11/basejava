package com.urise.webapp.model.storage.stream;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStream implements StreamSerializer {
    @Override
    public void doWrite(final Resume r, final OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
           oos.writeObject(r);
      }
    }

    @Override
    public Resume doRead(final InputStream is) throws IOException{
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

}
