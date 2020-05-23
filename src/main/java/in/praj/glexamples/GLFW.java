/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.glexamples;

import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CFunctionPointer;
import org.graalvm.nativeimage.c.function.CLibrary;
import org.graalvm.nativeimage.c.function.InvokeCFunctionPointer;
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
class GLFW {
    static class Directives implements CContext.Directives {
        @Override
        public List<String> getHeaderFiles() {
            return Collections.singletonList("<GLFW/glfw3.h>");
        }
    }

    @CConstant("GLFW_KEY_ESCAPE")
    static native int KEY_ESCAPE();

    @CConstant("GLFW_RELEASE")
    static native int RELEASE();

    interface Keyfun extends CFunctionPointer {
        @InvokeCFunctionPointer
        void _invoke(VoidPointer window,
                     int key,
                     int scancode,
                     int action,
                     int mods);
    }

    @CFunction("glfwInit")
    static private native int _init();
    static boolean init() {
        return CTypeConversion.toBoolean(_init());
    }

    @CFunction("glfwCreateWindow")
    static native VoidPointer createWindow(
            int width, int height, CCharPointer title, VoidPointer monitor, VoidPointer share);

    @CFunction("glfwSetKeyCallback")
    static native VoidPointer setKeyCallback(VoidPointer window, Keyfun callback);

    static CEntryPointLiteral<Keyfun> keyfun = CEntryPointLiteral.create(GLFW.class, "_keyfun",
            VoidPointer.class, int.class, int.class, int.class, int.class);

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class)
    private static void _keyfun(VoidPointer window, int key, int scancode, int action, int mods) {
        if (key == KEY_ESCAPE() && action == RELEASE()) {
            setWindowShouldClose(window, 1);
            System.out.println("Window closed");
        }
    }

    @CFunction("glfwMakeContextCurrent")
    static native void makeContextCurrent(VoidPointer window);

    @CFunction("glfwWindowShouldClose")
    private static native int _windowShouldClose(VoidPointer window);
    static boolean windowShouldClose(VoidPointer window) {
        return CTypeConversion.toBoolean(_windowShouldClose(window));
    }

    @CFunction("glfwSetWindowShouldClose")
    static native void setWindowShouldClose(VoidPointer window, int value);

    @CFunction("glfwSwapBuffers")
    static native void swapBuffers(VoidPointer window);

    @CFunction("glfwPollEvents")
    static native void pollEvents();

    @CFunction("glfwTerminate")
    static native int terminate();
}
