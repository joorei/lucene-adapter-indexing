package org.codeturnery.lucene.transfer;

import java.io.IOException;
import java.util.OptionalInt;

@FunctionalInterface
public interface CountSupplier {
	OptionalInt getCount() throws IOException;
}