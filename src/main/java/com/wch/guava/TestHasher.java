package com.wch.guava;

import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Hashing类
 * Hashing类提供了若干散列函数，以及运算HashCode对象的工具方法。
 *
 * 已提供的散列函数
 *
 * md5()	murmur3_128()	murmur3_32()	sha1()
 * sha256()	sha512()	goodFastHash(int bits)	
 * HashCode运算
 *
 *   方法	描述
 *HashCode combineOrdered( Iterable<HashCode>)	以有序方式联接散列码，如果两个散列集合用该方法联接出的散列码相同，那么散列集合的元素可能是顺序相等的
 *HashCode   combineUnordered( Iterable<HashCode>)	以无序方式联接散列码，如果两个散列集合用该方法联接出的散列码相同，那么散列集合的元素可能在某种排序下是相等的
 *int   consistentHash( HashCode, int buckets)	为给定的”桶”大小返回一致性哈希值。当”桶”增长时，该方法保证最小程度的一致性哈希值变化。详见一致性哈希。
 * </pre>
 * @author qinghao
 *
 */
public class TestHasher {

	public static void main(String[] args) {
		HashFunction hf = Hashing.md5();
		long id = 0l ;;
		CharSequence name = "dsadsa";
		Person personTmp = new  Person();
		Person person = personTmp ;
		
		int birthYear = 0;
		Funnel<Person> personFunnel = new Funnel<Person>() {
		    @Override
		    public void funnel(Person person, PrimitiveSink into) {
				into
		            .putInt(person.id)
		            .putString(person.firstName, Charsets.UTF_8)
		            .putString(person.lastName, Charsets.UTF_8)
		            .putInt(birthYear);
		    }
		};
		HashCode hc = hf.newHasher()
		        .putLong(id)
		        .putString(name, Charsets.UTF_8)
		        .putObject(person, personFunnel)
		        .hash();
		
		
		BloomFilter<Person> friends = BloomFilter.create(personFunnel, 500, 0.01);
		List<Person> friendsList = Lists.newArrayList(personTmp); 
		for(Person friend : friendsList ) {
		    friends.put(friend);
		}

		// 很久以后
		if (friends.mightContain(new Person())) {
		    //dude不是朋友还运行到这里的概率为1%
		    //在这儿，我们可以在做进一步精确检查的同时触发一些异步加载
			System.out.println("------------");
		}
		
		System.out.println(hc.toString());
	}
}

@Data
@NoArgsConstructor 
class Person {
     static  int id = 0;
     static  String firstName = "xixi";
     static  String lastName = "haha";
     static  int birthYear = 1990;
}