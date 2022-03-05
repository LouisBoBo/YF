package com.yssj.app;

import java.io.FileDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

public class ParcelCompat {

	private Parcel parcel;
//
//	public boolean equals(Object o) {
//		return parcel.equals(o);
//	}
//
//	public int hashCode() {
//		return parcel.hashCode();
//	}
//
//	public String toString() {
//		return parcel.toString();
//	}
//
//	public final void recycle() {
//		parcel.recycle();
//	}
//
//	public final int dataSize() {
//		return parcel.dataSize();
//	}
//
//	public final int dataAvail() {
//		return parcel.dataAvail();
//	}
//
//	public final int dataPosition() {
//		return parcel.dataPosition();
//	}
//
//	public final int dataCapacity() {
//		return parcel.dataCapacity();
//	}
//
//	public final void setDataSize(int size) {
//		parcel.setDataSize(size);
//	}
//
//	public final void setDataPosition(int pos) {
//		parcel.setDataPosition(pos);
//	}
//
//	public final void setDataCapacity(int size) {
//		parcel.setDataCapacity(size);
//	}
//
//	public final byte[] marshall() {
//		return parcel.marshall();
//	}
//
//	public final void unmarshall(byte[] data, int offest, int length) {
//		parcel.unmarshall(data, offest, length);
//	}
//
//	public final void appendFrom(Parcel parcel, int offset, int length) {
//		parcel.appendFrom(parcel, offset, length);
//	}
//
//	public final boolean hasFileDescriptors() {
//		return parcel.hasFileDescriptors();
//	}
//
//	public final void writeInterfaceToken(String interfaceName) {
//		parcel.writeInterfaceToken(interfaceName);
//	}
//
//	public final void enforceInterface(String interfaceName) {
//		parcel.enforceInterface(interfaceName);
//	}
//
//	public final void writeByteArray(byte[] b) {
//		parcel.writeByteArray(b);
//	}
//
//	public final void writeByteArray(byte[] b, int offset, int len) {
//		parcel.writeByteArray(b, offset, len);
//	}
//
	public final void writeInt(int val) {
		parcel.writeInt(val);
	}
//
	public final void writeLong(long val) {
		parcel.writeLong(val);
	}
//
//	public final void writeFloat(float val) {
//		parcel.writeFloat(val);
//	}
//
//	public final void writeDouble(double val) {
//		parcel.writeDouble(val);
//	}
//
	public final void writeString(String val) {
		parcel.writeString(val);
	}
//
//	public final void writeStrongBinder(IBinder val) {
//		parcel.writeStrongBinder(val);
//	}
//
//	public final void writeStrongInterface(IInterface val) {
//		parcel.writeStrongInterface(val);
//	}
//
//	public final void writeFileDescriptor(FileDescriptor val) {
//		parcel.writeFileDescriptor(val);
//	}
//
//	public final void writeByte(byte val) {
//		parcel.writeByte(val);
//	}
//
//	public final void writeMap(Map val) {
//		parcel.writeMap(val);
//	}
//
//	public final void writeBundle(Bundle val) {
//		parcel.writeBundle(val);
//	}
//
//	public final void writeList(List val) {
//		parcel.writeList(val);
//	}
//
//	public final void writeArray(Object[] val) {
//		parcel.writeArray(val);
//	}
//
//	public final void writeSparseArray(SparseArray<Object> val) {
//		parcel.writeSparseArray(val);
//	}
//
//	public final void writeSparseBooleanArray(SparseBooleanArray val) {
//		parcel.writeSparseBooleanArray(val);
//	}
//
//	public final void writeBooleanArray(boolean[] val) {
//		parcel.writeBooleanArray(val);
//	}
//
//	public final boolean[] createBooleanArray() {
//		return parcel.createBooleanArray();
//	}
//
//	public final void readBooleanArray(boolean[] val) {
//		parcel.readBooleanArray(val);
//	}
//
//	public final void writeCharArray(char[] val) {
//		parcel.writeCharArray(val);
//	}
//
//	public final char[] createCharArray() {
//		return parcel.createCharArray();
//	}
//
//	public final void readCharArray(char[] val) {
//		parcel.readCharArray(val);
//	}
//
//	public final void writeIntArray(int[] val) {
//		parcel.writeIntArray(val);
//	}
//
//	public final int[] createIntArray() {
//		return parcel.createIntArray();
//	}
//
//	public final void readIntArray(int[] val) {
//		parcel.readIntArray(val);
//	}
//
//	public final void writeLongArray(long[] val) {
//		parcel.writeLongArray(val);
//	}
//
//	public final long[] createLongArray() {
//		return parcel.createLongArray();
//	}
//
	public final void readLongArray(long[] val) {
		parcel.readLongArray(val);
	}
//
//	public final void writeFloatArray(float[] val) {
//		parcel.writeFloatArray(val);
//	}
//
//	public final float[] createFloatArray() {
//		return parcel.createFloatArray();
//	}
//
//	public final void readFloatArray(float[] val) {
//		parcel.readFloatArray(val);
//	}
//
//	public final void writeDoubleArray(double[] val) {
//		parcel.writeDoubleArray(val);
//	}
//
//	public final double[] createDoubleArray() {
//		return parcel.createDoubleArray();
//	}
//
//	public final void readDoubleArray(double[] val) {
//		parcel.readDoubleArray(val);
//	}
//
//	public final void writeStringArray(String[] val) {
//		parcel.writeStringArray(val);
//	}
//
//	public final String[] createStringArray() {
//		return parcel.createStringArray();
//	}
//
//	public final void readStringArray(String[] val) {
//		parcel.readStringArray(val);
//	}
//
//	public final void writeBinderArray(IBinder[] val) {
//		parcel.writeBinderArray(val);
//	}
//
//	public final IBinder[] createBinderArray() {
//		return parcel.createBinderArray();
//	}
//
//	public final void readBinderArray(IBinder[] val) {
//		parcel.readBinderArray(val);
//	}
//
//	public final <T extends Parcelable> void writeTypedList(List<T> val) {
//		parcel.writeTypedList(val);
//	}
//
//	public final void writeStringList(List<String> val) {
//		parcel.writeStringList(val);
//	}
//
//	public final void writeBinderList(List<IBinder> val) {
//		parcel.writeBinderList(val);
//	}
//
//	public final <T extends Parcelable> void writeTypedArray(T[] val,
//			int parcelableFlags) {
//		parcel.writeTypedArray(val, parcelableFlags);
//	}
//
//	public final void writeValue(Object v) {
//		parcel.writeValue(v);
//	}
//
	public final void writeParcelable(Parcelable p, int parcelableFlags) {
		parcel.writeParcelable(p, parcelableFlags);
	}
//
	public final void writeSerializable(Serializable s) {
		parcel.writeSerializable(s);
	}
//
//	public final void writeException(Exception e) {
//		parcel.writeException(e);
//	}
//
//	public final void writeNoException() {
//		parcel.writeNoException();
//	}
//
//	public final void readException() {
//		parcel.readException();
//	}
//
//	public final void readException(int code, String msg) {
//		parcel.readException(code, msg);
//	}
//
	public final int readInt() {
		return parcel.readInt();
	}
//
	public final long readLong() {
		return parcel.readLong();
	}
//
//	public final float readFloat() {
//		return parcel.readFloat();
//	}
//
//	public final double readDouble() {
//		return parcel.readDouble();
//	}
//
	public final String readString() {
		return parcel.readString();
	}
//
//	public final IBinder readStrongBinder() {
//		return parcel.readStrongBinder();
//	}
//
//	public final ParcelFileDescriptor readFileDescriptor() {
//		return parcel.readFileDescriptor();
//	}
//
//	public final byte readByte() {
//		return parcel.readByte();
//	}
//
//	public final void readMap(Map outVal, ClassLoader loader) {
//		parcel.readMap(outVal, loader);
//	}
//
//	public final void readList(List outVal, ClassLoader loader) {
//		parcel.readList(outVal, loader);
//	}
//
//	public final HashMap readHashMap(ClassLoader loader) {
//		return parcel.readHashMap(loader);
//	}
//
//	public final Bundle readBundle() {
//		return parcel.readBundle();
//	}
//
//	public final Bundle readBundle(ClassLoader loader) {
//		return parcel.readBundle(loader);
//	}
//
//	public final byte[] createByteArray() {
//		return parcel.createByteArray();
//	}
//
//	public final void readByteArray(byte[] val) {
//		parcel.readByteArray(val);
//	}
//
//	public final ArrayList readArrayList(ClassLoader loader) {
//		return parcel.readArrayList(loader);
//	}
//
//	public final Object[] readArray(ClassLoader loader) {
//		return parcel.readArray(loader);
//	}
//
//	public final SparseArray readSparseArray(ClassLoader loader) {
//		return parcel.readSparseArray(loader);
//	}
//
//	public final SparseBooleanArray readSparseBooleanArray() {
//		return parcel.readSparseBooleanArray();
//	}
//
//	public final <T> ArrayList<T> createTypedArrayList(Creator<T> c) {
//		return parcel.createTypedArrayList(c);
//	}
//
//	public final <T> void readTypedList(List<T> list, Creator<T> c) {
//		parcel.readTypedList(list, c);
//	}
//
//	public final ArrayList<String> createStringArrayList() {
//		return parcel.createStringArrayList();
//	}
//
//	public final ArrayList<IBinder> createBinderArrayList() {
//		return parcel.createBinderArrayList();
//	}
//
	public final void readStringList(List<String> list) {
		parcel.readStringList(list);
	}
//
//	public final void readBinderList(List<IBinder> list) {
//		parcel.readBinderList(list);
//	}
//
//	public final <T> T[] createTypedArray(Creator<T> c) {
//		return parcel.createTypedArray(c);
//	}
//
//	public final <T> void readTypedArray(T[] val, Creator<T> c) {
//		parcel.readTypedArray(val, c);
//	}
//
//	public final <T extends Parcelable> void writeParcelableArray(T[] value,
//			int parcelableFlags) {
//		parcel.writeParcelableArray(value, parcelableFlags);
//	}
//
//	public final Object readValue(ClassLoader loader) {
//		return parcel.readValue(loader);
//	}
//
	public final <T extends Parcelable> T readParcelable(ClassLoader loader) {
		return parcel.readParcelable(loader);
	}
//
//	public final Parcelable[] readParcelableArray(ClassLoader loader) {
//		return parcel.readParcelableArray(loader);
//	}
//
	public final Serializable readSerializable() {
		return parcel.readSerializable();
	}
//
//	// New method
//	
	public ParcelCompat(Parcel p) {
		this.parcel = p;
	}
//	
	public void writeBoolean(boolean val) {
		parcel.writeInt(val ? 1 : 0);
	}
//	
	public boolean readBoolean() {
		return parcel.readInt() == 1 ? true : false;
	}
//	
}
