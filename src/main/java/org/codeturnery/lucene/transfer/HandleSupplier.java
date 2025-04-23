package org.codeturnery.lucene.transfer;

import java.io.IOException;
import java.util.stream.Stream;

@FunctionalInterface
public interface HandleSupplier<T extends Handle> {
    Stream<T> getHandles() throws IOException;
}