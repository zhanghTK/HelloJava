反射中的`isAccessible`

今天看到[JOOR](https://github.com/jOOQ/jOOR)里的`Reflect.accessible`方法，方法是使一个`AccessibleObject`对象具有可访的权限。

方法是这么实现的：

```java
public static <T extends AccessibleObject> T accessible(T accessible) {
    if (accessible == null) {
        return null;
    }
    if (accessible instanceof Member) {
      Member member = (Member) accessible;
      if (Modifier.isPublic(member.getModifiers()) &&
        Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
          return accessible;
        }
    }
    if (!accessible.isAccessible()) {
      accessible.setAccessible(true);
    }
    return accessible;
}
```

我不理解这段代码的意义何在，感觉没有必要。

```java
if (accessible instanceof Member) {
    Member member = (Member) accessible;
    if (Modifier.isPublic(member.getModifiers()) &&
        Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
            return accessible;
    }
}
```

查了一下git log。

果然，此方法的第一版实现时没有这段程序的，在第二版加上上面这段代码。作者在代码上注释了[issues](https://github.com/jOOQ/jOOQ/issues/3392) 。根据issues，发现有人提出在严格权限检查下的会出现错误，看到对`setAccessible(boolean)`这部分的讨论。我发现我之前的理解是有问题的：

之前的理解是`AccessibleObject.isAccessible()`返回对象是否具有访问权限，拿一个字段举例子：

对`private`级别的Field调用`isAccessible` ，一定返回的就是false，

对`public`级别的Field调用`isAccessible`， 一定返回的就是true。

方法实际意义并不是这样，[stackoverflow](http://stackoverflow.com/questions/18179593/why-a-public-constructor-is-not-accessible-via-reflection)有类似的提问，回答如下：

>`isAccessible` allows reflection API to access any member at runtime. By calling `Field.setAcessible(true)` you turn off the access checks for this particular Field instance, for reflection only. Now you can access it even if it is private, protected or package scope, even if the caller is not part of those scopes. 

自己实际动手验证了一下：

```java
public class Employee {
    private String id;
    public String name;
    // 省略构造，toString方法
}    

public class AccessibleTest {
    private static Logger logger = LoggerFactory.getLogger(AccessibleTest.class);

    public static void main(String[] args) throws Exception {
        Employee employee = new Employee("1", "ZS");
        logger.info(employee.toString());  // Employee{id='1', name='ZS'}
        Class<?> clazz = employee.getClass();

        printFieldAccess(clazz, "id");
        // Is 'id' public? false
        // Is 'id' accessible? false
        printFieldAccess(clazz, "name");
        // Is 'name' public? true
        // Is 'name' accessible? false
        
        changeFieldValue(employee, "name");
        // changed 'name' field value,Employee{id='1', name='TEST'}
        changeFieldValue(employee, "id");
        // changed 'id' field value fail,by class java.lang.IllegalAccessException
    }

    private static void printFieldAccess(Class<?> clazz, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        logger.info("Is '{}' public? {}", field.getName(), Modifier.isPublic(field.getModifiers()));
        logger.info("Is '{}' accessible? {}", field.getName(), field.isAccessible());
    }

    private static void changeFieldValue(Object object, String fieldName) throws NoSuchFieldException {
        Class clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        try {
            field.set(object, "TEST");
            logger.info("changed '{}' field value,{}", fieldName, object);
        } catch (IllegalAccessException e) {
            logger.error("changed '{}' field value fail,by {}", fieldName, e.getClass());
        }
    }
}
```

总结：

所有`accessibleObject.isAccessible()`无论源码上的修饰符，一律返回的都是`false`。返回值的含义：

`true` ：表示运行时可以通过反射直接访问，不需要进行权限检查

`false` ：表示运行时不能通过反射直接访问，需要进行权限检查

如果设置为true，则关闭运行时反射调用检查，可以通过反射随意调用，缺乏安全保障。

如果是一个访问级别为`public`的`accessibleObject`那么，即使`isAccessible`返回的是`false`也可以直接调用。

回到JOOR的`Reflect.accessible`,`public`级别的成员不需要`setAccessible`，如果设置了不仅没有意义反而会降低安全性，在严格的安全检查下反而会出现问题。