/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.glexamples;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CLibrary;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.nativeimage.c.type.VoidPointer;

import java.util.Collections;
import java.util.List;

/**
 * Access native GLFW functions
 */
@CContext(GLFW.Directives.class)
@CLibrary("glfw")
public class GLFW {
    static class Directives implements CContext.Directives {
        @Override
        public List<String> getHeaderFiles() {
            return Collections.singletonList("<GLFW/glfw3.h>");
        }
    }

    @CFunction("glfwInit")
    private static native int _init();
    public static boolean init() {
        return CTypeConversion.toBoolean(_init());
    }

    @CFunction("glfwCreateWindow")
    public static native VoidPointer createWindow(
            int width, int height, CCharPointer title, VoidPointer monitor, VoidPointer share);

    @CFunction("glfwMakeContextCurrent")
    public static native void makeContextCurrent(VoidPointer window);

    @CFunction("glfwWindowShouldClose")
    private static native int _windowShouldClose(VoidPointer window);
    public static boolean windowShouldClose(VoidPointer window) {
        return CTypeConversion.toBoolean(_windowShouldClose(window));
    }

    @CFunction("glfwSwapBuffers")
    public static native void swapBuffers(VoidPointer window);

    @CFunction("glfwPollEvents")
    public static native void pollEvents();

    @CFunction("glfwTerminate")
    public static native int terminate();
}
