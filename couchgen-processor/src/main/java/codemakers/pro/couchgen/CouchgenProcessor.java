package codemakers.pro.couchgen;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("codemakers.pro.couchgen.Doc")
public class CouchgenProcessor extends AbstractProcessor {

    private List<MethodSpec> accessMethodSpecs;
    private List<FieldSpec> fieldSpecs;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        accessMethodSpecs = new ArrayList<>();
        fieldSpecs = new ArrayList<>();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        final Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Doc.class);

        for (Element element : elements) {
            /*if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Doc can only use for classes");
                return false;
            }*/

            // processFields((TypeElement) element);


            try {
                generateDocTest((TypeElement) element);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
            }
        }
        return roundEnvironment.processingOver();

    }

    private void processFields(TypeElement element) {
        for (Element e : element.getEnclosedElements()) {
            if (e.getKind() == ElementKind.FIELD) {

                final FieldSpec fieldSpec = FieldSpec.builder(TypeName.get(e.asType()), e.getSimpleName().toString())
                        .addModifiers(Modifier.PRIVATE)
                        .build();

                fieldSpecs.add(fieldSpec);
                methodGetSpec(e);
                methodSetSpec(e);


            }
        }
    }

    private void methodGetSpec(Element e) {
        final MethodSpec methodSpec = MethodSpec
                .methodBuilder("get" + e.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(e.asType()))
                .addStatement("return " + e.getSimpleName().toString())
                .build();
        accessMethodSpecs.add(methodSpec);
    }

    private void methodSetSpec(Element e) {
        final MethodSpec methodSpec = MethodSpec
                .methodBuilder("set" + e.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(e.asType()), e.getSimpleName().toString())
                .addStatement("this." + e.getSimpleName().toString() + " = " + e.getSimpleName().toString())
                .build();

        accessMethodSpecs.add(methodSpec);
    }

    private void generateDoc(TypeElement element) throws IOException {
        final TypeSpec.Builder builder = TypeSpec.classBuilder("Doc" + element.getSimpleName().toString());
        builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        for (FieldSpec fieldSpec : fieldSpecs) {
            builder.addField(fieldSpec);
        }
        for (MethodSpec methodSpec : accessMethodSpecs) {
            builder.addMethod(methodSpec);
        }


        final TypeSpec docSpec = builder.build();
        JavaFile.builder("org.test.couchbasedb.data", docSpec)
                .build()
                .writeTo(processingEnv.getFiler());
    }

    private void generateDocTest(TypeElement element) throws IOException {
        final TypeSpec.Builder builder = TypeSpec.classBuilder("Doc" + element.getSimpleName().toString());
        builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);


        final TypeSpec docSpec = builder.build();
        JavaFile.builder("org.test.couchbasedb.data", docSpec)
                .build()
                .writeTo(processingEnv.getFiler());
    }

}
