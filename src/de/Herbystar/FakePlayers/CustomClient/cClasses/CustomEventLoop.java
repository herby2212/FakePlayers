package de.Herbystar.FakePlayers.CustomClient.cClasses;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ScheduledFuture;

public class CustomEventLoop implements EventLoop {

	@Override
	public boolean inEventLoop() {
		return false;
	}

	@Override
	public boolean inEventLoop(Thread arg0) {
		return false;
	}

	@Override
	public <V> Future<V> newFailedFuture(Throwable arg0) {
		return null;
	}

	@Override
	public <V> ProgressivePromise<V> newProgressivePromise() {
		return null;
	}

	@Override
	public <V> Promise<V> newPromise() {
		return null;
	}

	@Override
	public <V> Future<V> newSucceededFuture(V arg0) {
		return null;
	}

	@Override
	public boolean isShuttingDown() {
		return false;
	}

	@Override
	public Iterator<EventExecutor> iterator() {
		return null;
	}

	@Override
	public ScheduledFuture<?> schedule(Runnable arg0, long arg1, TimeUnit arg2) {
		return null;
	}

	@Override
	public <V> ScheduledFuture<V> schedule(Callable<V> arg0, long arg1, TimeUnit arg2) {
		return null;
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable arg0, long arg1, long arg2, TimeUnit arg3) {
		return null;
	}

	@Override
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable arg0, long arg1, long arg2, TimeUnit arg3) {
		return null;
	}

	@Override
	public void shutdown() {
		
	}

	@Override
	public Future<?> shutdownGracefully() {
		return null;
	}

	@Override
	public Future<?> shutdownGracefully(long arg0, long arg1, TimeUnit arg2) {
		return null;
	}

	@Override
	public List<Runnable> shutdownNow() {
		return null;
	}

	@Override
	public Future<?> submit(Runnable arg0) {
		return null;
	}

	@Override
	public <T> Future<T> submit(Callable<T> arg0) {
		return null;
	}

	@Override
	public <T> Future<T> submit(Runnable arg0, T arg1) {
		return null;
	}

	@Override
	public Future<?> terminationFuture() {
		return null;
	}

	@Override
	public boolean awaitTermination(long arg0, TimeUnit arg1) throws InterruptedException {
		return false;
	}

	@Override
	public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> arg0)
			throws InterruptedException {
		return null;
	}

	@Override
	public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> arg0, long arg1,
			TimeUnit arg2) throws InterruptedException {
		return null;
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> arg0) throws InterruptedException, ExecutionException {
		return null;
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> arg0, long arg1, TimeUnit arg2)
			throws InterruptedException, ExecutionException, TimeoutException {
		return null;
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public void execute(Runnable arg0) {
		
	}

	@Override
	public EventLoop next() {
		return null;
	}

	@Override
	public ChannelFuture register(Channel arg0) {
		return null;
	}

	@Override
	public ChannelFuture register(Channel arg0, ChannelPromise arg1) {
		return null;
	}

	@Override
	public EventLoopGroup parent() {
		return null;
	}

}
