package org.codeturnery.lucene.transfer.accessor;

import java.io.InputStream;

public record InputStreamWithLength(int length, InputStream inputStream) {

}
