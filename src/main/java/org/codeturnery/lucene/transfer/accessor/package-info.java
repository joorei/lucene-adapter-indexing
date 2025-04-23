/**
 * This package contains so called {@link org.codeturnery.lucene.transfer.accessor.Accessor}
 * types. An instance is bound to a specific kind of data "handle" and knows how
 * to retrieve specific data from it. How the data is actually stored in the
 * handle and how the accessor retrieves it is not exposed to the outside.
 * Instead, the accessor allows to retrieve the data in different shapes and
 * forms via different getter methods.
 * <p>
 * Different logical concepts of the underlying data are represented by
 * different child interfaces of {@link org.codeturnery.lucene.transfer.accessor.Accessor}.
 * <p>
 * E.g. the concept of a mathematical integer is represented by the
 * {@link org.codeturnery.lucene.transfer.accessor.IntegerAccessor}. Typically an integer is
 * represented in Java as {@code int} or {@link java.lang.Integer} and the
 * interface provides the corresponding
 * {@link org.codeturnery.lucene.transfer.accessor.IntegerAccessor#getAsPrimitiveInt} and
 * {@link org.codeturnery.lucene.transfer.accessor.IntegerAccessor#getAsInteger} methods.
 * However, the actual data may be stored as bytes, which may be exactly the
 * format that is needed by the logic accessing the getter methods. Hence, the
 * method {@link org.codeturnery.lucene.transfer.accessor.IntegerAccessor#getAsByteBuffer} is
 * available too.
 * <p>
 * Implementations of the {@link org.codeturnery.lucene.transfer.accessor.IntegerAccessor}
 * are responsible to convert the actual type of the data into the type returned
 * by the getter method in the most efficient way possible. Ideally no
 * conversion is necessary at all, i.e. when the data is already stored with the
 * type returned by the method. However, even if this is not the case will this
 * approach avoid unnecessary conversions.
 * <p>
 * The {@link org.codeturnery.lucene.transfer.accessor.AccessorFactory} can be used for some
 * ready-to-use {@link org.codeturnery.lucene.transfer.accessor.Accessor} implementations.
 * You should only use those if the format your data is stored in actually
 * matches that they are optimized for. Hence, use-cases where a custom class
 * inheriting from {@link org.codeturnery.lucene.transfer.accessor.Accessor} needs to be
 * implemented are to be expected.
 */
@org.eclipse.jdt.annotation.NonNullByDefault
package org.codeturnery.lucene.transfer.accessor;