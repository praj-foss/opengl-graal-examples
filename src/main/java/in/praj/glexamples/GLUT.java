/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.glexamples;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CFunctionPointer;
import org.graalvm.nativeimage.c.function.InvokeCFunctionPointer;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CCharPointerPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;

@CContext(Main.Directives.class)
class GLUT {
    @CConstant("GLUT_SINGLE")
    static native int SINGLE();

    @CConstant("GLUT_RGB")
    static native int RGB();

    @CConstant("GLUT_DEPTH")
    static native int DEPTH();

    @CFunction("glutInit")
    static native void init(CIntPointer argc, CCharPointerPointer argv);

    @CFunction("glutInitDisplayMode")
    static native void initDisplayMode(int displayMode);

    @CFunction("glutInitWindowPosition")
    static native void initWindowPosition(int x, int y);

    @CFunction("glutInitWindowSize")
    static native void initWindowSize(int width, int height);

    @CFunction("glutCreateWindow")
    static native void createWindow(CCharPointer title);

    @CFunction("glutMainLoop")
    static native void mainLoop();

    @CFunction("glutPostRedisplay")
    static native void postRedisplay();

    @CFunction("glutDisplayFunc")
    static native void displayFunc(Callback callback);

    @CFunction("glutIdleFunc")
    static native void idleFunc(Callback callback);

    @CFunction("glutWireTeapot")
    static native void wireTeapot(double size);

    interface Callback extends CFunctionPointer {
        @InvokeCFunctionPointer void invoke();
    }
}
