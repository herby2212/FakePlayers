package de.Herbystar.FakePlayers.CustomClient.cClasses;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class CustomChannelFuture implements ChannelFuture {

	@Override
	public boolean await(long arg0) throws InterruptedException {
		return false;
	}

	@Override
	public boolean await(long arg0, TimeUnit arg1) throws InterruptedException {
		return false;
	}

	@Override
	public boolean awaitUninterruptibly(long arg0) {
		return false;
	}

	@Override
	public boolean awaitUninterruptibly(long arg0, TimeUnit arg1) {
		return false;
	}

	@Override
	public boolean cancel(boolean arg0) {
		return false;
	}

	@Override
	public Throwable cause() {
		return null;
	}

	@Override
	public Void getNow() {
		return null;
	}

	@Override
	public boolean isCancellable() {
		return false;
	}

	@Override
	public boolean isSuccess() {
		return false;
	}

	@Override
	public Void get() throws InterruptedException, ExecutionException {
		return null;
	}

	@Override
	public Void get(long arg0, TimeUnit arg1) throws InterruptedException, ExecutionException, TimeoutException {
		return null;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> arg0) {
		return null;
	}

	@Override
	public ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... arg0) {
		return null;
	}

	@Override
	public ChannelFuture await() throws InterruptedException {
		return null;
	}

	@Override
	public ChannelFuture awaitUninterruptibly() {
		return null;
	}

	@Override
	public Channel channel() {
		return null;
	}

	@Override
	public ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> arg0) {
		return null;
	}

	@Override
	public ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... arg0) {
		return null;
	}

	@Override
	public ChannelFuture sync() throws InterruptedException {
		return null;
	}

	@Override
	public ChannelFuture syncUninterruptibly() {
		return null;
	}

}
