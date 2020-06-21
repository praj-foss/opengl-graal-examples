/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.glexamples.gl;

import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import org.graalvm.nativeimage.PinnedObject;
import org.graalvm.nativeimage.StackValue;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.c.function.CFunctionPointer;
import org.graalvm.nativeimage.c.function.InvokeCFunctionPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;

@CContext(GLUT.Directives.class)
public class GLUTExample {
    public static void start(String[] args) {
        // Init
        try (var argv = CTypeConversion.toCStrings(args)) {
            var argc = StackValue.get(CIntPointer.class);
            argc.write(args.length);
            GLUT.init(argc, argv.get());
        }

        // Window
        GLUT.initDisplayMode(GLUT.SINGLE() | GLUT.RGB() | GLUT.DEPTH());
        GLUT.initWindowPosition(15, 15);
        GLUT.initWindowSize(400, 400);
        try (var title = CTypeConversion.toCString("Utah Teapot - GraalVM")) {
            GLUT.createWindow(title.get());
        }

        // Materials
        GLUT.clearColor(0.5f, 0.5f, 0.5f, 0f);
        GLUT.shadeModel(GLUT.SMOOTH());
        try (var white = PinnedObject.create(new float[] {1f, 1f, 1f, 0f});
             var shine = PinnedObject.create(new float[] {70f})) {
            GLUT.lightfv(GLUT.LIGHT0(), GLUT.AMBIENT(), white.addressOfArrayElement(0));
            GLUT.lightfv(GLUT.LIGHT0(), GLUT.DIFFUSE(), white.addressOfArrayElement(0));
            GLUT.materialfv(GLUT.FRONT(), GLUT.SHININESS(), shine.addressOfArrayElement(0));
        }
        GLUT.enable(GLUT.LIGHTING());
        GLUT.enable(GLUT.LIGHT0());
        GLUT.enable(GLUT.DEPTH_TEST());

        // Callbacks
        GLUT.displayFunc(displayCallback.getFunctionPointer());
        GLUT.idleFunc(idleCallback.getFunctionPointer());

        // Begin
        GLUT.mainLoop();
    }

    private static float rot = 0;

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class)
    private static void display() {
        GLUT.clear(GLUT.COLOR_BUFFER_BIT() | GLUT.DEPTH_BUFFER_BIT());

        GLUT.pushMatrix();
        GLUT.rotatef(30f, 1f, 1f, 0f);
        GLUT.rotatef(rot, 0f, 1f, 1f);
        try (var mat = PinnedObject.create(new float[] {1, 0, 0, 0})) {
            GLUT.materialfv(GLUT.FRONT(), GLUT.DIFFUSE(), mat.addressOfArrayElement(0));
        }
        GLUT.wireTeapot(0.5);
        GLUT.popMatrix();

        GLUT.flush();
    }
    private static final CEntryPointLiteral<Callback> displayCallback =
            CEntryPointLiteral.create(GLUTExample.class, "display");

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class)
    private static void idle() {
        rot += 0.01;
        GLUT.postRedisplay();
    }
    private static final CEntryPointLiteral<Callback> idleCallback =
            CEntryPointLiteral.create(GLUTExample.class, "idle");

    private interface Callback extends CFunctionPointer {
        @InvokeCFunctionPointer void invoke();
    }
}

