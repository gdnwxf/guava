package com.wch.guava;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.common.base.Function;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

/**
 * <pre>TypeToken提供了一种方法来动态的解决泛型类型参数，如下所示：</pre>
 * @author wch
 *
 */
public class TestReflection {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		
		TypeToken<String> stringTok = TypeToken.of(String.class);
		TypeToken<Integer> intTok = TypeToken.of(Integer.class);
		
		TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};
		
		TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {};
		
		TypeToken<Map<String, BigInteger>> mapToken = mapToken(
			    TypeToken.of(String.class),
			    TypeToken.of(BigInteger.class)
			);
		
		TypeToken<Map<Integer, Queue<String>>> complexToken = mapToken(
				   TypeToken.of(Integer.class),
				   new TypeToken<Queue<String>>() {}
				);
		
		System.out.println(Util.<String, BigInteger>incorrectMapToken());
		
		TypeToken<Function<Integer, String>> funToken = new TypeToken<Function<Integer, String>>() {};

		TypeToken<?> funResultToken = funToken.resolveType(Function.class.getTypeParameters()[1]);
		// returns a TypeToken<String>
		
		TypeToken<Map<String, Integer>> mapToken1 = new TypeToken<Map<String, Integer>>() {};
		TypeToken<?> entrySetToken = mapToken1.resolveType(Map.class.getMethod("entrySet").getGenericReturnType());
		// returns a TypeToken<Set<Map.Entry<String, Integer>>>
		
		
		/**
		 * jdk
		 * 
		 */
		Method[] declaredMethods = Util.class.getDeclaredMethods();
		Method method = declaredMethods[0];
		Modifier.isPublic(method.getModifiers());
		//!(Modifier.isPrivate(method.getModifiers()) || Modifier.isPublic(method.getModifiers()))
		
		/**
		 * jdk中方法是否能被子类重写
		 * !(Modifier.isFinal(method.getModifiers())
			|| Modifiers.isPrivate(method.getModifiers())
			|| Modifiers.isStatic(method.getModifiers())
			|| Modifiers.isFinal(method.getDeclaringClass().getModifiers()))
		 */
		
		
		/**
		 * 判断第一个参数是否定义了注解
		 * for (Annotation annotation : method.getParameterAnnotations[0]) {
			    if (annotation instanceof Nullable) {
			        return true;
			    }
		   }
			return false;
		 */
		
		 Parameter parameter = method.getParameters()[0];
		 parameter.isAnnotationPresent(Nullable.class);
		Annotation[] parameterAnnotations = parameter.getDeclaredAnnotations();
		for (Annotation annotation : parameterAnnotations) {
			    if (annotation instanceof Nullable) {
			       System.out.println("true");
			    }
		   }
		Invokable<?, Object> from = Invokable.from(method);
		from.isPublic();
		from.isPrivate();
		from.isPackagePrivate();
		from.isOverridable();
		
		from.getParameters().get(0).isAnnotationPresent(Nullable.class);
		
		
		
		/**
		 * 获取返回类型
		 */
//		Invokable<List<String>, ?> invokable = new TypeToken<List<String>>(){}.method(method);
//		invokable.getReturnType(); // String.class
		System.out.println("end");
		
		
		
		/**
		 * 动态代理
		 * 
		 */
		
		
		InvocationHandler invocationHandler = new InvocationHandler() {
			
		 
			@Override
			public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
				 /**
				  * new Foo() 是对象实例
				  */
				 Object invoke = arg1.invoke(new Foo(), arg2);
				 return invoke;
			}
		};
		FooInterface foo = (FooInterface) Proxy.newProxyInstance(
				Foo.class.getClassLoader(),
				new Class<?>[] {FooInterface.class},
				invocationHandler );
		
		foo.doAdd(1, 2);
		
		
		/**
		 * guava 处理
		 */
		FooInterface foo2 = Reflection.newProxy(FooInterface.class, invocationHandler);
		foo2.doAdd(100, 10000);
		System.out.println("end");
	}
	
	static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
	    return new TypeToken<Map<K, V>>() {}
	        .where(new TypeParameter<K>() {}, keyToken)
	        .where(new TypeParameter<V>() {}, valueToken);
	}
}

interface FooInterface{
	int doAdd(int a,int b);
}

class  Foo implements FooInterface{
	public int doAdd (int a,int b)
	{
		return a + b;
	}
}

class Util {
	static <K, V> TypeToken<Map<K, V>> incorrectMapToken() {
		return new TypeToken<Map<K, V>>() {};
	}
	
	public void add(@Nullable Integer a,String b)
	{
		System.out.println(a+b);
	}
}
/**
 * @Retention(RetentionPolicy.SOURCE)   //注解仅存在于源码中，在class字节码文件中不包含
   @Retention(RetentionPolicy.CLASS)     // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
   @Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
 * @author wch
 *
 */

@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到  
@Target({ElementType.PARAMETER,ElementType.METHOD})//定义注解的作用目标**作用范围字段、枚举的常量/方法  
@Documented//说明该注解将被包含在javadoc中  
  @interface Nullable{
	
	  boolean value() default true ;
}