/*
 * Copyright 2014 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.bag.immutable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import com.gs.collections.api.LazyIterable;
import com.gs.collections.api.bag.Bag;
import com.gs.collections.api.bag.ImmutableBag;
import com.gs.collections.api.bag.MutableBag;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function0;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.block.predicate.primitive.IntPredicate;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.block.procedure.Procedure2;
import com.gs.collections.api.block.procedure.primitive.ObjectIntProcedure;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.ImmutableMap;
import com.gs.collections.api.map.MapIterable;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.multimap.MutableMultimap;
import com.gs.collections.api.multimap.bag.ImmutableBagMultimap;
import com.gs.collections.api.partition.bag.PartitionImmutableBag;
import com.gs.collections.api.set.ImmutableSet;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.UnmodifiableIteratorAdapter;
import com.gs.collections.impl.bag.mutable.HashBag;
import com.gs.collections.impl.block.factory.Predicates;
import com.gs.collections.impl.factory.Bags;
import com.gs.collections.impl.utility.Iterate;

/**
 * @since 1.0
 */
public class ImmutableHashBag<T>
        extends AbstractImmutableBag<T>
        implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final MutableBag<T> delegate;

    public ImmutableHashBag()
    {
        this.delegate = Bags.mutable.of();
    }

    public ImmutableHashBag(Iterable<? extends T> source)
    {
        this.delegate = HashBag.newBag(source);
    }

    public ImmutableHashBag(Bag<? extends T> source)
    {
        this.delegate = HashBag.newBag(source);
    }

    public static <T> ImmutableHashBag<T> newBag()
    {
        return new ImmutableHashBag<T>();
    }

    public static <T> ImmutableHashBag<T> newBag(Iterable<? extends T> source)
    {
        return new ImmutableHashBag<T>(source);
    }

    public static <T> ImmutableHashBag<T> newBagWith(T... elements)
    {
        return ImmutableHashBag.newBag(Arrays.asList(elements));
    }

    public static <T> ImmutableHashBag<T> newBagWith(Bag<? extends T> bag)
    {
        return new ImmutableHashBag<T>(bag);
    }

    public ImmutableBag<T> newWith(T element)
    {
        return HashBag.newBag(this.delegate).with(element).toImmutable();
    }

    public ImmutableBag<T> newWithout(T element)
    {
        HashBag<T> hashBag = HashBag.newBag(this.delegate);
        hashBag.remove(element);
        return hashBag.toImmutable();
    }

    public ImmutableBag<T> newWithAll(Iterable<? extends T> elements)
    {
        return Iterate.addAllTo(elements, HashBag.newBag(this.delegate)).toImmutable();
    }

    public ImmutableBag<T> newWithoutAll(Iterable<? extends T> elements)
    {
        return this.reject(Predicates.in(elements));
    }

    public int size()
    {
        return this.delegate.size();
    }

    public <V> ImmutableBagMultimap<V, T> groupBy(Function<? super T, ? extends V> function)
    {
        return this.delegate.groupBy(function).toImmutable();
    }

    @Override
    public <V, R extends MutableMultimap<V, T>> R groupBy(
            Function<? super T, ? extends V> function, R target)
    {
        return this.delegate.groupBy(function, target);
    }

    public <V> ImmutableBagMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.delegate.groupByEach(function).toImmutable();
    }

    @Override
    public <V, R extends MutableMultimap<V, T>> R groupByEach(
            Function<? super T, ? extends Iterable<V>> function, R target)
    {
        return this.delegate.groupByEach(function, target);
    }

    public <V> ImmutableMap<V, T> groupByUniqueKey(Function<? super T, ? extends V> function)
    {
        return this.delegate.groupByUniqueKey(function).toImmutable();
    }

    @Override
    public boolean isEmpty()
    {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean notEmpty()
    {
        return this.delegate.notEmpty();
    }

    public T getFirst()
    {
        return this.delegate.getFirst();
    }

    public T getLast()
    {
        return this.delegate.getLast();
    }

    @Override
    public T min(Comparator<? super T> comparator)
    {
        return this.delegate.min(comparator);
    }

    @Override
    public T max(Comparator<? super T> comparator)
    {
        return this.delegate.max(comparator);
    }

    @Override
    public T min()
    {
        return this.delegate.min();
    }

    @Override
    public T max()
    {
        return this.delegate.max();
    }

    @Override
    public <V extends Comparable<? super V>> T minBy(Function<? super T, ? extends V> function)
    {
        return this.delegate.minBy(function);
    }

    @Override
    public <V extends Comparable<? super V>> T maxBy(Function<? super T, ? extends V> function)
    {
        return this.delegate.maxBy(function);
    }

    @Override
    public boolean contains(Object object)
    {
        return this.delegate.contains(object);
    }

    @Override
    public boolean containsAllIterable(Iterable<?> source)
    {
        return this.delegate.containsAllIterable(source);
    }

    @Override
    public boolean containsAllArguments(Object... elements)
    {
        return this.delegate.containsAllArguments(elements);
    }

    @Override
    public <K, V> MutableMap<K, V> toMap(
            Function<? super T, ? extends K> keyFunction,
            Function<? super T, ? extends V> valueFunction)
    {
        return this.delegate.toMap(keyFunction, valueFunction);
    }

    public void forEach(Procedure<? super T> procedure)
    {
        this.delegate.forEach(procedure);
    }

    @Override
    public void forEachWithIndex(ObjectIntProcedure<? super T> objectIntProcedure)
    {
        this.delegate.forEachWithIndex(objectIntProcedure);
    }

    @Override
    public <P> void forEachWith(Procedure2<? super T, ? super P> procedure, P parameter)
    {
        this.delegate.forEachWith(procedure, parameter);
    }

    public ImmutableBag<T> selectByOccurrences(IntPredicate predicate)
    {
        return this.delegate.selectByOccurrences(predicate).toImmutable();
    }

    public ImmutableBag<T> select(Predicate<? super T> predicate)
    {
        return this.delegate.select(predicate).toImmutable();
    }

    public ImmutableBag<T> reject(Predicate<? super T> predicate)
    {
        return this.delegate.reject(predicate).toImmutable();
    }

    @Override
    public PartitionImmutableBag<T> partition(Predicate<? super T> predicate)
    {
        return this.delegate.partition(predicate).toImmutable();
    }

    @Override
    public <P> PartitionImmutableBag<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.delegate.partitionWith(predicate, parameter).toImmutable();
    }

    public <S> ImmutableBag<S> selectInstancesOf(Class<S> clazz)
    {
        return this.delegate.selectInstancesOf(clazz).toImmutable();
    }

    public <V> ImmutableBag<V> collect(Function<? super T, ? extends V> function)
    {
        return this.delegate.collect(function).toImmutable();
    }

    public <V> ImmutableBag<V> collectIf(
            Predicate<? super T> predicate, Function<? super T, ? extends V> function)
    {
        return this.delegate.collectIf(predicate, function).toImmutable();
    }

    public <V> ImmutableBag<V> flatCollect(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.delegate.flatCollect(function).toImmutable();
    }

    @Override
    public MutableList<T> toList()
    {
        return this.delegate.toList();
    }

    public int sizeDistinct()
    {
        return this.delegate.sizeDistinct();
    }

    public int occurrencesOf(Object item)
    {
        return this.delegate.occurrencesOf(item);
    }

    public void forEachWithOccurrences(ObjectIntProcedure<? super T> objectIntProcedure)
    {
        this.delegate.forEachWithOccurrences(objectIntProcedure);
    }

    @Override
    public MutableList<T> toSortedList()
    {
        return this.delegate.toSortedList();
    }

    @Override
    public MutableList<T> toSortedList(Comparator<? super T> comparator)
    {
        return this.delegate.toSortedList(comparator);
    }

    @Override
    public <R extends Collection<T>> R select(Predicate<? super T> predicate, R target)
    {
        return this.delegate.select(predicate, target);
    }

    @Override
    public <P, R extends Collection<T>> R selectWith(
            Predicate2<? super T, ? super P> predicate, P parameter, R targetCollection)
    {
        return this.delegate.selectWith(predicate, parameter, targetCollection);
    }

    @Override
    public <R extends Collection<T>> R reject(Predicate<? super T> predicate, R target)
    {
        return this.delegate.reject(predicate, target);
    }

    @Override
    public <P, R extends Collection<T>> R rejectWith(
            Predicate2<? super T, ? super P> predicate, P parameter, R targetCollection)
    {
        return this.delegate.rejectWith(predicate, parameter, targetCollection);
    }

    @Override
    public <V, R extends Collection<V>> R collect(Function<? super T, ? extends V> function, R target)
    {
        return this.delegate.collect(function, target);
    }

    @Override
    public <P, V, R extends Collection<V>> R collectWith(
            Function2<? super T, ? super P, ? extends V> function, P parameter, R targetCollection)
    {
        return this.delegate.collectWith(function, parameter, targetCollection);
    }

    @Override
    public <V, R extends Collection<V>> R flatCollect(
            Function<? super T, ? extends Iterable<V>> function, R target)
    {
        return this.delegate.flatCollect(function, target);
    }

    @Override
    public <V, R extends Collection<V>> R collectIf(
            Predicate<? super T> predicate, Function<? super T, ? extends V> function, R target)
    {
        return this.delegate.collectIf(predicate, function, target);
    }

    @Override
    public T detect(Predicate<? super T> predicate)
    {
        return this.delegate.detect(predicate);
    }

    @Override
    public T detectIfNone(Predicate<? super T> predicate, Function0<? extends T> function)
    {
        return this.delegate.detectIfNone(predicate, function);
    }

    @Override
    public int count(Predicate<? super T> predicate)
    {
        return this.delegate.count(predicate);
    }

    @Override
    public boolean anySatisfy(Predicate<? super T> predicate)
    {
        return this.delegate.anySatisfy(predicate);
    }

    @Override
    public boolean allSatisfy(Predicate<? super T> predicate)
    {
        return this.delegate.allSatisfy(predicate);
    }

    @Override
    public boolean noneSatisfy(Predicate<? super T> predicate)
    {
        return this.delegate.noneSatisfy(predicate);
    }

    @Override
    public <IV> IV injectInto(IV injectedValue, Function2<? super IV, ? super T, ? extends IV> function)
    {
        return this.delegate.injectInto(injectedValue, function);
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.delegate.equals(obj);
    }

    @Override
    public int hashCode()
    {
        return this.delegate.hashCode();
    }

    public MapIterable<T, Integer> toMapOfItemToCount()
    {
        return this.delegate.toMapOfItemToCount();
    }

    @Override
    public MutableSet<T> toSet()
    {
        return this.delegate.toSet();
    }

    @Override
    public MutableBag<T> toBag()
    {
        return this.delegate.toBag();
    }

    public ImmutableBag<T> toImmutable()
    {
        return this;
    }

    @Override
    public LazyIterable<T> asLazy()
    {
        return this.delegate.asLazy();
    }

    @Override
    public Object[] toArray()
    {
        return this.delegate.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return this.delegate.toArray(a);
    }

    @Override
    public String toString()
    {
        return this.makeString("[", ", ", "]");
    }

    @Override
    public String makeString()
    {
        return this.delegate.makeString();
    }

    @Override
    public String makeString(String separator)
    {
        return this.delegate.makeString(separator);
    }

    @Override
    public String makeString(String start, String separator, String end)
    {
        return this.delegate.makeString(start, separator, end);
    }

    @Override
    public void appendString(Appendable appendable)
    {
        this.delegate.appendString(appendable);
    }

    @Override
    public void appendString(Appendable appendable, String separator)
    {
        this.delegate.appendString(appendable, separator);
    }

    @Override
    public void appendString(Appendable appendable, String start, String separator, String end)
    {
        this.delegate.appendString(appendable, start, separator, end);
    }

    public <S> ImmutableBag<Pair<T, S>> zip(Iterable<S> that)
    {
        return this.delegate.zip(that).toImmutable();
    }

    @Override
    public <S, R extends Collection<Pair<T, S>>> R zip(Iterable<S> that, R target)
    {
        return this.delegate.zip(that, target);
    }

    public ImmutableSet<Pair<T, Integer>> zipWithIndex()
    {
        return this.delegate.zipWithIndex().toImmutable();
    }

    @Override
    public <R extends Collection<Pair<T, Integer>>> R zipWithIndex(R target)
    {
        return this.delegate.zipWithIndex(target);
    }

    public Iterator<T> iterator()
    {
        return new UnmodifiableIteratorAdapter<T>(this.delegate.iterator());
    }

    protected Object writeReplace()
    {
        return new ImmutableBagSerializationProxy<T>(this);
    }
}
