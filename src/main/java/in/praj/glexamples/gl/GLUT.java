/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.glexamples.gl;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CFunctionPointer;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CCharPointerPointer;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CContext(GLUT.Directives.class)
class GLUT {
    static final class Directives implements CContext.Directives {
        @Override
        public List<String> getHeaderFiles() {
            return Collections.singletonList("<GL/glut.h>");
        }

        @Override
        public List<String> getLibraries() {
            return Arrays.asList("glut", "GL");
        }
    }

    // GLUT
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
    static native void displayFunc(CFunctionPointer callback);

    @CFunction("glutIdleFunc")
    static native void idleFunc(CFunctionPointer callback);

    @CFunction("glutWireTeapot")
    static native void wireTeapot(double size);

    // GL
    @CConstant("GL_COLOR_BUFFER_BIT")
    static native int COLOR_BUFFER_BIT();

    @CConstant("GL_DEPTH_BUFFER_BIT")
    static native int DEPTH_BUFFER_BIT();

    @CConstant("GL_POLYGON")
    static native int POLYGON();

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

    @CFunction("glBegin")
    static native void begin(int mode);

    @CFunction("glColor3f")
    static native void color3f(float red, float green, float blue);

    @CFunction("glVertex3f")
    static native void vertex3f(float x, float y, float z);

    @CFunction("glEnd")
    static native void end();

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
