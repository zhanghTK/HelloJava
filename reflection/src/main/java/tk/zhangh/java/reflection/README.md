# reflection包说明

- `ClassAnalyzer`：运行时类信息分析，获取类的字段，构造器，方法定义信息
- `InstanceAnalyzer`：运行时对象信息分析，获取对象字段信息
- `MethodTimer`,`Timer`：方法时长统计
- `ReflectionHelper`：反射调用封装帮助类
- `ReflectException`：封装反射异常，非受检异常

---

**ClassAnalyzer**

| 方法                                  | 说明                          |
| ----------------------------------- | --------------------------- |
| `getClassInfo(Class):String`        | 获取类的信息,类信息，包含字段，构造方法，普通方法信息 |
| `getConstructorsInfo(Class):String` | 获取类的构造器信息                   |
| `getMethodsInfo(Class):String`      | 类的方法信息                      |
| `getFieldsInfo(Class):String`       | 类字段信息                       |

**InstanceAnalyzer**

`getInstanceInfo(Object):String`：获取运行时对象信息分析

**MethodTimer**

统计反射调用执行方法的时长,要求方法所在类必须包含无参数构造方法

- 针对类，要求有无参构造方法，统计使用@Timer且无参数的方法运行时长
- 针对方法，统计指定方法的运行时长

| 方法                                       | 说明                  |
| ---------------------------------------- | ------------------- |
| `printMethodsDuration(Class):void`       | 打印`@Timer`注解的方法运行时长 |
| `getMethodDuration(Method, Object...):long` | 统计方法运行时长            |

`@Timer`

标记需要统计时长的方法，只能对无参数方法使用，使用`MethodTimer`统计

**ReflectionHelper**

| 方法                                       | 说明                        |
| ---------------------------------------- | ------------------------- |
| `forName(String):Class`                  | 使用当前类加载器根据全类名加载一个类        |
| `forName(String, ClassLoader):Class`     | 使用指定加载器根据全类名加载一个类         |
| `newInstance(Class):Object`              | 调用类的空构造器，获得类对象的实例         |
| `isPublic(T):boolean`                    | 判断一个Member是否是公开的成员        |
| `accessible(T):T`                        | 使一个AccessibleObject对象可以访问 |
| `field(Class, String):Field`             | 获取类的指定字段                  |
| `fields(Class):Map<String, Object>`      | 获取类的静态字段以及对应值             |
| `fields(Object):Map<String, Object>`     | 获取一个类的所有字段以及对应值           |
| `set(Object, String, Object):void`       | 给对象的字段设置值                 |
| `call(Object, Method, Object...):Object` | 执行指定方法                    |
| `call(Object, String, Object...):Object` | 执行指定签名的方法                 |
| `exactMethod(Class<?>, String, Class<?>):Method` | 根据方法签名准确匹配一个方法            |
| `similarMethod(Class<?>, String, Class<?>...)` | 根据方法签名匹配一个相似方法            |
| `types(Object...):Class<?>[]`            | 批量获取对象类型信息                |
| `getMethodName(Method):String`           | 获取方法名                     |
| `isSimilarSignature(Method, String, Class<?>...):boolean` | 判断方法签名是否匹配                |