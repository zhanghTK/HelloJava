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

public class TestHierarchicalMethodsSubclass extends TestHierarchicalMethodsBase {

    public static String PUBLIC_RESULT = "PUBLIC_SUB";
    private static String PRIVATE_RESULT = "PRIVATE_SUB";
    public int visibleField2;
    public int visibleField3;
    // Both of these are hiding fields in the super type
    private int invisibleField2;
    private int invisibleField3;

    private String priv_method(int number) {
        return PRIVATE_RESULT;
    }

    public String pub_method(Integer number) {
        return PUBLIC_RESULT;
    }

    public int getInvisibleField3() {
        return invisibleField3;
    }
}
