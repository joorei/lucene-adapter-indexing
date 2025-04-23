package org.codeturnery.lucene.transfer;

import java.util.Map;

import org.codeturnery.lucene.transfer.accessor.Accessor;

public class Converter {
	public static String asString(String value) {
		return value;
	}

	public static <T> Accessor<T> asDataFromField(String value, Map<String, Field<T, Accessor<T>>> fields) {
		return fields.get(value).getData();
	}
}
