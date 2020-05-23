/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.glexamples;

import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.WordFactory;

/**
 * Entry-point to the application
 */
public class Main {
    public static void main(String[] args) {
        if (! GLFW.init()) {
            System.out.println("Failed to initialize app");
            return;
        }

        VoidPointer nullPtr = WordFactory.nullPointer();
        var title = CTypeConversion.toCString("Hello OpenGL");
        var window = GLFW.createWindow(640, 480, title.get(), nullPtr, nullPtr);

        if (window.isNull()) {
            System.out.println("Failed to create window");
            GLFW.terminate();
            return;
        }

        GLFW.setKeyCallback(window, GLFW.keyfun.getFunctionPointer());

        GLFW.makeContextCurrent(window);
        while (! GLFW.windowShouldClose(window)) {
            GLFW.swapBuffers(window);
            GLFW.pollEvents();
        }

        GLFW.terminate();
    }
}
