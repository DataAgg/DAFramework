@java -cp wCodeGen-all-1.0.jar io.github.datasays.codeGen2.ProjectGen projects.yml
@java -cp wCodeGen-all-1.0.jar io.github.datasays.codeGen2.GradleGen gradle.yml
gradle idea classes -p ..