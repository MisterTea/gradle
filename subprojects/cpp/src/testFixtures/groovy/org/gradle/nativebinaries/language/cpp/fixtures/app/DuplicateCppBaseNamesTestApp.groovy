/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.nativebinaries.language.cpp.fixtures.app

class DuplicateCppBaseNamesTestApp extends TestComponent {

    def plugins = ["cpp"]

    @Override
    List<SourceFile> getSourceFiles() {
        [sourceFile("cpp", "main.cpp", """
            #include <iostream>
            #include "foo.h"
            using namespace std;
            int main () {
               foo1();
               foo2();
               return 0;
            }
        """),
         sourceFile("cpp/foo1", "foo.cpp", """
            #include <iostream>
            #include "foo.h"
            using namespace std;

            void DLL_FUNC foo1() {
                cout << "foo1";
            }
        """),

         sourceFile("cpp/foo2", "foo.cpp", """
            #include <iostream>
            #include "foo.h"
            using namespace std;

            void DLL_FUNC foo2() {
                cout << "foo2";
            }
        """)]
    }

    @Override
    List<SourceFile> getHeaderFiles() {
        [sourceFile("headers", "foo.h", """
           #ifdef _WIN32
           #define DLL_FUNC __declspec(dllexport)
           #else
           #define DLL_FUNC
           #endif
           void DLL_FUNC foo1();
           void DLL_FUNC foo2();
           """)
         ]
    }
}
