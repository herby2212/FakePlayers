package de.Herbystar.FakePlayers.CustomClient.cClasses;

import java.util.Map;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;

public class CustomChannelConfig implements ChannelConfig {

	@Override
	public ByteBufAllocator getAllocator() {
		return null;
	}

	@Override
	public int getConnectTimeoutMillis() {
		return 0;
	}

	@Override
	public int getMaxMessagesPerRead() {
		return 0;
	}

	@Override
	public MessageSizeEstimator getMessageSizeEstimator() {
		return null;
	}

	@Override
	public <T> T getOption(ChannelOption<T> arg0) {
		return null;
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return null;
	}

	@Override
	public RecvByteBufAllocator getRecvByteBufAllocator() {
		return null;
	}

	@Override
	public int getWriteBufferHighWaterMark() {
		return 0;
	}

	@Override
	public int getWriteBufferLowWaterMark() {
		return 0;
	}

	@Override
	public int getWriteSpinCount() {
		return 0;
	}

	@Override
	public boolean isAutoClose() {
		return false;
	}

	@Override
	public boolean isAutoRead() {
		return false;
	}

	@Override
	public ChannelConfig setAllocator(ByteBufAllocator arg0) {
		return null;
	}

	@Override
	public ChannelConfig setAutoClose(boolean arg0) {
		return null;
	}

	@Override
	public ChannelConfig setAutoRead(boolean arg0) {
		return null;
	}

	@Override
	public ChannelConfig setConnectTimeoutMillis(int arg0) {
		return null;
	}

	@Override
	public ChannelConfig setMaxMessagesPerRead(int arg0) {
		return null;
	}

	@Override
	public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator arg0) {
		return null;
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> arg0, T arg1) {
		return false;
	}

	@Override
	public boolean setOptions(Map<ChannelOption<?>, ?> arg0) {
		return false;
	}

	@Override
	public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator arg0) {
		return null;
	}

	@Override
	public ChannelConfig setWriteBufferHighWaterMark(int arg0) {
		return null;
	}

	@Override
	public ChannelConfig setWriteBufferLowWaterMark(int arg0) {
		return null;
	}

	@Override
	public ChannelConfig setWriteSpinCount(int arg0) {
		return null;
	}

}
