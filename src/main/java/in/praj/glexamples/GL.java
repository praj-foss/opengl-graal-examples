/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.glexamples;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.type.CFloatPointer;

@CContext(Main.Directives.class)
class GL {
    @CConstant("GL_COLOR_BUFFER_BIT")
    static native int COLOR_BUFFER_BIT();

    @CConstant("GL_DEPTH_BUFFER_BIT")
    static native int DEPTH_BUFFER_BIT();

    @CConstant("GL_SMOOTH")
    static native int SMOOTH();

    @CConstant("GL_LIGHTING")
    static native int LIGHTING();

    @CConstant("GL_LIGHT0")
    static native int LIGHT0();

    @CConstant("GL_DEPTH_TEST")
    static native int DEPTH_TEST();

    @CConstant("GL_AMBIENT")
    static native int AMBIENT();

    @CConstant("GL_DIFFUSE")
    static native int DIFFUSE();

    @CConstant("GL_FRONT")
    static native int FRONT();

    @CConstant("GL_SHININESS")
    static native int SHININESS();

    @CFunction("glClear")
    static native void clear(int mask);

    @CFunction("glClearColor")
    static native void clearColor(float red, float green, float blue, float alpha);

    @CFunction("glFlush")
    static native void flush();

    @CFunction("glShadeModel")
    static native void shadeModel(int mode);

    @CFunction("glLightfv")
    static native void lightfv(int light, int pname, CFloatPointer params);

    @CFunction("glMaterialfv")
    static native void materialfv(int face, int pname, CFloatPointer params);

    @CFunction("glEnable")
    static native void enable(int cap);

    @CFunction("glPushMatrix")
    static native void pushMatrix();

    @CFunction("glPopMatrix")
    static native void popMatrix();

    @CFunction("glRotatef")
    static native void rotatef(float angle, float x, float y, float z);
}
