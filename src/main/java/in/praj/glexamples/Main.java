/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.glexamples;

import com.oracle.svm.core.c.CGlobalData;
import com.oracle.svm.core.c.CGlobalDataFactory;
import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import org.graalvm.nativeimage.PinnedObject;
import org.graalvm.nativeimage.StackValue;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.c.function.CFunctionPointer;
import org.graalvm.nativeimage.c.function.InvokeCFunctionPointer;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Entry-point to the application
 */
@CContext(Main.Directives.class)
public class Main {
    public static final class Directives implements CContext.Directives {
        @Override
        public List<String> getHeaderFiles() {
            return Collections.singletonList("<GL/glut.h>");
        }

        @Override
        public List<String> getLibraries() {
            return Arrays.asList("GL", "glut");
        }
    }

    public static void main(String[] args) {
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
        GL.clearColor(0.5f, 0.5f, 0.5f, 0f);
        GL.shadeModel(GL.SMOOTH());
        try (var white = PinnedObject.create(new float[] {1f, 1f, 1f, 0f});
             var shine = PinnedObject.create(new float[] {70f})) {
            GL.lightfv(GL.LIGHT0(), GL.AMBIENT(), white.addressOfArrayElement(0));
            GL.lightfv(GL.LIGHT0(), GL.DIFFUSE(), white.addressOfArrayElement(0));
            GL.materialfv(GL.FRONT(), GL.SHININESS(), shine.addressOfArrayElement(0));
        }
        GL.enable(GL.LIGHTING());
        GL.enable(GL.LIGHT0());
        GL.enable(GL.DEPTH_TEST());

        // Callbacks
        GLUT.displayFunc(displayCallback.getFunctionPointer());
        GLUT.idleFunc(idleCallback.getFunctionPointer());

        // Begin
        GLUT.mainLoop();
    }

    // Global variable to store rotation angle
    private static final CGlobalData<CFloatPointer> rotation = CGlobalDataFactory.createBytes(() -> 4);

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void display() {
        GL.clear(GL.COLOR_BUFFER_BIT() | GL.DEPTH_BUFFER_BIT());

        GL.pushMatrix();
        GL.rotatef(rotation.get().read(), 0f, 1f, 1f);
        try (var mat = PinnedObject.create(new float[] {1, 0, 0, 0})) {
            GL.materialfv(GL.FRONT(), GL.DIFFUSE(), mat.addressOfArrayElement(0));
        }
        GLUT.wireTeapot(0.5);
        GL.popMatrix();

        GL.flush();
    }
    private static final CEntryPointLiteral<GLUT.Callback> displayCallback =
            CEntryPointLiteral.create(Main.class, "display");

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void idle() {
        var rotPtr = rotation.get();
        rotPtr.write(0.1f + rotPtr.read());
        GLUT.postRedisplay();
    }
    private static final CEntryPointLiteral<GLUT.Callback> idleCallback =
            CEntryPointLiteral.create(Main.class, "idle");
}
