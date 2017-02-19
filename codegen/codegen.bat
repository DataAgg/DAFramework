java -cp wCodeGen-all-1.0.jar io.github.datasays.codeGen2.ProjectGen projects.yml
java -jar wCodeGen-all-1.0.jar gradle.yml
cd ..
gradle cleanidea idea bootrepackage
cd codegen