/**
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy france (FR Pack).
 *
 * billy france (FR Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy france (FR Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy france (FR Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.france.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentTestUtil {

    private int totalThreads;

    public ConcurrentTestUtil(int totalThreads) {
        this.totalThreads = totalThreads;
    }

    public List<Future<?>> runThreads(Callable<?> callable) {
        ExecutorService executor = Executors.newFixedThreadPool(this.totalThreads);
        ArrayList<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < this.totalThreads; i++) {
            futures.add(executor.submit(callable));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }

        return futures;
    }

}
