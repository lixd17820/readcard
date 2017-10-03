/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\android_project\\readcard\\app\\src\\main\\aidl\\com\\sdses\\readcardservice\\IReadCardService.aidl
 */
package com.sdses.readcardservice;
public interface IReadCardService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.sdses.readcardservice.IReadCardService
{
private static final java.lang.String DESCRIPTOR = "com.sdses.readcardservice.IReadCardService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.sdses.readcardservice.IReadCardService interface,
 * generating a proxy if needed.
 */
public static com.sdses.readcardservice.IReadCardService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.sdses.readcardservice.IReadCardService))) {
return ((com.sdses.readcardservice.IReadCardService)iin);
}
return new com.sdses.readcardservice.IReadCardService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_startReadCard:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.startReadCard();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_stopReadCard:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.stopReadCard();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readCard:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.readCard();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSAMID:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getSAMID();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getBoardVersion:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getBoardVersion();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getBoardSN:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getBoardSN();
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.sdses.readcardservice.IReadCardService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
//开启读卡线程

@Override public boolean startReadCard() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startReadCard, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//关闭读卡线程

@Override public boolean stopReadCard() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopReadCard, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//未实现

@Override public boolean readCard() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readCard, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getSAMID() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSAMID, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getBoardVersion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBoardVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getBoardSN() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBoardSN, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_startReadCard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_stopReadCard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_readCard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getSAMID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getBoardVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getBoardSN = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
//开启读卡线程

public boolean startReadCard() throws android.os.RemoteException;
//关闭读卡线程

public boolean stopReadCard() throws android.os.RemoteException;
//未实现

public boolean readCard() throws android.os.RemoteException;
public java.lang.String getSAMID() throws android.os.RemoteException;
public java.lang.String getBoardVersion() throws android.os.RemoteException;
public java.lang.String getBoardSN() throws android.os.RemoteException;
}
