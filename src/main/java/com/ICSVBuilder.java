package com;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder<E> {
    public Iterator<E> getCSVIterator(Reader reader, Class<E> csvClass);
}
