package de.Herbystar.FakePlayers.CustomClient.cClasses;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.EventExecutorGroup;

public class CustomPipeline implements ChannelPipeline {

	@Override
	public Iterator<Entry<String, ChannelHandler>> iterator() {
		return new ArrayList<Map.Entry<String, ChannelHandler>>().iterator();
	}

	@Override
	public ChannelPipeline addAfter(String arg0, String arg1, ChannelHandler arg2) {
		
		return null;
	}

	@Override
	public ChannelPipeline addAfter(EventExecutorGroup arg0, String arg1, String arg2, ChannelHandler arg3) {
		
		return null;
	}

	@Override
	public ChannelPipeline addBefore(String arg0, String arg1, ChannelHandler arg2) {
		
		return null;
	}

	@Override
	public ChannelPipeline addBefore(EventExecutorGroup arg0, String arg1, String arg2, ChannelHandler arg3) {
		
		return null;
	}

	@Override
	public ChannelPipeline addFirst(ChannelHandler... arg0) {
		
		return null;
	}

	@Override
	public ChannelPipeline addFirst(String arg0, ChannelHandler arg1) {
		
		return null;
	}

	@Override
	public ChannelPipeline addFirst(EventExecutorGroup arg0, ChannelHandler... arg1) {
		
		return null;
	}

	@Override
	public ChannelPipeline addFirst(EventExecutorGroup arg0, String arg1, ChannelHandler arg2) {
		
		return null;
	}

	@Override
	public ChannelPipeline addLast(ChannelHandler... arg0) {
		
		return null;
	}

	@Override
	public ChannelPipeline addLast(String arg0, ChannelHandler arg1) {
		
		return null;
	}

	@Override
	public ChannelPipeline addLast(EventExecutorGroup arg0, ChannelHandler... arg1) {
		
		return null;
	}

	@Override
	public ChannelPipeline addLast(EventExecutorGroup arg0, String arg1, ChannelHandler arg2) {
		
		return null;
	}

	@Override
	public ChannelFuture bind(SocketAddress arg0) {
		
		return null;
	}

	@Override
	public ChannelFuture bind(SocketAddress arg0, ChannelPromise arg1) {
		
		return null;
	}

	@Override
	public Channel channel() {
		
		return null;
	}

	@Override
	public ChannelFuture close() {
		
		return null;
	}

	@Override
	public ChannelFuture close(ChannelPromise arg0) {
		
		return null;
	}

	@Override
	public ChannelFuture connect(SocketAddress arg0) {
		
		return null;
	}

	@Override
	public ChannelFuture connect(SocketAddress arg0, SocketAddress arg1) {
		
		return null;
	}

	@Override
	public ChannelFuture connect(SocketAddress arg0, ChannelPromise arg1) {
		
		return null;
	}

	@Override
	public ChannelFuture connect(SocketAddress arg0, SocketAddress arg1, ChannelPromise arg2) {
		
		return null;
	}

	@Override
	public ChannelHandlerContext context(ChannelHandler arg0) {
		
		return null;
	}

	@Override
	public ChannelHandlerContext context(String arg0) {
		
		return null;
	}

	@Override
	public ChannelHandlerContext context(Class<? extends ChannelHandler> arg0) {
		
		return null;
	}

	@Override
	public ChannelFuture deregister() {
		
		return null;
	}

	@Override
	public ChannelFuture deregister(ChannelPromise arg0) {
		
		return null;
	}

	@Override
	public ChannelFuture disconnect() {
		
		return null;
	}

	@Override
	public ChannelFuture disconnect(ChannelPromise arg0) {
		
		return null;
	}

	@Override
	public ChannelPipeline fireChannelActive() {
		
		return null;
	}

	@Override
	public ChannelPipeline fireChannelInactive() {
		
		return null;
	}

	@Override
	public ChannelPipeline fireChannelRead(Object arg0) {
		
		return null;
	}

	@Override
	public ChannelPipeline fireChannelReadComplete() {
		
		return null;
	}

	@Override
	public ChannelPipeline fireChannelRegistered() {
		
		return null;
	}

	@Override
	public ChannelPipeline fireChannelUnregistered() {
		
		return null;
	}

	@Override
	public ChannelPipeline fireChannelWritabilityChanged() {
		
		return null;
	}

	@Override
	public ChannelPipeline fireExceptionCaught(Throwable arg0) {
		
		return null;
	}

	@Override
	public ChannelPipeline fireUserEventTriggered(Object arg0) {
		
		return null;
	}

	@Override
	public ChannelHandler first() {
		
		return null;
	}

	@Override
	public ChannelHandlerContext firstContext() {
		
		return null;
	}

	@Override
	public ChannelPipeline flush() {
		
		return null;
	}

	@Override
	public ChannelHandler get(String arg0) {
		
		return null;
	}

	@Override
	public <T extends ChannelHandler> T get(Class<T> arg0) {
		
		return null;
	}

	@Override
	public ChannelHandler last() {
		
		return null;
	}

	@Override
	public ChannelHandlerContext lastContext() {
		
		return null;
	}

	@Override
	public List<String> names() {
		return new ArrayList<String>();
	}

	@Override
	public ChannelPipeline read() {
		
		return null;
	}

	@Override
	public ChannelPipeline remove(ChannelHandler arg0) {
		
		return null;
	}

	@Override
	public ChannelHandler remove(String arg0) {
		
		return null;
	}

	@Override
	public <T extends ChannelHandler> T remove(Class<T> arg0) {
		
		return null;
	}

	@Override
	public ChannelHandler removeFirst() {
		
		return null;
	}

	@Override
	public ChannelHandler removeLast() {
		
		return null;
	}

	@Override
	public ChannelPipeline replace(ChannelHandler arg0, String arg1, ChannelHandler arg2) {
		
		return null;
	}

	@Override
	public ChannelHandler replace(String arg0, String arg1, ChannelHandler arg2) {
		
		return null;
	}

	@Override
	public <T extends ChannelHandler> T replace(Class<T> arg0, String arg1, ChannelHandler arg2) {
		
		return null;
	}

	@Override
	public Map<String, ChannelHandler> toMap() {
		
		return null;
	}

	@Override
	public ChannelFuture write(Object arg0) {
		
		return null;
	}

	@Override
	public ChannelFuture write(Object arg0, ChannelPromise arg1) {
		
		return null;
	}

	@Override
	public ChannelFuture writeAndFlush(Object arg0) {
		
		return null;
	}

	@Override
	public ChannelFuture writeAndFlush(Object arg0, ChannelPromise arg1) {
		
		return null;
	}

}
