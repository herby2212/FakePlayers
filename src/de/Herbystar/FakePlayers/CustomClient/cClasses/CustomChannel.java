package de.Herbystar.FakePlayers.CustomClient.cClasses;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class CustomChannel implements Channel {

	@Override
	public <T> Attribute<T> attr(AttributeKey<T> arg0) {
		return null;
	}

	@Override
	public int compareTo(Channel arg0) {
		return 0;
	}

	@Override
	public ByteBufAllocator alloc() {
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
	public ChannelFuture close() {
		return null;
	}

	@Override
	public ChannelFuture close(ChannelPromise arg0) {
		return null;
	}

	@Override
	public ChannelFuture closeFuture() {
		return null;
	}

	@Override
	public ChannelConfig config() {
		return new CustomChannelConfig();
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
	public EventLoop eventLoop() {
		return new CustomEventLoop();
	}

	@Override
	public Channel flush() {
		return null;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public boolean isRegistered() {
		return false;
	}

	@Override
	public boolean isWritable() {
		return false;
	}

	@Override
	public SocketAddress localAddress() {
		return null;
	}

	@Override
	public ChannelMetadata metadata() {
		return null;
	}

	@Override
	public ChannelFuture newFailedFuture(Throwable arg0) {
		return null;
	}

	@Override
	public ChannelProgressivePromise newProgressivePromise() {
		return null;
	}

	@Override
	public ChannelPromise newPromise() {
		return null;
	}

	@Override
	public ChannelFuture newSucceededFuture() {
		return null;
	}

	@Override
	public Channel parent() {
		return null;
	}

	@Override
	public ChannelPipeline pipeline() {
		return new CustomPipeline();
	}

	@Override
	public Channel read() {
		return null;
	}

	@Override
	public SocketAddress remoteAddress() {
		return new InetSocketAddress("localhost", Bukkit.getServer().getPort());
	}

	@Override
	public Unsafe unsafe() {
		return null;
	}

	@Override
	public ChannelPromise voidPromise() {
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
