package org.codeturnery.lucene.transfer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Set;

import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.util.BytesRef;
import org.codeturnery.lucene.document.CommonFieldTypes;
import org.codeturnery.lucene.transfer.accessor.ByteAccessor;
import org.codeturnery.lucene.transfer.accessor.TextAccessor;
import org.codeturnery.lucene.transfer.accessor.TextListAccessor;
import org.codeturnery.lucene.transfer.accessor.UnsignedIntegerAccessor;

public class FieldFactory {
	private static final org.codeturnery.lucene.document.FieldFactory FIELD_FACTORY = new org.codeturnery.lucene.document.FieldFactory();

	public static <T> IndexableField[] createUnsignedIntField(final String fieldName, final T handle,
			final UnsignedIntegerAccessor<T> accessor) {
		final int integerValue = accessor.getAsUnsignedPrimitive(handle);
		return new IndexableField[] { FIELD_FACTORY.createStoredInt(fieldName, integerValue) };
	}

	/**
	 * @param <T>
	 * @param fieldName
	 * @param handle
	 * @param accessor
	 * @param taxomize
	 * @return
	 */
	public static <T> IndexableField[] createTextField(final String fieldName, final T handle,
			final TextAccessor<T> accessor, final Boolean taxomize) {
		return FIELD_FACTORY.createExactMatchingTerm(fieldName, accessor.getAsCharSequence(handle),
				taxomize.booleanValue());
	}


	public static <T> IndexableField[] createStringSetField(final String fieldName, final T handle,
			final TextListAccessor<T> accessor, final Boolean taxomize) {
		return FIELD_FACTORY.createExactMatchingTerms(fieldName, accessor.getAsStringSet(handle),
				taxomize.booleanValue());
	}

	public static <T> IndexableField[] createBytesField(final String fieldName, final T handle,
			final ByteAccessor<T> accessor) {
		final byte[] bytes = accessor.getAsByteArray(handle);
		return new IndexableField[] { FIELD_FACTORY.createStoredBytes(fieldName, bytes) };
	}

	// FIXME: integrate/move/clean up code below

//	protected IndexableField[] getGallery(int galleryImageCount, OpusFolder opusFolder, ZipName zipName) {
//		final int galleryFileCount = opusFolder.getGalleryImageFileCount();
//		final var fields = new IndexableField[galleryImageCount + 1 + galleryFileCount * 2];
//
//		for (int imageIndex = 0; imageIndex < galleryImageCount; imageIndex++) {
//			final ImageFile imageFile = opusFolder.getGalleryImageFile(imageIndex);
//			// TODO: performance can be improved by re-using the ByteBuffer, as the size
//			// stays the same in each iteration
//			final byte[] imageFileReference = getImageReference(zipName, imageFile);
//			fields[imageIndex] = FIELD_FACTORY.createStoredBytes("galleryImageReference", imageFileReference, 0,
//					imageFileReference.length);
//		}
//
//		fields[galleryImageCount] = FIELD_FACTORY.createStoredInt(ImageCollectionField.GALLERY_IMAGE_COUNT.name(),
//				galleryImageCount);
//
//		for (int i = 0; i < galleryFileCount; i++) {
//			final ImageFile galleryFile = opusFolder.getGalleryImageFile(i);
//			final var reference = new FileHeaderReference(galleryFile);
//			final var buffer = ByteBuffer.allocate(FileHeaderReference.MIN_BUFFER_SIZE + zipName.getBytesLength());
//			reference.addToBuffer(buffer);
//			buffer.put(zipName.getBytes());
//			fields[galleryImageCount + 2 * i] = FIELD_FACTORY
//					.createStoredBytes(ImageCollectionField.GALLERY_IMAGE_REFERENCE.name(), buffer.array());
//			fields[galleryImageCount + 2 * i + 1] = FIELD_FACTORY.createStoredInt(
//					ImageCollectionField.GALLERY_IMAGE_ACTUAL_SIZE.name(), galleryFile.getUncompressedSize());
//		}
//
//		return fields;
//	}
//
//	protected StoredField createCoverImageReference(String fieldName, ZipName zipName, ImageFile coverImageFile) {
//		final byte[] coverImageId = getImageReference(zipName, coverImageFile);
//		return FIELD_FACTORY.createStoredBytes(fieldName, coverImageId, 0, coverImageId.length);
//	}
//
//	protected IndexableField[] generateAdapterField() {
//		return FIELD_FACTORY.createString(ImageCollectionField.ADAPTER.name(),
//				"net.datendose.tomo-bundle-pudding:0.1.0", CommonFieldTypes.TERM_EXACT_MATCHING, true);
//	}

}
