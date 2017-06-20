package com.wch.guava.executor;

import com.google.common.util.concurrent.ListenableFuture;

public interface DataService {

	public default ListenableFuture read(RowKey rowKey) {
		return null;
	}

}
