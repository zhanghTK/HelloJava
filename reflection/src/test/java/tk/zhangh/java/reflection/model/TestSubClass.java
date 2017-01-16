/*
 * Copyright (c) 2011-2016, Data Geekery GmbH (http://www.datageekery.com)
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
package tk.zhangh.java.reflection.model;

import java.util.List;

public class TestSubClass extends TestBaseClass {
    public static String PUBLIC_STATIC_FIELD = "SUB_PUBLIC_STATIC_FIELD";
    private static String PRIVATE_STATIC_FIELD = "SUB_PRIVATE_STATIC_FIELD";
    public Integer subPublicField;
    private Integer subPrivateField = 1000;

    public static String publicStaticMethod(Integer num) {
        return PUBLIC_STATIC_FIELD;
    }

    public static String privateStaticMethod(Integer num) {
        return PRIVATE_STATIC_FIELD;
    }

    public Integer publicMethod(int num) {
        return basePublicField;
    }

    public Integer privateMethod(int num) {
        return subPrivateField;
    }

    public Integer baseSimilarMethod(List list) {
        return subPrivateField;
    }
}
